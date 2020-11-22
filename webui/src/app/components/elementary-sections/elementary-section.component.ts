import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { ElementarySectionModel } from 'src/app/models/elementary-section.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { DataViewDialogComponent } from '../data-view-dialog/data-view-dialog.component';
import { SwitchOperationsComponent } from '../../components/switch-operations/switch-operations.component';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';

@Component({
  selector: 'app-elementary-section',
  templateUrl: './elementary-section.component.html',
  styleUrls: []
})
export class ElementarySectionComponent implements OnInit {

  pagination =Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;

  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  displayedColumns = ['sno', 'elementarySectionCode', 'facilityId', 'stationCode', 'sectorCode','subSectorCode','protectionCrossover','protectionTurnout','actions','switch'];
  dataSource: MatTableDataSource<ElementarySectionModel>;
  dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
  gridData = [];
  elemenSectionList: any;
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
    public dialog: MatDialog,
  ) { }

  ngOnInit() {
    var permissionName = this.commonService.getPermissionNameByLoggedData("ASSET REGISTER","ELEMENTARY SECTIONS") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();
    this.getElementarySectionsData();

    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'elementarySectionCode', "Value": " " },
        { "Key": 'facilityId', "Value": " " },
        { "Key": 'stationCode', "Value": " " },
        { "Key": 'trackCode', "Value": "" },
        { "Key": 'sidingMain', "Value": " " },
        { "Key": 'sectorCode', "Value": " " },
        { "Key": 'subSectorCode', "Value": "" },
        { "Key": 'protectionCrossover', "Value": " " }, 
        { "Key": 'protectionTurnout', "Value": " " }
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
  getElementarySectionsData() {
    const eleSection: ElementarySectionModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ELEMENTARYSECTIONS.GET_ELEMENTARY_SECTIONS).subscribe((data) => {
      this.elemenSectionList = data;
      for (let i = 0; i < this.elemenSectionList.length; i++) {
        this.elemenSectionList[i].sno = i + 1;
        this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY+JSON.stringify(this.elemenSectionList[i].facilityId)).subscribe((data) => {
          this.spinnerService.hide();
          this.facilityData = data;
          this.elemenSectionList[i].facilityId = this.facilityData.facilityName;
        }, error => {
          this.spinnerService.hide();
      });
      eleSection.push(this.elemenSectionList[i]);
    }
    this.filterData.gridData = eleSection;
    this.dataSource = new MatTableDataSource(eleSection);
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
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.ENERGY_BILL_PAYMENTS.ELEMENTARYSECTIONS.DELETE_ELEMENTARY_SECTIONS, id).subscribe(data => {
          console.log(JSON.stringify(data));
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted Elementarysections Successfully");
          this.getElementarySectionsData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Elementary sections Deletion Failed.");
        })
      }
      this.confirmDialogRef = null;
    });
  }
  ViewData(data){
    var result = {
      'title':this.Titles.ELEMENTARY_SECTIONS_DATA,
      'dataSource':[
                    {label:FieldLabelsConstant.LABELS.ELEMENTARY_SECTION_CODE,value:data.elementarySectionCode},
                    {label:FieldLabelsConstant.LABELS.DEPOT,value:data.facilityId},
                    {label:FieldLabelsConstant.LABELS.STATION_CODE,value:data.stationCode},
                    {label:FieldLabelsConstant.LABELS.TRACK_CODE, value:data.trackCode},
                    {label:FieldLabelsConstant.LABELS.SIDING, value:data.sidingMain},
                    {label:FieldLabelsConstant.LABELS.SECTION_CODE, value:data.sectionCode},
                    {label:FieldLabelsConstant.LABELS.SECTOR_CODE,value:data.sectorCode},
                    {label:FieldLabelsConstant.LABELS.SUB_SECTOR_CODE,value:data.subSectorCode},
                    {label:FieldLabelsConstant.LABELS.FROM_KILOMETER,value:data.fromKm},
                    {label:FieldLabelsConstant.LABELS.FROM_SEQ,value:data.fromSeq},
                    {label:FieldLabelsConstant.LABELS.TO_KILOMETER,value:data.toKm},
                    {label:FieldLabelsConstant.LABELS.TO_SEQ,value:data.toSeq},
                    {label:FieldLabelsConstant.LABELS.DIVISION,value:data.devisionId},
                    {label:FieldLabelsConstant.LABELS.PROTECTION_CROSSOVER,value:data.protectionCrossover},
                    {label:FieldLabelsConstant.LABELS.PROTECTION_TURNOUT,value:data.protectionTurnout},
                    {label:FieldLabelsConstant.LABELS.REMARKS_SHUNTING,value:data.remarksShunting},
                    {label:FieldLabelsConstant.LABELS.REMARKS_NO,value:data.remarksNo},
                    {label:FieldLabelsConstant.LABELS.AUTO_DEAD,value:data.isAutoDead}]
    }
    this.dataViewDialogRef = this.dialog.open(DataViewDialogComponent, {
      disableClose: false,
      height: '400px',
      width: '80%',       
      data:result,  
    });            
}
  switchDialog(elementarySectionCode){
    this.dialog.open(SwitchOperationsComponent, {
      height: '600px',
      width: '80%', 
      data:elementarySectionCode,
    });

  }
}
