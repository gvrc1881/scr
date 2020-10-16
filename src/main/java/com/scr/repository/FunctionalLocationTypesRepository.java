package com.scr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.FunctionalLocationTypes;

public interface FunctionalLocationTypesRepository  extends JpaRepository<FunctionalLocationTypes, Long>{

	Optional<FunctionalLocationTypes> findByCode(String depotType);

}
