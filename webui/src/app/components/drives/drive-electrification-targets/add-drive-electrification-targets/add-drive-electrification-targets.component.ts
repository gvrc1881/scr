import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';
import { DateAdapter, MAT_DATE_FORMATS } from '@angular/material';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';




@Component({
  selector: 'app-add-drive-electrification-targets',
  templateUrl: './add-drive-electrification-targets.component.html',
  providers: [
    {
        provide: DateAdapter, useClass: AppDateAdapter
    },
    {
        provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
    }
    ]
})
export class AddDriveElectrificationTargetsComponent implements OnInit {

  pagination =Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  title:string = Constants.EVENTS.ADD;
  addDriveElectrificationTargetsFormGroup: FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  public stateList = ['Yes','No'];
  toMinDate = new Date();
  currentDate = new Date();
  dateFormat = 'MM-dd-yyyy ';
  electrificationTargetsFormErrors: any;
  resp: any;
  guageList:any;
  executionAgencyList:any;
  doublingTripplingList:any;
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
    this.electrificationTargetsFormErrors = {
      section: {},
      guage: {},
      targetDate: {},
      status: {},
      division: {},
      executionAgency: {},
      TKM: {},
      RKM: {},
      crsInspection: {},
      crsAuthorisation: {},
      targetSetBy: {},
      doublingTrippling: {},
      state: {},
      phase: {},
      proposalScheme: {},
      sanctionByBoard: {},
      yearOfSanction: {},
      dateOfCompletion: {},
    };
  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
    this.createForm();
    this.findGuage();
    this.findExecutionAgency();
    this.findDoublingTrippling();
    this.findDivisions();
    if (!isNaN(this.id)) {
      this.addDriveElectrificationTargetsFormGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      });
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;
      this.getElectrificationTargetsDataById(this.id);
    } else {
      this.save = true;
      this.update = false;
      this.title = Constants.EVENTS.ADD;
    }
  }

  createForm() {
    this.addDriveElectrificationTargetsFormGroup
      = this.formBuilder.group({
        id: 0,
        'section': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
        'guage': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.pattern(this.pattern)])],
        'targetDate': [null],
        'status': [null],
        'division': [null],
        'executionAgency': [null],
        'TKM': [null],
        'RKM': [null],
        'crsInspection': ['No'],
        'crsAuthorisation': [null],
        'targetSetBy': ['No'],
        'doublingTrippling': ['Yes'],
        'state': [null],
        'phase': [null, Validators.maxLength(255)],
        'proposalScheme': [null, Validators.maxLength(255)],
        'sanctionByBoard': [null],
        'yearOfSanction': [null],
        'dateOfCompletion': [null],
      });
      
  }

  onFormValuesChanged() {
    for (const field in this.electrificationTargetsFormErrors) {
      if (!this.electrificationTargetsFormErrors.hasOwnProperty(field)) {
        continue;
      }
      this.electrificationTargetsFormErrors[field] = {};
      const control = this.addDriveElectrificationTargetsFormGroup.get(field);

      if (control && control.dirty && !control.valid) {
        this.electrificationTargetsFormErrors[field] = control.errors;
      }
    }
  }
  addEvent($event) {
    this.toMinDate = new Date($event.value);
  }
  getElectrificationTargetsDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.ELECTRIFICATION_TARGETS.EDIT + id)
      .subscribe((resp) => {
        this.resp = resp;
        this.addDriveElectrificationTargetsFormGroup.patchValue({
          id: this.resp.id,
          section: this.resp.section,
          guage: this.resp.guage,
          targetDate: !!this.resp.targetDate ? new Date(this.resp.targetDate) : '',
          status: this.resp.status,
          division: this.resp.division,
          executionAgency: this.resp.executionAgency,
          TKM: this.resp.tkm,
          RKM: this.resp.rkm,
          crsInspection: this.resp.crsInspection,
          crsAuthorisation: this.resp.crsAuthorisation,
          targetSetBy: this.resp.targetSetBy,
          doublingTrippling: this.resp.doublingTrippling,
          state: this.resp.state,
          phase: this.resp.phase,
          proposalScheme: this.resp.proposalScheme,
          sanctionByBoard: this.resp.sanctionByBoard,
          yearOfSanction: this.resp.yearOfSanction,
          dateOfCompletion: !!this.resp.dateOfCompletion ? new Date(this.resp.dateOfCompletion) : '',
        });
        this.toMinDate = new Date(this.resp.targetDate);
        this.spinnerService.hide();
      })
  }
  findGuage(){
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_STATUS_ITEM + Constants.STATUS_ITEMS.GAUGE_TYPE)
    .subscribe((resp) => {
      this.guageList = resp;
    });
  }

  findExecutionAgency(){
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_STATUS_ITEM + Constants.STATUS_ITEMS.ELECTRIFICATION_EXEC_AGENCY_TYPE)
    .subscribe((resp) => {
      this.executionAgencyList = resp;
    });
  }

  findDoublingTrippling(){
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_STATUS_ITEM + Constants.STATUS_ITEMS.DOUBLE_TRIPLE_TYPE)
    .subscribe((resp) => {
      this.doublingTripplingList = resp;
    });
  }

  findDivisions(){
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_ONLY_SCR_DIVISIONS)
    .subscribe((resp) => {
      this.divisionList = resp;
    });
  }

  onAddElectrificationTargetsFormSubmit() {

    if (this.addDriveElectrificationTargetsFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    var data = {};
    var message = '';
    var failedMessage = '';
    if (this.save) {
      data = {
        "section": this.addDriveElectrificationTargetsFormGroup.value.section,
        "guage": this.addDriveElectrificationTargetsFormGroup.value.guage,
        "targetDate": this.addDriveElectrificationTargetsFormGroup.value.targetDate,
        "status": this.addDriveElectrificationTargetsFormGroup.value.status,
        "division": this.addDriveElectrificationTargetsFormGroup.value.division,
        "executionAgency": this.addDriveElectrificationTargetsFormGroup.value.executionAgency,
        "tkm": this.addDriveElectrificationTargetsFormGroup.value.TKM,
        "rkm": this.addDriveElectrificationTargetsFormGroup.value.RKM,
        "crsInspection": this.addDriveElectrificationTargetsFormGroup.value.crsInspection,
        "crsAuthorisation": this.addDriveElectrificationTargetsFormGroup.value.crsAuthorisation,
        "targetSetBy": this.addDriveElectrificationTargetsFormGroup.value.targetSetBy,
        "doublingTrippling": this.addDriveElectrificationTargetsFormGroup.value.doublingTrippling,
        "state": this.addDriveElectrificationTargetsFormGroup.value.state,
        "phase": this.addDriveElectrificationTargetsFormGroup.value.phase,
        "proposalScheme": this.addDriveElectrificationTargetsFormGroup.value.proposalScheme,
        "sanctionByBoard": this.addDriveElectrificationTargetsFormGroup.value.sanctionByBoard,
        "yearOfSanction": this.addDriveElectrificationTargetsFormGroup.value.yearOfSanction,
        "dateOfCompletion": this.addDriveElectrificationTargetsFormGroup.value.dateOfCompletion,
        "createdBy": this.loggedUserData.username,
        "createdOn": new Date()
      }    
      message = 'Saved';
      failedMessage = "Saving";
      this.sendAndRequestService.requestForPOST(Constants.app_urls.DRIVE.ELECTRIFICATION_TARGETS.SAVE ,data, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Electrification Targets Data "+message+" Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Electrification Targets Data "+failedMessage+" Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Electrification Targets Data "+failedMessage+" Failed.");
      })
    }else if(this.update){
      data = {
        "id":this.id,
        "section": this.addDriveElectrificationTargetsFormGroup.value.section,
        "guage": this.addDriveElectrificationTargetsFormGroup.value.guage,
        "targetDate": this.addDriveElectrificationTargetsFormGroup.value.targetDate,
        "status": this.addDriveElectrificationTargetsFormGroup.value.status,
        "division": this.addDriveElectrificationTargetsFormGroup.value.division,
        "executionAgency": this.addDriveElectrificationTargetsFormGroup.value.executionAgency,
        "tkm": this.addDriveElectrificationTargetsFormGroup.value.TKM,
        "rkm": this.addDriveElectrificationTargetsFormGroup.value.RKM,
        "crsInspection": this.addDriveElectrificationTargetsFormGroup.value.crsInspection,
        "crsAuthorisation": this.addDriveElectrificationTargetsFormGroup.value.crsAuthorisation,
        "targetSetBy": this.addDriveElectrificationTargetsFormGroup.value.targetSetBy,
        "doublingTrippling": this.addDriveElectrificationTargetsFormGroup.value.doublingTrippling,
        "state": this.addDriveElectrificationTargetsFormGroup.value.state,
        "phase": this.addDriveElectrificationTargetsFormGroup.value.phase,
        "proposalScheme": this.addDriveElectrificationTargetsFormGroup.value.proposalScheme,
        "sanctionByBoard": this.addDriveElectrificationTargetsFormGroup.value.sanctionByBoard,
        "yearOfSanction": this.addDriveElectrificationTargetsFormGroup.value.yearOfSanction,
        "dateOfCompletion": this.addDriveElectrificationTargetsFormGroup.value.dateOfCompletion,
        "updatedBy": this.loggedUserData.username,
        "updatedOn": new Date()
      }   
      message = 'Updated';
      failedMessage = "Updating";
      this.sendAndRequestService.requestForPUT(Constants.app_urls.DRIVE.ELECTRIFICATION_TARGETS.UPDATE ,data, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Electrification Targets Data "+message+" Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Electrification Targets Data "+failedMessage+" Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Electrification Targets Data "+failedMessage+" Failed.");
      })
    }
    
  }
  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }
  
}
