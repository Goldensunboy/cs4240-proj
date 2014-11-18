package com.analyzer;

import java.util.List;

public interface RegisterAllocator {
	public abstract void analyzeRegistersFromIRCode(List<String> IRList);
}
