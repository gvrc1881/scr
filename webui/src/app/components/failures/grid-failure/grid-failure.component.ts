import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { Constants } from 'src/app/common/constants';
import { MatTableDataSource, MatPaginator, MatSort, MatDialog, MatDialogRef } from '@angular/material';
import { FailureAnalysisModel } from 'src/app/models/drive.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Router, ActivatedRoute } from '@angular/router';
import { DatePipe } from '@angular/common';
import { DataViewDialogComponent } from 'src/app/components/data-view-dialog/data-view-dialog.component';

@Component({
  selector: 'app-grid-failure',
  templateUrl: './grid-failure.component.html',
  styleUrls: ['./grid-failure.component.css']
})
export class GridFailureComponent implements OnInit {

  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  displayedColumns = ['sno', 'feedOf', 'fromDateTime', 'thruDateTime', 'duration', 'extendedOf',
    'feedExtendedFromDateTime', 'feedExtendedThruDateTime', 'feedExtendedDuration', 'maxDemand',
     'divisionLocal', 'internalExternal', 'remarks', 'actions'];
  dataSource: MatTableDataSource<FailureAnalysisModel>;
  dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
  facilityList:any;
  extendedFromList:any;
  filterData;
  gridData = [];
  gridFailList: any;

  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private sendAndRequestService: SendAndRequestService,
    private router: Router,
    private route: ActivatedRoute,
    public dialog: MatDialog,
    private datePipe: DatePipe,
  ) { }

  ngOnInit() {
    var permissionName = this.commonService.getPermissionNameByLoggedData("FAILURES","FAILURE ANALYSIS") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();
    this.getGridFailureData();
    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'feedOf', "Value": " " },           
        { "Key": 'fromDateTime', "Value": " " },
        { "Key": 'thruDateTime', "Value": " " },
        { "Key": 'duration', "Value": " " },
        { "Key": 'extendedOf', "Value": " " },
        { "Key": 'feedExtendedFromDateTime', "Value": " " },
        { "Key": 'feedExtendedThruDateTime', "Value": " " },
        { "Key": 'feedExtendedDuration', "Value": " " },
        { "Key": 'maxDemand', "Value": " " },
        { "Key": 'divisionLocal', "Value": " " },
        { "Key": 'internalExternal', "Value": " " },
        { "Key": 'remarks', "Value": " " },
       
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
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }
  getGridFailureData() {
    const gridFail: FailureAnalysisModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.FAILURE_BY_TYPE + Constants.FAILURE_TYPES.GRID_FAILURE).subscribe((data) => {
      this.gridFailList = data;
      console.log(this.gridFailList)
      for (let i = 0; i < this.gridFailList.length; i++) {
        this.gridFailList[i].sno = i + 1;
        this.gridFailList[i].fromDateTime = this.datePipe.transform(this.gridFailList[i].fromDateTime, 'dd-MM-yyyy hh:mm:ss');
        this.gridFailList[i].thruDateTime = this.datePipe.transform(this.gridFailList[i].thruDateTime, 'dd-MM-yyyy hh:mm:ss');
        this.gridFailList[i].feedExtendedFromDateTime = this.datePipe.transform(this.gridFailList[i].feedExtendedFromDateTime, 'dd-MM-yyyy hh:mm:ss');
        this.gridFailList[i].feedExtendedThruDateTime = this.datePipe.transform(this.gridFailList[i].feedExtendedThruDateTime, 'dd-MM-yyyy hh:mm:ss');
     
        this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY+this.gridFailList[i].feedOf).subscribe((data) => {
          this.spinnerService.hide();
          this.facilityList = data;
          this.gridFailList[i].feedOf = this.facilityList.facilityName;
        });
        this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY+this.gridFailList[i].extendedOf).subscribe((data) => {
          this.spinnerService.hide();
          this.extendedFromList = data;
          this.gridFailList[i].extendedOf = this.extendedFromList.facilityName;
        });
  
  
        gridFail.push(this.gridFailList[i]);
      }
     
      this.filterData.gridData = gridFail;
      this.dataSource = new MatTableDataSource(gridFail);
      this.commonService.updateDataSource(this.dataSource, this.displayedColumns);
      this.filterData.dataSource = this.dataSource;
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;

      // this.dataSource = new MatTableDataSource(gridFail);
      // this.dataSource.paginator = this.paginator;
      // this.dataSource.sort = this.sort;
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
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.FAILURES.DELETE_FAILURE_TYPE_ID, id).subscribe(data => {
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted Grid Fail Record Successfully");
          this.getGridFailureData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Grid Fail Record Deletion Failed.");
        })
      }
      this.confirmDialogRef = null;
    });
  }
  ViewData(data){
    var result = {
      'title':'GRID Failure',
      'dataSource':[{label:'FeedOf',value:data.feedOf},{label:'FromDateTime',value:data.fromDateTime},
      {label:'ThruDateTime', value:data.thruDateTime},{label:'Duration', value:data.duration},
      {label:'ExtendedOf', value:data.extendedOf},{label:'FeedExtendedFromDateTime',value:data.feedExtendedFromDateTime},
      {label:'FeedExtendedThruDateTime',value:data.feedExtendedThruDateTime},{label:'FeedExtendedDuration',value:data.feedExtendedDuration},
      {label:'MaxDemand',value:data.maxDemand},{label:'Division/Local',value:data.divisionLocal},
      {label:'Internal/External',value:data.internalExternal},{label:'Remarks',value:data.remarks},
      {label:'TypeOfFailure', value:data.typeOfFailure} ]
    }
    this.dataViewDialogRef = this.dialog.open(DataViewDialogComponent, {
      disableClose: false,
      height: '400px',
      width: '80%',       
      data:result,  
    });            
  }
}
