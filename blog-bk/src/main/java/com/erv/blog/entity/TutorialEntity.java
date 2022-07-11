package com.erv.blog.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;

@Entity
public class TutorialEntity {

	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE)
	private Integer tutorialId;
	
	private Integer userId;
	
	private String type; // tutorial, interview, notes
	
	private String category; // frontend, backend, database etc...
	
	private String name; // java, angular, typescript, springboot etc...
	
	private String title;
	
	@Column(columnDefinition="CLOB NOT NULL")
	@Lob
	private String content;
	
	private String[] tags;
	
	private Date createdDate;
	
	@Transient
	private List<String> urls;

	public Integer getTutorialId() {
		return tutorialId;
	}

	public void setTutorialId(Integer tutorialId) {
		this.tutorialId = tutorialId;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
