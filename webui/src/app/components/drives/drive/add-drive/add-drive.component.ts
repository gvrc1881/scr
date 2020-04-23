import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DrivesService } from 'src/app/services/drives.service';
import { DriveModel } from 'src/app/models/drive.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Constants } from 'src/app/common/constants';
@Component({
  selector: 'app-add-drive',
  templateUrl: './add-drive.component.html',
  styleUrls: ['./add-drive.component.css']
})
export class AddDriveComponent implements OnInit {

  save: boolean = true;
  update: boolean = false;
  title: string = '';
  isSubmit: boolean = false;
  addDriveFormGroup: FormGroup;
  id: number = 0;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  depoTypeList = [];
  assetTypeList = [];
  isIdRequiredsList = [{ 'id': 1, "value": 'Yes' }, { 'id': 2, "value": 'No' }];
  CheckList = [{ 'id': 1, "value": 'Yes' }, { 'id': 2, "value": 'No' }];
  statusList = [{ 'id': 1, "value": 'Yes' }, { 'id': 2, "value": 'No' }];
  functionalUnitList: any;
  allFunctionalUnitsList: any;
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
    this.findFunctionalUnits();
    this.findDepoTypeList();
    this.findStatusItemDetails();
    this.createDriveForm();

    if (!isNaN(this.id)) {
      this.addDriveFormGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      });
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = 'Edit';

      //this.findAssetTypeList();

      this.getDriveDataById(this.id);

    } else {
      this.title = 'Save'
      // this.findDepoTypeList();
      //this.findAssetTypeList();
      //this.findStatusItemDetails();
      //this.findFunctionalUnits();
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
      'description': [null, Validators.compose([Validators.required, Validators.pattern(this.pattern)])],
      'fromDate': [null, Validators.required],
      'toDate': [null],
      'depoType': [null],
      'assetType': [null],
      'assetDescription': [null],
      'criteria': [null],
      'targetQuantity': [null],
      'isIdRequired': ['No'],
      'functionalUnit': [null],
      'checklist': ['No'],
      'status': ['Yes']
    });
  }

  public get f() { return this.addDriveFormGroup.controls; }

  findDepoTypeList() {
    this.drivesService.findDepoTypeList()
      .subscribe((depoTypes) => {
        //console.log('depoTypes = ' + JSON.stringify(depoTypes))
        this.depoTypeList = depoTypes;
      })
  }

  findAssetTypeList(assertType) {
    this.assetTypeList = [];
    // console.log(assertType)
    this.drivesService.findAssetTypeList(assertType)
      .subscribe((assetTypes) => {
        // console.log('assetTypes = '+JSON.stringify(assetTypes))
        this.assetTypeList = assetTypes;
        // this.depoTypeList = assetTypes;
      })
  }
  findStatusItemDetails() {
    /* this.drivesService.findStatusItemDetails()
      .subscribe((assetTypes) => {
       console.log('assetTypes = '+JSON.stringify(assetTypes))
        
      }) */
  }
  findFunctionalUnits() {
    this.drivesService.findFunctionslUnits()
      .subscribe((units) => {
        // console.log('findFunctionalUnits = ' + JSON.stringify(units))
        this.allFunctionalUnitsList = units;
      })
  }
  updateAssertType($event) {
    //console.log($event);
    // console.log(this.addDriveFormGroup.value.depotType);
    // console.log(Constants.ASSERT_TYPE[$event.value])
    if ($event.value) {
      this.findAssetTypeList(Constants.ASSERT_TYPE[$event.value]);
      this.functionalUnitList = [];
      this.functionalUnitList = this.allFunctionalUnitsList.filter(element => {
        return element.depotType == $event.value;
      });
      //  console.log('functionalUnitList = '+JSON.stringify(this.functionalUnitList));
    }
  }

  getDriveDataById(id) {
    this.drivesService.findDriveDataById(id)
      .subscribe((resp) => {

        this.resp = resp;
        this.addDriveFormGroup.patchValue({
          id: this.resp.id,
          name: this.resp.name,
          description: this.resp.description,
          fromDate: new Date(this.resp.fromDate),
          toDate: !!this.resp.toDate ? new Date(this.resp.toDate) : '',
          depoType: !!this.resp.depotType ? this.resp.depotType['depotType'] : '',
          assetType: this.resp.assetType,
          assetDescription: this.resp.assetDescription,
          criteria: this.resp.criteria,
          targetQuantity: this.resp.target_qty,
          functionalUnit: this.resp.functionalUnit,
          isIdRequired: this.resp.isIdRequired,
          checklist: this.resp.checklist,
          status: this.resp.active
        });

        if (this.resp.depotType != null) {
          this.findAssetTypeList(Constants.ASSERT_TYPE[this.resp.depotType['depotType']]);
          this.functionalUnitList = [];
          this.functionalUnitList = this.allFunctionalUnitsList.filter(element => {
            return element.depotType == this.resp.depotType['depotType'];
          });
        }
        this.spinnerService.hide();
      })
  }
  typeId = [];
  assertTypeId = [];
  onAddDriveFormSubmit() {
    this.isSubmit = true;
    if (this.addDriveFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    console.log(this.addDriveFormGroup.value);
    console.log(this.addDriveFormGroup.value.assetType);

    /* this.typeId = this.depoTypeList.find(element =>{
      console.log(this.addDriveFormGroup.value.depoType+' = '+JSON.stringify(element.code) +"= "+(element.code == this.addDriveFormGroup.value.depoType))
      return element.code == this.addDriveFormGroup.value.depoType;
    });
   
    this.assertTypeId = this.assetTypeList.find(element =>{
      console.log(this.addDriveFormGroup.value.assertType+' ='+JSON.stringify(element.id)+" = "+(element.id == this.addDriveFormGroup.value.assetType))
      return element.id == this.addDriveFormGroup.value.assetType;
    });
    console.log(this.typeId['description']);
    console.log(this.assertTypeId['productId']); */
    //return false;
    if (this.save) {
      var saveDriveModel = {
        "name": this.addDriveFormGroup.value.name,
        "description": this.addDriveFormGroup.value.description,
        "fromDate": this.addDriveFormGroup.value.fromDate,
        "toDate": this.addDriveFormGroup.value.toDate,
        "depotType": this.addDriveFormGroup.value.depoType,
        "assetType": this.addDriveFormGroup.value.assetType,
        "assetDescription": this.addDriveFormGroup.value.assetDescription,
        "criteria": this.addDriveFormGroup.value.criteria,
        "target_qty": this.addDriveFormGroup.value.targetQuantity,
        "isIdRequired": this.addDriveFormGroup.value.isIdRequired,
        "functionalUnit": this.addDriveFormGroup.value.functionalUnit,
        "checklist": this.addDriveFormGroup.value.checklist,
        "active": this.addDriveFormGroup.value.status
      }
      this.drivesService.saveDriveData(saveDriveModel).subscribe(data => {
        console.log(JSON.stringify(data));
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Drive Data Saved Successfully");
        this.router.navigate(['../../'], { relativeTo: this.route });
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Drive Data Saving Failed.");
      });
    } else if (this.update) {
      var updateDriveModel = {
        "id": this.id,
        "name": this.addDriveFormGroup.value.name,
        "description": this.addDriveFormGroup.value.description,
        "fromDate": this.addDriveFormGroup.value.fromDate,
        "toDate": this.addDriveFormGroup.value.toDate,
        "depotType": this.addDriveFormGroup.value.depoType,
        "assetType": this.addDriveFormGroup.value.assetType,
        "assetDescription": this.addDriveFormGroup.value.assetDescription,
        "criteria": this.addDriveFormGroup.value.criteria,
        "target_qty": this.addDriveFormGroup.value.targetQuantity,
        "isIdRequired": this.addDriveFormGroup.value.isIdRequired,
        "functionalUnit": this.addDriveFormGroup.value.functionalUnit,
        "checklist": this.addDriveFormGroup.value.checklist,
        "active": this.addDriveFormGroup.value.status
      }
      this.drivesService.updateDriveData(updateDriveModel).subscribe(data => {
        console.log(JSON.stringify(data));
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Drive Data Updated Successfully");
        this.router.navigate(['../../'], { relativeTo: this.route });
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Drive Data Updating Failed.");
      })

    }
  }

  onGoBack() {
    this.router.navigate(['../../'], { relativeTo: this.route });
  }
}
