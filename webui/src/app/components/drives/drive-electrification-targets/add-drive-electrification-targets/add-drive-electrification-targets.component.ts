import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { DrivesService } from 'src/app/services/drives.service';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Constants } from 'src/app/common/constants';

@Component({
  selector: 'app-add-drive-electrification-targets',
  templateUrl: './add-drive-electrification-targets.component.html',
  styleUrls: ['./add-drive-electrification-targets.component.css']
})
export class AddDriveElectrificationTargetsComponent implements OnInit {
  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  title:string;
  addDriveElectrificationTargetsFormGroup: FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  stateList = [{ 'id': 1, "value": 'Yes' }, { 'id': 2, "value": 'No' }];

  electrificationTargetsFormErrors: any;
  resp: any;
  guageList:any;
  executionAgencyList:any;
  doublingTripplingList:any;
  divisionList:any;
  //stateList: any;
  constructor(
    private formBuilder: FormBuilder,    
    private drivesService: DrivesService,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private route: ActivatedRoute,
    private router: Router,
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
      this.title = 'Edit';
      this.getElectrificationTargetsDataById(this.id);
    } else {
      this.save = true;
      this.update = false;
      this.title = 'Save';
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
        'phase': [null],
        'proposalScheme': [null],
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

  getElectrificationTargetsDataById(id) {
    this.drivesService.findElectrificationTargetsDataById(id)
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
        this.spinnerService.hide();
      })
  }
  findGuage(){
    this.drivesService.findStatusItem(Constants.STATUS_ITEMS.GAUGE_TYPE)
    .subscribe((resp) => {
      this.guageList = resp;
    });
  }

  findExecutionAgency(){
    this.drivesService.findStatusItem(Constants.STATUS_ITEMS.ELECTRIFICATION_EXEC_AGENCY_TYPE)
    .subscribe((resp) => {
      this.executionAgencyList = resp;
    });
  }

  findDoublingTrippling(){
    this.drivesService.findStatusItem(Constants.STATUS_ITEMS.DOUBLE_TRIPLE_TYPE)
    .subscribe((resp) => {
      this.doublingTripplingList = resp;
    });
  }

  findDivisions(){
    this.drivesService.findDivisions()
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
        "createdBy": this.loggedUserData.id,
        "createdOn": new Date()
      }    
      message = 'Saved';
      failedMessage = "Saving";
      this.drivesService.saveElectrificationTargetsData(data).subscribe(response => {
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
        "updatedBy": this.loggedUserData.id,
        "updatedOn": new Date()
      }   
      message = 'Updated';
      failedMessage = "Updating";
      this.drivesService.updateElectrificationTargetsData(data).subscribe(response => {
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
