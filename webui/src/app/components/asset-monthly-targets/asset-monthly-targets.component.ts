import { OnInit, Component, ViewChild,ElementRef } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatTableDataSource, MatPaginator, MatSort, MatDialog, MatDialogRef } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { environment } from './../../../environments/environment';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { AssetMonthltTargetsModel } from 'src/app/models/asset-monthly-targets.model';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';

@Component({
    selector: 'asset-monthly-targets',
    templateUrl: './asset-monthly-targets.component.html',
    styleUrls: ['./asset-monthly-targets.component.css']
})
export class AssetMonthlyTargetsComponent implements OnInit {
        
    FiledLabels = FieldLabelsConstant.LABELS;
    addPermission: boolean = true;
    facilityData: any;
    workGroupList: any;
    workPhaseList: any;
    searchInputFormGroup: FormGroup;
    originalDepotsData: any = JSON.parse(sessionStorage.getItem('depotData'));    
    assetMonthlyTargetsList =  [];
    dataSource: MatTableDataSource<AssetMonthltTargetsModel>;
    displayedColumns = ['sno','assetType','scheduleType','population','id','totalPopulation','targetApr','targetMay','targetJune','targetJuly',
    'targetAug','targetSep','targetOct','targetNov','targetDec','targetJan','targetFeb','targetMar'];
    enableSave: boolean;
    res: any;
    facilityList:any;    
    totalPopulation:any;
    enableTargetApr: boolean;
    enableTargetMay: boolean;
    enableTargetJune: boolean;
    enableTargetJuly: boolean;
    enableTargetAug: boolean;
    enableTargetSep: boolean;
    enableTargetOct: boolean;
    enableTargetNov: boolean;
    enableTargetDec: boolean;
    enableTargetJan: boolean;
    enableTargetFeb: boolean;
    enableTargetMar: boolean;
    
    
    constructor(
        public dialog: MatDialog,
        private formBuilder: FormBuilder,
        private spinnerService: Ng4LoadingSpinnerService,
        private commonService: CommonService,
        private sendAndRequestService:SendAndRequestService        
    ) {
    }
        
    ngOnInit() {
        var permissionName = this.commonService.getPermissionNameByLoggedData("ASSET REGISTER","ASSET MONTHLY TARGETS") ;
  	    this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
        this.depotTypeForOhe();
       this.searchInputFormGroup = this.formBuilder.group({
            'facilityId': [null , Validators.required ],
            'year' : [null , Validators.required ]
        });
             
    }
    
    saveAction() {

        console.log("saving records===="+JSON.stringify(this.assetMonthlyTargetsList))
       this.sendAndRequestService.requestForPOST(Constants.app_urls.OPERATIONS.ASSET_MONTHLY_TARGETS.UPDATE_ASSET_MONTHLY_TARGETS,this.assetMonthlyTargetsList,false).subscribe((response) => {
            this.spinnerService.hide();
           this.res = response;
           if( this.res.code == 200 ){
                this.commonService.showAlertMessage("Targets Updated Successfully");
                this.searchInputFormGroup.reset();
                this.dataSource=new MatTableDataSource();
                this.enableSave=false;
                //this.addPermission=true;
           } else {
               this.commonService.showAlertMessage("Targets Updation failed");
           }    
       })     
    }
    applyFilter(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase();
         this.dataSource.filter = filterValue;
      
        
      }
    getAssetMonthlyTargets(){
        this.assetMonthlyTargetsList = [];

        this.enableSave = false;
        this.dataSource = new MatTableDataSource(this.assetMonthlyTargetsList);
       // this.spinnerService.show();
        this.sendAndRequestService.requestForGET(Constants.app_urls.OPERATIONS.ASSET_MONTHLY_TARGETS.GET_ASSET_MONTHLY_TARGETS_BASED_ON_FACILITYID_YEAR
            +this.searchInputFormGroup.value.facilityId +'/' + this.searchInputFormGroup.value.year
            ).subscribe((response) => {
                this.assetMonthlyTargetsList = response;               
                console.log('** rseponse **'+JSON.stringify(this.assetMonthlyTargetsList));
                
                if(this.assetMonthlyTargetsList.length > 0) {
                    for(let i =0 ; i < this.assetMonthlyTargetsList.length ; i++ ){
                        this.assetMonthlyTargetsList[i].sno = i+1;
                        
                        switch(this.assetMonthlyTargetsList[i].seqId){
                            
                            case "1" :  this.assetMonthlyTargetsList[i].enableTargetApr = true;
                            
                                        break;
                            case "2" : this.assetMonthlyTargetsList[i].enableTargetApr = true;
                            this.assetMonthlyTargetsList[i].enableTargetMay = true;
                                     
                                        break;
                            case "3" : this.assetMonthlyTargetsList[i].enableTargetApr = true;
                            this.assetMonthlyTargetsList[i].enableTargetMay = true;
                            this.assetMonthlyTargetsList[i].enableTargetJune = true;
                                           break;
                            case "4" : this.assetMonthlyTargetsList[i].enableTargetApr = true;
                            this.assetMonthlyTargetsList[i].enableTargetMay = true;
                            this.assetMonthlyTargetsList[i].enableTargetJune = true;
                            this.assetMonthlyTargetsList[i].enableTargetJuly = true;
                                              break;
                            case "5" : this.assetMonthlyTargetsList[i].enableTargetApr = true;
                                           this.assetMonthlyTargetsList[i].enableTargetMay = true;
                                           this.assetMonthlyTargetsList[i].enableTargetJune = true;
                                           this.assetMonthlyTargetsList[i].enableTargetJuly = true;
                                           this.assetMonthlyTargetsList[i].enableTargetAug = true;
                                              break;
                            case "6" : this.assetMonthlyTargetsList[i].enableTargetApr = true;
                                              this.assetMonthlyTargetsList[i].enableTargetMay = true;
                                              this.assetMonthlyTargetsList[i].enableTargetJune = true;
                                              this.assetMonthlyTargetsList[i].enableTargetJuly = true;
                                              this.assetMonthlyTargetsList[i].enableTargetAug = true;
                                              this.assetMonthlyTargetsList[i].enableTargetSep = true;
                                                 break;
                            case "7" : this.assetMonthlyTargetsList[i].enableTargetApr = true;
                                                 this.assetMonthlyTargetsList[i].enableTargetMay = true;
                                                 this.assetMonthlyTargetsList[i].enableTargetJune = true;
                                                 this.assetMonthlyTargetsList[i].enableTargetJuly = true;
                                                 this.assetMonthlyTargetsList[i].enableTargetAug = true;
                                                 this.assetMonthlyTargetsList[i].enableTargetSep = true;
                                                 this.assetMonthlyTargetsList[i].enableTargetOct = true;
                                                    break;
                            case "8" : this.assetMonthlyTargetsList[i].enableTargetApr = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetMay = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetJune = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetJuly = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetAug = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetSep = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetOct = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetNov = true;
                                                       break;
                            case "9" : this.assetMonthlyTargetsList[i].enableTargetApr = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetMay = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetJune = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetJuly = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetAug = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetSep = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetOct = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetNov = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetDec = true;
                                                       break;
                            case "10" : this.assetMonthlyTargetsList[i].enableTargetApr = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetMay = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetJune = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetJuly = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetAug = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetSep = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetOct = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetNov = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetDec = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetJan = true;
                                                    
                                                       break;
                            case "11" : this.assetMonthlyTargetsList[i].enableTargetApr = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetMay = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetJune = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetJuly = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetAug = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetSep = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetOct = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetNov = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetDec = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetJan = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetFeb = true;
                                                       break;
                            case "12" : this.assetMonthlyTargetsList[i].enableTargetApr = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetMay = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetJune = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetJuly = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetAug = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetSep = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetOct = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetNov = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetDec = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetJan = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetFeb = true;
                                                    this.assetMonthlyTargetsList[i].enableTargetMar = true;
                                                    
                                                       break;
                            

                        }
                        
                        
                            
                        
                    }
                    this.enableSave = true;
                }
                 this.dataSource = new MatTableDataSource(this.assetMonthlyTargetsList);
        });
    }
    
    depotTypeForOhe()
  {  
//          this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_DEPOTTYPE_FOR_OHE).subscribe((data) => {
//            this.facilityData = data;
//   }
//          );
    this.facilityList=[];
         for (let i = 0; i < this.originalDepotsData.length; i++) {
        
            if( this.originalDepotsData[i].depotType == 'TSS' || this.originalDepotsData[i].depotType == 'OHE' || this.originalDepotsData[i].depotType == 'PSI'|| this.originalDepotsData[i].depotType == 'SP' || this.originalDepotsData[i].depotType == 'SSP'){
               
               this.facilityList.push(this.originalDepotsData[i]);
              
               // this.facilityHierarchy.facilityList;
                
            }
         } 
    

 }
    
 copy(row){

    let population = row.population;
    if(row.enableTargetApr == true)
    {
        row.targetApr = population
    }
    else{ 
        row.targetApr = 0 }
    if(row.enableTargetMay == true)
    {
        row.targetMay = population}
    else
    { row.targetMay = 0 }
    if(row.enableTargetJune == true)
    {
        row.targetJune = population}
    else
    {
        row.targetJune = 0}
    if(row.enableTargetJuly == true)
    {
        row.targetJuly = population
    }else{
        row.targetJuly = 0
    }
    if(row.enableTargetAug == true)
    {
        row.targetAug = population
    }else{
        row.targetAug = 0
    }
    if(row.enableTargetSep == true)
    {row.targetSep = population}
    else{
        row.targetSep = 0
    }
    if(row.enableTargetOct == true)
    {
        row.targetOct = population}
    else
    {row.targetOct = 0}
    if(row.enableTargetNov == true)
    {
        row.targetNov = population
    }else{
        row.targetNov = 0
    }
    if(row.enableTargetDec == true){
        row.targetDec = population
    }
    else{
        row.targetDec = 0
    }
    if(row.enableTargetJan == true){
        row.targetJan = population
    }else{
        row.targetJan = 0
    }
    if(row.enableTargetFeb == true){
        row.targetFeb = population
    }
    else{
        row.targetFeb = 0
    }
    if(row.enableTargetMar == true){
        row.targetMar = population
    }
    else {
        row.targetMar = 0
    }


    this.function(row);
 }  
    
 function(row){
    console.log("function==")
    var z = parseInt(row.targetApr)+parseInt(row.targetMay)+parseInt(row.targetJune)+parseInt(row.targetJuly)+parseInt(row.targetAug)+parseInt(row.targetSep)+parseInt(row.targetOct)+parseInt(row.targetNov)+parseInt(row.targetDec)+parseInt(row.targetJan)+parseInt(row.targetFeb)+parseInt(row.targetMar);  
    console.log("z result=="+z);
    row.totalPopulation=z;
    console.log("result=="+this.totalPopulation)
   
    }
    
}