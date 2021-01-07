package com.scr.message.request;

public class AssetMasterDataSearchRequest {
	private String adeeSection;
	private String assetId;
	private String assetType;
	private String elementarySection;
	private String facilityId;
	private Double kilometer;
	private String locationPosition;
	private String majorSection;
	private String section;
	private String type;
	private Integer from;
	private Integer to;
	
	
	public String getAdeeSection() {
		return adeeSection;
	}
	public void setAdeeSection(String adeeSection) {
		this.adeeSection = adeeSection;
	}
	public String getAssetId() {
		return assetId;
	}
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	public String getAssetType() {
		return assetType;
	}
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
	public String getElementarySection() {
		return elementarySection;
	}
	public void setElementarySection(String elementarySection) {
		this.elementarySection = elementarySection;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public Double getKilometer() {
		return kilometer;
	}
	public void setKilometer(Double kilometer) {
		this.kilometer = kilometer;
	}
	public String getLocationPosition() {
		return locationPosition;
	}
	public void setLocationPosition(String locationPosition) {
		this.locationPosition = locationPosition;
	}
	public String getMajorSection() {
		return majorSection;
	}
	public void setMajorSection(String majorSection) {
		this.majorSection = majorSection;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getFrom() {
		return from;
	}
	public void setFrom(Integer from) {
		this.from = from;
	}
	public Integer getTo() {
		return to;
	}
	public void setTo(Integer to) {
		this.to = to;
	}
	@Override
	public String toString() {
		return "AssetMasterDataSearchRequest [adeeSection=" + adeeSection + ", assetId=" + assetId + ", assetType="
				+ assetType + ", elementarySection=" + elementarySection + ", facilityId=" + facilityId + ", kilometer="
				+ kilometer + ", locationPosition=" + locationPosition + ", majorSection=" + majorSection + ", section="
				+ section + ", type=" + type + ", from=" + from + ", to=" + to + "]";
	}
	
	
}
