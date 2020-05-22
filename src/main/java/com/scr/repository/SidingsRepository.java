package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.scr.model.SidingDetails;
//sidings Repository
@Repository
public interface SidingsRepository extends JpaRepository<SidingDetails, Long>{
	List<SidingDetails> findAll();

}
