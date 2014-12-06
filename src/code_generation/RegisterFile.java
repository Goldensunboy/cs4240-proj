package code_generation;

import java.util.HashMap;

import com.exception.InvalidInvocationException;

import code_generation.Register.Category;


/*
 * This is a singleton design class
 */
public class RegisterFile {
	

	
	public static final String[] AVAILABLE_INT_REGISTERS= {"$t0", "$t1", "$t2", "$t3", "$t4", "$t5",
		"$t6", "$s0", "$s1", "$s2", "$s3", "$s4", "$s5", "$s6", "$s7"};
	public static final String[] AVAILABLE_FLOAT_REGISTERS = {"$f4", "$f5", "$f6", "$f7", "$f8", "$f9", "$f10", 
		"$f11", "$f16", "$f17", "$f18", "$f19", "$f20", "$f21", "$f22", "$f23", "$f24", "$f25", "$f26", "$f27", "$f28"};
	public static final String[] AVAILABLE_TEMPORARY_INT_REGISTERS= {"$t7", "$t8", "$t9"};
	public static final String[] AVAILABLE_TEMPORARY_FLOAT_REGISTERS = {"$f29", "$f30", "$f31"};

	private static RegisterFile registerFile; 	//This is a singleton design class. Yes, register file should be private.

	private HashMap<String,Register> intRegisters = new HashMap<String, Register>();	//The 32 int registers that MIPS contains
	private HashMap<String,Register> floatRegisters = new HashMap<String, Register>(); //The 32 float registers that MIPS contains
	
	private RegisterFile(){
		intRegisters.put("$zero", new Register("$zero", "$0", Register.RegisterType.INT, Category.UNINITIALIZED));	// Contains an fixed value 0.
		
		intRegisters.put("$v0", new Register("$v0", "$2", Register.RegisterType.INT, Category.RETURN_VALUE));		// First INT return value.
		intRegisters.put("$v1", new Register("$v1", "$3", Register.RegisterType.INT, Category.RETURN_VALUE));		// Second INT return value.
		
		intRegisters.put("$a0", new Register("$a0", "$4", Register.RegisterType.INT, Category.PARAMETERS));		// First INT function argument.
		intRegisters.put("$a1", new Register("$a1", "$5", Register.RegisterType.INT, Category.PARAMETERS));		// Second INT function argument.
		intRegisters.put("$a2", new Register("$a2", "$6", Register.RegisterType.INT, Category.PARAMETERS));		// Third INT function argument.
		intRegisters.put("$a3", new Register("$a3", "$7", Register.RegisterType.INT, Category.PARAMETERS));		// Fourth INT function argument.
		
		intRegisters.put("$t0", new Register("$t0", "$8", Register.RegisterType.INT, Category.CALLER_SAVED));		// Temporary value. Caller saved.
		intRegisters.put("$t1", new Register("$t1", "$9", Register.RegisterType.INT, Category.CALLER_SAVED));		// Temporary value. Caller saved.
		intRegisters.put("$t2", new Register("$t2", "$10", Register.RegisterType.INT, Category.CALLER_SAVED));		// Temporary value. Caller saved.
		intRegisters.put("$t3", new Register("$t3", "$11", Register.RegisterType.INT, Category.CALLER_SAVED));		// Temporary value. Caller saved.
		intRegisters.put("$t4", new Register("$t4", "$12", Register.RegisterType.INT, Category.CALLER_SAVED));		// Temporary value. Caller saved.
		intRegisters.put("$t5", new Register("$t5", "$13", Register.RegisterType.INT, Category.CALLER_SAVED));		// Temporary value. Caller saved.
		intRegisters.put("$t6", new Register("$t6", "$14", Register.RegisterType.INT, Category.CALLER_SAVED));		// Temporary value. Caller saved.
		intRegisters.put("$t7", new Register("$t7", "$15", Register.RegisterType.INT, Category.CALLER_SAVED));		// Temporary value. Caller saved.
		intRegisters.put("$t8", new Register("$t8", "$24", Register.RegisterType.INT, Category.CALLER_SAVED));		// Temporary value. Caller saved.
		intRegisters.put("$t9", new Register("$t9", "$25", Register.RegisterType.INT, Category.CALLER_SAVED));		// Temporary value. Caller saved.
		
		intRegisters.put("$s0", new Register("$s0", "$16", Register.RegisterType.INT, Category.CALLEE_SAVED));		// Callee saved.
		intRegisters.put("$s1", new Register("$s1", "$17", Register.RegisterType.INT, Category.CALLEE_SAVED));		// Callee saved.
		intRegisters.put("$s2", new Register("$s2", "$18", Register.RegisterType.INT, Category.CALLEE_SAVED));		// Callee saved.
		intRegisters.put("$s3", new Register("$s3", "$19", Register.RegisterType.INT, Category.CALLEE_SAVED));		// Callee saved.
		intRegisters.put("$s4", new Register("$s4", "$20", Register.RegisterType.INT, Category.CALLEE_SAVED));		// Callee saved.
		intRegisters.put("$s5", new Register("$s5", "$21", Register.RegisterType.INT, Category.CALLEE_SAVED));		// Callee saved.
		intRegisters.put("$s6", new Register("$s6", "$22", Register.RegisterType.INT, Category.CALLEE_SAVED));		// Callee saved.
		intRegisters.put("$s7", new Register("$s7", "$23", Register.RegisterType.INT, Category.CALLEE_SAVED));		// Callee saved.


		intRegisters.put("$sp", new Register("$sp", "$29", Register.RegisterType.INT, Category.STACK_POINTER));		// Stack pointer. Callee saved.
		intRegisters.put("$fp", new Register("$fp", "$30", Register.RegisterType.INT, Category.FRAME_POINTER));		// Frame pointer Calle saved.
		intRegisters.put("$ra", new Register("$ra", "$31", Register.RegisterType.INT, Category.RETURN_ADDRESS));		// Return address. Callee saved.
		
		intRegisters.get("$sp").addVariable("StackPointer");
		/* Puts float register values into the hashmap.
		 * $f0 - $f2 contain the float return values.
		 * $f12 first float argument. $f14 second float argument.
		 * $f4 - $f11 Callee saved
		 * $f16 - $f31 Caller saved
		 */

		floatRegisters.put("$f0", new Register("$f0", "$f0", Register.RegisterType.FLOAT, Category.RETURN_VALUE));
		floatRegisters.put("$f2", new Register("$f2", "$f2", Register.RegisterType.FLOAT, Category.RETURN_VALUE));
		for(int i = 4; i <= 11; i++){
			String registerName = "$f"+i;
			floatRegisters.put(registerName, new Register(registerName, registerName, Register.RegisterType.FLOAT, Category.CALLEE_SAVED));	
		}
		floatRegisters.put("$f12", new Register("$f12", "$f12", Register.RegisterType.FLOAT, Category.PARAMETERS));
		floatRegisters.put("$f14", new Register("$f14", "$f14", Register.RegisterType.FLOAT, Category.PARAMETERS));
		
		for(int i = 16; i <= 31; i++){
			String registerName = "$f"+i;
			floatRegisters.put(registerName, new Register(registerName, registerName, Register.RegisterType.FLOAT, Category.CALLER_SAVED));	
		}
	}
	/**
	 * Clears all registers in both files
	 */
	public static void clearRegisters(){
		for(Register reg : registerFile.intRegisters.values()){
			reg.removeVariable();
		}
		for(Register reg : registerFile.floatRegisters.values()){
			reg.removeVariable();
		}
	}
	
	/**
	 * Call this function to get the one instance if the int registers
	 * @return
	 */
	static public HashMap<String,Register> getIntRegisters(){
		if(registerFile==null){
			registerFile = new RegisterFile();
		}
		return registerFile.intRegisters;
	}
	
	/**
	 * Call this function to check if the inputed register is in the int register file
	 * @return
	 */
	static public boolean isIntRegister(String register){
		if(registerFile==null){
			registerFile = new RegisterFile();
		}
		if(registerFile.intRegisters.containsKey(register))
			return true;
		return false;
	}
	
	/**
	 * Call this function to get the one instance if the float registers
	 * @return
	 */
	static public HashMap<String,Register> getFloatRegisters(){
		if(registerFile==null){
			registerFile = new RegisterFile();
		}
		return registerFile.floatRegisters;
	}
	
	/**
	 * Call this function to check if the inputed register is in the float register file
	 * @return
	 */
	static public boolean isFloatRegister(String register){
		if(registerFile==null){
			registerFile = new RegisterFile();
		}
		if(registerFile.floatRegisters.containsKey(register))
			return true;
		return false;
	}
	
	static public boolean isCalleeRegister(String register){
		if(isIntRegister(register)){
			HashMap<String,Register> intRegisters = getIntRegisters();
			return intRegisters.get(register).getCategory() == Category.CALLEE_SAVED;
		} else if(isFloatRegister(register)){
			HashMap<String,Register> floatRegisters = getFloatRegisters();
			return floatRegisters.get(register).getCategory() == Category.CALLEE_SAVED;
		}
		throw new InvalidInvocationException("Register is not contained in the register file");
	}
	
	static public boolean isCallerRegister(String register){
		if(isIntRegister(register)){
			HashMap<String,Register> intRegisters = getIntRegisters();
			return intRegisters.get(register).getCategory() == Category.CALLER_SAVED;
		} else if(isFloatRegister(register)){
			HashMap<String,Register> floatRegisters = getFloatRegisters();
			return floatRegisters.get(register).getCategory() == Category.CALLER_SAVED;
		}
		throw new InvalidInvocationException("Register is not contained in the register file");
	}
}
