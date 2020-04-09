package com.scr.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.scr.model.AssetScheduleAssoc;



@Repository
public interface AssetSchAssoRepository extends JpaRepository<AssetScheduleAssoc, Long>{
	
	List<AssetScheduleAssoc> findByAssetType(String assetType);
	
	

}
