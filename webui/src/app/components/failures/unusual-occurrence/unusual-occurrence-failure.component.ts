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
  selector: 'app-unusual-occurrence-failure',
  templateUrl: './unusual-occurrence-failure.component.html',
  styleUrls: ['./unusual-occurrence-failure.component.css']
})
export class UnusualOccurrenceFailureComponent implements OnInit {

  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  displayedColumns = ['sno', 'subStation', 'location', 'causeOfFailure', 'fromDateTime', 'thruDateTime',
    'duration','impact', 'remarks','divisionLocal','internalExternal', 'actions'];
  dataSource: MatTableDataSource<any>;
  dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;

  UnusualOccurrenceFailList: any;

  displayedColumnsActions = ['sno', 'failureActivity', 'fromTime', 'thruTime','by','specialRemarks',
    'remarks','location','trainNo', 'actions'];
  dataSourceActions: MatTableDataSource<any>;
  @ViewChild(MatPaginator, { static: true }) paginatorActions: MatPaginator;
  @ViewChild(MatSort, { static: true }) sortActions: MatSort;
  @ViewChild('filter', { static: true }) filterActions: ElementRef;
  ActionsFailListActions: any;

  filterData;
  filterActionsData;
  gridData = [];

  updatePagination() {
    this.filterData.dataSource = this.filterData.dataSource;
    this.filterData.dataSource.paginator = this.paginator;
  }
  updatePaginatn() {
    this.filterActionsData.dataSource = this.filterActionsData.dataSource;
    this.filterActionsData.dataSource.paginator = this.paginator;
  }

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
    var permissionName = this.commonService.getPermissionNameByLoggedData("FAILURES","Unusual Occurrence Failure") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();
    this.getUnusualOccurrenceFailureData();

    this.getActionsFailureData();
    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'subStation', "Value": " " },           
        { "Key": 'location', "Value": " " },
        { "Key": 'causeOfFailure', "Value": " " },
        { "Key": 'fromDateTime', "Value": " " },
        { "Key": 'thruDateTime', "Value": " " },
        { "Key": 'duration', "Value": " " },
        { "Key": 'impact', "Value": " " },
        { "Key": 'remarks', "Value": " " },
        { "Key": 'divisionLocal', "Value": " " },
        { "Key": 'internalExternal', "Value": " " },
       
      
       
      ],
      gridData: this.gridData,
      dataSource: this.dataSource,
      paginator: this.paginator,
      sort: this.sort
     
    }; 
    this.filterActionsData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'failureActivity', "Value": " " },           
        { "Key": 'fromTime', "Value": " " },
        { "Key": 'thruTime', "Value": " " },
        { "Key": 'by', "Value": " " },
        { "Key": 'specialRemarks', "Value": " " },
        { "Key": 'remarks', "Value": " " },
        { "Key": 'location', "Value": " " },
        { "Key": 'trainNo', "Value": " " },
      
       
      
       
      ],
      gridData: this.gridData,
      dataSource: this.dataSource,
      paginator: this.paginator,
      sort: this.sort
     
    }; 

  }
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }
  getUnusualOccurrenceFailureData() {
    const UnusualOccurrenceFail: any[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.FAILURE_BY_TYPE + Constants.FAILURE_TYPES.UNUSUAL_OCCURRENCE_FAILURE).subscribe((data) => {
      this.UnusualOccurrenceFailList = data;
      console.log(this.UnusualOccurrenceFailList)
      for (let i = 0; i < this.UnusualOccurrenceFailList.length; i++) {
        this.UnusualOccurrenceFailList[i].sno = i + 1;
        this.UnusualOccurrenceFailList[i].fromDateTime = this.datePipe.transform(this.UnusualOccurrenceFailList[i].fromDateTime, 'dd-MM-yyyy hh:mm:ss');
        this.UnusualOccurrenceFailList[i].thruDateTime = this.datePipe.transform(this.UnusualOccurrenceFailList[i].thruDateTime, 'dd-MM-yyyy hh:mm:ss');
        
        UnusualOccurrenceFail.push(this.UnusualOccurrenceFailList[i]);
      }
      this.filterData.gridData = UnusualOccurrenceFail;
      this.dataSource = new MatTableDataSource(UnusualOccurrenceFail);
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
    this.router.navigate(['unusual-occurrence/'+id], { relativeTo: this.route });
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
          this.commonService.showAlertMessage("Deleted UnusualOccurrence Fail Record Successfully");
          this.getUnusualOccurrenceFailureData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("UnusualOccurrence Fail Record Deletion Failed.");
        })
      }
      this.confirmDialogRef = null;
    });
  }

/******* ACTIONS */
applyFilterActions(filterValue: string) {
  filterValue = filterValue.trim(); // Remove whitespace
  filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
  this.dataSourceActions.filter = filterValue;
}
getActionsFailureData() {
  const ActionsFail: any[] = [];
  console.log("list"+this.ActionsFailListActions);
  this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.FAILURE_BY_TYPE + Constants.FAILURE_TYPES.UNUSUAL_OCCURRENCE_FAILURE).subscribe((data) => {
    this.ActionsFailListActions = data;
    console.log(this.ActionsFailListActions)
    for (let i = 0; i < this.ActionsFailListActions.length; i++) {
      this.ActionsFailListActions[i].sno = i + 1;
      ActionsFail.push(this.ActionsFailListActions[i]);
    }

    this.filterActionsData.gridData = ActionsFail;
    this.dataSourceActions = new MatTableDataSource(ActionsFail);
    this.commonService.updateDataSource(this.dataSourceActions, this.displayedColumnsActions);
    this.filterActionsData.dataSourceActions = this.dataSource;
    this.dataSourceActions.paginator = this.paginator;
    this.dataSourceActions.sort = this.sort;
    this.spinnerService.hide();
  }, error => {
    this.spinnerService.hide();
  });
}
processEditActions(id) {
  this.router.navigate(['actions/' + id], { relativeTo: this.route });
}
deleteActions(id) {
  this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
    disableClose: false
  });
  this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete?';
  this.confirmDialogRef.afterClosed().subscribe(result => {
    if (result) {
      this.spinnerService.show();
      this.sendAndRequestService.requestForDELETE(Constants.app_urls.FAILURES.DELETE_FAILURE_TYPE_ID, id).subscribe(data => {
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Deleted Actions Fail Record Successfully");
        this.getActionsFailureData();
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Actions Fail Record Deletion Failed.");
      })
    }
    this.confirmDialogRef = null;
  });
}
ViewData(data){
  var result = {
    'title':'Unusual Occurence ',
    'dataSource':[{label:'SubStation',value:data.subStation},{label:'location',value:data.location},
    {label:'CauseOfFailure',value:data.causeOfFailure},{label:'FromDateTime', value:data.fromDateTime},
    {label:'ThruDateTime', value:data.thruDateTime},{label:'Duration',value:data.duration},
    {label:'DivisionLocal',value:data.divisionLocal},{label:'InternalExternal', value:data.internalExternal},
    {label:'Impact',value:data.impact},{label:'Remarks', value:data.remarks} ]
  }
  this.dataViewDialogRef = this.dialog.open(DataViewDialogComponent, {
    disableClose: false,
    height: '400px',
    width: '80%',       
    data:result,  
  });            
}
}
