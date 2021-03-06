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
  selector: 'app-failure-occurrence',
  templateUrl: './failure-occurrence-failure.component.html',
  styleUrls: ['./failure-occurrence-failure.component.css']
})
export class FailureOccurrenceComponent implements OnInit {

  pagination = Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(sessionStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  displayedColumns = ['sno', 'occurrence','trainNo','place', 'fromDateTime', 'thruDateTime',
    'duration','cbInternalFailure','cbExternalFailure', 'remarks', 'actions'];
  dataSource: MatTableDataSource<any>;
  dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;

  FailureOccurrenceFailList: any;
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
    this.getFailureOccurrenceFailureData();
    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'occurrence', "Value": " " },           
        { "Key": 'trainNo', "Value": " " },    
        { "Key": 'place', "Value": " " },
        { "Key": 'fromDateTime', "Value": " " },
        { "Key": 'thruDateTime', "Value": " " },
        { "Key": 'duration', "Value": " " },  
        { "Key": 'cbInternalFailure', "Value": " " },
        { "Key": 'cbExternalFailure', "Value": " " },
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
  getFailureOccurrenceFailureData() {
    const FailureOccurrenceFail: any[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.FAILURE_BY_TYPE_BASED_ON_DIVISION + Constants.FAILURE_TYPES.FAILURE_OCCURRENCE+'/'+this.userdata.username).subscribe((data) => {
      this.FailureOccurrenceFailList = data;
      console.log(this.FailureOccurrenceFailList)
      for (let i = 0; i < this.FailureOccurrenceFailList.length; i++) {
        this.FailureOccurrenceFailList[i].sno = i + 1;
         this.FailureOccurrenceFailList[i].fromDateTime = this.datePipe.transform(this.FailureOccurrenceFailList[i].fromDateTime, 'dd-MM-yyyy HH:mm:ss');
        this.FailureOccurrenceFailList[i].thruDateTime = this.datePipe.transform(this.FailureOccurrenceFailList[i].thruDateTime, 'dd-MM-yyyy HH:mm:ss');
        this.FailureOccurrenceFailList[i].divisionLocal=this.FailureOccurrenceFailList[i].divisionLocal == 'true' ? 'Local': 'Division',
        this.FailureOccurrenceFailList[i].internalExternal=this.FailureOccurrenceFailList[i].internalExternal == 'true' ? 'External': 'Internal',
        FailureOccurrenceFail.push(this.FailureOccurrenceFailList[i]);
      }

     

      this.filterData.gridData = FailureOccurrenceFail;
      this.dataSource = new MatTableDataSource(FailureOccurrenceFail);
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
          this.commonService.showAlertMessage("Deleted FailureOccurrence Fail Record Successfully");
          this.getFailureOccurrenceFailureData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("FailureOccurrence Fail Record Deletion Failed.");
        })
      }
      this.confirmDialogRef = null;
    });
  }
  ViewData(data){
    var result = {
      'title':this.Titles.FAILURE_OCCURENCE,
      'dataSource':[
        
  
      { label:FieldLabelsConstant.LABELS.OCCURENCE, value:data.occurrence },
      { label:FieldLabelsConstant.LABELS.TRAIN_NO, value:data.trainNo },
      { label:FieldLabelsConstant.LABELS.PLACE, value:data.place },
      { label:FieldLabelsConstant.LABELS.FAILURE_FROM_DATE, value:data.fromDateTime },
      { label:FieldLabelsConstant.LABELS.FAILURE_TO_DATE, value:data.thruDateTime },
      { label:FieldLabelsConstant.LABELS.DURATION, value:data.duration },
      { label:FieldLabelsConstant.LABELS.INTERNAL, value:data.cbInternalFailure },
      { label:FieldLabelsConstant.LABELS.EXTERNAL, value:data.cbExternalFailure },
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
