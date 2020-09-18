import { OnInit, Component, ViewChild, ElementRef} from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { AssetMasterDataModel } from 'src/app/models/asset-master-data.model';
import { Constants } from 'src/app/common/constants';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { ReportModel } from 'src/app/models/report.model';


@Component({
    selector: 'asset-master-data',
    templateUrl: './asset-master-data.component.html',
    styleUrls: []
})

export class AssetMasterDataComponent implements OnInit{
    editPermission: boolean = true;
    addPermission: boolean = true;
    deletePermission: boolean = true;
    addAssetMaster:boolean;
    assetMasterFormGroup:FormGroup;
    assetMasterDataList: any;
    title: string = "Save";
    AssocList:any;
    makeName:any;
    modelName:any;
    zoneList:any;
    divisionsList:any;
    subDivisionList:any;
    parameterData:any;
    depoTypeList = [];
    assetTypeList = [];
    functionalUnitList: any;
    allFunctionalUnitsList: any;
    editAssetMasterResponse: any;
    responseStatus: any;
    assocErrors : any;
    funLocTypeData:any;
    saveAssetMaster:boolean;
    reportModel: ReportModel;
    value:string;
    onlyOHE:boolean = false;
    onlyPSI:boolean = false;
    pattern = "^[A-Z0-9]+$";
    assetMasterDataSource: MatTableDataSource<AssetMasterDataModel>;
    assetMasterDisplayColumns = ['sno','type','depot','assetType','assetId','adeeSection','majorSection','section','locationPosition'
    ,'kilometer','elementarySection','id'] ;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    editAssetMasterDataResponse: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    assetMasterResponse:any;
    loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
    assetsList:any;
    assetTypeParametersData:any;
    scheduleList:any;
    zone: any;
    facilityData: any;
    division: any;
    amdErrors:any;
    constructor( 
        private formBuilder: FormBuilder,
        private commonService: CommonService,
        private spinnerService: Ng4LoadingSpinnerService,
        private sendAndRequestService:SendAndRequestService,
        public dialog: MatDialog
        ){

          this.amdErrors = {
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

    ngOnInit () {
      this.reportModel = new ReportModel();
      this.getAllAssetMasterData();
      this.findFunctionalUnits();
      this.findZones();
      this.findDepoTypeList();
      this.findMakeDetails();
      this.findModelDetails();
      var permissionName = this.commonService.getPermissionNameByLoggedData("ASSET REGISTER","OHE ASSET MASTER") ;
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
      this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
      this.assetMasterFormGroup = this.formBuilder.group({
        id: 0,
        'zone':[null],
        'division':[null],
        'subDivision':[null],
        'type':[null],
        'facilityId':[null],
        'adeeSection':[null],
        'majorSection':[null],
        'assetType':[null],
        'section':[null],
        'locationPosition':[null],
        'kilometer':[null],
        'assetId':[null],
        'part1':[null],
        'part2':[null],
        'part3':[null],
        'elementarySection':[null],
        'line':[null],
        'parentAssetType':[null],
        'parentAssetTypeId':[null],
        'make':[null],
        'model':[null],
        'structure':[null],
        'warrantyAmc':[null],
        'station':[null],
        'positionId':[null],
        'capacityRating':[null],
        'oemSerial':[null],
        'rlyAssignedSerial':[null],
        'source':[null],
        'implantation':[null],
        'vendor':[null],
        'namePlateDetails':[null],
        'end1Side1':[null],
        'end2Side2':[null],
        'remark1':[null],
        'remark2':[null],
        'codalLife':[null],
        'voltage':[null],
        'batch':[null],
        'stagger1':[null],
        'stagger2':[null],
        'stagger3':[null],
        'stay1InsulatorBatch':[null],
        'stay1InsulatorMake':[null],
        'bracket1InsulatorBatch':[null],
        'bracket1InsulatorMake':[null],
        'stag1Ton9InsulatorBatch':[null],
        'stag1Ton9InsulatorMake':[null],
        'rod1InsulatorBatch':[null],
        'rod1InsulatorMake':[null],
        'pedestal1InsulatorBatch':[null],
        'pedestal1InsulatorMake':[null],
        'core1InsulatorBatch':[null],
        'core1InsulatorMake':[null],
        'stay2InsulatorBatch':[null],
        'stay2InsulatorMake':[null],
        'bracket2InsulatorBatch':[null],
        'bracket2InsulatorMake':[null],
        'stag2Ton9InsulatorBatch':[null],
        'stag2Ton9InsulatorMake':[null],
        'rod2InsulatorBatch':[null],
        'rod2InsulatorMake':[null],
        'pedestal2InsulatorBatch':[null],
        'pedestal2InsulatorMake':[null],
        'core2InsulatorBatch':[null],
        'core2InsulatorMake':[null],
        'stay3InsulatorBatch':[null],
        'stay3InsulatorMake':[null],
        'bracket3InsulatorBatch':[null],
        'bracket3InsulatorMake':[null],
        'stag3Ton9InsulatorBatch':[null],
        'stag3Ton9InsulatorMake':[null],
        'rod3InsulatorBatch':[null],
        'rod3InsulatorMake':[null],
        'pedestal3InsulatorBatch':[null],
        'pedestal3InsulatorMake':[null],
        'core3InsulatorBatch':[null],
        'core3InsulatorMake':[null],
        'stagger':[null],  
        'createdOn':[null],
        'dateOfCommision':[null],
        'dateOfManufacture':[null],
        'dateOfReceived':[null],
        'equippedDate':[null],
        'expiryDate':[null],
        'lugDate':[null],
        'stripDate':[null],
        'warrantyAmcEndDate':[null],


    });      
    }
    onFormValuesChanged() {
      for (const field in this.amdErrors) {
        if (!this.amdErrors.hasOwnProperty(field)) {
          continue;
        }
        // Clear previous errors
        this.amdErrors[field] = {};
  
        // Get the control
        const control = this.assetMasterFormGroup.get(field);
        if (control && control.dirty && !control.valid) {
          this.amdErrors[field] = control.errors;
        }
      }
    }
    public get f() { return this.assetMasterFormGroup.controls; } 
    getAllAssetMasterData() {
        const assetMasterData: AssetMasterDataModel[] = [];
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.GET_ASSET_MASTER_DATA).subscribe((data) => {
          this.assetMasterDataList = data;
          for (let i = 0; i < this.assetMasterDataList.length; i++) {
            this.assetMasterDataList[i].sno = i + 1;
            this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY+JSON.stringify(this.assetMasterDataList[i].facilityId)).subscribe((data) => {
              this.spinnerService.hide();
              this.facilityData = data;
              this.assetMasterDataList[i].facilityId = this.facilityData.facilityName;
          }, error => {
	      		this.spinnerService.hide();
	    	});
        assetMasterData.push(this.assetMasterDataList[i]);
      }
          this.assetMasterDataSource = new MatTableDataSource(assetMasterData);
          this.assetMasterDataSource.paginator = this.paginator;
          this.assetMasterDataSource.sort = this.sort;
    
        }, error => { });
    
      }
    
    assetMasterDataSubmit() {
        let type: string = this.assetMasterFormGroup.value.type;
        let facilityId: string = this.assetMasterFormGroup.value.facilityId;
        let adeeSection: string = this.assetMasterFormGroup.value.adeeSection;
        let majorSection: string = this.assetMasterFormGroup.value.majorSection;
        let assetType: string = this.assetMasterFormGroup.value.assetType;
        let section: string = this.assetMasterFormGroup.value.section;
        let locationPosition: string = this.assetMasterFormGroup.value.locationPosition;
        let kilometer: string = this.assetMasterFormGroup.value.kilometer;
        let assetId: string = this.assetMasterFormGroup.value.assetId;
        let part1: string = this.assetMasterFormGroup.value.part1;
        let part2: string = this.assetMasterFormGroup.value.part2;
        let part3: string = this.assetMasterFormGroup.value.part3;
        let elementarySection: string = this.assetMasterFormGroup.value.elementarySection;
        let line: string = this.assetMasterFormGroup.value.line;
        let parentAssetType: string = this.assetMasterFormGroup.value.parentAssetType;
        let parentAssetTypeId: string = this.assetMasterFormGroup.value.parentAssetTypeId;
        let make: string = this.assetMasterFormGroup.value.make;
        let model: string = this.assetMasterFormGroup.value.model;
        let structure: string = this.assetMasterFormGroup.value.structure;
        let warrantyAmc: string = this.assetMasterFormGroup.value.warrantyAmc;
        let station: string = this.assetMasterFormGroup.value.station;
        let positionId: string = this.assetMasterFormGroup.value.positionId;
        let capacityRating: string = this.assetMasterFormGroup.value.capacityRating;
        let oemSerial: string = this.assetMasterFormGroup.value.oemSerial;
        let rlyAssignedSerial: string = this.assetMasterFormGroup.value.rlyAssignedSerial;
        let source: string = this.assetMasterFormGroup.value.source;
        let implantation: string = this.assetMasterFormGroup.value.implantation;
        let vendor: string = this.assetMasterFormGroup.value.vendor;
        let namePlateDetails: string = this.assetMasterFormGroup.value.namePlateDetails;
        let end1Side1: string = this.assetMasterFormGroup.value.end1Side1;
        let end2Side2: string = this.assetMasterFormGroup.value.end2Side2;
        let remark1: string = this.assetMasterFormGroup.value.remark1;
        let remark2: string = this.assetMasterFormGroup.value.remark2;
        let codalLife: string = this.assetMasterFormGroup.value.codalLife;
        let voltage: string = this.assetMasterFormGroup.value.voltage;
        let batch: string = this.assetMasterFormGroup.value.batch;
        let stagger1: string = this.assetMasterFormGroup.value.stagger1;
        let stagger2: string = this.assetMasterFormGroup.value.stagger2;
        let stagger3: string = this.assetMasterFormGroup.value.stagger3;
        let stay1InsulatorBatch: string = this.assetMasterFormGroup.value.stay1InsulatorBatch;
        let stay1InsulatorMake: string = this.assetMasterFormGroup.value.stay1InsulatorMake;
        let bracket1InsulatorBatch: string = this.assetMasterFormGroup.value.bracket1InsulatorBatch;
        let bracket1InsulatorMake: string = this.assetMasterFormGroup.value.bracket1InsulatorMake;
        let stag1Ton9InsulatorBatch: string = this.assetMasterFormGroup.value.stag1Ton9InsulatorBatch;
        let stag1Ton9InsulatorMake: string = this.assetMasterFormGroup.value.stag1Ton9InsulatorMake;
        let rod1InsulatorBatch: string = this.assetMasterFormGroup.value.rod1InsulatorBatch;
        let rod1InsulatorMake: string = this.assetMasterFormGroup.value.rod1InsulatorMake;
        let pedestal1InsulatorBatch: string = this.assetMasterFormGroup.value.pedestal1InsulatorBatch;
        let pedestal1InsulatorMake: string = this.assetMasterFormGroup.value.pedestal1InsulatorMake;
        let core1InsulatorBatch: string = this.assetMasterFormGroup.value.core1InsulatorBatch;
        let core1InsulatorMake: string = this.assetMasterFormGroup.value.core1InsulatorMake;
        let stay2InsulatorBatch: string = this.assetMasterFormGroup.value.stay2InsulatorBatch;
        let stay2InsulatorMake: string = this.assetMasterFormGroup.value.stay2InsulatorMake;
        let bracket2InsulatorBatch: string = this.assetMasterFormGroup.value.bracket2InsulatorBatch;
        let bracket2InsulatorMake: string = this.assetMasterFormGroup.value.bracket2InsulatorMake;
        let stag2Ton9InsulatorBatch: string = this.assetMasterFormGroup.value.stag2Ton9InsulatorBatch;
        let stag2Ton9InsulatorMake: string = this.assetMasterFormGroup.value.stag2Ton9InsulatorMake;
        let rod2InsulatorBatch: string = this.assetMasterFormGroup.value.rod2InsulatorBatch;
        let rod2InsulatorMake: string = this.assetMasterFormGroup.value.rod2InsulatorMake;
        let pedestal2InsulatorBatch: string = this.assetMasterFormGroup.value.pedestal2InsulatorBatch;
        let pedestal2InsulatorMake: string = this.assetMasterFormGroup.value.pedestal2InsulatorMake;
        let core2InsulatorBatch: string = this.assetMasterFormGroup.value.core2InsulatorBatch;
        let core2InsulatorMake: string = this.assetMasterFormGroup.value.core2InsulatorMake;
        let stay3InsulatorBatch: string = this.assetMasterFormGroup.value.stay3InsulatorBatch;
        let stay3InsulatorMake: string = this.assetMasterFormGroup.value.stay3InsulatorMake;
        let bracket3InsulatorBatch: string = this.assetMasterFormGroup.value.bracket3InsulatorBatch;
        let bracket3InsulatorMake: string = this.assetMasterFormGroup.value.bracket3InsulatorMake;
        let stag3Ton9InsulatorBatch: string = this.assetMasterFormGroup.value.stag3Ton9InsulatorBatch;
        let stag3Ton9InsulatorMake: string = this.assetMasterFormGroup.value.stag3Ton9InsulatorMake;
        let rod3InsulatorBatch: string = this.assetMasterFormGroup.value.rod3InsulatorBatch;
        let rod3InsulatorMake: string = this.assetMasterFormGroup.value.rod3InsulatorMake;
        let pedestal3InsulatorBatch: string = this.assetMasterFormGroup.value.pedestal3InsulatorBatch;
        let pedestal3InsulatorMake: string = this.assetMasterFormGroup.value.pedestal3InsulatorMake;
        let core3InsulatorBatch: string = this.assetMasterFormGroup.value.core3InsulatorBatch;
        let core3InsulatorMake: string = this.assetMasterFormGroup.value.core3InsulatorMake;
        let stagger: string = this.assetMasterFormGroup.value.stagger;
        let createdOn: Date = this.assetMasterFormGroup.value.createdOn;
        let dateOfCommision: Date = this.assetMasterFormGroup.value.dateOfCommision;
        let dateOfManufacture: Date = this.assetMasterFormGroup.value.dateOfManufacture;
        let dateOfReceived: Date = this.assetMasterFormGroup.value.dateOfReceived;
        let equippedDate: Date = this.assetMasterFormGroup.value.equippedDate;
        let expiryDate: Date = this.assetMasterFormGroup.value.expiryDate;
        let lugDate: Date = this.assetMasterFormGroup.value.lugDate;
        let stripDate: Date = this.assetMasterFormGroup.value.stripDate;
        let warrantyAmcEndDate: Date = this.assetMasterFormGroup.value.warrantyAmcEndDate;

        this.addAssetMaster = false;
        if (this.title == Constants.EVENTS.SAVE) {
          var saveAmdModel = {
            'type': type,
            'facilityId': facilityId,
            'adeeSection': adeeSection,
            'majorSection': majorSection,
            'assetType': assetType,
            'section': section,
            'locationPosition': locationPosition,
            'kilometer': kilometer,
            'assetId': assetId,
            'part1': part1,
            'part2': part2,
            'part3': part3,
            'elementarySection': elementarySection,
            'line': line,
            'parentAssetType': parentAssetType,
            'parentAssetTypeId': parentAssetTypeId,
            'make': make,
            'model': model,
            'structure': structure,
            'warrantyAmc': warrantyAmc,
            'station': station,
            'positionId': positionId,
            'capacityRating': capacityRating,
            'oemSerial': oemSerial,
            'rlyAssignedSerial': rlyAssignedSerial,
            'source': source,
            'implantation': implantation,
            'vendor': vendor,
            'namePlateDetails': namePlateDetails,
            'end1Side1': end1Side1,
            'end2Side2': end2Side2,
            'remark1': remark1,
            'remark2': remark2,
            'codalLife': codalLife,
            'voltage': voltage,
            'batch': batch,
            'stagger1': stagger1,
            'stagger2': stagger2,
            'stagger3': stagger3,
            'stay1InsulatorBatch': stay1InsulatorBatch,
            'stay1InsulatorMake': stay1InsulatorMake,
            'bracket1InsulatorBatch': bracket1InsulatorBatch,
            'bracket1InsulatorMake': bracket1InsulatorMake,
            'stag1Ton9InsulatorBatch': stag1Ton9InsulatorBatch,
            'stag1Ton9InsulatorMake': stag1Ton9InsulatorMake,
            'rod1InsulatorBatch': rod1InsulatorBatch,
            'rod1InsulatorMake': rod1InsulatorMake,
            'pedestal1InsulatorBatch': pedestal1InsulatorBatch,
            'pedestal1InsulatorMake': pedestal1InsulatorMake,
            'core1InsulatorBatch': core1InsulatorBatch,
            'core1InsulatorMake': core1InsulatorMake,
            'stay2InsulatorBatch': stay2InsulatorBatch,
            'stay2InsulatorMake': stay2InsulatorMake,
            'bracket2InsulatorBatch': bracket2InsulatorBatch,
            'bracket2InsulatorMake': bracket2InsulatorMake,
            'stag2Ton9InsulatorBatch': stag2Ton9InsulatorBatch,
            'stag2Ton9InsulatorMake': stag2Ton9InsulatorMake,
            'rod2InsulatorBatch': rod2InsulatorBatch,
            'rod2InsulatorMake': rod2InsulatorMake,
            'pedestal2InsulatorBatch': pedestal2InsulatorBatch,
            'pedestal2InsulatorMake': pedestal2InsulatorMake,
            'core2InsulatorBatch': core2InsulatorBatch,
            'core2InsulatorMake': core2InsulatorMake,
            'stay3InsulatorBatch': stay3InsulatorBatch,
            'stay3InsulatorMake': stay3InsulatorMake,
            'bracket3InsulatorBatch': bracket3InsulatorBatch,
            'bracket3InsulatorMake': bracket3InsulatorMake,
            'stag3Ton9InsulatorBatch': stag3Ton9InsulatorBatch,
            'stag3Ton9InsulatorMake': stag3Ton9InsulatorMake,
            'rod3InsulatorBatch': rod3InsulatorBatch,
            'rod3InsulatorMake': rod3InsulatorMake,
            'pedestal3InsulatorBatch': pedestal3InsulatorBatch,
            'pedestal3InsulatorMake': pedestal3InsulatorMake,
            'core3InsulatorBatch': core3InsulatorBatch,
            'core3InsulatorMake': core3InsulatorMake,
            'stagger': stagger,
            'createdOn':createdOn,
            'dateOfCommision':dateOfCommision,
            'dateOfManufacture':dateOfManufacture,
            'dateOfReceived':dateOfReceived,
            'equippedDate':equippedDate,
            'expiryDate':expiryDate,
            'lugDate':lugDate,
            'stripDate':stripDate,
            'warrantyAmcEndDate':warrantyAmcEndDate,
            "createdBy": this.loggedUserData.username,
            "createdStamp": new Date(),
            "createdTxStamp": new Date(),
            "lastUpdatedStamp": new Date(),
            "lastUpdatedTxStamp": new Date(),
          }
          this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.SAVE_ASSET_MASTER_DATA,saveAmdModel, false).subscribe(data => {
            this.assetMasterResponse = data;
            if(this.assetMasterResponse.code == 200 && !!this.assetMasterResponse) {
              this.commonService.showAlertMessage(this.assetMasterResponse.message);
              this.getAllAssetMasterData();
              this.assetMasterFormGroup.reset();
          }else {
              this.commonService.showAlertMessage("Asset Master Data Saving Failed.");
          }
          this.spinnerService.hide();
      } , error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Asset Master Data Saving Failed.");
      });
  } else if (this.title == Constants.EVENTS.UPDATE) {
          let id: number = this.editAssetMasterResponse.id;
          var updateAmdModel = {
            'id':id,
            'type': type,
            'facilityId': facilityId,
            'adeeSection': adeeSection,
            'majorSection': majorSection,
            'assetType': assetType,
            'section': section,
            'locationPosition': locationPosition,
            'kilometer': kilometer,
            'assetId': assetId,
            'part1': part1,
            'part2': part2,
            'part3': part3,
            'elementarySection': elementarySection,
            'line': line,
            'parentAssetType': parentAssetType,
            'parentAssetTypeId': parentAssetTypeId,
            'make': make,
            'model': model,
            'structure': structure,
            'warrantyAmc': warrantyAmc,
            'station': station,
            'positionId': positionId,
            'capacityRating': capacityRating,
            'oemSerial': oemSerial,
            'rlyAssignedSerial': rlyAssignedSerial,
            'source': source,
            'implantation': implantation,
            'vendor': vendor,
            'namePlateDetails': namePlateDetails,
            'end1Side1': end1Side1,
            'end2Side2': end2Side2,
            'remark1': remark1,
            'remark2': remark2,
            'codalLife': codalLife,
            'voltage': voltage,
            'batch': batch,
            'stagger1': stagger1,
            'stagger2': stagger2,
            'stagger3': stagger3,
            'stay1InsulatorBatch': stay1InsulatorBatch,
            'stay1InsulatorMake': stay1InsulatorMake,
            'bracket1InsulatorBatch': bracket1InsulatorBatch,
            'bracket1InsulatorMake': bracket1InsulatorMake,
            'stag1Ton9InsulatorBatch': stag1Ton9InsulatorBatch,
            'stag1Ton9InsulatorMake': stag1Ton9InsulatorMake,
            'rod1InsulatorBatch': rod1InsulatorBatch,
            'rod1InsulatorMake': rod1InsulatorMake,
            'pedestal1InsulatorBatch': pedestal1InsulatorBatch,
            'pedestal1InsulatorMake': pedestal1InsulatorMake,
            'core1InsulatorBatch': core1InsulatorBatch,
            'core1InsulatorMake': core1InsulatorMake,
            'stay2InsulatorBatch': stay2InsulatorBatch,
            'stay2InsulatorMake': stay2InsulatorMake,
            'bracket2InsulatorBatch': bracket2InsulatorBatch,
            'bracket2InsulatorMake': bracket2InsulatorMake,
            'stag2Ton9InsulatorBatch': stag2Ton9InsulatorBatch,
            'stag2Ton9InsulatorMake': stag2Ton9InsulatorMake,
            'rod2InsulatorBatch': rod2InsulatorBatch,
            'rod2InsulatorMake': rod2InsulatorMake,
            'pedestal2InsulatorBatch': pedestal2InsulatorBatch,
            'pedestal2InsulatorMake': pedestal2InsulatorMake,
            'core2InsulatorBatch': core2InsulatorBatch,
            'core2InsulatorMake': core2InsulatorMake,
            'stay3InsulatorBatch': stay3InsulatorBatch,
            'stay3InsulatorMake': stay3InsulatorMake,
            'bracket3InsulatorBatch': bracket3InsulatorBatch,
            'bracket3InsulatorMake': bracket3InsulatorMake,
            'stag3Ton9InsulatorBatch': stag3Ton9InsulatorBatch,
            'stag3Ton9InsulatorMake': stag3Ton9InsulatorMake,
            'rod3InsulatorBatch': rod3InsulatorBatch,
            'rod3InsulatorMake': rod3InsulatorMake,
            'pedestal3InsulatorBatch': pedestal3InsulatorBatch,
            'pedestal3InsulatorMake': pedestal3InsulatorMake,
            'core3InsulatorBatch': core3InsulatorBatch,
            'core3InsulatorMake': core3InsulatorMake,
            'stagger': stagger,
            'createdOn':createdOn,
            'dateOfCommision':dateOfCommision,
            'dateOfManufacture':dateOfManufacture,
            'dateOfReceived':dateOfReceived,
            'equippedDate':equippedDate,
            'expiryDate':expiryDate,
            'lugDate':lugDate,
            'stripDate':stripDate,
            'warrantyAmcEndDate':warrantyAmcEndDate,
            "lastUpdatedStamp": new Date(),
            "lastUpdatedTxStamp": new Date(),
          }
          this.sendAndRequestService.requestForPUT(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.UPDATE_ASSET_MASTER_DATA, updateAmdModel, false).subscribe(data => {
            this.assetMasterResponse = data;
            if(this.assetMasterResponse.code == 200 && !!this.assetMasterResponse) {
                this.commonService.showAlertMessage(this.assetMasterResponse.message);
                this.getAllAssetMasterData();
                this.assetMasterFormGroup.reset();
                this.addAssetMaster =  false;
            }else {
                this.commonService.showAlertMessage("Asset Master Data Updating Failed.");
            }
        } , error => {
            console.log('ERROR >>>');
            this.spinnerService.hide();
            this.commonService.showAlertMessage("Asset Master  Data Updating Failed.");
        });
        
    }
}
      editAssetNasterItem (id) {
        this.addAssetMaster = true;
        this.assetMasterItemEditAction(id);
        this.title = 'Update';
    }

    assetMasterItemEditAction(id: number) {
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.GET_ASSET_MASTER_DATA_ID+id).subscribe((responseData) => {
            this.editAssetMasterResponse = responseData;
            this.assetMasterFormGroup.patchValue({
                id: this.editAssetMasterResponse.id,
                type:this.editAssetMasterResponse.type,
                facilityId: this.editAssetMasterResponse.facilityId,
                adeeSection: this.editAssetMasterResponse.adeeSection,
                majorSection: this.editAssetMasterResponse.majorSection,
                assetType: this.editAssetMasterResponse.assetType,
                section: this.editAssetMasterResponse.section,
                locationPosition: this.editAssetMasterResponse.locationPosition,
                kilometer: this.editAssetMasterResponse.kilometer,
                assetId: this.editAssetMasterResponse.assetId,
                part1: this.editAssetMasterResponse.part1,
                part2: this.editAssetMasterResponse.part2,
                part3: this.editAssetMasterResponse.part3,
                elementarySection: this.editAssetMasterResponse.elementarySection,
                line: this.editAssetMasterResponse.line,
                parentAssetType:this.editAssetMasterResponse.parentAssetType,
                parentAssetTypeId:this.editAssetMasterResponse.parentAssetTypeId,               
                make:this.editAssetMasterResponse.make,
                model:this.editAssetMasterResponse.model,
                structure:this.editAssetMasterResponse.structure,
                warrantyAmc:this.editAssetMasterResponse.warrantyAmc,
                station:this.editAssetMasterResponse.station,
                positionId:this.editAssetMasterResponse.positionId,
                capacityRating:this.editAssetMasterResponse.capacityRating,
                oemSerial:this.editAssetMasterResponse.oemSerial,
                rlyAssignedSerial:this.editAssetMasterResponse.rlyAssignedSerial,
                source:this.editAssetMasterResponse.source,
                implantation:this.editAssetMasterResponse.implantation,
                vendor:this.editAssetMasterResponse.vendor,
                namePlateDetails:this.editAssetMasterResponse.namePlateDetails,
                end1Side1:this.editAssetMasterResponse.end1Side1,
                end2Side2:this.editAssetMasterResponse.end2Side2,
                remark1:this.editAssetMasterResponse.remark1,
                remark2:this.editAssetMasterResponse.remark2,
                codalLife:this.editAssetMasterResponse.codalLife,
                voltage:this.editAssetMasterResponse.voltage,
                batch:this.editAssetMasterResponse.batch,
                stagger1:this.editAssetMasterResponse.stagger1,
                stagger2:this.editAssetMasterResponse.stagger2,
                stagger3:this.editAssetMasterResponse.stagger3,
                stay1InsulatorBatch:this.editAssetMasterResponse.stay1InsulatorBatch,
                stay1InsulatorMake:this.editAssetMasterResponse.stay1InsulatorMake,
                bracket1InsulatorBatch:this.editAssetMasterResponse.bracket1InsulatorBatch,
                bracket1InsulatorMake:this.editAssetMasterResponse.bracket1InsulatorMake,
                stag1Ton9InsulatorBatch:this.editAssetMasterResponse.stag1Ton9InsulatorBatch,
                stag1Ton9InsulatorMake:this.editAssetMasterResponse.stag1Ton9InsulatorMake,
                rod1InsulatorBatch:this.editAssetMasterResponse.rod1InsulatorBatch,
                rod1InsulatorMake:this.editAssetMasterResponse.rod1InsulatorMake,
                pedestal1InsulatorBatch:this.editAssetMasterResponse.pedestal1InsulatorBatch,
                pedestal1InsulatorMake:this.editAssetMasterResponse.pedestal1InsulatorMake,
                core1InsulatorBatch:this.editAssetMasterResponse.core1InsulatorBatch,
                core1InsulatorMake:this.editAssetMasterResponse.core1InsulatorMake,
                stay2InsulatorBatch:this.editAssetMasterResponse.stay2InsulatorBatch,
                stay2InsulatorMake:this.editAssetMasterResponse.stay2InsulatorMake,
                bracket2InsulatorBatch:this.editAssetMasterResponse.bracket2InsulatorBatch,
                bracket2InsulatorMake:this.editAssetMasterResponse.bracket2InsulatorMake,
                stag2Ton9InsulatorBatch:this.editAssetMasterResponse.stag2Ton9InsulatorBatch,
                stag2Ton9InsulatorMake:this.editAssetMasterResponse.stag2Ton9InsulatorMake,
                rod2InsulatorBatch:this.editAssetMasterResponse.rod2InsulatorBatch,
                rod2InsulatorMake:this.editAssetMasterResponse.rod2InsulatorMake,
                pedestal2InsulatorBatch:this.editAssetMasterResponse.pedestal2InsulatorBatch,
                pedestal2InsulatorMake:this.editAssetMasterResponse.pedestal2InsulatorMake,
                core2InsulatorBatch:this.editAssetMasterResponse.core2InsulatorBatch,
                core2InsulatorMake:this.editAssetMasterResponse.core2InsulatorMake,
                stay3InsulatorBatch:this.editAssetMasterResponse.stay3InsulatorBatch,
                stay3InsulatorMake:this.editAssetMasterResponse.stay3InsulatorMake,
                bracket3InsulatorBatch:this.editAssetMasterResponse.bracket3InsulatorBatch,
                bracket3InsulatorMake:this.editAssetMasterResponse.bracket3InsulatorMake,
                stag3Ton9InsulatorBatch:this.editAssetMasterResponse.stag3Ton9InsulatorBatch,
                stag3Ton9InsulatorMake:this.editAssetMasterResponse.stag3Ton9InsulatorMake,
                rod3InsulatorBatch:this.editAssetMasterResponse.rod3InsulatorBatch,
                rod3InsulatorMake:this.editAssetMasterResponse.rod3InsulatorMake,
                pedestal3InsulatorBatch:this.editAssetMasterResponse.pedestal3InsulatorBatch,
                pedestal3InsulatorMake:this.editAssetMasterResponse.pedestal3InsulatorMake,
                core3InsulatorBatch:this.editAssetMasterResponse.core3InsulatorBatch,
                core3InsulatorMake:this.editAssetMasterResponse.core3InsulatorMake,
                stagger:this.editAssetMasterResponse.stagger,
                createdOn:new Date(this.editAssetMasterResponse.createdOn), 
                dateOfCommision:new Date(this.editAssetMasterResponse.dateOfCommision), 
                dateOfManufacture:new Date(this.editAssetMasterResponse.dateOfManufacture), 
                dateOfReceived:new Date(this.editAssetMasterResponse.dateOfReceived), 
                equippedDate:new Date(this.editAssetMasterResponse.equippedDate), 
                expiryDate:new Date(this.editAssetMasterResponse.expiryDate),
                lugDate:new Date(this.editAssetMasterResponse.lugDate), 
                stripDate:new Date(this.editAssetMasterResponse.stripDate), 
                warrantyAmcEndDate:new Date(this.editAssetMasterResponse.warrantyAmcEndDate), 
            });
            this.onFormValuesChanged();
        } ,error => {})
    }


    deleteAssetMasterDataItem (id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected Asset Master  item?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
                this.sendAndRequestService.requestForDELETE(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.DELETE_ASSET_MASTER_DATA, id)
                    .subscribe((data) => {
                        this.commonService.showAlertMessage('Asset Master Data Deleted Successfully');
                        this.getAllAssetMasterData();
                    },error => {});
            }
        });
    }
        
    
    applyFilter(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase();
        this.assetMasterDataSource.filter = filterValue;
    }
    
    
    onGoBack() {
        this.assetMasterFormGroup.reset();
        this.addAssetMaster= false;
        this.title = 'Save';
    }


    NewAssetMaster(){
        this.addAssetMaster = true;      
    }
    statusChange() {
      if (this.assetMasterFormGroup.value.type == 'OHE') {     
          this.onlyOHE = true;
          return;
      } else {
          this.onlyOHE = false;
          return true;
      }
          }
          psiChange() {
            if (this.assetMasterFormGroup.value.type == 'PSI') {     
                this.onlyPSI = true;
                return;
            } else {
                this.onlyPSI = false;
                return true;
            }
                }      
          
                findZones()
                {
                    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_ZONE_LIST).subscribe((data) => {
                        this.zoneList = data;
               }
                      ); 
            
                }
                getDivisions(){
                    this.zone =this.assetMasterFormGroup.controls['zone'].value; 
                  this.sendAndRequestService.requestForPOST(Constants.app_urls.REPORTS.GET_DIVISION_BASED_ON_ZONE, this.zone, false).subscribe((data) => {
                    this.divisionsList = data;
                             
                            });
                          
                }
                
            
                getSubDivisions(){
                   
                  this.division=this.assetMasterFormGroup.controls['division'].value; 
                    this.sendAndRequestService.requestForPOST(Constants.app_urls.REPORTS.GET_SUBDIVISION_BASED_ON_DIVISION ,this.division, false).subscribe((data) => {
                        this.subDivisionList = data;
                         });
                    
            
                }
    findDepoTypeList() {
      this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FUNCTIONAL_LOCATION_TYPES)
        .subscribe((depoTypes) => {
          this.depoTypeList = depoTypes;
        })
    }
    findAssetTypeList(assertType) {
      this.assetTypeList = [];
      this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_ASSET_TYPES+assertType)
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
    updateAssertType($event) {
      if ($event.value) {
        this.findAssetTypeList(Constants.ASSERT_TYPE[$event.value]);
        this.functionalUnitList = [];
        this.functionalUnitList = this.allFunctionalUnitsList.filter(element => {
          return element.depotType == $event.value;
        });
      }
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
        getAssetTypes(){
          var assetType = this.assetMasterFormGroup.value.assetType ;
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.GET_PARAMETER_NAMES_BASED_ON_ASSET_TYPES+assetType).subscribe((data) => {
                   this.assetTypeParametersData = data;
              });
      }
}

    
    
    

   


   

      
     



    
    
    

   