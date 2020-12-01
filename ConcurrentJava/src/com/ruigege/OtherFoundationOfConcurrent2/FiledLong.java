package com.ruigege.OtherFoundationOfConcurrent2;

public class FiledLong {
	public volatile long value =0L;
	public long p1,p2,p3,p4,p5,p6;
}


@sun.misc.Contended
class FiledLong2{
	public volatile long value=0L;
}