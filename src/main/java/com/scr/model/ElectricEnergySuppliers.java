package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the facility database table.
 * 
 */
@Entity
//

@Table(name = "electric_energy_suppliers" , uniqueConstraints={@UniqueConstraint(name = "old_pk_electric_energy_suppliers_uniq", columnNames ={"id","state_id","code", "description"})})
//
@NamedQuery(name="ElectricEnergySuppliers.findAll", query="SELECT e FROM ElectricEnergySuppliers e")
public class ElectricEnergySuppliers implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
	@Column(name="code")
	private String code;

	@Column(name="description")
	private String description;

	@Column(name="head_quarter")
	private String headQuarter;

	@Column(name="state_id")
	private String stateId;

	@Column(name="signing_authority")
	private String signingAuthority;
	
	@ManyToOne
	@JoinColumn(name = "geographicStateId", foreignKey = @ForeignKey(name = "fk_electricEnergySuppliers_geographicState_id"))
	private GeographicState geographicStateId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHeadQuarter() {
		return headQuarter;
	}

	public void setHeadQuarter(String headQuarter) {
		this.headQuarter = headQuarter;
	}

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getSigningAuthority() {
		return signingAuthority;
	}

	public void setSigningAuthority(String signingAuthority) {
		this.signingAuthority = signingAuthority;
	}

	
}