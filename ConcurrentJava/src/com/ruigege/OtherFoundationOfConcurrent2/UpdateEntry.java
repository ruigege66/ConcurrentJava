package com.ruigege.OtherFoundationOfConcurrent2;

public class UpdateEntry {

	//使用悲观锁来获取
	EntryObject entry = query("select * from table1 where id =#{id} for update",id);
	//修改记录内容，根据计算修改entry记录的属性
	String name=generatorName(entry);
	entry.setName(name);
	
	//update操作
	
	int count = updateZ("update table1 set name=#{name},age=#{age} where id=#{id}",entry);
	return count;
	
}
