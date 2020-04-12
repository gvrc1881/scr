package com.scr.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "major_section" )
public class MajorSections implements Serializable{
	private static final long serialVersionUID = 1L;

	
	@Id
	private BigInteger id;
	
	@Column(name="code")
	private String code;

	
	@Column(name="description")
	private String description;

}
