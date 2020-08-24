package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.scr.model.PbSwitchControl;

public interface PBSwitchControlRepository extends JpaRepository<PbSwitchControl, Long> {
	List<PbSwitchControl> findDistinctBySwitchType(String switchType);

	List<PbSwitchControl> findByPbExtentTypeAndPbExtentCodeIn(String extentTyp , List<String> extentCode);

}
