import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource, MatDialogRef, MatDialog } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ComplianceModel } from 'src/app/models/foot-patrolling-inspection.model';
import { Router, ActivatedRoute } from '@angular/router';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { DataViewDialogComponent } from 'src/app/components/data-view-dialog/data-view-dialog.component';
import { FuseConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';
import { DatePipe } from '@angular/common';
import { ComplianceDocumentComponent } from '../../compliance-document-dialog/compliance-document-dialog.component';
@Component({
  selector: 'app-compliance-details',
  templateUrl: './compliance-details.component.html',
  styleUrls: []
})
export class ComplianceDetailsComponent implements OnInit {
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  displayedColumns = ['sno','status','action' , 'complianceBy' ,  'compliedDateTime' ,'document', 'actions'] ;

  dataSource: MatTableDataSource<ComplianceModel>;
  dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
  complianceDocumentDialogRef: MatDialogRef<ComplianceDocumentComponent>;
  gridData = [];
  filterData;
  
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
  complianceList: any;

  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private route: ActivatedRoute,
    public dialog: MatDialog,
    private datePipe: DatePipe,
    private sendAndRequestService:SendAndRequestService
  ) { }

  ngOnInit() {
    var permissionName = this.commonService.getPermissionNameByLoggedData("FP","FP Inspection") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();
    this.getComplianceData();

    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'status', "Value": " " },
        { "Key": 'action', "Value": " " },
        { "Key": 'complianceBy', "Value": " " },
        { "Key": 'compliedDateTime', "Value": " " },
        { "Key": 'document', "Value": "" },
      ],
      gridData: this.gridData,
      dataSource: this.dataSource,
      paginator: this.paginator,
      sort: this.sort
    };
  }
 

  complianceApplyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }
       updatePagination() {
        this.filterData.dataSource = this.filterData.dataSource;
        this.filterData.dataSource.paginator = this.paginator;
      }
  
  
  getComplianceData() {
    const compliances : ComplianceModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.COMPLIANCES.GET_COMPLIANCE).subscribe((data) => {
        this.complianceList = data;
        for (let i = 0; i < this.complianceList.length; i++) {
            this.complianceList[i].sno = i+1;
            this.complianceList[i].compliedDateTime = this.datePipe.transform(this.complianceList[i].compliedDateTime, 'dd-MM-yyyy hh:mm:ss');
            compliances.push(this.complianceList[i]);              
        }

        this.filterData.gridData = compliances;
        this.dataSource = new MatTableDataSource(compliances);
        this.commonService.updateDataSource(this.dataSource, this.displayedColumns);
        this.filterData.dataSource = this.dataSource;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        this.spinnerService.hide();
      }, error => {
        this.spinnerService.hide();
      });
    }

  complianceEdit(id:any) {
    this.router.navigate([id], { relativeTo: this.route });
  }
  complianceDelete(id) {
    this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
      disableClose: false
      
    });
    this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete?';
    this.confirmDialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.spinnerService.show();
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.DAILY_SUMMARY.COMPLIANCES.DELETE_COMPLIANCE, id).subscribe(response => {
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted compliance Successfully");
          this.getComplianceData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("compliance Deletion Failed.");
        })
      }
      this.confirmDialogRef = null;
    });
  }
  filesInfor: any;
  complianceFilesDetails(id) {
    this.spinnerService.show();
    localStorage.setItem('observationFileType', 'compliance');
    localStorage.setItem('observationFileTypeId', id);
    this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.OBSERVATION.GET_OBSERVATION_CONTENT_ID + id).subscribe((response) => {
      this.filesInfor = response;
      this.spinnerService.hide();
      this.complianceDocumentDialogRef = this.dialog.open(ComplianceDocumentComponent, {
        disableClose: false,
        height: '600px',
        width: '80%',
        data: this.filesInfor,
      });
    }, error => this.commonService.showAlertMessage(error));


  } 
}
