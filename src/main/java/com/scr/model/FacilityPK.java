package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the facility database table.
 * 
 */
@Embeddable
public class FacilityPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="facility_id")
	private String facilityId;

	@Column(name="data_div")
	private String dataDiv;

	public FacilityPK() {
	}
	public String getFacilityId() {
		return this.facilityId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public String getDataDiv() {
		return this.dataDiv;
	}
	public void setDataDiv(String dataDiv) {
		this.dataDiv = dataDiv;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FacilityPK)) {
			return false;
		}
		FacilityPK castOther = (FacilityPK)other;
		return 
			this.facilityId.equals(castOther.facilityId)
			&& this.dataDiv.equals(castOther.dataDiv);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.facilityId.hashCode();
		hash = hash * prime + this.dataDiv.hashCode();
		
		return hash;
	}
}