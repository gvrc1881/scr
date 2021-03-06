import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { MatDatepickerInputEvent,DateAdapter, MAT_DATE_FORMATS } from '@angular/material';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';


@Component({
  selector: 'app-add-fp-sections',
  templateUrl: './add-fp-sections.component.html',
  providers: [
    {
        provide: DateAdapter, useClass: AppDateAdapter
    },
    {
        provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
    }
    ]
  
})
export class AddFpSectionsComponent implements OnInit {

  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
  depotData: any = JSON.parse(sessionStorage.getItem('depotData'));
  resp: any;
  toMinDate=new Date();
  title:string = Constants.EVENTS.ADD;
  fpSectionsFormErrors:any;
  fpSectionsItemFormGroup: FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  facilityData:any;
  fpSectionsList : any;
  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private sendAndRequestService:SendAndRequestService
  ) {
    this.fpSectionsFormErrors = {            
      facilityDepot: {},
      fpSection:{},
      fromLocation:{},
      toLocation:{},
      fromDate:{},
      toDate:{},
      remarks:{},


    };
  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
    if (!isNaN(this.id)) {  
      this.updateFpSectionsForm();  
      this.fpSectionsItemFormGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      }); 
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;
       this.fpSectionsItemEditAction(this.id);
    } else {
      this.createFpSectionsForm();
      this.save = true;
      this.update = false;
      this.title = Constants.EVENTS.ADD;
    }
  }
  addEvent(type: string, event: MatDatepickerInputEvent<Date>) {
    this.toMinDate = new Date(event.value);
}
  onFormValuesChanged() {
    for (const field in this.fpSectionsFormErrors) {
      if (!this.fpSectionsFormErrors.hasOwnProperty(field)) {
        continue;
      }
      this.fpSectionsFormErrors[field] = {};
      const control = this.fpSectionsItemFormGroup.get(field);
      if (control && control.dirty && !control.valid) {
        this.fpSectionsFormErrors[field] = control.errors;
      }
    }
  }
  createFpSectionsForm() {
    this.fpSectionsItemFormGroup = this.formBuilder.group({
		            id: 0,
            'facilityDepot':[null],
            'fpSection':[null, Validators.compose([Validators.required, Validators.maxLength(250)]), this.duplicatefpSection.bind(this)],
            'fromLocation': [null],
            'toLocation': [null],
            'fromDate': [null],
            'toDate' : [null],
            'remarks' : [null,Validators.maxLength(250)]
      
    });
  }
  updateFpSectionsForm() {
    this.fpSectionsItemFormGroup = this.formBuilder.group({
      id: 0,
            'facilityDepot':[null],
            'fpSection':[null, Validators.compose([Validators.required, Validators.maxLength(250)]), this.duplicatefpSectionAndId.bind(this)],
            'fromLocation': [null],
            'toLocation': [null],
            'fromDate': [null],
            'toDate' : [null],
            'remarks' : [null,Validators.maxLength(250)]
      
    });
  }
  

   public get f() { return this.fpSectionsItemFormGroup.controls; } 

  
   fpSectionsItemEditAction(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.FP_SECTIONS.GET_FP_SECTIONS_ID+id)
    .subscribe((resp) => {
        this.resp = resp;
        this.fpSectionsItemFormGroup.patchValue({
          id: this.resp.id,
          facilityDepot:this.resp.facilityDepot,
          fpSection:this.resp.fpSection,
          fromLocation: this.resp.fromLocation,
          toLocation: this.resp.toLocation,
          fromDate: this.resp.fromDate,
          toDate: !!this.resp.toDate ? new Date(this.resp.toDate) : '',
          remarks: this.resp.remarks
        });
        this.spinnerService.hide();
      })
  }
  onAddFpSectionsFormSubmit() {
    this.isSubmit = true;
    if (this.fpSectionsItemFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    if (this.save) {
      var saveFpSectCionsModel = {
        "facilityDepot": this.fpSectionsItemFormGroup.value.facilityDepot,
        "fpSection": this.fpSectionsItemFormGroup.value.fpSection,
        "fromLocation": this.fpSectionsItemFormGroup.value.fromLocation,
        "toLocation": this.fpSectionsItemFormGroup.value.toLocation,
        "fromDate": this.fpSectionsItemFormGroup.value.fromDate,
        "toDate": this.fpSectionsItemFormGroup.value.toDate,
        "remarks": this.fpSectionsItemFormGroup.value.remarks,
        
      }
      this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.FP_SECTIONS.SAVE_FP_SECTIONS, saveFpSectCionsModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
     
        if (this.resp.code == Constants.CODES.SUCCESS) {
          this.commonService.showAlertMessage("Foot Patrolling Sections  Data Saved Successfully");
          this.router.navigate(['../'], { relativeTo: this.route });
        } else {
          this.commonService.showAlertMessage("Foot Patrolling Sections Data Saving Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Foot Patrolling Sections Data Saving Failed.");
      });
    } else if (this.update) {
      var updateFpSectCionsModel = {
        "id": this.id,
        "facilityDepot": this.fpSectionsItemFormGroup.value.facilityDepot,
        "fpSection": this.fpSectionsItemFormGroup.value.fpSection,
        "fromLocation": this.fpSectionsItemFormGroup.value.fromLocation,
        "toLocation": this.fpSectionsItemFormGroup.value.toLocation,
        "fromDate": this.fpSectionsItemFormGroup.value.fromDate,
        "toDate": this.fpSectionsItemFormGroup.value.toDate,
        "remarks": this.fpSectionsItemFormGroup.value.remarks,
      }
      this.sendAndRequestService.requestForPUT(Constants.app_urls.ENERGY_BILL_PAYMENTS.FP_SECTIONS.UPDATE_FP_SECTIONS, updateFpSectCionsModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Foot Patrolling Sections Updated Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Foot Patrolling Sections Updating Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Foot Patrolling Sections Updating Failed.");
      })

    }
  }

  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }
  
  
  duplicatefpSection() {
    const q = new Promise((resolve, reject) => {
      let fpSection: string = this.fpSectionsItemFormGroup.controls['fpSection'].value;
      var filter = !!this.fpSectionsList && this.fpSectionsList.filter(fpSections => {
        return fpSections.fpSection.toLowerCase() == fpSection.trim().toLowerCase();
      });
      if (filter.length > 0) {
        resolve({ 'duplicatefpSection': true });
      }
      this.sendAndRequestService.requestForGET( Constants.app_urls.ENERGY_BILL_PAYMENTS.FP_SECTIONS.EXIST_FP_SECTIONS +
        this.fpSectionsItemFormGroup.controls['fpSection'].value
      ).subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicatefpSection': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicatefpSection': true }); });
    });
    return q;
  }
  duplicatefpSectionAndId() {
    let id=this.id;
    let fpSection: string = this.fpSectionsItemFormGroup.controls['fpSection'].value;         

    const q = new Promise((resolve, reject) => {          

       this.sendAndRequestService.requestForGET(
              Constants.app_urls.ENERGY_BILL_PAYMENTS.FP_SECTIONS.EXIST_FP_SECTIONS_AND_ID+id+'/'+fpSection).subscribe
              ((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicatefpSectionAndId': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicatefpSectionAndId': true }); }); 
    });
    return q;
  }

}