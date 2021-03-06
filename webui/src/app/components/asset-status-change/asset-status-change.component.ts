import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray, FormControl } from '@angular/forms';
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
  editPermission: boolean;
  addPermission: boolean;
  deletePermission: boolean = true;
  title: any;
  Titles = FieldLabelsConstant.TITLE;
  searchInputFormGroup: FormGroup;
  standardPhaseActivityList: any;
  save: boolean;
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  statusDataSource: MatTableDataSource<AssetStatusChangeModel>;
  dataSource: MatTableDataSource<AssetStatusChangeModel>;
  statusDisplayedColumns = ['sno', 'facility', 'asset', 'asetId', 'dateStatus', 'currentStatus', 'stats', 'dateOfChange', 'remark', 'actions'];
  //displayedColumns = ['sno','facilityId','assetType', 'assetId','make','nextAoh','nextPoh','dateOfStatus','actions'];
  displayedColumns = ['sno', 'facility', 'asset', 'asetId', 'make', 'nextAoh', 'nextPoh', 'dateStatus','currentStatus', 'stats','remark','dateOfChange', 'actions'];
  enableUpdate: boolean;
  AssetStatusList: any;
  activity = [];
  resp: any;
  maxDate = new Date();
  toMinDate = new Date();
  dateFormat = 'dd-MM-yyyy';
  id: number;
  editStatusResponse: any;
  dateOfStatus:any;
  

  loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
  userHierarchy: any = JSON.parse(sessionStorage.getItem('userHierarchy'));
  zoneHierarchy: any = JSON.parse(sessionStorage.getItem('zoneData'));
  divisionHierarchy: any = JSON.parse(sessionStorage.getItem('divisionData'));
  subDivisionHierarchy: any = JSON.parse(sessionStorage.getItem('subDivData'));
  depotHierarchy: any = JSON.parse(sessionStorage.getItem('depotData'));
  loggedUser: any = JSON.parse(sessionStorage.getItem('loggedUser'));
  divisionList: any;
  subDivisionList: any;
  depotList: any;
  towerCarList: any;
  ChangeStatus: any;
  StatusChangeList: any;
  enableTable: boolean;
  enableStausTable: boolean;
  updateStatusChangeFormGroup: FormGroup;
  enableButton: any;
  facility: any;
  userDefaultData: any;
  division: any;
  subDivision: any;
  depots: any;
  enableDivision: boolean;
  enableSubDivision: boolean;
  enableDepot: boolean;
  controls: FormArray;
  statusList: any;
  list: BehaviorSubject<AssetStatusChangeModel>;


  remarksEdit: boolean = false;
  editAssetId:number=0;
  dateStatusEdit:boolean = false;
  statsEdit:boolean = false;
  dateOfChangeEdit:boolean = false;
  curentStatusEdit:boolean = false;
  status:any;
  constructor(
    public dialog: MatDialog,
    private formBuilder: FormBuilder,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private sendAndRequestService: SendAndRequestService,
    private route: ActivatedRoute,
    private router: Router,
    private datePipe: DatePipe,
    private location: Location,
  ) {
    this.list = new BehaviorSubject(this.statusList)
  }

  ngOnInit() {

    this.getAllData();
    this.findDivision();
    this.findChangeStatus();
    this.searchInputFormGroup = this.formBuilder.group({
      'dataDiv': [null],
      'subDiv': [null],
      'facilityId': [null]
    });


  }



  findDivision() {

    if (!this.enableDivision) {

      this.enableDepot = true;
      this.enableDivision = false;
      this.enableSubDivision = false;
      this.findFacility();
    }
    this.divisionList = [];

    for (let i = 0; i < this.divisionHierarchy.length; i++) {


      if (this.divisionHierarchy[i].depotType == 'DIV') {

        this.divisionHierarchy.divisionList;
        this.enableDivision = true;
      }
    }

  }

  findSubDivision() {

    let division: string = this.searchInputFormGroup.value.dataDiv;

    this.subDivisionList = [];

    for (let i = 0; i < this.subDivisionHierarchy.length; i++) {

      if (this.subDivisionHierarchy[i].division == division && this.subDivisionHierarchy[i].depotType == 'SUB_DIV') {

        this.subDivisionList.push(this.subDivisionHierarchy[i]);
        this.enableSubDivision = true;

      }
    }

  }

  findDepot() {

    this.depotList = [];

    let subDivision = this.searchInputFormGroup.value.subDiv;

    for (let i = 0; i < this.depotHierarchy.length; i++) {

      if (this.depotHierarchy[i].subDivision == subDivision && this.depotHierarchy[i].depotType == 'OHE') {

        this.depotList.push(this.depotHierarchy[i]);
        //this.depotHierarchy.depotList;
        this.enableDepot = true;

      }
    }

  }

  findFacility() {
    this.depotList = [];
    for (let i = 0; i < this.depotHierarchy.length; i++) {

      if (this.depotHierarchy[i].depotType == 'OHE') {

        this.depotList.push(this.depotHierarchy[i]);
        //this.depotHierarchy.depotList;
        this.enableDepot = true;

      }
    }
  }

  findChangeStatus() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_STATUS_ITEM + 'ASSET_STATUS_TYPES').subscribe((data) => {
      this.ChangeStatus = data;

    }, error => {
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
    this.sendAndRequestService.requestForGET(Constants.app_urls.OPERATIONS.ASSET_STATUS_CHANGE.GET_ALL_TOWERCARS+this.loggedUserData.username).subscribe((data) => {
      this.towerCarList = data;
      for (let i = 0; i < this.towerCarList.length; i++) {
        this.towerCarList[i].sno = i + 1;
        this.towerCarList[i].dateOfStatus = this.datePipe.transform(this.towerCarList[i].dateOfStatus, 'dd-MM-yyyy ');
        this.towerCarList[i].targetDateOfReady = this.datePipe.transform(this.towerCarList[i].targetDateOfReady, 'dd-MM-yyyy ');

        status.push(this.towerCarList[i]);
      }

      this.statusDataSource = new MatTableDataSource(status);
      this.enableStausTable = true;
      this.enableTable = false;
      //this.dataSource.paginator = this.paginator;
      // this.dataSource.sort = this.sort;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }

  getAssetStatusChange() {

    let statusList: AssetStatusChangeModel[] = [];
    this.AssetStatusList = [];
    this.activity = [];
    let division = this.searchInputFormGroup.value.dataDiv;

    let subDivision = this.searchInputFormGroup.value.subDiv;

    let facilityId = this.searchInputFormGroup.value.facilityId;
    this.spinnerService.show();
    this.sendAndRequestService.requestForGET(Constants.app_urls.OPERATIONS.ASSET_STATUS_CHANGE.
      GET_TOWERCARS_BASEDON_DIVISION + division + '/' + subDivision + '/' + facilityId).subscribe((data) => {
        this.AssetStatusList = data;

        if (data) {
          for (var i = 0; i < this.AssetStatusList.length; i++) {
            this.AssetStatusList[i].sno = i + 1;
            if (this.AssetStatusList[i].dateOfStatus) {
              this.AssetStatusList[i].dateOfStatus = new Date(this.AssetStatusList[i].dateOfStatus);
            }
            if (this.AssetStatusList[i].targetDateOfReady) {
              this.AssetStatusList[i].targetDateOfReady = !!this.AssetStatusList[i].targetDateOfReady ? new Date(this.AssetStatusList[i].dateOfStatus) : '';
            }
            this.AssetStatusList[i].dateOfManufacture = this.datePipe.transform(this.AssetStatusList[i].dateOfManufacture, 'dd-MM-yyyy');
            this.AssetStatusList[i].nextAoh = this.datePipe.transform(this.AssetStatusList[i].nextAoh, 'dd-MM-yyyy');
            this.AssetStatusList[i].nextPoh = this.datePipe.transform(this.AssetStatusList[i].nextPoh, 'dd-MM-yyyy');
            //this.AssetStatusList[i].dateOfStatus = this.datePipe.transform(this.AssetStatusList[i].dateOfStatus,'dd-MM-yyyy');     
            this.activity.push(this.AssetStatusList[i]);
          }
        }
        this.dataSource = new MatTableDataSource(this.activity);
        console.log((this.dataSource));
        this.activity = this.statusList;
        this.enableStausTable = false;
        this.enableTable = true;
        this.title = Constants.EVENTS.ADD;

      });

  }

  saveAction(row) {
    
   if (row.dateOfChange == null ) {
   
     this.dateOfStatus = row.dateOfStatus
     
     
   } else{
    this.dateOfStatus = row.dateOfChange
    
   }
   if(row.status == null){
     this.status=  row.currentStatus
   }
   else{
    this.status=  row.status
   }
    let towerCar = {
      
      "assetType": row.assetType,
      "assetId": row.assetId,
      "facilityId": row.facilityId,
      "dateOfStatus": this.dateOfStatus,
      "currentStatus": this.status,
      //"status": row.status,
     // "dateOfChange": row.dateOfChange,
      "remarks": row.remarks,
      "createdBy": this.loggedUserData.username,
      "createdOn": new Date()
      

    }
    this.sendAndRequestService.requestForPOST(Constants.app_urls.OPERATIONS.ASSET_STATUS_CHANGE.SAVE, towerCar, false).subscribe(response => {
      this.spinnerService.show();
      this.resp = response;

      if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("TW Status Data Added Successfully");
        this.searchInputFormGroup.reset();
        this.dataSource = new MatTableDataSource();
        this.getAllData();
       
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


  addEvent($event, assetId) {
    this.toMinDate = new Date($event.value);
    console.log('assetId: '+assetId);
    console.log($event.target.value);
    this.dateStatusEdit = false;
    this.dataSource.filteredData.map((item, index) =>{
      if(item.assetId == assetId){
        this.dataSource.filteredData[index]['dateOfStatus']= this.toMinDate;
      }
    });
    console.log(this.dataSource.filteredData)
  }
  addChangeEvent($event, assetId){
    this.toMinDate = new Date($event.value);
    console.log('assetId: '+assetId);
    console.log($event.target.value);
    this.dateOfChangeEdit = false;
    this.dataSource.filteredData.map((item, index) =>{
      if(item.assetId == assetId){
        this.dataSource.filteredData[index]['dateOfChange']= this.toMinDate;
      }
    });
    console.log(this.dataSource.filteredData)
  }
  onGoBack() {
    this.location.back();
  }

  delete(id) {
    this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
      disableClose: false
    });
    this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete TW status?";
    this.confirmDialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.OPERATIONS.ASSET_STATUS_CHANGE.DELETE, id).subscribe((data) => {
          this.resp = data;
          if (this.resp.code == 200 && !!this.resp) {
            this.commonService.showAlertMessage(this.resp.message);
            this.getAllData();
          } else {
            this.commonService.showAlertMessage("TW Status Deletion Failed.");
          }
        }, error => {
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

  twHistory(row) {
    this.dialog.open(AssetStatusDialogComponent, {
      height: '600px',
      width: '80%',
      data: row.assetId,
    });
  }

  enableRemarks($event, assetId) {
    console.log($event);
    this.remarksEdit = true;
    this.editAssetId = assetId
  }
  disableRemarks($event, assetId){
    console.log('assetId: '+assetId);
    console.log($event.target.value);
    this.remarksEdit = false;
    this.dataSource.filteredData.map((item, index) =>{
      if(item.assetId == assetId){
        this.dataSource.filteredData[index]['remarks']=$event.target.value;
      }
    });
    console.log(this.dataSource.filteredData)
  }  
  enableDateStatus($event, assetId) {
    console.log($event);
    this.dateStatusEdit = true;
    this.editAssetId = assetId
  }
  disableDateStatusEdit($event, assetId){
    console.log('assetId: '+assetId);
    console.log($event.target.value);
    this.dateStatusEdit = false;
    this.dataSource.filteredData.map((item, index) =>{
      if(item.assetId == assetId){
        this.dataSource.filteredData[index]['dateStatus']=$event.target.value;
      }
    });
    console.log(this.dataSource.filteredData)
  }
  
  enableStatus($event, assetId) {
    console.log($event);
    this.statsEdit = true;
    this.editAssetId = assetId
  }
  selectedStatus(value, assetId){
    console.log('assetId: '+assetId);
    console.log(value);
    this.statsEdit = false;
    this.dataSource.filteredData.map((item, index) =>{
      if(item.assetId == assetId){
        this.dataSource.filteredData[index]['status']=value;
      }
    });
    console.log(this.dataSource.filteredData)
  }
  enableDateOfChange($event, assetId) {
    console.log($event);
    this.dateOfChangeEdit = true;
    this.editAssetId = assetId
  }
  disableDateOfChangeEdit($event, assetId){
    console.log('assetId: '+assetId);
    console.log($event.target.value);
    this.dateOfChangeEdit = false;
    this.dataSource.filteredData.map((item, index) =>{
      if(item.assetId == assetId){
        this.dataSource.filteredData[index]['dateOfChange']=$event.target.value;
      }
    });
    console.log(this.dataSource.filteredData)
  }

  enableCurentStatus($event, assetId) {
    console.log($event);
    this.curentStatusEdit = true;
    this.editAssetId = assetId
  }
  selectedCurrentStatus(value, assetId){
    console.log('assetId: '+assetId);
    console.log(value);
    this.curentStatusEdit = false;
    this.dataSource.filteredData.map((item, index) =>{
      if(item.assetId == assetId){
        this.dataSource.filteredData[index]['currentStatus']=value;
      }
    });
    console.log(this.dataSource.filteredData)
  }
  
}

