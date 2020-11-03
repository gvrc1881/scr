package com.scr.mapper;

	import java.sql.Timestamp;
	import java.util.Calendar;
	import java.util.Optional;

	import javax.validation.Valid;
	import org.apache.log4j.LogManager;
	import org.apache.log4j.Logger;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Component;

	import com.scr.model.AssetScheduleActivityAssoc;
	import com.scr.model.AssetScheduleAssoc;
	import com.scr.repository.AssetSchAssoRepository;
	import com.scr.repository.MeasureOrActivityListRepository;
	import com.scr.util.Constants;
	import com.scr.message.request.AssetScheduleActivityAssocRequest;

	@Component
	public class AssetScheduleActivityAssocMapper {
		
		static Logger logger = LogManager.getLogger(AssetScheduleActivityAssocMapper.class);
		
	
		@Autowired
		private MeasureOrActivityListRepository measureOrActivityListRepository;
		
		@Autowired
		private AssetSchAssoRepository assetSchAssocRepository;
		
		public AssetScheduleActivityAssoc prepareAssetScheduleActivityAssocModel(@Valid AssetScheduleActivityAssocRequest assetSchDataRequest) throws Exception {
			AssetScheduleActivityAssoc assetScheduleActivityAssoc = null;
			logger.info("Preparing the Asset sch activity assoc Data model object");
			try {
			if (assetSchDataRequest != null) {
				
				assetScheduleActivityAssoc = new AssetScheduleActivityAssoc();	
				
				/*Optional<AssetScheduleAssoc> assetOptional = assetSchAssocRepository.findById(assetSchDataRequest.getAsaSeq());
				if(assetOptional.isPresent()) {
					assetScheduleActivityAssoc.setAsaSeqId(assetOptional.get());
				}*/
				assetScheduleActivityAssoc.setAsaSeqId(assetSchDataRequest.getAsaSeqId());
				assetScheduleActivityAssoc.setActivityId(assetSchDataRequest.getActivityId());
				assetScheduleActivityAssoc.setActivityPositionId(assetSchDataRequest.getActivityPositionId());
				assetScheduleActivityAssoc.setMakeCode(assetSchDataRequest.getMakeCode());
				assetScheduleActivityAssoc.setModelCode(assetSchDataRequest.getModelCode());
				assetScheduleActivityAssoc.setActivityFlag(assetSchDataRequest.getActivityFlag());
				assetScheduleActivityAssoc.setDisplayOrder(assetSchDataRequest.getDisplayOrder());
				assetScheduleActivityAssoc.setLowerLimit(assetSchDataRequest.getLowerLimit());
				assetScheduleActivityAssoc.setUpperLimit(assetSchDataRequest.getUpperLimit());
				assetScheduleActivityAssoc.setDescription(assetSchDataRequest.getDescription());
				assetScheduleActivityAssoc.setCreatedBy(assetSchDataRequest.getCreatedBy());
				//assetScheduleActivityAssoc.setSeqId(assetSchDataRequest.getId().toString());
				assetScheduleActivityAssoc.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
				assetScheduleActivityAssoc.setCreatedStamp(new Timestamp(Calendar.getInstance().getTime().getTime()));
				assetScheduleActivityAssoc.setCreatedTxStamp(new Timestamp(Calendar.getInstance().getTime().getTime()));
				}
			logger.info("Prepared model object = "+assetScheduleActivityAssoc);
			}catch (Exception e) {
				throw new Exception(e.getMessage());
			}
			
			return assetScheduleActivityAssoc;
		}
		
		public AssetScheduleActivityAssoc prepareAssetSchActivityUpdateData(AssetScheduleActivityAssoc assetSchActivityData,
				@Valid AssetScheduleActivityAssocRequest assetSchDataRequest) {

						
			if (assetSchDataRequest != null) {

				logger.info(assetSchDataRequest);		
							
				
				assetSchActivityData.setAsaSeqId(assetSchDataRequest.getAsaSeqId());
				assetSchActivityData.setActivityId(assetSchDataRequest.getActivityId());
				assetSchActivityData.setActivityPositionId(assetSchDataRequest.getActivityPositionId());
				assetSchActivityData.setMakeCode(assetSchDataRequest.getMakeCode());
				assetSchActivityData.setModelCode(assetSchDataRequest.getModelCode());
				assetSchActivityData.setActivityFlag(assetSchDataRequest.getActivityFlag());
				assetSchActivityData.setDisplayOrder(assetSchDataRequest.getDisplayOrder());
				assetSchActivityData.setLowerLimit(assetSchDataRequest.getLowerLimit());
				assetSchActivityData.setUpperLimit(assetSchDataRequest.getUpperLimit());
				assetSchActivityData.setDescription(assetSchDataRequest.getDescription());
				assetSchActivityData.setCreatedBy(assetSchDataRequest.getCreatedBy());
				assetSchActivityData.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
				assetSchActivityData.setLastUpdatedStamp(new Timestamp(Calendar.getInstance().getTime().getTime()));
				assetSchActivityData.setLastUpdatedTxStamp(new Timestamp(Calendar.getInstance().getTime().getTime()));

				
			}
			return assetSchActivityData;
		}

	}



