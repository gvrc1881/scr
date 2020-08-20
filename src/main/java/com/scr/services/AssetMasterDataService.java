package com.scr.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.model.AssetMasterData;
import com.scr.repository.AssetMastersRepository;

@Service
public class AssetMasterDataService {
	
	@Autowired
	private AssetMastersRepository assetMastersRepository;
	
	public List<AssetMasterData> findAll() {
		// TODO Auto-generated method stub
		return assetMastersRepository.findAll();
	}

	public void save(AssetMasterData assetMasterData) {
		// TODO Auto-generated method stub
		assetMastersRepository.save(assetMasterData);
	}

	public Optional<AssetMasterData> findAssetMasterItemById(Long id) {
		// TODO Auto-generated method stub
		return assetMastersRepository.findById(id);
	}

	public void deleteAssetMasterDataById(Long id) {
		// TODO Auto-generated method stub
		assetMastersRepository.deleteById(id);
	}
	

}
