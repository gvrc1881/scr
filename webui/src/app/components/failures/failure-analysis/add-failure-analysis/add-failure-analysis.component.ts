import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { DatePipe } from '@angular/common';
import { OWL_DATE_TIME_FORMATS } from 'ng-pick-datetime';
import { MY_CUSTOM_FORMATS } from 'src/app/common/date-filter.pipe';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';


@Component({
  selector: 'app-add-failure-analysis',
  templateUrl: './add-failure-analysis.component.html',
  styleUrls: []
})
export class AddFailureAnalysisComponent implements OnInit {
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
  divisionHierarchy:any = JSON.parse(sessionStorage.getItem('divisionData'));
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
  maxDate = new Date();
  reportDescriptionFlag=false;
  toMinDate=new Date();
  completeMinDate=new Date();
  divisionList:any;
  dateFormat = 'dd-MM-yyyy hh:mm:ss';
  currentDate = new Date();
  targetDate=new Date();
  completeDate = new Date();
  assetsList:any;
  constructor(
    private formBuilder: FormBuilder,    
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private datePipe: DatePipe,
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
      actionDescription: {},
      avoidable:{},
      remarkBrief:{},
      remarkDetails:{}
    };
  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
    this.getsData();
    this.findYesNoStatus();
    this.findDivisions();
    this.findAssetTypes();
    this.createForm();
    if (!isNaN(this.id)) {
      this.addFailureAnalysisFormGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      });
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;
      this.getFailureAnalysisDataById(this.id);
    } else {
      this.save = true;
      this.update = false;
      this.title = Constants.EVENTS.ADD;
    }
  }

  // findDivisions(){
  //   this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.GET_DIVISIONS)
  //   .subscribe((resp) => {
  //     this.divisionList = resp;
  //   });
  // }

  findDivisions(){
   
    this.divisionList=[];    

    for (let i = 0; i < this.divisionHierarchy.length; i++) {
        
           if( this.divisionHierarchy[i].depotType == 'DIV'){           
               
               this.divisionHierarchy.divisionList;
              
           }
        }
}
findAssetTypes(){
  
  this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_ALL_ASSET_TYPES).subscribe((data) => {
    this.assetsList = data;
    this.spinnerService.hide();
  }, error => {
    this.spinnerService.hide();
  });
}
  createForm() {
    this.addFailureAnalysisFormGroup
      = this.formBuilder.group({
        id: 0,
        'reported': [null],
        "reportDescription":[null, Validators.maxLength(255)],
        'repurcussion': [null, Validators.maxLength(255)],
        'date': [null,Validators.compose([Validators.required])],
        'div': [null,Validators.compose([Validators.required])],
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
        'actionDescription': [null, Validators.compose([Validators.required, Validators.maxLength(255)])],
        'avoidable':[null, Validators.maxLength(250)],
      'remarkBrief':[null, Validators.maxLength(250)],
      'remarkDetails':[null, Validators.maxLength(250)]
      });
      this.addFailureAnalysisFormGroup.patchValue({

        reported: 'NO'
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
        this.toMinDate = new Date(this.resp.date);
        this.completeMinDate = new Date(this.resp.actionTargetDate);
        this.addFailureAnalysisFormGroup.patchValue({
          id: this.resp.id,
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
          avoidable:this.resp.avoidable,
          remarkBrief:this.resp.remarkBrief,
          remarkDetails:this.resp.remarkDetails
        });
        this.spinnerService.hide();
      })
  }
  addEvent($event) {
    this.toMinDate = new Date($event.value);
    this.currentDate = new Date($event.value);
  }
  addEventTargetDate($event) {
    this.completeMinDate = new Date($event.value);    
    this.targetDate = new Date($event.value);
  }
  addEventCompleteDate($event){
    this.completeDate = new Date($event.value);
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
        'avoidable':this.addFailureAnalysisFormGroup.value.avoidable,
        'remarkBrief':this.addFailureAnalysisFormGroup.value.remarkBrief,
        'remarkDetails':this.addFailureAnalysisFormGroup.value.remarkDetails,
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
        'avoidable':this.addFailureAnalysisFormGroup.value.avoidable,
        'remarkBrief':this.addFailureAnalysisFormGroup.value.remarkBrief,
        'remarkDetails':this.addFailureAnalysisFormGroup.value.remarkDetails,
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
