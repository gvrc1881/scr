
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

@Component({
  selector: 'app-ohe-location',
  templateUrl: './ohe-location.component.html',
  styleUrls: []
})
export class OheLocationComponent implements OnInit {
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
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
      'title':'Ohe Locations Data',
      'dataSource':[{label:'Division',value:data.division},{label:'Section',value:data.section},{label:'Pwi',value:data.pwi},{label:'Track Line',value:data.trackLine},
                    {label:'Ohe Mast ', value:data.oheMast},{label:'Structure Type ', value:data.structureType},{label:'Eng Feature ', value:data.engFeature},{label:'Ohe Feature', value:data.oheFeature},
                    {label:'Longitude',value:data.longitude},{label:'Latitude',value:data.latitude},{label:'Altitude',value:data.altitude},{label:'Date',value:data.date},
                    {label:'Validity',value:data.validity},{label:'Satellites',value:data.satellites},{label:'Speed',value:data.speed},{label:'Heading',value:data.heading},
                    {label:'Remark One',value:data.remarkOne},{label:'Remark Two',value:data.remarkTwo},{label:'Ohe Sequence',value:data.oheSequence},{label:'Curvature',value:data.curvature},
                    {label:'Curvature Remark',value:data.curvatureRemark},{label:'Chainage',value:data.chainage},{label:'Chainage Remark',value:data.chainageRemark}]
    }
    this.dataViewDialogRef = this.dialog.open(DataViewDialogComponent, {
      disableClose: false,
      height: '400px',
      width: '80%',       
      data:result,  
    });            
  }
}
