package code_generation;

import java.util.ArrayList;

import code_generation.Register.RegisterType;
import code_generation.StackArgument.Category;


public class CodeGenerator {
	

	
	
	public static void main(String[] args){
		Instruction instruction = new Instruction();
		instruction.decodeInstruction("FUNC_main:", true);
		instruction.decodeInstruction("assign, x$3%i#$t0, 5", true);
		instruction.decodeInstruction("assign, z$3%f#$f0, x$3%i#$t2", true);
		instruction.decodeInstruction("assign, y$3%i#$t2, x$3%i#$t0", true);
		instruction.decodeInstruction("label:", true);
		instruction.decodeInstruction("add, x$3%i#$t0, y$3%i#$t2, x$3%i#$t0", true);
		instruction.decodeInstruction("breq, x$3%i#$t0, y$3%i#$t2, hello", true);
	}
}
