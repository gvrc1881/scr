package com.scr.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "drive_report_sub_headings")
@NamedQuery(name = "DriveReportSubHeadings.findAll", query = "SELECT drsh FROM DriveReportSubHeadings drsh")
public class DriveReportSubHeadings implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String subHeading;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubHeading() {
		return subHeading;
	}

	public void setSubHeading(String subHeading) {
		this.subHeading = subHeading;
	}

	@Override
	public String toString() {
		return "DriveReportSubHeadings [id=" + id + ", subHeading=" + subHeading + "]";
	}

}
