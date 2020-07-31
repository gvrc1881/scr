import { Injectable, Inject } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import { environment } from '../../environments/environment';

@Injectable()
export class SendAndRequestService {
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

    // METHOD FOR GET REQUESTS
    requestForGET(requestUrl) {        
        return this._http.get<any[]>(environment.apiUrl + requestUrl, { headers: this.header });
    } 
    
    //METHOD FOR GET ID REQUESTS
   // requestForGETId(requestUrl,requestData) {        
     //   return this._http.get<any[]>(environment.apiUrl + requestUrl+requestData, { headers: this.header });
    //}
    // METHOD FOR POST REQUESTS
    requestForPOST(requestUrl, requestData:any, flag){
        console.log(flag)
        if(flag == true){
            
            let header = new HttpHeaders({           
                'Authorization': `Bearer ${this.accessToken}`
            });
            console.log(header)
            return this._http.post(environment.apiUrl + requestUrl, requestData, { headers: header });
        }else{
            return this._http.post(environment.apiUrl + requestUrl, requestData, { headers: this.header });
        }
        
        
    }
    //METHOD FOR PUT REQUESTS
    requestForPUT(requestUrl, requestData, flag){
        if(flag){
            let header = new HttpHeaders({           
                'Authorization': `Bearer ${this.accessToken}`
            });
            this.header = header;
        }
        return this._http.put(environment.apiUrl + requestUrl, requestData, { headers: this.header });

    }
    //METHOD FOR DELETE
    requestForDELETE(requestUrl, requestData){
        return this._http.delete(environment.apiUrl + requestUrl+requestData, { headers: this.header });
    }
      //METHOD FOR GET ID REQUESTS
    //  requestForEXIST(requestUrl,requestData) {        
      //  return this._http.get<any[]>(environment.apiUrl + requestUrl+requestData, { headers: this.header });
    //}
    
}

