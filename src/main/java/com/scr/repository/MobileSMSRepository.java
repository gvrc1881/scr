/**
 * 
 */
package com.scr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.MobileSMS;

/**
 * @author vt1056
 *
 */
@Repository
public interface MobileSMSRepository  extends JpaRepository<MobileSMS, Long>{

}
