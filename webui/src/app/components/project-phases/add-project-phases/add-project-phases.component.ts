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
  selector: 'app-add-project-phases',
  templateUrl: './add-project-phases.component.html',
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

export class AddProjectPhasesComponent implements OnInit {

    FiledLabels = FieldLabelsConstant.LABELS;
    Titles = FieldLabelsConstant.TITLE;
    save: boolean = true;
    update: boolean = false;
    id: number = 0;
    isSubmit: boolean = false;
    loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
    resp: any;    
    title:string;      
    workGroupData:any;   
    addProjectPhaseFormGroup: FormGroup;
    phaseFormErrors:any;
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
    dependencyValidation:boolean=false;

  constructor( private formBuilder: FormBuilder,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private route: ActivatedRoute,
    private router: Router,
    private sendAndRequestService:SendAndRequestService
   
  ) { 
    this.phaseFormErrors = {            
      workId:{},
        phaseName: {},
        description: {},
        sequence:{},
        dependencyToStart:{},
        weightage: {},
        status:{},
        plannedStartDate:{},
        targetCompletionDate:{},       
        commenceDate:{},
        completionDate:{}
      };
  }

  ngOnInit() {  
    
    
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WORK).subscribe((data) => {
        this.workGroupData = data;
        
    },error => {} );
    this.id = +this.route.snapshot.params['id'];
      if (!isNaN(this.id)) { 
    
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;
      
    } else {
      this.createGroupsSectionsForm();         
      this.title = Constants.EVENTS.ADD;
    }
    
  }
  addEvent($event) {
    this.toMinDate = new Date($event.value); 
    
  }
  addTargetEvent($event) {
    
    this.toMinDate = new Date($event.value); 
    
    this.toTargetDate = new Date($event.value);
    
  }
  onFormValuesChanged() {
    for (const field in this.phaseFormErrors) {
      if (!this.phaseFormErrors.hasOwnProperty(field)) {
        continue;
      }
      this.phaseFormErrors[field] = {};
      const control = this.addProjectPhaseFormGroup.get(field);

      if (control && control.dirty && !control.valid) {
        this.phaseFormErrors[field] = control.errors;
      }
    }
  }

createGroupsSectionsForm() {
this.addProjectPhaseFormGroup = this.formBuilder.group({
  id: 0, 
  'work':[null, Validators.compose([Validators.required])],
  'phaseName': [null, Validators.compose([Validators.required]),this.duplicateWorkIdAndName.bind(this)],
  'description': [null],
  'sequence':[null,Validators.compose([Validators.required]),this.duplicateWorkIdAndSequence.bind(this)],
  'dependencyToStart':[null],
  'weightage': [null],
  'status':[null],
  'plannedStartDate':[null],
  'targetCompletionDate':[null],   
   'commenceDate':[null],
   'completionDate':[null]  ,
   
});
}

projectPhaseSubmit() {
this.isSubmit = true;
if (this.addProjectPhaseFormGroup.invalid) {
  this.isSubmit = false;
  return;
}
this.spinnerService.show();

  if (this.save) {
    var savePhaseModel = {
      "workId": this.addProjectPhaseFormGroup.value.work,
      "phaseName": this.addProjectPhaseFormGroup.value.phaseName,
      "description": this.addProjectPhaseFormGroup.value.description,
      "sequence": this.addProjectPhaseFormGroup.value.sequence,
      "dependencyToStart": this.addProjectPhaseFormGroup.value.dependencyToStart,
      "weightage": this.addProjectPhaseFormGroup.value.weightage,
      "status": this.addProjectPhaseFormGroup.value.status,
      "plannedStartDate": this.addProjectPhaseFormGroup.value.plannedStartDate,
      "targetCompletionDate": this.addProjectPhaseFormGroup.value.targetCompletionDate,      
      "commenceDate":  this.addProjectPhaseFormGroup.value.commenceDate,    
      "completionDate":  this.addProjectPhaseFormGroup.value.completionDate,                                                       
      "createdBy": this.loggedUserData.username,
        "createdOn": new Date()

    }
    this.sendAndRequestService.requestForPOST(Constants.app_urls.PROJECT_ADMIN.PHASES.SAVE, savePhaseModel, false).subscribe(response => {
      this.spinnerService.hide();
      this.resp = response;
   
      if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Project Phase Data Saved Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
      } else {
        this.commonService.showAlertMessage("Project Phase Saving Failed.");
      }
    }, error => {
      console.log('ERROR >>>');
      this.spinnerService.hide();
      this.commonService.showAlertMessage("Project Phase Saving Failed.");
    });
  }
}

onGoBack() {
this.router.navigate(['../'], { relativeTo: this.route });
}

duplicateWorkIdAndName() {
  const q = new Promise((resolve, reject) => {              

    let work  = this.addProjectPhaseFormGroup.value.work.id;
    let phaseName: string = this.addProjectPhaseFormGroup.controls['phaseName'].value;
    
    this.sendAndRequestService.requestForGET(Constants.app_urls.PROJECT_ADMIN.PHASES.EXIST_WORK_AND_PHASE_NAME+work+'/'+phaseName)
    .subscribe((duplicate) => {
      if (duplicate) {
        resolve({ 'duplicateWorkIdAndName': true });
      } else {
        resolve(null);
      }
    }, () => { resolve({ 'duplicateWorkIdAndName': true }); });
  });
  return q;
}

duplicateWorkIdAndSequence() {
  console.log('check');
  const q = new Promise((resolve, reject) => {              

    let work  = this.addProjectPhaseFormGroup.value.work.id;
    let sequence = this.addProjectPhaseFormGroup.controls['sequence'].value;
    
    this.sendAndRequestService.requestForGET(Constants.app_urls.PROJECT_ADMIN.PHASES.EXIST_WORK_AND_SEQUENCE+work+'/'+sequence)
    .subscribe((duplicate) => {
      if (duplicate) {
        resolve({ 'duplicateWorkIdAndSequence': true });
      } else {
        resolve(null);
      }
    }, () => { resolve({ 'duplicateWorkIdAndSequence': true }); });
  });
  return q;
}

test() {

  console.log("get sequence");
  console.log("seq=="+this.addProjectPhaseFormGroup.value.sequence);
   console.log("depen=="+this.addProjectPhaseFormGroup.value.dependencyToStart);
  if(this.addProjectPhaseFormGroup.value.sequence > this.addProjectPhaseFormGroup.value.dependencyToStart)
  {
    console.log("get sequence in if condition");
    this.dependencyValidation = false;

  }else{
    console.log("get sequence in else condition");
    this.dependencyValidation = true;
   
  }

}


}
