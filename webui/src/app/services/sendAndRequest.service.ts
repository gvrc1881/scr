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

    // METHOD FOR POST REQUESTS
    requestForPOST(requestUrl, requestData){
        return this._http.post(environment.apiUrl + requestUrl, requestData, { headers: this.header });
    }

}