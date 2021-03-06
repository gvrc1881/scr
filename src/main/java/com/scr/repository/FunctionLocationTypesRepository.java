package com.scr.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scr.model.Facility;
import com.scr.model.FunctionalLocationTypes;

@Repository
public interface FunctionLocationTypesRepository extends JpaRepository<FunctionalLocationTypes, Long >{
	 @Query("FROM FunctionalLocationTypes ORDER BY code ASC")
	    List<FunctionalLocationTypes> findAllOrderByCodeAsc();
	 
	 Optional<FunctionalLocationTypes> findByCode(String code);
	 
}
