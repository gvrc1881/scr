package com.scr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.Assistance;

@Repository
public interface AssistanceRepository extends JpaRepository<Assistance, Long>{

}
