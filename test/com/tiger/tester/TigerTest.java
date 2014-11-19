package com.tiger.tester;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.antlr.generated.TigerParser;
import com.compiler.TigerCompiler;
import com.compiler.TigerCompiler.CompilerErrorReport;
import com.exception.ExceptionHandler;
import com.exception.ShouldNotHappenException;
import com.exception.UnrecoverableException;
import com.analyzer.GraphNode;
import com.analyzer.RegisterAllocator;
import com.analyzer.NaiveRegisterAllocator;

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
		switch (developerName) {
		case MARISSA:
			break;
		
		case SAMAN:
			break;
			
		case ANDREW:
			
			// Get IR code
			List<String> IRList = parser.getIRCode();
			System.out.println("IR Code:");
			if(IRList == null) {
				System.out.println(IRList);
			} else {
				for(String s : IRList) {
					System.out.println("\t" + s);
				}
			}
			
			// Analyze IR code
			RegisterAllocator regalloc = new NaiveRegisterAllocator(IRList);
			
			// Print labels found
			System.out.println("Labels:");
			for(String s : ((NaiveRegisterAllocator)regalloc).getLabelList()) {
				System.out.println("\t" + s);
			}
			
			// Print variables found
			ArrayList<String> varSetSorted =
					new ArrayList<String>(((NaiveRegisterAllocator)regalloc).getVarSet());
			Collections.sort(varSetSorted, new Comparator<String>() {
				public int compare(String s0, String s1) {
					boolean s0temp = "$t".equals(s0.substring(0, 2));
					boolean s1temp = "$t".equals(s1.substring(0, 2));
					if(s0temp && !s1temp) {
						return 1;
					} else if(!s0temp && s1temp) {
						return -1;
					} else if(s0temp && s1temp) {
						int L = Integer.parseInt(s0.substring(2));
						int R = Integer.parseInt(s1.substring(2));
						return L - R;
					} else {
						String[] s0parts = s0.split("$");
						String[] s1parts = s1.split("$");
						int cmp = s0parts[0].compareTo(s1parts[0]);
						if(cmp != 0) {
							return cmp;
						} else {
							return Integer.parseInt(s0parts[1]) - Integer.parseInt(s1parts[1]);
						}
					}
				}
			});
			System.out.println("Variables:");
			for(String s : varSetSorted) {
				System.out.println("\t" + s);
			}
			
			// Print variable IDs
			Map<String, Integer> varMap = ((NaiveRegisterAllocator)regalloc).getVarMap();
			System.out.println("Variable IDs:");
			for(Entry<String, Integer> e : varMap.entrySet()) {
				System.out.println("\t" + e);
			}
			
			// Print live matrix
			boolean liveMatrix[][] = ((NaiveRegisterAllocator)regalloc).getLiveMatrix();
			System.out.println("Live matrix:");
			System.out.println("\tHeight: " + liveMatrix.length);
			System.out.println("\tWidth:  " + liveMatrix[0].length);
			
			// Print time alive for each variable
			int lineLength = 0;
			int varLength = 0;
			for(String s : IRList) {
				lineLength = s.length() > lineLength ? s.length() : lineLength;
			}
			for(String s : varMap.keySet()) {
				varLength = s.length() > varLength ? s.length() : varLength;
			}
			for(int i = 0; i < lineLength + 1; ++i) {
				System.out.print(' ');
			}
			for(String s : varMap.keySet()) {
				System.out.print(" " + s);
			}
			System.out.println();
			for(int i = 0; i < lineLength + 1; ++i) {
				System.out.print(' ');
			}
			System.out.print('+');
			for(String s : varMap.keySet()) {
				for(int i = 0; i < s.length() + 1; ++i) {
					System.out.print('-');
				}
			}
			System.out.println();
			ArrayList<String> varList = new ArrayList<String>(varMap.keySet());
			for(int i = 0; i < IRList.size(); ++i) {
				System.out.print(IRList.get(i));
				for(int j = 0; j < lineLength - IRList.get(i).length() + 1; ++j) {
					System.out.print(' ');
				}
				System.out.print('|');
				for(int j = 0; j < liveMatrix[0].length; ++j) {
					System.out.print(" " + (liveMatrix[i][varMap.get(varList.get(j))] ? '1' : '0'));
					for(int k = 0; k < varList.get(j).length() - 1; ++k) {
						System.out.print(' ');
					}
				}
				System.out.println();
			}
			
			// Print graph nodes
			System.out.println("Variable graph:");
			Map<String, GraphNode> varGraph = ((NaiveRegisterAllocator)regalloc).getVarGraph();
			for(String s : varSetSorted) {
				System.out.println("\t" + varGraph.get(s));
			}
			
			// Assert that there are no illegal colorings
			for(GraphNode g : varGraph.values()) {
				if(g.getColor() < 0) {
					throw new ShouldNotHappenException("Illegal coloring: " + g.getColor());
				}
				for(GraphNode n : g.getNeighbors()) {
					if(g.getColor() == n.getColor()) {
						throw new ShouldNotHappenException("Coloring collision: " + g);
					}
				}
			}
			
			// Print annotated IR code
			List<String> annotatedIRCode = regalloc.getAnnotatedIRCode();
			System.out.println("Annotated IR code:");
			for(String s : annotatedIRCode) {
				System.out.println("\t" + s);
			}
			
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
