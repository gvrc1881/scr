package com.scr.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.scr.model.ProductCategoryMember;



public interface ProductCategoryMemberRepository extends JpaRepository<ProductCategoryMember, Long> {
	List<ProductCategoryMember> findByProductCategoryId(String productCategoryId);

}
