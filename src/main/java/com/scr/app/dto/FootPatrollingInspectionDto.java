package com.scr.app.dto;

public class FootPatrollingInspectionDto {

        public FootPatrollingInspectionDto(){
            //TODO:auto generated constructor stub
        }

        String seqId;
        String deviceId;
        String deviceSeqId;
        String inspectionType;
        String startTime;
        String stopTime;
        String inspectionBy;
        String section ;
        String facilityId;
        String km;
        String location;


        public String getSeqId() {
            return seqId;
        }
        public void setSeqId(String seqId) {
            this.seqId = seqId;
        }

        public String getDeviceId() {
            return deviceId;
        }
        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getDeviceSeqId() {
            return deviceSeqId;
        }
        public void setDeviceSeqId(String deviceSeqId) {
            this.deviceSeqId = deviceSeqId;
        }

        public String getInspectionType() {
            return inspectionType;
        }
        public void setInspectionType(String inspectionType) {
            this.inspectionType = inspectionType;
        }

        public String getStartTime() {
            return startTime;
        }
        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getStopTime() {
            return stopTime;
        }
        public void setStopTime(String stopTime) {
            this.stopTime = stopTime;
        }

        public String getInspectionBy() {
            return inspectionBy;
        }
        public void setInspectionBy(String inspectionBy) {
            this.inspectionBy = inspectionBy;
        }

        public String getSection() {
            return section;
        }
        public void setSection(String section) {
            this.section = section;
        }
        public String getFacilityId() {
        return facilityId;
        }
        public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
        }
		public String getKm() {
			return km;
		}
		public void setKm(String km) {
			this.km = km;
		}
		public String getLocation() {
			return location;
		}
		public void setLocation(String location) {
			this.location = location;
		}
		

}