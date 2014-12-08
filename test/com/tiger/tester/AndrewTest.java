package com.tiger.tester;

import java.util.HashMap;
import java.util.List;

import code_generation.CodeGenerator;

import com.analyzer.RegisterAllocator;
import com.analyzer.RegisterAndVariableDetectionFactory;
import com.analyzer.basic_block_approach.cfg.CFGRegisterAllocator;
import com.analyzer.basic_block_approach.ebb.EBBRegisterAllocator;
import com.analyzer.naive_approach.NaiveRegisterAllocator;
import com.analyzer.naive_approach.NaiveRegisterAllocator_deprecated;
import com.antlr.generated.TigerParser;

public class AndrewTest {
	static int idx = 0;
	public static void test(TigerParser parser, String filename) {
		
		List<String> IRList = parser.getIRCode();

		boolean all_allocs = true;
		int which_alloc = 1;
		
		for(int j = 1; !all_allocs && j == 1 || all_allocs && j <= 3; ++j) {
			
			// Get IR code
			RegisterAllocator registerAllocator;
			String alloc = "naive";
			switch(all_allocs ? j : which_alloc) {
			case 1:
				alloc = "ebb";
				registerAllocator = new EBBRegisterAllocator(IRList);
				break;
			case 2:
				alloc = "cfg";
				registerAllocator = new CFGRegisterAllocator(IRList);
				break;
			default:
				registerAllocator = new NaiveRegisterAllocator(IRList);
			}
			System.out.println("===============================================");
			System.out.println("Compiling " + filename + " using " + alloc + ":");
			System.out.println("===============================================");
			List<String> IRIR = registerAllocator.getAnnotatedIRCode();
			for(int i = 0; i<IRIR.size(); i++)
				System.out.println(IRIR.get(i));
			for(String s : IRIR)
				System.out.println("\t\t" + s);
			HashMap<String, List<String>> functionVariables = RegisterAndVariableDetectionFactory.getFunctionVariables(IRIR);
			HashMap<String, List<String>> functionRegisters = RegisterAndVariableDetectionFactory.getFunctionRegisters(IRIR);
			HashMap<String, HashMap<String, Integer>> functionArraySizes = RegisterAndVariableDetectionFactory.getFunctionArraySizes(IRIR);
			CodeGenerator codeGenerator = new CodeGenerator(parser, IRIR, functionVariables, functionRegisters, functionArraySizes,
					"output/" + filename.replaceFirst(".tiger", "_") + alloc + ".s");
			codeGenerator.generateCode();
		}
	}
}
