import { OnInit, Component, ViewChild, ElementRef} from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { AssetScheduleActivityAssocModel } from 'src/app/models/asset-schedule-activity-assoc.model';
import { Constants } from 'src/app/common/constants';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { DatePipe } from '@angular/common';
import { DataViewDialogComponent } from 'src/app/components/data-view-dialog/data-view-dialog.component';


@Component({
    selector: 'asset-schedule-activity-assoc',
    templateUrl: './asset-schedule-activity-assoc.component.html',
    styleUrls: []
})

export class AssetScheduleActivityAssocComponent implements OnInit{
    editPermission: boolean = true;
    addPermission: boolean = true;
    deletePermission: boolean = true;
    addSchActAssoc:boolean;
    assetSchActAssocFormGroup:FormGroup;
    id: number = 0;
    title: string = "Save";
    ActivityAssocList:any;
    editAssetSchActAssocResponse: any;
    responseStatus: any;
    activityAssocErrors : any;
    saveAssoc:boolean;
    value:string;
    pattern = "^[A-Z0-9]+$";
    assetSchActAssocDataSource: MatTableDataSource<AssetScheduleActivityAssocModel>;
    assetSchActAssocDisplayColumns = ['sno','asaSeqId','activityId','activityPositionId','makeCode',
    'modelCode','activityFlag','displayOrder','lowerLimit','upperLimit','description','id'] ;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
    assetSchActassocResponse:any;
    loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
    assetSchList:any;
    activityList:any;
    measuresList:any;
    makeList:any;
    modelList:any;
    enableAsaSeqId:any;
    enableActivityId:any;
    enablePositionId:any;
    resp:any;
    filterData;
    gridData = [];

    constructor(  
        private formBuilder: FormBuilder,
        private commonService: CommonService,
        private spinnerService: Ng4LoadingSpinnerService,
        private sendAndRequestService:SendAndRequestService,
        public dialog: MatDialog,
        private datePipe: DatePipe,){
            this.activityAssocErrors = {
                asaSeqId: {},
                activityId:{},
                activityPositionId:{},
                makeCode:{},
                modelCode:{},
                activityFlag:{},
                displayOrder:{},
                lowerLimit:{},
                upperLimit:{},
                description:{}
            }
                
            
    }

    ngOnInit () {
                this.findAssetsSchedules();
                this.findActivities();
                this.findMeasures();
                this.findMake();
                this.findModel();
        var permissionName = this.commonService.getPermissionNameByLoggedData("TRD CONFIG","ASSET SCHEDULE ACTIVITY ASSOCIATION") ;
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
    
        //this.getAllActivityAssocData(0, 30);
        this.getAllActivityAssocData();   

        this.filterData = {
          filterColumnNames: [
            { "Key": 'sno', "Value": " " },
            { "Key": 'asaSeqId', "Value": " " },           
            { "Key": 'activityId', "Value": " " },
            { "Key": 'activityPositionId', "Value": " " },
            { "Key": 'makeCode', "Value": " " },
            { "Key": 'modelCode', "Value": " " },
            { "Key": 'activityFlag', "Value": " " },
            { "Key": 'displayOrder', "Value": " " },
            { "Key": 'lowerLimit', "Value": " " },
            { "Key": 'upperLimit', "Value": " " },
            { "Key": 'description', "Value": " " },
           
          ],
          gridData: this.gridData,
          dataSource: this.assetSchActAssocDataSource,
          paginator: this.paginator,
          sort: this.sort
         
        }; 
              
    }
    updatePagination() {
      this.filterData.dataSource = this.filterData.dataSource;
      this.filterData.dataSource.paginator = this.paginator;
    }
            findAssetsSchedules()
            {
                
                    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSET_SCH_ASSOC.GET_ASSET_SCH_ASSOC)
                .subscribe((resp) => {
                this.assetSchList = resp;
               
                });
            }
            findActivities()
            {
                this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.MEASURE_ACTIVITY.GET_MEASURE)
                .subscribe((resp) => {
                this.activityList = resp;
                });

            }
            findMeasures()
            {
                this.sendAndRequestService.requestForGET(Constants.app_urls.CONFIG.ASSET_SCH_ACTIVITY_ASSOC.GET_MEASURES)
                .subscribe((resp) => {
                this.measuresList = resp;
                });
            }
            findMake()
            {
                this.sendAndRequestService.requestForGET(Constants.app_urls.CONFIG.MAKE.GET_MAKE)
                .subscribe((resp) => {
                this.makeList = resp;
                });

            }
            findModel()
            {
                this.sendAndRequestService.requestForGET(Constants.app_urls.CONFIG.MODEL.GET_MODEL)
                .subscribe((resp) => {
                this.modelList = resp;
                });

            }
   
          //  getAllActivityAssocData(from: number, to: number)
        getAllActivityAssocData() {

          this.spinnerService.show();
            const assoc : AssetScheduleActivityAssocModel[] = [];     
           // this.sendAndRequestService.requestForGET(Constants.app_urls.CONFIG.ASSET_SCH_ACTIVITY_ASSOC. GET_ASSET_SCH_ACT_ASSOC+ '/' + from + '/' + to)   
        this.sendAndRequestService.requestForGET(Constants.app_urls.CONFIG.ASSET_SCH_ACTIVITY_ASSOC. GET_ASSET_SCH_ACT_ASSOC)
        .subscribe((data) => {
            this.ActivityAssocList = data;
            for (let i = 0; i < this.ActivityAssocList.length; i++) {
                this.ActivityAssocList[i].sno = i+1;
                      this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSET_SCH_ASSOC.GET_ASSETTYPE_SCHEDULE_CODE_BASED_ON_ID+this.ActivityAssocList[i].asaSeqId).subscribe((data) => {
                     this.spinnerService.hide();
                     this.resp = data;
                     this.ActivityAssocList[i].asaSeqId = this.resp.assetType+'-'+ this.resp.scheduleCode;
                   });
                  this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.MEASURE_ACTIVITY.GET_ACTIVITYNAME_BASED_ON_ACTIVITY_ID+this.ActivityAssocList[i].activityId).subscribe((data) => {
                   this.spinnerService.hide();
                    this.resp = data;
                    this.ActivityAssocList[i].activityId = this.resp.activityName;
                  });
                assoc.push(this.ActivityAssocList[i]);              
            }
            // this.assetSchActAssocDataSource = new MatTableDataSource(assoc);
            // this.assetSchActAssocDataSource.paginator = this.paginator;
            // this.assetSchActAssocDataSource.sort = this.sort;
            this.filterData.gridData = assoc;
      this.assetSchActAssocDataSource = new MatTableDataSource(assoc);
      this.commonService.updateDataSource(this.assetSchActAssocDataSource,this.assetSchActAssocDisplayColumns);
      this.filterData.dataSource = this.assetSchActAssocDataSource;
      this.assetSchActAssocDataSource.paginator = this.paginator;
      this.assetSchActAssocDataSource.sort = this.sort;

        } , error => {});

        }

        onGoBack() {
            this.assetSchActAssocFormGroup.reset();
            this.addSchActAssoc= false;
            this.title = 'Save';
        }  

        NewAssetSchActAssoc(){
        this.addSchActAssoc = true;
        this.enableAsaSeqId=false;
        this.enableActivityId=false;
        this.enablePositionId=false;
        this.assetSchActAssocFormGroup = this.formBuilder.group({
        id: 0,
        'asaSeqId': [null,Validators.compose([Validators.required,Validators.maxLength(255)])],
        'activityId': [null, Validators.compose([Validators.required,Validators.maxLength(255)])],
        'activityPositionId': [null, Validators.compose([Validators.required,Validators.maxLength(255)]),this.duplicateasaSeqPosition.bind(this)],
        'makeCode' : [null, Validators.maxLength(255)],
        'modelCode' : [null, Validators.compose([Validators.required,Validators.maxLength(255)]),this.duplicateasaSeqPositionActivity.bind(this)],
        'activityFlag' : [null, Validators.compose([Validators.required,Validators.maxLength(255)])],
        'displayOrder' : [null, Validators.compose([Validators.required,Validators.maxLength(255)]),this.duplicateasaSeqActivityDispaly.bind(this)],
        'lowerLimit' : [null, Validators.maxLength(255)],
        'upperLimit' : [null, Validators.maxLength(255)],
        'description':[null, Validators.maxLength(255)],
        
    });
    } 

        applyFilter(filterValue: string) {
            filterValue = filterValue.trim(); // Remove whitespace
            filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
            this.assetSchActAssocDataSource.filter = filterValue;
        }

  
  assetSchActAssocSubmit(){ 

    let asaSeqId: string=this.assetSchActAssocFormGroup.value.asaSeqId;
   let activityId: string=this.assetSchActAssocFormGroup.value.activityId;
   let activityPositionId: string=this.assetSchActAssocFormGroup.value.activityPositionId;
   let makeCode: string=this.assetSchActAssocFormGroup.value.makeCode;
   let modelCode: string=this.assetSchActAssocFormGroup.value.modelCode;
   let activityFlag: string=this.assetSchActAssocFormGroup.value.activityFlag;
   let displayOrder: string=this.assetSchActAssocFormGroup.value.displayOrder;
   let lowerLimit: string=this.assetSchActAssocFormGroup.value.lowerLimit;
   let upperLimit: string=this.assetSchActAssocFormGroup.value.upperLimit;
   let description: string=this.assetSchActAssocFormGroup.value.description;

   if (this.title ==  Constants.EVENTS.SAVE) {
       var saveActAssocModel={
                               'asaSeqId':asaSeqId,
                               'activityId': activityId,
                               'activityPositionId':activityPositionId,
                               'makeCode':makeCode,
                               'modelCode':modelCode,
                               'activityFlag':activityFlag,
                               'displayOrder':displayOrder,
                               'lowerLimit':lowerLimit,
                               'upperLimit':upperLimit,
                               'description':description,                
                               "createdBy" : this.loggedUserData.username,
                               "createdStamp": new Date(),
                                "createdTxStamp": new Date(),
       }              
       this.sendAndRequestService.requestForPOST(Constants.app_urls.CONFIG.ASSET_SCH_ACTIVITY_ASSOC.SAVE_ASSET_SCH_ACT_ASSOC,saveActAssocModel, false).subscribe(data => {
           this.assetSchActassocResponse = data;
           if(this.assetSchActassocResponse.code == 200 && !!this.assetSchActassocResponse) {
               this.commonService.showAlertMessage(this.assetSchActassocResponse.message);
               //this.getAllActivityAssocData(0, 30);
               this.getAllActivityAssocData();
               this.assetSchActAssocFormGroup.reset();
           }else {
               this.commonService.showAlertMessage("Asset Schedule Activity Assoc Data Saving Failed.");
           }
           this.spinnerService.hide();
       } , error => {
           console.log('ERROR >>>');
           this.spinnerService.hide();
           this.commonService.showAlertMessage("Asset Schedule Activity Assoc Data Saving Failed.");
       });
   }

     
       else if (this.title == Constants.EVENTS.UPDATE )
        {
           let id: number = this.editAssetSchActAssocResponse.id;
           var updateActAssocModel={
                                       'id':id,
                                       'asaSeqId':this.editAssetSchActAssocResponse.asaSeqId,
                               'activityId':this.editAssetSchActAssocResponse.activityId,
                               'activityPositionId':activityPositionId,
                               'makeCode':makeCode,
                               'modelCode':modelCode,
                               'activityFlag':activityFlag,
                               'displayOrder':displayOrder,
                               'lowerLimit':lowerLimit,
                               'upperLimit':upperLimit,
                               'description':description,
                               "lastUpdatedStamp": new Date(),
                               "lastUpdatedTxStamp": new Date(),
                                       
                                    }    
               this.sendAndRequestService.requestForPUT(Constants.app_urls.CONFIG.ASSET_SCH_ACTIVITY_ASSOC.UPDATE_ASSET_SCH_ACT_ASSOC,updateActAssocModel, false).
               subscribe(data => {
                                   this.assetSchActassocResponse = data;
                                   if(this.assetSchActassocResponse.code == 200 && !!this.assetSchActassocResponse)
                                   {
                                       this.commonService.showAlertMessage(this.assetSchActassocResponse.message);
                                      // this.getAllActivityAssocData(0, 30);
                                      this.getAllActivityAssocData();
                                       this.assetSchActAssocFormGroup.reset();
                                       this.addSchActAssoc=  false;
                                       this.title = "Save";
                                   }
                                   else {
                                           this.commonService.showAlertMessage("Asset Schedule Activity Assoc Data Updating Failed.");
                                       }
                               } , 
                               error => {
               
                                       this.spinnerService.hide();
                                       this.commonService.showAlertMessage("Asset Schedule Activity Assoc Data Updating Failed.");
                                       })
           
       }
}
deleteAssetActSchAssoc(id) {
    this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
        disableClose: false
      });
    this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected Assoc?";
    this.confirmDialogRef.afterClosed().subscribe(result => {
        if(result){
          this.sendAndRequestService.requestForDELETE(Constants.app_urls.CONFIG.ASSET_SCH_ACTIVITY_ASSOC.DELETE_ASSET_SCH_ACT_ASSOC,id).subscribe(data => {
                  this.assetSchActassocResponse = data;
                
                    if(this.assetSchActassocResponse.code == 200 && !!this.assetSchActassocResponse) {
                        this.commonService.showAlertMessage(this.assetSchActassocResponse.message);
                         //this.getAllActivityAssocData(0, 30);
                         this.getAllActivityAssocData();
                     } else {
                         this.commonService.showAlertMessage("Activity ScheduleAssoc Deletion Failed.");
                     }	
                },error => {
                    console.log('ERROR >>>');
                      this.spinnerService.hide();
                      this.commonService.showAlertMessage("Activity ScheduleAssoc Deletion Failed.");
                });
        }
    });
}

editAssetSchActAssoc(id) {
    this.addSchActAssoc = true;
    this.ActAssocEditAction(id);
    this.title = 'Update';
    this.enableAsaSeqId=true;
    this.enableActivityId=true;
    this.enablePositionId=true;

}
ActAssocEditAction(id: number) {
 
    this.assetSchActAssocFormGroup = this.formBuilder.group({
    id: 0,
    'asaSeqId': [null,Validators.compose([Validators.required,Validators.maxLength(255)])],
    'activityId': [null, Validators.compose([Validators.required,Validators.maxLength(255)])],
    'activityPositionId': [null, Validators.compose([Validators.required,Validators.maxLength(255)]),this.duplicateasaSeqPositionAndId.bind(this)],
    'makeCode' : [null, Validators.maxLength(255)],
    'modelCode' : [null, Validators.compose([Validators.required,Validators.maxLength(255)]),this.duplicateasaSeqPositionActivityAndId.bind(this)],
    'activityFlag' : [null, Validators.compose([Validators.required,Validators.maxLength(255)])],
    'displayOrder' : [null, Validators.compose([Validators.required,Validators.maxLength(255)]),this.duplicateasaSeqActivityDispalyAndId.bind(this)],
    'lowerLimit' : [null, Validators.maxLength(255)],
    'upperLimit' : [null, Validators.maxLength(255)],
    'description':[null, Validators.maxLength(255)],
    
});
    this.sendAndRequestService.requestForGET(Constants.app_urls.CONFIG.ASSET_SCH_ACTIVITY_ASSOC.GET_ASSET_SCH_ACT_ASSOC_ID+id).subscribe((responseData) => {
        this.editAssetSchActAssocResponse = responseData;
         let activityName = '';
         this.activityList.filter(element => {
           if (element.activityId === this.editAssetSchActAssocResponse.activityId) {
              activityName = element.activityName
           }
        
        });
        let assetTypeAndscheduleCode='';
       
        this.assetSchList.filter(element =>{
          if(element.asaSeqId===this.editAssetSchActAssocResponse.asaSeqId){
            assetTypeAndscheduleCode = element.assetType +'-'+element.scheduleCode
            
       
          }
        })
          this.assetSchActAssocFormGroup.patchValue
          ({
                            id: this.editAssetSchActAssocResponse.id,
                            asaSeqId:assetTypeAndscheduleCode,
                            activityId: activityName,
                            activityPositionId: this.editAssetSchActAssocResponse.activityPositionId,
                            makeCode:this.editAssetSchActAssocResponse.makeCode,
                            modelCode :this.editAssetSchActAssocResponse.modelCode,
                            activityFlag:this.editAssetSchActAssocResponse.activityFlag,
                            displayOrder:this.editAssetSchActAssocResponse.displayOrder,
                            lowerLimit:this.editAssetSchActAssocResponse.lowerLimit,
                            upperLimit:this.editAssetSchActAssocResponse.upperLimit,
                            description:this.editAssetSchActAssocResponse.description
             })
        
    } ,error => {})
    this.id=id;
    if (!isNaN(this.id)) {
        this.title = 'Update';
      } else {
        this.title = 'Save';      
      }
}
  onFormValuesChanged() {
    for (const field in this.activityAssocErrors) {
      if (!this.activityAssocErrors.hasOwnProperty(field)) {
        continue;
      }
      // Clear previous errors
      this.activityAssocErrors[field] = {};

      // Get the control
      const control = this.assetSchActAssocFormGroup.get(field);
      if (control && control.dirty && !control.valid) {
        this.activityAssocErrors[field] = control.errors;
      }
    }
  }

  duplicateasaSeqPositionActivity() {
    let asaSeqId: string = this.assetSchActAssocFormGroup.controls['asaSeqId'].value;
    let activityId: string = this.assetSchActAssocFormGroup.controls['activityId'].value;
    let makeCode: string = this.assetSchActAssocFormGroup.controls['makeCode'].value;
    let modelCode: string = this.assetSchActAssocFormGroup.controls['modelCode'].value;

    const q = new Promise((resolve, reject) => {          

       this.sendAndRequestService.requestForGET(
              Constants.app_urls.CONFIG.ASSET_SCH_ACTIVITY_ASSOC. EXIST_SEQ_POSITION_ACTIVITY+asaSeqId+'/'+activityId+'/'+makeCode+'/'+modelCode).subscribe
              ((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateasaSeqPositionActivity': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateasaSeqPositionActivity': true }); });
    });
    return q;
  }

  duplicateasaSeqPosition() {
    let asaSeqId: string = this.assetSchActAssocFormGroup.controls['asaSeqId'].value;
    let activityPositionId: string = this.assetSchActAssocFormGroup.controls['activityPositionId'].value;
    let makeCode: string = this.assetSchActAssocFormGroup.controls['makeCode'].value;
    let modelCode: string = this.assetSchActAssocFormGroup.controls['modelCode'].value;
   

    const q = new Promise((resolve, reject) => {          

       this.sendAndRequestService.requestForGET(
              Constants.app_urls.CONFIG.ASSET_SCH_ACTIVITY_ASSOC.EXIST_SEQ_POSITION+asaSeqId+'/'+activityPositionId+'/'+makeCode+'/'+modelCode).subscribe
              ((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateasaSeqPosition': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateasaSeqPosition': true }); });
    });
    return q;
  }

  duplicateasaSeqActivityDispaly() {
    let asaSeqId: string = this.assetSchActAssocFormGroup.controls['asaSeqId'].value;
    let activityId: string = this.assetSchActAssocFormGroup.controls['activityId'].value;
    let displayOrder: string = this.assetSchActAssocFormGroup.controls['displayOrder'].value;
    let makeCode: string = this.assetSchActAssocFormGroup.controls['makeCode'].value;
    let modelCode: string = this.assetSchActAssocFormGroup.controls['modelCode'].value;

    const q = new Promise((resolve, reject) => {          

       this.sendAndRequestService.requestForGET(
              Constants.app_urls.CONFIG.ASSET_SCH_ACTIVITY_ASSOC.EXIST_SEQ_ACTIVITY_DISPLAY+asaSeqId+'/'+activityId+'/'+displayOrder+'/'+makeCode+'/'+modelCode).subscribe
              ((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateasaSeqActivityDispaly': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateasaSeqActivityDispaly': true }); });
    });
    return q;
  }

  duplicateasaSeqPositionActivityAndId() {
    let id=this.id;
    let asaSeqId: string = this.assetSchActAssocFormGroup.controls['asaSeqId'].value;
    let activityId: string = this.assetSchActAssocFormGroup.controls['activityId'].value;
    let makeCode: string = this.assetSchActAssocFormGroup.controls['makeCode'].value;
    let modelCode: string = this.assetSchActAssocFormGroup.controls['modelCode'].value;

    const q = new Promise((resolve, reject) => {          

       this.sendAndRequestService.requestForGET(
              Constants.app_urls.CONFIG.ASSET_SCH_ACTIVITY_ASSOC. EXIST_SEQ_POSITION_ACTIVITY_AND_ID+id+'/'+asaSeqId+'/'+activityId+'/'+makeCode+'/'+modelCode).subscribe
              ((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateasaSeqPositionActivityAndId': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateasaSeqPositionActivityAndId': true }); });
    });
    return q;
  }

  duplicateasaSeqPositionAndId() {
    let id=this.id;
    let asaSeqId: string = this.assetSchActAssocFormGroup.controls['asaSeqId'].value;
    let activityPositionId: string = this.assetSchActAssocFormGroup.controls['activityPositionId'].value;
    let makeCode: string = this.assetSchActAssocFormGroup.controls['makeCode'].value;
    let modelCode: string = this.assetSchActAssocFormGroup.controls['modelCode'].value;
   

    const q = new Promise((resolve, reject) => {          

       this.sendAndRequestService.requestForGET(
              Constants.app_urls.CONFIG.ASSET_SCH_ACTIVITY_ASSOC.EXIST_SEQ_POSITION_AND_ID+id+'/'+asaSeqId+'/'+activityPositionId+'/'+makeCode+'/'+modelCode).subscribe
              ((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateasaSeqPositionAndId': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateasaSeqPositionAndId': true }); });
    });
    return q;
  }

  duplicateasaSeqActivityDispalyAndId() {
    let id=this.id;
    let asaSeqId: string = this.assetSchActAssocFormGroup.controls['asaSeqId'].value;
    let activityId: string = this.assetSchActAssocFormGroup.controls['activityId'].value;
    let displayOrder: string = this.assetSchActAssocFormGroup.controls['displayOrder'].value;
    let makeCode: string = this.assetSchActAssocFormGroup.controls['makeCode'].value;
    let modelCode: string = this.assetSchActAssocFormGroup.controls['modelCode'].value;

    const q = new Promise((resolve, reject) => {          

       this.sendAndRequestService.requestForGET(
              Constants.app_urls.CONFIG.ASSET_SCH_ACTIVITY_ASSOC.EXIST_SEQ_ACTIVITY_DISPLAY_AND_ID+id+'/'+asaSeqId+'/'+activityId+'/'+displayOrder+'/'+makeCode+'/'+modelCode).subscribe
              ((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateasaSeqActivityDispalyAndId': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateasaSeqActivityDispalyAndId': true }); });
    });
    return q;
  }

  ViewData(data){
    var result = {
      'title':'Asset Schedule Activity Assoc',
      'dataSource':[{label:'Asset Schedule',value:data.asaSeqId},{label:'activity',value:data.activityId},
      {label:'activityPosition',value:data.activityPositionId},{label:'makeCode',value:data.makeCode},
      {label:'modelCode',value:data.modelCode},{label:'activityFlag',value:data.activityFlag},
      {label:'displayOrder',value:data.displayOrder},{label:'lowerLimit',value:data.lowerLimit},
      {label:'upperLimit',value:data.upperLimit},{label:'description',value:data.description}]
    }
    this.dataViewDialogRef = this.dialog.open(DataViewDialogComponent, {
      disableClose: false,
      height: '400px',
      width: '80%',       
      data:result,  
    });            
  }

  getServerData($event) {
    console.log($event);
    console.log($event.pageIndex + " : " + $event.pageSize + " : " + $event.length);
    if (((parseInt($event.pageIndex) + 1) * parseInt($event.pageSize)) == $event.length) {
      //this.getAllActivityAssocData($event.length + 1, $event.length + 30);
    }
  } 

}

    
    
    

   


   

      
     



    
    
    

   