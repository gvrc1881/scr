import { Component, OnInit, ViewChild, ElementRef, Inject } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { DriveChecklistModel } from 'src/app/models/drive.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DriveDailyProgressModel } from 'src/app/models/drive.model';
import { DriveModel } from 'src/app/models/drive.model';

@Component({
  selector: 'drive-daily-progress',
  templateUrl: './drive-daily-progress.component.html',
  styleUrls: ['./drive-daily-progress.component.scss']
})
export class DriveDailyProgressComponent implements OnInit {
        
    
    dailyProgressFormGroup: FormGroup;
    dailyProgressDate: any;
    depotType: any;
    dataSource: MatTableDataSource<DriveModel>;
    displayedColumns = ['sno', 'drive','alreadyDone','assetType','performedCount','ids', 'actions'];
    searchInputFormGroup: FormGroup;
    depotTypeList = [];
    drivesList: any;
    performedCount: any;
    resp: any;
    DDProgress: any;
    
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
    
    processSaveAction(row: any){
        var message = '';
        var failedMessage = ''; 
        let saveDriveDailyProgress = {
            id: 0,
            driveId: row.drive.id,
            performedCount: row.performedCount,
            performedDate: this.searchInputFormGroup.controls['fromDate'].value
        }
        message = 'Saved';
        failedMessage = "Saving";
        this.sendAndRequestService.requestForPOST(Constants.app_urls.PROGRESS_RECORD.SAVE_DRIVE_DAILY_PROGRESS_RECORD ,saveDriveDailyProgress, false).subscribe(response => {
            this.spinnerService.hide();
            this.resp = response;
            if (this.resp.code == Constants.CODES.SUCCESS) {
            this.commonService.showAlertMessage("Drive Daily Progress Data "+message+" Successfully");
            //this.router.navigate(['../'], { relativeTo: this.route });
            }else{
              this.commonService.showAlertMessage("Drive Daily Progress Data "+failedMessage+" Failed.");
            }
            
          });  
    }
    
    assetIdsDialog(){
        
        const dialogRef = this.dialog.open(AddAssetIdsDriveDialogComponent, {
          height: '600px',
          width: '80%', 
          data: {sno:30}
        });
        
    }
    
    
    
    findDepoTypeList() {
        this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FUNCTIONAL_LOCATION_TYPES)
          .subscribe((depoTypes) => {
            this.depotTypeList = depoTypes;
          })
      }
    
    getDriveDailyProgress() {
        const drivesData: DriveModel [] = [];
        this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE.GET_DIRIVES_BASED_ON_FROMDATE_AND_DEPOTTYPE
        +this.searchInputFormGroup.controls['fromDate'].value 
            ).subscribe((data) => {
                this.drivesList = data;
          for (let i = 0; i < this.drivesList.length; i++) {
            this.drivesList[i].sno = i + 1;
            this.drivesList[i].drive = this.drivesList[i];
             this.drivesList[i].performedCount;
              console.log('*** id ***'+this.drivesList[i].id);
              console.log('*** from date ***'+this.searchInputFormGroup.controls['fromDate'].value);
             // this.DDProgress = '';
              this.sendAndRequestService.requestForGET(Constants.app_urls.PROGRESS_RECORD.GET_DDPROGRESS_BASED_ON_DRIVE_FROM_DATE
              +this.drivesList[i].id+'/'+this.searchInputFormGroup.controls['fromDate'].value
                  ).subscribe((data) =>{
                      this.DDProgress = data;
                      if(this.DDProgress != null) {
                          this.drivesList[i].alreadyDone = this.DDProgress.performedCount;
                        console.log('data***'+this.DDProgress.performedCount);
                          }
                      });
          //  this.driveTargetList[i].driveId = this.driveTargetList[i].driveId['name'];
            drivesData.push(this.drivesList[i]);
          }
    
          this.dataSource = new MatTableDataSource(drivesData);
         // this.dataSource.paginator = this.paginator;
         // this.dataSource.sort = this.sort;
          this.spinnerService.hide(); 
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

@Component({
  selector: 'add-asset-ids-drive-dialog',
  templateUrl: 'add-asset-ids-drive-dialog.component.html',
})
export class AddAssetIdsDriveDialogComponent implements OnInit  {
        
    dailyProgressIdFormGroup: FormGroup;
    assetIdList: any;
    displayedColumns = ['sno', 'assetId', 'actions'];
    dataSource: any;

  constructor(
    public formBuilder: FormBuilder,
    public dialogRef: MatDialogRef<AddAssetIdsDriveDialogComponent>,
    @Inject(MAT_DIALOG_DATA) private data: any) {
      
    console.log('** data **'+JSON.stringify(data));
  }
    
    ngOnInit() {
        this.dailyProgressIdFormGroup = this.formBuilder.group({
            fromKilometer:[null],
            toKilometer: [null],
            assetId: [null]    
        })
        
    }
    
    addAssetIdsFormSubmit() {
        
    }
    
    
    

  onGoBack(): void {
    this.dialogRef.close();
  }

}