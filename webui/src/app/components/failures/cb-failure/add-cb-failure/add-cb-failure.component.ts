import { Component, OnInit } from '@angular/core';
import { Constants } from 'src/app/common/constants';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { DatePipe } from '@angular/common';
import { OWL_DATE_TIME_FORMATS } from 'ng-pick-datetime';
import { MY_CUSTOM_FORMATS } from 'src/app/common/date-filter.pipe';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';

@Component({
  selector: 'app-add-cb-failure',
  templateUrl: './add-cb-failure.component.html',
  styleUrls: ['./add-cb-failure.component.css'],
})
export class AddCbFailureComponent implements OnInit { 
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
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
  currentDate = new Date();
  closeDate=new Date();
  maxDate = new Date();
  toMinDate=new Date();
  dateFormat = 'dd-MM-yyyy hh:mm:ss';  
  failureList:any;
  failurecasList:any;
  difference:any;
  duration:any;
  result:any;
  zoneHierarchy:any = JSON.parse(localStorage.getItem('zoneData'));
  divisionHierarchy:any = JSON.parse(localStorage.getItem('divisionData'));   
  subDivisionHierarchy:any = JSON.parse(localStorage.getItem('subDivData'));   
  facilityHierarchy:any = JSON.parse(localStorage.getItem('depotData'));  


  divisionList:any; 
  facilityList:any;
  enableStation:boolean;
  constructor(
    private formBuilder: FormBuilder,    
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private route: ActivatedRoute,
    private router: Router,
    private sendAndRequestService: SendAndRequestService,
   
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
      zConstant: {},
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
    
   // console.log("facilityData=="+this.depotHierarchy);
    this.findRelayIndicationStatus();
    this.findNatureOfCloseStatus();
   // this.findFacilities();
    this.id = +this.route.snapshot.params['id'];   
  
      if (!isNaN(this.id)) {
      this.updateForm();
      this.findFacilities();
      this.addCbFailFromGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      });
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;       
      this.getCbFailDataById(this.id);
    } else {
      this.findFacilities();
      this.createForm();
      this.title = Constants.EVENTS.ADD;
    }
  
 

  }

  findEquipments()
  {
    let substation= this.addCbFailFromGroup.controls['subStation'].value;
    this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.FAILURE_EQUIPMENT+substation).subscribe((data) => {
      this.failureList = data;
      
      } 
      , error => {});
     
      this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.FAILURE_EQUIPMENT+substation)
      .subscribe((data) => {
        
        this.failurecasList = data;
      //  this.extendedFromList = response;
        this.spinnerService.hide();
      })
  }
  findFeedersList(){
    this.spinnerService.show();
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_CONSUMPTION.FIND_TSS_FEEDER_MASTER )
      .subscribe((response) => {
        
        this.feedersList = response;
      //  this.extendedFromList = response;
        this.spinnerService.hide();
      })
  }

  createForm() {
    this.addCbFailFromGroup
      = this.formBuilder.group({
        id: 0,
        'subStation': [null,Validators.compose([Validators.required])], 
        'equipment': [null,Validators.compose([Validators.required])], 
        'cascadeAssets': [null], 
        'fromDateTime': [null,Validators.compose([Validators.required]),this.duplicateSubStationAndEquipmentAndFromDateTime.bind(this)],
        'thruDateTime': [null],
        'duration': [null], 
        'relayIndication': [null], 
        'natureOfClosure': [null], 
        'rValue': [null],
        'xValue': [null], 
        'zConstant': [null],
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
  updateForm() {
  
    this.addCbFailFromGroup
      = this.formBuilder.group({

        id: 0,
        'subStation': [null,Validators.compose([Validators.required])], 
        'equipment': [null,Validators.compose([Validators.required])], 
        'cascadeAssets': [null], 
        'fromDateTime': [null,Validators.compose([Validators.required]),this.duplicateSubStationAndEquipmentAndFromDateTimeAndId.bind(this)],
        'thruDateTime': [null],
        'duration': [null], 
        'relayIndication': [null], 
        'natureOfClosure': [null], 
        'rValue': [null],
        'xValue': [null], 
        'zConstant': [null],
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
 
 

  getCbFailDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.FAILURE_TYPE_BY_ID+id)
      .subscribe((resp) => {
        this.resp = resp;
        this.toMinDate = new Date(this.resp.fromDateTime);
        this.addCbFailFromGroup.patchValue({
          id: this.resp.id,
          subStation:this.resp.subStation,
          equipment:this.resp.equipment,
          cascadeAssets:this.resp.cascadeAssets.split(","),
          fromDateTime:!!this.resp.fromDateTime ? new Date(this.resp.fromDateTime) : '',
          thruDateTime:!!this.resp.thruDateTime ? new Date(this.resp.thruDateTime) : '',
          duration:this.resp.duration, 
          relayIndication:this.resp.relayIndication,
          natureOfClosure:this.resp.natureOfClosure, 
          rValue:this.resp.rValue,
          xValue:this.resp.xValue,
          zConstant:this.resp.zConstant,
          faultDistance:this.resp.faultDistance,
          actualFaultDistance:this.resp.actualFaultDistance,
          current:this.resp.current,
          voltage:this.resp.voltage,
          phaseAngle:this.resp.phaseAngle,
          trippedIdentifiedFault:this.resp.trippedIdentifiedFault== 'true' ?  true : false,
          divisionLocal:this.resp.divisionLocal == 'true' ? true: false,
          internalExternal:this.resp.internalExternal == 'true' ? true: false,
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
    this.currentDate = new Date($event.value);
  }
  addEventCloseDate($event) {
    this.toMinDate = new Date($event.value);
    this.closeDate=new Date($event.value);
  }



  findFacilities(){
   
    this.facilityList=[]; 
     if(this.loggedUserData.username == 'tpc_admin'){
console.log("facility in tpc===="+this.facilityHierarchy);
    //  this.enableStation=true;
      for (let i = 0; i < this.facilityHierarchy.length; i++) {
        
        if( this.facilityHierarchy[i].depotType == 'TSS'|| this.facilityHierarchy[i].depotType == 'SP'|| this.facilityHierarchy[i].depotType == 'SSP'){
           
           this.facilityList.push(this.facilityHierarchy[i]);
            //this.facilityHierarchy.facilityList;
            
        }
     }     

    } else {
      //this.enableStation=true;
      console.log("facility in else===="+this.facilityHierarchy);
      for (let i = 0; i < this.facilityHierarchy.length; i++) {
        
        if( this.facilityHierarchy[i].depotType == 'TSS'|| this.facilityHierarchy[i].depotType == 'SP'|| this.facilityHierarchy[i].depotType == 'SSP'){
           
           this.facilityList.push(this.facilityHierarchy[i]);
           // this.facilityHierarchy.facilityList;
            
        }
     } 

    }    
}

timeDuration(){
    
  //   var fromDateTime=this.sendAndRequestService.convertIndiaStandardTimeToTimestamp(this.addCbFailFromGroup.value.fromDateTime);
    
  //   var thruDateTime=this.sendAndRequestService.convertIndiaStandardTimeToTimestamp(this.addCbFailFromGroup.value.thruDateTime);
   
   
  //  if(this.addCbFailFromGroup.value.fromDateTime.getTime()!="" && this.addCbFailFromGroup.value.thruDateTime.getTime()!=""){
   
    
  //   let diff=this.addCbFailFromGroup.value.thruDateTime.getTime()-this.addCbFailFromGroup.value.fromDateTime.getTime();
  

  //  let days=Math.floor(diff / (60*60*24*1000));
   
  //  let hours=Math.floor(diff / (60*60*1000))-(days*24);
  //  let hour=hours+(days*24);
  
  //  let minutes=Math.floor(diff /(60*1000)) -((days*24*60) + (hours*60));
   
  //  let seconds=Math.floor(diff / 1000) - ((days*24*60*60)+(hours*60*60)+(minutes*60))
  
  //  this.duration=String(hour)+":" + String(minutes)+":" +String(seconds) ;
   
  //   }

    var ffdate=this.addCbFailFromGroup.value.fromDateTime;
  
    var ftdate=this.addCbFailFromGroup.value.thruDateTime;
  
    if(ffdate!=null && ftdate!=null)
    {
      if(ftdate >= ffdate)   
       {       
  
      this.duration  =this.sendAndRequestService.Duration(ffdate,ftdate)
      }
  
      }else{
        this.duration=""
      }
}

function(){
  var resp2=this.addCbFailFromGroup.value.rValue;
  var R=resp2*resp2;
  var resp1=this.addCbFailFromGroup.value.xValue;
  var X=resp1*resp1; 
  var z=R+X;  
  this.result=String(Math.round(Math.sqrt(z)*100)/100);
 
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
     
      let casc=this.addCbFailFromGroup.value.cascadeAssets;
     
      data = {
        'subStation': this.addCbFailFromGroup.value.subStation , 
        'equipment': this.addCbFailFromGroup.value.equipment , 
        'cascadeAssets':  casc.toString(),
        'fromDateTime': this.addCbFailFromGroup.value.fromDateTime,
        'thruDateTime': this.addCbFailFromGroup.value.thruDateTime,
        'duration': this.addCbFailFromGroup.value.duration, 
        'relayIndication': this.addCbFailFromGroup.value.relayIndication, 
        'natureOfClosure': this.addCbFailFromGroup.value.natureOfClosure, 
        'rValue':this.addCbFailFromGroup.value.rValue,
        'xValue':this.addCbFailFromGroup.value.xValue,
        'zConstant': this.addCbFailFromGroup.value.zConstant,
        'faultDistance': this.addCbFailFromGroup.value.faultDistance,
        'actualFaultDistance': this.addCbFailFromGroup.value.actualFaultDistance,
        'current': this.addCbFailFromGroup.value.current,
        'voltage': this.addCbFailFromGroup.value.voltage,
        'phaseAngle': this.addCbFailFromGroup.value.phaseAngle,
        'trippedIdentifiedFault': this.addCbFailFromGroup.value.trippedIdentifiedFault== true ?  'true' : 'false',
        'divisionLocal': this.addCbFailFromGroup.value.divisionLocal== true ?  'true' : 'false',
        'internalExternal': this.addCbFailFromGroup.value.internalExternal== true ? 'true' : 'false', 
        'remarks': this.addCbFailFromGroup.value.remarks,
        "typeOfFailure":Constants.FAILURE_TYPES.CB_FAILURE,
        "createdDate":this.addCbFailFromGroup.value.fromDateTime,
        "createdBy": this.loggedUserData.username,
        "createdOn": new Date()
      }   
    
      message = 'Saved';
      failedMessage = "Saving";
      this.sendAndRequestService.requestForPOST(Constants.app_urls.FAILURES.FAILURE_TYPE_SAVE,data, false).subscribe(response => {
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
      let casc=this.addCbFailFromGroup.value.cascadeAssets;
      data = {
        "id":this.id,
        'subStation': this.addCbFailFromGroup.value.subStation , 
        'equipment': this.addCbFailFromGroup.value.equipment , 
        'cascadeAssets':  casc.toString(),
        'fromDateTime': this.addCbFailFromGroup.value.fromDateTime,
        'thruDateTime': this.addCbFailFromGroup.value.thruDateTime,
        'duration': this.addCbFailFromGroup.value.duration, 
        'relayIndication': this.addCbFailFromGroup.value.relayIndication, 
        'natureOfClosure': this.addCbFailFromGroup.value.natureOfClosure, 
        'rValue': this.addCbFailFromGroup.value.rValue,
        'xValue': this.addCbFailFromGroup.value.xValue, 
        'zConstant': this.addCbFailFromGroup.value.zConstant,
        'faultDistance': this.addCbFailFromGroup.value.faultDistance,
        'actualFaultDistance': this.addCbFailFromGroup.value.actualFaultDistance,
        'current': this.addCbFailFromGroup.value.current,
        'voltage': this.addCbFailFromGroup.value.voltage,
        'phaseAngle': this.addCbFailFromGroup.value.phaseAngle,
        'trippedIdentifiedFault': this.addCbFailFromGroup.value.trippedIdentifiedFault== true ?  'true' : 'false',
        'divisionLocal': this.addCbFailFromGroup.value.divisionLocal == true ?  'true' : 'false',
        'internalExternal': this.addCbFailFromGroup.value.internalExternal == true ? 'true' : 'false', 
        'remarks': this.addCbFailFromGroup.value.remarks,
        "typeOfFailure":this.resp.typeOfFailure,
        "createdDate": this.addCbFailFromGroup.value.fromDateTime,
        "createdBy": this.loggedUserData.username,
        "lastUpdatedStamp": new Date()
      }   
      message = 'Updated';
      failedMessage = "Updating";
      this.sendAndRequestService.requestForPUT(Constants.app_urls.FAILURES.FAILURE_TYPE_UPDATE,data, false).subscribe(response => {
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

  duplicateSubStationAndEquipmentAndFromDateTime() {
    const q = new Promise((resolve, reject) => {              

      let subStation: string = this.addCbFailFromGroup.controls['subStation'].value;
      let equipment: string = this.addCbFailFromGroup.controls['equipment'].value;

      let fromDateTime = this.sendAndRequestService.convertIndiaStandardTimeToTimestamp(this.addCbFailFromGroup.controls['fromDateTime'].value);
      
      
      this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.EXIST_SUBSTATION_EQUPMENT_FROMDATETIME+subStation+'/'+equipment+'/'+fromDateTime)
      .subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateSubStationAndEquipmentAndFromDateTime': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateSubStationAndEquipmentAndFromDateTime': true }); });
    });
    return q;
  }

  duplicateSubStationAndEquipmentAndFromDateTimeAndId() {
    const q = new Promise((resolve, reject) => {

         let id=this.id;      
      let subStation: string = this.addCbFailFromGroup.controls['subStation'].value;
      let equipment: string = this.addCbFailFromGroup.controls['equipment'].value;
      let fromDateTime = this.sendAndRequestService.convertIndiaStandardTimeToTimestamp(this.addCbFailFromGroup.controls['fromDateTime'].value);   ;
     
      
      this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.EXIST_SUBSTATION_EQUPMENT_FROMDATETIME_ID+id+'/'+subStation+'/'+equipment+'/'+fromDateTime)
      .subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateSubStationAndEquipmentAndFromDateTimeAndId': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateSubStationAndEquipmentAndFromDateTimeAndId': true }); });
    });
    return q;
  }


}
