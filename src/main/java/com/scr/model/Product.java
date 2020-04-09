package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the product database table.
 * 
 */
@Entity
@Table(name = "product" , uniqueConstraints={@UniqueConstraint(name = "old_pk_product_uniq", columnNames ={"data_div", "product_id"})})
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String comments;

	@Column(name="created_by_user_login")
	private String createdByUserLogin;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	@Column(name="depth_uom_id")
	private String depthUomId;

	private String description;

	@Column(name="diameter_uom_id")
	private String diameterUomId;

	@Column(name="facility_id")
	private String facilityId;

	@Column(name="height_uom_id")
	private String heightUomId;

	@Column(name="is_active")
	private String isActive;

	@Column(name="is_serialized")
	private String isSerialized;

	@Column(name="last_modified_by_user_login")
	private String lastModifiedByUserLogin;

	@Column(name="last_modified_date")
	private Timestamp lastModifiedDate;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="long_description",columnDefinition="TEXT")
	private String longDescription;

	@Column(name="material_classification")
	private String materialClassification;

	@Column(name="pl_no")
	private String plNo;

	@Column(name="primary_product_category_id")
	private String primaryProductCategoryId;

	@Column(name="product_code_type_id")
	private String productCodeTypeId;

	@Column(name="product_depth")
	private BigDecimal productDepth;

	@Column(name="product_diameter")
	private BigDecimal productDiameter;

	@Column(name="product_height")
	private BigDecimal productHeight;

	@Column(name="product_id")
	private String productId;

	@Column(name="product_name")
	private String productName;

	@Column(name="product_type_id")
	private String productTypeId;

	@Column(name="product_weight")
	private BigDecimal productWeight;

	@Column(name="product_width")
	private BigDecimal productWidth;

	@Column(name="quantity_included")
	private BigDecimal quantityIncluded;

	@Column(name="quantity_uom_id")
	private String quantityUomId;

	@Column(name="rly_id")
	private String rlyId;

	@Column(name="trd_div_id")
	private String trdDivId;

	private BigDecimal weight;

	@Column(name="weight_uom_id")
	private String weightUomId;

	@Column(name="width_uom_id")
	private String widthUomId;

	public Product() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCreatedByUserLogin() {
		return this.createdByUserLogin;
	}

	public void setCreatedByUserLogin(String createdByUserLogin) {
		this.createdByUserLogin = createdByUserLogin;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
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

	public String getDepthUomId() {
		return this.depthUomId;
	}

	public void setDepthUomId(String depthUomId) {
		this.depthUomId = depthUomId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDiameterUomId() {
		return this.diameterUomId;
	}

	public void setDiameterUomId(String diameterUomId) {
		this.diameterUomId = diameterUomId;
	}

	public String getFacilityId() {
		return this.facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getHeightUomId() {
		return this.heightUomId;
	}

	public void setHeightUomId(String heightUomId) {
		this.heightUomId = heightUomId;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getIsSerialized() {
		return this.isSerialized;
	}

	public void setIsSerialized(String isSerialized) {
		this.isSerialized = isSerialized;
	}

	public String getLastModifiedByUserLogin() {
		return this.lastModifiedByUserLogin;
	}

	public void setLastModifiedByUserLogin(String lastModifiedByUserLogin) {
		this.lastModifiedByUserLogin = lastModifiedByUserLogin;
	}

	public Timestamp getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
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

	public String getLongDescription() {
		return this.longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public String getMaterialClassification() {
		return this.materialClassification;
	}

	public void setMaterialClassification(String materialClassification) {
		this.materialClassification = materialClassification;
	}

	public String getPlNo() {
		return this.plNo;
	}

	public void setPlNo(String plNo) {
		this.plNo = plNo;
	}

	public String getPrimaryProductCategoryId() {
		return this.primaryProductCategoryId;
	}

	public void setPrimaryProductCategoryId(String primaryProductCategoryId) {
		this.primaryProductCategoryId = primaryProductCategoryId;
	}

	public String getProductCodeTypeId() {
		return this.productCodeTypeId;
	}

	public void setProductCodeTypeId(String productCodeTypeId) {
		this.productCodeTypeId = productCodeTypeId;
	}

	public BigDecimal getProductDepth() {
		return this.productDepth;
	}

	public void setProductDepth(BigDecimal productDepth) {
		this.productDepth = productDepth;
	}

	public BigDecimal getProductDiameter() {
		return this.productDiameter;
	}

	public void setProductDiameter(BigDecimal productDiameter) {
		this.productDiameter = productDiameter;
	}

	public BigDecimal getProductHeight() {
		return this.productHeight;
	}

	public void setProductHeight(BigDecimal productHeight) {
		this.productHeight = productHeight;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductTypeId() {
		return this.productTypeId;
	}

	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}

	public BigDecimal getProductWeight() {
		return this.productWeight;
	}

	public void setProductWeight(BigDecimal productWeight) {
		this.productWeight = productWeight;
	}

	public BigDecimal getProductWidth() {
		return this.productWidth;
	}

	public void setProductWidth(BigDecimal productWidth) {
		this.productWidth = productWidth;
	}

	public BigDecimal getQuantityIncluded() {
		return this.quantityIncluded;
	}

	public void setQuantityIncluded(BigDecimal quantityIncluded) {
		this.quantityIncluded = quantityIncluded;
	}

	public String getQuantityUomId() {
		return this.quantityUomId;
	}

	public void setQuantityUomId(String quantityUomId) {
		this.quantityUomId = quantityUomId;
	}

	public String getRlyId() {
		return this.rlyId;
	}

	public void setRlyId(String rlyId) {
		this.rlyId = rlyId;
	}

	public String getTrdDivId() {
		return this.trdDivId;
	}

	public void setTrdDivId(String trdDivId) {
		this.trdDivId = trdDivId;
	}

	public BigDecimal getWeight() {
		return this.weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public String getWeightUomId() {
		return this.weightUomId;
	}

	public void setWeightUomId(String weightUomId) {
		this.weightUomId = weightUomId;
	}

	public String getWidthUomId() {
		return this.widthUomId;
	}

	public void setWidthUomId(String widthUomId) {
		this.widthUomId = widthUomId;
	}

}
