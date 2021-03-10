package com.scr.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.scr.model.AlertGroup;
import com.scr.model.AlertGroupMember;
import com.scr.model.EventAlertRecipient;
import com.scr.model.EventType;


@Repository
public interface EventAlertRecipientRepository extends JpaRepository<EventAlertRecipient, Long>{
	
	Boolean existsByName(String name);

	Boolean existsByDescription(String description);
	
	Optional<EventAlertRecipient> findByName(String name);
	
	Optional<EventAlertRecipient> findByDescription(String description);
	
	@Query(value = "SELECT case when count(ti)> 0 then true else false  end  FROM EventAlertRecipient ti WHERE ti.eventTypeId = :eventTypeId and ti.alertGroupId = :alertGroupId and ti.alertGroupMemberId = :alertGroupMemberId")
	Boolean existsByEventTypeIdAndAlertGroupIdAndAlertGroupMemberId(@Param("eventTypeId") EventType eventTypeId,@Param("alertGroupId") AlertGroup alertGroupId,@Param("alertGroupMemberId") AlertGroupMember alertGroupMemberId);	
	
	
	Optional<EventAlertRecipient> findByEventTypeIdAndAlertGroupIdAndAlertGroupMemberId(EventType eventTypeId,AlertGroup alertGroupId,AlertGroupMember alertGroupMemberId);

}
