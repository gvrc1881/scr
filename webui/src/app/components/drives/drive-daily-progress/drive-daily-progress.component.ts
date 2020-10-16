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
import { DriveProgressIdModel } from 'src/app/models/drive.model';

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
    displayedColumns = ['sno', 'drive','alreadyDone','assetType','performedCount','ids', 'actions']; //,'depot'
    searchInputFormGroup: FormGroup;
    depotTypeList = [];
    drivesList: any;
    performedCount: any;
    resp: any;
    DDProgress: any;
    depotsList: any = JSON.parse(localStorage.getItem('depotData'));
    facilityId: any;
    loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
    
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
        
       // console.log('in ng oninit function'+JSON.stringify(this.depotTypeList));
        this.searchInputFormGroup = this.formBuilder.group({
            'fromDate': [null],
            'depotType' : [null],
            'facilityId': [null]
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
            performedDate: this.searchInputFormGroup.controls['fromDate'].value,
            depot: this.searchInputFormGroup.controls['facilityId'].value,
            createdBy : this.loggedUserData.username,
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
    
    assetIdsDialog(row: any){
        const dialogRef = this.dialog.open(AddAssetIdsDriveDialogComponent, {
          height: '600px',
          width: '80%', 
          data: { driveId : row.drive.id,
                  performedCount: row.performedCount,
                  facilityId: this.searchInputFormGroup.controls['facilityId'].value,
                  performedDate: this.searchInputFormGroup.controls['fromDate'].value,
                  depotType: row.drive.depotType.code,
                  assetType : row.drive.assetType
                }
        });
        
        dialogRef.afterClosed().subscribe(result => {
            row.performedCount = 0;
            row.performedCount = result;
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
        this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE.GET_DIRIVES_BASED_ON_FROMDATE_AND_DEPOT
        +this.searchInputFormGroup.controls['fromDate'].value +'/' + this.searchInputFormGroup.controls['facilityId'].value
            ).subscribe((data) => {
                this.drivesList = data;
          for (let i = 0; i < this.drivesList.length; i++) {
            this.drivesList[i].sno = i + 1;
            this.drivesList[i].drive = this.drivesList[i];
             this.drivesList[i].performedCount;
              this.sendAndRequestService.requestForGET(Constants.app_urls.PROGRESS_RECORD.GET_DDPROGRESS_BASED_ON_DRIVE_FROM_DATE
              +this.drivesList[i].id+'/'+this.searchInputFormGroup.controls['fromDate'].value
                  ).subscribe((data) =>{
                      this.sendAndRequestService.requestForGET(Constants.app_urls.PROGRESS_RECORD.GET_ALREADY_DONE_COUNT_BASED_ON_DRIVE_FROM_DATE
                        +this.drivesList[i].id+'/'+this.searchInputFormGroup.controls['fromDate'].value
                          ).subscribe((data) => {
                             this.drivesList[i].alreadyDone = data;
                          });
                      this.DDProgress = data;
                      if(this.DDProgress != null) {
                          this.drivesList[i].facilityId = this.DDProgress.depot;
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
    
}

@Component({
  selector: 'add-asset-ids-drive-dialog',
  templateUrl: 'add-asset-ids-drive-dialog.component.html',
})
export class AddAssetIdsDriveDialogComponent implements OnInit  {
        
    dailyProgressIdFormGroup: FormGroup;
    assetIdList: any;
    displayedColumns = ['sno', 'assetId', 'actions'];
    dataSource: MatTableDataSource<DriveProgressIdModel>;
    facilityId: any;
    selectedAssetIdList: any [] = [];
    assetIdsExists: boolean;
    loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
    DDProgressId: any;
    driveId: any;
    performedDate : any;
    resp: any;
    DDProgress: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    performedCount: any;
    enableSubmit: boolean;
    depotType: any;
    hideFields: boolean;
    assetType: any;

  constructor(
    private formBuilder: FormBuilder,
    private spinnerService: Ng4LoadingSpinnerService,  
    private sendAndRequestService:SendAndRequestService,
    private dialog: MatDialog,
    private commonService: CommonService,
    private dialogRef: MatDialogRef<AddAssetIdsDriveDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {
      
      if(data) {
          this.facilityId = data.facilityId;
          this.driveId = data.driveId;
          this.performedDate = data.performedDate;
          this.depotType = data.depotType;
          this.assetType = data.assetType;
      }
  }
    
    ngOnInit() {
        this.dailyProgressIdFormGroup = this.formBuilder.group({
            fromKilometer:[null],
            toKilometer: [null],
            assetId: [null],
            manual: [null],
            assetType: [null]
        })
        this.getDriveProgressIdData();
        if('TSS' === this.depotType || 'SSP' === this.depotType ||  'SP' === this.depotType){
            this.hideFields = false;
            this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.GET_ASSETID_BASED_ON_ASSETTYPE_FACILITYID + this.assetType + '/'+this.facilityId).subscribe((data) => {
              this.assetIdList = data;
            }, error => {
              this.spinnerService.hide();
            });
        }else {
            this.hideFields = true;    
        }
    }
    
    getDriveProgressIdData() {
        const driveProgressId: DriveProgressIdModel[] = [];
        this.sendAndRequestService.requestForGET(Constants.app_urls.PROGRESS_RECORD.GET_DDPROGRESS_BASED_ON_DRIVE_FROM_DATE
              +this.driveId+'/'+this.performedDate
                  ).subscribe((data) =>{
                      this.DDProgress = data;
                      if(this.DDProgress != null) {
                          this.DDProgressId = this.DDProgress.id;
                          this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE_PROGRESS_ID.GET_DRIVE_PROGRESS_ID_DATA_BASED_ON_DRIVE_DAILY_PROGRESS
                                  +this.DDProgressId
                                      ).subscribe((data) =>{
                                          this.resp = data;
                                          if(this.resp){
                                              this.performedCount = this.resp.length;
                                              for (let i = 0; i < this.resp.length; i++) {
                                                this.resp[i].sno = i + 1;
                                                driveProgressId.push(this.resp[i]);
                                              }
                                            }
                                          this.dataSource = new MatTableDataSource(driveProgressId);
                                          });
                          }
                      });    
    }
    
    addAssetIdsFormSubmit() {
        this.enableSubmit = false;
        this.assetIdsExists = false;
        let saveDriveDailyProgress = {
            id: 0,
            driveId: this.driveId,
            performedDate: this.performedDate,
            depot: this.facilityId,
            createdBy : this.loggedUserData.username,
        }
        this.sendAndRequestService.requestForPOST(Constants.app_urls.PROGRESS_RECORD.SAVE_DRIVE_DAILY_PROGRESS_RECORD ,saveDriveDailyProgress, false).subscribe(response => {
            this.spinnerService.hide();
            this.resp = response;
            if(this.resp) {
                this.DDProgressId = this.resp.id;
                let currentDate = new Date();
                let formdata: FormData = new FormData();
                for(var i=0;i<this.selectedAssetIdList.length;i++){
                    formdata.append('assetIds', this.selectedAssetIdList[i]);
                }
                formdata.append('createdBy', this.loggedUserData.username);
                formdata.append('driveDailyProgressId',this.DDProgressId);
                this.sendAndRequestService.requestForPOST(Constants.app_urls.DRIVE_PROGRESS_ID.SAVE, formdata, true).subscribe(data => {
                        this.spinnerService.hide();
                        this.selectedAssetIdList = [];
                        this.getDriveProgressIdData();
                        
                    }, error => {
                        console.log('ERROR >>>');
                        this.spinnerService.hide();
                    })
            }
            
          });
        
    }
    
    addAssetIds() {
        this.enableSubmit = true;
        let assetIds = this.dailyProgressIdFormGroup.value.assetId;
        if (assetIds) {
             this.assetIdsExists = true;
            for (var i = 0; i < assetIds.length; i++) {
                this.selectedAssetIdList.push(assetIds[i]);
            }
        }
        let manualEnteredData = this.dailyProgressIdFormGroup.value.manual;
        if(manualEnteredData) {
            this.assetIdsExists = true;
            let manualArray = manualEnteredData.split(";");
            for (var i = 0 ; i < manualArray.length; i++) {
                if(manualArray[i]) {
                    this.selectedAssetIdList.push(manualArray[i]);
                }
            }
        }

        Object.keys(this.dailyProgressIdFormGroup.controls).forEach(key => {
            this.dailyProgressIdFormGroup.get(key).setValue(null) ;
        });
    }
    
    removeAssetId(id) {
        this.selectedAssetIdList.splice(id, 1);
        if(this.selectedAssetIdList.length === 0) {
            this.assetIdsExists = false;
        }
    }
    
    
    selectedFromKm(fKM) {
      this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.GET_ASSETIDS_BY_FACILITYID_FROMKM_TOKM + this.facilityId + '/'+this.dailyProgressIdFormGroup.controls['fromKilometer'].value + '/'+this.dailyProgressIdFormGroup.controls['fromKilometer'].value).subscribe((data) => {
        this.assetIdList = data;
      }, error => {
        this.spinnerService.hide();
      });
  }
    
    selectedToKm(tKM) {
      this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.GET_ASSETIDS_BY_FACILITYID_FROMKM_TOKM + this.facilityId + '/' +this.dailyProgressIdFormGroup.controls['fromKilometer'].value + '/'+this.dailyProgressIdFormGroup.controls['toKilometer'].value).subscribe((data) => {
        this.assetIdList = data;
      }, error => {
        this.spinnerService.hide();
      });
  }
    
  delete(id) {
    this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
      disableClose: false
    });
    this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete?';
    this.confirmDialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.spinnerService.show();
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.DRIVE_PROGRESS_ID.DELETE ,id).subscribe(data => {
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted  Daily Progress Id Successfully");
          this.getDriveProgressIdData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Drive Drive Progress Id Failed.");
        })
      }
      this.confirmDialogRef = null;
    });
  }
    
    
    

  onGoBack(): void {
    this.dialogRef.close();
  }

}