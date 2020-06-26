package com.scr.app.dto;

import java.util.List;
import java.util.Map;

public class FpAppMasterDto {

	public FpAppMasterDto() {
		// TODO Auto-generated constructor stub
	}

	private String previousTimestamp;
	private String currentTimestamp;
	private String appName;
	private String imeiNo;
	private boolean imeiAuth;
	private String message;
	private String phoneNum;

	// server to app

	private ResponseUserLoginDto createdResponseUserLoginDto;
	private ResponseUserLoginDto updatedResponseUserLoginDto;

	private ResponseFacilityDto createdResponseFacilityDto;
	private ResponseFacilityDto updatedResponseFacilityDto;

	private ResponseProductDto createdResponseProductDto;
	private ResponseProductDto updatedResponseProductDto;

	private ResponseInspectionTypeDto createdResponseInspectionTypeDto;
	private ResponseInspectionTypeDto updatedResponseInspectionTypeDto;

	private ResponseObservationCategoriesDto createdObservationCategoriesDto;
	private ResponseObservationCategoriesDto updatedObservationCategoriesDto;

	private ResponseObservationsCheckListDto createdObservationsCheckListDto;
	private ResponseObservationsCheckListDto updatedObservationsCheckListDto;

	private ResponseFootPatrollingSectionsDto createdFootPatrollingSectionsDto;
	private ResponseFootPatrollingSectionsDto updatedFootPatrollingSectionsDto;

	private ResponseFunctionalLocationHierarchyDto createdFunctionalLocationHierarchyDto;
	private ResponseFunctionalLocationHierarchyDto updatedFunctionalLocationHierarchyDto;

	private ResponseOheLocationDto createdResponseOheLocationDto;
	private ResponseOheLocationDto updatedResponseOheLocationDto;
	
	// APP TO SERVER

	private ResponseFootPatrollingInspectionDto appToServerCreatedFootPatrollingInspectionDto;
	private ResponseFootPatrollingInspectionDto serverToAppCreatedFootPatrollingInspectionDto;
	private ResponseFootPatrollingInspectionDto serverToAppUpdatedFootPatrollingInspectionDto;
	

	private Map<String,String> serverToAppFootPatrollingInspectionMap;

	private ResponseObservationsDto appToServerCreatedResponseObservationsDto;
	private ResponseObservationsDto serverToAppCreatedResponseObservationsDto;
	private ResponseObservationsDto serverToAppupdatedResponseObservationsDto;
	
	private Map<String, String> serverToAppObservationMap;
	
	private ResponseCompliancesDto createdResponseCompliancesDto;
	private ResponseCompliancesDto updatedResponseCompliancesDto;
	
	private ResponseCompliancesDto appToServerCreatedResponseCompliancesDto;
	
	private Map<String,String> serverToAppCompliancesMap;
	
	private List<String> reportNames;
	
	private ResponseFpMovementDto appToServerCreatedResponseFpMovementDto;

	// GETTERS and SETTERS for SERVER to APP

	public String getPreviousTimestamp() {
		return previousTimestamp;
	}

	public void setPreviousTimestamp(String previousTimestamp) {
		this.previousTimestamp = previousTimestamp;
	}

	public String getCurrentTimestamp() {
		return currentTimestamp;
	}

	public void setCurrentTimestamp(String currentTimestamp) {
		this.currentTimestamp = currentTimestamp;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getImeiNo() {
		return imeiNo;
	}

	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}

	public boolean getImeiAuth() {
		return imeiAuth;
	}

	public void setImeiAuth(boolean imeiAuth) {
		this.imeiAuth = imeiAuth;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public ResponseUserLoginDto getCreatedResponseUserLoginDto() {
		return createdResponseUserLoginDto;
	}

	public void setCreatedResponseUserLoginDto(ResponseUserLoginDto createdResponseUserLoginDto) {
		this.createdResponseUserLoginDto = createdResponseUserLoginDto;
	}

	public ResponseUserLoginDto getUpdatedResponseUserLoginDto() {
		return updatedResponseUserLoginDto;
	}

	public void setUpdatedResponseUserLoginDto(ResponseUserLoginDto updatedResponseUserLoginDto) {
		this.updatedResponseUserLoginDto = updatedResponseUserLoginDto;
	}

	public ResponseFacilityDto getCreatedResponseFacilityDto() {
		return createdResponseFacilityDto;
	}

	public void setCreatedResponseFacilityDto(ResponseFacilityDto createdResponseFacilityDto) {
		this.createdResponseFacilityDto = createdResponseFacilityDto;
	}

	public ResponseFacilityDto getUpdatedResponseFacilityDto() {
		return updatedResponseFacilityDto;
	}

	public void setUpdatedResponseFacilityDto(ResponseFacilityDto updatedResponseFacilityDto) {
		this.updatedResponseFacilityDto = updatedResponseFacilityDto;
	}

	public ResponseProductDto getCreatedResponseProductDto() {
		return createdResponseProductDto;
	}

	public void setCreatedResponseProductDto(ResponseProductDto createdResponseProductDto) {
		this.createdResponseProductDto = createdResponseProductDto;
	}

	public ResponseProductDto getUpdatedResponseProductDto() {
		return updatedResponseProductDto;
	}

	public void setUpdatedResponseProductDto(ResponseProductDto updatedResponseProductDto) {
		this.updatedResponseProductDto = updatedResponseProductDto;
	}

	public ResponseInspectionTypeDto getCreatedResponseInspectionTypeDto() {
		return createdResponseInspectionTypeDto;
	}

	public ResponseObservationsCheckListDto getCreatedObservationsCheckListDto() {
		return createdObservationsCheckListDto;
	}

	public ResponseFunctionalLocationHierarchyDto getCreatedFunctionalLocationHierarchyDto() {
		return createdFunctionalLocationHierarchyDto;
	}

	public void setCreatedFunctionalLocationHierarchyDto(
			ResponseFunctionalLocationHierarchyDto createdFunctionalLocationHierarchyDto) {
		this.createdFunctionalLocationHierarchyDto = createdFunctionalLocationHierarchyDto;
	}

	public ResponseFunctionalLocationHierarchyDto getUpdatedFunctionalLocationHierarchyDto() {
		return updatedFunctionalLocationHierarchyDto;
	}

	public void setUpdatedFunctionalLocationHierarchyDto(
			ResponseFunctionalLocationHierarchyDto updatedFunctionalLocationHierarchyDto) {
		this.updatedFunctionalLocationHierarchyDto = updatedFunctionalLocationHierarchyDto;
	}

	public ResponseFootPatrollingSectionsDto getCreatedFootPatrollingSectionsDto() {
		return createdFootPatrollingSectionsDto;
	}

	public void setCreatedFootPatrollingSectionsDto(
			ResponseFootPatrollingSectionsDto createdFootPatrollingSectionsDto) {
		this.createdFootPatrollingSectionsDto = createdFootPatrollingSectionsDto;
	}

	public ResponseFootPatrollingSectionsDto getUpdatedFootPatrollingSectionsDto() {
		return updatedFootPatrollingSectionsDto;
	}

	public void setUpdatedFootPatrollingSectionsDto(
			ResponseFootPatrollingSectionsDto updatedFootPatrollingSectionsDto) {
		this.updatedFootPatrollingSectionsDto = updatedFootPatrollingSectionsDto;
	}

	public void setCreatedObservationsCheckListDto(ResponseObservationsCheckListDto createdObservationsCheckListDto) {
		this.createdObservationsCheckListDto = createdObservationsCheckListDto;
	}

	public ResponseObservationsCheckListDto getUpdatedObservationsCheckListDto() {
		return updatedObservationsCheckListDto;
	}

	public void setUpdatedObservationsCheckListDto(ResponseObservationsCheckListDto updatedObservationsCheckListDto) {
		this.updatedObservationsCheckListDto = updatedObservationsCheckListDto;
	}

	public ResponseObservationCategoriesDto getCreatedObservationCategoriesDto() {
		return createdObservationCategoriesDto;
	}

	public void setCreatedObservationCategoriesDto(ResponseObservationCategoriesDto createdObservationCategoriesDto) {
		this.createdObservationCategoriesDto = createdObservationCategoriesDto;
	}

	public ResponseObservationCategoriesDto getUpdatedObservationCategoriesDto() {
		return updatedObservationCategoriesDto;
	}

	public void setUpdatedObservationCategoriesDto(ResponseObservationCategoriesDto updatedObservationCategoriesDto) {
		this.updatedObservationCategoriesDto = updatedObservationCategoriesDto;
	}

	public void setCreatedResponseInspectionTypeDto(ResponseInspectionTypeDto createdResponseInspectionTypeDto) {
		this.createdResponseInspectionTypeDto = createdResponseInspectionTypeDto;
	}

	public ResponseInspectionTypeDto getUpdatedResponseInspectionTypeDto() {
		return updatedResponseInspectionTypeDto;
	}

	public void setUpdatedResponseInspectionTypeDto(ResponseInspectionTypeDto updatedResponseInspectionTypeDto) {
		this.updatedResponseInspectionTypeDto = updatedResponseInspectionTypeDto;
	}

	public ResponseOheLocationDto getCreatedResponseOheLocationDto() {
		return createdResponseOheLocationDto;
	}

	public void setCreatedResponseOheLocationDto(ResponseOheLocationDto createdResponseOheLocationDto) {
		this.createdResponseOheLocationDto = createdResponseOheLocationDto;
	}

	public ResponseOheLocationDto getUpdatedResponseOheLocationDto() {
		return updatedResponseOheLocationDto;
	}

	public void setUpdatedResponseOheLocationDto(ResponseOheLocationDto updatedResponseOheLocationDto) {
		this.updatedResponseOheLocationDto = updatedResponseOheLocationDto;
	}

	public ResponseFootPatrollingInspectionDto getAppToServerCreatedFootPatrollingInspectionDto() {
		return appToServerCreatedFootPatrollingInspectionDto;
	}

	public void setAppToServerCreatedFootPatrollingInspectionDto(
			ResponseFootPatrollingInspectionDto appToServerCreatedFootPatrollingInspectionDto) {
		this.appToServerCreatedFootPatrollingInspectionDto = appToServerCreatedFootPatrollingInspectionDto;
	}

	public ResponseFootPatrollingInspectionDto getServerToAppCreatedFootPatrollingInspectionDto() {
		return serverToAppCreatedFootPatrollingInspectionDto;
	}

	public void setServerToAppCreatedFootPatrollingInspectionDto(
			ResponseFootPatrollingInspectionDto serverToAppCreatedFootPatrollingInspectionDto) {
		this.serverToAppCreatedFootPatrollingInspectionDto = serverToAppCreatedFootPatrollingInspectionDto;
	}

	public ResponseFootPatrollingInspectionDto getServerToAppUpdatedFootPatrollingInspectionDto() {
		return serverToAppUpdatedFootPatrollingInspectionDto;
	}

	public void setServerToAppUpdatedFootPatrollingInspectionDto(
			ResponseFootPatrollingInspectionDto serverToAppUpdatedFootPatrollingInspectionDto) {
		this.serverToAppUpdatedFootPatrollingInspectionDto = serverToAppUpdatedFootPatrollingInspectionDto;
	}

	public Map<String, String> getServerToAppFootPatrollingInspectionMap() {
		return serverToAppFootPatrollingInspectionMap;
	}

	public void setServerToAppFootPatrollingInspectionMap(Map<String, String> serverToAppFootPatrollingInspectionMap) {
		this.serverToAppFootPatrollingInspectionMap = serverToAppFootPatrollingInspectionMap;
	}

	public ResponseObservationsDto getAppToServerCreatedResponseObservationsDto() {
		return appToServerCreatedResponseObservationsDto;
	}

	public void setAppToServerCreatedResponseObservationsDto(
			ResponseObservationsDto appToServerCreatedResponseObservationsDto) {
		this.appToServerCreatedResponseObservationsDto = appToServerCreatedResponseObservationsDto;
	}

	public ResponseObservationsDto getServerToAppCreatedResponseObservationsDto() {
		return serverToAppCreatedResponseObservationsDto;
	}

	public void setServerToAppCreatedResponseObservationsDto(
			ResponseObservationsDto serverToAppCreatedResponseObservationsDto) {
		this.serverToAppCreatedResponseObservationsDto = serverToAppCreatedResponseObservationsDto;
	}

	public ResponseObservationsDto getServerToAppupdatedResponseObservationsDto() {
		return serverToAppupdatedResponseObservationsDto;
	}

	public void setServerToAppupdatedResponseObservationsDto(
			ResponseObservationsDto serverToAppupdatedResponseObservationsDto) {
		this.serverToAppupdatedResponseObservationsDto = serverToAppupdatedResponseObservationsDto;
	}

	public Map<String, String> getServerToAppObservationMap() {
		return serverToAppObservationMap;
	}

	public void setServerToAppObservationMap(Map<String, String> serverToAppObservationMap) {
		this.serverToAppObservationMap = serverToAppObservationMap;
	}

	public ResponseCompliancesDto getCreatedResponseCompliancesDto() {
		return createdResponseCompliancesDto;
	}

	public void setCreatedResponseCompliancesDto(ResponseCompliancesDto createdResponseCompliancesDto) {
		this.createdResponseCompliancesDto = createdResponseCompliancesDto;
	}

	public ResponseCompliancesDto getUpdatedResponseCompliancesDto() {
		return updatedResponseCompliancesDto;
	}

	public void setUpdatedResponseCompliancesDto(ResponseCompliancesDto updatedResponseCompliancesDto) {
		this.updatedResponseCompliancesDto = updatedResponseCompliancesDto;
	}

	public ResponseCompliancesDto getAppToServerCreatedResponseCompliancesDto() {
		return appToServerCreatedResponseCompliancesDto;
	}

	public void setAppToServerCreatedResponseCompliancesDto(
			ResponseCompliancesDto appToServerCreatedResponseCompliancesDto) {
		this.appToServerCreatedResponseCompliancesDto = appToServerCreatedResponseCompliancesDto;
	}

	public Map<String, String> getServerToAppCompliancesMap() {
		return serverToAppCompliancesMap;
	}

	public void setServerToAppCompliancesMap(Map<String, String> serverToAppCompliancesMap) {
		this.serverToAppCompliancesMap = serverToAppCompliancesMap;
	}

	public ResponseFpMovementDto getAppToServerCreatedResponseFpMovementDto() {
		return appToServerCreatedResponseFpMovementDto;
	}

	public void setAppToServerCreatedResponseFpMovementDto(ResponseFpMovementDto appToServerCreatedResponseFpMovementDto) {
		this.appToServerCreatedResponseFpMovementDto = appToServerCreatedResponseFpMovementDto;
	}

	public List<String> getReportNames() {
		return reportNames;
	}

	public void setReportNames(List<String> reportNames) {
		this.reportNames = reportNames;
	}

}
