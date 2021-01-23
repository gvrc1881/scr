import { Component, OnInit } from '@angular/core';
import { Constants } from 'src/app/common/constants';
import { Validators, FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';




@Component({
  selector: 'app-add-grid-failure',
  templateUrl: './add-grid-failure.component.html',
  styleUrls: ['./add-grid-failure.component.css']
})
export class AddGridFailureComponent implements OnInit {
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
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
  loadReliefList:any;
  extendedFromList:any=[];
  resp: any;
  reportDescriptionFlag=false;
  currentDate=new Date();
  toDate=new Date();
  exFromDate=new Date();
  exToDate=new Date();
  maxDate = new Date();
  minDate=new Date();
  toMinDate=new Date();
  fMinDate=new Date();
  dateFormat = 'dd-MM-yyyy hh:mm:ss';
  divisionList:any;
  duration:any;
  dur:any;
  myModel:boolean=true;
  enableStation:boolean;
  enableExtend:boolean;
  zoneHierarchy:any = JSON.parse(sessionStorage.getItem('zoneData'));
  divisionHierarchy:any = JSON.parse(sessionStorage.getItem('divisionData'));   
  subDivisionHierarchy:any = JSON.parse(sessionStorage.getItem('subDivData'));   
  facilityHierarchy:any = JSON.parse(sessionStorage.getItem('depotData')); 
  //depotHierarchy:any = JSON.parse(sessionStorage.getItem('facilityData')); 

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
    this.gridFailFormErrors = {
      feedOff: {},
      fromDateTime:{},
      ftdate: {},
      fduration: {},
      extendedFrom: {},
      efdate: {},
      etdate: {},
      eduration: {},
      maxDemand: {},
      dl: {},
      ie: {},
      loadReliefBreakDown:{},
      remarks: {}
    };
  }

  ngOnInit() {   
    this.findLoadReliefBreakDown();
    this.id = +this.route.snapshot.params['id'];    
    
    if (!isNaN(this.id)) {
      this.updateForm();
      this.findFacilities();
      this.addGridFailFromGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      });
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;
      this.getGridFailDataById(this.id);
    } else {
      this.createForm();
      this.findFacilities();
      this.save = true;
      this.update = false;
      this.title = Constants.EVENTS.ADD;
    }

   

  
  }

 
findFacilities(){
   
  this.facilityList=[];   
   if(this.loggedUserData.username === 'tpc_admin'){

    this.enableStation=true;
    this.enableExtend=true;
    for (let i = 0; i < this.facilityHierarchy.length; i++) {
        
      if( this.facilityHierarchy[i].depotType == 'TSS'){
      
       this.facilityList.push(this.facilityHierarchy[i]);
          //this.facilityHierarchy.facilityList;
          
      }
   }     
 
  } else {
     this.enableStation=true;
     this.enableExtend=true;

    for (let i = 0; i < this.facilityHierarchy.length; i++) {
      
      if( this.facilityHierarchy[i].depotType == 'TSS'){
         
         this.facilityList.push(this.facilityHierarchy[i]);
          //this.facilityHierarchy.facilityList;
          
      }
   } 

  }    
}


  createForm() {
    this.addGridFailFromGroup
      = this.formBuilder.group({
        id: 0,
        'feedOff': [null,Validators.compose([Validators.required])],
        'fromDateTime':[null,Validators.compose([Validators.required]),this.duplicateFeedOfAndFromDateTime.bind(this)],
        'ftdate': [null],
        'fduration': [null],
        'extendedFrom': [null],
        'efdate': [null],
        'etdate': [null],
        'eduration': [null],
        'maxDemand': [null],
        'dl': [null],
        'ie': [null],
        'loadReliefBreakDown':[null,Validators.compose([Validators.required])],
        'remarks': [null, Validators.maxLength(250)]   ,
          
      });
     
  }
  updateForm() {
   
    this.addGridFailFromGroup
      = this.formBuilder.group({
        id: 0,
        'feedOff': [null,Validators.compose([Validators.required])],
        'fromDateTime':[null,Validators.compose([Validators.required]),this.duplicateFeedOfAndFromDateTimeAndID.bind(this)],
        'ftdate': [null],
        'fduration': [null],
        'extendedFrom': [null],
        'efdate': [null],
        'etdate': [null],
        'eduration': [null],
        'maxDemand': [null],
        'dl': [null],
        'ie': [null],
        'loadReliefBreakDown':[null,Validators.compose([Validators.required])],
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
 
timeDuration(){ 
  
  var ffdate=this.addGridFailFromGroup.value.fromDateTime;
  
  var ftdate=this.addGridFailFromGroup.value.ftdate;

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

   
 
//   if(this.addGridFailFromGroup.value.fromDateTime.getTime()!="" && this.addGridFailFromGroup.value.ftdate.getTime()!=""){
//  var diff=this.addGridFailFromGroup.value.ftdate.getTime()-this.addGridFailFromGroup.value.fromDateTime.getTime();
//  let days=Math.floor(diff / (60*60*24*1000));
   
//  let hours=Math.floor(diff / (60*60*1000))-(days*24);
//  let hour=hours+(days*24);

//  let minutes=Math.floor(diff /(60*1000)) -((days*24*60) + (hours*60));
 
//  let seconds=Math.floor(diff / 1000) - ((days*24*60*60)+(hours*60*60)+(minutes*60))

//  this.duration=String(hour)+":" + String(minutes)+":" +String(seconds) ;

//   }
//this. duration  =this.sendAndRequestService.Duration(ffdate,ftdate)
 
  
//}

timDuration(){

  var efdate=this.addGridFailFromGroup.value.efdate;
  
  var etdate=this.addGridFailFromGroup.value.etdate;

  if(efdate!=null && etdate!=null)
  {
    if(etdate >= efdate)
    {   

    this.dur =this.sendAndRequestService.Duration(efdate,etdate)
    }

    }else{
      this.dur=""
    }
  

 
//   if(this.addGridFailFromGroup.value.efdate.getTime()!="" && this.addGridFailFromGroup.value.etdate.getTime()!=""){
//  var diff=this.addGridFailFromGroup.value.etdate.getTime()-this.addGridFailFromGroup.value.efdate.getTime();
 
 
//  let days=Math.floor(diff / (60*60*24*1000));
   
//  let hours=Math.floor(diff / (60*60*1000))-(days*24);
//  let hour=hours+(days*24);

//  let minutes=Math.floor(diff /(60*1000)) -((days*24*60) + (hours*60));
 
//  let seconds=Math.floor(diff / 1000) - ((days*24*60*60)+(hours*60*60)+(minutes*60))

//  this.dur=String(hour)+":" + String(minutes)+":" +String(seconds) ;

//   }
}
 
  updateFeedOff($event){
    if ($event.value) {
      console.log($event.value)
      this.extendedFromList = [];
      //this.reportDescriptionFlag = $event.value == Constants.YES ? true : false;
      this.facilityList.map(element => {
        if(element.facilityId != $event.value){
          this.extendedFromList.push(element);
        }
      });
    }
  }
  findLoadReliefBreakDown(){
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_STATUS_ITEM + Constants.STATUS_ITEMS.GRID_FAILURE)
    .subscribe((resp) => {
      this.loadReliefList = resp;
    });
  }
  getGridFailDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.FAILURE_TYPE_BY_ID+id)
      .subscribe((resp) => {
        this.resp = resp;
        console.log(this.resp);

        this.minDate=new Date(this.resp.fromDateTime),
        this.toMinDate=new Date(this.resp.efdate),
        this.fMinDate=new Date(this.resp.ftdate),
        this.addGridFailFromGroup.patchValue({
          id: this.resp.id,
          feedOff: this.resp.feedOf,
          fromDateTime:!!this.resp.fromDateTime ? new Date(this.resp.fromDateTime) : '',
          ftdate:!!this.resp.thruDateTime ? new Date(this.resp.thruDateTime) : '',
          fduration: this.resp.duration ,
          extendedFrom: this.resp.extendedOf,
          efdate:!!this.resp.feedExtendedFromDateTime ? new Date(this.resp.feedExtendedFromDateTime) : '',
          etdate:!!this.resp.feedExtendedThruDateTime ? new Date(this.resp.feedExtendedThruDateTime) : '',
          
          eduration: this.resp.feedExtendedDuration,
          maxDemand: this.resp.maxDemand,
          dl: this.resp.divisionLocal =='true' ? true: false,
          ie: this.resp.internalExternal == 'true' ? true : false,
          loadReliefBreakDown:this.resp.loadReliefBreakDown,
          remarks: this.resp.remarks
        });
        this.facilityList.map(element => {
          if(element.id != this.resp.id){
            this.extendedFromList.push(element);
          }
        });
        this.spinnerService.hide();

      })
  }
  addEvent($event) {
    this.minDate  = new Date($event.value);
   // this.currentDate = new Date($event.value);
    this.fMinDate  = new Date($event.value);
  }
  addEventTargetDate($event) {
    //this.fMinDate  = new Date($event.value);

    this.toMinDate  = new Date($event.value);
  
   
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
        "fromDateTime":this.addGridFailFromGroup.value.fromDateTime,
        "thruDateTime": this.addGridFailFromGroup.value.ftdate,
        "duration": this.addGridFailFromGroup.value.fduration,
        "extendedOf": this.addGridFailFromGroup.value.extendedFrom,
        "feedExtendedFromDateTime": this.addGridFailFromGroup.value.efdate,
        "feedExtendedThruDateTime": this.addGridFailFromGroup.value.etdate,
        "feedExtendedDuration": this.addGridFailFromGroup.value.eduration,
        "maxDemand": this.addGridFailFromGroup.value.maxDemand,
        "divisionLocal": this.addGridFailFromGroup.value.dl== true ?  'true' : 'false',
        "internalExternal": this.addGridFailFromGroup.value.ie == true ? 'true' : 'false',
        "loadReliefBreakDown":this.addGridFailFromGroup.value.loadReliefBreakDown,
        "remarks": this.addGridFailFromGroup.value.remarks,
        "typeOfFailure":Constants.FAILURE_TYPES.GRID_FAILURE,
        "createdDate": this.addGridFailFromGroup.value.fromDateTime,
        "createdBy": this.loggedUserData.username,
        "createdOn": new Date()
      }    
      message = 'Saved';
      failedMessage = "Saving";
      this.sendAndRequestService.requestForPOST(Constants.app_urls.FAILURES.FAILURE_TYPE_SAVE,data, false).subscribe(response => {
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
        "fromDateTime":this.addGridFailFromGroup.value.fromDateTime,
        "thruDateTime": this.addGridFailFromGroup.value.ftdate,
        "duration": this.addGridFailFromGroup.value.fduration,
        "extendedOf": this.addGridFailFromGroup.value.extendedFrom,
        "feedExtendedFromDateTime": this.addGridFailFromGroup.value.efdate,
        "feedExtendedThruDateTime": this.addGridFailFromGroup.value.etdate,
        "feedExtendedDuration": this.addGridFailFromGroup.value.eduration,
        "maxDemand": this.addGridFailFromGroup.value.maxDemand,
        "divisionLocal": this.addGridFailFromGroup.value.dl== true ? 'true' : 'false',
        "internalExternal": this.addGridFailFromGroup.value.ie== true ? 'true' : 'false',
        "loadReliefBreakDown":this.addGridFailFromGroup.value.loadReliefBreakDown,
        "remarks": this.addGridFailFromGroup.value.remarks,
        "typeOfFailure":this.resp.typeOfFailure,
        "createdDate": this.addGridFailFromGroup.value.fromDateTime,
        "updatedBy": this.loggedUserData.username,
        "updatedOn": new Date()
      }  
  
      message = 'Updated';
      failedMessage = "Updating";
      this.sendAndRequestService.requestForPUT(Constants.app_urls.FAILURES.FAILURE_TYPE_UPDATE,data, false).subscribe(response => {
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

  duplicateFeedOfAndFromDateTime() {   

              const q = new Promise((resolve, reject) => {
        let feedOf: string = this.addGridFailFromGroup.value.feedOff;
        let fromDateTime: string = this.sendAndRequestService.convertIndiaStandardTimeToTimestamp(this.addGridFailFromGroup.controls['fromDateTime'].value);   
      this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.EXIST_FEEDOF_FROMDATETIME+feedOf+'/'+fromDateTime+'/'+Constants.FAILURE_TYPES.GRID_FAILURE)
      .subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateFeedOfAndFromDateTime': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateFeedOfAndFromDateTime': true }); });
    });
    return q;
  }

  duplicateFeedOfAndFromDateTimeAndID() {
    const q = new Promise((resolve, reject) => {
              
      let id=this.id;
      let feedOf: string = this.addGridFailFromGroup.controls['feedOff'].value;
      let fromDateTime: string = this.sendAndRequestService.convertIndiaStandardTimeToTimestamp(this.addGridFailFromGroup.controls['fromDateTime'].value);
  
      this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.EXIST_FEEDOF_FROMDATETIME_ID+id+'/'+feedOf+'/'+fromDateTime+'/'+Constants.FAILURE_TYPES.GRID_FAILURE)
      .subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateFeedOfAndFromDateTimeAndID': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateFeedOfAndFromDateTimeAndID': true }); });
    });
    return q;
  }
  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  } 


}
