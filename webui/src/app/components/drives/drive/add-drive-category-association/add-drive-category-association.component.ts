import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DrivesService } from 'src/app/services/drives.service';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';

@Component({
  selector: 'app-add-drive-category-association',
  templateUrl: './add-drive-category-association.component.html',
  styleUrls: ['./add-drive-category-association.component.css']
})
export class AddDriveCategoryAssociationComponent implements OnInit {
  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  save: boolean = true;
  update: boolean = false;
  title: string = '';
  isSubmit: boolean = false;
  addDriveCategoryAssoFormGroup: FormGroup;
  id: number = 0;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  driveList = [];
  driveCategoryList = [];
  driveFormErrors: any;
  resp: any;
  statusList = [{ 'id': 1, "value": 'Yes' }, { 'id': 2, "value": 'No' }];
  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
   // private drivesService: DrivesService,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private sendAndRequestService:SendAndRequestService

  ) {
    // Reactive form errors
    this.driveFormErrors = {
      drive: {},
      driveCategory: {},
      active: {},      
    };
  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
    this.getDrivesData();
    this.getDriveCategoryData();
    this.createDriveForm();

    if (!isNaN(this.id)) {
      this.addDriveCategoryAssoFormGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      });
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = 'Edit';
      this.getDriveCategoryAssoDataById(this.id);
    } else {
      this.title = 'Save';
    }
  }
  getDrivesData() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE.GET_DRIVES).subscribe((data) => {
      this.driveList = data;
      console.log(this.driveList)
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }
  getDriveCategoryData() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CATEGORY.GET_DRIVE_CATEGORY).subscribe((data) => {
      this.driveCategoryList = data;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }
  onFormValuesChanged() {
    for (const field in this.driveFormErrors) {
      if (!this.driveFormErrors.hasOwnProperty(field)) {
        continue;
      }
      this.driveFormErrors[field] = {};
      const control = this.addDriveCategoryAssoFormGroup.get(field);

      if (control && control.dirty && !control.valid) {
        this.driveFormErrors[field] = control.errors;
      }
    }
  }
  createDriveForm() {
    this.addDriveCategoryAssoFormGroup = this.formBuilder.group({
      id: 0,
      'drive': [null, Validators.compose([Validators.required])],
      'driveCategory': [null, Validators.compose([Validators.required])],
      'active': [null, Validators.required]
    });
  }

  public get f() { return this.addDriveCategoryAssoFormGroup.controls; }


  getDriveCategoryAssoDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CATEGORY_ASSOCIATION.GET_DRIVE_CATEGORY_ASSOC_ID+'/'+id)
      .subscribe((resp) => {
        console.log(resp)
        this.resp = resp;
        this.addDriveCategoryAssoFormGroup.patchValue({
          id: this.resp.id,
          driveCategory: this.resp.driveCategoryId['id'],
          drive : this.resp.driveId['id'],     
          active: this.resp.active,
        });

        this.spinnerService.hide();
      })
  }
  
  onAddDriveCategoryFormSubmit() {
    this.isSubmit = true;
    if (this.addDriveCategoryAssoFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    console.log(this.addDriveCategoryAssoFormGroup.value);
    if (this.save) {
      var saveDriveModel = {
        "driveId": this.addDriveCategoryAssoFormGroup.value.drive,
        "driveCategoryId": this.addDriveCategoryAssoFormGroup.value.driveCategory,
        "active": this.addDriveCategoryAssoFormGroup.value.active,
        "createdBy": this.loggedUserData.username,
        "createdOn": new Date()
      }
      this.sendAndRequestService.requestForPOST(Constants.app_urls.DRIVE.DRIVE_CATEGORY_ASSOCIATION.SAVE_DRIVE_CATEGORY_ASSOC, saveDriveModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Drive Category Association Data Saved Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Drive Category Association Data Saving Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Drive Category Association Data Saving Failed.");
      });
    } else if (this.update) {
      var updateDriveModel = {
        "id": this.id,
        "driveId": this.addDriveCategoryAssoFormGroup.value.drive,
        "driveCategoryId": this.addDriveCategoryAssoFormGroup.value.driveCategory,
        "active": this.addDriveCategoryAssoFormGroup.value.active,
        "updatedBy": this.loggedUserData.username,
        "updatedOn": new Date()
      }
      this.sendAndRequestService.requestForPUT(Constants.app_urls.DRIVE.DRIVE_CATEGORY_ASSOCIATION.UPDATE_DRIVE_CATEGORY_ASSOC, updateDriveModel).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;

        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Drive Category Association Data Updated Successfully");
        this.router.navigate(['../../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Drive Category Association Data Updating Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Drive Category Association Data Updating Failed.");
      })

    }
  }

  onGoBack() {
    if(this.save){
      this.router.navigate(['../'], { relativeTo: this.route });
    }else if(this.update){
      this.router.navigate(['../../'], { relativeTo: this.route });
    }
  }
}
