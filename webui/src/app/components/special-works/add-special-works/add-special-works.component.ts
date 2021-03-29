import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { DateAdapter, MAT_DATE_FORMATS} from '@angular/material';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';


@Component({
  selector: 'app-add-special-works',
  templateUrl: './add-special-works.component.html',
  providers: [
    {
        provide: DateAdapter, useClass: AppDateAdapter
    },
    {
        provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
    }
    ]
})
export class AddSpecialWorksComponent implements OnInit {

  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
  depotData: any = JSON.parse(sessionStorage.getItem('depotData'));
  divisionData: any = JSON.parse(sessionStorage.getItem('divisionData'));
  resp: any;
  title:string = Constants.EVENTS.ADD;
  addSpecialWorksFormGroup: FormGroup;
  specialWorksFormErrors:any;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  toMinDate = new Date();
  today=new Date();
  currentDate = new Date();
  dateFormat = 'MM-dd-yyyy ';
  specialWorksData:any;
  selectedWork: any;
  facilityData:any;
  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private sendAndRequestService:SendAndRequestService
  ) {
    this.specialWorksFormErrors = {            
      facilityId:{},
      precautionaryMeasure: {},
      count:{},
      remarks:{},
      dateOfWork: {},
      doneBy:{},
    };
  }

  ngOnInit() {
    console.log("depotData"+JSON.stringify(this.depotData));
    this.id = +this.route.snapshot.params['id'];
    this.findSpecialWorksList();
    this.depotTypeForOhe();
    if (!isNaN(this.id)) {  
      this.updateSpecialWorksForm();  
      this.addSpecialWorksFormGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      }); 
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;
       this.getSpecialWorksDataById(this.id);
    } else {
      this.createSpecialWorksForm();
      this.save = true;
      this.update = false;
      this.title = Constants.EVENTS.ADD;
    }
  }
  
  onFormValuesChanged() {
    for (const field in this.specialWorksFormErrors) {
      if (!this.specialWorksFormErrors.hasOwnProperty(field)) {
        continue;
      }
      this.specialWorksFormErrors[field] = {};
      const control = this.addSpecialWorksFormGroup.get(field);
      if (control && control.dirty && !control.valid) {
        this.specialWorksFormErrors[field] = control.errors;
      }
    }
  }
  createSpecialWorksForm() {
    this.addSpecialWorksFormGroup = this.formBuilder.group({
      id: 0,
      'dateOfWork': [null,Validators.required],
      'facilityId': [null, Validators.compose([Validators.required])],
      'precautionaryMeasure': [null,Validators.compose([Validators.required]),this.duplicateFacilityIdPrecautionaryMeasureDateOfWork.bind(this)],
      'count': [null],
      'remarks': [null, Validators.compose([Validators.required, Validators.maxLength(255)])],
      'doneBy': [null]
      
    });
  }
  updateSpecialWorksForm() {
    this.addSpecialWorksFormGroup = this.formBuilder.group({
      id: 0,
      'dateOfWork': [null,Validators.required],
      'facilityId': [null, Validators.compose([Validators.required])],
      'precautionaryMeasure': [null,Validators.compose([Validators.required]), this.duplicateFacilityIdPrecautionaryMeasureDateOfWorkAndId.bind(this)],
      'count': [null],
      'remarks': [null, Validators.compose([Validators.required, Validators.maxLength(255)])],
      'doneBy': [null]
      
    });
  }
  

   public get f() { return this.addSpecialWorksFormGroup.controls; } 

 
  
  addEvent($event) {
    this.toMinDate = new Date($event.value);
  }
  
  
    
  getSpecialWorksDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.SPECIALWORKS.GET_SPECIAL_WORKS_ID+id)
    .subscribe((resp) => {
        this.resp = resp;
        let precautionaryMeasure = this.resp.precautionaryMeasure
        console.log("list==="+this.resp.precautionaryMeasure.id);
        this.addSpecialWorksFormGroup.patchValue({
          id: this.resp.id,
          dataDiv: this.resp.dataDiv,
          facilityId: this.resp.facilityId,
          location: this.resp.location,
          precautionaryMeasure: this.resp.precautionaryMeasure.id,
          count: this.resp.count,
          remarks: this.resp.remarks,
          dateOfWork: new Date(this.resp.dateOfWork),
          doneBy: this.resp.doneBy,
        });
        this.getPreMeaMast();
        console.log('*** group values **'+this.addSpecialWorksFormGroup.value.precautionaryMeasure.id);
        this.spinnerService.hide();
      })
  }
  onAddSpecialWorksFormSubmit() {
    this.isSubmit = true;
    if (this.addSpecialWorksFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    if (this.save) {
      var saveSpecialWorksModel = {
        "dateOfWork": this.addSpecialWorksFormGroup.value.dateOfWork, 
        "facilityId": this.addSpecialWorksFormGroup.value.facilityId,
        "precautionaryMeasure": this.selectedWork,
        "count": this.addSpecialWorksFormGroup.value.count,
        "remarks": this.addSpecialWorksFormGroup.value.remarks,
        "doneBy": this.addSpecialWorksFormGroup.value.doneBy, 
        "createdDate":new Date(),
        "createdStamp": new Date(),
        "createdTxStamp": new Date(),
        "lastUpdatedStamp": new Date(),
        "lastUpdatedTxStamp": new Date()    
        
      }
      this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.SPECIALWORKS.SAVE_SPECIAL_WORKS, saveSpecialWorksModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
     
        if (this.resp.code == Constants.CODES.SUCCESS) {
          this.commonService.showAlertMessage("Special Works  Data Saved Successfully");
          this.router.navigate(['../'], { relativeTo: this.route });
        } else {
          this.commonService.showAlertMessage("Special Works Data Saving Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Special Works Data Saving Failed.");
      });
    } else if (this.update) {
      var updateSpecialWorksModel = {
        "id": this.id,
        "dateOfWork": this.addSpecialWorksFormGroup.value.dateOfWork, 
        "facilityId": this.addSpecialWorksFormGroup.value.facilityId,
        "precautionaryMeasure": this.selectedWork,
        "count": this.addSpecialWorksFormGroup.value.count,
        "remarks": this.addSpecialWorksFormGroup.value.remarks,
        "doneBy": this.addSpecialWorksFormGroup.value.doneBy,
        "lastUpdatedStamp": new Date(),
        "lastUpdatedTxStamp": new Date()   
      }
      this.sendAndRequestService.requestForPUT(Constants.app_urls.ENERGY_BILL_PAYMENTS.SPECIALWORKS.UPDATE_SPECIAL_WORKS, updateSpecialWorksModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Special Works Data Updated Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Special Works Data Updating Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Special Works Data Updating Failed.");
      })

    }
  }

  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }
  depotTypeForOhe()
  {  
         this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY_NAMES).subscribe((data) => {
           this.facilityData = data;
  }
         );

 }
  findSpecialWorksList() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_PRECAUTIONARY_MEASURE_DETAILS)
      .subscribe((data) => {
        this.specialWorksData = data;
      })
  }

  getPreMeaMast () {
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.SPECIALWORKS.GET_PRE_MEA_MAS_ID+this.addSpecialWorksFormGroup.value.precautionaryMeasure).subscribe((data) => {
         this.selectedWork = data;
     });
  }
  duplicateFacilityIdPrecautionaryMeasureDateOfWork() {
    const q = new Promise((resolve, reject) => {
       this.sendAndRequestService.requestForGET(
              Constants.app_urls.ENERGY_BILL_PAYMENTS.SPECIALWORKS. EXIST_FACILITY_ID_AND_PRECAU_MEA_DATE +
            this.addSpecialWorksFormGroup.controls['dateOfWork'].value + '/'+
            this.addSpecialWorksFormGroup.controls['facilityId'].value+'/'+
            this.addSpecialWorksFormGroup.controls['precautionaryMeasure'].value
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
  duplicateFacilityIdPrecautionaryMeasureDateOfWorkAndId() {
    let id=this.id;
    let dateOfWork: string = this.addSpecialWorksFormGroup.controls['dateOfWork'].value;
    let facilityId: string = this.addSpecialWorksFormGroup.controls['facilityId'].value;
    let precautionaryMeasure: string = this.addSpecialWorksFormGroup.controls['precautionaryMeasure'].value;
   

    const q = new Promise((resolve, reject) => {          

       this.sendAndRequestService.requestForGET(
              Constants.app_urls.ENERGY_BILL_PAYMENTS.SPECIALWORKS.EXIST_FACILITY_ID_AND_PRECAU_MEA_DATE_ID +id+'/'+dateOfWork+'/'+facilityId+'/'+precautionaryMeasure).subscribe
              ((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateFacilityIdPrecautionaryMeasureDateOfWorkAndId': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateFacilityIdPrecautionaryMeasureDateOfWorkAndId': true }); });
    });
    return q;
  }
}