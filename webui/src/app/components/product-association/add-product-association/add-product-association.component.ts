import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { DateAdapter, MAT_DATE_FORMATS} from '@angular/material';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';

@Component({
  selector: 'app-add-product-association',
  templateUrl: './add-product-association.component.html',
  providers: [
    {
        provide: DateAdapter, useClass: AppDateAdapter
    },
    {
        provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
    }
    ]
})
export class AddProductAssociationComponent implements OnInit {

  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
  resp: any;
  title:string = Constants.EVENTS.ADD;
  addProductAssociationFormGroup: FormGroup;
  productAssociationFormErrors:any;
  productData:any;
  productId:any;
  productIdTo:any;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  toMinDate = new Date();
  today=new Date();
  currentDate = new Date();
  dateFormat = 'MM-dd-yyyy ';

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private sendAndRequestService:SendAndRequestService
  ) {
    this.productAssociationFormErrors = {            
      name:{},
      makeCode: {},
      modelCode: {},
      description:{}
    };
  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
    this.productDetails();
    if (!isNaN(this.id)) {  
      this.updateProductAssociationForm();  
      this.addProductAssociationFormGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      }); 
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;
       this.getProductAssociationDataById(this.id);
    } else {
      this.createProductAssociationForm();
      this.save = true;
      this.update = false;
      this.title = Constants.EVENTS.ADD;
    }
  }
  addEvent($event) {
    this.toMinDate = new Date($event.value);
  }
  onFormValuesChanged() {
    for (const field in this.productAssociationFormErrors) {
      if (!this.productAssociationFormErrors.hasOwnProperty(field)) {
        continue;
      }
      this.productAssociationFormErrors[field] = {};
      const control = this.addProductAssociationFormGroup.get(field);
      if (control && control.dirty && !control.valid) {
        this.productAssociationFormErrors[field] = control.errors;
      }
    }
  }
  createProductAssociationForm() {
    this.addProductAssociationFormGroup = this.formBuilder.group({
      id: 0,
      'productId':[null,Validators.compose([Validators.required])],
      'productIdTo': [null],
      //'productAssocTypeId': [null],
      'fromDate': [null],
      'thruDate': [null],
      'reason': [null],
      'quantity': [null],
      'scrapFactor': [null],
      //'instruction': [null],
    });
  }
  updateProductAssociationForm() {
    this.addProductAssociationFormGroup = this.formBuilder.group({
      id: 0,
      'productId':[null,Validators.compose([Validators.required])],
      'productIdTo': [null],
      //'productAssocTypeId': [null],
      'fromDate': [null],
      'thruDate': [null],
      'reason': [null,Validators.compose([Validators.required, Validators.maxLength(255)])],
      'quantity': [null],
      'scrapFactor': [null],
      //'instruction': [null],
    });
  }
  

   public get f() { return this.addProductAssociationFormGroup.controls; } 
   
  getProductAssociationDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.INSPECTION.TEST_INSPECTION.GET_TEST_INSPECTION_BY_ID+id)
    .subscribe((resp) => {
        this.resp = resp;
        this.addProductAssociationFormGroup.patchValue({
          id: this.resp.id,
          productId: this.resp.productId,
          productIdTo: this.resp.makeCode.productIdTo,
          //productAssocTypeId: this.resp.modelCode.productAssocTypeId,
          fromDate: this.resp.fromDate,
          thruDate: this.resp.thruDate,
          reason: this.resp.reason,
          quantity: this.resp.quantity,
          scrapFactor: this.resp.scrapFactor,
          //instruction: this.resp.instruction,
        });
        this.spinnerService.hide();
        this.getProductDetails();
        this.getProductIdDetails();
      })
  }
  onAddProductAssociationFormSubmit() {
    this.isSubmit = true;
    if (this.addProductAssociationFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    if (this.save) {
      var saveProductAssocModel = {
        "productId": this.productId,
        "productIdTo": this.productIdTo,
        //"productAssocTypeId": this.addProductAssociationFormGroup.value.productAssocTypeId,
        "fromDate": this.addProductAssociationFormGroup.value.fromDate,
        "thruDate": this.addProductAssociationFormGroup.value.thruDate,  
        "reason": this.addProductAssociationFormGroup.value.reason,  
        "quantity": this.addProductAssociationFormGroup.value.quantity,  
        "scrapFactor": this.addProductAssociationFormGroup.value.scrapFactor, 
        //"instruction": this.addProductAssociationFormGroup.value.instruction, 
        
      }
      this.sendAndRequestService.requestForPOST(Constants.app_urls.INSPECTION.PRODUCT_ASSOCIATION.SAVE_PRODUCT_ASSOCIATION, saveProductAssocModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
     
        if (this.resp.code == Constants.CODES.SUCCESS) {
          this.commonService.showAlertMessage("Product Association  Data Saved Successfully");
          this.router.navigate(['../'], { relativeTo: this.route });
        } else {
          this.commonService.showAlertMessage("Product Association Data Saving Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Product Association Data Saving Failed.");
      });
    } else if (this.update) {
      var updateProductAssociationModel = {
        "id": this.id,
        "productId": this.productId,
        "productIdTo": this.productIdTo,
        "fromDate": this.addProductAssociationFormGroup.value.fromDate,
        "thruDate": this.addProductAssociationFormGroup.value.thruDate,  
        "reason": this.addProductAssociationFormGroup.value.reason,  
        "quantity": this.addProductAssociationFormGroup.value.quantity,  
        "scrapFactor": this.addProductAssociationFormGroup.value.scrapFactor, 
        //"instruction": this.addProductAssociationFormGroup.value.instruction, 
      }
      this.sendAndRequestService.requestForPUT(Constants.app_urls.INSPECTION.PRODUCT_ASSOCIATION.UPDATE_PRODUCT_ASSOCIATION, updateProductAssociationModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Product Association Data Updated Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Product Association Data Updating Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Product Association Data Updating Failed.");
      })

    }
  }

  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }
  getProductDetails() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.PRODUCTS.PRODUCT.GET_PRODUCT_ID+this.addProductAssociationFormGroup.value.productId).subscribe((data) => {
         this.productId = data;
     });
  }
  getProductIdDetails() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.PRODUCTS.PRODUCT.GET_PRODUCT_ID+this.addProductAssociationFormGroup.value.productIdTo).subscribe((data) => {
         this.productIdTo = data;
     });
  }
  productDetails()
  {  
         this.sendAndRequestService.requestForGET(Constants.app_urls.PRODUCTS.PRODUCT.GET_PRODUCT).subscribe((data) => {
           this.productData = data;
  }
         );

 }
 
 
}