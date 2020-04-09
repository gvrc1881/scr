package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the product database table.
 * 
 */
@Embeddable
public class ProductPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="product_id")
	private String productId;

	@Column(name="data_div")
	private String dataDiv;

	public ProductPK() {
	}
	public String getProductId() {
		return this.productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
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
		if (!(other instanceof ProductPK)) {
			return false;
		}
		ProductPK castOther = (ProductPK)other;
		return 
			this.productId.equals(castOther.productId)
			&& this.dataDiv.equals(castOther.dataDiv);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.productId.hashCode();
		hash = hash * prime + this.dataDiv.hashCode();
		
		return hash;
	}
}