import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { ContentManagementModel } from '../models/content-management.model';


@Injectable()
export class ContentManagementService {
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

    uploadAttachedFiles(file: File[], saveDetails) {   
        let header = new HttpHeaders({           
             'Authorization': `Bearer ${this.accessToken}`
         });     
        let formdata: FormData = new FormData();
        for(var i=0;i<file.length;i++){
            formdata.append('file', file[i]);
        }
        formdata.append('GenOps', saveDetails.GenOps);
        formdata.append('description', saveDetails.description);
        formdata.append('divisionCode', saveDetails.divisionCode);
        formdata.append('createdBy', saveDetails.createdBy);
        formdata.append('zonal', saveDetails.zonal);
        formdata.append('FU', saveDetails.FU);
        formdata.append('topic', saveDetails.topic);
        formdata.append('assetTypeRlyId', saveDetails.assetTypeRlyId);
        formdata.append('make', saveDetails.make);
        formdata.append('model', saveDetails.model);
        formdata.append('docCategory', saveDetails.docCategory);        
        return this.http.post(environment.apiUrl + '/uploadAttachedFiles', formdata, { headers: header });
    }
    
    getUploadedFiles(id:number, value:string){       
        return this.http.get<ContentManagementModel[]>(environment.apiUrl + '/getUploadedFiles/'+id+'/'+value.replace(' ','-'), { headers: this.header });
    }

    deleteFile(id:number){
        return this.http.delete(environment.apiUrl + '/deleteFile/'+id, { headers: this.header });
    }
    updateDescription(content) {
        return this.http.put(environment.apiUrl + '/updateDescription', content, { headers: this.header });
    }
}