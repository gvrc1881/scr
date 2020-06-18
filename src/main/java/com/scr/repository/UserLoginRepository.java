package com.scr.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.UserLogin;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLogin, Long>{

	List<UserLogin> findByCreatedStampLessThanEqualAndCreatedStampGreaterThanAndUserLoginIdIn(Timestamp currenTimestamp,
			Timestamp previousTimestamp, List<String> userLogins);

	List<UserLogin> findByLastUpdatedStampLessThanEqualAndLastUpdatedStampGreaterThanAndUserLoginIdIn(
			Timestamp currenTimestamp, Timestamp previousTimestamp, List<String> userLogins);

}
