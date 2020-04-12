/**
 * 
 */
package com.scr.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.Repository;
import com.scr.repository.RepositoryRepository;

/**
 * @author vt1056
 *
 */
@Service
public class RepositoryService {

	@Autowired
	private RepositoryRepository repositoryRepository;
	
	public List<Repository> findAllByIsActiveOrderByModifiedDateDesc(Integer isActive) {		
		return repositoryRepository.findAllByIsActiveOrderByModifiedDateDesc(isActive);
	}

	public Repository saveRepository(Repository repository) {		
		return repositoryRepository.save(repository);
	}

	public Repository findByRepositoryId(Integer repositoryId) {		
		return repositoryRepository.findByRepositoryId(repositoryId);
	}

	public Boolean existsByRepositoryIpByIsActive(String repositoryIp, Integer isActive) {		
		return repositoryRepository.existsByRepositoryIpAndIsActive(repositoryIp, isActive);
	}

	public Boolean existsByRepositoryCodeByIsActive(String repositoryCode, Integer isActive) {	
		return repositoryRepository.existsByRepositoryCodeAndIsActive(repositoryCode, isActive);
	}

	public Boolean existsByRepositoryNameByIsActive(String repositoryName, Integer isActive) {		
		return repositoryRepository.existsByRepositoryNameAndIsActive(repositoryName, isActive);
	}

	public Repository findByRepositoryNameAndIsActive(String repositoryName, int isActive) {		
		return repositoryRepository.findByRepositoryNameAndIsActive(repositoryName, isActive);
	}

	public Repository findByRepositoryCode(String repositoryCode) {	
		return repositoryRepository.findByRepositoryCode(repositoryCode);
	}

}
