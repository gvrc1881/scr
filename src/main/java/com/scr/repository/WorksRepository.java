package com.scr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.Works;

@Repository
public interface WorksRepository extends JpaRepository<Works, Integer> {

}
