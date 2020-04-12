/**
 * 
 */
package com.scr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author vt1056
 *
 */
@Entity
@Table(name="alert_communication_details")
@NamedQuery(name="AlertCommunicationDetails.findAll", query="SELECT acd FROM AlertCommunicationDetails acd")
public class AlertCommunicationDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@Column(name="name")
	private String name;
	@Column(name="description")
	private String description;
}
