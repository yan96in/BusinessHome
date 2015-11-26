package com.businesshome.model;

import java.io.Serializable;

public class News implements Serializable{

	private String title;
	private String url;
	private int zang;
	private String comment;
	private String imgAddr;
	//private int id;
	private String tag;
	public News() {
		// TODO Auto-generated constructor stub
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getImgAddr() {
		return imgAddr;
	}
	public void setImgAddr(String imgAddr) {
		this.imgAddr = imgAddr;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	
	
}
