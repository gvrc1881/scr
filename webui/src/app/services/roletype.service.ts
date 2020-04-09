import { Injectable, Inject } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Router } from '@angular/router';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import { environment } from '../../environments/environment';

@Injectable()
export class RoleTypeService {
    myAppUrl: string = environment.apiUrl;
    header: any;
    accessToken: any = JSON.parse(localStorage.getItem('accessToken'));
    constructor(private _http: HttpClient) {
        this.header = new HttpHeaders({
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${this.accessToken}`
        });
    }
    getRoleList() {        
        return this._http.get(this.myAppUrl + '/findAllRoles', { headers: this.header })
            .map((response: Response) => response)
            .catch(this.errorHandler);
    }    
    getMasterRoleList() {        
        return this._http.get(this.myAppUrl + '/findMasterRoles', { headers: this.header })
            .map((response: Response) => response)
            .catch(this.errorHandler);
    }
    getRoledata(id) {      
        return this._http.get(this.myAppUrl + '/roleById/' + id , {headers:this.header})
            .map((response: Response) => response)
            .catch(this.errorHandler);
    }
    addMroleType(roleType) {
        console.log("roleType:"+JSON.stringify(roleType))
        return this._http.post(this.myAppUrl + '/addRole', roleType, {headers:this.header})
            .map((response: Response) => response)
            .catch(this.errorHandler);
    }

    editMroleType(roleType, id) {

        console.log(roleType);
        console.log(id);
        return this._http.post(this.myAppUrl + '/editRole',roleType, {headers:this.header})
            .map((response: Response) => response)
            .catch(this.errorHandler);

    }
    saveRolePermission(row, id) {

        console.log(row);
        console.log(id);


        console.log("saveRolePermission...........");
        return this._http.post(this.myAppUrl + '/api/saveRolePermission/ ' + id + '?access_token=' + this.accessToken.access_token, row)
            .map((response: Response) => response
                // {
                //     console.log("data in save role permissions");
                //     console.log(response);
                // }
            );

        //response;
    }
    deleteRole(id) {
        console.log("response");
        return this._http.get(this.myAppUrl + "/deleteRole/"+ id,{headers:this.header})
            .map((response: Response) => {
                console.log(response);
            }
                // response
            )
            .catch(this.errorHandler);
    }
    getPermissions() {
        return this._http.get(this.myAppUrl + '/permissions',{headers:this.header})
            .map((response: Response) => response)
            .catch(this.errorHandler);
    }
    deleteRolePermission(id) {
        return this._http.get(this.myAppUrl + "/api/api/Permissions/Delete?id=" + id + '?access_token=' + this.accessToken.access_token)
            .map((response: Response) => response)
            .catch(this.errorHandler);
    }

    getRoleTypePermissons(id) {

        return this._http.get(this.myAppUrl + '/api/getRoleTypePermissons/ ' + id + '?access_token=' + this.accessToken.access_token)
            .map((response: Response) => response)
    }
    duplicateRoletype(roleType) {
        return this._http.get(this.myAppUrl + '/findDuplicateRole/'+roleType.roleType+'/1', { headers: this.header })
            .map((response: Response) => response)
            .catch(this.errorHandler);

    }
    createdPermissions(permissions, createdBy, obj, id) {
       const data = {
            "createdBy":createdBy,
            "roleTypeId":id
        }
        return this._http.post(this.myAppUrl + '/savePermission', data, {headers:this.header})
            .map((response: Response) => {
                console.log(permissions);
                console.log(createdBy);
                console.log(obj);
                console.log(id);
            }
                // response
            )

        //.catch(this.errorHandler);

    }
    // createdPermissions(permissions,userId,clonedRoleId,createdRoleId){    

    //                      console.log("saveRolePermission...........");    
    //                          return this._http.post(this.myAppUrl +'/api/createMroleTypePermissions/'+userId+"/"+clonedRoleId+"/"+createdRoleId+'?access_token='+this.accessToken.access_token,permissions)   
    //                             .map((response: Response) =>
    //                                {      
    //                                         console.log("data in save role permissions"); 
    //                                         console.log(permissions);
    //                                                 console.log(userId);
    //                                                console.log(clonedRoleId);
    //                                               console.log(createdRoleId);        
    //                                 //  console.log(response);  
    //                                      }      
    //                                       );
    //                                     }

    errorHandler(error: Response) {
        console.log(error);
        return Observable.throw(error);
    }
}