package com.erv.blog.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SettingsEntity {
	
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE)
	private Integer settingId;
	
	private Integer userId;
	
	private String emailId;
	
	private String facebookId;
	
	private boolean comments;

	public Integer getSettingId() {
		return settingId;
	}

	public void setSettingId(Integer settingId) {
		this.settingId = settingId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public boolean isComments() {
		return comments;
	}

	public void setComments(boolean comments) {
		this.comments = comments;
	}

}
