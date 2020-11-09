import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { AssetsScheduleModel } from 'src/app/models/assets-schedule.model';
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
  selector: 'app-assets-schedule',
  templateUrl: './assets-schedule.component.html',
  styleUrls: []
})
export class AssetsScheduleComponent implements OnInit {
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  assetsScheduleList:any;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  assetsScheduleDisplayColumns = ['sno', 'scheduleCode', 'scheduleName', 'description', 'actions'];
  assetsScheduleDataSource: MatTableDataSource<AssetsScheduleModel>;
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
    var permissionName = this.commonService.getPermissionNameByLoggedData("TRD CONFIG","Assets schedule") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();
    this.getAssetsScheduleData();
    
}
applyFilter(filterValue: string) {
  filterValue = filterValue.trim(); 
  filterValue = filterValue.toLowerCase(); 

}
getAssetsScheduleData() {
  const schedule: AssetsScheduleModel[] = [];
  this.sendAndRequestService.requestForGET(Constants.app_urls.CONFIG.ASSETS_SCHEDULE.GET_ASSET_SCHEDULES).subscribe((data) => {
    this.assetsScheduleList = data;
    for (let i = 0; i < this.assetsScheduleList.length; i++) {
      this.assetsScheduleList[i].sno = i + 1;

    schedule.push(this.assetsScheduleList[i]);
  }
  this.assetsScheduleDataSource = new MatTableDataSource(schedule);
  this.assetsScheduleDataSource.paginator = this.paginator;
  this.assetsScheduleDataSource.sort = this.sort;
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
      this.sendAndRequestService.requestForDELETE(Constants.app_urls.CONFIG.ASSETS_SCHEDULE.DELETE_ASSET_SCHEDULE, id).subscribe(data => {
        console.log(JSON.stringify(data));
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Deleted Assets Schedule Successfully");
        this.getAssetsScheduleData();
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Assets Schedule Deletion Failed.");
      })
    }
    this.confirmDialogRef = null;
  });
}
ViewData(data){
  var result = {
    'title':this.Titles.ASSETS_SCHEDULE_DATA,
    'dataSource':[
      
                  { label:FieldLabelsConstant.LABELS.SCHEDULE_CODE, value:data.scheduleCode },
                  { label:FieldLabelsConstant.LABELS.SCHEDULE_NAME, value:data.scheduleName },
                  { label:FieldLabelsConstant.LABELS.DESCRIPTION, value:data.description }
                  
                ]
  }
  this.dataViewDialogRef = this.dialog.open(DataViewDialogComponent, {
    disableClose: false,
    height: '400px',
    width: '80%',       
    data:result,  
  });            
}
}