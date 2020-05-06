package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the facility database table.
 * 
 */
@Entity
//

@Table(name = "geographic_state" , uniqueConstraints={@UniqueConstraint(name = "old_pk_geographic_state_uniq", columnNames ={"id","code", "description"})})
//
@NamedQuery(name="GeographicState.findAll", query="SELECT g FROM GeographicState g")
public class GeographicState implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
	@Column(name="code")
	private String code;

	@Column(name="description")
	private String description;

	@Column(name="state_ut")
	private String stateUt;

	@Column(name="country")
	private String country;

	@Column(name="capital")
	private String capital;

	@Column(name="founded_on")
	private String foundedOn;

	@Column(name="depot_type")
	private String depotType;

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

	public String getStateUt() {
		return stateUt;
	}

	public void setStateUt(String stateUt) {
		this.stateUt = stateUt;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getFoundedOn() {
		return foundedOn;
	}

	public void setFoundedOn(String foundedOn) {
		this.foundedOn = foundedOn;
	}

	public String getDepotType() {
		return depotType;
	}

	public void setDepotType(String depotType) {
		this.depotType = depotType;
	}

}