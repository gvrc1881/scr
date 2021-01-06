import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { GantryModel } from 'src/app/models/gantry.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { DatePipe } from '@angular/common';
import { DataViewDialogComponent } from '../data-view-dialog/data-view-dialog.component';
import { SwitchOperationsComponent } from '../../components/switch-operations/switch-operations.component';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';

@Component({
  selector: 'app-gantry',
  templateUrl: './gantry.component.html',
  styleUrls: []
})
export class GantryComponent implements OnInit {

  pagination = Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  displayedColumns = ['sno', 'facilityId', 'gantryCode', 'elementarySections', 'protectionTraverseCrossover', 'protectionTraverseTurnout', 'protectionLongitudnalUp','protectionLongitudnalDn','normallyOpen','tpcBoard','actions','switch'];
  dataSource: MatTableDataSource<GantryModel>;
  dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
  gridData = [];
  gantryList: any;
  filterData;
  facilityData: any;

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
    var permissionName = this.commonService.getPermissionNameByLoggedData("ASSET REGISTER","GANTRY") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();
    this.getGantryData();

    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'facilityId', "Value": " " },
        { "Key": 'gantryCode', "Value": " " },
        { "Key": 'elementarySections', "Value": " " },
        { "Key": 'protectionTraverseCrossover', "Value": "" },
        { "Key": 'protectionTraverseTurnout', "Value": " " },
        { "Key": 'protectionLongitudnalUp', "Value": " " },
        { "Key": 'protectionLongitudnalDn', "Value": "" },
        { "Key": 'normallyOpen', "Value": " " }, 
        { "Key": 'tpcBoard', "Value": " " }
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
  getGantryData() {
    const gantry: GantryModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.GANTRY.GET_GANTRY).subscribe((data) => {
      this.gantryList = data;
      for (let i = 0; i < this.gantryList.length; i++) {
        this.gantryList[i].sno = i + 1;
        this.gantryList[i].facilityId = this.gantryList[i].facilityId;
      gantry.push(this.gantryList[i]);
    }
    this.filterData.gridData = gantry;
    this.dataSource = new MatTableDataSource(gantry);
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
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.ENERGY_BILL_PAYMENTS.GANTRY.DELETE_GANTRY, id).subscribe(data => {
          console.log(JSON.stringify(data));
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted Gantry Successfully");
          this.getGantryData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Gantry Deletion Failed.");
        })
      }
      this.confirmDialogRef = null;
    });
  }
  ViewData(data){
    var result = {
      'title':this.Titles.GANTRY_DATA,
      'dataSource':[
        
                    { label:FieldLabelsConstant.LABELS.DEPOT, value:data.facilityId },
                    { label:FieldLabelsConstant.LABELS.GANTRY_CODE, value:data.gantryCode },
                    { label:FieldLabelsConstant.LABELS.ELEMENTARY_SECTIONS, value:data.elementarySections },
                    { label:FieldLabelsConstant.LABELS.PROTECTION_TRAVERSE_CROSSOVER, value:data.protectionTraverseCrossover },
                    { label:FieldLabelsConstant.LABELS.PROTECTION_TRAVERSE_TURNOUT, value:data.protectionTraverseTurnout },
                    { label:FieldLabelsConstant.LABELS.PROTECTION_LONGITUDINAL_UP, value:data.protectionLongitudnalUp },
                    { label:FieldLabelsConstant.LABELS.PROTECTION_LONGITUDINAL_DN, value:data.protectionLongitudnalDn },
                    { label:FieldLabelsConstant.LABELS.NORMALLY_OPEN, value:data.normallyOpen },
                    { label:FieldLabelsConstant.LABELS.TPC_BOARD, value:data.tpcBoard },
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
  switchDialog(gantryCode){
    var gantryData={
      'type':"GANTRY",
      'data':gantryCode
    }
    this.dialog.open(SwitchOperationsComponent, {
      height: '600px',
      width: '80%', 
      data:gantryData,
    });

  }
}
