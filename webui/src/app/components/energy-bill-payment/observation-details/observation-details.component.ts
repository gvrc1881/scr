import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource, MatDialogRef, MatDialog } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ObservationModel } from 'src/app/models/foot-patrolling-inspection.model';
import { Router, ActivatedRoute } from '@angular/router';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { DataViewDialogComponent } from 'src/app/components/data-view-dialog/data-view-dialog.component';
import { FuseConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';
import { InspectionDocumentComponent } from '../../inpection-document-dialog/inspection-document-dialog.component';
@Component({
  selector: 'app-observation-details',
  templateUrl: './observation-details.component.html',
  styleUrls: []
})
export class ObservationDetailsComponent implements OnInit {
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  
  displayedColumns = ['sno' ,'location','observationCategory' , 'observationItem' ,  'description' ,'actionRequired','attachment','actions'] ;

  dataSource: MatTableDataSource<ObservationModel>;  
  dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
  inspectionDocumentDialogRef: MatDialogRef<InspectionDocumentComponent>;
  gridData = [];
  filterData;
  facilityList: any;
  facilityData:any;
  

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
  observationList: any;

  

  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private route: ActivatedRoute,
    public dialog: MatDialog,
    private sendAndRequestService:SendAndRequestService
  ) { }

  ngOnInit() {
    var permissionName = this.commonService.getPermissionNameByLoggedData("FP","FP Inspection") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();


    this.getObservationData();
    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'location', "Value": " " },
        { "Key": 'observationCategory', "Value": " " },
        { "Key": 'observationItem', "Value": " " },
        { "Key": 'description', "Value": " " },
        { "Key": 'actionRequired', "Value": "" },
        { "Key": 'attachment', "Value": "" },

      ],
      gridData: this.gridData,
      dataSource: this.dataSource,
      paginator: this.paginator,
      sort: this.sort
    };

    
  }
  
  ObservationApplyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }

  updatePagination() {
    this.filterData.dataSource = this.filterData.dataSource;
    this.filterData.dataSource.paginator = this.paginator;
  }
  

  getObservationData() {
    const observation: ObservationModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.OBSERVATION.GET_OBSERVATION).subscribe((data) => {
      this.observationList = data;
                for (let i = 0; i < this.observationList.length; i++) {
                    this.observationList[i].sno = i+1;
                    observation.push(this.observationList[i]);              
                }
                this.filterData.gridData = observation;
                this.dataSource = new MatTableDataSource(observation);
                this.commonService.updateDataSource(this.dataSource, this.displayedColumns);
                this.filterData.dataSource = this.dataSource;
                this.dataSource.paginator = this.paginator;
                this.dataSource.sort = this.sort;
                this.spinnerService.hide();
              }, error => {
                this.spinnerService.hide();
              });
            }

  
  

  observationEdit(id) {
    this.router.navigate([id], { relativeTo: this.route });
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

  
  filesInfor: any;
  viewFilesDetails(id) {
    this.spinnerService.show();
    localStorage.setItem('observationFileType', 'observation');
    localStorage.setItem('observationFileTypeId', id);
    this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.OBSERVATION.GET_OBSERVATION_CONTENT_ID + id).subscribe((response) => {
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
