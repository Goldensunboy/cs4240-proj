package code_generation;

import code_generation.Register.Type;

public class StackArgument {
	
	public enum Category {UNINITIALIZED, CALLER_SAVED, CALLEE_SAVED, RETURN_ADDRESS, FRAME_POINTER, PARAMETERS}; 
	
	private String variableName;
	private Type variableType;
	private boolean containsValue; // True if the stack location contains a valid value
	private Category category;
	
	public StackArgument(String variableName, Type variableType, boolean containsValue, Category category){
		this.variableName = variableName;
		this.variableType = variableType;
		this.containsValue = containsValue;
		this.category = category;
	}
	
	public boolean getContainsValue(){
		return containsValue;
	}
	
	public void setContainsValue(boolean containsValue){
		this.containsValue = containsValue;
	}
}
