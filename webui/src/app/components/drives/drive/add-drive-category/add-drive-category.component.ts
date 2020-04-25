import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DrivesService } from 'src/app/services/drives.service';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';

@Component({
  selector: 'app-add-drive-category',
  templateUrl: './add-drive-category.component.html',
  styleUrls: ['./add-drive-category.component.css']
})
export class AddDriveCategoryComponent implements OnInit {

  save: boolean = true;
  update: boolean = false;
  title: string = '';
  isSubmit: boolean = false;
  addDriveCategoryFormGroup: FormGroup;
  id: number = 0;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  
  driveFormErrors: any;
  resp: any;

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
      name: {},
      description: {},
      fromDate: {},
      toDate: {},
      depoType: {},
      assetType: {},
      assetDescription: {},
      criteria: {},
      targetQuantity: {},
      isIdRequired: {},
      functionalUnit: {},
      checklist: {},
      status: {}
    };
  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
   
    this.createDriveForm();

    if (!isNaN(this.id)) {
      this.addDriveCategoryFormGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      });
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = 'Edit';
      this.getDriveCategoryDataById(this.id);

    } else {
      this.title = 'Save';
    }
  }
  onFormValuesChanged() {
    for (const field in this.driveFormErrors) {
      if (!this.driveFormErrors.hasOwnProperty(field)) {
        continue;
      }
      this.driveFormErrors[field] = {};
      const control = this.addDriveCategoryFormGroup.get(field);

      if (control && control.dirty && !control.valid) {
        this.driveFormErrors[field] = control.errors;
      }
    }
  }
  createDriveForm() {
    this.addDriveCategoryFormGroup = this.formBuilder.group({
      id: 0,
      'name': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
      'description': [null, Validators.compose([Validators.required, Validators.pattern(this.pattern)])],
      'fromDate': [null, Validators.required],
      'toDate': [null],
      'authority': [null]
    });
  }

  public get f() { return this.addDriveCategoryFormGroup.controls; }


  getDriveCategoryDataById(id) {
    this.drivesService.findDriveCategoryDataById(id)
      .subscribe((resp) => {

        this.resp = resp;
        this.addDriveCategoryFormGroup.patchValue({
          id: this.resp.id,
          name: this.resp.driveCategoryName,
          description: this.resp.description,
          fromDate: new Date(this.resp.fromDate),
          toDate: !!this.resp.toDate ? new Date(this.resp.toDate) : '',
          authority: this.resp.authority,
        });

        this.spinnerService.hide();
      })
  }
  
  onAddDriveCategoryFormSubmit() {
    this.isSubmit = true;
    if (this.addDriveCategoryFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    console.log(this.addDriveCategoryFormGroup.value);
    if (this.save) {
      var saveDriveModel = {
        "name": this.addDriveCategoryFormGroup.value.name,
        "description": this.addDriveCategoryFormGroup.value.description,
        "fromDate": this.addDriveCategoryFormGroup.value.fromDate,
        "toDate": this.addDriveCategoryFormGroup.value.toDate,
        "authority": this.addDriveCategoryFormGroup.value.authority,
      }
      this.drivesService.saveDriveCategoryData(saveDriveModel).subscribe(data => {
        console.log(JSON.stringify(data));
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Drive Category Data Saved Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Drive Category Data Saving Failed.");
      });
    } else if (this.update) {
      var updateDriveModel = {
        "id": this.id,
        "name": this.addDriveCategoryFormGroup.value.name,
        "description": this.addDriveCategoryFormGroup.value.description,
        "fromDate": this.addDriveCategoryFormGroup.value.fromDate,
        "toDate": this.addDriveCategoryFormGroup.value.toDate,
        "authority": this.addDriveCategoryFormGroup.value.authority,
      }
      this.drivesService.updateDriveCategoryData(updateDriveModel).subscribe(data => {
        console.log(JSON.stringify(data));
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Drive Category Data Updated Successfully");
        this.router.navigate(['../../'], { relativeTo: this.route });
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Drive Category Data Updating Failed.");
      })

    }
  }

  onGoBack() {
    this.router.navigate(['../../'], { relativeTo: this.route });
  }
}
