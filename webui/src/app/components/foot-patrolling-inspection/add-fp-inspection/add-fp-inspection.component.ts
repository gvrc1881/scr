import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Constants } from 'src/app/common/constants';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog,DateAdapter, MAT_DATE_FORMATS} from '@angular/material';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';




@Component({
  selector: 'app-fp-inspection',
  templateUrl: './add-fp-inspection.component.html',
  providers: [
    {
        provide: DateAdapter, useClass: AppDateAdapter
    },
    {
        provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
    }
    ]
})
export class AddFpInspectionComponent implements OnInit {

  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  save: boolean = true;
  update: boolean = false;
  title: string = Constants.EVENTS.ADD;
  isSubmit: boolean = false;
  fpInspectionItemFormGroup: FormGroup;
  id: number = 0;
  pattern = "/^[a-zA-Z ]*$/";
  facilityList: any;
  fpInspectionErrors: any;
  resp: any;
  toMinDate = new Date();
  currentDate = new Date();
  dateFormat = 'dd-MM-yyyy hh:mm:ss';
  scheduleList:any;
  depotCode: any;
  
  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private dialog: MatDialog,
  
    private sendAndRequestService:SendAndRequestService
  ) {
    // Reactive form errors
    this.fpInspectionErrors = {
      facilityId: {},
      inspectionType: {},
      section: {},
      inspectionBy: {},
      startTime: {},
      stopTime: {}    
    };
  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
    this.depotTypeForOhe();
    this.createInspectionForm();
    if (!isNaN(this.id)) {
      this.fpInspectionItemFormGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      });
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;
      this.getFpInspectionDataById(this.id);
    } else {
      this.title = Constants.EVENTS.ADD;      
    }
  }
  onFormValuesChanged() {
    for (const field in this.fpInspectionErrors) {
      if (!this.fpInspectionErrors.hasOwnProperty(field)) {
        continue;
      }
      this.fpInspectionErrors[field] = {};
      const control = this.fpInspectionItemFormGroup.get(field);

      if (control && control.dirty && !control.valid) {
        this.fpInspectionErrors[field] = control.errors;
      }
    }
  }

  
  createInspectionForm() {
    this.fpInspectionItemFormGroup = this.formBuilder.group({
      id: 0,
            'seqId':[null],
            'facilityId':[null],
            'inspectionType':[null, Validators.compose([Validators.required, Validators.maxLength(250)])],
            'section': [null, Validators.compose([Validators.required, Validators.maxLength(250)])],
            'inspectionBy': [null,Validators.compose([Validators.required, Validators.maxLength(250)])],
            'startTime': [null],
            'stopTime' : [null]
      
    });
  }
  

   public get f() { return this.fpInspectionItemFormGroup.controls; } 

  
  addEvent($event) {
    this.toMinDate = new Date($event.value);
  }
  
  
  
      
      
  getFpInspectionDataById(id) {
   // this.findFunctionalUnits();
    this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.FP_INSPECTION.GET_FP_INSPECTION_ID+id)
    .subscribe((resp) => {
        this.resp = resp;
        this.fpInspectionItemFormGroup.patchValue({
          id: this.resp.id,
          facilityId:this.resp.facilityId,
          inspectionType:this.resp.inspectionType,
          section: this.resp.section,
          inspectionBy: this.resp.inspectionBy,
          startTime: new Date(this.resp.startTime),
          stopTime: !!this.resp.stopTime ? new Date(this.resp.stopTime) : ''
          
        });
      this.toMinDate = new Date(this.resp.startTime);
        
        this.spinnerService.hide();
      })
  }
  fpInspectionItemSubmit() {
    this.isSubmit = true;
    if (this.fpInspectionItemFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    if (this.save) {
      var saveFpInspecModel = {
        "seqId":this.fpInspectionItemFormGroup.value.seqId,
        "facilityId": this.fpInspectionItemFormGroup.value.facilityId,
        "inspectionType": this.fpInspectionItemFormGroup.value.inspectionType,
        "section": this.fpInspectionItemFormGroup.value.section,
        "inspectionBy": this.fpInspectionItemFormGroup.value.inspectionBy,
        "startTime": this.fpInspectionItemFormGroup.value.startTime,
        "stopTime": this.fpInspectionItemFormGroup.value.stopTime,      
        "createdBy": this.loggedUserData.username,
        "createdOn": new Date()
      }

      this.sendAndRequestService.requestForPOST(Constants.app_urls.DAILY_SUMMARY.FP_INSPECTION.SAVE_FP_INSPECTION, saveFpInspecModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
     
        if (this.resp.code == Constants.CODES.SUCCESS) {
          this.commonService.showAlertMessage("Fp Inspection Data Saved Successfully");
          this.router.navigate(['../'], { relativeTo: this.route });
        } else {
          this.commonService.showAlertMessage("Fp Inspection Saving Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Fp Inspection Saving Failed.");
      });
    } else if (this.update) {
      var updateFpInspectModel = {
        "seqId":this.id,
        "id": this.id,
        "facilityId": this.fpInspectionItemFormGroup.value.facilityId,
        "inspectionType": this.fpInspectionItemFormGroup.value.inspectionType,
        "section": this.fpInspectionItemFormGroup.value.section,
        "inspectionBy": this.fpInspectionItemFormGroup.value.inspectionBy,
        "startTime": this.fpInspectionItemFormGroup.value.startTime,
        "stopTime": this.fpInspectionItemFormGroup.value.stopTime,         
        "updatedBy": this.loggedUserData.username,
        "updatedOn": new Date()
      }
      this.sendAndRequestService.requestForPUT(Constants.app_urls.DAILY_SUMMARY.FP_INSPECTION.UPDATE_FP_INSPECTION, updateFpInspectModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Fp Inspection Updated Successfully");
        this.router.navigate(['../../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Fp Inspection Updating Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Fp Inspection Updating Failed.");
      })

    }
  }
  depotTypeForOhe()
  {  
         this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_DEPOTTYPE_FOR_OHE).subscribe((data) => {
           this.facilityList = data;
  }
         );

 }
  onGoBack() {
    if (this.save) {
      this.router.navigate(['../'], { relativeTo: this.route });
    } else if (this.update) {
      this.router.navigate(['../../'], { relativeTo: this.route });
    }
  }
 
}



