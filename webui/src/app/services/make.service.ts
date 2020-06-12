import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient , HttpHeaders } from '@angular/common/http';
import { MakeModel } from '../models/make.model';

@Injectable()
export class MakeService {
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



    getAllMake(){
        
        return this.http.get<MakeModel[]>( this.url+"/findAllMake", {headers: this.header});
    }

    save(make) {
        console.log('make==='+JSON.stringify(make));
        return this.http.post(this.url + "/addMake" , make, {headers: this.header});
    }
    update(make){
        console.log('update make'+JSON.stringify(make));
        return this.http.put(this.url + "/updateMake" , make , { headers: this.header });
    }
    delete(id: number) {
        return this.http.delete(this.url + "/deleteMake/" + id, { headers: this.header});
    }
    findMakeById(id : number){
        return this.http.get<MakeModel>(this.url+"/findMakeById/"+id, {headers:this.header});
    }
    existsMakeName(makeName:string)
    {
        return this.http.get<MakeModel>(this.url+"/existsMakeName/"+makeName ,{headers:this.header}); 
    }
    existsMakeCode(makeCode :string){
        return this.http.get<MakeModel>(this.url+"/existsMakeCode/"+makeCode ,{headers:this.header});
    }


    }

  