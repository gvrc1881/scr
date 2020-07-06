import { Component, ViewChild, OnInit, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatPaginator, MatSort, MatTableDataSource, MatDialog, MatDialogRef } from '@angular/material';
import { ScheduleJobPayload } from '../../payloads/schedule-job.payload';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { FuseConfirmDialogComponent } from '../../components/confirm-dialog/confirm-dialog.component';
import { ScheduleJobModel } from 'src/app/models/schedule-job.model';
import { Constants } from 'src/app/common/constants';
import { CommonService } from 'src/app/common/common.service';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
@Component({
  selector: 'app-scheduler-settings',
  templateUrl: './scheduler-settings.component.html',
  styleUrls: ['./scheduler-settings.component.css']
})
export class SchedulerSettingsComponent implements OnInit {

  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  rolePermission: boolean = true;
  repositoryList: any = [];
  jobTypeList: any = [];
  timeIntervalList: any = [];
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  status: boolean;
  addScheduleJob: boolean = false;
  saveScheduleJob: boolean;
  cloneUpdateScheduleJob: boolean = true;
  scheduleJobData: any;
  scheduleJobFormGroup: FormGroup;
  scheduleJobErrors: any;
  scheduleJobResponse: any;
  editScheduleJobResponse: any;
  combination: boolean = false;
  portPattern = "^[0-9]{4}$";
  pattern = "[a-zA-Z][a-zA-Z ]*";
  ipPattern = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  title: string = "Add";
  scheduleJobDisplayedColumns = ['sno', 'jobId', 'repositoryCode', 'repositoryName', 'jobTypeName', 'timeInterval', 'id'];
  scheduleJobDataSource: MatTableDataSource<ScheduleJobModel>;
  @ViewChild(MatPaginator, { static: true }) scheduleJobPaginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
  
  constructor(
    private formBuilder: FormBuilder,
    public dialog: MatDialog,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private sendAndRequestService: SendAndRequestService
  ) {
    this.scheduleJobErrors = {
      jobTypeId: {},
      repositoryId: {},
      timeIntervalId: {}
    };
  }

  ngOnInit() {
  	
  	var permissionName = this.commonService.getPermissionNameByLoggedData("MASTERS","SCHEDULE") ;//p == 0 ? 'No Permission' : p[0].permissionName;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName); //getPermission("Add", );
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
  	 
    this.rolePermission = this.commonService.rolePermission();
   
   
    this.findAllScheduleJobData();
    this.scheduleJobFormGroup = this.formBuilder.group({
      id: 0,
      //'jobId': [null, Validators.compose([Validators.required])], //, this.duplicateDivisionCode.bind(this)
      'jobType': [null, Validators.compose([Validators.required])], //, this.duplicateDivisionName.bind(this)
      'repository': [null, Validators.compose([Validators.required])], //, this.duplicateDivisionName.bind(this)
      'timeInterval': [null, Validators.compose([Validators.required]), this.duplicateTimeinterval.bind(this)]
    });
    this.scheduleJobFormGroup.valueChanges.subscribe(() => {
      this.onFormValuesChanged();
    });
  }
  onFormValuesChanged() {
    for (const field in this.scheduleJobErrors) {
      if (!this.scheduleJobErrors.hasOwnProperty(field)) {
        continue;
      }
      this.scheduleJobErrors[field] = {};
      const control = this.scheduleJobFormGroup.get(field);
      if (control && control.dirty && !control.valid) {
        this.scheduleJobErrors[field] = control.errors;
      }
    }
  }
  
  duplicateTimeinterval() {
    const q = new Promise((resolve, reject) => {
      let divisionName = this.scheduleJobFormGroup.controls['repository'].value;
      let timeinterval = this.scheduleJobFormGroup.controls['timeInterval'].value;
      let jobType = this.scheduleJobFormGroup.controls['jobType'].value;
       var filteredArray = !!this.scheduleJobData && this.scheduleJobData.filter(function (division) {
        return division.repository.repositoryId == divisionName && 
                division.jobType.jobTypeId == jobType && 
                division.interval.timeIntervalId == timeinterval;
      });
      if (filteredArray.length !== 0) {
        resolve({ 'duplicateTimeinterval': true });
      } else {
        resolve(null);
      }
      });
    
    return q;
  }

  duplicateDivisionName() {
    const q = new Promise((resolve, reject) => {
      if(this.title == Constants.EVENTS.UPDATE){
        resolve(null);
      }else{
      let divisionName = this.scheduleJobFormGroup.controls['repository'].value;
       var filteredArray = !!this.scheduleJobData && this.scheduleJobData.filter(function (division) {
        return division.repository.repositoryId == divisionName;
      });
      if (filteredArray.length !== 0) {
        resolve({ 'duplicateDivisionName': true });
      } else {
        resolve(null);
      }
      }
    });
    
    return q;
  }
  

  onAddScheduleJobFormSubmit() {
    let jobTypeId: number = this.scheduleJobFormGroup.value.jobType
    let repositoryId: number = this.scheduleJobFormGroup.value.repository;
    let timeIntervalId: number = this.scheduleJobFormGroup.value.timeInterval;
    if (!this.scheduleJobFormGroup.valid) {
      return;
    }
    if (!!this.editScheduleJobResponse && jobTypeId == this.editScheduleJobResponse.jobType.jobTypeId &&
      repositoryId == this.editScheduleJobResponse.repository.repositoryId &&
      timeIntervalId == this.editScheduleJobResponse.timeInterval.timeIntervalId) {
      this.combination = true;
      setTimeout(() => {
        this.combination = false;
      }, 3000);
      return false;
    }
    if (this.title == Constants.EVENTS.ADD || this.title == Constants.EVENTS.SAVE) {
      this.spinnerService.show();
      ScheduleJobPayload.ADD_PAYLOAD.createdBy = this.loggedUserData.id;
      ScheduleJobPayload.ADD_PAYLOAD.modifiedBy = this.loggedUserData.id;
      ScheduleJobPayload.ADD_PAYLOAD.jobTypeId = jobTypeId;
      ScheduleJobPayload.ADD_PAYLOAD.repositoryId = repositoryId;
      ScheduleJobPayload.ADD_PAYLOAD.timeIntervalId = timeIntervalId;      
      this.addScheduleJob = false;
      this.sendAndRequestService.requestForPOST(Constants.app_urls.MASTERS.SCHEDULER_SETTINGS.SAVE, ScheduleJobPayload.ADD_PAYLOAD, false).subscribe((response) => {        
        this.scheduleJobResponse = response;
        if (!!this.scheduleJobResponse && this.scheduleJobResponse.code == 200) {
          this.commonService.showAlertMessage(this.scheduleJobResponse.message);
          this.saveScheduleJob = false;
          this.findAllScheduleJobData();
          this.scheduleJobFormGroup.reset();
          this.addScheduleJob = false;
        } else {
          this.commonService.showAlertMessage("ScheduleJob Addition Failed");
        }
        this.spinnerService.hide();
      },
        error => error => {
          this.scheduleJobErrors = error
          console.log(' >>> ERROR ' + error);
          this.commonService.showAlertMessage(this.scheduleJobErrors);
          this.spinnerService.hide();
        })
    }
    else if (this.title == Constants.EVENTS.UPDATE) {
      this.spinnerService.show();
      this.saveScheduleJob = false;
      let jobId: number = this.editScheduleJobResponse.jobId;
      ScheduleJobPayload.UPDATE_PAYLOAD.modifiedBy = this.loggedUserData.id;
      ScheduleJobPayload.UPDATE_PAYLOAD.jobTypeId = jobTypeId;
      ScheduleJobPayload.UPDATE_PAYLOAD.repositoryId = repositoryId;
      ScheduleJobPayload.UPDATE_PAYLOAD.timeIntervalId = timeIntervalId;
      ScheduleJobPayload.UPDATE_PAYLOAD.jobId = jobId;
      


      this.sendAndRequestService.requestForPUT(Constants.app_urls.MASTERS.SCHEDULER_SETTINGS.UPDATE,ScheduleJobPayload.UPDATE_PAYLOAD, false).subscribe((response) => {       
        this.scheduleJobResponse = response;
        if (!!this.scheduleJobResponse && this.scheduleJobResponse.code == 200) {
          this.commonService.showAlertMessage(this.scheduleJobResponse.message);
          this.saveScheduleJob = false;
          this.findAllScheduleJobData();
          this.scheduleJobFormGroup.reset();
          this.addScheduleJob = false;
        } else {
          this.commonService.showAlertMessage("ScheduleJob Updating Failed");
        }
        this.spinnerService.hide();
      },
        error => error => {
          this.scheduleJobErrors = error
          console.log(' >>> ERROR ' + error);
          this.commonService.showAlertMessage("ScheduleJob Updating Failed");
          this.spinnerService.hide();
        })
      
    }


  }

  deleteScheduleJob(id) {
    this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
      disableClose: false
    });
    this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete the selected division?';
    this.confirmDialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.spinnerService.show();
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.MASTERS.SCHEDULER_SETTINGS.DELETE, id)
          .subscribe((response) => {
            this.scheduleJobResponse = response;
            if (!!this.scheduleJobResponse && this.scheduleJobResponse.code == 200) {
              this.commonService.showAlertMessage(this.scheduleJobResponse.message);
              this.saveScheduleJob = false;
              this.findAllScheduleJobData();
              this.scheduleJobFormGroup.reset();
              this.addScheduleJob = false;
            } else {
              this.commonService.showAlertMessage("ScheduleJob Deletion Failed");
            }
          }, error => {
            this.scheduleJobErrors = error
            console.log(' >>> ERROR ' + error);
            this.commonService.showAlertMessage("ScheduleJob Deletion Failed");
            this.spinnerService.hide();
          });
      }
      this.confirmDialogRef = null;
    });


    this.spinnerService.hide();

  }

  findAllScheduleJobData() {
    this.spinnerService.show();
    const scheduleJobData: ScheduleJobModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.SCHEDULER_SETTINGS.GET_SCHEDULER_JOBS).subscribe((data) => {
      this.scheduleJobData = data;
      for (let i = 0; i < this.scheduleJobData.length; i++) {
        if (!this.rolePermission) {
          if (this.scheduleJobData[i].repository.repositoryCode == this.loggedUserData.divisionCode) {
            this.scheduleJobData[i].sno = i + 1;
            this.scheduleJobData[i].repositoryName = this.scheduleJobData[i].repository.repositoryName;
            this.scheduleJobData[i].jobTypeName = this.scheduleJobData[i].jobType.jobTypeName
            this.scheduleJobData[i].repositoryCode = this.scheduleJobData[i].repository.repositoryCode;
            this.scheduleJobData[i].interval = this.scheduleJobData[i].timeInterval;
            this.scheduleJobData[i].timeInterval = this.scheduleJobData[i].timeInterval != null ? this.scheduleJobData[i].timeInterval.timeInterval : 0;           
            scheduleJobData.push(this.scheduleJobData[i]);
          }else if(this.loggedUserData.divisionCode == 'All'){
            this.scheduleJobData[i].sno = i + 1;
            this.scheduleJobData[i].repositoryName = this.scheduleJobData[i].repository.repositoryName;
            this.scheduleJobData[i].jobTypeName = this.scheduleJobData[i].jobType.jobTypeName
            this.scheduleJobData[i].repositoryCode = this.scheduleJobData[i].repository.repositoryCode;
            this.scheduleJobData[i].interval = this.scheduleJobData[i].timeInterval;
            this.scheduleJobData[i].timeInterval = this.scheduleJobData[i].timeInterval != null ? this.scheduleJobData[i].timeInterval.timeInterval : 0;            
            scheduleJobData.push(this.scheduleJobData[i]);
          }
        } else {
          if (this.scheduleJobData[i].repository.repositoryCode == this.loggedUserData.divisionCode) {
          this.scheduleJobData[i].sno = i + 1;
          this.scheduleJobData[i].repositoryName = this.scheduleJobData[i].repository.repositoryName;
          this.scheduleJobData[i].jobTypeName = this.scheduleJobData[i].jobType.jobTypeName
          this.scheduleJobData[i].repositoryCode = this.scheduleJobData[i].repository.repositoryCode;
          this.scheduleJobData[i].interval = this.scheduleJobData[i].timeInterval;
          this.scheduleJobData[i].timeInterval = this.scheduleJobData[i].timeInterval != null ? this.scheduleJobData[i].timeInterval.timeInterval : 0;          
          scheduleJobData.push(this.scheduleJobData[i]);
          }else if(this.loggedUserData.divisionCode == 'All'){
            this.scheduleJobData[i].sno = i + 1;
            this.scheduleJobData[i].repositoryName = this.scheduleJobData[i].repository.repositoryName;
            this.scheduleJobData[i].jobTypeName = this.scheduleJobData[i].jobType.jobTypeName
            this.scheduleJobData[i].repositoryCode = this.scheduleJobData[i].repository.repositoryCode;
            this.scheduleJobData[i].interval = this.scheduleJobData[i].timeInterval;
            this.scheduleJobData[i].timeInterval = this.scheduleJobData[i].timeInterval != null ? this.scheduleJobData[i].timeInterval.timeInterval : 0;            
            scheduleJobData.push(this.scheduleJobData[i]);
          }
        }

      }     
      this.scheduleJobDataSource = new MatTableDataSource(scheduleJobData);
      this.scheduleJobDataSource.paginator = this.scheduleJobPaginator;
      this.scheduleJobDataSource.sort = this.sort;
      this.spinnerService.hide();
    });
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.scheduleJobDataSource.filter = filterValue;
  }

  scheduleJobEditAction(id: number) {
    this.addScheduleJob = true;
    this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.SCHEDULER_SETTINGS.EDIT + id).subscribe((response) => {
      this.cloneUpdateScheduleJob = false;
      this.saveScheduleJob = false;
      this.scheduleJobResponse = response;
      this.editScheduleJobResponse = response;      
      this.scheduleJobFormGroup.patchValue({
        repository: this.scheduleJobResponse.repository.repositoryId,
        jobType: this.scheduleJobResponse.jobType.jobTypeId,
        timeInterval: this.scheduleJobResponse.timeInterval != null ? this.scheduleJobResponse.timeInterval.timeIntervalId : 0
      });
      this.spinnerService.hide();
    }, error =>{ this.spinnerService.hide(); this.scheduleJobErrors = error});
    this.commonService.scrollTop("forms");
    this.updateFields();
    //this.spinnerService.hide();
    this.scheduleJobFormGroup.valueChanges.subscribe(() => {
      this.onFormValuesChanged();
    });
  }

  editScheduleJobById(id) {
    this.spinnerService.show();
    this.addScheduleJob = true;
    this.cloneUpdateScheduleJob = false;
    this.scheduleJobEditAction(id);
    this.title = "Update";   
  }

  updateFields() {
    //this.spinnerService.show();
    this.addScheduleJob = true;
    this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.SCHEDULER_SETTINGS.GET_SCHEDULER_JOBS_LIST).subscribe((response) => {
      this.cloneUpdateScheduleJob = false;
      this.saveScheduleJob = false;
      this.scheduleJobResponse = response;     
      this.repositoryList = this.scheduleJobResponse.repositoryList;
      this.jobTypeList = this.scheduleJobResponse.jobTypeList;
      this.timeIntervalList = this.scheduleJobResponse.timeIntervalList;    
      //this.spinnerService.hide(); 
    }, error => {this.scheduleJobErrors = error; this.spinnerService.hide();});
    this.commonService.scrollTop("forms");
  }

  AddScheduleJob() {
    this.addScheduleJob = true;
    this.title = 'Add';
    this.updateFields();
  }
  onGoBack() {
    this.scheduleJobFormGroup.reset();
    this.saveScheduleJob = false;
    this.cloneUpdateScheduleJob = true;
    this.addScheduleJob = false;
    this.title = 'Save';
  }
}