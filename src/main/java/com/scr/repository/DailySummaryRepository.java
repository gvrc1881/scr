package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.DailyProgressSummery;

public interface DailySummaryRepository extends JpaRepository<DailyProgressSummery,Long>{
 List<DailyProgressSummery> findAll();
}
