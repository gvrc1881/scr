import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { AssetMasterDataModel } from 'src/app/models/asset-master-data.model';
import { DataViewDialogComponent } from '../data-view-dialog/data-view-dialog.component';
import { ReportModel } from 'src/app/models/report.model';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'amd',
  templateUrl: './amd.component.html',
  styleUrls: []
})
export class AmdComponent implements OnInit {

  pagination = Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  addPermission: boolean = true;
  editPermission: boolean = true;
  deletePermission: boolean = true;
  amdList: any;
  filterData;
  reportModel: ReportModel;
  from: number;
  to: number;
  loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
  displayedColumns = ['sno', 'type', 'facilityId', 'assetType', 'assetId', 'adeeSection', 'majorSection', 'section', 'locationPosition', 'kilometer', 'elementarySection', 'actions'];
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  facilityData: any;
  dataSource: MatTableDataSource<AssetMasterDataModel>;
  gridData = [];
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
  dataViewDialogRef: MatDialogRef<DataViewDialogComponent>;
  depoTypeList = [];
  assetTypeList = [];
  scheduleList = [];
  allFunctionalUnitsList: any;
  private searchQuery = new Map<string, string>();
  jsonObject = {};
  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private route: ActivatedRoute,
    public dialog: MatDialog,
    private sendAndRequestService: SendAndRequestService,
    private datePipe: DatePipe,
  ) { }

  ngOnInit() {
    this.from = 0;
    this.to = 0;
    this.reportModel = new ReportModel();
    this.getAllAssetMasterData(0, 30);
    var permissionName = this.commonService.getPermissionNameByLoggedData("ASSET REGISTER", "OHE ASSET MASTER");
    this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
    this.spinnerService.show();
    this.findDepoTypeList();
    this.findFunctionalUnits();
    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'type', "Value": " " },
        { "Key": 'facilityId', "Value": " " },
        { "Key": 'assetType', "Value": " " },
        { "Key": 'assetId', "Value": "" },
        { "Key": 'adeeSection', "Value": " " },
        { "Key": 'majorSection', "Value": " " },
        { "Key": 'section', "Value": "" },
        { "Key": 'locationPosition', "Value": " " },
        { "Key": 'kilometer', "Value": " " },
        { "Key": 'elementarySection', "Value": " " },
      ],
      gridData: this.gridData,
      dataSource: this.dataSource,
      paginator: this.paginator,
      sort: this.sort
    };

  }
  findDepoTypeList() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FUNCTIONAL_LOCATION_TYPES)
      .subscribe((depoTypes) => {
        this.depoTypeList = depoTypes;
      })
  }

  findAssetTypeList(assertType) {
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
  getAllAssetMasterData(from: number, to: number) {
    this.spinnerService.show();
    const assetMasterData: AssetMasterDataModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.GET_ASSET_MASTER_DATA + '/' + from + '/' + to+'/'+this.loggedUserData.username).subscribe((data) => {
      this.amdList = data;
      for (let i = 0; i < this.amdList.length; i++) {
        this.amdList[i].sno = i + 1;
        this.amdList[i].dateOfCommision = this.datePipe.transform(this.amdList[i].dateOfCommision, 'dd-MM-yyyy');
        this.amdList[i].dateOfManufacture = this.datePipe.transform(this.amdList[i].dateOfManufacture, 'dd-MM-yyyy');
        this.amdList[i].dateOfReceived = this.datePipe.transform(this.amdList[i].dateOfReceived, 'dd-MM-yyyy');
        this.amdList[i].equippedDate = this.datePipe.transform(this.amdList[i].equippedDate, 'dd-MM-yyyy');
        this.amdList[i].expiryDate = this.datePipe.transform(this.amdList[i].expiryDate, 'dd-MM-yyyy');
        this.amdList[i].lugDate = this.datePipe.transform(this.amdList[i].lugDate, 'dd-MM-yyyy');
        this.amdList[i].warrantyAmcEndDate = this.datePipe.transform(this.amdList[i].warrantyAmcEndDate, 'dd-MM-yyyy');
        this.amdList[i].stripDate = this.datePipe.transform(this.amdList[i].stripDate, 'dd-MM-yyyy');
        this.amdList[i].facilityId = this.amdList[i].facilityId;
        assetMasterData.push(this.amdList[i]);
      }
      this.filterData.gridData = assetMasterData;
      this.dataSource = new MatTableDataSource(assetMasterData);
      this.commonService.updateDataSource(this.dataSource, this.displayedColumns);
      this.filterData.dataSource = this.dataSource;
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    }, error => { });

  }
  processEditAction(id) {
    this.router.navigate([id], { relativeTo: this.route });
  }
  delete(id) {
    this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
      disableClose: false

    });
    this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete?';
    this.confirmDialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.spinnerService.show();
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.DELETE_ASSET_MASTER_DATA, id)
          .subscribe(data => {
            this.spinnerService.hide();
            this.commonService.showAlertMessage("Deleted Asset MasterData Successfully");
            this.getAllAssetMasterData(0, 30);
          }, error => {
            console.log('ERROR >>>');
            this.spinnerService.hide();
            this.commonService.showAlertMessage("Asset MasterData Deletion failed.");
          })
      }
      this.confirmDialogRef = null;
    });
  }

  updatePagination(columnName: string, value: string) {
    console.log(columnName + " : " + JSON.stringify(value));

    if (this.searchQuery.size == 0) {
      this.searchQuery.set(columnName, value.trim());
    } else {
      if (value.trim() == '' && this.searchQuery.has(columnName)) {
        this.searchQuery.delete(columnName);
      } else {
        this.searchQuery.set(columnName, value.trim());
      }
    }
    console.log(this.searchQuery);
    this.filterData.dataSource = this.filterData.dataSource;
    this.filterData.dataSource.paginator = this.paginator;
    if (this.searchQuery.size != 0) {
      this.jsonObject = {};
      this.searchQuery.forEach((value, key) => {
        this.jsonObject[key] = value
      });
      this.jsonObject['from'] = 1;
      this.jsonObject['to'] = 29;
      this.updatePaginationResults();
    }
  }

  updatePaginationResults(){
    const assetMasterData: AssetMasterDataModel[] = [];
      this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSETMASTERDATA.ASSET_MASTER_DATA_SEARCH, this.jsonObject, false).subscribe((data) => {
        console.log(data);
        this.amdList = data;
        for (let i = 0; i < this.amdList.length; i++) {
          this.amdList[i].sno = i + 1;
          this.amdList[i].facilityId = this.amdList[i].facilityId;
          assetMasterData.push(this.amdList[i]);
        }
        this.filterData.gridData = assetMasterData;
        this.dataSource = new MatTableDataSource(assetMasterData);
        this.commonService.updateDataSource(this.dataSource, this.displayedColumns);
        this.filterData.dataSource = this.dataSource;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      }, error => { });
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.filterData.dataSource.filter = filterValue;
  }
  ViewData(data) {
    var result = {
      'title': this.Titles.ASSET_MASTER_DATA,
      'dataSource': [

        { label: FieldLabelsConstant.LABELS.DEPOT_TYPE, value: data.type },
        { label: FieldLabelsConstant.LABELS.DEPOT, value: data.facilityId },
        { label: FieldLabelsConstant.LABELS.ASSET_TYPE, value: data.assetType },
        { label: FieldLabelsConstant.LABELS.ASSET_ID, value: data.assetId },
        { label: FieldLabelsConstant.LABELS.ADEE_SECTION, value: data.adeeSection },
        { label: FieldLabelsConstant.LABELS.MAJOR_SECTION, value: data.majorSection },
        { label: FieldLabelsConstant.LABELS.LOCATION_POSITION, value: data.locationPosition },
        { label: FieldLabelsConstant.LABELS.KILOMETER, value: data.kilometer },
        { label: FieldLabelsConstant.LABELS.ELEMENTARY_SECTIONS, value: data.elementarySections },
       // { label: FieldLabelsConstant.LABELS.CREATED_ON, value: data.createdOn },
        { label: FieldLabelsConstant.LABELS.DATE_OF_COMMISSION, value: data.dateOfCommision },
        { label: FieldLabelsConstant.LABELS.DATE_OF_MANUFACTURE, value: data.dateOfManufacture },
        { label: FieldLabelsConstant.LABELS.DATE_OF_RECEIVED, value: data.dateOfReceived },
        { label: FieldLabelsConstant.LABELS.EQUIPPED_DATE, value: data.equippedDate },
        { label: FieldLabelsConstant.LABELS.EXPIRY_DATE, value: data.expiryDate },
        { label: FieldLabelsConstant.LABELS.LUG_DATE, value: data.lugDate },
        { label: FieldLabelsConstant.LABELS.STRIP_DATE, value: data.stripDate },
        { label: FieldLabelsConstant.LABELS.WARRANTY_AMC_ENDDATE, value: data.warrantyAmcEndDate },
        { label: FieldLabelsConstant.LABELS.REMARK1, value: data.remark1 },
        { label: FieldLabelsConstant.LABELS.REMARK2, value: data.remark2 }

      ]
    }
    this.dataViewDialogRef = this.dialog.open(DataViewDialogComponent, {
      disableClose: false,
      height: '400px',
      width: '80%',
      data: result,
    });
  }

  getServerData($event) {
    console.log($event);
    console.log($event.pageIndex + " : " + $event.pageSize + " : " + $event.length);
    //if (((parseInt($event.pageIndex)) * parseInt($event.pageSize)) + (5 * parseInt($event.pageIndex)) == $event.length+($event.pageIndex)) {
      if(Object.keys(this.jsonObject).length == 0){
        this.getAllAssetMasterData($event.length + 1, $event.length + 29);
      }else{
        this.jsonObject['from'] = $event.length + 1;
        this.jsonObject['to'] = $event.length + 29;
        console.log(this.jsonObject);
        this.updatePaginationResults();
      }
    //}
  }
}













