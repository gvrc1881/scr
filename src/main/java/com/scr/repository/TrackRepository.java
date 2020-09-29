package com.scr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scr.model.Facility;
import com.scr.model.Track;

@Repository
public interface TrackRepository extends JpaRepository<Track, Integer>{

	@Query(value = "SELECT case when count(t)> 0 then true else false  end  FROM Track t WHERE t.facilityId = :facility ")
	Boolean existsByFacilityId(@Param("facility") Facility facility);

}
