package code_generation;



import com.exception.BadDeveloperException;
import com.exception.RegisterInUseException;

public class Register {

	public enum RegisterType {UNINITIALIZED, INT, FLOAT};

	public enum Category {UNINITIALIZED, CALLER_SAVED, CALLEE_SAVED, RETURN_ADDRESS, FRAME_POINTER, PARAMETERS, LOCAL_VARIABLES, RETURN_VALUE, STACK_POINTER}; 
	
	private String registerName;
	private String registerNumber; // Yes, this is a string because float register "numbers" contain an f
	private RegisterType registerType;
	private boolean inUse;
	private String variableName;
	private Category category;
	
	
	public Register(String registerName, String registerNumber, RegisterType registerType, Category category){
		this.registerName = registerName;
		this.registerNumber = registerNumber;
		this.registerType = registerType;
		this.category = category;
		this.inUse = false;
	}
	
//	public Register(String IRregister){
//		this.registerName = IRParser.getRegisterName(IRregister);
//		this.registerNumber = "-1";
//		this.registerType = IRParser.getRegisterType(IRregister);
//		this.inUse = true;
//		this.variableName = 
//				IRParser.getVariableName(IRregister);
//	}
//	
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
	
	public Category getCategory(){
		return category;
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
	
	public static RegisterType getRegisterType(String register){
		if(RegisterFile.getIntRegisters().containsKey(register))
			return RegisterType.INT;
		else if(RegisterFile.getFloatRegisters().containsKey(register))
			return RegisterType.FLOAT;
		throw new BadDeveloperException("This isn't a register");
	}
	
	public static boolean isCalleeRegister(String registerName){
		if(RegisterFile.getIntRegisters().containsKey(registerName)){
			Register register = RegisterFile.getIntRegisters().get(registerName);
			if(register.category==Category.CALLEE_SAVED)
				return true;
			return false;
		}
		else if(RegisterFile.getFloatRegisters().containsKey(registerName)){
			
		}
		throw new BadDeveloperException("This isn't a register");
	}
	
//	public static boolean isValidIRRegister(String register){
//		try {
//			IRParser.getRegisterName(register);
//			IRParser.getRegisterType(register);
//			IRParser.getVariableName(register);
//		} catch (BadIRInstructionException e){
//			return false;
//		}
//		return true;
//	}
	

}
