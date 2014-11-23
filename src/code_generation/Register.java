package code_generation;

import com.exception.RegisterInUseException;

public class Register {

	public enum Type {UNINITALIZED, INT, FLOAT};
	
	private String registerName;
	private String registerNumber; // Yes, this is a string because float register "numbers" contain an f
	private Type registerType;
	private boolean inUse;
	private String variableName;
	
	
	public Register(String registerName, String registerNumber, Type registerType){
		this.registerName = registerName;
		this.registerNumber = registerNumber;
		this.registerType = registerType;
		this.inUse = false;
		this.registerName = "";
	}
	/**
	 * Adds variable to register. If the register already contains a variable, an exception is thrown.
	 * @param variableName
	 */
	public void addVariable(String variableName){
		if(inUse && !variableName.equals(this.variableName))
			throw new RegisterInUseException(registerName +" currently contains "+ this.variableName
					+ " you must first call removeVariable before you can put "+variableName+" in "+
					registerName);
		this.variableName = variableName;
		this.inUse = true;
	}
	
	/**
	 * Removes the variable from the register.
	 */
	public void removeVariable(){
		this.inUse = false;
		this.registerName = "";
	}
	
	public boolean containsVariable(){
		return inUse;
	}
	
	
	/**
	 * Gets register name from complicated IR string. NOTE that this is static!
	 * Returns null if the input cannot be parsed for a valid register name
	 */
	public static String getRegisterName(String register){
		String[] name = register.split("#");	
		if(name.length == 2 && (RegisterFile.getIntRegisters().containsKey(name[1]) || RegisterFile.getFloatRegisters().containsKey(name[1])))
			return name[1];
		return null;
	}
	
	/**
	 * Gets type from register from complicated IR string. NOTE that this is static!
	 * Returns null if the input cannot be parsed for a valid register name
	 */
	public static Type getRegisterType(String register){
		// TODO
		return Type.INT;
	}
	
	/**
	 * Gets variable name from register from complicated IR string. NOTE that this is static!
	 * Returns null if the input cannot be parsed for a valid register name
	 */
	public static String getRegisterVariableName(String register){
		// TODO
		return register;		
	}
}
