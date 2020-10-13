package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.AssetMasterDataFormParameter;

public interface AssetMasterFormParameterRepository extends JpaRepository<AssetMasterDataFormParameter, Long>{
	
      List<AssetMasterDataFormParameter> findAll();
      List<AssetMasterDataFormParameter> findByAssetTypeAndActive(String assetType,String active);

}
