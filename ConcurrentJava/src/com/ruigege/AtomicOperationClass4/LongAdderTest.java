package com.ruigege.AtomicOperationClass4;
import java.util.function.LongBinaryOperator;

import sun.misc.Unsafe;

@sun.misc.Contended public class LongAdderTest {
	
	volatile long value;
	
	public LongAdderTest(long value) {
		this.value = value;
	}

	private static final Unsafe unsafe;
	private static final long valueOffset;
	static {
		try {
			unsafe = Unsafe.getUnsafe();
			Class<?> ak = Cell.class;
			valueOffset = unsafe.objectFieldOffset(ak.getDeclaredField("value"));
		}catch(Exception e) {
			throw new Error(e);
		}
	}
	
	final long cas(long cmp,long val) {
		return unsafe.compareAndSwapLong(this,valueOffset,cmp,val);
	}
	
	public long sum() {
		Cell[] as = cells;
		long sum = base;
		if(as != null) {
			for (int i=0;i<as.length;i++) {
				if(Cell[i] != null) {
					sum += Cell[i].value;
				}
			}
		}
	}
	
	public void reset() {
		Cell[] as = cells;
		base = 0L;
		if(as != null) {
			for (int i=0;i<as.length;i++) {
				if(as[i] != null) {
					as[i].value = 0L;
				}
			}
		}
	}
	
	public long sunThenReset() {
		Cell[] as = cells;
		int sum = base;
		if(as != null) {
			for (int i=0;i<as.length;i++) {
				if(as[i] != null) {
					sum += as[i];
					as[i] = 0L;
					
				}
			}
		}
	}
	
	public void add(long x) {
		Cell[] as;
		long b,v;
		int m;
		Cell a;
		if((as = cells) != null) || !caseBase(b=base,b+x)){
			//���as��һ�������飬Ҳ���ǵ�һ����false����ô��ȥ�жϵڶ������ڶ�����������ʵ����һ��CAS������
			//���������ζ��û�г�ʼ�����飬���LongAdderʵ���������һ��base������Ҳ����˵������̫С�ˣ�������
			//��ʼ�����飬��ʱ���ʵ������һ��AtomicLongʵ����û�����𣬵ڶ����жϾ��Ǹ�base��ֵΪ���µ�������
			//���casʧ���ˣ���ô�ͽ��뵽������ж������
			boolean uncontended = true;
			if(as==null || (m = as.length-1)<0 || (a = as[getProbe() & m]) == null || !(uncontended=a.cas(v=a.value,v+x))){
				/**
				 * �ĸ��ж�������������
				 * ��1���������Ϊ�գ�����ִ���ж����ڣ���Ϊ�գ����ڶ�������
				 * ��2�����������Ԫ��Ϊ0������ִ���ж����ڣ�������1����������������
				 * ��3��getProbe()�������ڻ�ȡ��ȡ��ǰ�߳��б���threadLocalRandomProbe��ֵ�����ֵһ��ʼΪ0���ڽ���
				 * �����ж����л���г�ʼ�������ҵ�ǰ�߳�ͨ�������CellԪ�ص�cas��������֤��CellԪ��valueֵ���µ�ԭ���ԡ�
				 * �����������Ϊ���ҵ�һ�������е�Cell�������������ҿ�����0&m,�Ǿ���0�ˣ�Ҳ���ǰ�as[0]��ֵ��a����������ǿյģ���ֱ��
				 * ���뵽�ж�ִ���壻�����Ϊ�գ����ſ����ĸ��ж�����
				 * ��4�����ĸ��ж��������Ǹղŵ��Ǹ�aʹ��CAS����������ֵ���������ʧ���˾ͽ��뵽ִ����
				 */
				longAccumulate(x,null,uncontended);
			}
		}
	}
	
	final boolean casBase(long cmp,long val) {
		return UNSAFE.compareAndSwapLong(this,BASE,cmp,val);
	}
	
	final void longAccumulate(long x,LongBinaryOperator fn,boolean wasUncontended) {
		//��ʼ����ǰ�̵߳ı���threadLocalRandomProbe��ֵ
		//����6
		int h;
		if(h = getProbe() == 0) {
			ThreadLocalRandom.current();//��ȡһ���������ʵ��
			h = getProbe();//��ʼ��
			//��������ڼ��㵱ǰ�߳�Ӧ�ñ����䵽cells�������һ��CellԪ�ص�ʱ����õ�
			wasUncontended = true;//contend���ᣬ���
		}
		
		boolean collide = false;//collide��ͻ����ײ
		for(;;) {
			Cell[] as;Cell a;int n;long v;
			if((as=cells) != null && (n=as.length)>0) {//��ǰ�̵߳�����add�������Ҹ��ݵ�ǰ�̵߳������threadLocalRandomProbe��cellsԪ�صĸ�������Ҫ���ʵ�CellԪ����
				//�±꣬Ȼ��������ֶ�Ӧ�±�Ԫ�ص�ֵΪnull��������һ��cellԪ�ص�cells���飬�����ڽ������ȼѵ�cells����֮ǰҪ��������cellsBusyΪ1
				if((a=as[(n-1) &h]) == null) {
					if(cellsBusy == 0) {
						Cell r = new Cell(x);
						if(cellsBusy == 0 && casCellsBusy()) {
							boolean created = false;
						}
						try {
							Cell[] rs;int m,j;
							if((rs=cells) != null && (m=rs.length)>0 && rs[j = (m-1) &h] == null) {
								rs[j] = r;
								created = true;
							}
						}finally {
							cellsBusy = 0;
						}
						if(created) {
							break;
						}
						continue;
					}
				}
				collide = false;
			}else if(!wasUntended) {
				wasUncontended = true;
			}else if(a.cas(v=a.value,((fn==null)?v+x : fn.applyAsLong(v,x)))) {
				break;
			}else if(n >= NCPU || cells != as) {
				collide = false;
			}else if(!collide) {
				collide = ture;
			}else if(cellsBusy == 0 && casCellsBusy()) {
				try {
					if(cells == as) {
						Cell[] rs = new Cell[n<<1];
						for(int i=0;i<n;++i) {
							rs[i] = as[i];
						}
						cells = rs;
					}
				}finally {
					cellsBusy = 0;
				}
				collide = false;
				continue;
			}
			h = advanceProbe(h);
		}
		//����14��������е���cells�����ʼ��
		/**
		 * 1.cellsBusy��һ����ʾ����Ա����������ʵ����������״ֵֻ̬��0��1��������CellԪ�أ�����Cell�������
		 * ��ʼ�����飬ʹ��CAS��������֤ͬʱֻ��һ���߳̿��Խ�������֮һ����
		 * 2.Ϊ0��ʱ��˵��cells����û�б���ʼ���������ݣ�Ҳû���½�CellԪ�أ�Ϊ1˵��cells�����ڱ���ʼ���������ݣ����ߵ�ǰ�½���CellԪ�أ�ͨ��CAS����������0��1״̬�л�������ʹ����casCellBusy����
		 * 3.���赱ǰ�߳�ͨ��CAS����cellsBusyΪ1����ǰ�߳̿�ʼ��ʼ����������ô���ʱ�������߳̾Ͳ��ܽ��������ˡ�
		 */
		else if(cellsBusy == 0 && cells == as && casCellsBusy()){
			boolean init = false;
			try {
				//��Ϊ�ڶ��߳��£������б�Ҫ�ٴ��ж�һ��
				if(cells == as) {
					//��ʼ����������Ϊ2
					Cell[] rs = new Cell[2];
					//h&l�������㵱ǰ�߳�Ӧ�÷���cell������ĸ�λ��
					//Ҳ����ʹ�õ�ǰ�̵߳�threadLocalRandomProbe����ֵ��cells����Ԫ�ظ���-1��������
					rs[h&l] = new Cell(x);
					cells = rs;
					//��ʾ�����Ѿ���ɳ�ʼ��
					init = true;
				}
			}finally {
				//���������cellBusys��ǣ�����û��ʹ��CAS����������ȴ���̰߳�ȫ�ģ���Ϊ�ñ�����һ��
				//volatile��������֤���ڴ�ɼ��ԣ��������ݺ��cells����������˰������ƹ�����Ԫ���⣬����������Ԫ�أ���ЩԪ�ص�ֵ
				//����null��
				cellsBusy = 0;
			}
			if(init) {
				break;
			}
		}else if(casBase(v = base,((fn == null) ? v+x:fn.applyAsLong(v,x)))) {
			break; 
		}
		
	}
}
