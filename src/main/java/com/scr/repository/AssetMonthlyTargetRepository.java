package com.scr.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.scr.model.AssetMonthlyTarget;

@Repository
public interface AssetMonthlyTargetRepository extends JpaRepository<AssetMonthlyTarget, Long>{
	
	List<AssetMonthlyTarget> findAll();

}
