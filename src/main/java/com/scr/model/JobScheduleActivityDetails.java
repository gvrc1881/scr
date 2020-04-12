/**
 * 
 */
package com.scr.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author venkat
 *
 */
@Entity
@Table(name="job_sch_activity_details")
@NamedQuery(name="JobScheduleActivityDetails.findAll", query="SELECT j FROM JobScheduleActivityDetails j")
public class JobScheduleActivityDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	
	private String active;

	@Column(name="create_update_delete")
	private String createUpdateDelete;

	@Column(name="create_update_delete_order")
	private Integer createUpdateDeleteOrder;

	@Column(name="div_to_stage_filters")
	private String divToStageFilters;

	@Column(name="mixed_entity_unique_cols")
	private String mixedEntityUniqueCols;

	private String periodicity;

	@Column(name="pre_requisite")
	private String preRequisite;

	private BigDecimal sequence;

	@Column(name="tab_name")
	private String tabName;

	@Column(name="table_scope")
	private String tableScope;

	public JobScheduleActivityDetails() {
	}

	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getCreateUpdateDelete() {
		return this.createUpdateDelete;
	}

	public void setCreateUpdateDelete(String createUpdateDelete) {
		this.createUpdateDelete = createUpdateDelete;
	}

	public Integer getCreateUpdateDeleteOrder() {
		return this.createUpdateDeleteOrder;
	}

	public void setCreateUpdateDeleteOrder(Integer createUpdateDeleteOrder) {
		this.createUpdateDeleteOrder = createUpdateDeleteOrder;
	}

	public String getDivToStageFilters() {
		return this.divToStageFilters;
	}

	public void setDivToStageFilters(String divToStageFilters) {
		this.divToStageFilters = divToStageFilters;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMixedEntityUniqueCols() {
		return this.mixedEntityUniqueCols;
	}

	public void setMixedEntityUniqueCols(String mixedEntityUniqueCols) {
		this.mixedEntityUniqueCols = mixedEntityUniqueCols;
	}

	public String getPeriodicity() {
		return this.periodicity;
	}

	public void setPeriodicity(String periodicity) {
		this.periodicity = periodicity;
	}

	public String getPreRequisite() {
		return this.preRequisite;
	}

	public void setPreRequisite(String preRequisite) {
		this.preRequisite = preRequisite;
	}

	public BigDecimal getSequence() {
		return this.sequence;
	}

	public void setSequence(BigDecimal sequence) {
		this.sequence = sequence;
	}

	public String getTabName() {
		return this.tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public String getTableScope() {
		return this.tableScope;
	}

	public void setTableScope(String tableScope) {
		this.tableScope = tableScope;
	}
	
	

	
}
