package com.scr.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.scr.model.Sector;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long>{
	
    Boolean existsBySectorCode(String sectorCode);
	
	Optional<Sector> findBySectorCode(String sectorCode);

}
