package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the precautionary_measures_master database table.
 * 
 */
@Embeddable
public class PrecautionaryMeasuresMasterPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="precautionary_measure")
	private String precautionaryMeasure;

	@Column(name="data_div")
	private String dataDiv;

	public PrecautionaryMeasuresMasterPK() {
	}
	public String getPrecautionaryMeasure() {
		return this.precautionaryMeasure;
	}
	public void setPrecautionaryMeasure(String precautionaryMeasure) {
		this.precautionaryMeasure = precautionaryMeasure;
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
		if (!(other instanceof PrecautionaryMeasuresMasterPK)) {
			return false;
		}
		PrecautionaryMeasuresMasterPK castOther = (PrecautionaryMeasuresMasterPK)other;
		return 
			this.precautionaryMeasure.equals(castOther.precautionaryMeasure)
			&& this.dataDiv.equals(castOther.dataDiv);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.precautionaryMeasure.hashCode();
		hash = hash * prime + this.dataDiv.hashCode();
		
		return hash;
	}
}