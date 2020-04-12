/**
 * 
 */
package com.scr.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * @author vt1056
 *
 */
@Entity(name="entity_dependency")
public class EntityDependency {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;	
	private Integer creation_order;
	private Integer deletion_order;	
	private String table_name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCreation_order() {
		return creation_order;
	}
	public void setCreation_order(Integer creation_order) {
		this.creation_order = creation_order;
	}
	public Integer getDeletion_order() {
		return deletion_order;
	}
	public void setDeletion_order(Integer deletion_order) {
		this.deletion_order = deletion_order;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	
	
	
}
