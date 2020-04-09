import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { DrivesService } from 'src/app/services/drives.service';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-add-drive-electrification-targets',
  templateUrl: './add-drive-electrification-targets.component.html',
  styleUrls: ['./add-drive-electrification-targets.component.css']
})
export class AddDriveElectrificationTargetsComponent implements OnInit {

  saveUser: boolean = true;
  updateUser: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;

  addDriveElectrificationTargetsFormGroup: FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  stateList = [{ 'id': 1, "value": 'Yes' }, { 'id': 2, "value": 'No' }];

  electrificationTargetsFormErrors: any;
  resp: any;

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
    if (!isNaN(this.id)) {
      this.addDriveElectrificationTargetsFormGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      });
      this.spinnerService.show();
      this.saveUser = false;
      this.updateUser = true;
      this.getElectrificationTargetsDataById(this.id);

    } else {


    }
  }

  createForm() {
    this.addDriveElectrificationTargetsFormGroup
      = this.formBuilder.group({
        id: 0,
        'section': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
        'guage': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.pattern(this.pattern)])],
        'targetDate': [null, Validators.required],
        'status': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
        'division': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.pattern(this.pattern)])],
        'executionAgency': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
        'TKM': [null, Validators.required],
        'RKM': [null, Validators.required],
        'crsInspection': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
        'crsAuthorisation': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.pattern(this.pattern)])],
        'targetSetBy': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
        'doublingTrippling': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
        'state': [null, Validators.required],
        'phase': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
        'proposalScheme': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
        'sanctionByBoard': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.pattern(this.pattern)])],
        'yearOfSanction': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
        'dateOfCompletion': [null, Validators.required],
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
        console.log('depoTypes = ' + JSON.stringify(resp));
        this.resp = resp;
        this.addDriveElectrificationTargetsFormGroup.patchValue({
          id: this.resp.id,
          section: this.resp.section,
          guage: this.resp.guage,
          targetDate: new Date(this.resp.targetDate),
          status: this.resp.status,
          division: this.resp.division,
          executionAgency: this.resp.executionAgency,
          tkm: this.resp.tkm,
          rkm: this.resp.rkm,
          crsInspection: this.resp.crsInspection,
          crsAuthorisation: this.resp.crsAuthorisation,
          targetSetBy: this.resp.targetSetBy,
          doublingTrippling: this.resp.doublingTrippling,
          state: this.resp.state,
          phase: this.resp.phase,
          proposalScheme: this.resp.proposalScheme,
          sanctionByBoard: this.resp.sanctionByBoard,
          yearOfSanction: this.resp.yearOfSanction,
          dateOfCompletion: new Date(this.resp.dateOfCompletion),
        });
        this.spinnerService.hide();
      })
  }

  onAddElectrificationTargetsFormSubmit() {

    if (this.addDriveElectrificationTargetsFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    console.log(this.addDriveElectrificationTargetsFormGroup.value);
    var data = {};
    var message = '';
    var failedMessage = '';
    if (this.saveUser) {
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
        "createdBy": "1",
        "createdOn": "2020-04-01",
        "updatedBy": "1",
        "updatedOn": "2020-04-01"
      }    
      message = 'Saved';
      failedMessage = "Saving";
      this.drivesService.saveElectrificationTargetsData(data).subscribe(response => {
        console.log(JSON.stringify(response));
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Electrification Targets Data "+message+" Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Electrification Targets Data "+failedMessage+" Failed.");
      })
    }else if(this.updateUser){
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
        "createdBy": "1",
        "createdOn": "2020-04-01",
        "updatedBy": "1",
        "updatedOn": "2020-04-01"
      }   
      message = 'Updated';
      failedMessage = "Updating";
      this.drivesService.updateElectrificationTargetsData(data).subscribe(response => {
        console.log(JSON.stringify(response));
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Electrification Targets Data "+message+" Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
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
