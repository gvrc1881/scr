package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.scr.model.StatusItem;

public interface StatusItemRepository extends JpaRepository<StatusItem, Long> {
	List<StatusItem> findByStatusTypeId(String statusTypeId);

	List<StatusItem> findByStatusTypeIdIn(List<String> statusTypeList);

}
