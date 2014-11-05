package com.compiler;

public class TempVarFactory {
	private static int nextVar;
	public TempVarFactory() {
		nextVar = 0;
	}
	public String nextTemp() {
		return "$t" + nextVar++;
	}
}
