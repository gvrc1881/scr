import { Component, OnInit } from '@angular/core';
import { Constants } from 'src/app/common/constants';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';

@Component({
  selector: 'app-add-grid-failure',
  templateUrl: './add-grid-failure.component.html',
  styleUrls: ['./add-grid-failure.component.css']
})
export class AddGridFailureComponent implements OnInit {

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
  feedersList:any;
  extendedFromList:any=[];
  resp: any;
  reportDescriptionFlag=false;
  maxDate = new Date();
  minDate=new Date();
  dateFormat = 'MM-dd-yyyy HH:MM:SS';
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
    this.findFeedersList();
    this.id = +this.route.snapshot.params['id'];    
    this.createForm();
    if (!isNaN(this.id)) {
      this.addGridFailFromGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      });
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = 'Edit';
      this.getGridFailDataById(this.id);
    } else {
      this.save = true;
      this.update = false;
      this.title = 'Save';
    }
  }

  findFeedersList(){
    this.spinnerService.show();
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_CONSUMPTION.FIND_TSS_FEEDER_MASTER )
      .subscribe((response) => {
        console.log(response)
        this.feedersList = response;
      //  this.extendedFromList = response;
        this.spinnerService.hide();
      })
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
 
  updateFeedOff($event){
    if ($event.value) {
      console.log($event.value)
      this.extendedFromList = [];
      //this.reportDescriptionFlag = $event.value == Constants.YES ? true : false;
      this.feedersList.map(element => {
        if(element.feederName != $event.value){
          this.extendedFromList.push(element);
        }
      });
    }
  }
  getGridFailDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.FAILURE_TYPE_BY_ID+id)
      .subscribe((resp) => {
        this.resp = resp;
        console.log(this.resp);
        this.addGridFailFromGroup.patchValue({
          id: this.resp.id,
          feedOff: this.resp.feedOf,
          ffdate: new Date(this.resp.fromDateTime),
          ftdate: new Date(this.resp.thruDateTime),
          fduration: this.resp.duration ,
          extendedFrom: this.resp.extendedOf,
          efdate: this.resp.feedExtendedFromDateTime != null ? new Date(this.resp.feedExtendedFromDateTime) : null,
          etdate: this.resp.feedExtendedThruDateTime != null ? new Date(this.resp.feedExtendedThruDateTime) : null,
          eduration: this.resp.feedExtendedDuration,
          maxDemand: this.resp.maxDemand,
          dl: this.resp.divisionLocal,
          ie: this.resp.internalExternal,
          remarks: this.resp.remarks
        });
        this.feedersList.map(element => {
          if(element.id != this.resp.id){
            this.extendedFromList.push(element);
          }
        });
        this.spinnerService.hide();

      })
  }
  addEvent($event) {
    this.minDate  = new Date($event.value);
  }
  addEventTargetDate($event) {
    this.minDate  = new Date($event.value);
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
        "feedOf": this.addGridFailFromGroup.value.feedOff,
        "fromDateTime":this.addGridFailFromGroup.value.ffdate,
        "thruDateTime": this.addGridFailFromGroup.value.ftdate,
        "duration": this.addGridFailFromGroup.value.fduration,
        "extendedOf": this.addGridFailFromGroup.value.extendedFrom,
        "feedExtendedFromDateTime": this.addGridFailFromGroup.value.efdate,
        "feedExtendedThruDateTime": this.addGridFailFromGroup.value.etdate,
        "feedExtendedDuration": this.addGridFailFromGroup.value.eduration,
        "maxDemand": this.addGridFailFromGroup.value.maxDemand,
        "divisionLocal": this.addGridFailFromGroup.value.dl,
        "internalExternal": this.addGridFailFromGroup.value.ie,
        "remarks": this.addGridFailFromGroup.value.remarks,
        "typeOfFailure":Constants.FAILURE_TYPES.GRID_FAILURE,
        "createdBy": this.loggedUserData.username,
        "createdOn": new Date()
      }    
      message = 'Saved';
      failedMessage = "Saving";
      this.sendAndRequestService.requestForPOST(Constants.app_urls.FAILURES.FAILURE_TYPE_UPDATE,data, false).subscribe(response => {
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
        "feedOf": this.addGridFailFromGroup.value.feedOff,
        "fromDateTime":this.addGridFailFromGroup.value.ffdate,
        "thruDateTime": this.addGridFailFromGroup.value.ftdate,
        "duration": this.addGridFailFromGroup.value.fduration,
        "extendedOf": this.addGridFailFromGroup.value.extendedFrom,
        "feedExtendedFromDateTime": this.addGridFailFromGroup.value.efdate,
        "feedExtendedThruDateTime": this.addGridFailFromGroup.value.etdate,
        "feedExtendedDuration": this.addGridFailFromGroup.value.eduration,
        "maxDemand": this.addGridFailFromGroup.value.maxDemand,
        "divisionLocal": this.addGridFailFromGroup.value.dl,
        "internalExternal": this.addGridFailFromGroup.value.ie,
        "remarks": this.addGridFailFromGroup.value.remarks,
        "typeOfFailure":this.resp.typeOfFailure,
        "updatedBy": this.loggedUserData.username,
        "updatedOn": new Date()
      }   
      message = 'Updated';
      failedMessage = "Updating";
      this.sendAndRequestService.requestForPOST(Constants.app_urls.FAILURES.FAILURE_TYPE_UPDATE,data, false).subscribe(response => {
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
