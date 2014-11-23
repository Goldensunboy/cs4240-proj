package code_generation;


public class CodeGenerator {
	
	public static void main(String[] args){
		Instruction instruction = new Instruction();
		instruction.decodeInstruction("assign, x$3%i#$t0, 5");
		instruction.decodeInstruction("assign, y$3%i#$t2, x$3%i#$t0");
		instruction.decodeInstruction("label:");
		instruction.decodeInstruction("add, x$3%i#$t0, y$3%i#$t2, x$3%i#$t0");
	}
}
