package com.tiger.tester;

import java.util.List;

import com.analyzer.RegisterAllocator;
import com.analyzer.naive_approach.NaiveRegisterAllocator_deprecated;
import com.antlr.generated.TigerParser;

public class AndrewTest {
	public static void test(TigerParser parser) {
		
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
		RegisterAllocator regalloc = new NaiveRegisterAllocator_deprecated(IRList);
		((NaiveRegisterAllocator_deprecated)regalloc).printRegisterAllocatorData();
		
	}
}
