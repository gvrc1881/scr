import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { DataViewDialogComponent } from '../data-view-dialog/data-view-dialog.component';
import { SwitchOperationsComponent } from '../../components/switch-operations/switch-operations.component';
import { SubSectorModel } from 'src/app/models/sub-sector.model';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';


@Component({
  selector: 'sub-sector',
  templateUrl: './sub-sector.component.html',
  styleUrls: []
})
export class  SubSectorComponent implements OnInit {
  pagination =Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  displayedColumns = ['sno', 'facilityId', 'sector','subSectorCode','fromLocation', 'fromLocationType', 'toLocation', 'toLocationType','division','line1','line2','actions','switch'];
  dataSource: MatTableDataSource<SubSectorModel>;
  dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
  gridData = [];
  subSectorList: any;
  filterData;
  facilityData: any;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;

  

  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
   private sendAndRequestService : SendAndRequestService,
    private router: Router,
    private route: ActivatedRoute,
    public dialog: MatDialog,
  ) { }

  ngOnInit() {
    var permissionName = this.commonService.getPermissionNameByLoggedData("ASSET REGISTER","SECTOR") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();
    this.getSectorData();

    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'facilityId', "Value": " " },
        { "Key": 'sector', "Value": " " },
        { "Key": 'subSectorCode', "Value": " " },
        { "Key": 'fromLocation', "Value": " " },
        { "Key": 'fromLocationType', "Value": "" },
        { "Key": 'toLocation', "Value": " " },
        { "Key": 'toLocationType', "Value": " " },
        { "Key": 'division', "Value": "" },
        { "Key": 'line1', "Value": " " }, 
        { "Key": 'line2', "Value": " " }
      ],
      gridData: this.gridData,
      dataSource: this.dataSource,
      paginator: this.paginator,
      sort: this.sort
    };

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
  getSectorData() {
    const subSector: SubSectorModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.SUBSECTOR.GET_SUB_SECTOR).subscribe((data) => {
      this.subSectorList = data;
      for (let i = 0; i < this.subSectorList.length; i++) {
        this.subSectorList[i].sno = i + 1;
        this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY+JSON.stringify(this.subSectorList[i].facilityId)).subscribe((data) => {
          this.spinnerService.hide();
          this.facilityData = data;
          this.subSectorList[i].facilityId = this.facilityData.facilityName;
        }, error => {
          this.spinnerService.hide();
      });
      subSector.push(this.subSectorList[i]);
    }
    this.filterData.gridData = subSector;
    this.dataSource = new MatTableDataSource(subSector);
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
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.ENERGY_BILL_PAYMENTS.SUBSECTOR.DELETE_SUB_SECTOR, id).subscribe(data => {
          console.log(JSON.stringify(data));
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted SubSector Successfully");
          this.getSectorData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("SubSector Deletion Failed.");
        })
      }
      this.confirmDialogRef = null;
    });
  }
  ViewData(data){
    var result = {
      'title':this.Titles.SUB_SECTOR_DATA,
      'dataSource':[
                    {label:FieldLabelsConstant.LABELS.DEPOT,value:data.facilityId},
                    {label:FieldLabelsConstant.LABELS.SECTOR_CODE,value:data.sector},
                    {label:FieldLabelsConstant.LABELS.SUB_SECTOR_CODE,value:data.subSectorCode},
                    {label:FieldLabelsConstant.LABELS.FROM_LOCATION,value:data.fromLocation},
                    {label:FieldLabelsConstant.LABELS.FROM_LOCATION_TYPE, value:data.fromLocationType},
                    {label:FieldLabelsConstant.LABELS.TO_LOCATION, value:data.toLocation},
                    {label:FieldLabelsConstant.LABELS.TO_LOCATION_TYPE, value:data.toLocationType},
                    {label:FieldLabelsConstant.LABELS.DIVISION,value:data.division},
                    {label:FieldLabelsConstant.LABELS.LINE1,value:data.line1},
                    {label:FieldLabelsConstant.LABELS.LINE2,value:data.line2}]
    }
    this.dataViewDialogRef = this.dialog.open(DataViewDialogComponent, {
      disableClose: false,
      height: '400px',
      width: '80%',       
      data:result,  
    });            
  }
  switchDialog(subSectorCode){
    var subSectorData={
      'type':"SUB SECTOR",
      'data':subSectorCode
    }
    this.dialog.open(SwitchOperationsComponent, {
      height: '600px',
      width: '80%', 
      data:subSectorData,
      
    });

  }
}













