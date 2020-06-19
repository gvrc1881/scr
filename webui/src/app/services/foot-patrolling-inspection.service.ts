import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { FootPatrollingInspectionModel } from '../models/foot-patrolling-inspection.model';

@Injectable()
export class FootPatrollingInspectionService {
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
    
    getAllFPInspectionItems () {
        return this.http.get<FootPatrollingInspectionModel[]>(this.myAppUrl + '/findAllFPInspectionItems', { headers: this.header });
    }

    saveFPInspection(model) {
        return this.http.post(this.myAppUrl + "/addFPInspection",model,{ headers: this.header});
    }

    findFPInspectionItemById(id: number){
        return this.http.get(this.myAppUrl + "/findFPInspectionItemById/" +id , { headers : this.header });
    }

    updateFPInspectionItem (model) {
        return this.http.put(this.myAppUrl + "/updateFPInspectionItem",model,{ headers: this.header});
    }

    deleteFPInspectionItem(id: number) {
        return this.http.delete(this.myAppUrl + "/deleteFPInspectionItem/" +id ,{headers: this.header});
    }

    
}