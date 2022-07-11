package com.easternspace.matrimony.model;

import java.io.Serializable;
import lombok.AccessLevel;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the m_location database table.
 * 
 */
@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name="m_location")
@NamedQuery(name="MLocation.findAll", query="SELECT m FROM MLocation m")
public class MLocation extends AuditModel {

	@Id
	private int id;

	private String description;

	private String name;

	@Column(name="parent_entity_id")
	private int parentEntityId;

	public MLocation() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParentEntityId() {
		return this.parentEntityId;
	}

	public void setParentEntityId(int parentEntityId) {
		this.parentEntityId = parentEntityId;
	}

}