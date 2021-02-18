/**
 * 
 */  
package com.scr.message.request;

import java.util.Date;
import com.scr.model.AssetsScheduleHistory;
import com.scr.model.ElementarySections;
import com.scr.model.Facility;
import com.scr.model.ObservationCategory;
import com.scr.model.ObservationsCheckList;
import com.scr.model.PbSwitchControl;
import com.scr.model.Works;

//import java.util.ArrayList;

/**
 * @author winfocus
 *
 */
public class ReportRequest {
	
	private String outputData;
	private String reportId;
	private String zone;
	private Facility facilityName;
    private Facility facilityId;
	private String reportHeader;
	private String division;
	private String subDivision;
	private ObservationCategory department;
	private Facility facility;
	private Date toDate;
	private String productId;
	private Date fromDate;
	private Date failureFromDate;
	private Date failureToDate;
	private ObservationsCheckList observationCategory;
	private AssetsScheduleHistory assetType;
	private AssetsScheduleHistory scheduleCode;
	private AssetsScheduleHistory assetId;
	private String scheduleDate;
	private String year;
	private String activityType;
	private String pbSwitchType;
	private PbSwitchControl pbExtentCode;
	private ElementarySections elementarySectionCode;
	private String fromkm;
	private String tokm;
	private Date Date;
	private String materialItem;
	private String tpcBoard;
	private String formatType;
	private String group;
	private String section;
	private String agency;
	private Works workName;
	private String WpaName;
	private String driveName;
	private Long checkPointsDepot;
	private String equipmentno;
	private String tempDiff;

	
	public ObservationsCheckList getObservationCategory() {
		return observationCategory;
	}

	public void setObservationCategory(ObservationsCheckList observationCategory) {
		this.observationCategory = observationCategory;
	}

	public Date getFailureFromDate() {
		return failureFromDate;
	}

	public void setFailureFromDate(Date failureFromDate) {
		this.failureFromDate = failureFromDate;
	}

	public Date getFailureToDate() {
		return failureToDate;
	}

	public void setFailureToDate(Date failureToDate) {
		this.failureToDate = failureToDate;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	private String permission;
	
	public String getOutputData() {
		return outputData;
	}

	public void setOutputData(String outputData) {
		this.outputData = outputData;
		
		
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Works getWorkName() {
		return workName;
	}

	public void setWorkName(Works workName) {
		this.workName = workName;
	}

	

	public String getWpaName() {
		return WpaName;
	}

	public void setWpaName(String wpaName) {
		WpaName = wpaName;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportName(String reportId) {
		this.reportId = reportId;
	}

	public String getReportHeader() {
		return reportHeader;
	}

	public void setReportHeader(String reportHeader) {
		this.reportHeader = reportHeader;
	}


	public String getPermission() {
		return permission;
	}

	

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public ObservationCategory getDepartment() {
		return department;
	}

	public void setDepartment(ObservationCategory department) {
		this.department = department;
	}

	public Facility getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(Facility facilityId) {
		this.facilityId = facilityId;
	}

	public Facility getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(Facility facilityName) {
		this.facilityName = facilityName;
	}

	

	public AssetsScheduleHistory getScheduleCode() {
		return scheduleCode;
	}

	public void setScheduleCode(AssetsScheduleHistory scheduleCode) {
		this.scheduleCode = scheduleCode;
	}

	

	public AssetsScheduleHistory getAssetId() {
		return assetId;
	}

	public void setAssetId(AssetsScheduleHistory assetId) {
		this.assetId = assetId;
	}

	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getPbSwitchType() {
		return pbSwitchType;
	}

	public void setPbSwitchType(String pbSwitchType) {
		this.pbSwitchType = pbSwitchType;
	}

	public PbSwitchControl getPbExtentCode() {
		return pbExtentCode;
	}

	public void setPbExtentCode(PbSwitchControl pbExtentCode) {
		this.pbExtentCode = pbExtentCode;
	}

	public ElementarySections getElementarySectionCode() {
		return elementarySectionCode;
	}

	public void setElementarySectionCode(ElementarySections elementarySectionCode) {
		this.elementarySectionCode = elementarySectionCode;
	}

	public String getFromkm() {
		return fromkm;
	}

	public void setFromkm(String fromkm) {
		this.fromkm = fromkm;
	}

	public String getTokm() {
		return tokm;
	}

	public void setTokm(String tokm) {
		this.tokm = tokm;
	}

	public AssetsScheduleHistory getAssetType() {
		return assetType;
	}

	public void setAssetType(AssetsScheduleHistory assetType) {
		this.assetType = assetType;
	}

	public String getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	

	public String getSubDivision() {
		return subDivision;
	}

	public void setSubDivision(String subDivision) {
		this.subDivision = subDivision;
	}

	public Date getDate() {
		return Date;
	}

	public void setDate(Date date) {
		Date = date;
	}

	public String getMaterialItem() {
		return materialItem;
	}

	public void setMaterialItem(String materialItem) {
		this.materialItem = materialItem;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getTpcBoard() {
		return tpcBoard;
	}

	public void setTpcBoard(String tpcBoard) {
		this.tpcBoard = tpcBoard;
	}

	public String getFormatType() {
		return formatType;
	}

	public void setFormatType(String formatType) {
		this.formatType = formatType;
	}

	public String getDriveName() {
		return driveName;
	}

	public void setDriveName(String driveName) {
		this.driveName = driveName;
	}

	public Long getCheckPointsDepot() {
		return checkPointsDepot;
	}

	public void setCheckPointsDepot(Long checkPointsDepot) {
		this.checkPointsDepot = checkPointsDepot;
	}

	public String getEquipmentno() {
		return equipmentno;
	}

	public void setEquipmentno(String equipmentno) {
		this.equipmentno = equipmentno;
	}

	public String getTempDiff() {
		return tempDiff;
	}

	public void setTempDiff(String tempDiff) {
		this.tempDiff = tempDiff;
	}

	
	
	
}