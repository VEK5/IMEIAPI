package com.xinan.dao;

import java.util.List;

import com.xinan.bean.Data;
import com.xinan.bean.User;

public interface UserMapper {

	//插入一条数据
    int insert(User record);

    //根据微信ID获取所有查询记录
    List<Data> findHistoryByWechatID(User user);
    
    //根据用户的微信ID和IMEI获取记录
    User findHistoryByWxidImei(User user);
    
    //更新用户历史记录时间
    int updatedate(User user);
    
    //用户当日查询次数
    int countTimes(User user);
    
}