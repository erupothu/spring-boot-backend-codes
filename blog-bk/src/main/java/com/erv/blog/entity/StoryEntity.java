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

import org.hibernate.annotations.Type;

/**
 * @author harish
 *
 */
@Entity
public class StoryEntity {
	
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE)
	private Integer storyId;
	
	private Integer userId;
	
	private String category;
	
	private String email;
	
	private String title;
	
	@Column(columnDefinition="CLOB NOT NULL")
	@Lob
	private String content;
	
	private String[] tags;;
	
	private Date createdDate;
	
	@Transient
	private List<String> urls;

	public Integer getStoryId() {
		return storyId;
	}

	public void setStoryId(Integer storyId) {
		this.storyId = storyId;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}
	
}
