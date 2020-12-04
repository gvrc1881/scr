import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { GroupsSectionsModel } from 'src/app/models/groups-sections.model';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';

@Component({
  selector: 'app-add-groups-sections',
  templateUrl: './add-groups-sections.component.html',
  styleUrls: []
})
export class AddGroupsSectionsComponent implements OnInit {
    FiledLabels = FieldLabelsConstant.LABELS;
    Titles = FieldLabelsConstant.TITLE;
    save: boolean = true;
    update: boolean = false;
    id: number = 0;
    isSubmit: boolean = false;
    loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
    resp: any;    
    title:string;  
    work:any; 
    workGroupData:any; 
    doublingTripplingList:any;
    divisionList:any;
    stationList:any;
    addGroupsSectionsFormGroup: FormGroup;
    gropsSectionsFormErrors:any;
    pattern = "[a-zA-Z][a-zA-Z ]*";
    selectedWork: any;
    constructor(
        private formBuilder: FormBuilder,
        private spinnerService: Ng4LoadingSpinnerService,
        private commonService: CommonService,
        private route: ActivatedRoute,
        private router: Router,
        private sendAndRequestService:SendAndRequestService
      ) {
        this.gropsSectionsFormErrors = {            
            workId:{},
            workGroup: {},
            section: {},
            agency:{},
            doublingTrippling:{},
            division: {},
            code:{},
            description:{},
            tkm:{},
            rkm:{},
            sidingYardStation:{}
          };
        
      }
      ngOnInit() {      
        this.findProjects();  
        this.findDoublingTrippling();
        this.findSations();
        this.findDivisions();
        this.id = +this.route.snapshot.params['id'];
          if (!isNaN(this.id)) {  
          
          this.updateGroupsSectionsForm();  
          this.addGroupsSectionsFormGroup.valueChanges.subscribe(() => {
            this.onFormValuesChanged();
          });
          this.spinnerService.show();
          this.save = false;
          this.update = true;
          this.title = Constants.EVENTS.UPDATE;
          this.getGroupsSectionsDataById(this.id);
        } else {
          this.createGroupsSectionsForm();         
          this.title = Constants.EVENTS.ADD;
        }
        
      }

      onFormValuesChanged() {
        for (const field in this.gropsSectionsFormErrors) {
          if (!this.gropsSectionsFormErrors.hasOwnProperty(field)) {
            continue;
          }
          this.gropsSectionsFormErrors[field] = {};
          const control = this.addGroupsSectionsFormGroup.get(field);
          if (control && control.dirty && !control.valid) {
            this.gropsSectionsFormErrors[field] = control.errors;
          }
        }
      }
     
    

    createGroupsSectionsForm() {
    this.addGroupsSectionsFormGroup = this.formBuilder.group({
      id: 0,
      'work':[null, Validators.compose([Validators.required])],
      'workGroup': [null, Validators.compose([Validators.required])],
      'section': [null,Validators.compose([Validators.required]),this.duplicateProjectAndGroupAndSection.bind(this)],
      'agency':[null],
      'doublingTrippling':[null],
      'division': [null],
      'code':[null],
      'description':[null],
      'tkm':[null],
       'rkm':[null],
       'sidingYardStation':[null]
    });
  }

  updateGroupsSectionsForm() {
    this.addGroupsSectionsFormGroup = this.formBuilder.group({
      id: 0,
      'work':[null, Validators.compose([Validators.required])],
      'workGroup': [null, Validators.compose([Validators.required])],
      'section': [null,Validators.compose([Validators.required]),this.duplicateProjectAndGroupAndSectionAndId.bind(this)],
      'agency':[null],
      'doublingTrippling':[null],
      'division': [null],
      'code':[null],
      'description':[null],
      'tkm':[null],
       'rkm':[null],
       'sidingYardStation':[null]
    });
  }
  getGroupsSectionsDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.PROJECT_ADMIN.GROUPS_SECTIONS.GET_GROUPS_SECTIONS_BY_ID+id)
    .subscribe((response) => {
        this.resp = response;
        let work = this.resp.workId
        console.log("list==="+this.resp.workId.id);
        //console.log("projectlist==="+JSON.stringify(this.workGroupData));
        //console.log("update==="+JSON.stringify(this.resp.workId));
        if (this.resp) {
          this.addGroupsSectionsFormGroup.patchValue({
            id: this.resp.id,
            work:this.resp.workId.id,
            workGroup: this.resp.workGroup,
            section: this.resp.section,
            agency: this.resp.agency,
            doublingTrippling: this.resp.doublingTrippling,
            division: this.resp.division,
            code: this.resp.code,
            description: this.resp.description,
            tkm: this.resp.tkm,
            rkm: this.resp.rkm,
            sidingYardStation:this.resp.sidingYardStation
            
          });
          this.getWork();
          console.log('*** group values **'+this.addGroupsSectionsFormGroup.value.work.id);
          
        }
        this.spinnerService.hide();
      })
  }

  findProjects()
  {
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WORK).subscribe((data) => {
        this.workGroupData = data;
}
      );
  }
  findDoublingTrippling(){
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_STATUS_ITEM + Constants.STATUS_ITEMS.DOUBLE_TRIPLE_TYPE)
    .subscribe((resp) => {
      this.doublingTripplingList = resp;
    });
  }

  findDivisions(){
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.GET_DIVISIONS)
    .subscribe((resp) => {
      this.divisionList = resp;
    });
  }
  findSations(){
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_STATUS_ITEM + Constants.STATUS_ITEMS.STATION_YARD_SIDING)
    .subscribe((resp) => {
      this.stationList = resp;
    });
  }

  getWork () {
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WORK_ID+this.addGroupsSectionsFormGroup.value.work).subscribe((data) => {
         this.selectedWork = data;
     });
  }

  groupsSectionsFormSubmit() {
    this.isSubmit = true;
    if (this.addGroupsSectionsFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    
    // this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WORK_ID+this.addGroupsSectionsFormGroup.value.workId).subscribe((data) => {
    //   this.selectedWork = data;
    // });
      if (this.save) {
        var saveGroupsSectionsModel = {
          "workId": this.selectedWork,
          "workGroup": this.addGroupsSectionsFormGroup.value.workGroup,
          "section": this.addGroupsSectionsFormGroup.value.section,
          "agency": this.addGroupsSectionsFormGroup.value.agency,
          "doublingTrippling": this.addGroupsSectionsFormGroup.value.doublingTrippling,
          "division": this.addGroupsSectionsFormGroup.value.division,
          "code": this.addGroupsSectionsFormGroup.value.code,
          "description": this.addGroupsSectionsFormGroup.value.description,
          "tkm": this.addGroupsSectionsFormGroup.value.tkm,  
          "rkm": this.addGroupsSectionsFormGroup.value.rkm,    
          "sidingYardStation":  this.addGroupsSectionsFormGroup.value.sidingYardStation,                                                         
          "createdBy": this.loggedUserData.username,
            "createdOn": new Date()
  
        }
        this.sendAndRequestService.requestForPOST(Constants.app_urls.PROJECT_ADMIN.GROUPS_SECTIONS.SAVE, saveGroupsSectionsModel, false).subscribe(response => {
          this.spinnerService.hide();
          this.resp = response;
       
          if (this.resp.code == Constants.CODES.SUCCESS) {
            this.commonService.showAlertMessage("groups sections Data Saved Successfully");
            this.router.navigate(['../'], { relativeTo: this.route });
          } else {
            this.commonService.showAlertMessage("groups sections Saving Failed.");
          }
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("groups sections Saving Failed.");
        });
      } else if (this.update) {
        var updateGroupsSectionsModel = {
          "id": this.id,
          "workId": this.selectedWork,
          "workGroup": this.addGroupsSectionsFormGroup.value.workGroup,
          "section": this.addGroupsSectionsFormGroup.value.section,
          "agency": this.addGroupsSectionsFormGroup.value.agency,
          "doublingTrippling": this.addGroupsSectionsFormGroup.value.doublingTrippling,
          "division": this.addGroupsSectionsFormGroup.value.division,
          "code": this.addGroupsSectionsFormGroup.value.code,
          "description": this.addGroupsSectionsFormGroup.value.description,
          "tkm": this.addGroupsSectionsFormGroup.value.tkm,  
          "rkm": this.addGroupsSectionsFormGroup.value.rkm,    
          "sidingYardStation":  this.addGroupsSectionsFormGroup.value.sidingYardStation,                                        
          "updatedBy": this.loggedUserData.username,
          "updatedOn": new Date()
        }
        this.sendAndRequestService.requestForPUT(Constants.app_urls.PROJECT_ADMIN.GROUPS_SECTIONS.UPDATE, updateGroupsSectionsModel, false).subscribe(response => {
          this.spinnerService.hide();
          this.resp = response;
          if (this.resp.code == Constants.CODES.SUCCESS) {
          this.commonService.showAlertMessage("groups sections Updated Successfully");
          this.router.navigate(['../'], { relativeTo: this.route });
          }else{
            this.commonService.showAlertMessage("groups sections Updating Failed.");
          }
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("groups sections Updating Failed.");
        })
      }
  }

  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }

  duplicateProjectAndGroupAndSection() {

    const q = new Promise((resolve, reject) => {

      let work = this.addGroupsSectionsFormGroup.value.work; 
      let group: string = this.addGroupsSectionsFormGroup.controls['workGroup'].value;
      let section: string = this.addGroupsSectionsFormGroup.controls['section'].value;        
      
      this.sendAndRequestService.requestForGET(Constants.app_urls.PROJECT_ADMIN.GROUPS_SECTIONS.EXIST_PROJECT_GROUP_SECTION+work+'/'+group+'/'+section)
      .subscribe((duplicate) => {      
        if (duplicate) {
          resolve({ 'duplicateProjectAndGroupAndSection': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateProjectAndGroupAndSection': true }); });
    });
    return q;
  }

  duplicateProjectAndGroupAndSectionAndId() {
    const q = new Promise((resolve, reject) => {

          let Id=this.id;        
         let work = this.addGroupsSectionsFormGroup.value.work; 
         console.log('** duplicate ***'+JSON.stringify(work));
         let group: string = this.addGroupsSectionsFormGroup.controls['workGroup'].value;
         let section: string = this.addGroupsSectionsFormGroup.controls['section'].value;   
        
      if (work) {
        this.sendAndRequestService.requestForGET
      (Constants.app_urls.PROJECT_ADMIN.GROUPS_SECTIONS.EXIST_PROJECT_GROUP_SECTION_ID+Id+'/'+work+'/'+group+'/'+section)
      .subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateProjectAndGroupAndSectionAndId': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateProjectAndGroupAndSectionAndId': true }); });  
      }
      
    });
    return q;
  }

}
