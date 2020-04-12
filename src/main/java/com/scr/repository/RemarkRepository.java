/**
 * 
 */
package com.scr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.Remark;

/**
 * @author vt1056
 *
 */
@Repository
public interface RemarkRepository extends JpaRepository<Remark, Long>{

}
