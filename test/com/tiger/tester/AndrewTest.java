package com.tiger.tester;

import java.util.HashMap;
import java.util.List;

import code_generation.CodeGenerator;

import com.analyzer.RegisterAllocator;
import com.analyzer.RegisterAndVariableDetectionFactory;
import com.analyzer.basic_block_approach.cfg.CFGRegisterAllocator;
import com.analyzer.naive_approach.NaiveRegisterAllocator_deprecated;
import com.antlr.generated.TigerParser;

public class AndrewTest {
	public static void test(TigerParser parser) {
		
		// Get IR code
		List<String> IRList = parser.getIRCode();
		System.out.println("==== IR Code ====");
		for (String ir : IRList) 
			System.out.println(ir);
		System.out.println("=================");
		
		// Allocate registers
		RegisterAllocator registerAllocator = new CFGRegisterAllocator(IRList);
		List<String> IRIR = registerAllocator.getAnnotatedIRCode();
		for(int i = 0; i<IRIR.size(); i++)
			System.out.println(IRIR.get(i));
		
		// Check registers and vars per function
		HashMap<String, List<String>> varmap =
				RegisterAndVariableDetectionFactory.getFunctionVariables(IRIR);
		HashMap<String, List<String>> regmap =
				RegisterAndVariableDetectionFactory.getFunctionRegisters(IRIR);
		
		System.out.println("Variables:");
		for(String func : varmap.keySet()) {
			System.out.println("\t" + func);
			for(String var : varmap.get(func)) {
				System.out.println("\t\t" + var);
			}
		}
		System.out.println();
		System.out.println("Registers:");
		for(String func : regmap.keySet()) {
			System.out.println("\t" + func);
			for(String reg : regmap.get(func)) {
				System.out.println("\t\t" + reg);
			}
		}
		
	}
}
