package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the work_effort database table.
 * 
 */
@Entity
@Table(name = "work_effort" , uniqueConstraints={@UniqueConstraint(name = "old_pk_work_effort_uniq", columnNames ={"work_effort_id", "data_div"})})
@NamedQuery(name="WorkEffort.findAll", query="SELECT w FROM WorkEffort w")
public class WorkEffort implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="accommodation_map_id")
	private String accommodationMapId;

	@Column(name="accommodation_spot_id")
	private String accommodationSpotId;

	@Column(name="actual_completion_date")
	private Timestamp actualCompletionDate;

	@Column(name="actual_milli_seconds")
	private double actualMilliSeconds;

	@Column(name="actual_setup_millis")
	private double actualSetupMillis;

	@Column(name="actual_start_date")
	private Timestamp actualStartDate;

	@Column(name="created_by_user_login")
	private String createdByUserLogin;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="current_status_id")
	private String currentStatusId;

	@Column(name="data_div")
	private String dataDiv;

	private String description;

	@Column(name="estimate_calc_method")
	private String estimateCalcMethod;

	@Column(name="estimated_completion_date")
	private Timestamp estimatedCompletionDate;

	@Column(name="estimated_milli_seconds")
	private double estimatedMilliSeconds;

	@Column(name="estimated_setup_millis")
	private double estimatedSetupMillis;

	@Column(name="estimated_start_date")
	private Timestamp estimatedStartDate;

	@Column(name="facility_id")
	private String facilityId;

	@Column(name="fixed_asset_id")
	private String fixedAssetId;

	@Column(name="info_url")
	private String infoUrl;

	@Column(name="last_modified_by_user_login")
	private String lastModifiedByUserLogin;

	@Column(name="last_modified_date")
	private Timestamp lastModifiedDate;

	@Column(name="last_status_update")
	private Timestamp lastStatusUpdate;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="location_desc")
	private String locationDesc;

	@Column(name="money_uom_id")
	private String moneyUomId;

	@Column(name="note_id")
	private String noteId;

	@Column(name="percent_complete")
	private BigDecimal percentComplete;

	private BigDecimal priority;

	@Column(name="quantity_produced")
	private BigDecimal quantityProduced;

	@Column(name="quantity_rejected")
	private BigDecimal quantityRejected;

	@Column(name="quantity_to_produce")
	private BigDecimal quantityToProduce;

	@Column(name="recurrence_info_id")
	private String recurrenceInfoId;

	@Column(name="reserv_nth_p_p_perc")
	private BigDecimal reservNthPPPerc;

	@Column(name="reserv_persons")
	private BigDecimal reservPersons;

	@Column(name="reserv2nd_p_p_perc")
	private BigDecimal reserv2ndPPPerc;

	@Column(name="revision_number")
	private BigDecimal revisionNumber;

	@Column(name="runtime_data_id")
	private String runtimeDataId;

	@Column(name="scope_enum_id")
	private String scopeEnumId;

	@Column(name="send_notification_email")
	private String sendNotificationEmail;

	@Column(name="sequence_num")
	private BigDecimal sequenceNum;

	@Column(name="service_loader_name")
	private String serviceLoaderName;

	private BigDecimal severity;

	@Column(name="show_as_enum_id")
	private String showAsEnumId;

	@Column(name="source_reference_id")
	private String sourceReferenceId;

	@Column(name="special_terms")
	private String specialTerms;

	@Column(name="temp_expr_id")
	private String tempExprId;

	@Column(name="time_transparency")
	private BigDecimal timeTransparency;

	@Column(name="total_milli_seconds_allowed")
	private double totalMilliSecondsAllowed;

	@Column(name="total_money_allowed")
	private BigDecimal totalMoneyAllowed;

	@Column(name="universal_id")
	private String universalId;

	@Column(name="work_effort_id")
	private String workEffortId;

	@Column(name="work_effort_name")
	private String workEffortName;

	@Column(name="work_effort_parent_id")
	private String workEffortParentId;

	@Column(name="work_effort_purpose_type_id")
	private String workEffortPurposeTypeId;

	@Column(name="work_effort_type_id")
	private String workEffortTypeId;

	public WorkEffort() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccommodationMapId() {
		return this.accommodationMapId;
	}

	public void setAccommodationMapId(String accommodationMapId) {
		this.accommodationMapId = accommodationMapId;
	}

	public String getAccommodationSpotId() {
		return this.accommodationSpotId;
	}

	public void setAccommodationSpotId(String accommodationSpotId) {
		this.accommodationSpotId = accommodationSpotId;
	}

	public Timestamp getActualCompletionDate() {
		return this.actualCompletionDate;
	}

	public void setActualCompletionDate(Timestamp actualCompletionDate) {
		this.actualCompletionDate = actualCompletionDate;
	}

	public double getActualMilliSeconds() {
		return this.actualMilliSeconds;
	}

	public void setActualMilliSeconds(double actualMilliSeconds) {
		this.actualMilliSeconds = actualMilliSeconds;
	}

	public double getActualSetupMillis() {
		return this.actualSetupMillis;
	}

	public void setActualSetupMillis(double actualSetupMillis) {
		this.actualSetupMillis = actualSetupMillis;
	}

	public Timestamp getActualStartDate() {
		return this.actualStartDate;
	}

	public void setActualStartDate(Timestamp actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public String getCreatedByUserLogin() {
		return this.createdByUserLogin;
	}

	public void setCreatedByUserLogin(String createdByUserLogin) {
		this.createdByUserLogin = createdByUserLogin;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getCreatedStamp() {
		return this.createdStamp;
	}

	public void setCreatedStamp(Timestamp createdStamp) {
		this.createdStamp = createdStamp;
	}

	public Timestamp getCreatedTxStamp() {
		return this.createdTxStamp;
	}

	public void setCreatedTxStamp(Timestamp createdTxStamp) {
		this.createdTxStamp = createdTxStamp;
	}

	public String getCurrentStatusId() {
		return this.currentStatusId;
	}

	public void setCurrentStatusId(String currentStatusId) {
		this.currentStatusId = currentStatusId;
	}

	public String getDataDiv() {
		return this.dataDiv;
	}

	public void setDataDiv(String dataDiv) {
		this.dataDiv = dataDiv;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEstimateCalcMethod() {
		return this.estimateCalcMethod;
	}

	public void setEstimateCalcMethod(String estimateCalcMethod) {
		this.estimateCalcMethod = estimateCalcMethod;
	}

	public Timestamp getEstimatedCompletionDate() {
		return this.estimatedCompletionDate;
	}

	public void setEstimatedCompletionDate(Timestamp estimatedCompletionDate) {
		this.estimatedCompletionDate = estimatedCompletionDate;
	}

	public double getEstimatedMilliSeconds() {
		return this.estimatedMilliSeconds;
	}

	public void setEstimatedMilliSeconds(double estimatedMilliSeconds) {
		this.estimatedMilliSeconds = estimatedMilliSeconds;
	}

	public double getEstimatedSetupMillis() {
		return this.estimatedSetupMillis;
	}

	public void setEstimatedSetupMillis(double estimatedSetupMillis) {
		this.estimatedSetupMillis = estimatedSetupMillis;
	}

	public Timestamp getEstimatedStartDate() {
		return this.estimatedStartDate;
	}

	public void setEstimatedStartDate(Timestamp estimatedStartDate) {
		this.estimatedStartDate = estimatedStartDate;
	}

	public String getFacilityId() {
		return this.facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getFixedAssetId() {
		return this.fixedAssetId;
	}

	public void setFixedAssetId(String fixedAssetId) {
		this.fixedAssetId = fixedAssetId;
	}

	public String getInfoUrl() {
		return this.infoUrl;
	}

	public void setInfoUrl(String infoUrl) {
		this.infoUrl = infoUrl;
	}

	public String getLastModifiedByUserLogin() {
		return this.lastModifiedByUserLogin;
	}

	public void setLastModifiedByUserLogin(String lastModifiedByUserLogin) {
		this.lastModifiedByUserLogin = lastModifiedByUserLogin;
	}

	public Timestamp getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Timestamp getLastStatusUpdate() {
		return this.lastStatusUpdate;
	}

	public void setLastStatusUpdate(Timestamp lastStatusUpdate) {
		this.lastStatusUpdate = lastStatusUpdate;
	}

	public Timestamp getLastUpdatedStamp() {
		return this.lastUpdatedStamp;
	}

	public void setLastUpdatedStamp(Timestamp lastUpdatedStamp) {
		this.lastUpdatedStamp = lastUpdatedStamp;
	}

	public Timestamp getLastUpdatedTxStamp() {
		return this.lastUpdatedTxStamp;
	}

	public void setLastUpdatedTxStamp(Timestamp lastUpdatedTxStamp) {
		this.lastUpdatedTxStamp = lastUpdatedTxStamp;
	}

	public String getLocationDesc() {
		return this.locationDesc;
	}

	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}

	public String getMoneyUomId() {
		return this.moneyUomId;
	}

	public void setMoneyUomId(String moneyUomId) {
		this.moneyUomId = moneyUomId;
	}

	public String getNoteId() {
		return this.noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	public BigDecimal getPercentComplete() {
		return this.percentComplete;
	}

	public void setPercentComplete(BigDecimal percentComplete) {
		this.percentComplete = percentComplete;
	}

	public BigDecimal getPriority() {
		return this.priority;
	}

	public void setPriority(BigDecimal priority) {
		this.priority = priority;
	}

	public BigDecimal getQuantityProduced() {
		return this.quantityProduced;
	}

	public void setQuantityProduced(BigDecimal quantityProduced) {
		this.quantityProduced = quantityProduced;
	}

	public BigDecimal getQuantityRejected() {
		return this.quantityRejected;
	}

	public void setQuantityRejected(BigDecimal quantityRejected) {
		this.quantityRejected = quantityRejected;
	}

	public BigDecimal getQuantityToProduce() {
		return this.quantityToProduce;
	}

	public void setQuantityToProduce(BigDecimal quantityToProduce) {
		this.quantityToProduce = quantityToProduce;
	}

	public String getRecurrenceInfoId() {
		return this.recurrenceInfoId;
	}

	public void setRecurrenceInfoId(String recurrenceInfoId) {
		this.recurrenceInfoId = recurrenceInfoId;
	}

	public BigDecimal getReservNthPPPerc() {
		return this.reservNthPPPerc;
	}

	public void setReservNthPPPerc(BigDecimal reservNthPPPerc) {
		this.reservNthPPPerc = reservNthPPPerc;
	}

	public BigDecimal getReservPersons() {
		return this.reservPersons;
	}

	public void setReservPersons(BigDecimal reservPersons) {
		this.reservPersons = reservPersons;
	}

	public BigDecimal getReserv2ndPPPerc() {
		return this.reserv2ndPPPerc;
	}

	public void setReserv2ndPPPerc(BigDecimal reserv2ndPPPerc) {
		this.reserv2ndPPPerc = reserv2ndPPPerc;
	}

	public BigDecimal getRevisionNumber() {
		return this.revisionNumber;
	}

	public void setRevisionNumber(BigDecimal revisionNumber) {
		this.revisionNumber = revisionNumber;
	}

	public String getRuntimeDataId() {
		return this.runtimeDataId;
	}

	public void setRuntimeDataId(String runtimeDataId) {
		this.runtimeDataId = runtimeDataId;
	}

	public String getScopeEnumId() {
		return this.scopeEnumId;
	}

	public void setScopeEnumId(String scopeEnumId) {
		this.scopeEnumId = scopeEnumId;
	}

	public String getSendNotificationEmail() {
		return this.sendNotificationEmail;
	}

	public void setSendNotificationEmail(String sendNotificationEmail) {
		this.sendNotificationEmail = sendNotificationEmail;
	}

	public BigDecimal getSequenceNum() {
		return this.sequenceNum;
	}

	public void setSequenceNum(BigDecimal sequenceNum) {
		this.sequenceNum = sequenceNum;
	}

	public String getServiceLoaderName() {
		return this.serviceLoaderName;
	}

	public void setServiceLoaderName(String serviceLoaderName) {
		this.serviceLoaderName = serviceLoaderName;
	}

	public BigDecimal getSeverity() {
		return this.severity;
	}

	public void setSeverity(BigDecimal severity) {
		this.severity = severity;
	}

	public String getShowAsEnumId() {
		return this.showAsEnumId;
	}

	public void setShowAsEnumId(String showAsEnumId) {
		this.showAsEnumId = showAsEnumId;
	}

	public String getSourceReferenceId() {
		return this.sourceReferenceId;
	}

	public void setSourceReferenceId(String sourceReferenceId) {
		this.sourceReferenceId = sourceReferenceId;
	}

	public String getSpecialTerms() {
		return this.specialTerms;
	}

	public void setSpecialTerms(String specialTerms) {
		this.specialTerms = specialTerms;
	}

	public String getTempExprId() {
		return this.tempExprId;
	}

	public void setTempExprId(String tempExprId) {
		this.tempExprId = tempExprId;
	}

	public BigDecimal getTimeTransparency() {
		return this.timeTransparency;
	}

	public void setTimeTransparency(BigDecimal timeTransparency) {
		this.timeTransparency = timeTransparency;
	}

	public double getTotalMilliSecondsAllowed() {
		return this.totalMilliSecondsAllowed;
	}

	public void setTotalMilliSecondsAllowed(double totalMilliSecondsAllowed) {
		this.totalMilliSecondsAllowed = totalMilliSecondsAllowed;
	}

	public BigDecimal getTotalMoneyAllowed() {
		return this.totalMoneyAllowed;
	}

	public void setTotalMoneyAllowed(BigDecimal totalMoneyAllowed) {
		this.totalMoneyAllowed = totalMoneyAllowed;
	}

	public String getUniversalId() {
		return this.universalId;
	}

	public void setUniversalId(String universalId) {
		this.universalId = universalId;
	}

	public String getWorkEffortId() {
		return this.workEffortId;
	}

	public void setWorkEffortId(String workEffortId) {
		this.workEffortId = workEffortId;
	}

	public String getWorkEffortName() {
		return this.workEffortName;
	}

	public void setWorkEffortName(String workEffortName) {
		this.workEffortName = workEffortName;
	}

	public String getWorkEffortParentId() {
		return this.workEffortParentId;
	}

	public void setWorkEffortParentId(String workEffortParentId) {
		this.workEffortParentId = workEffortParentId;
	}

	public String getWorkEffortPurposeTypeId() {
		return this.workEffortPurposeTypeId;
	}

	public void setWorkEffortPurposeTypeId(String workEffortPurposeTypeId) {
		this.workEffortPurposeTypeId = workEffortPurposeTypeId;
	}

	public String getWorkEffortTypeId() {
		return this.workEffortTypeId;
	}

	public void setWorkEffortTypeId(String workEffortTypeId) {
		this.workEffortTypeId = workEffortTypeId;
	}

}