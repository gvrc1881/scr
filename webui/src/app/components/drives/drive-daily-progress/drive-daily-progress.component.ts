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
    displayedColumns = ['sno', 'drive','alreadyDone','assetType','performedCount','depot','ids', 'actions'];
    searchInputFormGroup: FormGroup;
    depotTypeList = [];
    drivesList: any;
    performedCount: any;
    resp: any;
    DDProgress: any;
    depotsList: any = JSON.parse(localStorage.getItem('depotData'));
    facilityId: any
    
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
        console.log('*** depot id **'+row.facilityId);
        let saveDriveDailyProgress = {
            id: 0,
            driveId: row.drive.id,
            performedCount: row.performedCount,
            performedDate: this.searchInputFormGroup.controls['fromDate'].value,
            depot: row.facilityId
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
        console.log('*** facility id***'+row.facilityId);
        //console.log('*** facility id***'+this.dailyProgressFormGroup.controls['fromDate'].value);
        //let fromDate = this.dailyProgressFormGroup.controls['fromDate'].value;
        const dialogRef = this.dialog.open(AddAssetIdsDriveDialogComponent, {
          height: '600px',
          width: '80%', 
          data: { driveId : row.drive.id,
                  performedCount: row.performedCount,
                  facilityId: '10073',
                  performedDate: this.searchInputFormGroup.controls['fromDate'].value
                }
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
              console.log('*** id ***'+this.drivesList[i].id);
              console.log('*** from date ***'+this.searchInputFormGroup.controls['fromDate'].value);
             // this.DDProgress = '';
              this.sendAndRequestService.requestForGET(Constants.app_urls.PROGRESS_RECORD.GET_DDPROGRESS_BASED_ON_DRIVE_FROM_DATE
              +this.drivesList[i].id+'/'+this.searchInputFormGroup.controls['fromDate'].value
                  ).subscribe((data) =>{
                      this.DDProgress = data;
                      if(this.DDProgress != null) {
                          this.drivesList[i].alreadyDone = this.DDProgress.performedCount;
                          this.drivesList[i].facilityId = this.DDProgress.depot;
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
    facilityId: any;
    selectedAssetIdList: any [] = [];
    assetIdsExists: boolean;
    loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
    DDProgressId: any;
    driveId: any;
    performedDate : any;
    resp: any;
    DDProgress: any;

  constructor(
    public formBuilder: FormBuilder,
    private spinnerService: Ng4LoadingSpinnerService,  
    private sendAndRequestService:SendAndRequestService,  
    public dialogRef: MatDialogRef<AddAssetIdsDriveDialogComponent>,
    @Inject(MAT_DIALOG_DATA) private data: any) {
      
    console.log('** data **'+JSON.stringify(data));
      if(data) {
          this.facilityId = data.facilityId;
          this.driveId = data.driveId;
          this.performedDate = data.performedDate;
          console.log('*** facility Id from data **'+this.facilityId);
      }
  }
    
    ngOnInit() {
        this.dailyProgressIdFormGroup = this.formBuilder.group({
            fromKilometer:[null],
            toKilometer: [null],
            assetId: [null]    
        })
        this.getDriveProgressIdData();
    }
    
    getDriveProgressIdData() {
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
                                          if(this.resp != null) {
                                              
                                            console.log('data count is***'+this.resp.length);
                                              }
                                          });
                        console.log('data count is***'+this.DDProgress.id);
                          }
                      });    
    }
    
    addAssetIdsFormSubmit() {
        let saveDriveDailyProgress = {
            id: 0,
            driveId: this.driveId,
            performedDate: this.performedDate 
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
               // formdata.append('tractionEnergyTariffId', saveDetails.tractionEnergyTariffId);
                //formdata.append('contentCategory', saveDetails.contentCategory);
                //formdata.append('driveDailyProgressId', saveDetails.description);
               // formdata.append('createdDate', currentDate);
                formdata.append('createdBy', this.loggedUserData.id);
                formdata.append('driveDailyProgressId',this.DDProgressId)
                this.sendAndRequestService.requestForPOST(Constants.app_urls.DRIVE_PROGRESS_ID.SAVE, formdata, true).subscribe(data => {
                        this.spinnerService.hide();
                        console.log('after save ');
                    }, error => {
                        console.log('ERROR >>>');
                        this.spinnerService.hide();
                    })
            }
            console.log('response ddProgressId '+this.resp.id);
            
          });
        
    }
    
    addAssetIds() {
        let assetIds = this.dailyProgressIdFormGroup.value.assetId;
       // console.log('ids list'+assetId1.toString());
        if (assetIds.length > 0) { this.assetIdsExists = true; }
        for (var i = 0; i < assetIds.length; i++) {
            this.selectedAssetIdList.push(assetIds[i]);
        }
        //this.dailyProgressIdFormGroup.reset();
        Object.keys(this.dailyProgressIdFormGroup.controls).forEach(key => {
            this.dailyProgressIdFormGroup.get(key).setValue(null) ;
        });
       // this.assetIdList = '';
        console.log('ids list'+this.selectedAssetIdList);    
    }
    
    removeAssetId(id) {
        this.selectedAssetIdList.splice(id, 1);
        if(this.selectedAssetIdList.length === 0) {
            this.assetIdsExists = false;
        }
    }
    
    selectedAssetId($event) {
        let assetId = $event.value;
        console.log('selected asset id***'+assetId);    
    }
    
    selectedFromKm(fKM) {
      this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.GET_ASSETIDS_BY_FACILITYID_FROMKM_TOKM + this.facilityId + '/'+this.dailyProgressIdFormGroup.controls['fromKilometer'].value + '/'+this.dailyProgressIdFormGroup.controls['toKilometer'].value).subscribe((data) => {
        this.assetIdList = data;
      }, error => {
        this.spinnerService.hide();
      });
  }
    
    selectedToKm(tKM) {
        console.log('value is****');
      this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.GET_ASSETIDS_BY_FACILITYID_FROMKM_TOKM + this.facilityId + '/' +this.dailyProgressIdFormGroup.controls['fromKilometer'].value + '/'+this.dailyProgressIdFormGroup.controls['toKilometer'].value).subscribe((data) => {
        this.assetIdList = data;
      }, error => {
        this.spinnerService.hide();
      });
  }
    
    
    

  onGoBack(): void {
    this.dialogRef.close();
  }

}