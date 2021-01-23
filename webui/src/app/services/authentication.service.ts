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
          sessionStorage.setItem('accessToken', JSON.stringify(data.accessToken));
        }
        return data;
      });
  }
  getLoginUserData(username: string, password: string) {
    let userData = {
      userName: username,
      password: password
    };
    let accessToken = JSON.parse(sessionStorage.getItem('accessToken'));
    let userDetailsUrl = environment.apiUrl + "/userData";
    this.header = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      ////'Access-Control-Allow-Origin': '*',
      'Authorization': `Bearer ${accessToken}`
    });    

    return this.http.post<any>(userDetailsUrl, userData, { headers: this.header })
      .map(data => {     
        console.log(userData)
       // sessionStorage.setItem('userData', JSON.stringify(data));     
          sessionStorage.setItem('userData', JSON.stringify(data));     
     //   sessionStorage.setItem('userMenuList', JSON.stringify(Menus));
        return data;
      });
  }
  logout() {
    // remove user from local storage to log user out
    sessionStorage.removeItem('accessToken');
    sessionStorage.removeItem('userMenuList');
    sessionStorage.removeItem('userData');
    sessionStorage.removeItem('loggedUser');
    sessionStorage.removeItem('menus');
  }
  getUserData(username: string, password: string) {    
    let userData = {
      email: username,
      password: password
    };
    let accessToken = JSON.parse(sessionStorage.getItem('accessToken'));
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
  findMenusAndSubMenus() {

    /* let Menus: any = [];
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
    } */
    //sessionStorage.setItem('userMenuList', JSON.stringify(Menus));
    let menus = sessionStorage.getItem("MenusList");
    return menus;
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
    let accessToken = JSON.parse(sessionStorage.getItem('accessToken'));    
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
    let accessToken = JSON.parse(sessionStorage.getItem('accessToken'));    
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