import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';


@Component({
  selector: 'app-add-standard-Phase-activity',
  templateUrl: './add-standard-phase-activity.component.html',
  
})
export class AddStandardPhaseActivityComponent implements OnInit {

  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  resp: any;
  title:string = Constants.EVENTS.ADD;
  addStandardPhaseActivityFormGroup: FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  isCheckList = [{ 'id': 1, "value": 'Yes' }, { 'id': 2, "value": 'No' }];
  isObjectIdRequired = [{ 'id': 1, "value": 'Yes' }, { 'id': 2, "value": 'No' }];
  depotType = [{ 'id': 1, "value": 'OHE' }, { 'id': 2, "value": 'PSI' },{ 'id': 3, "value": 'TSS'},{ 'id': 4, "value": 'SSP'},{ 'id': 5, "value": 'SP'}];
  
  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private sendAndRequestService:SendAndRequestService
  ) {
   
  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
    if (!isNaN(this.id)) {  
      this.updateStandardPhaseActivityForm();   
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;
       this.getSPADataById(this.id);
    } else {
      this.createStandardPhaseActivityForm();
      this.save = true;
      this.update = false;
      this.title = Constants.EVENTS.ADD;
    }
  }
  
  
  createStandardPhaseActivityForm() {
    this.addStandardPhaseActivityFormGroup = this.formBuilder.group({
    id: 0, 
    'assetType':[null],
    'depotType':[null], 
    'isCheckList':[null],
    'isObjectIdRequired':[null],
    'name': [null, Validators.compose([Validators.required])],
    'uom': [null],
    'standardPhaseId':[null,Validators.compose([Validators.required])],
    'dependencyToStart':[null],
    'description': [null],
    });
    }
    
    updateStandardPhaseActivityForm() {
    this.addStandardPhaseActivityFormGroup = this.formBuilder.group({
    id: 0,
    'assetType':[null],
    'depotType':[null], 
    'isCheckList':[null],
    'isObjectIdRequired':[null],
    'name': [null, Validators.compose([Validators.required])],
    'uom': [null],
    'standardPhaseId':[null,Validators.compose([Validators.required])],
    'dependencyToStart':[null],
    'description': [null],
    });
    }
  

   public get f() { return this.addStandardPhaseActivityFormGroup.controls; } 


  getSPADataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.STANDARD_PHASE_ACTIVITY.GET_SPA_ID+id)
    .subscribe((resp) => {
        this.resp = resp;
        this.addStandardPhaseActivityFormGroup.patchValue({
          id: this.resp.id,
          assetType: this.resp.assetType,
          depotType: this.resp.depotType,
          isCheckList: this.resp.isCheckList,
          isObjectIdRequired: this.resp.isObjectIdRequired,
          name: this.resp.name,
          uom: this.resp.uom,
          dependencyToStart: this.resp.dependencyToStart,
          description: this.resp.description,
          
          
        });
      
      })
  }
  onAddSPAFormSubmit() {
    this.isSubmit = true;
    if (this.addStandardPhaseActivityFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    if (this.save) {
      var saveSpaModel = {
        "assetType": this.addStandardPhaseActivityFormGroup.value.assetType,
        "depotType": this.addStandardPhaseActivityFormGroup.value.depotType,
        "isCheckList": this.addStandardPhaseActivityFormGroup.value.isCheckList,
        "isObjectIdRequired": this.addStandardPhaseActivityFormGroup.value.isObjectIdRequired,
        "name": this.addStandardPhaseActivityFormGroup.value.name,
        "uom": this.addStandardPhaseActivityFormGroup.value.uom,
        "standardPhaseId": this.addStandardPhaseActivityFormGroup.value.standardPhaseId,
        "dependencyToStart": this.addStandardPhaseActivityFormGroup.value.dependencyToStart, 
        "description": this.addStandardPhaseActivityFormGroup.value.description,    
        
      }
      this.sendAndRequestService.requestForPOST(Constants.app_urls.STANDARD_PHASE_ACTIVITY.SAVE_SPA, saveSpaModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
     
        if (this.resp.code == Constants.CODES.SUCCESS) {
          this.commonService.showAlertMessage("Standard Phase Activity  Data Saved Successfully");
          this.router.navigate(['../'], { relativeTo: this.route });
        } else {
          this.commonService.showAlertMessage("Standard Phase Activity Data Saving Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Standard Phase Activity Data Saving Failed.");
      });
    } else if (this.update) {
      var updateSpaModel = {
        "id": this.id,
        "assetType": this.addStandardPhaseActivityFormGroup.value.assetType,
        "depotType": this.addStandardPhaseActivityFormGroup.value.depotType,
        "isCheckList": this.addStandardPhaseActivityFormGroup.value.isCheckList,
        "isObjectIdRequired": this.addStandardPhaseActivityFormGroup.value.isObjectIdRequired,
        "name": this.addStandardPhaseActivityFormGroup.value.name,
        "uom": this.addStandardPhaseActivityFormGroup.value.uom,
        "standardPhaseId": this.addStandardPhaseActivityFormGroup.value.standardPhaseId,
        "dependencyToStart": this.addStandardPhaseActivityFormGroup.value.dependencyToStart, 
        "description": this.addStandardPhaseActivityFormGroup.value.description,
      }
      this.sendAndRequestService.requestForPUT(Constants.app_urls.STANDARD_PHASE_ACTIVITY.UPDATE_SPA, updateSpaModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Standard Phase Activity Data Updated Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Standard Phase Activity Data Updating Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Standard Phase Activity Data Updating Failed.");
      })

    }
  }

  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }
  
}