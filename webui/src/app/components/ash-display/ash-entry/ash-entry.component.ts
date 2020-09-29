import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ValidatorFn, FormControl, FormGroupDirective, NgForm, AbstractControl } from '@angular/forms';
import { FacilityModel } from 'src/app/models/facility.model';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { DatePipe } from '@angular/common';
import { ErrorStateMatcher } from '@angular/material/core';
@Component({
  selector: 'app-ash-entry',
  templateUrl: './ash-entry.component.html',
  styleUrls: ['./ash-entry.component.css']
})
export class AshEntryComponent implements OnInit {
  id: number = 0;
  title: string;
  save: boolean = true;
  update: boolean = false;
  today = new Date();
  loggedHierarchyList: any = JSON.parse(localStorage.getItem('userHierarchy'));
  loggedUserData: any=JSON.parse(localStorage.getItem('userData'));
  isSubmit: boolean = false;
  resp: any;
  List = [];
  entryScheduleReportGroup: FormGroup;
  facilityId: any;
  facility: any;
  fromKm: any;
  toKm: any;
  facilityList: FacilityModel[] = [];
  depoNameList: any;
  powerBlockList: any;
  assetTypeList: any;
  scheduleDate: any;
  assetIdList: any;
  scheduleCodeList: any;
  statusItems: any;
  assetType: any;
  failureAnalysisFormErrors: any;
  DepotName: any;
  PwrBlock: any;
  AssetTyp:any;
  AsstId:any;
  assetidObjModel:any;
  SchCode:any;

  constructor(private router: Router,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private sendAndRequestService: SendAndRequestService,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private datePipe: DatePipe) {
    this.today.setDate(this.today.getDate());
    // Reactive form errors
    console.log('constructor called')
  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
    console.log('this.id:::'+this.id)
    this.getsData();
    this.createAssetDailyScheduleReportGroup();
    if (!isNaN(this.id)) {
      console.log(" inside:::")
            
      this.spinnerService.show();
      this.save = true;
      this.update = false;
      this.title = 'Edit';
      this.getAshDataById(this.id);
    } else {
      this.save = true;
      this.update = false;
      this.title = 'Save';
    }
  

  }
  createAssetDailyScheduleReportGroup() {
    this.entryScheduleReportGroup = this.formBuilder.group({
      id: 0,
      'Schedule_date': [null, Validators.compose([Validators.required])],
      'Depot_Name': [null, Validators.compose([Validators.required])],
      'Power_Block': [null, Validators.compose([Validators.required])],
      'Asset_Type': [null,],
      
      'Schedule': [null, Validators.compose([Validators.required])],
      
      'Asset_Id': [null, Validators.compose([Validators.required])]
    });

  }

  

  selectedScheduleDate(date) {
    //console.log("loggedUserData::" + this.loggedUserData.depoNames);
    console.log("selectedScheduleDate::" + date);
    this.scheduleDate = this.datePipe.transform(date, "yyyy-MM-dd");
    console.log(this.datePipe.transform(date, "yyyy-MM-dd"));

  }
  
 
  process(data: any) {
    let statuses: any[] = [];
    for (let status of data) {
      statuses.push({ "pbOperationSeqId": status.statusCode });
    }
    return statuses;
  }

  getAssetIdForEdit(assetyp) {
    console.log("selected asset type for edit  ::" +assetyp+"::"+this.facilityId);
    this.assetType = assetyp;
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.GET_ASSETID_BASED_ON_ASSETTYPE_FACILITYID + this.assetType + '/'+this.facilityId).subscribe((data) => {
      this.assetIdList = data;
      console.log("selected assetId List for edit  ::" +this.assetIdList);
    }, error => {
      this.spinnerService.hide();
    });
  }
 
  
  getsData() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.ASH.ASH.GET_ASH_DEPO).subscribe((data) => {
      this.List = data;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }
 
  
  getAshDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.ASH.ASH.GET_ASH_DEPO_BY_ID + id)
      .subscribe((resp) => {
        console.log("Ash edit record response:::" + JSON.stringify(resp))
        this.resp = resp;
        this.entryScheduleReportGroup.patchValue({
          id: this.resp.id,
          // failure_id: this.resp.failure_id,  !!this.resp.actionTargetDate ? new Date(this.resp.actionTargetDate) : '',
          Schedule_date: !!this.resp.scheduleDate?new Date(this.resp.scheduleDate) : '',
          //Depot_Name: this.resp.depo,
          //Power_Block: this.resp.pbOperationSeqId,
          //Asset_Type: this.resp.assetType,
          //Schedule: this.resp.scheduleCode,
          Details_Of_Maint: this.resp.detailsOfMaint,
          Done_By: this.resp.doneBy,
          Remarks: this.resp.remarks,
          //'facilityId': this.resp.facilityId,
          Incharge: this.resp.initialOfIncharge,
         // Asset_Id: [this.resp.assetId],

 
        });
        console.log("::: this.entryScheduleReportGroup" + this.entryScheduleReportGroup);
        this.spinnerService.hide();
        this.DepotName=this.resp.facilityId;
        this.PwrBlock=this.resp.pbOperationSeqId;
        this.AssetTyp=this.resp.assetType;
        console.log("assetIdList mapped for edit page for ng mpodel::"+this.assetIdList);
       if(this.assetIdList===undefined){
        console.log("assetid not mapped for edit page for ng mpodel undefined");
       }else{
        
         this.AsstId=this.resp.assetId;       
       }
       this.AsstId=this.resp.assetId;  
       this.SchCode=this.resp.scheduleCode;
      })
  }
  
  onGoBack() {
    this.router.navigate(['../../'], { relativeTo: this.route });
  }
  onAssetDailyScheduleReportSubmit() {
    // this.isSubmit = true;
    if (this.entryScheduleReportGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    if (this.save) {
      let assetIdAssetTypes = [];
      this.entryScheduleReportGroup.controls.Asset_Id.value.map(value => {
        assetIdAssetTypes.push(value.assetId + "_" + value.assetType);
      })
      let ele = this.entryScheduleReportGroup.value.Asset_Id;
      var saveAshModel = {
        'scheduleDate': this.entryScheduleReportGroup.value.Schedule_date,
        'depotName': this.entryScheduleReportGroup.value.Depot_Name,
        'pbOperationSeqId': this.entryScheduleReportGroup.value.Power_Block,
        'assetType': this.entryScheduleReportGroup.value.Asset_Type,
        'scheduleCode': this.entryScheduleReportGroup.value.Schedule,
        
        'facilityId': this.facilityId,
        'assetId': JSON.stringify(assetIdAssetTypes),
        "createdBy": this.loggedUserData.username,
        "createdOn": new Date(),
        "status": 'EntryPending',
        "dataDiv": this.facility.division
      }
      console.log(" model for save ash:::" + saveAshModel);
      this.sendAndRequestService.requestForPOST(Constants.app_urls.ASH.ASH.SAVE_ASH, saveAshModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;

        if (this.resp.code == Constants.CODES.SUCCESS) {
          this.commonService.showAlertMessage("Ash Entry Data Saved Successfully");
          //this.router.navigate(['/'], { relativeTo: this.route });
          window.location.reload;
        } else {
          this.commonService.showAlertMessage("Ash Entry Data Saving Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Ash Entry Data Saving Failed.");
      });
    }
  }

}
