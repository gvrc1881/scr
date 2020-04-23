import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient , HttpHeaders } from '@angular/common/http';
import { StationsSectionsModule } from '../components/energy-bill-payment/stations-sections/stations-sections.module';
import { StationsSectionsModel } from '../models/stations-sections.model';

@Injectable()
export class StationsSectionsService {
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

    getAllStationsSections(){
        return this.http.get<StationsSectionsModel[]>( this.url+"/findAllStationSections" , {headers: this.header});
    }

    saveStationsSections(model) {
        console.log("saveStationsSections"+model);
        return this.http.post(this.url + "/addStationSections" , model , {headers: this.header});
    }

    findStationSectionsById(id : number){
        return this.http.get<StationsSectionsModel>(this.url+"/findStationSectionsById/"+id, {headers:this.header});
    }

    update(model){
        return this.http.put(this.url + "/updateStationSections" , model , { headers: this.header });
    }

    deleteStationSections(id: number) {
        return this.http.delete(this.url + "/deleteStationSections/" + id, { headers: this.header});
    }

}