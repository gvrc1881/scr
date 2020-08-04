import { Component, OnInit } from '@angular/core';
import { Constants } from 'src/app/common/constants';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';

@Component({
  selector: 'app-add-unusual-occurrence-failure',
  templateUrl: './add-unusual-occurrence-failure.component.html',
  styleUrls: ['./add-unusual-occurrence-failure.component.css']
})
export class AddUnusualOccurrenceFailureComponent implements OnInit {

  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  title:string;
  relayIndicationList = [];
  natureOfCloseList = [];
  addUnusualOccurrenceFromGroup: FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  stateList = [{ 'id': 1, "value": 'Yes' }, { 'id': 2, "value": 'No' }];
  driveList = [];
  reportedList=[];
  UnusualOccurrenceFailFormErrors: any;
  feedersList:any;
  extendedFromList:any=[];
  resp: any;
  reportDescriptionFlag=false;
  maxDate = new Date();
  minDate=new Date();
  dateFormat = 'MM-dd-yyyy ';
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
    this.UnusualOccurrenceFailFormErrors = {
      subStation: {}, 
      location: {}, 
      causeOfFailure: {}, 
      fromDateTime: {},
      thruDateTime: {},
      duration: {}, 
      divisionLocal: {},
      internalExternal: {}, 
      impact:{},
      remarks:{}
    };
  }

  ngOnInit() {
    //this.findRelayIndicationStatus();
  //  this.findNatureOfCloseStatus();
    this.findFeedersList();
    this.id = +this.route.snapshot.params['id'];    
    this.createForm();
    if (!isNaN(this.id)) {
      this.addUnusualOccurrenceFromGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      });
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = 'Edit';
      this.getUnusualOccurrenceFailDataById(this.id);
    } else {
      this.save = true;
      this.update = false;
      this.title = 'Save';
    }
  }

  findFeedersList(){
    
    this.spinnerService.show();
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_DEPOTTYPE_FOR_OHE)
   
      .subscribe((response) => {
       
        console.log("depots======"+JSON.stringify(response));
        this.feedersList = response;
       
      //  this.extendedFromList = response;
        this.spinnerService.hide();
      })
  }
  createForm() {
    this.addUnusualOccurrenceFromGroup
      = this.formBuilder.group({
        id: 0,
        'subStation': [null], 
        'location': [null], 
        'causeOfFailure': [null], 
        'fromDateTime': [null],
        'thruDateTime': [null],
        'duration': [null], 
        'divisionLocal': [null],
        'internalExternal': [null], 
        'impact': [null],
        'remarks': [null, Validators.maxLength(250)]
      });
  }

  onFormValuesChanged() {
    for (const field in this.UnusualOccurrenceFailFormErrors) {
      if (!this.UnusualOccurrenceFailFormErrors.hasOwnProperty(field)) {
        continue;
      }
      this.UnusualOccurrenceFailFormErrors[field] = {};
      const control = this.addUnusualOccurrenceFromGroup.get(field);
      if (control && control.dirty && !control.valid) {
        this.UnusualOccurrenceFailFormErrors[field] = control.errors;
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
  getUnusualOccurrenceFailDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.FAILURE_TYPE_BY_ID+id)
      .subscribe((resp) => {
        this.resp = resp;
        console.log(this.resp);
        this.addUnusualOccurrenceFromGroup.patchValue({
          id: this.resp.id,
          subStation:this.resp.subStation,
          location:this.resp.location,
          causeOfFailure:this.resp.causeOfFailure,
          fromDateTime:!!this.resp.fromDateTime ? new Date(this.resp.fromDateTime) : '',
          thruDateTime:!!this.resp.thruDateTime ? new Date(this.resp.thruDateTime) : '',
          duration:this.resp.duration, 
          divisionLocal:this.resp.divisionLocal,
          internalExternal:this.resp.internalExternal,
          impact:this.resp.impact,
          remarks: this.resp.remarks
        });
        /* this.feedersList.map(element => {
          if(element.id != this.resp.id){
            this.extendedFromList.push(element);
          }
        }); */
        this.spinnerService.hide();

      })
  }
  findRelayIndicationStatus(){
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_STATUS_ITEM + Constants.STATUS_ITEMS.RELAY_INDICATION)
    .subscribe((resp) => {
      this.relayIndicationList = resp;
    });
  }

  findNatureOfCloseStatus(){
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_STATUS_ITEM + Constants.STATUS_ITEMS.NATURE_OF_CLOSE)
    .subscribe((resp) => {
      this.natureOfCloseList = resp;
    });
  }

  addEvent($event) {
    this.minDate = new Date($event.value);
  }
  addEventTargetDate($event) {
    this.minDate = new Date($event.value);
  }
  onAddFailureAnalysisFormSubmit() {
    if (this.addUnusualOccurrenceFromGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    var data = {};
    var message = '';
    var failedMessage = '';
    if (this.save) {
      data = {
        'subStation': this.addUnusualOccurrenceFromGroup.value.subStation , 
        'location': this.addUnusualOccurrenceFromGroup.value.equipment , 
        'causeOfFailure': this.addUnusualOccurrenceFromGroup.value.cascadeAssets, 
        'fromDateTime': this.addUnusualOccurrenceFromGroup.value.fromDateTime,
        'thruDateTime': this.addUnusualOccurrenceFromGroup.value.thruDateTime,
        'duration': this.addUnusualOccurrenceFromGroup.value.duration, 
        'divisionLocal': this.addUnusualOccurrenceFromGroup.value.divisionLocal,
        'internalExternal': this.addUnusualOccurrenceFromGroup.value.internalExternal, 
        'impact':this.addUnusualOccurrenceFromGroup.value.impact,
        'remarks': this.addUnusualOccurrenceFromGroup.value.remarks,
        "typeOfFailure":Constants.FAILURE_TYPES.UNUSUAL_OCCURRENCE_FAILURE,
        "createdBy": this.loggedUserData.username,
        "createdOn": new Date()
      }    
      message = 'Saved';
      failedMessage = "Saving";
      this.sendAndRequestService.requestForPOST(Constants.app_urls.FAILURES.FAILURE_TYPE_UPDATE,data, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("UnusualOccurrence Fail Data "+message+" Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("UnusualOccurrence Fail Data "+failedMessage+" Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("UnusualOccurrence Fail Data "+failedMessage+" Failed.");
      })
    }else if(this.update){
      data = {
        "id":this.id,
        'subStation': this.addUnusualOccurrenceFromGroup.value.subStation , 
        'location': this.addUnusualOccurrenceFromGroup.value.equipment , 
        'causeOfFailure': this.addUnusualOccurrenceFromGroup.value.cascadeAssets, 
        'fromDateTime': this.addUnusualOccurrenceFromGroup.value.fromDateTime,
        'thruDateTime': this.addUnusualOccurrenceFromGroup.value.thruDateTime,
        'duration': this.addUnusualOccurrenceFromGroup.value.duration, 
        'divisionLocal': this.addUnusualOccurrenceFromGroup.value.divisionLocal,
        'internalExternal': this.addUnusualOccurrenceFromGroup.value.internalExternal, 
        'impact':this.addUnusualOccurrenceFromGroup.value.impact,
        'remarks': this.addUnusualOccurrenceFromGroup.value.remarks,
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
        this.commonService.showAlertMessage("UnusualOccurrence Fail Data "+message+" Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("UnusualOccurrence Fail Data "+failedMessage+" Failed."); 
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("UnusualOccurrence Fail Data "+failedMessage+" Failed.");
      })
    }
    
  }
  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }


}
