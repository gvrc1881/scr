import { OnInit, Component, ViewChild } from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import { ComplianceModel } from 'src/app/models/foot-patrolling-inspection.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { ComplianceDocumentComponent } from '../../compliance-document-dialog/compliance-document-dialog.component';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';

@Component({
    selector: 'compliances',
    templateUrl: './compliances.component.html',
})
export class CompliancesComponent implements OnInit{
    loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
    addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    addComplianceItem:boolean;
    title: string = "Save";
    selectedFiles: File[] = [];
    filesExists: boolean = false;
    observationCategoryData:any;
    ComplianceStatus:any;
    statusTypeData:any;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
   complianceDocumentDialogRef: MatDialogRef<ComplianceDocumentComponent>;
   complianceFormGroup: FormGroup;
   complianceList : any;
   dataSource: MatTableDataSource<ComplianceModel>;
   complianceDisplayColumns = ['sno' ,'status','action' , 'complianceBy' ,  'compliedDateTime' ,'attachment', 'id'] ;
   editComplianceResponse: any;
   complianceResponse: any;
   attachedImages:any;
   filterData;
   gridData=[];
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
        var permissionName = this.commonService.getPermissionNameByLoggedData("MAINTENANCE","COMPLIANCES") ;//p == 0 ? 'No Permission' : p[0].permissionName;
        console.log("COMPLIANCESpermissionName***"+permissionName);
        this.addPermission = this.commonService.getPermissionByType("Add", permissionName); 
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
        //Compliance Permissions
        this.statusList();
        this.getAllCompliancesData();
        this.complianceFormGroup = this.formBuilder.group({
            id: 0,
            'status':[null],
            'action':[null],
            'complianceBy': [null],
            'compliedDateTime' : [null],
            'attachment'  :[null]
        });
        this.filterData = {
            filterColumnNames: [
              { "Key": 'sno', "Value": " " },
              { "Key": 'status', "Value": " " },
              { "Key": 'action', "Value": " " },
              { "Key": 'complianceBy', "Value": " " },
              { "Key": 'compliedDateTime', "Value": "" },
              { "Key": 'attachment', "Value": " " },
            ],
            gridData: this.gridData,
            dataSource: this.dataSource,
            paginator: this.paginator,
            sort: this.sort
          };
       
    }
    
      public get c() {return this.complianceFormGroup.controls;}

   
    findAttachedFiles(commonId){
        this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.OBSERVATION.GET_OBSERVATION_CONTENT_ID + commonId)
        .subscribe((resp) => {
          this.attachedImages = resp;
        })
      }

    

    categoryList()
        {  
               this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.OBSERVATION_CATEGORIES.GET_OBS_CATEGORIES).subscribe((data) => {
                 this.observationCategoryData = data;
        }
               );

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
     
//Compliance CRUD Operations

getAllCompliancesData() {
    const compliances : ComplianceModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.COMPLIANCES.GET_COMPLIANCE).subscribe((data) => {
        this.complianceList = data;
        for (let i = 0; i < this.complianceList.length; i++) {
            this.complianceList[i].sno = i+1;
            this.complianceList[i].fromDate = this.datePipe.transform(this.complianceList[i].compliedDateTime, 'dd-MM-yyyy hh:mm:ss');
            compliances.push(this.complianceList[i]);              
        }
        this.filterData.gridData = compliances;
      this.dataSource = new MatTableDataSource(compliances);
      this.commonService.updateDataSource(this.dataSource, this.complianceDisplayColumns);
      this.filterData.dataSource = this.dataSource;
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }
NewComplianceItem () {
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
                'status':status,
                'action':action,
                'complianceBy': complianceBy,
                'compliedDateTime':compliedDateTime,
                "updatedBy": this.loggedUserData.id,
                attachment: this.editComplianceResponse.attachment,
            }
            let formdata: FormData = new FormData();
          for(var i=0;i<this.selectedFiles.length;i++){
              formdata.append('file', this.selectedFiles[i]);
          }
          formdata.append('id', updateCompliances.id.toString());
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
        this.filterData.dataSource.filter = filterValue;
    }
    updatePagination() {
        this.filterData.dataSource = this.filterData.dataSource;
        this.filterData.dataSource.paginator = this.paginator;
      }
    statusList()
    {  
        this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_STATUS_ITEM + 'COMPLIANCES_STATUS').subscribe((data) => {
            this.statusTypeData = data;
    }
           );

   }
    }
