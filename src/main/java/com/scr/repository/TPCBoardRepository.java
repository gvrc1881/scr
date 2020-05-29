package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.TpcBoard;

public interface TPCBoardRepository extends JpaRepository<TpcBoard, Long>{
	List<TpcBoard> findAll();
	Boolean existsByTpcBoard(String tpcBoard);

}
