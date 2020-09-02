package com.scr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.Works;

@Repository
public interface WorksRepository extends JpaRepository<Works, Integer> {

	Boolean existsByWorkName(String workName);

	Optional<Works> findByWorkName(String workName);

}
