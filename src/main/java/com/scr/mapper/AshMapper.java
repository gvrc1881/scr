package com.scr.mapper;

import java.sql.Timestamp;
import java.util.Calendar;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.scr.message.request.AssetsScheduleHistoryRequest;
import com.scr.model.AssetsScheduleHistory;


@Component
public class AshMapper {
	static Logger logger = LogManager.getLogger(AshMapper.class);
	
	@Value("${stipulation.path}")
	private String stipulationPath;
	
	@Value("${inspection.path}")
	private String inspectionPath;
	
	public AssetsScheduleHistory prepareDriveModel(@Valid AssetsScheduleHistoryRequest ashRequest) throws Exception {
		AssetsScheduleHistory ash = null;
		logger.info("Preparing the ash model object");
		try {
		if (ashRequest != null) {
			ash = new AssetsScheduleHistory();

			ash.setAssetId(ashRequest.getAssetId());
			ash.setAssetType(ashRequest.getAssetType());
			ash.setCreatedBy(ashRequest.getCreatedBy());
			ash.setDetailsOfMaint(ashRequest.getDetailsOfMaint());
			ash.setDoneBy(ashRequest.getDoneBy());
			ash.setFacilityId(ashRequest.getFacilityId());
			ash.setScheduleCode(ashRequest.getScheduleCode());
			ash.setRemarks(ashRequest.getRemarks());
			ash.setScheduleDate(ashRequest.getScheduleDate());
			ash.setPbOperationSeqId(ashRequest.getPbOperationSeqId());
			ash.setCreatedBy(ashRequest.getCreatedBy());
			ash.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
			ash.setInitialOfIncharge(ashRequest.getInitialOfIncharge());
			ash.setStatus(ashRequest.getStatus());
			ash.setDataDiv(ashRequest.getDataDiv());
		}
		logger.info("Prepared ASH model object = "+ash);
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return ash;
	}

	

	
}
