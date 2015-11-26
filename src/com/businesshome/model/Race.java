package com.businesshome.model;

import java.io.Serializable;

public class Race implements Serializable{
	
	private String title;
	private String imgUrl;
	private String url;
	private int zang;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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
	public Race(String title, String imgUrl, String url, int zang) {
		super();
		this.title = title;
		this.imgUrl = imgUrl;
		this.url = url;
		this.zang = zang;
	}
	public Race() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
