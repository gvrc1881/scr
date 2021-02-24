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
import com.scr.message.request.AssetMasterDataSearchRequest;
import com.scr.message.response.AssetStatusUpdateResponse;
import com.scr.model.AssetMasterData;
import com.scr.model.AssetMasterDataFormParameter;
import com.scr.repository.AssetMasterFormParameterRepository;
import com.scr.repository.AssetMastersRepository;
import com.scr.search.models.SearchCriteria;
import com.scr.specifications.AssetMasterDataSpecification;

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
	
	
	public List<AssetMasterData> findPaginated(int from, int to,List<String> fac) {
		logger.info("Fetching data from page "+from+" to page "+to);
		Pageable paging = PageRequest.of(from, to);
		//Page<AssetMasterData> pagedResult = assetMastersRepository.findAll(paging);
		Page<AssetMasterData> pagedResult = assetMastersRepository.findByFacilityIdIn(paging,fac);
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
		List<AssetStatusUpdateResponse> assetStatusUpdate = new ArrayList<>();
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

	public List<AssetMasterData> findSearch(AssetMasterDataSearchRequest assetMasterData) {
		List<AssetMasterData> results = null;
		try {
			if(assetMasterData != null) {
				List<AssetMasterDataSpecification> specifications = new ArrayList<AssetMasterDataSpecification>();
				
				if(assetMasterData.getType() != null && !assetMasterData.getType().isEmpty()) {
					AssetMasterDataSpecification type = 
						      new AssetMasterDataSpecification(new SearchCriteria("type", ":", assetMasterData.getType().toUpperCase()));
					specifications.add(type);
				}
				if(assetMasterData.getFacilityId() != null && !assetMasterData.getFacilityId().isEmpty()) {
					AssetMasterDataSpecification facilityId = 
						      new AssetMasterDataSpecification(new SearchCriteria("facilityId", ":", assetMasterData.getFacilityId().toUpperCase()));
					specifications.add(facilityId);
				}
				if(assetMasterData.getAssetType() != null) {					
					AssetMasterDataSpecification assetType = 
						      new AssetMasterDataSpecification(new SearchCriteria("assetType", ":", assetMasterData.getAssetType().toUpperCase()));
					specifications.add(assetType);
				}
				if(assetMasterData.getAssetId() != null && !assetMasterData.getAssetId().isEmpty()) {
					AssetMasterDataSpecification assetId = 
						      new AssetMasterDataSpecification(new SearchCriteria("assetId", ":", assetMasterData.getAssetId().toUpperCase()));
					specifications.add(assetId);
				}
				if(assetMasterData.getAdeeSection() != null && !assetMasterData.getAdeeSection().isEmpty()) {
					AssetMasterDataSpecification adeeSection = 
						      new AssetMasterDataSpecification(new SearchCriteria("adeeSection", ":", assetMasterData.getAdeeSection().toUpperCase()));
					specifications.add(adeeSection);
				}
				if(assetMasterData.getMajorSection() != null && !assetMasterData.getMajorSection().isEmpty()) {
					AssetMasterDataSpecification majorSection = 
						      new AssetMasterDataSpecification(new SearchCriteria("majorSection", ":", assetMasterData.getMajorSection().toUpperCase()));
					specifications.add(majorSection);
				}
				if(assetMasterData.getSection() != null && !assetMasterData.getSection().isEmpty()) {
					AssetMasterDataSpecification section = 
						      new AssetMasterDataSpecification(new SearchCriteria("section", ":", assetMasterData.getSection().toUpperCase()));
					specifications.add(section);
				}
				if(assetMasterData.getLocationPosition() != null && !assetMasterData.getLocationPosition().isEmpty()) {
					AssetMasterDataSpecification locationPosition = 
						      new AssetMasterDataSpecification(new SearchCriteria("locationPosition", ":", assetMasterData.getLocationPosition().toUpperCase()));
					specifications.add(locationPosition);
				}if(assetMasterData.getKilometer() != null ) {
					AssetMasterDataSpecification kilometer = 
						      new AssetMasterDataSpecification(new SearchCriteria("kilometer", ">", assetMasterData.getKilometer()));
					specifications.add(kilometer);
				}if(assetMasterData.getElementarySection() != null && !assetMasterData.getElementarySection().isEmpty()) {
					AssetMasterDataSpecification elementarySection = 
						      new AssetMasterDataSpecification(new SearchCriteria("elementarySection", ":", assetMasterData.getElementarySection().toUpperCase()));
					specifications.add(elementarySection);
				}
				
				if(!specifications.isEmpty()) {				
					Pageable paging = PageRequest.of(assetMasterData.getFrom(), assetMasterData.getTo());
					Page<AssetMasterData> data = null;
					if(specifications.size() == 1)
						data = assetMastersRepository.findAll(specifications.get(0), paging);
					else if(specifications.size() == 2) 
						data = assetMastersRepository.findAll(Specification.where(specifications.get(0)).and(specifications.get(1)), paging);
					else if(specifications.size() == 3)
						data = assetMastersRepository.findAll(Specification.where(specifications.get(0)).and(specifications.get(1)).and(specifications.get(2)), paging);
					else if(specifications.size() == 4)
						data = assetMastersRepository.findAll(Specification.where(specifications.get(0)).and(specifications.get(1)).and(specifications.get(2)).and(specifications.get(3)), paging);
					else if(specifications.size() == 5)
						data = assetMastersRepository.findAll(Specification.where(specifications.get(0)).and(specifications.get(1)).and(specifications.get(2)).and(specifications.get(3)).and(specifications.get(4)), paging);
					else if(specifications.size() == 6)
						data = assetMastersRepository.findAll(Specification.where(specifications.get(0)).and(specifications.get(1)).and(specifications.get(2)).and(specifications.get(3)).and(specifications.get(4)).and(specifications.get(5)), paging);
					else if(specifications.size() == 7)
						data = assetMastersRepository.findAll(Specification.where(specifications.get(0)).and(specifications.get(1)).and(specifications.get(2)).and(specifications.get(3)).and(specifications.get(4)).and(specifications.get(5)).and(specifications.get(6)), paging);
					else if(specifications.size() == 8)
						data = assetMastersRepository.findAll(Specification.where(specifications.get(0)).and(specifications.get(1)).and(specifications.get(2)).and(specifications.get(3)).and(specifications.get(4)).and(specifications.get(5)).and(specifications.get(6)).and(specifications.get(7)), paging);
					else if(specifications.size() == 9)
						data = assetMastersRepository.findAll(Specification.where(specifications.get(0)).and(specifications.get(1)).and(specifications.get(2)).and(specifications.get(3)).and(specifications.get(4)).and(specifications.get(5)).and(specifications.get(6)).and(specifications.get(7)).and(specifications.get(8)), paging);
					else if(specifications.size() == 10)
						data = assetMastersRepository.findAll(Specification.where(specifications.get(0)).and(specifications.get(1)).and(specifications.get(2)).and(specifications.get(3)).and(specifications.get(4)).and(specifications.get(5)).and(specifications.get(6)).and(specifications.get(7)).and(specifications.get(8)).and(specifications.get(9)), paging);
				    
					if(data != null)
						results = data.getContent();
					logger.info("resulst : "+results.size());
				}else {
					logger.info("hello......");
				}
			}
		}catch (Exception e) {
			logger.error("ERROR >>> while finding the search results, "+e.getMessage());
		}
		return results;
	}
}
