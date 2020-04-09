import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { TimeIntervalModel } from '../models/time-interval.model';
import { SchedulerTrackingModel } from '../models/scheduler-tracking.model';
@Injectable()
export class SchedulerTrackingService {
    myAppUrl: string = environment.apiUrl;
    header: any;
    accessToken: any = JSON.parse(localStorage.getItem('accessToken'));
    constructor(private http: HttpClient) {
        this.header = new HttpHeaders({
            'Content-Type': 'application/json',
            'Accept': 'application/json',
             'Authorization': `Bearer ${this.accessToken}`
        });
    }
//findAllJobInfo //findOperationTypesHistoryInfo
    findAllJobInfo() {
        return this.http.get<SchedulerTrackingModel[]>(environment.apiUrl + '/findAllJobInfo', { headers: this.header });
    }

    reRunByIdByType(remarkDetails:any) {
        return this.http.post(environment.apiUrl + '/reRunWithRemark', remarkDetails, { headers: this.header });
    }

    divisionInfo(jobsHistoryId:number) {
        return this.http.get(environment.apiUrl + '/divisionInfo' +"/"+jobsHistoryId, { headers: this.header });
    }
    
    findJobsHistoryInfo(trackingId: number) {
        return this.http.get(environment.apiUrl + '/findJobsHistoryInfo/' + trackingId, { headers: this.header });
    }

    findOperationTypesHistoryInfo(operationId: number) {
        return this.http.get(environment.apiUrl + '/findOperationsInfo/' + operationId, { headers: this.header });
    }

    downloadXSL(details:any) {
        return this.http.post(environment.apiUrl + '/download', details, { headers: this.header });
    }
}