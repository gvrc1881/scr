package com.scr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.TssFeederMaster;

@Repository
public interface TssFeederMasterRepository extends JpaRepository<TssFeederMaster, Long> {

}
