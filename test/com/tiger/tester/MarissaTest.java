package com.tiger.tester;

import java.util.HashMap;
import java.util.List;

import code_generation.CodeGenerator;

import com.analyzer.RegisterAndVariableDetectionFactory;
import com.analyzer.basic_block_approach.cfg.CFGRegisterAllocator;
import com.analyzer.basic_block_approach.ebb.EBBRegisterAllocator;
import com.analyzer.naive_approach.NaiveRegisterAllocator;
import com.antlr.generated.TigerParser;

public class MarissaTest {
	public static void test(TigerParser parser) {
		List<String> IRList = parser.getIRCode();
		System.out.println("==== IR Code ====");
		for (String ir : IRList) 
			System.out.println(ir);
		System.out.println("=================");
//		EBBRegisterAllocator registerAllocator = new EBBRegisterAllocator(IRList);
//		CFGRegisterAllocator registerAllocator = new CFGRegisterAllocator(IRList);
		NaiveRegisterAllocator registerAllocator = new NaiveRegisterAllocator(IRList);
		List<String> IRIR = registerAllocator.getAnnotatedIRCode();
		for(int i = 0; i<IRIR.size(); i++)
			System.out.println(IRIR.get(i));
		HashMap<String, List<String>> functionVariables = RegisterAndVariableDetectionFactory.getFunctionVariables(IRIR);
		HashMap<String, List<String>> functionRegisters = RegisterAndVariableDetectionFactory.getFunctionRegisters(IRIR);
		HashMap<String, HashMap<String, Integer>> functionArraySizes = RegisterAndVariableDetectionFactory.getFunctionArraySizes(IRIR);
		CodeGenerator codeGenerator = new CodeGenerator(parser, IRIR, functionVariables, functionRegisters, functionArraySizes);
		codeGenerator.generateCode();
//		codeGenerator.test();
		// Get IR code
//		IRList = parser.getIRCode();
//		System.out.println("IR Code:");
//		if(IRList == null) {
//			System.out.println(IRList);
//		} else {
//			for(String s : IRList) {
//				System.out.println("\t" + s);
//			}
//		}
//		
//		// Print details about the analyzed IR code
//		regalloc = new NaiveRegisterAllocator(IRList);
//		System.out.println("before");
//		((NaiveRegisterAllocator)regalloc).printRegisterAllocatorData();


	}
}
