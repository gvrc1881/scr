import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { ElectrificationTargetstModel } from 'src/app/models/drive.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog} from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { MatDatepickerInputEvent } from '@angular/material';
import { Constants } from 'src/app/common/constants';
import { DatePipe } from '@angular/common';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';


 
@Component({
  selector: 'app-drive-electrification-targets',
  templateUrl: './drive-electrification-targets.component.html',
  styleUrls: ['./drive-electrification-targets.component.css']
})
export class DriveElectrificationTargetsComponent implements OnInit {

  pagination =Constants.PAGINATION_NUMBERS;
FiledLabels = FieldLabelsConstant.LABELS;
 Titles = FieldLabelsConstant.TITLE;
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  displayedColumns = ['sno', 'section', 'guage', 'targetDate', 'status', 'division', 'executionAgency',
    'TKM', 'RKM', 'crsInspection', 'crsAuthorisation', 'targetSetBy', 'doublingTrippling', 'state', 'phase',
    'proposalScheme', 'sanctionByBoard', 'yearOfSanction', 'dateOfCompletion', 'actions'];
  dataSource: MatTableDataSource<ElectrificationTargetstModel>;


  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;

  electrificationTargetsList: any;

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
    var permissionName = this.commonService.getPermissionNameByLoggedData("ASSET REGISTER","ELECTRIFICATION TARGETS") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();
    this.getElectrificationTargetsData();

  }
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }
  getElectrificationTargetsData() {
    const electrificationTargets: ElectrificationTargetstModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.ELECTRIFICATION_TARGETS.GET_ELECTRIFICATION_TARGETS).subscribe((data) => {
      this.electrificationTargetsList = data;
      for (let i = 0; i < this.electrificationTargetsList.length; i++) {
        this.electrificationTargetsList[i].sno = i + 1;
        this.electrificationTargetsList[i].TKM = this.electrificationTargetsList[i].tkm;
        this.electrificationTargetsList[i].RKM = this.electrificationTargetsList[i].rkm;
        this.electrificationTargetsList[i].targetDate = this.datePipe.transform(this.electrificationTargetsList[i].targetDate, 'dd-MM-yyyy');
        this.electrificationTargetsList[i].dateOfCompletion = this.datePipe.transform(this.electrificationTargetsList[i].dateOfCompletion, 'dd-MM-yyyy');
        electrificationTargets.push(this.electrificationTargetsList[i]);
      }

      this.dataSource = new MatTableDataSource(electrificationTargets);
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
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.DRIVE.ELECTRIFICATION_TARGETS.DELETE, id).subscribe(data => {
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted Electrification Targets Successfully");
          this.getElectrificationTargetsData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Electrification Targets Deletion Failed.");
        })
      }
      this.confirmDialogRef = null;
    });
  }
}
