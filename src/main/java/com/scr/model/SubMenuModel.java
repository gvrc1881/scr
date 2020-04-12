package com.scr.model;

import java.io.Serializable;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Embeddable
@Entity
@Table(name = "submenu")

public class SubMenuModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "sub_menu")
	private String submenu;
	@Column(name = "sub_menu_url")
	private String submenuURL;
	@Column(name = "orders")
	private int orders;
	@Column(name = "menu_id")
	private int menuId;
	@Column(name = "sub_menu_icon")
	private int submenuIcon;
	@Column(name = "created_by")
	private int createdBy;
	@Column(name = "created_date")
	private Date createdDate;
	@Column(name = "modified_by")
	private int modifiedBy;
	@Column(name = "modified_date")
	private Date modifiedDate;
	@Column(name = "status_id")
	private int statusId;

	public SubMenuModel() {
		super();
	}

	public SubMenuModel(int id, String submenu, String submenuURL, int order, int menuId, int submenuIcon,
			int createdBy, Date createdDate, int modifiedBy, Date modifiedDate, int statusId) {
		super();
		this.id = id;
		this.submenu = submenu;
		this.submenuURL = submenuURL;
		// this.orders = orders;
		this.menuId = menuId;
		this.submenuIcon = submenuIcon;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.statusId = statusId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubmenu() {
		return submenu;
	}

	public void setSubmenu(String submenu) {
		this.submenu = submenu;
	}

	public String getSubmenuURL() {
		return submenuURL;
	}

	public void setSubmenuURL(String submenuURL) {
		this.submenuURL = submenuURL;
	}

	public int getOrders() {
		return orders;
	}

	public void setOrders(int orders) {
		this.orders = orders;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public int getSubmenuIcon() {
		return submenuIcon;
	}

	public void setSubmenuIcon(int submenuIcon) {
		this.submenuIcon = submenuIcon;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	@Override
	public String toString() {
		return "SubMenuModel [id=" + id + ", submenu=" + submenu + ", submenuURL=" + submenuURL + ", orders=" + orders
				+ ", menuId=" + menuId + ", submenuIcon=" + submenuIcon + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate + ", statusId="
				+ statusId + "]";
	}

}
