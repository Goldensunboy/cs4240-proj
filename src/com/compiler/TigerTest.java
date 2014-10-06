package com.compiler;
import org.antlr.runtime.RecognitionException;


public class TigerTest {
	public static void main(String[] args) throws RecognitionException {

		String input = "void main()\n"+
						"begin\n"+ 
						"begin\n"+
						"var a : int := 1;\n" +
						"a := 2;\n" +
						"end;\n"+
						"end;\n";
		TigerCompiler compiler = new TigerCompiler(); 
		compiler.compile(input);
		
	}
}
