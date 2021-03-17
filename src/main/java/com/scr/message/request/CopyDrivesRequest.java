package com.scr.message.request;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.scr.model.DriveCategory;
import com.scr.model.Drives;

public class CopyDrivesRequest implements Serializable {
	
	private List<DriveRequest> drivesRequest;
	private DriveCategory driveCategory;

	public List<DriveRequest> getDrivesRequest() {
		return drivesRequest;
	}

	public void setDrivesRequest(List<DriveRequest> drivesRequest) {
		this.drivesRequest = drivesRequest;
	}

	public DriveCategory getDriveCategory() {
		return driveCategory;
	}

	public void setDriveCategory(DriveCategory driveCategory) {
		this.driveCategory = driveCategory;
	}

}
