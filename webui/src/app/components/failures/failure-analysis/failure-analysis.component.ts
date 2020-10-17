import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { FailureAnalysisModel } from 'src/app/models/drive.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { DatePipe } from '@angular/common';
import { DataViewDialogComponent } from 'src/app/components/data-view-dialog/data-view-dialog.component';
@Component({
  selector: 'app-failure-analysis',
  templateUrl: './failure-analysis.component.html',
  styleUrls: []
})
export class FailureAnalysisComponent implements OnInit {

  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
  displayedColumns = ['sno', 'reported', 'div','date',
    'failureSection', 'assetType', 'assetId',
    'rootCause', 'actionPlan', 'actionStatus', 'actionTargetDate', 'actionCompletedDate',
    'actionDescription', 'actions'];
  dataSource: MatTableDataSource<FailureAnalysisModel>;
  filterData;
  gridData = [];

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
 

  driveTargetList: any;

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
    this.getFailureAnalysisData();
    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'reported', "Value": " " },           
        { "Key": 'div', "Value": " " },
        { "Key": 'date', "Value": " " },
        { "Key": 'failureSection', "Value": " " },
        { "Key": 'assetType', "Value": " " },
        { "Key": 'assetId', "Value": " " },
        { "Key": 'rootCause', "Value": " " },
        { "Key": 'actionPlan', "Value": " " },
        { "Key": 'actionStatus', "Value": " " },
        { "Key": 'actionTargetDate', "Value": " " },
        { "Key": 'actionCompletedDate', "Value": " " },
        { "Key": 'actionDescription', "Value": " " },
       
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
  updatePagination() {
    this.filterData.dataSource = this.filterData.dataSource;
    this.filterData.dataSource.paginator = this.paginator;
  }
  getFailureAnalysisData() {
    const driveTarget: FailureAnalysisModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.GET_FAILURES).subscribe((data) => {
      this.driveTargetList = data;
      for (let i = 0; i < this.driveTargetList.length; i++) {
        this.driveTargetList[i].sno = i + 1;
        this.driveTargetList[i].date = this.datePipe.transform(this.driveTargetList[i].date, 'dd-MM-yyyy hh:mm:ss');
        this.driveTargetList[i].actionTargetDate = this.datePipe.transform(this.driveTargetList[i].actionTargetDate, 'dd-MM-yyyy hh:mm:ss');
        this.driveTargetList[i].actionCompletedDate = this.datePipe.transform(this.driveTargetList[i].actionCompletedDate, 'dd-MM-yyyy hh:mm:ss');
        driveTarget.push(this.driveTargetList[i]);
      }

      // this.dataSource = new MatTableDataSource(driveTarget);
      // this.dataSource.paginator = this.paginator;
      // this.dataSource.sort = this.sort;
      this.filterData.gridData = driveTarget;
      this.dataSource = new MatTableDataSource(driveTarget);
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
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.FAILURES.DELETE,id).subscribe(data => {
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted Failure Analysis Successfully");
          this.getFailureAnalysisData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Failure Analysis Deletion Failed.");
        })
      }
      this.confirmDialogRef = null;
    });
  }
  ViewData(data){
    var result = {
      'title':'Failure Analysis',
      'dataSource':[{label:'Reported',value:data.reported},{label:'Division',value:data.div},
      {label:'ReportDescription',value:data.reportDescription},{label:'Repurcussion',value:data.repurcussion},
      {label:'Date',value:data.date},{label:'Failure scetion',value:data.section},{label:'AssetType',value:data.assetType},
      {label:'AssetId',value:data.assetId},{label:'SubAssetType',value:data.subAssetType},{label:'SubAssetId',value:data.subAssetId},
      {label:'Make',value:data.make},{label:'Model',value:data.model},{label:'RootCause',value:data.rootCause},      
      {label:'ActionPlan',value:data.actionPlan},{label:'ActionStatus',value:data.actionStatus},
      {label:'ApprovedBy',value:data.approvedBy},{label:'ActionTargetDate',value:data.actionTargetDate},
      {label:'ActionCompletedDate',value:data.actionCompletedDate},
      {label:'ActionDescription',value:data.actionDescription}
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
