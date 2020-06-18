package com.scr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.AppDeviceUnit;

@Repository
public interface AppDeviceUnitRepository extends JpaRepository<AppDeviceUnit, Long>{

	Optional<AppDeviceUnit> findByAppDeviceSeqId(String seqId);

}
