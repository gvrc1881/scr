package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the user_login database table.
 * 
 */
@Entity
@Table(name = "user_login" , uniqueConstraints={@UniqueConstraint(name = "old_pk_user_login_uniq", columnNames ={"user_login_id", "data_div"})})
@NamedQuery(name="UserLogin.findAll", query="SELECT u FROM UserLogin u")
public class UserLogin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="current_password")
	private String currentPassword;

	@Column(name="data_div")
	private String dataDiv;

	@Column(name="disabled_date_time")
	private Timestamp disabledDateTime;

	private String enabled;

	@Column(name="external_auth_id")
	private String externalAuthId;

	@Column(name="has_logged_out")
	private String hasLoggedOut;

	@Column(name="is_system")
	private String isSystem;

	@Column(name="last_currency_uom")
	private String lastCurrencyUom;

	@Column(name="last_locale")
	private String lastLocale;

	@Column(name="last_time_zone")
	private String lastTimeZone;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="party_id")
	private String partyId;

	@Column(name="password_hint")
	private String passwordHint;

	@Column(name="require_password_change")
	private String requirePasswordChange;

	@Column(name="successive_failed_logins")
	private BigDecimal successiveFailedLogins;

	@Column(name="user_ldap_dn")
	private String userLdapDn;

	@Column(name="user_login_id")
	private String userLoginId;

	public UserLogin() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCurrentPassword() {
		return this.currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getDataDiv() {
		return this.dataDiv;
	}

	public void setDataDiv(String dataDiv) {
		this.dataDiv = dataDiv;
	}

	public Timestamp getDisabledDateTime() {
		return this.disabledDateTime;
	}

	public void setDisabledDateTime(Timestamp disabledDateTime) {
		this.disabledDateTime = disabledDateTime;
	}

	public String getEnabled() {
		return this.enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getExternalAuthId() {
		return this.externalAuthId;
	}

	public void setExternalAuthId(String externalAuthId) {
		this.externalAuthId = externalAuthId;
	}

	public String getHasLoggedOut() {
		return this.hasLoggedOut;
	}

	public void setHasLoggedOut(String hasLoggedOut) {
		this.hasLoggedOut = hasLoggedOut;
	}

	public String getIsSystem() {
		return this.isSystem;
	}

	public void setIsSystem(String isSystem) {
		this.isSystem = isSystem;
	}

	public String getLastCurrencyUom() {
		return this.lastCurrencyUom;
	}

	public void setLastCurrencyUom(String lastCurrencyUom) {
		this.lastCurrencyUom = lastCurrencyUom;
	}

	public String getLastLocale() {
		return this.lastLocale;
	}

	public void setLastLocale(String lastLocale) {
		this.lastLocale = lastLocale;
	}

	public String getLastTimeZone() {
		return this.lastTimeZone;
	}

	public void setLastTimeZone(String lastTimeZone) {
		this.lastTimeZone = lastTimeZone;
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

	public String getPartyId() {
		return this.partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getPasswordHint() {
		return this.passwordHint;
	}

	public void setPasswordHint(String passwordHint) {
		this.passwordHint = passwordHint;
	}

	public String getRequirePasswordChange() {
		return this.requirePasswordChange;
	}

	public void setRequirePasswordChange(String requirePasswordChange) {
		this.requirePasswordChange = requirePasswordChange;
	}

	public BigDecimal getSuccessiveFailedLogins() {
		return this.successiveFailedLogins;
	}

	public void setSuccessiveFailedLogins(BigDecimal successiveFailedLogins) {
		this.successiveFailedLogins = successiveFailedLogins;
	}

	public String getUserLdapDn() {
		return this.userLdapDn;
	}

	public void setUserLdapDn(String userLdapDn) {
		this.userLdapDn = userLdapDn;
	}

	public String getUserLoginId() {
		return this.userLoginId;
	}

	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

}