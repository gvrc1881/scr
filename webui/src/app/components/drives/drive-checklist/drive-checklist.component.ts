import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { DriveChecklistModel } from 'src/app/models/drive.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { DrivesService } from 'src/app/services/drives.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-drive-checklist',
  templateUrl: './drive-checklist.component.html',
  styleUrls: ['./drive-checklist.component.css']
})
export class DriveChecklistComponent implements OnInit {

  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  displayedColumns = ['sno', 'drive', 'measureActivityList', 'displayOrder', 'lowerLimit', 'upperLimit', 'active', 'actions'];
  dataSource: MatTableDataSource<DriveChecklistModel>;


  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;

  drivesCheckList: any;

  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private drivesService: DrivesService,
    private router: Router,
    private route: ActivatedRoute,
    public dialog: MatDialog,
  ) { }

  ngOnInit() {
    var permissionName = this.commonService.getPermissionNameByLoggedData("DRIVES","CHECKLIST") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();
    this.getDrivesData();

  }
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }
  getDrivesData() {
    const drive: DriveChecklistModel[] = [];
    this.drivesService.getDrivesCheckListData().subscribe((data) => {
      this.drivesCheckList = data;
      for (let i = 0; i < this.drivesCheckList.length; i++) {
        this.drivesCheckList[i].sno = i + 1;
        this.drivesCheckList[i].drive = this.drivesCheckList[i].driveId['name'];
        this.drivesCheckList[i].measureActivityList = this.drivesCheckList[i].activityId['activityName'];
        drive.push(this.drivesCheckList[i]);
      }

      this.dataSource = new MatTableDataSource(drive);
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
        this.drivesService.deleteDriveData(id).subscribe(data => {
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted Drive Successfully");
          this.getDrivesData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Drive Deletion Failed.");
        });
      }
      this.confirmDialogRef = null;
    });
  }

}
