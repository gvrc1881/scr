/**
 * 
 */
package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.Permissions;

/**
 * @author vt1056
 *
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permissions, Long>{

	List<Permissions> findAllByStatusId(Integer statusId);

}
