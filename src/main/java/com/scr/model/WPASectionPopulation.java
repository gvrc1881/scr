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

	@Column(name = "work_phase_activity_id")
	private String workPhaseActivityId;

	@Column(name = "work_grade_id")
	private String workGradeId;

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

	public String getWorkPhaseActivityId() {
		return workPhaseActivityId;
	}

	public void setWorkPhaseActivityId(String workPhaseActivityId) {
		this.workPhaseActivityId = workPhaseActivityId;
	}

	public String getWorkGradeId() {
		return workGradeId;
	}

	public void setWorkGradeId(String workGradeId) {
		this.workGradeId = workGradeId;
	}

	public String getPopulation() {
		return Population;
	}

	public void setPopulation(String population) {
		Population = population;
	} 

}
