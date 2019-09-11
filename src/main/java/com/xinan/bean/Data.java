package com.xinan.bean;

import java.sql.Timestamp;

public class Data {
    private String imei;//IMEI

    private String brand;//品牌

    private String model;//型号

    private String manufacturer;//制造商

    private String band;//频段

    private String allocated;//分配机构

    private String snr;//序列号
    
    private  Timestamp date;//时间

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer == null ? null : manufacturer.trim();
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band == null ? null : band.trim();
    }

    public String getAllocated() {
        return allocated;
    }

    public void setAllocated(String allocated) {
        this.allocated = allocated == null ? null : allocated.trim();
    }

    public String getSnr() {
        return snr;
    }

    public void setSnr(String snr) {
        this.snr = snr == null ? null : snr.trim();
    }



	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Data [imei=" + imei + ", brand=" + brand + ", model=" + model + ", manufacturer=" + manufacturer
				+ ", band=" + band + ", allocated=" + allocated + ", snr=" + snr + ", date=" + date + "]";
	}


    
}