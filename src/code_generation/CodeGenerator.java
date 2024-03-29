package code_generation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;

import com.antlr.generated.TigerParser;
import com.attribute.FunctionAttribute;
import com.symbol_table.SymbolTableManager;

/**
 * TODO Change to be functional. Remove all static.
 * @author Risa
 *
 */
public class CodeGenerator {
	
	private String fileOutputed = "output\\MIPS.s";
	private TigerParser parser;
	private List<String> IRIR;
	private SymbolTableManager symbolTableManager;
	private HashMap<String, List<String>> functionVariables;
	private HashMap<String, List<String>> functionRegisters;
	private HashMap<String, HashMap<String, Integer>> functionArraySizes;
	
	public CodeGenerator(TigerParser parser,List<String> IRIR, HashMap<String, List<String>> functionVariables, HashMap<String, List<String>> functionRegisters,
			HashMap<String, HashMap<String, Integer>> functionArraySize, String fileOutputed){
		this(parser, IRIR, functionVariables, functionRegisters, functionArraySize);
		this.fileOutputed = fileOutputed;
	}
	
	public CodeGenerator(TigerParser parser,List<String> IRIR, HashMap<String, List<String>> functionVariables, HashMap<String, List<String>> functionRegisters, 
			HashMap<String, HashMap<String, Integer>> functionArraySizes){
		
		this.parser = parser;
		this.IRIR = IRIR;
		this.functionVariables = functionVariables;
		this.functionRegisters = functionRegisters;
		this.symbolTableManager = parser.getSymbolTableManager();
		this.functionArraySizes = functionArraySizes;
		System.out.println(functionVariables);
	}
	
	
	public void generateCode(){
		setUpFile();
		translateIRtoMIPS();		
	}

	
	/**
	 * Set up file for mips before adding instructions
	 */
	public void setUpFile(){
		try {
			PrintWriter writer = new PrintWriter(fileOutputed);
			String header = "";
			header +=generateData();
			header +=generateText();
			writer.print(header);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Creates the data section for mips
	 * @return
	 */
	private String generateData(){
		return ".data\n";
	}
	
	/**
	 * Creates the text section for mips include the .global and .ent
	 * @return
	 */
	private String generateText(){
		//TODO actually implement this
		return ".text\n";
		
	}
	
	
	
	public void translateIRtoMIPS(){
		String MIPSLine;
		
		for(String line: IRIR) {
			MIPSLine = translateInstruction(line,symbolTableManager)+"\n";
			System.out.print(MIPSLine);
			writeMIPSToFile(MIPSLine);
		}
	}
	
	private String translateInstruction(String instruction, SymbolTableManager symbolTableManager){
		return Instruction.decodeInstruction(instruction, symbolTableManager, functionVariables, functionRegisters, functionArraySizes);
	}
	
	/**
	 * Write the MIPS
	 * @param instruction
	 */
	private void writeMIPSToFile(String instruction){
		try {
			Writer output;
			output = new BufferedWriter(new FileWriter(fileOutputed,true));
			output.append(instruction);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	
//	public static void main(String[] args){
//		boolean naive = true;
//		CodeGenerator cg = new CodeGenerator(naive);
//		cg.setUpFile();
//		cg.translateIRtoMIPS();
//		
////		String temp =Instruction.decodeInstruction("FUNC_main:", naive);
////		cg.writeMIPSToFile(temp);
////		Instruction.decodeInstruction("assign, x$3%i#$t0, 5", naive);
////		Instruction.decodeInstruction("assign, z$3%f#$f0, x$3%i#$t2", naive);
////		Instruction.decodeInstruction("assign, y$3%i#$t2, x$3%i#$t0", naive);
////		Instruction.decodeInstruction("label:", naive);
////		Instruction.decodeInstruction("add, x$3%i#$t0, y$3%i#$t2, b$3%i#$t1", naive);
////		Instruction.decodeInstruction("breq, x$3%i#$t0, y$3%i#$t2, hello", naive);
////		Instruction.decodeInstruction("return, 5", naive);
//////		StackFrame.printStackFrame();
////		Instruction.decodeInstruction("FUNC_test:", naive);
////		Instruction.decodeInstruction("assign, x$3%f#$f16, 5.0", naive);
////		Instruction.decodeInstruction("return, x$3%f#$f16", naive);
//
//	}
}
