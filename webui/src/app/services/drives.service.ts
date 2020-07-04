import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { DriveModel, ElectrificationTargetstModel, StipulationstModel, InspectionstModel, FailureAnalysisModel, DriveTargetModel, DriveDailyProgressModel, DriveCategoryAssoModel, DriveCategoryModel } from '../models/drive.model';
import { format } from 'util';

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
    }
    updateDriveData(updateDriveData){
        return this.http.put(environment.apiUrl + '/updateDrive', updateDriveData, { headers: this.header });
    }
    deleteDriveData(id:number){
        return this.http.delete<any[]>(environment.apiUrl + '/deleteDrive/'+id, { headers: this.header });
    }
    existsDriveName(driveName: string) {
        return this.http.get(environment.apiUrl + '/existsDriveName/' + driveName, { headers: this.header });
    }
    existsDriveDescription(driveDescription: string) {
        return this.http.get(environment.apiUrl + '/existsDriveDescription/' + driveDescription, { headers: this.header });
    }

    // DRIVE CATEGORY
    getDriveCategoryData() {
        return this.http.get<DriveCategoryModel[]>(environment.apiUrl + '/driveCategory', { headers: this.header });
    }
    findDriveCategoryDataById(id:number){
        return this.http.get<any[]>(environment.apiUrl + '/driveCategoryById/'+id, { headers: this.header });
    }
    saveDriveCategoryData(saveDriveData){
        return this.http.post(environment.apiUrl + '/saveDriveCategory', saveDriveData, { headers: this.header });
    }updateDriveCategoryData(updateDriveData){
        return this.http.put(environment.apiUrl + '/updateDriveCategory', updateDriveData, { headers: this.header });
    }
    deleteDriveCategoryData(id:number){
        return this.http.delete<any[]>(environment.apiUrl + '/deleteDriveCategory/'+id, { headers: this.header });
    }
    existsDriveCategoryName(driveCategoryName: string) {
        return this.http.get(environment.apiUrl + '/existsDriveCategoryName/' + driveCategoryName, { headers: this.header });
    }
    existsDriveCategoryDescription(driveCategoryDescription: string) {
        return this.http.get(environment.apiUrl + '/existsDriveCategoryDescription/' + driveCategoryDescription, { headers: this.header });
    }

     // DRIVE CATEGORY ASS
     getDriveCategoryAssoData() {
        return this.http.get<DriveCategoryAssoModel[]>(environment.apiUrl + '/driveCategoryAsso', { headers: this.header });
    }
    findDriveCategoryAssoDataById(id:number){
        return this.http.get<any[]>(environment.apiUrl + '/driveCategoryAssoById/'+id, { headers: this.header });
    }
    saveDriveCategoryAssoData(saveDriveData){
        return this.http.post(environment.apiUrl + '/saveDriveCategoryAsso', saveDriveData, { headers: this.header });
    }updateDriveCategoryAssoData(updateDriveData){
        return this.http.put(environment.apiUrl + '/updateDriveCategoryAsso', updateDriveData, { headers: this.header });
    }
    deleteDriveCategoryAssoData(id:number){
        return this.http.delete<any[]>(environment.apiUrl + '/deleteDriveCategoryAsso/'+id, { headers: this.header });
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

    // DRIVE TARGETS
    getDriveTargetData(){
        return this.http.get<DriveTargetModel[]>(environment.apiUrl + '/driveTarget', { headers: this.header });            
    }
    saveDriveTargetData(save){
        return this.http.post(environment.apiUrl + '/saveDriveTarget', save, { headers: this.header });
    }
    updateDriveTargetData(update){
        return this.http.put(environment.apiUrl + '/updateDriveTarget', update, { headers: this.header });
    }
    findDriveTargetDataById(id:number){
        return this.http.get<any[]>(environment.apiUrl + '/driveTargetById/'+id, { headers: this.header });
    }
    deleteDriveTargetData(id:number){
        return this.http.delete<any[]>(environment.apiUrl + '/deleteDriveTarget/'+id, { headers: this.header });
    }
    // DRIVE TARGETS

    // DRIVE DAILY PROGRESS 
    getDriveDailyProgressData(){
        return this.http.get<DriveDailyProgressModel[]>(environment.apiUrl + '/driveDailyProgress', { headers: this.header });            
    }
    saveDriveDailyProgressData(save){
        return this.http.post(environment.apiUrl + '/saveDriveDailyProgress', save, { headers: this.header });
    }
    updateDriveDailyProgressData(update){
        return this.http.put(environment.apiUrl + '/updateDriveDailyProgress', update, { headers: this.header });
    }
    findDriveDailyProgressDataById(id:number){
        return this.http.get<any[]>(environment.apiUrl + '/driveDailyProgressById/'+id, { headers: this.header });
    }
    deleteDriveDailyProgressData(id:number){
        return this.http.delete<any[]>(environment.apiUrl + '/deleteDriveDailyProgress/'+id, { headers: this.header });
    }
    // DRIVE DAILY PROGRESS
    // DRIVE FAILURE ANALYSIS
    getFailureAnalysisData(){
        return this.http.get<FailureAnalysisModel[]>(environment.apiUrl + '/failureAnalysis', { headers: this.header });            
    }
    saveFailureAnalysisData(save){
        return this.http.post(environment.apiUrl + '/saveFailureAnalysis', save, { headers: this.header });
    }
    updateFailureAnalysisData(update){
        return this.http.put(environment.apiUrl + '/updateFailureAnalysis', update, { headers: this.header });
    }
    findFailureAnalysisDataById(id:number){
        return this.http.get<any[]>(environment.apiUrl + '/failureAnalysisById/'+id, { headers: this.header });
    }
    deleteFailureAnalysisData(id:number){
        return this.http.delete<any[]>(environment.apiUrl + '/deleteFailureAnalysis/'+id, { headers: this.header });
    }
    // DRIVE FAILURE ANALYSIS

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
       formdata.append('attachment', update.attachment);
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
    getInspectionsType(){
        return this.http.get<any[]>(environment.apiUrl + '/inspectionType', { headers: this.header });
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
        formdata.append('inspectionId', saveDetails.inspectionId);
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
       formdata.append('inspectionId', update.inspectionId);
       formdata.append('dateOfStipulation', update.dateOfStipulation);
       formdata.append('dateComplied', update.dateComplied);
       formdata.append('compliance', update.compliance);
       formdata.append('compliedBy', update.compliedBy);
       formdata.append('assetType', update.assetType);
       formdata.append('createdBy', update.createdBy);
       formdata.append('createdOn', update.createdOn);
       formdata.append('updatedBy', update.updatedBy);
       formdata.append('updatedOn', update.updatedOn);
       formdata.append('attachment',update.attachment);
       let header = new HttpHeaders({           
        'Authorization': `Bearer ${this.accessToken}`
    }); 
        return this.http.post(environment.apiUrl + '/updateStipulations', formdata, { headers: header });
    }
    //
    findStipulationAndInspectionDataById(id:number){
        return this.http.get<any[]>(environment.apiUrl + '/inspectionsContentById/'+id, { headers: this.header });
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

    deleteFile(id:string,rowid:string, type:string){
        var data ={
            "id":id,
            "fileName":rowid,
            "type":type
        }
        return this.http.post<any[]>(environment.apiUrl + '/deleteFile', data, { headers: this.header });
    }
    findStatusItem(statusType){
        return this.http.get<any[]>(environment.apiUrl + '/statusItem/'+statusType, { headers: this.header });
    }

    findDivisions(){
        return this.http.get<any[]>(environment.apiUrl + '/divisions', { headers: this.header });
    }

    downloadDriveFile(type, fileName){
        return this.http.get<any[]>(environment.apiUrl + '/driveFiles/'+type+'/'+fileName, { headers: this.header });
    }

}