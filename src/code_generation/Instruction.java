package code_generation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import code_generation.Register.RegisterType;

import com.exception.BadDeveloperException;
import com.exception.BadIRInstructionException;
import com.exception.InvalidInvocationException;
import com.exception.InvalidTypeException;
import com.exception.UndeclaredFunctionException;
import com.symbol_table.SymbolTableManager;

public class Instruction {
	
	public enum InstructionType {UNINITIALIZED, INT, FLOAT, MIXED};
	
	private String currentFunctionName;
	
	private static Instruction instruction;
		
	private Instruction(){}
	
	/**
	 * Turns an IR instruction into a MIPS instruction(s).
	 * Also modifies the content in the Register File and Stack to ensure that all spill is handled correctly.
	 * @param instruction
	 * @return
	 */
	public static String decodeInstruction(String IRInstruction, SymbolTableManager symbolTableManager, 
			HashMap<String, List<String>> functionVariables, HashMap<String, List<String>> functionRegisters){
		
		
		if(Instruction.instruction==null){
			instruction = new Instruction();
		}
		
		if(IRInstruction == null)
			return "";
		
		String[] instructionParts = IRInstruction.replaceAll("\\s","").split("\\,");
		
		/* Handle edge cases where the length of the array is zero */
		if(instructionParts.length==0){
			return "";
		}
		

		String MIPSInstruction = "";			/* Final MIPS instruction to be returned; */
		
		
		String functionIRNotation = "FUNC_";
		if(instructionParts[0].contains(functionIRNotation)){ 		/* The line is the beginning of a function */

			String oldFunctionName = instruction.currentFunctionName;
			instruction.currentFunctionName = instructionParts[0];//.substring(functionIRNotation.length());
			instruction.currentFunctionName = instruction.currentFunctionName.replace(":", "");
			
			if(!StackFrame.isEmpty()) {
//				if(oldFunctionName.equals("main"))
//					MIPSInstruction += "\n.end "+oldFunctionName+"\n";
//				else
				MIPSInstruction += "\n.end "+oldFunctionName+"\n";
				StackFrame.emptyFrame();

			}
			if(instruction.currentFunctionName.equals("FUNC_main")){
				instruction.currentFunctionName = "main";
			}
			MIPSInstruction += ".ent "+instruction.currentFunctionName;
			MIPSInstruction += "\n.globl "+instruction.currentFunctionName;
			MIPSInstruction += "\n"+instruction.currentFunctionName +":";			
			MIPSInstruction += "\n"+instruction.enterFunction(instruction.currentFunctionName, symbolTableManager,functionVariables,functionRegisters);
			return MIPSInstruction;
		}
		
		if(instructionParts[0].contains(":")){
			return IRInstruction;		/* The line is a label */
		}	
		ArrayList<String> parameters;
		switch(instructionParts[0]){
			case "assign":
				MIPSInstruction += assign(instructionParts);
				break;
			case "add":
			case "sub":
			case "mult":
			case "div":
			case "and":
			case "or":
				MIPSInstruction += binaryOperands(instructionParts);
				break;
			case "goto":
				MIPSInstruction += goTo(instructionParts);
				break;
			case "load":
				MIPSInstruction += load(instructionParts);
				break;
			case "store": 
				MIPSInstruction += store(instructionParts);
				break;
			case "li":
			case "li.s":
				MIPSInstruction += loadImmediate(instructionParts);
				break;
			case "breq":
			case "brneq":
			case "brlt":
			case "brgt":
			case "brgeq":
			case "brleq":
				MIPSInstruction += branch(instructionParts);
				break;
				
			case "return":
				MIPSInstruction += returnStatement(instructionParts,functionRegisters);
				break;
				
			case "call":
				if(instructionParts.length < 2)
					throw new BadDeveloperException("Call needs at least a function name");
				parameters = new ArrayList<String>();
				for(int i = 2; i < instructionParts.length; i++){
					parameters.add(instructionParts[i]);
				}
				if(instruction.isLibraryCall(instructionParts))
					MIPSInstruction += instruction.callLibraryFunction(instructionParts);
				else
					MIPSInstruction += instruction.callFunction(instructionParts[1],parameters, symbolTableManager,functionVariables,functionRegisters);
				break;
			case "callr":
				if(instructionParts.length < 3)
					throw new BadDeveloperException("Callr needs at least a function name and a return type");
				parameters = new ArrayList<String>();
				for(int i = 3; i < instructionParts.length; i++){
					parameters.add(instructionParts[i]);
				}
				if(instruction.isLibraryCall(instructionParts))
					MIPSInstruction += instruction.callLibraryFunction(instructionParts);
				else {
					MIPSInstruction += instruction.callFunction(instructionParts[2],parameters, symbolTableManager,functionVariables,functionRegisters);
					MIPSInstruction += "\n"+instruction.getReturnValue(instructionParts[2],instructionParts[1], symbolTableManager);
				}
				break;
				
			case "array_store":
				break;
			case "array_load":
				break;
			case "mtc1":
			case "cvt.s.w":
				MIPSInstruction += IRInstruction.replaceFirst(",","");
				break;
			default:
				MIPSInstruction += MIPSInstruction;
		}
		return MIPSInstruction;
	}
	
	private static String assign(String[] instructionParts){
		if(instructionParts.length != 3){
			throw new BadIRInstructionException("assign takes in two registers");
		} 
		String MIPSInstruction = "";
		/* a :=b */
		if(RegisterFile.isIntRegister(instructionParts[1])){
			if(RegisterFile.isIntRegister(instructionParts[2])){
				MIPSInstruction += "move "+instructionParts[1]+", "+instructionParts[2];
			} else 
				throw new InvalidTypeException("Can only assign ints to ints");
				
		} else if (RegisterFile.isFloatRegister(instructionParts[1])){
			if(RegisterFile.isIntRegister(instructionParts[2])){
				MIPSInstruction += "mtc1 "+instructionParts[2]+", "+instructionParts[1]+"\ncvt.s.w "+instructionParts[1]+", "+instructionParts[1];
			} else if (RegisterFile.isFloatRegister(instructionParts[2])){
				MIPSInstruction += "mov.s "+instructionParts[1]+", "+instructionParts[2];
			} else
				throw new InvalidTypeException("Can only assign ints and floats to floats");
			
		} else 
			throw new InvalidTypeException("Types can only be int or float. Arrays have not been implmented yet");
			
		return MIPSInstruction;
	}
	
	private static String binaryOperands(String[] instructionParts){
		if(instructionParts.length != 4) {
			String message = instructionParts[0] + " must take in exactly three registers";
			throw new BadIRInstructionException(message);
		}
		String[] registers = {instructionParts[1],instructionParts[2],instructionParts[3]};
		String operation = instructionParts[0];
		if(operation.equals("mult"))
			operation = "mul";
		InstructionType type = determineInstructionType(registers);
		if("mult".equals(operation)) {
			operation = "mul";
		}
		if(type == InstructionType.FLOAT)
			operation +=".s";
		return operation+" "+instructionParts[3]+", "+instructionParts[1]+", "+instructionParts[2];
	}
	
	private static String goTo(String[] instructionParts){
		if(instructionParts.length != 2)
			throw new BadIRInstructionException();
		return "j "+instructionParts[1];			// TODO cannot jump the full span of memory.
	}
	
	private static String branch(String[] instructionParts){
		if(instructionParts.length != 4) {
			String message = instructionParts[0] + " must take in exactly two registers and a label";
			throw new BadIRInstructionException(message);
		}
		String[] registers = {instructionParts[1],instructionParts[2]};
		String operation = instructionParts[0];
		String MIPSInstruction ="";
		InstructionType type = determineInstructionType(registers);
		if(type == InstructionType.INT){
			switch(operation) {
			case "breq":
				MIPSInstruction += "beq ";
				break;
			case "brneq":
				MIPSInstruction += "bne ";						
				break;
			case "brgt":
				MIPSInstruction += "bgt ";
				break;
			case "brgeq":
				MIPSInstruction += "bge ";
				break;
			case "brlt":
				MIPSInstruction += "blt ";
				break;
			case "brleq":
				MIPSInstruction += "ble ";
				break;
			default:
				MIPSInstruction += operation;
			}
			MIPSInstruction += " "+instructionParts[1]+ ", "+ instructionParts[2] + ", "+ instructionParts[3];
			return MIPSInstruction;
		} else if (type == InstructionType.FLOAT){
			switch(operation) {
			case "breq":
				MIPSInstruction += "c.eq.s "+instructionParts[1]+ ", "+ instructionParts[2];
				MIPSInstruction += "\nbc1t "+ instructionParts[3];
				break;
			case "brleq":
				MIPSInstruction += "c.le.s "+instructionParts[1]+ ", "+ instructionParts[2];
				MIPSInstruction += "\nbc1t "+ instructionParts[3];
				break;
			case "brlt":
				MIPSInstruction += "c.lt.s "+instructionParts[1]+ ", "+ instructionParts[2];
				MIPSInstruction += "\nbc1t "+ instructionParts[3];
				break;
			case "brgt":
				MIPSInstruction += "c.le.s "+instructionParts[1]+ ", "+ instructionParts[2];
				MIPSInstruction += "\nbc1f "+ instructionParts[3];
				break;
			case "brgeq":
				MIPSInstruction += "c.lt.s "+instructionParts[1]+ ", "+ instructionParts[2];
				MIPSInstruction += "\nbc1f "+ instructionParts[3];
				break;
			case "brneq":
				MIPSInstruction += "c.eq.s "+instructionParts[1]+ ", "+ instructionParts[2];
				MIPSInstruction += "\nbc1f "+ instructionParts[3];
				break;
			}
			return MIPSInstruction;
		}
		throw new BadDeveloperException("Can only preform operation on all int registers or all float registers");
	}
	
	private static String returnStatement(String[] instructionParts,HashMap<String, List<String>> functionRegisters){
		if(instructionParts.length > 2) {
			String message = instructionParts[0] + " cannot take in more than one register";
			throw new BadIRInstructionException(message);
		}
		String MIPSInstruction = "";
		if(instructionParts.length == 2){
			if(instructionParts[1].matches("[0-9]+")){
				MIPSInstruction += "li $v0, "+instructionParts[1]+"\n";
			} else if (instructionParts[1].matches("[0-9]+\\.[0-9]+")){
				MIPSInstruction += "li.s $f0, "+instructionParts[1]+"\n";
			} else {
				boolean isInt =  IRParser.getVariableType(instructionParts[1]) == RegisterType.INT;
				MIPSInstruction += StackFrame.generateLoad(IRParser.getVariableName(instructionParts[1]), (isInt)?"$v0":"$f0", isInt)+"\n";
			}
		}
		MIPSInstruction += instruction.exitFunction(instruction.currentFunctionName,functionRegisters);
		return MIPSInstruction;
	}
	
	private static String load(String[] instructionParts){
		if(instructionParts.length != 3)
			throw new BadIRInstructionException("load must take in one variable and one register");
		return StackFrame.generateLoad(IRParser.getVariableName(instructionParts[1]), instructionParts[2], RegisterType.INT == Register.getRegisterType(instructionParts[2]));
	}
	
	private static String store(String[] instructionParts){
		if(instructionParts.length != 3)
			throw new BadIRInstructionException("store must take in one variable and one register");
		
		return StackFrame.generateStore(IRParser.getVariableName(instructionParts[1]), instructionParts[2], RegisterType.INT == Register.getRegisterType(instructionParts[2]));
	}
	
	private static String loadImmediate(String[] instructionParts){
		if(instructionParts.length != 3)
			throw new BadIRInstructionException(instructionParts[0]+" must take in one register and one literal");
		return instructionParts[0]+" "+instructionParts[2]+", "+instructionParts[1];
	}
	
	private static InstructionType determineInstructionType(String[] registers){
		RegisterType registerType = Register.getRegisterType(registers[0]);
		for(String register: registers){
			if(registerType != Register.getRegisterType(register))
				return InstructionType.MIXED;
		}
		if(registerType == RegisterType.INT)
			return InstructionType.INT;
		else if(registerType == RegisterType.FLOAT)
			return InstructionType.FLOAT;
		throw new BadDeveloperException("Type should be mixed, int or float");
	}
	
	/**
	 * Generates code for entering a function
	 * @param functionName
	 * @return
	 */
	private String enterFunction(String functionName, SymbolTableManager symbolTableManager,HashMap<String, 
			List<String>> functionVariables, HashMap<String, List<String>> functionRegisters){
		String MIPSInstruction = "";
		MIPSInstruction += StackFrame.enterCurrentFrame(functionName, symbolTableManager,functionVariables,functionRegisters);
		
		List<String> usedRegisters = IRParser.getFuncCalleeRegisters(functionName,functionRegisters);
		for(String register: usedRegisters){
			MIPSInstruction += "\n"+StackFrame.generateStore(register,register, Register.getRegisterType(register)==RegisterType.INT);
		}
		MIPSInstruction += "\n"+StackFrame.generateStore("$fp","$fp", true);
		MIPSInstruction += "\n"+StackFrame.generateStore("$ra","$ra", true);
		return MIPSInstruction;
	}

	/**
	 * Generates code for exiting a function
	 * @return
	 */
	private String exitFunction(String functionName,HashMap<String, List<String>> functionRegisters){
		String MIPSInstruction = "";
		MIPSInstruction += StackFrame.generateLoad("$fp","$fp", true);
		MIPSInstruction += "\n"+StackFrame.generateLoad("$ra","$ra", true);
		
		List<String> usedRegisters = IRParser.getFuncCalleeRegisters(functionName,functionRegisters);
		for(String register: usedRegisters){
			MIPSInstruction += "\n"+StackFrame.generateLoad(register,register, Register.getRegisterType(register)==RegisterType.INT);
		}
		MIPSInstruction += "\n"+StackFrame.exitCurrentFrame();
		MIPSInstruction += "\njr $ra";
		
		return MIPSInstruction;
	}
	
	private String callFunction(String functionName, ArrayList<String> localParameters, SymbolTableManager symbolTableManager,
			HashMap<String, List<String>> functionVariables, HashMap<String, List<String>> functionRegisters){
		
		String MIPSInstruction = StackFrame.callingFunctionBegin(functionName,symbolTableManager,functionVariables,functionRegisters);
		
		List<String> usedRegisters = IRParser.getFuncCallerRegisters(functionName,functionRegisters);
		for(String register: usedRegisters){
			MIPSInstruction += "\n"+StackFrame.generateStore(register,register, Register.getRegisterType(register)==RegisterType.INT);
		}
		List<String> actualParameters = IRParser.getFuncParams(functionName,symbolTableManager);
		if(actualParameters.size()!=localParameters.size())
			throw new BadDeveloperException("Calling function incorrectly");
		for(int i = 0; i<actualParameters.size(); i++){
			if(IRParser.getVariableType(actualParameters.get(i))==RegisterType.INT){
				MIPSInstruction += "\n"+putIntoRegister(localParameters.get(i),"$a0",null);
				MIPSInstruction += "\n"+StackFrame.generateStore(IRParser.getVariableName(actualParameters.get(i))+"_param","$a0", true);

			} else if (IRParser.getVariableType(actualParameters.get(i))==RegisterType.FLOAT){
				MIPSInstruction += "\n"+putIntoRegister(localParameters.get(i),"$f12","$a0");
				MIPSInstruction += "\n"+StackFrame.generateStore(IRParser.getVariableName(actualParameters.get(i))+"_param","$f12", false);
				
			} else {
				throw new InvalidTypeException("Types can only be int or float. Arrays have not been implmented yet");
			}
		}
		MIPSInstruction += "\njal "+functionName;
		for(String register: usedRegisters){
			MIPSInstruction += "\n"+StackFrame.generateLoad(register,register, Register.getRegisterType(register)==RegisterType.INT);
		}
		MIPSInstruction += "\n"+StackFrame.callingFunctionEnd(functionName);
		return MIPSInstruction;
	}
	
	private String getReturnValue(String functionName, String returnVariable, SymbolTableManager symbolTableManager){
		String MIPSInstruction = "";
		RegisterType type = IRParser.returnType(functionName, symbolTableManager);
		if(type == RegisterType.INT){
			
			if(IRParser.getVariableType(returnVariable) == RegisterType.INT){
				MIPSInstruction += StackFrame.generateStore(IRParser.getVariableName(returnVariable),"$v0", true);
				
			} else if (IRParser.getVariableType(returnVariable) == RegisterType.FLOAT){
				MIPSInstruction += "\n"+putIntoRegister("$v0","$f0",null);
				MIPSInstruction += "\n"+StackFrame.generateStore(IRParser.getVariableName(returnVariable),"$f0", false);
				
			} else
				throw new InvalidTypeException("Int return types can only be stored to ints and floats");
			
		} else if (type == RegisterType.FLOAT){
			if (IRParser.getVariableType(returnVariable) == RegisterType.FLOAT){
				MIPSInstruction += StackFrame.generateStore(IRParser.getVariableName(returnVariable),"$f0", false);
				
			} else
				throw new InvalidTypeException("Float return types can only be stored to and floats");
			
		} else 
			throw new InvalidTypeException("Retrieving return value can only be done for ints and floats");
		return MIPSInstruction;
	}
	
	boolean isLibraryCall(String[] instructionParts){
		try {
			callLibraryFunction(instructionParts);
			return true;
		} catch (UndeclaredFunctionException |InvalidInvocationException e){
			return false;
		}
	}
	
	String callLibraryFunction(String[] instructionParts){
		String MIPSInstruction = "";
		if(instructionParts.length!=3)
			throw new InvalidInvocationException();
		
		
		if(instructionParts[0].equals("call")){
			switch(instructionParts[1]){
			case "FUNC_Print_int":
				if(IRParser.getVariableType(instructionParts[2])==RegisterType.INT){
					MIPSInstruction += "li $v0, 1";
					MIPSInstruction += putIntoRegister(instructionParts[2],"$a0",null);
					MIPSInstruction += "\nsyscall";
					
				} else
					throw new InvalidInvocationException();				
				break;
			case "FUNC_Print_float":
					MIPSInstruction += "li $v0, 2\n";
					MIPSInstruction += putIntoRegister(instructionParts[2],"$f12","$a0");			
					MIPSInstruction += "syscall\n";
				break;
			default:
				throw new UndeclaredFunctionException("Not a library function");
			}
			
		} else if (instructionParts[0].equals("callr")){
			
			switch(instructionParts[2]){
			case "FUNC_Read_int":
				if(IRParser.getVariableType(instructionParts[1])==RegisterType.INT){
					MIPSInstruction += "li $v0, 5";
					MIPSInstruction += "\nsyscall";
					MIPSInstruction += "\n"+StackFrame.generateStore(IRParser.getVariableName(instructionParts[1]),"$v0", true);
					
				} else if (IRParser.getVariableType(instructionParts[1])==RegisterType.FLOAT){
					MIPSInstruction += "li $v0, 5";
					MIPSInstruction += "\nsyscall";
					MIPSInstruction += putIntoRegister("$f0","$v0",null);			
					MIPSInstruction += "\n"+StackFrame.generateStore(IRParser.getVariableName(instructionParts[1]),"$f0", false);
				} else
					throw new InvalidInvocationException();
				break;
			case "FUNC_Read_float":
				if(IRParser.getVariableType(instructionParts[1])==RegisterType.FLOAT){
					MIPSInstruction += "li $v0, 6";
					MIPSInstruction += "\nsyscall";
					MIPSInstruction += "\n"+StackFrame.generateStore(IRParser.getVariableName(instructionParts[1]),"$f0", false);
					
				} else
					throw new InvalidInvocationException();
				break;
			default:
				throw new UndeclaredFunctionException("Not a library function");
			}	
		}
		if(MIPSInstruction.equals(""))
			throw new UndeclaredFunctionException("Not a library function");
			
		return MIPSInstruction;
	}
	
	/**
	 * Takes in a literal, register, local variable or parameter and loads it into the requested register (registerFinal). 
	 * If the operation requires an intermediate reigster, the register of the appropriate type should be provided in the 
	 * reigsterIntermediate parameter.
	 * @param argument
	 * @param registerFinal
	 * @param registerIntermediate
	 * @return
	 */
	private static String putIntoRegister(String argument, String registerFinal, String registerIntermediate){
		if(argument==null||registerFinal==null)
			throw new InvalidInvocationException("Cannot call put into reigster with either a null argument or a null final register");
		if(!(RegisterFile.isIntRegister(registerFinal)||RegisterFile.isFloatRegister(registerFinal)))
			throw new InvalidInvocationException("The requested final register is not a register in either register file");
		
		String MIPSInstruction = "\n";
		/* Argument is a literal */
		if(argument.matches("[0-9]+")){
			if(RegisterFile.isIntRegister(registerFinal)){							/* Int literal into int register */
				MIPSInstruction += "li "+registerFinal+", "+argument;
			}
			else if(RegisterFile.isFloatRegister(registerFinal)&&registerIntermediate!=null&&RegisterFile.isIntRegister(registerIntermediate)){
																					/* Int literal into float register */
				MIPSInstruction += "li "+registerIntermediate+", "+argument+"\n";
				MIPSInstruction += "mtc1 "+registerIntermediate+", "+registerFinal+"\n";
				MIPSInstruction += "cvt.s.w "+registerFinal+", "+registerFinal+"\n";				
			} else 
				throw new InvalidTypeException("Cannot transfer the int literal the requested register");
			
		} else if(argument.matches("[0-9]+\\.[0-9]+")){
			if(RegisterFile.isFloatRegister(registerFinal))							/* Float literal into float register */
				MIPSInstruction += "li.s "+registerFinal+", "+argument+"\n";
			else
				throw new InvalidTypeException("Cannot transfer the float literal into the requested register");
		}
		/* Argument is a register */
		else if(RegisterFile.isIntRegister(argument)){
			if(RegisterFile.isIntRegister(registerFinal)){							/* Int register into int register */
				MIPSInstruction += "move "+registerFinal+", "+argument+"\n";
				
			} else if(RegisterFile.isFloatRegister(registerFinal)){					/* Int register into Float register */
				MIPSInstruction += "mtc1 "+argument+", "+registerFinal+"\n";
				MIPSInstruction += "cvt.s.w "+registerFinal+", "+registerFinal+"\n";	
				
			} else
				throw new InvalidTypeException("Cannot transfer the int register into the requested register");
			
		} else if(RegisterFile.isFloatRegister(argument)){
			if(RegisterFile.isFloatRegister(registerFinal)){						/* Float register into Float register */
				MIPSInstruction += "mov.s "+registerFinal+", "+argument+"\n";
				
			} else 
				throw new InvalidTypeException("Cannot transfer the int register into the requested register");
		}
		/* Argument is a local variable or parameter */
		else if(StackFrame.isOnStack(IRParser.getVariableName(argument)) && StackFrame.getVariableType(IRParser.getVariableName(argument))==RegisterType.INT
				&& IRParser.getVariableType(argument)==RegisterType.INT){
			if(RegisterFile.isIntRegister(registerFinal)){							/* Int variable/parameter into int register */
				MIPSInstruction += StackFrame.generateLoad(IRParser.getVariableName(argument),registerFinal, true)+"\n";
				
			} else if (RegisterFile.isFloatRegister(registerFinal)&&registerIntermediate!=null&&RegisterFile.isIntRegister(registerIntermediate)){
																					/* int variable/parameter into float register */
				MIPSInstruction += StackFrame.generateLoad(IRParser.getVariableName(argument),registerIntermediate, true)+"\n";
				MIPSInstruction += "mtc1 "+registerIntermediate+", "+registerFinal+"\n";
				MIPSInstruction += "cvt.s.w "+registerFinal+", "+registerFinal+"\n";	
				
			} else
				throw new InvalidTypeException("Cannot transfer the int variable into the requested register");
				
		} else if(StackFrame.isOnStack(IRParser.getVariableName(argument)) && StackFrame.getVariableType(IRParser.getVariableName(argument))==RegisterType.FLOAT
				&& IRParser.getVariableType(argument)==RegisterType.FLOAT) { 
			if(RegisterFile.isFloatRegister(registerFinal)){						/* float variable/parameter into float register */
				MIPSInstruction += StackFrame.generateLoad(IRParser.getVariableName(argument),registerFinal, false)+"\n";
					
			} else
				throw new InvalidTypeException("Cannot transfer the float variable into the requested register");
			
		} else 
			throw new InvalidTypeException("The argument is neither a literal, register or a variable/parameter currently on the stack");
		
		return MIPSInstruction;
	}
	
}
