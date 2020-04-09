import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient , HttpHeaders } from '@angular/common/http';
import { GuidenceItemModule } from '../components/energy-bill-payment/guidence-item/guidence-item.module';
import { GuidenceItemModel } from '../models/guidence-item.model';

@Injectable()
export class GuidenceItemService {
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

    getAllGuidenceItems(){
        return this.http.get<GuidenceItemModel[]>( this.url+"/findAllGuidenceItems" , {headers: this.header});
    }

    save(model) {
        return this.http.post(this.url + "/addGuidenceItem" , model , {headers: this.header});
    }

    findGuidenceItemById(id : number){
        return this.http.get<GuidenceItemModel>(this.url+"/findGuidenceItemById/"+id, {headers:this.header});
    }

    update(model){
        return this.http.put(this.url + "/updateGuidenceItem" , model , { headers: this.header });
    }

    deleteGuidenceItem(id: number) {
        return this.http.delete(this.url + "/deleteGuidenceItem/" + id, { headers: this.header});
    }

}