package com.scr.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.scr.model.SidingDetails;
@Repository
public interface SidingsRepository extends JpaRepository<SidingDetails, Long>{
	List<SidingDetails> findAll();
	boolean existsBySidingCode(String sidingCode);
	Optional<SidingDetails> findBySidingCode(String sidingCode);


}
