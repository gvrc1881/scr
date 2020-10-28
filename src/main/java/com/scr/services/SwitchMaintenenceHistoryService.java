package com.scr.services;

import java.util.List;

import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.PbSwitchControl;
import com.scr.model.SwitchMaintenenceHistory;
import com.scr.repository.SwitchMaintenenceHistoryRepository;

@Service
public class SwitchMaintenenceHistoryService {
	
	static Logger logger = LogManager.getLogger(SwitchMaintenenceHistoryService.class);
	
	@Autowired
	private PbSwitchControlService pbSwitchControlService;
	
	@Autowired
	private SwitchMaintenenceHistoryRepository  switchMaintenenceHistoryRepository;

	public void save(SwitchMaintenenceHistory switchMaintenenceHistory) {
		// TODO Auto-generated method stub
		switchMaintenenceHistoryRepository.save(switchMaintenenceHistory);
	}

	public List<SwitchMaintenenceHistory> findByPbOperationSeqId(String pbId) {
		// TODO Auto-generated method stub
		return switchMaintenenceHistoryRepository.findByPbOperationSeqId(pbId);
	}

	public Optional<SwitchMaintenenceHistory> findByPbOperationSeqIdAndIoLocationAndIoType(String pbOperationSeqId,
			String ioLocation, String ioType) {
		// TODO Auto-generated method stub
		return switchMaintenenceHistoryRepository.findByPbOperationSeqIdAndIoLocationAndIoType(pbOperationSeqId,ioLocation,ioType);
	}

	public List<SwitchMaintenenceHistory> prepareSMHDataForSwitchOperation(String pbId, String section,
			List<String> elementarySectionCode) {

		List<SwitchMaintenenceHistory> switchMaintenenceHistoryList = this
				.findByPbOperationSeqId(pbId);
		List<SwitchMaintenenceHistory> preparedSwitchMaintenenceHistory = this
				.findByPbOperationSeqId(pbId);
		List<PbSwitchControl> pbSwitchControlList = pbSwitchControlService.findByPbExtentTypeAndPbExtentCodeIn(section,
				elementarySectionCode);
		logger.info("***assigned data size ***" + preparedSwitchMaintenenceHistory.size());
			if (pbSwitchControlList.size() == switchMaintenenceHistoryList.size()) {
				return switchMaintenenceHistoryList;
			} else {
				for (PbSwitchControl pbSwitchControl : pbSwitchControlList) {
					int flag = 0;
							for (SwitchMaintenenceHistory switchMaintenenceHistory : switchMaintenenceHistoryList) {
								//logger.info("*** in inner loop **io location*****" + switchMaintenenceHistory.getIoLocation()+ "** switch id***" + pbSwitchControl.getSwitchId());
								if (pbSwitchControl.getSwitchId().equals(switchMaintenenceHistory.getIoLocation())) {
									logger.info("*** get location ****" + switchMaintenenceHistory.getIoLocation()
											+ "*** switch id **" + pbSwitchControl.getSwitchId());
											boolean b = switchMaintenenceHistoryList.remove(switchMaintenenceHistory);
											logger.info("*** b values ***"+b);
									flag = 1;
									//logger.info("after remove"+flag);
								}
							}
						if (flag == 0) {
							SwitchMaintenenceHistory SMH = new SwitchMaintenenceHistory();
							SMH.setIoLocation(pbSwitchControl.getSwitchId());
							SMH.setIoType(pbSwitchControl.getSwitchType());
							logger.info("***before data size ***" + preparedSwitchMaintenenceHistory.size());
							preparedSwitchMaintenenceHistory.add(SMH);
						}
				}
				
				//return preparedSwitchMaintenenceHistory;
				
			}
		return preparedSwitchMaintenenceHistory;
		
	
	}

}
