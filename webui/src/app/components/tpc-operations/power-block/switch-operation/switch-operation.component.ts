import { Component, OnInit } from '@angular/core';
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
  //  finalOrginalData: any;

	constructor(
	    private formBuilder: FormBuilder,
	    private spinnerService: Ng4LoadingSpinnerService,
	    private commonService: CommonService,
	    private route: ActivatedRoute,
	    private router: Router,
	    private sendAndRequestService:SendAndRequestService
	  ) {
	  }
	  
	ngOnInit() {
        //this.powerBlockData = new PowerBlockModel();
  		this.pbId = +this.route.snapshot.params['pbId'];
  		//const SMHData: SwitchMaintenenceHistory [] = [];
        this.sendAndRequestService.requestForGET(Constants.app_urls.OPERATIONS.POWER_BLOCK.GET_POWER_BLOCK_BY_ID +  this.pbId)
	      .subscribe((resp) => {
	        this.powerBlockData = resp;
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
                  let SMHDetails:any = SMHResponse;
                    this.sendAndRequestService.requestForGET(Constants.app_urls.OPERATIONS.PB_SWITCH_CONTROL.GET_PB_SWITCH_CONTROL_BASED_ON_EXTEND_TYPE_AND_EXTEND_CODE+'/'+this.powerBlockData.section+'/'+this.powerBlockData.elementarySectionCode)
                        .subscribe((PBSCResponse) => {
                        this.pbSwitchControl = PBSCResponse;
                          if(SMHResponse.length === PBSCResponse.length){
                             // console.log('** total object **'+JSON.stringify(this.orginalSMHData));
                              console.log('*** both length are same');
                              this.dataSource = new MatTableDataSource(SMHResponse);
                          }else {
                            for (let i = 0; i < this.pbSwitchControl.length; i++) {
                                let flag = 0;
                              
                                for (let j = 0; j < SMHDetails.length; j++) {
                                    if(this.pbSwitchControl[i].switchId === SMHDetails[j].ioLocation) {
                                        SMHDetails.splice(j,1);
                                        j--;
                                        flag =1;
                                    }
                                }// inner loop close
                                if(flag === 0){
                                        this.pbSwitchControl[i].ioLocation = this.pbSwitchControl[i].switchId;
                                        this.pbSwitchControl[i].ioType = this.pbSwitchControl[i].switchType;
                                        this.orginalSMHData.push(this.pbSwitchControl[i]);
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
            this.commonService.showAlertMessage("Switch Maintenance Data Saved  Successfully");
            //this.router.navigate(['../'], { relativeTo: this.route });
            }else{
              this.commonService.showAlertMessage("Switch Maintenance Data   Failed.");
            }
            
       });
    }
    
    pbSaveAction(){
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
          })    
    }

    getOpenDate(row) {
        row.ioOpenedDateTime = this.getCurrentDate(); 
        row.tpcNoIoOpen = this.getRandomNumber()
    }

    getOpenDoneDate(row) {
        row.ioOpenedDateTimeDone = this.getCurrentDate(); 
        row.tpcNoIoOpenDone = this.getRandomNumber()
    }

    getCloseDate(row) {
        row.ioClosedDateTime = this.getCurrentDate(); 
        row.tpcNoIoClose = this.getRandomNumber()
    }

    getCloseDoneDate(row) {
        row.ioClosedDateTimeDone = this.getCurrentDate(); 
        row.tpcNoIoCloseDone = this.getRandomNumber()
    }
    
    getPtwIssueDate(){
        this.getCurrentDate(); 
        this.ptwAvailedFromDateTime = new Date(this.date+" "+this.time);
        this.ptwAvailedFromDateOnlyTime = this.time;
        this.tpcNoPtwIssue = this.getRandomNumber()
    }
    
    getPtwReturnDate(){
        this.getCurrentDate(); 
        this.ptwAvailedThruDateTime = new Date(this.date+" "+this.time);
        this.ptwAvailedThruDateOnlyTime = this.time;
        this.tpcNoPtwReturn = this.getRandomNumber()
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
	
}