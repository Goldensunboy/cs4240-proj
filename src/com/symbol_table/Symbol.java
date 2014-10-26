package com.symbol_table;

import com.attribute.Attribute;

public class Symbol {
	private Scope scope;
	private Attribute attribute;
	
	public Symbol(Scope scope, Attribute attribute) {
		this.scope = scope;
		this.attribute = attribute;
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}
	
	public String toString() {
		return attribute.toString();
	}
}
