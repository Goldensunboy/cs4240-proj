package code_generation;

import com.exception.BadDeveloperException;
import com.exception.BadIRInstructionException;
import com.exception.RegisterInUseException;

public class Register {

	public enum RegisterType {UNINITIALIZED, INT, FLOAT};
	
	private String registerName;
	private String registerNumber; // Yes, this is a string because float register "numbers" contain an f
	private RegisterType registerType;
	private boolean inUse;
	private String variableName;
	
	
	public Register(String registerName, String registerNumber, RegisterType registerType){
		this.registerName = registerName;
		this.registerNumber = registerNumber;
		this.registerType = registerType;
		this.inUse = false;
	}
	
	public Register(String IRregister){
		this.registerName = getRegisterName(IRregister);
		this.registerNumber = "-1";
		this.registerType = getRegisterType(IRregister);
		this.inUse = false;
		this.variableName = getVariableName(IRregister);
	}
	
	/**
	 * Adds variable to register. If the register already contains a variable, an exception is thrown.
	 * @param variableName
	 */
	public void addVariable(String variableName){
		if(inUse && !variableName.equals(this.variableName)) {
			throw new RegisterInUseException(registerName +" currently contains "+ this.variableName
					+ " you must first call removeVariable before you can put "+variableName+" in "+
					registerName);
		}
		this.variableName = variableName;
		this.inUse = true;
	}
	
	public String getVariableName(){
		if(inUse)
			return variableName;
		throw new BadDeveloperException("Register does not contain any value");
	}
	
	public RegisterType getRegisterType(){
		if(inUse)
			return registerType;
		throw new BadDeveloperException("Register does not contain any value");
	}
	
	public String getRegisterName(){
		if(inUse)
			return registerName;
		throw new BadDeveloperException("Register does not contain any value");
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
	
	public static boolean isValidIRRegister(String register){
		try {
			getRegisterName(register);
			getRegisterType(register);
			getVariableName(register);
		} catch (BadIRInstructionException e){
			return false;
		}
		return true;
	}
	
	/**
	 * Gets register name from complicated IR string. NOTE that this is static!
	 * Returns null if the input cannot be parsed for a valid register name
	 */
	public static String getRegisterName(String register){
		String[] name = register.split("#");	
		if(name.length == 2 && (RegisterFile.getIntRegisters().containsKey(name[1]) || RegisterFile.getFloatRegisters().containsKey(name[1])))
			return name[1];
		throw new BadIRInstructionException("Not a valid register");
	}
	
	/**
	 * Gets type from register from complicated IR string. NOTE that this is static!
	 * Returns null if the input cannot be parsed for a valid register name
	 */
	public static RegisterType getRegisterType(String register){
		String[] names = register.split("\\%|\\#");
		if(names.length != 3)
			throw new BadIRInstructionException("Not a valid register");
		if(names[1].equals("i")||names[1].equals("a"))
			return RegisterType.INT;
		else if(names[1].equals("f"))
			return RegisterType.FLOAT;
		return RegisterType.UNINITIALIZED;
	}
	
	/**
	 * Gets variable name from register from complicated IR string. NOTE that this is static!
	 * Returns null if the input cannot be parsed for a valid register name
	 */
	public static String getVariableName(String register){
		String[] names = register.split("\\%");
		if(names.length == 2)
			return names[0];	
		throw new BadIRInstructionException("Not a valid register");
	}
}
