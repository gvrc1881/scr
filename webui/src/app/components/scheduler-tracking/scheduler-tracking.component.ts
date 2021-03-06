import { Component, ViewChild, OnInit, ElementRef, ViewEncapsulation } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource, MatDialog, MatDialogRef } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { SchedulerTrackingModel } from 'src/app/models/scheduler-tracking.model';
import { RemarkDialogComponent } from '../remark-dialog/remark-dialog.component';
import { Router } from '@angular/router';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';

@Component({
  selector: 'app-scheduler-tracking',
  templateUrl: './scheduler-tracking.component.html',
  styleUrls: ['./scheduler-tracking.component.css'],
  
})
export class SchedulerTrackingComponent implements OnInit {

  rolePermission: boolean = true;
  confirmDialogRef: MatDialogRef<RemarkDialogComponent>;
  userdata: any = JSON.parse(sessionStorage.getItem('userData'));
  status: boolean;
  schedulerData: any;
  response:any;
  schedulerResponse: any;
  portPattern = "^[0-9]{4}$";
  pattern = "[a-zA-Z][a-zA-Z ]*";
  ipPattern = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
  loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
  title: string = "Add";
  schedulerDisplayedColumns = ['sno', 'trackingId', 'jobId', 'divisionCode', 'timeInterval', 'processedDate', 'startTime', 'endTime', 'runType','runBy', 'jobStatus', 'id'];
  schedulerDataSource: MatTableDataSource<SchedulerTrackingModel>;
  @ViewChild(MatPaginator, { static: true }) schedulerPaginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
  pagination=Constants.PAGINATION_NUMBERS;
  constructor(
    public dialog: MatDialog,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
   private sendAndRequestService: SendAndRequestService,
    private router: Router,
  ) {

  }

  ngOnInit() {
    this.rolePermission = this.commonService.rolePermission();   
    this.findAllJobInfo();
  }

  findAllJobInfo() {
    this.spinnerService.show();
    const schedulerData: SchedulerTrackingModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.SCHEDULER_TRACKING.GET_JOBS).subscribe((data) => {
      this.schedulerData = data;
      for (let i = 0; i < this.schedulerData.length; i++) {

        if (!this.rolePermission) {
          if (this.schedulerData[i].repository.repositoryCode == this.userdata.divisionCode) {
            this.schedulerData[i].sno = i + 1;
            this.schedulerData[i].jobId = this.schedulerData[i].jobId.jobId;
            this.schedulerData[i].divisionCode = this.schedulerData[i].repository.repositoryCode;
            this.schedulerData[i].timeInterval = this.schedulerData[i].timeInterval.timeInterval;
            this.schedulerData[i].jobStatus = this.schedulerData[i].processStatus;
            schedulerData.push(this.schedulerData[i]);
          }else if(this.userdata.divisionCode == 'All'){
            this.schedulerData[i].sno = i + 1;
            this.schedulerData[i].jobId = this.schedulerData[i].jobId.jobId;
            this.schedulerData[i].divisionCode = this.schedulerData[i].repository.repositoryCode;
            this.schedulerData[i].timeInterval = this.schedulerData[i].timeInterval.timeInterval;
            this.schedulerData[i].jobStatus = this.schedulerData[i].processStatus;
            schedulerData.push(this.schedulerData[i]);
          }
        } else {
          if (this.schedulerData[i].repository.repositoryCode == this.userdata.divisionCode) {
            this.schedulerData[i].sno = i + 1;
            this.schedulerData[i].jobId = this.schedulerData[i].jobId.jobId;
            this.schedulerData[i].divisionCode = this.schedulerData[i].repository.repositoryCode;
            this.schedulerData[i].timeInterval = this.schedulerData[i].timeInterval.timeInterval;
            this.schedulerData[i].jobStatus = this.schedulerData[i].processStatus;
            schedulerData.push(this.schedulerData[i]);
          }else if(this.userdata.divisionCode == 'All'){
            this.schedulerData[i].sno = i + 1;
            this.schedulerData[i].jobId = this.schedulerData[i].jobId.jobId;
            this.schedulerData[i].divisionCode = this.schedulerData[i].repository.repositoryCode;
            this.schedulerData[i].timeInterval = this.schedulerData[i].timeInterval.timeInterval;
            this.schedulerData[i].jobStatus = this.schedulerData[i].processStatus;
            schedulerData.push(this.schedulerData[i]);
          }
        }

      }
      this.schedulerDataSource = new MatTableDataSource(schedulerData);
      this.schedulerDataSource.paginator = this.schedulerPaginator;
      this.schedulerDataSource.sort = this.sort;
      this.spinnerService.hide();
    });
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.schedulerDataSource.filter = filterValue;
  }

  convert(str) {
    var date = new Date(str),
      mnth = ("0" + (date.getMonth() + 1)).slice(-2),
      day = ("0" + date.getDate()).slice(-2);
    return [date.getFullYear(), mnth, day].join("-");
  }

  runSchedulerJobById(jobId, runTypeId, processedDate) {
    var date = new Date(processedDate);
    var dataString = this.convert(date)+" "+date.toLocaleTimeString()+"."+date.getMilliseconds();
    this.confirmDialogRef = this.dialog.open(RemarkDialogComponent, {
      disableClose: false,
      height: '',
      width: '50%', 
      data:{"jobId":jobId, "runTypeId":runTypeId}
    });
    
   this.confirmDialogRef.afterClosed().subscribe(remark => {
      if (remark) {
        this.spinnerService.show();
        const remarkDetails ={
          "jobId":jobId,
          "runTypeId": runTypeId,
          "remark":remark,
          "runBy":this.loggedUserData.id,
          "processedDate":dataString
      }
         this.sendAndRequestService.requestForPOST(Constants.app_urls.MASTERS.SCHEDULER_TRACKING.RERUN_WITH_REMARK,remarkDetails, false).subscribe((response) => {
          this.schedulerResponse = response;
          this.spinnerService.hide();
          setTimeout(() => {
            this.findAllJobInfo()
          }, 500);
          
        }, error =>{ this.spinnerService.hide(); this.commonService.showAlertMessage(error)}); 
      } this.confirmDialogRef = null;
    });
    this.commonService.scrollTop("forms");
  }
  
  downloadXSL(trackingId, runTypeId){
    const details ={
      "trackingId":trackingId,
      "runTypeId": runTypeId,     
      "runBy":this.loggedUserData.id
    }
    this.sendAndRequestService.requestForPOST(Constants.app_urls.MASTERS.SCHEDULER_TRACKING.DOWNLOAD_XL,details, false).subscribe((response) => {
      this.response = response;
      if(this.response.code == 200){
        this.commonService.showAlertMessage("Downloaded Successfully.");
      }
      else{
        this.commonService.showAlertMessage("Download Failed.")
      }
    },error =>{
      this.spinnerService.hide(); this.commonService.showAlertMessage(error)
    });
  }

  operationTypes(trackingId){
    this.router.navigate(['jobs/'+trackingId]);
               
  }

}
