package code_generation;

import java.util.ArrayList;
import java.util.List;

import code_generation.Register.RegisterType;

import com.attribute.FunctionAttribute;
import com.exception.BadIRInstructionException;
import com.symbol_table.SymbolTableManager;

public class IRParser {
	
	/*
	 * Would like to be able to get the local variables, parameters, if its void return and callee registers used
	 */
	
	/* 
	 * SAMAN: store for call, callr, return
	 * 
	 * MARISSA: reduce register saves, hook up our code, library code, arrays
	 * 
	 * add/beq with mixed literals and registers what about type conversions?
	 * params are all passed on stack right now
	 * transfering float to int MIXED
	 * Function calls
	 * Need to handle float literals, arrays, non naive
	 * Write to file
	 * library calls
	 * Currently we are over saving registers
	 */
	/**
	 * Used temporarily during testing. TODO GET RID OF THIS.
	 * @param funcName
	 * @return
	 */
	public static ArrayList<String> getFuncVariables(String funcName){
		ArrayList<String> funcVariables = new ArrayList<String>();
		funcVariables.add("int_var2%i");
		funcVariables.add("fp_var2%f");
		return funcVariables;
	}
	/**
	 * Used temporarily during testing. TODO GET RID OF THIS.
	 * First param is in first location in array list
	 * @param funcName
	 * @return
	 */	
	public static List<String> getFuncParams(String functionName, SymbolTableManager symbolTableManager){
//		funcParams.add("int_var1%i");
//		funcParams.add("b$3%i");
//		funcParams.add("c$3%f");
		
		FunctionAttribute functionAttribute = symbolTableManager.getFunctionAttribute(functionName);
		List<String> parameters = new ArrayList<String>();
		for(int i = 0;i<functionAttribute.getAcrualtParameters().size();i++)
			parameters.set(i, functionAttribute.getAcrualtParameters().get(i)+functionAttribute.getParameterTypes().get(i).getType().getSuffix());
		return parameters;
	}
	
	/**
	 * Used temporarily during testing. TODO GET RID OF THIS.
	 * @param funcName
	 * @return
	 */	
	public static RegisterType returnType(String funcName, SymbolTableManager symbolTableManager){
		FunctionAttribute functionAttribute = symbolTableManager.getFunctionAttribute(funcName);
		functionAttribute.getReturnTypeName();
		return RegisterType.INT;
	}
	

	

	/**
	 * Gets variable name from register from complicated. NOTE that this is static!
	 * Returns null if the input cannot be parsed for a valid register name
	 * @param variable
	 * @return
	 */
	public static String getVariableName(String variable){
		String[] names = variable.split("\\%");
		if(names.length == 2)
			return names[0];	
		throw new BadIRInstructionException("Not a valid variable");
	}
	
	/**
	 * Gets variable type from register from complicated. NOTE that this is static!
	 * Returns null if the input cannot be parsed for a valid register name
	 * @param variable
	 * @return
	 */
	public static RegisterType getVariableType(String variable){
		String[] names = variable.split("\\%");
		if(names.length == 2) {
			if(names[1].equals("i"))
				return RegisterType.INT;
			else if(names[1].equals("f"))
				return RegisterType.FLOAT;
		}
		throw new BadIRInstructionException("Not a valid variable");
	}

	
	
	
	
//	
//	/**
//	 * Gets register name from complicated IR string. NOTE that this is static!
//	 * Returns null if the input cannot be parsed for a valid register name
//	 */
//	public static String getRegisterName(String register){
//		String[] name = register.split("#");	
//		if(name.length == 2 && (RegisterFile.getIntRegisters().containsKey(name[1]) || RegisterFile.getFloatRegisters().containsKey(name[1])))
//			return name[1];
//		throw new BadIRInstructionException("Not a valid register");
//	}
//	
//	/**
//	 * Gets type from register from complicated IR string. NOTE that this is static!
//	 * Returns null if the input cannot be parsed for a valid register name
//	 */
//	public static RegisterType getRegisterType(String register){
//		String[] names = register.split("\\%|\\#");
//		if(names.length != 3)
//			throw new BadIRInstructionException("Not a valid register");
//		if(names[1].equals("i")||names[1].equals("a"))
//			return RegisterType.INT;
//		else if(names[1].equals("f"))
//			return RegisterType.FLOAT;
//		return RegisterType.UNINITIALIZED;
//	}
//	
	
//	public static String getParameterName(String parameter){
//		String[] names = parameter.split("\\%");
//		if(names.length == 2)
//			return names[0];	
//		throw new BadIRInstructionException("Not a valid parameter name");
//		
//	}
	
//	public static RegisterType getParameterType(String parameter){
//		String[] names = parameter.split("\\%");
//		if(names.length != 2)
//			throw new BadIRInstructionException("Not a valid parameter");
//		if(names[1].equals("i")||names[1].equals("a"))
//			return RegisterType.INT;
//		else if(names[1].equals("f"))
//			return RegisterType.FLOAT;
//		return RegisterType.UNINITIALIZED;
//	}
}
