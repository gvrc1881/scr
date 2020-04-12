/**
 * 
 */
package com.scr.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.DivisionsHistory;
import com.scr.repository.DivisionHistoryRepository;
import com.scr.util.Helper;

/**
 * @author vt1056
 *
 */
@Service
public class DivisionHistoryService {

	@Autowired
	private DivisionHistoryRepository divisionHistoryRepository;
	
	public void saveDivisionHistory(DivisionsHistory divisionsHistory) {		
		divisionHistoryRepository.saveAndFlush(divisionsHistory);
	}

	public void saveDivisionHistoryDetails(String tableName, String repositoryCode, Integer historyId, Integer records,
			String tableStatus, String operation, List<String> faileTablesList) {
		DivisionsHistory divisionsHistory = new DivisionsHistory();
		if(faileTablesList != null) {
			divisionsHistory = divisionHistoryRepository.findByJobsHistoryIdAndTableNameAndOperationType(historyId, tableName, operation);
			divisionsHistory.setUpdatedRecords(records);
			divisionsHistory.setStatus(tableStatus);		
			divisionsHistory.setOperationType(operation);
			saveDivisionHistory(divisionsHistory);
		}else {
			divisionsHistory.setTableName(tableName);
			divisionsHistory.setDivisionId(repositoryCode);
			divisionsHistory.setUpdatedDate(Helper.currentTimeStamp());
			divisionsHistory.setJobsHistoryId(historyId);
			divisionsHistory.setUpdatedRecords(records);
			divisionsHistory.setStatus(tableStatus);
			divisionsHistory.setOperationType(operation);
			saveDivisionHistory(divisionsHistory);
		}
	}

	public List<DivisionsHistory> findByJobsHistoryIdAndStatusNotIn(Integer jobsHistoryId, String status) {		
		return divisionHistoryRepository.findByJobsHistoryIdAndStatusNotIn(jobsHistoryId, status);
	}

}
