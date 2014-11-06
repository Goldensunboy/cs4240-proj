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
		
		DeveloperName[] developerNames = DeveloperName.lookup(System.getProperty("user.name"));
		System.out.println(System.getProperty("user.dir"));
		System.out.println();
		
		for(DeveloperName developerName : developerNames) {
			String path = "test/com/tiger/test_case_" + developerName.getPreferedName();
			System.out.println("----------Running \""+path + "\"------------");
			System.out.println();
			
			File folder = new File(path);
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
	}
	
	/**
	 * Put your test methods below your case. It will automatically call those methods
	 */
	private static void runSpecificTestCases(TigerParser parser, DeveloperName developerName) {
		switch (developerName) {
		case MARISSA:
			break;
		
		case SAMAN:
//			parser.printAttributeMap();
			//parser.printTheNameSpace();
			break;
			
		case ANDREW:
			parser.printTheIRCode();
			break;
		}
	}
	
	public enum DeveloperName {
		SAMAN("saman", "saman"),
		MARISSA("Risa", "marissa"),
		ANDREW("andrew", "andrew");
		
		private String actualName, preferedName;
		DeveloperName(String actualName, String preferedName) {
			this.actualName = actualName;
			this.preferedName = preferedName;
		}
		
		public String getActualName() {
			return actualName;
		}
		
		public String getPreferedName() {
			return preferedName;
		}
		
		public static DeveloperName[] lookup(String preferedName) {
			if(preferedName.equals(MARISSA.getActualName())) {
				return new DeveloperName[]{MARISSA};
			} else if (preferedName.equals(SAMAN.getPreferedName())) {
				return new DeveloperName[]{SAMAN, ANDREW};
			} else if (preferedName.equals(ANDREW.getPreferedName())) {
				return new DeveloperName[]{ANDREW};
			}
			throw new RuntimeException("Invalid user " + preferedName);
		}
	}
}
