package com.scr.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.GuidenceItem;

@Repository
public interface GuidenceItemRepository extends JpaRepository<GuidenceItem, Integer> {

	List<GuidenceItem> findAll();

	Boolean existsByAgencyRbRdsoAndDateAndLetterNo(String agencyRbRdso, Timestamp date, String letterNo);

	Optional<GuidenceItem> findByAgencyRbRdsoAndDateAndLetterNo(String agencyRbRdso, Timestamp date, String letterNo);


	
}
