package com.scr.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
		
	
	@ManyToOne
	@JoinColumn(name = "make_code", foreignKey = @ForeignKey(name = "fk_make_code_make"))
	private Make makeCode;
	
	@ManyToOne
	@JoinColumn(name = "model_code", foreignKey = @ForeignKey(name = "fk_model_code_model"))
	private Model modelCode;

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

	public Make getMakeCode() {
		return makeCode;
	}

	public void setMakeCode(Make makeCode) {
		this.makeCode = makeCode;
	}

	public Model getModelCode() {
		return modelCode;
	}

	public void setModelCode(Model modelCode) {
		this.modelCode = modelCode;
	}

	@Override
	public String toString() {
		return "TestInspection [id=" + id + ", name=" + name + ", description=" + description + ", makeCode=" + makeCode
				+ ", modelCode=" + modelCode + "]";
	}

}
