/**
 * 
 */
package com.scr.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scr.controller.ContentManagementController;
import com.scr.mapper.CommonMapper;
import com.scr.mapper.ContentManagementMapper;
import com.scr.message.request.ContentManagementRequest;
import com.scr.message.response.ResponseStatus;
import com.scr.model.ContentManagement;
import com.scr.repository.ContentManagementRepository;
import com.scr.util.Constants;

/**
 * @author vt1056
 *
 */
@Service
public class ContentManagementService {
	static Logger logger = LogManager.getLogger(ContentManagementController.class);	
	
	@Autowired
	private ContentManagementRepository repository;
	
	@Autowired
	private ContentManagementMapper mapper;
	
	@Value("${content.management.path}")
	private String contentManagementPath;
	
	@Autowired
	private CommonMapper commonMapper;
	
	public ResponseStatus storeUploadedFiles(List<MultipartFile> multipartFile, String genOps, String description,
			String divisionCode, String createdBy, String zonal, String fU, String topic, String assetTypeRlyId, String make, String model, String docCategory) {
		ResponseStatus responseStatus = new ResponseStatus();
		try {
			ResponseStatus folderResponse = mapper.checkAndCreateFolderStructure(contentManagementPath, genOps );
			if(folderResponse.getCode() == Constants.SUCCESS_CODE) {				
				List<ContentManagement> liContentManagements = new ArrayList<ContentManagement>();
				liContentManagements = commonMapper.prepareContentManagementList(multipartFile, contentManagementPath, genOps, divisionCode, fU, genOps, topic, description, assetTypeRlyId, make, model, docCategory, Integer.parseInt(createdBy));
				/*
				 * ContentManagement fileId = repository.findTopByOrderByCommonFileIdDesc();
				 * Long commonFileId = (long) 0.0; if(fileId == null || fileId.getCommonFileId()
				 * == null) { commonFileId = (long) 1; }else { commonFileId =
				 * fileId.getCommonFileId()+1; } for(MultipartFile mf: multipartFile) { String
				 * folderPath = folderResponse.getMessage();
				 * liContentManagements.add(mapper.saveAndStoreDetails(mf, divisionCode,
				 * createdBy, zonal, fU, topic, description, genOps, folderPath, commonFileId,
				 * assetTypeRlyId, make, model, docCategory)); }
				 */
				if(liContentManagements != null && !liContentManagements.isEmpty()) {
					repository.saveAll(liContentManagements);
					logger.info("Files Details saved in to Database Successfully.");
				}
			}					
			responseStatus.setCode(Constants.SUCCESS_CODE);
			responseStatus.setMessage(Constants.JOB_SUCCESS_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while saving files "+e.getMessage());
			responseStatus.setCode(Constants.FAILURE_CODE);
			responseStatus.setMessage("ERROR >>> "+e.getMessage());
		}
		return responseStatus;
	}

	public List<ContentManagement> findByCreatedBy(Integer createdBy) {		
		return repository.findByCreatedByOrderByModifiedDateDesc(createdBy);
	}

	public void deleteById(Long id) {		
		repository.deleteById(id);
	}

	public Optional<ContentManagement> findById(Long id) {		
		return repository.findById(id);
	}

	public ResponseStatus updateDescription(List<ContentManagement> updatedContentManagement,
			ContentManagementRequest contentManagement) {
		ResponseStatus responseStatus = new ResponseStatus();
		try {
			System.out.println(updatedContentManagement.toString());			
			for(ContentManagement updateList: updatedContentManagement) {
				updateList.setDescription(contentManagement.getDescription());
				repository.save(updateList);
			}
		}catch (Exception e) {
			logger.error("Error while saving files "+e.getMessage());
			responseStatus.setCode(Constants.FAILURE_CODE);
			responseStatus.setMessage("ERROR >>> "+e.getMessage());
		}
		return responseStatus;
	}

	public List<ContentManagement> findByCommonFileId(Long commonFileId) {		
		return repository.findByCommonFileId(commonFileId);
	}

	public List<ContentManagement> findByCreatedByAndGenOps(Integer createdBy, String GenOps) {
		return repository.findByCreatedByAndGenOps(createdBy, GenOps);
	}


}
