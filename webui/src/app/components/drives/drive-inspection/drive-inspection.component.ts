import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatDialog, MatDialogRef } from '@angular/material';
import { InspectionstModel } from 'src/app/models/drive.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { DrivesService } from 'src/app/services/drives.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FilesInformationDialogComponent } from '../../file-information-dialog/file-information-dialog.component';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-drive-inspection',
  templateUrl: './drive-inspection.component.html',
  styleUrls: ['./drive-inspection.component.css']
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

  fileInformationDialogRef: MatDialogRef<FilesInformationDialogComponent>;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;

  inspectionsList: any;

  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private drivesService: DrivesService,
    private router: Router,
    private route: ActivatedRoute,
    public dialog: MatDialog,
  ) { }

  ngOnInit() {
    var permissionName = this.commonService.getPermissionNameByLoggedData("INSPECTIONS","CRS EIG INSPECTIONS") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();
    this.getInspectionData();
  }
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }
  getInspectionData() {
    const inspections: InspectionstModel[] = [];
    this.drivesService.getInspectionData().subscribe((data) => {
      this.inspectionsList = data;
      for (let i = 0; i < this.inspectionsList.length; i++) {
        this.inspectionsList[i].sno = i + 1;
        this.inspectionsList[i].TKM = this.inspectionsList[i].tkm;
        this.inspectionsList[i].RKM = this.inspectionsList[i].rkm;
        inspections.push(this.inspectionsList[i]);
      }

      this.dataSource = new MatTableDataSource(inspections);
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
        this.drivesService.deleteInspectionsData(id).subscribe(data => {
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
    this.drivesService.findStipulationAndInspectionDataById(id).subscribe((response) => {
      this.filesInfor = response;
      localStorage.setItem('driveFileType', 'inspection');
      localStorage.setItem('driveFileTypeId', id);
     /*  var data = [];
      if (this.filesInfor.attachment != '') {
        data = this.filesInfor.attachment.split(',');
      } */
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
