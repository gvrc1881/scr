package com.scr.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.scr.model.ProductCategoryMember;



public interface ProductCategoryMemberRepository extends JpaRepository<ProductCategoryMember, Long> {
	
	List<ProductCategoryMember> findByProductCategoryId(String productCategoryId);
	
	@Query(value = "select * from product_category_member where product_category_id in('OHE_FIXED_ASSET','PSI_FIXED_ASSET','RCC_FIXED_ASSET') order by product_id ASC",
            nativeQuery=true )
    public List<ProductCategoryMember> findByProductId(String productCategoryId);

}
