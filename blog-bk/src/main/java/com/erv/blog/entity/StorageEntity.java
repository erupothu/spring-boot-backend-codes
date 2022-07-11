package com.erv.blog.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class StorageEntity {
	
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE)
	private Integer assetId;
	
	private Integer userId;
	
	private Integer StoryId;
	
	private Integer tutorialId;
	
	private String url;
	
	private Date date;

	public Integer getAssetId() {
		return assetId;
	}

	public void setAssetId(Integer assetId) {
		this.assetId = assetId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStoryId() {
		return StoryId;
	}

	public void setStoryId(Integer storyId) {
		StoryId = storyId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getTutorialId() {
		return tutorialId;
	}

	public void setTutorialId(Integer tutorialId) {
		this.tutorialId = tutorialId;
	}
	
	
	
	
}
