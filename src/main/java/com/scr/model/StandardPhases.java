package com.scr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "standardPhases")
@NamedQuery(name = "StandardPhases.findAll", query = "SELECT sp FROM StandardPhases sp")
public class StandardPhases {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	@Column(name = "type_of_work")
	private String typeOfWork;

	private String description;

	private Integer sequence;

	@Column(name = "dependency_to_start")
	private String dependencyToStart;
	
	@Column( nullable = true )
	private Double weightage;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypeOfWork() {
		return typeOfWork;
	}

	public void setTypeOfWork(String typeOfWork) {
		this.typeOfWork = typeOfWork;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getDependencyToStart() {
		return dependencyToStart;
	}

	public void setDependencyToStart(String dependencyToStart) {
		this.dependencyToStart = dependencyToStart;
	}

	public Double getWeightage() {
		return weightage;
	}

	public void setWeightage(Double weightage) {
		this.weightage = weightage;
	}

	@Override
	public String toString() {
		return "StandardPhases [id=" + id + ", name=" + name + ", typeOfWork=" + typeOfWork + ", description="
				+ description + ", sequence=" + sequence + ", dependencyToStart=" + dependencyToStart + ", weightage="
				+ weightage + "]";
	}

}
