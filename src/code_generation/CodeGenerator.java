package code_generation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;

import code_generation.Register.RegisterType;
import code_generation.StackArgument.Category;

/**
 * TODO Change to be functional. Remove all static.
 * @author Risa
 *
 */
public class CodeGenerator {
	
	private String fileOutputed = "output\\MIPS.s";
	private String fileToTranslate = "C:\\Users\\Risa\\Desktop\\IR\\IR_Test_Code.txt";
	private boolean naive;
	
	public CodeGenerator(String fileOutputed, String fileToTranslate, boolean naive){
		this.fileOutputed = fileOutputed;
		this.naive = naive;
	}
	
	public CodeGenerator(boolean naive){
		this.naive = naive;
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
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileToTranslate));
			String line;
			String MIPSLine;
			
			while ((line = br.readLine()) != null) {
				MIPSLine = translateInstruction(line)+"\n";
				System.out.println(MIPSLine);
				writeMIPSToFile(MIPSLine);
			}
			
			br.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	private String translateInstruction(String instruction){
		return Instruction.decodeInstruction(instruction);
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
	
	
	public static void main(String[] args){
		boolean naive = true;
		CodeGenerator cg = new CodeGenerator(naive);
		cg.setUpFile();
		cg.translateIRtoMIPS();
		
//		String temp =Instruction.decodeInstruction("FUNC_main:", naive);
//		cg.writeMIPSToFile(temp);
//		Instruction.decodeInstruction("assign, x$3%i#$t0, 5", naive);
//		Instruction.decodeInstruction("assign, z$3%f#$f0, x$3%i#$t2", naive);
//		Instruction.decodeInstruction("assign, y$3%i#$t2, x$3%i#$t0", naive);
//		Instruction.decodeInstruction("label:", naive);
//		Instruction.decodeInstruction("add, x$3%i#$t0, y$3%i#$t2, b$3%i#$t1", naive);
//		Instruction.decodeInstruction("breq, x$3%i#$t0, y$3%i#$t2, hello", naive);
//		Instruction.decodeInstruction("return, 5", naive);
////		StackFrame.printStackFrame();
//		Instruction.decodeInstruction("FUNC_test:", naive);
//		Instruction.decodeInstruction("assign, x$3%f#$f16, 5.0", naive);
//		Instruction.decodeInstruction("return, x$3%f#$f16", naive);

	}
}
