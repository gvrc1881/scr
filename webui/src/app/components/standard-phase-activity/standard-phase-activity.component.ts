import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { StandardPhaseActivityModel } from 'src/app/models/standard-phase-activity.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { DataViewDialogComponent } from '../data-view-dialog/data-view-dialog.component';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';

@Component({
  selector: 'app-standard-phase-activity',
  templateUrl: './standard-phase-activity.component.html',
  styleUrls: []
})
export class StandardPhaseActivityComponent implements OnInit {

  pagination =Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  displayedColumns = ['sno','name','description','dependencyToStart','uom','depotType','assetType','actions'];
  dataSource: MatTableDataSource<StandardPhaseActivityModel>;
  dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
  filterData;


  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
  gridData = [];

  standardPhaseActivityList: any;

  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private route: ActivatedRoute,
    public dialog: MatDialog,
    private sendAndRequestService:SendAndRequestService
  ) { }

  ngOnInit() {
    var permissionName = this.commonService.getPermissionNameByLoggedData("PROJECT ADMIN","STD Phase Activity") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();
    this.getStandardPhasesData();
    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'name', "Value": " " },
        { "Key": 'description', "Value": " " },
        { "Key": 'dependencyToStart', "Value": " " },
        { "Key": 'uom', "Value": "" },
        { "Key": 'depotType', "Value": " " },
        { "Key": 'assetType', "Value": " " },



      ],
      gridData: this.gridData,
      dataSource: this.dataSource,
      paginator: this.paginator,
      sort: this.sort
    };


  }
  getStandardPhasesData() {
    const standardPhases: StandardPhaseActivityModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.STANDARD_PHASE_ACTIVITY.GET_SPA).subscribe((data) => {
      this.standardPhaseActivityList = data;
      for (let i = 0; i < this.standardPhaseActivityList.length; i++) {
        this.standardPhaseActivityList[i].sno = i + 1;
        standardPhases.push(this.standardPhaseActivityList[i]);
      }
      this.filterData.gridData = standardPhases;
      this.dataSource = new MatTableDataSource(standardPhases);
      this.commonService.updateDataSource(this.dataSource, this.displayedColumns);
      this.filterData.dataSource = this.dataSource;
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
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
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.STANDARD_PHASE_ACTIVITY.DELETE_SPA ,id).subscribe(data => {
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted Standard Phases Activity Successfully");
          this.getStandardPhasesData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Standard Phases Activity Deletion Failed.");
        });
      }
      this.confirmDialogRef = null;
    });
  }
  updatePagination() {
    this.filterData.dataSource = this.filterData.dataSource;
    this.filterData.dataSource.paginator = this.paginator;
  }
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); 
    filterValue = filterValue.toLowerCase(); 
    this.filterData.dataSource.filter = filterValue;
  }
  ViewData(data){
    var result = {
      'title':this.Titles.STANDARD_PHASE_ACTIVITY_DATA,
      'dataSource':[                                 
                    { label:FieldLabelsConstant.LABELS.NAME, value:data.name },
                    { label:FieldLabelsConstant.LABELS.ASSET_TYPE, value:data.assetType },
                    { label:FieldLabelsConstant.LABELS.DEPOT_TYPE, value:data.depotType },
                    { label:FieldLabelsConstant.LABELS.IS_CHECKLIST, value:data.isCheckList },
                    { label:FieldLabelsConstant.LABELS.IS_OBJECT_ID_REQUIRED, value:data.isObjectIdRequired },
                    { label:FieldLabelsConstant.LABELS.UOM, value:data.uom },
                    { label:FieldLabelsConstant.LABELS.DEPENDENCY_TO_START, value:data.dependencyToStart },
                    { label:FieldLabelsConstant.LABELS.DESCRIPTION, value:data.description },
                    
                  ]
    }
    this.dataViewDialogRef = this.dialog.open(DataViewDialogComponent, {
      disableClose: false,
      height: '400px',
      width: '80%',       
      data:result,  
    });            
  }
}
