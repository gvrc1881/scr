package com.scr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.SwitchMaintenenceHistory;

@Repository
public interface SwitchMaintenenceHistoryRepository extends JpaRepository<SwitchMaintenenceHistory, Long> {

}
