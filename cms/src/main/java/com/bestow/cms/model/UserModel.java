package com.bestow.cms.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Transient;


@Entity
public class UserModel extends AuditModel {
	
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE)
	private Integer userId;
	
	private String name;
	
	private String password;
	
	private String email;
	
	private String phoneNumber;
	
	private String token;
	
//	@OneToMany(mappedBy = "userModel", cascade = CascadeType.ALL)
	@Transient
	private Set<BlogModel> blogList;

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
	public Set<BlogModel> getBlogList() {
		return blogList;
	}

	public void setBlogList(Set<BlogModel> blogList) {
		this.blogList = blogList;
	}
	
}
