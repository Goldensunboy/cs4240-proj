package code_generation;

import code_generation.Register.RegisterType;
import code_generation.StackArgument.Category;


public class CodeGenerator {
	
	public static void main(String[] args){
		Instruction instruction = new Instruction();
		StackFrame.pushOnStack(new StackArgument("x$3", RegisterType.INT, false, Category.UNINITIALIZED));
		StackFrame.pushOnStack(new StackArgument("y$3", RegisterType.INT, false, Category.UNINITIALIZED));
		instruction.decodeInstruction("assign, x$3%i#$t0, 5", true);
		instruction.decodeInstruction("assign, y$3%i#$t2, x$3%i#$t0", true);
		instruction.decodeInstruction("label:", true);
		instruction.decodeInstruction("add, x$3%i#$t0, y$3%i#$t2, x$3%i#$t0", true);
	}
}
