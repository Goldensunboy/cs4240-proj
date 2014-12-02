package code_generation;

import java.util.ArrayList;
import java.util.HashMap;

import code_generation.Register.RegisterType;
import code_generation.StackArgument.Category;

import com.exception.BadDeveloperException;
import com.exception.BadIRInstructionException;
import com.exception.InvalidTypeException;

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
	public static String decodeInstruction(String IRInstruction){
		
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
			instruction.currentFunctionName = instructionParts[0].substring(functionIRNotation.length());
			instruction.currentFunctionName = instruction.currentFunctionName.replace(":", "");
			
			if(!StackFrame.isEmpty()) {
				if(oldFunctionName.equals("main"))
					MIPSInstruction += "\n.end "+oldFunctionName+"\n";
				else
					MIPSInstruction += "\n.end FUNC_"+oldFunctionName+"\n";
				StackFrame.emptyFrame();

			}
			if(instruction.currentFunctionName.equals("main"))
				instructionParts[0] = "main:";
			MIPSInstruction += ".ent "+instruction.currentFunctionName;
			MIPSInstruction += "\n.globl "+instruction.currentFunctionName;
			MIPSInstruction += "\n"+instruction.currentFunctionName +":";			
			MIPSInstruction += "\n"+instruction.enterFunction(instruction.currentFunctionName);
			
			return MIPSInstruction;
		}
		
		if(instructionParts[0].contains(":")){
			return IRInstruction;		/* The line is a label */
		}	
		
		switch(instructionParts[0]){
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
				MIPSInstruction += returnStatement(instructionParts);
				break;
				
			case "call":
				break;
			case "callr":
				break;
				
			case "array_store":
				break;
			case "array_load":
				break;
			case "mtc":
			case "cvt.s.w":
				MIPSInstruction = IRInstruction;
				break;
		}
		return MIPSInstruction;
	}
	
	private static String binaryOperands(String[] instructionParts){
		if(instructionParts.length != 4) {
			String message = instructionParts[0] + " must take in exactly three registers";
			throw new BadIRInstructionException(message);
		}
		String[] registers = {instructionParts[1],instructionParts[2],instructionParts[3]};
		String operation = instructionParts[0];
		InstructionType type = determineInstructionType(registers);
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
			case "brgeq":
				MIPSInstruction += "bge ";
				break;
			case "brleq":
				MIPSInstruction += "ble ";
				break;
			default:
				MIPSInstruction += operation;
			}
			MIPSInstruction += instructionParts[1]+ ", "+ instructionParts[2] + ", "+ instructionParts[3];
			return MIPSInstruction;
		} else if (type == InstructionType.FLOAT){
			switch(operation) {
			case "breq":
				MIPSInstruction += "c.eq.s ";
				break;
			case "brneq":
				MIPSInstruction += "c.ne.s ";
				break;
			case "brlt":
				MIPSInstruction += "c.lt.s ";
				break;
			case "brgt":
				MIPSInstruction += "c.negt.s ";
				break;
			case "brgeq":
				MIPSInstruction += "c.ge.s ";
				break;
			case "brleq":
				MIPSInstruction += "c.le.s ";
				break;
			}
			MIPSInstruction +=  instructionParts[1]+ ", "+ instructionParts[2];
			MIPSInstruction += "\nbc1t "+ instructionParts[3];
			return MIPSInstruction;
		}
		throw new BadDeveloperException("Can only preform operation on all int registers or all float registers");
	}
	
	private static String returnStatement(String[] instructionParts){
		if(instructionParts.length > 2) {
			String message = instructionParts[0] + " cannot take in more than one register";
			throw new BadIRInstructionException(message);
		}
		return instruction.exitFunction(instruction.currentFunctionName);
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
		return instructionParts[0]+" "+instructionParts[1]+", "+instructionParts[2];
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
	private String enterFunction(String functionName){
		String MIPSInstruction = "";
		MIPSInstruction += StackFrame.enterCurrentFrame(functionName);
		
		for(int i = 0; i < 7; i++){
			MIPSInstruction += "\n"+StackFrame.generateStore("$s"+i,"$s"+i, true);
		}
		for(int i = 16; i < 31; i++){
			MIPSInstruction += "\n"+StackFrame.generateStore("$f"+i,"$f"+i, false);
		}
		MIPSInstruction += "\n"+StackFrame.generateStore("$fp","$fp", true);
		MIPSInstruction += "\n"+StackFrame.generateStore("$ra","$ra", true);
		return MIPSInstruction;
	}

	/**
	 * Generates code for exiting a function
	 * @return
	 */
	private String exitFunction(String functionName){
		String MIPSInstruction = "";
		MIPSInstruction += StackFrame.generateLoad("$fp","$fp", true);
		MIPSInstruction += "\n"+StackFrame.generateLoad("$ra","$ra", true);
		
		for(int i = 0; i < 7; i++){
			MIPSInstruction += "\n"+StackFrame.generateLoad("$s"+i,"$s"+i, true);
		}
		for(int i = 16; i < 31; i++){
			MIPSInstruction += "\n"+StackFrame.generateLoad("$f"+i,"$f"+i, false);
		}
		MIPSInstruction += "\n"+StackFrame.exitCurrentFrame();
		MIPSInstruction += "\njr $ra";
		
		return MIPSInstruction;
	}
	
	private String callFunction(String functionName){
		String MIPSInstruction = callFunction(functionName);
		for(int i = 0; i < 9; i++){
			MIPSInstruction += "\n"+StackFrame.generateStore("$t"+i,"$t"+i, true);
		}
		ArrayList<String> localParameters = IRParser.getFuncParams(functionName);
		for(String parameter : localParameters){
			if(IRParser.getVariableType(parameter)==RegisterType.INT){
				if(StackFrame.getVariableType(IRParser.getVariableName(parameter))==RegisterType.INT){
					MIPSInstruction += "\n"+StackFrame.generateLoad(IRParser.getVariableName(parameter),"$a0", true);
					MIPSInstruction += "\n"+StackFrame.generateStore(IRParser.getVariableName(parameter),"$a0", true);
					
				} else{
					throw new InvalidTypeException("Can only pass ints for int parameters");
				}
				//get from stack, check if need to convert
			} else if (IRParser.getVariableType(parameter)==RegisterType.FLOAT){
				
			} else {
				throw new InvalidTypeException("Types can only be int or float. Arrays have not been implmented yet");
			}
			// TODO check types, the load appropriately into correct register, then store
		}
		return MIPSInstruction;
	}
}
