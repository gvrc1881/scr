import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { ScheduleJobModel } from '../models/schedule-job.model';
@Injectable()
export class ScheduleJobService {
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

    findAllSchedulerJobs() {
        return this.http.get<ScheduleJobModel[]>(environment.apiUrl + '/findAllSchedulerJobs', { headers: this.header });
    }

    getScheduleJobById(id: number) {
        return this.http.get(environment.apiUrl + '/findScheduleJobById/' + id, { headers: this.header });
    }

    existsRepositoryName(repositoryName: number) {
        return this.http.get(environment.apiUrl + '/existsRepositoryName/' + repositoryName, { headers: this.header });
    }

    addSchedulerSettings(schedulerJob) {
        return this.http.post(environment.apiUrl + '/addSchedulerSettings', schedulerJob, { headers: this.header });
    }
    findAllSchedulerJobsList(){
        return this.http.get(environment.apiUrl + '/findAllSchedulerJobsList', { headers: this.header });
    }

    updateScheduleJob(scheduleJob) {
        return this.http.put(environment.apiUrl + '/updateSchedulerSettings', scheduleJob, { headers: this.header });
    }

    deleteScheduleJob(id: string) {
        return this.http.delete(environment.apiUrl + '/deleteSchedulerSettings/' + id, { headers: this.header });
    }
}