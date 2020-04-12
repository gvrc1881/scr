import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { TractionEnergyTariffModel } from '../models/traction-energy-tariff.model';

@Injectable()
export class TractionEnergyTariffService {
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
    
    findAllTractionEnergyTariff() {
        return this.http.get<TractionEnergyTariffModel[]>(this.myAppUrl + '/findAllTractionEnergyTariff', { headers: this.header });
    }

    saveTrationEneTariff(trationEneTariff) {
        return this.http.post(this.myAppUrl + "/addTractionEnergyTariff",trationEneTariff,{ headers: this.header});
    }

    findTrationEneTariffById(id: number){
        return this.http.get(this.myAppUrl + "/findTractionEnergyTariff/" +id , { headers : this.header });
    }

    updateTrationEneTariff(trationEneTariff) {
        console.log('update service:::'+trationEneTariff);
        return this.http.put(this.myAppUrl + "/updateTractionEnergyTariff",trationEneTariff,{ headers: this.header});
    }

    deleteTractionEneTariffById(id: number) {
        return this.http.delete(this.myAppUrl + "/deleteTractionEnergyTariff/" +id ,{headers: this.header});
    }
    
    uploadAttachedFiles(file: File[], saveDetails) {   
        let header = new HttpHeaders({           
             'Authorization': `Bearer ${this.accessToken}`
         });     
        let formdata: FormData = new FormData();
        for(var i=0;i<file.length;i++){
            formdata.append('file', file[i]);
        }
        formdata.append('tractionEnergyTariffId', saveDetails.tractionEnergyTariffId);
        formdata.append('contentCategory', saveDetails.contentCategory);
        formdata.append('description', saveDetails.description);
        formdata.append('divisionCode', saveDetails.divisionCode);
        formdata.append('createdBy', saveDetails.createdBy);
        formdata.append('zonal', saveDetails.zonal);
        formdata.append('FU', saveDetails.FU);
        formdata.append('contentTopic', saveDetails.contentTopic);        
        return this.http.post(environment.apiUrl + '/tariffUploadFiles', formdata, { headers: header });
    }

}