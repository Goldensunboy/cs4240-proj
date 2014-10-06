package com.compiler;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTree;

import com.antlr.generated.TigerLexer;
import com.antlr.generated.TigerParser;

public class TigerCompiler extends AbstractCompiler {
	public CommonTree compile(String expression) {
		ANTLRStringStream input = new ANTLRStringStream(expression);
		Lexer lexer = new TigerLexer(input);
		TokenStream tokens = new CommonTokenStream(lexer);

		// parser generates abstract syntax tree
		TigerParser parser = new TigerParser(tokens);
		TigerParser.tigerProgram_return ret;
		try {
			ret = parser.tigerProgram();

			// acquire parse result
			CommonTree ast = (CommonTree) ret.getTree();
			printTree(ast);
			return ast;
		} catch (RecognitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
