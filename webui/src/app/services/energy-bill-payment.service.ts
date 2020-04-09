import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { EnergyBillPaymentModel } from '../models/energy-bill-payment.model';
import { Observable } from 'rxjs';

@Injectable()
export class EnergyBillPaymentService {
    url: string = environment.apiUrl;
    header: any;
    accessToken: any = JSON.parse(localStorage.getItem('accessToken'));
    constructor(private http: HttpClient){
        this.header = new HttpHeaders({
            'Content-Type':'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer  ${this.accessToken}`
        });
    }

    findAllEnergyBillPayments() {
        return this.http.get<EnergyBillPaymentModel[]>(this.url + '/findAllEnergyBillPayments', { headers: this.header });
    }

    saveEneBillPayment(eleBillPayment) {
        return this.http.post(this.url + "/addEneBillPayment",eleBillPayment,{ headers: this.header});
    }

    findEneBillPaymentById(id: number){
        return this.http.get(this.url + "/findEneBillPayment/" +id , { headers : this.header });
    }

    updateEneBillPayment(eleBillPayment) {
        console.log('update service:::'+eleBillPayment);
        return this.http.put(this.url + "/updateEneBillPayment",eleBillPayment,{ headers: this.header});
    }

    deleteEneBillPaymentById(id: number) {
        return this.http.delete(this.url + "/deleteEneBillPayment/" +id ,{headers: this.header});
    }
}