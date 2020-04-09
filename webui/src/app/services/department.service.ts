import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { DepartmentModel } from '../models/department.model';
@Injectable()
export class DepartmentService {
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

    findAllDepartments() {
        return this.http.get<DepartmentModel[]>(environment.apiUrl + '/findAllDepartments', { headers: this.header });
    }

    findRepartmentById(departmentId: number) {
        return this.http.get(environment.apiUrl + '/findRepartmentById/' + departmentId, { headers: this.header });
    }

    existsDepartmentName(departmentName: string) {
        return this.http.get(environment.apiUrl + '/existsDepartmentName/' + departmentName, { headers: this.header });
    }

    addDepartment(department) {
        return this.http.post(environment.apiUrl + '/addDepartment', department, { headers: this.header });
    }

    updateDepartment(department) {
        return this.http.put(environment.apiUrl + '/updateDepartment', department, { headers: this.header });
    }

    deleteDepartment(departmentId: number) {
        return this.http.delete(environment.apiUrl + '/deleteDepartment/' + departmentId, { headers: this.header });
    }
}