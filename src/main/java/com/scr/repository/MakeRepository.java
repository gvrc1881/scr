package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scr.model.Make;

@Repository

public interface MakeRepository extends JpaRepository<Make, Long>{
	
	List<Make> findAll();
	
	//@Query(value = "SELECT case when count(ma)> 0 then true else false  end  FROM Make ma WHERE ma.makeCode = :makeCode")
	//Boolean existsByIdAndMakeCode(@Param("id")Long id, @Param("makeCode")String makeCode);
	
	//public boolean existMake(String makeCode);
	Boolean existsByMakeName(String makeName);
	Boolean existsByMakeCode(String makeCode);

	Optional<Make> findByMakeCode(String makeCode);

}