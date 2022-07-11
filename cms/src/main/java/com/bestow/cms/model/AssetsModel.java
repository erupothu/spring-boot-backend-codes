package com.bestow.cms.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AssetsModel {

	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE)
	private Integer assetId;
	
	private String assetType;
	
	private String title;
	
	private String urlData;
	
	private String filename;
	
	private Long fileSize;
	
	private Date fileModifiedDate;
	
	private String url;

	public Integer getAssetId() {
		return assetId;
	}

	public void setAssetId(Integer assetId) {
		this.assetId = assetId;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrlData() {
		return urlData;
	}

	public void setUrlData(String urlData) {
		this.urlData = urlData;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Date getFileModifiedDate() {
		return fileModifiedDate;
	}

	public void setFileModifiedDate(Date fileModifiedDate) {
		this.fileModifiedDate = fileModifiedDate;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Assets [assetId=" + assetId + ", assetType=" + assetType + ", title=" + title + ", urlData=" + urlData
				+ ", filename=" + filename + ", fileSize=" + fileSize + ", fileModifiedDate=" + fileModifiedDate
				+ ", url=" + url + "]";
	}

	
	
	
	
	
}
