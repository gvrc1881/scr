package com.scr.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "asset_master_data_form_parameter", uniqueConstraints = {
		@UniqueConstraint(name = "asset_master_data_form_parameter_pkey", columnNames = { "id" }) })
public class AssetMasterDataFormParameter implements Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@Column(name = "asset_type")
		private String assetType ;

		@Column(name = "column_name")
		private String columnName ;

		@Column(name = "display_order")
		private String displayOrder ;

		@Column(name = "data_type")
		private String dataType;

		@Column(name = "data_wizard")
		private String dataWizard;

		@Column(name = "prompt_label")
		private String promptLabel;

		@Column(name = "hint")
		private String hint;

		@Column(name = "short_name")
		private String shortName;

		@Column(name = "active")
		private String active;

		@Column(name = "services")
		private String services;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getAssetType() {
			return assetType;
		}

		public void setAssetType(String assetType) {
			this.assetType = assetType;
		}

		public String getColumnName() {
			return columnName;
		}

		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}

		public String getDisplayOrder() {
			return displayOrder;
		}

		public void setDisplayOrder(String displayOrder) {
			this.displayOrder = displayOrder;
		}

		public String getDataType() {
			return dataType;
		}

		public void setDataType(String dataType) {
			this.dataType = dataType;
		}

		public String getDataWizard() {
			return dataWizard;
		}

		public void setDataWizard(String dataWizard) {
			this.dataWizard = dataWizard;
		}

		public String getPromptLabel() {
			return promptLabel;
		}

		public void setPromptLabel(String promptLabel) {
			this.promptLabel = promptLabel;
		}

		public String getHint() {
			return hint;
		}

		public void setHint(String hint) {
			this.hint = hint;
		}

		public String getShortName() {
			return shortName;
		}

		public void setShortName(String shortName) {
			this.shortName = shortName;
		}

		public String getActive() {
			return active;
		}

		public void setActive(String active) {
			this.active = active;
		}

		public String getServices() {
			return services;
		}

		public void setServices(String services) {
			this.services = services;
		}

		
}
