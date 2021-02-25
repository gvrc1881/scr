package com.scr.model;

import javax.persistence.Column;
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
@Table(name = "inspection_check_list")
@NamedQuery(name = "InspectionCheckList.findAll", query = "SELECT  icl from InspectionCheckList icl")
public class InspectionCheckList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String active;

	private String measureActivityMma;

	@Column(columnDefinition = "TEXT")
	private String measureActivityCode;
	@Column(columnDefinition = "TEXT")
	private String description;

	private String activityPositionId;

	private String displayOrder;

	private String lowerLimit;

	private String upperLimit;

	private String makeCode;

	private String modelCode;

	private String reportColumnHeader;

	private String remark;
	
	private Integer versionNo;
	
	@Column(name="default_no_of_items")
	private Integer defaultNoOfItems;

	@ManyToOne
	@JoinColumn(name = "uom_id", foreignKey = @ForeignKey(name = "fk_ins_check_list_uom"))
	private Uom uomId;
	
	@ManyToOne
	@JoinColumn(name = "test_inspection_id", foreignKey = @ForeignKey(name = "fk_ins_check_list_test_ins"))
	private TestInspection testInspectionId;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getMeasureActivityMma() {
		return measureActivityMma;
	}

	public void setMeasureActivityMma(String measureActivityMma) {
		this.measureActivityMma = measureActivityMma;
	}

	public String getMeasureActivityCode() {
		return measureActivityCode;
	}

	public void setMeasureActivityCode(String measureActivityCode) {
		this.measureActivityCode = measureActivityCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getActivityPositionId() {
		return activityPositionId;
	}

	public void setActivityPositionId(String activityPositionId) {
		this.activityPositionId = activityPositionId;
	}

	public String getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(String lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	public String getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(String upperLimit) {
		this.upperLimit = upperLimit;
	}

	public String getMakeCode() {
		return makeCode;
	}

	public void setMakeCode(String makeCode) {
		this.makeCode = makeCode;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getReportColumnHeader() {
		return reportColumnHeader;
	}

	public void setReportColumnHeader(String reportColumnHeader) {
		this.reportColumnHeader = reportColumnHeader;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Uom getUomId() {
		return uomId;
	}

	public void setUomId(Uom uomId) {
		this.uomId = uomId;
	}

	public TestInspection getTestInspectionId() {
		return testInspectionId;
	}

	public void setTestInspectionId(TestInspection testInspectionId) {
		this.testInspectionId = testInspectionId;
	}

	public Integer getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Integer versionNo) {
		this.versionNo = versionNo;
	}

	public Integer getDefaultNoOfItems() {
		return defaultNoOfItems;
	}

	public void setDefaultNoOfItems(Integer defaultNoOfItems) {
		this.defaultNoOfItems = defaultNoOfItems;
	}

	@Override
	public String toString() {
		return "InspectionCheckList [id=" + id + ", active=" + active + ", measureActivityMma=" + measureActivityMma
				+ ", measureActivityCode=" + measureActivityCode + ", description=" + description
				+ ", activityPositionId=" + activityPositionId + ", displayOrder=" + displayOrder + ", lowerLimit="
				+ lowerLimit + ", upperLimit=" + upperLimit + ", makeCode=" + makeCode + ", modelCode=" + modelCode
				+ ", reportColumnHeader=" + reportColumnHeader + ", remark=" + remark + ", versionNo=" + versionNo
				+ ", defaultNoOfItems=" + defaultNoOfItems + ", uomId=" + uomId + ", testInspectionId="
				+ testInspectionId + "]";
	}

	
}
