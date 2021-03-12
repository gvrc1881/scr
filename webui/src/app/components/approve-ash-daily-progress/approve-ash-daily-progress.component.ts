import { OnInit, Component, ViewChild } from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog, DateAdapter, MAT_DATE_FORMATS } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { DatePipe } from '@angular/common';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';

@Component({
    selector: 'approve-ash-daily-progress',
    templateUrl: './approve-ash-daily-progress.component.html',
    styleUrls: ['./approve-ash-daily-progress.component.css'],
    providers: [
    {
        provide: DateAdapter, useClass: AppDateAdapter
    },
    {
        provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
    }
    ]
})
export class ApproveAshDailyProgressComponent implements OnInit{
  
  pagination = Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
    addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    displayedColumns = ['sno', 'check','facility','schedule', 'progress','monthlyProgress','cumProgress','monthlyTarget','cumTarget'];
    dataSource: MatTableDataSource<any>;
    ashDailyProgressData: any;
    ashDailyProgressList = [];
    inputFormGroup: FormGroup;
    depotsList: any = JSON.parse(sessionStorage.getItem('depotData'));
    userdata: any = JSON.parse(sessionStorage.getItem('userData')); 
    maxDate = new Date();
    enableSave: boolean;
    resp: any;
    selectedItems = [];
    

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
        this.sendAndRequestService.requestForPOST(Constants.app_urls.ASH.ASH_DAILY_PROGRESS.SAVE_APPROVED_ASH_DAILY_PROGRESS,this.selectedItems,false).subscribe((response) => {
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
        this.spinnerService.show();
        this.enableSave = false;
        this.ashDailyProgressList = [];
        this.selectedItems = [];
        this.dataSource = new MatTableDataSource(this.ashDailyProgressList);
        this.sendAndRequestService.requestForGET(Constants.app_urls.ASH.ASH_DAILY_PROGRESS.GET_ASH_DAILY_PROGRESS_BY_APPROVED_STATUS
        +this.inputFormGroup.controls['fromDate'].value +'/' + this.inputFormGroup.controls['depot'].value +'/'+this.userdata.username
            ).subscribe((data) => {
                this.ashDailyProgressData = data;
                console.log('*** data ***'+JSON.stringify(data));
             for (let i = 0; i < this.ashDailyProgressData.length; i++) {
                this.ashDailyProgressData[i].sno = i + 1;
                this.ashDailyProgressList.push(this.ashDailyProgressData[i]);
             }
              //this.enableSave = true;
              this.dataSource = new MatTableDataSource(this.ashDailyProgressList);
              this.spinnerService.hide();
          this.spinnerService.hide(); 
        }, error => {
          this.spinnerService.hide();
        });
    }
   
    onCheckboxChange(e, row) {
        if (e.target.checked) {
            row.approvedStatus = "Approve";
            row.approveBy = this.userdata.username;
          this.selectedItems.push(row);
          this.selectedItems.push(row.approvedBy = this.userdata.username);
          this.enableSave = true;
        } else {
          this.selectedItems.splice(row.index, 1);
          if (this.selectedItems.length == 0) {
            this.enableSave = false;
          }
        }
      }
    
    selectAll(event) {
        for (var i = 0; i < this.ashDailyProgressData.length; i++) {
                if(event.target.checked) {
                    this.ashDailyProgressData[i].checked = true;
                    this.ashDailyProgressData[i].approvedStatus = "Approve";
                    this.ashDailyProgressData[i].approveBy = this.userdata.username;
                    
                    this.selectedItems.push(this.ashDailyProgressData[i]);                     
                    this.enableSave = true;   
                }else {
                    this.ashDailyProgressData[i].checked = false;
                    this.selectedItems = [];
                    this.enableSave = false;
                }
                
            }
    }
   
    
 }   