package com.scr.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.scr.model.GantryMasterData;

@Repository
public interface GantryMasterDataRepository extends JpaRepository<GantryMasterData, Long>{
	
   Boolean existsByGantryCode(String gantryCode);
	
	Optional<GantryMasterData> findByGantryCode(String gantryCode);

}
