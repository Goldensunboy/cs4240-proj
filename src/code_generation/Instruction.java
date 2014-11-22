package code_generation;

import java.util.HashMap;

import code_generation.Register.Type;

import com.exception.BadDeveloperException;
import com.exception.BadIRInstructionException;
import com.exception.InvalidTypeException;

public class Instruction {
	
	/**
	 * Turns an IR instruction into a MIPS instruction(s).
	 * Also modifies the content in the Register File and Stack to ensure that all spill is handled correctly.
	 * @param instruction
	 * @return
	 */
	private String decodeInstruction(String IRinstruction){
		if(IRinstruction == null)
			return "";
		String[] instructionParts = IRinstruction.replaceAll("\\s","").split(",");
		
		for(String part:instructionParts){
			System.out.println(part);
		}
		
		// Handle edge cases where the length of the array is zero 
		if(instructionParts.length==0){
			return "";
		}
		
		int startIndex = 0; 		// The beginning index of the instruction without any labels
		while(startIndex<instructionParts.length && instructionParts[startIndex].contains(":")){
			startIndex++;
		}
		
		if(startIndex == instructionParts.length){
			return IRinstruction;		// The line is only labels
		}
		
		int instructionLength = instructionParts.length - startIndex;		// Use this to determine the length of the array minus any beginning labels
		
		
		Type type;								// Type of instruction, either int or float
		String MIPSInstruction = "";			// Final MIPS instruction to be returned;
		for(int i = 0; i < startIndex; i++){			// Add labels to MIPS instruction
			MIPSInstruction += instructionParts[i] + " ";
		}
		
		switch(instructionParts[startIndex]){
			case "assign":
				/*
				 * panic (handle arrays too)
				 * move between int and float
				 */
				break;
				
			case "add":
			case "sub":
			case "mult":
			case "div":
			case "and":
			case "or":
				if(instructionLength != 4) {
					String message = instructionParts[startIndex] + " must take in exactly three registers";
					throw new BadIRInstructionException(message);
				}
				type = validateRegisters(instructionParts, startIndex+1, startIndex+3, startIndex+1, -1);
				if(type == Type.FLOAT)
						if(instructionParts[startIndex].equals("and") || instructionParts[startIndex].equals("or"))
							throw new InvalidTypeException("Cannot preform 'and' or 'or' on floats");
						else
							instructionParts[startIndex]=instructionParts[startIndex]+".s";

				MIPSInstruction += instructionParts[startIndex] + " ";
				MIPSInstruction += instructionParts[startIndex+1] + ", ";
				MIPSInstruction += instructionParts[startIndex+2] + ", ";
				MIPSInstruction += instructionParts[startIndex+3];
				
				break;
				
			case "goto":
				if(instructionLength != 2)
					throw new BadIRInstructionException();
				MIPSInstruction += "j";			// TODO cannot jump the full span of memory.
				MIPSInstruction += instructionParts[startIndex+1];
				break;
				
			case "breq":
			case "brneq":
			case "brlt":
			case "brgt":
			case "brgeq":
			case "brleq":
				if(instructionLength != 3) {
					String message = instructionParts[startIndex] + " must take in exactly two registers and a label";
					throw new BadIRInstructionException(message);
				}
				type = validateRegisters(instructionParts, startIndex+1, startIndex+2 ,startIndex+1, -1);
				if(type == Type.INT){
					switch(instructionParts[startIndex]) {
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
						MIPSInstruction += instructionParts[startIndex];
					}
					MIPSInstruction += instructionParts[startIndex+1] + ", ";
					MIPSInstruction += instructionParts[startIndex+2] + ", ";
					MIPSInstruction += instructionParts[startIndex+3];
				} 
				else {
					switch(instructionParts[startIndex]) {
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
					MIPSInstruction += instructionParts[startIndex+1] + ", ";
					MIPSInstruction += instructionParts[startIndex+2] + "\n";
					MIPSInstruction += "bc1t "+ instructionParts[startIndex+3];
				}
				break;
				
			case "return":
				break;
				
			case "call":
				break;
			case "callr":
				break;
				
			case "array_store":
				break;
			case "array_load":
				break;


		}
		
		/*
		 * labels
		 * register file
		 * int vs float vs arrayint vs arrayfloat
		 * 
		 */
		return MIPSInstruction;
	}
	

	

	/**
	 * Figures out the register Types (int or float)
	 * 
	 * Replaces IR register names with MIPS register names
	 * 
	 * Checks if any of the registers are 'empty' and therefore should not be used in an instruction. Does not check the 
	 * returnValue or skipIndex to see if they have been initialized
	 * 
	 * Adds the returnValue variable to the appropriate register.
	 * 
	 * Does not check the skipIndex register.
	 * 
	 * @param instructionParts - Should not contain any IR register names, only MIPS register names
	 * @param startIndex
	 * @param endIndex - is inclusive
	 * @param returnValue - does not check this register to see if it has been initialized. It instead adds the variable
	 * 			to the register. If there is no return value, pass in a -1;
	 * @param skipIndex - ignores this index. If no index should be ignored, pass in a -1
	 */
	private Type validateRegisters(String[] instructionParts, int startIndex, int endIndex, int returnValue, int skipIndex){
		if(startIndex > endIndex || instructionParts == null || instructionParts.length >= endIndex){
			throw new BadDeveloperException("Don't call checkForEmptyRegisters with bad parameters");
		}
		
		Type type = determineInstructionRegisterType(instructionParts, startIndex, endIndex, skipIndex);
		
		HashMap<String,Register> registerFile;
		if(type == Type.INT)
			registerFile = RegisterFile.getIntRegisters(); 
		else
			registerFile = RegisterFile.getFloatRegisters();
		
		for(int i = startIndex; i <= endIndex; i++){
			if(i == skipIndex)
				continue;

			String registerName = Register.getRegisterName(instructionParts[i]);
			String variableName = Register.getRegisterVariableName(registerName);
							
			Register register = registerFile.get(registerName);
			if(register == null) 
				throw new BadDeveloperException("Register name not found.");
			
			// TODO cannot handle spill
			
			if(i == returnValue)
				register.addVariable(variableName);
			else
				if(!register.containsVariable()) 
					throw new BadDeveloperException("Register is empty. Cannot preform instruction on it");
			
			instructionParts[i] = registerName;
		}
		
		return type;
	}
	
	/**
	 * Determines the type of registers in an instruction. If all int or all float, it returns appropriately, otherwise it throws an exception.
	 * @param instructionParts
	 * @param startIndex
	 * @param endIndex - is inclusive
	 * @param skipIndex - ignores this index. If no index should be ignored, pass in a -1
	 * @return
	 */
	private Type determineInstructionRegisterType(String[] instructionParts, int startIndex, int endIndex, int skipIndex){
		if(startIndex > endIndex || instructionParts == null || instructionParts.length >= endIndex){
			throw new BadDeveloperException("Don't call determineInstructionRegisterType with bad parameters");
		}
		Type type = Register.getRegisterType(instructionParts[startIndex]);
		for(int i = startIndex+1; i <= endIndex; i++){
			if(i == skipIndex)
				continue;
			if(Register.getRegisterType(instructionParts[i]) != type)
				throw new BadIRInstructionException("IR instruction contains registers with mixed int and float types.");
		}
		return type;
	}
}
