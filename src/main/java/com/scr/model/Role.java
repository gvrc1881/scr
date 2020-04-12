package com.scr.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "roles")
public @Data class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    
    @Column(length = 60)
    private String name;    
	private String roleType;	
	private Integer createdBy;	
	private Date createdDate;	
	private Integer modifiedBy;	
	private Date modifiedDate;	
	private Integer statusId;  
    
    public Role() {}    
}