import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { TimeIntervalModel } from '../models/time-interval.model';
@Injectable()
export class TimeIntervalService {
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

    findAllTimeIntervals() {
        return this.http.get<TimeIntervalModel[]>(environment.apiUrl + '/findAllTimeIntervals', { headers: this.header });
    }

    getTimeIntervalById(id: number) {
        return this.http.get(environment.apiUrl + '/findTimeIntervalById/' + id, { headers: this.header });
    }

    existsTimeInterval(timeInterval: number) {
        return this.http.get(environment.apiUrl + '/existsTimeInterval/' + timeInterval, { headers: this.header });
    }

    addTimeInterval(timeInterval) {
        return this.http.post(environment.apiUrl + '/addTimeInterval', timeInterval, { headers: this.header });
    }

    updateTimeInterval(timeInterval) {
        return this.http.put(environment.apiUrl + '/updateTimeInterval', timeInterval, { headers: this.header });
    }

    deleteTimeInterval(id: string) {
        return this.http.delete(environment.apiUrl + '/deleteTimeInterval/' + id, { headers: this.header });
    }
}