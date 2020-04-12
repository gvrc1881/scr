/**
 * 
 */
package com.scr.repository;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import com.scr.model.RoleType;

/**
 * @author vt1056
 *
 */
public interface RoleTypeRepository extends JpaRepository<RoleType, Long> {
	List<RoleType> findAll();

	List<RoleType> findAllByStatusId(Integer statusId);
	
	Optional<RoleType> findById(Integer id);

	Optional<RoleType> findByRoleNameAndStatusId(String roleName, Integer statusId);

	/*
	 * @Query(
	 * name="Insert into role_permission (menu_id,sub_menu_id,role_type_id,permission_id,is_menu, "
	 * +
	 * " field_permission,created_by,created_date,modified_by,modified_date,status_id) "
	 * +
	 * " SELECT  menu_id,sub_menu_id,?0,permission_id,is_menu,field_permission,?1,current_date,?2,current_date, "
	 * + " status_id FROM role_permission where status_id=1 AND role_type_id=?",
	 * nativeQuery=true) boolean createRoleTypePermissions(int createdBy, int
	 * roleId, int userId);
	 */

	@Valid
	RoleType findByRoleTypeAndStatusId(String roleType, int statusId);

	boolean existsByRoleTypeAndStatusId(String roleType, Integer statusId);



	
}
