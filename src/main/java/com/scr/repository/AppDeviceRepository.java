package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.AppDevice;

@Repository
public interface AppDeviceRepository extends JpaRepository<AppDevice, Long>{

	Optional<AppDevice> findBySecurityCodeAndAppNameAndActiveStatus(String phoneNum, String appName, String string);

	Optional<AppDevice> findBySecurityCodeAndAppNameAndActiveStatusAndDeviceId(String phoneNum, String appName,
			String string, String imeiNo);
	List<AppDevice> findByDeviceIdAndSecurityCodeAndAppNameAndActiveStatus(String imeiNo, String phoneNum, String appName,
			String string);

}
