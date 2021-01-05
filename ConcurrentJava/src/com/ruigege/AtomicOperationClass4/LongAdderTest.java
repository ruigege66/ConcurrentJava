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
			//如果as是一个空数组，也就是第一个是false，那么会去判断第二个，第二个函数，其实就是一个CAS操作，
			//空数组就意味着没有初始化数组，这个LongAdder实例，里面就一个base变量，也就是说并发量太小了，不足以
			//初始化数组，这时这个实例就是一个AtomicLong实例，没有区别，第二个判断就是给base赋值为最新的增长量
			//如果cas失败了，那么就进入到下面的判断语句中
			boolean uncontended = true;
			if(as==null || (m = as.length-1)<0 || (a = as[getProbe() & m]) == null || !(uncontended=a.cas(v=a.value,v+x))){
				/**
				 * 四个判断条件，逐步深入
				 * （1）如果数组为空，继续执行判断体内；不为空，看第二个条件
				 * （2）如果数组中元素为0，继续执行判断体内；不少于1个，看第三个条件
				 * （3）getProbe()函数用于获取获取当前线程中变量threadLocalRandomProbe的值，这个值一开始为0，在接下
				 * 来的判断体中会进行初始化，并且当前线程通过分配的Cell元素的cas函数来保证对Cell元素value值更新的原子性。
				 * 这第三个就是为了找到一个数组中的Cell变量，我们暂且看到是0&m,那就是0了，也就是啊as[0]赋值给a，并且如果是空的，则直接
				 * 进入到判断执行体；如果不为空，接着看第四个判断条件
				 * （4）第四个判断条件就是刚才的那个a使用CAS操作来更新值，如果更新失败了就进入到执行体
				 */
				longAccumulate(x,null,uncontended);
			}
		}
	}
	
	final boolean casBase(long cmp,long val) {
		return UNSAFE.compareAndSwapLong(this,BASE,cmp,val);
	}
	
	final void longAccumulate(long x,LongBinaryOperator fn,boolean wasUncontended) {
		//初始化当前线程的变量threadLocalRandomProbe的值
		//代码6
		int h;
		if(h = getProbe() == 0) {
			ThreadLocalRandom.current();//获取一个随机数的实例
			h = getProbe();//初始化
			//这个变量在计算当前线程应该被分配到cells数组的哪一个Cell元素的时候会用到
			wasUncontended = true;//contend争夺，辩称
		}
		
		boolean collide = false;//collide冲突，碰撞
		for(;;) {
			Cell[] as;Cell a;int n;long v;
			if((as=cells) != null && (n=as.length)>0) {//当前线程调用了add方法并且根据当前线程的随机数threadLocalRandomProbe和cells元素的个数计算要访问的Cell元素下
				//下标，然后如果发现对应下标元素的值为null，则新增一个cell元素到cells数组，并且在将其提娜佳到cells数组之前要竞争设置cellsBusy为1
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
		//代码14，这里进行的是cells数组初始化
		/**
		 * 1.cellsBusy是一个标示，成员变量，用来实现自旋锁，状态值只有0和1，当创建Cell元素，扩容Cell数组或者
		 * 初始化数组，使用CAS操作来保证同时只有一个线程可以进行其中之一操作
		 * 2.为0的时候说明cells数组没有被初始化或者扩容，也没有新建Cell元素；为1说明cells数组在被初始化或者扩容，或者当前新建了Cell元素；通过CAS操作来进行0或1状态切换，这里使用了casCellBusy函数
		 * 3.假设当前线程通过CAS设置cellsBusy为1，则当前线程开始初始化操作，那么这个时候其他线程就不能进行扩容了。
		 */
		else if(cellsBusy == 0 && cells == as && casCellsBusy()){
			boolean init = false;
			try {
				//因为在多线程下，所以有必要再次判断一下
				if(cells == as) {
					//初始化数组容量为2
					Cell[] rs = new Cell[2];
					//h&l用来计算当前线程应该访问cell数组的哪个位置
					//也就是使用当前线程的threadLocalRandomProbe变量值和cells数组元素个数-1做与运算
					rs[h&l] = new Cell(x);
					cells = rs;
					//标示数组已经完成初始化
					init = true;
				}
			}finally {
				//最后重置了cellBusys标记，这里没有使用CAS操作，但是却是线程安全的，因为该变量是一个
				//volatile变量，保证了内存可见性，另外扩容后的cells数组里面除了包含复制过来的元素外，还包含其他元素，这些元素的值
				//都是null，
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
