import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { Constants } from 'src/app/common/constants';
import { MatDialogRef, MatTableDataSource, MatPaginator, MatSort, MatDialog } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Router, ActivatedRoute } from '@angular/router';
import { DatePipe } from '@angular/common';
import { DataViewDialogComponent } from 'src/app/components/data-view-dialog/data-view-dialog.component';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';

@Component({
  selector: 'app-rc-failure',
  templateUrl: './rc-failure.component.html',
  styleUrls: ['./rc-failure.component.css']
})
export class RcFailureComponent implements OnInit {

  pagination = Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(sessionStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  displayedColumns = ['sno', 'subStation','relayIndication', 'fromDateTime', 'thruDateTime',
    'duration','divisionLocal','internalExternal', 'remarks', 'actions'];
  dataSource: MatTableDataSource<any>;
  dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;

  RcFailList: any;
  facilityList:any;
  filterData;
  gridData = [];

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
    var permissionName = this.commonService.getPermissionNameByLoggedData("FAILURES","RC FAIL") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();
    this.getRcFailureData();
    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'subStation', "Value": " " },           
        { "Key": 'relayIndication', "Value": " " },    
        { "Key": 'fromDateTime', "Value": " " },
        { "Key": 'thruDateTime', "Value": " " },
        { "Key": 'duration', "Value": " " },  
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
  getRcFailureData() {
    const RcFail: any[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.FAILURE_BY_TYPE_BASED_ON_DIVISION + Constants.FAILURE_TYPES.RC_FAILURE+'/'+this.userdata.username).subscribe((data) => {
      this.RcFailList = data;      
      for (let i = 0; i < this.RcFailList.length; i++) {
        this.RcFailList[i].sno = i + 1;
        this.RcFailList[i].fromDateTime = this.datePipe.transform(this.RcFailList[i].fromDateTime, 'dd-MM-yyyy HH:mm:ss');
        this.RcFailList[i].thruDateTime = this.datePipe.transform(this.RcFailList[i].thruDateTime, 'dd-MM-yyyy HH:mm:ss');        
        this.RcFailList[i].divisionLocal=this.RcFailList[i].divisionLocal == 'true' ? 'Local': 'Division',
        this.RcFailList[i].internalExternal=this.RcFailList[i].internalExternal == 'true' ? 'External': 'Internal',
        // this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY+this.RcFailList[i].subStation).subscribe((data) => {
        //   this.spinnerService.hide();
        //   this.facilityList = data;
        //   this.RcFailList[i].subStation = this.facilityList.facilityName;
        // });
       
        RcFail.push(this.RcFailList[i]);
      }
      this.filterData.gridData = RcFail;
      this.dataSource = new MatTableDataSource(RcFail);
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
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.FAILURES.DELETE_FAILURE_TYPE_ID, id).subscribe(data => {
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted Rc Fail Record Successfully");
          this.getRcFailureData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Rc Fail Record Deletion Failed.");
        })
      }
      this.confirmDialogRef = null;
    });
  }
  ViewData(data){
    var result = {
      'title':this.Titles.RC_Failures,
      'dataSource':[
    
      { label:FieldLabelsConstant.LABELS.SUB_STATION, value:data.subStation },
      { label:FieldLabelsConstant.LABELS.RELAY_INDICATION, value:data.relayIndication },
      { label:FieldLabelsConstant.LABELS.FROM_DATE_TIME, value:data.fromDateTime },
      { label:FieldLabelsConstant.LABELS.THRU_DATE_TIME, value:data.thruDateTime },
      { label:FieldLabelsConstant.LABELS.DURATION, value:data.duration },
      { label:FieldLabelsConstant.LABELS.DIVISION_LOCAL, value:data.divisionLocal },
      { label:FieldLabelsConstant.LABELS.INTERNAL_EXTERNAL, value:data.internalExternal },
      { label:FieldLabelsConstant.LABELS.REMARKS, value:data.remarks }
      
        
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
