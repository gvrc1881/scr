package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.PbSwitchControl;

public interface PBSwitchControlRepository extends JpaRepository<PbSwitchControl, Long> {
	List<PbSwitchControl> findDistinctBySwitchType(String switchType);

}
