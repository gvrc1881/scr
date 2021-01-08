import { Component, OnInit,Inject } from '@angular/core';
import { Constants } from 'src/app/common/constants';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog, DateAdapter, MAT_DATE_FORMATS,MAT_DIALOG_DATA } from '@angular/material';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-add-actions',
  templateUrl: './add-actions.component.html',
  styleUrls: ['./add-actions.component.css']
})
export class AddActionsComponent implements OnInit {

  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  actionsPagination = Constants.PAGINATION_NUMBERS;
  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  title:string = Constants.EVENTS.ADD;
  relayIndicationList = [];
  natureOfCloseList = [];
  addActionsFailFromGroup: FormGroup;
  currentDate = new Date();
  resolveDate=new Date(); 
  pattern = "[a-zA-Z][a-zA-Z ]*";
  //actionsList = [{ 'id': 1, "value": 'Event' }, { 'id': 2, "value": 'Repurcussion' },
               // {'id': 3, "value": 'Work Done' }, { 'id': 4, "value": 'Damage' },
               // { 'id': 5, "value": 'Availability' }];
               actionsList=[];
  failureOccurrenceFailFormErrors: any;
  feedersList:any;
  extendedFromList:any=[];
  resp: any;
  reportDescriptionFlag=false;
  toMinDate=new Date();
  completeMinDate=new Date();
  fromTime:boolean=false;
  thruTime:boolean=false;
  location:boolean=false;
  by:boolean=false;
  remarks:boolean=false;
  specialRemarks:boolean=false;
  rootCause:boolean=false;
  trainNo:boolean=false;
  maxDate = new Date();
  minDate=new Date();
  subStation:any;
  occurenceId:any;
  occurenceLocation:any;
  UnusualOccurrenceFailList:any;
  ActionsFailListActions:any;
  dateFormat = 'dd-MM-yyyy hh:mm:ss';
  displayedColumnsActions = ['sno', 'failureActivity', 'fromTime', 'thruTime','by','specialRemarks',
    'remarks','location','trainNo'];
  dataSourceActions: MatTableDataSource<any>;

  constructor(
    private formBuilder: FormBuilder,    
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private route: ActivatedRoute,
    private router: Router,
    private sendAndRequestService: SendAndRequestService,
    private datePipe: DatePipe,
   // @Inject(MAT_DIALOG_DATA) public data:any,
  ) {
    // Reactive form errors
    this.failureOccurrenceFailFormErrors = {
      failureSeqId:{},
      failureActivity: {}, 
      fromTime: {},
      thruTime: {},
      by:{},
      specialRemarks:{},
      trainNo: {}, 
      location:{}, 
      remarks:{}
    };
  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];    
    this.occurenceId = +this.route.snapshot.params['occurenceId']; 
    this.createForm();
    this.findActions();
    this.getActionsFailureData();
    this.fromTime=true;
    this.thruTime=true;
    this.location=false;
    this.by=false;
    this.remarks = false;
    this.specialRemarks=true;
    this.rootCause=false;
    this.trainNo=false;
    
     this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.FAILURE_TYPE_BY_ID+this.occurenceId).subscribe((data) => {
       this.UnusualOccurrenceFailList = data;   
       console.log("occurence"+this.UnusualOccurrenceFailList) 
          this.occurenceLocation = this.UnusualOccurrenceFailList.location
         },error => {} );

    if (!isNaN(this.id)) {
      this.addActionsFailFromGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      });
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;
      this.getActionsFailDataById(this.id);
    } else {
      this.save = true;
      this.update = false;
      this.title = Constants.EVENTS.ADD;
    }
  }


  createForm() {
    this.addActionsFailFromGroup
      = this.formBuilder.group({
        id: 0,
        'failureActivity': [null], 
        'fromTime': [null],
        'thruTime': [null],
        'by':[null],
        'specialRemarks':[null],
        'trainNo':[null],
        'location':[null], 
        'remarks': [null, Validators.maxLength(250)]
      });
  }
  updateActions(from, thru, location, by, remarks, spr,rt, tn){
    this.fromTime=from;
    this.thruTime=thru;
    this.location=location;
    this.by=by;
    this.remarks = remarks;
    this.specialRemarks=spr;
    this.rootCause=rt;
    this.trainNo=tn;
  }
  
  updateFeedOff($event){
    if ($event.value) {
      console.log($event.value);
      if($event.value === 'EVENT'){
        this.updateActions(true, true, false, false, false,true, false, false);
      }else if($event.value === 'REPURCUSSION'){
        this.updateActions(true, true, false, false, false,true, false, true);
      }else if($event.value === 'WORK DONE'){
        this.updateActions(true, true, false, false, false,true, false, false);
      }else if($event.value === 'DAMAGE'){
        this.updateActions(false, false, false, false, false,true, false, false);
      }else if($event.value === 'TRIPPING DETAILS'){
        this.updateActions(false, false, false, false, false,true, false, false);
      }else if($event.value === 'SEGRGATION IF ANY'){
        this.updateActions(false, false, false, false, false,true, false, false);
      } else if($event.value === 'FIR DETAILS'){
        this.updateActions(false, false, false, false, false,true, false, false);
      } else if($event.value === 'TOWER WAGON MOVEMENT'){
        this.updateActions(false, false, false, false, false,true, false, false);
      } else if($event.value === 'ROAD VEHICLE MOVEMENT'){
        this.updateActions(false, false, false, false, false,true, false, false);
      } else if($event.value === 'OFFICERS AT SITE '){
        this.updateActions(false, false, false, false, false,true, false, false);
      } else if($event.value === 'OFFICERS AT CONTROL ROOM'){
        this.updateActions(false, false, false, false, false,true, false, false);
      } else if($event.value === 'SUPERVISORS AT SITE '){
        this.updateActions(false, false, false, false, false,true, false, false);
      } else if($event.value === 'SUPERVISORS AT CONTROL ROOM'){
        this.updateActions(false, false, false, false, false,true, false, false);
      } else if($event.value === 'BLOCK DETAILS'){
        this.updateActions(false, false, false, false, false,true, false, false);
      } else if($event.value === 'LAST MAINTENANCE DETAILS AND FOOT PATROLLING'){
        this.updateActions(false, false, false, false, false,true, false, false);
      } else if($event.value === 'FAILED EQUIPMENT'){
        this.updateActions(false, false, false, false, false,true, false, false);
      } else if($event.value === 'INVESTIGATION'){
        this.updateActions(false, false, false, false, false,true, false, false);
      }
    }
  }

  onFormValuesChanged() {
    for (const field in this.failureOccurrenceFailFormErrors) {
      if (!this.failureOccurrenceFailFormErrors.hasOwnProperty(field)) {
        continue;
      }
      this.failureOccurrenceFailFormErrors[field] = {};
      const control = this.addActionsFailFromGroup.get(field);
      if (control && control.dirty && !control.valid) {
        this.failureOccurrenceFailFormErrors[field] = control.errors;
      }
    }
  }
  getActionsFailDataById(id) {
 
    this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.EDIT_ACTIONS+id)
      .subscribe((resp) => {
        this.resp = resp;
        console.log(this.resp);
        this.minDate=new Date(this.resp.fromTime),
        this.addActionsFailFromGroup.patchValue({
          id: this.resp.id,
          failureActivity:this.resp.failureActivity,
          failureSeqId:this.resp.failureSeqId,
          by:this.resp.by,
          specialRemarks:this.resp.specialRemarks,
          trainNo:this.resp.trainNo,
          location:this.resp.location,
          fromTime:!!this.resp.fromTime ? new Date(this.resp.fromTime) : '',
          thruTime:!!this.resp.thruTime ? new Date(this.resp.thruTime) : '',
          remarks: this.resp.remarks
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
  findActions(){
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_STATUS_ITEM +Constants.STATUS_ITEMS.ACTION)
    .subscribe((resp) => {
      
      this.actionsList = resp;
    });
  }



  addEvent($event) {
    this.minDate = new Date($event.value);
  
  }
  getActionsFailureData() {
    const ActionsFail: any[] = [];
  
    this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.GET_ACTIONS_ID+this.occurenceId).subscribe((data) => {
      this.ActionsFailListActions = data;   
      for (let i = 0; i < this.ActionsFailListActions.length; i++) {
        this.ActionsFailListActions[i].sno = i + 1;
        this.ActionsFailListActions[i].fromTime = this.datePipe.transform(this.ActionsFailListActions[i].fromTime, 'dd-MM-yyyy HH:mm:ss');
        this.ActionsFailListActions[i].thruTime = this.datePipe.transform(this.ActionsFailListActions[i].thruTime, 'dd-MM-yyyy HH:mm:ss');
        
        ActionsFail.push(this.ActionsFailListActions[i]);    
      }
  
      this.dataSourceActions = new MatTableDataSource(ActionsFail);
      //this.dataSourceActions.paginator = this.paginatorActions;
      //this.dataSourceActions.sort = this.sortActions;
     
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }

  onAddFailureAnalysisFormSubmit() {
    if (this.addActionsFailFromGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    var data = {};
    var message = '';
    var failedMessage = '';
    if (this.save) {
      data = {
        'failureActivity': this.addActionsFailFromGroup.value.failureActivity ,
        'failureSeqId':this.occurenceId,
        'by':this.addActionsFailFromGroup.value.by,
        'specialRemarks':this.addActionsFailFromGroup.value.specialRemarks,
        'trainNo':this.addActionsFailFromGroup.value.trainNo,
        'location':this.addActionsFailFromGroup.value.location,  
        'fromTime': this.addActionsFailFromGroup.value.fromTime,
        'thruTime': this.addActionsFailFromGroup.value.thruTime,
        'remarks': this.addActionsFailFromGroup.value.remarks,
        "createdBy": this.loggedUserData.username,
        "createdOn": new Date()
      }    
      message = 'Saved';
      failedMessage = "Saving";
      this.sendAndRequestService.requestForPOST(Constants.app_urls.FAILURES.SAVE_ACTIONS,data, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Actions Fail Data "+message+" Successfully");
        this.router.navigate(['../../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Actions Fail Data "+failedMessage+" Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Actions Fail Data "+failedMessage+" Failed.");
      })
    }else if(this.update){
      data = {
        "id":this.id,
        'failureActivity': this.addActionsFailFromGroup.value.failureActivity ,
        'failureSeqId':this.occurenceId,
        'by':this.addActionsFailFromGroup.value.by,
        'specialRemarks':this.addActionsFailFromGroup.value.specialRemarks,
        'trainNo':this.addActionsFailFromGroup.value.trainNo,
        'location':this.addActionsFailFromGroup.value.location,  
        'fromTime': this.addActionsFailFromGroup.value.fromTime,
        'thruTime': this.addActionsFailFromGroup.value.thruTime,
        'remarks': this.addActionsFailFromGroup.value.remarks,
        "updatedBy": this.loggedUserData.username,
        "updatedOn": new Date()
      }   
      message = 'Updated';
      failedMessage = "Updating";
      this.sendAndRequestService.requestForPUT(Constants.app_urls.FAILURES.UPDATE_ACTIONS,data, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Actions Fail Data "+message+" Successfully");
        this.router.navigate(['../../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Actions Fail Data "+failedMessage+" Failed."); 
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Actions Fail Data "+failedMessage+" Failed.");
      })
    }
    
  }
  onGoBack() {
    if (this.save) {
      this.router.navigate(['../../'], { relativeTo: this.route });
    } else if (this.update) {
      this.router.navigate(['../../'], { relativeTo: this.route });
    }
  }


}
