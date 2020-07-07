import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { StipulationstModel } from 'src/app/models/drive.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FilesInformationDialogComponent } from '../../file-information-dialog/file-information-dialog.component';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';

@Component({
  selector: 'app-drive-stipulation',
  templateUrl: './drive-stipulation.component.html',
  styleUrls: ['./drive-stipulation.component.css']
})
export class DriveStipulationComponent implements OnInit {

  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  filterData;
  displayedColumns = ['sno', 'stipulation', 'inspectionId', 'dateOfStipulation', 'dateComplied',
    'compliance', 'attachment', 'compliedBy', 'actions'];
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
  ) { }

  ngOnInit() {
     var permissionName = this.commonService.getPermissionNameByLoggedData("INSPECTIONS","STIPULATIONS") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();
    this.getStipulationData();
    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'stipulation', "Value": " " },
        { "Key": 'inspectionId', "Value": " " },
        { "Key": 'dateOfStipulation', "Value": " " },
        { "Key": 'dateComplied', "Value": " " },
        { "Key": 'compliance', "Value": "" },
        { "Key": 'attachment', "Value": " " },
        { "Key": 'compliedBy', "Value": " " },
      ],
      gridData: this.gridData,
      dataSource: this.dataSource,
      paginator: this.paginator,
      sort: this.sort
    };
  }

  getStipulationData() {
    const stipulations: StipulationstModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.INSPECTIONS.STIPULATION.GET_STIPULATION).subscribe((data) => {

      this.stipulationsList = data;
      for (let i = 0; i < this.stipulationsList.length; i++) {
        this.stipulationsList[i].sno = i + 1;
        stipulations.push(this.stipulationsList[i]);
      }
      this.filterData.gridData = stipulations;
      this.dataSource = new MatTableDataSource(stipulations);
      this.commonService.updateDataSource(this.dataSource, this.displayedColumns);
      this.filterData.dataSource = this.dataSource;
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }
  updatePagination() {
    this.filterData.dataSource = this.filterData.dataSource;
    this.filterData.dataSource.paginator = this.paginator;
  }
  processEditAction(id) {
    this.router.navigate([id], { relativeTo: this.route });
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
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.INSPECTIONS.STIPULATION.DELETE_STIPULATION , id).subscribe(data => {
          console.log(JSON.stringify(data));
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted Stipulation Successfully");
          this.getStipulationData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Inspection Stipulation Failed.");
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


}
