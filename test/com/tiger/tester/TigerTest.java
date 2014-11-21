package com.tiger.tester;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import com.analyzer.NaiveRegisterAllocator;
import com.analyzer.RegisterAllocator;
import com.antlr.generated.TigerParser;
import com.compiler.TigerCompiler;
import com.compiler.TigerCompiler.CompilerErrorReport;
import com.exception.ExceptionHandler;
import com.exception.UnrecoverableException;

public class TigerTest {

	private ExceptionHandler exceptionHandler;
	private String mainDeveloperName;
	private boolean irCodeOn, symbolTableOn;
	
	public TigerTest(String mainDeveloperName, boolean irCodeOn, boolean symbolTableOn) {
		this.exceptionHandler = new ExceptionHandler();
		this.mainDeveloperName = mainDeveloperName;
		this.irCodeOn = irCodeOn;
		this.symbolTableOn = symbolTableOn;
	}
	
	/**
	 * Each team member has a test_case_<user_name> package in test.com.tiger
	 * Please put your test cases there. Based on the user name of the system
	 * tester figures out which set of test cases to execute
	 */
	public void runTestCases(){
		
		DeveloperName[] developerNames = DeveloperName.lookup(mainDeveloperName);

		for(DeveloperName developerName : developerNames) {
			String path = "test/com/tiger/test_case_" + developerName.getPreferedName();
			System.out.println("----------Running \""+path + "\"------------");
			System.out.println();
			System.out.println(path);
			File folder = new File(path);
			for (File file : folder.listFiles()) {
				String fileName = file.getName();

				System.out.println("Compiling: " + fileName);
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
						runSpecificTestCases(compiler.getTigerParser(), developerName,
								irCodeOn, symbolTableOn);
					}
					
					System.out.println(errorReport.getErrorReportMessage());
				} catch (IOException  e ) {
					exceptionHandler.handleException(-1,null, null, null, UnrecoverableException.class);
				}catch (NullPointerException e) {
					exceptionHandler.handleException(-1,null, null, null, UnrecoverableException.class);
				} catch (Exception e) {
					exceptionHandler.handleException(-1,null, null, null, UnrecoverableException.class);
				}
	
				System.out.println("******************************");
				System.out.println();
			}
		}
	}
	
	/**
	 * Put your test methods below your case. It will automatically call those methods
	 */
	private void runSpecificTestCases(TigerParser parser, DeveloperName developerName,
			boolean irCodeOn, boolean symbolTableOn) {
		List<String> IRList;
		RegisterAllocator regalloc;
		switch (developerName) {
		case MARISSA:
			break;
		
		case SAMAN:
			// Get IR code
			IRList = parser.getIRCode();
			System.out.println("IR Code:");
			if(IRList == null) {
				System.out.println(IRList);
			} else {
				for(String s : IRList) {
					System.out.println("\t" + s);
				}
			}
			
			// Print details about the analyzed IR code
			regalloc = new NaiveRegisterAllocator(IRList);
			((NaiveRegisterAllocator)regalloc).printRegisterAllocatorData();
			break;
			
		case ANDREW:
			
			// Get IR code
			IRList = parser.getIRCode();
			System.out.println("IR Code:");
			if(IRList == null) {
				System.out.println(IRList);
			} else {
				for(String s : IRList) {
					System.out.println("\t" + s);
				}
			}
			
			// Print details about the analyzed IR code
			regalloc = new NaiveRegisterAllocator(IRList);
			((NaiveRegisterAllocator)regalloc).printRegisterAllocatorData();
			
			break;
			
		case VINCENT:
			if(irCodeOn) {				
				runIRCode(parser);
			}
			if(symbolTableOn) {				
				printSymbolTable(parser);
			}
			break;
		}
	}

	private void printSymbolTable(TigerParser parser) {
		parser.printSymbolTable();
	}
	
	private void runIRCode(TigerParser parser) {
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
	
	public enum DeveloperName {
		SAMAN("saman", "saman"),
		MARISSA("Risa", "marissa"),
		ANDREW("andrew", "andrew"),
		VINCENT("vincent", "vincent"),
		INDIVIDUAL("indvidual", "individual");
		
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
				return new DeveloperName[]{SAMAN};
			} else if (preferedName.equals(ANDREW.getPreferedName())) {
				return new DeveloperName[]{ANDREW};
			} else if (preferedName.equals(VINCENT.getPreferedName())) {
				return new DeveloperName[]{VINCENT};
			}
			
			throw new RuntimeException("Invalid user " + preferedName);
		}
	}
}
