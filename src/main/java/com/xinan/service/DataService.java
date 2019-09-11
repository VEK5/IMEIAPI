package com.xinan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinan.bean.Data;
import com.xinan.dao.DataMapper;

@Service
public class DataService {

	@Autowired
	DataMapper datamapper;

	public Data findUserByImei(Data data) {
		return datamapper.findUserByImei(data);
	}

	public int insert(Data data) {
		return datamapper.insert(data);
	}
}
