package com.scr.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


	@Entity
	@Table(name = "product_facility" , uniqueConstraints={@UniqueConstraint(name = "pk_product_facility", columnNames ={"data_div", "product_id","facility_id"})})
	
	public class ProductFacility implements Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;		

		@Column(name="product_id ")
		private String productId ;
		
		@Column(name="facility_id ")
		private String facilityId ;
		
		@Column(name="minimum_stock")
		private BigDecimal minimumStock;
		
		@Column(name="reorder_quantity")
		private BigDecimal reorderQuantity;
		
		@Column(name="days_to_ship")
		private BigDecimal daysToShip; 

		@Column(name="created_stamp")
		private Timestamp createdStamp;

		@Column(name="created_tx_stamp")
		private Timestamp createdTxStamp;
		
		@Column(name="last_updated_stamp")
		private Timestamp lastUpdatedStamp;

		@Column(name="last_updated_tx_stamp")
		private Timestamp lastUpdatedTxStamp;

		@Column(name="data_div")
		private String dataDiv;		

		@Column(name="replenish_method_enum_id")
		private String replenishMethodEnumId;

		@Column(name="replenish_from_facility_id")
		private String replenishFromFacilityId; 

		@Column(name="last_inventory_count")
		private BigDecimal lastInventoryCount; 
		
		public ProductFacility() {
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getProductId() {
			return productId;
		}

		public void setProductId(String productId) {
			this.productId = productId;
		}

		public String getFacilityId() {
			return facilityId;
		}

		public void setFacilityId(String facilityId) {
			this.facilityId = facilityId;
		}

		public BigDecimal getMinimumStock() {
			return minimumStock;
		}

		public void setMinimumStock(BigDecimal minimumStock) {
			this.minimumStock = minimumStock;
		}

		public BigDecimal getReorderQuantity() {
			return reorderQuantity;
		}

		public void setReorderQuantity(BigDecimal reorderQuantity) {
			this.reorderQuantity = reorderQuantity;
		}

		public BigDecimal getDaysToShip() {
			return daysToShip;
		}

		public void setDaysToShip(BigDecimal daysToShip) {
			this.daysToShip = daysToShip;
		}

		public Timestamp getCreatedStamp() {
			return createdStamp;
		}

		public void setCreatedStamp(Timestamp createdStamp) {
			this.createdStamp = createdStamp;
		}

		public Timestamp getCreatedTxStamp() {
			return createdTxStamp;
		}

		public void setCreatedTxStamp(Timestamp createdTxStamp) {
			this.createdTxStamp = createdTxStamp;
		}

		public Timestamp getLastUpdatedStamp() {
			return lastUpdatedStamp;
		}

		public void setLastUpdatedStamp(Timestamp lastUpdatedStamp) {
			this.lastUpdatedStamp = lastUpdatedStamp;
		}

		public Timestamp getLastUpdatedTxStamp() {
			return lastUpdatedTxStamp;
		}

		public void setLastUpdatedTxStamp(Timestamp lastUpdatedTxStamp) {
			this.lastUpdatedTxStamp = lastUpdatedTxStamp;
		}

		public String getDataDiv() {
			return dataDiv;
		}

		public void setDataDiv(String dataDiv) {
			this.dataDiv = dataDiv;
		}

		public String getReplenishMethodEnumId() {
			return replenishMethodEnumId;
		}

		public void setReplenishMethodEnumId(String replenishMethodEnumId) {
			this.replenishMethodEnumId = replenishMethodEnumId;
		}

		public String getReplenishFromFacilityId() {
			return replenishFromFacilityId;
		}

		public void setReplenishFromFacilityId(String replenishFromFacilityId) {
			this.replenishFromFacilityId = replenishFromFacilityId;
		}

		public BigDecimal getLastInventoryCount() {
			return lastInventoryCount;
		}

		public void setLastInventoryCount(BigDecimal lastInventoryCount) {
			this.lastInventoryCount = lastInventoryCount;
		}

		

	}

		


