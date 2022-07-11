package com.erv.blog.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CommentEntity {
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE)
	private Integer commentId;
	
	private Integer storyId;
	
	private Integer userId;
	
	private Integer commentedByUserId;
	
	private String comment;
	
	private Date date;

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
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

	public Integer getCommentedByUserId() {
		return commentedByUserId;
	}

	public void setCommentedByUserId(Integer commentedByUserId) {
		this.commentedByUserId = commentedByUserId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
