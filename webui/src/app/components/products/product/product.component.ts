import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import{ProductModel} from 'src/app/models/product.model'
import { DataViewDialogComponent } from '../../data-view-dialog/data-view-dialog.component';

@Component({
  selector: 'product',
  templateUrl: './product.component.html',
  styleUrls: []
})
export class ProductComponent implements OnInit {

	addPermission: boolean = true;
  	editPermission: boolean = true;
    deletePermission: boolean = true;
    productList:any;
    filterData;
  	displayedColumns = ['sno','productId','rlyId','plNo','description','quantityUomId','materialClassification','productTypeId','primaryProductCategoryId','actions'];
  	confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  	facilityData: any;
    dataSource: MatTableDataSource<ProductModel>;
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
    private sendAndRequestService:SendAndRequestService
  ) { }
  
  ngOnInit() {
    var permissionName = this.commonService.getPermissionNameByLoggedData("PRODUCTS","PRODUCT") ;
    this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
    this.spinnerService.show();
    this.getProductData();

    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'productId', "Value": " " },
        { "Key": 'rlyId', "Value": " " },
        { "Key": 'plNo', "Value": " " },
        { "Key": 'description', "Value": "" },
        { "Key": 'quantityUomId', "Value": " " },
        { "Key": 'materialClassification', "Value": " " },
        { "Key": 'productTypeId', "Value": "" },
        { "Key": 'primaryProductCategoryId', "Value": " " },  
      ],
      gridData: this.gridData,
      dataSource: this.dataSource,
      paginator: this.paginator,
      sort: this.sort
    };

  }
  getProductData() {
    const product: ProductModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.PRODUCTS.PRODUCT.GET_PRODUCT).subscribe((data) => {
      this.productList = data;
      for (let i = 0; i < this.productList.length; i++) {
        this.productList[i].sno = i + 1;
        product.push(this.productList[i]);
      }
      this.filterData.gridData = product;
      this.dataSource = new MatTableDataSource(product);
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
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.PRODUCTS.PRODUCT.DELETE_PRODUCT, id).subscribe(data => {
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted Product Successfully");
          this.getProductData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Product Deletion failed.");
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
      'title':'Product Data',
      'dataSource':[{label:'Product Id',value:data.productId},{label:'Rly Id',value:data.rlyId},{label:'Pl No',value:data.plNo},
                    {label:'Quantity UomId', value:data.quantityUomId},{label:'Material Classification', value:data.materialClassification},
                    {label:'Product Type Id',value:data.productTypeId},
                    {label:'Primary Product Category Id',value:data.primaryProductCategoryId}]
    }
    this.dataViewDialogRef = this.dialog.open(DataViewDialogComponent, {
      disableClose: false,
      height: '400px',
      width: '80%',       
      data:result,  
    });            
  }

}













