package com.scr.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.scr.mapper.AssetMasterDataMapper;
import com.scr.mapper.AssetStatusUpdateMapper;
import com.scr.message.response.AssetStatusUpdateResponse;
import com.scr.model.AssetMasterData;
import com.scr.model.AssetMasterDataFormParameter;
import com.scr.repository.AssetMasterFormParameterRepository;
import com.scr.repository.AssetMastersRepository;
import com.scr.search.models.SearchCriteria;
import com.scr.specifications.UserSpecification;

@Service
public class AssetMasterDataService {
	
	static Logger logger = LogManager.getLogger(AssetMasterDataService.class);

	
	@Autowired
	private AssetMastersRepository assetMastersRepository;
	@Autowired
	private AssetMasterFormParameterRepository assetMasterFormParameterRepository;
	@Autowired
	private AssetMasterDataMapper assetMasterDataMapper;
	@Autowired
	private AssetStatusUpdateMapper assetStatusUpdateMapper;
	
	
	public List<AssetMasterData> findPaginated(int from, int to) {
		logger.info("Fetching data from page "+from+" to page "+to);
		Pageable paging = PageRequest.of(from, to);
		Page<AssetMasterData> pagedResult = assetMastersRepository.findAll(paging);
		//return assetMastersRepository.findAll();
		List<AssetMasterData> amdList =  new ArrayList<AssetMasterData>();// pagedResult.getContent();
		logger.info("Records size: "+amdList.size());
		for (AssetMasterData assetMasterData : pagedResult.getContent()) {
			assetMasterData = assetMasterDataMapper.prepareAssetMasterData(assetMasterData);
			amdList.add(assetMasterData);
		}
		return amdList;
	}

	public List<AssetMasterData> findAll() {
		logger.info("Calling mapper class for find All assetMaster Data");
		List<AssetMasterData> assetMasterList = new ArrayList<>();
		List<AssetMasterData> assetMasterDataList = assetMastersRepository.findAll();
		for (AssetMasterData assetMasterData : assetMasterDataList) {
			assetMasterData = assetMasterDataMapper.prepareAssetMasterData(assetMasterData);
			assetMasterList.add(assetMasterData);
		}
		 return assetMasterList;
	}
	
	public void save(AssetMasterData assetMasterData) {
		assetMastersRepository.save(assetMasterData);
	}
	public Optional<AssetMasterData> findAssetMasterItemById(Long id) {
		
		return assetMastersRepository.findById(id);
	}

	public void deleteAssetMasterDataById(Long id) {
		
		assetMastersRepository.deleteById(id);
	}
	public List<AssetMasterData> findByAssetTypeAndFacilityId(String assetType,String facilityId) {
		List<AssetMasterData> assetId = assetMastersRepository.findByAssetTypeAndFacilityId(assetType,facilityId);
		return assetId;
	}
	
	public List<AssetMasterDataFormParameter> findByAssetTypeAndActive(String assetType,String active) {	
		return assetMasterFormParameterRepository.findByAssetTypeAndActive(assetType,active);
	}

	public List<AssetMasterData> findByAssetTypeAndFacilityIdAndKM(String assetType, String facilityId, Double fromKm,
			Double toKm) {
		List<AssetMasterData> assetIds = assetMastersRepository.findByAssetTypeAndFacilityIdAndKM(assetType,facilityId,fromKm,toKm);
		return assetIds;
	}

	public List<AssetMasterData> findAssetIdsByFacilityId(String facilityId, Double fromKm, Double toKm) {
		List<AssetMasterData> assetIds = assetMastersRepository.findAssetIdsByFacilityId(facilityId,fromKm,toKm);
		return assetIds;
	}

	public List<AssetMasterData> findMakeModel(String assetId, String assetType, String facilityId) {
		List<AssetMasterData> assetIds = assetMastersRepository.findMakeModel(assetId,assetType,facilityId);
		return assetIds;
	}

	public List<AssetStatusUpdateResponse> getByDataDiv(String div) {
		// TODO Auto-generated method stub
		List<AssetMasterData> amd = assetMastersRepository.getByDataDiv(div);
		List<AssetStatusUpdateResponse> assetStatusUpdate = null;
		 logger.info("*** amd list ***"+amd.size());
			for (AssetMasterData assetMasterData : amd) 
			{
					
					 assetStatusUpdate = assetStatusUpdateMapper.prepareAssetStatusUpdateData(assetMasterData,div);
					
				}
			return assetStatusUpdate;
	}

	public List<AssetStatusUpdateResponse> getByFacilityId(String facilityId) {
		// TODO Auto-generated method stub
		
		List<AssetMasterData> amd = assetMastersRepository.getFacilityId(facilityId);
		 logger.info("*** amd list ***"+amd.size());
		 List<AssetStatusUpdateResponse> assetStatusUpdate = new ArrayList<>();
			for (AssetMasterData assetMasterData2 : amd) 
			{	
			logger.info("*** in for loop  ***"+assetMasterData2.getFacilityId());
				assetStatusUpdate = assetStatusUpdateMapper.prepareAssetStatusUpdateDataBasedOnFacility(assetMasterData2,facilityId);
			} 		
				
					
			return assetStatusUpdate;
		}
	
	public Boolean existsByFacilityIdAndAssetTypeAndAssetId(String facilityId, String assetType,String assetId) {
		 
		return assetMastersRepository.existsByFacilityIdAndAssetTypeAndAssetId(facilityId,assetType,assetId);
	}
	
	public Optional<AssetMasterData> findByFacilityIdAndAssetTypeAndAssetId(String facilityId,String assetType,String assetId) {
		return assetMastersRepository.findByFacilityIdAndAssetTypeAndAssetId(facilityId,assetType,assetId);
	}

	public List<AssetMasterData> findSearch(AssetMasterData assetMasterData) {
		List<AssetMasterData> results = null;
		try {
			if(assetMasterData != null) {
				List<UserSpecification> specifications = new ArrayList<UserSpecification>();
				
				if(assetMasterData.getType() != null && !assetMasterData.getType().isEmpty()) {
					UserSpecification type = 
						      new UserSpecification(new SearchCriteria("type", ":", assetMasterData.getType().toUpperCase()));
					specifications.add(type);
				}
				if(assetMasterData.getFacilityId() != null && !assetMasterData.getFacilityId().isEmpty()) {
					UserSpecification facilityId = 
						      new UserSpecification(new SearchCriteria("facilityId", ":", assetMasterData.getFacilityId().toUpperCase()));
					specifications.add(facilityId);
				}
				if(assetMasterData.getAssetType() != null) {					
					UserSpecification assetType = 
						      new UserSpecification(new SearchCriteria("assetType", ":", assetMasterData.getAssetType().toUpperCase()));
					specifications.add(assetType);
				}
				if(assetMasterData.getAssetId() != null && !assetMasterData.getAssetId().isEmpty()) {
					UserSpecification assetId = 
						      new UserSpecification(new SearchCriteria("assetId", ":", assetMasterData.getAssetId().toUpperCase()));
					specifications.add(assetId);
				}
				if(assetMasterData.getAdeeSection() != null && !assetMasterData.getAdeeSection().isEmpty()) {
					UserSpecification adeeSection = 
						      new UserSpecification(new SearchCriteria("adeeSection", ":", assetMasterData.getAdeeSection().toUpperCase()));
					specifications.add(adeeSection);
				}
				if(assetMasterData.getMajorSection() != null && !assetMasterData.getMajorSection().isEmpty()) {
					UserSpecification majorSection = 
						      new UserSpecification(new SearchCriteria("majorSection", ":", assetMasterData.getMajorSection().toUpperCase()));
					specifications.add(majorSection);
				}
				if(assetMasterData.getSection() != null && !assetMasterData.getSection().isEmpty()) {
					UserSpecification section = 
						      new UserSpecification(new SearchCriteria("section", ":", assetMasterData.getSection().toUpperCase()));
					specifications.add(section);
				}
				if(assetMasterData.getLocationPosition() != null && !assetMasterData.getLocationPosition().isEmpty()) {
					UserSpecification locationPosition = 
						      new UserSpecification(new SearchCriteria("locationPosition", ":", assetMasterData.getLocationPosition().toUpperCase()));
					specifications.add(locationPosition);
				}if(assetMasterData.getKilometer() != null ) {
					UserSpecification kilometer = 
						      new UserSpecification(new SearchCriteria("kilometer", ">", assetMasterData.getKilometer()));
					specifications.add(kilometer);
				}if(assetMasterData.getElementarySection() != null && !assetMasterData.getElementarySection().isEmpty()) {
					UserSpecification elementarySection = 
						      new UserSpecification(new SearchCriteria("elementarySection", ":", assetMasterData.getElementarySection().toUpperCase()));
					specifications.add(elementarySection);
				}
				
				if(!specifications.isEmpty()) {					
					if(specifications.size() == 1)
						results = assetMastersRepository.findAll(specifications.get(0));
					else if(specifications.size() == 2)
						results = assetMastersRepository.findAll(Specification.where(specifications.get(0)).and(specifications.get(1)));
					else if(specifications.size() == 3)
						results = assetMastersRepository.findAll(Specification.where(specifications.get(0)).and(specifications.get(1)).and(specifications.get(2)));
					else if(specifications.size() == 4)
						results = assetMastersRepository.findAll(Specification.where(specifications.get(0)).and(specifications.get(1)).and(specifications.get(2)).and(specifications.get(3)));
					else if(specifications.size() == 5)
						results = assetMastersRepository.findAll(Specification.where(specifications.get(0)).and(specifications.get(1)).and(specifications.get(2)).and(specifications.get(3)).and(specifications.get(4)));
					else if(specifications.size() == 6)
						results = assetMastersRepository.findAll(Specification.where(specifications.get(0)).and(specifications.get(1)).and(specifications.get(2)).and(specifications.get(3)).and(specifications.get(4)).and(specifications.get(5)));
					else if(specifications.size() == 7)
						results = assetMastersRepository.findAll(Specification.where(specifications.get(0)).and(specifications.get(1)).and(specifications.get(2)).and(specifications.get(3)).and(specifications.get(4)).and(specifications.get(5)).and(specifications.get(6)));
					else if(specifications.size() == 8)
						results = assetMastersRepository.findAll(Specification.where(specifications.get(0)).and(specifications.get(1)).and(specifications.get(2)).and(specifications.get(3)).and(specifications.get(4)).and(specifications.get(5)).and(specifications.get(6)).and(specifications.get(7)));
					else if(specifications.size() == 9)
						results = assetMastersRepository.findAll(Specification.where(specifications.get(0)).and(specifications.get(1)).and(specifications.get(2)).and(specifications.get(3)).and(specifications.get(4)).and(specifications.get(5)).and(specifications.get(6)).and(specifications.get(7)).and(specifications.get(8)));
					else if(specifications.size() == 10)
						results = assetMastersRepository.findAll(Specification.where(specifications.get(0)).and(specifications.get(1)).and(specifications.get(2)).and(specifications.get(3)).and(specifications.get(4)).and(specifications.get(5)).and(specifications.get(6)).and(specifications.get(7)).and(specifications.get(8)).and(specifications.get(9)));
				    logger.info("resulst : "+results.size());
				}
			}
		}catch (Exception e) {
			logger.error("ERROR >>> while finding the search results, "+e.getMessage());
		}
		return results;
	}
}
