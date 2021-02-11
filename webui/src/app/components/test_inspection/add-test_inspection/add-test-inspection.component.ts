import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';


@Component({
  selector: 'app-add-test-inspection',
  templateUrl: './add-test-inspection.component.html'
})
export class AddTestInspectionComponent implements OnInit {

  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
  resp: any;
  title:string = Constants.EVENTS.ADD;
  addTestInspectionFormGroup: FormGroup;
  testInspectionFormErrors:any;
  makeData:any;
  makeCode:any;
  modelCode:any;
  modelData:any;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  specialWorksData:any;
  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private sendAndRequestService:SendAndRequestService
  ) {
    this.testInspectionFormErrors = {            
      name:{},
      makeCode: {},
      modelCode: {},
      description:{}
    };
  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
    this.modelDetails();
    this.makeDetails();
    if (!isNaN(this.id)) {  
      this.updateTestInspectionForm();  
      this.addTestInspectionFormGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      }); 
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;
       this.getTestInspectionDataById(this.id);
    } else {
      this.createTestInspectionForm();
      this.save = true;
      this.update = false;
      this.title = Constants.EVENTS.ADD;
    }
  }
  
  onFormValuesChanged() {
    for (const field in this.testInspectionFormErrors) {
      if (!this.testInspectionFormErrors.hasOwnProperty(field)) {
        continue;
      }
      this.testInspectionFormErrors[field] = {};
      const control = this.addTestInspectionFormGroup.get(field);
      if (control && control.dirty && !control.valid) {
        this.testInspectionFormErrors[field] = control.errors;
      }
    }
  }
  createTestInspectionForm() {
    this.addTestInspectionFormGroup = this.formBuilder.group({
      id: 0,
      'name':[null,Validators.compose([Validators.required, Validators.maxLength(255)])],
      'makeCode': [null],
      'modelCode': [null,this.duplicateNameMakeCodeAndModelCode.bind(this)],
      'description': [null, Validators.compose([Validators.required, Validators.maxLength(255)]),this.duplicateMakeCodeModelCodeAndDescri.bind(this)]
      
    });
  }
  updateTestInspectionForm() {
    this.addTestInspectionFormGroup = this.formBuilder.group({
      id: 0,
      'name':[null, Validators.compose([Validators.required, Validators.maxLength(255)])],
      'makeCode': [null],
      'modelCode': [null],
      'description': [null, Validators.compose([Validators.required, Validators.maxLength(255)]),this.duplicateMakeCodeAndModelCodeDescriAndId.bind(this)]
    });
  }
  

   public get f() { return this.addTestInspectionFormGroup.controls; } 
   
  getTestInspectionDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.INSPECTION.TEST_INSPECTION.GET_TEST_INSPECTION_BY_ID+id)
    .subscribe((resp) => {
        this.resp = resp;
        let makeCode = this.resp.makeCode
        this.addTestInspectionFormGroup.patchValue({
          id: this.resp.id,
          name: this.resp.name,
          makeCode: this.resp.makeCode.id,
          modelCode: this.resp.modelCode.id,
          description: this.resp.description,
        });
        this.spinnerService.hide();
        this.getMakeData();
        this.getModelData();
      })
  }
  onAddTestInspectionFormSubmit() {
    this.isSubmit = true;
    if (this.addTestInspectionFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    if (this.save) {
      var saveTestInspectionModel = {
        "name": this.addTestInspectionFormGroup.value.name,
        "makeCode": this.makeCode,
        "modelCode": this.modelCode,
        "description": this.addTestInspectionFormGroup.value.description  
        
      }
      this.sendAndRequestService.requestForPOST(Constants.app_urls.INSPECTION.TEST_INSPECTION.SAVE_TEST_INSPECTION, saveTestInspectionModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
     
        if (this.resp.code == Constants.CODES.SUCCESS) {
          this.commonService.showAlertMessage("Test Inspection  Data Saved Successfully");
          this.router.navigate(['../'], { relativeTo: this.route });
        } else {
          this.commonService.showAlertMessage("Test Inspection Data Saving Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Test Inspection Data Saving Failed.");
      });
    } else if (this.update) {
      var updateTestInspectionModel = {
        "id": this.id,
        "name": this.addTestInspectionFormGroup.value.name,
        "makeCode": this.makeCode,
        "modelCode": this.modelCode,
        "description": this.addTestInspectionFormGroup.value.description   
      }
      this.sendAndRequestService.requestForPUT(Constants.app_urls.INSPECTION.TEST_INSPECTION.UPDATE_TEST_INSPECTION, updateTestInspectionModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Test Inspection Data Updated Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Test Inspection Data Updating Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Test Inspection Data Updating Failed.");
      })

    }
  }

  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }
  getMakeData() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.CONFIG.MAKE.GET_MAKE_ID+this.addTestInspectionFormGroup.value.makeCode).subscribe((data) => {
         this.makeCode = data;
     });
  }
  getModelData() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.CONFIG.MODEL.GET_MODEL_ID+this.addTestInspectionFormGroup.value.modelCode).subscribe((data) => {
         this.modelCode = data;
     });
  }
  makeDetails()
  {  
         this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_MAKE_DETAILS).subscribe((data) => {
           this.makeData = data;
  }
         );

 }
 modelDetails()
  {  
         this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_MODEL_DETAILS).subscribe((data) => {
           this.modelData = data;
  }
         );

 }
 duplicateNameMakeCodeAndModelCode() {
  const q = new Promise((resolve, reject) => {
     this.sendAndRequestService.requestForGET(
            Constants.app_urls.INSPECTION.TEST_INSPECTION.EXIST_NAME_AND_MAKE_CODE_MODEL_CODE+
          this.addTestInspectionFormGroup.controls['name'].value + '/'+
          this.addTestInspectionFormGroup.controls['makeCode'].value+'/'+
          this.addTestInspectionFormGroup.controls['modelCode'].value
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

  
    duplicateNameMakeCodeAndModelCodeAndId() {
    let id=this.id;
    let name: string = this.addTestInspectionFormGroup.controls['name'].value;
    let makeCode: string = this.addTestInspectionFormGroup.controls['makeCode'].value;
    let modelCode: string = this.addTestInspectionFormGroup.controls['modelCode'].value;
   

    const q = new Promise((resolve, reject) => {          

       this.sendAndRequestService.requestForGET(
              Constants.app_urls.INSPECTION.TEST_INSPECTION.EXIST_NAME_AND_MAKE_CODE_MODEL_CODE_ID +id+'/'+name+'/'+makeCode+'/'+modelCode).subscribe
              ((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateNameMakeCodeAndModelCodeAndId': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateNameMakeCodeAndModelCodeAndId': true }); });
    });
    return q;
  }
  duplicateMakeCodeModelCodeAndDescri() {
    const q = new Promise((resolve, reject) => {
       this.sendAndRequestService.requestForGET(
              Constants.app_urls.INSPECTION.TEST_INSPECTION.EXIST_MAKE_CODE_MODEL_CODE_AND_DESCRI+
            this.addTestInspectionFormGroup.controls['makeCode'].value + '/'+
            this.addTestInspectionFormGroup.controls['modelCode'].value+'/'+
            this.addTestInspectionFormGroup.controls['description'].value
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
    duplicateMakeCodeAndModelCodeDescriAndId() {
    let id=this.id;
    let makeCode: string = this.addTestInspectionFormGroup.controls['makeCode'].value;
    let modelCode: string = this.addTestInspectionFormGroup.controls['modelCode'].value;
    let description: string = this.addTestInspectionFormGroup.controls['description'].value;
   

    const q = new Promise((resolve, reject) => {          

       this.sendAndRequestService.requestForGET(
              Constants.app_urls.INSPECTION.TEST_INSPECTION.EXIST_MAKE_CODE_MODEL_CODE_AND_DESCRI_ID +id+'/'+makeCode+'/'+modelCode+'/'+description).subscribe
              ((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateNameMakeCodeAndModelCodeAndId': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateNameMakeCodeAndModelCodeAndId': true }); });
    });
    return q;
  }
}