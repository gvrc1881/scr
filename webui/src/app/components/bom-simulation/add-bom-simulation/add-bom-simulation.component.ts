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
  selector: 'app-add-bom-simulation',
  templateUrl: './add-bom-simulation.component.html',
  providers: [
    {
        provide: DateAdapter, useClass: AppDateAdapter
    },
    {
        provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
    }
    ]
})
export class AddBomSimulationComponent implements OnInit {

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
  selectedProduct:any;
  onlyYes:boolean;
  productData:any;
  selectedproductIdTo:any;
  addBomSimulationFormGroup: FormGroup;
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
    this.findProductNameList();
    if (!isNaN(this.id)) { 
      this.updateBomSimulationForm();    
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;
      this.getBomSimulationDataById(this.id);
    } else {
      this.createBomSimulationForm();
      this.save = true;
      this.update = false;
      this.title = Constants.EVENTS.ADD;
    }
    
  }
  
  

  createBomSimulationForm() {
    this.addBomSimulationFormGroup = this.formBuilder.group({
      id: 0,
      'productId':['', Validators.compose([Validators.required])],
      'productAssocTypeId': [''],
      'fromDate':[''],
      'quantity':[''],
    });
  }
  updateBomSimulationForm() {
    this.onlyYes = true;
    this.addBomSimulationFormGroup = this.formBuilder.group({
      id: 0,
      'productId':['', Validators.compose([Validators.required])],
      'productIdTo':['', Validators.compose([Validators.required])],
      'productAssocTypeId': [''],
      'fromDate':[''],
      'thruDate':[''],
      'sequenceNum':[''],
      'reason': [null,Validators.compose([Validators.required, Validators.maxLength(255)])],
      'quantity':[''],
      'scrapFactor':[''],
    });
  }
  												
  getBomSimulationDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.INSPECTION.PRODUCT_ASSOCIATION.GET_PRODUCT_ASSOCIATION_ID+id)
    .subscribe((resp) => {
        this.resp = resp;
        this.addBomSimulationFormGroup.patchValue({
          id: this.resp.id,
          productId: this.resp.productId.id,
          productAssocTypeId: this.resp.productAssocTypeId,
          fromDate: this.resp.fromDate,
          thruDate: this.resp.thruDate,
          sequenceNum: this.resp.sequenceNum,
          reason: this.resp.reason,
          quantity: this.resp.quantity,
          scrapFactor: this.resp.scrapFactor
        });
        this.spinnerService.hide();
        this.getProductName();
        this.getProductIdName();
      })
  }
  
  BomSimulationFormSubmit() {
    this.isSubmit = true;
    if (this.addBomSimulationFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    if (this.save) {
      var saveBomModel = {
        "productId": this.selectedProduct,
        "productAssocTypeId": this.selectedproductIdTo,
        "fromDate": this.addBomSimulationFormGroup.value.fromDate,
        "quantity": this.addBomSimulationFormGroup.value.quantity,

      }
      this.sendAndRequestService.requestForPOST(Constants.app_urls.INSPECTION.PRODUCT_ASSOCIATION.SAVE_PRODUCT_ASSOCIATION, saveBomModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
     
        if (this.resp.code == Constants.CODES.SUCCESS) {
          this.commonService.showAlertMessage("Bom Simulation Saved Successfully");
          this.router.navigate(['../'], { relativeTo: this.route });
        } else {
          this.commonService.showAlertMessage("Bom Simulation Saving Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Bom Simulation Saving Failed.");
      });
    } else if (this.update) {
      var updateBomModel = {
        "id": this.id,
        "productId": this.selectedProduct,
        "productIdTo":this.selectedproductIdTo,
        "fromDate": this.addBomSimulationFormGroup.value.fromDate,
        "thruDate": this.addBomSimulationFormGroup.value.thruDate,
        "sequenceNum": this.addBomSimulationFormGroup.value.sequenceNum,
        "reason": this.addBomSimulationFormGroup.value.reason,
        "quantity": this.addBomSimulationFormGroup.value.quantity,
        "scrapFactor": this.addBomSimulationFormGroup.value.scrapFactor,                                       
      }
      this.sendAndRequestService.requestForPUT(Constants.app_urls.INSPECTION.PRODUCT_ASSOCIATION.UPDATE_PRODUCT_ASSOCIATION, updateBomModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Bom Simulation Updated Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Bom Simulation Updating Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Bom Simulation Updating Failed.");
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

  getProductName(){
    this.sendAndRequestService.requestForGET(Constants.app_urls.PRODUCTS.PRODUCT.GET_PRODUCT_ID+this.addBomSimulationFormGroup.value.productId).subscribe((data) => {
         this.selectedProduct = data;
     });
  }
  getProductIdName(){
    this.sendAndRequestService.requestForGET(Constants.app_urls.PRODUCTS.PRODUCT.GET_PRODUCT_ID+this.addBomSimulationFormGroup.value.productIdTo).subscribe((data) => {
         this.selectedproductIdTo = data;
     });
  }
}
