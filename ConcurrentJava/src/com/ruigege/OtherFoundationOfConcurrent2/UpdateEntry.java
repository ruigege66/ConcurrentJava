package com.ruigege.OtherFoundationOfConcurrent2;

public class UpdateEntry {

	//ʹ�ñ���������ȡ
	EntryObject entry = query("select * from table1 where id =#{id} for update",id);
	//�޸ļ�¼���ݣ����ݼ����޸�entry��¼������
	String name=generatorName(entry);
	entry.setName(name);
	
	//update����
	
	int count = updateZ("update table1 set name=#{name},age=#{age} where id=#{id}",entry);
	return count;
	
}
