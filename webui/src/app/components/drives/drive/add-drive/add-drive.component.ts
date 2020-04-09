import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DrivesService } from 'src/app/services/drives.service';
import { DriveModel } from 'src/app/models/drive.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
@Component({
  selector: 'app-add-drive',
  templateUrl: './add-drive.component.html',
  styleUrls: ['./add-drive.component.css']
})
export class AddDriveComponent implements OnInit {

  saveUser: boolean = true;
  updateUser: boolean = false;

  isSubmit: boolean = false;
  addDriveFormGroup: FormGroup;
  id: number = 0;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  depoTypeList = [];
  assetTypeList = [];
  isIdRequiredsList = [{ 'id': 1, "value": 'Yes' }, { 'id': 2, "value": 'No' }];
  CheckList = [{ 'id': 1, "value": 'Yes' }, { 'id': 2, "value": 'No' }];
  statusList = [{ 'id': 1, "value": 'Yes' }, { 'id': 2, "value": 'No' }];
  functionalUnitList = [{ 'id': 1, "value": 'Yes' }, { 'id': 2, "value": 'No' }];

  driveFormErrors: any;
  resp:any;

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
      status:{}
    };
  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];

    this.createDriveForm();

    if (!isNaN(this.id)) {
      this.addDriveFormGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      });
      this.spinnerService.show();
      this.saveUser = false;
      this.updateUser = true;
      this.getDriveDataById(this.id);

    } else {

      this.findDepoTypeList();
      this.findAssetTypeList();
    }
  }
  onFormValuesChanged() {
    for (const field in this.driveFormErrors) {
      if (!this.driveFormErrors.hasOwnProperty(field)) {
        continue;
      }
      this.driveFormErrors[field] = {};
      const control = this.addDriveFormGroup.get(field);

      if (control && control.dirty && !control.valid) {
        this.driveFormErrors[field] = control.errors;
      }
    }
  }
  createDriveForm() {
    this.addDriveFormGroup = this.formBuilder.group({
      id: 0,
      'name': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
      'description': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.pattern(this.pattern)])],
      'fromDate': [null, Validators.required],
      'toDate': [null, Validators.required],
      'depoType': [null, Validators.compose([Validators.required])],
      'assetType': [null, Validators.compose([Validators.required, Validators.minLength(10), Validators.maxLength(10)])],
      'assetDescription': [null, Validators.required],
      'criteria': [null, Validators.required],
      'targetQuantity': [null, Validators.required],
      'isIdRequired': [null, Validators.required],
      'functionalUnit': [null, Validators.required],
      'checklist': [null, Validators.required],
      'status': [null, Validators.required]
    });
  }

  public get f() { return this.addDriveFormGroup.controls; }

  findDepoTypeList() {
    this.drivesService.findDepoTypeList()
      .subscribe((depoTypes) => {
        //console.log('depoTypes = '+depoTypes)
        this.depoTypeList = depoTypes;
      })
  }

  findAssetTypeList() {
    this.drivesService.findAssetTypeList('PSI_FIXED_ASSET')
      .subscribe((assetTypes) => {
        //console.log('assetTypes = '+JSON.stringify(assetTypes))
        this.assetTypeList = assetTypes;
        this.depoTypeList = assetTypes;
      })
  }

  getDriveDataById(id){
    this.drivesService.findDriveDataById(id)
      .subscribe((resp) => {
        console.log('depoTypes = '+JSON.stringify(resp)); 
        this.resp = resp;
        this.addDriveFormGroup.patchValue({
          id: this.resp.id,
          name: this.resp.name,
          description: this.resp.description,
          fromDate: new Date(this.resp.fromDate),
          toDate: new Date(this.resp.toDate),
          assetType: this.resp.assetType,
          assetDescription: this.resp.assetDescription,
          criteria: this.resp.criteria,
          targetQuantity: this.resp.target_qty,
          functionalUnit:this.resp.functionalUnit,
          isIdRequired:this.resp.isIdRequired,
          checklist:this.resp.checklist,
      }); 
      this.spinnerService.hide();       
      })
  }

  onAddDriveFormSubmit() {
    this.isSubmit = true;
    if (this.addDriveFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    console.log(this.addDriveFormGroup.value);
    if (this.saveUser) {
      var saveDriveModel = {
        "name": this.addDriveFormGroup.value.name,
        "description": this.addDriveFormGroup.value.description,
        "fromDate": this.addDriveFormGroup.value.fromDate,
        "toDate": this.addDriveFormGroup.value.toDate,
        "depotType": this.addDriveFormGroup.value.depoType,
        "assetType": this.addDriveFormGroup.value.assetType,
        "assetDescription": this.addDriveFormGroup.value.assetDescription,
        "criteria": this.addDriveFormGroup.value.criteria,
        "targetQty": this.addDriveFormGroup.value.targetQuantity,
        "isIdRequired": this.addDriveFormGroup.value.isIdRequired,
        "functionalUnit": this.addDriveFormGroup.value.functionalUnit,
        "checkList": this.addDriveFormGroup.value.checklist,
        "status": this.addDriveFormGroup.value.status
      }
      this.drivesService.saveDriveData(saveDriveModel).subscribe(data => {
        console.log(JSON.stringify(data));
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Drive Data Saved Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Drive Data Saving Failed.");
      })

    }
  }

  onGoBack(){
    this.router.navigate(['../'], { relativeTo: this.route });
  }
}
