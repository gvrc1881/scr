import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog,DateAdapter, MAT_DATE_FORMATS} from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import{SidingsModel} from 'src/app/models/sidings.model';
import { DataViewDialogComponent } from '../data-view-dialog/data-view-dialog.component';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'siding-details',
  templateUrl: './siding-details.component.html',
  providers: [
    {
        provide: DateAdapter, useClass: AppDateAdapter
    },
    {
        provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
    }
    ]
})
export class SidingDetailsComponent implements OnInit {

	addPermission: boolean = true;
  	editPermission: boolean = true;
    deletePermission: boolean = true;
    sidingsList:any;
    filterData;
  	displayedColumns = ['sno' ,'station' , 'sidingCode' , 'section' , 'sectionEletrifiedStatus' , 'sidingEletrifiedStatus' , 
    'privateRailway' ,'sidingProposed','proposedDate','approvalDate',
    'workOrderDate','completionDate','actions' ] ;;
  	confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  	facilityData: any;
    dataSource: MatTableDataSource<SidingsModel>;
    gridData = [];
  	@ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  	@ViewChild(MatSort, { static: true }) sort: MatSort;
  	@ViewChild('filter', { static: true }) filter: ElementRef;  	
    dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;

  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private route: ActivatedRoute,
    public dialog: MatDialog,
    private datePipe: DatePipe,
    private sendAndRequestService:SendAndRequestService
  ) { }
  
  ngOnInit() {
    var permissionName = this.commonService.getPermissionNameByLoggedData("ASSET REGISTER","Sidings") ;
    this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
    this.spinnerService.show();
    this.getSidingsData();

    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'station', "Value": " " },
        { "Key": 'sidingCode', "Value": " " },
        { "Key": 'section', "Value": " " },
        { "Key": 'sectionEletrifiedStatus', "Value": "" },
        { "Key": 'sidingEletrifiedStatus', "Value": " " },
        { "Key": 'privateRailway', "Value": " " },
        { "Key": 'sidingProposed', "Value": "" },
        { "Key": 'proposedDate', "Value": " " },
        { "Key": 'approvalDate', "Value": " " },
        { "Key": 'workOrderDate', "Value": " " },
        { "Key": 'completionDate', "Value": " " },  
      ],
      gridData: this.gridData,
      dataSource: this.dataSource,
      paginator: this.paginator,
      sort: this.sort
    };

  }
  getSidingsData() {
    const sidings: SidingsModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.SIDINGS.GET_SIDINGS).subscribe((data) => {
      this.sidingsList = data;
      for (let i = 0; i < this.sidingsList.length; i++) {
        this.sidingsList[i].sno = i + 1;
        this.sidingsList[i].proposedDate = this.datePipe.transform(this.sidingsList[i].proposedDate, 'dd-MM-yyyy');
                this.sidingsList[i].approvalDate = this.datePipe.transform(this.sidingsList[i].approvalDate, 'dd-MM-yyyy');
                this.sidingsList[i].workOrderDate = this.datePipe.transform(this.sidingsList[i].workOrderDate, 'dd-MM-yyyy');
                this.sidingsList[i].completionDate = this.datePipe.transform(this.sidingsList[i].completionDate, 'dd-MM-yyyy');
        sidings.push(this.sidingsList[i]);
      }
      this.filterData.gridData = sidings;
      this.dataSource = new MatTableDataSource(sidings);
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
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.ENERGY_BILL_PAYMENTS.SIDINGS.DELETE_SIDINGS, id).subscribe(data => {
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted Sidings Successfully");
          this.getSidingsData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Sidings Deletion failed.");
        })
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
      'title':'Sidings  Data',
      'dataSource':[{label:'Station',value:data.station},{label:'Siding Code',value:data.sidingCode},{label:'Section',value:data.section},
                    {label:'Section Eletrified Status', value:data.sectionEletrifiedStatus},{label:'Siding Eletrified Status', value:data.sidingEletrifiedStatus},{label:'Private Railway', value:data.privateRailway},
                    {label:'status', value:data.status},{label:'Tkm', value:data.tkm},{label:'Remarks', value:data.remarks},
                    {label:'proposedDate', value:data.proposedDate},{label:'ApprovalDate', value:data.approvalDate},{label:'WorkOrderDate', value:data.workOrderDate}, ]
    }
    this.dataViewDialogRef = this.dialog.open(DataViewDialogComponent, {
      disableClose: false,
      height: '400px',
      width: '80%',       
      data:result,  
    });            
  }

}













