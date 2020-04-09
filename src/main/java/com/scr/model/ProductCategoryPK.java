package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the product_category database table.
 * 
 */
@Embeddable
public class ProductCategoryPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="product_category_id")
	private String productCategoryId;

	@Column(name="data_div")
	private String dataDiv;

	public ProductCategoryPK() {
	}
	public String getProductCategoryId() {
		return this.productCategoryId;
	}
	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
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
		if (!(other instanceof ProductCategoryPK)) {
			return false;
		}
		ProductCategoryPK castOther = (ProductCategoryPK)other;
		return 
			this.productCategoryId.equals(castOther.productCategoryId)
			&& this.dataDiv.equals(castOther.dataDiv);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.productCategoryId.hashCode();
		hash = hash * prime + this.dataDiv.hashCode();
		
		return hash;
	}
}