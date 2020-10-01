import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatDialog, MatDialogRef } from '@angular/material';
import { InspectionstModel } from 'src/app/models/drive.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FilesInformationDialogComponent } from '../../file-information-dialog/file-information-dialog.component';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { DatePipe } from '@angular/common';
import { DataViewDialogComponent } from 'src/app/components/data-view-dialog/data-view-dialog.component';


@Component({
  selector: 'app-drive-inspection',
  templateUrl: './drive-inspection.component.html',
  styleUrls: []
})
export class DriveInspectionComponent implements OnInit {

  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  displayedColumns = ['sno', 'inspectionType', 'section', 'sectionStartLocation', 'sectionEndLocation',
    'dateOfInspection', 'RKM', 'TKM', 'remarks', 'authorisationDate', 'chargingDate', 'attachment',
    'station', 'actions'];
  dataSource: MatTableDataSource<InspectionstModel>;
  filterData;
  fileInformationDialogRef: MatDialogRef<FilesInformationDialogComponent>;
  dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
  gridData = [];
  inspectionsList: any;

  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private sendandRequestService:SendAndRequestService,
    private router: Router,
    private route: ActivatedRoute,
    public dialog: MatDialog,
    private datePipe: DatePipe
  ) { }

  ngOnInit() {
    var permissionName = this.commonService.getPermissionNameByLoggedData("INSPECTIONS","CRS EIG INSPECTIONS") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();
    this.getInspectionData();
    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'inspectionType', "Value": " " },
        { "Key": 'section', "Value": " " },
        { "Key": 'sectionStartLocation', "Value": " " },
        { "Key": 'sectionEndLocation', "Value": " " },
        { "Key": 'dateOfInspection', "Value": "" },
        { "Key": 'RKM', "Value": " " },
        { "Key": 'TKM', "Value": " " },
        { "Key": 'remarks', "Value": " " },
        { "Key": 'authorisationDate', "Value": " " },
        { "Key": 'chargingDate', "Value": " " },
        { "Key": 'attachment', "Value": " " },
        { "Key": 'station', "Value": " " },
        { "Key": 'action', "Value": " " },
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
    this.filterData.dataSource.filter = filterValue;
  }
  updatePagination() {
    this.filterData.dataSource = this.filterData.dataSource;
    this.filterData.dataSource.paginator = this.paginator;
  }
  getInspectionData() {
    const inspections: InspectionstModel[] = [];
    this.sendandRequestService.requestForGET(Constants.app_urls.INSPECTIONS.INSPECTIONS.GET_INSPECTIONS).subscribe((data) => {
      this.inspectionsList = data;
      for (let i = 0; i < this.inspectionsList.length; i++) {
        this.inspectionsList[i].sno = i + 1;
        this.inspectionsList[i].TKM = this.inspectionsList[i].tkm;
        this.inspectionsList[i].RKM = this.inspectionsList[i].rkm;
        this.inspectionsList[i].dateOfInspection = this.datePipe.transform(this.inspectionsList[i].dateOfInspection, 'dd-MM-yyyy hh:mm:ss');
        this.inspectionsList[i].authorisationDate = this.datePipe.transform(this.inspectionsList[i].authorisationDate, 'dd-MM-yyyy hh:mm:ss');
        this.inspectionsList[i].chargingDate = this.datePipe.transform(this.inspectionsList[i].chargingDate, 'dd-MM-yyyy hh:mm:ss');
        inspections.push(this.inspectionsList[i]);
      }
      this.filterData.gridData = inspections;
      this.dataSource = new MatTableDataSource(inspections);
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
        this.sendandRequestService.requestForDELETE(Constants.app_urls.INSPECTIONS.INSPECTIONS.DELETE, id).subscribe(data => {
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted Inspection Successfully");
          this.getInspectionData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Inspection Deletion Failed.");
        })
      }
      this.confirmDialogRef = null;
    });
  }

  filesInfor: any;
  viewFilesDetails(id) {
    this.spinnerService.show();
    this.sendandRequestService.requestForGET(Constants.app_urls.INSPECTIONS.STIPULATION.GET_INSPECTION_AND_STIPULATION_ID + id).subscribe((response) => {
      this.filesInfor = response;
      localStorage.setItem('driveFileType', 'inspection');
      localStorage.setItem('driveFileTypeId', id);     
      this.spinnerService.hide();
      this.fileInformationDialogRef = this.dialog.open(FilesInformationDialogComponent, {
        disableClose: false,
        height: '600px',
        width: '80%',
        data: this.filesInfor,
      });
    }, error => this.commonService.showAlertMessage(error));


  }

}
