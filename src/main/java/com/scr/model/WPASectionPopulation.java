package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * The persistent class for the works database table.
 * 
 */
@Entity
@Table(name = "wpa_section_population")
@NamedQuery(name = "WPASectionPopulation.findAll", query = "SELECT w FROM WPASectionPopulation w")

public class WPASectionPopulation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "work_phase_activity_id", foreignKey = @ForeignKey(name = "fk_wpa_section_population_work_phase_activity"))
	private WorkPhaseActivity workPhaseActivityId;

	@ManyToOne
	@JoinColumn(name = "work_group_id", foreignKey = @ForeignKey(name = "fk_wpa_section_population_work_group"))
	private WorkGroup workGroupId;

	@Column(name = "Population")
	private String Population;

	public WPASectionPopulation() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public WorkPhaseActivity getWorkPhaseActivityId() {
		return workPhaseActivityId;
	}

	public void setWorkPhaseActivityId(WorkPhaseActivity workPhaseActivityId) {
		this.workPhaseActivityId = workPhaseActivityId;
	}

	public WorkGroup getWorkGroupId() {
		return workGroupId;
	}

	public void setWorkGroupId(WorkGroup workGroupId) {
		this.workGroupId = workGroupId;
	}

	public String getPopulation() {
		return Population;
	}

	public void setPopulation(String population) {
		Population = population;
	}

}
