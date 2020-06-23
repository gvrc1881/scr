import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient , HttpHeaders } from '@angular/common/http';
import { ModelModel } from '../models/model.model';

@Injectable()
export class ModelService {
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

    getAllModel(){
        
        return this.http.get<ModelModel[]>( this.url+"/findAllModel", {headers: this.header});
    }

    save(model) {
        console.log('model==='+JSON.stringify(model));
        return this.http.post(this.url + "/addModel" , model, {headers: this.header});
    }
    update(model){
        console.log('update model'+JSON.stringify(model));
        return this.http.put(this.url + "/updateModel" , model , { headers: this.header });
    }
    delete(id: number) {
        return this.http.delete(this.url + "/deleteModel/" + id, { headers: this.header});
    }
    findModelById(id : number){
        return this.http.get<ModelModel>(this.url+"/findModelById/"+id, {headers:this.header});
    }
   
    existsModelCode(modelCode :string){
        return this.http.get<ModelModel>(this.url+"/existsModelCode/"+modelCode ,{headers:this.header});
    }


    }

  