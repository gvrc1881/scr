import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { TPCBoardDepotAssocModel } from '../models/tpc-board-depot-assoc.model';

@Injectable()
export class TPCBoardDepotAssocService {
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
    
    getAllTPCBoardDepotAssocDetails () {
        return this.http.get<TPCBoardDepotAssocModel[]>(this.myAppUrl + '/findAllTPCBoardDepotAssoc', { headers: this.header });
    }

    saveTPCBoardDepotAssoc (model) {
    console.log('json object::'+JSON.stringify(model));
        return this.http.post(this.myAppUrl + "/addTPCBoardDeotAssoc",model,{ headers: this.header});
    }

    findTPCBoardDepotAssocById(id: number){
        return this.http.get(this.myAppUrl + "/findTPCBoardDepotAssocById/" +id , { headers : this.header });
    }

    updateTPCBoardDepotAssoc (model) {
        
        return this.http.put(this.myAppUrl + "/updateTPCBoardDepotAssoc",model,{ headers: this.header});
    }

    deleteTPCBoarDepotAssocdById(id: number) {
        return this.http.delete(this.myAppUrl + "/deleteTPCBoardDepotAssoc/" +id ,{headers: this.header});
    }
    existsByTpcBoard(tpcBoard: string) {
        console.log('existsTpcBoard'+tpcBoard);
        return this.http.get(environment.apiUrl + '/existsByTpcBoard/' + tpcBoard, { headers: this.header });
    }
    existsUnitName(unitName: string) {
        return this.http.get(environment.apiUrl + '/existsUnitName/' + unitName, { headers: this.header });
    }
    
}