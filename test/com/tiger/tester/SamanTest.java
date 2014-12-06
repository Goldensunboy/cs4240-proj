package com.tiger.tester;

import java.util.List;

import com.analyzer.basic_block_approach.ebb.EBBRegisterAllocator;
import com.antlr.generated.TigerParser;

public class SamanTest {
	public static void test(TigerParser parser) {		
		List<String> IRList = parser.getIRCode();
		System.out.println("==== IR Code ====");
		for (String ir : IRList) 
			System.out.println(ir);
		System.out.println("=================");
		
//	CFGRegisterAllocator allocator = new CFGRegisterAllocator(IRList);
		EBBRegisterAllocator allocator = new EBBRegisterAllocator(IRList);
//		NaiveRegisterAllocator allocator = new NaiveRegisterAllocator(IRList);
		List<String> IRAndRegs = allocator.getAnnotatedIRCode();
		System.out.println("==== IR Regs ("+ allocator.getClass().getSimpleName()+ ") ====");
		for (String ir : IRAndRegs) 
			System.out.println(ir);
		System.out.println("=================");
	}
}
