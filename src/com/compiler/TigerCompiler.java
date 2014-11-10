package com.compiler;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;

import com.antlr.generated.TigerLexer;
import com.antlr.generated.TigerParser;
import com.exception.ParserCreationException;

public class TigerCompiler{
	private TigerParser parser;
	private CompilerErrorReport errorReport = CompilerErrorReport.NO_ERROR;
	public void compile(String expression) {
		ANTLRStringStream input = new ANTLRStringStream(expression);
		TigerLexer lexer = new TigerLexer(input);
		TokenStream tokens = new CommonTokenStream(lexer);

		parser = new TigerParser(tokens);
		try {
			parser.tigerProgram();
			if (lexer.getErrorFlag()){
				errorReport = CompilerErrorReport.LEXER_ERROR;
			} else if(parser.getErrorFlag()) {
				errorReport = CompilerErrorReport.PARSER_ERROR;
			}
			
		} catch (RecognitionException e) {
			e.printStackTrace();
			throw new ParserCreationException(e.getMessage());
		}
	}
	
	public TigerParser getTigerParser() {
		return parser;
	}
	
	public CompilerErrorReport getErrorReport() {
		return errorReport;
	}
	
	public enum CompilerErrorReport {
		LEXER_ERROR("Finished with lexer error(s)", true),
		PARSER_ERROR("Finished with parser error(s)", true),
		NO_ERROR("Successfully parsed", false);
		
		private String errorMessage; 
		private boolean hasError;
		CompilerErrorReport(String errorMessage, boolean hasError) {
			this.errorMessage = errorMessage;
			this.hasError = hasError;
		}
		
		public String getErrorReportMessage() {
			return errorMessage;
		}
		
		public boolean hasError() {
			return hasError;
		}
		
	}
}
