
import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { OheLocationModel } from 'src/app/models/ohe-location.model';
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
  selector: 'app-ohe-location',
  templateUrl: './ohe-location.component.html',
  styleUrls: []
})
export class OheLocationComponent implements OnInit {

  pagination = Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(sessionStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  displayedColumns = ['sno', 'division', 'section', 'pwi', 'trackLine', 'oheMast', 'structureType','date','oheFeature','longitude','latitude','altitude','satellites','actions'];
  dataSource: MatTableDataSource<OheLocationModel>;
  dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
  gridData = [];
  oheLocationList: any;
  filterData;

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
    var permissionName = this.commonService.getPermissionNameByLoggedData("ASSET REGISTER","OHE LOCATIONS") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();
    this.getOheLocationData();

    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'division', "Value": " " },
        { "Key": 'section', "Value": " " },
        { "Key": 'pwi', "Value": " " },
        { "Key": 'trackLine', "Value": "" },
        { "Key": 'oheMast', "Value": " " },
        { "Key": 'structureType', "Value": " " },
        { "Key": 'date', "Value": "" },
        { "Key": 'oheFeature', "Value": " " }, 
        { "Key": 'longitude', "Value": " " },  
        { "Key": 'latitude', "Value": " " }, 
        { "Key": 'altitude', "Value": " " },  
        { "Key": 'satellites', "Value": " " },  

      ],
      gridData: this.gridData,
      dataSource: this.dataSource,
      paginator: this.paginator,
      sort: this.sort
    };

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
  getOheLocationData() {
    const oheLocation: OheLocationModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.OHELOCATION.GET_OHE_LOCATION).subscribe((data) => {
      this.oheLocationList = data;
      for (let i = 0; i < this.oheLocationList.length; i++) {
        this.oheLocationList[i].sno = i + 1;
        this.oheLocationList[i].date = this.datePipe.transform(this.oheLocationList[i].date, 'dd-MM-yyyy');
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
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.ENERGY_BILL_PAYMENTS.OHELOCATION.DELETE_OHE_LOCATION, id).subscribe(data => {
          console.log(JSON.stringify(data));
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted Ohe Location Successfully");
          this.getOheLocationData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Ohe Location Deletion Failed.");
        })
      }
      this.confirmDialogRef = null;
    });
  }
  ViewData(data){
    var result = {
      'title':this.Titles.OHE_LOCATIONS_DATA,
      'dataSource':[                
                    { label:FieldLabelsConstant.LABELS.DIVISION, value:data.division },
                    { label:FieldLabelsConstant.LABELS.SECTION, value:data.section },
                    { label:FieldLabelsConstant.LABELS.PWI, value:data.pwi },
                    { label:FieldLabelsConstant.LABELS.TRACK_LINE, value:data.trackLine },
                    { label:FieldLabelsConstant.LABELS.OHE_MAST, value:data.oheMast },
                    { label:FieldLabelsConstant.LABELS.STRUCTURE_TYPE, value:data.structureType },
                    { label:FieldLabelsConstant.LABELS.ENG_FEATURE, value:data.engFeature },
                    { label:FieldLabelsConstant.LABELS.OHE_FEATURE, value:data.oheFeature },
                    { label:FieldLabelsConstant.LABELS.LONGITUDE, value:data.longitude },
                    { label:FieldLabelsConstant.LABELS.LATITUDE, value:data.latitude },
                    { label:FieldLabelsConstant.LABELS.ALTITUDE, value:data.altitude },
                    { label:FieldLabelsConstant.LABELS.DATE, value:data.date },
                    { label:FieldLabelsConstant.LABELS.VALIDITY, value:data.validity },
                    { label:FieldLabelsConstant.LABELS.SATELLITES, value:data.satellites },
                    { label:FieldLabelsConstant.LABELS.SPEED, value:data.speed },
                    { label:FieldLabelsConstant.LABELS.HEADING, value:data.heading },
                    { label:FieldLabelsConstant.LABELS.REMARK_ONE, value:data.remarkOne },
                    { label:FieldLabelsConstant.LABELS.REMARK_TWO, value:data.remarkTwo },
                    { label:FieldLabelsConstant.LABELS.OHE_SEQUENCE, value:data.oheSequence },
                    { label:FieldLabelsConstant.LABELS.CURVATURE, value:data.curvature },
                    { label:FieldLabelsConstant.LABELS.CURVATURE_REMARK, value:data.curvatureRemark },
                    { label:FieldLabelsConstant.LABELS.CHAINAGE, value:data.chainage },
                    { label:FieldLabelsConstant.LABELS.CHAINAGE_REMARK, value:data.chainageRemark }

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
