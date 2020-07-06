import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource, MatDialogRef, MatDialog } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { DriveModel, DriveCategoryModel, DriveCategoryAssoModel } from 'src/app/models/drive.model';
import { Router, ActivatedRoute } from '@angular/router';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';

@Component({
  selector: 'app-drives',
  templateUrl: './drive.component.html',
  styleUrls: ['./drive.component.css']
})
export class DrivesComponent implements OnInit {
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  displayedColumns = ['sno', 'name', 'description', 'fromDate', 'toDate', 'depoType', 'assetType',
    'assetDescription', 'criteria', 'targetQuantity', 'isIdRequired', 'functionalUnit',
    'checkList', 'active', 'actions'];
  driveCategoryDisplayedColumns = ['sno', 'name', 'description', 'fromDate', 'toDate', 'authority', 'actions'];
  driveCategoryAssoDisplayedColumns = ['sno', 'drive', 'driveCategory', 'active', 'actions'];

  dataSource: MatTableDataSource<DriveModel>;
  driveCategoryDataSource: MatTableDataSource<DriveCategoryModel>;
  driveCategoryAssoDataSource: MatTableDataSource<DriveCategoryAssoModel>;
  depoTypeList = [];
  assetTypeList = [];

  functionalUnitList: any;
  allFunctionalUnitsList: any;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
  drivesList: any;

  @ViewChild(MatPaginator, { static: true }) driveCategoryPaginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) driveCategorySort: MatSort;
  @ViewChild('filter', { static: true }) driveCategoryFilter: ElementRef;
  driveCategoryList: any;

  @ViewChild(MatPaginator, { static: true }) driveCategoryAssoPaginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) driveCategoryAssoSort: MatSort;
  @ViewChild('filter', { static: true }) driveCategoryAssoFilter: ElementRef;
  driveCategoryAssoList: any;

  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private route: ActivatedRoute,
    public dialog: MatDialog,
    private sendAndRequestService:SendAndRequestService
  ) { }

  ngOnInit() {
    var permissionName = this.commonService.getPermissionNameByLoggedData("DRIVES","DRIVES") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();
    this.findDepoTypeList();
    this.findFunctionalUnits();
    this.getDrivesData();

    this.getDriveCategoryData();
    this.getDriveCategoryAssoData();
  }
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }

  driveCategoryApplyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.driveCategoryDataSource.filter = filterValue;
  }

  driveCategoryAssoApplyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.driveCategoryAssoDataSource.filter = filterValue;
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
  getDrivesData() {
    const drive: DriveModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE.GET_DRIVES).subscribe((data) => {
      this.drivesList = data;
      for (let i = 0; i < this.drivesList.length; i++) {
        this.drivesList[i].sno = i + 1;
        this.drivesList[i].targetQuantity = this.drivesList[i].target_qty;
        this.drivesList[i].status = this.drivesList[i].active;
        this.drivesList[i].checkList = this.drivesList[i].checklist;
        this.drivesList[i].depoType = !!this.drivesList[i].depotType ? this.drivesList[i].depotType['depotType'] : '';
        drive.push(this.drivesList[i]);
      }

      this.dataSource = new MatTableDataSource(drive);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }

  getDriveCategoryData() {
    const drive: DriveCategoryModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CATEGORY.GET_DRIVE_CATEGORY).subscribe((data) => {
      this.driveCategoryList = data;
      for (let i = 0; i < this.driveCategoryList.length; i++) {
        this.driveCategoryList[i].sno = i + 1;
        this.driveCategoryList[i].name = this.driveCategoryList[i].driveCategoryName;
        drive.push(this.driveCategoryList[i]);
      }

      this.driveCategoryDataSource = new MatTableDataSource(drive);
      this.driveCategoryDataSource.paginator = this.driveCategoryPaginator;
      this.driveCategoryDataSource.sort = this.driveCategorySort;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }

  getDriveCategoryAssoData() {
    const drive: DriveCategoryAssoModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CATEGORY_ASSOCIATION.GET_DRIVE_CATEGORY_ASSOC).subscribe((data) => {
      this.driveCategoryAssoList = data;
      for (let i = 0; i < this.driveCategoryAssoList.length; i++) {
        this.driveCategoryAssoList[i].sno = i + 1;
        this.driveCategoryAssoList[i].drive = this.driveCategoryAssoList[i].driveCategoryId['driveCategoryName'];
        this.driveCategoryAssoList[i].driveCategory = this.driveCategoryAssoList[i].driveId['name'];
        drive.push(this.driveCategoryAssoList[i]);
      }

      this.driveCategoryAssoDataSource = new MatTableDataSource(drive);
      this.driveCategoryAssoDataSource.paginator = this.driveCategoryAssoPaginator;
      this.driveCategoryAssoDataSource.sort = this.driveCategoryAssoSort;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }

  processEditAction(id) {
    this.router.navigate(['drive/' + id], { relativeTo: this.route });
  }
  delete(id) {
    this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
      disableClose: false
    });
    this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete?';
    this.confirmDialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.spinnerService.show();
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.DRIVE.DRIVE.DELETE_DRIVE, id).subscribe(data => {
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted Drive Successfully");
          this.getDrivesData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Drive Deletion Failed.");
        })
      }
      this.confirmDialogRef = null;
    });
  }

  driveCategoryEdit(id) {
    this.router.navigate(['drive-category/' + id], { relativeTo: this.route });
  }
  driveCategoryDelete(id) {
    this.spinnerService.show();
    this.sendAndRequestService.requestForDELETE(Constants.app_urls.DRIVE.DRIVE_CATEGORY.DELETE_DRIVE_CATEGORY, id).subscribe(data => {
      this.spinnerService.hide();
      this.commonService.showAlertMessage("Deleted Drive Category Successfully");
      this.getDriveCategoryData();
    }, error => {
      console.log('ERROR >>>');
      this.spinnerService.hide();
      this.commonService.showAlertMessage("Drive Category Deletion Failed.");
    })
  }

  driveCategoryAssoEdit(id) {
    this.router.navigate(['drive-category-association/' + id], { relativeTo: this.route });
  }
  driveCategoryAssoDelete(id) {
    this.spinnerService.show();
    this.sendAndRequestService.requestForDELETE(Constants.app_urls.DRIVE.DRIVE_CATEGORY_ASSOCIATION.DELETE_DRIVE_CATEGORY_ASSOC, id).subscribe(data => {
      this.spinnerService.hide();
      this.commonService.showAlertMessage("Deleted Drive Category Asso Successfully");
      this.getDriveCategoryAssoData();
    }, error => {
      console.log('ERROR >>>');
      this.spinnerService.hide();
      this.commonService.showAlertMessage("Drive Category Asso Deletion Failed.");
    })
  }
}
