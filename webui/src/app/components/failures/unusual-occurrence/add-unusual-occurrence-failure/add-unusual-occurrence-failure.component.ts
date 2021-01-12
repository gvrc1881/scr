import { Component, OnInit } from '@angular/core';
import { Constants } from 'src/app/common/constants';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';

@Component({
  selector: 'app-add-unusual-occurrence-failure',
  templateUrl: './add-unusual-occurrence-failure.component.html',
  styleUrls: ['./add-unusual-occurrence-failure.component.css']
})
export class AddUnusualOccurrenceFailureComponent implements OnInit {

  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  facilityHierarchy:any = JSON.parse(localStorage.getItem('depotData')); 
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
  currentDate = new Date();
  resolveDate=new Date(); 
  toMinDate=new Date();
  dateFormat = 'dd-MM-yyyy hh:mm:ss';     
  divisionList:any;
  duration:any;
  facilityList:any;
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
      majorSection:{},
      minorSection:{},
      remarks:{}
    };
  }

  ngOnInit() {
    //this.findRelayIndicationStatus();
  //  this.findNatureOfCloseStatus();
    this.findDepots();
    this.id = +this.route.snapshot.params['id'];      
    if (!isNaN(this.id)) {
      this.updateForm();
      this.addUnusualOccurrenceFromGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      });
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;
      this.getUnusualOccurrenceFailDataById(this.id);
    } else {
      this.createForm();
      this.save = true;
      this.update = false;
      this.title = Constants.EVENTS.ADD;
    }
  }
  timeDuration(){
    
  //   var fromDateTime=this.addUnusualOccurrenceFromGroup.value.fromDateTime;
    
  //   var thruDateTime=this.addUnusualOccurrenceFromGroup.value.thruDateTime;
   
  //   if(this.addUnusualOccurrenceFromGroup.value.fromDateTime.getTime()!="" && this.addUnusualOccurrenceFromGroup.value.thruDateTime.getTime()!=""){
  //  var diff=this.addUnusualOccurrenceFromGroup.value.thruDateTime.getTime()-this.addUnusualOccurrenceFromGroup.value.fromDateTime.getTime();
  //  let days=Math.floor(diff / (60*60*24*1000));
   
  //  let hours=Math.floor(diff / (60*60*1000))-(days*24);
  //  let hour=hours+(days*24);
  
  //  let minutes=Math.floor(diff /(60*1000)) -((days*24*60) + (hours*60));
   
  //  let seconds=Math.floor(diff / 1000) - ((days*24*60*60)+(hours*60*60)+(minutes*60))
  
  //  this.duration=String(hour)+":" + String(minutes)+":" +String(seconds) ;
  //   }

  var ffdate=this.addUnusualOccurrenceFromGroup.value.fromDateTime;
  
  var ftdate=this.addUnusualOccurrenceFromGroup.value.thruDateTime;

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
  // findFeedersList(){
    
  //   this.spinnerService.show();
  //   this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_DEPOTTYPE_FOR_OHE)
   
  //     .subscribe((response) => {       
        
  //       this.facilityList = response;       
     
  //       this.spinnerService.hide();
  //     })
  // }
  findDepots(){

    this.facilityList=[]; 
    for (let i = 0; i < this.facilityHierarchy.length; i++) {
        
      if( this.facilityHierarchy[i].depotType == 'OHE'){
         
         this.facilityList.push(this.facilityHierarchy[i]);
         // this.facilityHierarchy.facilityList;
          
      }
   } 
  }
  createForm() {
    this.addUnusualOccurrenceFromGroup
      = this.formBuilder.group({
        id: 0,
        'subStation': [null,Validators.compose([Validators.required])], 
        'location': [null,Validators.compose([Validators.required])], 
        'causeOfFailure': [null], 
        'fromDateTime': [null,Validators.compose([Validators.required]),this.duplicateSubStationAndLocationAndFromDateTime.bind(this)],
        'thruDateTime': [null],
        'duration': [null], 
        'divisionLocal': [null],
        'internalExternal': [null], 
        'impact': [null],
        'majorSection':[null],
        'minorSection':[null],
        'remarks': [null, Validators.maxLength(250)]
      });
  }
  updateForm() {
    this.addUnusualOccurrenceFromGroup
      = this.formBuilder.group({
        id: 0,
        'subStation': [null,Validators.compose([Validators.required])], 
        'location': [null,Validators.compose([Validators.required])], 
        'causeOfFailure': [null], 
        'fromDateTime': [null,Validators.compose([Validators.required]),this.duplicateSubStationAndLocationAndFromDateTimeID.bind(this)],
        'thruDateTime': [null],
        'duration': [null], 
        'divisionLocal': [null],
        'internalExternal': [null], 
        'impact': [null],
        'majorSection':[null],
        'minorSection':[null],
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
 

  getUnusualOccurrenceFailDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.FAILURE_TYPE_BY_ID+id)
      .subscribe((resp) => {
        this.resp = resp;
        console.log(this.resp);
        this.minDate = new Date(this.resp.fromDateTime);
        this.addUnusualOccurrenceFromGroup.patchValue({
          id: this.resp.id,
          subStation:this.resp.subStation,
          location:this.resp.location,
          causeOfFailure:this.resp.causeOfFailure,
          fromDateTime:!!this.resp.fromDateTime ? new Date(this.resp.fromDateTime) : '',
          thruDateTime:!!this.resp.thruDateTime ? new Date(this.resp.thruDateTime) : '',
          duration:this.resp.duration, 
          divisionLocal:this.resp.divisionLocal  == 'true' ? true: false,
          internalExternal:this.resp.internalExternal == 'true' ? true: false,
          impact:this.resp.impact,
          majorSection:this.resp.majorSection,
          minorSection:this.resp.minorSection,
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


  addEvent($event) {
    this.minDate = new Date($event.value);
    this.currentDate=new Date($event.value);
  }
  addResloveEvent($event) {
    this.toMinDate = new Date($event.value);
    this.resolveDate=new Date($event.value);
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
        'location': this.addUnusualOccurrenceFromGroup.value.location , 
        'causeOfFailure': this.addUnusualOccurrenceFromGroup.value.causeOfFailure, 
        'fromDateTime': this.addUnusualOccurrenceFromGroup.value.fromDateTime,
        'thruDateTime': this.addUnusualOccurrenceFromGroup.value.thruDateTime,
        'duration': this.addUnusualOccurrenceFromGroup.value.duration, 
        'divisionLocal': this.addUnusualOccurrenceFromGroup.value.divisionLocal == true ?  'true' : 'false',
        'internalExternal': this.addUnusualOccurrenceFromGroup.value.internalExternal == true ? 'true' : 'false', 
        'impact':this.addUnusualOccurrenceFromGroup.value.impact,
        'majorSection':this.addUnusualOccurrenceFromGroup.value.majorSection,
        'minorSection':this.addUnusualOccurrenceFromGroup.value.minorSection,
        'remarks': this.addUnusualOccurrenceFromGroup.value.remarks,
        "typeOfFailure":Constants.FAILURE_TYPES.UNUSUAL_OCCURRENCE_FAILURE,
        "createdDate": this.addUnusualOccurrenceFromGroup.value.fromDateTime,
        "createdBy": this.loggedUserData.username,
        "createdOn": new Date()
      }    
      message = 'Saved';
      failedMessage = "Saving";
      this.sendAndRequestService.requestForPOST(Constants.app_urls.FAILURES.FAILURE_TYPE_SAVE ,data, false).subscribe(response => {
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
        'location': this.addUnusualOccurrenceFromGroup.value.location , 
        'causeOfFailure': this.addUnusualOccurrenceFromGroup.value.causeOfFailure, 
        'fromDateTime': this.addUnusualOccurrenceFromGroup.value.fromDateTime,
        'thruDateTime': this.addUnusualOccurrenceFromGroup.value.thruDateTime,
        'duration': this.addUnusualOccurrenceFromGroup.value.duration, 
        'divisionLocal': this.addUnusualOccurrenceFromGroup.value.divisionLocal== true ? 'true' : 'false',
        'internalExternal': this.addUnusualOccurrenceFromGroup.value.internalExternal== true ? 'true' : 'false', 
        'impact':this.addUnusualOccurrenceFromGroup.value.impact,
        'majorSection':this.addUnusualOccurrenceFromGroup.value.majorSection,
        'minorSection':this.addUnusualOccurrenceFromGroup.value.minorSection,
        'remarks': this.addUnusualOccurrenceFromGroup.value.remarks,
        "typeOfFailure":this.resp.typeOfFailure,
        "createdDate": this.addUnusualOccurrenceFromGroup.value.fromDateTime,
        "updatedBy": this.loggedUserData.username,
        "updatedOn": new Date()
      }   
      message = 'Updated';
      failedMessage = "Updating";
      this.sendAndRequestService.requestForPUT(Constants.app_urls.FAILURES.FAILURE_TYPE_UPDATE,data, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("UnusualOccurrence Fail Data "+message+" Successfully");
        this.router.navigate(['../../'], { relativeTo: this.route });
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
    if (this.save) {
      this.router.navigate(['../'], { relativeTo: this.route });
    } else if (this.update) {
      this.router.navigate(['../../'], { relativeTo: this.route });
    }
  }

  duplicateSubStationAndLocationAndFromDateTime() {
    const q = new Promise((resolve, reject) => {
              
      let subStation: string = this.addUnusualOccurrenceFromGroup.controls['subStation'].value;
      //let location: string = this.addUnusualOccurrenceFromGroup.value.location;
      let fromDateTime: string = this.sendAndRequestService.convertIndiaStandardTimeToTimestamp(this.addUnusualOccurrenceFromGroup.controls['fromDateTime'].value);
    
      this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.EXIST_STATION_LOCATION_FROMDATETIME+subStation+'/'+this.addUnusualOccurrenceFromGroup.value.location+'/'+fromDateTime)
      .subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateSubStationAndLocationAndFromDateTime': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateSubStationAndLocationAndFromDateTime': true }); });
    });
    return q;
  }
  duplicateSubStationAndLocationAndFromDateTimeID() { 
    const q = new Promise((resolve, reject) => {

      let id=this.id;        
      let subStation: string = this.addUnusualOccurrenceFromGroup.controls['subStation'].value;
      //let location: string = this.addUnusualOccurrenceFromGroup.value.location;
      let fromDateTime: string =this.sendAndRequestService.convertIndiaStandardTimeToTimestamp( this.addUnusualOccurrenceFromGroup.controls['fromDateTime'].value);
    
      this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.EXIST_STATION_LOCATION_FROMDATETIME_ID+id+'/'+subStation+'/'+this.addUnusualOccurrenceFromGroup.value.location+'/'+fromDateTime)
      .subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateSubStationAndLocationAndFromDateTimeID': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateSubStationAndLocationAndFromDateTimeID': true }); });
    });
    return q;
  }

}
