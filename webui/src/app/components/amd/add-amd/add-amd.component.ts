import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Constants } from 'src/app/common/constants';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog,DateAdapter, MAT_DATE_FORMATS } from '@angular/material';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';
import { IfStmt } from '@angular/compiler';

@Component({
  selector: 'app-add-amd',
  templateUrl: './add-amd.component.html',
  providers: [
    {
        provide: DateAdapter, useClass: AppDateAdapter
    },
    {
        provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
    }
    ]
})
export class AddAmdComponent implements OnInit {

  pagination = Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
  zoneHierarchy:any = JSON.parse(sessionStorage.getItem('zoneData'));
  divisionHierarchy:any = JSON.parse(sessionStorage.getItem('divisionData'));
  subDivisionHierarchy:any = JSON.parse(sessionStorage.getItem('subDivData'));
  depotHierarchy:any = JSON.parse(sessionStorage.getItem('depotData'))
  save: boolean = true;
  update: boolean = false;
  title: string = Constants.EVENTS.ADD;
  isSubmit: boolean = false;
  assetMasterFormGroup: FormGroup;
  id: number = 0;
  pattern = "/^[a-zA-Z ]*$/";
  depoTypeList = [];
  assetTypeList = [];
  functionalUnitList: any;
  functionalUnitsList: any;
  allFunctionalUnitsList: any;
  amdFormErrors: any;
  zoneList: any;
  divisionList: any;  
  subDivisionList: any;
  resp: any;
  assetsList: any;
  assetTypeParametersData: any;
  scheduleList: any;
  zone: any;
  facilityData: any;
  division: any;
  depotsList:any;
  toMinDate = new Date();
  currentDate = new Date();
  dateFormat = 'MM-dd-yyyy ';
  depotCode: any;
  makeName: any;
  modelName: any;
  subDivList:any;
  facilityList:any;
  facility:any;
  enableZone :boolean;
   enableDivision :boolean;  
   enableSubDivision :boolean;
   enableDepot : boolean;
   subDivision:any;
  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private dialog: MatDialog,
  
    private sendAndRequestService:SendAndRequestService
  ) 
  {
    // Reactive form errors
    this.amdFormErrors = {
      createdOn: {},
      dateOfCommision: {},
      dateOfManufacture: {},
      dateOfReceived: {},
      equippedDate: {},
      expiryDate: {},
      lugDate: {},
      stripDate: {},
      warrantyAmcEndDate: {}, 
    };
  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
    this.findFunctionalUnits();
    this.findZones();
    this.findDepoTypeList();
    this.findMakeDetails();
    this.findModelDetails();
    if (!isNaN(this.id)) {
      this.updateAmdForm();
      this.assetMasterFormGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      });
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;
      this.getAssetMasterDataById(this.id);
    } else {     
      this.createAmdForm();
      this.save = true;
      this.update = false; 
      this.title = Constants.EVENTS.ADD;      
    }  
    
  }
  onFormValuesChanged() {
    for (const field in this.amdFormErrors) {
      if (!this.amdFormErrors.hasOwnProperty(field)) {
        continue;
      }
      this.amdFormErrors[field] = {};
      const control = this.assetMasterFormGroup.get(field);

      if (control && control.dirty && !control.valid) {
        this.amdFormErrors[field] = control.errors;
      }
    }
  }

  createAmdForm() {
    this.assetMasterFormGroup = this.formBuilder.group({
      id: 0,
      'zone': [null],
      'dataDiv': [null],
      'subDivision': [null],
      'type': [null],
      'facilityId': [null],
      'adeeSection': [null],
      'majorSection': [null],
      'assetType': [null],
      'section': [null],
      'locationPosition': [null],
      'kilometer': [null],
      'assetId': [null,Validators.required, this.duplicateFacilityIdAssetType.bind(this)],
      'part1': [null],
      'part2': [null],
      'part3': [null],
      'elementarySection': [null],
      'line': [null],
      'parentAssetType': [null],
      'parentAssetTypeId': [null],
      'make': [null],
      'model': [null],
      'structure': [null],
      'warrantyAmc': [null],
      'station': [null],
      'positionId': [null],
      'capacityRating': [null],
      'oemSerial': [null],
      'rlyAssignedSerial': [null],
      'source': [null],
      'implantation': [null],
      'vendor': [null],
      'namePlateDetails': [null],
      'end1Side1': [null],
      'end2Side2': [null],
      'remark1': [null],
      'remark2': [null],
      'codalLife': [null],
      'voltage': [null],
      'batch': [null],
      'stagger1': [null],
      'stagger2': [null],
      'stagger3': [null],
      'stay1InsulatorBatch': [null],
      'stay1InsulatorMake': [null],
      'bracket1InsulatorBatch': [null],
      'bracket1InsulatorMake': [null],
      'stag1Ton9InsulatorBatch': [null],
      'stag1Ton9InsulatorMake': [null],
      'rod1InsulatorBatch': [null],
      'rod1InsulatorMake': [null],
      'pedestal1InsulatorBatch': [null],
      'pedestal1InsulatorMake': [null],
      'core1InsulatorBatch': [null],
      'core1InsulatorMake': [null],
      'stay2InsulatorBatch': [null],
      'stay2InsulatorMake': [null],
      'bracket2InsulatorBatch': [null],
      'bracket2InsulatorMake': [null],
      'stag2Ton9InsulatorBatch': [null],
      'stag2Ton9InsulatorMake': [null],
      'rod2InsulatorBatch': [null],
      'rod2InsulatorMake': [null],
      'pedestal2InsulatorBatch': [null],
      'pedestal2InsulatorMake': [null],
      'core2InsulatorBatch': [null],
      'core2InsulatorMake': [null],
      'stay3InsulatorBatch': [null],
      'stay3InsulatorMake': [null],
      'bracket3InsulatorBatch': [null],
      'bracket3InsulatorMake': [null],
      'stag3Ton9InsulatorBatch': [null],
      'stag3Ton9InsulatorMake': [null],
      'rod3InsulatorBatch': [null],
      'rod3InsulatorMake': [null],
      'pedestal3InsulatorBatch': [null],
      'pedestal3InsulatorMake': [null],
      'core3InsulatorBatch': [null],
      'core3InsulatorMake': [null],
      'stagger': [null],
      'createdOn': [null],
      'dateOfCommision': [null],
      'dateOfManufacture': [null],
      'dateOfReceived': [null],
      'equippedDate': [null],
      'expiryDate': [null],
      'lugDate': [null],
      'stripDate': [null],
      'warrantyAmcEndDate': [null],
      
    });
    
  }
  updateAmdForm() {
    this.assetMasterFormGroup = this.formBuilder.group({
      id: 0,
      'zone': [null],
      'dataDiv': [null],
      'subDivision': [null],
      'type': [null],
      'facilityId': [null],
      'adeeSection': [null],
      'majorSection': [null],
      'assetType': [null],
      'section': [null],
      'locationPosition': [null],
      'kilometer': [null],
      'assetId': [null,Validators.required, this.duplicateFacilityIdAssetTypeAndId.bind(this)],
      'part1': [null],
      'part2': [null],
      'part3': [null],
      'elementarySection': [null],
      'line': [null],
      'parentAssetType': [null],
      'parentAssetTypeId': [null],
      'make': [null],
      'model': [null],
      'structure': [null],
      'warrantyAmc': [null],
      'station': [null],
      'positionId': [null],
      'capacityRating': [null],
      'oemSerial': [null],
      'rlyAssignedSerial': [null],
      'source': [null],
      'implantation': [null],
      'vendor': [null],
      'namePlateDetails': [null],
      'end1Side1': [null],
      'end2Side2': [null],
      'remark1': [null],
      'remark2': [null],
      'codalLife': [null],
      'voltage': [null],
      'batch': [null],
      'stagger1': [null],
      'stagger2': [null],
      'stagger3': [null],
      'stay1InsulatorBatch': [null],
      'stay1InsulatorMake': [null],
      'bracket1InsulatorBatch': [null],
      'bracket1InsulatorMake': [null],
      'stag1Ton9InsulatorBatch': [null],
      'stag1Ton9InsulatorMake': [null],
      'rod1InsulatorBatch': [null],
      'rod1InsulatorMake': [null],
      'pedestal1InsulatorBatch': [null],
      'pedestal1InsulatorMake': [null],
      'core1InsulatorBatch': [null],
      'core1InsulatorMake': [null],
      'stay2InsulatorBatch': [null],
      'stay2InsulatorMake': [null],
      'bracket2InsulatorBatch': [null],
      'bracket2InsulatorMake': [null],
      'stag2Ton9InsulatorBatch': [null],
      'stag2Ton9InsulatorMake': [null],
      'rod2InsulatorBatch': [null],
      'rod2InsulatorMake': [null],
      'pedestal2InsulatorBatch': [null],
      'pedestal2InsulatorMake': [null],
      'core2InsulatorBatch': [null],
      'core2InsulatorMake': [null],
      'stay3InsulatorBatch': [null],
      'stay3InsulatorMake': [null],
      'bracket3InsulatorBatch': [null],
      'bracket3InsulatorMake': [null],
      'stag3Ton9InsulatorBatch': [null],
      'stag3Ton9InsulatorMake': [null],
      'rod3InsulatorBatch': [null],
      'rod3InsulatorMake': [null],
      'pedestal3InsulatorBatch': [null],
      'pedestal3InsulatorMake': [null],
      'core3InsulatorBatch': [null],
      'core3InsulatorMake': [null],
      'stagger': [null],
      'createdOn': [null],
      'dateOfCommision': [null],
      'dateOfManufacture': [null],
      'dateOfReceived': [null],
      'equippedDate': [null],
      'expiryDate': [null],
      'lugDate': [null],
      'stripDate': [null],
      'warrantyAmcEndDate': [null],
      
    });
   /* this.depotCode = 'TRD';    
    this.findAssetTypeList(Constants.ASSERT_TYPE[this.depotCode]);
    this.functionalUnitsList = [];
    this.getFunctionalUnits(this.depotCode); */
  }
  
   public get f() { return this.assetMasterFormGroup.controls; } 

  
  addEvent($event) {
    this.toMinDate = new Date($event.value);
  }
  
 /* updateAssertType($event) {
    if ($event.value) {
      this.depoTypeList.filter(element => {
        if(element.id === $event.value){
            this.depotCode = element.code;
        }
      });  
      
      this.findAssetTypeList(Constants.ASSERT_TYPE[this.depotCode]);
      this.functionalUnitList = [];
      this.functionalUnitList = this.allFunctionalUnitsList.filter(element => {
        return element.depotType == this.depotCode;
      });
    }
  } */

  // getFunctionalUnits (depotType){
  //      this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY_BASED_ON_DEPOTTYPE+depotType).subscribe((data) => {
  //         this.functionalUnitList = data;
  //   });
  // }
  /*
  getDepot(){
    var zone = this.assetMasterFormGroup.value.zone ;
    this.depotsList = this.divisionData.filter(value => {
      return value.zone == zone;
    });
  }
  getSubDiv(){
    var dataDiv = this.assetMasterFormGroup.value.dataDiv ;
    console.log("dataDiv"+dataDiv)
    this.subDivList = this.subDivData.filter(value => {
      return value.division == dataDiv;
    });
    console.log("subDivList"+JSON.stringify(this.subDivList))
  }*/
  getAssetMasterDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.GET_ASSET_MASTER_DATA_ID + id)
    .subscribe((resp) => {
        this.resp = resp;
        if(this.resp.assetType) {
          this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.GET_PARAMETER_NAMES_BASED_ON_ASSET_TYPES +this.resp.assetType+'/' + 'Yes').subscribe(response => {
             this.spinnerService.hide();
             this.assetTypeParametersData = response;
           })
       }   
       this.enableDivision=true;   
      this.enableSubDivision=true;
      console.log("response of amd==="+JSON.stringify(resp));
       this.sendAndRequestService.requestForGET(Constants.app_urls.CONFIG.FACILITY.FIND_FACILITY_BY_FACILITYID+this.resp.facilityId)
        .subscribe((data) => {          
          this.facility = data;              
          this.assetMasterFormGroup.patchValue({zone:this.facility.zone,
            dataDiv :this.facility.division,
            subDivision :this.facility.subDivision
            //facilityId :this.facility.subDivision
            })           
        })
        if(this.resp.type != null){  
          console.log("type=="+this.resp.type)        
        this.findFacilities();
        }
        console.log("dept type=="+JSON.stringify(this.depotHierarchy));
        this.assetMasterFormGroup.patchValue({
        id: this.resp.id,
        type: this.resp.type,          
        facilityId: this.resp.facilityId,
        adeeSection: this.resp.adeeSection,
        majorSection: this.resp.majorSection,
        assetType: this.resp.assetType,
        section: this.resp.section,
        locationPosition: this.resp.locationPosition,
        kilometer: this.resp.kilometer,
        assetId: this.resp.assetId,
        part1: this.resp.part1,
        part2: this.resp.part2,
        part3: this.resp.part3,
        elementarySection: this.resp.elementarySection,
        line: this.resp.line,
        parentAssetType: this.resp.parentAssetType,
        parentAssetTypeId: this.resp.parentAssetTypeId,
        make: this.resp.make,
        model: this.resp.model,
        structure: this.resp.structure,
        warrantyAmc: this.resp.warrantyAmc,
        station: this.resp.station,
        positionId: this.resp.positionId,
        capacityRating: this.resp.capacityRating,
        oemSerial: this.resp.oemSerial,
        rlyAssignedSerial: this.resp.rlyAssignedSerial,
        source: this.resp.source,
        implantation: this.resp.implantation,
        vendor: this.resp.vendor,
        namePlateDetails: this.resp.namePlateDetails,
        end1Side1: this.resp.end1Side1,
        end2Side2: this.resp.end2Side2,
        remark1: this.resp.remark1,
        remark2: this.resp.remark2,
        codalLife: this.resp.codalLife,
        voltage: this.resp.voltage,
        batch: this.resp.batch,
        stagger1: this.resp.stagger1,
        stagger2: this.resp.stagger2,
        stagger3: this.resp.stagger3,
        stay1InsulatorBatch: this.resp.stay1InsulatorBatch,
        stay1InsulatorMake: this.resp.stay1InsulatorMake,
        bracket1InsulatorBatch: this.resp.bracket1InsulatorBatch,
        bracket1InsulatorMake: this.resp.bracket1InsulatorMake,
        stag1Ton9InsulatorBatch: this.resp.stag1Ton9InsulatorBatch,
        stag1Ton9InsulatorMake: this.resp.stag1Ton9InsulatorMake,
        rod1InsulatorBatch: this.resp.rod1InsulatorBatch,
        rod1InsulatorMake: this.resp.rod1InsulatorMake,
        pedestal1InsulatorBatch: this.resp.pedestal1InsulatorBatch,
        pedestal1InsulatorMake: this.resp.pedestal1InsulatorMake,
        core1InsulatorBatch: this.resp.core1InsulatorBatch,
        core1InsulatorMake: this.resp.core1InsulatorMake,
        stay2InsulatorBatch: this.resp.stay2InsulatorBatch,
        stay2InsulatorMake: this.resp.stay2InsulatorMake,
        bracket2InsulatorBatch: this.resp.bracket2InsulatorBatch,
        bracket2InsulatorMake: this.resp.bracket2InsulatorMake,
        stag2Ton9InsulatorBatch: this.resp.stag2Ton9InsulatorBatch,
        stag2Ton9InsulatorMake: this.resp.stag2Ton9InsulatorMake,
        rod2InsulatorBatch: this.resp.rod2InsulatorBatch,
        rod2InsulatorMake: this.resp.rod2InsulatorMake,
        pedestal2InsulatorBatch: this.resp.pedestal2InsulatorBatch,
        pedestal2InsulatorMake: this.resp.pedestal2InsulatorMake,
        core2InsulatorBatch: this.resp.core2InsulatorBatch,
        core2InsulatorMake: this.resp.core2InsulatorMake,
        stay3InsulatorBatch: this.resp.stay3InsulatorBatch,
        stay3InsulatorMake: this.resp.stay3InsulatorMake,
        bracket3InsulatorBatch: this.resp.bracket3InsulatorBatch,
        bracket3InsulatorMake: this.resp.bracket3InsulatorMake,
        stag3Ton9InsulatorBatch: this.resp.stag3Ton9InsulatorBatch,
        stag3Ton9InsulatorMake: this.resp.stag3Ton9InsulatorMake,
        rod3InsulatorBatch: this.resp.rod3InsulatorBatch,
        rod3InsulatorMake: this.resp.rod3InsulatorMake,
        pedestal3InsulatorBatch: this.resp.pedestal3InsulatorBatch,
        pedestal3InsulatorMake: this.resp.pedestal3InsulatorMake,
        core3InsulatorBatch: this.resp.core3InsulatorBatch,
        core3InsulatorMake: this.resp.core3InsulatorMake,
        stagger: this.resp.stagger,
        createdOn: new Date(this.resp.createdOn),
        dateOfCommision: !!this.resp.dateOfCommision ? new Date(this.resp.dateOfCommision) : '',
        dateOfManufacture:!!this.resp.dateOfManufacture ? new Date(this.resp.dateOfManufacture):'',
        dateOfReceived:!!this.resp.dateOfReceived ? new Date(this.resp.dateOfReceived) :'',
        equippedDate: !!this.resp.equippedDate ? new Date(this.resp.equippedDate):'',
        expiryDate:!!this.resp.expiryDate ? new Date(this.resp.expiryDate):'',
        lugDate:!!this.resp.lugDate ? new Date(this.resp.lugDate):'',
        stripDate:!!this.resp.stripDate ? new Date(this.resp.stripDate):'',
        warrantyAmcEndDate:!!this.resp.warrantyAmcEndDate ? new Date(this.resp.warrantyAmcEndDate):'',
          
        });        
         this.spinnerService.hide();
      })
      
  }
  
  onAddAmdFormSubmit() {
    this.isSubmit = true;
    if (this.assetMasterFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    if (this.save) {
      var saveAmdModel = {
    "type" : this.assetMasterFormGroup.value.type,
    "facilityId" : this.assetMasterFormGroup.value.facilityId,
    "adeeSection" : this.assetMasterFormGroup.value.adeeSection,
    "majorSection" :this.assetMasterFormGroup.value.majorSection,
    "assetType" : this.assetMasterFormGroup.value.assetType,
    "section" : this.assetMasterFormGroup.value.section,
    "locationPosition" : this.assetMasterFormGroup.value.locationPosition,
    "kilometer" : this.assetMasterFormGroup.value.kilometer,
    "assetId" : this.assetMasterFormGroup.value.assetId,
    "part1" : this.assetMasterFormGroup.value.part1,
    "part2":  this.assetMasterFormGroup.value.part2,
    "part3":  this.assetMasterFormGroup.value.part3,
    "elementarySection":  this.assetMasterFormGroup.value.elementarySection,
    "line": this.assetMasterFormGroup.value.line,
    "parentAssetType": this.assetMasterFormGroup.value.parentAssetType,
    "parentAssetTypeId": this.assetMasterFormGroup.value.parentAssetTypeId,
    "make":  this.assetMasterFormGroup.value.make,
    "model":  this.assetMasterFormGroup.value.model,
    "structure": this.assetMasterFormGroup.value.structure,
    "warrantyAmc":  this.assetMasterFormGroup.value.warrantyAmc,
    "station": this.assetMasterFormGroup.value.station,
    "positionId":  this.assetMasterFormGroup.value.positionId,
    "capacityRating":  this.assetMasterFormGroup.value.capacityRating,
    "oemSerial":  this.assetMasterFormGroup.value.oemSerial,
    "rlyAssignedSerial":  this.assetMasterFormGroup.value.rlyAssignedSerial,
    "source":  this.assetMasterFormGroup.value.source,
    "implantation": this.assetMasterFormGroup.value.implantation,
    "vendor":  this.assetMasterFormGroup.value.vendor,
    "namePlateDetails":  this.assetMasterFormGroup.value.namePlateDetails,
    "end1Side1":  this.assetMasterFormGroup.value.end1Side1,
    "end2Side2": this.assetMasterFormGroup.value.end2Side2,
    "remark1":  this.assetMasterFormGroup.value.remark1,
    "remark2":  this.assetMasterFormGroup.value.remark2,
    "codalLife":  this.assetMasterFormGroup.value.codalLife,
    "voltage":  this.assetMasterFormGroup.value.voltage,
    "batch":  this.assetMasterFormGroup.value.batch,
    "stagger1":  this.assetMasterFormGroup.value.stagger1,
    "stagger2": this.assetMasterFormGroup.value.stagger2,
    "stagger3":  this.assetMasterFormGroup.value.stagger3,
    "stay1InsulatorBatch":  this.assetMasterFormGroup.value.stay1InsulatorBatch,
    "stay1InsulatorMake":  this.assetMasterFormGroup.value.stay1InsulatorMake,
    "bracket1InsulatorBatch":  this.assetMasterFormGroup.value.bracket1InsulatorBatch,
    "bracket1InsulatorMake":  this.assetMasterFormGroup.value.bracket1InsulatorMake,
    "stag1Ton9InsulatorBatch":  this.assetMasterFormGroup.value.stag1Ton9InsulatorBatch,
    "stag1Ton9InsulatorMake":  this.assetMasterFormGroup.value.stag1Ton9InsulatorMake,
    "rod1InsulatorBatch":  this.assetMasterFormGroup.value.rod1InsulatorBatch,
    "rod1InsulatorMake":  this.assetMasterFormGroup.value.rod1InsulatorMake,
    "pedestal1InsulatorBatch":  this.assetMasterFormGroup.value.pedestal1InsulatorBatch,
    "pedestal1InsulatorMake":  this.assetMasterFormGroup.value.pedestal1InsulatorMake,
    "core1InsulatorBatch":  this.assetMasterFormGroup.value.core1InsulatorBatch,
    "core1InsulatorMake": this.assetMasterFormGroup.value.core1InsulatorMake,
    "stay2InsulatorBatch":  this.assetMasterFormGroup.value.stay2InsulatorBatch,
    "stay2InsulatorMake": this.assetMasterFormGroup.value.stay2InsulatorMake,
    "bracket2InsulatorBatch":  this.assetMasterFormGroup.value.bracket2InsulatorBatch,
    "bracket2InsulatorMake":  this.assetMasterFormGroup.value.bracket2InsulatorMake,
    "stag2Ton9InsulatorBatch":  this.assetMasterFormGroup.value.stag2Ton9InsulatorBatch,
    "stag2Ton9InsulatorMake":  this.assetMasterFormGroup.value.stag2Ton9InsulatorMake,
    "rod2InsulatorBatch":  this.assetMasterFormGroup.value.rod2InsulatorBatch,
    "rod2InsulatorMake":  this.assetMasterFormGroup.value.rod2InsulatorMake,
    "pedestal2InsulatorBatch":  this.assetMasterFormGroup.value.pedestal2InsulatorBatch,
    "pedestal2InsulatorMake":  this.assetMasterFormGroup.value.pedestal2InsulatorMake,
    "core2InsulatorBatch":  this.assetMasterFormGroup.value.core2InsulatorBatch,
    "core2InsulatorMake":  this.assetMasterFormGroup.value.core2InsulatorMake,
    "stay3InsulatorBatch":  this.assetMasterFormGroup.value.stay3InsulatorBatch,
    "stay3InsulatorMake": this.assetMasterFormGroup.value.stay3InsulatorMake,
    "bracket3InsulatorBatch":  this.assetMasterFormGroup.value.bracket3InsulatorBatch,
    "bracket3InsulatorMake":  this.assetMasterFormGroup.value.bracket3InsulatorMake,
    "stag3Ton9InsulatorBatch":  this.assetMasterFormGroup.value.stag3Ton9InsulatorBatch,
    "stag3Ton9InsulatorMake":  this.assetMasterFormGroup.value.stag3Ton9InsulatorMake,
    "rod3InsulatorBatch":  this.assetMasterFormGroup.value.rod3InsulatorBatch,
    "rod3InsulatorMake": this.assetMasterFormGroup.value.rod3InsulatorMake,
    "pedestal3InsulatorBatch":  this.assetMasterFormGroup.value.pedestal3InsulatorBatch,
    "pedestal3InsulatorMake":  this.assetMasterFormGroup.value.pedestal3InsulatorMake,
    "core3InsulatorBatch":  this.assetMasterFormGroup.value.core3InsulatorBatch,
    "core3InsulatorMake":  this.assetMasterFormGroup.value.core3InsulatorMake,
    "dateOfCommision":this.assetMasterFormGroup.value.dateOfCommision,
    "stagger": this.assetMasterFormGroup.value.stagger,
    "createdOn":  this.assetMasterFormGroup.value.createdOn,
    "dateOfManufacture":this.assetMasterFormGroup.value.dateOfManufacture,
    "dateOfReceived":  this.assetMasterFormGroup.value.dateOfReceived,
    "equippedDate":  this.assetMasterFormGroup.value.equippedDate,
    "expiryDate":  this.assetMasterFormGroup.value.expiryDate,
    "lugDate":  this.assetMasterFormGroup.value.lugDate,
    "stripDate":  this.assetMasterFormGroup.value.stripDate,
    "warrantyAmcEndDate":  this.assetMasterFormGroup.value.warrantyAmcEndDate,      
    "createdBy": this.loggedUserData.username,   
    "createdStamp": new Date(),
    "createdTxStamp": new Date(),
    "lastUpdatedStamp": new Date(),
    "lastUpdatedTxStamp": new Date(),
      }
      this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.SAVE_ASSET_MASTER_DATA, saveAmdModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
       
     console.log("saveAmdModel"+JSON.stringify(saveAmdModel));
        if (this.resp.code == Constants.CODES.SUCCESS) {
          this.commonService.showAlertMessage("Asset Master Data Saved Successfully");
          this.router.navigate(['../'], { relativeTo: this.route });
        } else {
          this.commonService.showAlertMessage("Asset Master Data Saving Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Asset Master Data Saving Failed.");
      });
    } else if (this.update) {

      var updateAmdModel = {

    "id": this.id,
    "type" : this.assetMasterFormGroup.value.type,
    "facilityId" : this.assetMasterFormGroup.value.facilityId,
    "adeeSection" : this.assetMasterFormGroup.value.adeeSection,
    "majorSection" :this.assetMasterFormGroup.value.majorSection,
    "assetType" : this.assetMasterFormGroup.value.assetType,
    "section" : this.assetMasterFormGroup.value.section,
    "locationPosition" : this.assetMasterFormGroup.value.locationPosition,
    "kilometer" : this.assetMasterFormGroup.value.kilometer,
    "assetId" : this.assetMasterFormGroup.value.assetId,
    "part1" : this.assetMasterFormGroup.value.part1,
    "part2":  this.assetMasterFormGroup.value.part2,
    "part3":  this.assetMasterFormGroup.value.part3,
    "elementarySection":  this.assetMasterFormGroup.value.elementarySection,
    "line": this.assetMasterFormGroup.value.line,
    "parentAssetType": this.assetMasterFormGroup.value.parentAssetType,
    "parentAssetTypeId": this.assetMasterFormGroup.value.parentAssetTypeId,
    "make":  this.assetMasterFormGroup.value.make,
    "model":  this.assetMasterFormGroup.value.model,
    "structure": this.assetMasterFormGroup.value.structure,
    "warrantyAmc":  this.assetMasterFormGroup.value.warrantyAmc,
    "station": this.assetMasterFormGroup.value.station,
    "positionId":  this.assetMasterFormGroup.value.positionId,
    "capacityRating":  this.assetMasterFormGroup.value.capacityRating,
    "oemSerial":  this.assetMasterFormGroup.value.oemSerial,
    "rlyAssignedSerial":  this.assetMasterFormGroup.value.rlyAssignedSerial,
    "source":  this.assetMasterFormGroup.value.source,
    "implantation": this.assetMasterFormGroup.value.implantation,
    "vendor":  this.assetMasterFormGroup.value.vendor,
    "namePlateDetails":  this.assetMasterFormGroup.value.namePlateDetails,
    "end1Side1":  this.assetMasterFormGroup.value.end1Side1,
    "end2Side2": this.assetMasterFormGroup.value.end2Side2,
    "remark1":  this.assetMasterFormGroup.value.remark1,
    "remark2":  this.assetMasterFormGroup.value.remark2,
    "codalLife":  this.assetMasterFormGroup.value.codalLife,
    "voltage":  this.assetMasterFormGroup.value.voltage,
    "batch":  this.assetMasterFormGroup.value.batch,
    "stagger1":  this.assetMasterFormGroup.value.stagger1,
    "stagger2": this.assetMasterFormGroup.value.stagger2,
    "stagger3":  this.assetMasterFormGroup.value.stagger3,
    "stay1InsulatorBatch":  this.assetMasterFormGroup.value.stay1InsulatorBatch,
    "stay1InsulatorMake":  this.assetMasterFormGroup.value.stay1InsulatorMake,
    "bracket1InsulatorBatch":  this.assetMasterFormGroup.value.bracket1InsulatorBatch,
    "bracket1InsulatorMake":  this.assetMasterFormGroup.value.bracket1InsulatorMake,
    "stag1Ton9InsulatorBatch":  this.assetMasterFormGroup.value.stag1Ton9InsulatorBatch,
    "stag1Ton9InsulatorMake":  this.assetMasterFormGroup.value.stag1Ton9InsulatorMake,
    "rod1InsulatorBatch":  this.assetMasterFormGroup.value.rod1InsulatorBatch,
    "rod1InsulatorMake":  this.assetMasterFormGroup.value.rod1InsulatorMake,
    "pedestal1InsulatorBatch":  this.assetMasterFormGroup.value.pedestal1InsulatorBatch,
    "pedestal1InsulatorMake":  this.assetMasterFormGroup.value.pedestal1InsulatorMake,
    "core1InsulatorBatch":  this.assetMasterFormGroup.value.core1InsulatorBatch,
    "core1InsulatorMake": this.assetMasterFormGroup.value.core1InsulatorMake,
    "stay2InsulatorBatch":  this.assetMasterFormGroup.value.stay2InsulatorBatch,
    "stay2InsulatorMake": this.assetMasterFormGroup.value.stay2InsulatorMake,
    "bracket2InsulatorBatch":  this.assetMasterFormGroup.value.bracket2InsulatorBatch,
    "bracket2InsulatorMake":  this.assetMasterFormGroup.value.bracket2InsulatorMake,
    "stag2Ton9InsulatorBatch":  this.assetMasterFormGroup.value.stag2Ton9InsulatorBatch,
    "stag2Ton9InsulatorMake":  this.assetMasterFormGroup.value.stag2Ton9InsulatorMake,
    "rod2InsulatorBatch":  this.assetMasterFormGroup.value.rod2InsulatorBatch,
    "rod2InsulatorMake":  this.assetMasterFormGroup.value.rod2InsulatorMake,
    "pedestal2InsulatorBatch":  this.assetMasterFormGroup.value.pedestal2InsulatorBatch,
    "pedestal2InsulatorMake":  this.assetMasterFormGroup.value.pedestal2InsulatorMake,
    "core2InsulatorBatch":  this.assetMasterFormGroup.value.core2InsulatorBatch,
    "core2InsulatorMake":  this.assetMasterFormGroup.value.core2InsulatorMake,
    "stay3InsulatorBatch":  this.assetMasterFormGroup.value.stay3InsulatorBatch,
    "stay3InsulatorMake": this.assetMasterFormGroup.value.stay3InsulatorMake,
    "bracket3InsulatorBatch":  this.assetMasterFormGroup.value.bracket3InsulatorBatch,
    "bracket3InsulatorMake":  this.assetMasterFormGroup.value.bracket3InsulatorMake,
    "stag3Ton9InsulatorBatch":  this.assetMasterFormGroup.value.stag3Ton9InsulatorBatch,
    "stag3Ton9InsulatorMake":  this.assetMasterFormGroup.value.stag3Ton9InsulatorMake,
    "rod3InsulatorBatch":  this.assetMasterFormGroup.value.rod3InsulatorBatch,
    "rod3InsulatorMake": this.assetMasterFormGroup.value.rod3InsulatorMake,
    "pedestal3InsulatorBatch":  this.assetMasterFormGroup.value.pedestal3InsulatorBatch,
    "pedestal3InsulatorMake":  this.assetMasterFormGroup.value.pedestal3InsulatorMake,
    "core3InsulatorBatch":  this.assetMasterFormGroup.value.core3InsulatorBatch,
    "core3InsulatorMake":  this.assetMasterFormGroup.value.core3InsulatorMake,
    "stagger": this.assetMasterFormGroup.value.stagger,
    "createdOn":  this.assetMasterFormGroup.value.createdOn,
    "dateOfCommision":  this.assetMasterFormGroup.value.dateOfCommision,
    "dateOfManufacture":  this.assetMasterFormGroup.value.dateOfManufacture,
    "dateOfReceived":  this.assetMasterFormGroup.value.dateOfReceived,
    "equippedDate":  this.assetMasterFormGroup.value.equippedDate,
    "expiryDate":  this.assetMasterFormGroup.value.expiryDate,
    "lugDate":  this.assetMasterFormGroup.value.lugDate,
    "stripDate":  this.assetMasterFormGroup.value.stripDate,
    "warrantyAmcEndDate":  this.assetMasterFormGroup.value.warrantyAmcEndDate,       
    "lastUpdatedStamp": new Date(),
    "lastUpdatedTxStamp": new Date(),
    "createdBy":this.loggedUserData.username
  }
      this.sendAndRequestService.requestForPUT(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.UPDATE_ASSET_MASTER_DATA, updateAmdModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Asset Master Data Updated Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Asset Master Data Updating Failed.");
        }
        this.findFunctionalUnits();
        this.findZones();
        this.findDepoTypeList();
        this.findMakeDetails();
        this.findModelDetails();
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Asset Master Data Updating Failed.");
      })

    }
  }
  /*
  findZones() {
    // this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_ZONE_LIST).subscribe((data) => {
    //   this.zoneList = data;
    // }
    // );
    

  }
  getDivisions() {
    this.zone = this.assetMasterFormGroup.controls['zone'].value;
    this.sendAndRequestService.requestForPOST(Constants.app_urls.REPORTS.GET_DIVISION_BASED_ON_ZONE, this.zone, false).subscribe((data) => {
      this.divisionsList = data;

    });

  }


  getSubDivisions() {
    this.division = this.assetMasterFormGroup.controls['division'].value;
    this.sendAndRequestService.requestForPOST(Constants.app_urls.REPORTS.GET_SUBDIVISION_BASED_ON_DIVISION, this.division, false).subscribe((data) => {
      this.subDivisionList = data;
    });


  }*/
  findDepoTypeList() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FUNCTIONAL_LOCATION_TYPES)
      .subscribe((depoTypes) => {
        this.depoTypeList = depoTypes;
      })
  } 
  findAssetTypeList(assertType) {
    this.assetTypeList = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_ASSET_TYPES + assertType)
      .subscribe((assetTypes) => {
        this.assetTypeList = assetTypes;
      })
  } 
  findFunctionalUnits() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY_NAMES)
      .subscribe((units) => {
        this.allFunctionalUnitsList = units;
      })
  }
  findMakeDetails() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_MAKE_DETAILS)
      .subscribe((units) => {
        this.makeName = units;
      })
  }
  findModelDetails() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_MODEL_DETAILS)
      .subscribe((units) => {
        this.modelName = units;
      })
  }
  getAssetTypes() {
    var assetType = this.assetMasterFormGroup.value.assetType;
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.GET_PARAMETER_NAMES_BASED_ON_ASSET_TYPES + assetType + '/' + 'Yes').subscribe((data) => {
      this.assetTypeParametersData = data;
    });
  }
  onGoBack() {
      this.router.navigate(['../'], { relativeTo: this.route });
    
  }
 duplicateFacilityIdAssetType() {
    const q = new Promise((resolve, reject) => {
       this.sendAndRequestService.requestForGET(
              Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.EXIST_FACILITY_ASSETTYPEID +
            this.assetMasterFormGroup.controls['facilityId'].value + '/'+
            this.assetMasterFormGroup.controls['assetType'].value+'/'+
            this.assetMasterFormGroup.controls['assetId'].value
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
    duplicateFacilityIdAssetTypeAndId() {
    let id=this.id;
    let facilityId: string = this.assetMasterFormGroup.controls['facilityId'].value;
    let assetType: string = this.assetMasterFormGroup.controls['assetType'].value;
    let assetId: string = this.assetMasterFormGroup.controls['assetId'].value;
   

    const q = new Promise((resolve, reject) => {          

       this.sendAndRequestService.requestForGET(
              Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.EXIST_FACILITY_ASSETTYPEID_AND_ID +id+'/'+facilityId+'/'+assetType+'/'+assetId).subscribe
              ((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateFacilityIdAssetTypeAndId': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateFacilityIdAssetTypeAndId': true }); });
    });
    return q;
  }



findZones(){
  this.zoneList = [];
  this.divisionList = [];
  this.subDivisionList = [];
  this.depotsList = [];

  this.zoneHierarchy.zoneList;
   this.enableZone = true;
     
  if(this.zoneHierarchy.length>0)
  {
 
    this.enableZone=true;
  }
  else if(this.divisionHierarchy.length>0){
  
    this.enableZone=false;
    this.enableDivision=true;    
   
  }
  else if(this.subDivisionHierarchy.length>0){
   this.enableZone=false;
   this.enableDivision=false;    
   this.enableSubDivision=true;
  }
  else{
   this.enableZone=false;
   this.enableDivision=false;  
   this.enableSubDivision=false;
   this.enableDepot=true;
  }

 
}
findDivisions(){
let zone: string = this.assetMasterFormGroup.controls['zone'].value;
this.divisionList=[];    

for (let i = 0; i < this.divisionHierarchy.length; i++) {
  
     if(this.divisionHierarchy[i].zone == zone&& this.divisionHierarchy[i].depotType == 'DIV'){
               
         this.divisionHierarchy.divisionList;
         this.enableDivision = true;
     }
  }
}

findSubDivision(){     

let  division: string = this.assetMasterFormGroup.controls['dataDiv'].value; 
this.subDivisionList=[];  
for (let i = 0; i < this.subDivisionHierarchy.length; i++) {
    
       if(this.subDivisionHierarchy[i].division == division && this.subDivisionHierarchy[i].depotType == 'SUB_DIV'){
       
          // this.subDivisionList.push(this.subDivisionHierarchy[i]); 
           this.subDivisionHierarchy.subDivisionList; 
           
           this.enableSubDivision = true;             
       }
    }
}


findFacilities(){
  
  this.facilityList=[];
  let  subDiv: string = this.assetMasterFormGroup.controls['subDivision'].value;
    for (let i = 0; i < this.depotHierarchy.length; i++) {
      if(subDiv != null){
      if( this.depotHierarchy[i].subDivision == subDiv && this.depotHierarchy[i].depotType == this.assetMasterFormGroup.controls['type'].value)
      {
         
         this.facilityList.push(this.depotHierarchy[i]);
          //this.facilityHierarchy.facilityList;
          
      }
    }else{
      if(this.depotHierarchy[i].depotType == this.assetMasterFormGroup.controls['type'].value)
      {
         
         this.facilityList.push(this.depotHierarchy[i]);
          //this.facilityHierarchy.facilityList;
          
      }
    }
      }
      this.updateAssertType();
   }     

   updateAssertType(){  
    this.findAssetTypeList(Constants.ASSERT_TYPE[this.assetMasterFormGroup.controls['type'].value]);    
   }

}