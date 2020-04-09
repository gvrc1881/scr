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
export class MainMenuService {
    myAppUrl: string = environment.apiUrl;
    header:any;
    accessToken:any = JSON.parse(localStorage.getItem('accessToken'));

    constructor(private _http: HttpClient) {
         this.header = new HttpHeaders({
            'Content-Type': 'application/x-www-form-urlencoded',
            'Accept': 'application/json'
          });
          console.log("environment.apiUrl");
        //  console.log(environment.apiUrl);
    }

    getMainMenu() {       
        return this._http.get(this.myAppUrl + '/api/menu?access_token='+this.accessToken.access_token)
            .map((response: Response) =>response)
            .catch(this.errorHandler);
    }

    getMainMenuById(id: number) {      
        return this._http.get(this.myAppUrl + "/api/menu/" +id+'?access_token='+this.accessToken.access_token)
            .map((response: Response) => response)
            .catch(this.errorHandler)
    }

    saveMainMenu(MainMenu) {    
        return this._http.post(this.myAppUrl + '/api/menu/add?access_token='+this.accessToken.access_token, MainMenu)
            .map((response: Response) => {              
            })
            .catch(this.errorHandler)
    }

    updateMainMenu(MainMenu,id) {
        return this._http.post(this.myAppUrl + '/api/menu/Put/'+id+'?access_token='+this.accessToken.access_token, MainMenu)
            .map((response: Response) => response)
            .catch(this.errorHandler);
    }

    deleteMainMenu(id) {
        return this._http.get(this.myAppUrl + "/api/menu/Delete/" + id+'?access_token='+this.accessToken.access_token)
            .map((response: Response) => response)
            .catch(this.errorHandler);
    }
    duplicateMenuName(MainMenu){      
        return this._http.post(this.myAppUrl + '/api/menu/Duplicate?access_token='+this.accessToken.access_token, MainMenu)
            .map((response: Response) => response)
            .catch(this.errorHandler);
    }
    errorHandler(error: Response) {
        console.log(error);
        return Observable.throw(error);
    }
}