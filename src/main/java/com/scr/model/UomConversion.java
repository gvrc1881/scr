package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the uom_conversion database table.
 * 
 */
@Entity
@Table(name = "uom_conversion" , uniqueConstraints={@UniqueConstraint(name = "old_pk_uom_conversion_uniq", columnNames ={"uom_id_to", "uom_id"})})
@NamedQuery(name="UomConversion.findAll", query="SELECT u FROM UomConversion u")
public class UomConversion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="conversion_factor")
	private double conversionFactor;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="custom_method_id")
	private String customMethodId;

	@Column(name="decimal_scale")
	private BigDecimal decimalScale;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="rounding_mode")
	private String roundingMode;

	@Column(name="uom_id")
	private String uomId;

	@Column(name="uom_id_to")
	private String uomIdTo;

	public UomConversion() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getConversionFactor() {
		return this.conversionFactor;
	}

	public void setConversionFactor(double conversionFactor) {
		this.conversionFactor = conversionFactor;
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

	public String getCustomMethodId() {
		return this.customMethodId;
	}

	public void setCustomMethodId(String customMethodId) {
		this.customMethodId = customMethodId;
	}

	public BigDecimal getDecimalScale() {
		return this.decimalScale;
	}

	public void setDecimalScale(BigDecimal decimalScale) {
		this.decimalScale = decimalScale;
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

	public String getRoundingMode() {
		return this.roundingMode;
	}

	public void setRoundingMode(String roundingMode) {
		this.roundingMode = roundingMode;
	}

	public String getUomId() {
		return this.uomId;
	}

	public void setUomId(String uomId) {
		this.uomId = uomId;
	}

	public String getUomIdTo() {
		return this.uomIdTo;
	}

	public void setUomIdTo(String uomIdTo) {
		this.uomIdTo = uomIdTo;
	}

}