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
    displayedColumns = ['sno','assetType','scheduleType','totalPopulaion','targetApr','targetMay','targetJune','targetJuly',
    'targetAug','targetSep','targetOct','targetNov','targetDec','targetJan','targetFeb','targetMar'];
    enableSave: boolean;
    res: any;
    facilityList:any;
    
    
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
                       console.log('targets'+this.assetMonthlyTargetsList[i].targetApr);
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
    
    
    
    
}