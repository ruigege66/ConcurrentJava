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
	
	protected final boolean tryAcuqire(int acquires) {
		final Thread current = Thread.currentThread();
		int c = getState();
		// (7)��ǰAQS״ֵ̬Ϊ0
		if(c == 0) {
			// (8)��ƽ�Բ���
			if(!hasQueuedPredecessors() && compareAndSetState(0,acquires)) {
				setExclusiveOwnerThread(current);
				return true;
			}
		}
		
		// (9)��ǰ�߳��Ǹ���������
		else if(current == getExclusiveOwnerThread()) {
			int nextc = c + acquires;
			if(nextc<0) {
				throw new Error("Maximum lock count exceeded");
			}
		}
	}
	

}
