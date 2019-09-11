package com.xinan.bean;

import java.util.List;

public class ReturnData {

	private int id;//1.id
	private String wechatid;//2.微信id
	private String imei;
	private String code;//4.返回码
	private String message;//5.返回信息
	private List<Data> datalist;//6.历史记录

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWechatid() {
		return wechatid;
	}

	public void setWechatid(String wechatid) {
		this.wechatid = wechatid;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Data> getDatalist() {
		return datalist;
	}

	public void setDatalist(List<Data> datalist) {
		this.datalist = datalist;
	}

	@Override
	public String toString() {
		return "ReturnData [id=" + id + ", wechatid=" + wechatid + ", imei=" + imei + ", code=" + code + ", message="
				+ message + ", datalist=" + datalist + "]";
	}

}
