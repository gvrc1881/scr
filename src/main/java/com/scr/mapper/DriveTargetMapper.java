package com.scr.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scr.message.response.AssetStatusUpdateResponse;
import com.scr.message.response.DriveTargetResponse;
import com.scr.model.Drives;
import com.scr.repository.DrivesRepository;


@Component
public class DriveTargetMapper {

	static Logger logger = LogManager.getLogger(DriveTargetMapper.class);
	
	@Autowired
	private DrivesRepository driveRepository;

	public List<DriveTargetResponse> prepareDriveTargets(Optional<Drives> drive, String zone) {
		
		//Optional<Drives> drives = driveRepository.getName(drive);
		
		DriveTargetResponse dtr = new DriveTargetResponse();
		
		//dtr.setDriveId(drive);
		
		return null;
	}

}
