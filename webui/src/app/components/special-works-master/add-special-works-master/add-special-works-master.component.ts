import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';


@Component({
  selector: 'app-add-special-works-master',
  templateUrl: './add-special-works-master.component.html',
  
})
export class AddSpecialWorksMasterComponent implements OnInit {

  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  resp: any;
  title:string = Constants.EVENTS.ADD;
  specialWorksFormErrors:any;
  addSpecialWorksFormGroup: FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
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
      precautionaryMeasure: {},
      doneBy:{},
    };
  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
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
      'precautionaryMeasure': [null, Validators.compose([Validators.required]),this.duplicatePrecautionaryMeasure.bind(this)],
      'doneBy': [null, Validators.compose([Validators.required])]
      
    });
  }
  updateSpecialWorksForm() {
    this.addSpecialWorksFormGroup = this.formBuilder.group({
      id: 0,
      'precautionaryMeasure': [null, Validators.compose([Validators.required]),this.duplicatePrecautionaryMeasureByID.bind(this)],
      'doneBy': [null, Validators.compose([Validators.required])]
      
    });
  }
  

   public get f() { return this.addSpecialWorksFormGroup.controls; } 

  
  getSpecialWorksDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.SPECIALWORKS.GET_PRE_MEA_MAS_ID+id)
    .subscribe((resp) => {
        this.resp = resp;
        this.addSpecialWorksFormGroup.patchValue({
          id: this.resp.id,
          precautionaryMeasure: this.resp.precautionaryMeasure,
          doneBy: this.resp.doneBy,
        });
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
        "precautionaryMeasure": this.addSpecialWorksFormGroup.value.precautionaryMeasure,
        "doneBy": this.addSpecialWorksFormGroup.value.doneBy, 
        "createdStamp": new Date(),
        "createdTxStamp": new Date(),
        "lastUpdatedStamp": new Date(),
        "lastUpdatedTxStamp": new Date()    
        
      }
      this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.SPECIALWORKS.SAVE_PRE_MEA_MAS, saveSpecialWorksModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
     
        if (this.resp.code == Constants.CODES.SUCCESS) {
          this.commonService.showAlertMessage("Special Works Master  Data Saved Successfully");
          this.router.navigate(['../'], { relativeTo: this.route });
        } else {
          this.commonService.showAlertMessage("Special Works Master Data Saving Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Special Works Master Data Saving Failed.");
      });
    } else if (this.update) {
      var updateSpecialWorksModel = {
        "id": this.id,
        "precautionaryMeasure": this.addSpecialWorksFormGroup.value.precautionaryMeasure, 
        "doneBy": this.addSpecialWorksFormGroup.value.doneBy,
        "lastUpdatedStamp": new Date(),
        "lastUpdatedTxStamp": new Date()   
      }
      this.sendAndRequestService.requestForPUT(Constants.app_urls.ENERGY_BILL_PAYMENTS.SPECIALWORKS.UPDATE_PRE_MEA_MAS, updateSpecialWorksModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Special Works Master Data Updated Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Special Works Master Data Updating Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Special Works Master Data Updating Failed.");
      })

    }
  }

  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }
  
  
  duplicatePrecautionaryMeasure() {
    const q = new Promise((resolve, reject) => {
              
      let precautionaryMeasure: string = this.addSpecialWorksFormGroup.controls['precautionaryMeasure'].value;
      
     
      this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.SPECIALWORKS.EXIST_PRE_MEA+precautionaryMeasure)
      .subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'precautionaryMeasure': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'precautionaryMeasure': true }); });
    });
    return q;
  }
  duplicatePrecautionaryMeasureByID() {
    const q = new Promise((resolve, reject) => {
      let id=this.id;              
      let precautionaryMeasure: string = this.addSpecialWorksFormGroup.controls['precautionaryMeasure'].value;
      this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.SPECIALWORKS.EXIST_PRE_MEA_ID+id+'/'+precautionaryMeasure)
      .subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicatePrecautionaryMeasureByID': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicatePrecautionaryMeasureByID': true }); });
    });
    return q;
  }
}