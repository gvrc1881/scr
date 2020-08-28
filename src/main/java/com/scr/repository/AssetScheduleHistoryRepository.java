package com.scr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.AssetsScheduleHistory;

@Repository
public interface AssetScheduleHistoryRepository extends JpaRepository<AssetsScheduleHistory, Long> {

	Optional<AssetsScheduleHistory> findAshById(Long id);

}
