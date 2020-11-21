import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog, DateAdapter, MAT_DATE_FORMATS } from '@angular/material';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';

@Component({
  selector: 'add-project-phase-activity',
  templateUrl: './add-project-phase-activity.component.html',
  styleUrls: [],
  providers: [
    {
        provide: DateAdapter, useClass: AppDateAdapter
    },
    {
        provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
    }
    ]
})

export class AddProjectPhaseActivityComponent implements OnInit {
 
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  resp: any;    
  title:string;      
   workPhaseData:any; 
   workGroupData:any;
  addPhaseActivityFormGroup: FormGroup;
  phaseActivityFormErrors:any;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  selectedWork: any;
  maxDate = new Date();
  toMinDate=new Date();
  completeMinDate=new Date();  
  dateFormat = 'dd-MM-yyyy';
  currentDate = new Date();
  expectDate=new Date();
  completeDate = new Date();
  toTargetDate=new Date();
  isCheckList = [{ 'id': 1, "value": 'Yes' }, { 'id': 2, "value": 'No' }];
  isObjectIdRequired = [{ 'id': 1, "value": 'Yes' }, { 'id': 2, "value": 'No' }];
  depotType = [{ 'id': 1, "value": 'OHE' }, { 'id': 2, "value": 'PSI' },{ 'id': 3, "value": 'TSS'},{ 'id': 4, "value": 'SSP'},{ 'id': 5, "value": 'SP'}];

constructor( private formBuilder: FormBuilder,
  private spinnerService: Ng4LoadingSpinnerService,
  private commonService: CommonService,
  private route: ActivatedRoute,
  private router: Router,
  private sendAndRequestService:SendAndRequestService
 
) { 
  this.phaseActivityFormErrors = {            
   work:{},
    workPhaseId: {},
    name:{},
      description: {},
      sequence:{},
      dependencyToStart:{},
      uom: {},
      isCheckList:{},
      isObjectIdRequired:{},
      depotType:{},       
      assetType:{}
    };
}

ngOnInit() {      
  this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WORK).subscribe((data) => {
      this.workGroupData = data;
      
  },error => {} );
  this.id = +this.route.snapshot.params['id'];
    if (!isNaN(this.id)) {  
    this.updateActivityForm();  
    this.spinnerService.show();
    this.save = false;
    this.update = true;
    this.title = Constants.EVENTS.UPDATE;
    
  } else {
    this.createActivityForm();         
    this.title = Constants.EVENTS.ADD;
  }
  
}
getWorkPhase(){
  this.sendAndRequestService.requestForGET( Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WORK_PHASES_BASED_ON_WORK + this.addPhaseActivityFormGroup.value.work.id).subscribe((response) => {
      this.workPhaseData = response;
   });
}

onFormValuesChanged() {
  for (const field in this.phaseActivityFormErrors) {
    if (!this.phaseActivityFormErrors.hasOwnProperty(field)) {
      continue;
    }
    this.phaseActivityFormErrors[field] = {};
    const control = this.addPhaseActivityFormGroup.get(field);

    if (control && control.dirty && !control.valid) {
      this.phaseActivityFormErrors[field] = control.errors;
    }
  }
}

createActivityForm() {
this.addPhaseActivityFormGroup = this.formBuilder.group({
id: 0, 
'work':[null,Validators.compose([Validators.required])],
'workPhase':[null,Validators.compose([Validators.required])],
'name': [null, Validators.compose([Validators.required]),this.duplicateWorkPhaseIdAndName.bind(this)],
'description': [null],
'sequence':[null,Validators.compose([Validators.required]),this.duplicateWorkPhaseIdAndSequence.bind(this)],
'dependencyToStart':[null],
'uom': [null],
'isCheckList':[null],
'isObjectIdRequired':[null],
'depotType':[null],   
 'assetType':[null]
 
});
}

updateActivityForm() {
this.addPhaseActivityFormGroup = this.formBuilder.group({
id: 0,
'work':[null],
'workPhase':[null],
'name': [null, Validators.compose([Validators.required])],
'description': [null],
'sequence':[null],
'dependencyToStart':[null],
'uom': [null],
'isCheckList':[null],
'isObjectIdRequired':[null],
'depotType':[null],   
 'assetType':[null]
});
}

phaseActivitySubmit() {
this.isSubmit = true;
if (this.addPhaseActivityFormGroup.invalid) {
this.isSubmit = false;
return;
}
this.spinnerService.show();

if (this.save) {
  var savePhaseModel = {
    "workPhaseId": this.addPhaseActivityFormGroup.value.workPhase,
    "name": this.addPhaseActivityFormGroup.value.name,
    "description": this.addPhaseActivityFormGroup.value.description,
    "sequence": this.addPhaseActivityFormGroup.value.sequence,
    "dependencyToStart": this.addPhaseActivityFormGroup.value.dependencyToStart,
     "uom": this.addPhaseActivityFormGroup.value.uom,
    "isCheckList": this.addPhaseActivityFormGroup.value.isCheckList,
    "isObjectIdRequired": this.addPhaseActivityFormGroup.value.isObjectIdRequired,      
    "depotType":  this.addPhaseActivityFormGroup.value.depotType,    
    "assetType":  this.addPhaseActivityFormGroup.value.assetType,                                                       
    "createdBy": this.loggedUserData.username,
      "createdOn": new Date()

  }
  this.sendAndRequestService.requestForPOST(Constants.app_urls.PROJECT_ADMIN.PHASE_ACTIVITY.SAVE, savePhaseModel, false).subscribe(response => {
    this.spinnerService.hide();
    this.resp = response;
 
    if (this.resp.code == Constants.CODES.SUCCESS) {
      this.commonService.showAlertMessage("Project Phase Activity Data Saved Successfully");
      this.router.navigate(['../'], { relativeTo: this.route });
    } else {
      this.commonService.showAlertMessage("Project Phase Activity Saving Failed.");
    }
  }, error => {
    console.log('ERROR >>>');
    this.spinnerService.hide();
    this.commonService.showAlertMessage("Project Phase Activity Saving Failed.");
  });
}
}

onGoBack() {
this.router.navigate(['../'], { relativeTo: this.route });
}


duplicateWorkPhaseIdAndName() {
  const q = new Promise((resolve, reject) => {              

    let workPhase  = this.addPhaseActivityFormGroup.value.workPhase.id;
    let name: string = this.addPhaseActivityFormGroup.controls['name'].value;
    
    this.sendAndRequestService.requestForGET(Constants.app_urls.PROJECT_ADMIN.PHASE_ACTIVITY.EXIST_WORKPHASE_AND_NAME+workPhase+'/'+name)
    .subscribe((duplicate) => {
      if (duplicate) {
        resolve({ 'duplicateWorkPhaseIdAndName': true });
      } else {
        resolve(null);
      }
    }, () => { resolve({ 'duplicateWorkPhaseIdAndName': true }); });
  });
  return q;
}

duplicateWorkPhaseIdAndSequence() {
  const q = new Promise((resolve, reject) => {              

    let workPhase  = this.addPhaseActivityFormGroup.value.workPhase.id;
    let sequence = this.addPhaseActivityFormGroup.controls['sequence'].value;
    
    this.sendAndRequestService.requestForGET(Constants.app_urls.PROJECT_ADMIN.PHASE_ACTIVITY.EXIST_WORKPHASE_AND_SEQUENCE+workPhase+'/'+sequence)
    .subscribe((duplicate) => {
      if (duplicate) {
        resolve({ 'duplicateWorkPhaseIdAndSequence': true });
      } else {
        resolve(null);
      }
    }, () => { resolve({ 'duplicateWorkPhaseIdAndSequence': true }); });
  });
  return q;
}

}


