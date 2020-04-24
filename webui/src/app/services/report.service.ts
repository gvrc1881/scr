import { Injectable, ɵConsole } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { ReportParameterModel } from '../models/reportParameter.model';
import {FacilityModel} from '../models/facility.model';
import { ReportModel } from '../models/report.model';
import {FailuresTableModel} from '../models/failures-table.model';

@Injectable()
export class ReportService {
    url: string = environment.apiUrl;
    header: any;
    accessToken: any = JSON.parse(localStorage.getItem('accessToken'));
    constructor(private http: HttpClient) {
        this.header = new HttpHeaders({
            'Content-Type': 'application/json;charset=UTF-8',
            'accept': 'application/json',
            'Authorization': `Bearer ${this.accessToken}`
        });
    }

    makeReport(model) {
        return this.http.post(this.url + '/makeReport' ,model,  { headers: this.header });
    }
   

reportParameterNames() {
    
    return this.http.get<ReportParameterModel>(environment.apiUrl + '/reportParameterNames' , { headers: this.header });
}

reportNames(reportType:string) {    
    return this.http.get(this.url + '/reportNames/'+reportType,{ headers: this.header });
}
facilityNames(){
    console.log('facilityNames::')
    return this.http.get<FacilityModel>(environment.apiUrl + '/facilityNames' , { headers: this.header });
}

powerBlocks(){
    console.log('powerBlocks::')
    return this.http.get<FailuresTableModel>(environment.apiUrl + '/powerBlocks' , { headers: this.header });
}
zoneList(){
    console.log("zoneCodes")
    
    return this.http.get(this.url + '/zoneList' , { headers: this.header });
}



pbSwitchControl(){
    return this.http.get(this.url + '/pbSwitchControl',{ headers: this.header });
}
elementarySections(){
    return this.http.get(this.url + '/elementarySectionsByFacilityId',{ headers: this.header });
}
observationCategories(){
    return this.http.get(this.url+'/observationCategories',{headers:this.header});
}
observationCheckList(){
    return this.http.get(this.url+'/observationCheckList',{headers:this.header});
}
generateReport(reportModel: ReportModel) {
    console.log("model2-1-2020"+JSON.stringify(reportModel.reportId));
    return this.http.post(this.url + '/generateReport' ,reportModel,  { headers: this.header });
}
allAssetTypeReports(assetType){
    return this.http.get(this.url + '/allAssetTypeReports/'+assetType,{ headers: this.header });
}

getScheduleCodesBasedOnAssetType(productCategoryMemObj) {
    return this.http.post(this.url+'/getscheduleCodesBasedonAssetType/',productCategoryMemObj, { headers: this.header });
}
getAssetIdBasedOnScheduleCodesAndAssetTypes(assetsScheHistObj) {
    console.log("assetsScheHistObj"+assetsScheHistObj);

    return this.http.post(this.url+'/getAssetIdBasedOnScheduleCodesAndAssetTypes/',assetsScheHistObj, { headers: this.header });
}
getDivisionBasedOnZone(code) {
    return this.http.post(this.url+'/getDivisionBasedOnZone/',code, { headers: this.header });
}
getSubDivisionBasedOnDivision(code) {
    return this.http.post(this.url+'/getSubDivisionBasedOnDivision/',code, { headers: this.header });
}
getFacilityBasedOnSubDivision(code) {
    console.log("facilityCode"+code);

    return this.http.post(this.url+'/getFacilityBasedOnSubDivision/',code, { headers: this.header });
}
functionalLocationTypes(){
    return this.http.get(this.url + '/functionalLocationsTypes',{ headers: this.header });
}
stipulationDetails(){
    return this.http.get(this.url + '/stipulationDetails',{ headers: this.header });
}
uomDetails(){
    return this.http.get(this.url + '/uomDetails',{ headers: this.header });
}
sectionDetails(){
    return this.http.get(this.url + '/sectionDetails',{ headers: this.header });
}
statusItemDetails(statusId){
    return this.http.get(this.url + '/statusItemDetails/'+statusId,{ headers: this.header });
}

getFacilitysBasedOnDepotType(depotType){
    return this.http.get<FacilityModel>(this.url + '/getFacilitysBasedOnDepotType/'+depotType,{ headers: this.header });
}

getAllContentCategories(){
	return this.http.get(this.url + '/existsContentCategory',{ headers: this.header });
}

getAllContentTopics(){
	return this.http.get(this.url + '/existsContentTopic',{ headers: this.header });
}

getAllTssFeederMaster(){
	return this.http.get(this.url + '/findAllTssFeederMaster',{ headers: this.header });
}
crsEigInspectionDetails(){
    return this.http.get(this.url + '/crsEigInspectionDetails',{ headers: this.header });
}
failureAnalysisDetails(){
    return this.http.get(this.url + '/failureAnalysisDetails',{ headers: this.header });
}
electrificationTargets(){
    return this.http.get(this.url + '/electrificationTargets',{ headers: this.header });
}
precautionaryMeasureDetails(){
    return this.http.get(this.url + '/precautionaryMeasureDetails',{ headers: this.header });
}
tpcBoardDetails(){
    return this.http.get(this.url + '/tpcBoardDetails',{ headers: this.header });
}
productMakeModelAssoc(){
    return this.http.get(this.url + '/productMakeModelAssoc',{ headers: this.header });
}
majorSectionDetails(){
    return this.http.get(this.url + '/majorSectionDetails',{ headers: this.header });
}
makeDetails(){
    return this.http.get(this.url + '/makeDetails',{ headers: this.header });
}
modelDetails(){
    return this.http.get(this.url + '/majorSectionDetails',{ headers: this.header });
}
assetTypeBasedonAssetIdAndFacilityId(assetType){
    return this.http.get(this.url + '/assetTypeBasedonAssetIdAndFacilityId/'+assetType,{ headers: this.header });
}
assetIdBasedonAssetTypeAndFacilityId(assetType){
    return this.http.get(this.url + '/assetIdBasedonAssetTypeAndFacilityId/'+assetType,{ headers: this.header });
}
assetMasterData(){
    return this.http.get(this.url + '/assetMasterData',{ headers: this.header });
}
}
    
    
