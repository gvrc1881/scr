import { Component, OnInit } from '@angular/core';
import { Constants } from 'src/app/common/constants';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';


@Component({
  selector: 'app-add-failure-occurrence',
  templateUrl: './add-failure-occurrence-failure.component.html',
  styleUrls: ['./add-failure-occurrence-failure.component.css']
})
export class AddFailureOccurrenceComponent implements OnInit {

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
  addFailureOccurrenceFailFromGroup: FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  stateList = [{ 'id': 1, "value": 'Yes' }, { 'id': 2, "value": 'No' }];
  driveList = [];
  reportedList=[];
  failureOccurrenceFailFormErrors: any;
  feedersList:any;
  extendedFromList:any=[];
  resp: any;
  reportDescriptionFlag=false;
  divisionList:any;
  duration:any;
  currentDate=new Date();
  failToDate=new Date();
  minDate=new Date();
  maxDate = new Date();
  dateFormat = 'dd-MM-yyyy hh:mm:ss';  
  zoneHierarchy:any = JSON.parse(localStorage.getItem('zoneData'));
  divisionHierarchy:any = JSON.parse(localStorage.getItem('divisionData'));   
  subDivisionHierarchy:any = JSON.parse(localStorage.getItem('subDivData'));   
  facilityHierarchy:any = JSON.parse(localStorage.getItem('depotData'));  
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
    this.failureOccurrenceFailFormErrors = {
      occurrence: {}, 
      trainNo: {}, 
      place:{},
      fromDateTime: {},
      thruDateTime: {},
      duration: {}, 
      divisionLocal: {},
      internalExternal: {}, 
      remarks:{}
    };
  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];    
    
    if (!isNaN(this.id)) {
      this.updateForm();
      this.addFailureOccurrenceFailFromGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      });
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;
      this.getFailureOccurrenceFailDataById(this.id);
    } else {
      this.createForm();
      this.save = true;
      this.update = false;
      this.title = Constants.EVENTS.ADD;
    }
  }

  timeDuration(){
    
  //   var fromDateTime=this.addFailureOccurrenceFailFromGroup.value.fromDateTime;
    
  //   var thruDateTime=this.addFailureOccurrenceFailFromGroup.value.thruDateTime;
   
  //   if(this.addFailureOccurrenceFailFromGroup.value.fromDateTime.getTime()!="" && this.addFailureOccurrenceFailFromGroup.value.thruDateTime.getTime()!=""){
  //  var diff=this.addFailureOccurrenceFailFromGroup.value.thruDateTime.getTime()-this.addFailureOccurrenceFailFromGroup.value.fromDateTime.getTime();

  //  let days=Math.floor(diff / (60*60*24*1000));
   
  //  let hours=Math.floor(diff / (60*60*1000))-(days*24);
  //  let hour=hours+(days*24);
  
  //  let minutes=Math.floor(diff /(60*1000)) -((days*24*60) + (hours*60));
   
  //  let seconds=Math.floor(diff / 1000) - ((days*24*60*60)+(hours*60*60)+(minutes*60))
  
  //  this.duration=String(hour)+":" + String(minutes)+":" +String(seconds) ;
  //   }
    var ffdate=this.addFailureOccurrenceFailFromGroup.value.fromDateTime;
  
    var ftdate=this.addFailureOccurrenceFailFromGroup.value.thruDateTime;
  
    if(ffdate!=null && ftdate!=null)
    {
      if(ftdate >= ffdate)    {
       
  
      this.duration  =this.sendAndRequestService.Duration(ffdate,ftdate)
      }
  
      }else{
        this.duration=""
      }
  }

  createForm() {
    this.addFailureOccurrenceFailFromGroup
      = this.formBuilder.group({
        id: 0,
        'occurrence': [null,Validators.compose([Validators.required])], 
        'trainNo':[null],
        'place':[null,Validators.compose([Validators.required])],
        'fromDateTime': [null,Validators.compose([Validators.required]),this.duplicateOccurenceAndPlaceAndFromDateTime.bind(this)],
        'thruDateTime': [null],
        'duration': [null], 
        'divisionLocal': [null],
        'internalExternal': [null], 
        'remarks': [null, Validators.maxLength(250)]
      });
  }
  updateForm() {
    this.addFailureOccurrenceFailFromGroup
      = this.formBuilder.group({
        id: 0,
        'occurrence': [null,Validators.compose([Validators.required])], 
        'trainNo':[null],
        'place':[null,Validators.compose([Validators.required])],
        'fromDateTime': [null,Validators.compose([Validators.required]),this.duplicateOccurenceAndPlaceAndFromDateTimeID.bind(this)],
        'thruDateTime': [null],
        'duration': [null], 
        'divisionLocal': [null],
        'internalExternal': [null], 
        'remarks': [null, Validators.maxLength(250)]
      });
  }

  onFormValuesChanged() {
    for (const field in this.failureOccurrenceFailFormErrors) {
      if (!this.failureOccurrenceFailFormErrors.hasOwnProperty(field)) {
        continue;
      }
      this.failureOccurrenceFailFormErrors[field] = {};
      const control = this.addFailureOccurrenceFailFromGroup.get(field);
      if (control && control.dirty && !control.valid) {
        this.failureOccurrenceFailFormErrors[field] = control.errors;
      }
    }
  }

  findFacilities(){
   
    this.facilityList=[];    

    for (let i = 0; i < this.facilityHierarchy.length; i++) {
        
           if( this.facilityHierarchy[i].depotType == 'TSS'|| this.facilityHierarchy[i].depotType == 'SP'|| this.facilityHierarchy[i].depotType == 'SSP'){
           
              
               this.facilityHierarchy.facilityList;
               
           }
        }
}
  getFailureOccurrenceFailDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.FAILURE_TYPE_BY_ID+id)
      .subscribe((resp) => {
        this.resp = resp;
        console.log(this.resp);
        this.minDate = new Date(this.resp.fromDateTime);
        this.addFailureOccurrenceFailFromGroup.patchValue({
          id: this.resp.id,
          occurrence:this.resp.occurrence,
          trainNo:this.resp.trainNo,
          place:this.resp.place,
          fromDateTime:!!this.resp.fromDateTime ? new Date(this.resp.fromDateTime) : '',
          thruDateTime:!!this.resp.thruDateTime ? new Date(this.resp.thruDateTime) : '',
          duration:this.resp.duration, 
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
  addEvent($event) {
    this.minDate = new Date($event.value);
    this.currentDate = new Date($event.value);
  }
  addEventFailToDate($event) {
   
    this.failToDate=new Date($event.value);
  }
  
  onAddFailureAnalysisFormSubmit() {
    if (this.addFailureOccurrenceFailFromGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    var data = {};
    var message = '';
    var failedMessage = '';
    if (this.save) {
      data = {
        'occurrence': this.addFailureOccurrenceFailFromGroup.value.occurrence ,
        'trainNo':this.addFailureOccurrenceFailFromGroup.value.trainNo,
        'place':this.addFailureOccurrenceFailFromGroup.value.place,  
        'fromDateTime': this.addFailureOccurrenceFailFromGroup.value.fromDateTime,
        'thruDateTime': this.addFailureOccurrenceFailFromGroup.value.thruDateTime,
        'duration': this.addFailureOccurrenceFailFromGroup.value.duration, 
        'divisionLocal': this.addFailureOccurrenceFailFromGroup.value.divisionLocal == true ?  'true' : 'false',
        'internalExternal': this.addFailureOccurrenceFailFromGroup.value.internalExternal == true ? 'true' : 'false', 
        'remarks': this.addFailureOccurrenceFailFromGroup.value.remarks,
        "typeOfFailure":Constants.FAILURE_TYPES.FAILURE_OCCURRENCE,
        "createdDate": this.addFailureOccurrenceFailFromGroup.value.fromDateTime,
        "createdBy": this.loggedUserData.username,
        "createdOn": new Date()
      }    
      message = 'Saved';
      failedMessage = "Saving";
      this.sendAndRequestService.requestForPOST(Constants.app_urls.FAILURES.FAILURE_TYPE_SAVE,data, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("FailureOccurrence Fail Data "+message+" Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("FailureOccurrence Fail Data "+failedMessage+" Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("FailureOccurrence Fail Data "+failedMessage+" Failed.");
      })
    }else if(this.update){
      data = {
        "id":this.id,
        'occurrence': this.addFailureOccurrenceFailFromGroup.value.occurrence ,
        'trainNo':this.addFailureOccurrenceFailFromGroup.value.trainNo,
        'place':this.addFailureOccurrenceFailFromGroup.value.place, 
        'fromDateTime': this.addFailureOccurrenceFailFromGroup.value.fromDateTime,
        'thruDateTime': this.addFailureOccurrenceFailFromGroup.value.thruDateTime,
        'duration': this.addFailureOccurrenceFailFromGroup.value.duration, 
        'divisionLocal': this.addFailureOccurrenceFailFromGroup.value.divisionLocal == true ?  'true' : 'false',
        'internalExternal': this.addFailureOccurrenceFailFromGroup.value.internalExternal == true ? 'true' : 'false', 
        'remarks': this.addFailureOccurrenceFailFromGroup.value.remarks,
        "typeOfFailure":this.resp.typeOfFailure,
        "createdDate": this.addFailureOccurrenceFailFromGroup.value.fromDateTime,
        "updatedBy": this.loggedUserData.username,
        "updatedOn": new Date()
      }   
      message = 'Updated';
      failedMessage = "Updating";
      this.sendAndRequestService.requestForPUT(Constants.app_urls.FAILURES.FAILURE_TYPE_UPDATE,data, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("FailureOccurrence Fail Data "+message+" Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("FailureOccurrence Fail Data "+failedMessage+" Failed."); 
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("FailureOccurrence Fail Data "+failedMessage+" Failed.");
      })
    }
    
  }
  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }

  duplicateOccurenceAndPlaceAndFromDateTime() {
    
              
      let occurrence: string = this.addFailureOccurrenceFailFromGroup.controls['occurrence'].value;
      let place: string = this.addFailureOccurrenceFailFromGroup.controls['place'].value;
      let fromDateTime: string = this.sendAndRequestService.convertIndiaStandardTimeToTimestamp(this.addFailureOccurrenceFailFromGroup.controls['fromDateTime'].value);
     
      const q = new Promise((resolve, reject) => {
      //this.makeService.existsMakeCode(makeCode)
      this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.EXIST_OCCURENCE_PLACE_FROMDATETIME+occurrence+'/'+place+'/'+fromDateTime)
      .subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateOccurenceAndPlaceAndFromDateTime': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateOccurenceAndPlaceAndFromDateTime': true }); });
    });
    return q;
  }

  duplicateOccurenceAndPlaceAndFromDateTimeID() {
    
    let id=this.id;        
    let occurrence: string = this.addFailureOccurrenceFailFromGroup.controls['occurrence'].value;
    let place: string = this.addFailureOccurrenceFailFromGroup.controls['place'].value;
    let fromDateTime: string = this.sendAndRequestService.convertIndiaStandardTimeToTimestamp(this.addFailureOccurrenceFailFromGroup.controls['fromDateTime'].value);
   
    const q = new Promise((resolve, reject) => {
    //this.makeService.existsMakeCode(makeCode)
    this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.EXIST_OCCURENCE_PLACE_FROMDATETIME_ID+id+'/'+occurrence+'/'+place+'/'+fromDateTime)
    .subscribe((duplicate) => {
      if (duplicate) {
        resolve({ 'duplicateOccurenceAndPlaceAndFromDateTimeID': true });
      } else {
        resolve(null);
      }
    }, () => { resolve({ 'duplicateOccurenceAndPlaceAndFromDateTimeID': true }); });
  });
  return q;
}

}
