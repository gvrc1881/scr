import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';


@Component({
  selector: 'app-add-asset-monthly-targets',
  templateUrl: './add-asset-monthly-targets.component.html',
  styleUrls: [],
})

export class AddAssetMonthlyTargetsComponent implements OnInit {

    FiledLabels = FieldLabelsConstant.LABELS;
    Titles = FieldLabelsConstant.TITLE;
    save: boolean = true;
    update: boolean = false;
    id: number = 0;
    isSubmit: boolean = false;
    loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
    resp: any;    
    onlyYes:boolean = false;
    title:string;      
    scheduledData:any;   
    addAssetMonthlyTargetsFormGroup: FormGroup;
    phaseFormErrors:any;
    pattern = "[a-zA-Z][a-zA-Z ]*";
    selectedWork: any;
    dependencyValidation:boolean=false;

  constructor( private formBuilder: FormBuilder,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private route: ActivatedRoute,
    private router: Router,
    private sendAndRequestService:SendAndRequestService
   
  ) { 
    this.phaseFormErrors = {            
      assetType:{},
      scheduleType: {},
      totalPopulation: {},
      targetApr:{},
      targetMay:{},
      targetJune: {},
      targetJuly:{},
      targetAug:{},
      targetSep:{},       
      targetOct:{},
      targetNov:{},
      targetDec:{},
      targetJan:{},
      targetFeb:{},
      targetMar:{},
      };
  }

  ngOnInit() { 
        let isDpr = 'Y';
        this.sendAndRequestService.requestForGET(Constants.app_urls.OPERATIONS.ASSET_MONTHLY_TARGETS.GET_SCHEDULES_BASED_ON_DPR+isDpr).subscribe((data) => {
          this.scheduledData = data;
          console.log("scheduledData"+JSON.stringify(data))
        }) 
    this.id = +this.route.snapshot.params['id'];
      if (!isNaN(this.id)) { 
    
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;
      
    } else {
      this.createAssetMonthlyTargetsForm();         
      this.title = Constants.EVENTS.ADD;
    }
    
  }
  
  onFormValuesChanged() {
    for (const field in this.phaseFormErrors) {
      if (!this.phaseFormErrors.hasOwnProperty(field)) {
        continue;
      }
      this.phaseFormErrors[field] = {};
      const control = this.addAssetMonthlyTargetsFormGroup.get(field);

      if (control && control.dirty && !control.valid) {
        this.phaseFormErrors[field] = control.errors;
      }
    }
  }

createAssetMonthlyTargetsForm() {
this.addAssetMonthlyTargetsFormGroup = this.formBuilder.group({
  id: 0, 
  'assetType':[null, Validators.compose([Validators.required])],
  'scheduleType': [null, Validators.compose([Validators.required])],
  'targetPlanMonths': [null],
  'totalPopulation':[null,Validators.compose([Validators.required])],
  'targetApr':[null],
  'targetMay': [null],
  'targetJune':[null],
  'targetJuly':[null],
  'targetAug':[null],   
   'targetSep':[null],
   'targetOct':[null]  ,
   'targetNov':[null]  ,
   'targetDec':[null]  ,
   'targetJan':[null]  ,
   'targetFeb':[null]  ,
   'targetMar':[null]  ,
});
}

assetMonthlyTargetsSubmit() {
this.isSubmit = true;
if (this.addAssetMonthlyTargetsFormGroup.invalid) {
  this.isSubmit = false;
  return;
}
this.spinnerService.show();

  if (this.save) {
    var saveTargetsModel = {
      "assetType": this.addAssetMonthlyTargetsFormGroup.value.work,
      "scheduleType": this.addAssetMonthlyTargetsFormGroup.value.phaseName,
      "totalPopulation": this.addAssetMonthlyTargetsFormGroup.value.description,
      "targetApr": this.addAssetMonthlyTargetsFormGroup.value.targetApr,
      "targetMay": this.addAssetMonthlyTargetsFormGroup.value.targetMay,
      "targetJune": this.addAssetMonthlyTargetsFormGroup.value.targetJune,
      "targetJuly": this.addAssetMonthlyTargetsFormGroup.value.targetJuly,
      "targetAug": this.addAssetMonthlyTargetsFormGroup.value.targetAug,
      "targetSep": this.addAssetMonthlyTargetsFormGroup.value.targetSep,  
      "targetOct": this.addAssetMonthlyTargetsFormGroup.value.targetOct,      
      "targetNov": this.addAssetMonthlyTargetsFormGroup.value.targetNov,      
      "targetDec": this.addAssetMonthlyTargetsFormGroup.value.targetDec,      
      "targetJan": this.addAssetMonthlyTargetsFormGroup.value.targetJan,      
      "targetFeb": this.addAssetMonthlyTargetsFormGroup.value.targetFeb,      
      "targetMar": this.addAssetMonthlyTargetsFormGroup.value.targetMar,                                                                
      "createdBy": this.loggedUserData.username,
      "createdStamp": new Date(),
      "createdTxStamp":new Date(),

    }
    this.sendAndRequestService.requestForPOST(Constants.app_urls.OPERATIONS.ASSET_MONTHLY_TARGETS.SAVE_ASSET_MONTHLY_TARGETS, saveTargetsModel, false).subscribe(response => {
      this.spinnerService.hide();
      this.resp = response;
   
      if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Asset Monthly Targets Data Saved Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
      } else {
        this.commonService.showAlertMessage("Asset Monthly Targets Saving Failed.");
      }
    }, error => {
      console.log('ERROR >>>');
      this.spinnerService.hide();
      this.commonService.showAlertMessage("Asset Monthly Targets Saving Failed.");
    });
  }
}
changeDates() {
  if (this.addAssetMonthlyTargetsFormGroup.value.targetPlanMonths == '12') {     
      this.onlyYes = false;
  } else {
      this.onlyYes = true;
  }
      }
onGoBack() {
this.router.navigate(['../'], { relativeTo: this.route });
}





}
