package com.xinan.controller;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xinan.bean.Data;
import com.xinan.bean.ReturnData;
import com.xinan.bean.User;
import com.xinan.service.DataService;
import com.xinan.service.UserService;
import com.xinan.utils.IMEIUtil;

@Controller
public class IMEISController {

	@Autowired
	DataService dataservice;
	@Autowired
	UserService userservic;

	public static int cs = 0;		
	static{
		ResourceBundle resource = ResourceBundle.getBundle("imei");
		cs =Integer.parseInt(resource.getString("cs"));
	}
	
	
	@RequestMapping("/imei.html")
	public String show(@RequestParam(value = "wxid") String wxid, Map<String, Object> map) {

		// 利用微信ID查询所有历史记录
		ReturnData returndata = new ReturnData();
		returndata.setWechatid(wxid);// 将微信ID存入

		// 数据库查询历史记录
		User user = new User();
		user.setWechatid(wxid);
		List<Data> historydata = userservic.findHistoryByPage(user, 1, 10);// 默认加载10条历史记录
		
		// set进返回对象中
		returndata.setDatalist(historydata);

		// 将返回对象转换为JSON形式发给前台
		String jsonString2 = JSON.toJSONString(returndata);
		map.put("returndata", jsonString2);
		map.put("wxid", wxid);
		return "index";
	}

	@RequestMapping("/imeis")
	@ResponseBody
	public ReturnData show(@RequestParam(value = "wxid") String wxid, @RequestParam(value = "imeis") String imeis) {

		// 1.新建对象为返回到前台所用
		ReturnData returndata = new ReturnData();
		returndata.setWechatid(wxid);// 2.将微信ID存入
		returndata.setImei(imeis);// 3.将IMEI存入

		// 2.判空，以及IMEI校验
		if (wxid == null || "".contentEquals(wxid)) {
			returndata.setMessage("微信ID不能为空！请检查URL地址！");
			return returndata;
		} else if (imeis == null || "".equals(imeis)) {
			returndata.setMessage("IMEI不能为空！");
			return returndata;
		}

		// 将imeis拆分
		List<String> result = Arrays.asList(imeis.split(","));

		// ======判断用户当日查询次数[开始]======
		User user = new User();
		user.setWechatid(wxid);
		int selectnum = userservic.countTimes(user);

		//System.out.println("默认次数："+cs);
		if (selectnum >= cs) {
			// 查询历史记录
			List<Data> historydata = userservic.findHistoryByWechatID(user);
			returndata.setDatalist(historydata);
			returndata.setMessage("对不起，您当日查询条数已达上限！");
			returndata.setCode("0");
			return returndata;
		}

		if ((result.size() + selectnum) > cs) {// 查询了一条，但是又查询两条
			// 查询历史记录
			List<Data> historydata = userservic.findHistoryByWechatID(user);
			returndata.setDatalist(historydata);
			int num = 2 - selectnum;
			returndata.setMessage("对不起，您当日查询条数剩余" + num + "条!");
			returndata.setCode("0");
			return returndata;
		}

		// ======判断用户当日查询次数[结束]======

		// 计数
		int msgnum = 0;// 成功条数
		int msgnum2 = 0;// 失败条数
		StringBuilder msgstr = null;// 信息

		// 循环
		if (result != null) {
			// 清零
			msgnum = 0;
			msgnum2 = 0;
			msgstr = new StringBuilder();
			// 循环
			for (String imei : result) {
				ReturnData rd = findimei(imei, wxid);
				if ("0".equals(rd.getCode())) {
					// 成功条数
					msgnum++;
				} else {
					msgnum2++;
					// 错误信息
					msgstr.append("<br/>"+rd.getMessage());
				}
			}
		}
		if (msgnum2 == 0) {
			returndata.setMessage("查询成功！");
		} else {
			returndata.setMessage(msgnum + "条成功," + msgnum2 + "条失败！" + msgstr);
		}

		// 查询历史记录
		List<Data> historydata = userservic.findHistoryByPage(user, 1, 10);
		returndata.setDatalist(historydata);
		return returndata;
	}

	
	@RequestMapping("/imeipage")
	@ResponseBody
	public ReturnData show(@RequestParam(value="wxid")String wxid,@RequestParam(value="page")int page,@RequestParam(value="num",required=false,defaultValue="10")int num) {
		
		ReturnData returndata = new ReturnData();	
		
		User user = new User();
		user.setWechatid(wxid);
		List<Data> datalist = userservic.findHistoryByPage(user,page,num);//获取历史记录
		//如果历史记录不为空
		if(datalist!=null){
			returndata.setDatalist(datalist);
			returndata.setMessage("Success");
		}else{
			returndata.setMessage("Error");
		}	
		return returndata;
	}

	
	
	
	
	public ReturnData findimei(String imei, String wxid) {

		Timestamp createTime = new Timestamp(new Date().getTime());// 当前时间

		ReturnData returndata = new ReturnData();
		returndata.setWechatid(wxid);// 2.微信id
		returndata.setImei(imei);// 3.IMEI

		User user = new User();// 存库用
		user.setWechatid(wxid);
		user.setImei(imei);
		user.setDate(createTime);

		// 从data表查询IMEI是否存在
		Data dt = new Data();
		dt.setImei(imei);
		Data data = dataservice.findUserByImei(dt);// 数据库查询

		if (data != null) {
			System.out.println("已从本地数据库中查到数据！");

			// user表中添加一条历史记录
			user.setCode("0");
			user.setMessage("Success");

			// 根据微信ID和IMEI查询数据（存在则更新时间，不存在则插入数据）
			if (userservic.findHistoryByWxidImei(user) != null) {
				userservic.updatedate(user);// 更新时间
			} else {
				userservic.insert(user);// 调用添加方法
			}

			// 返回
			returndata.setCode("0");
			returndata.setMessage("Success");
		} else {
			System.out.println("本地无数据，正在调用网络接口...");
			User imeiuser = IMEIUtil.imeiAPI(imei);// 调用接口
			//User imeiuser = new User();// 测试专用代码
			//imeiuser.setCode("302311");// 测试专用代码
			imeiuser.setWechatid(wxid);
			imeiuser.setImei(imei);
			imeiuser.setDate(createTime);	
			
			if (imeiuser.getCode().equals("0")) {// 如果返回码为0则代表正常
				imeiuser.setMessage("Success");
				// 调用添加方法
				dataservice.insert(imeiuser.getData());// 将imei数据存入数据库
				userservic.insert(imeiuser);// 添加历史记录

				// 返回
				returndata.setCode("0");
				returndata.setMessage("Success");
			} else {
				String code = imeiuser.getCode();
				returndata.setCode(code);
				if (code.equals("302301")) {
					returndata.setMessage("请求头无密钥信息，请联系管理员");
				} else if (code.equals("302302")) {
					returndata.setMessage("密钥无效，请联系管理员");
				} else if (code.equals("302303")) {
					returndata.setMessage("余额不足，请联系管理员充值");
				} else if (code.equals("302304")) {
					returndata.setMessage("IP白名单限制");
				} else if (code.equals("302305")) {
					returndata.setMessage("接口维护，请稍后重试");
				} else if (code.equals("302311")) {
					returndata.setMessage("IMEI错误:" + imei);
				} else if (code.equals("302312")) {
					returndata.setMessage("IMEI无效：" + imei);
				} else if (code.equals("302314")) {
					returndata.setMessage("其他错误，不扣费");
				} else {
					returndata.setMessage("未知错误");
				}

			}

		}
		return returndata;

	}
}
