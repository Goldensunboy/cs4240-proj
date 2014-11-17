package com.analyzer;

import java.util.List;

public interface RegisterAllocator {
	public abstract List<String> allocateRegistersFromIRCode(List<String> IRList);
}
