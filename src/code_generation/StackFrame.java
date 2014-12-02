package code_generation;

import java.util.ArrayList;

import com.exception.BadDeveloperException;
import com.exception.CorruptedStackException;

import code_generation.Register.RegisterType;
import code_generation.StackArgument.Category;

public class StackFrame {
	
	private static StackFrame stackFrame;
	
	ArrayList<StackArgument> stack;
	
	private StackFrame(){
		stack = new ArrayList<StackArgument>();
	}

	int beginOfFrame = -1;
	
	public static void pushOnStack(StackArgument arg){
		if(stackFrame == null){
			stackFrame = new StackFrame();
		}
		if(!stackFrame.stack.contains(arg))
			stackFrame.stack.add(arg);
		else
			throw new CorruptedStackException("Variable begin added, "+arg.getVariableName()+", is already on the stack");
		
	}
	

	
	/**
	 * Builds up the stack for the current function
	 * @param functionName
	 */
	public static String enterCurrentFrame(String functionName) {		
		
		if(stackFrame == null){
			stackFrame = new StackFrame();
		}
		
		String MIPSInstruction = "";
		/* parameters */
		ArrayList<String> localParameters = IRParser.getFuncParams(functionName);
		for(String parameter : localParameters){
			pushOnStack(new StackArgument(IRParser.getVariableName(parameter), IRParser.getVariableType(parameter), true, Category.PARAMETERS)); 
		}
		
		stackFrame.beginOfFrame = stackFrame.stack.size(); /* points to one after the last parameter */
		
		/* return address */
		pushOnStack(new StackArgument("$ra", RegisterType.INT, false, Category.RETURN_ADDRESS));
		
		/* old frame pointer */
		pushOnStack(new StackArgument("$fp", RegisterType.INT, false, Category.FRAME_POINTER));
		
		/* local variables */
		ArrayList<String> localVariables = IRParser.getFuncVariables(functionName);
		for(String var : localVariables){
			pushOnStack(new StackArgument(IRParser.getVariableName(var), IRParser.getVariableType(var), false, Category.LOCAL_VARIABLES));
		}
		
		/* callee saved registers */ //TODO do not need to save all registers every time
		for(int i = 0; i < 7; i++){
			pushOnStack(new StackArgument("$s"+i, RegisterType.INT, false, Category.CALLEE_SAVED));
		}
		for(int i = 16; i < 31; i++){
			pushOnStack(new StackArgument("$f"+i, RegisterType.FLOAT, false, Category.CALLEE_SAVED));
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
	
	public static String callFunction(String functionName){
		if(stackFrame == null){
			stackFrame = new StackFrame();
		}
		
		String MIPSInstruction = "";
		
		int oldSPLocation = stackFrame.stack.size();
		/* caller saved registers */
		for(int i = 0; i < 7; i++){
			pushOnStack(new StackArgument("$t"+i, RegisterType.INT, false, Category.CALLER_SAVED));
		}
		for(int i = 4; i < 11; i++){
			pushOnStack(new StackArgument("$f"+i, RegisterType.FLOAT, false, Category.CALLER_SAVED));
		}	
		/* parameters for the next function */
		ArrayList<String> localParameters = IRParser.getFuncParams(functionName);
		for(String parameter : localParameters){
			pushOnStack(new StackArgument(IRParser.getVariableName(parameter), IRParser.getVariableType(parameter), true, Category.PARAMETERS)); 
		}
		MIPSInstruction += "addi $sp, $sp, "+ (4*(stackFrame.stack.size() - oldSPLocation)); //TODO check indexing /* generate the instruction to move the stack pointer at the being of function */
		
		return MIPSInstruction;
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
	
//	/**
//	 * Given a variable name and register name, this function will generate the instruction to load the value from the stack and into the register
//	 * @return
//	 */
//	public static String generateLoad(Register register){ /* NOTE: this is not an instance of a register from the register file */
//		if(stackFrame == null){
//			stackFrame = new StackFrame();
//		}
//		if(findVariable(register.getVariableName()).getContainsValue()){
//			String instruction = ((register.getRegisterType() == RegisterType.INT)?"lw ":"lwc1 ")+register.getRegisterName()+", ";
//			instruction += findVariableLocation(register.getVariableName())+"($sp)";
//			return instruction;
//		}
//		throw new BadDeveloperException("Variable on stack does not contain a value.");
//	}
	
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
	
//	public static String generateStore(Register register){ /* NOTE: this is not an instance of a register from the register file */
//		if(stackFrame == null){
//			stackFrame = new StackFrame();
//		}
//		String instruction = ((register.getRegisterType() == RegisterType.INT)?"sw ":"swc1 ")+register.getRegisterName()+", ";
//		instruction += findVariableLocation(register.getVariableName())+"($sp)";
//		findVariable(register.getVariableName()).setContainsValue(true);
//		return instruction;
//	}
	
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
	
}

//	Local Variables
//	Saved Registers
//	Frame Pointer
//	RA
//	Arguments in reverse order
//	private StackArgument[] argumentsToCurrentFunction;		//Parameters that were passed into the current function but not in registers
//	private StackArgument[] returnAddress;
//	private StackArgument[] oldFramePointer;
//	private StackArgument[] calleeSavedRegisters;			//Saved when function is first called
//	private StackArgument[] localVariables;
//	private StackArgument[] callerSavedRegisters;			//Saved before calling new function
//	private StackArgument[] argumentsToNextFunction;		//Parameters that are being passed into another function we are calling

//	private int stackPointer = -1;
//	private int framePointer = -1;

//	private int argumentsToCurrentFunctionIndex = -1;
//	private int returnAddressIndex = -1;
//	private int calleeSavedRegistersIndex = -1;
//	private int localVariablesIndex = -1;
//	private int callerSavedRegistersIndex = -1;
//	private int argumentsToNextFunctionIndex = -1;