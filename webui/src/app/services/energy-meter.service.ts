import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient , HttpHeaders } from '@angular/common/http';
import { EnergyMeterModel } from '../models/energy-meter.model';

@Injectable()
export class EnergyMeterService {
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

    getAllEnergyMeters(){
        return this.http.get<EnergyMeterModel[]>( this.url+"/findAllEnergyMeter" , {headers: this.header});
    }

    save(model) {
        return this.http.post(this.url + "/addEnergyMeter" , model , {headers: this.header});
    }

    findById(id : number){
        return this.http.get<EnergyMeterModel>(this.url+"/findEnergyMeter/"+id, {headers:this.header});
    }

    update(model){
        return this.http.put(this.url + "/updateEnergyMeter" , model , { headers: this.header });
    }

    delete(id: number) {
        return this.http.delete(this.url + "/deleteEnergyMeter/" + id, { headers: this.header});
    }
    
    existsFeederAndStartDate(feederId: string, startDate: string) {
        return this.http.get(this.url + '/existsFeederAndStartDate/' + feederId+"/"+startDate , { headers: this.header });
    }

}