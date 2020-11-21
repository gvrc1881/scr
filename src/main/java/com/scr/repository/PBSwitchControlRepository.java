package com.scr.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.scr.model.PbSwitchControl;

public interface PBSwitchControlRepository extends JpaRepository<PbSwitchControl, Long> {
	List<PbSwitchControl> findDistinctSwitchTypeBySwitchType(String switchType);

	List<PbSwitchControl> findByPbExtentTypeAndPbExtentCodeIn(String extentTyp , List<String> extentCode);
	
	Optional<PbSwitchControl> findByPbExtentCode(String pbExtentCode);


}
