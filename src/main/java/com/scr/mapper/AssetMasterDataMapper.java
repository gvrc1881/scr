package com.scr.mapper;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Optional;
import javax.validation.Valid;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.scr.message.request.AssetMasterDataRequest;
import com.scr.model.AssetMasterData;
import com.scr.model.Facility;
import com.scr.repository.FacilityRepository;

@Component
public class AssetMasterDataMapper {
	
	static Logger logger = LogManager.getLogger(AssetMasterDataMapper.class);
	@Autowired
	private FacilityRepository facilityRepository;
	
	public AssetMasterData prepareAssetMasterDataModel(@Valid AssetMasterDataRequest assetMasterDataRequest) throws Exception {
		AssetMasterData assetMasterData = null;
		logger.info("Preparing the AssetMaster Data model object");
		try {
		if (assetMasterDataRequest != null) {
			assetMasterData = new AssetMasterData();

			assetMasterData.setAdeeSection(assetMasterDataRequest.getAdeeSection());
			assetMasterData.setAssetId(assetMasterDataRequest.getAssetId());
			assetMasterData.setAssetType(assetMasterDataRequest.getAssetType());
			assetMasterData.setBatch(assetMasterDataRequest.getBatch());
			assetMasterData.setBracket1InsulatorBatch(assetMasterDataRequest.getBracket1InsulatorBatch());
			assetMasterData.setBracket1InsulatorMake(assetMasterDataRequest.getBracket1InsulatorMake());
			assetMasterData.setBracket2InsulatorBatch(assetMasterDataRequest.getBracket2InsulatorBatch());
			assetMasterData.setBracket2InsulatorMake(assetMasterDataRequest.getBracket2InsulatorMake());
			assetMasterData.setBracket3InsulatorBatch(assetMasterDataRequest.getBracket3InsulatorBatch());
			assetMasterData.setBracket3InsulatorMake(assetMasterDataRequest.getBracket3InsulatorMake());
			assetMasterData.setCapacityRating(assetMasterDataRequest.getCapacityRating());
			assetMasterData.setCodalLife(assetMasterDataRequest.getCodalLife());
			assetMasterData.setCore1InsulatorBatch(assetMasterDataRequest.getCore1InsulatorBatch());
			assetMasterData.setCore1InsulatorMake(assetMasterDataRequest.getCore1InsulatorMake());
			assetMasterData.setCore2InsulatorMake(assetMasterDataRequest.getCore2InsulatorMake());
			assetMasterData.setCore2InsulatorBatch(assetMasterDataRequest.getCore2InsulatorBatch());
			assetMasterData.setCore3InsulatorBatch(assetMasterDataRequest.getCore3InsulatorBatch());
			assetMasterData.setCore3InsulatorMake(assetMasterDataRequest.getCore3InsulatorMake());
			assetMasterData.setCreatedBy(assetMasterDataRequest.getCreatedBy());
			assetMasterData.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
			assetMasterData.setDateOfCommision(assetMasterDataRequest.getDateOfCommision());
			assetMasterData.setDateOfManufacture(assetMasterDataRequest.getDateOfManufacture());
			assetMasterData.setDateOfReceived(assetMasterDataRequest.getDateOfReceived());
			assetMasterData.setElementarySection(assetMasterDataRequest.getElementarySection());
			assetMasterData.setEnd1Side1(assetMasterDataRequest.getEnd1Side1());
			assetMasterData.setEnd2Side2(assetMasterDataRequest.getEnd2Side2());
			assetMasterData.setEquippedDate(assetMasterDataRequest.getEquippedDate());
			assetMasterData.setExpiryDate(assetMasterDataRequest.getExpiryDate());
			assetMasterData.setFacilityId(assetMasterDataRequest.getFacilityId());
			assetMasterData.setImplantation(assetMasterDataRequest.getImplantation());
			assetMasterData.setKilometer(assetMasterDataRequest.getKilometer());
			assetMasterData.setLine(assetMasterDataRequest.getLine());
			assetMasterData.setLocationPosition(assetMasterDataRequest.getLocationPosition());
			assetMasterData.setLugDate(assetMasterDataRequest.getLugDate());
			assetMasterData.setMajorSection(assetMasterDataRequest.getMajorSection());
			assetMasterData.setMake(assetMasterDataRequest.getMake());
			assetMasterData.setModel(assetMasterDataRequest.getModel());
			assetMasterData.setNamePlateDetails(assetMasterDataRequest.getNamePlateDetails());
			assetMasterData.setOemSerial(assetMasterDataRequest.getOemSerial());
			assetMasterData.setParentAssetType(assetMasterDataRequest.getParentAssetType());
			assetMasterData.setParentAssetTypeId(assetMasterDataRequest.getParentAssetTypeId());
			assetMasterData.setPart1(assetMasterDataRequest.getPart1());
			assetMasterData.setPart2(assetMasterDataRequest.getPart2());
			assetMasterData.setPart3(assetMasterDataRequest.getPart3());
			assetMasterData.setPedestal1InsulatorBatch(assetMasterDataRequest.getPedestal1InsulatorBatch());
			assetMasterData.setPedestal1InsulatorMake(assetMasterDataRequest.getPedestal1InsulatorMake());
			assetMasterData.setPedestal2InsulatorBatch(assetMasterDataRequest.getPedestal2InsulatorBatch());
			assetMasterData.setPedestal2InsulatorMake(assetMasterDataRequest.getPedestal2InsulatorMake());
			assetMasterData.setPedestal3InsulatorBatch(assetMasterDataRequest.getPedestal3InsulatorBatch());
			assetMasterData.setPedestal3InsulatorMake(assetMasterDataRequest.getPedestal3InsulatorMake());
			assetMasterData.setPositionId(assetMasterDataRequest.getPositionId());
			assetMasterData.setRemark1(assetMasterDataRequest.getRemark1());
			assetMasterData.setRemark2(assetMasterDataRequest.getRemark2());
			assetMasterData.setRlyAssignedSerial(assetMasterDataRequest.getRlyAssignedSerial());
			assetMasterData.setRod1InsulatorBatch(assetMasterDataRequest.getRod1InsulatorBatch());
			assetMasterData.setRod1InsulatorMake(assetMasterDataRequest.getRod1InsulatorMake());
			assetMasterData.setRod2InsulatorBatch(assetMasterDataRequest.getRod2InsulatorBatch());
			assetMasterData.setRod2InsulatorMake(assetMasterDataRequest.getRod2InsulatorMake());
			assetMasterData.setRod3InsulatorBatch(assetMasterDataRequest.getRod3InsulatorBatch());
			assetMasterData.setRod3InsulatorMake(assetMasterDataRequest.getRod3InsulatorMake());
			assetMasterData.setSection(assetMasterDataRequest.getSection());
			assetMasterData.setSource(assetMasterDataRequest.getSource());
			assetMasterData.setStag1Ton9InsulatorBatch(assetMasterDataRequest.getStag1Ton9InsulatorBatch());
			assetMasterData.setStag1Ton9InsulatorMake(assetMasterDataRequest.getStag1Ton9InsulatorMake());
			assetMasterData.setStag2Ton9InsulatorBatch(assetMasterDataRequest.getStag2Ton9InsulatorBatch());
			assetMasterData.setStag2Ton9InsulatorMake(assetMasterDataRequest.getStag2Ton9InsulatorMake());
			assetMasterData.setStag3Ton9InsulatorBatch(assetMasterDataRequest.getStag3Ton9InsulatorBatch());
			assetMasterData.setStag3Ton9InsulatorMake(assetMasterDataRequest.getStag3Ton9InsulatorMake());
			assetMasterData.setStagger(assetMasterDataRequest.getStagger());
			assetMasterData.setStagger1(assetMasterDataRequest.getStagger1());
			assetMasterData.setStagger2(assetMasterDataRequest.getStagger2());
			assetMasterData.setStagger3(assetMasterDataRequest.getStagger3());
			assetMasterData.setStation(assetMasterDataRequest.getStation());
			assetMasterData.setStay1InsulatorBatch(assetMasterDataRequest.getStay1InsulatorBatch());
			assetMasterData.setStay1InsulatorMake(assetMasterDataRequest.getStay1InsulatorMake());
			assetMasterData.setStay2InsulatorBatch(assetMasterDataRequest.getStay2InsulatorBatch());
			assetMasterData.setStay2InsulatorMake(assetMasterDataRequest.getStay2InsulatorMake());
			assetMasterData.setStay3InsulatorBatch(assetMasterDataRequest.getStay3InsulatorBatch());
			assetMasterData.setStay3InsulatorMake(assetMasterDataRequest.getStay3InsulatorMake());
			assetMasterData.setStripDate(assetMasterDataRequest.getStripDate());
			assetMasterData.setStructure(assetMasterDataRequest.getStructure());
			assetMasterData.setType(assetMasterDataRequest.getType());
			assetMasterData.setVendor(assetMasterDataRequest.getVendor());
			assetMasterData.setVoltage(assetMasterDataRequest.getVoltage());
			assetMasterData.setWarrantyAmc(assetMasterDataRequest.getWarrantyAmc());
			assetMasterData.setWarrantyAmcEndDate(assetMasterDataRequest.getWarrantyAmcEndDate());

		}
		logger.info("Prepared model object = "+assetMasterData);
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return assetMasterData;
	}
	
	public AssetMasterData prepareAssetMasterUpdateData(AssetMasterData assetMasterData, @Valid AssetMasterDataRequest assetMasterDataRequest) {

		if (assetMasterDataRequest != null) {

			logger.info(assetMasterDataRequest);

			assetMasterData.setAdeeSection(assetMasterDataRequest.getAdeeSection());
			assetMasterData.setAssetId(assetMasterDataRequest.getAssetId());
			assetMasterData.setAssetType(assetMasterDataRequest.getAssetType());
			assetMasterData.setBatch(assetMasterDataRequest.getBatch());
			assetMasterData.setBracket1InsulatorBatch(assetMasterDataRequest.getBracket1InsulatorBatch());
			assetMasterData.setBracket1InsulatorMake(assetMasterDataRequest.getBracket1InsulatorMake());
			assetMasterData.setBracket2InsulatorBatch(assetMasterDataRequest.getBracket2InsulatorBatch());
			assetMasterData.setBracket2InsulatorMake(assetMasterDataRequest.getBracket2InsulatorMake());
			assetMasterData.setBracket3InsulatorBatch(assetMasterDataRequest.getBracket3InsulatorBatch());
			assetMasterData.setBracket3InsulatorMake(assetMasterDataRequest.getBracket3InsulatorMake());
			assetMasterData.setCapacityRating(assetMasterDataRequest.getCapacityRating());
			assetMasterData.setCodalLife(assetMasterDataRequest.getCodalLife());
			assetMasterData.setCore1InsulatorBatch(assetMasterDataRequest.getCore1InsulatorBatch());
			assetMasterData.setCore1InsulatorMake(assetMasterDataRequest.getCore1InsulatorMake());
			assetMasterData.setCore2InsulatorMake(assetMasterDataRequest.getCore2InsulatorMake());
			assetMasterData.setCore2InsulatorBatch(assetMasterDataRequest.getCore2InsulatorBatch());
			assetMasterData.setCore3InsulatorBatch(assetMasterDataRequest.getCore3InsulatorBatch());
			assetMasterData.setCore3InsulatorMake(assetMasterDataRequest.getCore3InsulatorMake());
			assetMasterData.setCreatedBy(assetMasterDataRequest.getCreatedBy());
			assetMasterData.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
			assetMasterData.setDateOfCommision(assetMasterDataRequest.getDateOfCommision());
			assetMasterData.setDateOfManufacture(assetMasterDataRequest.getDateOfManufacture());
			assetMasterData.setDateOfReceived(assetMasterDataRequest.getDateOfReceived());
			assetMasterData.setElementarySection(assetMasterDataRequest.getElementarySection());
			assetMasterData.setEnd1Side1(assetMasterDataRequest.getEnd1Side1());
			assetMasterData.setEnd2Side2(assetMasterDataRequest.getEnd2Side2());
			assetMasterData.setEquippedDate(assetMasterDataRequest.getEquippedDate());
			assetMasterData.setExpiryDate(assetMasterDataRequest.getExpiryDate());
			assetMasterData.setFacilityId(assetMasterDataRequest.getFacilityId());
			assetMasterData.setImplantation(assetMasterDataRequest.getImplantation());
			assetMasterData.setKilometer(assetMasterDataRequest.getKilometer());
			assetMasterData.setLine(assetMasterDataRequest.getLine());
			assetMasterData.setLocationPosition(assetMasterDataRequest.getLocationPosition());
			assetMasterData.setLugDate(assetMasterDataRequest.getLugDate());
			assetMasterData.setMajorSection(assetMasterDataRequest.getMajorSection());
			assetMasterData.setMake(assetMasterDataRequest.getMake());
			assetMasterData.setModel(assetMasterDataRequest.getModel());
			assetMasterData.setNamePlateDetails(assetMasterDataRequest.getNamePlateDetails());
			assetMasterData.setOemSerial(assetMasterDataRequest.getOemSerial());
			assetMasterData.setParentAssetType(assetMasterDataRequest.getParentAssetType());
			assetMasterData.setParentAssetTypeId(assetMasterDataRequest.getParentAssetTypeId());
			assetMasterData.setPart1(assetMasterDataRequest.getPart1());
			assetMasterData.setPart2(assetMasterDataRequest.getPart2());
			assetMasterData.setPart3(assetMasterDataRequest.getPart3());
			assetMasterData.setPedestal1InsulatorBatch(assetMasterDataRequest.getPedestal1InsulatorBatch());
			assetMasterData.setPedestal1InsulatorMake(assetMasterDataRequest.getPedestal1InsulatorMake());
			assetMasterData.setPedestal2InsulatorBatch(assetMasterDataRequest.getPedestal2InsulatorBatch());
			assetMasterData.setPedestal2InsulatorMake(assetMasterDataRequest.getPedestal2InsulatorMake());
			assetMasterData.setPedestal3InsulatorBatch(assetMasterDataRequest.getPedestal3InsulatorBatch());
			assetMasterData.setPedestal3InsulatorMake(assetMasterDataRequest.getPedestal3InsulatorMake());
			assetMasterData.setPositionId(assetMasterDataRequest.getPositionId());
			assetMasterData.setRemark1(assetMasterDataRequest.getRemark1());
			assetMasterData.setRemark2(assetMasterDataRequest.getRemark2());
			assetMasterData.setRlyAssignedSerial(assetMasterDataRequest.getRlyAssignedSerial());
			assetMasterData.setRod1InsulatorBatch(assetMasterDataRequest.getRod1InsulatorBatch());
			assetMasterData.setRod1InsulatorMake(assetMasterDataRequest.getRod1InsulatorMake());
			assetMasterData.setRod2InsulatorBatch(assetMasterDataRequest.getRod2InsulatorBatch());
			assetMasterData.setRod2InsulatorMake(assetMasterDataRequest.getRod2InsulatorMake());
			assetMasterData.setRod3InsulatorBatch(assetMasterDataRequest.getRod3InsulatorBatch());
			assetMasterData.setRod3InsulatorMake(assetMasterDataRequest.getRod3InsulatorMake());
			assetMasterData.setSection(assetMasterDataRequest.getSection());
			assetMasterData.setSource(assetMasterDataRequest.getSource());
			assetMasterData.setStag1Ton9InsulatorBatch(assetMasterDataRequest.getStag1Ton9InsulatorBatch());
			assetMasterData.setStag1Ton9InsulatorMake(assetMasterDataRequest.getStag1Ton9InsulatorMake());
			assetMasterData.setStag2Ton9InsulatorBatch(assetMasterDataRequest.getStag2Ton9InsulatorBatch());
			assetMasterData.setStag2Ton9InsulatorMake(assetMasterDataRequest.getStag2Ton9InsulatorMake());
			assetMasterData.setStag3Ton9InsulatorBatch(assetMasterDataRequest.getStag3Ton9InsulatorBatch());
			assetMasterData.setStag3Ton9InsulatorMake(assetMasterDataRequest.getStag3Ton9InsulatorMake());
			assetMasterData.setStagger(assetMasterDataRequest.getStagger());
			assetMasterData.setStagger1(assetMasterDataRequest.getStagger1());
			assetMasterData.setStagger2(assetMasterDataRequest.getStagger2());
			assetMasterData.setStagger3(assetMasterDataRequest.getStagger3());
			assetMasterData.setStation(assetMasterDataRequest.getStation());
			assetMasterData.setStay1InsulatorBatch(assetMasterDataRequest.getStay1InsulatorBatch());
			assetMasterData.setStay1InsulatorMake(assetMasterDataRequest.getStay1InsulatorMake());
			assetMasterData.setStay2InsulatorBatch(assetMasterDataRequest.getStay2InsulatorBatch());
			assetMasterData.setStay2InsulatorMake(assetMasterDataRequest.getStay2InsulatorMake());
			assetMasterData.setStay3InsulatorBatch(assetMasterDataRequest.getStay3InsulatorBatch());
			assetMasterData.setStay3InsulatorMake(assetMasterDataRequest.getStay3InsulatorMake());
			assetMasterData.setStripDate(assetMasterDataRequest.getStripDate());
			assetMasterData.setStructure(assetMasterDataRequest.getStructure());
			assetMasterData.setType(assetMasterDataRequest.getType());
			assetMasterData.setVendor(assetMasterDataRequest.getVendor());
			assetMasterData.setVoltage(assetMasterDataRequest.getVoltage());
			assetMasterData.setWarrantyAmc(assetMasterDataRequest.getWarrantyAmc());
			assetMasterData.setWarrantyAmcEndDate(assetMasterDataRequest.getWarrantyAmcEndDate());
		}
		return assetMasterData;
	}
	public AssetMasterData prepareAssetMasterData(AssetMasterData assetMasterData) {
		// TODO Auto-generated method stub
		if (assetMasterData.getFacilityId() != null ) {
			Optional<Facility> facility  = facilityRepository.findByFacilityId(assetMasterData.getFacilityId());
			if (facility.isPresent()) {
				assetMasterData.setFacilityId(facility.get().getFacilityName());
			}
		}
		
		return assetMasterData;
	}

}
