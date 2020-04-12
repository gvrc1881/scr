package com.scr.model;
import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;



@Entity
@Table(name = "product_make_model_association" )
public class ProductMakeModelAssociation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private BigInteger id;
	
	@Column(name="product_id")
	private BigInteger productId;

	@Column(name="Make_id")
	private BigInteger MakeId;
	
	@Column(name="Model_id")
	private BigInteger ModelId;
	
	@Column(name="description")
	private String description;



	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getProductId() {
		return productId;
	}

	public void setProductId(BigInteger productId) {
		this.productId = productId;
	}

	public BigInteger getMakeId() {
		return MakeId;
	}

	public void setMakeId(BigInteger makeId) {
		MakeId = makeId;
	}

	public BigInteger getModelId() {
		return ModelId;
	}

	public void setModelId(BigInteger modelId) {
		ModelId = modelId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


}






