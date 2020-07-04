import { Component, ViewChild, OnInit, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { MatPaginator, MatSort, MatTableDataSource, MatDialog, MatDialogRef } from '@angular/material';
import { DatePipe } from '@angular/common';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { FuseConfirmDialogComponent } from '../../../components/confirm-dialog/confirm-dialog.component';
import { Constants } from 'src/app/common/constants';
import { CommonService } from 'src/app/common/common.service';
import { TimeIntervalService } from 'src/app/services/time-interval.service';
import { TimeIntervalPayload } from 'src/app/payloads/time-interval.payload';
import { TimeIntervalModel } from 'src/app/models/time-interval.model';
import { formatDate } from '@angular/common';
import {MomentDateAdapter} from '@angular/material-moment-adapter';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from '@angular/material/core';

// Depending on whether rollup is used, moment needs to be imported differently.
// Since Moment.js doesn't have a default export, we normally need to import using the `* as`
// syntax. However, rollup creates a synthetic default module and we thus need to import it using
// the `default as` syntax.
import * as _moment from 'moment';
// tslint:disable-next-line:no-duplicate-imports
import {default as _rollupMoment} from 'moment';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';

const moment = _rollupMoment || _moment;
export const MY_FORMATS = {
  parse: {
    dateInput: 'YYYY-MM-DD',
  },
  display: {
    dateInput: 'YYYY-MM-DD',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM-YYYY',
  },
};

@Component({
  selector: 'app-time-interval',
  templateUrl: './time-interval.component.html',
  styleUrls: ['./time-interval.component.css'],
  providers: [
    // `MomentDateAdapter` can be automatically provided by importing `MomentDateModule` in your
    // application's root module. We provide it at the component level here, due to limitations of
    // our example generation script.
    {provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE]},

    {provide: MAT_DATE_FORMATS, useValue: MY_FORMATS},
  ],
})
export class TimeIntervalComponent implements OnInit {

  rolePermission:boolean=true;
  editPermission:boolean=true;
  addPermission:boolean=true;
  deletePermission:boolean=true;
  date = new FormControl(moment());  
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  status: boolean;
  addTimeInterval: boolean = false;
  saveTimeInterval: boolean;
  cloneUpdateTimeInterval: boolean = true;
  timeIntervalData: any;
  timeIntervalFormGroup: FormGroup;
  timeIntervalErrors: any;
  timeIntervalResponse: any;
  timeIntervals=[{ id: 1, interval: 'Daily' }, { id: 2, interval: 'Weekly' }, { id:3, interval:'Fortnightly'}, { id:4, interval:'Monthly'}, { id:5, interval:'Quarterly'}, { id:6, interval:'Half Yearly'},  { id:7, interval:'Yearly'}];
  pattern = "[a-zA-Z][a-zA-Z ]*";
  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  title: string = "Add";
  timeIntervalDisplayedColumns = ['sno', 'timeInterval', 'id'];
  timeIntervalDataSource: MatTableDataSource<TimeIntervalModel>;
  @ViewChild(MatPaginator, { static: true }) timeIntervalPaginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
  constructor(
    private datePipe: DatePipe,
    private formBuilder: FormBuilder,
    //private timeIntervalService: TimeIntervalService,
    private sendAndRequestService:SendAndRequestService,
    public dialog: MatDialog,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
  ) {
    this.timeIntervalErrors = {      
      startDate: {},
      
      timeInterval: {},
    };
  }

  ngOnInit() {
    this.rolePermission = this.commonService.rolePermission();
    var permissionName = this.commonService.getPermissionNameByLoggedData("MASTERS","TIME INTERVAL") ;
  		console.log("permissionName = "+permissionName);
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
    this.spinnerService.show();
    this.findAllTimeIntervalData();
   
    this.timeIntervalFormGroup = this.formBuilder.group({
      id: 0,
     // 'startDate': [null, Validators.compose([Validators.required])],
      'timeInterval': [null, Validators.compose([Validators.required]), this.duplicateTimeInterval.bind(this)],
    });;    
    this.timeIntervalFormGroup.valueChanges.subscribe(() => {
      this.onFormValuesChanged();
    });
  }
  onFormValuesChanged() {
    for (const field in this.timeIntervalErrors) {
      if (!this.timeIntervalErrors.hasOwnProperty(field)) {
        continue;
      }
      this.timeIntervalErrors[field] = {};
      const control = this.timeIntervalFormGroup.get(field);
      if (control && control.dirty && !control.valid) {
        this.timeIntervalErrors[field] = control.errors;
      }
    }
  }

  duplicateTimeInterval() {
    const q = new Promise((resolve, reject) => {
    //  console.log(this.timeIntervalFormGroup.controls['timeInterval'].value)
    let timeInterval = this.timeIntervalFormGroup.controls['timeInterval'].value;
      //this.timeIntervalService.existsTimeInterval().subscribe((duplicate) => {
        var filteredArray = !!this.timeIntervalData && this.timeIntervalData.filter(function(interval){          
          return interval.timeInterval == timeInterval;
        });        
        if(filteredArray.length !== 0){
        //if (duplicate) {
          resolve({ 'duplicateTimeIntervalName': true });
        } else {
          resolve(null);
        }
     // }, () => { resolve({ 'duplicateTimeIntervalName': true }); });
    });
    return q;
  }
  

  onAddTimeIntervalFormSubmit() {

    let timeInterval: string = this.timeIntervalFormGroup.value.timeInterval;
    //console.log("timeInterval: "+timeInterval);
   // console.log(JSON.stringify(this.timeIntervalData));
    var filteredArray = !!this.timeIntervalData && this.timeIntervalData.filter(function(interval){
     // console.log("hello")
      return interval.timeInterval == timeInterval;
    });
   // console.log(filteredArray);
    if(filteredArray.length !== 0){
      this.commonService.showAlertMessage(timeInterval+' time interval already selected.');
      return
    }
    //if(timeInterval == this.)
   // return;
   // let startDate: string = this.timeIntervalFormGroup.value.startDate;    
   // console.log('startDate: '+startDate);
  //  console.log(JSON.stringify(this.timeIntervalFormGroup.value))
    /* const format = 'yyyy-MM-dd';
    const myDate = startDate;
    const locale = 'en-US';
    const formattedDate = formatDate(myDate, format, locale); */
   // console.log("formattedDate: "+formattedDate)
  

    if (!this.timeIntervalFormGroup.valid) {
      return;
    }
    //console.log('title: ' + this.title);
    if (this.title == Constants.EVENTS.ADD || this.title == Constants.EVENTS.SAVE) {
      TimeIntervalPayload.ADD_PAYLOAD.createdBy = this.loggedUserData.id;
      TimeIntervalPayload.ADD_PAYLOAD.modifiedBy = this.loggedUserData.id;
      TimeIntervalPayload.ADD_PAYLOAD.timeInterval = timeInterval
      TimeIntervalPayload.ADD_PAYLOAD.startDate = '';
      //console.log('Add Payload =' + JSON.stringify(TimeIntervalPayload.ADD_PAYLOAD));
      this.spinnerService.show();
      this.addTimeInterval = false;
      this.sendAndRequestService.requestForPOST(Constants.app_urls.MASTERS.SCHEDULER_SETTINGS.TIME_INTERVAL.SAVE_TIME_INTERVAL,TimeIntervalPayload.ADD_PAYLOAD, false).subscribe((response) => {       
        this.spinnerService.hide();
        this.timeIntervalResponse = response;
        if (!!this.timeIntervalResponse && this.timeIntervalResponse.code == 200) {
          this.commonService.showAlertMessage(this.timeIntervalResponse.message);
          this.saveTimeInterval = false;
          this.findAllTimeIntervalData();
          this.timeIntervalFormGroup.reset();
          this.addTimeInterval = false;
        } else {
          this.commonService.showAlertMessage("TimeInterval Addition Failed");
        }
      },
        error => error => {
          this.timeIntervalErrors = error
          console.log(' >>> ERROR ' + error);
          this.commonService.showAlertMessage(this.timeIntervalErrors);
          this.spinnerService.hide();
        })
    }
    else if (this.title == Constants.EVENTS.UPDATE) {
      this.spinnerService.show();
      this.saveTimeInterval = false;    
      let timeIntervalId: number = this.timeIntervalResponse.timeIntervalId;
      TimeIntervalPayload.UPDATE_PAYLOAD.timeIntervalId = timeIntervalId;
      TimeIntervalPayload.UPDATE_PAYLOAD.modifiedBy = this.loggedUserData.id;
      TimeIntervalPayload.UPDATE_PAYLOAD.timeInterval = timeInterval;
      TimeIntervalPayload.UPDATE_PAYLOAD.startDate = '';      
      this.sendAndRequestService.requestForPUT(Constants.app_urls.MASTERS.SCHEDULER_SETTINGS.TIME_INTERVAL.UPDATE,TimeIntervalPayload.UPDATE_PAYLOAD, false).subscribe((response) => {      
        this.spinnerService.hide();
        this.timeIntervalResponse = response;
        if (!!this.timeIntervalResponse && this.timeIntervalResponse.code == 200) {
          this.commonService.showAlertMessage(this.timeIntervalResponse.message);
          this.saveTimeInterval = false;
          this.findAllTimeIntervalData();
          this.timeIntervalFormGroup.reset();
          this.addTimeInterval = false;
        } else {
          this.commonService.showAlertMessage("TimeInterval Updating Failed");
        }
      },
        error => error => {
          this.timeIntervalErrors = error
          console.log(' >>> ERROR ' + error);
          this.commonService.showAlertMessage("TimeInterval Updating Failed");
          this.spinnerService.hide();
        })
    }


  }

  deleteTimeInterval(id) {
    this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
      disableClose: false
    });
    this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete the selected job type?';
    this.confirmDialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.spinnerService.show();
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.MASTERS.SCHEDULER_SETTINGS.TIME_INTERVAL.DELETE_TIME_INTERVAL,id)
          .subscribe((response) => {
            this.timeIntervalResponse = response;
            if (!!this.timeIntervalResponse && this.timeIntervalResponse.code == 200) {
              this.commonService.showAlertMessage(this.timeIntervalResponse.message);
              this.saveTimeInterval = false;
              this.findAllTimeIntervalData();
              this.timeIntervalFormGroup.reset();
              this.addTimeInterval = false;
            } else {
              this.commonService.showAlertMessage("TimeInterval Name Deletion Failed");
            }
          }, error => {
            this.timeIntervalErrors = error
            console.log(' >>> ERROR ' + error);
            this.commonService.showAlertMessage("TimeInterval Name Deletion Failed");
            this.spinnerService.hide();
          });
      }
      this.confirmDialogRef = null;
    });


    this.spinnerService.hide();

  }

  findAllTimeIntervalData() {
    const timeIntervalData: TimeIntervalModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.SCHEDULER_SETTINGS.TIME_INTERVAL.GET_INTERVALS).subscribe((data) => {     
      this.timeIntervalData = data;
      for (let i = 0; i < this.timeIntervalData.length; i++) {
        this.timeIntervalData[i].sno = i + 1;        
        timeIntervalData.push(this.timeIntervalData[i]);
      }
      this.timeIntervalDataSource = new MatTableDataSource(timeIntervalData);
      this.timeIntervalDataSource.paginator = this.timeIntervalPaginator;
      this.timeIntervalDataSource.sort = this.sort;
      this.spinnerService.hide();
    });
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.timeIntervalDataSource.filter = filterValue;
  }

  timeIntervalEditAction(id: number) {
    this.addTimeInterval = true;
    this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.SCHEDULER_SETTINGS.TIME_INTERVAL.GET_TIME_INTERVAL_BY_ID + id).subscribe((response) => {
      this.cloneUpdateTimeInterval = false;
      this.saveTimeInterval = false;  
      this.timeIntervalResponse = response;
     // console.log("resp: " + JSON.stringify(response));
      const format = 'yyyy-MM-dd';
      const myDate = this.timeIntervalResponse.startDate;
      const locale = 'en-US';
      const formattedDate = formatDate(myDate, format, locale);
      this.timeIntervalFormGroup.patchValue({
        //startDate: formattedDate,
        timeInterval: this.timeIntervalResponse.timeInterval
      });
    }, error => this.timeIntervalErrors = error);
    this.commonService.scrollTop("forms");
  }

  editTimeIntervalById(id) {
    this.spinnerService.show();
    this.addTimeInterval = true;
    this.cloneUpdateTimeInterval = false;
    this.timeIntervalEditAction(id);
    this.title = "Update";
    this.spinnerService.hide();
  }

  addTimeIntervalName() {
    this.addTimeInterval = true;
    this.title = 'Add';
  }
  onGoBack() {
    this.timeIntervalFormGroup.reset();
    this.saveTimeInterval = false;
    this.cloneUpdateTimeInterval = true;
    this.addTimeInterval = false;
    this.title = 'Save';
  }


}
