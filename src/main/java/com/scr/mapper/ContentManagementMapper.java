/**
 * 
 */
package com.scr.mapper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Calendar;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.scr.message.response.ResponseStatus;
import com.scr.model.ContentManagement;
import com.scr.util.Constants;
import com.scr.util.Helper;

/**
 * @author vt1056
 *
 */
@Component
public class ContentManagementMapper {

	static Logger logger = LogManager.getLogger(ContentManagementMapper.class);
	private final Path rootLocation = Paths.get("upload-dir");
	public ResponseStatus checkAndCreateFolderStructure(String zonal, String divisionCode, String fU, String topic, String genOps) {
		ResponseStatus resStatus = new ResponseStatus();
		try {
			String currentYear = Helper.getCurrentYear();
			File file = new File(rootLocation+Constants.BACK_SLASH+
					currentYear+Constants.BACK_SLASH+
					//zonal+Constants.BACK_SLASH+
					//divisionCode+Constants.BACK_SLASH+
					//fU+Constants.BACK_SLASH+
					//topic+Constants.BACK_SLASH+
					genOps.toLowerCase().replace(" ", "-"));
			if(!file.exists()) {
				file.mkdirs();
				logger.info("Folder created successfully.. "+file.getPath());
			}else {
				logger.info("Folders already created...");
			}
			resStatus.setCode(Constants.SUCCESS_CODE);
			resStatus.setMessage(file.getPath());
		}catch (Exception e) {
			logger.error("ERROR >>> "+e.getMessage());
			resStatus.setCode(Constants.FAILURE_CODE);
		}
		return resStatus;
	}
	public ContentManagement saveAndStoreDetails(MultipartFile mf, String divisionCode, String createdBy, String zonal,
			String fU, String topic, String description, String genOps, String folderPath, Long commonFileId, String assetTypeRlyId, String make, String model, String docCategory) {
		ContentManagement contentManagement = new ContentManagement();
		logger.info("filename: "+mf.getOriginalFilename());
		String changedFileName = Helper.prepareChangeFileName(mf, divisionCode, createdBy);
		logger.info("File Saved Successfully with name "+changedFileName);
		contentManagement = new ContentManagement();
		contentManagement.setCommonFileId(commonFileId);
		contentManagement.setDivision(divisionCode);
		contentManagement.setFunUnit("PSI");
		contentManagement.setGenOps(genOps);
		contentManagement.setTopic("Indents");
		contentManagement.setDescription(description);
		contentManagement.setOriginalFileName(mf.getOriginalFilename());				
		contentManagement.setChangeFileName(changedFileName);
		contentManagement.setCreatedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		contentManagement.setModifiedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		contentManagement.setCreatedBy(Integer.parseInt(createdBy));
		contentManagement.setAssetTypeRlyId(assetTypeRlyId);
		contentManagement.setMake(make);
		contentManagement.setModel(model);
		contentManagement.setDocCategory(docCategory);
		contentManagement.setStatusId(Constants.ACTIVE_STATUS_ID);
		try {
			Path rootLocation = Paths.get(folderPath);
			Files.copy(mf.getInputStream(), rootLocation.resolve(changedFileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contentManagement;
	}
	public ContentManagement saveAndStoreDetails(MultipartFile mf, String divisionCode, String createdBy, String zonal,
			String fU, String contentTopic, String description, String contentCategory, String folderPath,
			Long commonFileId) {
		ContentManagement contentManagement = new ContentManagement();
		logger.info("filename: "+mf.getOriginalFilename());
		String changedFileName = Helper.prepareChangeFileName(mf, divisionCode, createdBy);
		logger.info("File Saved Successfully with name "+changedFileName);
		contentManagement = new ContentManagement();
		contentManagement.setCommonFileId(commonFileId);
		contentManagement.setDivision(divisionCode);
		contentManagement.setFunUnit("PSI");
		contentManagement.setGenOps(contentCategory);
		contentManagement.setTopic(contentTopic);
		contentManagement.setDescription(description);
		contentManagement.setOriginalFileName(mf.getOriginalFilename());				
		contentManagement.setChangeFileName(changedFileName);
		contentManagement.setCreatedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		contentManagement.setModifiedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		contentManagement.setCreatedBy(Integer.parseInt(createdBy));
		contentManagement.setStatusId(Constants.ACTIVE_STATUS_ID);
		try {
			Path rootLocation = Paths.get(folderPath);
			Files.copy(mf.getInputStream(), rootLocation.resolve(changedFileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contentManagement;
	}

}
