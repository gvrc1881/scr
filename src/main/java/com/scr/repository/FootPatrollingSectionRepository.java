package com.scr.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.scr.model.FootPatrollingSection;

public interface FootPatrollingSectionRepository extends JpaRepository<FootPatrollingSection, Long> {

	List<FootPatrollingSection> findAll();

	Boolean existsByFpSection(String fpSection);

	List<FootPatrollingSection> findByCreatedStampLessThanEqualAndCreatedStampGreaterThan(Timestamp currenTimestamp,
			Timestamp previousTimestamp);

	List<FootPatrollingSection> findByLastUpdatedStampLessThanEqualAndLastUpdatedStampGreaterThan(
			Timestamp currenTimestamp, Timestamp previousTimestamp);
	
	Optional<FootPatrollingSection> findByFpSection(String fpSection);


}
