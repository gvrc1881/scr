import { OnInit, Component, ViewChild,ElementRef } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatTableDataSource, MatPaginator, MatSort, MatDialog, MatDialogRef } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { environment } from './../../../environments/environment';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';
import { DateAdapter, MAT_DATE_FORMATS } from '@angular/material';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
    selector: 'project-activity-inspection',
    templateUrl: './project-activity-inspection.component.html',
    styleUrls: ['./project-activity-inspection.component.css'],
    providers: [
    {
        provide: DateAdapter, useClass: AppDateAdapter
    },
    {
        provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
    }
    ]
})
export class ProjectActivityInspectionComponent implements OnInit {
        
    wpaDailyProgressId: any;
    wpaDailyProgressData: any;
    insCheckListData: any;
    measureList: any;
    activityList: any;
    prjActivityIns : FormGroup;
    measureExists: boolean;
    title: any;
    resp: any;

    constructor(
        public dialog: MatDialog,
        private formBuilder: FormBuilder,
        private spinnerService: Ng4LoadingSpinnerService,
        private commonService: CommonService,
        private sendAndRequestService:SendAndRequestService,
        private router: Router,
        private route: ActivatedRoute     
    ) {
    }
    
    ngOnInit() {
        this.wpaDailyProgressId = +this.route.snapshot.params['wpaId'];
        this.createPrjActivityIns();
        this.getWPADailyProgressData();
    }
    
    projectActivityInsSubmit(){
      const convMeasureMap = {};
      this.measureMap.forEach((val: string, key: string) => {
      convMeasureMap[key] = val;
      });

      const convActivityMap = {};
      this.activityMap.forEach((val: string, key: string) => {
      convActivityMap[key] = val;
      });
       var saveModel = {
        "measureMap":convMeasureMap,
        "activityMap":convActivityMap    
       }
      console.log(" model for save PAI entry:::" + JSON.stringify(saveModel));
      this.sendAndRequestService.requestForPOST(Constants.app_urls.INSPECTION.PROJECT_ACTIVITY_INSPECTION.SAVE_PRJ_ACT_INS, saveModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;

        if (this.resp.code == Constants.CODES.SUCCESS) {
          this.commonService.showAlertMessage("Entry Data Saved Successfully");
          //this.router.navigate(['/'], { relativeTo: this.route });
          window.location.reload;
        } else {
          this.commonService.showAlertMessage("Entry Data Saving Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Entry Data Saving Failed.");
      });     
    }
    
    getWPADailyProgressData() {
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WPA_DAILY_PROGRESS_BASED_ON_ID+this.wpaDailyProgressId
            ).subscribe((response) => {
                this.wpaDailyProgressData = response; 
                //console.log("*** daily progress ***"+JSON.stringify(this.wpaDailyProgressData));
                if(this.wpaDailyProgressData.workPhaseActivityId.testInspectionId){
                    this.sendAndRequestService.requestForGET(Constants.app_urls.INSPECTION.INSPECTION_CHECK_LIST.GET_INS_CHECK_LIST_BASED_ON_TEST_INS_ID+this.wpaDailyProgressData.workPhaseActivityId.testInspectionId.id
                    ).subscribe((response) => {
                        this.insCheckListData = response;
                        //console.log('*** check list response ***'+JSON.stringify(response));
                        if(this.insCheckListData){
                            this.title = "Save";
                             this.measureList = this.insCheckListData.filter(value => {
                                return value.measureActivityMma.toLowerCase() == "MEASURE".toLowerCase();
                             });
                             this.activityList = this.insCheckListData.filter(value => {
                                return value.measureActivityMma.toLowerCase() == "activity".toLowerCase();
                             });
                           // console.log('***  measureList ***'+JSON.stringify(this.measureList));
                        }
                    });    
                }
                
        });
    }
    
    measureMap = new Map<string, string>();
    changeMeasure(index,val) {
        this.measureMap.set(this.measureList[index].activityPositionId,val);
        //console.log("updated value is::"+JSON.stringify(this.measureMap.get(this.measureList[index].activityPositionId)));
       
    }
    
    activityMap = new Map<string, string>();
    changeActivity(index,val) {
        //console.log('*** position id ***'+this.activityList[index].activityPositionId);
        this.activityMap.set(this.activityList[index].activityPositionId,val);
       // console.log("updated value is::"+JSON.stringify(this.activityMap.get(this.activityList[index].activityPositionId)));
       
    }
    
    onGoBack() {
        this.router.navigate(['../../'], { relativeTo: this.route });
    }
    
    createPrjActivityIns(){
        this.prjActivityIns = this.formBuilder.group({
          id: 0,
          'Schedule_date': [null, Validators.compose([Validators.required])],
          'Depot_Name': [null, Validators.compose([Validators.required])],
          'Power_Block': [null, Validators.compose([Validators.required])],
          'Asset_Type': [null,],
          
          'Schedule': [null, Validators.compose([Validators.required])],
          //'0':[null, Validators.compose([Validators.required])],
          'Asset_Id': [null, Validators.compose([Validators.required])]
        });    
    }

}