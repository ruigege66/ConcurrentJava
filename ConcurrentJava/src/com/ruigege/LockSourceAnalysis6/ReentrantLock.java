package com.ruigege.LockSourceAnalysis6;

public class ReentrantLock {

	Sync sync;
	
	public ReentrantLock() {
		sync = new NonfairLock();
	}
	
	public ReentrantLock(boolean fair) {
		sync = fair? new FairLock():new NonfairLock();
	}
}
