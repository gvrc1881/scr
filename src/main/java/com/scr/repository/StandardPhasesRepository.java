package com.scr.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.scr.model.StandardPhases;

public interface StandardPhasesRepository extends JpaRepository<StandardPhases, Integer>{
	
	@Query(value = "SELECT case when count(sp)> 0 then true else false  end  FROM StandardPhases sp WHERE sp.description = :description and sp.typeOfWork  = :typeOfWork")
	Boolean existsByDescriptionAndTypeOfWork(@Param("description") String description,
			@Param("typeOfWork") String typeOfWork);
	
    Optional<StandardPhases>findByDescriptionAndTypeOfWork(String description,String typeOfWork);
    
    @Query(value = "SELECT case when count(sp)> 0 then true else false  end  FROM StandardPhases sp WHERE sp.sequence = :sequence and sp.typeOfWork  = :typeOfWork")
	Boolean existsBySequenceAndTypeOfWork(@Param("sequence") Integer sequence,
			@Param("typeOfWork") String typeOfWork);
	
    Optional<StandardPhases>findBySequenceAndTypeOfWork(Integer sequence,String typeOfWork);
    
    
    @Query(value = "SELECT case when count(sp)> 0 then true else false  end  FROM StandardPhases sp WHERE sp.name = :name and sp.typeOfWork  = :typeOfWork")
	Boolean existsByNameAndTypeOfWork(@Param("name") String name,
			@Param("typeOfWork") String typeOfWork);
	
    Optional<StandardPhases>findByNameAndTypeOfWork(String name,String typeOfWork);

}
