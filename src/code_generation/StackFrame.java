package code_generation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.exception.BadDeveloperException;
import com.exception.CorruptedStackException;
import com.exception.InvalidInvocationException;
import com.symbol_table.SymbolTableManager;

import code_generation.Register.Category;
import code_generation.Register.RegisterType;

public class StackFrame {
	
	private static StackFrame stackFrame;
	
	ArrayList<StackArgument> stack;
	
	private StackFrame(){
		stack = new ArrayList<StackArgument>();
	}

	int beginOfFrame = -1;
	int beforeFuncCall = -1;
	
	private static void pushOnStack(StackArgument arg){
		if(stackFrame == null){
			stackFrame = new StackFrame();
		}
		if(!stackFrame.stack.contains(arg))
			stackFrame.stack.add(arg);
		else
			throw new CorruptedStackException("Variable begin added, "+arg.getVariableName()+", is already on the stack");
	}
	
	private static void pushArrayVariableOnStack(HashMap<String, HashMap<String, Integer>> functionArraySizes, String functionName, String variable){
		if(stackFrame == null){
			stackFrame = new StackFrame();
		}
		if(!stackFrame.stack.contains(IRParser.getVariableName(variable))){
			int size = IRParser.sizeOfArray(functionArraySizes,functionName,variable);
			/* Push array address on stack */
			pushOnStack(new StackArgument(IRParser.getVariableName(variable),RegisterType.INT,true,Category.LOCAL_VARIABLES_ARRAY));
			/* Push array elements on stack */
			for(int i = 0; i < size; i++)
				pushOnStack(new StackArgument(IRParser.getVariableName(variable)+"_array["+i+"]",IRParser.getArrayType(variable),true,Category.LOCAL_VARIABLES));
		} else
			throw new CorruptedStackException("Variable begin added, "+IRParser.getVariableName(variable)+", is already on the stack");
	}
	
	
	private static void popOffStack(){
		if(stackFrame == null){
			stackFrame = new StackFrame();
		}
		stackFrame.stack.remove(stackFrame.stack.size()-1);
	}

	
	/**
	 * Builds up the stack for the current function
	 * @param functionName
	 */
	public static String enterCurrentFrame(String functionName, SymbolTableManager symbolTableManager,HashMap<String, List<String>> functionVariables,
			HashMap<String, List<String>> functionRegisters,HashMap<String, HashMap<String, Integer>> functionArraySizes) {		
		
		if(stackFrame == null){
			stackFrame = new StackFrame();
		}
		
		String MIPSInstruction = "";
		/* parameters */
		List<String> localParameters = IRParser.getFuncParams(functionName, symbolTableManager);
		for(String parameter : localParameters){
			pushOnStack(new StackArgument(IRParser.getVariableName(parameter), IRParser.getVariableType(parameter), true, Category.PARAMETERS)); 
		}
		
		stackFrame.beginOfFrame = stackFrame.stack.size(); /* points to one after the last parameter */
		
		/* return address */
		pushOnStack(new StackArgument("$ra", RegisterType.INT, false, Category.RETURN_ADDRESS));
		
		/* old frame pointer */
		pushOnStack(new StackArgument("$fp", RegisterType.INT, false, Category.FRAME_POINTER));
		
		/* local variables */
		List<String> localVariables = IRParser.getFuncVariables(functionName,functionVariables);
		List<String> localArrays = new ArrayList<String>();
		for(String variable : localVariables){
			if(IRParser.isArray(variable)){
				pushArrayVariableOnStack(functionArraySizes,functionName,variable);
				localArrays.add(IRParser.getVariableName(variable));
			} else 
				pushOnStack(new StackArgument(IRParser.getVariableName(variable), IRParser.getVariableType(variable), false, Category.LOCAL_VARIABLES));
		}
		
		/* callee saved registers */
		List<String> usedRegisters = IRParser.getFuncCalleeRegisters(functionName,functionRegisters);
		for(String register: usedRegisters){
			pushOnStack(new StackArgument(register, Register.getRegisterType(register), false, Category.CALLEE_SAVED));
		}
		
		MIPSInstruction += "addi $sp, $sp, "+ (4*(stackFrame.stack.size() - stackFrame.beginOfFrame)); //TODO check indexing /* generate the instruction to move the stack pointer at the being of function */	
		return MIPSInstruction;
	}
	
	/**
	 * Generates code to exit the current stack frame but does NOT tear down the current frame!
	 * To be used when hitting a return statement. However, this function does not remove the current
	 * frame from this data structure.
	 */
	public static String exitCurrentFrame(){	

		if(stackFrame == null){
			stackFrame = new StackFrame();
		}
		if(isEmpty())
			throw new BadDeveloperException("Cannot exit empty frame");
		String instruction = "addi $sp, $sp, "+ -(4*(stackFrame.stack.size() - stackFrame.beginOfFrame)); //TODO check indexing
		return instruction;
	}
	
	/**
	 * Sets up stack before a function is called
	 * @param functionName
	 * @return
	 */
	public static String callingFunctionBegin(String functionName,SymbolTableManager symbolTableManager,HashMap<String, List<String>> functionVariables, HashMap<String, List<String>> functionRegisters){
		if(stackFrame == null){
			stackFrame = new StackFrame();
		}
		
		String MIPSInstruction = "";
		
		stackFrame.beforeFuncCall = stackFrame.stack.size();
		/* caller saved registers */
		List<String> usedRegisters = IRParser.getFuncCallerRegisters(functionName,functionRegisters);
		for(String register: usedRegisters){
			pushOnStack(new StackArgument(register,  Register.getRegisterType(register), false, Category.CALLER_SAVED));
		}

		List<String> localParameters = IRParser.getFuncParams(functionName, symbolTableManager);
		for(String parameter : localParameters){
			pushOnStack(new StackArgument(IRParser.getVariableName(parameter)+"_param", IRParser.getVariableType(parameter), true, Category.PARAMETERS)); 
		}
		MIPSInstruction += "addi $sp, $sp, "+ (4*(stackFrame.stack.size() - stackFrame.beforeFuncCall)); //TODO check indexing /* generate the instruction to move the stack pointer at the being of function */
		
		return MIPSInstruction;
	}
	/**
	 * arrayAddressAddress is the register that contains the address of where the address of the array is going. 
	 * @return
	 */
	public static String initializeArray(String array, String arrayAddressAddress, String register, int arraySize){
		String MIPSInstruction = "";
		boolean isInt = RegisterFile.isIntRegister(array);
		MIPSInstruction += "\naddi "+arrayAddressAddress+", "+arrayAddressAddress+", 4";
		MIPSInstruction += "\n"+generateStore(IRParser.getVariableName(array), arrayAddressAddress, true);
		for(int i = 0; i < arraySize; i++){
			MIPSInstruction += "\n"+((isInt)?"sw ":"swc1 ")+register+", 0("+arrayAddressAddress+")";
			MIPSInstruction += "\naddi "+arrayAddressAddress+", "+arrayAddressAddress+", 4";
		}
		return MIPSInstruction;
	}
	
	
	/**
	 * Tears down stack after the function that was called returns
	 */
	public static String callingFunctionEnd(String functionName){
		if(stackFrame == null){
			stackFrame = new StackFrame();
		}
		int amountToPop = stackFrame.stack.size() - stackFrame.beforeFuncCall;
		for(int i = 0; i < amountToPop; i++){
			popOffStack();
		}
		String instruction = "addi $sp, $sp, "+ -(4*(amountToPop)); 
		return instruction;
	}
	
	/**
	 * Clears the current frame
	 */
	public static void emptyFrame(){
		stackFrame.stack.clear();
		stackFrame.beginOfFrame = -1;
		
	}
	
	
	public static boolean isEmpty(){
		if(stackFrame == null){
			stackFrame = new StackFrame();
		}
		return stackFrame.stack.isEmpty();
	}	
	
	
	
	public static void printStackFrame(){
		if(stackFrame == null){
			stackFrame = new StackFrame();
		}
		System.out.println("Current Stack Frame:");
		for(int i = 0; i < stackFrame.stack.size(); i++){
			System.out.println(stackFrame.stack.get(i));
		}
	}
	
	
	
	
	/**
	 * Given a variable name and register name, this function will generate the instruction to load the value from the stack and into the register
	 * @param variableName
	 * @param registerName
	 * @return
	 */
	public static String generateLoad(String variableName, String registerName, boolean isInt){
		if(stackFrame == null){
			stackFrame = new StackFrame();
		}
		if(findVariable(variableName).getContainsValue()){
			String instruction = ((isInt)?"lw ":"lwc1 ")+registerName+", ";
			instruction += findVariableLocation(variableName)+"($sp)";
			return instruction;
		}
		throw new BadDeveloperException("Variable on stack does not contain a value.");
	}
	

	/**
	 * Given a variable name and a register name, this function will generate the instruction to store the value in the register to the stack
	 * @param variableName
	 * @param registerName
	 * @return
	 */
	public static String generateStore(String variableName, String registerName, boolean isInt){
		if(stackFrame == null){
			stackFrame = new StackFrame();
		}
		String instruction = ((isInt)?"sw ":"swc1 ")+registerName+", ";
		instruction += findVariableLocation(variableName)+"($sp)";
		findVariable(variableName).setContainsValue(true);
		return instruction;
	}
	
	/**
	 * Will be relative to the stack pointer and in BYTES. Will return 1 if not found.
	 * @param variableName
	 * @return
	 */
	private static int findVariableLocation(String variableName){
		if(stackFrame == null){
			stackFrame = new StackFrame();
		}
		for(int i = 0; i <stackFrame.stack.size(); i++){
			if(stackFrame.stack.get(i).getVariableName().equals(variableName)){
				return (-(stackFrame.stack.size() - i) + 1)*4;
			}
		}
		throw new BadDeveloperException("Variable is not in the stack");
	}

	/**
	 * Returns the stack argument that corresponds to the name
	 * @param variableName
	 * @return
	 */
	private static StackArgument findVariable(String variableName){
		if(stackFrame == null){
			stackFrame = new StackFrame();
		}
		for(int i = 0; i <stackFrame.stack.size(); i++){
			if(stackFrame.stack.get(i).getVariableName().equals(variableName)){
				return stackFrame.stack.get(i);
			}
		}
		throw new BadDeveloperException("Variable is not in the stack");
	}
	
	public static RegisterType getVariableType(String variableName){
		if(stackFrame == null){
			stackFrame = new StackFrame();
		}
		for(int i = 0; i <stackFrame.stack.size(); i++){
			if(stackFrame.stack.get(i).getVariableName().equals(variableName)){
				return stackFrame.stack.get(i).getType();
			}
		}
		throw new BadDeveloperException("Variable is not in the stack");
	}

	public static boolean isOnStack(String variableName){
		if(stackFrame == null){
			stackFrame = new StackFrame();
		}
		for(int i = 0; i <stackFrame.stack.size(); i++){
			if(stackFrame.stack.get(i).getVariableName().equals(variableName)){
				return true;
			}
		}
		return false;
	}
}
