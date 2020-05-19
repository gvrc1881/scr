import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { DriveDailyProgressModel } from 'src/app/models/drive.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { DrivesService } from 'src/app/services/drives.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-drive-progress-record',
  templateUrl: './drive-progress-record.component.html',
  styleUrls: ['./drive-progress-record.component.css']
})
export class DriveProgressRecordComponent implements OnInit {

  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  displayedColumns = ['sno', 'activityId', 'performedDate', 'division', 'depot', 'section', 'performedCount', 'supervisor', 'driveId', 'actions'];
  dataSource: MatTableDataSource<DriveDailyProgressModel>;


  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;

  driveTargetList: any;

  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private drivesService: DrivesService,
    private router: Router,
    private route: ActivatedRoute,
    public dialog: MatDialog,
  ) { }

  ngOnInit() {
    this.addPermission = this.commonService.getPermission("Add");
    this.editPermission = this.commonService.getPermission("Edit");
    this.deletePermission = this.commonService.getPermission("Delete");

    this.spinnerService.show();
    this.getDriveDailyProgressData();

  }
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }
  getDriveDailyProgressData() {
    const driveTarget: DriveDailyProgressModel[] = [];
    this.drivesService.getDriveDailyProgressData().subscribe((data) => {
      this.driveTargetList = data;
      for (let i = 0; i < this.driveTargetList.length; i++) {
        this.driveTargetList[i].sno = i + 1;
        this.driveTargetList[i].driveId = this.driveTargetList[i].driveId['name'];
        driveTarget.push(this.driveTargetList[i]);
      }

      this.dataSource = new MatTableDataSource(driveTarget);
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
        this.drivesService.deleteDriveDailyProgressData(id).subscribe(data => {
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted Drive Daily Progress Successfully");
          this.getDriveDailyProgressData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Drive Drive Daily Progress Failed.");
        })
      }
      this.confirmDialogRef = null;
    });
  }


}
