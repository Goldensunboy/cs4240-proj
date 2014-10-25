package com.compiler;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;

import com.antlr.generated.TigerLexer;
import com.antlr.generated.TigerParser;

public class TigerCompiler{
	public void compile(String expression) {
		ANTLRStringStream input = new ANTLRStringStream(expression);
		TigerLexer lexer = new TigerLexer(input);
		TokenStream tokens = new CommonTokenStream(lexer);

		TigerParser parser = new TigerParser(tokens);
		try {
			parser.tigerProgram();
			if (lexer.getErrorFlag()){
				System.out.println("Finished with lexer error(s)");
			} else if(parser.getErrorFlag()) {
				System.out.println("Finished with parser error(s)");
			} else {
				parser.printAttributeMap();
				System.out.println("Successfully parsed");
			}

		} catch (RecognitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
