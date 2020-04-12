package com.scr.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.ContentManagement;

/**
 * 
 */

/**
 * @author vt1056
 *
 */
@Repository
public interface ContentManagementRepository extends JpaRepository<ContentManagement, Long>  {

	List<ContentManagement> findByCreatedByOrderByModifiedDateDesc(Integer createdBy);	

	ContentManagement findTopByOrderByCommonFileIdDesc();

	List<ContentManagement> findByCommonFileId(Long commonFileId);

	List<ContentManagement> findByCreatedByAndGenOps(Integer createdBy, String GenOps);

}
