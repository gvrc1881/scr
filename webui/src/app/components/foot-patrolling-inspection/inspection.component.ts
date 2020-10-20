import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource, MatDialogRef, MatDialog } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { FootPatrollingInspectionModel,ObservationModel,ComplianceModel } from 'src/app/models/foot-patrolling-inspection.model';
import { Router, ActivatedRoute } from '@angular/router';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { DataViewDialogComponent } from 'src/app/components/data-view-dialog/data-view-dialog.component';
import { FuseConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';
import { DatePipe } from '@angular/common';
import { InspectionDocumentComponent } from '../inpection-document-dialog/inspection-document-dialog.component';
import { ComplianceDocumentComponent } from '../compliance-document-dialog/compliance-document-dialog.component';
@Component({
  selector: 'app-inspection',
  templateUrl: './inspection.component.html',
  styleUrls: []
})
export class InspectionComponent implements OnInit {
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  displayedColumns  = ['sno' ,'facilityId','inspectionType' , 'section' , 'inspectionBy' , 'startTime' , 'stopTime' , 'actions','observation'] ;
  observationDisplayColumns = ['sno' ,'location','observationCategory' , 'observationItem' ,  'description' ,'actionRequired','attachment','actions','compliance'] ;
  complianceDisplayColumns = ['sno','status','action' , 'complianceBy' ,  'compliedDateTime' ,'attachment', 'actions'] ;

  dataSource: MatTableDataSource<FootPatrollingInspectionModel>;
  observationDataSource: MatTableDataSource<ObservationModel>;
  complianceDataSource: MatTableDataSource<ComplianceModel>;
  dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
  inspectionDocumentDialogRef: MatDialogRef<InspectionDocumentComponent>;
  complianceDocumentDialogRef: MatDialogRef<ComplianceDocumentComponent>;
  gridData = [];
  filterData;
  facilityList: any;
  facilityData:any;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
  fpInspectionsList: any;

  @ViewChild(MatPaginator, { static: true }) observationPaginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) observationSort: MatSort;
  @ViewChild('filter', { static: true }) observationFilter: ElementRef;
  observationList: any;

  @ViewChild(MatPaginator, { static: true }) compliancePaginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) complianceSort: MatSort;
  @ViewChild('filter', { static: true }) complianceFilter: ElementRef;
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
    this.depotTypeForOhe();

    this.getFpInspectionsData();

    this.getObservationData();
    this.getComplianceData();

    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'facilityId', "Value": " " },
        { "Key": 'inspectionType', "Value": " " },
        { "Key": 'section', "Value": " " },
        { "Key": 'inspectionBy', "Value": " " },
        { "Key": 'startTime', "Value": "" },
        { "Key": 'stopTime', "Value": " " },
      ],
      gridData: this.gridData,
      dataSource: this.dataSource,
      paginator: this.paginator,
      sort: this.sort
    };
  }
  fpInspectionApplyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }

  ObservationApplyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.observationDataSource.filter = filterValue;
  }

  complianceApplyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.complianceDataSource.filter = filterValue;
  }
  depotTypeForOhe()
        {  
               this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_DEPOTTYPE_FOR_OHE).subscribe((data) => {
                 this.facilityList = data;
        }
               );

       }
       updatePagination() {
        this.filterData.dataSource = this.filterData.dataSource;
        this.filterData.dataSource.paginator = this.paginator;
      }
  getFpInspectionsData() {
    const footPatrollingInspection : FootPatrollingInspectionModel[] = [];
        this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.FP_INSPECTION.GET_FP_INSPECTION).subscribe((data) => {
            this.fpInspectionsList = data;
            for (let i = 0; i < this.fpInspectionsList.length; i++) {
                this.fpInspectionsList[i].sno = i+1;
                this.fpInspectionsList[i].startTime = this.datePipe.transform(this.fpInspectionsList[i].startTime, 'dd-MM-yyyy hh:mm:ss');
                this.fpInspectionsList[i].stopTime = this.datePipe.transform(this.fpInspectionsList[i].stopTime, 'dd-MM-yyyy hh:mm:ss');
                this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY+JSON.stringify(this.fpInspectionsList[i].facilityId)).subscribe((data) => {
		        this.spinnerService.hide();
	    	    this.facilityData = data;
	        	this.fpInspectionsList[i].facilityId = this.facilityData.facilityName;
	        });
                footPatrollingInspection.push(this.fpInspectionsList[i]);              
            }

            this.filterData.gridData = footPatrollingInspection;
            this.dataSource = new MatTableDataSource(footPatrollingInspection);
            this.commonService.updateDataSource(this.dataSource, this.displayedColumns);
            this.filterData.dataSource = this.dataSource;
            this.dataSource.paginator = this.paginator;
            this.dataSource.sort = this.sort;
            this.spinnerService.hide();
          }, error => {
            this.spinnerService.hide();
          });
        }

  getObservationData() {
    const observation: ObservationModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.OBSERVATION.GET_OBSERVATION).subscribe((data) => {
      this.observationList = data;
                for (let i = 0; i < this.observationList.length; i++) {
                    this.observationList[i].sno = i+1;
                    observation.push(this.observationList[i]);              
                }
      this.observationDataSource = new MatTableDataSource(observation);
      this.observationDataSource.paginator = this.observationPaginator;
      this.observationDataSource.sort = this.observationSort;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
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

      this.complianceDataSource = new MatTableDataSource(compliances);
      this.complianceDataSource.paginator = this.compliancePaginator;
      this.complianceDataSource.sort = this.complianceSort;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });

  }

  processEditAction(id) {
    this.router.navigate(['inspection/' + id], { relativeTo: this.route });
  }
  delete(id) {
    this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
      disableClose: false
      
    });
    this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete?';
    this.confirmDialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.spinnerService.show();
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.DAILY_SUMMARY.FP_INSPECTION.DELETE_FP_INSPECTION, id).subscribe(response => {
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted Fp Inspection Successfully");
          this.getFpInspectionsData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Fp Inspection Deletion Failed.");
        })
      }
      this.confirmDialogRef = null;
    });
  }

  observationEdit(id) {
    this.router.navigate(['observation/' + id], { relativeTo: this.route });
  }
  insIdRelatedToObser(id) {
    this.router.navigate(['add-observation/' + id], { relativeTo: this.route });
  }
  observationDelete(id) {
    this.spinnerService.show();
    this.sendAndRequestService.requestForDELETE(Constants.app_urls.DAILY_SUMMARY.OBSERVATION.DELETE_OBSERVATION, id).subscribe(response => {
      this.spinnerService.hide();
      this.commonService.showAlertMessage("Deleted Observation Successfully");
      this.getObservationData();
    }, error => {
      console.log('ERROR >>>');
      this.spinnerService.hide();
      this.commonService.showAlertMessage("Observation Deletion Failed.");
    })
  }

  complianceEdit(id) {
    this.router.navigate(['compliance/' + id], { relativeTo: this.route });
  }
  obsIdRelatedToCompliance(id) {
    this.router.navigate(['add-compliance/' + id], { relativeTo: this.route });
  }
  complianceDelete(id) {
    this.spinnerService.show();
    this.sendAndRequestService.requestForDELETE(Constants.app_urls.DAILY_SUMMARY.COMPLIANCES.DELETE_COMPLIANCE, id).subscribe(response => {
      this.spinnerService.hide();
      this.commonService.showAlertMessage("Deleted Drive compliance Successfully");
      this.getComplianceData();
    }, error => {
      console.log('ERROR >>>');
      this.spinnerService.hide();
      this.commonService.showAlertMessage("compliance Deletion Failed.");
    })
  }
  filesInfor: any;
  viewFilesDetails(id) {
    this.spinnerService.show();
    localStorage.setItem('observationFileType', 'observation');
    localStorage.setItem('observationFileTypeId', id);
    this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.OBSERVATION.GET_OBSERVATION_CONTENT_ID+ id).subscribe((response) => {
      this.filesInfor = response;
      this.spinnerService.hide();
      this.inspectionDocumentDialogRef = this.dialog.open(InspectionDocumentComponent, {
        disableClose: false,
        height: '600px',
        width: '80%',
        data: this.filesInfor,
      });
    }, error => this.commonService.showAlertMessage(error));


  }
  ComplianceViewFilesDetails(id) {
    this.spinnerService.show();
    localStorage.setItem('complianceFileType', 'compliance');
    localStorage.setItem('complianceFileTypeId', id);
    this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.OBSERVATION.GET_OBSERVATION_CONTENT_ID+ id).subscribe((response) => {
      this.filesInfor = response;
      this.spinnerService.hide();
      this.inspectionDocumentDialogRef = this.dialog.open(InspectionDocumentComponent, {
        disableClose: false,
        height: '600px',
        width: '80%',
        data: this.filesInfor,
      });
    }, error => this.commonService.showAlertMessage(error));


  }
 
}
