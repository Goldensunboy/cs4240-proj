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
	public static void test(TigerParser parser) {
		
		// Get IR code
		List<String> IRList = parser.getIRCode();
		System.out.println("==== IR Code ====");
		for (String ir : IRList) 
			System.out.println(ir);
		System.out.println("=================");
		
//		RegisterAllocator registerAllocator = new EBBRegisterAllocator(IRList);
//		RegisterAllocator registerAllocator = new CFGRegisterAllocator(IRList);
		RegisterAllocator registerAllocator = new NaiveRegisterAllocator(IRList);
		List<String> IRIR = registerAllocator.getAnnotatedIRCode();
		for(int i = 0; i<IRIR.size(); i++)
			System.out.println(IRIR.get(i));
		HashMap<String, List<String>> functionVariables = RegisterAndVariableDetectionFactory.getFunctionVariables(IRIR);
		HashMap<String, List<String>> functionRegisters = RegisterAndVariableDetectionFactory.getFunctionRegisters(IRIR);
		CodeGenerator codeGenerator = new CodeGenerator(parser, IRIR, functionVariables, functionRegisters, "output/prog" + idx++ + ".s");
		codeGenerator.generateCode();
	}
}
