import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';
import {  MatDialogRef, MatDialog ,DateAdapter, MAT_DATE_FORMATS} from '@angular/material';


@Component({
  selector: 'app-add-ohe-location',
  templateUrl: './add-ohe-location.component.html',
  providers: [
    {
        provide: DateAdapter, useClass: AppDateAdapter
    },
    {
        provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
    }
    ]
})
export class AddOheLocationComponent implements OnInit {

pagination = Constants.PAGINATION_NUMBERS;
 FiledLabels = FieldLabelsConstant.LABELS;
 Titles = FieldLabelsConstant.TITLE;
  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
  resp: any;
  oheFounData:any;
  structureTypeData:any;
  title:string = Constants.EVENTS.ADD;
  productCateData:any;
  productCateTypeData:any;
  addOheLocationFormGroup: FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  constructor(
    private formBuilder: FormBuilder,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private route: ActivatedRoute,
    private router: Router,
    private sendAndRequestService:SendAndRequestService
  ) {
    
  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
    this.oheFoundationDetails();
    this.mastStructureDetails();
    this.createOheLocationForm();
    if (!isNaN(this.id)) {     
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;
      this.getOheLocationDataById(this.id);
    } else {
      this.save = true;
      this.update = false;
      this.title = Constants.EVENTS.ADD;
    }
    
  }
  
  

  createOheLocationForm() {
    this.addOheLocationFormGroup = this.formBuilder.group({
      id: 0,
      'division':['', Validators.compose([Validators.required, Validators.maxLength(250)])],
      'section': ['', Validators.compose([Validators.required, Validators.maxLength(250)])],
      'pwi':[''],
      'trackLine':[''],
      'oheMast': ['', Validators.maxLength(255)],
      'structureType':[''],
      'engFeature':[''],
      'oheFeature':[''],
      'longitude':[''],
      'latitude':[''],
      'altitude':[''],
      'kilometer':[''],
      'date':[''],
      'validity':[''],
      'satellites':[''],
      'speed':[''],
      'heading':[''],
      'remarkOne':[''],
      'remarkTwo':[''],
      'oheSequence':[''],
      'curvature':[''],
      'curvatureRemark':[''],
      'chainage':[''],
      'chainageRemark':[''],
    });
  }
  getOheLocationDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.OHELOCATION.GET_OHE_LOCATION_ID+id)
    .subscribe((resp) => {
        this.resp = resp;
        this.addOheLocationFormGroup.patchValue({
          id: this.resp.id,
          division: this.resp.division,
          section: this.resp.section,
          pwi: this.resp.pwi,
          trackLine: this.resp.trackLine,
          oheMast: this.resp.oheMast,
          structureType: this.resp.structureType,
          engFeature: this.resp.engFeature,
          oheFeature: this.resp.oheFeature,
          longitude: this.resp.longitude,
          latitude: this.resp.latitude,
          altitude: this.resp.altitude,
          kilometer: this.resp.kilometer,
          date:new Date(this.resp.date), 
          validity: this.resp.validity,
          satellites: this.resp.satellites,
          speed: this.resp.speed,
          heading: this.resp.heading,
          remarkOne: this.resp.remarkOne,
          remarkTwo: this.resp.remarkTwo,
          oheSequence: this.resp.oheSequence,
          curvature: this.resp.curvature,
          curvatureRemark: this.resp.curvatureRemark,
          chainage: this.resp.chainage,
          chainageRemark: this.resp.chainageRemark,
        });
        this.spinnerService.hide();
      })
  }
  
  oheLocationFormSubmit() {
    this.isSubmit = true;
    if (this.addOheLocationFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    if (this.save) {
      var saveOheLocationModel = {
        "division": this.addOheLocationFormGroup.value.division,
        "section": this.addOheLocationFormGroup.value.section,
        "pwi": this.addOheLocationFormGroup.value.pwi,
        "trackLine": this.addOheLocationFormGroup.value.trackLine,
        "oheMast": this.addOheLocationFormGroup.value.oheMast,
        "structureType": this.addOheLocationFormGroup.value.structureType,
        "engFeature": this.addOheLocationFormGroup.value.engFeature,
        "oheFeature": this.addOheLocationFormGroup.value.oheFeature,
        "longitude": this.addOheLocationFormGroup.value.longitude,
        "latitude": this.addOheLocationFormGroup.value.latitude,
        "altitude": this.addOheLocationFormGroup.value.altitude,
        "kilometer": this.addOheLocationFormGroup.value.kilometer,
        "date": this.addOheLocationFormGroup.value.date,
        "validity": this.addOheLocationFormGroup.value.validity,  
        "satellites": this.addOheLocationFormGroup.value.satellites,
        "speed": this.addOheLocationFormGroup.value.speed,
        "heading": this.addOheLocationFormGroup.value.heading,
        "remarkOne": this.addOheLocationFormGroup.value.remarkOne,
        "remarkTwo": this.addOheLocationFormGroup.value.remarkTwo,
        "oheSequence": this.addOheLocationFormGroup.value.oheSequence, 
        "curvature": this.addOheLocationFormGroup.value.curvature, 
        "curvatureRemark": this.addOheLocationFormGroup.value.curvatureRemark, 
        "chainage": this.addOheLocationFormGroup.value.chainage,  
        "chainageRemark": this.addOheLocationFormGroup.value.chainageRemark,                                                                   
        "createdStamp": new Date(),
        "createdTxStamp": new Date(),
        "lastUpdatedStamp": new Date(),
        "lastUpdatedTxStamp": new Date()

      }
      this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.OHELOCATION.SAVE_OHE_LOCATION, saveOheLocationModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
     
        if (this.resp.code == Constants.CODES.SUCCESS) {
          this.commonService.showAlertMessage("Ohe Location Data Saved Successfully");
          this.router.navigate(['../'], { relativeTo: this.route });
        } else {
          this.commonService.showAlertMessage("Ohe Location Data Saving Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Ohe Location Data Saving Failed.");
      });
    } else if (this.update) {
      var updateOheLocationModel = {
        "id": this.id,
        "division": this.addOheLocationFormGroup.value.division,
        "section": this.addOheLocationFormGroup.value.section,
        "pwi": this.addOheLocationFormGroup.value.pwi,
        "trackLine": this.addOheLocationFormGroup.value.trackLine,
        "oheMast": this.addOheLocationFormGroup.value.oheMast,
        "structureType": this.addOheLocationFormGroup.value.structureType,
        "engFeature": this.addOheLocationFormGroup.value.engFeature,
        "oheFeature": this.addOheLocationFormGroup.value.oheFeature,
        "longitude": this.addOheLocationFormGroup.value.longitude,
        "latitude": this.addOheLocationFormGroup.value.latitude,
        "altitude": this.addOheLocationFormGroup.value.altitude,
        "kilometer": this.addOheLocationFormGroup.value.kilometer,
        "date": this.addOheLocationFormGroup.value.date,
        "validity": this.addOheLocationFormGroup.value.validity,  
        "satellites": this.addOheLocationFormGroup.value.satellites,
        "speed": this.addOheLocationFormGroup.value.speed,
        "heading": this.addOheLocationFormGroup.value.heading,
        "remarkOne": this.addOheLocationFormGroup.value.remarkOne,
        "remarkTwo": this.addOheLocationFormGroup.value.remarkTwo,
        "oheSequence": this.addOheLocationFormGroup.value.oheSequence, 
        "curvature": this.addOheLocationFormGroup.value.curvature, 
        "curvatureRemark": this.addOheLocationFormGroup.value.curvatureRemark, 
        "chainage": this.addOheLocationFormGroup.value.chainage,  
        "chainageRemark": this.addOheLocationFormGroup.value.chainageRemark,                                         
        "lastUpdatedStamp": new Date()
      }
      this.sendAndRequestService.requestForPUT(Constants.app_urls.ENERGY_BILL_PAYMENTS.OHELOCATION.UPDATE_OHE_LOCATION, updateOheLocationModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Ohe Location Data Updated Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Ohe Location Data Updating Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Ohe Location Data Updating Failed.");
      })

    }
  }

  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }

  oheFoundationDetails(){
    var statusTypeId='OHE_FOUNDATION_TYPE'
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_STATUS_ITEM + statusTypeId).subscribe((data) => {
      this.oheFounData = data;
  }
  );
}
mastStructureDetails(){
  var statusTypeId = 'MAST_STRUCTURE_TYPE'
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_STATUS_ITEM + statusTypeId).subscribe((data) => {
      this.structureTypeData = data;
  }
  );

}
}
