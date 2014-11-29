package code_generation;

import java.util.ArrayList;
import java.util.HashMap;

import code_generation.Register.RegisterType;

import com.exception.BadDeveloperException;
import com.exception.BadIRInstructionException;
import com.exception.InvalidTypeException;

public class Instruction {
	
	public enum InstructionType {UNINITIALIZED, INT, FLOAT, MIXED};
	
	
	/**
	 * Turns an IR instruction into a MIPS instruction(s).
	 * Also modifies the content in the Register File and Stack to ensure that all spill is handled correctly.
	 * @param instruction
	 * @return
	 */
	public String decodeInstruction(String IRInstruction, boolean naive){
		if(IRInstruction == null)
			return "";

		System.out.println("IR Instruction: "+IRInstruction);
		
		String[] instructionParts = IRInstruction.replaceAll("\\s","").split("\\,");
//		for(String s: instructionParts){
//			System.out.println(s);
//		}
		
		// Handle edge cases where the length of the array is zero 
		if(instructionParts.length==0){
			return "";
		}
				
		if(instructionParts[0].contains(":")){
			return IRInstruction;		// The line is a label
		}
		

		InstructionType type;								// Type of instruction, either int or float
		String MIPSInstruction = "";			// Final MIPS instruction to be returned;


		ArrayList<Integer> literals = getLiteralIndexes(instructionParts, 1, 2, 1, -1);
		
		
		switch(instructionParts[0]){
			case "assign":
				/* TODO
				 * panic (handle arrays too)
				 */
				if(instructionParts.length != 3) {
					String message = instructionParts[0] + " must take in exactly two registers";
					throw new BadIRInstructionException(message);
				}
				
				Register registerAssignedTo = new Register(instructionParts[1]);
				
				if(naive) { 	/* If we are doing naive we will need to load/store every time */
					if(literals.isEmpty()){		

						Register registerAssignedFrom = new Register(instructionParts[2]);
						String variableAssignedFromName = Register.getVariableName(instructionParts[2]);	
						String registerAssignedFromName = Register.getVariableName(instructionParts[2]);	
						RegisterType typeAssignedFromName = Register.getRegisterType(instructionParts[2]);
						
						type = validateRegisters(instructionParts, 1, 2, 1, -1);
						
						
						if (type != InstructionType.MIXED) {	 /* if we are assigning one register to another we will need to load and then store. */				
							MIPSInstruction = StackFrame.generateLoad(variableAssignedFromName, registerAssignedFromName, type == InstructionType.INT);
							MIPSInstruction += "\n"+StackFrame.generateStore(registerAssignedTo.getVariableName(), registerAssignedTo.getRegisterName(), type == InstructionType.INT);
							
						} else {	/* mixed assignments require special instructions */
							if(registerAssignedTo.getRegisterType() == RegisterType.INT && typeAssignedFromName == RegisterType.FLOAT) {
								
								MIPSInstruction = StackFrame.generateLoad(variableAssignedFromName, registerAssignedFromName, false);
								MIPSInstruction += "mfcZ "+registerAssignedTo.getRegisterName()+", "+registerAssignedFromName;
								MIPSInstruction += "\n" + StackFrame.generateStore(registerAssignedTo.getVariableName(), registerAssignedTo.getRegisterName(), true);
								
							} else if (registerAssignedTo.getRegisterType() == RegisterType.FLOAT && typeAssignedFromName == RegisterType.INT) {
								MIPSInstruction = StackFrame.generateLoad(variableAssignedFromName, registerAssignedFromName, true);
								MIPSInstruction += "mtcZ "+registerAssignedTo.getRegisterName()+", "+registerAssignedFromName;
								MIPSInstruction += "\n" + StackFrame.generateStore(registerAssignedTo.getVariableName(), registerAssignedTo.getRegisterName(), false);
								
							} else {
								throw new BadDeveloperException("Should be assigning INT to FLOAT or FLOAT to INT");
							}
						}
					} else {

						type = validateRegisters(instructionParts, 1, 2, 1, 2);
						
						MIPSInstruction = "addi "+registerAssignedTo.getRegisterName()+", $0, "+instructionParts[2]; /* Load immediate value into register 
						 																					TODO only allows 16 bit offsets*/
						MIPSInstruction += "\n"+StackFrame.generateStore(registerAssignedTo.getVariableName(), registerAssignedTo.getRegisterName(), type == InstructionType.INT);
					}
				} else {
					//TODO
				}
				break;
			case "add":
			case "sub":
			case "mult":
			case "div":
			case "and":
			case "or":
				if(instructionParts.length != 4) {
					String message = instructionParts[0] + " must take in exactly three registers";
					throw new BadIRInstructionException(message);
				}
				if(naive){
					type = validateRegisters(instructionParts, 1, 3, 3, -1);
					if(type == InstructionType.MIXED)
						throw new BadIRInstructionException("Cannot call "+instructionParts[0]+" with mixed types.");
					else if(type == InstructionType.FLOAT) {
							if(instructionParts[0].equals("and") || instructionParts[0].equals("or"))
								throw new InvalidTypeException("Cannot preform 'and' or 'or' on floats");
							else {
								MIPSInstruction = StackFrame.generateLoad(variableAssignedFromName, registerAssignedFromName, false);
								
								instructionParts[0]=instructionParts[0]+".s";
							}
					} else {
						
					}
					MIPSInstruction += instructionParts[0] + " ";
					MIPSInstruction += instructionParts[1] + ", ";
					MIPSInstruction += instructionParts[2] + ", ";
					MIPSInstruction += instructionParts[3];
				}
				break;
				
			case "goto":
				if(instructionParts.length != 2)
					throw new BadIRInstructionException();
				MIPSInstruction += "j";			// TODO cannot jump the full span of memory.
				MIPSInstruction += instructionParts[1];
				break;
				
			case "breq":
			case "brneq":
			case "brlt":
			case "brgt":
			case "brgeq":
			case "brleq":
				if(instructionParts.length != 4) {
					String message = instructionParts[0] + " must take in exactly two registers and a label";
					throw new BadIRInstructionException(message);
				}
				type = validateRegisters(instructionParts, 1, 2 , -1, -1);
				if(type == InstructionType.MIXED)
					throw new BadIRInstructionException("Cannot call "+instructionParts[0]+" with mixed types.");
				
				if(type == InstructionType.INT){
					switch(instructionParts[0]) {
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
						MIPSInstruction += instructionParts[0];
					}
					MIPSInstruction += instructionParts[1] + ", ";
					MIPSInstruction += instructionParts[2] + ", ";
					MIPSInstruction += instructionParts[3];
				} 
				else {
					switch(instructionParts[0]) {
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
					MIPSInstruction += instructionParts[1] + ", ";
					MIPSInstruction += instructionParts[2] + "\n";
					MIPSInstruction += "bc1t "+ instructionParts[3];
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
		System.out.println("Final MIPS Instruction: " +MIPSInstruction + "\n");
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
	 * @param instructionParts - Should contain only IR register names
	 * @param startIndex
	 * @param endIndex - is inclusive
	 * @param returnValue - does not check this register to see if it has been initialized. It instead adds the variable
	 * 			to the register. If there is no return value, pass in a -1;
	 * @param skipIndex - ignores this index. If no index should be ignored, pass in a -1
	 */
	private InstructionType validateRegisters(String[] instructionParts, int startIndex, int endIndex, int returnValue, int skipIndex){
		if(startIndex > endIndex || instructionParts == null || instructionParts.length <= endIndex){
			throw new BadDeveloperException("Don't call checkForEmptyRegisters with bad parameters");
		}
		ArrayList<Integer> literalList = getLiteralIndexes(instructionParts,startIndex,endIndex,returnValue,skipIndex);
		
		InstructionType type = determineInstructionType(instructionParts, startIndex, endIndex, skipIndex, literalList);
		
		HashMap<String,Register> registerFile;
		if(type == InstructionType.INT)
			registerFile = RegisterFile.getIntRegisters(); 
		else
			registerFile = RegisterFile.getFloatRegisters();
		
		String returnValueVariableName = "";
		String returnValueRegisterName = "";
		
		for(int i = startIndex; i <= endIndex; i++){
			if(i == skipIndex || literalList.contains(i))
				continue;

			String registerName = Register.getRegisterName(instructionParts[i]);
			String variableName = Register.getVariableName(instructionParts[i]);
							
			Register register = registerFile.get(registerName);
			if(register == null) 
				throw new BadIRInstructionException("Register name not found.");
			
			// TODO cannot handle spill
			if(i != returnValue){
				if(!register.containsVariable())
					throw new BadIRInstructionException("Register is empty. Cannot preform instruction on it.");
			} 
			else{
				returnValueVariableName = variableName;
				returnValueRegisterName = registerName;
			}
			
			instructionParts[i] = registerName;
		}
		if(returnValue != -1){
			Register register = registerFile.get(returnValueRegisterName);
			try{
				register.addVariable(returnValueVariableName);
			}
			catch(BadIRInstructionException e){
				String oldVariable = register.getVariableName();
				String newVariable = returnValueVariableName;
				//TODO
			}
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
	private InstructionType determineInstructionType(String[] instructionParts, int startIndex, int endIndex, int skipIndex, ArrayList<Integer> literalList){
		if(startIndex > endIndex || instructionParts == null || instructionParts.length <= endIndex){
			throw new BadDeveloperException("Don't call determineInstructionRegisterType with bad parameters");
		} 

		RegisterType type = RegisterType.UNINITIALIZED;
		for(int i = startIndex; i <= endIndex; i++){
			if(i == skipIndex || literalList.contains(i))
				continue;
			if(type == RegisterType.UNINITIALIZED)
				type = Register.getRegisterType(instructionParts[i]);
			if(Register.getRegisterType(instructionParts[i]) != type)
				return InstructionType.MIXED;
		}
		if(type == RegisterType.INT){
			return InstructionType.INT;
		} else if (type == RegisterType.FLOAT) {
			return InstructionType.FLOAT;
		}
		throw new BadDeveloperException("Only valid types for an instruction are INT, FLOAT and MIXED");
	}
	/**
	 * Returns the indexes of all the float/int literals in the instruction
	 * 
	 * @param instructionParts - Should contain only IR register names
	 * @param startIndex
	 * @param endIndex - is inclusive
	 * @param returnValue - does not check this register to see if it has been initialized. It instead adds the variable
	 * 			to the register. If there is no return value, pass in a -1;
	 * @param skipIndex - ignores this index. If no index should be ignored, pass in a -1
	 */
	private ArrayList<Integer> getLiteralIndexes(String[] instructionParts, int startIndex, int endIndex, int returnValue, int skipIndex){
		ArrayList<Integer> literalList = new ArrayList<Integer>();
		for(int i = startIndex; i <= endIndex; i++){
			if(i == skipIndex)
				continue;
			if(!Register.isValidIRRegister(instructionParts[i])) {
				if(i == returnValue)
					throw new InvalidTypeException("Cannot assign values to a literal");
				if(instructionParts[i].matches("\\d+(\\.\\d+)?")){
					literalList.add(i);		//The value is not a register, it is an immediate offset
				}
				else {
					throw new BadIRInstructionException("Value is neither a literal nor a register name");
				}
			}
		}
		return literalList;
	}
	
}
