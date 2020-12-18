import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { SpecialWorksModel } from 'src/app/models/special-works.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { DataViewDialogComponent } from '../data-view-dialog/data-view-dialog.component';
import { DatePipe } from '@angular/common';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';

@Component({
  selector: 'app-special-works',
  templateUrl: './special-works.component.html',
  styleUrls: []
})
export class SpecialWorksComponent implements OnInit {

  pagination =Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  displayedColumns = ['sno', 'facilityId', 'location','precautionaryMeasure','count', 'dateOfWork','actions'];
  dataSource: MatTableDataSource<SpecialWorksModel>;
  dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
  filterData;
  facilityData:any;
   precautionaryMeasureData:any;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
  gridData = [];

  specialWorksList: any;

  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private datePipe: DatePipe,
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
    this.getSpecialWorksData();
    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'facilityId', "Value": " " },
        { "Key": 'location', "Value": " " },
        { "Key": 'precautionaryMeasure', "Value": " " },
        { "Key": 'count', "Value": " " },
        { "Key": 'dateOfWork', "Value": "" },
      ],
      gridData: this.gridData,
      dataSource: this.dataSource,
      paginator: this.paginator,
      sort: this.sort
    };


  }
  getSpecialWorksData() {
    const specialWorks: SpecialWorksModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.SPECIALWORKS.GET_SPECIAL_WORKS).subscribe((data) => {
      this.specialWorksList = data;
      for (let i = 0; i < this.specialWorksList.length; i++) {
        this.specialWorksList[i].sno = i + 1;
        this.specialWorksList[i].dateOfWork = this.datePipe.transform(this.specialWorksList[i].dateOfWork, 'dd-MM-yyyy');
                this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY+JSON.stringify(this.specialWorksList[i].facilityId)).subscribe((data) => {
                  this.spinnerService.hide();
                  this.facilityData = data;
                  this.specialWorksList[i].facilityId = this.facilityData.facilityName;
                });
                specialWorks.push(this.specialWorksList[i]);
      }
      this.filterData.gridData = specialWorks;
      this.dataSource = new MatTableDataSource(specialWorks);
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
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.ENERGY_BILL_PAYMENTS.SPECIALWORKS.DELETE_SPECIAL_WORKS ,id).subscribe(data => {
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted Special Works  Successfully");
          this.getSpecialWorksData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Special Works  Deletion Failed.");
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
      'title':this.Titles.SPECIAL_WORKS_DATA,
      'dataSource':[                                 
                    { label:FieldLabelsConstant.LABELS.DEPOT, value:data.facilityId },
                    { label:FieldLabelsConstant.LABELS.LOCATION, value:data.location },
                    { label:FieldLabelsConstant.LABELS.COUNT, value:data.count },
                    { label:FieldLabelsConstant.LABELS.DATE_OF_WORK, value:data.dateOfWork },
                    { label:FieldLabelsConstant.LABELS.DONE_BY, value:data.doneBy },
                    { label:FieldLabelsConstant.LABELS.REMARKS, value:data.remarks },
                    { label:FieldLabelsConstant.LABELS.SPECIAL_WORKS, value:data.precautionaryMeasure.precautionaryMeasure},
                    
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
