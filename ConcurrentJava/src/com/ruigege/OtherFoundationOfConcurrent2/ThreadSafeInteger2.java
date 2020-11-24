package com.ruigege.OtherFoundationOfConcurrent2;

public class ThreadSafeInteger2 {

	private volatile int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	
	
	
}
