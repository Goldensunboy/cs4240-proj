package code_generation;

import code_generation.Register.RegisterType;

public class StackArgument {
	
	public enum Category {UNINITIALIZED, CALLER_SAVED, CALLEE_SAVED, RETURN_ADDRESS, FRAME_POINTER, PARAMETERS, LOCAL_VARIABLES}; 
	
	private String variableName;
	private RegisterType variableType;
	private boolean containsValue; // True if the stack location contains a valid value
	private Category category;
	
	public StackArgument(String variableName, RegisterType variableType, boolean containsValue, Category category){
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
	
	public String getVariableName(){
		return variableName;
	}
	
	public String toString(){
		return "Category: "+category+",\tName: "+variableName+",\tType "+variableType+",\t Contains Value: "+containsValue;
	}
	
	public boolean equals(Object o){
		try {
			if(((StackArgument)o).variableName.equals(variableName))
				return true;
			return false;
		} catch (ClassCastException e){
			return false;
		}
	}
}
