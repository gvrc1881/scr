import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { Constants } from 'src/app/common/constants';
import { MatDialogRef, MatTableDataSource, MatPaginator, MatSort, MatDialog } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Router, ActivatedRoute } from '@angular/router';
import { DatePipe } from '@angular/common';
import { DataViewDialogComponent } from 'src/app/components/data-view-dialog/data-view-dialog.component';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { AddActionsComponent } from 'src/app/components/failures/unusual-occurrence/add-actions/add-actions.component';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { DocumentDialogComponent } from '../../document-view-dialog/document-dialog.component';


@Component({
  selector: 'app-unusual-occurrence-failure',
  templateUrl: './unusual-occurrence-failure.component.html',
  styleUrls: ['./unusual-occurrence-failure.component.css']
})
export class UnusualOccurrenceFailureComponent implements OnInit {

  pagination = Constants.PAGINATION_NUMBERS;
  actionsPagination = Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  occurenceDisplayedColumns = ['sno', 'subStation', 'location', 'causeOfFailure', 'fromDateTime', 
  'thruDateTime',  'duration','internalExternal', 'actions','failureActions'];
  dataSource: MatTableDataSource<any>;
  dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;

  UnusualOccurrenceFailList: any;

  displayedColumnsActions = ['sno', 'failureActivity', 'fromTime', 'thruTime','by','specialRemarks',
    'remarks','location','trainNo', 'actions'];
  dataSourceActions: MatTableDataSource<any>;
  @ViewChild('paginatorActions', { static: true }) paginatorActions: MatPaginator;
  @ViewChild('sortActions', { static: true }) sortActions: MatSort;
  @ViewChild('filterActions', { static: true }) filterActions: ElementRef;
  ActionsFailListActions: any;
  facilityList;
  filterData; 
  gridData = [];
  subStation:any; 
  unUsaulOccurenceList:any;
  contentCategoryList: any;
    contentTopicList: any;
    uploadFile: boolean = false;
    contentManagementFormGroup: FormGroup;
    selectedFiles: File[] = [];
    filesExists: boolean = false;
    unUsualOccurenceFailId: any;
    documentDialogRef:MatDialogRef<DocumentDialogComponent>;
    pattern = "[a-zA-Z][a-zA-Z ]*";
    loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
    id: any;
 

  updatePagination() {
    this.filterData.dataSource = this.filterData.dataSource;
    this.filterData.dataSource.paginator = this.paginator;
  }
  

  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private sendAndRequestService: SendAndRequestService,
    private router: Router,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    public dialog: MatDialog,
    private datePipe: DatePipe,
  ) { }

  ngOnInit() {
    var permissionName = this.commonService.getPermissionNameByLoggedData("FAILURES","Unusual Occurrence Failure") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();
    this.getUnusualOccurrenceFailureData();

    this.getActionsFailureData();    
    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'subStation', "Value": " " },           
        { "Key": 'location', "Value": " " },
        { "Key": 'causeOfFailure', "Value": " " },
        { "Key": 'fromDateTime', "Value": " " },
     { "Key": 'thruDateTime', "Value": " " },
        { "Key": 'duration', "Value": " " },        
      //  { "Key": 'divisionLocal', "Value": " " },
        { "Key": 'internalExternal', "Value": " " },   
      
       
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
  getUnusualOccurrenceFailureData() {
    const UnusualOccurrenceFail: any[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.FAILURE_BY_TYPE_BASED_ON_DIVISION + Constants.FAILURE_TYPES.UNUSUAL_OCCURRENCE_FAILURE+'/'+this.userdata.username).subscribe((data) => {
      this.UnusualOccurrenceFailList = data;
      console.log(this.UnusualOccurrenceFailList)
      for (let i = 0; i < this.UnusualOccurrenceFailList.length; i++) {
        this.UnusualOccurrenceFailList[i].sno = i + 1;
        this.UnusualOccurrenceFailList[i].fromDateTime = this.datePipe.transform(this.UnusualOccurrenceFailList[i].fromDateTime, 'dd-MM-yyyy HH:mm:ss');
        this.UnusualOccurrenceFailList[i].thruDateTime = this.datePipe.transform(this.UnusualOccurrenceFailList[i].thruDateTime, 'dd-MM-yyyy HH:mm:ss');
        //this.UnusualOccurrenceFailList[i].divisionLocal=this.UnusualOccurrenceFailList[i].divisionLocal == 'true' ? 'Local': 'Division',
        //this.UnusualOccurrenceFailList[i].internalExternal=this.UnusualOccurrenceFailList[i].internalExternal == 'true' ? 'External': 'Internal',
        // this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY+this.UnusualOccurrenceFailList[i].subStation).subscribe((data) => {
        //   this.spinnerService.hide();
        //   this.facilityList = data;
        //   this.UnusualOccurrenceFailList[i].subStation = this.facilityList.facilityName;
        // });
        UnusualOccurrenceFail.push(this.UnusualOccurrenceFailList[i]);
      }
      this.filterData.gridData = UnusualOccurrenceFail;
      this.dataSource = new MatTableDataSource(UnusualOccurrenceFail);
      this.commonService.updateDataSource(this.dataSource, this.occurenceDisplayedColumns);
       this.filterData.dataSource = this.dataSource;
       this.dataSource.paginator = this.paginator;
       this.dataSource.sort = this.sort;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    }); 
  }
  processEditAction(id) {
    this.router.navigate(['unusual-occurrence/'+id], { relativeTo: this.route });
    
  }
  delete(id) {
    this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
      disableClose: false
    });
    this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete?';
    this.confirmDialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.spinnerService.show();
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.FAILURES.DELETE_FAILURE_TYPE_ID, id).subscribe(data => {
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

/******* ACTIONS */

applyFilterActions(filterValue: string) {
  filterValue = filterValue.trim(); // Remove whitespace
  filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
  this.dataSourceActions.filter = filterValue;
}
getActionsFailureData() {
  const ActionsFail: any[] = [];

  this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.GET_ACTIONS).subscribe((data) => {
    this.ActionsFailListActions = data;   
    for (let i = 0; i < this.ActionsFailListActions.length; i++) {
      this.ActionsFailListActions[i].sno = i + 1;
      this.ActionsFailListActions[i].fromTime = this.datePipe.transform(this.ActionsFailListActions[i].fromTime, 'dd-MM-yyyy HH:mm:ss');
      this.ActionsFailListActions[i].thruTime = this.datePipe.transform(this.ActionsFailListActions[i].thruTime, 'dd-MM-yyyy HH:mm:ss');
      
      ActionsFail.push(this.ActionsFailListActions[i]);    
    }

    this.dataSourceActions = new MatTableDataSource(ActionsFail);
    this.dataSourceActions.paginator = this.paginatorActions;
    this.dataSourceActions.sort = this.sortActions;
   
    this.spinnerService.hide();
  }, error => {
    this.spinnerService.hide();
  });
}
processEditActions(id) {
  this.router.navigate(['actions/'+ id], { relativeTo: this.route });
}
deleteActions(id) {
  this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
    disableClose: false
  });
  this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete?';
  this.confirmDialogRef.afterClosed().subscribe(result => {
    if (result) {
      this.spinnerService.show();
      this.sendAndRequestService.requestForDELETE(Constants.app_urls.FAILURES.DELETE_ACTIONS,id).subscribe(data => {
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Deleted Actions Fail Record Successfully");
        this.getActionsFailureData();
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Actions Fail Record Deletion Failed.");
      })
    }
    this.confirmDialogRef = null;
  });
}

NewAction(occurenceId){
//    console.log("substaion=="+JSON.stringify(subStation));
//    this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.FAILURE_TYPE_BY_ID +subStation).subscribe((data) => {
//     this.unUsaulOccurenceList = data;
//      console.log("occunce list"+JSON.stringify(data))
     
//  })
//  this.facilityId = this.unUsaulOccurenceList.subStation;
//  console.log("facility_id=="+this.facilityId);
this.router.navigate(['add-actions/'+occurenceId], { relativeTo: this.route });


}

ViewData(data){
  var result = {
    'title':this.Titles.UNUSUAL_OCCURRENCE,
        'dataSource':[

          { label:FieldLabelsConstant.LABELS.STATION, value:data.subStation },
          { label:FieldLabelsConstant.LABELS.LOCATION, value:data.location },
          { label:FieldLabelsConstant.LABELS.CAUSE_OF_FAILURE, value:data.causeOfFailure },
          { label:FieldLabelsConstant.LABELS.FROM_DATE_TIME, value:data.fromDateTime },
          { label:FieldLabelsConstant.LABELS.THRU_DATE_TIME, value:data.thruDateTime },
          { label:FieldLabelsConstant.LABELS.DURATION, value:data.duration },
          { label:FieldLabelsConstant.LABELS.IMPACT, value:data.impact },
          { label:FieldLabelsConstant.LABELS.REMARKS, value:data.remarks },
          { label:FieldLabelsConstant.LABELS.INTERNAL, value:data.cbInternalFailure },
          { label:FieldLabelsConstant.LABELS.EXTERNAL, value:data.cbExternalFailure }
        
  
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
  this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.UNUSUALOCCURENCE_FAILURE_ATTACHMENT_LIST + id).subscribe((response) => {     
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
  this.unUsualOccurenceFailId = id;
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
    'unUsualOccurenceFailId': this.unUsualOccurenceFailId,
            'description': this.contentManagementFormGroup.value.description,
            'divisionCode': this.loggedUserData.divisionCode,
            'createdBy': this.loggedUserData.id,
            'createdDate': new Date(),
            'contentCategory': 'OPERATIONS',
            'zonal': 'zonal',
            'FU': 'division',
            'contentTopic': 'UUO',
      }
      let formdata: FormData = new FormData();
      for(var i=0;i<this.selectedFiles.length;i++){
          formdata.append('file', this.selectedFiles[i]);
      }
      formdata.append('unUsualOccurenceFailId', saveDetails.unUsualOccurenceFailId);
      formdata.append('contentCategory', saveDetails.contentCategory);
      formdata.append('description', saveDetails.description);
      formdata.append('divisionCode', saveDetails.divisionCode);
      formdata.append('createdBy', saveDetails.createdBy);
      formdata.append('zonal', saveDetails.zonal);
      formdata.append('FU', saveDetails.FU);
      formdata.append('contentTopic', saveDetails.contentTopic);
  this.sendAndRequestService.requestForPOST(Constants.app_urls.FAILURES.UNUSUALOCCURENCE_FAILURE_UPLOAD_FILES, formdata, true).subscribe(data => {
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
