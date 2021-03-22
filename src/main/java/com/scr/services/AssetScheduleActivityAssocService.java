package com.scr.services;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import com.scr.mapper.AssetScheduleActivityAssocMapper;
import com.scr.message.request.AssetScheduleActivityAssocRequest;
import com.scr.message.request.AssetScheduleAssocRequest;
import com.scr.message.request.MeasureOrActivityListRequest;
import com.scr.model.AssetScheduleActivityAssoc;
import com.scr.repository.AssetScheduleActivityAssocRepository;
import com.scr.util.Constants;

@Service
public class AssetScheduleActivityAssocService {
	
	static Logger logger = LogManager.getLogger(AssetScheduleActivityAssocService.class);
	
	@Autowired
	private AssetScheduleActivityAssocRepository assetSchActivityAssocRepository;
	@Autowired
	private AssetScheduleActivityAssocMapper assetSchActivityAssocDataMapper;
/*	
	public List<AssetScheduleActivityAssoc> findPaginated(int from, int to) {
		Pageable paging = PageRequest.of(from, to);
		Page<AssetScheduleActivityAssoc> pagedResult = assetSchActivityAssocRepository.findAll(paging);
		
		return pagedResult.getContent();
	}*/

	/*public List<AssetScheduleActivityAssoc> findAll() {
		// TODO Auto-generated method stub
		return assetSchActivityAssocRepository.findAll();
	}*/

	
	public AssetScheduleActivityAssoc saveAssocData(@Valid AssetScheduleActivityAssocRequest assetScheduleActivityAssoc) throws Exception {
		logger.info("Calling mapper for preparing the Asset schedule activity assoc model object");
		AssetScheduleActivityAssoc assetschData = assetSchActivityAssocDataMapper.prepareAssetScheduleActivityAssocModel(assetScheduleActivityAssoc);
		if (assetschData != null) {
			logger.info("After prepared model object, saving to Asset Master table");
			assetschData = assetSchActivityAssocRepository.save(assetschData);
			logger.info("Asset Master data saved successfully.");
			return assetschData;
		} else {
			logger.info("Preparing Asset Master Data model object failed");
			return assetschData;
		}
	}
	


	
	public String updateAssocData(@Valid AssetScheduleActivityAssocRequest assetScheduleActivityAssoc) {
		Optional<AssetScheduleActivityAssoc> assetschassocData = assetSchActivityAssocRepository.findById(assetScheduleActivityAssoc.getId());
		if(assetschassocData.isPresent()) {
			AssetScheduleActivityAssoc assetSchUpdate = assetSchActivityAssocDataMapper.prepareAssetSchActivityUpdateData(assetschassocData.get(), assetScheduleActivityAssoc);
			assetSchUpdate = assetSchActivityAssocRepository.save(assetSchUpdate);
			return Constants.JOB_SUCCESS_MESSAGE;
		}else {
			return "Invalid Asset Master Id";
		}
	}
	
		
/*	public AssetScheduleActivityAssoc save(AssetScheduleActivityAssoc assetScheduleActivityAssoc) {
		// TODO Auto-generated method stub
		return assetSchActivityAssocRepository.save(assetScheduleActivityAssoc);
	}*/

	public Optional<AssetScheduleActivityAssoc> findAssetSchAssocById(Long id) {
		// TODO Auto-generated method stub
		return assetSchActivityAssocRepository.findById(id);
	}

	public void deleteAssetSchActAssocById(Long id) {
		// TODO Auto-generated method stub
		assetSchActivityAssocRepository.deleteById(id);
	}

	public Boolean existsByAsaSeqIdAndActivityPositionIdAndMakeCodeAndModelCode(String asaSeqId, String activityPositionId,String makeCode,String modelCode) {
		 //TODO Auto-generated method stub
		return assetSchActivityAssocRepository.existsByAsaSeqIdAndActivityPositionIdAndMakeCodeAndModelCode(asaSeqId,activityPositionId,makeCode,modelCode);
	}
	
	public Boolean existsByAsaSeqIdAndactivityIdAndMakeCodeAndModelCode(String asaSeqId, String activityId,String makeCode,String modelCode) {
		 //TODO Auto-generated method stub
		return assetSchActivityAssocRepository.existsByAsaSeqIdAndactivityIdAndMakeCodeAndModelCode(asaSeqId,activityId,makeCode,modelCode);
	}
	
	public Boolean existsByAsaSeqIdAndactivityIdAndDisplayOrderAndMakeCodeAndModelCode(String asaSeqId, String activityId,BigDecimal displayOrder,String makeCode,String modelCode) {
		 //TODO Auto-generated method stub
		return assetSchActivityAssocRepository.existsByAsaSeqIdAndactivityIdAndDisplayOrderAndMakeCodeAndModelCode(asaSeqId,activityId,displayOrder,makeCode,modelCode);
	}

	public Optional<AssetScheduleActivityAssoc> findByAsaSeqIdAndActivityPositionIdAndMakeCodeAndModelCode(String asaSeqId,String activityPositionId,String makeCode,String modelCode) {
		// TODO Auto-generated method stub
		return assetSchActivityAssocRepository.findByAsaSeqIdAndActivityPositionIdAndMakeCodeAndModelCode(asaSeqId,activityPositionId,makeCode,modelCode);
	}
	public Optional<AssetScheduleActivityAssoc> findByAsaSeqIdAndActivityIdAndMakeCodeAndModelCode(String asaSeqId,String activityId,String makeCode,String modelCode) {
		// TODO Auto-generated method stub
		return assetSchActivityAssocRepository.findByAsaSeqIdAndActivityIdAndMakeCodeAndModelCode(asaSeqId,activityId,makeCode,modelCode);
	}
	public Optional<AssetScheduleActivityAssoc> findByAsaSeqIdAndActivityIdAndDisplayOrderAndMakeCodeAndModelCode(String asaSeqId,String activityId,BigDecimal displayOrder,String makeCode,String modelCode) {
		// TODO Auto-generated method stub
		return assetSchActivityAssocRepository.findByAsaSeqIdAndActivityIdAndDisplayOrderAndMakeCodeAndModelCode(asaSeqId,activityId,displayOrder,makeCode,modelCode);
	}


	public void save(AssetScheduleActivityAssoc asaa) {
		// TODO Auto-generated method stub
		assetSchActivityAssocRepository.save(asaa);
	}


	public List<AssetScheduleActivityAssoc> findAll() {
		logger.info("Calling mapper for preparing to get All Asset schedule activity assoc model object");
		List<AssetScheduleActivityAssoc> asaa = new ArrayList<>();
		List<AssetScheduleActivityAssoc> assetScheduleActivityAssocs = assetSchActivityAssocRepository.findAllOrderByCreatedOnDesc();
		for (AssetScheduleActivityAssoc assetScheduleActivityAssoc : assetScheduleActivityAssocs) {
			assetScheduleActivityAssoc = assetSchActivityAssocDataMapper.prepareAssetSchActivityData(assetScheduleActivityAssoc);
			asaa.add(assetScheduleActivityAssoc);
		}
		//AssetScheduleActivityAssoc assetschassocData = assetSchActivityAssocDataMapper.prepareAssetSchActivityData(assetScheduleActivityAssocRequest,assetSchassDataRequest,measuresDataRequest);
		 return asaa;
	}




/*	public List<AssetScheduleActivityAssoc> findByDataDivInAndOrderByCreatedOnDesc(List<String> fac) {
		
		List<AssetScheduleActivityAssoc> asaa = new ArrayList<>();
		List<AssetScheduleActivityAssoc> assetScheduleActivityAssocs = assetSchActivityAssocRepository.findByDataDivInOrderByCreatedOnDesc(fac);
		for (AssetScheduleActivityAssoc assetScheduleActivityAssoc : assetScheduleActivityAssocs) {
			assetScheduleActivityAssoc = assetSchActivityAssocDataMapper.prepareAssetSchActivityData(assetScheduleActivityAssoc);
			asaa.add(assetScheduleActivityAssoc);
		}
		//AssetScheduleActivityAssoc assetschassocData = assetSchActivityAssocDataMapper.prepareAssetSchActivityData(assetScheduleActivityAssocRequest,assetSchassDataRequest,measuresDataRequest);
		 return asaa;
	}*/

}
