import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';


@Component({
  selector: 'app-add-assets-schedule',
  templateUrl: './add-assets-schedule.component.html',
  styleUrls: []
})
export class AddAssetsScheduleComponent implements OnInit {
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  resp: any;
  title:string;
  addAssetsScheduleFormGroup: FormGroup;
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
    
    if (!isNaN(this.id)) {  
      this.updateschedulesForm();   
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;
      this.getSchedulesDataById(this.id);
    } else {
      this.createschedulesForm();
      this.save = true;
      this.update = false;
      this.title = Constants.EVENTS.ADD;
    }
    
  }
  createschedulesForm(){
    this.addAssetsScheduleFormGroup = this.formBuilder.group({
      id: 0,
      'scheduleCode':[null, Validators.compose([Validators.required,Validators.maxLength(255)]),this.duplicateScheduleCode.bind(this)],
      'scheduleName': [null,Validators.compose([ Validators.maxLength(255)])],   
       'description':[null,Validators.compose([ Validators.maxLength(255)])],
    });
  }


  updateschedulesForm(){
    this.addAssetsScheduleFormGroup = this.formBuilder.group({
      id: 0,
      'scheduleCode':[null, Validators.compose([Validators.required,Validators.maxLength(255)]),this.duplicateScheduleCodeID.bind(this)],
      'scheduleName': [null,Validators.compose([ Validators.maxLength(255)])],   
       'description':[null,Validators.compose([ Validators.maxLength(255)])],
    });
  }
  duplicateScheduleCode() {
    const q = new Promise((resolve, reject) => {
              
      let scheduleCode: string = this.addAssetsScheduleFormGroup.controls['scheduleCode'].value;
      
     
      this.sendAndRequestService.requestForGET(Constants.app_urls.CONFIG.ASSETS_SCHEDULE.EXIST_SCHEDULE_CODE+scheduleCode)
      .subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateScheduleCode': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateScheduleCode': true }); });
    });
    return q;
  }
  duplicateScheduleCodeID() {
    const q = new Promise((resolve, reject) => {
      
      let id=this.id;              
      let scheduleCode: string = this.addAssetsScheduleFormGroup.controls['scheduleCode'].value;
      
     
      this.sendAndRequestService.requestForGET(Constants.app_urls.CONFIG.ASSETS_SCHEDULE.EXIST_SCHEDULE_CODE_ID+id+'/'+scheduleCode)
      .subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateScheduleCodeID': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateScheduleCodeID': true }); });
    });
    return q;
  }
  getSchedulesDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.CONFIG.ASSETS_SCHEDULE.GET_ASSET_SCHEDULE_ID+id)
    .subscribe((resp) => {
        this.resp = resp;
        this.addAssetsScheduleFormGroup.patchValue({
          id: this.resp.id,
          scheduleCode: this.resp.scheduleCode,
          scheduleName: this.resp.scheduleName,
          description: this.resp.description         
        });
        this.spinnerService.hide();
      })
  }
  
  assetsScheduleFormSubmit() {
    this.isSubmit = true;
    if (this.addAssetsScheduleFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    if (this.save) {
      var saveSchedule = {
        "scheduleCode": this.addAssetsScheduleFormGroup.value.scheduleCode,
        "scheduleName": this.addAssetsScheduleFormGroup.value.scheduleName,
        "description": this.addAssetsScheduleFormGroup.value.description,                                                                    
        "createdStamp": new Date(),
        "createdTxStamp": new Date(),
        "createdBy":this.loggedUserData.username
             }
      this.sendAndRequestService.requestForPOST(Constants.app_urls.CONFIG.ASSETS_SCHEDULE.SAVE_ASSET_SCHEDULE, saveSchedule, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
     
        if (this.resp.code == Constants.CODES.SUCCESS) {
          this.commonService.showAlertMessage("Assets schedule Data Saved Successfully");
          this.router.navigate(['../'], { relativeTo: this.route });
        } else {
          this.commonService.showAlertMessage("Assets schedule Data Saving Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Assets schedule Data Saving Failed.");
      });
    } else if (this.update) {
      var updateSchedule = {
        "id": this.id,
        "scheduleCode": this.addAssetsScheduleFormGroup.value.scheduleCode,
        "scheduleName": this.addAssetsScheduleFormGroup.value.scheduleName,
        "description": this.addAssetsScheduleFormGroup.value.description,                                        
        "lastUpdatedStamp": new Date(),
        "lastUpdatedTxStamp": new Date()
      }
      this.sendAndRequestService.requestForPUT(Constants.app_urls.CONFIG.ASSETS_SCHEDULE.UPDATE_ASSET_SCHEDULE, updateSchedule, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Assets schedule Data Updated Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Assets schedule Data Updating Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Assets schedule Data Updating Failed.");
      })

    }
  }

  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }
}
