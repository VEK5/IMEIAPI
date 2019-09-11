package com.xinan.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinan.bean.User;

public class IMEIUtil {

	private static String host =null;
	private static String appkey = null;	
	static{
		ResourceBundle resource = ResourceBundle.getBundle("imei");
		appkey = resource.getString("AppKey"); 
		host = resource.getString("host"); 
	}
	
	public static User imeiAPI(String imei) {
		//String host = "https://api.3023data.com";
		//String appkey = "aabdbf27751ff2a093bde9522499ccb0";
		String path = "/imei/imei";
		String method = "GET";

		// 请求头
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("key", appkey);

		Map<String, String> querys = new HashMap<String, String>();
		querys.put("imei", imei);

		try {
			HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
//			System.out.println(response.toString());

			// 获取response的body
			String userdata = EntityUtils.toString(response.getEntity());

			// json数据转化为对象（fastjson）
			JSONObject userJson = JSONObject.parseObject(userdata);
			User user = JSON.toJavaObject(userJson, User.class);
			System.out.println("打印返回JSON数据：" + userdata);
			return user;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
