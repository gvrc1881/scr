package com.scr.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.scr.model.PrecautionaryMeasuresMaster;

@Repository
public interface PrecautionaryMeasureMasterRepository extends JpaRepository<PrecautionaryMeasuresMaster, Integer>{
	@Query("FROM PrecautionaryMeasuresMaster ORDER BY precautionaryMeasure ASC")
    List<PrecautionaryMeasuresMaster> findAllOrderByPrecautionaryMeasureAsc();
	
	Optional<PrecautionaryMeasuresMaster> findByActiveOrderByPrecautionaryMeasureAsc(String active);
	
    Boolean existsByPrecautionaryMeasure(String precautionaryMeasure);
	Optional<PrecautionaryMeasuresMaster> findByPrecautionaryMeasure(String precautionaryMeasure);
	
}


