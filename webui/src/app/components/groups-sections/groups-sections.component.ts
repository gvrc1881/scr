import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { GroupsSectionsModel } from 'src/app/models/groups-sections.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { Constants } from 'src/app/common/constants';
import { DatePipe } from '@angular/common';
import { DataViewDialogComponent } from '../data-view-dialog/data-view-dialog.component';

@Component({
  selector: 'app-groups-sections',
  templateUrl: './groups-sections.component.html',
  styleUrls: []
})
export class GroupsSectionsComponent implements OnInit {
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  Pagination=Constants.PAGINATION_NUMBERS;
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  displayedColumns = ['sno', 'workId', 'workGroup', 'section', 'agency', 'doublingTrippling', 'division','code','description','tkm','rkm','sidingYardStation','actions'];
  groupsSectionsdataSource: MatTableDataSource<GroupsSectionsModel>;
  dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
  gridData = [];
  groupsSectionsList: any;
 

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
    var permissionName = this.commonService.getPermissionNameByLoggedData("PROJECT ADMIN","Groups Sections") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();
    this.getGroupsSectionsData();

  }
 
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.groupsSectionsdataSource.filter = filterValue;
}
getGroupsSectionsData() {
  const groups: GroupsSectionsModel[] = [];
  this.sendAndRequestService.requestForGET(Constants.app_urls.PROJECT_ADMIN.GROUPS_SECTIONS.GET_GROUPS_SECTIONS).subscribe((data) => {
    this.groupsSectionsList = data;
    for (let i = 0; i < this.groupsSectionsList.length; i++) {
      this.groupsSectionsList[i].sno = i + 1;
        groups.push(this.groupsSectionsList[i]);        
  }
  this.groupsSectionsdataSource = new MatTableDataSource(groups);
  this.groupsSectionsdataSource.paginator = this.paginator;
  this.groupsSectionsdataSource.sort = this.sort;
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
      this.sendAndRequestService.requestForDELETE(Constants.app_urls.PROJECT_ADMIN.GROUPS_SECTIONS.DELETE, id).subscribe(data => {
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Deleted Groups Sections Successfully");
        this.getGroupsSectionsData();
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Groups Sections Deletion Failed.");
      })
    }
    this.confirmDialogRef = null;
  });
}
ViewData(data){
  var result = {
    'title':this.Titles.GROUPS_SECTIONS,
    'dataSource':[
      
                  { label:FieldLabelsConstant.LABELS.PROJECT, value:data.workId.workName },
                  { label:FieldLabelsConstant.LABELS.GROUP, value:data.workGroup },
                  { label:FieldLabelsConstant.LABELS.SECTION, value:data.section },
                  { label:FieldLabelsConstant.LABELS.AGENCY, value:data.agency },
                  { label:FieldLabelsConstant.LABELS.DIVISION, value:data.division },
                  { label:FieldLabelsConstant.LABELS.CODE, value:data.code },
                  { label:FieldLabelsConstant.LABELS.DESCRIPTION, value:data.description },
                  { label:FieldLabelsConstant.LABELS.TKM, value:data.tkm },
                  { label:FieldLabelsConstant.LABELS.RKM, value:data.rkm },
                  { label:FieldLabelsConstant.LABELS.SIDING_YARD_STATION, value:data.sidingYardStation }             
                  
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






