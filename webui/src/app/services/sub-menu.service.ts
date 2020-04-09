import { Injectable, Inject } from '@angular/core';
import { Http, Response } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Router } from '@angular/router';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import {environment} from '../../environments/environment';

@Injectable()
export class SubMenuService {
    myAppUrl: string = environment.apiUrl;
    header:any;
    accessToken:any = JSON.parse(localStorage.getItem('accessToken'));

    constructor(private _http: HttpClient) {
         this.header = new HttpHeaders({
            'Content-Type': 'application/x-www-form-urlencoded',
            'Accept': 'application/json'
          });
    }
    

    getSubMenu() {
        console.log("getSubMenu..............");
        return this._http.get(this.myAppUrl + '/api/submenu?access_token='+this.accessToken.access_token)
            .map((response: Response) =>response)
           .catch(this.errorHandler);
          
    }
    getMainMenu() {
       
        return this._http.get(this.myAppUrl + '/api/menu?access_token='+this.accessToken.access_token)
            .map((response: Response) =>response)
            .catch(this.errorHandler);
    }
    saveSubMenu(SubMenu) {
        console.log(SubMenu);
        return this._http.post(this.myAppUrl + '/api/submenu/add?access_token='+this.accessToken.access_token, SubMenu)
        .map((response: Response) => {
            console.log(response);
            //response;
        })
        .catch(this.errorHandler)
}
updateSubMenu(SubMenu,id) {
      
    console.log(id);
    console.log(SubMenu);
    return this._http.post(this.myAppUrl + '/api/submenu/Put/'+id+'?access_token='+this.accessToken.access_token, SubMenu)
        .map((response: Response) => response)
        .catch(this.errorHandler);
}
getSubMenuById(id: number) {
      
    return this._http.get(this.myAppUrl + "/api/submenu/" +id+'?access_token='+this.accessToken.access_token)
        .map((response: Response) => response)
        .catch(this.errorHandler)
}
duplicateSubMenu(SubMenu){   
    return this._http.post(this.myAppUrl +'/api/submenu/Duplicate?access_token='+this.accessToken.access_token,SubMenu)
    .map((response: Response) => response)   
    .catch(this.errorHandler);
    
}
deleteSubmenu(id){
    return this._http.get(this.myAppUrl + "/api/submenu/Delete/" +id+'?access_token='+this.accessToken.access_token)
        .map((response: Response) => response)
        .catch(this.errorHandler);
}

    errorHandler(error: Response) {
        console.log(error);
        return Observable.throw(error);
    }
}   