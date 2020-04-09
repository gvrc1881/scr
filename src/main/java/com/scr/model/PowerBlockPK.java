package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the power_blocks database table.
 * 
 */
@Embeddable
public class PowerBlockPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="pb_operation_seq_id")
	private String pbOperationSeqId;

	@Column(name="data_div")
	private String dataDiv;

	public PowerBlockPK() {
	}
	public String getPbOperationSeqId() {
		return this.pbOperationSeqId;
	}
	public void setPbOperationSeqId(String pbOperationSeqId) {
		this.pbOperationSeqId = pbOperationSeqId;
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
		if (!(other instanceof PowerBlockPK)) {
			return false;
		}
		PowerBlockPK castOther = (PowerBlockPK)other;
		return 
			this.pbOperationSeqId.equals(castOther.pbOperationSeqId)
			&& this.dataDiv.equals(castOther.dataDiv);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.pbOperationSeqId.hashCode();
		hash = hash * prime + this.dataDiv.hashCode();
		
		return hash;
	}
}