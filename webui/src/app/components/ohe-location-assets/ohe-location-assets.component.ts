import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { DataViewDialogComponent } from '../data-view-dialog/data-view-dialog.component';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { OheLocationModel } from 'src/app/models/ohe-location.model';


@Component({
  selector: 'app-ohe-location-assets',
  templateUrl: './ohe-location-assets.component.html',
  styleUrls: []
})
export class OheLocationAssetsComponent implements OnInit {

  pagination =Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  displayedColumns = ['sno', 'project', 'workGroup','location', 'division', 'facilityId','actions','oheLocation'];
  dataSource: MatTableDataSource<OheLocationModel>;
  dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
  filterData;
  oheLocationList:any;
  workList:any;
  facilityData: any;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
  gridData = [];

  categoryMemberList: any;

  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private route: ActivatedRoute,
    public dialog: MatDialog,
    private sendAndRequestService:SendAndRequestService
  ) { }

  ngOnInit() {
    var permissionName = this.commonService.getPermissionNameByLoggedData("PROJECT","OHE LOCATION AND ASSET") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
    this.getOheLocationData();
    this.spinnerService.show();
    
    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'project', "Value": " " },
        { "Key": 'workGroup', "Value": " " },
        { "Key": 'location', "Value": " " },
        { "Key": 'division', "Value": "" },
        { "Key": 'facilityId', "Value": " " },
        
      ],
      gridData: this.gridData,
      dataSource: this.dataSource,
      paginator: this.paginator,
      sort: this.sort
    };

  }
  getOheLocationData() {
    const oheLocation: OheLocationModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.OHELOCATION.GET_OHE_LOCATION).subscribe((data) => {
      this.oheLocationList = data;
      for (let i = 0; i < this.oheLocationList.length; i++) {
        this.oheLocationList[i].sno = i + 1;
        this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY+JSON.stringify(this.oheLocationList[i].facilityId)).subscribe((data) => {
          this.spinnerService.hide();
          this.facilityData = data;
          this.oheLocationList[i].facilityId = this.facilityData.facilityName;
        }, error => {
          this.spinnerService.hide();
      });
        oheLocation.push(this.oheLocationList[i]);
      }
      this.filterData.gridData = oheLocation;
      this.dataSource = new MatTableDataSource(oheLocation);
      this.commonService.updateDataSource(this.dataSource, this.displayedColumns);
      this.filterData.dataSource = this.dataSource;
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }
  oheLocationEditScreen(id) {
    this.router.navigate(['ohe-location/' + id], { relativeTo: this.route });
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
      'title':this.Titles.OHE_LOCATION_AND_ASSETTYPE_DATA,
      'dataSource':[
                      
                    {label:FieldLabelsConstant.LABELS.PROJECT,value:data.project},
                    {label:FieldLabelsConstant.LABELS.WORK_GROUP,value:data.workGroup},
                    {label:FieldLabelsConstant.LABELS.SECTION,value:data.section},
                    {label:FieldLabelsConstant.LABELS.LOCATION,value:data.location},
                    {label:FieldLabelsConstant.LABELS.DIVISION, value:data.division},
                    {label:FieldLabelsConstant.LABELS.DEPOT, value:data.facilityId},
                    {label:FieldLabelsConstant.LABELS.KILOMETER, value:data.kilometer},
                    {label:FieldLabelsConstant.LABELS.FOUNDATION,value:data.foundation},
                    {label:FieldLabelsConstant.LABELS.OHE_MAST,value:data.oheMast},
                    {label:FieldLabelsConstant.LABELS.STATION,value:data.station}
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
