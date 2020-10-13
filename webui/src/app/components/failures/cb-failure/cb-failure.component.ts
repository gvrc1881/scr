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
@Component({
  selector: 'app-cb-failure',
  templateUrl: './cb-failure.component.html',
  styleUrls: ['./cb-failure.component.css']
})
export class CbFailureComponent implements OnInit {

  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  displayedColumns = ['sno', 'subStation', 'equipment', 'cascadeAssets', 'fromDateTime', 'thruDateTime',
    'duration', 'relayIndication', 'natureOfClosure', 'rValue',
     'xValue', 'zConstant','faultDistance','actualFaultDistance','current','voltage',
     'trippedIdentifiedFault','divisionLocal','internalExternal', 'remarks', 'actions'];
  dataSource: MatTableDataSource<any>;
  dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;

  CbFailList: any;
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
    var permissionName = this.commonService.getPermissionNameByLoggedData("FAILURES","CB FAIL") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();
    this.getCbFailureData();
    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'subStation', "Value": " " },           
        { "Key": 'equipment', "Value": " " },
        { "Key": 'cascadeAssets', "Value": " " },
        { "Key": 'fromDateTime', "Value": " " },
        { "Key": 'thruDateTime', "Value": " " },
        { "Key": 'duration', "Value": " " },
        { "Key": 'relayIndication', "Value": " " },
        { "Key": 'natureOfClosure', "Value": " " },
        { "Key": 'rValue', "Value": " " },
        { "Key": 'xValue', "Value": " " },
        { "Key": 'zConstant', "Value": " " },
        { "Key": 'faultDistance', "Value": " " },
        { "Key": 'actualFaultDistance', "Value": " " },
        { "Key": 'current', "Value": " " },
        { "Key": 'voltage', "Value": " " },
        { "Key": 'phaseAngle', "Value": " " },
        { "Key": 'trippedIdentifiedFault', "Value": " " },
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
  getCbFailureData() {
    const CbFail: any[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.FAILURE_BY_TYPE + Constants.FAILURE_TYPES.CB_FAILURE).subscribe((data) => {
      this.CbFailList = data;
      console.log(this.CbFailList)
      for (let i = 0; i < this.CbFailList.length; i++) {
        this.CbFailList[i].sno = i + 1;
        this.CbFailList[i].fromDateTime = this.datePipe.transform(this.CbFailList[i].fromDateTime, 'dd-MM-yyyy hh:mm:ss');
        this.CbFailList[i].thruDateTime = this.datePipe.transform(this.CbFailList[i].thruDateTime, 'dd-MM-yyyy hh:mm:ss');
       
        this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY+this.CbFailList[i].subStation).subscribe((data) => {
          this.spinnerService.hide();
          this.facilityList = data;
          this.CbFailList[i].subStation = this.facilityList.facilityName;
        });
        
        CbFail.push(this.CbFailList[i]);
      }
      this.filterData.gridData = CbFail;
      this.dataSource = new MatTableDataSource(CbFail);
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
          this.commonService.showAlertMessage("Deleted Cb Fail Record Successfully");
          this.getCbFailureData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Cb Fail Record Deletion Failed.");
        })
      }
      this.confirmDialogRef = null;
    });
  }
  ViewData(data){
    var result = {
      'title':'CB Failures',
      'dataSource':[{label:'SubStation',value:data.subStation},{label:'Equipment',value:data.equipment},
      {label:'CascadeAssets', value:data.cascadeAssets},{label:'FromDateTime', value:data.fromDateTime},
      {label:'ThruDateTime', value:data.thruDateTime},{label:'Duration',value:data.duration},
      {label:'RelayIndication',value:data.relayIndication},{label:'NatureOfClosure',value:data.natureOfClosure},
      {label:'RValue',value:data.rValue},{label:'XValue',value:data.xValue},
      {label:'zConstant',value:data.zConstant},{label:'FaultDistance',value:data.faultDistance},
      {label:'ActualFaultDistance', value:data.actualFaultDistance},{label:'Current', value:data.current},
      {label:'Voltage',value:data.voltage},{label:'PhaseAngle',value:data.phaseAngle},
      {label:'TrippedIdentifiedFault',value:data.trippedIdentifiedFault},{label:'DivisionLocal',value:data.divisionLocal},
      {label:'InternalExternal', value:data.internalExternal},{label:'Remarks', value:data.remarks} ]
    }
    this.dataViewDialogRef = this.dialog.open(DataViewDialogComponent, {
      disableClose: false,
      height: '400px',
      width: '80%',       
      data:result,  
    });            
  }
}
