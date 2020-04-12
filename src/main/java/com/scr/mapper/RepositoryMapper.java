/**
 * 
 */
package com.scr.mapper;

import java.sql.Timestamp;
import java.util.Calendar;
import javax.validation.Valid;

import org.springframework.stereotype.Component;

import com.scr.message.request.RepositoryRequest;
import com.scr.model.Repository;
import com.scr.model.User;
import com.scr.util.Constants;

/**
 * @author venkat
 *
 */
@Component
public class RepositoryMapper {

	public Repository prepareAddRepositoryMapper(@Valid RepositoryRequest repositoryRequest, User user) {
		Repository repository = new Repository();
		if (repositoryRequest != null && user != null) {
			repository.setIsActive(Constants.ACTIVE_STATUS_ID);
			repository.setCreatedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
			repository.setModifiedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
			repository.setCreatedBy(user);
			repository.setModifiedBy(user);
			repository.setRepositoryCode(repositoryRequest.getRepositoryCode());
			repository.setRepositoryName(repositoryRequest.getRepositoryName());
			repository.setRepositoryIp(repositoryRequest.getRepositoryIp());
			repository.setRepositoryPort(repositoryRequest.getRepositoryPort());
			repository.setRepositoryDbName(repositoryRequest.getRepositoryDbName());
			repository.setRepositoryUser(repositoryRequest.getRepositoryUser());
			repository.setRepositoryPassword(repositoryRequest.getRepositoryPassword());
		}
		return repository;
	}

	public Repository prepareUpdateRepository(Repository repositoryToUpdate, @Valid RepositoryRequest repositoryRequest,
			User user) {
		repositoryToUpdate.setModifiedBy(user);
		repositoryToUpdate.setRepositoryCode(repositoryRequest.getRepositoryCode());
		repositoryToUpdate.setRepositoryDbName(repositoryRequest.getRepositoryDbName());
		repositoryToUpdate.setRepositoryIp(repositoryRequest.getRepositoryIp());
		repositoryToUpdate.setRepositoryName(repositoryRequest.getRepositoryName());
		repositoryToUpdate.setRepositoryPassword(repositoryRequest.getRepositoryPassword());
		repositoryToUpdate.setRepositoryPort(repositoryRequest.getRepositoryPort());
		repositoryToUpdate.setRepositoryUser(repositoryRequest.getRepositoryUser());
		repositoryToUpdate.setModifiedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		return repositoryToUpdate;
	}

}
