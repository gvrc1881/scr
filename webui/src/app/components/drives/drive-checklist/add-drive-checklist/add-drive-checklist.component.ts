import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';

@Component({
  selector: 'app-add-drive-checklist',
  templateUrl: './add-drive-checklist.component.html',
  styleUrls: []
})
export class AddDriveChecklistComponent implements OnInit {
  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  resp: any;
  title:string;
  addDriveChecklistFormGroup: FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  measureActivityList = [];
  driveList = [];
  statusList = [];

  checkListFormErrors: any;
  constructor(
    private formBuilder: FormBuilder,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private route: ActivatedRoute,
    private router: Router,
    private sendAndRequestService:SendAndRequestService
  ) {
    // Reactive form errors
    this.checkListFormErrors = {
      drive: {},
      measureActivityList: {},
      displayOrder: {},
      lowerLimit: {},
      upperLimit: {},
      status: {}
    };
  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
    this.getDrivesData();
    this.findMeasureActivityList();
    this.findYesNoStatus();
    this.createCheckListForm();
    this.addDriveChecklistFormGroup.valueChanges.subscribe(() => {
      this.onFormValuesChanged();
  });
    if (!isNaN(this.id)) {     
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = 'Edit';
      this.getCheckListData(this.id);
    } else {
      this.save = true;
      this.update = false;
      this.title = 'Save';
    }
    
  }
  findYesNoStatus(){
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_STATUS_ITEM + Constants.STATUS_ITEMS.YES_NO_TYPE)
    .subscribe((resp) => {
      this.statusList = resp;
    });
  }
  getCheckListData(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_CHECKLIST_BY_ID +  id)
      .subscribe((resp) => {
        this.resp = resp;
        this.addDriveChecklistFormGroup.patchValue({
          id: this.resp.id,
          drive: this.resp.driveId['id'],
          measureActivityList: this.resp.activityId['activityId'],
          displayOrder: this.resp.displayOrder,
          lowerLimit: this.resp.lowerLimit,
          upperLimit: this.resp.upperLimit,
          status: this.resp.active
        });
        this.spinnerService.hide();
      })
  }

  getDrivesData() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE.GET_DRIVES).subscribe((data) => {
      this.driveList = data;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }
  

  findMeasureActivityList() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_MEASURE_ACTIVITY_LIST).subscribe((data) => {
      this.measureActivityList = data;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }

  onFormValuesChanged() {
    for (const field in this.checkListFormErrors) {
      if (!this.checkListFormErrors.hasOwnProperty(field)) {
        continue;
      }
      this.checkListFormErrors[field] = {};
      const control = this.addDriveChecklistFormGroup.get(field);

      if (control && control.dirty && !control.valid) {
        this.checkListFormErrors[field] = control.errors;
      }
    }
  }


  createCheckListForm() {
    this.addDriveChecklistFormGroup = this.formBuilder.group({
      id: 0,
      'drive': [null, Validators.compose([Validators.required])],
      'measureActivityList': [null, Validators.compose([Validators.required])],
      'displayOrder': [null, Validators.compose([Validators.required])],
      'lowerLimit': [null],
      'upperLimit': [null],
      'status': ['Yes']
    });
  }
  onAddDriveChecklistFormSubmit() {
    if (this.addDriveChecklistFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    if (this.save) {
      let save = {
        driveId: this.addDriveChecklistFormGroup.value.drive,
        activityId: this.addDriveChecklistFormGroup.value.measureActivityList,
        displayOrder: this.addDriveChecklistFormGroup.value.displayOrder,
        lowerLimit: this.addDriveChecklistFormGroup.value.lowerLimit,
        upperLimit: this.addDriveChecklistFormGroup.value.upperLimit,
        active: this.addDriveChecklistFormGroup.value.status,
        "createdBy": this.loggedUserData.username,
        "createdOn": new Date()
      }
      this.sendAndRequestService.requestForPOST(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.SAVE_CHECK_LIST ,save, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("CheckList Data saved Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("CheckList Data saving Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("CheckList Data saving Failed.");
      })
    } else if (this.update) {
      let update = {
        id: this.id,
        driveId: this.addDriveChecklistFormGroup.value.drive,
        activityId: this.addDriveChecklistFormGroup.value.measureActivityList,
        displayOrder: this.addDriveChecklistFormGroup.value.displayOrder,
        lowerLimit: this.addDriveChecklistFormGroup.value.lowerLimit,
        upperLimit: this.addDriveChecklistFormGroup.value.upperLimit,
        active: this.addDriveChecklistFormGroup.value.status,
        "updatedBy": this.loggedUserData.username,
        "updatedOn": new Date()
      }
      this.sendAndRequestService.requestForPUT(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.UPDATE_CHECK_LIST ,update, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("CheckList Data Updated Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("CheckList Data Updation Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("CheckList Data Updation Failed.");
      })
    }
  }
  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }
}
