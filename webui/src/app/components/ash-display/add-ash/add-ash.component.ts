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
  selector: 'app-add-ash',
  templateUrl: './add-ash.component.html',
  styleUrls: ['./add-ash.component.css']
})
export class AddAshComponent implements OnInit {

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
  addAssetDailyScheduleReportGroup: FormGroup;
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
  matcher = new MyErrorStateMatcher();
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
      
        this.addAssetDailyScheduleReportGroup.get('Schedule_date').valueChanges.subscribe(date => {
            console.log("ngonit scheduleDate:::"+ date);
            if(date===undefined){
              console.log("ngonit undefined scheduleDate:::"+ date);
            }
            else
            this.scheduleDate = this.datePipe.transform(date, "yyyy-MM-dd");
           })
        this.addAssetDailyScheduleReportGroup.get('Depot_Name').valueChanges.subscribe(depo => {
          console.log("ngonit facilityid:::"+ depo);
          this.facilityId = depo;
          if(depo===undefined){
            console.log("ngonit undefined facilityid:::"+ depo);
          }else
          this.selectedDepoName(this.facilityId);
        })
        this.addAssetDailyScheduleReportGroup.get('Power_Block').valueChanges.subscribe(pbseqId => {
            console.log("ngonit Power_Block:::"+ pbseqId);
            if(pbseqId===undefined){
              console.log("ngonit undefined Power_Block:::"+ pbseqId);
            }
            else
            this.getAssetTypes(pbseqId);
           })
        this.addAssetDailyScheduleReportGroup.get('Asset_Type').valueChanges.subscribe(asstyp => {
            console.log("ngonit Asset_Type:::"+ asstyp);
            if(asstyp===undefined){
              console.log("ngonit undefined Asset_Type:::"+ asstyp);
            }
            else{
              this.getAssetIdForEdit(asstyp);
              this.getAshDataById(this.id);
            }
           }) 
        this.addAssetDailyScheduleReportGroup.get('Asset_Id').valueChanges.subscribe(asstid => {
            console.log("ngonit Asset_Id:::"+ asstid);
            if(asstid===undefined){
              console.log("ngonit undefined Asset_Type:::"+ asstid);
            }
            else{
              this.getScheduleCodes(asstid);
            }
           })        
      // this.addAssetDailyScheduleReportGroup.valueChanges.subscribe(selectedValue => {
      //  // this.onFormValuesChanged();
      //  console.log('form value changed')
      //  console.log(selectedValue)
      // });
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = 'Edit';
      this.getAshDataById(this.id);
    } else {
      this.save = true;
      this.update = false;
      this.title = 'Save';
    }
  

  }
  createAssetDailyScheduleReportGroup() {
    this.addAssetDailyScheduleReportGroup = this.formBuilder.group({
      id: 0,
      'Schedule_date': [null, Validators.compose([Validators.required])],
      'Depot_Name': [null, Validators.compose([Validators.required])],
      'Power_Block': [null, Validators.compose([Validators.required])],
      'Asset_Type': [null,],
      'From_Kilometer': [null],
      'To_Kilometer': [null],
      'Schedule': [null, Validators.compose([Validators.required])],
      'Details_Of_Maint': [null, Validators.compose([Validators.required])],
      'Done_By': [null, Validators.compose([Validators.required])],
      'Remarks': [null, Validators.compose([Validators.required])],
      'Incharge': [null, Validators.compose([Validators.required])],
      'Asset_Id': [null, Validators.compose([Validators.required])]
    }, { validator: this.checkKms });

  }

  checkKms(c: AbstractControl): { invalid: boolean } {
    if (c.get('From_Kilometer').value > c.get('To_Kilometer').value) {
      return { invalid: true };
    }
  }

  selectedScheduleDate(date) {
    //console.log("loggedUserData::" + this.loggedUserData.depoNames);
    console.log("selectedScheduleDate::" + date);
    this.scheduleDate = this.datePipe.transform(date, "yyyy-MM-dd");
    console.log(this.datePipe.transform(date, "yyyy-MM-dd"));

  }
  
  selectedDepoName(depo) {
    console.log("selected Depo id::" +depo);
    // let statusItem=[];
    this.facilityId = depo;
    this.sendAndRequestService.requestForGET(Constants.app_urls.ASH.ASH.STATUS_ON_STATUS_TYPE + 'PB_STATIC_STATUS').subscribe((data) => {
      this.statusItems = data;

      this.sendAndRequestService.requestForGET(Constants.app_urls.OPERATIONS.POWER_BLOCK.GET_POWER_BLOCKS_BASED_ON_FACILITYID_AND_CREATEDDATE +'/'+this.facilityId+'/'+this.scheduleDate).subscribe((data) => { //'/30000/2019-03-04').subscribe((data) => {
        //this.powerBlockList = [...data,  this.statusItems
        this.powerBlockList = [...data, ...this.statusItems.map(value => {
          //console.log("value:::"+value.statusCode);
          return ({ "pbOperationSeqId": value.statusCode });
        })
        ];
        console.log( this.powerBlockList);

      }, error => {
        this.spinnerService.hide();
      });

    }, error => {
      this.spinnerService.hide();
    });



  }
  process(data: any) {
    let statuses: any[] = [];
    for (let status of data) {
      statuses.push({ "pbOperationSeqId": status.statusCode });
    }
    return statuses;
  }
  selectedPowerBlock($event) {
    console.log("selected power block" + $event.value);
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.GET_ASSETID_BASED_ON_ASSETTYPE_FACILITYID + '131-4A' + '/30015').subscribe((data) => {
      this.assetTypeList = data;
    }, error => {
      this.spinnerService.hide();
    });
  }
  getAssetTypes(pbseqId) {
    console.log("getAssetTypes for facilityId::" + this.facilityId);
    this.sendAndRequestService.requestForGET(Constants.app_urls.CONFIG.FACILITY.FIND_FACILITY_BY_FACILITYID + this.facilityId).subscribe((data) => {
      this.facility = data;
      console.log( this.facility);
      this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_ASSET_TYPES + this.facility.depotType).subscribe((data) => {
        this.assetTypeList = data;
        console.log(this.assetTypeList)
      }, error => {
        this.spinnerService.hide();
      });
    }, error => {
      this.spinnerService.hide();
    });

  }
  getAssetIds(assetyp) {
    console.log("selected asset type ::" +assetyp);
    this.assetType = assetyp;
    // this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.GET_ASSETIDS_BY_ASSETTYPE_FACILITYID_FROMKM_TOKM + this.assetType + '/'+this.facilityId + '/' +this.fromKm+ '/'+this.toKm).subscribe((data) => {
    //   this.assetIdList = data;
    // }, error => {
    //   this.spinnerService.hide();
    // });
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
  getScheduleCodes(schedulecode) {
    console.log("selected asset type for schedule code::" + this.assetType);
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_SCHEDULE_CODE_BASED_ON_ASSETTYPE + this.assetType).subscribe((data) => {
      this.scheduleCodeList = data;
      console.log("selected asset type for schedule code list :::::" + JSON.stringify(data));
    }, error => {
      this.spinnerService.hide();
    });
  }

  selectedFromKm(fKM) {
    if (this.assetType == null) {
      console.log("selected assetType" + this.assetType);
      this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.GET_ASSETIDS_BY_FACILITYID_FROMKM_TOKM + '30015' + '/121' + '/131').subscribe((data) => {
        this.assetIdList = data;
      }, error => {
        this.spinnerService.hide();
      });
    }
    console.log("assetType:::" + fKM);
    this.fromKm = fKM;
    console.log("fromKm::" + this.fromKm);
  }
  selectedToKm(tKM) {
    console.log("selectedToKm" + tKM);
    this.toKm = tKM;
    if (this.assetType == null) {
      console.log("selected assetType" + this.assetType);
      this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.GET_ASSETIDS_BY_FACILITYID_FROMKM_TOKM + this.facilityId + '/' +this.fromKm+ '/'+this.toKm).subscribe((data) => {
        this.assetIdList = data;
      }, error => {
        this.spinnerService.hide();
      });
    }
    else{
      console.log("selected assetType" + this.assetType);
      this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.GET_ASSETIDS_BY_ASSETTYPE_FACILITYID_FROMKM_TOKM + this.assetType + '/'+this.facilityId + '/' +this.fromKm+ '/'+this.toKm).subscribe((data) => {
        this.assetIdList = data;
      }, error => {
        this.spinnerService.hide();
      });
      
    }
  }
  getsData() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.ASH.ASH.GET_ASH_DEPO).subscribe((data) => {
      this.List = data;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }
  get scheduleDat() {
    return this.addAssetDailyScheduleReportGroup.get('Schedule_date');
   }
   get depoId() {
    return this.addAssetDailyScheduleReportGroup.get('Depot_Name');
   }

  onFormValuesChanged() {
    //console.log("values on onformvalueschanged:::"+ this.addAssetDailyScheduleReportGroup.get('Schedule_date'));
   
    this.scheduleDat.valueChanges.subscribe(
      date => {
        console.log("ngonit scheduleDate:::"+ date);
        this.scheduleDate = this.datePipe.transform(date, "yyyy-MM-dd");
      }
    );
    this.depoId.valueChanges.subscribe(
      depo => {
        console.log("ngonit depoId:::"+ depo);
        this.facilityId = depo;
        this.selectedDepoName(this.facilityId);
      }
    );
    // this.addAssetDailyScheduleReportGroup.get('Schedule_date').valueChanges.subscribe(date => {
    //   console.log("ngonit scheduleDate:::"+ date);
    //   this.scheduleDate = this.datePipe.transform(date, "yyyy-MM-dd");
    // })
    // this.addAssetDailyScheduleReportGroup.get('Depot_Name').valueChanges.subscribe(depo => {
    //   console.log("ngonit facilityid:::"+ depo);
    //   this.facilityId = depo;
    //   this.selectedDepoName(this.facilityId);
    // })
    
  }
  getAshDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.ASH.ASH.GET_ASH_DEPO_BY_ID + id)
      .subscribe((resp) => {
        console.log("Ash edit record response:::" + JSON.stringify(resp))
        this.resp = resp;
        this.addAssetDailyScheduleReportGroup.patchValue({
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
        console.log("::: this.addAssetDailyScheduleReportGroup" + this.addAssetDailyScheduleReportGroup);
        this.spinnerService.hide();
        this.DepotName=this.resp.facilityId;
        this.PwrBlock=this.resp.pbOperationSeqId;
        this.AssetTyp=this.resp.assetType;
        console.log("assetIdList mapped for edit page for ng mpodel::"+this.assetIdList);
       if(this.assetIdList===undefined){
        console.log("assetid not mapped for edit page for ng mpodel undefined");
       }else{
        this.assetIdList.map(asset => {      
          console.log("assetid mapped for edit page for ng mpodel::"+asset.assetId+"::this.resp.assetId::"+this.resp.assetId);      
          if(asset.assetId===this.resp.assetId)    
          console.log("assetid mapped for edit page for ng mpodel::"+asset.assetId);      
           this.assetidObjModel=asset;
         })
         this.AsstId= [this.assetidObjModel];       
       }
       this.SchCode=this.resp.scheduleCode;
      })
  }
  
  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }
  onAssetDailyScheduleReportSubmit() {
    // this.isSubmit = true;
    if (this.addAssetDailyScheduleReportGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    if (this.save) {
      let assetIdAssetTypes = [];
      this.addAssetDailyScheduleReportGroup.controls.Asset_Id.value.map(value => {
        assetIdAssetTypes.push(value.assetId + "_" + value.assetType);
      })
      let ele = this.addAssetDailyScheduleReportGroup.value.Asset_Id;
      var saveAshModel = {
        'scheduleDate': this.addAssetDailyScheduleReportGroup.value.Schedule_date,
        'depotName': this.addAssetDailyScheduleReportGroup.value.Depot_Name,
        'pbOperationSeqId': this.addAssetDailyScheduleReportGroup.value.Power_Block,
        'assetType': this.addAssetDailyScheduleReportGroup.value.Asset_Type,
        'scheduleCode': this.addAssetDailyScheduleReportGroup.value.Schedule,
        'detailsOfMaint': this.addAssetDailyScheduleReportGroup.value.Details_Of_Maint,
        'doneBy': this.addAssetDailyScheduleReportGroup.value.Done_By,
        'remarks': this.addAssetDailyScheduleReportGroup.value.Remarks,
        'facilityId': this.facilityId,
        'initialOfIncharge': this.addAssetDailyScheduleReportGroup.value.Incharge,
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
          this.commonService.showAlertMessage("Ash Data Saved Successfully");
          //this.router.navigate(['/'], { relativeTo: this.route });
          window.location.reload;
        } else {
          this.commonService.showAlertMessage("Ash Data Saving Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Ash Data Saving Failed.");
      });
    }else if(this.update){
      let assetIdAssetTypes = [];
      this.addAssetDailyScheduleReportGroup.controls.Asset_Id.value.map(value => {
        assetIdAssetTypes.push(value.assetId + "_" + value.assetType);
      })
     var updateAshModel = {
       
        "id":this.id,
        'scheduleDate': this.addAssetDailyScheduleReportGroup.value.Schedule_date,
        'depotName': this.addAssetDailyScheduleReportGroup.value.Depot_Name,
        'pbOperationSeqId': this.addAssetDailyScheduleReportGroup.value.Power_Block,
        'assetType': this.addAssetDailyScheduleReportGroup.value.Asset_Type,
        'scheduleCode': this.addAssetDailyScheduleReportGroup.value.Schedule,
        'detailsOfMaint': this.addAssetDailyScheduleReportGroup.value.Details_Of_Maint,
        'doneBy': this.addAssetDailyScheduleReportGroup.value.Done_By,
        'remarks': this.addAssetDailyScheduleReportGroup.value.Remarks,
        'facilityId': this.facilityId,
        'initialOfIncharge': this.addAssetDailyScheduleReportGroup.value.Incharge,
        'assetId': JSON.stringify(assetIdAssetTypes),
        "createdBy": this.loggedUserData.username,
        "createdOn": new Date(),
        "status": 'EntryPending',
        "dataDiv": this.facility.division,
        "updatedBy": this.loggedUserData.username,
        "updatedOn": new Date()
      }   
     var message = 'Updated';
     var failedMessage = "Updating";
      this.sendAndRequestService.requestForPUT(Constants.app_urls.ASH.ASH.UPDATE_ASH,updateAshModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Failure Analysis Data "+message+" Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Failure Analysis Data "+failedMessage+" Failed."); 
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Failure Analysis Data "+failedMessage+" Failed.");
      })
    }
  }

}
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return (control && control.invalid);
  }
}

