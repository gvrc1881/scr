package com.scr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.PowerBlocksAmendment;

public interface PowerBlocksAmendmentRepository extends JpaRepository<PowerBlocksAmendment, Long> {

	Optional<PowerBlocksAmendment> findByPbOperationSeqId(String pbOperationId);

}
