package com.scr.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.AssetScheduleActivityAssoc;
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

	Optional<ContentManagement> findByIdAndCommonFileId(Long Id, Long commonFileId);

	List<ContentManagement> findByCommonFileIdAndStatusId(Long commonFileId, Integer statusId);

	//Optional<ContentManagement> findByIdAndCommonFileId(Long Id, Long commonFileId);
	
	List<ContentManagement> findAll();

	Optional<ContentManagement> findByCommonFileIdAndGenOpsAndOriginalFileName(Long commonFileId, String genOps,
			String originalFileName);

}
