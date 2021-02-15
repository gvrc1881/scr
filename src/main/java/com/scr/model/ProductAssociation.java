package com.scr.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "product_association")
@NamedQuery(name="ProductAssociation.findAll", query="SELECT p FROM ProductAssociation p")
public class ProductAssociation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_product_id_product"))
	private Product productId;
	
	@ManyToOne
	@JoinColumn(name = "product_id_to", foreignKey = @ForeignKey(name = "fk_product_id_to_product"))
	private Product productIdTo;
	
	@ManyToOne
	@JoinColumn(name = "product_assoc_type_id", foreignKey = @ForeignKey(name = "fk_product_assoc_type_id"))
	private Product productAssocTypeId;
	
	@Temporal(TemporalType.DATE)
	@Column(name="from_date")
	private Date fromDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="thru_date")
	private Date thruDate;
	
	@Column(name="sequence_num")
	private Integer sequenceNum;
	
	private String reason;
	
	@Column(name="quantity")
	private BigDecimal quantity;
	
	private Double scrapFactor;

	
	private String instruction;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Product getProductId() {
		return productId;
	}


	public void setProductId(Product productId) {
		this.productId = productId;
	}


	public Product getProductIdTo() {
		return productIdTo;
	}


	public void setProductIdTo(Product productIdTo) {
		this.productIdTo = productIdTo;
	}


	public Product getProductAssocTypeId() {
		return productAssocTypeId;
	}


	public void setProductAssocTypeId(Product productAssocTypeId) {
		this.productAssocTypeId = productAssocTypeId;
	}


	public Date getFromDate() {
		return fromDate;
	}


	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}


	public Date getThruDate() {
		return thruDate;
	}


	public void setThruDate(Date thruDate) {
		this.thruDate = thruDate;
	}


	public Integer getSequenceNum() {
		return sequenceNum;
	}


	public void setSequenceNum(Integer sequenceNum) {
		this.sequenceNum = sequenceNum;
	}


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public BigDecimal getQuantity() {
		return quantity;
	}


	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}


	public Double getScrapFactor() {
		return scrapFactor;
	}


	public void setScrapFactor(Double scrapFactor) {
		this.scrapFactor = scrapFactor;
	}


	public String getInstruction() {
		return instruction;
	}


	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}


	@Override
	public String toString() {
		return "ProductAssociation [id=" + id + ", productId=" + productId + ", productIdTo=" + productIdTo
				+ ", productAssocTypeId=" + productAssocTypeId + ", fromDate=" + fromDate + ", thruDate=" + thruDate
				+ ", sequenceNum=" + sequenceNum + ", reason=" + reason + ", quantity=" + quantity + ", scrapFactor="
				+ scrapFactor + ", instruction=" + instruction + "]";
	}

	
	
}
