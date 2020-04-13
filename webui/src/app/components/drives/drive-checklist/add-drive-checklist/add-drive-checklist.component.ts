import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DrivesService } from 'src/app/services/drives.service';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-add-drive-checklist',
  templateUrl: './add-drive-checklist.component.html',
  styleUrls: ['./add-drive-checklist.component.css']
})
export class AddDriveChecklistComponent implements OnInit {
  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  resp: any;

  addDriveChecklistFormGroup: FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  measureActivityList = [];
  driveList = [];
  statusList = [];

  checkListFormErrors: any;
  constructor(
    private formBuilder: FormBuilder,
    private drivesService: DrivesService,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private route: ActivatedRoute,
    private router: Router,
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
    this.createCheckListForm();
    if (!isNaN(this.id)) {
      this.addDriveChecklistFormGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      });
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.getCheckListData(this.id);
    } else {
      this.save = true;
      this.update = false;
    }
    this.getDrivesData();
  }

  getCheckListData(id) {
    this.drivesService.findCheckListDataById(id)
      .subscribe((resp) => {
        console.log('depoTypes = ' + JSON.stringify(resp));
        this.resp = resp;
        console.log('date : ' + this.resp.dateOfStipulation)
        this.addDriveChecklistFormGroup.patchValue({
          id: this.resp.id,
          drive: this.resp.drive,
          measureActivityList: this.resp.measureActivityList,
          displayOrder: this.resp.displayOrder,
          lowerLimit: this.resp.lowerLimit,
          upperLimit: this.resp.upperLimit,
          status: this.resp.status
        });
        this.spinnerService.hide();
      })
  }

  getDrivesData() {
    this.drivesService.getDrivesData().subscribe((data) => {
      this.driveList = data;
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
      'drive': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
      'measureActivityList': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.pattern(this.pattern)])],
      'displayOrder': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
      'lowerLimit': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
      'upperLimit': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
      'status': [null, Validators.required]
    });
  }
  onAddDriveChecklistFormSubmit() {
    if (this.addDriveChecklistFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    console.log(this.addDriveChecklistFormGroup.value);
    if (this.save) {
      let save = {
        drive: this.addDriveChecklistFormGroup.value.drive,
        measureActivityList: this.addDriveChecklistFormGroup.value.measureActivityList,
        displayOrder: this.addDriveChecklistFormGroup.value.displayOrder,
        lowerLimit: this.addDriveChecklistFormGroup.value.lowerLimit,
        upperLimit: this.addDriveChecklistFormGroup.value.upperLimit,
        status: this.addDriveChecklistFormGroup.value.status,
        "createdBy": "1",
        "createdOn": "2020-04-01",
        "updatedBy": "1",
        "updatedOn": "2020-04-01"
      }
      this.drivesService.saveCheckListData(save).subscribe(response => {
        console.log(JSON.stringify(response));
        this.spinnerService.hide();
        this.commonService.showAlertMessage("CheckList Data saved Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("CheckList Data saving Failed.");
      })
    } else if (this.update) {
      let update = {
        id: this.id,
        drive: this.addDriveChecklistFormGroup.value.drive,
        measureActivityList: this.addDriveChecklistFormGroup.value.measureActivityList,
        displayOrder: this.addDriveChecklistFormGroup.value.displayOrder,
        lowerLimit: this.addDriveChecklistFormGroup.value.lowerLimit,
        upperLimit: this.addDriveChecklistFormGroup.value.upperLimit,
        status: this.addDriveChecklistFormGroup.value.status,
        "createdBy": "1",
        "createdOn": "2020-04-01",
        "updatedBy": "1",
        "updatedOn": "2020-04-01"
      }
      this.drivesService.updateCheckListData(update).subscribe(response => {
        console.log(JSON.stringify(response));
        this.spinnerService.hide();
        this.commonService.showAlertMessage("CheckList Data Updated Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
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
