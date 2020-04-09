package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the product_category_member database table.
 * 
 */
@Embeddable
public class ProductCategoryMemberPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="product_category_id")
	private String productCategoryId;

	@Column(name="product_id")
	private String productId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="from_date")
	private java.util.Date fromDate;

	@Column(name="data_div")
	private String dataDiv;

	public ProductCategoryMemberPK() {
	}
	public String getProductCategoryId() {
		return this.productCategoryId;
	}
	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}
	public String getProductId() {
		return this.productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public java.util.Date getFromDate() {
		return this.fromDate;
	}
	public void setFromDate(java.util.Date fromDate) {
		this.fromDate = fromDate;
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
		if (!(other instanceof ProductCategoryMemberPK)) {
			return false;
		}
		ProductCategoryMemberPK castOther = (ProductCategoryMemberPK)other;
		return 
			this.productCategoryId.equals(castOther.productCategoryId)
			&& this.productId.equals(castOther.productId)
			&& this.fromDate.equals(castOther.fromDate)
			&& this.dataDiv.equals(castOther.dataDiv);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.productCategoryId.hashCode();
		hash = hash * prime + this.productId.hashCode();
		hash = hash * prime + this.fromDate.hashCode();
		hash = hash * prime + this.dataDiv.hashCode();
		
		return hash;
	}
}