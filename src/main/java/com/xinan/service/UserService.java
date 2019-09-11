package com.xinan.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xinan.bean.Data;
import com.xinan.bean.User;
import com.xinan.dao.UserMapper;

@Service
public class UserService {

	@Autowired
	UserMapper usermapper;

	// 添加
	public int insert(User user) {

		return usermapper.insert(user);

	}

	// 根据微信ID获取所有查询记录
	public List<Data> findHistoryByWechatID(User user) {

		return usermapper.findHistoryByWechatID(user);
	}

	// 根据用户的微信ID和IMEI获取记录
	public User findHistoryByWxidImei(User user) {

		return usermapper.findHistoryByWxidImei(user);
	}

	// 更新历史记录时间
	public int updatedate(User user) {

		return usermapper.updatedate(user);
	}

	// 当日查询次数
	public int countTimes(User user) {
		return usermapper.countTimes(user);
	}

	// 分页查询记录
	public List<Data> findHistoryByPage(User user, int page, int num) {
		Page<Data> pages = PageHelper.startPage(page, num);// 第几页，每页显示几条
		List<Data> userlist = usermapper.findHistoryByWechatID(user);
		//System.out.println("当前页码：" + pages.getPageNum() + "总记录数：" + pages.getTotal() + "每页记录数：" + pages.getPageSize()
		//		+ "总页码：" + pages.getPages());
		if (page > pages.getPages()) {
			return null;
		}
		return userlist;
	}

}
