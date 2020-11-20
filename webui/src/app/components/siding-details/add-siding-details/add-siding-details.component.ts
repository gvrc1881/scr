import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FacilityModel } from 'src/app/models/facility.model';
import { MatDatepickerInputEvent,DateAdapter, MAT_DATE_FORMATS  } from '@angular/material';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';

@Component({
  selector: 'app-add-siding-details',
  templateUrl: './add-siding-details.component.html',
  providers: [
    {
        provide: DateAdapter, useClass: AppDateAdapter
    },
    {
        provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
    }
    ]
})
export class AddSidingDetailsComponent implements OnInit {

    FiledLabels = FieldLabelsConstant.LABELS;
    Titles = FieldLabelsConstant.TITLE;
    pagination =Constants.PAGINATION_NUMBERS;    
    title: string =  Constants.EVENTS.ADD;
    loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
    save: boolean = true;
    update: boolean = false;
    isSubmit: boolean = false;
    sidingsItemFormGroup: FormGroup;
    id: number = 0;
    pattern = "/^[a-zA-Z ]*$/";
    driveFormErrors: any;
    resp: any;
    toMinDate=new Date();
    today=new Date();
    sidingsItemList : any;
    public status=['Yes','No'];
    sidingEletrifiedStatus: any;
    onlyYes:boolean = false;
    selected: any;
    yes: any;
    onlyNo: boolean;
    divisionsList:any;
    zonesList:any;
    depotList:any;
    userHierarchy:any = JSON.parse(localStorage.getItem('userHierarchy'));
    zoneList: FacilityModel [] = [];
    divisionList:  FacilityModel [] = [];
    subDivList:  FacilityModel [] = [];
    facilityList: FacilityModel [] = [];
	enableZone: boolean ;
	enableDivision: boolean;
	
	enableDepot: boolean;
    
    depotData: any;
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
    this.createSidingsForm();
    if (!isNaN(this.id)) {     
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title =  Constants.EVENTS.UPDATE;;
      this.getSidingsDataById(this.id);
    } else {
      this.save = true;
      this.update = false;
      this.title =  Constants.EVENTS.ADD;;
    }
    }  
    
    createSidingsForm() {
      this.sidingsItemFormGroup = this.formBuilder.group({
        id: 0,
            'station':[null,Validators.maxLength(250)],
            'sidingCode': [null, Validators.compose([Validators.required, Validators.maxLength(250)]), this.duplicateSidingCode.bind(this)],
            'section': [null,Validators.maxLength(250)],
            'sectionEletrifiedStatus': [null],
            'sidingEletrifiedStatus' : [null],
            'privateRailway' : [null,Validators.maxLength(250)],
            'status': [null,Validators.maxLength(250)],
            'tkm' : [null],
            'remarks' : [null, Validators.maxLength(250)],
            'sidingProposed' : [null],
            'proposedDate' : [null],
            'approvalDate' : [null],
            'workOrderDate' : [null],
            'workProgressPercentage' : [null],
            'workProgressRemark' : [null,Validators.maxLength(250)],
            'completionDate' : [null],
            'zone' : [null],
            'division':[null],
            'depot':[null],
        
      });
    }
    
  
     public get f() { return this.sidingsItemFormGroup.controls; } 
     duplicateSidingCode() {
      const q = new Promise((resolve, reject) => {
        let siding: string = this.sidingsItemFormGroup.controls['sidingCode'].value;
        var filter = !!this.sidingsItemList && this.sidingsItemList.filter(sidings => {
          return sidings.sidingCode.toLowerCase() == siding.trim().toLowerCase();
        });
        if (filter.length > 0) {
          resolve({ 'duplicateSidingCode': true });
        }
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.SIDINGS.EXISTS_SIDING_CODE +
          this.sidingsItemFormGroup.controls['sidingCode'].value
        ).subscribe((duplicate) => {
          if (duplicate) {
            resolve({ 'duplicateSidingCode': true });
          } else {
            resolve(null);
          }
        }, () => { resolve({ 'duplicateSidingCode': true }); });
      });
      return q;
    }
    addEvent(type: string, event: MatDatepickerInputEvent<Date>) {
      this.toMinDate = new Date(event.value);
  }

     findFacilityNameList() {
        this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY_NAMES)
          .subscribe((data) => {
            this.depotData = data;
          })
      }
      
      statusChange() {
        if (this.sidingsItemFormGroup.value.sidingEletrifiedStatus == 'Yes') {     
            this.onlyYes = false;
        } else {
            this.onlyYes = true;
        }
            }

  
   
    getSidingsDataById(id) {
      this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.SIDINGS.GET_SIDINGS_ID+id)
      .subscribe((resp) => {
          this.resp = resp;
          this.sidingsItemFormGroup.patchValue({
            id: this.resp.id,
            station:this.resp.station,
            sidingCode: this.resp.sidingCode,
            section: this.resp.section,
            sectionEletrifiedStatus: this.resp.sectionEletrifiedStatus,
            sidingEletrifiedStatus: this.resp.sidingEletrifiedStatus,
            privateRailway: this.resp.privateRailway,
            status: this.resp.status,
            tkm: this.resp.tkm,
            remarks: this.resp.remarks,
            sidingProposed: this.resp.sidingProposed,
            proposedDate: this.resp.proposedDate,
            approvalDate: !!this.resp.approvalDate ? new Date(this.resp.approvalDate) : '',
            workOrderDate: !!this.resp.workOrderDate ? new Date(this.resp.workOrderDate) : '',
            workProgressPercentage: this.resp.workProgressPercentage,
            workProgressRemark: this.resp.workProgressRemark,
            completionDate: this.resp.completionDate,
            zone:this.resp.zone,
            division:this.resp.division,               
            depot:this.resp.depot,  
          });
          this.spinnerService.hide();
        })
    }
    SidingsFormSubmit() {
      this.isSubmit = true;
      if (this.sidingsItemFormGroup.invalid) {
        this.isSubmit = false;
        return;
      }
      this.spinnerService.show();
      if (this.save) {
        var saveSidingsModel = {
         // "facilityId": this.addProductFormGroup.value.facilityId,
         "station": this.sidingsItemFormGroup.value.station,
         "sidingCode": this.sidingsItemFormGroup.value.sidingCode,
         "section": this.sidingsItemFormGroup.value.section,
         "sectionEletrifiedStatus": this.sidingsItemFormGroup.value.sectionEletrifiedStatus,                                                                                                                
         "sidingEletrifiedStatus": this.sidingsItemFormGroup.value.sidingEletrifiedStatus,
         "privateRailway": this.sidingsItemFormGroup.value.privateRailway,
         "status": this.sidingsItemFormGroup.value.status,
         "tkm":this.sidingsItemFormGroup.value.tkm,
          "remarks": this.sidingsItemFormGroup.value.remarks,
          "sidingProposed": this.sidingsItemFormGroup.value.sidingProposed,
          "proposedDate": this.sidingsItemFormGroup.value.proposedDate,
          "approvalDate": this.sidingsItemFormGroup.value.approvalDate,
          "workOrderDate": this.sidingsItemFormGroup.value.workOrderDate,
          "workProgressPercentage": this.sidingsItemFormGroup.value.workProgressPercentage,
          "workProgressRemark": this.sidingsItemFormGroup.value.workProgressRemark,
          "completionDate": this.sidingsItemFormGroup.value.completionDate,
          "zone": this.sidingsItemFormGroup.value.zone,       
          "division": this.sidingsItemFormGroup.value.division,       
          "depot": this.sidingsItemFormGroup.value.depot,
          "createdBy": this.loggedUserData.username
        }
        this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.SIDINGS.SAVE_SIDINGS, saveSidingsModel, false).subscribe(response => {
          this.spinnerService.hide();
          this.resp = response;
       
          if (this.resp.code == Constants.CODES.SUCCESS) {
            this.commonService.showAlertMessage("Sidings Data Saved Successfully");
            this.router.navigate(['../'], { relativeTo: this.route });
          } else {
            this.commonService.showAlertMessage("Sidings Data Saving Failed.");
          }
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Sidings Data Saving Failed.");
        });
      } else if (this.update) {
        var updateSidingsModel = {
          "id": this.id,
          // "facilityId": this.addProductFormGroup.value.facilityId,
          "station": this.sidingsItemFormGroup.value.station,
          "sidingCode": this.sidingsItemFormGroup.value.sidingCode,
          "section": this.sidingsItemFormGroup.value.section,
          "sectionEletrifiedStatus": this.sidingsItemFormGroup.value.sectionEletrifiedStatus,                                                                                                                
          "sidingEletrifiedStatus": this.sidingsItemFormGroup.value.sidingEletrifiedStatus,
          "privateRailway": this.sidingsItemFormGroup.value.privateRailway,
          "status": this.sidingsItemFormGroup.value.status,
          "tkm":this.sidingsItemFormGroup.value.tkm,
          "remarks": this.sidingsItemFormGroup.value.remarks,
          "sidingProposed": this.sidingsItemFormGroup.value.sidingProposed,
          "proposedDate": this.sidingsItemFormGroup.value.proposedDate,
          "approvalDate": this.sidingsItemFormGroup.value.approvalDate,
          "workOrderDate": this.sidingsItemFormGroup.value.workOrderDate,
          "workProgressPercentage": this.sidingsItemFormGroup.value.workProgressPercentage,
          "workProgressRemark": this.sidingsItemFormGroup.value.workProgressRemark,
          "completionDate": this.sidingsItemFormGroup.value.completionDate,
          "zone": this.sidingsItemFormGroup.value.zone,       
          "division": this.sidingsItemFormGroup.value.division,       
          "depot": this.sidingsItemFormGroup.value.depot,                                          
          "updatedBy": this.loggedUserData.username
        }
        this.sendAndRequestService.requestForPUT(Constants.app_urls.ENERGY_BILL_PAYMENTS.SIDINGS.UPDATE_SIDINGS, updateSidingsModel, false).subscribe(response => {
          this.spinnerService.hide();
          this.resp = response;
          if (this.resp.code == Constants.CODES.SUCCESS) {
          this.commonService.showAlertMessage("Sidings Data Updated Successfully");
          this.router.navigate(['../'], { relativeTo: this.route });
        }else{
            this.commonService.showAlertMessage("Sidings Data Updating Failed.");
          }
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Sidings Data Updating Failed.");
        })
  
      }
    }
  
    onGoBack() {
      this.router.navigate(['../'], { relativeTo: this.route });
    }
  }
  
  
  
  
















