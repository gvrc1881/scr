package com.scr.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Embeddable
@Entity
@Table(name = "menu")
public class Menu implements Serializable {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "menu")
	private String menu;
/*	@Column(name = "menu_url")
	private String menuURL;
	@Column(name = "menu_color")
	private String menuColor;
	@Column(name = "menu_icon")
	private Integer menuIcon;*/
	@Column(name = "created_by")
	private Integer createdBy;
	@Column(name = "created_date")
	private Date createdDate;
	@Column(name = "modified_by", nullable = true)
	private Integer modifiedBy;
	@Column(name = "modified_date")
	private Date modifiedDate;
	@Column(name = "status_id")
	private Integer statusId;
	
	@Column(name="subMenu")
	private String subMenu;
	
	/*@ManyToOne
	@JoinColumn(name="permissionId",foreignKey=@ForeignKey(name = "fk_menu_permission_id"))
	private Permissions permissionId;*/

	public Menu() {
		super();
	}

	public Menu(int id, String menu, String menuURL, String menuColor, int menuIcon, int createdBy, Date createdDate,
			int modifiedBy, Date modifiedDate, int statusId) {
		super();
		this.id = id;
		this.menu = menu;
		/*this.menuURL = menuURL;
		this.menuColor = menuColor;
		this.menuIcon = menuIcon;*/
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.statusId = statusId;
	}

	public int getId() {
		return id;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "m_menu_m_submenu", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "menu_id"))
	public void setId(int id) {
		this.id = id;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	/*public String getMenuURL() {
		return menuURL;
	}

	public void setMenuURL(String menuURL) {
		this.menuURL = menuURL;
	}

	public String getMenuColor() {
		return menuColor;
	}

	public void setMenuColor(String menuColor) {
		this.menuColor = menuColor;
	}

	public int getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(int menuIcon) {
		this.menuIcon = menuIcon;
	}*/

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

	public String getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(String subMenu) {
		this.subMenu = subMenu;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", menu=" + menu + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate + ", statusId=" + statusId
				+ ", subMenu=" + subMenu + ", getId()=" + getId() + ", getMenu()=" + getMenu() + ", getCreatedBy()="
				+ getCreatedBy() + ", getCreatedDate()=" + getCreatedDate() + ", getModifiedBy()=" + getModifiedBy()
				+ ", getModifiedDate()=" + getModifiedDate() + ", getStatusId()=" + getStatusId() + ", getSubMenu()="
				+ getSubMenu() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}



}
