package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.Facility;
import com.scr.model.TssSpSspAssoc;


@Repository

public interface TssSpSspAssocRepository extends JpaRepository<TssSpSspAssoc, Long> {	

	//List<TssSpSspAssoc> findByTssFacilityId(Long id);

	List<TssSpSspAssoc> findByTssFacilityId(Facility facility);

	Optional<TssSpSspAssoc> findBySspSpFacilityId(String string);

}
