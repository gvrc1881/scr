import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { FailureAnalysisModel } from 'src/app/models/drive.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { DatePipe } from '@angular/common';
import { DataViewDialogComponent } from 'src/app/components/data-view-dialog/data-view-dialog.component';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { DocumentDialogComponent } from '../../document-view-dialog/document-dialog.component';

@Component({
  selector: 'app-failure-analysis',
  templateUrl: './failure-analysis.component.html',
  styleUrls: ['./failure-analysis.component.scss']
})
export class FailureAnalysisComponent implements OnInit {
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  pagination=Constants.PAGINATION_NUMBERS;
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(sessionStorage.getItem('userData'));
  divisionHierarchy:any = JSON.parse(sessionStorage.getItem('divisionData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
  displayedColumns = ['sno', 'reported', 'div','date',
    'failureSection', 'assetType', 'assetId',
    'rootCause', 'actionPlan', 'actionStatus',  'actionCompletedDate',
     'actions'];
  dataSource: MatTableDataSource<FailureAnalysisModel>;
  filterData;
  gridData = [];
  divisionList:any;
  driveTargetList: any;
  contentCategoryList: any;
    contentTopicList: any;
    uploadFile: boolean = false;
    contentManagementFormGroup: FormGroup;
    selectedFiles: File[] = [];
    filesExists: boolean = false;
    failureAnalysisId: any;
    documentDialogRef:MatDialogRef<DocumentDialogComponent>;
    pattern = "[a-zA-Z][a-zA-Z ]*";

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
 

 

  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private sendAndRequestService: SendAndRequestService,
    private router: Router,
    private route: ActivatedRoute,
    public dialog: MatDialog,
    private datePipe: DatePipe,
    private formBuilder: FormBuilder,
  ) { }

  ngOnInit() {
    var permissionName = this.commonService.getPermissionNameByLoggedData("FAILURES","FAILURE ANALYSIS") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();
    this.getFailureAnalysisData();
    this.findDivisions();
    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'reported', "Value": " " },           
        { "Key": 'div', "Value": " " },
        { "Key": 'date', "Value": " " },
        { "Key": 'section', "Value": " " },
        { "Key": 'assetType', "Value": " " },
        { "Key": 'assetId', "Value": " " },
        { "Key": 'rootCause', "Value": " " },
        { "Key": 'actionPlan', "Value": " " },
        { "Key": 'actionStatus', "Value": " " },
        { "Key": 'actionTargetDate', "Value": " " },
        { "Key": 'actionCompletedDate', "Value": " " },
        { "Key": 'actionDescription', "Value": " " },
       
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

  findDivisions(){
   
    this.divisionList=[];    
    console.log("divison list=="+JSON.stringify(this.divisionHierarchy));

    for (let i = 0; i < this.divisionHierarchy.length; i++) {
        
           if( this.divisionHierarchy[i].depotType == 'DIV'){           
               
               this.divisionHierarchy.divisionList;
              
           }
        }
}
  getFailureAnalysisData() {
    const driveTarget: FailureAnalysisModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.GET_FAILURES_BASEDON_DIVISION+this.userdata.username
    ).subscribe((data) => {
      this.driveTargetList = data;
      for (let i = 0; i < this.driveTargetList.length; i++) {
        this.driveTargetList[i].sno = i + 1;
        this.driveTargetList[i].date = this.datePipe.transform(this.driveTargetList[i].date, 'dd-MM-yyyy HH:mm:ss');
        this.driveTargetList[i].actionTargetDate = this.datePipe.transform(this.driveTargetList[i].actionTargetDate, 'dd-MM-yyyy HH:mm:ss');
        this.driveTargetList[i].actionCompletedDate = this.datePipe.transform(this.driveTargetList[i].actionCompletedDate, 'dd-MM-yyyy HH:mm:ss');
        driveTarget.push(this.driveTargetList[i]);
      }

      // this.dataSource = new MatTableDataSource(driveTarget);
      // this.dataSource.paginator = this.paginator;
      // this.dataSource.sort = this.sort;
      this.filterData.gridData = driveTarget;
      this.dataSource = new MatTableDataSource(driveTarget);
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
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.FAILURES.DELETE,id).subscribe(data => {
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted Failure Analysis Successfully");
          this.getFailureAnalysisData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Failure Analysis Deletion Failed.");
        })
      }
      this.confirmDialogRef = null;
    });
  }
  ViewData(data){
    var result = {
      'title': FieldLabelsConstant.TITLE.FAILURE_ANALYSIS,
      'dataSource':[
        { label:FieldLabelsConstant.LABELS.REPORTED, value:data.reported},
        { label:FieldLabelsConstant.LABELS.DIVISION, value:data.div},
        { label:FieldLabelsConstant.LABELS.REPORT_DESCRIPTION, value:data.reportDescription},
        { label:FieldLabelsConstant.LABELS.REPERCUSSION, value:data.repurcussion},
        { label:FieldLabelsConstant.LABELS.DATE, value:data.date},
        { label:FieldLabelsConstant.LABELS.FAILURE_SECTION, value:data.section},
        { label:FieldLabelsConstant.LABELS.ASSET_TYPE, value:data.assetType},
        { label:FieldLabelsConstant.LABELS.ASSET_ID, value:data.assetId},
        { label:FieldLabelsConstant.LABELS.SUB_ASSET_TYPE, value:data.subAssetType},
        { label:FieldLabelsConstant.LABELS.SUB_ASSET_ID, value:data.subAssetId},
        { label:FieldLabelsConstant.LABELS.MAKE, value:data.make},
        { label:FieldLabelsConstant.LABELS.MODEL, value:data.model},
        { label:FieldLabelsConstant.LABELS.ROOT_CAUSE, value:data.rootCause},      
        { label:FieldLabelsConstant.LABELS.ACTION_PLAN, value:data.actionPlan},
        { label:FieldLabelsConstant.LABELS.ACTION_STATUS, value:data.actionStatus},
        { label:FieldLabelsConstant.LABELS.APPROVED_BY, value:data.approvedBy},
        { label:FieldLabelsConstant.LABELS.ACTION_TARGET_DATE, value:data.actionTargetDate},
        { label:FieldLabelsConstant.LABELS.ACTION_COMPLETED_DATE, value:data.actionCompletedDate},
        { label:FieldLabelsConstant.LABELS.ACTION_DESCRIPTION, value:data.actionDescription},
        { label:FieldLabelsConstant.LABELS.AVOIDABLE, value:data.avoidable},
        { label:FieldLabelsConstant.LABELS.REAMRK_BRIEF, value:data.remarkBrief},
        { label:FieldLabelsConstant.LABELS.REMARK_DETAILS, value:data.remarkDetails},

    ]
    }
    this.dataViewDialogRef = this.dialog.open(DataViewDialogComponent, {
      disableClose: false,
      height: '400px',
      width: '80%',       
      data:result,  
    });            
  }


viewDocumentDetails(id){
  this.spinnerService.show();    
  this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.FAILUREANALYSIS_ATTACHMENT_LIST + id).subscribe((response) => {     
    this.spinnerService.hide(); 
    this.documentDialogRef = this.dialog.open(DocumentDialogComponent, {
        disableClose: false,
        height: '600px',
        width: '80%',       
        data:response,       
      });            
  }, error => this.commonService.showAlertMessage(error));
}    

fileUpload(id) {
  this.uploadFile = true;
  this.addPermission = false;
  this.failureAnalysisId = id;
  this.contentManagementFormGroup = this.formBuilder.group({
        contentCategory: [''],
        description: ['', Validators.compose([Validators.required, Validators.pattern(this.pattern)])],
        uploadFiles: ['', Validators.required],
        contentTopic: [''],
    });
  }
    public get f() { return this.contentManagementFormGroup.controls; }
    
onContentManagementSubmit () {
  //console.log('*** guidence item id ***'+this.guidenceItemId);
  let category = this.contentManagementFormGroup.value.contentCategory;
    this.uploadFile = false;
  let saveDetails = {
    'failureAnalysisId': this.failureAnalysisId,
            'description': this.contentManagementFormGroup.value.description,
            'divisionCode': this.userdata.divisionCode,
            'createdBy': this.userdata.id,
            'createdDate': new Date(),
            'contentCategory': 'OPERATIONS',
            'zonal': 'zonal',
            'FU': 'division',
            'contentTopic': 'FAILURE_ANALYSIS',
      }
      let formdata: FormData = new FormData();
      for(var i=0;i<this.selectedFiles.length;i++){
          formdata.append('file', this.selectedFiles[i]);
      }
      formdata.append('failureAnalysisId', saveDetails.failureAnalysisId);
      formdata.append('contentCategory', saveDetails.contentCategory);
      formdata.append('description', saveDetails.description);
      formdata.append('divisionCode', saveDetails.divisionCode);
      formdata.append('createdBy', saveDetails.createdBy);
      formdata.append('zonal', saveDetails.zonal);
      formdata.append('FU', saveDetails.FU);
      formdata.append('contentTopic', saveDetails.contentTopic);
  this.sendAndRequestService.requestForPOST(Constants.app_urls.FAILURES.FAILUREANALYSIS_UPLOAD_FILES, formdata, true).subscribe(data => {
            this.spinnerService.hide();
            this.commonService.showAlertMessage("Files Uploaded and Saved Successfully");
            this.selectedFiles = [];
            this.filesExists = false;
            //window.location.reload();
        }, error => {
            console.log('ERROR >>>');
            this.spinnerService.hide();
            this.commonService.showAlertMessage("Files Uploading Failed.");
        })
        
}

upload(event) {
    if (event.target.files.length > 0) { this.filesExists = true; }
    for (var i = 0; i < event.target.files.length; i++) {
        this.selectedFiles.push(event.target.files[i]);
    }
}

removeFile(id) {
    this.selectedFiles.splice(id, 1);
    if(this.selectedFiles.length === 0) {
      this.filesExists = false;
        this.contentManagementFormGroup.reset();
    }
}

close() {
  this.contentManagementFormGroup.reset();
    this.uploadFile = false;
    this.selectedFiles = [];
    this.filesExists = false;
    this.addPermission = true;
}

}
