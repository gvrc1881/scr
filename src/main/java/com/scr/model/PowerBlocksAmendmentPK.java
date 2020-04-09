package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the power_blocks_amendment database table.
 * 
 */
@Embeddable
public class PowerBlocksAmendmentPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="pb_amendment_seq_id")
	private String pbAmendmentSeqId;

	@Column(name="data_div")
	private String dataDiv;

	public PowerBlocksAmendmentPK() {
	}
	public String getPbAmendmentSeqId() {
		return this.pbAmendmentSeqId;
	}
	public void setPbAmendmentSeqId(String pbAmendmentSeqId) {
		this.pbAmendmentSeqId = pbAmendmentSeqId;
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
		if (!(other instanceof PowerBlocksAmendmentPK)) {
			return false;
		}
		PowerBlocksAmendmentPK castOther = (PowerBlocksAmendmentPK)other;
		return 
			this.pbAmendmentSeqId.equals(castOther.pbAmendmentSeqId)
			&& this.dataDiv.equals(castOther.dataDiv);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.pbAmendmentSeqId.hashCode();
		hash = hash * prime + this.dataDiv.hashCode();
		
		return hash;
	}
}