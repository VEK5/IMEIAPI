package com.xinan.test;

import java.sql.Timestamp;
import java.util.Date;

public class Test {

	public static void main(String[] args) {
		Timestamp createTime = new Timestamp(new Date().getTime());
		System.out.println(createTime.toString());
	}

}
