import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { DriveTargetModel } from 'src/app/models/drive.model';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';

@Component({
  selector: 'app-add-drive-target',
  templateUrl: './add-drive-target.component.html',
  styleUrls: []
})
export class AddDriveTargetComponent implements OnInit {
  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
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
  constructor(
    private formBuilder: FormBuilder,    
    private sendAndRequestService: SendAndRequestService,
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
    this.getDriveTargetData();
    if (!isNaN(this.id)) {
      this.updateForm();
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
      this.createForm();
    }
  }
  getDriveTargetData() {
    const driveTarget: DriveTargetModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.TARGETS.GET_TARGETS).subscribe((data) => {
      this.driveTargetList = data;                  
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }
  updateForm() {
    this.addDriveTargetFormGroup
      = this.formBuilder.group({
        id: 0,
        'unitType': [null, Validators.compose([Validators.required])],
        'unitName': [null, Validators.compose([Validators.required]),this.duplicateCombinationId.bind(this)],
        'target': [null],
        'poulation': [null],
        'drive': [null, Validators.compose([Validators.required])],
      });
  }
  createForm() {
    this.addDriveTargetFormGroup
      = this.formBuilder.group({
        id: 0,
        'unitType': [null, Validators.compose([Validators.required])],
        'unitName': [null, Validators.compose([Validators.required]), this.duplicateCombination.bind(this)],
        'target': [null],
        'poulation': [null],
        'drive': [null, Validators.compose([Validators.required])],
      });
  }
    duplicateCombination() {    
      let unitType = this.addDriveTargetFormGroup.controls['unitType'].value;
      let unitName = this.addDriveTargetFormGroup.controls['unitName'].value;
      const q = new Promise((resolve, reject) => {
    
          var filteredArray = !!this.driveTargetList && 
          this.driveTargetList.filter(function(interval){          
            return interval.unitType == unitType && interval.unitName == unitName;
          });        
          if(filteredArray.length != 0){
          resolve({ 'duplicateCombination': true });
          } else {
            resolve(null);
         }
     });
      return q;
    }
    
   duplicateCombinationId() {
   
      let id=this.id;
      let unitType = this.addDriveTargetFormGroup.controls['unitType'].value;
      let unitName = this.addDriveTargetFormGroup.controls['unitName'].value;
      const q = new Promise((resolve, reject) => {   
                this.sendAndRequestService.requestForGET(
              Constants.app_urls.DRIVE.TARGETS.EXIST_TARGET+id+'/'+unitType
              +'/'+unitName
             ).subscribe
              ((duplicate) => {
        if (duplicate) {
           resolve({ 'duplicateCombination': true });
         } else {
           resolve(null);
         }
       }, () => { resolve({ 'duplicateCombination': true }); });
     });
     return q;
    }
  findDepoTypeList() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FUNCTIONAL_LOCATION_TYPES)
      .subscribe((depoTypes) => {
        this.depoTypeList = depoTypes;
      })
  }
  findFunctionalUnits() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY_NAMES)
      .subscribe((units) => {
        this.allFunctionalUnitsList = units;
      })
  }
  updateAssertType($event) {
    if ($event.value) {
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
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE.GET_DRIVES).subscribe((data) => {
      this.driveList = data;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }
  getDriveTargetDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.TARGETS.EDIT + id)
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
        "createdBy": this.loggedUserData.username,
        "createdOn": new Date()
      }    
      message = 'Saved';
      failedMessage = "Saving";
      this.sendAndRequestService.requestForPOST(Constants.app_urls.DRIVE.TARGETS.SAVE ,data, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Drive Target Data "+message+" Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Drive Target Data "+failedMessage+" Failed.");
        }
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
        "updatedBy": this.loggedUserData.username,
        "updatedOn": new Date()
      }   
      message = 'Updated';
      failedMessage = "Updating";
      this.sendAndRequestService.requestForPUT(Constants.app_urls.DRIVE.TARGETS.UPDATE,data, false).subscribe(response => {
        this.spinnerService.hide();
      
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Drive Target Data "+message+" Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Drive Target Data "+failedMessage+" Failed.");
        }
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
