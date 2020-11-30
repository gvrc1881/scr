import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { FacilityModel } from 'src/app/models/facility.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog, DateAdapter, MAT_DATE_FORMATS } from '@angular/material';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';

@Component({
  selector: 'app-add-project',
  templateUrl: './add-projects.component.html',
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
export class AddProjectComponent implements OnInit { 

    FiledLabels = FieldLabelsConstant.LABELS;
    Titles = FieldLabelsConstant.TITLE;
    save: boolean = true;
    update: boolean = false;
    id: number = 0;
    isSubmit: boolean = false;
    loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
    resp: any;
    title:string;
    addProjectFormGroup: FormGroup;
    addProjectsFormErrors:any;
    currentYear: any;
    pattern = "[a-zA-Z][a-zA-Z ]*";
    statusItems: any;
    execAgencyList: any;
    userHierarchy:any = JSON.parse(localStorage.getItem('userHierarchy'));
    //divisionList:  FacilityModel [] = [];
    divisionList:any;
    maxDate = new Date();
  toMinDate=new Date();
  completeMinDate=new Date();  
  toComDate=new Date();
  dateFormat = 'dd-MM-yyyy';
  currentDate = new Date();
  expectDate=new Date();
  completeDate = new Date();
  toTargetDate=new Date();
    constructor(
      private formBuilder: FormBuilder,
      private spinnerService: Ng4LoadingSpinnerService,
      private commonService: CommonService,
      private route: ActivatedRoute,
      private router: Router,
      private sendAndRequestService:SendAndRequestService
    ) {
      this.addProjectsFormErrors = {            
        workId:{},
        workGroup: {},
        section: {},
        agency:{},
        doublingTrippling:{},
        division: {},
        code:{},
        description:{},
        tkm:{},
        rkm:{},
        sidingYardStation:{}
      };
      
    }
    
  

  ngOnInit() {

    this.findDivisions();
   this.id = +this.route.snapshot.params['id'];
    
  if (!isNaN(this.id)) {  
    this.updateProjectForm();
    this.addProjectsFormErrors.valueChanges.subscribe(() => {
      this.onFormValuesChanged();
    });   
    this.spinnerService.show();
    this.save = false;
    this.update = true;
    this.title = Constants.EVENTS.UPDATE;
    this.getProjectDataById(this.id);
  } else {
    this.createProjectForm();
    this.save = true;
    this.update = false;
    this.title = Constants.EVENTS.ADD;
  }
  this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_STATUS_ITEM + 'WORK_PROGRESS_STATUS').subscribe((data) => {
    this.statusItems = data;
 });
 this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_STATUS_ITEM +'ELECTRIFICATION_EXEC_AGENCY').subscribe((data) => {
this.execAgencyList = data;
},  error => {
   this.commonService.showAlertMessage("Error in Get")
});
this.spinnerService.show();	
}


onFormValuesChanged() {
  for (const field in this.addProjectsFormErrors) {
    if (!this.addProjectsFormErrors.hasOwnProperty(field)) {
      continue;
    }
    this.addProjectsFormErrors[field] = {};
    const control = this.addProjectsFormErrors.get(field);

    if (control && control.dirty && !control.valid) {
      this.addProjectsFormErrors[field] = control.errors;
    }
  }
}

addEvent($event) {
  this.toMinDate = new Date($event.value); 
  
}
addTargetEvent($event) {

  this.toMinDate = new Date($event.value);

  this.toTargetDate = new Date($event.value);
  
}




createProjectForm(){
    this.addProjectFormGroup = this.formBuilder.group({
        id: 0,
        "allocation" : [null, Validators.maxLength(250)],
         "division" : [null],
        "estdLatestAnticCost" : [null],
        "executedBy" : [null, Validators.maxLength(250) ],
        "executingAgency" : [null],
        "financialProgressPercentage" : [null,Validators.max(100)],
        "latestRevisedCost" : [null],
        "pbLawLswp" : [null],
        "pbLawLswpCode" : [null],
        "physicalProgressPercentage" : [null,Validators.max(100)],
        "presentStatus" : [null],
        "reWorks" : [null],
        "rkm" : [null],
        "sanctionCost" : [null],
        "section" : [ null, Validators.maxLength(250)],
        "statusRemarks" : [null , Validators.maxLength(250)],
        "targetStartDate" : [null],
        "targetDateOfCompletion" : [null],
        "tkm" : [null],
        "workName" : [null,  Validators.compose([Validators.required, Validators.maxLength(250)]),this.duplicateWorkName.bind(this)],
        "yearOfSanction" : [null, Validators.compose([Validators.min(2000), Validators.max(this.currentYear)]) ],
        "commencementDate" :[null],
        "endKm" :[null],
        "expectedCompletion":[null,Validators.compose([Validators.required])],
        "lineType" :[null],
        "loaDate" :[null],
        "loaNo":[null],
        "startKm" :[null],
        "tenderValue" :[null],
    
    });
  }


  updateProjectForm(){
    this.addProjectFormGroup = this.formBuilder.group({
      id: 0,
      "allocation" : [null, Validators.maxLength(250)],
      "division" : [null],
     "estdLatestAnticCost" : [null],
     "executedBy" : [null, Validators.maxLength(250) ],
     "executingAgency" : [null],
     "financialProgressPercentage" : [null,Validators.max(100)],
     "latestRevisedCost" : [null],
     "pbLawLswp" : [null],
     "pbLawLswpCode" : [null],
     "physicalProgressPercentage" : [null,Validators.max(100)],
     "presentStatus" : [null],
     "reWorks" : [null],
     "rkm" : [null],
     "sanctionCost" : [null],
     "section" : [ null, Validators.maxLength(250)],
     "statusRemarks" : [null , Validators.maxLength(250)],
     "targetStartDate" : [null],
     "targetDateOfCompletion" : [null],
     "tkm" : [null],
     "workName" : [null,  Validators.compose([Validators.required, Validators.maxLength(250)]),this.duplicateWorkNameAndId.bind(this)],
     "yearOfSanction" : [null, Validators.compose([Validators.min(2000), Validators.max(this.currentYear)]) ],
     "commencementDate" :[null],
     "endKm" :[null],
     "expectedCompletion":[null,Validators.compose([Validators.required])],
     "lineType" :[null],
     "loaDate" :[null],
     "loaNo":[null],
     "startKm" :[null],
     "tenderValue" :[null],
    });
  }

  getProjectDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WORK_ID+id)
    .subscribe((resp) => {
        this.resp = resp;

        this.toMinDate=new Date(this.resp.targetStartDate),
        this.toTargetDate=new Date(this.resp.commencementDate),
        this.addProjectFormGroup.patchValue({
          id: this.resp.id,
          allocation : this.resp.allocation,
          division : this.resp.division,
         estdLatestAnticCost : this.resp.estdLatestAnticCost,
         executedBy : this.resp.executedBy,
         executingAgency : this.resp.executingAgency,
         financialProgressPercentage : this.resp.financialProgressPercentage,
         latestRevisedCost : this.resp.latestRevisedCost,
         pbLawLswp : this.resp.pbLawLswp,
         pbLawLswpCode : this.resp.pbLawLswpCode,
         physicalProgressPercentage :this.resp.physicalProgressPercentage,
         presentStatus : this.resp.presentStatus,
         reWorks : this.resp.reWorks,
         rkm : this.resp.rkm,
         sanctionCost : this.resp.sanctionCost,
         section : this.resp.section,
         statusRemarks :this.resp.statusRemarks,
         targetStartDate : this.resp.targetStartDate,
         targetDateOfCompletion : this.resp.targetDateOfCompletion,
         tkm : this.resp.tkm,
         workName :this.resp.workName,
         yearOfSanction :this.resp.yearOfSanction,
         commencementDate :this.resp.commencementDate,
         endKm :this.resp.endKm,
         expectedCompletion:this.resp.expectedCompletion,
         lineType :this.resp.lineType,
         loaDate :this.resp.loaDate,
         loaNo:this.resp.loaNo,
         startKm :this.resp.startKm,
         tenderValue :this.resp.tenderValue,                      
        });
        this.spinnerService.hide();
      })
  }
  
  projectSubmit() {
    this.isSubmit = true;
    if (this.addProjectFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    if (this.save) {
      var saveProject = {
        "allocation" : this.addProjectFormGroup.value.allocation,
        "division" : this.addProjectFormGroup.value.division,
       "estdLatestAnticCost" : this.addProjectFormGroup.value.estdLatestAnticCost,
       "executedBy" : this.addProjectFormGroup.value.executedBy,
       "executingAgency" : this.addProjectFormGroup.value.executingAgency,
       "financialProgressPercentage" : this.addProjectFormGroup.value.financialProgressPercentage,
       "latestRevisedCost" : this.addProjectFormGroup.value.latestRevisedCost,
       "pbLawLswp" : this.addProjectFormGroup.value.pbLawLswp,
       "pbLawLswpCode" : this.addProjectFormGroup.value.pbLawLswpCode,
       "physicalProgressPercentage" :this.addProjectFormGroup.value.physicalProgressPercentage,
       "presentStatus" : this.addProjectFormGroup.value.presentStatus,
       "reWorks" : this.addProjectFormGroup.value.reWorks,
       "rkm" : this.addProjectFormGroup.value.rkm,
       "sanctionCost" : this.addProjectFormGroup.value.sanctionCost,
       "section" : this.addProjectFormGroup.value.section,
       "statusRemarks" :this.addProjectFormGroup.value.statusRemarks,
       "targetStartDate" : this.addProjectFormGroup.value.targetStartDate,
       "targetDateOfCompletion" : this.addProjectFormGroup.value.targetDateOfCompletion,
       "tkm" : this.addProjectFormGroup.value.tkm,
       "workName" :this.addProjectFormGroup.value.workName,
       "yearOfSanction" :this.addProjectFormGroup.value.yearOfSanction,
       "commencementDate" :this.addProjectFormGroup.value.commencementDate,
       "endKm" :this.addProjectFormGroup.value.endKm,
       "expectedCompletion":this.addProjectFormGroup.value.expectedCompletion,
       "lineType" :this.addProjectFormGroup.value.lineType,
       "loaDate" :this.addProjectFormGroup.value.loaDate,
       "loaNo":this.addProjectFormGroup.value.loaNo,
       "startKm" :this.addProjectFormGroup.value.startKm,
       "tenderValue" :this.addProjectFormGroup.value.tenderValue,                                                                         
        "createdOn": new Date(),
        "createdBy":this.loggedUserData.username
             }
      this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.SAVE_WORK, saveProject, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
     
        if (this.resp.code == Constants.CODES.SUCCESS) {
          this.commonService.showAlertMessage("Project Data Saved Successfully");
          this.router.navigate(['../'], { relativeTo: this.route });
        } else {
          this.commonService.showAlertMessage("Project Data Saving Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Project Data Saving Failed.");
      });
    } else if (this.update) {
      var updateProject = {
        "id": this.id,
        "allocation" : this.addProjectFormGroup.value.allocation,
        "division" : this.addProjectFormGroup.value.division,
       "estdLatestAnticCost" : this.addProjectFormGroup.value.estdLatestAnticCost,
       "executedBy" : this.addProjectFormGroup.value.executedBy,
       "executingAgency" : this.addProjectFormGroup.value.executingAgency,
       "financialProgressPercentage" : this.addProjectFormGroup.value.financialProgressPercentage,
       "latestRevisedCost" : this.addProjectFormGroup.value.latestRevisedCost,
       "pbLawLswp" : this.addProjectFormGroup.value.pbLawLswp,
       "pbLawLswpCode" : this.addProjectFormGroup.value.pbLawLswpCode,
       "physicalProgressPercentage" :this.addProjectFormGroup.value.physicalProgressPercentage,
       "presentStatus" : this.addProjectFormGroup.value.presentStatus,
       "reWorks" : this.addProjectFormGroup.value.reWorks,
       "rkm" : this.addProjectFormGroup.value.rkm,
       "sanctionCost" : this.addProjectFormGroup.value.sanctionCost,
       "section" : this.addProjectFormGroup.value.section,
       "statusRemarks" :this.addProjectFormGroup.value.statusRemarks,
       "targetStartDate" : this.addProjectFormGroup.value.targetStartDate,
       "targetDateOfCompletion" : this.addProjectFormGroup.value.targetDateOfCompletion,
       "tkm" : this.addProjectFormGroup.value.tkm,
       "workName" :this.addProjectFormGroup.value.workName,
       "yearOfSanction" :this.addProjectFormGroup.value.yearOfSanction,
       "commencementDate" :this.addProjectFormGroup.value.commencementDate,
       "endKm" :this.addProjectFormGroup.value.endKm,
       "expectedCompletion":this.addProjectFormGroup.value.expectedCompletion,
       "lineType" :this.addProjectFormGroup.value.lineType,
       "loaDate" :this.addProjectFormGroup.value.loaDate,
       "loaNo":this.addProjectFormGroup.value.loaNo,
       "startKm" :this.addProjectFormGroup.value.startKm,
       "tenderValue" :this.addProjectFormGroup.value.tenderValue,                                                    
        "modifiedOn": new Date(),
        "updatedBy":this.loggedUserData.username
      }
      this.sendAndRequestService.requestForPUT(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.UPDATE_WORK, updateProject, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Project Data Updated Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Project Data Updating Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Project Data Updating Failed.");
      })

    }
  }

  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }

findDivisions()
{
  this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.GET_DIVISIONS)
  .subscribe((resp) => {
    this.divisionList = resp;
  });
}
duplicateWorkName() {
  const q = new Promise((resolve, reject) => {
    let work: string = this.addProjectFormGroup.controls['workName'].value;   
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.EXISTS_WORK_NAME +
      this.addProjectFormGroup.controls['workName'].value
    ).subscribe((duplicate) => {
      if (duplicate) {
        resolve({ 'duplicateWorkName': true });
      } else {
        resolve(null);
      }
    }, () => { resolve({ 'duplicateWorkName': true }); });
  });
  return q;
}

duplicateWorkNameAndId() {
  const q = new Promise((resolve, reject) => {
    let work: string = this.addProjectFormGroup.controls['workName'].value;

    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.EXISTS_WORK_NAME_AND_ID +this.id+'/'+
      this.addProjectFormGroup.controls['workName'].value
    ).subscribe((duplicate) => {
      if (duplicate) {
        resolve({ 'duplicateWorkNameAndId': true });
      } else {
        resolve(null);
      }
    }, () => { resolve({ 'duplicateWorkNameAndId': true }); });
  });
  return q;
}

}