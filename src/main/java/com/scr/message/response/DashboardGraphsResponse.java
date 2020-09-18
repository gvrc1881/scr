package com.scr.message.response;

public class DashboardGraphsResponse {
	
	private String zone;
	private String division;
	private String subDivision;
	private Long facilityId;
	private String depotName;
	private String materialDesc;
	private Long receivedQty;
	private Long consumedQty;
	private Long QtyNetPeriod;
	private String uom;
	private Double qoh;
	private String header;
	
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
	public String getSubDivision() {
		return subDivision;
	}
	public void setSubDivision(String subDivision) {
		this.subDivision = subDivision;
	}
	public Long getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(Long facilityId) {
		this.facilityId = facilityId;
	}
	public String getDepotName() {
		return depotName;
	}
	public void setDepotName(String depotName) {
		this.depotName = depotName;
	}
	public String getMaterialDesc() {
		return materialDesc;
	}
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
	public Long getReceivedQty() {
		return receivedQty;
	}
	public void setReceivedQty(Long receivedQty) {
		this.receivedQty = receivedQty;
	}
	public Long getConsumedQty() {
		return consumedQty;
	}
	public void setConsumedQty(Long consumedQty) {
		this.consumedQty = consumedQty;
	}
	public Long getQtyNetPeriod() {
		return QtyNetPeriod;
	}
	public void setQtyNetPeriod(Long qtyNetPeriod) {
		QtyNetPeriod = qtyNetPeriod;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public Double getQoh() {
		return qoh;
	}
	public void setQoh(Double qoh) {
		this.qoh = qoh;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	
	
}
