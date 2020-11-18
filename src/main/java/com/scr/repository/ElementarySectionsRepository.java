/**
 * 
 */
package com.scr.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.scr.model.ElementarySections;

/**
 * @author opentaps
 *
 */
@Repository
public interface ElementarySectionsRepository extends JpaRepository<ElementarySections, Long> {

	List<ElementarySections> findByFacilityId(String facilityId);
		
	@Query(value = "select * from ElementarySection  order by elementary_section_code ASC",
            nativeQuery=true
    )
    public List<ElementarySections> findAllOrderByElementarySectionCodeAsc();
	
     Boolean existsByElementarySectionCode(String elementarySectionCode);
	
	Optional<ElementarySections> findByElementarySectionCode(String elementarySectionCode);
}
