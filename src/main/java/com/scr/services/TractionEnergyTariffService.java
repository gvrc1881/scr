package com.scr.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scr.mapper.ContentManagementMapper;
import com.scr.message.response.ResponseStatus;
import com.scr.model.ContentManagement;
import com.scr.model.TractionEnergyTariff;
import com.scr.repository.ContentManagementRepository;
import com.scr.repository.TractionEnergyTariffRepository;
import com.scr.util.Constants;

@Service
public class TractionEnergyTariffService {
	
	@Autowired
	private TractionEnergyTariffRepository tractionEnergyTariffRepository;
	
	@Autowired
	private ContentManagementMapper contentManagementMapper;
	
	@Autowired
	private ContentManagementRepository contentManagementRepository;
	
	static Logger logger = LogManager.getLogger(TractionEnergyTariffService.class);

	public List<TractionEnergyTariff> findAll() {
		// TODO Auto-generated method stub
		return tractionEnergyTariffRepository.findAll();
	}

	public TractionEnergyTariff saveTractionEneTariff(TractionEnergyTariff tractionEnergyTariff) {
		// TODO Auto-generated method stub
		return tractionEnergyTariffRepository.save(tractionEnergyTariff);
	}

	public Optional<TractionEnergyTariff> findById(Integer id) {
		// TODO Auto-generated method stub
		return tractionEnergyTariffRepository.findById(id);
	}

	public void deleteTractionEneTariffById(Integer id) {
		// TODO Auto-generated method stub
		tractionEnergyTariffRepository.deleteById(id);
	}

	public ResponseStatus storeUploadedFiles(List<MultipartFile> multipartFile, String contentCategory, String description,
			String divisionCode, String createdBy, String zonal, String fU, String contentTopic,Integer tractionEnergyTariffId) {
		ResponseStatus responseStatus = new ResponseStatus();
		try {
			ResponseStatus folderResponse = contentManagementMapper.checkAndCreateFolderStructure(zonal, divisionCode, fU, contentTopic, contentCategory );
			if(folderResponse.getCode() == Constants.SUCCESS_CODE) {				
				List<ContentManagement> liContentManagements = new ArrayList<ContentManagement>();	
				ContentManagement fileId = contentManagementRepository.findTopByOrderByCommonFileIdDesc();
				Long commonFileId = (long) 0.0; 
				if(fileId == null || fileId.getCommonFileId() == null) {
					commonFileId = (long) 1;
				}else {
					commonFileId = fileId.getCommonFileId()+1;
				}
				for(MultipartFile mf: multipartFile)
				{
					String folderPath = folderResponse.getMessage();
					liContentManagements.add(contentManagementMapper.saveAndStoreDetails(mf, divisionCode, createdBy, zonal, fU, contentTopic, description, contentCategory, folderPath, commonFileId));									
				}
				if(!liContentManagements.isEmpty()) {
					liContentManagements = contentManagementRepository.saveAll(liContentManagements);
					Optional<TractionEnergyTariff> traEneTariff =tractionEnergyTariffRepository.findById(tractionEnergyTariffId);
					if (traEneTariff.isPresent()) {
						TractionEnergyTariff tractionEnergyTariff = traEneTariff.get();
						String contentLink = tractionEnergyTariff.getContentLink();
						for (ContentManagement contentManagement : liContentManagements) {
							if (contentLink != null)
								contentLink = contentLink+","+contentManagement.getId().toString();
							else
								contentLink = contentManagement.getId().toString();	
						}
						tractionEnergyTariff.setContentLink(contentLink);
						tractionEnergyTariffRepository.save(tractionEnergyTariff);
					}
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
