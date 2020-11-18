import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { SectorModel } from 'src/app/models/sector.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { DatePipe } from '@angular/common';
import { DataViewDialogComponent } from '../data-view-dialog/data-view-dialog.component';
import { SwitchOperationsComponent } from '../../components/switch-operations/switch-operations.component';

@Component({
  selector: 'app-sector',
  templateUrl: './sector.component.html',
  styleUrls: []
})
export class SectorComponent implements OnInit {
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  displayedColumns = ['sno', 'facilityId', 'sectorCode', 'fromLocation', 'fromLocationType', 'toLocation', 'toLocationType','division','line1','line2','actions','switch'];
  dataSource: MatTableDataSource<SectorModel>;
  dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
  switchDialogRef:MatDialogRef<SwitchOperationsComponent>;
  gridData = [];
  sectorList: any;
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
    private datePipe: DatePipe,
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
        { "Key": 'sectorCode', "Value": " " },
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
    const sector: SectorModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.SECTOR.GET_SECTOR).subscribe((data) => {
      this.sectorList = data;
      for (let i = 0; i < this.sectorList.length; i++) {
        this.sectorList[i].sno = i + 1;
        this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY+JSON.stringify(this.sectorList[i].facilityId)).subscribe((data) => {
          this.spinnerService.hide();
          this.facilityData = data;
          this.sectorList[i].facilityId = this.facilityData.facilityName;
        }, error => {
          this.spinnerService.hide();
      });
      sector.push(this.sectorList[i]);
    }
    this.filterData.gridData = sector;
    this.dataSource = new MatTableDataSource(sector);
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
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.ENERGY_BILL_PAYMENTS.SECTOR.DELETE_SECTOR, id).subscribe(data => {
          console.log(JSON.stringify(data));
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted Sector Successfully");
          this.getSectorData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Sector Deletion Failed.");
        })
      }
      this.confirmDialogRef = null;
    });
  }
  ViewData(data){
    var result = {
      'title':'Sector Data',
      'dataSource':[{label:'Depot',value:data.facilityId},{label:'Sector Code',value:data.sectorCode},{label:'From Location',value:data.fromLocation},
                    {label:'From Location Type', value:data.fromLocationType},{label:'To Location', value:data.toLocation},{label:'To Location Type', value:data.toLocationType},
                    {label:'Division',value:data.division},{label:'Line1',value:data.line1},{label:'line2',value:data.line2}]
    }
    this.dataViewDialogRef = this.dialog.open(DataViewDialogComponent, {
      disableClose: false,
      height: '400px',
      width: '80%',       
      data:result,  
    });            
  }
  switchDialog(sectorCode){
    this.dialog.open(SwitchOperationsComponent, {
      height: '600px',
      width: '80%', 
      data:sectorCode,
      disableClose: false,
    });
  }
}
