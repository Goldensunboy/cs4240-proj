package code_generation;

import code_generation.Register.RegisterType;

import com.exception.BadIRInstructionException;

public class IRParser {
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
	
	
	public static String getParameterName(String parameter){
		String[] names = parameter.split("\\%");
		if(names.length == 2)
			return names[0];	
		throw new BadIRInstructionException("Not a valid parameter name");
		
	}
	
	public static RegisterType getParameterType(String parameter){
		String[] names = parameter.split("\\%");
		if(names.length != 2)
			throw new BadIRInstructionException("Not a valid parameter");
		if(names[1].equals("i")||names[1].equals("a"))
			return RegisterType.INT;
		else if(names[1].equals("f"))
			return RegisterType.FLOAT;
		return RegisterType.UNINITIALIZED;
	}
}
