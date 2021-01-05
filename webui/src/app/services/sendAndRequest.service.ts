import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import { environment } from '../../environments/environment';
import { OwlDateTime } from 'ng-pick-datetime/date-time/date-time.class';


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
    requestForPOST(requestUrl, requestData: any, flag) {
    
        console.log(requestData)
        if (flag == true) {

            let header = new HttpHeaders({
                'Authorization': `Bearer ${this.accessToken}`
            });
            console.log(header)
            return this._http.post(environment.apiUrl + requestUrl, requestData, { headers: header });
        } else {
            return this._http.post(environment.apiUrl + requestUrl, requestData, { headers: this.header });
        }


    }
    //METHOD FOR PUT REQUESTS
    requestForPUT(requestUrl, requestData, flag) {
        if (flag) {
            let header = new HttpHeaders({
                'Authorization': `Bearer ${this.accessToken}`
            });
            this.header = header;
        }
        return this._http.put(environment.apiUrl + requestUrl, requestData, { headers: this.header });

    }
    //METHOD FOR DELETE
    requestForDELETE(requestUrl, requestData) {
        return this._http.delete(environment.apiUrl + requestUrl + requestData, { headers: this.header });
    }
    //METHOD FOR GET ID REQUESTS
    //  requestForEXIST(requestUrl,requestData) {        
    //  return this._http.get<any[]>(environment.apiUrl + requestUrl+requestData, { headers: this.header });
    //}
    convertIndiaStandardTimeToTimestamp(dateformat: string) {
       // var month, day, year, hours, minutes, seconds;
        var date = new Date(dateformat);

       // month = ("0" + (date.getMonth() + 1)).slice(-2),
        //day = ("0" + date.getDate()).slice(-2);
       /// hours = ("0" + date.getHours()).slice(-2);
        // minutes = ("0" + date.getMinutes()).slice(-2);
        // seconds = ("0" + date.getSeconds()).slice(-2);

        var orignalDate = [date.getFullYear(), ("0" + (date.getMonth() + 1)).slice(-2) , ("0" + date.getDate()).slice(-2)].join("-");
        var time = [("0" + date.getHours()).slice(-2), ("0" + date.getMinutes()).slice(-2), ("0" + date.getSeconds()).slice(-2)].join(":");
        var timestamp  = [orignalDate, time].join(" ");
       
        return timestamp;
    }

    Duration(fromdate:string,thrudate:string)
    {
        var fdate = new Date(fromdate);
        var tdate = new Date(thrudate);      
        
            var diff=tdate.getTime()-fdate.getTime(); 
 
            let days=Math.floor(diff / (60*60*24*1000));
              
            let hours=Math.floor(diff / (60*60*1000))-(days*24);

            let hour=hours+(days*24);
           
            let minutes=Math.floor(diff /(60*1000)) -((days*24*60) + (hours*60));
            
            let seconds=Math.floor(diff / 1000) - ((days*24*60*60)+(hours*60*60)+(minutes*60))
           
            var duration =String(hour)+":" + String(minutes)+":" +String(seconds) ;
               
             console.log("duarionin send rq="+duration)
             return  duration;
       
    }
  

}

