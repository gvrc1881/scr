package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.scr.model.ReportRepository;

public interface DailyReportsRepository extends JpaRepository<ReportRepository, Long> {
	
List<ReportRepository> findByReportCategory(String DailyProgress);

}
	

