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
  ashEntry:any;
  List = [];
  entryScheduleReportGroup: FormGroup;
  facilityId: any;
  facility: any;
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
  makeModelList:any;
  measuresList:any;
  activityList:any;

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

      this.entryScheduleReportGroup.get('Asset_Id').valueChanges.subscribe(asstid => {
        console.log("ngonit Asset_Id:::"+ asstid);
        if(asstid===undefined){
          console.log("ngonit undefined Asset_Type:::"+ asstid);
        }
        else{
         this.AsstId=asstid;
        }
       }) 
       this.entryScheduleReportGroup.get('Depot_Name').valueChanges.subscribe(depo => {
        console.log("ngonit facilityid:::"+ depo);
       
        if(depo===undefined){
          console.log("ngonit undefined facilityid:::"+ depo);
        }else{
        this.facilityId = depo;
        let asset=this.AsstId;
        if(asset.includes("/")){
          console.log("assetId replacement");
          asset=asset.replace("/","@");
        }
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.GET_MAKE_MODEL +asset +'/'+this.assetType + '/'+this.facilityId).subscribe((data) => {
          this.makeModelList = data;
         console.log("this.makeModelList:::"+JSON.stringify(this.makeModelList[0].make));
         //get measures
         this.sendAndRequestService.requestForGET(Constants.app_urls.ASH.ASH_ENTRY.GET_MEASURES +encodeURI(this.assetType) + '/'+encodeURI(this.SchCode) + '/'+this.makeModelList[0].make + '/'+this.makeModelList[0].model).subscribe((data) => {
          this.measuresList = data;
         console.log("this.measuresList:::"+JSON.stringify(this.measuresList));
         
        }, error => {
          this.spinnerService.hide();
        });
        // get activites
        this.sendAndRequestService.requestForGET(Constants.app_urls.ASH.ASH_ENTRY.GET_ACTIVITES +encodeURI(this.assetType) + '/'+encodeURI(this.SchCode) + '/'+this.makeModelList[0].make + '/'+this.makeModelList[0].model).subscribe((data) => {
          this.activityList = data;
         console.log("this.activityList:::"+JSON.stringify(this.activityList));
         
        }, error => {
          this.spinnerService.hide();
        });
        //get facility 
        console.log("getAssetTypes for facilityId::" + this.facilityId);
          this.sendAndRequestService.requestForGET(Constants.app_urls.CONFIG.FACILITY.FIND_FACILITY_BY_FACILITYID + this.facilityId).subscribe((data) => {
          this.facility = data;
          console.log( this.facility);
        }, error => {
          this.spinnerService.hide();
        });
        }, error => {
          this.spinnerService.hide();
        });
        }
      })
       this.entryScheduleReportGroup.get('Asset_Type').valueChanges.subscribe(asstyp => {
        console.log("ngonit Asset_Type:::"+ asstyp);
        if(asstyp===undefined){
          console.log("ngonit undefined Asset_Type:::"+ asstyp);
        }
        else{
          this.assetType=asstyp;
         
        }
       }) 
            
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
      //'0':[null, Validators.compose([Validators.required])],
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
        this.ashEntry = resp;
        this.entryScheduleReportGroup.patchValue({
          id: this.ashEntry.id,
          // failure_id: this.resp.failure_id,  !!this.resp.actionTargetDate ? new Date(this.resp.actionTargetDate) : '',
          Schedule_date: !!this.ashEntry.scheduleDate?new Date(this.ashEntry.scheduleDate) : '',
          //Depot_Name: this.resp.depo,
          //Power_Block: this.resp.pbOperationSeqId,
          //Asset_Type: this.resp.assetType,
          //Schedule: this.resp.scheduleCode,
          Details_Of_Maint: this.ashEntry.detailsOfMaint,
          Done_By: this.ashEntry.doneBy,
          Remarks: this.ashEntry.remarks,
          //'facilityId': this.resp.facilityId,
          Incharge: this.ashEntry.initialOfIncharge,
         // Asset_Id: [this.resp.assetId],

 
        });
        console.log("::: this.entryScheduleReportGroup" + this.entryScheduleReportGroup);
        this.spinnerService.hide();
        this.DepotName=this.ashEntry.facilityId;
        this.PwrBlock=this.ashEntry.pbOperationSeqId;
        this.AssetTyp=this.ashEntry.assetType;
        console.log("assetIdList mapped for edit page for ng mpodel::"+this.assetIdList);
       if(this.assetIdList===undefined){
        console.log("assetid not mapped for edit page for ng mpodel undefined");
       }else{
        
         this.AsstId=this.ashEntry.assetId;
         this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.GET_MAKE_MODEL +this.AsstId +'/'+this.assetType + '/'+this.facilityId).subscribe((data) => {
          this.makeModelList = data;
         console.log("this.makeModelList:::"+JSON.stringify(this.makeModelList));
        }, error => {
          this.spinnerService.hide();
        });       
       }
       this.AsstId=this.ashEntry.assetId;  
       this.SchCode=this.ashEntry.scheduleCode;
      })

      
  }
  
  onGoBack() {
    this.router.navigate(['../../'], { relativeTo: this.route });
  }
  onEntryScheduleReportSubmit() {
   
    if (this.entryScheduleReportGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    if (this.save) {
              
      console.log("while saving ash entery data is :::"+JSON.stringify(this.ashEntry));
      console.log(this.measureMap);
      const convMeasureMap = {};
      this.measureMap.forEach((val: string, key: string) => {
      convMeasureMap[key] = val;
      });

      const convActivityMap = {};
      this.activityMap.forEach((val: string, key: string) => {
      convActivityMap[key] = val;
      });
      var saveAshModel = {
        'scheduleDate': this.entryScheduleReportGroup.value.Schedule_date,
        'depotName': this.entryScheduleReportGroup.value.Depot_Name,
        'pbOperationSeqId': this.entryScheduleReportGroup.value.Power_Block,
        'assetType': this.entryScheduleReportGroup.value.Asset_Type,
        'scheduleCode': this.entryScheduleReportGroup.value.Schedule,       
        'detailsOfMaint':  this.ashEntry.detailsOfMaint,
        'doneBy':  this.ashEntry.doneBy,
        'remarks':  this.ashEntry.remarks,
        'initialOfIncharge':  this.ashEntry.initialOfIncharge,
        'facilityId': this.facilityId,
        'assetId': this.entryScheduleReportGroup.value.Asset_Id,
        "createdBy": this.loggedUserData.username,
        "createdOn": new Date(),
        "status": 'EntryPending',
        "dataDiv": this.facility.division,
        "measureMap":convMeasureMap,
        "activityMap":convActivityMap
      }
      console.log(" model for save ash entry:::" + JSON.stringify(saveAshModel));
      this.sendAndRequestService.requestForPOST(Constants.app_urls.ASH.ASH_ENTRY.SAVE_ENTY, saveAshModel, false).subscribe(response => {
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
  
  updatedMeasures:String = "";
  measureMap = new Map<string, string>();
  //mastr=any;
  changeMeasure(index,val) {
    //this.measuresList[index].activityName=val;
    this.updatedMeasures+=val+",";
    //this.mmapastr+=","+val;
    
    this.measureMap.set(this.measuresList[index].activityPositionId,val);
    console.log("updated value is::"+JSON.stringify(this.measureMap.get(this.measuresList[index].activityPositionId)));
   
  }
  activityMap = new Map<string, string>();
  //mastr=any;
  changeActivity(index,val) {
    //this.measuresList[index].activityName=val;
    this.updatedMeasures+=val+",";
    //this.mmapastr+=","+val;
    
    this.activityMap.set(this.activityList[index].activityPositionId,val);
    console.log("updated value is::"+JSON.stringify(this.activityMap.get(this.activityList[index].activityPositionId)));
   
  }
  
  

}
