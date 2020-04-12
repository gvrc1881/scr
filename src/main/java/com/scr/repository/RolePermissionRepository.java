/**
 * 
 */
package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.RolePermissions;

/**
 * @author vt1056
 *
 */
public interface RolePermissionRepository extends JpaRepository<RolePermissions, Long>{

	List<RolePermissions> findAllByStatusId(Integer statusId);

	Optional<RolePermissions> findByRoleTypeId(Integer roleId);

}
