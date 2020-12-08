package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scr.model.Facility;
import com.scr.model.User;
import com.scr.model.UserJurisdiction;
import com.scr.model.WorkGroup;
import com.scr.model.Works;

@Repository
public interface UserJurisdictionRepository extends JpaRepository<UserJurisdiction, Long>{

	Optional<UserJurisdiction> findByUserIdAndWorkIdAndWorkGroupIdAndSection(User user, Works works,
			WorkGroup workGroup, WorkGroup section);

	List<UserJurisdiction> findByUserIdAndWorkIdAndWorkGroupId(User user, Works works, WorkGroup workGroup);

	@Query(value = "select distinct workId from UserJurisdiction uj where uj.userId = :userData")
	List<Works> findDistinctWorkIdByUserId(@Param("userData")User userData);

	@Query(value = "select distinct workGroupId from UserJurisdiction uj where uj.workId = :works and uj.userId = :user")
	List<WorkGroup> findDistinctWorkGroupIdByWorkIdAndUserId(@Param("works")Works works,@Param("user") User user);

}
