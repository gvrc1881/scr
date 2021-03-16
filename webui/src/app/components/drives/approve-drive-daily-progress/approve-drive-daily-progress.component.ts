import { Component, OnInit, ViewChild, ElementRef, Inject } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog, MAT_DIALOG_DATA, DateAdapter, MAT_DATE_FORMATS } from '@angular/material';
import { DriveChecklistModel } from 'src/app/models/drive.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ApproveDriveDailyProgressModel } from 'src/app/models/drive.model';
import { DriveModel } from 'src/app/models/drive.model';
import { DriveProgressIdModel } from 'src/app/models/drive.model';
import { DatePipe } from '@angular/common';
import { ViewApproveDriveDailyProgressComponent } from './view-approve-drive-daily-progress/view-approve-drive-daily-progress.component';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';



@Component({
  selector: 'approve-drive-daily-progress',
  templateUrl: './approve-drive-daily-progress.component.html',
  styleUrls: ['./approve-drive-daily-progress.component.scss'],
  providers: [
    {
        provide: DateAdapter, useClass: AppDateAdapter
    },
    {
        provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
    }
    ]
})
export class ApproveDriveDailyProgressComponent implements OnInit {
      
  pagination = Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
    dailyProgressFormGroup: FormGroup;
    dailyProgressDate: any;
    depotType: any;
    dataSource: MatTableDataSource<DriveModel>;
    searchInputFormGroup: FormGroup;
    depotTypeList = [];
    selectedItems = [];
    drivesList: any;
    performedCount: any;
    resp: any;
    DDProgress: any;
    depotsList: any = JSON.parse(sessionStorage.getItem('depotData'));
    facilityId: any;
    loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
    driveDailyProgressDialogRef:MatDialogRef<ViewApproveDriveDailyProgressComponent>;
    requestType: string;
    titleName: any;
    breadCrumb: any;
    maxDate = new Date();
    displayedColumns = ['sno','depot', 'drive','description','performedCount','check'];
    enableSave: boolean;
    checked: boolean;
    
    constructor (
        private spinnerService: Ng4LoadingSpinnerService,
        private commonService: CommonService,
        private router: Router,
        private route: ActivatedRoute,
        public dialog: MatDialog,
        private datePipe: DatePipe,
        private sendAndRequestService:SendAndRequestService,
        private formBuilder: FormBuilder
        ){ }
    
    ngOnInit () {
        
       // console.log('in ng oninit function'+JSON.stringify(this.depotTypeList));
       /*
        if(this.router.url == "/asset-schedule-progress") {
            this.requestType = "Schedule Progress";
            this.titleName = "Asset Schedule Progress Information";
            this.FiledLabels.DRIVE  = "Asset type & Sch Code"
            this.FiledLabels.PERFORMED_COUNT = "Schedule Performed";
            this.breadCrumb = "Asset Schedule Progress";
            this.FiledLabels.FROM_DATE = "Sch Date";
            this.displayedColumns = ['sno', 'drive','description','alreadyDone','performedCount','ids', 'actions'];  
        }else {
            this.requestType = "Daily Progress";
            this.titleName = "Drive Daily Progress Information";
            this.breadCrumb = "Drive Daily Progress";
            this.FiledLabels.DRIVE = "Drive";
            this.FiledLabels.PERFORMED_COUNT = "Performed Count"
            this.FiledLabels.FROM_DATE = "Date";
            this.displayedColumns = ['sno','check', 'drive','description','performedCount'];    
        }
        */
        this.searchInputFormGroup = this.formBuilder.group({
            'fromDate': [null],
            'facilityId': [null]
        });
    }
    
    saveAction() {
        this.sendAndRequestService.requestForPOST(Constants.app_urls.PROGRESS_RECORD.UPDATE_DRIVE_DAILY_PROGRESS_RECORD ,this.selectedItems, false).subscribe(response => {
            this.spinnerService.hide();
            this.resp = response;
            if(this.resp.code == 200 && !!this.resp ) {
			    this.commonService.showAlertMessage("Drive Daily  Progress Data Saved Successfully");
                this.checked = false;
                this.getDriveDailyProgress();
                
            }else
                 this.commonService.showAlertMessage("Drive Daily Progress Data Saving Failed");
          });  
    }
    
    getDriveDailyProgress() {
        this.enableSave = false;
        this.checked = false;
        const drivesData: DriveModel [] = [];
        this.selectedItems = [];
        this.dataSource = new MatTableDataSource(drivesData);
        this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE.GET_NON_APPROVED_DIRIVES_BASED_ON_FROMDATE_AND_DEPOT
        +this.searchInputFormGroup.controls['fromDate'].value +'/' + this.searchInputFormGroup.controls['facilityId'].value +'/'+this.loggedUserData.username
            ).subscribe((data) => {
                this.drivesList = data;
                // console.log('*** data ****'+JSON.stringify(data));
                if(this.drivesList){
                  for (let i = 0; i < this.drivesList.length; i++) {
                    this.drivesList[i].sno = i + 1;
                    drivesData.push(this.drivesList[i]);
                  }
                  this.dataSource = new MatTableDataSource(drivesData);
                  this.spinnerService.show();   
                }
          
        }, error => {
          this.spinnerService.hide();
        });
    }
    
    onCheckboxChange(e, row) {
      if (e.target.checked) {
          row.approvedStatus = "Approve";
          row.approveBy = this.loggedUserData.username;
        this.selectedItems.push(row);
        this.enableSave = true;
      } else {
        this.selectedItems.splice(row.index, 1);
        if (this.selectedItems.length == 0) {
          this.enableSave = false;
        }
      }
    }
    
    reset(){
        this.searchInputFormGroup.reset();
    }
  
    selectAll(event) {
        for (var i = 0; i < this.drivesList.length; i++) {
            if (event.target.checked) {
                this.drivesList[i].checked = true;
                this.drivesList[i].approvedStatus = "Approve";
                this.drivesList[i].approveBy = this.loggedUserData.username;
                this.selectedItems.push(this.drivesList[i]);
                this.enableSave = true;   
            } else {
                this.drivesList[i].checked = false;
                this.selectedItems = [];
                this.enableSave = false;
            }

        }
    }

    
}
