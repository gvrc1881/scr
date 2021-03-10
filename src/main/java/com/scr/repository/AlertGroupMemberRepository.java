package com.scr.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.scr.model.AlertGroupMember;

@Repository
public interface AlertGroupMemberRepository extends JpaRepository<AlertGroupMember, Long>{
	
	Boolean existsByName(String name);

	Boolean existsByDescription(String description);
	
	Optional<AlertGroupMember> findByName(String name);
	
	Optional<AlertGroupMember> findByDescription(String description);

}
