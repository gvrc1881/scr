import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { ProjectModel } from 'src/app/models/projects.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { DatePipe } from '@angular/common';
import { DataViewDialogComponent } from '../data-view-dialog/data-view-dialog.component';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';


@Component({
  selector: 'app-project',
  templateUrl: './projects.component.html',
  styleUrls: []
})
export class ProjectComponent implements OnInit {  

    FiledLabels = FieldLabelsConstant.LABELS;
    Titles = FieldLabelsConstant.TITLE;
    pagination=Constants.PAGINATION_NUMBERS;
    editPermission: boolean = true;
    addPermission: boolean = true;
    deletePermission: boolean = true;
    projectList:any;
    userdata: any = JSON.parse(localStorage.getItem('userData'));
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    displayedColumns = ['sno' ,  'division'  , 'workName' , 'section','executedBy'  , 'latestRevisedCost' , 'actions','WorkPhaseDetails'];
    dataSource: MatTableDataSource<ProjectModel>;
    dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
    
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    @ViewChild('filter', { static: true }) filter: ElementRef;
    
  
    constructor(
      private spinnerService: Ng4LoadingSpinnerService,
      private commonService: CommonService,
     private sendAndRequestService : SendAndRequestService,
      private router: Router,
      private route: ActivatedRoute,
      private datePipe: DatePipe,
      public dialog: MatDialog,
     
    ) { }
  

  ngOnInit() {
    var permissionName = this.commonService.getPermissionNameByLoggedData("PROJECT ADMIN","Projects") ;
    this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
  this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
  this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

  this.spinnerService.show();
  this.getProjectData();
  
}

applyFilter(filterValue: string) {
filterValue = filterValue.trim(); 
filterValue = filterValue.toLowerCase(); 
this.dataSource.filter = filterValue;
}
getProjectData() {
const project: ProjectModel[] = [];
this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WORK).subscribe((data) => {
  this.projectList = data;
  for (let i = 0; i < this.projectList.length; i++) {
    this.projectList[i].sno = i + 1;
    this.projectList[i].commencementDate = this.datePipe.transform(this.projectList[i].commencementDate, 'dd-MM-yyyy ');
    this.projectList[i].expectedCompletion = this.datePipe.transform(this.projectList[i].expectedCompletion, 'dd-MM-yyyy ');
    this.projectList[i].targetStartDate = this.datePipe.transform(this.projectList[i].targetStartDate, 'dd-MM-yyyy ');
    this.projectList[i].targetDateOfCompletion = this.datePipe.transform(this.projectList[i].targetDateOfCompletion, 'dd-MM-yyyy ');
  project.push(this.projectList[i]);
}
this.dataSource = new MatTableDataSource(project);
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
    this.sendAndRequestService.requestForDELETE(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.DELETE_WORK, id).subscribe(data => {
      console.log(JSON.stringify(data));
      this.spinnerService.hide();
      this.commonService.showAlertMessage("Deleted Project Successfully");
      this.getProjectData();
    }, error => {
      console.log('ERROR >>>');
      this.spinnerService.hide();
      this.commonService.showAlertMessage("Project Deletion Failed.");
    })
  }
  this.confirmDialogRef = null;
});
}
ViewData(data){
var result = {
  'title':this.Titles.PROJECT,
  'dataSource':[
    
                { label:FieldLabelsConstant.LABELS.PROJECT, value:data.workName },
                { label:FieldLabelsConstant.LABELS.ALLOCATION, value:data.allocation },
                { label:FieldLabelsConstant.LABELS.LOA_DATE, value:data.loaDate },
                { label:FieldLabelsConstant.LABELS.LOA_NO, value:data.loaNo },
                { label:FieldLabelsConstant.LABELS.TENDER_VALUE, value:data.tenderValue },
                { label:FieldLabelsConstant.LABELS.COMMENCEMENT_DATE, value:data.commencementDate },
                { label:FieldLabelsConstant.LABELS.EXPECTED_COMPLETION, value:data.expectedCompletion },
                { label:FieldLabelsConstant.LABELS.TARGET_DATE, value:data.targetStartDate },
                { label:FieldLabelsConstant.LABELS.TARGET_COMPLETION_DATE, value:data.targetDateOfCompletion },
                { label:FieldLabelsConstant.LABELS.DIVISION, value:data.division },
                { label:FieldLabelsConstant.LABELS.ESTIMATED_REVISED_ANTICIPATED_COST, value:data.estdLatestAnticCost },
                { label:FieldLabelsConstant.LABELS.EXECUTION_AGENCY, value:data.executingAgency },
                { label:FieldLabelsConstant.LABELS.SECTION, value:data.section },
                { label:FieldLabelsConstant.LABELS.RKM, value:data.rkm },
                { label:FieldLabelsConstant.LABELS.TKM, value:data.tkm },
                { label:FieldLabelsConstant.LABELS.LATEST_REVISED_COST, value:data.latestRevisedCost },
                { label:FieldLabelsConstant.LABELS.SANCTION_COST, value:data.sanctionCost },
                { label:FieldLabelsConstant.LABELS.STATUS, value:data.presentStatus },
                { label:FieldLabelsConstant.LABELS.STATUS_REMARKS, value:data.statusRemarks },
                { label:FieldLabelsConstant.LABELS.START_KM, value:data.startKm },
                { label:FieldLabelsConstant.LABELS.END_KM, value:data.endKm },
                { label:FieldLabelsConstant.LABELS.LINE_TYPE, value:data.lineType }
                
              ]
}
this.dataViewDialogRef = this.dialog.open(DataViewDialogComponent, {
  disableClose: false,
  height: '400px',
  width: '80%',       
  data:result,  
});            
}

processNewProcess(workId) {
 //this.router.navigate(['copy-wp-and-wpa/'+workId], { relativeTo: this.route });
 this.router.navigate(['copy-wp-and-wpa/'+workId], { relativeTo: this.route });
  
  //	this.router.navigate(['/copy-wp-and-wpa'+'/'+id]); // navigate to other page
  //this.router.navigate(['/copy-wp-and-wpa']);
	
  }

  }
