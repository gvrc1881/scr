import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource, MatDialogRef, MatDialog } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { AssistanceModel } from 'src/app/models/assistance.model';
import { Router, ActivatedRoute } from '@angular/router';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { DataViewDialogComponent } from 'src/app/components/data-view-dialog/data-view-dialog.component';
import { FuseConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';
import { AssistanceDocumentComponent } from '../assistance-document-dialog/assistance-document-dialog.component';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { DatePipe } from '@angular/common';


@Component({
  selector: 'app-assistance',
  templateUrl: './assistance.component.html'
})
export class AssistanceComponent implements OnInit {
  

  pagination = Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  
  displayedColumns = ['sno' ,'typeOfAssistance','requestTo','assistance' , 'requestedDate' ,  'responseDate' ,'responseBy','attachment','actions'] ;

  dataSource: MatTableDataSource<AssistanceModel>;  
  dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
  assistanceDocumentDialogRef: MatDialogRef<AssistanceDocumentComponent>;
  gridData = [];
  filterData;
  facilityList: any;
  facilityData:any;
  

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
  assistanceList: any;

  

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


    this.getAssistanceData();
    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'typeOfAssistance', "Value": " " },
        { "Key": 'requestTo', "Value": " " },
        { "Key": 'assistance', "Value": " " },
        { "Key": 'requestedDate', "Value": " " },
        { "Key": 'responseDate', "Value": " " },
        { "Key": 'responseBy', "Value": "" },
        { "Key": 'attachment', "Value": "" },

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
    this.dataSource.filter = filterValue;
  }

  updatePagination() {
    this.filterData.dataSource = this.filterData.dataSource;
    this.filterData.dataSource.paginator = this.paginator;
  }
  

  getAssistanceData() {
    const assistance: AssistanceModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.PROJECT_ADMIN.ASSISTANCE.GET_ASSISTANCE).subscribe((data) => {
      this.assistanceList = data;
      for (let i = 0; i < this.assistanceList.length; i++) {
          this.assistanceList[i].sno = i+1;
          this.assistanceList[i].requestedDate = this.datePipe.transform(this.assistanceList[i].requestedDate, 'dd-MM-yyyy hh:mm:ss');
          this.assistanceList[i].responseDate = this.datePipe.transform(this.assistanceList[i].responseDate, 'dd-MM-yyyy hh:mm:ss');
          assistance.push(this.assistanceList[i]);              
      }

      this.filterData.gridData = assistance;
      this.dataSource = new MatTableDataSource(assistance);
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
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.PROJECT_ADMIN.ASSISTANCE.DELETE_ASSISTANCE, id).subscribe(response => {
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted Assistance Successfully");
          this.getAssistanceData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Assistance Deletion Failed.");
        })
      }
      this.confirmDialogRef = null;
    });
  }
  
  filesInfor: any;
  viewFilesDetails(id) {
    this.spinnerService.show();
    localStorage.setItem('assistanceFileType', 'assistance');
    localStorage.setItem('assistanceFileTypeId', id);
    this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.OBSERVATION.GET_OBSERVATION_CONTENT_ID + id).subscribe((response) => {
      this.filesInfor = response;
      this.spinnerService.hide();
      this.assistanceDocumentDialogRef = this.dialog.open(AssistanceDocumentComponent, {
        disableClose: false,
        height: '600px',
        width: '80%',
        data: this.filesInfor,
      });
    }, error => this.commonService.showAlertMessage(error));

  } 
  
}
