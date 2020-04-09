import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { DrivesService } from 'src/app/services/drives.service';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-add-drive-stipulation',
  templateUrl: './add-drive-stipulation.component.html',
  styleUrls: ['./add-drive-stipulation.component.css']
})
export class AddDriveStipulationComponent implements OnInit {
  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  resp: any;

  addDriveStipulationFormGroup: FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  stipulationFormErrors: any;
  stateList: any;
  constructor(
    private formBuilder: FormBuilder,
    private drivesService: DrivesService,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private route: ActivatedRoute,
    private router: Router,
  ) {
    // Reactive form errors
    this.stipulationFormErrors = {
      stipulation: {},
      stipulationTo: {},
      dateOfStipulation: {},
      dateComplied: {},
      compliance: {},
      attachment: {},
      compliedBy: {},
      assetType: {}
    };

  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
    this.createStipulationForm();
    if (!isNaN(this.id)) {
      this.addDriveStipulationFormGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      });
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.getStipulationDataById(this.id);

    } else {
      this.save = true;
      this.update = false;
    }
  }

  getStipulationDataById(id) {
    this.drivesService.findStipulationDataById(id)
      .subscribe((resp) => {
        console.log('depoTypes = ' + JSON.stringify(resp));
        this.resp = resp;
        console.log('date : '+this.resp.dateOfStipulation)
        this.addDriveStipulationFormGroup.patchValue({
          id: this.resp.id,
          stipulation: this.resp.stipulation,
          stipulationTo: this.resp.stipulationTo,
          dateOfStipulation: new Date(this.resp.dateOfStipulation),
          dateComplied: new Date(this.resp.dateComplied),
          compliance: this.resp.compliance,
          attachment: this.resp.attachment,
          compliedBy: this.resp.compliedBy,
          assetType: this.resp.assetType
        });
        this.spinnerService.hide();
      })
  }


  onFormValuesChanged() {
    for (const field in this.stipulationFormErrors) {
      if (!this.stipulationFormErrors.hasOwnProperty(field)) {
        continue;
      }
      this.stipulationFormErrors[field] = {};
      const control = this.addDriveStipulationFormGroup.get(field);

      if (control && control.dirty && !control.valid) {
        this.stipulationFormErrors[field] = control.errors;
      }
    }
  }

  createStipulationForm() {
    this.addDriveStipulationFormGroup
      = this.formBuilder.group({
        id: 0,
        'stipulation': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
        'stipulationTo': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.pattern(this.pattern)])],
        'dateOfStipulation': [null, Validators.required],
        'dateComplied': [null, Validators.required],
        'compliance': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.pattern(this.pattern)])],
        'attachment': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
        'compliedBy': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
        'assetType': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])]
      });
  }

  onAddStipulationFormSubmit() {

    if (this.addDriveStipulationFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    console.log(this.addDriveStipulationFormGroup.value);
    if (this.save) {
      let save = {
        stipulation: this.addDriveStipulationFormGroup.value.stipulation,
          stipulationTo: this.addDriveStipulationFormGroup.value.stipulationTo,
          dateOfStipulation: this.addDriveStipulationFormGroup.value.dateOfStipulation,
          dateComplied: this.addDriveStipulationFormGroup.value.dateComplied,
          compliance: this.addDriveStipulationFormGroup.value.compliance,
          attachment: this.addDriveStipulationFormGroup.value.attachment,
          compliedBy: this.addDriveStipulationFormGroup.value.compliedBy,
          assetType: this.addDriveStipulationFormGroup.value.assetType,
          "createdBy": "1",
          "createdOn": "2020-04-01",
          "updatedBy": "1",
          "updatedOn": "2020-04-01"
      }
      this.drivesService.saveStipulationData(save).subscribe(response => {
        console.log(JSON.stringify(response));
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Stipulation Data saved Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Stipulation Data saving Failed.");
      })
    }else if(this.update){
      let update = {
          id:this.id,
          stipulation: this.addDriveStipulationFormGroup.value.stipulation,
          stipulationTo: this.addDriveStipulationFormGroup.value.stipulationTo,
          dateOfStipulation: this.addDriveStipulationFormGroup.value.dateOfStipulation,
          dateComplied: this.addDriveStipulationFormGroup.value.dateComplied,
          compliance: this.addDriveStipulationFormGroup.value.compliance,
          attachment: this.addDriveStipulationFormGroup.value.attachment,
          compliedBy: this.addDriveStipulationFormGroup.value.compliedBy,
          assetType: this.addDriveStipulationFormGroup.value.assetType,
          "createdBy": "1",
          "createdOn": "2020-04-01",
          "updatedBy": "1",
          "updatedOn": "2020-04-01"
      }
      this.drivesService.updateStipulationData(update).subscribe(response => {
        console.log(JSON.stringify(response));
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Stipulation Data Updated Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Stipulation Data Updation Failed.");
      })
    }
  }
  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }
}
