import { Component, ViewChild, OnInit, ElementRef, ViewEncapsulation } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource, MatDialog, MatDialogRef } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { FuseConfirmDialogComponent } from '../../components/confirm-dialog/confirm-dialog.component';
import { CommonService } from 'src/app/common/common.service';
import { SchedulerTrackingService } from '../../services/scheduler-tracking.service';
import { SchedulerTrackingModel } from 'src/app/models/scheduler-tracking.model';
import { RemarkDialogComponent } from '../remark-dialog/remark-dialog.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-scheduler-tracking',
  templateUrl: './scheduler-tracking.component.html',
  styleUrls: ['./scheduler-tracking.component.css'],
  
})
export class SchedulerTrackingComponent implements OnInit {

  rolePermission: boolean = true;
  confirmDialogRef: MatDialogRef<RemarkDialogComponent>;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  status: boolean;
  schedulerData: any;
  response:any;
  schedulerResponse: any;
  portPattern = "^[0-9]{4}$";
  pattern = "[a-zA-Z][a-zA-Z ]*";
  ipPattern = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  title: string = "Add";
  schedulerDisplayedColumns = ['sno', 'trackingId', 'jobId', 'divisionCode', 'timeInterval', 'processedDate', 'startTime', 'endTime', 'runType','runBy', 'jobStatus', 'id'];
  schedulerDataSource: MatTableDataSource<SchedulerTrackingModel>;
  @ViewChild(MatPaginator, { static: true }) schedulerPaginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
  constructor(
    public dialog: MatDialog,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private schedulerTrackingService: SchedulerTrackingService,
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
    this.schedulerTrackingService.findAllJobInfo().subscribe((data) => {
      this.schedulerData = data;
      console.log('schedulerData: '+JSON.stringify(data))
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
     // console.log('schedulerData: ' + JSON.stringify(schedulerData))
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
    //console.log('date: '+this.convert(date));
    //console.log('date: '+date.toLocaleTimeString());
    //console.log('date: '+date.getMilliseconds());
    var dataString = this.convert(date)+" "+date.toLocaleTimeString()+"."+date.getMilliseconds();
    console.log('dataString: '+dataString);
     //date.format('%Y-%m-%d %H:%M:%S')
   // return false;
    this.confirmDialogRef = this.dialog.open(RemarkDialogComponent, {
      disableClose: false,
      height: '',
      width: '50%', 
      data:{"jobId":jobId, "runTypeId":runTypeId}
    });
    
//date.toString("MMM dd"); // "Dec 20"
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
         this.schedulerTrackingService.reRunByIdByType(remarkDetails).subscribe((response) => {
          this.schedulerResponse = response;
          console.log(JSON.stringify(response));
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
    this.schedulerTrackingService.downloadXSL(details).subscribe((response) => {
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
  //  console.log(trackingId);
    this.router.navigate(['jobs/'+trackingId]);
               
  }

}
