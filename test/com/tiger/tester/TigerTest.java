package com.tiger.tester;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.antlr.runtime.RecognitionException;

import com.antlr.generated.TigerParser;
import com.compiler.TigerCompiler;
import com.compiler.TigerCompiler.CompilerErrorReport;


public class TigerTest {

	/**
	 * Each team member has a test_case_<user_name> package in test.com.tiger
	 * Please put your test cases there. Based on the user name of the system
	 * tester figures out which set of test cases to execute
	 */
	public static void main(String[] args) throws RecognitionException {
		
		String developerName = System.getProperty("user.name");
		System.out.println(System.getProperty("user.dir"));
		System.out.println();
		
		File folder = new File("test/com/tiger/test_case_" + developerName);
		for (File file : folder.listFiles()) {
			System.out.println("Compiling: " + file.getName());
			System.out.println("******************************");
			FileInputStream fis;
			try {
				fis = new FileInputStream(file);
				byte[] data = new byte[(int) file.length()];
				fis.read(data);
				fis.close();

				TigerCompiler compiler = new TigerCompiler();
				String input = new String(data, "UTF-8");
				
				compiler.compile(input);
				
				CompilerErrorReport errorReport = compiler.getErrorReport(); 
				
				if (!errorReport.hasError()) {
					runSpecificTestCases(compiler.getTigerParser(), developerName);
				}
				
				System.out.println(errorReport.getErrorReportMessage());				
			} catch (IOException e) {
				e.printStackTrace();
			} finally{
			}

			System.out.println("******************************");
			System.out.println();
		}
	}
	
	/**
	 * Put your test methods below your case. It will automatically call those methods
	 */
	private static void runSpecificTestCases(TigerParser parser, String developerName) {
		switch (developerName) {
		case "rissa":
			break;
		
		case "saman":
			parser.printAttributeMap();
			//parser.printTheNameSpace();
			break;
			
		case "andrew":
			parser.printTheIRCode();
			break;
		}
	}
}
