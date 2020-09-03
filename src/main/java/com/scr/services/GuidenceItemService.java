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

import com.scr.mapper.ContentManagementMapper;
import com.scr.message.response.ResponseStatus;
import com.scr.model.ContentManagement;
import com.scr.model.GuidenceItem;
import com.scr.repository.ContentManagementRepository;
import com.scr.repository.GuidenceItemRepository;
import com.scr.util.Constants;

@Service
public class GuidenceItemService {
	
	static Logger logger = LogManager.getLogger(GuidenceItemService.class);
	
	@Autowired
	private GuidenceItemRepository guidenceItemRepository;
	
	@Autowired
	private ContentManagementMapper contentManagementMapper;
	
	@Autowired
	private ContentManagementRepository contentManagementRepository;
	
	@Value("${guidenceItem.path}")
	private String guidenceItemPath;
	
	public List<GuidenceItem> findAll() {
		// TODO Auto-generated method stub
		return guidenceItemRepository.findAll();
	}

	public void save(GuidenceItem guidenceItem) {
		// TODO Auto-generated method stub
		guidenceItemRepository.save(guidenceItem);
	}

	public Optional<GuidenceItem> findGuidenceItemById(Integer id) {
		// TODO Auto-generated method stub
		return guidenceItemRepository.findById(id);
	}

	public void deleteGuidenceItemById(Integer id) {
		// TODO Auto-generated method stub
		guidenceItemRepository.deleteById(id);
	}
	
	public ResponseStatus storeUploadedFiles(List<MultipartFile> multipartFile, String contentCategory, String description,
			String divisionCode, String createdBy, String zonal, String fU, String contentTopic,
			Integer guidenceItemId) {
		ResponseStatus responseStatus = new ResponseStatus();
		try {
			ResponseStatus folderResponse = contentManagementMapper.checkAndCreateFolderStructure(guidenceItemPath, contentCategory );
			if(folderResponse.getCode() == Constants.SUCCESS_CODE) {				
				List<ContentManagement> liContentManagements = new ArrayList<ContentManagement>();	
				ContentManagement fileId = contentManagementRepository.findTopByOrderByCommonFileIdDesc();
				Long commonFileId = (long) 0.0; 
				if(fileId == null || fileId.getCommonFileId() == null) {
					commonFileId = (long) 1;
				}else {
					commonFileId = fileId.getCommonFileId()+1;
				}
				Optional<GuidenceItem> guidenceItem =guidenceItemRepository.findById(guidenceItemId);
				if (guidenceItem.isPresent()) {
					GuidenceItem guidenceItemDetails = guidenceItem.get();
					if (guidenceItemDetails.getContentLink() != null) {
						commonFileId = Long.parseLong(guidenceItemDetails.getContentLink());
					} else {
						guidenceItemDetails.setContentLink(String.valueOf(commonFileId));
					}
					
					guidenceItemRepository.save(guidenceItemDetails);
				}
				
				for(MultipartFile mf: multipartFile)
				{
					String folderPath = folderResponse.getMessage();
					liContentManagements.add(contentManagementMapper.saveAndStoreDetails(mf, divisionCode, createdBy, zonal, fU, contentTopic, description, contentCategory, folderPath, commonFileId));									
				}
				if(!liContentManagements.isEmpty()) {
					liContentManagements = contentManagementRepository.saveAll(liContentManagements);
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

}
