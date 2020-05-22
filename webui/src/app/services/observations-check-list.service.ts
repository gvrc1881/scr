import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { ObservationsCheckListModel } from '../models/observations-check-list.model';

@Injectable()
export class ObservationsCheckListService {
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
    
    getAllObservationCheckListDetails () {
        return this.http.get<ObservationsCheckListModel[]>(this.myAppUrl + '/findAllObservationCheckList', { headers: this.header });
    }

    saveObservationCheckList (model) {
    console.log('json object::'+JSON.stringify(model));
        return this.http.post(this.myAppUrl + "/addObservationCheckList",model,{ headers: this.header});
    }

    findObservationCheckListById(id: number){
        return this.http.get(this.myAppUrl + "/findObservationCheckListById/" +id , { headers : this.header });
    }

    updateObservationCheckList (model) {
        return this.http.put(this.myAppUrl + "/updateObservationCheckList",model,{ headers: this.header});
    }

    deleteObservationCheckList(id: number) {
        return this.http.delete(this.myAppUrl + "/deleteObservationCheckList/" +id ,{headers: this.header});
    }


}