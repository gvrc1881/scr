package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the pb_switch_control database table.
 * 
 */
@Embeddable
public class PbSwitchControlPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="seq_id")
	private String seqId;

	@Column(name="data_div")
	private String dataDiv;

	public PbSwitchControlPK() {
	}
	public String getSeqId() {
		return this.seqId;
	}
	public void setSeqId(String seqId) {
		this.seqId = seqId;
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
		if (!(other instanceof PbSwitchControlPK)) {
			return false;
		}
		PbSwitchControlPK castOther = (PbSwitchControlPK)other;
		return 
			this.seqId.equals(castOther.seqId)
			&& this.dataDiv.equals(castOther.dataDiv);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.seqId.hashCode();
		hash = hash * prime + this.dataDiv.hashCode();
		
		return hash;
	}
}