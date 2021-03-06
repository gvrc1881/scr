import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { FootPatrollingSectionsModel } from 'src/app/models/foot-patrolling-sections.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { DataViewDialogComponent } from '../data-view-dialog/data-view-dialog.component';
import { DatePipe } from '@angular/common';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { FuseConfirmPopupComponent } from '../confirm-popup/confirm-popup.component';

@Component({
  selector: 'app-fp-sections',
  templateUrl: './fp-sections.component.html',
  styleUrls: []
})
export class FpSectionsComponent implements OnInit {

  pagination =Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(sessionStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  displayedColumns = ['sno' ,'facilityDepot','fpSection' , 'fromLocation' , 'toLocation' , 'fromDate' , 'toDate' , 'actions'];
  dataSource: MatTableDataSource<FootPatrollingSectionsModel>;
  dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
  filterData;
  facilityData:any;
  resp: any; 
   precautionaryMeasureData:any;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
  gridData = [];

  fpSectionsList: any;

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
    var permissionName = this.commonService.getPermissionNameByLoggedData("FP","FP Sections") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();
    this.getFpSectionsData();
    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'facilityDepot', "Value": " " },
        { "Key": 'fpSection', "Value": " " },
        { "Key": 'fromLocation', "Value": "" },
        { "Key": 'toLocation', "Value": "" },
        { "Key": 'fromDate', "Value": "" },
        { "Key": 'toDate', "Value": "" },
        { "Key": 'remarks', "Value": "" },

      ],
      gridData: this.gridData,
      dataSource: this.dataSource,
      paginator: this.paginator,
      sort: this.sort
    };


  }
  getFpSectionsData() {
    const fpSections: FootPatrollingSectionsModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.FP_SECTIONS.GET_FP_SECTIONS).subscribe((data) => {
      this.fpSectionsList = data;
      for (let i = 0; i < this.fpSectionsList.length; i++) {
        this.fpSectionsList[i].sno = i + 1;
        this.fpSectionsList[i].facilityDepot = this.fpSectionsList[i].facilityDepot;
        this.fpSectionsList[i].fromDate = this.datePipe.transform(this.fpSectionsList[i].fromDate, 'dd-MM-yyyy');
        this.fpSectionsList[i].toDate = this.datePipe.transform(this.fpSectionsList[i].toDate, 'dd-MM-yyyy');
        fpSections.push(this.fpSectionsList[i]);
      }
      this.filterData.gridData = fpSections;
      this.dataSource = new MatTableDataSource(fpSections);
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
    this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected fpSections item?";
    this.confirmDialogRef.afterClosed().subscribe(result => {
        if(result){
            this.sendAndRequestService.requestForDELETE(Constants.app_urls.ENERGY_BILL_PAYMENTS.FP_SECTIONS.DELETE_FP_SECTIONS, id).subscribe(response => {
                    this.commonService.showAlertMessage('FP Sections Deleted Successfully');
                    this.getFpSectionsData();
                },error => {});
        }
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
      'title':'Foot Patrolling Sections',
      'dataSource':[        
        { label:FieldLabelsConstant.LABELS.DEPOT, value:data.facilityDepot },
        { label:FieldLabelsConstant.LABELS.FP_SECTION, value:data.fpSection },
        { label:FieldLabelsConstant.LABELS.FROM_LOCATION, value:data.fromLocation },
        { label:FieldLabelsConstant.LABELS.TO_LOCATION, value:data.toLocation },
        { label:FieldLabelsConstant.LABELS.FROM_DATE, value:data.fromDate },
        { label:FieldLabelsConstant.LABELS.TO_DATE, value:data.toDate },
        { label:FieldLabelsConstant.LABELS.REMARKS, value:data.remarks },


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
