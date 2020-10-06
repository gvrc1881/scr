import { OnInit, Component, ViewChild } from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import { ObservationModel} from 'src/app/models/foot-patrolling-inspection.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { InspectionDocumentComponent } from '../../inpection-document-dialog/inspection-document-dialog.component';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { Router } from '@angular/router';

@Component({
    selector: 'observations',
    templateUrl: './observations.component.html',
})
export class ObservationsComponent implements OnInit{
    loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
    addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    addObservation:boolean;
    addMap:boolean;
    title: string = "Save";
    toMinDate=new Date();
    currentDate = new Date();
    facilityData:any;
    selectedFiles: File[] = [];
    filesExists: boolean = false;
    observationCategoryData:any;
    statusTypeData:any;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    editfpInspectionItemResponse: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    inspectionDocumentDialogRef: MatDialogRef<InspectionDocumentComponent>;
   observationFormGroup: FormGroup;
   obsList : any;
   dataSource: MatTableDataSource<ObservationModel>;
   observationDisplayColumns = ['sno' ,'location','observationCategory' , 'observationItem' ,  'description' ,'actionRequired','attachment','id'] ;
   editObservationResponse: any;
   observationResponse: any;
   observationItemData:any;
   attachedImages:any;
   filterData;
   gridData = [];

    constructor(
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private dialog: MatDialog,
        private router: Router, 
        private spinnerService: Ng4LoadingSpinnerService,
        private sendAndRequestService:SendAndRequestService
    ){

    }

    ngOnInit () {
      this.categoryList();
        this.getAllObservationsData();
        var ObspermissionName = this.commonService.getPermissionNameByLoggedData("MAINTENANCE","OBSERVATIONS") ;//p == 0 ? 'No Permission' : p[0].permissionName;
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
        this.filterData = {
            filterColumnNames: [
              { "Key": 'sno', "Value": " " },
              { "Key": 'location', "Value": " " },
              { "Key": 'observationCategory', "Value": " " },
              { "Key": 'observationItem', "Value": " " },
              { "Key": 'actionRequired', "Value": "" },
              { "Key": 'description', "Value": " " },
            ],
            gridData: this.gridData,
            dataSource: this.dataSource,
            paginator: this.paginator,
            sort: this.sort
          };
    }
    

      public get o() { return this.observationFormGroup.controls; }

    

      addEvent($event) {
        this.toMinDate = new Date($event.value);
      }
   
   

   
     //Observations Related code for Add,delete,update,fetch records
     getAllObservationsData() {
            const observation : ObservationModel[] = [];
            this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.OBSERVATION.GET_OBSERVATION).subscribe((data) => {
                this.obsList = data;
                for (let i = 0; i < this.obsList.length; i++) {
                    this.obsList[i].sno = i+1;
                    observation.push(this.obsList[i]);              
                }
                this.filterData.gridData = observation;
                this.dataSource = new MatTableDataSource(observation);
                this.commonService.updateDataSource(this.dataSource, this.observationDisplayColumns);
                this.filterData.dataSource = this.dataSource;
                this.dataSource.paginator = this.paginator;
                this.dataSource.sort = this.sort;
                this.spinnerService.hide();
              }, error => {
                this.spinnerService.hide();
              });
            }
     observationItemSubmit () {
        let id: number = this.observationFormGroup.value.id;
        let location: string = this.observationFormGroup.value.location;
        let observationCategory: string = this.observationFormGroup.value.observationCategory;
        let observationItem: string = this.observationFormGroup.value.observationItem;
        let description: string = this.observationFormGroup.value.description;
        let actionRequired: string = this.observationFormGroup.value.actionRequired;
        let attachment:string = this.observationFormGroup.value.attachment;
        this.addObservation = false;
        
        if (this.title ==  Constants.EVENTS.SAVE) {
            var observationModel ={
                'inspectionSeqId':id,
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
      formdata.append('inspectionSeqId', observationModel.inspectionSeqId.toString());
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
                this.getAllObservationsData();
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
            'inspectionSeqId':id,
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
      formdata.append('inspectionSeqId', updateObservationModel.inspectionSeqId.toString());
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
                this.getAllObservationsData();
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
    NewObservationItem () {
        this.addObservation = true;
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
                        this.getAllObservationsData();
                    },error => {});
            }
        });
    }

    updatePagination() {
        this.filterData.dataSource = this.filterData.dataSource;
        this.filterData.dataSource.paginator = this.paginator;
      }
    observationApplyFilter(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase();
        this.filterData.dataSource.filter = filterValue;
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

    }
