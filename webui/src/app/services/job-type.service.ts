import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { JobTypeModel } from '../models/job-type.model';
@Injectable()
export class JobTypeService {
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

    getAllJobTypes() {
        return this.http.get<JobTypeModel[]>(environment.apiUrl + '/findAllJobTypes', { headers: this.header });
    }

    getJobTypeById(id: number) {
        return this.http.get(environment.apiUrl + '/findJobTypeById/' + id, { headers: this.header });
    }

    existsJobTypeName(jobTypeName: string) {
        return this.http.get(environment.apiUrl + '/existsjobTypeName/' + jobTypeName, { headers: this.header });
    }

    addJobType(jobType) {
        return this.http.post(environment.apiUrl + '/addJobType', jobType, { headers: this.header });
    }

    updateJobType(jobType) {
        return this.http.put(environment.apiUrl + '/updateJobType', jobType, { headers: this.header });
    }

    deleteJobType(id: string) {
        return this.http.delete(environment.apiUrl + '/deleteJobType/' + id, { headers: this.header });
    }
}