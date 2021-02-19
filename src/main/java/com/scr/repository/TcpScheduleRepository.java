package com.scr.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scr.model.Facility;
import com.scr.model.TcpSchedule;

@Repository
public interface TcpScheduleRepository extends JpaRepository<TcpSchedule, Long>{

	Optional<TcpSchedule> findByFacilityIdAndDateTime(Long facId, Date tcpsDate);

	List<TcpSchedule> findByFacilityId(Long facId);

	@Query(value = "SELECT tcp FROM TcpSchedule tcp WHERE tcp.facility = :facilityId and tcp.dateTime >= :fromDate and tcp.dateTime <= :thruDate")
	List<TcpSchedule> findByFacilityIdAndDateTimeGreaterThanAndDateTimeLessThan(@Param("facilityId") Facility facility,@Param("fromDate") Date fromDate,
			@Param("thruDate") Date thruDate);

}
