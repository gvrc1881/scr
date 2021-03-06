import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';


@Component({
  selector: 'app-add-check-points',
  templateUrl: './add-check-points.component.html',
  styleUrls: [],
})

export class AddCheckPointsComponent implements OnInit {

    FiledLabels = FieldLabelsConstant.LABELS;
    Titles = FieldLabelsConstant.TITLE;
    save: boolean = true;
    update: boolean = false;
    id: number = 0;
    isSubmit: boolean = false;
    depotData: any = JSON.parse(sessionStorage.getItem('depotData'));

     filterDepot: any = this.depotData.filter(u => 
      u.depotType == 'SP' || u.depotType == 'SSP' || u.depotType == 'TSS');
   

    loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
    resp: any;    
    title:string;      
    addCheckPointsFormGroup: FormGroup;
    checkPointFormErrors:any;
    pattern = "[a-zA-Z][a-zA-Z ]*";
    facilityData: any;
    facilityList:any;
    comparisonPointsData:any;
    dependencyValidation:boolean=false;
    public tempDiff = ['YES','NO'];

  constructor( private formBuilder: FormBuilder,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private route: ActivatedRoute,
    private router: Router,
    private sendAndRequestService:SendAndRequestService
   
  ) { 
    this.checkPointFormErrors = {            
      facilityId:{},
      checkPointPart: {},
      checkPointDescription: {},
      commparisonPoints:{},
      typOfCheckPoint:{},
      displayOrder:{},
      };
  }

  ngOnInit() { 
    this.id = +this.route.snapshot.params['id'];
      if (!isNaN(this.id)) { 
    
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;
      
    } else {
      this.createCheckPointsForm();         
      this.title = Constants.EVENTS.ADD;
    }
    
  }
 
  
  onFormValuesChanged() {
    for (const field in this.checkPointFormErrors) {
      if (!this.checkPointFormErrors.hasOwnProperty(field)) {
        continue;
      }
      this.checkPointFormErrors[field] = {};
      const control = this.addCheckPointsFormGroup.get(field);

      if (control && control.dirty && !control.valid) {
        this.checkPointFormErrors[field] = control.errors;
      }
    }
  }

createCheckPointsForm() {
this.addCheckPointsFormGroup = this.formBuilder.group({
  id: 0, 
  'facilityId':[null, Validators.compose([Validators.required])],
  'checkPointPart': [null, Validators.compose([Validators.required])],
  'checkPoint1Description': [null,Validators.required,this.duplicateFacilityIdCheckPointPartAndCheckPoint1Description.bind(this)],
  'checkPoint2Description': [null,Validators.required,this.duplicateFacilityIdCheckPointPartAndCheckPoint2Description.bind(this)],

  //'commparisonPoints':[null],
  //'typeOfCheckPoint':[null],
  'displayOrder':[null],
  //'displayOfTempDiff':['NO']
});
}

checkPointSubmit() {
this.isSubmit = true;
if (this.addCheckPointsFormGroup.invalid) {
  this.isSubmit = false;
  return;
}
this.spinnerService.show();

  if (this.save) {
    var saveCheckPointsModel = {
      "active":"Yes",
      "facilityId": this.addCheckPointsFormGroup.value.facilityId,
      "checkPointPart": this.addCheckPointsFormGroup.value.checkPointPart,
      "checkPoint1Description": this.addCheckPointsFormGroup.value.checkPoint1Description,
      "checkPoint2Description": this.addCheckPointsFormGroup.value.checkPoint2Description,
      //"commparisonPoints": this.addCheckPointsFormGroup.value.commparisonPoints,
      //"typeOfCheckPoint": this.addCheckPointsFormGroup.value.typeOfCheckPoint,
      "displayOrder": this.addCheckPointsFormGroup.value.displayOrder,
      //"displayOfTempDiff":this.addCheckPointsFormGroup.value.displayOfTempDiff
    }
    this.sendAndRequestService.requestForPOST(Constants.app_urls.THERMOVISION.THERMOVISION_CHECK_POINTS.SAVE_CHECK_POINTS, saveCheckPointsModel, false).subscribe(response => {
      this.spinnerService.hide();
      this.resp = response;
   console.log("saveCheckPointsModel"+saveCheckPointsModel.facilityId)
      if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Check Point Data Saved Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
      } else {
        this.commonService.showAlertMessage("Check Point Saving Failed.");
      }
    }, error => {
      console.log('ERROR >>>');
      this.spinnerService.hide();
      this.commonService.showAlertMessage("Check Point Saving Failed");
    });
  }
}

onGoBack() {
this.router.navigate(['../'], { relativeTo: this.route });
}
depotTypeForOhe()
  {  
         this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_DEPOTTYPE_FOR_OHE).subscribe((data) => {
           this.facilityData = data;
  }
         );

 }
 getComparisonPoints(){
  var facilityId = this.addCheckPointsFormGroup.value.facilityId.id ;
  console.log("facilityId"+facilityId);
this.sendAndRequestService.requestForGET(Constants.app_urls.THERMOVISION.THERMOVISION_CHECK_POINTS.GET_CHECK_POINTS_BASED_ON_FACILITY_ID+facilityId).subscribe((data) => {
           this.comparisonPointsData = data;
      });
}
public checkPoints = ['FIXED', 'FIXED_CClamp','CClamp'];



duplicateFacilityIdCheckPointPartAndCheckPoint1Description() {
  const q = new Promise((resolve, reject) => {
     this.sendAndRequestService.requestForGET(Constants.app_urls.THERMOVISION.THERMOVISION_CHECK_POINTS.EXIST_FACILITY_AND_CHECK_POINT_DESCRIPTION1 +
      this.addCheckPointsFormGroup.controls['facilityId'].value.id + '/'+
      this.addCheckPointsFormGroup.controls['checkPointPart'].value + '/'+this.addCheckPointsFormGroup.controls['checkPoint1Description'].value
    ).subscribe((duplicate) => {
      if (duplicate) {
        resolve({ 'duplicate': true });
      } else {
        resolve(null);
      }
    }, () => { resolve({ 'duplicate': true }); });
  });
  return q;
}

  
duplicateFacilityIdCheckPointPartAndCheckPoint2Description() {
  const q = new Promise((resolve, reject) => {
     this.sendAndRequestService.requestForGET(
            Constants.app_urls.THERMOVISION.THERMOVISION_CHECK_POINTS.EXIST_FACILITY_AND_CHECK_POINT_DESCRIPTION2+
          this.addCheckPointsFormGroup.controls['facilityId'].value.id + '/'+
          this.addCheckPointsFormGroup.controls['checkPointPart'].value+'/'+
          this.addCheckPointsFormGroup.controls['checkPoint2Description'].value
    ).subscribe((duplicate) => {
      if (duplicate) {
        resolve({ 'duplicate': true });
      } else {
        resolve(null);
      }
    }, () => { resolve({ 'duplicate': true }); });
  });
  return q;
  }
  

}
