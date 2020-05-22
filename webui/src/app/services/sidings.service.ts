import { Injectable, ɵConsole } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { SidingsModel } from '../models/sidings.model';


@Injectable()
export class SidingsService {
    url: string = environment.apiUrl;
    header: any;
    accessToken: any = JSON.parse(localStorage.getItem('accessToken'));
    constructor(private http: HttpClient) {
        this.header = new HttpHeaders({
            'Content-Type': 'application/json;charset=UTF-8',
            'accept': 'application/json',
            'Authorization': `Bearer ${this.accessToken}`
        });
    }
    stationCode(){
        return this.http.get(this.url+'/stationCode',{headers:this.header});
    }
    getAllSidingsItems(){
        return this.http.get<SidingsModel[]>( this.url+"/findAllSidingsItems" , {headers: this.header});
    }
    save(model) {
        console.log('save sidings:::'+model);
        return this.http.post(this.url + "/addSlidingsItem" , model , {headers: this.header});
    }
    findSidingsItemById(id : number){
        return this.http.get<SidingsModel>(this.url+"/findSidingsItemById/"+id, {headers:this.header});
    }

    update(model){
        console.log('update service:::'+model);
        return this.http.put(this.url + "/updateSlidingsItem" , model , { headers: this.header });
    }

    deleteSidingsItem(id: number) {
        return this.http.delete(this.url + "/deleteSidingsItem/" + id, { headers: this.header});
    }
   



}
    
    
