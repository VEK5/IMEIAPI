package com.xinan.bean;

import java.sql.Timestamp;

public class User {
	private int id;
	
    private String wechatid;

    private String imei;

    private String code;

    private String message;

	private Timestamp date;
	
    private Data data;
    
    
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getWechatid() {
        return wechatid;
    }

    public void setWechatid(String wechatid) {
        this.wechatid = wechatid == null ? null : wechatid.trim();
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }



	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", wechatid=" + wechatid + ", imei=" + imei + ", code=" + code + ", message="
				+ message + ", date=" + date + ", data=" + data + "]";
	}


    
}