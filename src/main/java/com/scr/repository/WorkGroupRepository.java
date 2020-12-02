package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;


import com.scr.model.WorkGroup;
import com.scr.model.Works;

public interface WorkGroupRepository extends JpaRepository<WorkGroup, Long>{

	List<WorkGroup> findByWorkId(Works works);

	List<WorkGroup> findByCode(String code);
	
	Optional<WorkGroup> findById(Long id);

	Optional<WorkGroup> findById(Integer id);

	
	@Query(value = "SELECT case when count(wk)> 0 then true else false  end  FROM WorkGroup wk WHERE wk.workId = :work and wk.workGroup = :group and wk.section = :section")
	Boolean existsByworkIdAndWorkGroupAndSection(@Param("work") Works work, @Param("group") String group,
			@Param("section") String section);

	Optional<WorkGroup> findByworkIdAndWorkGroupAndSection(Works work, String group, String section);

	List<WorkGroup> findByWorkGroup(String workGroup);

	List<WorkGroup> getByWorkId(Works works);





}
