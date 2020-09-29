import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { StipulationstModel } from 'src/app/models/drive.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FilesInformationDialogComponent } from '../file-information-dialog/file-information-dialog.component';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-ash-display',
  templateUrl: './ash-display.component.html',
  styleUrls: ['./ash-display.component.css']
})
export class AshDisplayComponent implements OnInit {


  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  exactDate: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  filterData;
  facilityList: any;
  selectedExactDate = new Date();
  selectedBWFrom = new Date();
  selectedBWTo = new Date();
  minDate: Date;
  maxDate: Date;
  //'createdStamp', 'createdTxStamp', , 'lastUpdatedStamp', 'lastUpdatedTxStamp''deviceCreatedStamp','deviceLastUpdatedStamp',
  //'sno', 'id', 'deviceId',, 'pbOperationSeqId', 'remarks','initialOfIncharge','detailsOfMaint', 'dataDiv',  'createdBy',
  //   'doneBy','seqId',  'facilityId',
  displayedColumns = ['sno', 'assetId', 'assetType', 'depo', 'scheduleCode', 'scheduleDate', 'status','measureEntry','actions'];


  dataSource: MatTableDataSource<StipulationstModel>;
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
  gridData = [];
  stipulationsList: any;
  fileInformationDialogRef: MatDialogRef<FilesInformationDialogComponent>;
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
    var permissionName = this.commonService.getPermissionNameByLoggedData("INSPECTIONS", "STIPULATIONS");
    this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();
    this.findAshData();
    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        //{ "Key": 'id', "Value": " " },
        { "Key": 'assetId', "Value": " " },
        { "Key": 'assetType', "Value": " " },
        // { "Key": 'createdBy', "Value": " " },
        //{ "Key": 'createdStamp', "Value": "" },
        //{ "Key": 'createdTxStamp', "Value": " " },
        // { "Key": 'dataDiv', "Value": " " },
        // { "Key": 'detailsOfMaint', "Value": "" },
        //{ "Key": 'deviceCreatedStamp', "Value": " " },
        //{ "Key": 'deviceId', "Value": " " },
        //{ "Key": 'deviceLastUpdatedStamp', "Value": "" },
        // { "Key": 'doneBy', "Value": " " },
        { "Key": 'facilityId', "Value": " " },
        //{ "Key": 'initialOfIncharge', "Value": "" },
        //{ "Key": 'lastUpdatedStamp', "Value": " " },
        //{ "Key": 'lastUpdatedTxStamp', "Value": " " },
        // { "Key": 'pbOperationSeqId', "Value": "" },
        //{ "Key": 'remarks', "Value": " " },
        { "Key": 'scheduleCode', "Value": " " },
        { "Key": 'scheduleDate', "Value": " " },
        // { "Key": 'seqId', "Value": " " },
        { "Key": 'status', "Value": " " },
        { "Key": 'depo', "Value": " " },
      ],
      gridData: this.gridData,
      dataSource: this.dataSource,
      paginator: this.paginator,
      sort: this.sort
    };
  }

  findAshData() {
    const stipulations: StipulationstModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.ASH.ASH.GET_ASH_DEPO).subscribe((data) => {

      this.stipulationsList = data;

      for (let i = 0; i < this.stipulationsList.length; i++) {
        this.stipulationsList[i].sno = i + 1;
        stipulations.push(this.stipulationsList[i]);
      }
     // console.log("stipulations:::" + stipulations);
      this.refreshDatasource(stipulations);
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }

  refreshDatasource(stipulations){
    this.filterData.gridData = stipulations;
    this.dataSource = new MatTableDataSource(stipulations);
    this.commonService.updateDataSource(this.dataSource, this.displayedColumns);
    this.filterData.dataSource = this.dataSource;
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  updatePagination() {
    this.filterData.dataSource = this.filterData.dataSource;
    this.filterData.dataSource.paginator = this.paginator;
  }
  processEditAction(id) {
    this.router.navigate([id], { relativeTo: this.route });
  }
  processEntryAction(id) {
    this.router.navigate(["entry/"+id], { relativeTo: this.route });
  }
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.filterData.dataSource.filter = filterValue;
  }
  delete(id) {
    this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
      disableClose: false
    });
    this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete?';
    this.confirmDialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.spinnerService.show();
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.ASH.ASH.DELETE_ASH, id).subscribe(data => {
          console.log(JSON.stringify(data));
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted Ash Successfully");
          this.findAshData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Ash Deletion Failed.");
        })
      }
      this.confirmDialogRef = null;
    });
  }

  filesInfor: any;
  viewFilesDetails(id) {
    this.spinnerService.show();
    localStorage.setItem('driveFileType', 'stipulation');
    localStorage.setItem('driveFileTypeId', id);
    this.sendAndRequestService.requestForGET(Constants.app_urls.INSPECTIONS.STIPULATION.GET_INSPECTION_AND_STIPULATION_ID + id).subscribe((response) => {
      this.filesInfor = response;
      this.spinnerService.hide();
      this.fileInformationDialogRef = this.dialog.open(FilesInformationDialogComponent, {
        disableClose: false,
        height: '600px',
        width: '80%',
        data: this.filesInfor,
      });
    }, error => this.commonService.showAlertMessage(error));


  }
  exactDateEvent($event) {
    this.selectedExactDate = new Date($event.value);
  }
  bwFromDateEvent($event) {
    this.selectedBWFrom = new Date($event.value);
    this.selectedBWTo = this.maxDate;
    console.log("this.selectedBWFrom:::" + this.selectedBWFrom);
    this.filteredByDates();
  }
  bwToDateEvent($event) {
    this.selectedBWTo = new Date($event.value);
    console.log("this.selectedBWTo:::" + this.selectedBWTo);
    this.filteredByDates();
  }
  makeQuery() {
    this.filteredByDates();
  }

  filteredByDates(){
    const stipulations: StipulationstModel[] = [];
    let from = this.datePipe.transform(this.selectedBWFrom, 'yyyy-MM-dd hh:mm:ss');
    let to = this.datePipe.transform(this.selectedBWTo, 'yyyy-MM-dd hh:mm:ss');
    for (let i = 0; i < this.stipulationsList.length; i++) {
      if (this.stipulationsList[i].scheduleDate >= from && this.stipulationsList[i].scheduleDate <= to) {
        this.stipulationsList[i].sno = i + 1;
        stipulations.push(this.stipulationsList[i]);
      }
    }
    this.refreshDatasource(stipulations);
  }
}