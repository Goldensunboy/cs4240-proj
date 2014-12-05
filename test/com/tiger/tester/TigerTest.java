package com.tiger.tester;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import com.analyzer.RegisterAllocator;
import com.analyzer.basic_block_approach.ebb.EBBRegisterAllocator;
import com.analyzer.naive_approach.NaiveRegisterAllocator_deprecated;
import com.antlr.generated.TigerParser;
import com.compiler.TigerCompiler;
import com.compiler.TigerCompiler.CompilerErrorReport;

public class TigerTest {

//	private ExceptionHandler exceptionHandler;
	private String mainDeveloperName;
	private boolean irCodeOn, symbolTableOn;
	
	public TigerTest(String mainDeveloperName, boolean irCodeOn, boolean symbolTableOn) {
//		this.exceptionHandler = new ExceptionHandler();
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
				} catch (Exception e) {
					e.printStackTrace();
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
			regalloc = new NaiveRegisterAllocator_deprecated(IRList);
			System.out.println("before");
			((NaiveRegisterAllocator_deprecated)regalloc).printRegisterAllocatorData();
			System.out.println("after");
			break;
		
		case SAMAN:
			IRList = parser.getIRCode();
			System.out.println("==== IR Code ====");
			for (String ir : IRList) 
				System.out.println(ir);
			System.out.println("=================");
			
//			CFGRegisterAllocator allocator = new CFGRegisterAllocator(IRList);
			EBBRegisterAllocator allocator = new EBBRegisterAllocator(IRList);
//			NaiveRegisterAllocator_saman allocator = new NaiveRegisterAllocator_saman(IRList);
			List<String> IRAndRegs = allocator.getAnnotatedIRCode();
			System.out.println("==== IR Regs ====");
			for (String ir : IRAndRegs) 
				System.out.println(ir);
			System.out.println("=================");
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
			
			// Verify that the IR code has valid variable names
			for(String s : IRList) {
				String parts[] = s.split(", ");
				// Skip the operation text (i = 1 instead of 0)
				for(int i = 1; i < parts.length; ++i) {
					if(parts[i].length() > 1 && "$t".equals(parts[i].substring(0, 2))) {
						// Temp var
						if(!parts[i].contains("%")) {
							System.err.println("No type on a temp: " + parts[i]);
							System.exit(1);
						}
					} else if(parts[i].contains("$")) {
						// Variable
						if(!parts[i].contains("%")) {
							System.err.println("No type on variable: " + parts[i]);
							System.exit(1);
						}
						String[] parts2 = parts[i].split("%");
						if("-1".equals(parts2[1])) {
							System.err.println("Invalid type on variable: " + parts[i]);
							System.exit(1);
						}
					} else {
						// Constant
						if(parts[i].contains("%")) {
							System.err.println("Type on a constant: " + parts[i]);
							System.exit(1);
						}
					}
				}
			}
			
			// Print details about the analyzed IR code
			regalloc = new NaiveRegisterAllocator_deprecated(IRList);
			((NaiveRegisterAllocator_deprecated)regalloc).printRegisterAllocatorData();
			
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
//				return new DeveloperName[]{INDIVIDUAL};
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
