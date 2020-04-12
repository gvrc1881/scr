/**
 * 
 */
package com.scr.mapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Component;

import com.scr.message.response.RolePermissionResponse;
import com.scr.model.Permissions;
import com.scr.model.RolePermissions;
import com.scr.model.RoleType;
import com.scr.util.Constants;
import com.scr.util.PredicateUtil;

/**
 * @author vt1056
 *
 */
@Component
public class RolePermissionMapper {

	public List<RolePermissionResponse> prepareRolePermissionResponse(List<RoleType> rolesList,
			List<RolePermissions> rolePermissionList, List<Permissions> permissionsList) {
		List<RolePermissionResponse> response = new ArrayList<RolePermissionResponse>();
		try {
			if(rolesList != null ) {
				RolePermissionResponse rolePermissionResponse = null;
				for(RoleType roleType: rolesList) {
					rolePermissionResponse = new RolePermissionResponse();
					rolePermissionResponse.setRoleId(roleType.getId());
					rolePermissionResponse.setRoleName(roleType.getRoleType());
					rolePermissionResponse.setPermissionId(rolePermissionList == null || rolePermissionList.size() == 0 ? 6 :  PredicateUtil.getPermissionId(roleType.getId(), rolePermissionList) == 0 ? 6 : PredicateUtil.getPermissionId(roleType.getId(), rolePermissionList));
					rolePermissionResponse.setPermission(PredicateUtil.getPermissionName(rolePermissionResponse.getPermissionId(), permissionsList));
					response.add(rolePermissionResponse);
				}
			}
		}catch (Exception e) {
			
		}
		return response;
	}

	public RolePermissions prepareRolePermissionAdd(RoleType roleType) {
		RolePermissions rolePermissions = new RolePermissions();
		rolePermissions.setModifiedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		rolePermissions.setCreatedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		rolePermissions.setRoleTypeId(roleType.getId());
		rolePermissions.setModifiedBy(roleType.getCreatedBy());
		rolePermissions.setCreatedBy(roleType.getModifiedBy());
		rolePermissions.setPermissionId(6);
		rolePermissions.setStatusId(Constants.ACTIVE_STATUS_ID);
		return rolePermissions;
	}

}
