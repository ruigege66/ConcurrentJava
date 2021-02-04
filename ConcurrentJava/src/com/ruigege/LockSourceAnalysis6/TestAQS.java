package com.ruigege.LockSourceAnalysis6;

import org.w3c.dom.Node;

public class TestAQS {
	
	public final void acquire(int arg) {
		if(!tryAcquire(arg) && acquireQueued(addWaiter(Node.EXCLUSIVE),arg)) {
			selfInterrupt();
		}
	}
	
	public final boolean release(int arg) {
		if(tryRelease(arg)) {
			Node h = head;
			if(head != null && head.waitStatus != 0) {
				unparkSuccessor(h);
			}
			return true;
		}
		return false;
	}
	
	public final void acquireShared(int arg) {
		if(tryAcquireShared(arg) < 0) {
			doAcquireShared(arg);
		}
	}
	
	public final boolean releaseShared(int arg) {
		if(tryReleaseShared(arg)) {
			doReleaseShared();
			return true;
		}
		return false;
	}
	
	private Node enq(final Node node) {
		for(;;) {
			Node t = tail;
			if( t == null) {
				if(compareAndSetHead(new Node())) {
					tail = head;
				}
			}else {
				node.prev = t;
				if(compareAndSetTail(t,node)) {
					t.next = node;
				}
			}
		}
	}

}
