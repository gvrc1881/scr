package com.scr.services;

import java.util.List;
import java.util.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.model.ProductAssociation;
import com.scr.repository.ProductAssociationRepository;

@Service
public class ProductAssociationService {
	
	static Logger logger = LogManager.getLogger(ProductAssociationService.class);


	@Autowired
	private ProductAssociationRepository productAssociationRepository;
	
	public List<ProductAssociation> findAll() {
		// TODO Auto-generated method stub
		return productAssociationRepository.findAll();
	}
	
	public void save(ProductAssociation productAssociation) {
		productAssociationRepository.save(productAssociation);
	}
	
	public Optional<ProductAssociation> findProductAssociationById(Long id) {
		// TODO Auto-generated method stub
		return productAssociationRepository.findById(id);
	}

	public void deleteProductAssociationById(Long id) {
		// TODO Auto-generated method stub
		productAssociationRepository.deleteById(id);
	}


}
