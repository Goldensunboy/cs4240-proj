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
		this.registerName = IRParser.getRegisterName(IRregister);
		this.registerNumber = "-1";
		this.registerType = IRParser.getRegisterType(IRregister);
		this.inUse = true;
		this.variableName = 
				IRParser.getVariableName(IRregister);
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
			IRParser.getRegisterName(register);
			IRParser.getRegisterType(register);
			IRParser.getVariableName(register);
		} catch (BadIRInstructionException e){
			return false;
		}
		return true;
	}
	

}
