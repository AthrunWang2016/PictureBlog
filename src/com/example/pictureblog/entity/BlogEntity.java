package com.example.pictureblog.entity;

import java.io.Serializable;

public class BlogEntity implements Serializable {

	private long date;
	private int pictures;
	private String description;
	
	public BlogEntity() {
	}

	public BlogEntity(long date, int pictures, String description) {
		this.date = date;
		this.pictures = pictures;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public int getPictures() {
		return pictures;
	}

	public void setPictures(int pictures) {
		this.pictures = pictures;
	}
	
}
