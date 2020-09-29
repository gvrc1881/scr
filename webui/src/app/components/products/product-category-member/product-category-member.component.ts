import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { ProductCategoryMemberModel } from 'src/app/models/product.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { DataViewDialogComponent } from '../../data-view-dialog/data-view-dialog.component';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-product-category-member',
  templateUrl: './product-category-member.component.html',
  styleUrls: []
})
export class ProductCategoryMemberComponent implements OnInit {

  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  displayedColumns = ['sno', 'productCategoryId', 'productId','quantity', 'fromDate', 'thruDate','actions'];
  dataSource: MatTableDataSource<ProductCategoryMemberModel>;
  dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;


  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;

  categoryMemberList: any;

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
    var permissionName = this.commonService.getPermissionNameByLoggedData("PRODUCTS","PRODUCT CATEGORY MEMBER") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();
    this.getProductCategoryMemberData();

  }
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }
  getProductCategoryMemberData() {
    const categoryMember: ProductCategoryMemberModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.PRODUCTS.PRODUCT_CATEGORY_MEMBER.GET_PRODUCT_CATEGORY_MEMBER).subscribe((data) => {
      this.categoryMemberList = data;
      for (let i = 0; i < this.categoryMemberList.length; i++) {
        this.categoryMemberList[i].sno = i + 1;
        this.categoryMemberList[i].fromDate = this.datePipe.transform(this.categoryMemberList[i].fromDate, 'dd-MM-yyyy hh:mm:ss');
                this.categoryMemberList[i].thruDate = this.datePipe.transform(this.categoryMemberList[i].thruDate, 'dd-MM-yyyy hh:mm:ss');
        categoryMember.push(this.categoryMemberList[i]);
      }

      this.dataSource = new MatTableDataSource(categoryMember);
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
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.PRODUCTS.PRODUCT_CATEGORY_MEMBER.DELETE_PRODUCT_CATEGORY_MEMBER ,id).subscribe(data => {
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted Product Category Member Successfully");
          this.getProductCategoryMemberData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Product Category Member Deletion Failed.");
        });
      }
      this.confirmDialogRef = null;
    });
  }
  ViewData(data){
    var result = {
      'title':'Product Category Data',
      'dataSource':[{label:'Product CategoryId',value:data.productCategoryId},{label:'ProductId',value:data.productId},
                    {label:'Quantity', value:data.quantity}]
    }
    this.dataViewDialogRef = this.dialog.open(DataViewDialogComponent, {
      disableClose: false,
      height: '400px',
      width: '80%',       
      data:result,  
    });            
  }
}
