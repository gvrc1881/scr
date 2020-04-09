package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.AssetMasterData;

public interface AssetMastersRepository extends JpaRepository<AssetMasterData, Long>{
	List<AssetMasterData> findAll();

}
