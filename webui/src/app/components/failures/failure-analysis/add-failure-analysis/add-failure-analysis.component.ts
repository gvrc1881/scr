import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';


@Component({
  selector: 'app-add-failure-analysis',
  templateUrl: './add-failure-analysis.component.html',
  styleUrls: []
})
export class AddFailureAnalysisComponent implements OnInit {
  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  title:string;
  addFailureAnalysisFormGroup: FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  stateList = [{ 'id': 1, "value": 'Yes' }, { 'id': 2, "value": 'No' }];
  List = [];
  reportedList=[];
  failureAnalysisFormErrors: any;
  resp: any;
  reportDescriptionFlag=false;
  toMinDate=new Date();
  completeMinDate=new Date();
  divisionList:any;
  constructor(
    private formBuilder: FormBuilder,    
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private route: ActivatedRoute,
    private router: Router,
    private sendAndRequestService: SendAndRequestService
  ) {
    // Reactive form errors
    this.failureAnalysisFormErrors = {
      reported: {},
      reportDescription:{},
      repurcussion: {},
      date: {},
      div: {},
      section: {},
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
    this.getsData();
    this.findYesNoStatus();
    this.findDivisions();
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

  findDivisions(){
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.GET_DIVISIONS)
    .subscribe((resp) => {
      this.divisionList = resp;
    });
  }

  createForm() {
    this.addFailureAnalysisFormGroup
      = this.formBuilder.group({
        id: 0,
        'reported': [null],
        "reportDescription":[null, Validators.maxLength(255)],
        'repurcussion': [null, Validators.maxLength(255)],
        'date': [null],
        'div': [null],
        'section': [null,Validators.maxLength(255)],
        'assetType': [null,Validators.maxLength(255)],
        'assetId': [null,Validators.maxLength(255)],
        'subAssetType': [null,Validators.maxLength(255)],
        'subAssetId': [null,Validators.maxLength(255)],
        'make': [null,Validators.maxLength(255)],
        'model': [null,Validators.maxLength(255)],
        'rootCause': [null, Validators.maxLength(255)],
        'actionPlan': [null, Validators.maxLength(255)],
        'actionStatus': [null,Validators.maxLength(255)],
        'approvedBy': [null,Validators.maxLength(255)],
        'actionTargetDate': [null],
        'actionCompletedDate': [null],
        'actionDescription': [null, Validators.maxLength(255)]
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

  getsData() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE.GET_DRIVES).subscribe((data) => {
      this.List = data;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }
  findYesNoStatus() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_STATUS_ITEM+Constants.STATUS_ITEMS.YES_NO_TYPE).subscribe((data) => {
      this.reportedList = data;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }
  
  updateDescription($event){
    if ($event.value) {
      this.reportDescriptionFlag = $event.value == Constants.YES ? true : false;
    }
  }
  getFailureAnalysisDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.EDIT+id)
      .subscribe((resp) => {
        this.resp = resp;
        this.addFailureAnalysisFormGroup.patchValue({
          id: this.resp.id,
         // failure_id: this.resp.failure_id,
          reported: this.resp.reported,
          reportDescription:this.resp.reportDescription,
          repurcussion: this.resp.repurcussion,
          date: !!this.resp.date ? new Date(this.resp.date) : '',
          section: this.resp.section,
          div: this.resp.div,
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
  addEvent($event) {
    this.toMinDate = new Date($event.value);
  }
  addEventTargetDate($event) {
    this.completeMinDate = new Date($event.value);
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
        "reported": this.addFailureAnalysisFormGroup.value.reported,
        "reportDescription":this.addFailureAnalysisFormGroup.value.reportDescription,
        "repurcussion": this.addFailureAnalysisFormGroup.value.repurcussion,
        "date": this.addFailureAnalysisFormGroup.value.date,
        "section": this.addFailureAnalysisFormGroup.value.section,
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
        "createdBy": this.loggedUserData.username,
        "createdOn": new Date()
      }    
      message = 'Saved';
      failedMessage = "Saving";
      this.sendAndRequestService.requestForPOST(Constants.app_urls.FAILURES.SAVE,data, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Failure Analysis Data "+message+" Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Failure Analysis Data "+failedMessage+" Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Failure Analysis Data "+failedMessage+" Failed.");
      })
    }else if(this.update){
      data = {
        "id":this.id,
        "reported": this.addFailureAnalysisFormGroup.value.reported,
        "reportDescription":this.addFailureAnalysisFormGroup.value.reportDescription,
        "repurcussion": this.addFailureAnalysisFormGroup.value.repurcussion,
        "date": this.addFailureAnalysisFormGroup.value.date,
        "section": this.addFailureAnalysisFormGroup.value.section,
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
        "updatedBy": this.loggedUserData.username,
        "updatedOn": new Date()
      }   
      message = 'Updated';
      failedMessage = "Updating";
      this.sendAndRequestService.requestForPUT(Constants.app_urls.FAILURES.UPDATE,data, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Failure Analysis Data "+message+" Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Failure Analysis Data "+failedMessage+" Failed."); 
        }
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
