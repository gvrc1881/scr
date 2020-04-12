/**
 * 
 */
package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author vt1056
 *
 */
@Repository
public interface RepositoryRepository extends JpaRepository<com.scr.model.Repository, Long>{

	List<com.scr.model.Repository> findAllByIsActiveOrderByModifiedDateDesc(Integer isActive);

	com.scr.model.Repository findByRepositoryId(Integer repositoryId);

	Boolean existsByRepositoryIpAndIsActive(String repositoryIp, Integer isActive);

	Boolean existsByRepositoryCodeAndIsActive(String repositoryCode, Integer isActive);

	Boolean existsByRepositoryNameAndIsActive(String repositoryName, Integer isActive);

	com.scr.model.Repository findByRepositoryNameAndIsActive(String repositoryName, int isActive);

	com.scr.model.Repository findByRepositoryCode(String repositoryCode);

}
