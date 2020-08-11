package com.scr.model;

import java.io.Serializable;

import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the measure_or_activity_list database table.
 * 
 */
@Entity
@Table(name = "measures" )
public class Measures implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="measure_activity")
	private String measureActivity;

	

	public Measures() {
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getMeasureActivity() {
		return measureActivity;
	}



	public void setMeasureActivity(String measureActivity) {
		this.measureActivity = measureActivity;
	}

	

}
