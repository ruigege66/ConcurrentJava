package com.ruigege.OtherFoundationOfConcurrent2;

public class updateEntry3 {

	boolean result = false;
	int retryNum = 5;
	while(retryNum>0) {
		//使用乐观锁获取记录
		EntryObject entry = query("select * from table1 where id=#{id}",id);
		String name = generatorName(entry);
		entry.setName(name);
		
		//update操作
		int count = update("update table1 set name=#{name},age=#{age},version=${version}+1 where id=#{id} and version =#{version}",entry);
		//返回的行如果不是0的话说明更新成功了，那么即刻跳出循环
		if(count == 1) {
			result = true;
			break;
		}
		retryNum--;
		
	}
	return result;
	
	
}
