package com.scr.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.scr.model.ProductCategoryMember;


@Repository
public interface ProductCategoryMemberRepository extends JpaRepository<ProductCategoryMember, Long> {
	//repository
	List<ProductCategoryMember> findByProductCategoryId(String productCategoryId);
	
	@Query(value = "select * from product_category_member where product_category_id in('OHE_FIXED_ASSET','PSI_FIXED_ASSET','RCC_FIXED_ASSET') order by product_id ASC",
            nativeQuery=true )
    public List<ProductCategoryMember> findByProductId(String productCategoryId);
	
	@Query(value = "SELECT case when count(pcm)> 0 then true else false  end  FROM ProductCategoryMember pcm WHERE pcm.productCategoryId = :productCategoryId and pcm.productId  = :productId")
	Boolean existsByProductCategoryIdAndProductId(@Param("productCategoryId") String productCategoryId,
			@Param("productId") String productId);
	
    Optional<ProductCategoryMember>findByProductCategoryIdAndProductId(String productCategoryId,String productId);


}
