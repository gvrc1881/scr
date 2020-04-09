import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DriveModel, ElectrificationTargetstModel, StipulationstModel, InspectionstModel } from '../models/drive.model';

@Injectable()
export class DrivesService {
    myAppUrl: string = environment.apiUrl;
    header:any;
    accessToken:any = JSON.parse(localStorage.getItem('accessToken'));
    
    constructor(private http: HttpClient) {
        this.header = new HttpHeaders({
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${this.accessToken}`
        });         
    }
    getDrivesData() {
        return this.http.get<DriveModel[]>(environment.apiUrl + '/drives', { headers: this.header });
    }
//functionalLocationsTypes
    findDepoTypeList(){
        return this.http.get<any[]>(environment.apiUrl + '/functionalLocationsTypes', { headers: this.header });
    }
    findAssetTypeList(assetType){
        return this.http.get<any[]>(environment.apiUrl + '/allAssetTypeReports/'+assetType, { headers: this.header });
    }
    findDriveDataById(id:number){
        return this.http.get<any[]>(environment.apiUrl + '/driveById/'+id, { headers: this.header });
    }
    saveDriveData(saveDriveData){
        return this.http.post(environment.apiUrl + '/saveDrive', saveDriveData, { headers: this.header });
    }
    deleteDriveData(id:number){
        return this.http.delete<any[]>(environment.apiUrl + '/deleteDrive/'+id, { headers: this.header });
    }




    getDrivesCheckListData() {
        return this.http.get<DriveModel[]>(environment.apiUrl + '/checklist', { headers: this.header });            
    }

    getElectrificationTargetsData(){
        return this.http.get<ElectrificationTargetstModel[]>(environment.apiUrl + '/electrificationTargets', { headers: this.header });            
    }
    saveElectrificationTargetsData(save){
        return this.http.post(environment.apiUrl + '/saveElectrificationTargets', save, { headers: this.header });
    }
    updateElectrificationTargetsData(update){
        return this.http.put(environment.apiUrl + '/updateElectrificationTargets', update, { headers: this.header });
    }
    findElectrificationTargetsDataById(id:number){
        return this.http.get<any[]>(environment.apiUrl + '/electrificationTargetsById/'+id, { headers: this.header });
    }
    deleteElectrificationTargetsData(id:number){
        return this.http.delete<any[]>(environment.apiUrl + '/deleteElectrificationTargets/'+id, { headers: this.header });
    }
    
    getInspectionData(){
        return this.http.get<InspectionstModel[]>(environment.apiUrl + '/inspections', { headers: this.header });
    }
    saveInspectionsData(save){
        return this.http.post(environment.apiUrl + '/saveInspections', save, { headers: this.header });
    }
    updateInspectionsData(update){
        return this.http.put(environment.apiUrl + '/updateInspections', update, { headers: this.header });
    }
    findInspectionsDataById(id:number){
        return this.http.get<any[]>(environment.apiUrl + '/inspectionsById/'+id, { headers: this.header });
    }
    deleteInspectionsData(id:number){
        return this.http.delete<any[]>(environment.apiUrl + '/deleteInspections/'+id, { headers: this.header });
    }


    getStipulationData(){
        return this.http.get<StipulationstModel[]>(environment.apiUrl + '/stipulations', { headers: this.header });
    }
    saveStipulationData(save){
        return this.http.post(environment.apiUrl + '/saveStipulations', save, { headers: this.header });
    }
    updateStipulationData(update){
        return this.http.put(environment.apiUrl + '/updateStipulations', update, { headers: this.header });
    }
    findStipulationDataById(id:number){
        return this.http.get<any[]>(environment.apiUrl + '/stipulationsById/'+id, { headers: this.header });
    }
    deleteStipulationData(id:number){
        return this.http.delete<any[]>(environment.apiUrl + '/deleteStipulations/'+id, { headers: this.header });
    }

}