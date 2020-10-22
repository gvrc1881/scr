import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FacilityModel } from 'src/app/models/facility.model';
import { PbSwitchControl } from 'src/app/models/tpc-operations.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material';


@Component({
  selector: 'app-switch-operation',
  templateUrl: './switch-operation.component.html',
  styleUrls: []
})
export class SwitchOperationComponent implements OnInit {
	
	pbId: number = 0;
	resp: any;
	pbSwitchControl: any;
    dataSource: MatTableDataSource<PbSwitchControl>;
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
    displayedColumns = ['switchId','openDate','fieldNumOpen','openDoneDate','fieldNumOpenDone','openBy','closeDate','fieldNumClose','closeDoneDate','fieldNumCloseDone','closeBy','actions'];
    randomNumber: any;
    currentDate: any ;

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
  		this.pbId = +this.route.snapshot.params['pbId'];
  		const pbSwitchControlData: PbSwitchControl [] = [];
  		this.sendAndRequestService.requestForGET(Constants.app_urls.OPERATIONS.POWER_BLOCK.GET_POWER_BLOCK_BY_ID +  this.pbId)
	      .subscribe((resp) => {
	        this.resp = resp;
	        this.sendAndRequestService.requestForGET(Constants.app_urls.OPERATIONS.PB_SWITCH_CONTROL.GET_PB_SWITCH_CONTROL_BASED_ON_EXTEND_TYPE_AND_EXTEND_CODE+'/'+this.resp.section+'/'+this.resp.elementarySectionCode)
	        .subscribe((res) => {
	        this.pbSwitchControl = res;
            console.log('***length ***'+this.pbSwitchControl.length);
            this.dataSource = new MatTableDataSource(res);
	        })
	        this.spinnerService.hide();
	      })
	      
  	}

    processSaveAction(row) {
    
        let savePbSwitchControl = {
            id: 0,
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
            pbOperationSeqId: this.pbId
        }
        this.sendAndRequestService.requestForPOST(Constants.app_urls.OPERATIONS.SWITCH_MAINTENENCE_HISTORY.SAVE_SWITCH_MAINTENENCE_HISTORY ,savePbSwitchControl, false).subscribe(response => {
            this.spinnerService.hide();
            this.resp = response;
            if (this.resp.code == Constants.CODES.SUCCESS) {
            this.commonService.showAlertMessage("Switch Maintenence History Data  Successfully");
            //this.router.navigate(['../'], { relativeTo: this.route });
            }else{
              this.commonService.showAlertMessage("Switch Maintenence History  Failed.");
            }
            
       });
    }

    getOpenDate(row) {
        row.ioOpenedDateTime = this.getCurrentDate(); 
        row.fieldNoIoOpen = this.getRandomNumber()
    }

    getOpenDoneDate(row) {
        row.ioOpenedDateTimeDone = this.getCurrentDate(); 
        row.fieldNoIoOpenDone = this.getRandomNumber()
    }

    getCloseDate(row) {
        row.ioClosedDateTime = this.getCurrentDate(); 
        row.fieldNoIoClose = this.getRandomNumber()
    }

    getCloseDoneDate(row) {
        row.ioClosedDateTimeDone = this.getCurrentDate(); 
        row.fieldNoIoCloseDone = this.getRandomNumber()
    }
    
    getCurrentDate(){
        this.currentDate  = new Date();
        return this.sendAndRequestService.convertIndiaStandardTimeToTimestamp(this.currentDate);
    }

    getRandomNumber() {
        var max = 99;
        return Math.floor(Math.random() * (max + 1));
    }
	
	
}