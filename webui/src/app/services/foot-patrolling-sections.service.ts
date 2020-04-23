import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { FootPatrollingSectionsModel } from '../models/foot-patrolling-sections.model';

@Injectable()
export class FootPatrollingSectionsService {
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
    
    getAllFPSectionsItems () {
        return this.http.get<FootPatrollingSectionsModel[]>(this.myAppUrl + '/findAllFPSectionsItems', { headers: this.header });
    }

    saveFPSections(model) {
    console.log('jsonFPSections'+JSON.stringify(model));
        return this.http.post(this.myAppUrl + "/addFPSectionsItem",model,{ headers: this.header});
    }

    findFPSectionsItemById(id: number){
        return this.http.get(this.myAppUrl + "/findFPSectionsItemById/" +id , { headers : this.header });
    }

    updateFPSectionsItem (model) {
        console.log('update service:::'+model);
        return this.http.put(this.myAppUrl + "/updateFPSectionsItem",model,{ headers: this.header});
    }

    deleteFPSectionsItem(id: number) {
        return this.http.delete(this.myAppUrl + "/deleteFPSectionsItem/" +id ,{headers: this.header});
    }


}