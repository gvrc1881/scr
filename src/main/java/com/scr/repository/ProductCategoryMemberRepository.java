package com.scr.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scr.model.PrecautionaryMeasure;
import com.scr.model.PrecautionaryMeasuresMaster;
import com.scr.model.Product;
import com.scr.model.ProductCategoryMember;


@Repository
public interface ProductCategoryMemberRepository extends JpaRepository<ProductCategoryMember, Long> {
	//repository
	@Query(value = "select  productId from ProductCategoryMember  where productCategoryId = :productCategoryId")
	List<String> findByProductCategoryId(@Param("productCategoryId")String productCategoryId);
	
	//@Query(value = "select distinct(product_id) from product_category_member where product_category_id in('OHE_FIXED_ASSET','PSI_FIXED_ASSET','RCC_FIXED_ASSET') order by product_id ASC",
      //      nativeQuery=true )
	@Query(value = "select distinct productId from ProductCategoryMember  where productCategoryId in :depotTypes Order by productId Asc")
    public List<String> findDistinctProductIdByProductCategoryIdInOrderByProductIdAsc(@Param("depotTypes")List<String> depotTypes);
	
	@Query(value = "SELECT case when count(pcm)> 0 then true else false  end  FROM ProductCategoryMember pcm WHERE pcm.productCategoryId = :productCategoryId and pcm.productId  = :productId")
	Boolean existsByProductCategoryIdAndProductId(@Param("productCategoryId") String productCategoryId,
			@Param("productId") String productId);
	
    Optional<ProductCategoryMember>findByProductCategoryIdAndProductId(String productCategoryId,String productId);
    @Query(value = "select  productId from ProductCategoryMember  where productCategoryId = :depotType Order by productId Asc ")
	List<String> findByProductCategoryIdOrderByProductIdAsc(@Param("depotType") String depotType);
    
	List<ProductCategoryMember> getByProductId(Product productId);


}
