import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';


@Component({
  selector: 'app-add-product-category',
  templateUrl: './add-product-category.component.html',
  styleUrls: []
})
export class AddProductCategoryComponent implements OnInit {

  pagination=Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
  resp: any;
  title:string = Constants.EVENTS.ADD;
  productCateData:any;
  productCateTypeData:any;
  addProductCategoryFormGroup: FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  constructor(
    private formBuilder: FormBuilder,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private route: ActivatedRoute,
    private router: Router,
    private sendAndRequestService:SendAndRequestService
  ) {
    
  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
    this.findProductCategoryList();
    this.findProductCategoryTypeList();
    if (!isNaN(this.id)) { 
      this.updateProductCategoryForm();    
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;
      this.getProductCategoryDataById(this.id);
    } else {
      this.createProductCategoryForm();
      this.save = true;
      this.update = false;
      this.title = Constants.EVENTS.ADD;
    }
    
  }
  
  

  createProductCategoryForm() {
    this.addProductCategoryFormGroup = this.formBuilder.group({
      id: 0,
      'productCategoryId':['', Validators.compose([Validators.required, Validators.maxLength(250)]), this.duplicateProductCategoryId.bind(this)],
      'categoryName': ['', Validators.compose([Validators.required, Validators.maxLength(250)]), this.duplicateCategoryName.bind(this)],
      'productCategoryTypeId':[''],
      'primaryParentCategoryId':[''],
      'description': ['', Validators.maxLength(255)],
    });
  }
  updateProductCategoryForm() {
    this.addProductCategoryFormGroup = this.formBuilder.group({
      id: 0,
      'productCategoryId':['', Validators.compose([Validators.required, Validators.maxLength(250)]), this.duplicateProductCategoryIdByID.bind(this)],
      'categoryName': ['', Validators.compose([Validators.required, Validators.maxLength(250)]), this.duplicateCategoryNameByID.bind(this)],
      'productCategoryTypeId':[''],
      'primaryParentCategoryId':[''],
      'description': ['', Validators.maxLength(255)],
    });
  }
  duplicateProductCategoryId() {
    const q = new Promise((resolve, reject) => {
              
      let productCategoryId: string = this.addProductCategoryFormGroup.controls['productCategoryId'].value;
      
     
      this.sendAndRequestService.requestForGET(Constants.app_urls.PRODUCTS.PRODUCT_CATEGORY.EXISTS_PRODUCT_CATEGORY_ID + productCategoryId)
      .subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateProductCategoryId': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateProductCategoryId': true }); });
    });
    return q;
  }
  duplicateProductCategoryIdByID() {
    const q = new Promise((resolve, reject) => {
      let id=this.id;              
      let productCategoryId: string = this.addProductCategoryFormGroup.controls['productCategoryId'].value;
      this.sendAndRequestService.requestForGET(Constants.app_urls.PRODUCTS.PRODUCT_CATEGORY.EXISTS_PRODUCT_CATEGORYID_BY_ID+id+'/'+productCategoryId)
      .subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateProductCategoryIdByID': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateProductCategoryIdByID': true }); });
    });
    return q;
  }
  duplicateCategoryName() {
    const q = new Promise((resolve, reject) => {
              
      let categoryName: string = this.addProductCategoryFormGroup.controls['categoryName'].value;
      
     
      this.sendAndRequestService.requestForGET(Constants.app_urls.PRODUCTS.PRODUCT_CATEGORY.EXISTS_CATEGORY_NAME  +categoryName)
      .subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateCategoryName': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateCategoryName': true }); });
    });
    return q;
  }
  duplicateCategoryNameByID() {
    const q = new Promise((resolve, reject) => {
      let id=this.id;              
      let categoryName: string = this.addProductCategoryFormGroup.controls['categoryName'].value;
      this.sendAndRequestService.requestForGET(Constants.app_urls.PRODUCTS.PRODUCT_CATEGORY.EXISTS_CATEGORY_NAME_ID+id+'/'+categoryName)
      .subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateCategoryNameByID': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateCategoryNameByID': true }); });
    });
    return q;
  }												
  getProductCategoryDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.PRODUCTS.PRODUCT_CATEGORY.GET_PRODUCT_CATEGORY_ID+id)
    .subscribe((resp) => {
        this.resp = resp;
        this.addProductCategoryFormGroup.patchValue({
          id: this.resp.id,
          productCategoryId: this.resp.productCategoryId,
          categoryName: this.resp.categoryName,
          productCategoryTypeId: this.resp.productCategoryTypeId,
          primaryParentCategoryId: this.resp.primaryParentCategoryId,
          description: this.resp.description,
        });
        this.spinnerService.hide();
      })
  }
  
  ProductCategoryFormSubmit() {
    this.isSubmit = true;
    if (this.addProductCategoryFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    if (this.save) {
      var saveProductModel = {
        "productCategoryId": this.addProductCategoryFormGroup.value.productCategoryId,
        "categoryName": this.addProductCategoryFormGroup.value.categoryName,
        "productCategoryTypeId": this.addProductCategoryFormGroup.value.productCategoryTypeId,
        "primaryParentCategoryId": this.addProductCategoryFormGroup.value.primaryParentCategoryId,
        "description": this.addProductCategoryFormGroup.value.description,                                                                      
        "createdStamp": new Date(),
        "lastUpdatedStamp": new Date()

      }
      this.sendAndRequestService.requestForPOST(Constants.app_urls.PRODUCTS.PRODUCT_CATEGORY.SAVE_PRODUCT_CATEGORY, saveProductModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
     
        if (this.resp.code == Constants.CODES.SUCCESS) {
          this.commonService.showAlertMessage("ProductCategory Data Saved Successfully");
          this.router.navigate(['../'], { relativeTo: this.route });
        } else {
          this.commonService.showAlertMessage("ProductCategory Data Saving Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("ProductCategory Data Saving Failed.");
      });
    } else if (this.update) {
      var updateProductModel = {
        "id": this.id,
        "productCategoryId": this.addProductCategoryFormGroup.value.productCategoryId,
        "categoryName": this.addProductCategoryFormGroup.value.categoryName,
        "productCategoryTypeId": this.addProductCategoryFormGroup.value.productCategoryTypeId,
        "primaryParentCategoryId": this.addProductCategoryFormGroup.value.primaryParentCategoryId,
        "description": this.addProductCategoryFormGroup.value.description,                                         
        "lastUpdatedStamp": new Date()
      }
      this.sendAndRequestService.requestForPUT(Constants.app_urls.PRODUCTS.PRODUCT_CATEGORY.UPDATE_PRODUCT_CATEGORY, updateProductModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Product Category Data Updated Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Product Category Data Updating Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Product Category Data Updating Failed.");
      })

    }
  }

  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }

  findProductCategoryList() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.PRODUCTS.PRODUCT_CATEGORY.GET_PRODUCT_CATEGORY)
      .subscribe((data) => {
        this.productCateData = data;
      })
  }
  findProductCategoryTypeList() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_PRODUCT_CATEGORY_TYPE)
      .subscribe((data) => {
        this.productCateTypeData = data;
      })
  }
}
