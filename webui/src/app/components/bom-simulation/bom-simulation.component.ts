import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { ProductAssociationModel } from 'src/app/models/product-association.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { DataViewDialogComponent } from '../data-view-dialog/data-view-dialog.component';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
@Component({
  selector: 'app-bom-simulation',
  templateUrl: './bom-simulation.component.html',
  styleUrls: []
})
export class BomSimulationComponent implements OnInit {

  pagination=Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  filterData;
  userdata: any = JSON.parse(sessionStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  displayedColumns = ['sno','bomType', 'rlyId', 'productId','fromDate','thruDate', 'actions'];
  dataSource: MatTableDataSource<ProductAssociationModel>;
  dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;


  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
  gridData = [];

  productCategoryList: any;

  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private route: ActivatedRoute,
    public dialog: MatDialog,
    private sendAndRequestService:SendAndRequestService
  ) { }

  ngOnInit() {
    var permissionName = this.commonService.getPermissionNameByLoggedData("PRODUCTS","PRODUCT CATEGORY") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();
    this.getProductCategoryData();
    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'bomType', "Value": " " },
        { "Key": 'rlyId', "Value": " " },
        { "Key": 'productId', "Value": " " },
        { "Key": 'fromDate', "Value": " " },
        { "Key": 'thruDate', "Value": " " },

      ],
      gridData: this.gridData,
      dataSource: this.dataSource,
      paginator: this.paginator,
      sort: this.sort
    };

  }
  getProductCategoryData() {
    const productCategory: ProductAssociationModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.INSPECTION.PRODUCT_ASSOCIATION.GET_PRODUCT_ASSOCIATION).subscribe((data) => {
      this.productCategoryList = data;
      for (let i = 0; i < this.productCategoryList.length; i++) {
        this.productCategoryList[i].sno = i + 1;
        productCategory.push(this.productCategoryList[i]);
      }

      this.filterData.gridData = productCategory;
      this.dataSource = new MatTableDataSource(productCategory);
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
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.INSPECTION.PRODUCT_ASSOCIATION.DELETE_PRODUCT_ASSOCIATION ,id).subscribe(data => {
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted Bom Simulation Successfully");
          this.getProductCategoryData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Bom Simulation Deletion Failed.");
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
      'title':this.Titles.BOM_SIMULATION_DATA,
      'dataSource':[
                  
                    { label:FieldLabelsConstant.LABELS.RLY_ID, value:data.productIdTo.rlyId },
                    { label:FieldLabelsConstant.LABELS.BOM_TYPE, value:data.productId.productId },
                    { label:FieldLabelsConstant.LABELS.FROM_DATE, value:data.fromDate },
                    { label:FieldLabelsConstant.LABELS.TO_DATE, value:data.thruDate },
                    { label:FieldLabelsConstant.LABELS.REASON, value:data.reason },
                    { label:FieldLabelsConstant.LABELS.QUANTITY, value:data.quantity },
                    { label:FieldLabelsConstant.LABELS.SCRAP_FACTOR, value:data.scrapFactor },                 
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
