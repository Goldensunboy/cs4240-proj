package com.tiger.tester;

import java.util.List;

import code_generation.CodeGenerator;

import com.analyzer.basic_block_approach.cfg.CFGRegisterAllocator;
import com.antlr.generated.TigerParser;

public class MarissaTest {
	public static void test(TigerParser parser) {
		List<String> IRList = parser.getIRCode();
		System.out.println("==== IR Code ====");
		for (String ir : IRList) 
			System.out.println(ir);
		System.out.println("=================");
		CFGRegisterAllocator registerAllocator = new CFGRegisterAllocator(IRList);
//		NaiveRegisterAllocator registerAllocator = new NaiveRegisterAllocator(parser.getIRCode());
		List<String> IRIR = registerAllocator.getAnnotatedIRCode();
		for(int i = 0; i<IRIR.size(); i++)
			System.out.println(IRIR.get(i));
		CodeGenerator codeGenerator = new CodeGenerator(parser, IRIR);
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
