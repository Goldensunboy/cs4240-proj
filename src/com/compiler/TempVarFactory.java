package com.compiler;

public class TempVarFactory {
	private static int nextVar;
	public TempVarFactory() {
		nextVar = 0;
	}
	public String nextTemp(Type t) {
		String type = "%";
		switch(t.getName()) {
		case "int":
			type += "i";
			break;
		case "fixedpt":
			type += "f";
			break;
		case "array":
			type += "a";
			break;
		default:
			type += "u";
		}
		return "$t" + nextVar++ + type;
	}
}
