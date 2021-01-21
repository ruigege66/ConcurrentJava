package com.ruigege.LockSourceAnalysis6;

import java.util.concurrent.locks.LockSupport;

public class TestPark {
	public void testPark() {
		LockSupport.park();
	}
	public static void main(String[] args) {
		TestPark testPark = new TestPark();
		testPark.testPark();
	}

}
