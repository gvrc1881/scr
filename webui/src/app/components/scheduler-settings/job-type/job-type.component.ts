import { Component, ViewChild, OnInit, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatPaginator, MatSort, MatTableDataSource, MatDialog, MatDialogRef } from '@angular/material';

import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { FuseConfirmDialogComponent } from '../../../components/confirm-dialog/confirm-dialog.component';


import { Constants } from 'src/app/common/constants';
import { CommonService } from 'src/app/common/common.service';
import { JobTypeModel } from 'src/app/models/job-type.model';
import { JobTypePayload } from 'src/app/payloads/job-type.payload';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
@Component({
  selector: 'app-job-type',
  templateUrl: './job-type.component.html',
  styleUrls: ['./job-type.component.css']
})
export class JobTypeComponent implements OnInit {
  
  rolePermission:boolean=true;
  editPermission:boolean=true;
  addPermission:boolean=true;
  deletePermission:boolean=true;
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  status: boolean;
  addJobType: boolean = false;
  saveJobType: boolean;
  cloneUpdateJobType: boolean = true;
  jobTypeData: any;
  jobTypeFormGroup: FormGroup;
  jobTypeErrors: any;
  jobTypeResponse: any;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  jobTypeNames=[{ id: 1, jobTypeName: 'Staging To Zonal' }]; 
  loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
  title: string = "Add";
  jobTypeDisplayedColumns = ['sno', 'jobTypeName', 'id'];
  jobTypeDataSource: MatTableDataSource<JobTypeModel>;
  @ViewChild(MatPaginator, { static: true }) jobTypePaginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
  constructor(
    private formBuilder: FormBuilder,
    private sendAndRequestService : SendAndRequestService,
    public dialog: MatDialog,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
  ) {
    this.jobTypeErrors = {
      jobTypeName: {},
    };
    
  }

  ngOnInit() {
    this.rolePermission = this.commonService.rolePermission();
    var permissionName = this.commonService.getPermissionNameByLoggedData("MASTERS","JOB TYPE") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
    this.spinnerService.show();
    this.findAllJobTypeData();
    this.jobTypeFormGroup = this.formBuilder.group({
      id: 0,
      'jobTypeName': [null, Validators.compose([Validators.required]), this.duplicateJobTypeName.bind(this)],
    });
    this.jobTypeFormGroup.valueChanges.subscribe(() => {
      this.onFormValuesChanged();
    });
  }
  onFormValuesChanged() {
    for (const field in this.jobTypeErrors) {
      if (!this.jobTypeErrors.hasOwnProperty(field)) {
        continue;
      }
      this.jobTypeErrors[field] = {};
      const control = this.jobTypeFormGroup.get(field);
      if (control && control.dirty && !control.valid) {
        this.jobTypeErrors[field] = control.errors;
      }
    }
  }

  duplicateJobTypeName() {
    const q = new Promise((resolve, reject) => {
      let jobTypeName: string = this.jobTypeFormGroup.controls['jobTypeName'].value;      
        var filteredArray = !!this.jobTypeData && this.jobTypeData.filter(function(jobType){          
          return jobType.jobTypeName == jobTypeName;
        });    
        if(filteredArray.length !== 0) {
          resolve({ 'duplicateJobTypeName': true });
        } else {
          resolve(null);
        }
    });
    return q;
  }
  

  onAddJobTypeFormSubmit() {

    let jobTypeName: string = this.jobTypeFormGroup.value.jobTypeName;
    var filteredArray = !!this.jobTypeData && this.jobTypeData.filter(function(jobType){   
      return jobType.jobTypeName == jobTypeName;
    });    
    if(filteredArray.length !== 0){
      this.commonService.showAlertMessage(jobTypeName+' job type already selected.');
      return
    }
    if (!this.jobTypeFormGroup.valid) {
      return;
    }
   
    if (this.title == Constants.EVENTS.ADD || this.title == Constants.EVENTS.SAVE) {
      JobTypePayload.ADD_PAYLOAD.createdBy = this.loggedUserData.id;
      JobTypePayload.ADD_PAYLOAD.modifiedBy = this.loggedUserData.id;
      JobTypePayload.ADD_PAYLOAD.jobTypeName = jobTypeName;
      this.spinnerService.show();
      this.addJobType = false;
      this.sendAndRequestService.requestForPOST(Constants.app_urls.MASTERS.SCHEDULER_SETTINGS.JOB_TYPE.SAVE_JOB_TYE,JobTypePayload.ADD_PAYLOAD, false).subscribe((response) => {
        this.spinnerService.hide();
        this.jobTypeResponse = response;
        if (!!this.jobTypeResponse && this.jobTypeResponse.code == 200) {
          this.commonService.showAlertMessage(this.jobTypeResponse.message);
          this.saveJobType = false;
          this.findAllJobTypeData();
          this.jobTypeFormGroup.reset();
          this.addJobType = false;
        } else {
          this.commonService.showAlertMessage("JobType Addition Failed");
        }
      },
        error => error => {
          this.jobTypeErrors = error
          console.log(' >>> ERROR ' + error);
          this.commonService.showAlertMessage(this.jobTypeErrors);
          this.spinnerService.hide();
        })
    }
    else if (this.title == Constants.EVENTS.UPDATE) {
      this.spinnerService.show();
      this.saveJobType = false;
      let jobTypeId: number = this.jobTypeResponse.jobTypeId;
      JobTypePayload.UPDATE_PAYLOAD.jobTypeId = jobTypeId;
      JobTypePayload.UPDATE_PAYLOAD.modifiedBy = this.loggedUserData.id;
      JobTypePayload.UPDATE_PAYLOAD.jobTypeName = jobTypeName;
      this.sendAndRequestService.requestForPOST(Constants.app_urls.MASTERS.SCHEDULER_SETTINGS.JOB_TYPE.UPDATE_JOB_TYPE,JobTypePayload.UPDATE_PAYLOAD, false).subscribe((response) => {
        this.spinnerService.hide();
        this.jobTypeResponse = response;
        if (!!this.jobTypeResponse && this.jobTypeResponse.code == 200) {
          this.commonService.showAlertMessage(this.jobTypeResponse.message);
          this.saveJobType = false;
          this.findAllJobTypeData();
          this.jobTypeFormGroup.reset();
          this.addJobType = false;
        } else {
          this.commonService.showAlertMessage("JobType Updating Failed");
        }
      },
        error => error => {
          this.jobTypeErrors = error
          console.log(' >>> ERROR ' + error);
          this.commonService.showAlertMessage("JobType Updating Failed");
          this.spinnerService.hide();
        })
    }


  }

  deleteJobType(id) {
    this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
      disableClose: false
    });
    this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete the selected job type?';
    this.confirmDialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.spinnerService.show();
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.MASTERS.SCHEDULER_SETTINGS.JOB_TYPE.DELETE_JOB_TYPE,id)
          .subscribe((response) => {
            this.jobTypeResponse = response;
            if (!!this.jobTypeResponse && this.jobTypeResponse.code == 200) {
              this.commonService.showAlertMessage(this.jobTypeResponse.message);
              this.saveJobType = false;
              this.findAllJobTypeData();
              this.jobTypeFormGroup.reset();
              this.addJobType = false;
            } else {
              this.commonService.showAlertMessage("JobType Name Deletion Failed");
            }
          }, error => {
            this.jobTypeErrors = error
            console.log(' >>> ERROR ' + error);
            this.commonService.showAlertMessage("JobType Name Deletion Failed");
            this.spinnerService.hide();
          });
      }
      this.confirmDialogRef = null;
    });


    this.spinnerService.hide();

  }

  findAllJobTypeData() {
    const jobTypeData: JobTypeModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.SCHEDULER_SETTINGS.JOB_TYPE.GET_JOB_TYPE).subscribe((data) => {
      this.jobTypeData = data;
      for (let i = 0; i < this.jobTypeData.length; i++) {
        this.jobTypeData[i].sno = i + 1;
        jobTypeData.push(this.jobTypeData[i]);
      }
      this.jobTypeDataSource = new MatTableDataSource(jobTypeData);
      this.jobTypeDataSource.paginator = this.jobTypePaginator;
      this.jobTypeDataSource.sort = this.sort;
      this.spinnerService.hide();
    });
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.jobTypeDataSource.filter = filterValue;
  }

  jobTypeEditAction(id: number) {
    this.addJobType = true;
    this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.SCHEDULER_SETTINGS.JOB_TYPE.GET_JOB_TYPE_ID + id).subscribe((response) => {
      this.cloneUpdateJobType = false;
      this.saveJobType = false;  
      this.jobTypeResponse = response;
      this.jobTypeFormGroup.patchValue({
        jobTypeName: this.jobTypeResponse.jobTypeName
      });
    }, error => this.jobTypeErrors = error);
    this.commonService.scrollTop("forms");
  }

  editJobTypeById(id) {
    this.spinnerService.show();
    this.addJobType = true;
    this.cloneUpdateJobType = false;
    this.jobTypeEditAction(id);
    this.title = "Update";
    this.spinnerService.hide();
  }

  addJobTypeName() {
    this.addJobType = true;
    this.title = 'Add';
  }
  onGoBack() {
    this.jobTypeFormGroup.reset();
    this.saveJobType = false;
    this.cloneUpdateJobType = true;
    this.addJobType = false;
    this.title = 'Save';
  }

}
