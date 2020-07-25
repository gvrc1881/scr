package com.scr.mapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.scr.controller.ContentManagementController;
import com.scr.message.response.ResponseStatus;
import com.scr.model.ContentManagement;
import com.scr.repository.ContentManagementRepository;
import com.scr.util.Constants;
import com.scr.util.Helper;

@Component
public class CommonMapper {
	
	static Logger logger = LogManager.getLogger(CommonMapper.class);	

	
	@Autowired
	private ContentManagementRepository repository;
	
	@Autowired
	private ContentManagementMapper mapper;

	public List<ContentManagement> prepareContentManagementList(List<MultipartFile> file, String folderPath,
			String cmType, String division, String funUnit, String genops, String topic, String description,
			String assetTypeRlyId, String make, String model, String docCategory, int createdBy) {
		List<ContentManagement> liContentManagements = null;
		try {
			liContentManagements = new ArrayList<ContentManagement>();
			ContentManagement fileId = repository.findTopByOrderByCommonFileIdDesc();
			Long commonFileId = (long) 0.0; 
			if(fileId == null || fileId.getCommonFileId() == null) {
				commonFileId = (long) 1;
			}else {
				commonFileId = fileId.getCommonFileId()+1;
			}
			
			ResponseStatus folderResponse = mapper.checkAndCreateFolderStructure(folderPath, cmType);
			
			for(MultipartFile mf: file)
			{	
				logger.info("filename: "+mf.getOriginalFilename());
				String changedFileName = Helper.prepareChangeFileName(mf, cmType, String.valueOf(createdBy));
				Path rootLocation = null;
				try {
					String createdFolderPath = folderResponse.getMessage();
					rootLocation = Paths.get(createdFolderPath);
					Files.copy(mf.getInputStream(), rootLocation.resolve(changedFileName));
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				ContentManagement contentManagement = new ContentManagement();			
				logger.info("File Saved Successfully with name "+changedFileName);
				contentManagement = new ContentManagement();
				contentManagement.setCommonFileId(commonFileId);
				contentManagement.setDivision("");
				contentManagement.setFunUnit("");
				contentManagement.setGenOps("");
				contentManagement.setTopic("Inspections");
				contentManagement.setDescription("");
				contentManagement.setOriginalFileName(mf.getOriginalFilename());				
				contentManagement.setChangeFileName(rootLocation+"\\"+changedFileName);
				double bytes = mf.getSize();
				logger.info("bytes = "+bytes);
				double kilobytes = Math.round((bytes / 1024) * 100.0) / 100.0;
				logger.info("KB = "+kilobytes);
				double megabytes = Math.round((kilobytes / 1024) * 100.0) / 100.0;
				logger.info("mega bytes = "+megabytes);
				contentManagement.setFileSize(kilobytes+" KB");
				contentManagement.setCreatedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
				contentManagement.setModifiedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
				contentManagement.setCreatedBy(createdBy);
				contentManagement.setAssetTypeRlyId(assetTypeRlyId);
				contentManagement.setMake(make);
				contentManagement.setModel(model);
				contentManagement.setDocCategory(docCategory);
				contentManagement.setStatusId(Constants.ACTIVE_STATUS_ID);
				
				liContentManagements.add(contentManagement);
			}
		}catch (Exception e) {
			logger.error("ERROR >>> while preparing the content management list "+e.getMessage());
		}
		return liContentManagements;
	}

	public List<ContentManagement> prepareForUpdateContentManagementList(List<MultipartFile> file, String folderPath,
			String cmType, String division, String funUnit, String genops, String topic, String description,
			String assetTypeRlyId, String make, String model, String docCategory, int modifiedBy, String attachment) {
		List<ContentManagement> liContentManagements = null;
		try {
			liContentManagements = new ArrayList<ContentManagement>();
			Long commonFileId = (long) 0.0; 
			if(file != null && file.size() > 0) {
				logger.info("file ");
				
			logger.info("commonfifle "+attachment);
			if(attachment == null || attachment.equalsIgnoreCase("undefined") || Long.parseLong(attachment) ==0.0 ) {
				
				ContentManagement fileId = repository.findTopByOrderByCommonFileIdDesc();		
				if(fileId == null || fileId.getCommonFileId() == null) {
					commonFileId = (long) 1;
				}else {
					commonFileId = fileId.getCommonFileId()+1;
				}
			}else {
				commonFileId = attachment != null ? Long.parseLong(attachment) : (long) 0.0;
			}
			logger.info("find file = "+commonFileId);
			
			ResponseStatus folderResponse = mapper.checkAndCreateFolderStructure(folderPath, cmType );
			
			for(MultipartFile mf: file)
			{	
				logger.info("filename: "+mf.getOriginalFilename());
				String changedFileName = Helper.prepareChangeFileName(mf, cmType, String.valueOf(modifiedBy));
				Path rootLocation = null;
				try {
					String mfolderPath = folderResponse.getMessage();
					rootLocation = Paths.get(mfolderPath);
					Files.copy(mf.getInputStream(), rootLocation.resolve(changedFileName));
				} catch (Exception e) {
					e.printStackTrace();
				}	
				ContentManagement contentManagement = new ContentManagement();
				logger.info("File Saved Successfully with name "+changedFileName);
				contentManagement = new ContentManagement();
				contentManagement.setCommonFileId(commonFileId);
				contentManagement.setDivision(division);
				contentManagement.setFunUnit(funUnit);
				contentManagement.setGenOps(genops);
				contentManagement.setTopic(topic);
				contentManagement.setDescription(description);
				contentManagement.setOriginalFileName(mf.getOriginalFilename());				
				contentManagement.setChangeFileName(rootLocation+"\\"+changedFileName);
				double bytes = mf.getSize();
				logger.info("bytes = "+bytes);
				double kilobytes = Math.round((bytes / 1024) * 100.0) / 100.0;
				logger.info("KB = "+kilobytes);
				double megabytes = Math.round((kilobytes / 1024) * 100.0) / 100.0;
				logger.info("mega bytes = "+megabytes);
				contentManagement.setFileSize(kilobytes+" KB");
				//contentManagement.setCreatedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
				contentManagement.setModifiedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
				contentManagement.setModifiedBy(modifiedBy);
				contentManagement.setAssetTypeRlyId(assetTypeRlyId);
				contentManagement.setMake(make);
				contentManagement.setModel(model);
				contentManagement.setDocCategory(docCategory);
				contentManagement.setStatusId(Constants.ACTIVE_STATUS_ID);
				
				liContentManagements.add(contentManagement);
			}
			}
		}catch (Exception e) {
			logger.error("ERROR >>> while preparing the content management list "+e.getMessage());
		}
		return liContentManagements;
	}
}

