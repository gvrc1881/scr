import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable()
export class WorksService {
    url: string = environment.apiUrl;
    header: any;
    accessToken: any = JSON.parse(localStorage.getItem("accessToken"));

    constructor(private http: HttpClient) {
        this.header = new HttpHeaders({
            'Content-Type':'application/json',
            'Accept':'application/json',
             'Authorization': `Bearer ${this.accessToken}`
        });
    }

    findAllWorks(){
        return this.http.get(this.url+"/findAllWorks" , {headers : this.header});
    }
    
    saveWork(work) {
        return this.http.post(this.url + "/addWork",work,{ headers: this.header});
    }

    findWorkById(id: number){
        return this.http.get(this.url + "/findWork/" +id , { headers : this.header });
    }

    updateWork(work) {
        console.log('update service:::'+work);
        return this.http.put(this.url + "/updateWork",work,{ headers: this.header});
    }

    deleteWorkById(id: number) {
        return this.http.delete(this.url + "/deleteWork/" +id ,{headers: this.header});
    }

}