package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the failures database table.
 * 
 */
@Embeddable
public class FailurePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="failure_seq_id")
	private String failureSeqId;

	@Column(name="data_div")
	private String dataDiv;

	public FailurePK() {
	}
	public String getFailureSeqId() {
		return this.failureSeqId;
	}
	public void setFailureSeqId(String failureSeqId) {
		this.failureSeqId = failureSeqId;
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
		if (!(other instanceof FailurePK)) {
			return false;
		}
		FailurePK castOther = (FailurePK)other;
		return 
			this.failureSeqId.equals(castOther.failureSeqId)
			&& this.dataDiv.equals(castOther.dataDiv);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.failureSeqId.hashCode();
		hash = hash * prime + this.dataDiv.hashCode();
		
		return hash;
	}
}