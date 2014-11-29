package code_generation;

import java.util.ArrayList;

import com.exception.BadDeveloperException;
import com.exception.CorruptedStackException;

import code_generation.Register.RegisterType;
import code_generation.StackArgument.Category;

public class StackFrame {
	
	private static StackFrame stackFrame;
	
	ArrayList<StackArgument> stack;
	
	public StackFrame(){
		stack = new ArrayList<StackArgument>();
	}

	int beginOfFrame = -1;
	
	public static void pushOnStack(StackArgument arg){
		// TODO see if already on the stack
		if(stackFrame == null){
			stackFrame = new StackFrame();
		}
		if(!stackFrame.stack.contains(arg))
			stackFrame.stack.add(arg);
		else
			throw new CorruptedStackException("Variable begin added, "+arg.getVariableName()+", is already on the stack");
		
	}
	
	/**
	 * Used temporarily during testing. TODO GET RID OF THIS.
	 * @param funcName
	 * @return
	 */
	public static ArrayList<String> getFuncVariables(String funcName){
		ArrayList<String> funcVariables = new ArrayList<String>();
		funcVariables.add("x$3%i");
		funcVariables.add("y$3%i");
		funcVariables.add("z$3%f");
		return funcVariables;
	}
	/**
	 * Used temporarily during testing. TODO GET RID OF THIS.
	 * @param funcName
	 * @return
	 */	
	public static ArrayList<String> getFuncParams(String funcName){
		ArrayList<String> funcParams = new ArrayList<String>();
		funcParams.add("a$3%i");
		funcParams.add("b$3%i");
		funcParams.add("c$3%f");
		return funcParams;
	}
	
	/**
	 * Builds up the stack for the current function
	 * @param functionName
	 */
	public static String enterCurrentFrame(String functionName) {		
		
		if(stackFrame == null){
			stackFrame = new StackFrame();
		}
		/* parameters */
		ArrayList<String> localParameters = getFuncParams(functionName);
		for(String parameter : localParameters){
			pushOnStack(new StackArgument(IRParser.getParameterName(parameter), IRParser.getParameterType(parameter), false, Category.LOCAL_VARIABLES));
		}
		
		stackFrame.beginOfFrame = stackFrame.stack.size(); /* points to one after the last parameter */
		
		/* return address */
		pushOnStack(new StackArgument("$ra", RegisterType.INT, false, Category.RETURN_ADDRESS));
		
		/* old frame pointer */
		pushOnStack(new StackArgument("$fp", RegisterType.INT, false, Category.FRAME_POINTER));
		
		/* local variables */
		ArrayList<String> localVariables = getFuncVariables(functionName);
		for(String var : localVariables){
			pushOnStack(new StackArgument(IRParser.getParameterName(var), IRParser.getParameterType(var), false, Category.LOCAL_VARIABLES));
		}
		
		/* callee saved registers */
		for(int i = 0; i < 7; i++){
			pushOnStack(new StackArgument("$s"+i, RegisterType.INT, false, Category.CALLEE_SAVED));
		}
		for(int i = 16; i < 31; i++){
			pushOnStack(new StackArgument("$f"+i, RegisterType.FLOAT, false, Category.CALLEE_SAVED));
		}
		
		return "addi $sp, $sp, "+ (stackFrame.stack.size()-1 + stackFrame.beginOfFrame); /* generate the instruction to move the stack pointer at the being of function */
	}
	
	/**
	 * Tears down the current stack frame
	 */
	public static String exitCurrentFrame(){	

		if(stackFrame == null){
			stackFrame = new StackFrame();
		}
		stackFrame.stack.clear();
		String instruction = "addi $sp, $sp, "+ (stackFrame.stack.size()-1 - stackFrame.beginOfFrame);
		stackFrame.beginOfFrame = -1;
		return instruction;
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
			String instruction = ((isInt)?"lw ":"lwc ")+registerName+", ";
			instruction += findVariableLocation(variableName)+"($sp)";
			return instruction;
		}
		throw new BadDeveloperException("Variable on stack does not contain a value.");
	}
	
	/**
	 * Given a variable name and register name, this function will generate the instruction to load the value from the stack and into the register
	 * @return
	 */
	public static String generateLoad(Register register){ /* NOTE: this is not an instance of a register from the register file */
		if(stackFrame == null){
			stackFrame = new StackFrame();
		}
		if(findVariable(register.getVariableName()).getContainsValue()){
			String instruction = ((register.getRegisterType() == RegisterType.INT)?"lw ":"lwc ")+register.getRegisterName()+", ";
			instruction += findVariableLocation(register.getVariableName())+"($sp)";
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
		String instruction = ((isInt)?"sw ":"swc ")+registerName+", ";
		instruction += findVariableLocation(variableName)+"($sp)";
		findVariable(variableName).setContainsValue(true);
		return instruction;
	}
	
	public static String generateStore(Register register){ /* NOTE: this is not an instance of a register from the register file */
		if(stackFrame == null){
			stackFrame = new StackFrame();
		}
		String instruction = ((register.getRegisterType() == RegisterType.INT)?"sw ":"swc ")+register.getRegisterName()+", ";
		instruction += findVariableLocation(register.getVariableName())+"($sp)";
		findVariable(register.getVariableName()).setContainsValue(true);
		return instruction;
	}
	
	/**
	 * Will be relative to the stack pointer. Will return 1 if not found.
	 * @param variableName
	 * @return
	 */
	private static int findVariableLocation(String variableName){
		if(stackFrame == null){
			stackFrame = new StackFrame();
		}
		for(int i = 0; i <stackFrame.stack.size(); i++){
			if(stackFrame.stack.get(i).getVariableName().equals(variableName)){
				return -(stackFrame.stack.size() - i) + 1;
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