import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { DrivesService } from 'src/app/services/drives.service';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { DriveTargetModel } from 'src/app/models/drive.model';

@Component({
  selector: 'app-add-drive-target',
  templateUrl: './add-drive-target.component.html',
  styleUrls: ['./add-drive-target.component.css']
})
export class AddDriveTargetComponent implements OnInit {
  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  title:string;
  addDriveTargetFormGroup: FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  stateList = [{ 'id': 1, "value": 'Yes' }, { 'id': 2, "value": 'No' }];
  driveList = [];
  driveTargetFormErrors: any;
  resp: any;
  depoTypeList = [];
  functionalUnitList: any;
  allFunctionalUnitsList: any;
  driveTargetList:any;
  //stateList: any;
  constructor(
    private formBuilder: FormBuilder,    
    private drivesService: DrivesService,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private route: ActivatedRoute,
    private router: Router,
  ) {
    // Reactive form errors
    this.driveTargetFormErrors = {
      unitType: {},
      unitName: {},
      target: {},
      poulation: {},
      drive: {}
    };
  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
    this.findFunctionalUnits();
    this.findDepoTypeList();
    this.getDrivesData();
    this.createForm();
    this.getDriveTargetData();
    if (!isNaN(this.id)) {
      this.addDriveTargetFormGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      });
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = 'Edit';
      this.getDriveTargetDataById(this.id);
    } else {
      this.save = true;
      this.update = false;
      this.title = 'Save';
    }
  }
  getDriveTargetData() {
    const driveTarget: DriveTargetModel[] = [];
    this.drivesService.getDriveTargetData().subscribe((data) => {
      console.log(JSON.stringify(data))
      this.driveTargetList = data;                  
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }
  createForm() {
    this.addDriveTargetFormGroup
      = this.formBuilder.group({
        id: 0,
        'unitType': [null, Validators.compose([Validators.required]), this.duplicateCombination.bind(this)],
        'unitName': [null, Validators.compose([Validators.required]), this.duplicateCombination.bind(this)],
        'target': [null],
        'poulation': [null],
        'drive': [null, Validators.compose([Validators.required])],
      });
  }
  duplicateCombination() {
    const q = new Promise((resolve, reject) => {
    let unitName = this.addDriveTargetFormGroup.controls['unitName'].value;
    let unitType = this.addDriveTargetFormGroup.controls['unitType'].value;
        var filteredArray = !!this.driveTargetList && 
        this.driveTargetList.filter(function(interval){          
          return interval.unitName == unitName && interval.unitType == unitType;
        });        
        if(filteredArray.length !== 0){
          resolve({ 'duplicateCombination': true });
        } else {
          resolve(null);
        }
    });
    return q;
  }
  findDepoTypeList() {
    this.drivesService.findDepoTypeList()
      .subscribe((depoTypes) => {
        this.depoTypeList = depoTypes;
      })
  }
  findFunctionalUnits() {
    this.drivesService.findFunctionslUnits()
      .subscribe((units) => {
        this.allFunctionalUnitsList = units;
      })
  }
  updateAssertType($event) {
    if ($event.value) {
      //this.findAssetTypeList(Constants.ASSERT_TYPE[$event.value]);
      this.functionalUnitList = [];
      this.functionalUnitList = this.allFunctionalUnitsList.filter(element => {
        return element.depotType == $event.value;
      });
    }
  }

  onFormValuesChanged() {
    for (const field in this.driveTargetFormErrors) {
      if (!this.driveTargetFormErrors.hasOwnProperty(field)) {
        continue;
      }
      this.driveTargetFormErrors[field] = {};
      const control = this.addDriveTargetFormGroup.get(field);

      if (control && control.dirty && !control.valid) {
        this.driveTargetFormErrors[field] = control.errors;
      }
    }
  }

  getDrivesData() {
    this.drivesService.getDrivesData().subscribe((data) => {
      this.driveList = data;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }
  getDriveTargetDataById(id) {
    this.drivesService.findDriveTargetDataById(id)
      .subscribe((resp) => {
        this.resp = resp;
        this.addDriveTargetFormGroup.patchValue({
          id: this.resp.id,
          unitType: this.resp.unitType,
          unitName: this.resp.unitName,
          target: this.resp.target,
          poulation: this.resp.poulation,
          drive: this.resp.driveId['id']
        });
        this.spinnerService.hide();
      })
  }

  onAddDriveTargetFormSubmit() {
    if (this.addDriveTargetFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    //console.log(this.addDriveTargetFormGroup.value);
    var data = {};
    var message = '';
    var failedMessage = '';
    if (this.save) {
      data = {
        "unitType": this.addDriveTargetFormGroup.value.unitType,
        "unitName": this.addDriveTargetFormGroup.value.unitName,
        "target": this.addDriveTargetFormGroup.value.target,
        "poulation": this.addDriveTargetFormGroup.value.poulation,
        "driveId": this.addDriveTargetFormGroup.value.drive,
        "createdBy": "1",
        "createdOn": "2020-04-01",
        "updatedBy": "1",
        "updatedOn": "2020-04-01"
      }    
      message = 'Saved';
      failedMessage = "Saving";
      this.drivesService.saveDriveTargetData(data).subscribe(response => {
      //  console.log(JSON.stringify(response));
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Drive Target Data "+message+" Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Drive Target Data "+failedMessage+" Failed.");
      })
    }else if(this.update){
      data = {
        "id":this.id,
        "unitType": this.addDriveTargetFormGroup.value.unitType,
        "unitName": this.addDriveTargetFormGroup.value.unitName,
        "target": this.addDriveTargetFormGroup.value.target,
        "poulation": this.addDriveTargetFormGroup.value.poulation,
        "driveId": this.addDriveTargetFormGroup.value.drive,
        "createdBy": "1",
        "createdOn": "2020-04-01",
        "updatedBy": "1",
        "updatedOn": "2020-04-01"
      }   
      message = 'Updated';
      failedMessage = "Updating";
      this.drivesService.updateDriveTargetData(data).subscribe(response => {
        //console.log(JSON.stringify(response));
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Drive Target Data "+message+" Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Drive Target Data "+failedMessage+" Failed.");
      })
    }
    
  }
  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }

}
