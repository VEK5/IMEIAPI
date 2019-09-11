package com.xinan.dao;

import com.xinan.bean.Data;

public interface DataMapper {
	
	Data findUserByImei (Data data);
	
    int insert(Data data);
}