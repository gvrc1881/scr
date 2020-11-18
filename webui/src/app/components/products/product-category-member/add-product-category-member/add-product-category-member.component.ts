import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { DateAdapter, MAT_DATE_FORMATS} from '@angular/material';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';


@Component({
  selector: 'app-add-product-category-member',
  templateUrl: './add-product-category-member.component.html',
  providers: [
    {
        provide: DateAdapter, useClass: AppDateAdapter
    },
    {
        provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
    }
    ]
})
export class AddProductCategoryMemberComponent implements OnInit {

  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  resp: any;
  title:string = Constants.EVENTS.ADD;
  addProductCategoryMemberFormGroup: FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  toMinDate = new Date();
  today=new Date();
  currentDate = new Date();
  dateFormat = 'MM-dd-yyyy ';
  scheduleList:any;
  productData:any;
  productCategoryData:any;
  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private sendAndRequestService:SendAndRequestService
  ) {
   
  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
    this.findProductNameList();
    this.findProductCategoryList();
    if (!isNaN(this.id)) {  
      this.updateProductCategoryMemberForm();   
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;
       this.getProductCategoryMemberDataById(this.id);
    } else {
      this.createProductCategoryMemberForm();
      this.save = true;
      this.update = false;
      this.title = Constants.EVENTS.ADD;
    }
  }
  
  
  createProductCategoryMemberForm() {
    this.addProductCategoryMemberFormGroup = this.formBuilder.group({
      id: 0,
      'productCategoryId': [null, Validators.compose([Validators.required, Validators.maxLength(255)])],
      'productId': [null, Validators.compose([Validators.required, Validators.maxLength(255)]),this.duplicateProductId.bind(this)],
      'quantity': [null],
      'fromDate': [null, Validators.required],
      'thruDate': [null],
      'comments': [null]
      
    });
  }
  updateProductCategoryMemberForm() {
    this.addProductCategoryMemberFormGroup = this.formBuilder.group({
      id: 0,
      'productCategoryId': [null, Validators.compose([Validators.required, Validators.maxLength(255)])],
      'productId': [null, Validators.compose([Validators.required, Validators.maxLength(255)]),this.duplicateProductIdProductCategoryIdAndId.bind(this)],
      'quantity': [null],
      'fromDate': [null, Validators.required],
      'thruDate': [null],
      'comments': [null]
      
    });
  }
  

   public get f() { return this.addProductCategoryMemberFormGroup.controls; } 

 
  
  addEvent($event) {
    this.toMinDate = new Date($event.value);
  }
  
  duplicateProductId() {
    const q = new Promise((resolve, reject) => {
       this.sendAndRequestService.requestForGET(
              Constants.app_urls.PRODUCTS.PRODUCT_CATEGORY_MEMBER.EXISTS_PRODUCT_CATEGORYID_PRODUCT_ID +
            this.addProductCategoryMemberFormGroup.controls['productCategoryId'].value + '/'+
            this.addProductCategoryMemberFormGroup.controls['productId'].value
      ).subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicate': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicate': true }); });
    });
    return q;
    }    
    duplicateProductIdProductCategoryIdAndId() {
      let id=this.id;
      let productCategoryId: string = this.addProductCategoryMemberFormGroup.controls['productCategoryId'].value;
      let productId: string = this.addProductCategoryMemberFormGroup.controls['productId'].value;

      const q = new Promise((resolve, reject) => {          

         this.sendAndRequestService.requestForGET(
                Constants.app_urls.PRODUCTS.PRODUCT_CATEGORY_MEMBER.EXISTS_PRODUCT_CATEGORYID_PRODUCTID_AND_ID+id+'/'+productCategoryId+'/'+productId).subscribe
                ((duplicate) => {
          if (duplicate) {
            resolve({ 'duplicateProductIdProductCategoryIdAndId': true });
          } else {
            resolve(null);
          }
        }, () => { resolve({ 'duplicateProductIdProductCategoryIdAndId': true }); });
      });
      return q;
    }
  getProductCategoryMemberDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.PRODUCTS.PRODUCT_CATEGORY_MEMBER.GET_PRODUCT_CATEGORY_MEMBER_ID+id)
    .subscribe((resp) => {
        this.resp = resp;
        this.addProductCategoryMemberFormGroup.patchValue({
          id: this.resp.id,
          productCategoryId: this.resp.productCategoryId,
          productId: this.resp.productId,
          quantity: this.resp.quantity,
          fromDate: new Date(this.resp.fromDate),
          thruDate: !!this.resp.thruDate ? new Date(this.resp.thruDate) : '',
          comments: this.resp.comments,
          
          
        });
      this.toMinDate = new Date(this.resp.fromDate);
        this.spinnerService.hide();
      })
  }
  onAddProductCateMemberFormSubmit() {
    this.isSubmit = true;
    if (this.addProductCategoryMemberFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    if (this.save) {
      var saveProductCategoryMemberModel = {
        "productCategoryId": this.addProductCategoryMemberFormGroup.value.productCategoryId,
        "productId": this.addProductCategoryMemberFormGroup.value.productId,
        "quantity": this.addProductCategoryMemberFormGroup.value.quantity,
        "fromDate": this.addProductCategoryMemberFormGroup.value.fromDate,
        "thruDate": this.addProductCategoryMemberFormGroup.value.thruDate,
        "comments": this.addProductCategoryMemberFormGroup.value.comments,   
        "createdStamp": new Date(),
        "lastUpdatedStamp": new Date(),
      }
      this.sendAndRequestService.requestForPOST(Constants.app_urls.PRODUCTS.PRODUCT_CATEGORY_MEMBER.SAVE_PRODUCT_CATEGORY_MEMBER, saveProductCategoryMemberModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
     
        if (this.resp.code == Constants.CODES.SUCCESS) {
          this.commonService.showAlertMessage("Product CategoryMember Data Saved Successfully");
          this.router.navigate(['../'], { relativeTo: this.route });
        } else {
          this.commonService.showAlertMessage("Product CategoryMember Data Saving Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Product CategoryMember Data Saving Failed.");
      });
    } else if (this.update) {
      var updateProductModel = {
        "id": this.id,
        "productCategoryId": this.addProductCategoryMemberFormGroup.value.productCategoryId,
        "productId": this.addProductCategoryMemberFormGroup.value.productId,
        "quantity": this.addProductCategoryMemberFormGroup.value.quantity,
        "fromDate": this.addProductCategoryMemberFormGroup.value.fromDate,
        "thruDate": this.addProductCategoryMemberFormGroup.value.thruDate,
        "comments": this.addProductCategoryMemberFormGroup.value.comments,   
        "lastUpdatedStamp": new Date(),
      }
      this.sendAndRequestService.requestForPUT(Constants.app_urls.PRODUCTS.PRODUCT_CATEGORY_MEMBER.UPDATE_PRODUCT_CATEGORY_MEMBER, updateProductModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Product Category Member Data Updated Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Product Category Member Data Updating Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Product Category Member Data Updating Failed.");
      })

    }
  }

  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }
  findProductNameList() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.PRODUCTS.PRODUCT.GET_PRODUCT)
      .subscribe((data) => {
        this.productData = data;
      })
  }
  findProductCategoryList() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.PRODUCTS.PRODUCT_CATEGORY.GET_PRODUCT_CATEGORY)
      .subscribe((data) => {
        this.productCategoryData = data;
      })
  }
}