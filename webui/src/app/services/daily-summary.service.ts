import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient , HttpHeaders } from '@angular/common/http';
import { DailySummaryModule } from '../components/energy-bill-payment/daily-summary/daily-summary.module';
import {DailySummaryModel} from '../models/daily-summary.model';

@Injectable()
export class DailySummaryService {
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

    getAllDailySummary(){
        return this.http.get<DailySummaryModel[]>( this.myAppUrl+"/findAllDailySummary" , {headers: this.header});
    }

    saveDailySummary(model) {
        return this.http.post(this.myAppUrl + "/addDailySummary" , model , {headers: this.header});
    }

    findDailySummaryById(id : number){
        return this.http.get<DailySummaryModel>(this.myAppUrl+"/findDailySummaryById/"+id, {headers:this.header});
    }

    updateDailySummary(model){
        return this.http.put(this.myAppUrl + "/updateDailySummary",model,{ headers: this.header});
    }
    
    deleteDailySummary(id: number) {
        return this.http.delete(this.myAppUrl + "/deleteDailySummary/" + id, { headers: this.header});
    }
    existsFacilityIdAndCreatedDate(facilityId: string, createdDate: string) {
        return this.http.get(this.myAppUrl + '/existsFacilityIdAndCreatedDate/' + facilityId+"/"+createdDate , { headers: this.header });
    }
}