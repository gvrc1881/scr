package com.scr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.SwitchMaintenenceHistoryAmendment;

public interface SwitchMaintenenceHistoryAmendmentRepository extends JpaRepository<SwitchMaintenenceHistoryAmendment, Long> {

	Optional<SwitchMaintenenceHistoryAmendment> findBySeqId(String switchId);

}
