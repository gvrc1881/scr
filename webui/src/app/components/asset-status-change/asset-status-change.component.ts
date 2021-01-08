import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormGroup, FormBuilder, Validators ,FormArray,FormControl} from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { AssetStatusChangeModel } from 'src/app/models/asset-status-change.model';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { ActivatedRoute, Router } from '@angular/router';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog, DateAdapter, MAT_DATE_FORMATS } from '@angular/material';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';
import { DatePipe } from '@angular/common';
import { Location } from '@angular/common';
import { AssetStatusDialogComponent } from 'src/app/components/asset-status-change/asset-status-history/asset-status-history.component';
import { BehaviorSubject } from 'rxjs'

@Component({
  selector: 'app-asset-status-change',
  templateUrl: './asset-status-change.component.html',
  styleUrls: ['./asset-status-change.component.scss'],
  providers: [
    {
        provide: DateAdapter, useClass: AppDateAdapter
    }, 
    {
        provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
    }
    ]
 
})

export class AssetStatusChangeComponent implements OnInit {
 

    FiledLabels = FieldLabelsConstant.LABELS;
    editPermission: boolean ;
    addPermission: boolean ;
    deletePermission: boolean = true;
    title: any;
    Titles = FieldLabelsConstant.TITLE;
    searchInputFormGroup: FormGroup;
    standardPhaseActivityList: any;
    save: boolean ;    
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    statusDataSource: MatTableDataSource<AssetStatusChangeModel>;
    dataSource: MatTableDataSource<AssetStatusChangeModel> ;
    statusDisplayedColumns = ['sno','facility','asset', 'asetId','dateStatus','curentStatus','stats','targetDateReady','remark','actions'];
    displayedColumns = ['sno','facilityId','assetType', 'assetId','make','nextAoh','nextPoh','dateOfStatus','actions'];
    enableUpdate: boolean;    
    AssetStatusList:any;
    activity = [];    
    resp: any; 
    maxDate = new Date();
    toMinDate=new Date();   
    dateFormat = 'dd-MM-yyyy';   
    id:number;
    editStatusResponse:any;
   
    loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
    userHierarchy:any = JSON.parse(localStorage.getItem('userHierarchy'));
    zoneHierarchy:any = JSON.parse(localStorage.getItem('zoneData'));
    divisionHierarchy:any = JSON.parse(localStorage.getItem('divisionData'));
    subDivisionHierarchy:any = JSON.parse(localStorage.getItem('subDivData'));
    depotHierarchy:any = JSON.parse(localStorage.getItem('depotData'));
    loggedUser: any = JSON.parse(localStorage.getItem('loggedUser'));
    divisionList: any;
    subDivisionList: any;
    depotList :any;
    towerCarList:any;
    ChangeStatus:any;
    StatusChangeList:any;
    enableTable:boolean;
    enableStausTable:boolean;
    updateStatusChangeFormGroup:FormGroup;
    enableButton:any;
    facility:any;
    userDefaultData:any;
    division:any;
    subDivision:any;
    depots:any;
    enableDivision :boolean;
    enableSubDivision :boolean;
    enableDepot: boolean;
    controls: FormArray;
    statusList:any;
    list: BehaviorSubject<AssetStatusChangeModel> = new BehaviorSubject(this.statusList);

    constructor(
      public dialog: MatDialog,
      private formBuilder: FormBuilder,
      private spinnerService: Ng4LoadingSpinnerService,
      private commonService: CommonService,
      private sendAndRequestService:SendAndRequestService ,
      private route: ActivatedRoute,
      private router: Router,    
      private datePipe: DatePipe,   
      private location: Location,
  ) {
  }
  
  ngOnInit() {
  
    this.getAllData();     
      this.findDivision();
      this.findChangeStatus();    
    this.searchInputFormGroup = this.formBuilder.group({
        'dataDiv': [null]  ,
        'subDiv': [null]  ,
        'facilityId':[null]   
    });
  
    //   const toGroups = this.list.value.map(entity => {
    //     return new FormGroup({
    //      dateOfStatus:  new FormControl(entity.dateOfStatus, Validators.required)
        
    //     },{updateOn: "blur"});
    //   });

    //  this.controls = new FormArray(toGroups);
  
     
  }

  

  findDivision(){

    if(!this.enableDivision){

      this.enableDepot = true;
      this.enableDivision=false;
      this.enableSubDivision = false; 
      this.findFacility();
    }
    this.divisionList=[];    

    for (let i = 0; i < this.divisionHierarchy.length; i++) {
    
        
           if( this.divisionHierarchy[i].depotType == 'DIV'){
           
              this.divisionHierarchy.divisionList;
              this.enableDivision = true;
           }
        }

  }

  findSubDivision(){     

    let  division: string = this.searchInputFormGroup.value.dataDiv;   

    this.subDivisionList=[]; 
   
    for (let i = 0; i < this.subDivisionHierarchy.length; i++) {
        
           if(this.subDivisionHierarchy[i].division == division && this.subDivisionHierarchy[i].depotType == 'SUB_DIV'){
           
               this.subDivisionList.push(this.subDivisionHierarchy[i]);           
               this.enableSubDivision = true;
               
           }
        }

  }

  findDepot(){

    this.depotList=[];  

    let subDivision = this.searchInputFormGroup.value.subDiv;
 
    for (let i = 0; i < this.depotHierarchy.length; i++) {
        
           if(this.depotHierarchy[i].subDivision == subDivision && this.depotHierarchy[i].depotType == 'OHE' ){
           
               this.depotList.push(this.depotHierarchy[i]);
               //this.depotHierarchy.depotList;
               this.enableDepot = true;
               
           }
        }

  }

  findFacility(){
    this.depotList=[];  
    for (let i = 0; i < this.depotHierarchy.length; i++) {
        
      if( this.depotHierarchy[i].depotType == 'OHE' ){
      
          this.depotList.push(this.depotHierarchy[i]);
          //this.depotHierarchy.depotList;
          this.enableDepot = true;
          
      }
   }
  }

  findChangeStatus(){
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_STATUS_ITEM +'ASSET_STATUS_TYPES').subscribe((data) => {
      this.ChangeStatus = data;
    
      },  error => {
         this.commonService.showAlertMessage("Error in Get")
      });
  }


  updateField(index, field) {
    const control = this.getControl(index, field);
    if (control.valid) {
      this.statusList.update(index,field,control.value);
    }

   }

  getControl(index, fieldName) {
    const a  = this.controls.at(index).get(fieldName) as FormControl;
    return this.controls.at(index).get(fieldName) as FormControl;
  }
  update(index, field, value) {
    this.statusList = this.statusList.map((e, i) => {
      if (index === i) {
        return {
          ...e,
          [field]: value
        }
      }
      return e;
    });
    this.list.next(this.statusList);
  }
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.statusDataSource.filter = filterValue;
  }

  getAllData() {
    const status: AssetStatusChangeModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.OPERATIONS.ASSET_STATUS_CHANGE.GET_ALL_TOWERCARS).subscribe((data) => {
      this.towerCarList = data;
      for (let i = 0; i < this.towerCarList.length; i++) {
        this.towerCarList[i].sno = i + 1;
        this.towerCarList[i].dateOfStatus = this.datePipe.transform(this.towerCarList[i].dateOfStatus, 'dd-MM-yyyy ');
        this.towerCarList[i].targetDateOfReady = this.datePipe.transform(this.towerCarList[i].targetDateOfReady, 'dd-MM-yyyy ');
        
       status.push(this.towerCarList[i]);
      }

      this.statusDataSource = new MatTableDataSource(status);
      this.enableStausTable = true;
      this.enableTable=false;
     //this.dataSource.paginator = this.paginator;
     // this.dataSource.sort = this.sort;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }
  
  getAssetStatusChange() { 
    
    let statusList : AssetStatusChangeModel[] = [];
    this.AssetStatusList = [];
    this.activity  = [];   
    let division=this.searchInputFormGroup.value.dataDiv;
 
    let subDivision=this.searchInputFormGroup.value.subDiv;
  
    let facilityId =this.searchInputFormGroup.value.facilityId;
    this.spinnerService.show();
     this.sendAndRequestService.requestForGET(Constants.app_urls.OPERATIONS.ASSET_STATUS_CHANGE.
      GET_TOWERCARS_BASEDON_DIVISION+division+'/'+subDivision+'/'+facilityId).subscribe((data) => {
             this.AssetStatusList = data;
        
            if(data) {
              for (var i = 0; i < this.AssetStatusList.length; i++) {
                this.AssetStatusList[i].sno = i + 1;
                 if(this.AssetStatusList[i].dateOfStatus){
                   this.AssetStatusList[i].dateOfStatus = new Date(this.AssetStatusList[i].dateOfStatus);
                 }
                if(this.AssetStatusList[i].targetDateOfReady){
                  this.AssetStatusList[i].targetDateOfReady = !!this.AssetStatusList[i].targetDateOfReady ? new Date(this.AssetStatusList[i].dateOfStatus):'';
                }
                this.AssetStatusList[i].dateOfManufacture = this.datePipe.transform(this.AssetStatusList[i].dateOfManufacture, 'dd-MM-yyyy');  
                this.AssetStatusList[i].nextAoh = this.datePipe.transform(this.AssetStatusList[i].nextAoh, 'dd-MM-yyyy'); 
                this.AssetStatusList[i].nextPoh = this.datePipe.transform(this.AssetStatusList[i].nextPoh, 'dd-MM-yyyy'); 
                //this.AssetStatusList[i].dateOfStatus = this.datePipe.transform(this.AssetStatusList[i].dateOfStatus,'dd-MM-yyyy');     
                this.activity.push(this.AssetStatusList[i]);
              }
            }
         this.dataSource = new MatTableDataSource(this.activity);  
         this.activity = this.statusList ;  
         this.enableStausTable = false;
         this.enableTable=true;  
        this. title = Constants.EVENTS.ADD;       
         
     });
     
  }

  saveAction(row)
   {
     let towerCar={

     "assetType":row.assetType,
     "assetId":row.assetId,
     "facilityId":row.facilityId,    
     "dateOfStatus":row.dateOfStatus,
     "currentStatus":row.currentStatus,
     "status":row.status,
     "targetDateOfReady":row.targetDateOfReady,
     "remarks":row.remarks,
     "createdBy": this.loggedUserData.username,
     "createdOn": new Date()

     }   
     
    this.sendAndRequestService.requestForPOST(Constants.app_urls.OPERATIONS.ASSET_STATUS_CHANGE.SAVE,  towerCar , false).subscribe(response => {
      this.spinnerService.show();
      this.resp = response;
   
      if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("TW Status Data Added Successfully");
        this.searchInputFormGroup.reset();
        this.dataSource=new MatTableDataSource();
        this.getAllData();
        this.enableUpdate=false;
        this.addPermission=true;
        //this.router.navigate(['../'], { relativeTo: this.route });
      } else {
        this.commonService.showAlertMessage("TW Status Added Failed.");
      }
    }, error => {
      console.log('ERROR >>>');
      this.spinnerService.hide();
      this.commonService.showAlertMessage("TW Status Added Failed.");
    });
   
  }
  /*
  updateStatusChangeSubmit(){
    
  
                  var updatestatus={
                   // id:this.editStatusResponse.id,
                    assetType:this.updateStatusChangeFormGroup.value.assetType,
                    assetId:this.updateStatusChangeFormGroup.value.assetId,
                    facilityId:this.updateStatusChangeFormGroup.value.facilityId,                  
                    dateOfStatus:this.updateStatusChangeFormGroup.value.dateOfStatus,
                    currentStatus:this.updateStatusChangeFormGroup.value.currentStatus,
                    status:this.updateStatusChangeFormGroup.value.status,
                    targetDateOfReady:this.updateStatusChangeFormGroup.value.targetDateOfReady,
                    remarks:this.updateStatusChangeFormGroup.value.remarks ,
                    "lastUpdatedStamp": new Date(),
                   "lastUpdatedTxStamp": new Date()
                  }      
                  console.log("updateresponse=="+updatestatus)  ;                                 
                    this.sendAndRequestService.requestForPUT(Constants.app_urls.OPERATIONS.ASSET_STATUS_CHANGE.UPDATE, updatestatus, false).subscribe(response => {
                      this.spinnerService.hide();
                      this.resp = response;
                      
                      if (this.resp.code == Constants.CODES.SUCCESS) {
                      this.commonService.showAlertMessage("Assets Status Data Updated Successfully");
                     
                      this.dataSource=new MatTableDataSource();
                      this.getAllData();
                      this.enableUpdate=false;
                      this.editPermission = false;
                      this.addPermission=true;
                      }else{
                        this.commonService.showAlertMessage("Assets Status Data Updating Failed.");
                      }
                    }, error => {
                      console.log('ERROR >>>');
                      this.spinnerService.hide();
                      this.commonService.showAlertMessage("Assets Status Data Updating Failed.");
                    })
     
} 


  editStatusChange(id){
    
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

    

} */
  

  addEvent($event) {
    this.toMinDate = new Date($event.value); 
    
  }
 
  onGoBack() {
    this.location.back();
  }

  delete (id) {
    this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
        disableClose: false
      });
    this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete TW status?";
    this.confirmDialogRef.afterClosed().subscribe(result => {
        if(result){
          this.sendAndRequestService.requestForDELETE(Constants.app_urls.OPERATIONS.ASSET_STATUS_CHANGE.DELETE, id).subscribe((data) => {
            this.resp = data;
              if(this.resp.code == 200 && !!this.resp) {
                      this.commonService.showAlertMessage(this.resp.message);
                       this.getAllData();
                     } else {
                       this.commonService.showAlertMessage("TW Status Deletion Failed.");
                     }	
                },error => {
                  console.log('ERROR >>>');
              this.spinnerService.hide();
              this.commonService.showAlertMessage("TW Status Deletion Failed.");
                });
        }
    });
}

// history(){
  
//   this.dialog.open(AssetStatusDialogComponent, {
//     height: '600px',
//     width: '80%', 
//     data:this.editStatusResponse.assetId,
    
//   });

// }

twHistory(row){
  
  this.dialog.open(AssetStatusDialogComponent, {
    height: '600px',
    width: '80%', 
    data:row.assetId,
    
  });

}
  
  
  }
  
  