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
  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  resp: any;
  title:string = Constants.EVENTS.ADD;
  addSpecialWorksFormGroup: FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  toMinDate = new Date();
  today=new Date();
  currentDate = new Date();
  dateFormat = 'MM-dd-yyyy ';
  specialWorksData:any;
  facilityData:any;
  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private sendAndRequestService:SendAndRequestService
  ) {
   
  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
    this.findSpecialWorksList();
    this.depotTypeForOhe();
    if (!isNaN(this.id)) {  
      this.updateSpecialWorksForm();   
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
  
  
  createSpecialWorksForm() {
    this.addSpecialWorksFormGroup = this.formBuilder.group({
      id: 0,
      'facilityId': [null, Validators.compose([Validators.required])],
      'location': [null, Validators.compose([Validators.required])],
      'precautionaryMeasure': [null,Validators.compose([Validators.required])],
      'count': [null],
      'remarks': [null, Validators.compose([Validators.required, Validators.maxLength(255)])],
      'fromDateTime': [null, Validators.required],
      'thruDateTime': [null],
      'doneBy': [null]
      
    });
  }
  updateSpecialWorksForm() {
    this.addSpecialWorksFormGroup = this.formBuilder.group({
      id: 0,
      'facilityId': [null, Validators.compose([Validators.required])],
      'location': [null, Validators.compose([Validators.required])],
      'precautionaryMeasure': [null,Validators.compose([Validators.required])],
      'count': [null],
      'remarks': [null, Validators.compose([Validators.required, Validators.maxLength(255)])],
      'fromDateTime': [null, Validators.required],
      'thruDateTime': [null],
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
        this.addSpecialWorksFormGroup.patchValue({
          id: this.resp.id,
          facilityId: this.resp.facilityId,
          location: this.resp.location,
          precautionaryMeasure: this.resp.precautionaryMeasure,
          count: this.resp.count,
          remarks: this.resp.remarks,
          fromDateTime: new Date(this.resp.fromDateTime),
          thruDateTime: !!this.resp.thruDateTime ? new Date(this.resp.thruDateTime) : '',
          doneBy: this.resp.doneBy,
          
          
        });
      this.toMinDate = new Date(this.resp.fromDateTime);
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
        "facilityId": this.addSpecialWorksFormGroup.value.facilityId,
        "location": this.addSpecialWorksFormGroup.value.location,
        "precautionaryMeasure": this.addSpecialWorksFormGroup.value.precautionaryMeasure,
        "count": this.addSpecialWorksFormGroup.value.count,
        "remarks": this.addSpecialWorksFormGroup.value.thruDate,
        "fromDateTime": this.addSpecialWorksFormGroup.value.fromDateTime, 
        "thruDateTime": this.addSpecialWorksFormGroup.value.thruDateTime,   
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
        "facilityId": this.addSpecialWorksFormGroup.value.facilityId,
        "location": this.addSpecialWorksFormGroup.value.location,
        "precautionaryMeasure": this.addSpecialWorksFormGroup.value.precautionaryMeasure,
        "count": this.addSpecialWorksFormGroup.value.count,
        "remarks": this.addSpecialWorksFormGroup.value.thruDate,
        "fromDateTime": this.addSpecialWorksFormGroup.value.fromDateTime, 
        "thruDateTime": this.addSpecialWorksFormGroup.value.thruDateTime,   
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
    this.sendAndRequestService.requestForGET(Constants.app_urls.PRODUCTS.PRODUCT.GET_PRODUCT)
      .subscribe((data) => {
        this.specialWorksData = data;
      })
  }
  
}