import { Component, OnInit, Inject, ViewChild, ElementRef } from '@angular/core';
import { AssetStatusChangeModel } from 'src/app/models/asset-status-change.model';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { Constants } from 'src/app/common/constants';
import { DatePipe } from '@angular/common';
import { FormGroup, FormBuilder, Validators ,FormArray,FormControl} from '@angular/forms';
import { CommonService } from 'src/app/common/common.service';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MAT_DIALOG_DATA, DateAdapter, MAT_DATE_FORMATS } from '@angular/material';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';
import { ActivatedRoute, Router } from '@angular/router';
@Component({
    selector: 'asset-status-history',
    templateUrl: './asset-status-history.component.html',
    providers: [
        {
            provide: DateAdapter, useClass: AppDateAdapter
        }, 
        {
            provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
        }
        ]
})
export class AssetStatusDialogComponent implements OnInit {
    public response:any=[];
    type:string;
    historyList:any;
    updateStatusChangeFormGroup:FormGroup;
    resp:any;
    editStatusResponse:any;
    facility:any;
    editPermission:boolean;
    ChangeStatus:any;
    FiledLabels = FieldLabelsConstant.LABELS;
    Titles = FieldLabelsConstant.TITLE;
    assetStatusDisplayedColumns = ['sno', 'assetId','dateOfStatus', 'status', 'remarks','actions'];
    assetStatusDataSource: MatTableDataSource<AssetStatusChangeModel>;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    @ViewChild('filter', { static: true }) filter: ElementRef;
    loggedUserData: any = JSON.parse(localStorage.getItem('userData'));

    constructor( 
        
        private sendAndRequestService:SendAndRequestService,       
        @Inject(MAT_DIALOG_DATA) public data:any,
        private datePipe: DatePipe, 
        private spinnerService: Ng4LoadingSpinnerService,
        public dialogRef: MatDialogRef<AssetStatusChangeModel>,
        private formBuilder: FormBuilder, 
        private commonService: CommonService,        
        private route: ActivatedRoute,
        private router: Router, 
    ){
        
    }

    ngOnInit() {      
       
        this.findChangeStatus();
       this.getAllData();

           
    }
   
    getAllData(){
        const assetStatusData: AssetStatusChangeModel[] = [];

        this.sendAndRequestService.requestForGET(Constants.app_urls.OPERATIONS.ASSET_STATUS_CHANGE.
            GET_BY_ASSETID+this.data).subscribe((data) => {
            this.historyList = data; 
           console.log("historylist=="+JSON.stringify(this.historyList));
            for (let i = 0; i < this.historyList.length; i++) {               
                this.historyList[i].sno = i + 1;               
                    this.historyList[i].dateOfStatus = this.datePipe.transform(this.historyList[i].dateOfStatus, 'dd-MM-yyyy');  
                  
                assetStatusData.push(this.historyList[i]);                
      
            }
            this.assetStatusDataSource = new MatTableDataSource(assetStatusData);
            this.assetStatusDataSource.paginator = this.paginator;
            this.assetStatusDataSource.sort = this.sort;  

           
            },  error => {
               
            });
    }
    updateStatusChangeSubmit(){
    
  
        var updatestatus={
          id:this.editStatusResponse.id,
          assetType:this.updateStatusChangeFormGroup.value.assetType,
          assetId:this.updateStatusChangeFormGroup.value.assetId,
          facilityId:this.updateStatusChangeFormGroup.value.facilityId,                  
          dateOfStatus:this.updateStatusChangeFormGroup.value.dateOfStatus,
          currentStatus:this.updateStatusChangeFormGroup.value.currentStatus,
          status:this.updateStatusChangeFormGroup.value.status,
          targetDateOfReady:this.updateStatusChangeFormGroup.value.targetDateOfReady,
          remarks:this.updateStatusChangeFormGroup.value.remarks ,
          "createdBy": this.loggedUserData.username,
          "lastUpdatedStamp": new Date(),
         "lastUpdatedTxStamp": new Date()
        }      
        console.log("updateresponse=="+updatestatus)  ;                                 
          this.sendAndRequestService.requestForPUT(Constants.app_urls.OPERATIONS.ASSET_STATUS_CHANGE.UPDATE, updatestatus, false).subscribe(response => {
            this.spinnerService.hide();
            this.resp = response;
            
            if (this.resp.code == Constants.CODES.SUCCESS) {
            this.commonService.showAlertMessage("Assets Status Data Updated Successfully");           
            this.assetStatusDataSource=new MatTableDataSource(); 
            this.updateStatusChangeFormGroup.reset();
            this.editPermission = false;  
            this.getAllData();         
            }else{
              this.commonService.showAlertMessage("Assets Status Data Updating Failed.");
            }
          }, error => {
            console.log('ERROR >>>');
            this.spinnerService.hide();
            this.commonService.showAlertMessage("Assets Status Data Updating Failed.");
          })

} 


processEditAction(id){

this.editPermission=true;

this.updateStatusChangeFormGroup = this.formBuilder.group({
'id':[null],
'assetType':[null],
'assetId':[null],
'facilityId':[null],
'dateOfStatus':[null],
'currentStatus':[null],
'targetDateOfReady':[null],
'status':[null],
'remarks':[null]
})

this.sendAndRequestService.requestForGET(Constants.app_urls.OPERATIONS.ASSET_STATUS_CHANGE.GET_TOWERCAR_ID+id).subscribe((responseData) => {
this.editStatusResponse = responseData;

console.log("response=="+this.editStatusResponse.facilityId);

this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY+this.editStatusResponse.facilityId).subscribe((response) => {
this.facility = response;
console.log("response fac=="+JSON.stringify(this.facility));
this.updateStatusChangeFormGroup.patchValue({ facilityId: this.facility.facilityName })       	
});
this.updateStatusChangeFormGroup.patchValue ({ 
id: this.editStatusResponse.id,
assetType:this.editStatusResponse.assetType,
assetId:this.editStatusResponse.assetId,
// facilityId:this.editStatusResponse.facilityId,
dateOfStatus:!!this.editStatusResponse.dateOfStatus ? new Date(this.editStatusResponse.dateOfStatus) : '',         
currentStatus:this.editStatusResponse.currentStatus,         
targetDateOfReady:!!this.editStatusResponse.targetDateOfReady ? new Date(this.editStatusResponse.targetDateOfReady) : '',
status:this.editStatusResponse.status,
remarks:this.editStatusResponse.remarks

});



} ,error => {})



} 
findChangeStatus(){
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_STATUS_ITEM +'ASSET_STATUS_TYPES').subscribe((data) => {
      this.ChangeStatus = data;
    
      },  error => {
         this.commonService.showAlertMessage("Error in Get")
      });
  }
  onGoBack() {
    this.getAllData();
   this.editPermission=false;
  }  
   
}
