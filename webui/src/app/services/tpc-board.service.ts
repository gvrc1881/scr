import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { TPCBoardModel } from '../models/tpc-board.model';

@Injectable()
export class TPCBoardService {
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
    
    getAllTPCBoardDetails () {
        return this.http.get<TPCBoardModel[]>(this.myAppUrl + '/findAllTPCBoard', { headers: this.header });
    }

    saveTPCBoard (model) {
    console.log('json object::'+JSON.stringify(model));
        return this.http.post(this.myAppUrl + "/addTPCBoard",model,{ headers: this.header});
    }

    findTPCBoardById(id: number){
        return this.http.get(this.myAppUrl + "/findTPCBoardById/" +id , { headers : this.header });
    }

    updateTPCBoard (model) {
        console.log('update service:::'+model);
        return this.http.put(this.myAppUrl + "/updateTPCBoard",model,{ headers: this.header});
    }

    deleteTPCBoardById(id: number) {
        return this.http.delete(this.myAppUrl + "/deleteTPCBoard/" +id ,{headers: this.header});
    }
    existsTpcBoardAndDataDiv(tpcBoard: string, dataDiv: string) {
        console.log("existsTpcBoard"+tpcBoard);
        console.log("existsdataDiv"+dataDiv);
        return this.http.get(this.myAppUrl + '/existsTpcBoardAndDataDiv/' + tpcBoard+"/"+dataDiv , { headers: this.header });
    }

}