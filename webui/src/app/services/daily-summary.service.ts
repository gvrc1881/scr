import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient , HttpHeaders } from '@angular/common/http';
import { DailySummaryModule } from '../components/energy-bill-payment/daily-summary/daily-summary.module';
import {DailySummaryModel} from '../models/daily-summary.model';

@Injectable()
export class DailySummaryService {
    url:string =environment.apiUrl;
    header: any;
    accessToken: any = JSON.parse(localStorage.getItem("accessToken"));
    constructor(private http: HttpClient){
        this.header = new HttpHeaders({
            'Content-Type':'application/json',
            'Accept':'application/json',
            'Authorization' : `Bearer ${this.accessToken}`
        });

    }

    getAllDailySummary(){
        return this.http.get<DailySummaryModel[]>( this.url+"/findAllDailySummary" , {headers: this.header});
    }

    save(model) {
        return this.http.post(this.url + "/addDailySummary" , model , {headers: this.header});
    }

    findDailySummaryById(id : number){
        return this.http.get<DailySummaryModel>(this.url+"/findDailySummaryById/"+id, {headers:this.header});
    }

    update(model){
        return this.http.put(this.url + "/updateDailySummary" , model , { headers: this.header });
    }

    deleteDailySummary(id: number) {
        return this.http.delete(this.url + "/deleteDailySummary/" + id, { headers: this.header});
    }

}