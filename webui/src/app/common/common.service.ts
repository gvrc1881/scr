import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material';
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
            url === Constants.app_urls.LOGIN ||
            url === Constants.app_urls.REGISTRATION ||
            // url === Constants.app_urls.CHANGE_PASSWORD || 
            url === Constants.app_urls.MAIL_CONFIRMATION ||
            url === Constants.app_urls.RESET_PASSWORD ||
            url === Constants.app_urls.FORGOT_PASSWORD) ? false : true;
    }
    findPermission(value, type, permissionType) {
        let loggerUser = JSON.parse(localStorage.getItem('loggedUser'));
        // console.log("log user:::***"+ JSON.stringify(loggerUser));
        if (type == 'menu') {
            if (loggerUser) {
                let pageRolePermissions = loggerUser.menuPermissionResponses;
               //   console.log('pageRolePermissions= ' + JSON.stringify(pageRolePermissions)); 
               if(pageRolePermissions != null) {
               var p= pageRolePermissions.filter(element => {
                  //  console.log(element);
                   /*  if(element.menuName.toLowerCase() == value.toLowerCase()){
                      //  console.log("iffff");
                        var permission = element.permissionName;
                        if (permission != null && !!permission && permission.includes(permissionType)) {
                         //   console.log('sdkhfskdfhskdfsjd')
                            return true;
                        } else {
                          //  console.log('else*********************************');
                            return false;
                        }
                    } */
                    return element.menuName.toLowerCase() == value.toLowerCase() && element.permissionName != null && element.permissionName != 'No Permissions' ? true : false;
                });
                }
              //  console.log("ppppp ="+JSON.stringify(p));
              //  return p.length == 0 ? false : true;
              return p == 0 ? false : true;
            }
        } else if (type == 'submenu') {
            if (loggerUser) {
                let pageRolePermissions = loggerUser.menuPermissionResponses;
             //   console.log('pageRolePermissions=**** ' + JSON.stringify(pageRolePermissions));
              /*   console.log('pageRolePermissions= ' + JSON.stringify(pageRolePermissions)); */
               if(pageRolePermissions != null){
               var p= pageRolePermissions.filter(element => {
                  /*  console.log("sdmgsdgl;;;;") */
                    return element.subMenuName != null && element.subMenuName.toLowerCase() == value.toLowerCase() && element.permissionName != null && element.permissionName != 'No Permissions' ? true : false;
                });
                }
              //  console.log("submenu ="+JSON.stringify(p));
               // return p.length == 0 ? false : true;
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
	  // console.log(element)
          return element.menuName.toLowerCase() == menu.toLowerCase() && element.subMenuName.toLowerCase() == submenu.toLowerCase() ;//&& element.permissionName != null && element.permissionName != 'No Permissions' ? true : false;
        });
      var permissionName=  p == 0 ? 'No Permission' : p[0].permissionName;
        return permissionName;
    }

	 getPermissionByType(permissionType, permissionName) {
        //console.log(JSON.stringify(localStorage.getItem("loggedUser")));
       
            let permission = permissionName;
            if (permission != null && !!permission && permission.includes(permissionType)) {
                return true;
            } else {
                return false;
            }      
    }

    getPermission(permissionType) {
        //console.log(JSON.stringify(localStorage.getItem("loggedUser")));
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
}
