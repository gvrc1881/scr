package com.scr.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scr.model.Make;
import com.scr.model.Model;
import com.scr.model.TestInspection;


@Repository
public interface TestInspectionRepository extends JpaRepository<TestInspection, Long>{
	
	@Query(value = "SELECT case when count(ti)> 0 then true else false  end  FROM TestInspection ti WHERE ti.name = :name and ti.makeCode = :makeCode and ti.modelCode = :modelCode")
	Boolean existsByNameAndMakeCodeAndModelCode(@Param("name") String name,@Param("makeCode") Make makeCode,@Param("modelCode") Model modelCode);	
	
	
	Optional<TestInspection> findByNameAndMakeCodeAndModelCode(String name,Make makeCode,Model modelCode);
	
	@Query(value = "SELECT case when count(ti)> 0 then true else false  end  FROM TestInspection ti WHERE ti.makeCode = :makeCode and ti.modelCode = :modelCode and ti.description = :description ")
	Boolean existsByMakeCodeAndModelCodeAndDescription(@Param("makeCode") Make makeCode,@Param("modelCode") Model modelCode,@Param("description") String description);	
	
	
	Optional<TestInspection> findByMakeCodeAndModelCodeAndDescription(Make makeCode,Model modelCode,String description);

}
