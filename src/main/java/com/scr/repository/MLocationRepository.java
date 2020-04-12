/**
 * 
 */
package com.scr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.MLocation;

/**
 * @author vt1056
 *
 */
@Repository
public interface MLocationRepository extends JpaRepository<MLocation, Long>{

}
