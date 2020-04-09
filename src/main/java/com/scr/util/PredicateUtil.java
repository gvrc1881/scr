/**
 * 
 */
package com.scr.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import com.scr.model.DivisionsHistory;
import com.scr.model.MLocation;
import com.scr.model.Permissions;
import com.scr.model.RolePermissions;
import com.scr.model.RoleType;

/**
 * @author vt1056
 *
 */
public class PredicateUtil {

	public static Integer getPermissionId(int roleId, List<RolePermissions> rolePermissionList) {		
		return rolePermissionList.stream().filter(isRolePermissionMatch(roleId)).findFirst().get().getPermissionId();
	}

	private static Predicate<RolePermissions> isRolePermissionMatch(int roleId) {		
		return rolePermission -> (rolePermission.getRoleTypeId() == roleId);
	}

	public static String getPermissionName(Integer permissionId, List<Permissions> permissionsList) {		
		return permissionsList.stream().filter(isRolePermissionNameMatch(permissionId)).findFirst().get().getPermission();
	}

	private static Predicate<Permissions> isRolePermissionNameMatch(Integer permissionId) {		
		return permissions -> (permissions.getId() == permissionId);
	}

	public static String getRoleName(Integer roleTypeId, List<RoleType> rolesList) {
		return rolesList.stream().filter(findRoleName(roleTypeId)).findFirst().get().getRoleType();
	}

	private static Predicate<? super RoleType> findRoleName(Integer roleTypeId) {
		return roles -> (roles.getId() == roleTypeId);
	}

	public static Integer getPermissionIdByRoleType(Integer roleTypeId,List<RolePermissions> rolePermissionsList) {		
		return rolePermissionsList.stream().filter(isRoleTypeIdMatch(roleTypeId)).findFirst().get().getPermissionId();
	}

	private static Predicate<? super RolePermissions> isRoleTypeIdMatch(Integer roleTypeId) {		
		return rolePermissions -> (rolePermissions.getRoleTypeId() == roleTypeId);
	}

	public static List<String> findFailedTables(List<DivisionsHistory> diHistories, String success) {
		List<String> result = new ArrayList<>();
		for(DivisionsHistory div : diHistories)
			if(!div.getStatus().equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE))
				result.add(div.getTableName());
		return result;
		//return diHistories.stream().filter(his -> !his.getStatus().equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE)).
	}

	public static MLocation findMLoactionByUserId(List<MLocation> mLocations, Integer id) {	
		return mLocations.stream().filter(isUserIdMatch(id)).findFirst().get();
	}

	private static Predicate<? super MLocation> isUserIdMatch(Integer id) {	
		return locations -> (locations.getID() == Long.valueOf(id));
	}
	
}
