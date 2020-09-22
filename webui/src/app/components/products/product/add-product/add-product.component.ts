import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FacilityModel } from 'src/app/models/facility.model';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: []
})
export class AddProductComponent implements OnInit {
    loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
    save: boolean = true;
    update: boolean = false;
    title: string = '';
    isSubmit: boolean = false;
    addProductFormGroup: FormGroup;
    id: number = 0;
    pattern = "/^[a-zA-Z ]*$/";
    driveFormErrors: any;
    resp: any;
    scheduleList:any;
    depotData:any;
    constructor(
      private formBuilder: FormBuilder,
      private route: ActivatedRoute,
      private spinnerService: Ng4LoadingSpinnerService,
      private commonService: CommonService,
      private router: Router,
      private sendAndRequestService:SendAndRequestService
    ) {
      // Reactive form errors
     
    }
  
    ngOnInit() {
      this.id = +this.route.snapshot.params['id'];
      this.findFacilityNameList();
      this.createProductForm();
      if (!isNaN(this.id)) {     
        this.spinnerService.show();
        this.save = false;
        this.update = true;
        this.title = 'Edit';
         this.getProductDataById(this.id);
      } else {
        this.save = true;
        this.update = false;
        this.title = 'Save';
      }
    }  
    
    createProductForm() {
      this.addProductFormGroup = this.formBuilder.group({
        id: 0,
        'facilityId': [null, Validators.required],
        'depthUomId': [null, Validators.maxLength(255)],
        'diameterUomId': [null, Validators.maxLength(255)],
        'heightUomId': [null, Validators.maxLength(255)],
        'materialClassification': [null, Validators.maxLength(255)],
        'plNo': [null, Validators.maxLength(255)],
        'primaryProductCategoryId':[null, Validators.maxLength(255)],
        'productCodeTypeId': [null, Validators.maxLength(255)],
        'productTypeId': [null, Validators.maxLength(255)],
        'productId':[null, Validators.maxLength(255)],
        'productName': [null, Validators.maxLength(255)],
        'productDepth': [null],
        'productDiameter': [null],
        'productHeight': [null],
        'productWeight':[null],
        'productWidth':[null],
        'quantityIncluded':[null],
        'quantityUomId':[null, Validators.maxLength(255)],
        'rlyId':[null, Validators.maxLength(255)],
        'description':[null, Validators.maxLength(255)],
        
      });
    }
    
  
     public get f() { return this.addProductFormGroup.controls; } 

     findFacilityNameList() {
        this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY_NAMES)
          .subscribe((data) => {
            this.depotData = data;
          })
      }
  
   
    getProductDataById(id) {
      this.sendAndRequestService.requestForGET(Constants.app_urls.PRODUCTS.PRODUCT.GET_PRODUCT_ID+id)
      .subscribe((resp) => {
          this.resp = resp;
          this.addProductFormGroup.patchValue({
            id: this.resp.id,
            facilityId: this.resp.facilityId,
            depthUomId: this.resp.depthUomId,
            diameterUomId: this.resp.diameterUomId,
            heightUomId: this.resp.heightUomId,
            materialClassification: this.resp.materialClassification,
            plNo: this.resp.plNo,
            primaryProductCategoryId: this.resp.primaryProductCategoryId,
            productCodeTypeId: this.resp.productCodeTypeId,
            productTypeId: this.resp.productTypeId,
            productId: this.resp.productId,
            productName: this.resp.productName,
            productDepth: this.resp.productDepth,
            productDiameter: this.resp.productDiameter,
            productHeight: this.resp.productHeight,
            productWeight: this.resp.productWeight,
            productWidth: this.resp.productWidth,
            quantityIncluded: this.resp.quantityIncluded,
            quantityUomId: this.resp.quantityUomId,
            rlyId: this.resp.rlyId,
            description: this.resp.description,

            
          });
          this.spinnerService.hide();
        })
    }
    ProductFormSubmit() {
      this.isSubmit = true;
      if (this.addProductFormGroup.invalid) {
        this.isSubmit = false;
        return;
      }
      this.spinnerService.show();
      if (this.save) {
        var saveProductModel = {
          "facilityId": this.addProductFormGroup.value.facilityId,
          "depthUomId": this.addProductFormGroup.value.depthUomId,
          "diameterUomId": this.addProductFormGroup.value.diameterUomId,
          "heightUomId": this.addProductFormGroup.value.heightUomId,
          "materialClassification": this.addProductFormGroup.value.materialClassification,
          "plNo": this.addProductFormGroup.value.plNo,
          "primaryProductCategoryId":this.addProductFormGroup.value.primaryProductCategoryId,
          "productCodeTypeId": this.addProductFormGroup.value.productCodeTypeId,
          "productTypeId": this.addProductFormGroup.value.productTypeId,
          "productId": this.addProductFormGroup.value.productId,
          "productName": this.addProductFormGroup.value.productName,
          "productDepth": this.addProductFormGroup.value.productDepth,
          "productDiameter": this.addProductFormGroup.value.productDiameter,
          "productHeight": this.addProductFormGroup.value.productHeight,
          "productWeight": this.addProductFormGroup.value.productWeight,       
          "productWidth": this.addProductFormGroup.value.productWidth,       
          "quantityIncluded": this.addProductFormGroup.value.quantityIncluded,
          "quantityUomId": this.addProductFormGroup.value.quantityUomId,
          "rlyId": this.addProductFormGroup.value.rlyId,
          "description": this.addProductFormGroup.value.description,                                                                                                                
          "createdByUserLogin": this.loggedUserData.username,
          "createdDate": new Date()
        }
        this.sendAndRequestService.requestForPOST(Constants.app_urls.PRODUCTS.PRODUCT.SAVE_PRODUCT, saveProductModel, false).subscribe(response => {
          this.spinnerService.hide();
          this.resp = response;
       
          if (this.resp.code == Constants.CODES.SUCCESS) {
            this.commonService.showAlertMessage("Product Data Saved Successfully");
            this.router.navigate(['../'], { relativeTo: this.route });
          } else {
            this.commonService.showAlertMessage("Product Data Saving Failed.");
          }
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Drive Data Saving Failed.");
        });
      } else if (this.update) {
        var updateProductModel = {
          "id": this.id,
          "facilityId": this.addProductFormGroup.value.facilityId,
          "depthUomId": this.addProductFormGroup.value.depthUomId,
          "diameterUomId": this.addProductFormGroup.value.diameterUomId,
          "heightUomId": this.addProductFormGroup.value.heightUomId,
          "materialClassification": this.addProductFormGroup.value.materialClassification,
          "plNo": this.addProductFormGroup.value.plNo,
          "primaryProductCategoryId":this.addProductFormGroup.value.primaryProductCategoryId,
          "productCodeTypeId": this.addProductFormGroup.value.productCodeTypeId,
          "productTypeId": this.addProductFormGroup.value.productTypeId,
          "productId": this.addProductFormGroup.value.productId,
          "productName": this.addProductFormGroup.value.productName,
          "productDepth": this.addProductFormGroup.value.productDepth,
          "productDiameter": this.addProductFormGroup.value.productDiameter,
          "productHeight": this.addProductFormGroup.value.productHeight,   
          "productWeight": this.addProductFormGroup.value.productWeight,       
          "productWidth": this.addProductFormGroup.value.productWidth,       
          "quantityIncluded": this.addProductFormGroup.value.quantityIncluded, 
          "quantityUomId": this.addProductFormGroup.value.quantityUomId,           
          "rlyId": this.addProductFormGroup.value.rlyId,
          "description": this.addProductFormGroup.value.description,                                          
          "updatedBy": this.loggedUserData.username,
          "updatedOn": new Date()
        }
        this.sendAndRequestService.requestForPUT(Constants.app_urls.PRODUCTS.PRODUCT.UPDATE_PRODUCT, updateProductModel, false).subscribe(response => {
          this.spinnerService.hide();
          this.resp = response;
          if (this.resp.code == Constants.CODES.SUCCESS) {
          this.commonService.showAlertMessage("Product Data Updated Successfully");
          this.router.navigate(['../../'], { relativeTo: this.route });
          }else{
            this.commonService.showAlertMessage("Product Data Updating Failed.");
          }
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Product Data Updating Failed.");
        })
  
      }
    }
  
    onGoBack() {
      if (this.save) {
        this.router.navigate(['../'], { relativeTo: this.route });
      } else if (this.update) {
        this.router.navigate(['../../'], { relativeTo: this.route });
      }
    }
  }
  
  
  
  
















