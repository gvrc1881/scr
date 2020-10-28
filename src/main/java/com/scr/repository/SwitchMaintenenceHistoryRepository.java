package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.SwitchMaintenenceHistory;

@Repository
public interface SwitchMaintenenceHistoryRepository extends JpaRepository<SwitchMaintenenceHistory, Long> {

	List<SwitchMaintenenceHistory> findByPbOperationSeqId(String pbId);

	Optional<SwitchMaintenenceHistory> findByPbOperationSeqIdAndIoLocationAndIoType(String pbOperationSeqId,
			String ioLocation, String ioType);

}
