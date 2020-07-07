import { Injectable } from '@angular/core';
import { MatSnackBar, MatTableDataSource } from '@angular/material';
import { Constants } from './constants';
@Injectable()
export class CommonService {
	/**
	 * Constructor
	 *
	 * @param {NotifierService} notifier Notifier service
	 */
    public constructor(private snackBar: MatSnackBar) {

    }

    showAlertMessage(message: string) {
        this.snackBar.open(message, "x", {
            duration: 4000,
            verticalPosition: 'top',
            horizontalPosition: 'right',
            panelClass: ['magenta-snackbar']
        });
    }
    scrollTop(id) {
        var elmnt = document.getElementById(id);
        elmnt.scrollLeft = 0;
        elmnt.scrollTop = 0;
    }

    removeSpaceMaoreThanOne(content: string) {
        return content !== null && !!content && content.replace(/ {2,}/g, ' ').trim()
    }

    loginValidate(url: string) {
        return (url === '/' ||
            url === Constants.app_urls.AUTHENTICATION.LOGIN ||
            url === Constants.app_urls.AUTHENTICATION.REGISTRATION ||
            url === Constants.app_urls.AUTHENTICATION.MAIL_CONFIRMATION ||
            url === Constants.app_urls.AUTHENTICATION.RESET_PASSWORD ||
            url === Constants.app_urls.AUTHENTICATION.FORGOT_PASSWORD) ? false : true;
    }
    findPermission(value, type, permissionType) {
        let loggerUser = JSON.parse(localStorage.getItem('loggedUser'));
        if (type == 'menu') {
            if (loggerUser) {
                let pageRolePermissions = loggerUser.menuPermissionResponses;
               if(pageRolePermissions != null) {
               var p= pageRolePermissions.filter(element => {
                    return element.menuName.toLowerCase() == value.toLowerCase() && element.permissionName != null && element.permissionName != 'No Permissions' ? true : false;
                });
                }
              return p == 0 ? false : true;
            }
        } else if (type == 'submenu') {
            if (loggerUser) {
                let pageRolePermissions = loggerUser.menuPermissionResponses;
               if(pageRolePermissions != null){
               var p= pageRolePermissions.filter(element => {
                    return element.subMenuName != null && element.subMenuName.toLowerCase() == value.toLowerCase() && element.permissionName != null && element.permissionName != 'No Permissions' ? true : false;
                });
                }
                return p == 0 ? false : true;
            }
        }
    }
    rolePermission() {
        if (!!localStorage.getItem("loggedUser") && localStorage.getItem("loggedUser") != "[object Object]") {
            let loggedUser = JSON.parse(localStorage.getItem("loggedUser"));
            return !!loggedUser.roleName && loggedUser.roleName == 'User' ? false : true;
        }
        else {
            return false
        }
    }
    
    getPermissionNameByLoggedData(menu, submenu){
    	let loggerUser = JSON.parse(localStorage.getItem('loggedUser'));
  		let pageRolePermissions = loggerUser.menuPermissionResponses;
	   var p= !!pageRolePermissions && pageRolePermissions.filter(element => {
          return element.menuName.toLowerCase() == menu.toLowerCase() && element.subMenuName.toLowerCase() == submenu.toLowerCase() ;//&& element.permissionName != null && element.permissionName != 'No Permissions' ? true : false;
        });
      var permissionName=  p == 0 ? 'No Permission' : p[0].permissionName;
        return permissionName;
    }

	 getPermissionByType(permissionType, permissionName) {       
            let permission = permissionName;
            if (permission != null && !!permission && permission.includes(permissionType)) {
                return true;
            } else {
                return false;
            }      
    }

    getPermission(permissionType) {
        if (!!localStorage.getItem("loggedUser") && localStorage.getItem("loggedUser") != "[object Object]") {
            let loggedUser = JSON.parse(localStorage.getItem("loggedUser"));
            let permission = !!loggedUser.roleName && loggedUser.permissionName;
            if (permission != null && !!permission && permission.includes(permissionType)) {
                return true;
            } else {
                return false;
            }
        }
        else {
            return false
        }
    }

    updateDataSource(dataSource:MatTableDataSource<any>, displayedColumns:string[]){
        dataSource.filteredData.map((item, index) =>{
            displayedColumns.map((col, i)=>{
              if(col != 'sno' && (item[col] == null ||  item[col]=='null') ){
                dataSource.filteredData[index][col] = "";
              }
            })
          })
    }
}
