package code_generation;

import java.util.ArrayList;

import code_generation.Register.Type;
import code_generation.StackArgument.Category;

public class StackFrame {
//	Local Variables
//	Saved Registers
//	Frame Pointer
//	RA
//	Arguments in reverse order
	private StackArgument[] argumentsToCurrentFunction;		//Parameters that were passed into the current function but not in registers
	private StackArgument[] returnAddress;
	private StackArgument[] oldFramePointer;
	private StackArgument[] calleeSavedRegisters;			//Saved when function is first called
	private StackArgument[] localVariables;
	private StackArgument[] callerSavedRegisters;			//Saved before calling new function
	private StackArgument[] argumentsToNextFunction;		//Parameters that are being passed into another function we are calling
	
	public StackFrame(int numArgumentsToCurrentFunction, int numCalleeSavedRegisters, int numLocalVariables){
		argumentsToCurrentFunction = new StackArgument[numArgumentsToCurrentFunction];
		returnAddress = new StackArgument[1];
		oldFramePointer = new StackArgument[1];
		calleeSavedRegisters = new StackArgument[numCalleeSavedRegisters];
		localVariables = new StackArgument[numLocalVariables];
	}
	
//	private void setArgumentsToCurrentFunction(String[] parametersOnStack){
//		argumentsToCurrentFunction = new StackArgument[parametersOnStack.length];
//		for(int i = 0; i < parametersOnStack.length; i++){
//			argumentsToCurrentFunction[i] = new StackArgument(parametersOnStack[i], Type variableType, boolean containsValue, Category category);
//		}
//	}
	
	private void setReturnAddress(){
		returnAddress[1] = new StackArgument("ReturnAddress", Type.INT, true, Category.RETURN_ADDRESS);
	}
	
	
}
