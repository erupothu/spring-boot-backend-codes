package com.bestow.cms.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class LayoutModel {
	
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE)
	private Integer layoutId;
	
	private String inputHeading;
	
	@Column(columnDefinition = "TEXT")
	private String texAreaDescription;
	
	@Column(columnDefinition = "TEXT")
	private String descriptionImage;
	
	@ManyToOne
	@JoinColumn(name = "blogModel", referencedColumnName = "blogId")
	private BlogModel blogModel;
	

	public BlogModel getBlogModel() {
		return blogModel;
	}

	public void setBlogModel(BlogModel blogModel) {
		this.blogModel = blogModel;
	}

	public String getInputHeading() {
		return inputHeading;
	}

	public void setInputHeading(String inputHeading) {
		this.inputHeading = inputHeading;
	}

	public String getTexAreaDescription() {
		return texAreaDescription;
	}

	public void setTexAreaDescription(String texAreaDescription) {
		this.texAreaDescription = texAreaDescription;
	}
	
	public String getDescriptionImage() {
		return descriptionImage;
	}

	public void setDescriptionImage(String descriptionImage) {
		this.descriptionImage = descriptionImage;
	}

	public Integer getLayoutId() {
		return layoutId;
	}

	public void setLayoutId(Integer layoutId) {
		this.layoutId = layoutId;
	}
//	
//	@Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof LayoutModel )) return false;
//        return id != null && id.equals(((BlogModel) o).getSpaceId());
//    }
//	
	

}
