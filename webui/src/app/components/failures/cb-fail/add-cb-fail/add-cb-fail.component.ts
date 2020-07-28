import { Component, OnInit } from '@angular/core';
import { Constants } from 'src/app/common/constants';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';

@Component({
  selector: 'app-add-cb-fail',
  templateUrl: './add-cb-fail.component.html',
  styleUrls: ['./add-cb-fail.component.css']
})
export class AddCbFailComponent implements OnInit {

  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  title:string;
  relayIndicationList = [];
  natureOfCloseList = [];
  addCbFailFromGroup: FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  stateList = [{ 'id': 1, "value": 'Yes' }, { 'id': 2, "value": 'No' }];
  driveList = [];
  reportedList=[];
  cbFailFormErrors: any;
  feedersList:any;
  extendedFromList:any=[];
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
    this.cbFailFormErrors = {
      subStation: {}, 
      equipment: {}, 
      cascadeAssets: {}, 
      fromDateTime: {},
      thruDateTime: {},
      duration: {}, 
      relayIndication: {}, 
      natureOfClosure: {}, 
      rValue: {},
      xValue: {}, 
      zValue: {},
      faultDistance: {},
      actualFaultDistance: {},
      current: {},
      voltage: {},
      phaseAngle: {},
      trippedIdentifiedFault: {},
      divisionLocal: {},
      internalExternal: {}, 
      remarks:{}
    };
  }

  ngOnInit() {
    this.findRelayIndicationStatus();
    this.findNatureOfCloseStatus();
    this.findFeedersList();
    this.id = +this.route.snapshot.params['id'];    
    this.createForm();
    if (!isNaN(this.id)) {
      this.addCbFailFromGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      });
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = 'Edit';
      this.getCbFailDataById(this.id);
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
    this.addCbFailFromGroup
      = this.formBuilder.group({
        id: 0,
        'subStation': [null], 
        'equipment': [null], 
        'cascadeAssets': [null], 
        'fromDateTime': [null],
        'thruDateTime': [null],
        'duration': [null], 
        'relayIndication': [null], 
        'natureOfClosure': [null], 
        'rValue': [null],
        'xValue': [null], 
        'zValue': [null],
        'faultDistance': [null],
        'actualFaultDistance': [null],
        'current': [null],
        'voltage': [null],
        'phaseAngle': [null],
        'trippedIdentifiedFault': [null],
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
      const control = this.addCbFailFromGroup.get(field);
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
  getCbFailDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.FAILURE_TYPE_BY_ID+id)
      .subscribe((resp) => {
        this.resp = resp;
        console.log(this.resp);
        this.addCbFailFromGroup.patchValue({
          id: this.resp.id,
          subStation:this.resp.subStation,
          equipment:this.resp.equipment,
          cascadeAssets:this.resp.cascadeAssets,
          fromDateTime:new Date(this.resp.fromDateTime),
          thruDateTime:new Date(this.resp.thruDateTime),
          duration:this.resp.duration, 
          relayIndication:this.resp.relayIndication,
          natureOfClosure:this.resp.natureOfClosure, 
          rValue:this.resp.rValue,
          xValue:this.resp.xValue,
          zValue:this.resp.xValue,
          faultDistance:this.resp.faultDistance,
          actualFaultDistance:this.resp.actualFaultDistance,
          current:this.resp.current,
          voltage:this.resp.voltage,
          phaseAngle:this.resp.phaseAngle,
          trippedIdentifiedFault:this.resp.trippedIdentifiedFault,
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
    this.toMinDate = new Date($event.value);
  }
  addEventTargetDate($event) {
    this.completeMinDate = new Date($event.value);
  }
  onAddFailureAnalysisFormSubmit() {
    if (this.addCbFailFromGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    var data = {};
    var message = '';
    var failedMessage = '';
    if (this.save) {
      data = {
        'subStation': this.addCbFailFromGroup.value.subStation , 
        'equipment': this.addCbFailFromGroup.value.equipment , 
        'cascadeAssets': this.addCbFailFromGroup.value.cascadeAssets, 
        'fromDateTime': this.addCbFailFromGroup.value.fromDateTime,
        'thruDateTime': this.addCbFailFromGroup.value.thruDateTime,
        'duration': this.addCbFailFromGroup.value.duration, 
        'relayIndication': this.addCbFailFromGroup.value.relayIndication, 
        'natureOfClosure': this.addCbFailFromGroup.value.natureOfClosure, 
        'rValue': this.addCbFailFromGroup.value.rValue,
        'xValue': this.addCbFailFromGroup.value.xValue, 
        'zValue': this.addCbFailFromGroup.value.zValue,
        'faultDistance': this.addCbFailFromGroup.value.faultDistance,
        'actualFaultDistance': this.addCbFailFromGroup.value.actualFaultDistance,
        'current': this.addCbFailFromGroup.value.current,
        'voltage': this.addCbFailFromGroup.value.voltage,
        'phaseAngle': this.addCbFailFromGroup.value.phaseAngle,
        'trippedIdentifiedFault': this.addCbFailFromGroup.value.trippedIdentifiedFault,
        'divisionLocal': this.addCbFailFromGroup.value.divisionLocal,
        'internalExternal': this.addCbFailFromGroup.value.internalExternal, 
        'remarks': this.addCbFailFromGroup.value.remarks,
        "typeOfFailure":Constants.FAILURE_TYPES.CB_FAILURE,
        "createdBy": this.loggedUserData.username,
        "createdOn": new Date()
      }    
      message = 'Saved';
      failedMessage = "Saving";
      this.sendAndRequestService.requestForPOST(Constants.app_urls.FAILURES.FAILURE_TYPE_UPDATE,data, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Cb Fail Data "+message+" Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Cb Fail Data "+failedMessage+" Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Cb Fail Data "+failedMessage+" Failed.");
      })
    }else if(this.update){
      data = {
        "id":this.id,
        'subStation': this.addCbFailFromGroup.value.subStation , 
        'equipment': this.addCbFailFromGroup.value.equipment , 
        'cascadeAssets': this.addCbFailFromGroup.value.cascadeAssets, 
        'fromDateTime': this.addCbFailFromGroup.value.fromDateTime,
        'thruDateTime': this.addCbFailFromGroup.value.thruDateTime,
        'duration': this.addCbFailFromGroup.value.duration, 
        'relayIndication': this.addCbFailFromGroup.value.relayIndication, 
        'natureOfClosure': this.addCbFailFromGroup.value.natureOfClosure, 
        'rValue': this.addCbFailFromGroup.value.rValue,
        'xValue': this.addCbFailFromGroup.value.xValue, 
        'zValue': this.addCbFailFromGroup.value.zValue,
        'faultDistance': this.addCbFailFromGroup.value.faultDistance,
        'actualFaultDistance': this.addCbFailFromGroup.value.actualFaultDistance,
        'current': this.addCbFailFromGroup.value.current,
        'voltage': this.addCbFailFromGroup.value.voltage,
        'phaseAngle': this.addCbFailFromGroup.value.phaseAngle,
        'trippedIdentifiedFault': this.addCbFailFromGroup.value.trippedIdentifiedFault,
        'divisionLocal': this.addCbFailFromGroup.value.divisionLocal,
        'internalExternal': this.addCbFailFromGroup.value.internalExternal, 
        'remarks': this.addCbFailFromGroup.value.remarks,
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
        this.commonService.showAlertMessage("Cb Fail Data "+message+" Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Cb Fail Data "+failedMessage+" Failed."); 
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Cb Fail Data "+failedMessage+" Failed.");
      })
    }
    
  }
  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }


}
