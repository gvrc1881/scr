import { OnInit, Component, ViewChild } from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import { FootPatrollingInspectionModel,ObservationModel,ComplianceModel } from 'src/app/models/foot-patrolling-inspection.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { InspectionDocumentComponent } from '../../inpection-document-dialog/inspection-document-dialog.component';
import { ComplianceDocumentComponent } from '../../compliance-document-dialog/compliance-document-dialog.component';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { Router } from '@angular/router';
import { query } from '@angular/animations';
import { DatePipe } from '@angular/common';

@Component({
    selector: 'foot-patrolling-inspection',
    templateUrl: './foot-patrolling-inspection.component.html',
    styleUrls: ['./foot-patrolling-inspection.component.scss']
})
export class FootPatrollingInspectionComponent implements OnInit{
    loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
    addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    addObservation:boolean;
    addFPInspectionItem: boolean ;
    addMap:boolean;
    addComplianceItem:boolean;
    title: string = "Save";
    fpInspectionItemFormGroup: FormGroup;
    fpInspectionList : any;
    toMinDate=new Date();
    currentDate = new Date();
    facilityData:any;
    selectedFiles: File[] = [];
    filesExists: boolean = false;
    ComplianceStatus:any;
    observationCategoryData:any;
    statusTypeData:any;
    fpInspectionItemDataSource: MatTableDataSource<FootPatrollingInspectionModel>;
    fpInspectionItemDisplayColumns = ['sno' ,'facilityId','inspectionType' , 'section' , 'inspectionBy' , 'startTime' , 'stopTime' , 'id','observation'] ;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    editfpInspectionItemResponse: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    inspectionDocumentDialogRef: MatDialogRef<InspectionDocumentComponent>;
       //Observation 
   observationFormGroup: FormGroup;
   obsList : any;
   observationData = [];
   gridData = [];
   observationDataSource: MatTableDataSource<ObservationModel>;
   observationDisplayColumns = ['sno' ,'location','observationCategory' , 'observationItem' ,  'description' ,'actionRequired','attachment','id','compliance'] ;
   editObservationResponse: any;
   observationResponse: any;
   insId: any;
   obsId:any;
   filterData;
   maxDate: Date;
   facilityId:string;
   observationItemData:any;
   attachedImages:any;
   selectedFacilityId = 0;
   selectedExactDate = new Date();
   nameOfStaff:any;
   section:any;
   //Compliance 
   complianceDocumentDialogRef: MatDialogRef<ComplianceDocumentComponent>;
   complianceFormGroup: FormGroup;
   complianceList : any;
   complianceDataSource: MatTableDataSource<ComplianceModel>;
   complianceDisplayColumns = ['sno' ,'status','action' , 'complianceBy' ,  'compliedDateTime' ,'attachment', 'id'] ;
   editComplianceResponse: any;
   complianceResponse: any;
   

    constructor(
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private dialog: MatDialog,
        private router: Router, 
        private datePipe: DatePipe,
        private spinnerService: Ng4LoadingSpinnerService,
        private sendAndRequestService:SendAndRequestService
    ){

    }

    ngOnInit () {
        if(this.router.url == '/Observations'){
             console.log("ObservationsUrl"+this.router.url)    
          }
        this.getAllFootPatrollingInspectionData();
        this.depotTypeForOhe();
        this.categoryList();
        var permissionName = this.commonService.getPermissionNameByLoggedData("FP","FP Inspection") ;//p == 0 ? 'No Permission' : p[0].permissionName;
        console.log("permissionName***"+permissionName);
        this.addPermission = this.commonService.getPermissionByType("Add", permissionName); 
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
        this.fpInspectionItemFormGroup = this.formBuilder.group({
            id: 0,
            'facilityId':[null],
            'inspectionType':[null, Validators.compose([Validators.required, Validators.maxLength(250)])],
            'section': [null, Validators.compose([Validators.required, Validators.maxLength(250)])],
            'inspectionBy': [null,Validators.compose([Validators.required, Validators.maxLength(250)])],
            'startTime': [null],
            'stopTime' : [null]
        });
        //Observation Permissions
        this.getAllObservationsData(query);
        var ObspermissionName = this.commonService.getPermissionNameByLoggedData("FP","Observations") ;//p == 0 ? 'No Permission' : p[0].permissionName;
          console.log("ObspermissionName"+ObspermissionName);
        this.addPermission = this.commonService.getPermissionByType("Add", ObspermissionName); 
    	this.editPermission = this.commonService.getPermissionByType("Edit", ObspermissionName);
        this.deletePermission = this.commonService.getPermissionByType("Delete", ObspermissionName);
        this.observationFormGroup = this.formBuilder.group({
            id: 0,
            'inspectionSeqId':[null],
            'location':[null],
            'observationCategory':[null],
            'observationItem': [null],
            'severity':[null],
            'priority':[null],
            'actionRequired':[null],
            'description' : [null],
            'attachment'  :[null]
            
        });
        //Compliance Permissions
        this.statusList();
        this.getAllCompliancesData();
        this.complianceFormGroup = this.formBuilder.group({
            id: 0,
            'obeservationSeqId':[null],
            'status':[null],
            'action':[null],
            'complianceBy': [null],
            'compliedDateTime' : [null],
            'attachment'  :[null]
        });
       var query = this.section +'/'+ this.selectedFacilityId +'/'+this.nameOfStaff +'/'+this.datePipe.transform(this.selectedExactDate, 'yyyy-MM-dd') ;
       console.log('query = '+query);
        localStorage.setItem('query','');

        this.filterData = {
            filterColumnNames: [
              { "Key": 'sno', "Value": " " },
              { "Key": "inspectionSeqId", "Value": "" },
              { "Key": "location", "Value":" "},
              { "Key": "observationCategory", "Value": "" },
              { "Key": "actionRequired", "Value": "" },
              { "Key": "description", "Value": "" },

            
            ],
            gridData: this.gridData,
            observationDataSource: this.observationDataSource,
            paginator: this.paginator,
            sort: this.sort
          };

    }
    
      public get f() { return this.fpInspectionItemFormGroup.controls; }

      public get o() { return this.observationFormGroup.controls; }

      public get c() {return this.complianceFormGroup.controls;}

      addEvent($event) {
        this.toMinDate = new Date($event.value);
      }
    getAllFootPatrollingInspectionData() {
        const footPatrollingInspection : FootPatrollingInspectionModel[] = [];
        this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.FP_INSPECTION.GET_FP_INSPECTION).subscribe((data) => {
            this.fpInspectionList = data;
            for (let i = 0; i < this.fpInspectionList.length; i++) {
                this.fpInspectionList[i].sno = i+1;
                footPatrollingInspection.push(this.fpInspectionList[i]);              
            }
            this.fpInspectionItemDataSource = new MatTableDataSource(footPatrollingInspection);
            this.fpInspectionItemDataSource.paginator = this.paginator;
            this.fpInspectionItemDataSource.sort = this.sort;

        } , error => {});

    }
    fpInspectionItemSubmit () {
        let facilityId: string = this.fpInspectionItemFormGroup.value.facilityId;
        let inspectionType: string = this.fpInspectionItemFormGroup.value.inspectionType;
        let section: string = this.fpInspectionItemFormGroup.value.section;
        let inspectionBy: string = this.fpInspectionItemFormGroup.value.inspectionBy;
        let startTime: Date = this.fpInspectionItemFormGroup.value.startTime;
        let stopTime: Date = this.fpInspectionItemFormGroup.value.stopTime
        this.addFPInspectionItem = false;
        
        if (this.title ==  Constants.EVENTS.SAVE) {
            var saveFpInspection={
                'facilityId':facilityId,
                'inspectionType':inspectionType,
                'section': section,
                'inspectionBy': inspectionBy,
                'startTime':startTime,
                'stopTime': stopTime
            }               
            this.sendAndRequestService.requestForPOST(Constants.app_urls.DAILY_SUMMARY.FP_INSPECTION.SAVE_FP_INSPECTION, saveFpInspection, false).subscribe(response => {
                this.commonService.showAlertMessage('Successfully saved');
                this.getAllFootPatrollingInspectionData();
                this.fpInspectionItemFormGroup.reset();
            } , error => {});
        }else if (this.title == Constants.EVENTS.UPDATE ) {
            let id: number = this.editfpInspectionItemResponse.id;
            var updateFpSInspection={
                'id':id,
                'facilityId':facilityId,
                'inspectionType':inspectionType,
                'section': section,
                'inspectionBy':inspectionBy,
                'startTime': startTime,
                'stopTime' : stopTime
            }
            this.sendAndRequestService.requestForPUT(Constants.app_urls.DAILY_SUMMARY.FP_INSPECTION.UPDATE_FP_INSPECTION,updateFpSInspection, false).subscribe(response => {
                this.commonService.showAlertMessage('Successfully updated');
                this.getAllFootPatrollingInspectionData();
                this.fpInspectionItemFormGroup.reset();
                this.addFPInspectionItem =  false;
            } , error => {})
            
        }
    }

    editFPInspectionItem (id) {
        this.addFPInspectionItem = true;
        this.fpInspectionItemEditAction(id);
        this.title = 'Update';
    }

    fpInspectionItemEditAction(id: number) {
        this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.FP_INSPECTION.GET_FP_INSPECTION_ID+id).subscribe((responseData) => {
            this.editfpInspectionItemResponse = responseData;
            this.fpInspectionItemFormGroup.patchValue({
                id: this.editfpInspectionItemResponse.id,
                facilityId:this.editfpInspectionItemResponse.facilityId,
                inspectionType:this.editfpInspectionItemResponse.inspectionType,
                section: this.editfpInspectionItemResponse.section,
                inspectionBy: this.editfpInspectionItemResponse.inspectionBy,
                startTime: this.editfpInspectionItemResponse.startTime,
                stopTime: !!this.editfpInspectionItemResponse.stopTime ? new Date(this.editfpInspectionItemResponse.stopTime) : ''

            });
            this.toMinDate = new Date(this.editfpInspectionItemResponse.startTime);
        } ,error => {})
    }


    deleteFPInspectionItem (id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected fpInspection item?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
                this.sendAndRequestService.requestForDELETE(Constants.app_urls.DAILY_SUMMARY.FP_INSPECTION.DELETE_FP_INSPECTION, id).subscribe(response => {
                        this.commonService.showAlertMessage('FP Inspection Deleted Successfully');
                        this.getAllFootPatrollingInspectionData();
                    },error => {});
            }
        });
    }


    applyFilter(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase();
        this.fpInspectionItemDataSource.filter = filterValue;
    }
    fpGoBack() {
        this.fpInspectionItemFormGroup.reset();
        this.addFPInspectionItem = false;
        this.title = 'Save';
    }
   
    depotTypeForOhe()
        {  
               this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_DEPOTTYPE_FOR_OHE).subscribe((data) => {
                 this.facilityData = data;
        }
               );

       }
    
    NewFPInspectionItem () {
        this.addFPInspectionItem = true;
    }
    NewMapItem(){
        this.addMap=true;
    }
    
     //Observations Related code for Add,delete,update,fetch records
     getAllObservationsData(query) {
         if(this.fpInspectionItemFormGroup.controls.id){
            const observation : ObservationModel[] = [];
            this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.OBSERVATION.GET_OBSERVATION_LIST_BASED_ON_QUERY+query).subscribe((data) => {
                this.obsList = data;
                for (let i = 0; i < this.obsList.length; i++) {
                    this.obsList[i].sno = i+1;
                    observation.push(this.obsList[i]);              
                }
                
                this.observationDataSource = new MatTableDataSource(observation);
                this.observationDataSource.paginator = this.paginator;
                this.observationDataSource.sort = this.sort;
        } , error => {});
    }
    }
     observationItemSubmit () {
        let location: string = this.observationFormGroup.value.location;
        let observationCategory: string = this.observationFormGroup.value.observationCategory;
        let observationItem: string = this.observationFormGroup.value.observationItem;
        let description: string = this.observationFormGroup.value.description;
        let actionRequired: string = this.observationFormGroup.value.actionRequired;
        let attachment:string = this.observationFormGroup.value.attachment;
        this.addObservation = false;
        
        if (this.title ==  Constants.EVENTS.SAVE) {
            let id: number = this.fpInspectionItemFormGroup.value.id;
            var observationModel ={
                'inspectionSeqId':this.insId,
                'location':location,
                'observationCategory': observationCategory,
                'observationItem':observationItem,
                'description':description,
                'actionRequired':actionRequired,
                "createdBy": this.loggedUserData.id,
            }
            let formdata: FormData = new FormData();
      for(var i=0;i<this.selectedFiles.length;i++){
          formdata.append('file', this.selectedFiles[i]);
      }
      formdata.append('inspectionSeqId', observationModel.inspectionSeqId);
      formdata.append('location', observationModel.location);
      formdata.append('observationCategory', observationModel.observationCategory);
      formdata.append('observationItem', observationModel.observationItem);
      formdata.append('description', observationModel.description);
      formdata.append('actionRequired', observationModel.actionRequired);
      formdata.append('createdBy', observationModel.createdBy);
            this.sendAndRequestService.requestForPOST(Constants.app_urls.DAILY_SUMMARY.OBSERVATION.SAVE_OBSERVATION,formdata, true).subscribe(response => {
                this.observationResponse = response
            if(this.observationResponse.code == 200 && !!this.observationResponse) {
                this.commonService.showAlertMessage(this.observationResponse.message);
                this.getAllObservationsData(query);
                this.observationFormGroup.reset();
                this.addObservation =  false;
            }else {
                this.commonService.showAlertMessage("Observation Data Saving Failed.");
            }    
        } , error => {
            console.log('ERROR >>>');
            this.spinnerService.hide();
            this.commonService.showAlertMessage("Observation  Data Saving Failed.");
        });
    }else if (this.title == Constants.EVENTS.UPDATE ) {
        let id: number = this.editObservationResponse.id;
        var updateObservationModel={
            'id':id,
            'inspectionSeqId':this.insId,
            'location':location,
            'observationCategory': observationCategory,
            'observationItem':observationItem,
            'description':description,
            'actionRequired':actionRequired,
            "updatedBy": this.loggedUserData.id,
            attachment: this.editObservationResponse.attachment,
        }
        let formdata: FormData = new FormData();
      for(var i=0;i<this.selectedFiles.length;i++){
          formdata.append('file', this.selectedFiles[i]);
      }
      formdata.append('id', updateObservationModel.id.toString());
      formdata.append('inspectionSeqId', updateObservationModel.inspectionSeqId);
      formdata.append('location', updateObservationModel.location);
      formdata.append('observationCategory', updateObservationModel.observationCategory);
      formdata.append('observationItem', updateObservationModel.observationItem);
      formdata.append('description', updateObservationModel.description);
      formdata.append('actionRequired', updateObservationModel.actionRequired);
      formdata.append('updatedBy', updateObservationModel.updatedBy);
      formdata.append('attachment', updateObservationModel.attachment);
        this.sendAndRequestService.requestForPUT(Constants.app_urls.DAILY_SUMMARY.OBSERVATION.UPDATE_OBSERVATION,formdata, true).subscribe(data => {
            this.observationResponse = data
            if(this.observationResponse.code == 200 && !!this.observationResponse) {
                this.commonService.showAlertMessage(this.observationResponse.message);
                this.getAllObservationsData(query);
                this.observationFormGroup.reset();
                this.addObservation =  false;
                this.title = "Save";
            }else {
                this.commonService.showAlertMessage("Observation Data Updating Failed.");
            }
        } , error => {
            console.log('ERROR >>>');
            this.spinnerService.hide();
            this.commonService.showAlertMessage("Observation  Data Updating Failed.");
        });
        
    }
}
    NewObservationItem (id) {
        console.log("observationItem"+id);
        this.insId = id;
        this.addObservation = true;
    }
    exactDateEvent($event) {
        this.selectedExactDate = new Date($event.value);
      }
      updateNameOfStaff($event) {
        this.nameOfStaff = $event.value;
      }
    updateFacilityName($event) {
        if ($event.value) {
          this.selectedFacilityId = $event.value;
        }
      }
      updateSection($event) {
        if ($event.value) {
          this.section = $event.value;
        }
      }

    executeQuery() {
        var query = "";
        query = this.section +'/'+ this.selectedFacilityId +'/'+this.nameOfStaff +'/'+this.datePipe.transform(this.selectedExactDate, 'yyyy-MM-dd') ;
        localStorage.setItem('query', query);
        this.getAllObservationsData(query);
      }
    onGoBack() {
        this.observationFormGroup.reset();
        this.addObservation = false;
        this.title = 'Save';
    }
    editObservationItem (id) {
        this.addObservation = true;
        this.ObservationEditAction(id);
        this.title = 'Update';
    }

    ObservationEditAction(id: number) {
        this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.OBSERVATION.GET_OBSERVATION_ID+id).subscribe((responseData) => {
            this.editObservationResponse = responseData;
            this.observationFormGroup.patchValue({
                id: this.editObservationResponse.id,
                location:this.editObservationResponse.location,
                observationCategory:this.editObservationResponse.observationCategory,
                observationItem: this.editObservationResponse.observationItem,
                description: this.editObservationResponse.description,
                actionRequired: this.editObservationResponse.actionRequired,

            });
            console.log(this.editObservationResponse.attachment);
            var commonId = !!this.editObservationResponse.attachment && this.editObservationResponse.attachment;
            console.log(commonId)
            this.spinnerService.hide();
            this.findAttachedFiles(commonId);
        } ,error => {})
    }
    findAttachedFiles(commonId){
        this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.OBSERVATION.GET_OBSERVATION_CONTENT_ID + commonId)
        .subscribe((resp) => {
          this.attachedImages = resp;
        })
      }

    deleteObservationItem (id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected Observation item?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
                this.sendAndRequestService.requestForDELETE(Constants.app_urls.DAILY_SUMMARY.OBSERVATION.DELETE_OBSERVATION, id).subscribe(response => {
                        this.commonService.showAlertMessage('Observation Deleted Successfully');
                        this.getAllObservationsData(query);
                    },error => {});
            }
        });
    }


    observationApplyFilter(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase();
        this.observationDataSource.filter = filterValue;
    }
    categoryList()
        {  
               this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.OBSERVATION_CATEGORIES.GET_OBS_CATEGORIES).subscribe((data) => {
                 this.observationCategoryData = data;
        }
               );

       }
       getObsCheckList(){
        var observationCategory = this.observationFormGroup.value.observationCategory ;
    	this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_OBSERVATION_CHECK_LIST_BASED_ON_OBSCATE + observationCategory).subscribe((data) => {
                 this.observationItemData = data;
        		});
    }
       upload(event) {
        if (event.target.files.length > 0) { this.filesExists = true; }
        for (var i = 0; i < event.target.files.length; i++) {
          this.selectedFiles.push(event.target.files[i]);
        }
      }
      removeFile(id) {
        this.selectedFiles.splice(id, 1);
      }
      removeEditFile(commonFileid, rowid){
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
          disableClose: false
      });
      this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete?';
      this.confirmDialogRef.afterClosed().subscribe(result => {
          if (result) {
              var id = localStorage.getItem('observationFileTypeId');
              var data ={
                "id":commonFileid,
                "fileName":rowid,
                "type":'observations'
            }
              this.sendAndRequestService.requestForPOST(Constants.app_urls.INSPECTIONS.INSPECTIONS.DELETE_FILE, data, false).subscribe(data => {
                  this.commonService.showAlertMessage("Deleted File Successfully");
                 this.findAttachedFiles(commonFileid);
              }, error => {
                  console.log('ERROR >>>');
                  this.commonService.showAlertMessage("File Deletion Failed.");
              })
               this.confirmDialogRef = null;
          }
         
      });
      }
      filesInfor: any;
      viewFilesDetails(id) {
        this.spinnerService.show();
        this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.OBSERVATION. GET_OBSERVATION_CONTENT_ID+ id).subscribe((response) => {
          this.filesInfor = response;
          this.spinnerService.hide();
          this.inspectionDocumentDialogRef = this.dialog.open(InspectionDocumentComponent, {
            disableClose: false,
            height: '600px',
            width: '80%',
            data: this.filesInfor,
          });
        }, error => this.commonService.showAlertMessage(error));
    
    
      }
//Compliance CRUD Operations

getAllCompliancesData() {
    const compliances : ComplianceModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.COMPLIANCES.GET_COMPLIANCE).subscribe((data) => {
        this.complianceList = data;
        for (let i = 0; i < this.complianceList.length; i++) {
            this.complianceList[i].sno = i+1;
            compliances.push(this.complianceList[i]);              
        }
        this.complianceDataSource = new MatTableDataSource(compliances);
        this.complianceDataSource.paginator = this.paginator;
        this.complianceDataSource.sort = this.sort;

    } , error => {});

}
NewComplianceItem (id) {
    console.log("complianceItem"+id);
    this.obsId = id;
    this.addComplianceItem = true;
}
complianceItemSubmit () {
        let status: string = this.complianceFormGroup.value.status;
        let action: string = this.complianceFormGroup.value.action;
        let complianceBy: string = this.complianceFormGroup.value.complianceBy;
        let compliedDateTime = this.complianceFormGroup.value.compliedDateTime
        this.addComplianceItem = false;
        if (this.title ==  Constants.EVENTS.SAVE) {
            var saveCompliance={
                'obeservationSeqId':this.obsId,
                'status':status,
                'action':action,
                'complianceBy': complianceBy,
                'compliedDateTime': compliedDateTime,
                "createdBy": this.loggedUserData.id,
            }
            let formdata: FormData = new FormData();
      for(var i=0;i<this.selectedFiles.length;i++){
          formdata.append('file', this.selectedFiles[i]);
      }
      formdata.append('obeservationSeqId', saveCompliance.obeservationSeqId);
      formdata.append('status', saveCompliance.status);
      formdata.append('action', saveCompliance.action);
      formdata.append('complianceBy', saveCompliance.complianceBy);
      formdata.append('compliedDateTime',saveCompliance.compliedDateTime );
      formdata.append('createdBy', saveCompliance.createdBy);              
            this.sendAndRequestService.requestForPOST(Constants.app_urls.DAILY_SUMMARY.COMPLIANCES.SAVE_COMPLIANCE,formdata , false).subscribe(response => {
                this.commonService.showAlertMessage('Successfully saved');
                this.getAllCompliancesData();
                this.complianceFormGroup.reset();
            } , error => {});
        }else if (this.title == Constants.EVENTS.UPDATE ) {
            let id: number = this.editComplianceResponse.id;
            var updateCompliances={
                'id':id,
                'obeservationSeqId':this.obsId,
                'status':status,
                'action':action,
                'complianceBy': complianceBy,
                'compliedDateTime':compliedDateTime,
                "updatedBy": this.loggedUserData.id,
                attachment: this.editObservationResponse.attachment,
            }
            let formdata: FormData = new FormData();
          for(var i=0;i<this.selectedFiles.length;i++){
              formdata.append('file', this.selectedFiles[i]);
          }
          formdata.append('id', updateCompliances.id.toString());
          formdata.append('obeservationSeqId', updateCompliances.obeservationSeqId);
          formdata.append('status', updateCompliances.status);
          formdata.append('action', updateCompliances.action);
          formdata.append('complianceBy', updateCompliances.complianceBy);
          formdata.append('compliedDateTime', updateCompliances.compliedDateTime);
          formdata.append('updatedBy', updateCompliances.updatedBy);
          formdata.append('attachment', updateCompliances.attachment);
            this.sendAndRequestService.requestForPUT(Constants.app_urls.DAILY_SUMMARY.COMPLIANCES.UPDATE_COMPLIANCE,formdata, false).subscribe(response => {
                this.commonService.showAlertMessage('Successfully updated');
                this.getAllCompliancesData();
                this.complianceFormGroup.reset();
                this.addComplianceItem =  false;
            } , error => {})
            
        }
    }
    comGoBack() {
        this.complianceFormGroup.reset();
        this.addComplianceItem = false;
        this.title = 'Save';
    }
    editComplianceItem (id) {
        this.addComplianceItem = true;
        this.complianceItemEditAction(id);
        this.title = 'Update';
    }

    complianceItemEditAction(id: number) {
        this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.COMPLIANCES.GET_COMPLIANCE_ID+id).subscribe((responseData) => {
            this.editComplianceResponse = responseData;
            this.complianceFormGroup.patchValue({
                id: this.editComplianceResponse.id,
                status:this.editComplianceResponse.status,
                action:this.editComplianceResponse.action,
                complianceBy: this.editComplianceResponse.complianceBy,
                compliedDateTime: this.editComplianceResponse.compliedDateTime,
            });console.log(this.editComplianceResponse.attachment);
            var commonId = !!this.editComplianceResponse.attachment && this.editComplianceResponse.attachment;
            console.log(commonId)
            this.spinnerService.hide();
            this.findCompAttachedFiles(commonId);
        } ,error => {})
    }
    findCompAttachedFiles(commonId){
        this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.OBSERVATION.GET_OBSERVATION_CONTENT_ID + commonId)
        .subscribe((resp) => {
          this.attachedImages = resp;
        })
      }
      comFilesInfor: any;
      compViewFilesDetails(id) {
        this.spinnerService.show();
        this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.OBSERVATION. GET_OBSERVATION_CONTENT_ID+ id).subscribe((response) => {
          this.comFilesInfor = response;
          this.spinnerService.hide();
          this.complianceDocumentDialogRef = this.dialog.open(ComplianceDocumentComponent, {
            disableClose: false,
            height: '600px',
            width: '80%',
            data: this.comFilesInfor,
          });
        }, error => this.commonService.showAlertMessage(error));
    
    
      }

    deleteComplianceItem (id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected Compliance item?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
                this.sendAndRequestService.requestForDELETE(Constants.app_urls.DAILY_SUMMARY.COMPLIANCES.DELETE_COMPLIANCE, id).subscribe(response => {
                        this.commonService.showAlertMessage('Compliances Deleted Successfully');
                        this.getAllCompliancesData();
                    },error => {});
            }
        });
    }
    comApplyFilter(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase();
        this.complianceDataSource.filter = filterValue;
    }
   
    statusList()
    {  
        this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_STATUS_ITEM + 'COMPLIANCES_STATUS').subscribe((data) => {
            this.statusTypeData = data;
    }
           );

   }
    }
