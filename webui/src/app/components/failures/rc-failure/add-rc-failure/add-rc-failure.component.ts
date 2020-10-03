import { Component, OnInit } from '@angular/core';
import { Constants } from 'src/app/common/constants';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';

@Component({
  selector: 'app-add-rc-failure',
  templateUrl: './add-rc-failure.component.html',
  styleUrls: ['./add-rc-failure.component.css']
})
export class AddRcFailureComponent implements OnInit {
  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  title:string;
  relayIndicationList = [];
  natureOfCloseList = [];
  addRcFailFromGroup: FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  stateList = [{ 'id': 1, "value": 'Yes' }, { 'id': 2, "value": 'No' }];
  driveList = [];
  reportedList=[];
  cbFailFormErrors: any;
  feedersList:any;
  extendedFromList:any=[];
  resp: any;
  reportDescriptionFlag=false;
  maxDate = new Date();
  minDate=new Date();
  dateFormat = 'MM-dd-yyyy HH:MM:SS';
  divisionList:any;
  duration:any;
  constructor(
    private formBuilder: FormBuilder,    
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private route: ActivatedRoute,
    private router: Router,
    private sendAndRequestService: SendAndRequestService
  ) {
    // Reactive form errors
    this.cbFailFormErrors = {
      subStation: {}, 
      relayIndication:{},
      fromDateTime: {},
      thruDateTime: {},
      duration: {}, 
      divisionLocal: {},
      internalExternal: {}, 
      remarks:{}
    };
  }

  ngOnInit() {
    //this.findRelayIndicationStatus();
    //this.findNatureOfCloseStatus();
    this.findFeedersList();
    this.id = +this.route.snapshot.params['id'];    
    this.createForm();
    if (!isNaN(this.id)) {
      this.addRcFailFromGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      });
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = 'Edit';
      this.getRcFailDataById(this.id);
    } else {
      this.save = true;
      this.update = false;
      this.title = 'Save';
    }
  }

  timeDuration(){
    console.log("duration")
    var fromDateTime=this.addRcFailFromGroup.value.fromDateTime;
    
    var thruDateTime=this.addRcFailFromGroup.value.thruDateTime;
   
    if(this.addRcFailFromGroup.value.fromDateTime.getTime()!="" && this.addRcFailFromGroup.value.thruDateTime.getTime()!=""){
   var diff=this.addRcFailFromGroup.value.thruDateTime.getTime()-this.addRcFailFromGroup.value.fromDateTime.getTime();
   console.log("diff"+diff)
   var days=Math.floor(diff / (60*60*24*1000));
   console.log("days"+days)
   var hours=Math.floor(diff / (60*60*1000))-(days*24);
   console.log("hours"+hours)
   var minutes=Math.floor(diff /(60*1000)) -((days*24*60) + (hours*60));
   console.log("minutes"+minutes)
   var seconds=Math.floor(diff / 1000) - ((days*24*60*60)+(hours*60*60)+(minutes*60))
   console.log("seconds"+seconds)
   this.duration=String(days)+":"+String(hours)+":" + String(minutes)+":" +String(seconds) ;
   console.log("duration"+this.duration)
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
    this.addRcFailFromGroup
      = this.formBuilder.group({
        id: 0,
        'subStation': [null], 
        'relayIndication':[null],
        'fromDateTime': [null],
        'thruDateTime': [null],
        'duration': [null], 
        'divisionLocal': [null],
        'internalExternal': [null], 
        'remarks': [null, Validators.maxLength(250)]
      });
  }

  onFormValuesChanged() {
    for (const field in this.cbFailFormErrors) {
      if (!this.cbFailFormErrors.hasOwnProperty(field)) {
        continue;
      }
      this.cbFailFormErrors[field] = {};
      const control = this.addRcFailFromGroup.get(field);
      if (control && control.dirty && !control.valid) {
        this.cbFailFormErrors[field] = control.errors;
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
  getRcFailDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.FAILURE_TYPE_BY_ID+id)
      .subscribe((resp) => {
        this.resp = resp;
        console.log(this.resp);
        this.minDate = new Date(this.resp.fromDateTime);
        this.addRcFailFromGroup.patchValue({
          id: this.resp.id,
          subStation:this.resp.subStation,
          relayIndication:this.resp.relayIndication,
          fromDateTime:!!this.resp.fromDateTime ? new Date(this.resp.fromDateTime) : '',
          thruDateTime:!!this.resp.thruDateTime ? new Date(this.resp.thruDateTime) : '',
          duration:this.resp.duration, 
          divisionLocal:this.resp.divisionLocal,
          internalExternal:this.resp.internalExternal,
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
    if (this.addRcFailFromGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    var data = {};
    var message = '';
    var failedMessage = '';
    if (this.save) {
      data = {
        'subStation': this.addRcFailFromGroup.value.subStation , 
        'relayIndication' :this.addRcFailFromGroup.value.relayIndication,
        'fromDateTime': this.addRcFailFromGroup.value.fromDateTime,
        'thruDateTime': this.addRcFailFromGroup.value.thruDateTime,
        'duration': this.addRcFailFromGroup.value.duration, 
        'divisionLocal': this.addRcFailFromGroup.value.divisionLocal,
        'internalExternal': this.addRcFailFromGroup.value.internalExternal, 
        'remarks': this.addRcFailFromGroup.value.remarks,
        "typeOfFailure":Constants.FAILURE_TYPES.RC_FAILURE,
        "createdBy": this.loggedUserData.username,
        "createdOn": new Date()
      }    
      message = 'Saved';
      failedMessage = "Saving";
      this.sendAndRequestService.requestForPOST(Constants.app_urls.FAILURES.FAILURE_TYPE_UPDATE,data, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Rc Fail Data "+message+" Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Rc Fail Data "+failedMessage+" Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Rc Fail Data "+failedMessage+" Failed.");
      })
    }else if(this.update){
      data = {
        "id":this.id,
        'subStation': this.addRcFailFromGroup.value.subStation , 
        'relayIndication':this.addRcFailFromGroup.value.relayIndication,
        'fromDateTime': this.addRcFailFromGroup.value.fromDateTime,
        'thruDateTime': this.addRcFailFromGroup.value.thruDateTime,
        'duration': this.addRcFailFromGroup.value.duration, 
        'divisionLocal': this.addRcFailFromGroup.value.divisionLocal,
        'internalExternal': this.addRcFailFromGroup.value.internalExternal, 
        'remarks': this.addRcFailFromGroup.value.remarks,
        "typeOfFailure":this.resp.typeOfFailure,
        "updatedBy": this.loggedUserData.username,
        "updatedOn": new Date()
      }   
      message = 'Updated';
      failedMessage = "Updating";
      this.sendAndRequestService.requestForPUT(Constants.app_urls.FAILURES.FAILURE_TYPE_UPDATE,data, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Rc Fail Data "+message+" Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Rc Fail Data "+failedMessage+" Failed."); 
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Rc Fail Data "+failedMessage+" Failed.");
      })
    }
    
  }
  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }

}
