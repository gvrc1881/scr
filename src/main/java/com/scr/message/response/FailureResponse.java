package com.scr.message.response;

import java.sql.Timestamp;



public class FailureResponse {

		private Long id;
		private String subStation;
		private String location;
		private String fromDateTime;
		
		private String typeOfFailure;
		private String occurrence;
		private String place;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getSubStation() {
			return subStation;
		}
		public void setSubStation(String subStation) {
			this.subStation = subStation;
		}
		public String getLocation() {
			return location;
		}
		public void setLocation(String location) {
			this.location = location;
		}
		public String getFromDateTime() {
			return fromDateTime;
		}
		public void setFromDateTime(String fromDateTime) {
			this.fromDateTime = fromDateTime;
		}
		public String getTypeOfFailure() {
			return typeOfFailure;
		}
		public void setTypeOfFailure(String typeOfFailure) {
			this.typeOfFailure = typeOfFailure;
		}
		public String getOccurrence() {
			return occurrence;
		}
		public void setOccurrence(String occurrence) {
			this.occurrence = occurrence;
		}
	
		public String getPlace() {
			return place;
		}
		public void setPlace(String place) {
			this.place = place;
		}


	


}
