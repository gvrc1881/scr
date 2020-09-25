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
      
     
    }
  
    ngOnInit() {
      this.id = +this.route.snapshot.params['id'];
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
        //'facilityId': [null, Validators.required],
        'productId':[null, Validators.maxLength(255)],
        'rlyId':[null, Validators.maxLength(255)],
        'plNo': [null, Validators.maxLength(255)],
        'description':[null, Validators.maxLength(255)],
        'quantityUomId':[null, Validators.maxLength(255)],
        'materialClassification': [null, Validators.maxLength(255)],
        'productTypeId': [null, Validators.maxLength(255)],
        'primaryProductCategoryId':[null, Validators.maxLength(255)],

        /*'depthUomId': [null, Validators.maxLength(255)],
        'diameterUomId': [null, Validators.maxLength(255)],
        'heightUomId': [null, Validators.maxLength(255)],
        'productCodeTypeId': [null, Validators.maxLength(255)],
        'productName': [null, Validators.maxLength(255)],
        'productDepth': [null],
        'productDiameter': [null],
        'productHeight': [null],
        'productWeight':[null],
        'productWidth':[null],
        'quantityIncluded':[null],*/
        
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
            //facilityId: this.resp.facilityId,
            productId: this.resp.productId,
            rlyId: this.resp.rlyId,
            plNo: this.resp.plNo,
            description: this.resp.description,
            quantityUomId: this.resp.quantityUomId,
            materialClassification: this.resp.materialClassification,
            productTypeId: this.resp.productTypeId,
            primaryProductCategoryId: this.resp.primaryProductCategoryId,

            /*depthUomId: this.resp.depthUomId,
            diameterUomId: this.resp.diameterUomId,
            heightUomId: this.resp.heightUomId,
            productCodeTypeId: this.resp.productCodeTypeId,
            productName: this.resp.productName,
            productDepth: this.resp.productDepth,
            productDiameter: this.resp.productDiameter,
            productHeight: this.resp.productHeight,
            productWeight: this.resp.productWeight,
            productWidth: this.resp.productWidth,
            quantityIncluded: this.resp.quantityIncluded,*/

            
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
         // "facilityId": this.addProductFormGroup.value.facilityId,
         "productId": this.addProductFormGroup.value.productId,
         "rlyId": this.addProductFormGroup.value.rlyId,
         "plNo": this.addProductFormGroup.value.plNo,
         "description": this.addProductFormGroup.value.description,                                                                                                                
         "quantityUomId": this.addProductFormGroup.value.quantityUomId,
         "materialClassification": this.addProductFormGroup.value.materialClassification,
         "productTypeId": this.addProductFormGroup.value.productTypeId,
         "primaryProductCategoryId":this.addProductFormGroup.value.primaryProductCategoryId,

         /* "depthUomId": this.addProductFormGroup.value.depthUomId,
          "diameterUomId": this.addProductFormGroup.value.diameterUomId,
          "heightUomId": this.addProductFormGroup.value.heightUomId,
          "productCodeTypeId": this.addProductFormGroup.value.productCodeTypeId,
          "productName": this.addProductFormGroup.value.productName,
          "productDepth": this.addProductFormGroup.value.productDepth,
          "productDiameter": this.addProductFormGroup.value.productDiameter,
          "productHeight": this.addProductFormGroup.value.productHeight,
          "productWeight": this.addProductFormGroup.value.productWeight,       
          "productWidth": this.addProductFormGroup.value.productWidth,       
          "quantityIncluded": this.addProductFormGroup.value.quantityIncluded,*/
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
            this.commonService.showAlertMessage("product Data Saving Failed.");
          }
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Product Data Saving Failed.");
        });
      } else if (this.update) {
        var updateProductModel = {
          "id": this.id,
          // "facilityId": this.addProductFormGroup.value.facilityId,
         "productId": this.addProductFormGroup.value.productId,
         "rlyId": this.addProductFormGroup.value.rlyId,
         "plNo": this.addProductFormGroup.value.plNo,
         "description": this.addProductFormGroup.value.description,                                                                                                                
         "quantityUomId": this.addProductFormGroup.value.quantityUomId,
         "materialClassification": this.addProductFormGroup.value.materialClassification,
         "productTypeId": this.addProductFormGroup.value.productTypeId,
         "primaryProductCategoryId":this.addProductFormGroup.value.primaryProductCategoryId,

         /* "depthUomId": this.addProductFormGroup.value.depthUomId,
          "diameterUomId": this.addProductFormGroup.value.diameterUomId,
          "heightUomId": this.addProductFormGroup.value.heightUomId,
          "productCodeTypeId": this.addProductFormGroup.value.productCodeTypeId,
          "productName": this.addProductFormGroup.value.productName,
          "productDepth": this.addProductFormGroup.value.productDepth,
          "productDiameter": this.addProductFormGroup.value.productDiameter,
          "productHeight": this.addProductFormGroup.value.productHeight,
          "productWeight": this.addProductFormGroup.value.productWeight,       
          "productWidth": this.addProductFormGroup.value.productWidth,       
          "quantityIncluded": this.addProductFormGroup.value.quantityIncluded,*/                                          
          "updatedBy": this.loggedUserData.username,
          "updatedOn": new Date()
        }
        this.sendAndRequestService.requestForPUT(Constants.app_urls.PRODUCTS.PRODUCT.UPDATE_PRODUCT, updateProductModel, false).subscribe(response => {
          this.spinnerService.hide();
          this.resp = response;
          if (this.resp.code == Constants.CODES.SUCCESS) {
          this.commonService.showAlertMessage("Product Data Updated Successfully");
          this.router.navigate(['../'], { relativeTo: this.route });
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
      this.router.navigate(['../'], { relativeTo: this.route });
    }
  }
  
  
  
  
















