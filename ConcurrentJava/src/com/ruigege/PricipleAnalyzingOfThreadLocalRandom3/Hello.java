package com.ruigege.PricipleAnalyzingOfThreadLocalRandom3;

public class Hello {
	public static void main(String[] args) {
		new Hello().helloB();
	}
	
	public synchronized void helloA() {
		System.out.println("HelloA");
	}
	
	public synchronized void helloB() {
		System.out.println("HelloB");
		helloA();
	}
	


}
