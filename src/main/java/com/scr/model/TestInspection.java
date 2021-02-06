package com.scr.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "test_inspection")
@NamedQuery(name = "TestInspection.findAll", query = "SELECT ti FROM TestInspection ti")
public class TestInspection implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String description;
	
	private String checkPoint2Description;
	
	private Double makeCode;
	
	private Double modelCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCheckPoint2Description() {
		return checkPoint2Description;
	}

	public void setCheckPoint2Description(String checkPoint2Description) {
		this.checkPoint2Description = checkPoint2Description;
	}

	public Double getMakeCode() {
		return makeCode;
	}

	public void setMakeCode(Double makeCode) {
		this.makeCode = makeCode;
	}

	public Double getModelCode() {
		return modelCode;
	}

	public void setModelCode(Double modelCode) {
		this.modelCode = modelCode;
	}

	@Override
	public String toString() {
		return "TestInspection [id=" + id + ", name=" + name + ", description=" + description
				+ ", checkPoint2Description=" + checkPoint2Description + ", makeCode=" + makeCode + ", modelCode="
				+ modelCode + "]";
	}

}
