import { OnInit, Component, ViewChild } from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import { EnergyMeterModel } from 'src/app/models/energy-meter.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog, DateAdapter, MAT_DATE_FORMATS } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { DatePipe } from '@angular/common';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';

@Component({
    selector: 'ash-daily-progress',
    templateUrl: './ash-daily-progress.component.html',
    styleUrls: ['./ash-daily-progress.component.css'],
    providers: [
    {
        provide: DateAdapter, useClass: AppDateAdapter
    },
    {
        provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
    }
    ]
})
export class AshDailyProgressComponent implements OnInit{
  
  pagination = Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
    addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    displayedColumns = ['sno', 'schedule', 'progress'];
    dataSource: MatTableDataSource<any>;
    ashDailyProgressData: any;
    ashDailyProgressList = [];
    inputFormGroup: FormGroup;
    depotsList: any = JSON.parse(localStorage.getItem('depotData'));
    maxDate = new Date();
    enableSave: boolean;
    resp: any;
    

    constructor(
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private dialog: MatDialog,
        private spinnerService: Ng4LoadingSpinnerService,
        private sendAndRequestService:SendAndRequestService,
        private datePipe: DatePipe

    ){

    }

    ngOnInit () {
         this.inputFormGroup = this.formBuilder.group({
            'fromDate': [null],
            'depot' : [null],
        });
        var permissionName = this.commonService.getPermissionNameByLoggedData("","") ;
        this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
        this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
        this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
        
    }
    
    saveAction(){
        this.sendAndRequestService.requestForPOST(Constants.app_urls.ASH.ASH_DAILY_PROGRESS.SAVE_ASH_DAILY_PROGRESS,this.ashDailyProgressList,false).subscribe((response) => {
            this.spinnerService.hide();
            this.resp = response;
            this.enableSave = false;
            if(this.resp.code == 200 && !!this.resp) {
                this.commonService.showAlertMessage("Progress Data Updated Successfully");    
            }else
                this.commonService.showAlertMessage("Progress Data Updating Failed");
            
        });
    }
    
    
    getAshDailyProgress() {
        this.enableSave = false;
        this.ashDailyProgressList = [];
        this.dataSource = new MatTableDataSource(this.ashDailyProgressList);
        this.sendAndRequestService.requestForGET(Constants.app_urls.ASH.ASH_DAILY_PROGRESS.GET_ASH_DAILY_PROGRESS
        +this.inputFormGroup.controls['fromDate'].value +'/' + this.inputFormGroup.controls['depot'].value 
            ).subscribe((data) => {
                this.ashDailyProgressData = data;
             for (let i = 0; i < this.ashDailyProgressData.length; i++) {
                this.ashDailyProgressData[i].sno = i + 1;
                this.ashDailyProgressList.push(this.ashDailyProgressData[i]);
             }
              this.enableSave = true;
              this.dataSource = new MatTableDataSource(this.ashDailyProgressList);
              this.spinnerService.hide();
          this.spinnerService.hide(); 
        }, error => {
          this.spinnerService.hide();
        });
    }
   
    
   
    
 }   