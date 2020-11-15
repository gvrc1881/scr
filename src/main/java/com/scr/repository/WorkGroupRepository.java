package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.scr.model.WorkGroup;
import com.scr.model.Works;

public interface WorkGroupRepository extends JpaRepository<WorkGroup, Integer>{

	List<WorkGroup> findByWorkId(Works works);

	List<WorkGroup> findByCode(String code);

}
