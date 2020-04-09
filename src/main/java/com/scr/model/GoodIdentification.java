package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the good_identification database table.
 * 
 */
@Entity
// @Table(name="good_identification")

@Table(name = "good_identification" , uniqueConstraints={@UniqueConstraint(name = "old_pk_good_identification_uniq", columnNames ={"product_id", "good_identification_type_id", "data_div"})})
//
@NamedQuery(name="GoodIdentification.findAll", query="SELECT g FROM GoodIdentification g")
public class GoodIdentification implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	@Column(name="good_identification_type_id")
	private String goodIdentificationTypeId;

	@Column(name="id_value")
	private String idValue;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="product_id")
	private String productId;

	public GoodIdentification() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getDataDiv() {
		return this.dataDiv;
	}

	public void setDataDiv(String dataDiv) {
		this.dataDiv = dataDiv;
	}

	public String getGoodIdentificationTypeId() {
		return this.goodIdentificationTypeId;
	}

	public void setGoodIdentificationTypeId(String goodIdentificationTypeId) {
		this.goodIdentificationTypeId = goodIdentificationTypeId;
	}

	public String getIdValue() {
		return this.idValue;
	}

	public void setIdValue(String idValue) {
		this.idValue = idValue;
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

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}