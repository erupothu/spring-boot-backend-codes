package com.erv.blog.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class StoryLogEntity {
	
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE)
	private Integer storyLogId;
	
	private Integer storyId;
	
	private Integer userId;
	
	private String category;
	
	private String location;
	
	private String device;
	
	private Date visitDate;

	public Integer getStoryLogId() {
		return storyLogId;
	}

	public void setStoryLogId(Integer storyLogId) {
		this.storyLogId = storyLogId;
	}

	public Integer getStoryId() {
		return storyId;
	}

	public void setStoryId(Integer storyId) {
		this.storyId = storyId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
	
	
	

}
