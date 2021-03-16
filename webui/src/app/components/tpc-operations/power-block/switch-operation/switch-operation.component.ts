import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FacilityModel } from 'src/app/models/facility.model';
import { PbSwitchControl, SwitchMaintenenceHistory , PowerBlockModel } from 'src/app/models/tpc-operations.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { DatePipe } from '@angular/common';
import { OWL_DATE_TIME_FORMATS } from 'ng-pick-datetime';
import { MY_CUSTOM_FORMATS } from 'src/app/common/date-filter.pipe';


@Component({
  selector: 'app-switch-operation',
  templateUrl: './switch-operation.component.html',
  styleUrls: []
})
export class SwitchOperationComponent implements OnInit {
  
  pagination = Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
	pbId: number = 0;
  resp: any;
	powerBlockData: any;
	pbSwitchControl: any;
    dataSource: MatTableDataSource<SwitchMaintenenceHistory>;
    ioOpenedDateTime: any;
    fieldNoIoOpen: any;
    fieldNoIoOpenDone: any;
    ioOpenedDateTimeDone: any;
    ioClosedDateTime: any;
    ioClosedDateTimeDone: any;
    fieldNoIoClose: any;
    fieldNoIoCloseDone: any;
    ioOpenedBy: any;
    ioClosedBy: any;
    tpcNoIoClose: any;
    tpcNoIoCloseDone: any;
    tpcNoIoOpen: any;
    tpcNoIoOpenDone: any;
    displayedColumns = ['switchId','openDate','tpcNoIoOpen','fieldNumOpen','openDoneDate','tpcNoIoOpenDone','fieldNumOpenDone','openBy','closeDate','tpcNoIoClose','fieldNumClose','closeDoneDate','tpcNoIoCloseDone','fieldNumCloseDone','closeBy','actions'];
    randomNumber: any;
    currentDate: any ;
    depot: any;
    section: any;
    requestedBy: any;
    facilityData: any;
    sectionCode: any;
    orginalSMHData: any;
    pbDisplayedColums = ['ptwIssue','tpcNo','fieldNo','ptwReturn','tpcNoPtwReturn','fieldNoPtwReturn'];
    pbDataSource: any;
    fieldNoPtwIssue: any;
    fieldNoPtwReturn: any; 
    ptwAvailedFromDateTime: any;
    ptwAvailedThruDateTime: any;
    tpcNoPtwIssue: any;
    tpcNoPtwReturn: any;
    date: any;
    time: any;
    ptwAvailedFromDateOnlyTime: any;
    ptwAvailedThruDateOnlyTime: any;
    validOperation: boolean;
    SMHDetails: any;
    ptwIssueAvailable: boolean;
    nowDate= this.datePipe.transform(new Date(), 'dd-MM-yyyy');
    pbDate: any
  //  finalOrginalData: any;

	constructor(
	    private formBuilder: FormBuilder,
	    private spinnerService: Ng4LoadingSpinnerService,
	    private commonService: CommonService,
	    private route: ActivatedRoute,
	    private router: Router,
        private datePipe: DatePipe,
	    private sendAndRequestService:SendAndRequestService,
        private dialog: MatDialog
	  ) {
	  }
	  
	ngOnInit() {
        //this.powerBlockData = new PowerBlockModel();
  		this.pbId = +this.route.snapshot.params['pbId'];
  		//const SMHData: SwitchMaintenenceHistory [] = [];
        this.sendAndRequestService.requestForGET(Constants.app_urls.OPERATIONS.POWER_BLOCK.GET_POWER_BLOCK_BY_ID +  this.pbId)
	      .subscribe((resp) => {
	        this.powerBlockData = resp;
              //console.log('*** block Data ****'+this.powerBlockData);
              this.pbDataSource = new MatTableDataSource(this.powerBlockData);
            this.sectionCode = this.powerBlockData.elementarySectionCode;
              this.section = this.powerBlockData.section;
              this.requestedBy = this.powerBlockData.reqnBy;
              if(this.powerBlockData.ptwAvailedFromDateTime) {
                this.getPtwIssueWithExistDate(new Date(this.powerBlockData.ptwAvailedFromDateTime));                  
              }
              if(this.powerBlockData.ptwAvailedThruDateTime) {
                this.getPtwReturnWithExistDate(new Date(this.powerBlockData.ptwAvailedThruDateTime))    
              }
              this.fieldNoPtwIssue = this.powerBlockData.fieldNoPtwIssue;
              this.fieldNoPtwReturn = this.powerBlockData.fieldNoPtwReturn;
              this.tpcNoPtwIssue = this.powerBlockData.tpcNoPtwIssue;
              this.tpcNoPtwReturn = this.powerBlockData.tpcNoPtwReturn;
              this.date = this.datePipe.transform(this.powerBlockData.createdDate, 'dd-MM-yyyy')
            this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY+this.powerBlockData.facilityId).subscribe((data) => {
                this.spinnerService.hide();
                this.facilityData = data;
                this.depot = this.facilityData.facilityName
            }, error => {
                this.spinnerService.hide();
            });
              
              /*
             this.sendAndRequestService.requestForGET(Constants.app_urls.OPERATIONS.SWITCH_MAINTENENCE_HISTORY.GET_SMH_DATA_FOR_OPERATION+this.pbId+'/'+this.resp.section+'/'+this.resp.elementarySectionCode)
              .subscribe((SMHResponse) => {
                  console.log('*** object size ***'+SMHResponse.length);
                //  SMHData.push(SMHResponse);
                  this.dataSource = new MatTableDataSource(SMHResponse); 
              }); 
              */
              
              this.sendAndRequestService.requestForGET(Constants.app_urls.OPERATIONS.SWITCH_MAINTENENCE_HISTORY.GET_SMH_DATA_BASED_ON_PBID+this.pbId)
              .subscribe((SMHResponse) => {
                  this.orginalSMHData = SMHResponse;
              });
              
             this.sendAndRequestService.requestForGET(Constants.app_urls.OPERATIONS.SWITCH_MAINTENENCE_HISTORY.GET_SMH_DATA_BASED_ON_PBID+this.pbId)
              .subscribe((SMHResponse) => {
                  this.SMHDetails = SMHResponse;
                    this.sendAndRequestService.requestForGET(Constants.app_urls.OPERATIONS.PB_SWITCH_CONTROL.GET_PB_SWITCH_CONTROL_BASED_ON_EXTEND_TYPE_AND_EXTEND_CODE+'/'+this.powerBlockData.section+'/'+this.powerBlockData.elementarySectionCode)
                        .subscribe((PBSCResponse) => {
                        this.pbSwitchControl = PBSCResponse;
                            //console.log("*** log ***"+JSON.stringify(this.pbSwitchControl));
                            //console.log("** switch sizes**"+this.SMHDetails.length );
                            //console.log("** PBSCResponse sizes**"+PBSCResponse.length );
                          if(SMHResponse.length === PBSCResponse.length){
                            // if(SMHResponse){
                             // console.log('** total object **'+JSON.stringify(this.orginalSMHData));
                              for (let i = 0; i < this.SMHDetails.length; i++) {
                                if(this.SMHDetails[i].ioOpenedDateTime){
                                    this.SMHDetails[i].ioOpenedOnlyTime = new Date(this.SMHDetails[i].ioOpenedDateTime).toLocaleTimeString();
                                }
                                if(this.SMHDetails[i].ioOpenedDateTimeDone){
                                    this.SMHDetails[i].ioOpenDoneOnlyTime = new Date(this.SMHDetails[i].ioOpenedDateTimeDone).toLocaleTimeString();
                                }
                                if(this.SMHDetails[i].ioClosedDateTime){
                                    this.SMHDetails[i].ioClosedOnlyTime = new Date(this.SMHDetails[i].ioClosedDateTime).toLocaleTimeString();
                                }
                                if(this.SMHDetails[i].ioClosedDateTimeDone){
                                    this.SMHDetails[i].ioCloseDoneOnlyTime = new Date(this.SMHDetails[i].ioClosedDateTimeDone).toLocaleTimeString();
                                }
                              }
                              this.dataSource = new MatTableDataSource(this.SMHDetails);
                          }else {
                            for (let i = 0; i < this.pbSwitchControl.length; i++) {
                                let flag = 0;
                              
                                for (let j = 0; j < this.SMHDetails.length; j++) {
                                    if(this.pbSwitchControl[i].switchId === this.SMHDetails[j].ioLocation) {
                                        this.SMHDetails.splice(j,1);
                                        j--;
                                        flag =1;
                                    }
                                }// inner loop close
                                if(flag === 0){
                                        this.pbSwitchControl[i].ioLocation = this.pbSwitchControl[i].switchId;
                                        this.pbSwitchControl[i].ioType = this.pbSwitchControl[i].switchType;
                                        this.orginalSMHData.push(this.pbSwitchControl[i]);
                                        for (let i = 0; i < this.orginalSMHData.length; i++) {
                                            if(this.orginalSMHData[i].ioOpenedDateTime){
                                                this.orginalSMHData[i].ioOpenedOnlyTime = new Date(this.orginalSMHData[i].ioOpenedDateTime).toLocaleTimeString();
                                            }
                                            if(this.orginalSMHData[i].ioOpenedDateTimeDone){
                                                this.orginalSMHData[i].ioOpenDoneOnlyTime = new Date(this.orginalSMHData[i].ioOpenedDateTimeDone).toLocaleTimeString();
                                            }
                                            if(this.orginalSMHData[i].ioClosedDateTime){
                                                this.orginalSMHData[i].ioClosedOnlyTime = new Date(this.orginalSMHData[i].ioClosedDateTime).toLocaleTimeString();
                                            }
                                            if(this.orginalSMHData[i].ioClosedDateTimeDone){
                                                this.orginalSMHData[i].ioCloseDoneOnlyTime = new Date(this.orginalSMHData[i].ioClosedDateTimeDone).toLocaleTimeString();
                                            }
                                        }    
                                       // SMHData.push(this.pbSwitchControl[i]); 
                                } 
                              }// outer loop close
                              //console.log('** total object **'+JSON.stringify(this.orginalSMHData));
                              this.dataSource = new MatTableDataSource(this.orginalSMHData);
                                 
                          }// else close 
                            
                    })// pb switch control data request service close
                   
              })// SMH data request service close
	        
	        this.spinnerService.hide();
              
	      })// power block data request service close
	      
  	}

    processSaveAction(row) {
        
        this.checkOpenAndOpenDoneStatus(row);
        if(this.validOperation ) {
            let savePbSwitchControl = {
                ioOpenedDateTime: new Date(row.ioOpenedDateTime),
                fieldNoIoOpen: row.fieldNoIoOpen,
                ioOpenedDateTimeDone: new Date(row.ioOpenedDateTimeDone),
                fieldNoIoOpenDone: row.fieldNoIoOpenDone,
                ioClosedDateTime: new Date(row.ioClosedDateTime),
                fieldNoIoClose: row.fieldNoIoClose,
                ioClosedDateTimeDone: new Date(row.ioClosedDateTimeDone),
                fieldNoIoCloseDone: row.fieldNoIoCloseDone,
                ioOpenedBy: row.ioOpenedBy,
                ioClosedBy: row.ioClosedBy,
                pbOperationSeqId: this.pbId,
                ioLocation: row.ioLocation,
                ioType: row.ioType,
                tpcNoIoClose: row.tpcNoIoClose,
                tpcNoIoCloseDone: row.tpcNoIoCloseDone,
                tpcNoIoOpen: row.tpcNoIoOpen,
                tpcNoIoOpenDone: row.tpcNoIoOpenDone
            }
            this.sendAndRequestService.requestForPOST(Constants.app_urls.OPERATIONS.SWITCH_MAINTENENCE_HISTORY.SAVE_SWITCH_MAINTENENCE_HISTORY ,savePbSwitchControl, false).subscribe(response => {
                this.spinnerService.hide();
                this.resp = response;
                if (this.resp.code == Constants.CODES.SUCCESS) {
                    this.validOperation = false;
                this.commonService.showAlertMessage("Switch Maintenance Data Saved  Successfully");
                //this.router.navigate(['../'], { relativeTo: this.route });
                }else{
                  this.commonService.showAlertMessage("Switch Maintenance Data   Failed.");
                }
                
           });    
        }
        
    }
    
    checkOpenAndOpenDoneStatus(row: any) {
        if (row.ioOpenedDateTime && row.ioOpenedDateTimeDone) {
            this.validOperation = true;
        }else 
            this.commonService.showAlertMessage("Please Operate Open and OpenDone Switch's");
    }
    
    pbSaveAction(){
        this.checkPTWIssueStatus();
        if(this.ptwIssueAvailable){
            this.powerBlockData.ptwAvailedFromDateTime = new Date(this.ptwAvailedFromDateTime);
            this.powerBlockData.ptwAvailedThruDateTime = new Date(this.ptwAvailedThruDateTime);
            this.powerBlockData.tpcNoPtwIssue = this.tpcNoPtwIssue;
            this.powerBlockData.tpcNoPtwReturn = this.tpcNoPtwReturn;
            this.powerBlockData.fieldNoPtwIssue = this.fieldNoPtwIssue;
            this.powerBlockData.fieldNoPtwReturn = this.fieldNoPtwReturn;
            //console.log('***power block data ***'+JSON.stringify(this.powerBlockData));
            this.sendAndRequestService.requestForPUT(Constants.app_urls.OPERATIONS.POWER_BLOCK.UPDATE_POWER_BLOCK ,this.powerBlockData, false).subscribe(response => {
                this.spinnerService.hide();
                this.resp = response;
                if (this.resp.code == Constants.CODES.SUCCESS) {
                this.commonService.showAlertMessage("Power Block Data Updated Successfully");
                //this.router.navigate(['../'], { relativeTo: this.route });
                }else{
                  this.commonService.showAlertMessage("Power Block Data Updation Failed.");
                }
              }, error => {
                console.log('ERROR >>>');
                this.spinnerService.hide();
                this.commonService.showAlertMessage("Power Block Data Updation Failed.");
              });    
        }
            
    }
    
    checkPTWIssueStatus(){
        if(this.ptwAvailedFromDateTime) {
            this.ptwIssueAvailable = true
        }else
            this.commonService.showAlertMessage("Please Issue PTW");
    }

    getOpenDate(row) {
        row.ioOpenedDateTime = this.getCurrentDate();
        row.ioOpenedOnlyTime = this.time; 
        //console.log("** open time ***"+row.ioOpenedTime);
        row.tpcNoIoOpen = this.getRandomNumber()
    }

    getOpenDoneDate(row) {
        row.ioOpenedDateTimeDone = this.getCurrentDate();
        row.ioOpenDoneOnlyTime = this.time; 
        row.tpcNoIoOpenDone = this.getRandomNumber()
    }

    getCloseDate(row) {
        if(this.powerBlockData.ptwAvailedThruDateTime){
            row.ioClosedDateTime = this.getCurrentDate();
            row.ioClosedOnlyTime = this.time; 
            row.tpcNoIoClose = this.getRandomNumber()  
        }else 
            this.commonService.showAlertMessage("Please Return PTW");    
    }

    getCloseDoneDate(row) {
         if(this.powerBlockData.ptwAvailedThruDateTime){
            row.ioClosedDateTimeDone = this.getCurrentDate();
            row.ioCloseDoneOnlyTime = this.time; 
            row.tpcNoIoCloseDone = this.getRandomNumber();
         }else
             this.commonService.showAlertMessage("Please Return PTW");      
    }
    
    getPtwIssueDate(){
        if(this.pbSwitchControl.length > 0 ) {
            let   operatedAllSwitchs: boolean = false;
           if(this.SMHDetails.length === this.pbSwitchControl.length){
            for (let i = 0; i < this.SMHDetails.length; i++) {
                if(this.SMHDetails[i].ioType === 'MANUAL'){
                    if(this.SMHDetails[i].ioOpenedDateTime && this.SMHDetails[i].ioOpenedDateTimeDone){
                        operatedAllSwitchs = true;   
                    }
                    else
                        operatedAllSwitchs = false;
                }else{
                    if(this.SMHDetails[i].ioOpenedDateTimeDone){
                        operatedAllSwitchs = true;   
                    }else
                        operatedAllSwitchs = false;
                 }    
            }
            if(operatedAllSwitchs){
                this.getCurrentDate(); 
                this.ptwAvailedFromDateTime = new Date(this.date+" "+this.time);
                this.ptwAvailedFromDateOnlyTime = this.time;
                this.tpcNoPtwIssue = this.getRandomNumber();    
            }else 
              this.commonService.showAlertMessage("Please Operate All Switch's");  
           }else{
                for (let i = 0; i < this.orginalSMHData.length; i++) {
                    if(this.orginalSMHData[i].ioType === 'MANUAL'){
                        if(this.orginalSMHData[i].ioOpenedDateTime && this.orginalSMHData[i].ioOpenedDateTimeDone){
                            operatedAllSwitchs = true;   
                        }
                        else
                            operatedAllSwitchs = false;
                    }else{
                        if(this.orginalSMHData[i].ioOpenedDateTimeDone){
                            operatedAllSwitchs = true;   
                        }else
                            operatedAllSwitchs = false;
                     }    
                }
                if(operatedAllSwitchs){
                    this.getCurrentDate(); 
                    this.ptwAvailedFromDateTime = new Date(this.date+" "+this.time);
                    this.ptwAvailedFromDateOnlyTime = this.time;
                    this.tpcNoPtwIssue = this.getRandomNumber();    
                }else 
                  this.commonService.showAlertMessage("Please Operate All Switch's");
               
           }
               
        }else
            this.commonService.showAlertMessage("Please Define Switch's");
    }
    
    getPtwReturnDate(){
        if(this.powerBlockData.ptwAvailedFromDateTime) {
            this.getCurrentDate(); 
            this.ptwAvailedThruDateTime = new Date(this.date+" "+this.time);
            this.ptwAvailedThruDateOnlyTime = this.time;
            this.tpcNoPtwReturn = this.getRandomNumber();   
        }else
            this.commonService.showAlertMessage("Please Issue PTW");
        
    }
    
    getPtwIssueWithExistDate(ptwIssuedate: Date){
        this.date = ptwIssuedate.toLocaleDateString();
        this.time = ptwIssuedate.toLocaleTimeString();
        this.ptwAvailedFromDateTime = ptwIssuedate;
        this.ptwAvailedFromDateOnlyTime = this.time;
    }
    
    getPtwReturnWithExistDate(ptwReturndate: Date){
        this.date = ptwReturndate.toLocaleDateString();
        this.time = ptwReturndate.toLocaleTimeString();
        this.ptwAvailedThruDateTime = ptwReturndate;
        this.ptwAvailedThruDateOnlyTime = this.time;
    }
    
    getCurrentDate(){
        this.currentDate  = new Date();
        //return this.datePipe.transform(this.currentDate, 'dd-MM-yyyy hh:mm:ss')
        this.date = this.currentDate.toLocaleDateString();
        //console.log('*** date ***'+this.date);
        this.time = this.currentDate.toLocaleTimeString();
        //console.log('*** time ***'+this.time);
        return this.sendAndRequestService.convertIndiaStandardTimeToTimestamp(this.currentDate);
    }

    getRandomNumber() {
        var max = 99;
        return Math.floor(Math.random() * (max + 1));
    }
    
    addSwitchDialog(row){
         const dialogRef = this.dialog.open(AddSwitchDialogComponent, {
          height: '300px',
          width: '80%', 
          data: {}
        });
        
        dialogRef.afterClosed().subscribe(result => {
            
            if(result){
                let switchRecord = { ioLocation: result.data.switchName, ioType: result.data.switchType,ioOpenedDateTime:'',fieldNoIoOpen:'',
                ioOpenedDateTimeDone:'',fieldNoIoOpenDone:'',ioClosedDateTime:'',fieldNoIoClose:'',
                ioClosedDateTimeDone:'',fieldNoIoCloseDone:'',ioOpenedBy:'',ioClosedBy:'',pbOperationSeqId: this.pbId,
                tpcNoIoClose:'',tpcNoIoCloseDone:'',tpcNoIoOpen:'',tpcNoIoOpenDone:''
                    
                }
                if(this.SMHDetails.length === this.pbSwitchControl.length){
                   this.SMHDetails.push(switchRecord);
                    this.dataSource = new MatTableDataSource(this.SMHDetails);
                }else {
                   this.orginalSMHData.push(switchRecord);
                   this.dataSource = new MatTableDataSource(this.orginalSMHData); 
                }                
            }
        });
    }
    
    ptwAmendment(){
         const dialogRef = this.dialog.open(PTWIssueReturnAmendmentDialogComponent, {
          height: '300px',
          width: '80%', 
          data: {PBDetails:this.powerBlockData}
        });
        
        dialogRef.afterClosed().subscribe(result => {
                       
        });
    }
    
    switchAmendment(row){
        
        const dialogRef = this.dialog.open(SwitchAmendmentDialogComponent, {
          height: '300px',
          width: '80%', 
          data: {switchDetails:row}
        });
        
        dialogRef.afterClosed().subscribe(result => {
                       
        });
        
    }
    
}


@Component({
  selector: 'add-switch-dialog',
  templateUrl: 'add-switch.component.html',
})
export class AddSwitchDialogComponent implements OnInit  {
        
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
    addSwitchFormGroup: FormGroup;
    switchTypes = ['REMOTE','MANUAL'];
    
    constructor(
    private formBuilder: FormBuilder,
    private spinnerService: Ng4LoadingSpinnerService,  
    private sendAndRequestService:SendAndRequestService,
    private dialog: MatDialog,
    private commonService: CommonService,
    private dialogRef: MatDialogRef<AddSwitchDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private datePipe: DatePipe
    ) {
      
      if(data) {
         
      }
  }
    
    ngOnInit() {
        
        this.addSwitchFormGroup = this.formBuilder.group({
            switchType:[null ,  Validators.required ],
            switchName: [null,  Validators.required  ],
        });
        
    }
    
    addSwitch(){
        let switchDetails = {
            switchType: this.addSwitchFormGroup.controls['switchType'].value,
            switchName: this.addSwitchFormGroup.controls['switchName'].value
        }
        this.dialogRef.close({data:switchDetails});   
    }

}


@Component({
  selector: 'ptw-amendment-dialog',
  templateUrl: 'ptw-issue-return-amendment.component.html',
})
export class PTWIssueReturnAmendmentDialogComponent implements OnInit  {
        
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
    pbAmendmentFormGroup: FormGroup;
    pbOperationSeqId: any;
    resp: any;
    PBAmendmentId: any;
    
    constructor(
    private formBuilder: FormBuilder,
    private spinnerService: Ng4LoadingSpinnerService,  
    private sendAndRequestService:SendAndRequestService,
    private dialog: MatDialog,
    private commonService: CommonService,
    private dialogRef: MatDialogRef<PTWIssueReturnAmendmentDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private datePipe: DatePipe
    ) {
      
      if(data) {
         this.pbOperationSeqId = data.PBDetails.id
      }
  }
    
    ngOnInit() {
        
        this.pbAmendmentFormGroup = this.formBuilder.group({
            id: [null],
            pbOperationSeqId: [null],
            pbAmendmentSeqId: [null],
            ptwAvailedFromDateTime:[null],
            ptwAvailedThruDateTime: [null],
            tpcNoPtwIssue:[null],
            tpcNoPtwReturn:[null],
            fieldNoPtwIssue:[null],
            fieldNoPtwReturn:[null]
        });
        this.getPowerBlocksAmendmentData();   
    }
    
    getPowerBlocksAmendmentData() {
        this.sendAndRequestService.requestForGET(Constants.app_urls.OPERATIONS.POWER_BLOCKS_AMENDMENT.GET_PB_AMENDMENT_BASED_ON_PB_OPERATION_ID +  this.pbOperationSeqId)
          .subscribe((resp) => {
            this.resp = resp;
              //console.log('*** values are ***'+JSON.stringify(this.resp));
              if(this.resp){
                  this.PBAmendmentId = this.resp.id;
                this.pbAmendmentFormGroup.patchValue({
                      id: this.resp.id,
                      pbOperationSeqId: this.pbOperationSeqId,
                      pbAmendmentSeqId: this.resp.pbAmendmentSeqId,
                      ptwAvailedFromDateTime: !! this.resp.ptwAvailedFromDateTime ? new Date(this.resp.ptwAvailedFromDateTime) : '',
                      ptwAvailedThruDateTime: !! this.resp.ptwAvailedThruDateTime ? new Date(this.resp.ptwAvailedThruDateTime) : '',
                      tpcNoPtwIssue: this.resp.tpcNoPtwIssue,
                      tpcNoPtwReturn: this.resp.tpcNoPtwReturn,
                      fieldNoPtwIssue: this.resp.fieldNoPtwIssue,
                      fieldNoPtwReturn: this.resp.fieldNoPtwReturn,
                    });
              }
            this.spinnerService.hide();
       });
            
    }
    
    savePBAmendment(){
        let svaePBAmendment = {
            id: this.PBAmendmentId,
            //pbAmendmentSeqId: this.resp.pbAmendmentSeqId,
            ptwAvailedFromDateTime: this.pbAmendmentFormGroup.controls['ptwAvailedFromDateTime'].value,
            ptwAvailedThruDateTime: this.pbAmendmentFormGroup.controls['ptwAvailedThruDateTime'].value,
            tpcNoPtwIssue: this.pbAmendmentFormGroup.controls['tpcNoPtwIssue'].value,
            tpcNoPtwReturn: this.pbAmendmentFormGroup.controls['tpcNoPtwReturn'].value,
            fieldNoPtwIssue: this.pbAmendmentFormGroup.controls['fieldNoPtwIssue'].value,
            fieldNoPtwReturn: this.pbAmendmentFormGroup.controls['fieldNoPtwReturn'].value,
            pbOperationSeqId: this.pbOperationSeqId
        }
        this.sendAndRequestService.requestForPOST(Constants.app_urls.OPERATIONS.POWER_BLOCKS_AMENDMENT.SAVE_PB_AMENDMENT ,svaePBAmendment, false).subscribe(response => {
            this.spinnerService.hide();
            this.resp = response;
            if (this.resp.code == Constants.CODES.SUCCESS) {
            this.commonService.showAlertMessage("Power Block Amendment Data saved Successfully");
                this.dialogRef.close();
            }else{
              this.commonService.showAlertMessage("Power Block Amendment Data saving Failed.");
            }
          }, error => {
            console.log('ERROR >>>');
            this.spinnerService.hide();
            this.commonService.showAlertMessage("Power Block Amendment Data saving Failed.");
          });
        
    }

}

@Component({
  selector: 'switch-amendment-dialog',
  templateUrl: 'switch-amendment.component.html',
})
export class SwitchAmendmentDialogComponent implements OnInit  {
        
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
    switchAmendmentFormGroup: FormGroup;
    switchSeqId: any;
    resp: any;
    switchAmendmentId: any;
    
    constructor(
    private formBuilder: FormBuilder,
    private spinnerService: Ng4LoadingSpinnerService,  
    private sendAndRequestService:SendAndRequestService,
    private dialog: MatDialog,
    private commonService: CommonService,
    private dialogRef: MatDialogRef<SwitchAmendmentDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private datePipe: DatePipe
    ) {
      
      if(data) {
          console.log("switch Details ***"+JSON.stringify(data));
         this.switchSeqId = data.switchDetails.id
          console.log("switch seq Id ***"+this.switchSeqId);
      }
  }
    
    ngOnInit() {
        
        this.switchAmendmentFormGroup = this.formBuilder.group({
            id: [null],
            seqId: [null],
            amendmentSeqId: [null],
            ioOpenedDateTime: [null],
            fieldNoIoOpen: [null],
            ioOpenedDateTimeDone: [null],
            fieldNoIoOpenDone: [null],
            ioClosedDateTime: [null],
            fieldNoIoClose: [null],
            ioClosedDateTimeDone: [null],
            fieldNoIoCloseDone: [null],
            ioOpenedBy: [null],
            ioClosedBy: [null],
            tpcNoIoClose: [null],
            tpcNoIoCloseDone: [null],
            tpcNoIoOpen: [null],
            tpcNoIoOpenDone: [null]
        });
        this.getSMHDetails();
    }
    
   getSMHDetails() {
        this.sendAndRequestService.requestForGET(Constants.app_urls.OPERATIONS.SWITCH_MAINTENENCE_HISTORY_AMENDMENT.GET_SWITCH_AMENDMENT_BASED_ON_SMH_ID +  this.switchSeqId)
          .subscribe((resp) => {
            this.resp = resp;
             // console.log('*** values are ***'+JSON.stringify(this.resp));
              if(this.resp){
                  this.switchAmendmentId = this.resp.id;
                this.switchAmendmentFormGroup.patchValue({
                      id: this.resp.id,
                    seqId: this.switchSeqId,
                    amendmentSeqId: this.resp.amendmentSeqId,
                    ioOpenedDateTime: !! this.resp.ioOpenedDateTime ? new Date(this.resp.ioOpenedDateTime) : '',
                    fieldNoIoOpen: this.resp.fieldNoIoOpen,
                    ioOpenedDateTimeDone: !! this.resp.ioOpenedDateTimeDone ? new Date(this.resp.ioOpenedDateTimeDone) : '',
                    fieldNoIoOpenDone: this.resp.fieldNoIoOpenDone,
                    ioClosedDateTime: !! this.resp.ioClosedDateTime ? new Date(this.resp.ioClosedDateTime) : '',
                    fieldNoIoClose: this.resp.fieldNoIoClose,
                    ioClosedDateTimeDone: !! this.resp.ioClosedDateTimeDone ? new Date(this.resp.ioClosedDateTimeDone) : '',
                    fieldNoIoCloseDone: this.resp.fieldNoIoCloseDone,
                    ioOpenedBy: this.resp.ioOpenedBy,
                    ioClosedBy: this.resp.ioClosedBy,
                    tpcNoIoClose: this.resp.tpcNoIoClose,
                    tpcNoIoCloseDone: this.resp.tpcNoIoCloseDone,
                    tpcNoIoOpen: this.resp.tpcNoIoOpen,
                    tpcNoIoOpenDone: this.resp.tpcNoIoOpenDone,
                    });
              }
            this.spinnerService.hide();
       });
            
    }
    
    savePBAmendment(){
        let svaeSMHAmendment = {
             id: this.switchAmendmentId,
             seqId: this.switchSeqId,
           // amendmentSeqId:  this.resp.amendmentSeqId,
            ioOpenedDateTime:  this.switchAmendmentFormGroup.controls['ioOpenedDateTime'].value,
            fieldNoIoOpen: this.switchAmendmentFormGroup.controls['fieldNoIoOpen'].value,
            ioOpenedDateTimeDone: this.switchAmendmentFormGroup.controls['ioOpenedDateTimeDone'].value,
            fieldNoIoOpenDone: this.switchAmendmentFormGroup.controls['fieldNoIoOpenDone'].value,
            ioClosedDateTime: this.switchAmendmentFormGroup.controls['ioClosedDateTime'].value,
            fieldNoIoClose: this.switchAmendmentFormGroup.controls['fieldNoIoClose'].value,
            ioClosedDateTimeDone: this.switchAmendmentFormGroup.controls['ioClosedDateTimeDone'].value,
            fieldNoIoCloseDone: this.switchAmendmentFormGroup.controls['fieldNoIoCloseDone'].value,
            ioOpenedBy: this.switchAmendmentFormGroup.controls['ioOpenedBy'].value,
            ioClosedBy: this.switchAmendmentFormGroup.controls['ioClosedBy'].value,
            tpcNoIoClose: this.switchAmendmentFormGroup.controls['tpcNoIoClose'].value,
            tpcNoIoCloseDone: this.switchAmendmentFormGroup.controls['tpcNoIoCloseDone'].value,
            tpcNoIoOpen: this.switchAmendmentFormGroup.controls['tpcNoIoOpen'].value,
            tpcNoIoOpenDone: this.switchAmendmentFormGroup.controls['tpcNoIoOpenDone'].value,
            
        }
        this.sendAndRequestService.requestForPOST(Constants.app_urls.OPERATIONS.SWITCH_MAINTENENCE_HISTORY_AMENDMENT.SAVE_SMH_AMENDMENT ,svaeSMHAmendment, false).subscribe(response => {
            this.spinnerService.hide();
            this.resp = response;
            if (this.resp.code == Constants.CODES.SUCCESS) {
            this.commonService.showAlertMessage("Switch Amendment Data saved Successfully");
                this.dialogRef.close();   
            }else{
              this.commonService.showAlertMessage("Switch Amendment Data saving Failed.");
            }
          }, error => {
            console.log('ERROR >>>');
            this.spinnerService.hide();
            this.commonService.showAlertMessage("Switch Amendment Data saving Failed.");
          });
        
    }

}