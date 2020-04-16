import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
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
    findFunctionslUnits(){
        return this.http.get<any[]>(environment.apiUrl + '/facilityNames', { headers: this.header });
    }
    findDriveDataById(id:number){
        return this.http.get<any[]>(environment.apiUrl + '/driveById/'+id, { headers: this.header });
    }
    saveDriveData(saveDriveData){
        return this.http.post(environment.apiUrl + '/saveDrive', saveDriveData, { headers: this.header });
    }updateDriveData(updateDriveData){
        return this.http.put(environment.apiUrl + '/updateDrive', updateDriveData, { headers: this.header });
    }
    deleteDriveData(id:number){
        return this.http.delete<any[]>(environment.apiUrl + '/deleteDrive/'+id, { headers: this.header });
    }

    getDrivesCheckListData() {
        return this.http.get<DriveModel[]>(environment.apiUrl + '/checklist', { headers: this.header });            
    }
    findMeasureActivityList() {
        return this.http.get<any[]>(environment.apiUrl + '/measureActivityList', { headers: this.header });            
    }
    saveCheckListData(save){
        return this.http.post(environment.apiUrl + '/saveCheckList', save, { headers: this.header });
    }
    updateCheckListData(update){
        return this.http.put(environment.apiUrl + '/updateCheckList', update, { headers: this.header });
    }
    findCheckListDataById(id:number){
        return this.http.get<any[]>(environment.apiUrl + '/checkListById/'+id, { headers: this.header });
    }
    deleteCheckListData(id:number){
        return this.http.delete<any[]>(environment.apiUrl + '/deleteCheckList/'+id, { headers: this.header });
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
    saveInspectionsData(saveDetails, file: File[]){
        let formdata: FormData = new FormData();
       for(var i=0;i<file.length;i++){
           formdata.append('file', file[i]);
       }
       formdata.append('inspectionType', saveDetails.inspectionType);
       formdata.append('section', saveDetails.section);
       formdata.append('sectionStartLocation', saveDetails.sectionStartLocation);
       formdata.append('sectionEndLocation', saveDetails.sectionEndLocation);
       formdata.append('dateOfInspection', saveDetails.dateOfInspection);
       formdata.append('tkm', saveDetails.tkm);
       formdata.append('rkm', saveDetails.rkm);
       formdata.append('remarks', saveDetails.remarks);
       formdata.append('authorisationDate', saveDetails.authorisationDate);
       formdata.append('chargingDate', saveDetails.chargingDate);
       formdata.append('station', saveDetails.station);
       formdata.append('stipulationsId', saveDetails.stipulationsId);
       formdata.append('createdBy', saveDetails.createdBy);
       formdata.append('createdOn', saveDetails.createdOn);
       formdata.append('updatedBy', saveDetails.updatedBy);
       formdata.append('updatedOn', saveDetails.updatedOn);
       let header = new HttpHeaders({           
           'Authorization': `Bearer ${this.accessToken}`
          // 'Content-Type': 'multipart/form-data; boundary=------WebKitFormBoundary7MA4YWxkTrZu0gW'
       }); 
        return this.http.post(environment.apiUrl + '/saveInspections', formdata, { headers: header });
    }
    updateInspectionsData(update, file: File[]){
        let formdata: FormData = new FormData();
       for(var i=0;i<file.length;i++){
           formdata.append('file', file[i]);
       }
       formdata.append('id', update.id);
       formdata.append('inspectionType', update.inspectionType);
       formdata.append('section', update.section);
       formdata.append('sectionStartLocation', update.sectionStartLocation);
       formdata.append('sectionEndLocation', update.sectionEndLocation);
       formdata.append('dateOfInspection', update.dateOfInspection);
       formdata.append('tkm', update.tkm);
       formdata.append('rkm', update.rkm);
       formdata.append('remarks', update.remarks);
       formdata.append('authorisationDate', update.authorisationDate);
       formdata.append('chargingDate', update.chargingDate);
       formdata.append('station', update.station);
       formdata.append('stipulationsId', update.stipulationsId);
       formdata.append('createdBy', update.createdBy);
       formdata.append('createdOn', update.createdOn);
       formdata.append('updatedBy', update.updatedBy);
       formdata.append('updatedOn', update.updatedOn);
       let header = new HttpHeaders({           
           'Authorization': `Bearer ${this.accessToken}`
          // 'Content-Type': 'multipart/form-data; boundary=------WebKitFormBoundary7MA4YWxkTrZu0gW'
       }); 
        return this.http.post(environment.apiUrl + '/updateInspections', formdata, { headers: header });
    }
    findInspectionsDataById(id:number){
        return this.http.get<any[]>(environment.apiUrl + '/inspectionsById/'+id, { headers: this.header });
    }
    deleteInspectionsData(id:number){
        return this.http.delete<any[]>(environment.apiUrl + '/deleteInspections/'+id, { headers: this.header });
    }
    inspectionsFileInfo(id:number){
        return this.http.get<any[]>(environment.apiUrl + '/inspectionsFileInfoById/'+id, { headers: this.header });
    }

    getStipulationData(){
        return this.http.get<StipulationstModel[]>(environment.apiUrl + '/stipulations', { headers: this.header });
    }
    findAssertTypeListFromProduct() {
        return this.http.get<any[]>(environment.apiUrl + '/assertType', { headers: this.header });            
    }
    saveStipulationData(saveDetails, file: File[]){
         let formdata: FormData = new FormData();
        for(var i=0;i<file.length;i++){
            formdata.append('file', file[i]);
        }
        formdata.append('stipulation', saveDetails.stipulation);
        formdata.append('stipulationTo', saveDetails.stipulationTo);
        formdata.append('dateOfStipulation', saveDetails.dateOfStipulation);
        formdata.append('dateComplied', saveDetails.dateComplied);
        formdata.append('compliance', saveDetails.compliance);
        formdata.append('compliedBy', saveDetails.compliedBy);
        formdata.append('assetType', saveDetails.assetType);
        formdata.append('createdBy', saveDetails.createdBy);
        formdata.append('createdOn', saveDetails.createdOn);
        formdata.append('updatedBy', saveDetails.updatedBy);
        formdata.append('updatedOn', saveDetails.updatedOn);
        let header = new HttpHeaders({           
            'Authorization': `Bearer ${this.accessToken}`
           // 'Content-Type': 'multipart/form-data; boundary=------WebKitFormBoundary7MA4YWxkTrZu0gW'
        }); 
        return this.http.post(environment.apiUrl + '/saveStipulations', formdata, { headers: header });
    }
    updateStipulationData(update, file: File[]){
        let formdata: FormData = new FormData();
       for(var i=0;i<file.length;i++){
           formdata.append('file', file[i]);
       }
       formdata.append('id', update.id);
       formdata.append('stipulation', update.stipulation);
       formdata.append('stipulationTo', update.stipulationTo);
       formdata.append('dateOfStipulation', update.dateOfStipulation);
       formdata.append('dateComplied', update.dateComplied);
       formdata.append('compliance', update.compliance);
       formdata.append('compliedBy', update.compliedBy);
       formdata.append('assetType', update.assetType);
       formdata.append('createdBy', update.createdBy);
       formdata.append('createdOn', update.createdOn);
       formdata.append('updatedBy', update.updatedBy);
       formdata.append('updatedOn', update.updatedOn);
       let header = new HttpHeaders({           
        'Authorization': `Bearer ${this.accessToken}`
    }); 
        return this.http.post(environment.apiUrl + '/updateStipulations', formdata, { headers: header });
    }
    findStipulationDataById(id:number){
        return this.http.get<any[]>(environment.apiUrl + '/stipulationsById/'+id, { headers: this.header });
    }
    deleteStipulationData(id:number){
        return this.http.delete<any[]>(environment.apiUrl + '/deleteStipulations/'+id, { headers: this.header });
    }
    stipulationFileInfo(id:number){
        return this.http.get<any[]>(environment.apiUrl + '/stipulationFileInfoById/'+id, { headers: this.header });
    }

    deleteFile(id:string,fileName:string, type:string){
        return this.http.get<any[]>(environment.apiUrl + '/deleteFile/'+id+'/'+fileName+'/'+type, { headers: this.header });
    }
}