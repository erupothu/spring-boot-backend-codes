package com.bestow.cms.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * @author harish
 *
 */
@Entity
public class BlogModel extends AuditModel {
	
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE)
	private Integer blogId;
	
	private String blogName;
	
	private String blogTitle;
	
	@ManyToOne
	@JoinColumn(name = "userModel", referencedColumnName = "userId")
	private UserModel userModel;
	
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "blogModel")
	@Transient
	private Set<LayoutModel> blogLayoutList;
	
	
	public UserModel getUserModel() {
		return userModel;
	}


	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}


	public Integer getBlogId() {
		return blogId;
	}


	public void setBlogId(Integer blogId) {
		this.blogId = blogId;
	}


	public String getBlogName() {
		return blogName;
	}

	public void setBlogName(String blogName) {
		this.blogName = blogName;
	}

	public String getBlogTitle() {
		return blogTitle;
	}

	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}

	public Set<LayoutModel> getBlogLayoutList() {
		return blogLayoutList;
	}

	public void setBlogLayoutList(Set<LayoutModel> blogLayoutList) {
		this.blogLayoutList = blogLayoutList;
	}
	
	

//	@Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof BlogModel )) return false;
//        return spaceId != null && spaceId.equals(((BlogModel) o).getSpaceId());
//    }
//	
//	@Override
//    public int hashCode() {
//        return 31;
//    }

}
