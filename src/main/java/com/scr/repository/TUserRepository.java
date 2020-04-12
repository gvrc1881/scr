/**
 * 
 */
package com.scr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.TUsers;

/**
 * @author vt1056
 *
 */
@Repository
public interface TUserRepository extends JpaRepository<TUsers, Long>{


}
