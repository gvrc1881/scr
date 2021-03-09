package com.scr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.Receipents;

@Repository
public interface ReceipentsRepository extends JpaRepository<Receipents, Long>{

}
