import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { StatusItemModel } from 'src/app/models/status-item.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { DataViewDialogComponent } from '../data-view-dialog/data-view-dialog.component';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';

@Component({
  selector: 'app-status-item',
  templateUrl: './status-item.component.html',
  styleUrls: []
})
export class StatusItemComponent implements OnInit {

  pagination =Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(sessionStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  displayedColumns = ['sno', 'statusTypeId', 'statusId','statusCode','sequenceId','actions'];
  dataSource: MatTableDataSource<StatusItemModel>;
  dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
  filterData;
  facilityData:any;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
  gridData = [];

  statusItemList: any;

  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private route: ActivatedRoute,
    public dialog: MatDialog,
    private sendAndRequestService:SendAndRequestService
  ) { }

  ngOnInit() {
    var permissionName = this.commonService.getPermissionNameByLoggedData("ASSET REGISTER","PRECAUTIONARY MEASURES") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();
    this.getStatusItemData();
    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'statusTypeId', "Value": " " },
        { "Key": 'statusId', "Value": " " },
        { "Key": 'statusCode', "Value": " " },
        { "Key": 'sequenceId', "Value": " " },
      ],
      gridData: this.gridData,
      dataSource: this.dataSource,
      paginator: this.paginator,
      sort: this.sort
    };


  }
  getStatusItemData() {
    const statusItem: StatusItemModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.CONFIG.STATUS_ITEM.GET_STATUS_ITEM).subscribe((data) => {
      this.statusItemList = data;
      for (let i = 0; i < this.statusItemList.length; i++) {
        this.statusItemList[i].sno = i + 1;        
        statusItem.push(this.statusItemList[i]);
    }
    this.filterData.gridData = statusItem;
    this.dataSource = new MatTableDataSource(statusItem);
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
    this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to Delete?';
    this.confirmDialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.spinnerService.show();
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.CONFIG.STATUS_ITEM.DELETE_STATUS_ITEM ,id).subscribe(data => {
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted Status Item  Successfully");
          this.getStatusItemData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Status Item  Deletion Failed.");
        });
      }
      this.confirmDialogRef = null;
    });
  }
  updatePagination() {
    this.filterData.dataSource = this.filterData.dataSource;
    this.filterData.dataSource.paginator = this.paginator;
  }
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); 
    filterValue = filterValue.toLowerCase(); 
    this.filterData.dataSource.filter = filterValue;
  }
  ViewData(data){
    var result = {
      'title':this.Titles.STATUS_ITEM_DATA,
      'dataSource':[                                 
                    { label:FieldLabelsConstant.LABELS.STATUS_TYPE_ID, value:data.statusTypeId},
                    { label:FieldLabelsConstant.LABELS.STATUS_ID, value:data.statusId },
                    { label:FieldLabelsConstant.LABELS.STATUS_CODE, value:data.statusCode },
                    { label:FieldLabelsConstant.LABELS.SEQUENCE_ID, value:data.sequenceId },
                    { label:FieldLabelsConstant.LABELS.DESCRIPTION, value:data.description },              
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
