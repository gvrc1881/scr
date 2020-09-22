package com.scr.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.model.ProductCategoryMember;
import com.scr.repository.ProductCategoryMemberRepository;

@Service
public class ProductCategoryMemberService {
	
	@Autowired
	private ProductCategoryMemberRepository productCategoryMemberRepository;

	
	public List<ProductCategoryMember> findAll(){
		return productCategoryMemberRepository.findAll();
	}
	
	public void save(ProductCategoryMember productCategoryMember) {
		productCategoryMemberRepository.save(productCategoryMember);
	}
	public Optional<ProductCategoryMember> findProductCategoryMemberById(Long id) {
		// TODO Auto-generated method stub
		return productCategoryMemberRepository.findById(id);

	}

	public void deleteProductCategoryMemberById(Long id) {
		// TODO Auto-generated method stub
		productCategoryMemberRepository.deleteById(id);
	}

}
