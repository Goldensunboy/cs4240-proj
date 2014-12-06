package com.tiger.tester;

import java.util.List;

import com.antlr.generated.TigerParser;

public class VincentTest {
	public static void test(TigerParser parser) {
		runIRCode(parser);
		printSymbolTable(parser);
	}
	
	private static void printSymbolTable(TigerParser parser) {
		parser.printSymbolTable();
	}
	
	private static void runIRCode(TigerParser parser) {
		List<String> IRList = parser.getIRCode();
		System.out.println("IR code:\n**********");
		if(IRList == null) {
			System.out.println(IRList);
			return;
		}
	    for(String s : IRList) {
	      System.out.println(s);
	    }
	    System.out.println("**********");
	}
	
}
