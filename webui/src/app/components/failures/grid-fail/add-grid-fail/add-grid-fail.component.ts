import { Component, OnInit } from '@angular/core';
import { Constants } from 'src/app/common/constants';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';

@Component({
  selector: 'app-add-grid-fail',
  templateUrl: './add-grid-fail.component.html',
  styleUrls: ['./add-grid-fail.component.css']
})
export class AddGridFailComponent implements OnInit {

  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  title:string;
  addGridFailFromGroup: FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  stateList = [{ 'id': 1, "value": 'Yes' }, { 'id': 2, "value": 'No' }];
  driveList = [];
  reportedList=[];
  gridFailFormErrors: any;
  resp: any;
  reportDescriptionFlag=false;
  toMinDate=new Date();
  completeMinDate=new Date();
  divisionList:any;
  constructor(
    private formBuilder: FormBuilder,    
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private route: ActivatedRoute,
    private router: Router,
    private sendAndRequestService: SendAndRequestService
  ) {
    // Reactive form errors
    this.gridFailFormErrors = {
      feedOff: {},
      ffdate:{},
      ftdate: {},
      fduration: {},
      extendedFrom: {},
      efdate: {},
      etdate: {},
      eduration: {},
      maxDemand: {},
      dl: {},
      ie: {},
      remarks: {}
    };
  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
  //  this.getDrivesData();
   // this.findYesNoStatus();
    //this.findDivisions();
    this.createForm();
    if (!isNaN(this.id)) {
      this.addGridFailFromGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      });
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = 'Edit';
      this.getFailureAnalysisDataById(this.id);
    } else {
      this.save = true;
      this.update = false;
      this.title = 'Save';
    }
  }

  findDivisions(){
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.GET_DIVISIONS)
    .subscribe((resp) => {
      this.divisionList = resp;
    });
  }

  createForm() {
    this.addGridFailFromGroup
      = this.formBuilder.group({
        id: 0,
        'feedOff': [null],
        "ffdate":[null],
        'ftdate': [null],
        'fduration': [null],
        'extendedFrom': [null],
        'efdate': [null],
        'etdate': [null],
        'eduration': [null],
        'maxDemand': [null],
        'dl': [null],
        'ie': [null],
        'remarks': [null, Validators.maxLength(250)]
      });
  }

  onFormValuesChanged() {
    for (const field in this.gridFailFormErrors) {
      if (!this.gridFailFormErrors.hasOwnProperty(field)) {
        continue;
      }
      this.gridFailFormErrors[field] = {};
      const control = this.addGridFailFromGroup.get(field);

      if (control && control.dirty && !control.valid) {
        this.gridFailFormErrors[field] = control.errors;
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
  findYesNoStatus() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_STATUS_ITEM+Constants.STATUS_ITEMS.YES_NO_TYPE).subscribe((data) => {
      this.reportedList = data;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }
  
  updateDescription($event){
    if ($event.value) {
      this.reportDescriptionFlag = $event.value == Constants.YES ? true : false;
    }
  }
  getFailureAnalysisDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.EDIT+id)
      .subscribe((resp) => {
        this.resp = resp;
        this.addGridFailFromGroup.patchValue({
          id: this.resp.id,
         // failure_id: this.resp.failure_id,
          feedOff: this.resp.feedOff,
          ffdate:this.resp.ffdate,
          ftdate: this.resp.ftdate,
          fduration: this.resp.fduration ,
          extendedFrom: this.resp.extendedFrom,
          efdate: this.resp.efdate,
          etdate: this.resp.etdate,
          eduration: this.resp.eduration,
          maxDemand: this.resp.maxDemand,
          dl: this.resp.dl,
          ie: this.resp.ie,
          remarks: this.resp.remarks
        });
        this.spinnerService.hide();
      })
  }
  addEvent($event) {
    this.toMinDate = new Date($event.value);
  }
  addEventTargetDate($event) {
    this.completeMinDate = new Date($event.value);
  }
  onAddFailureAnalysisFormSubmit() {
    if (this.addGridFailFromGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    var data = {};
    var message = '';
    var failedMessage = '';
    if (this.save) {
      data = {
        "feedOff": this.addGridFailFromGroup.value.feedOff,
        "ffdate":this.addGridFailFromGroup.value.ffdate,
        "ftdate": this.addGridFailFromGroup.value.ftdate,
        "fduration": this.addGridFailFromGroup.value.fduration,
        "extendedFrom": this.addGridFailFromGroup.value.extendedFrom,
        "efdate": this.addGridFailFromGroup.value.efdate,
        "etdate": this.addGridFailFromGroup.value.etdate,
        "eduration": this.addGridFailFromGroup.value.eduration,
        "maxDemand": this.addGridFailFromGroup.value.maxDemand,
        "dl": this.addGridFailFromGroup.value.dl,
        "ie": this.addGridFailFromGroup.value.ie,
        "remarks": this.addGridFailFromGroup.value.remarks,
        "createdBy": this.loggedUserData.username,
        "createdOn": new Date()
      }    
      message = 'Saved';
      failedMessage = "Saving";
      this.sendAndRequestService.requestForPOST(Constants.app_urls.FAILURES.SAVE,data, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Grid Fail Data "+message+" Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Grid Fail Data "+failedMessage+" Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Grid Fail Data "+failedMessage+" Failed.");
      })
    }else if(this.update){
      data = {
        "id":this.id,
        "feedOff": this.addGridFailFromGroup.value.feedOff,
        "ffdate":this.addGridFailFromGroup.value.ffdate,
        "ftdate": this.addGridFailFromGroup.value.ftdate,
        "fduration": this.addGridFailFromGroup.value.fduration,
        "extendedFrom": this.addGridFailFromGroup.value.extendedFrom,
        "efdate": this.addGridFailFromGroup.value.efdate,
        "etdate": this.addGridFailFromGroup.value.etdate,
        "eduration": this.addGridFailFromGroup.value.eduration,
        "maxDemand": this.addGridFailFromGroup.value.maxDemand,
        "dl": this.addGridFailFromGroup.value.dl,
        "ie": this.addGridFailFromGroup.value.ie,
        "remarks": this.addGridFailFromGroup.value.remarks,
        "updatedBy": this.loggedUserData.username,
        "updatedOn": new Date()
      }   
      message = 'Updated';
      failedMessage = "Updating";
      this.sendAndRequestService.requestForPUT(Constants.app_urls.FAILURES.UPDATE,data, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Grid Fail Data "+message+" Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Grid Fail Data "+failedMessage+" Failed."); 
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Grid Fail Data "+failedMessage+" Failed.");
      })
    }
    
  }
  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }


}
