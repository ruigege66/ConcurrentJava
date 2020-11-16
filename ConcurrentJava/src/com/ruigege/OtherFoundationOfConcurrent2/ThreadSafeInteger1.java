package com.ruigege.OtherFoundationOfConcurrent2;

public class ThreadSafeInteger1 {
	private int value;

	public synchronized int getValue() {
		return value;
	}

	public synchronized void setValue(int value) {
		this.value = value;
	}
	

}
