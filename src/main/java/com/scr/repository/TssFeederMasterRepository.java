package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.scr.model.TssFeederMaster;

@Repository
public interface TssFeederMasterRepository extends JpaRepository<TssFeederMaster, Long> {
	@Query("FROM TssFeederMaster ORDER BY feederName ASC")
    List<TssFeederMaster> findAllOrderByFeederNameAsc();
}
