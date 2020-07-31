import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { Constants } from 'src/app/common/constants';
import { MatDialogRef, MatTableDataSource, MatPaginator, MatSort, MatDialog } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Router, ActivatedRoute } from '@angular/router';

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
    'duration', 'remarks','divisionLocal','internalExternal', 'actions'];
  dataSource: MatTableDataSource<any>;


  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;

  UnusualOccurrenceFailList: any;

  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private sendAndRequestService: SendAndRequestService,
    private router: Router,
    private route: ActivatedRoute,
    public dialog: MatDialog,
  ) { }

  ngOnInit() {
    var permissionName = this.commonService.getPermissionNameByLoggedData("FAILURES","Unusual Occurrence Failure") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();
    this.getUnusualOccurrenceFailureData();

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
        UnusualOccurrenceFail.push(this.UnusualOccurrenceFailList[i]);
      }

      this.dataSource = new MatTableDataSource(UnusualOccurrenceFail);
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
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.FAILURES.DELETE, id).subscribe(data => {
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
}
