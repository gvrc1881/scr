import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map'
import { environment } from '../../environments/environment';


@Injectable()
export class AuthenticationService {
  header: any;
  constructor(private http: HttpClient) {
    this.header = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      //'Access-Control-Allow-Origin': '*'
    });
  }

  checkUsersExists() {
    return this.http.get<any>(environment.apiUrl + "/auth/checkUsersExists", { headers: this.header })
      .map(data => {
        return data;
      });
  }

  addUser(register) {
    return this.http.post<any>(environment.apiUrl + "/auth/addUser",register, { headers: this.header })
      .map(data => {
        return data;
      });
  }

  login(username: string, password: string) {
    let userData = {
      userName: username,
      password: password
    };
    return this.http.post<any>(environment.apiUrl + "/auth/login", userData, { headers: this.header })
      .map(data => {
        if (data && data.accessToken) {
          localStorage.setItem('accessToken', JSON.stringify(data.accessToken));
        }
        return data;
      });
  }
  getLoginUserData(username: string, password: string) {
    let userData = {
      userName: username,
      password: password
    };
    let accessToken = JSON.parse(localStorage.getItem('accessToken'));
    let userDetailsUrl = environment.apiUrl + "/userData";
    this.header = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      ////'Access-Control-Allow-Origin': '*',
      'Authorization': `Bearer ${accessToken}`
    });    

    return this.http.post<any>(userDetailsUrl, userData, { headers: this.header })
      .map(data => {     
        localStorage.setItem('userData', JSON.stringify(data));
        let Menus: any = [];
        Menus = [
          {
            "MenuColor": "#706189",
            "MenuIcon": "fa fa-home ",
            "IsMenu": 0,
            "CreatedBy": 1001,
            "RoleType": 1,
            "RoleTypeName": "Admin",
            "SubMenuName": "Complaints",
            "ModifiedBy": 1001,
            "ModifiedDate": null,
            "MenuURL": "topnavigation",
            "MenuName": "Dashboard",
            "MenuId": 9,
            "PermissionName": "View",
            "SubMenuURL": "comingSoon",
            "Permission": 1,
            "CreatedDate": 1562005800000,
            "StatusId": 1,
            "ID": 418,
            "SubMenuId": 35,
            "SubmenuIcon": "fa fa-home",
            "subMenus": [
              {
                "MenuColor": "#706189",
                "MenuIcon": "fa fa-inr ",
                "IsMenu": 0,
                "CreatedBy": 1001,
                "RoleType": 1,
                "RoleTypeName": "Admin",
                "SubMenuName": "Complaints",
                "ModifiedBy": 1001,
                "ModifiedDate": null,
                "MenuURL": "topnavigation",
                "MenuName": "Top Navigation",
                "MenuId": 9,
                "PermissionName": "View",
                "SubMenuURL": "comingSoon",
                "Permission": 1,
                "CreatedDate": 1562005800000,
                "StatusId": 1,
                "ID": 418,
                "SubMenuId": 35,
                "SubmenuIcon": "fa fa-home"
              },
              {
                "MenuColor": "#706189",
                "MenuIcon": "fa fa-inr ",
                "IsMenu": 0,
                "CreatedBy": 1001,
                "RoleType": 1,
                "RoleTypeName": "sss main menu name",
                "SubMenuName": "Complaints",
                "ModifiedBy": 1001,
                "ModifiedDate": null,
                "MenuURL": "topnavigation",
                "MenuName": "Top Navigation",
                "MenuId": 9,
                "PermissionName": "View",
                "SubMenuURL": "comingSoon",
                "Permission": 1,
                "CreatedDate": 1562005800000,
                "StatusId": 1,
                "ID": 418,
                "SubMenuId": 35,
                "SubmenuIcon": "fa fa-home"
              },
              {
                "MenuColor": "#706189",
                "MenuIcon": "fa fa-inr ",
                "IsMenu": 0,
                "CreatedBy": 1001,
                "RoleType": 1,
                "RoleTypeName": "Admin",
                "SubMenuName": "Support Central",
                "ModifiedBy": 1001,
                "ModifiedDate": null,
                "MenuURL": "topnavigation",
                "MenuName": "Top Navigation",
                "MenuId": 9,
                "PermissionName": "View/Add",
                "SubMenuURL": "comingSoon",
                "Permission": 2,
                "CreatedDate": 1562092200000,
                "StatusId": 1,
                "ID": 419,
                "SubMenuId": 36,
                "SubmenuIcon": "fa fa-home"
              },
              {
                "MenuColor": "#706189",
                "MenuIcon": "fa fa-inr ",
                "IsMenu": 0,
                "CreatedBy": 1001,
                "RoleType": 1,
                "RoleTypeName": "sss main menu name",
                "SubMenuName": "Support Central",
                "ModifiedBy": 1001,
                "ModifiedDate": null,
                "MenuURL": "topnavigation",
                "MenuName": "Top Navigation",
                "MenuId": 9,
                "PermissionName": "View/Add",
                "SubMenuURL": "comingSoon",
                "Permission": 2,
                "CreatedDate": 1562092200000,
                "StatusId": 1,
                "ID": 419,
                "SubMenuId": 36,
                "SubmenuIcon": "fa fa-home"
              },
              {
                "MenuColor": "#706189",
                "MenuIcon": "fa fa-inr ",
                "IsMenu": 0,
                "CreatedBy": 1001,
                "RoleType": 1,
                "RoleTypeName": "Admin",
                "SubMenuName": "Enquiry",
                "ModifiedBy": 1001,
                "ModifiedDate": null,
                "MenuURL": "topnavigation",
                "MenuName": "Top Navigation",
                "MenuId": 9,
                "PermissionName": "View/Add",
                "SubMenuURL": "comingSoon",
                "Permission": 2,
                "CreatedDate": 1562005800000,
                "StatusId": 1,
                "ID": 417,
                "SubMenuId": 34,
                "SubmenuIcon": "fa fa-home"
              },
              {
                "MenuColor": "#706189",
                "MenuIcon": "fa fa-inr ",
                "IsMenu": 0,
                "CreatedBy": 1001,
                "RoleType": 1,
                "RoleTypeName": "sss main menu name",
                "SubMenuName": "Enquiry",
                "ModifiedBy": 1001,
                "ModifiedDate": null,
                "MenuURL": "topnavigation",
                "MenuName": "Top Navigation",
                "MenuId": 9,
                "PermissionName": "View/Add",
                "SubMenuURL": "comingSoon",
                "Permission": 2,
                "CreatedDate": 1562005800000,
                "StatusId": 1,
                "ID": 417,
                "SubMenuId": 34,
                "SubmenuIcon": "fa fa-home"
              }
            ]
          },
          {
            "MenuColor": "#2b84ee",
            "MenuIcon": "fa fa-area-chart",
            "IsMenu": 0,
            "CreatedBy": 1001,
            "RoleType": 1,
            "RoleTypeName": "Admin",
            "SubMenuName": "Log List Monitoring",
            "ModifiedBy": 1001,
            "ModifiedDate": null,
            "MenuURL": "Monitoring",
            "MenuName": "Reports",
            "MenuId": 10,
            "PermissionName": "View",
            "SubMenuURL": "logListMonitoring",
            "Permission": 1,
            "CreatedDate": 1562005800000,
            "StatusId": 1,
            "ID": 448,
            "SubMenuId": 44,
            "SubmenuIcon": "fa fa-home",
            "subMenus": [
              {
                "MenuColor": "#2b84ee",
                "MenuIcon": "fa fa-home",
                "IsMenu": 0,
                "CreatedBy": 1001,
                "RoleType": 1,
                "RoleTypeName": "Admin",
                "SubMenuName": "Log List Monitoring",
                "ModifiedBy": 1001,
                "ModifiedDate": null,
                "MenuURL": "Monitoring",
                "MenuName": "Monitoring",
                "MenuId": 10,
                "PermissionName": "View",
                "SubMenuURL": "logListMonitoring",
                "Permission": 1,
                "CreatedDate": 1562005800000,
                "StatusId": 1,
                "ID": 448,
                "SubMenuId": 44,
                "SubmenuIcon": "fa fa-home"
              },
              {
                "MenuColor": "#2b84ee",
                "MenuIcon": "fa fa-home",
                "IsMenu": 0,
                "CreatedBy": 1001,
                "RoleType": 1,
                "RoleTypeName": "sss main menu name",
                "SubMenuName": "Log List Monitoring",
                "ModifiedBy": 1001,
                "ModifiedDate": null,
                "MenuURL": "Monitoring",
                "MenuName": "Monitoring",
                "MenuId": 10,
                "PermissionName": "View",
                "SubMenuURL": "logListMonitoring",
                "Permission": 1,
                "CreatedDate": 1562005800000,
                "StatusId": 1,
                "ID": 448,
                "SubMenuId": 44,
                "SubmenuIcon": "fa fa-home"
              }
            ]
          },

          {
            "MenuColor": "#67809f",
            "MenuIcon": "fa fa-cogs",
            "IsMenu": 0,
            "CreatedBy": 1001,
            "RoleType": 1,
            "RoleTypeName": "Admin",
            "SubMenuName": "App Messages",
            "ModifiedBy": 1001,
            "ModifiedDate": null,
            "MenuURL": "configurationMaster",
            "MenuName": "Scheduler Settings",
            "MenuId": 8,
            "PermissionName": "View/Add",
            "SubMenuURL": "comingSoon",
            "Permission": 2,
            "CreatedDate": 1562092200000,
            "StatusId": 1,
            "ID": 395,
            "SubMenuId": 12,
            "SubmenuIcon": "fa fa-home",
            "subMenus": [
              {
                "MenuColor": "#67809f",
                "MenuIcon": "fa fa-cogs",
                "IsMenu": 0,
                "CreatedBy": 1001,
                "RoleType": 1,
                "RoleTypeName": "Admin",
                "SubMenuName": "App Messages",
                "ModifiedBy": 1001,
                "ModifiedDate": null,
                "MenuURL": "configurationMaster",
                "MenuName": "Settings",
                "MenuId": 8,
                "PermissionName": "View/Add",
                "SubMenuURL": "comingSoon",
                "Permission": 2,
                "CreatedDate": 1562092200000,
                "StatusId": 1,
                "ID": 395,
                "SubMenuId": 12,
                "SubmenuIcon": "fa fa-home"
              },
              {
                "MenuColor": "#67809f",
                "MenuIcon": "fa fa-cogs",
                "IsMenu": 0,
                "CreatedBy": 1001,
                "RoleType": 1,
                "RoleTypeName": "sss main menu name",
                "SubMenuName": "App Messages",
                "ModifiedBy": 1001,
                "ModifiedDate": null,
                "MenuURL": "configurationMaster",
                "MenuName": "Settings",
                "MenuId": 8,
                "PermissionName": "View/Add",
                "SubMenuURL": "comingSoon",
                "Permission": 2,
                "CreatedDate": 1562092200000,
                "StatusId": 1,
                "ID": 395,
                "SubMenuId": 12,
                "SubmenuIcon": "fa fa-home"
              },
              {
                "MenuColor": "#67809f",
                "MenuIcon": "fa fa-cogs",
                "IsMenu": 0,
                "CreatedBy": 1001,
                "RoleType": 1,
                "RoleTypeName": "Admin",
                "SubMenuName": "Constants",
                "ModifiedBy": 1001,
                "ModifiedDate": null,
                "MenuURL": "configurationMaster",
                "MenuName": "Settings",
                "MenuId": 8,
                "PermissionName": "View/Add",
                "SubMenuURL": "comingSoon",
                "Permission": 2,
                "CreatedDate": 1562092200000,
                "StatusId": 1,
                "ID": 394,
                "SubMenuId": 11,
                "SubmenuIcon": "fa fa-home"
              },
              {
                "MenuColor": "#67809f",
                "MenuIcon": "fa fa-cogs",
                "IsMenu": 0,
                "CreatedBy": 1001,
                "RoleType": 1,
                "RoleTypeName": "sss main menu name",
                "SubMenuName": "Constants",
                "ModifiedBy": 1001,
                "ModifiedDate": null,
                "MenuURL": "configurationMaster",
                "MenuName": "Settings",
                "MenuId": 8,
                "PermissionName": "View/Add",
                "SubMenuURL": "comingSoon",
                "Permission": 2,
                "CreatedDate": 1562092200000,
                "StatusId": 1,
                "ID": 394,
                "SubMenuId": 11,
                "SubmenuIcon": "fa fa-home"
              }
            ]
          },
          {
            "MenuColor": "#2b84ee",
            "MenuIcon": "fa fa-briefcase",
            "IsMenu": 0,
            "CreatedBy": 1001,
            "RoleType": 1,
            "RoleTypeName": "Admin",
            "SubMenuName": "NEWsubmenu",
            "ModifiedBy": 1001,
            "ModifiedDate": null,
            "MenuURL": "newMenuUrl",
            "MenuName": "Scheduler Tracking",
            "MenuId": 60,
            "PermissionName": "View/Add",
            "SubMenuURL": "newsubMenuUrl",
            "Permission": 2,
            "CreatedDate": 1562610600000,
            "StatusId": 1,
            "ID": 978,
            "SubMenuId": 76,
            "SubmenuIcon": "fa fa-home",
            "subMenus": [
              {
                "MenuColor": "#2b84ee",
                "MenuIcon": "fa fa-home",
                "IsMenu": 0,
                "CreatedBy": 1001,
                "RoleType": 1,
                "RoleTypeName": "Admin",
                "SubMenuName": "NEWsubmenu",
                "ModifiedBy": 1001,
                "ModifiedDate": null,
                "MenuURL": "newMenuUrl",
                "MenuName": "NEWmenu",
                "MenuId": 60,
                "PermissionName": "View/Add",
                "SubMenuURL": "newsubMenuUrl",
                "Permission": 2,
                "CreatedDate": 1562610600000,
                "StatusId": 1,
                "ID": 978,
                "SubMenuId": 76,
                "SubmenuIcon": "fa fa-home"
              },
              {
                "MenuColor": "#2b84ee",
                "MenuIcon": "fa fa-home",
                "IsMenu": 0,
                "CreatedBy": 1001,
                "RoleType": 1,
                "RoleTypeName": "sss main menu name",
                "SubMenuName": "NEWsubmenu",
                "ModifiedBy": 1001,
                "ModifiedDate": null,
                "MenuURL": "newMenuUrl",
                "MenuName": "NEWmenu",
                "MenuId": 60,
                "PermissionName": "View/Add",
                "SubMenuURL": "newsubMenuUrl",
                "Permission": 2,
                "CreatedDate": 1562610600000,
                "StatusId": 1,
                "ID": 978,
                "SubMenuId": 76,
                "SubmenuIcon": "fa fa-home"
              }
            ]
          },
          {
            "MenuColor": "#0183b5",
            "MenuIcon": "fa fa-wrench",
            "IsMenu": 0,
            "CreatedBy": 1001,
            "RoleType": 1,
            "RoleTypeName": "Admin",
            "SubMenuName": "Role",
            "ModifiedBy": 1001,
            "ModifiedDate": null,
            "MenuURL": "Masters",
            "MenuName": "Masters",
            "MenuId": 2,
            "PermissionName": "View/Add",
            "SubMenuURL": "roletypeMaster",
            "Permission": 2,
            "CreatedDate": 1562005800000,
            "StatusId": 1,
            "ID": 410,
            "SubMenuId": 27,
            "SubmenuIcon": "fa fa-home",
            "subMenus": [
              {
                "MenuColor": "#0183b5",
                "MenuIcon": "fa fa-wrench",
                "IsMenu": 0,
                "CreatedBy": 1001,
                "RoleType": 1,
                "RoleTypeName": "Admin",
                "SubMenuName": "Role",
                "ModifiedBy": 1001,
                "ModifiedDate": null,
                "MenuURL": "Masters",
                "MenuName": "Masters",
                "MenuId": 2,
                "PermissionName": "View/Add",
                "SubMenuURL": "roletypeMaster",
                "Permission": 2,
                "CreatedDate": 1562005800000,
                "StatusId": 1,
                "ID": 410,
                "SubMenuId": 27,
                "SubmenuIcon": "fa fa-home"
              },
              {
                "MenuColor": "#0183b5",
                "MenuIcon": "fa fa-wrench",
                "IsMenu": 0,
                "CreatedBy": 1001,
                "RoleType": 1,
                "RoleTypeName": "sss main menu name",
                "SubMenuName": "Role",
                "ModifiedBy": 1001,
                "ModifiedDate": null,
                "MenuURL": "Masters",
                "MenuName": "Masters",
                "MenuId": 2,
                "PermissionName": "View/Add",
                "SubMenuURL": "roletypeMaster",
                "Permission": 2,
                "CreatedDate": 1562005800000,
                "StatusId": 1,
                "ID": 410,
                "SubMenuId": 27,
                "SubmenuIcon": "fa fa-home"
              },
              {
                "MenuColor": "#0183b5",
                "MenuIcon": "fa fa-wrench",
                "IsMenu": 0,
                "CreatedBy": 1001,
                "RoleType": 1,
                "RoleTypeName": "Admin",
                "SubMenuName": "Menus",
                "ModifiedBy": 1001,
                "ModifiedDate": null,
                "MenuURL": "Masters",
                "MenuName": "Masters",
                "MenuId": 2,
                "PermissionName": "View/Edit",
                "SubMenuURL": "menuMaster",
                "Permission": 4,
                "CreatedDate": 1562005800000,
                "StatusId": 1,
                "ID": 408,
                "SubMenuId": 25,
                "SubmenuIcon": "fa fa-home"
              },
              {
                "MenuColor": "#0183b5",
                "MenuIcon": "fa fa-wrench",
                "IsMenu": 0,
                "CreatedBy": 1001,
                "RoleType": 1,
                "RoleTypeName": "sss main menu name",
                "SubMenuName": "Menus",
                "ModifiedBy": 1001,
                "ModifiedDate": null,
                "MenuURL": "Masters",
                "MenuName": "Masters",
                "MenuId": 2,
                "PermissionName": "View/Edit",
                "SubMenuURL": "menuMaster",
                "Permission": 4,
                "CreatedDate": 1562005800000,
                "StatusId": 1,
                "ID": 408,
                "SubMenuId": 25,
                "SubmenuIcon": "fa fa-home"
              },
              {
                "MenuColor": "#0183b5",
                "MenuIcon": "fa fa-wrench",
                "IsMenu": 0,
                "CreatedBy": 1001,
                "RoleType": 1,
                "RoleTypeName": "Admin",
                "SubMenuName": "Users",
                "ModifiedBy": 1001,
                "ModifiedDate": null,
                "MenuURL": "Masters",
                "MenuName": "Masters",
                "MenuId": 2,
                "PermissionName": "View/Add/Edit/Delete",
                "SubMenuURL": "userMaster",
                "Permission": 5,
                "CreatedDate": 1562092200000,
                "StatusId": 1,
                "ID": 423,
                "SubMenuId": 41,
                "SubmenuIcon": "fa fa-home"
              },
              {
                "MenuColor": "#0183b5",
                "MenuIcon": "fa fa-wrench",
                "IsMenu": 0,
                "CreatedBy": 1001,
                "RoleType": 1,
                "RoleTypeName": "sss main menu name",
                "SubMenuName": "Users",
                "ModifiedBy": 1001,
                "ModifiedDate": null,
                "MenuURL": "Masters",
                "MenuName": "Masters",
                "MenuId": 2,
                "PermissionName": "View/Add/Edit/Delete",
                "SubMenuURL": "userMaster",
                "Permission": 5,
                "CreatedDate": 1562092200000,
                "StatusId": 1,
                "ID": 423,
                "SubMenuId": 41,
                "SubmenuIcon": "fa fa-home"
              },
              {
                "MenuColor": "#0183b5",
                "MenuIcon": "fa fa-wrench",
                "IsMenu": 0,
                "CreatedBy": 1001,
                "RoleType": 1,
                "RoleTypeName": "Admin",
                "SubMenuName": "Sub Menu",
                "ModifiedBy": 1001,
                "ModifiedDate": null,
                "MenuURL": "Masters",
                "MenuName": "Masters",
                "MenuId": 2,
                "PermissionName": "View/Add/Edit/Delete",
                "SubMenuURL": "submenuMaster",
                "Permission": 5,
                "CreatedDate": 1562005800000,
                "StatusId": 1,
                "ID": 409,
                "SubMenuId": 26,
                "SubmenuIcon": "fa fa-home"
              },
              {
                "MenuColor": "#0183b5",
                "MenuIcon": "fa fa-wrench",
                "IsMenu": 0,
                "CreatedBy": 1001,
                "RoleType": 1,
                "RoleTypeName": "sss main menu name",
                "SubMenuName": "Sub Menu",
                "ModifiedBy": 1001,
                "ModifiedDate": null,
                "MenuURL": "Masters",
                "MenuName": "Masters",
                "MenuId": 2,
                "PermissionName": "View/Add/Edit/Delete",
                "SubMenuURL": "submenuMaster",
                "Permission": 5,
                "CreatedDate": 1562005800000,
                "StatusId": 1,
                "ID": 409,
                "SubMenuId": 26,
                "SubmenuIcon": "fa fa-home"
              }
            ]
          }
        ];

        localStorage.setItem('userMenuList', JSON.stringify(Menus));
        return data;
      });
  }
  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('accessToken');
    localStorage.removeItem('userMenuList');
    localStorage.removeItem('userData');
    localStorage.removeItem('loggedUser');
    localStorage.removeItem('menus');
  }
  getUserData(username: string, password: string) {    
    let userData = {
      email: username,
      password: password
    };
    let accessToken = JSON.parse(localStorage.getItem('accessToken'));
    let userDetailsUrl = environment.apiUrl + "/userData";
    this.header = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      //'Access-Control-Allow-Origin': '*',
      'Authorization': `Bearer ${accessToken}`
    });
    return this.http.post<any>(environment.apiUrl + "/loggedUserData", userData, { headers: this.header })
      .map(response => {
        return response;
      }, error => {
        console.log("error")
      });
  }
  findMenusAndSubMenus(data) {

    let Menus: any = [];
    let SubMenus: any = [];
    for (let Menu of data) {
      if (Menu.SubMenuId != null) {
        SubMenus.push(Menu);
        if (Menus.filter(function (e, index) {
          return (e.MenuId == Menu.MenuId);
        }).length == 0) {
          let tempObj = Object.assign({}, Menu)
          tempObj.subMenus = [];
          Menus.push(tempObj);
        }
      }
      else {
        Menu.subMenus = [];
        Menus.push(Menu)
      }
    }

    for (let sm of SubMenus) {
      Menus.filter(function (m, index) {
        if (sm.MenuId == m.MenuId) {

          if (m.subMenus.filter(function (e, index) {
            return (e.SubMenuId == m.SubMenuId);
          }).length >= 0) {
            m.subMenus.push(sm);
          }
        }
      });
    }
    localStorage.setItem('userMenuList', JSON.stringify(Menus));

  }

  forgotPassword(email: string) {    
    return this.http.post<any>(environment.apiUrl + "/auth/forgotPassword", {"email":email},  { headers: this.header })
      .map(data => {        
        return data;
      });
  }

  checkTokenIsValid(token: string) {    
    return this.http.post<any>(environment.apiUrl + "/auth/confirm-reset",{"confirmationToken":token},  { headers: this.header })
      .map(data => {        
        return data;
      });
  }

  updatePassword(user) {   
    let accessToken = JSON.parse(localStorage.getItem('accessToken'));    
    this.header = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      //'Access-Control-Allow-Origin': '*',
      'Authorization': `Bearer ${accessToken}`
    }); 
    return this.http.post<any>(environment.apiUrl + "/updatePassword",user,  { headers: this.header })
      .map(data => {        
        return data;
      });
  }

  resetPassword(user) {    
    return this.http.post<any>(environment.apiUrl + "/auth/resetPassword",user,  { headers: this.header })
      .map(data => {        
        return data;
      });
  }

  validateCurrentPassword(currentPassword:string, email:string) {    
    let accessToken = JSON.parse(localStorage.getItem('accessToken'));    
    this.header = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      //'Access-Control-Allow-Origin': '*',
      'Authorization': `Bearer ${accessToken}`
    });
    return this.http.post<any>(environment.apiUrl + "/currentPassword",{"password":currentPassword,"email":email},  { headers: this.header })
      .map(data => {        
        return data;
      });
  }

  userHierarchy(user:string) {
    return this.http.get<string>(environment.apiUrl+"/userHierarchy/"+user, {headers: this.header });
  }
}