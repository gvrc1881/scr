import { Injectable, Inject } from '@angular/core';
import { Http, Response } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Router } from '@angular/router';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import { environment } from '../../environments/environment';

@Injectable()
export class UsersService {
    myAppUrl: string = environment.apiUrl;
    header:any;
    accessToken:any = JSON.parse(localStorage.getItem('accessToken'));
    
    constructor(private _http: HttpClient) {
        this.header = new HttpHeaders({
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${this.accessToken}`
        });
         
    }

    getUsersList() {
        return this._http.get(this.myAppUrl + '/allUsers', {headers:this.header})
            .map((response: Response) =>response)
            .catch(this.errorHandler);
            
    }
    roletypeMaster(){
        return this._http.get(this.myAppUrl + '/findAllRoles', {headers : this.header})
        .map((response: Response) =>response)
        .catch(this.errorHandler);
         
    }
    departmentNameList(){
        return this._http.get(this.myAppUrl + '/findAllDepartments', {headers : this.header})
        .map((response: Response) =>response)
        .catch(this.errorHandler);
    }
    
    saveUsersList(UserList) {
        console.log('data in save users list service');
        console.log('UserList: '+JSON.stringify(UserList));
        //return this._http.post(this.myAppUrl + '/api/userMaster/1/add?access_token='+this.accessToken.access_token, UserList)
        return this._http.post(this.myAppUrl + '/addUser', UserList, {headers:this.header})
            .map((response: Response) => { 
                console.log(response);
                //response;
            })
            .catch(this.errorHandler)
    }
    getUsersListById(ID: number) {
        console.log("id: "+ID)
        return this._http.get(this.myAppUrl +'/findUserById/' +ID, {headers:this.header})
            .map((response: Response) => response)
            .catch(this.errorHandler)
    }
    
    
    deleteUserList(ID){
        return this._http.get(this.myAppUrl + "/deleteUser/" +ID, {headers:this.header})
        .map((response: Response) => response)
        .catch(this.errorHandler);
    }
    
    editUser(UserList,ID){
        console.log('data will be edited in users list service');
        console.log(UserList);
        console.log(ID);
        return this._http.post(this.myAppUrl +'/editUser', UserList, {headers:this.header})
        .map((response: Response) => response)
       
        .catch(this.errorHandler);
       
    }
    duplicateEmail(UserList){
      
        return this._http.post(this.myAppUrl +'/duplicateEmail', UserList, {headers:this.header})
        .map((response: Response) => response)
       
        .catch(this.errorHandler);
    }

    errorHandler(error: Response) {
        console.log(error);
        return Observable.throw(error);
    }
}