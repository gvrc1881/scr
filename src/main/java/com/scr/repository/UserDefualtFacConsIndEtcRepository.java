package com.scr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.UserDefualtFacConsIndEtc;

@Repository
public interface UserDefualtFacConsIndEtcRepository extends JpaRepository<UserDefualtFacConsIndEtc, Long> {

	Optional<UserDefualtFacConsIndEtc> findByUserLoginId(String userLogin);

}
