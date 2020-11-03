package com.scr.message.request;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.scr.model.DriveCategory;
import com.scr.model.Drives;

public class CopyDrivesRequest implements Serializable {
	private List<Drives> drives;
	private DriveCategory driveCategory;

	public List<Drives> getDrives() {
		return drives;
	}

	public void setDrives(List<Drives> drives) {
		this.drives = drives;
	}

	public DriveCategory getDriveCategory() {
		return driveCategory;
	}

	public void setDriveCategory(DriveCategory driveCategory) {
		this.driveCategory = driveCategory;
	}

}
