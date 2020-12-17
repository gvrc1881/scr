import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
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
import { FuseConfirmPopupComponent } from 'src/app/components/confirm-popup/confirm-popup.component';
import { FacilityModel } from 'src/app/models/facility.model';
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
    title: string = Constants.EVENTS.ADD;
    searchInputFormGroup: FormGroup;
    standardPhaseActivityList: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    statusDataSource: MatTableDataSource<AssetStatusChangeModel>;
    dataSource: MatTableDataSource<AssetStatusChangeModel>;
   // statusDisplayedColumns = ['sno','facilityId','assetType', 'assetId','make','model','dateOfManufacture','dueDateForAoh','dueDateForPoh','dateOfStatus','currentStatus','status','targetDateOfready','remarks','actions'];
    displayedColumns = ['sno','facilityId','assetType', 'assetId','make','model','dateOfManufacture','dueDateForAoh','dueDateForPoh','dateOfStatus','currentStatus','status','targetDateOfready','remarks','actions'];
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
    divisionList: FacilityModel [] = [];
    subDivisionList: FacilityModel [] = [];
    facilityList :FacilityModel [] = [];
    towerCarList:any;
    ChangeStatus:any;
    StatusChangeList:any;
    enableSave:boolean;
    updateStatusChangeFormGroup:FormGroup;

    constructor(
      public dialog: MatDialog,
      private formBuilder: FormBuilder,
      private spinnerService: Ng4LoadingSpinnerService,
      private commonService: CommonService,
      private sendAndRequestService:SendAndRequestService ,
      private route: ActivatedRoute,
      private router: Router,       
  ) {
  }
  
  ngOnInit() {
    console.log("divisionHierarchy=="+JSON.stringify(this.divisionHierarchy));
    this.getAllData();     
      this.findDivision();
      this.findChangeStatus();    
    this.searchInputFormGroup = this.formBuilder.group({
        'dataDiv': [null]  ,
        'subDiv': [null]  ,
        'facilityId':[null]   
    });
  
     
  }

  findDivision(){
   
    for (let i = 0; i < this.userHierarchy.length; i++) {
             if(this.userHierarchy[i].depotType == 'DIV'){
               this.divisionList.push(this.userHierarchy[i]);
              
             }
          }
  }
  
  findSubDivision(){
    let  division: string = this.searchInputFormGroup.value.dataDiv;
    for (let i = 0; i < this.userHierarchy.length; i++) {
             if(this.userHierarchy[i].division == division && this.userHierarchy[i].depotType == 'SUB_DIV'){
               this.subDivisionList.push(this.userHierarchy[i]);
               
             }
          }
  }
  
  findDepot(){
    let subDivision = this.searchInputFormGroup.value.subDiv;
    for (let i = 0; i < this.userHierarchy.length; i++) {
             if(this.userHierarchy[i].subDivision == subDivision && this.userHierarchy[i].depotType == 'OHE'  ){
               this.facilityList.push(this.userHierarchy[i]);
              
             }
          }
  }

  // findDivision(){
  //   this.divisionList=[];    

  //   for (let i = 0; i < this.divisionHierarchy.length; i++) {
    
        
  //          if( this.divisionHierarchy[i].depotType == 'DIV'){
           
  //             // this.divisionList.push(this.divisionHierarchy[i]);
  //             this.divisionHierarchy.divisionList;
              
  //          }
  //       }

  // }

  // findSubDivision(){     

  //   let  division: string = this.searchInputFormGroup.value.dataDiv;
  //   console.log("subdivison=="+this.searchInputFormGroup.value.dataDiv);

  //   this.subDivisionList=[]; 
   
  //   for (let i = 0; i < this.subDivisionHierarchy.length; i++) {
        
  //          if(this.subDivisionHierarchy[i].division == division && this.subDivisionHierarchy[i].depotType == 'SUB_DIV'){
           
  //              this.subDivisionList.push(this.subDivisionHierarchy[i]);
  //            console.log("subdivision=="+JSON.stringify(this.subDivisionList));
  //           //this.subDivisionHierarchy.subDivisionList;
              
               
  //          }
  //       }

  // }

  // findDepot(){
  //   this.depotList=[];  

  //   let subDivision = this.searchInputFormGroup.value.subDiv;

  //   for (let i = 0; i < this.depotHierarchy.length; i++) {
        
  //          if(this.depotHierarchy[i].subDivision == subDivision && this.depotHierarchy[i].depotType == 'OHE' ){
           
  //              this.depotList.push(this.depotHierarchy[i]);
  //              //this.depotHierarchy.depotList;
               
  //          }
  //       }

  // }

  findChangeStatus(){
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_STATUS_ITEM +'ASSET_STATUS_TYPES').subscribe((data) => {
      this.ChangeStatus = data;
    
      },  error => {
         this.commonService.showAlertMessage("Error in Get")
      });
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
       status.push(this.towerCarList[i]);
      }

      this.statusDataSource = new MatTableDataSource(status);
     //this.dataSource.paginator = this.paginator;
     // this.dataSource.sort = this.sort;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }
  
  getAssetStatusChange() { 
  
    //const phase: ProjectPhaseModel[] = [];
    this.AssetStatusList = [];
    this.activity = []; 
    let div=this.searchInputFormGroup.value.dataDiv;
    let division= div.slice(0, -4);   
    let subDivision=this.searchInputFormGroup.value.subDiv;
    let depot =this.searchInputFormGroup.value.depot;

     this.sendAndRequestService.requestForGET(Constants.app_urls.OPERATIONS.ASSET_STATUS_CHANGE.
      GET_TOWERCARS_BASEDON_DIVISION+division+'/'+subDivision+'/'+depot).subscribe((data) => {
             this.AssetStatusList = data;
             let assetType=this.AssetStatusList.assetType;
             let assetId=this.AssetStatusList.assetId;
             let facilityId=this.AssetStatusList.facilityId;

             this.sendAndRequestService.requestForGET(Constants.app_urls.OPERATIONS.ASSET_STATUS_CHANGE.EXIST_BY_ASSETYPE_ASSETID_FACILITY +assetType+"/"+assetId+"/"+facilityId).subscribe((data) => {
              this.StatusChangeList = data;
              },  error => {
                 this.commonService.showAlertMessage("Error in Get")
              });
             this. editPermission =true;
             this.addPermission=false;
              for (var i = 0; i < this.AssetStatusList.length; i++) {
             this.AssetStatusList[i].sno = i + 1;
            
             this.activity.push(this.AssetStatusList[i]);
         }

         this.dataSource = new MatTableDataSource(this.activity);         
         this.addPermission=true;
         this.editPermission=false;
     });
     
  }

  saveAction(row)
   {
     let towerCar={

     assetType:row.assetType,
     assetId:row.assetId,
     facilityId:row.facilityId,
     make:row.make,
     model:row.model,
     dateOfStatus:row.dateOfStatus,
     currentStatus:row.currentStatus,
     ChangeStatus:row.status,
     targetDateOfready:row.targetDateOfready,
     remarks:row.remarks

     }   
     
    this.sendAndRequestService.requestForPOST(Constants.app_urls.OPERATIONS.ASSET_STATUS_CHANGE.SAVE,  towerCar , false).subscribe(response => {
      this.spinnerService.show();
      this.resp = response;
   
      if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("TW Status Data Updated Successfully");
        this.searchInputFormGroup.reset();
        this.statusDataSource=new MatTableDataSource();
        this.enableUpdate=false;
        this.addPermission=true;
        //this.router.navigate(['../'], { relativeTo: this.route });
      } else {
        this.commonService.showAlertMessage("TW Status Updating Failed.");
      }
    }, error => {
      console.log('ERROR >>>');
      this.spinnerService.hide();
      this.commonService.showAlertMessage("TW Status Updating Failed.");
    });
   
  }
  updateStatusChangeSubmit(){

   
    if (this.title ==  Constants.EVENTS.UPDATE) {
                  var updatestatus={
                    assetType:this.updateStatusChangeFormGroup.value.assetType,
                    assetId:this.updateStatusChangeFormGroup.value.assetId,
                    facilityId:this.updateStatusChangeFormGroup.value.facilityId,                  
                    dateOfStatus:this.updateStatusChangeFormGroup.value.dateOfStatus,
                    currentStatus:this.updateStatusChangeFormGroup.value.currentStatus,
                    ChangeStatus:this.updateStatusChangeFormGroup.value.status,
                    targetDateOfready:this.updateStatusChangeFormGroup.value.targetDateOfready,
                    remarks:this.updateStatusChangeFormGroup.value.remarks 
                  }                                         
                    this.sendAndRequestService.requestForPUT(Constants.app_urls.OPERATIONS.ASSET_STATUS_CHANGE.UPDATE, updatestatus, false).subscribe(response => {
                      this.spinnerService.hide();
                      this.resp = response;
                      if (this.resp.code == Constants.CODES.SUCCESS) {
                      this.commonService.showAlertMessage("Assets Status Data Updated Successfully");
                      this.router.navigate(['../'], { relativeTo: this.route });
                      }else{
                        this.commonService.showAlertMessage("Assets Status Data Updating Failed.");
                      }
                    }, error => {
                      console.log('ERROR >>>');
                      this.spinnerService.hide();
                      this.commonService.showAlertMessage("Assets Status Data Updating Failed.");
                    })
     }
}


  editStatusChange(id){

    this.sendAndRequestService.requestForGET(Constants.app_urls.OPERATIONS.ASSET_STATUS_CHANGE.GET_TOWERCAR_ID+id).subscribe((responseData) => {
      this.editStatusResponse = responseData;
       
        this.updateStatusChangeFormGroup.patchValue
        ({
                          id: this.editStatusResponse.id,
                          dataDiv:this.editStatusResponse.dataDiv,
                          assetType: this.editStatusResponse.assetType,
                          assetId: this.editStatusResponse.assetId,
                          facilityId:this.editStatusResponse.facilityId,
                          make:this.editStatusResponse.make,
                          model:this.editStatusResponse.model,
                          dateOfStatus:this.editStatusResponse.dateOfStatus,
                          currentStatus:this.editStatusResponse.currentStatus,
                          date:this.editStatusResponse.targetDateOfready,
                          remarks:this.editStatusResponse.remarks
                        
           })
          
      
  } ,error => {})
  this.id=id;
  if (!isNaN(this.id)) {
      this.title = Constants.EVENTS.UPDATE;
    } else {
      this.title = Constants.EVENTS.ADD;      
    }
    

    
  }

  addEvent($event) {
    this.toMinDate = new Date($event.value); 
    
  }
 
  onGoBack()
  {
    
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

  
  
  }
  
  