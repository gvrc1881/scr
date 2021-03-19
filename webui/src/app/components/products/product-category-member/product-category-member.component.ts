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
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';

@Component({
  selector: 'app-product-category-member',
  templateUrl: './product-category-member.component.html',
  styleUrls: []
})
export class ProductCategoryMemberComponent implements OnInit {

  pagination =Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(sessionStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  displayedColumns = ['sno', 'productCategoryId', 'productId','quantity', 'fromDate', 'thruDate','actions'];
  dataSource: MatTableDataSource<ProductCategoryMemberModel>;
  dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
  filterData;


  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
  gridData = [];

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
    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'productCategoryId', "Value": " " },
        { "Key": 'productId', "Value": " " },
        { "Key": 'quantity', "Value": " " },
        { "Key": 'fromDate', "Value": "" },
        { "Key": 'thruDate', "Value": " " },
      ],
      gridData: this.gridData,
      dataSource: this.dataSource,
      paginator: this.paginator,
      sort: this.sort
    };


  }
  getProductCategoryMemberData() {
    const categoryMember: ProductCategoryMemberModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.PRODUCTS.PRODUCT_CATEGORY_MEMBER.GET_PRODUCT_CATEGORY_MEMBER).subscribe((data) => {
      this.categoryMemberList = data;
      for (let i = 0; i < this.categoryMemberList.length; i++) {
        this.categoryMemberList[i].sno = i + 1;
        this.categoryMemberList[i].fromDate = this.datePipe.transform(this.categoryMemberList[i].fromDate, 'dd-MM-yyyy');
                this.categoryMemberList[i].thruDate = this.datePipe.transform(this.categoryMemberList[i].thruDate, 'dd-MM-yyyy');
        categoryMember.push(this.categoryMemberList[i]);
      }
      this.filterData.gridData = categoryMember;
      this.dataSource = new MatTableDataSource(categoryMember);
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
      'title':this.Titles.PRODUCT_CATEGORY_MEMBER_DATA,
      'dataSource':[                                 
                    { label:FieldLabelsConstant.LABELS.PRODUCT_CATEGORY_ID, value:data.productCategoryId },
                    { label:FieldLabelsConstant.LABELS.PRODUCT_ID, value:data.productId.productId },
                    { label:FieldLabelsConstant.LABELS.QUANTITY, value:data.quantity },
                    { label:FieldLabelsConstant.LABELS.FROM_DATE, value:data.fromDate },
                    { label:FieldLabelsConstant.LABELS.TO_DATE, value:data.thruDate },
                    
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
