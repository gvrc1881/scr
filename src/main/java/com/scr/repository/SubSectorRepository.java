package com.scr.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.scr.model.SubSector;


@Repository
public interface SubSectorRepository extends JpaRepository<SubSector, Long>{
	
   Boolean existsBySubSectorCode(String subSectorCode);
	
	Optional<SubSector> findBySubSectorCode(String subSectorCode);

}
