package code_generation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import code_generation.Register.RegisterType;

import com.attribute.FunctionAttribute;
import com.compiler.Type;
import com.exception.BadIRInstructionException;
import com.exception.InvalidInvocationException;
import com.exception.InvalidTypeException;
import com.exception.UndeclaredFunctionException;
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

	private static List<String> getFuncRegisters(String funcName,HashMap<String, List<String>> functionRegisters){
		if(funcName.equals("main"))
			funcName ="FUNC_main";
		List<String> variables = functionRegisters.get(funcName);
		if(variables!=null)
			return variables;
		throw new UndeclaredFunctionException("Function does not exists in the IR");
	}
	
	public static List<String> getFuncCalleeRegisters(String funcName,HashMap<String, List<String>> functionRegisters){
		List<String> funcRegisters = getFuncRegisters(funcName,functionRegisters);
		List<String> calleeRegisters = new ArrayList<String>();
		for(String register: funcRegisters){
			if(RegisterFile.isCalleeRegister(register))
				calleeRegisters.add(register);
		}
		return calleeRegisters;
	}
	
	public static List<String> getFuncCallerRegisters(String funcName,HashMap<String, List<String>> functionRegisters){
		List<String> funcRegisters = getFuncRegisters(funcName,functionRegisters);
		List<String> callerRegisters = new ArrayList<String>();
		for(String register: funcRegisters){
			if(RegisterFile.isCallerRegister(register))
				callerRegisters.add(register);
		}
		return callerRegisters;
	}
	
	public static List<String> getFuncVariables(String funcName,HashMap<String, List<String>> functionVariables){
		if(funcName.equals("main"))
			funcName ="FUNC_main";
		List<String> variables = functionVariables.get(funcName);
		if(variables==null)
			throw new UndeclaredFunctionException("Function does not exists in the IR");
		
		List<String> localVariables = new ArrayList<String>();
		for(String variable: variables){
			if(!variable.contains("$-1%"))
				localVariables.add(variable);
		}
		return localVariables;
	}

	public static List<String> getFuncParams(String functionName, SymbolTableManager symbolTableManager){
		if(!functionName.equals("main"))
			functionName =functionName.replaceFirst("FUNC_","");
		FunctionAttribute functionAttribute = symbolTableManager.getFunctionAttribute(functionName);
		List<String> parameters = new ArrayList<String>();
		for(int i = 0;i<functionAttribute.getAcrualtParameters().size();i++){
			parameters.add(i, functionAttribute.getAcrualtParameters().get(i)+"$-1"+functionAttribute.getParameterTypes().get(i).getType().getSuffix() + (functionAttribute.getParameterTypes().get(i).getType() == Type.ARRAY ?  functionAttribute.getParameterTypes().get(i).getTypeOfArray().getNoPercentSuffix() : ""));
		}
		return parameters;
	}
	
	/**
	 * Used temporarily during testing. TODO GET RID OF THIS.
	 * @param funcName
	 * @return
	 */	
	public static RegisterType returnType(String functionName, SymbolTableManager symbolTableManager){
		if(!functionName.equals("main"))
			functionName =functionName.replaceFirst("FUNC_","");
		FunctionAttribute functionAttribute = symbolTableManager.getFunctionAttribute(functionName);
		String returnType = functionAttribute.getReturnTypeName();
		if(returnType.equals("int"))
			return RegisterType.INT;
		else if(returnType.equals("fixedpt"))
			return RegisterType.FLOAT;
		throw new InvalidTypeException("Can only return int or float");
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
			else if(names[1].equals("ai"))
				return RegisterType.INT;
			else if(names[1].equals("af"))
				return RegisterType.INT;
		}
		throw new BadIRInstructionException("Not a valid variable");
	}

	public static boolean isArray(String variable){
		String[] parts = variable.split("\\%");
		return (parts.length==2&&(parts[1].equals("ai")||parts[1].equals("af")));
	}
	
	public static int sizeOfArray(HashMap<String, HashMap<String, Integer>> functionArraySizes, String functionName, String variable){
		if(functionName.equals("main"))
			functionName = "FUNC_"+functionName;
		return functionArraySizes.get(functionName).get(variable);
	}
	
	public static RegisterType getArrayType(String array){
		String[] parts = array.split("\\%");
		if(parts.length==2){
			if(parts[1].equals("ai"))
				return RegisterType.INT;
			else if(parts[1].equals("af"))
				return RegisterType.FLOAT;
		}
		throw new InvalidInvocationException("Can only get the type of valid arrays");
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
