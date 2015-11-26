package com.businesshome.model;

import java.io.Serializable;

public class Policy implements Serializable{

	private String title;
	private String startTime;
	private String city;
	private String url;
	private int zang;
	private String imgurl;
	public Policy() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Policy(String title, String startTime, String city, String url,
			int zang, String imgurl) {
		super();
		this.title = title;
		this.startTime = startTime;
		this.city = city;
		this.url = url;
		this.zang = zang;
		this.imgurl = imgurl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getZang() {
		return zang;
	}
	public void setZang(int zang) {
		this.zang = zang;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	
	
	
}
