import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { ObservationCategoriesModel } from '../models/observation-categories.model';

@Injectable()
export class ObservationCategoriesService {
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
    
    getAllObservationCategoriesDetails () {
        return this.http.get<ObservationCategoriesModel[]>(this.myAppUrl + '/findAllObservationCategory', { headers: this.header });
    }

    saveObservationCategories (model) {
    console.log('json object::'+JSON.stringify(model));
        return this.http.post(this.myAppUrl + "/addObservationCategories",model,{ headers: this.header});
    }

    findObservationCategoriesById(id: number){
        return this.http.get(this.myAppUrl + "/findObservationCategoriesById/" +id , { headers : this.header });
    }

    updateObservationCategories (model) {
        console.log('update service:::'+model);
        return this.http.put(this.myAppUrl + "/updateObservationCategories",model,{ headers: this.header});
    }

    deleteObservationCategories(id: number) {
        return this.http.delete(this.myAppUrl + "/deleteObservationCategories/" +id ,{headers: this.header});
    }


}