import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DrivesService } from 'src/app/services/drives.service';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';

@Component({
  selector: 'app-add-drive-category-association',
  templateUrl: './add-drive-category-association.component.html',
  styleUrls: ['./add-drive-category-association.component.css']
})
export class AddDriveCategoryAssociationComponent implements OnInit {

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
    private drivesService: DrivesService,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
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
    this.drivesService.getDrivesData().subscribe((data) => {
      this.driveList = data;
      console.log(this.driveList)
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }
  getDriveCategoryData() {
    this.drivesService.getDriveCategoryData().subscribe((data) => {
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
    this.drivesService.findDriveCategoryAssoDataById(id)
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
      }
      this.drivesService.saveDriveCategoryAssoData(saveDriveModel).subscribe(data => {
        console.log(JSON.stringify(data));
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Drive Category Association Data Saved Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
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
      }
      this.drivesService.updateDriveCategoryAssoData(updateDriveModel).subscribe(data => {
        console.log(JSON.stringify(data));
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Drive Category Association Data Updated Successfully");
        this.router.navigate(['../../'], { relativeTo: this.route });
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
