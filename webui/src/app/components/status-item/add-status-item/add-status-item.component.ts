import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';


@Component({
  selector: 'app-add-status-item',
  templateUrl: './add-status-item.component.html',
  
})
export class AddStatusItemComponent implements OnInit {

  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
  resp: any;
  title:string = Constants.EVENTS.ADD;
  addStatusItemFormGroup: FormGroup;
  statusItemFormErrors:any;
  statusData:any;
  

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private sendAndRequestService:SendAndRequestService
  ) {
    this.statusItemFormErrors = {            
      statusTypeId:{},
      statusId: {},
      statusCode: {},
      description:{},
      sequenceId:{}
    };
  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
    this.statusDetails();
    if (!isNaN(this.id)) {  
      this.updateStatusItemForm();  
      this.addStatusItemFormGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      }); 
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;
       this.getStatusItemDataById(this.id);
    } else {
      this.createStatusItemForm();
      this.save = true;
      this.update = false;
      this.title = Constants.EVENTS.ADD;
    }
  }
  
  onFormValuesChanged() {
    for (const field in this.statusItemFormErrors) {
      if (!this.statusItemFormErrors.hasOwnProperty(field)) {
        continue;
      }
      this.statusItemFormErrors[field] = {};
      const control = this.addStatusItemFormGroup.get(field);
      if (control && control.dirty && !control.valid) {
        this.statusItemFormErrors[field] = control.errors;
      }
    }
  }
  createStatusItemForm() {
    this.addStatusItemFormGroup = this.formBuilder.group({
      id: 0,
      'statusTypeId':[null,Validators.compose([Validators.required])],
      'statusId': [null,Validators.required, this.duplicateStatusId.bind(this)],
      'statusCode': [null,Validators.required, this.duplicateStatusCode.bind(this)],
      'description': [null, Validators.compose([Validators.required, Validators.maxLength(250)]), this.duplicateDescription.bind(this)],
      'sequenceId': [null],
      
    });
  }
  updateStatusItemForm() {
    this.addStatusItemFormGroup = this.formBuilder.group({
      id: 0,
      'statusTypeId':[null,Validators.compose([Validators.required])],
      'statusId': [null,Validators.required, this.duplicateStatusIdAndId.bind(this)],
      'statusCode': [null,Validators.required, this.duplicateStatusCodeAndId.bind(this)],
      'description': [null, Validators.compose([Validators.required, Validators.maxLength(250)]), this.duplicateDescriptionAndId.bind(this)],
      'sequenceId': [null],
    });
  }
  

   public get f() { return this.addStatusItemFormGroup.controls; } 
   
  getStatusItemDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.CONFIG.STATUS_ITEM.GET_STATUS_ITEM_ID+id)
    .subscribe((resp) => {
        this.resp = resp;
        this.addStatusItemFormGroup.patchValue({
          id: this.resp.id,
          statusTypeId: this.resp.statusTypeId,
          statusId: this.resp.statusId,
          statusCode: this.resp.statusCode,
          description: this.resp.description,
          sequenceId: this.resp.sequenceId,
          
        });
        this.spinnerService.hide();
      })
  }
  onAddStatusItemFormSubmit() {
    this.isSubmit = true;
    if (this.addStatusItemFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    if (this.save) {
      var saveStatusItemModel = {
        "statusTypeId": this.addStatusItemFormGroup.value.statusTypeId,
        "statusId": this.addStatusItemFormGroup.value.statusId,  
        "statusCode": this.addStatusItemFormGroup.value.statusCode,  
        "description": this.addStatusItemFormGroup.value.description,  
        "sequenceId": this.addStatusItemFormGroup.value.sequenceId, 
        
      }
      this.sendAndRequestService.requestForPOST(Constants.app_urls.CONFIG.STATUS_ITEM.SAVE_STATUS_ITEM, saveStatusItemModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
     
        if (this.resp.code == Constants.CODES.SUCCESS) {
          this.commonService.showAlertMessage("Status Item  Data Saved Successfully");
          this.router.navigate(['../'], { relativeTo: this.route });
        } else {
          this.commonService.showAlertMessage("Status Item Data Saving Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Status Item Data Saving Failed.");
      });
    } else if (this.update) {
      var updateStatusItemModel = {
        "id": this.id,
        "statusTypeId": this.addStatusItemFormGroup.value.statusTypeId,
        "statusId": this.addStatusItemFormGroup.value.statusId,  
        "statusCode": this.addStatusItemFormGroup.value.statusCode,  
        "description": this.addStatusItemFormGroup.value.description,  
        "sequenceId": this.addStatusItemFormGroup.value.sequenceId, 
      }
      this.sendAndRequestService.requestForPUT(Constants.app_urls.CONFIG.STATUS_ITEM.UPDATE_STATUS_ITEM, updateStatusItemModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Status Item Data Updated Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Status Item Data Updating Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Status Item Data Updating Failed.");
      })

    }
  }

  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }
  
  statusDetails()
  {  
         this.sendAndRequestService.requestForGET(Constants.app_urls.CONFIG.STATUS_TYPE.GET_STATUS_TYPE).subscribe((data) => {
           this.statusData = data;
  }
         );

 }
 duplicateStatusId() {
  const q = new Promise((resolve, reject) => {
    this.sendAndRequestService.requestForGET(
      Constants.app_urls.CONFIG.STATUS_ITEM.EXIST_STATUS_TYPE_ID_AND_STATUS_ID +
         this.addStatusItemFormGroup.controls['statusTypeId'].value + '/'+
         this.addStatusItemFormGroup.controls['statusId'].value
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
  duplicateStatusIdAndId() {
    let id=this.id;
    let statusTypeId: string = this.addStatusItemFormGroup.controls['statusTypeId'].value;
    let statusId: string = this.addStatusItemFormGroup.controls['statusId'].value;

    const q = new Promise((resolve, reject) => {          

       this.sendAndRequestService.requestForGET(
              Constants.app_urls.CONFIG.STATUS_ITEM.EXIST_STATUS_TYPE_ID_AND_STATUS_ID_AND_ID+id+'/'+statusTypeId+'/'+statusId).subscribe
              ((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateStatusIdAndId': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateStatusIdAndId': true }); });
    });
    return q;
  } 
  duplicateStatusCode() {
    const q = new Promise((resolve, reject) => {
      this.sendAndRequestService.requestForGET(
        Constants.app_urls.CONFIG.STATUS_ITEM.EXIST_STATUS_TYPE_ID_AND_STATUS_CODE +
           this.addStatusItemFormGroup.controls['statusTypeId'].value + '/'+
           this.addStatusItemFormGroup.controls['statusCode'].value
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
    duplicateStatusCodeAndId() {
      let id=this.id;
      let statusTypeId: string = this.addStatusItemFormGroup.controls['statusTypeId'].value;
      let statusCode: string = this.addStatusItemFormGroup.controls['statusCode'].value;
  
      const q = new Promise((resolve, reject) => {          
  
         this.sendAndRequestService.requestForGET(
                Constants.app_urls.CONFIG.STATUS_ITEM.EXIST_STATUS_TYPE_ID_AND_STATUS_CODE_AND_ID+id+'/'+statusTypeId+'/'+statusCode).subscribe
                ((duplicate) => {
          if (duplicate) {
            resolve({ 'duplicateStatusCodeAndId': true });
          } else {
            resolve(null);
          }
        }, () => { resolve({ 'duplicateStatusCodeAndId': true }); });
      });
      return q;
    } 
    duplicateDescription() {
      const q = new Promise((resolve, reject) => {
        this.sendAndRequestService.requestForGET(
          Constants.app_urls.CONFIG.STATUS_ITEM.EXIST_STATUS_TYPE_ID_AND_DESC +
             this.addStatusItemFormGroup.controls['statusTypeId'].value + '/'+
             this.addStatusItemFormGroup.controls['description'].value
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
      duplicateDescriptionAndId() {
        let id=this.id;
        let statusTypeId: string = this.addStatusItemFormGroup.controls['statusTypeId'].value;
        let description: string = this.addStatusItemFormGroup.controls['description'].value;
    
        const q = new Promise((resolve, reject) => {          
    
           this.sendAndRequestService.requestForGET(
                  Constants.app_urls.CONFIG.STATUS_ITEM.EXIST_STATUS_TYPE_ID_AND_DESC_AND_ID+id+'/'+statusTypeId+'/'+description).subscribe
                  ((duplicate) => {
            if (duplicate) {
              resolve({ 'duplicateDescriptionAndId': true });
            } else {
              resolve(null);
            }
          }, () => { resolve({ 'duplicateDescriptionAndId': true }); });
        });
        return q;
      } 
 
}