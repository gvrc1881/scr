package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.MasterRoles;
import com.scr.model.PageRolePermission;

@Repository
public interface PageRolePermissionRepository extends JpaRepository<PageRolePermission, Long>{

	List<PageRolePermission> findPageRolePermissionByMasterRoleId(MasterRoles masterRoleId);

}
