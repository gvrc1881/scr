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
    id: number = 0;
    title: string = "Save";
    AssocList:any;
    zoneData:any;
    makeName:any;
    modelName:any;
    divisionsData:any;
    subDivisionData:any;
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
    onlyOHE:boolean = true;
    onlyPSI:boolean = true;
    pattern = "^[A-Z0-9]+$";
    divisionsList:any;
    assetMasterDataSource: MatTableDataSource<AssetMasterDataModel>;
    //assetSchAssocDisplayColumns = ['sno','assetType','scheduleCode','isDpr','targetPlanMonths',
   // 'sequenceCode','duration','uomOfDuration','description','asaSeqId','id'] ;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    editAssetMasterDataResponse: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    assetMasterResponse:any;
    loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
    assetsList:any;
    scheduleList:any;

    constructor( 
        private formBuilder: FormBuilder,
        private commonService: CommonService,
        private spinnerService: Ng4LoadingSpinnerService,
        private sendAndRequestService:SendAndRequestService,
        public dialog: MatDialog
        ){
                this.assocErrors = {
                    assetType: {},
            
                };
            
    }

    ngOnInit () {
      this.reportModel = new ReportModel();
      this.getAllAssetMasterData();
      this.findFunctionalUnits();
      this.zoneList();
      this.findDepoTypeList();
      this.findMakeDetails();
      this.findModelDetails();
      var permissionName = this.commonService.getPermissionNameByLoggedData("ASSET REGISTER","OHE ASSET MASTER") ;
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
      this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
    }
   
    public get f() { return this.assetMasterFormGroup.controls; } 
    getAllAssetMasterData() {
        const assetMasterData: AssetMasterDataModel[] = [];
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.GET_ASSET_MASTER_DATA).subscribe((data) => {
          this.assetMasterDataList = data;
          for (let i = 0; i < this.assetMasterDataList.length; i++) {
            this.assetMasterDataList[i].sno = i + 1;
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
        let bracket1InsulatorBatch: string = this.assetMasterFormGroup.value.bracket1InsulatorBatch;
        let stag1Ton9InsulatorBatch: string = this.assetMasterFormGroup.value.stag1Ton9InsulatorBatch;
        let rod1InsulatorBatch: string = this.assetMasterFormGroup.value.rod1InsulatorBatch;
        let pedestal1InsulatorBatch: string = this.assetMasterFormGroup.value.pedestal1InsulatorBatch;
        let core1InsulatorBatch: string = this.assetMasterFormGroup.value.core1InsulatorBatch;
        let stay2InsulatorBatch: string = this.assetMasterFormGroup.value.stay2InsulatorBatch;
        let bracket2InsulatorBatch: string = this.assetMasterFormGroup.value.bracket2InsulatorBatch;
        let stag2Ton9InsulatorBatch: string = this.assetMasterFormGroup.value.stag2Ton9InsulatorBatch;
        let rod2InsulatorBatch: string = this.assetMasterFormGroup.value.rod2InsulatorBatch;
        let pedestal2InsulatorBatch: string = this.assetMasterFormGroup.value.pedestal2InsulatorBatch;
        let core2InsulatorBatch: string = this.assetMasterFormGroup.value.core2InsulatorBatch;
        let stay3InsulatorBatch: string = this.assetMasterFormGroup.value.stay3InsulatorBatch;
        let bracket3InsulatorBatch: string = this.assetMasterFormGroup.value.bracket3InsulatorBatch;
        let stag3Ton9InsulatorBatch: string = this.assetMasterFormGroup.value.stag3Ton9InsulatorBatch;
        let rod3InsulatorBatch: string = this.assetMasterFormGroup.value.rod3InsulatorBatch;
        let pedestal3InsulatorBatch: string = this.assetMasterFormGroup.value.pedestal3InsulatorBatch;
        let core3InsulatorBatch: string = this.assetMasterFormGroup.value.core3InsulatorBatch;
        let stagger: string = this.assetMasterFormGroup.value.stagger;

    
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
            'bracket1InsulatorBatch': bracket1InsulatorBatch,
            'stag1Ton9InsulatorBatch': stag1Ton9InsulatorBatch,
            'rod1InsulatorBatch': rod1InsulatorBatch,
            'pedestal1InsulatorBatch': pedestal1InsulatorBatch,
            'core1InsulatorBatch': core1InsulatorBatch,
            'stay2InsulatorBatch': stay2InsulatorBatch,
            'bracket2InsulatorBatch': bracket2InsulatorBatch,
            'stag2Ton9InsulatorBatch': stag2Ton9InsulatorBatch,
            'rod2InsulatorBatch': rod2InsulatorBatch,
            'pedestal2InsulatorBatch': pedestal2InsulatorBatch,
            'core2InsulatorBatch': core2InsulatorBatch,
            'stay3InsulatorBatch': stay3InsulatorBatch,
            'bracket3InsulatorBatch': bracket3InsulatorBatch,
            'stag3Ton9InsulatorBatch': stag3Ton9InsulatorBatch,
            'rod3InsulatorBatch': rod3InsulatorBatch,
            'pedestal3InsulatorBatch': pedestal3InsulatorBatch,
            'core3InsulatorBatch': core3InsulatorBatch,
            'stagger': stagger,
           
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
          let id: number = this.editAssetMasterDataResponse.id;
          var updateAmdModel = {
            'id': id,
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
            'bracket1InsulatorBatch': bracket1InsulatorBatch,
            'stag1Ton9InsulatorBatch': stag1Ton9InsulatorBatch,
            'rod1InsulatorBatch': rod1InsulatorBatch,
            'pedestal1InsulatorBatch': pedestal1InsulatorBatch,
            'core1InsulatorBatch': core1InsulatorBatch,
            'stay2InsulatorBatch': stay2InsulatorBatch,
            'bracket2InsulatorBatch': bracket2InsulatorBatch,
            'stag2Ton9InsulatorBatch': stag2Ton9InsulatorBatch,
            'rod2InsulatorBatch': rod2InsulatorBatch,
            'pedestal2InsulatorBatch': pedestal2InsulatorBatch,
            'core2InsulatorBatch': core2InsulatorBatch,
            'stay3InsulatorBatch': stay3InsulatorBatch,
            'bracket3InsulatorBatch': bracket3InsulatorBatch,
            'stag3Ton9InsulatorBatch': stag3Ton9InsulatorBatch,
            'rod3InsulatorBatch': rod3InsulatorBatch,
            'pedestal3InsulatorBatch': pedestal3InsulatorBatch,
            'core3InsulatorBatch': core3InsulatorBatch,
            'stagger': stagger,
          }
          this.sendAndRequestService.requestForPUT(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.UPDATE_ASSET_MASTER_DATA, updateAmdModel, false).subscribe(response => {
            this.commonService.showAlertMessage('Successfully updated');
            this.getAllAssetMasterData();
            this.assetMasterFormGroup.reset();
            this.addAssetMaster = false;
          }, error => { })
    
        }
      }
    
    
    
    onGoBack() {
        this.assetMasterFormGroup.reset();
        this.addAssetMaster= false;
        this.title = 'Save';
    }


    NewAssetMaster(){
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
            'bracket1InsulatorBatch':[null],
            'stag1Ton9InsulatorBatch':[null],
            'rod1InsulatorBatch':[null],
            'pedestal1InsulatorBatch':[null],
            'core1InsulatorBatch':[null],
            'stay2InsulatorBatch':[null],
            'bracket2InsulatorBatch':[null],
            'stag2Ton9InsulatorBatch':[null],
            'rod2InsulatorBatch':[null],
            'pedestal2InsulatorBatch':[null],
            'core2InsulatorBatch':[null],
            'stay3InsulatorBatch':[null],
            'bracket3InsulatorBatch':[null],
            'stag3Ton9InsulatorBatch':[null],
            'rod3InsulatorBatch':[null],
            'pedestal3InsulatorBatch':[null],
            'core3InsulatorBatch':[null],
            'stagger':[null],

            
            
        });
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
          
    zoneList()
    {   
          this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_ZONE_LIST).subscribe((data) => {
             this.zoneData = data;
    }
           ); }
    
           divisionCode(code: any){
            this.sendAndRequestService.requestForPOST(Constants.app_urls.REPORTS.GET_DIVISION_BASED_ON_ZONE, code, false).subscribe((data) => {
              this.divisionsData=data;   
            }    
            )
      }
        subDivision(code){
              this.sendAndRequestService.requestForPOST(Constants.app_urls.REPORTS.GET_SUBDIVISION_BASED_ON_DIVISION ,code, false).subscribe((data) => {
                this.subDivisionData=data;   
              }    
              )
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
}

    
    
    

   


   

      
     



    
    
    

   