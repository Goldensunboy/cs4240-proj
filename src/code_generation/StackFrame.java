package code_generation;

import java.util.ArrayList;

import com.exception.BadDeveloperException;

import code_generation.Register.RegisterType;
import code_generation.StackArgument.Category;

public class StackFrame {
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
	
	private static StackFrame stackFrame;
	
	ArrayList<StackArgument> stack;
	
	public StackFrame(){
		stack = new ArrayList<StackArgument>();
	}
	
	public static void pushOnStack(StackArgument arg){
		// TODO see if already on the stack
		if(stackFrame == null){
			stackFrame = new StackFrame();
		}
		stackFrame.stack.add(arg);
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
			instruction += findVariableLocation(register.getRegisterName())+"($sp)";
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
		String instruction = ((register.getType() == RegisterType.INT)?"sw ":"swc ")+registerName+", ";
		instruction += findVariableLocation(variableName)+"($sp)";
		findVariable(variableName).setContainsValue(true);
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
