import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { DrivesService } from 'src/app/services/drives.service';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { MatDatepickerInputEvent } from '@angular/material';

@Component({
  selector: 'app-add-drive-failure-analysis',
  templateUrl: './add-drive-failure-analysis.component.html',
  styleUrls: ['./add-drive-failure-analysis.component.css']
})
export class AddDriveFailureAnalysisComponent implements OnInit {
  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  title:string;
  addFailureAnalysisFormGroup: FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  stateList = [{ 'id': 1, "value": 'Yes' }, { 'id': 2, "value": 'No' }];
  driveList = [];
  reportedList=[];
  failureAnalysisFormErrors: any;
  resp: any;
  reportDescriptionFlag=false;
  toMinDate=new Date();
  completeMinDate=new Date();
  constructor(
    private formBuilder: FormBuilder,    
    private drivesService: DrivesService,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private route: ActivatedRoute,
    private router: Router,
  ) {
    // Reactive form errors
    this.failureAnalysisFormErrors = {
      //failure_id: {},
      reported: {},
      reportDescription:{},
      repurcussion: {},
      date: {},
      div: {},
      failureSection: {},
      assetType: {},
      assetId: {},
      subAssetType: {},
      subAssetId: {},
      make: {},
      model: {},
      rootCause: {},
      actionPlan: {},
      actionStatus: {},
      approvedBy: {},
      actionTargetDate: {},
      actionCompletedDate: {},
      actionDescription: {}
    };
  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
    this.getDrivesData();
    this.findYesNoStatus();
    this.createForm();
    if (!isNaN(this.id)) {
      this.addFailureAnalysisFormGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      });
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = 'Edit';
      this.getFailureAnalysisDataById(this.id);
    } else {
      this.save = true;
      this.update = false;
      this.title = 'Save';
    }
  }

  createForm() {
    this.addFailureAnalysisFormGroup
      = this.formBuilder.group({
        id: 0,
       // 'failure_id': [null],
        'reported': [null],
        "reportDescription":[null],
        'repurcussion': [null],
        'date': [null],
        'div': [null],
        'failureSection': [null],
        'assetType': [null],
        'assetId': [null],
        'subAssetType': [null],
        'subAssetId': [null],
        'make': [null],
        'model': [null],
        'rootCause': [null],
        'actionPlan': [null],
        'actionStatus': [null],
        'approvedBy': [null],
        'actionTargetDate': [null],
        'actionCompletedDate': [null],
        'actionDescription': [null]
      });
  }

  onFormValuesChanged() {
    for (const field in this.failureAnalysisFormErrors) {
      if (!this.failureAnalysisFormErrors.hasOwnProperty(field)) {
        continue;
      }
      this.failureAnalysisFormErrors[field] = {};
      const control = this.addFailureAnalysisFormGroup.get(field);

      if (control && control.dirty && !control.valid) {
        this.failureAnalysisFormErrors[field] = control.errors;
      }
    }
  }

  getDrivesData() {
    this.drivesService.getDrivesData().subscribe((data) => {
      this.driveList = data;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }
  findYesNoStatus() {
    this.drivesService.findStatusItem(Constants.STATUS_ITEMS.YES_NO_TYPE).subscribe((data) => {
      this.reportedList = data;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }
  getFailureAnalysisDataById(id) {
    this.drivesService.findFailureAnalysisDataById(id)
      .subscribe((resp) => {
        this.resp = resp;
        this.addFailureAnalysisFormGroup.patchValue({
          id: this.resp.id,
         // failure_id: this.resp.failure_id,
          reported: this.resp.reported,
          reportDescription:this.resp.reportDescription,
          repurcussion: this.resp.repurcussion,
          date: !!this.resp.poulation ? new Date(this.resp.poulation) : '',
          failureSection: this.resp.section,
          assetType: this.resp.assetType,
          assetId: this.resp.assetId,
          subAssetType: this.resp.subAssetType,
          subAssetId: this.resp.subAssetId,
          make: this.resp.make,
          model: this.resp.model,
          rootCause: this.resp.rootCause,
          actionPlan: this.resp.actionPlan,
          actionStatus: this.resp.actionStatus,
          approvedBy: this.resp.approvedBy,
          actionTargetDate: !!this.resp.actionTargetDate ? new Date(this.resp.actionTargetDate) : '',
          actionCompletedDate: !!this.resp.actionCompletedDate ? new Date(this.resp.actionCompletedDate) : '',
          actionDescription: this.resp.actionDescription,
        });
        this.spinnerService.hide();
      })
  }
  addEvent(type: string, event: MatDatepickerInputEvent<Date>) {
    this.toMinDate = event.value;
  }
  addEventTargetDate(type: string, event: MatDatepickerInputEvent<Date>) {
    this.completeMinDate = event.value;
  }
  onAddFailureAnalysisFormSubmit() {
    if (this.addFailureAnalysisFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    var data = {};
    var message = '';
    var failedMessage = '';
    if (this.save) {
      data = {
       // "failure_id": this.addFailureAnalysisFormGroup.value.failure_id,
        "reported": this.addFailureAnalysisFormGroup.value.reported,
        "reportDescription":this.addFailureAnalysisFormGroup.value.reportDescription,
        "repurcussion": this.addFailureAnalysisFormGroup.value.repurcussion,
        "date": this.addFailureAnalysisFormGroup.value.date,
        "failureSection": this.addFailureAnalysisFormGroup.value.failureSection,
        "div": this.addFailureAnalysisFormGroup.value.div,
        "assetType": this.addFailureAnalysisFormGroup.value.assetType,
        "assetId": this.addFailureAnalysisFormGroup.value.assetId,
        "subAssetType": this.addFailureAnalysisFormGroup.value.subAssetType,
        "subAssetId": this.addFailureAnalysisFormGroup.value.subAssetId,
        "make": this.addFailureAnalysisFormGroup.value.make,
        "model": this.addFailureAnalysisFormGroup.value.model,
        "rootCause": this.addFailureAnalysisFormGroup.value.rootCause,
        "actionPlan": this.addFailureAnalysisFormGroup.value.actionPlan,
        "actionStatus": this.addFailureAnalysisFormGroup.value.actionStatus,
        "approvedBy": this.addFailureAnalysisFormGroup.value.approvedBy,
        "actionTargetDate": this.addFailureAnalysisFormGroup.value.actionTargetDate,
        "actionCompletedDate": this.addFailureAnalysisFormGroup.value.actionCompletedDate,
        "actionDescription": this.addFailureAnalysisFormGroup.value.actionDescription,
        "createdBy": "1",
        "createdOn": "2020-04-01",
        "updatedBy": "1",
        "updatedOn": "2020-04-01"
      }    
      message = 'Saved';
      failedMessage = "Saving";
      this.drivesService.saveFailureAnalysisData(data).subscribe(response => {
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Failure Analysis Data "+message+" Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Failure Analysis Data "+failedMessage+" Failed.");
      })
    }else if(this.update){
      data = {
        "id":this.id,
        //"failure_id": this.addFailureAnalysisFormGroup.value.failure_id,
        "reported": this.addFailureAnalysisFormGroup.value.reported,
        "reportDescription":this.addFailureAnalysisFormGroup.value.reportDescription,
        "repurcussion": this.addFailureAnalysisFormGroup.value.repurcussion,
        "date": this.addFailureAnalysisFormGroup.value.date,
        "failureSection": this.addFailureAnalysisFormGroup.value.failureSection,
        "div": this.addFailureAnalysisFormGroup.value.div,
        "assetType": this.addFailureAnalysisFormGroup.value.assetType,
        "assetId": this.addFailureAnalysisFormGroup.value.assetId,
        "subAssetType": this.addFailureAnalysisFormGroup.value.subAssetType,
        "subAssetId": this.addFailureAnalysisFormGroup.value.subAssetId,
        "make": this.addFailureAnalysisFormGroup.value.make,
        "model": this.addFailureAnalysisFormGroup.value.model,
        "rootCause": this.addFailureAnalysisFormGroup.value.rootCause,
        "actionPlan": this.addFailureAnalysisFormGroup.value.actionPlan,
        "actionStatus": this.addFailureAnalysisFormGroup.value.actionStatus,
        "approvedBy": this.addFailureAnalysisFormGroup.value.approvedBy,
        "actionTargetDate": this.addFailureAnalysisFormGroup.value.actionTargetDate,
        "actionCompletedDate": this.addFailureAnalysisFormGroup.value.actionCompletedDate,
        "actionDescription": this.addFailureAnalysisFormGroup.value.actionDescription,
        "createdBy": "1",
        "createdOn": "2020-04-01",
        "updatedBy": "1",
        "updatedOn": "2020-04-01"
      }   
      message = 'Updated';
      failedMessage = "Updating";
      this.drivesService.updateFailureAnalysisData(data).subscribe(response => {
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Failure Analysis Data "+message+" Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Failure Analysis Data "+failedMessage+" Failed.");
      })
    }
    
  }
  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }


}
