import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { DriveChecklistModel } from 'src/app/models/drive.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DriveDailyProgressModel } from 'src/app/models/drive.model';

@Component({
  selector: 'drive-daily-progress',
  templateUrl: './drive-daily-progress.component.html',
  styleUrls: []
})
export class DriveDailyProgressComponent implements OnInit {
        
    
    dailyProgressFormGroup: FormGroup;
    dailyProgressDate: any;
    depotType: any;
    dataSource: MatTableDataSource<DriveDailyProgressModel>;
    displayedColumns = ['sno', 'activityPlan', 'actionDone','performedCount', 'actions'];
    searchInputFormGroup: FormGroup;
    depotTypeList = [];
    
    constructor (
        private spinnerService: Ng4LoadingSpinnerService,
        private commonService: CommonService,
        private router: Router,
        private route: ActivatedRoute,
        public dialog: MatDialog,
        private sendAndRequestService:SendAndRequestService,
        private formBuilder: FormBuilder
        ){ }
    
    ngOnInit () {
        
        console.log('in ng oninit function');
        this.searchInputFormGroup = this.formBuilder.group({
            'fromDate': [null],
            'depotType' : [null]   
        });
        this.findDepoTypeList();
    }
    
    findDepoTypeList() {
        this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FUNCTIONAL_LOCATION_TYPES)
          .subscribe((depoTypes) => {
            this.depotTypeList = depoTypes;
          })
      }
    
    getDriveDailyProgress() {
        console.log(this.searchInputFormGroup.controls['fromDate'].value);
        console.log(this.searchInputFormGroup.controls['depotType'].value);
        this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE.GET_DIRIVES_BASED_ON_FROMDATE_AND_DEPOTTYPE
        +this.searchInputFormGroup.controls['fromDate'].value + '/'+this.searchInputFormGroup.controls['depotType'].value
            ).subscribe((data) => {
         /* this.driveTargetList = data;
          for (let i = 0; i < this.driveTargetList.length; i++) {
            this.driveTargetList[i].sno = i + 1;
            this.driveTargetList[i].driveId = this.driveTargetList[i].driveId['name'];
            driveTarget.push(this.driveTargetList[i]);
          }
    
          this.dataSource = new MatTableDataSource(driveTarget);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
          this.spinnerService.hide(); */
        }, error => {
          this.spinnerService.hide();
        });
    }
    
    createCheckListForm() {
        this.dailyProgressFormGroup = this.formBuilder.group({
          id: 0,
          'drive': [null, Validators.compose([Validators.required])],
          'activityType':[null],
         // 'measureActivityList': [null, Validators.compose([Validators.required]),this.duplicateDriveActivityList.bind(this)],
         // 'activityPositionId':[null,Validators.compose([Validators.required]),this.duplicateDrivePositionId.bind(this)],
          'displayOrder': [null],
          'lowerLimit': [null],
          'upperLimit': [null],
          'status': ['Yes']
        });
      }
    
    

}