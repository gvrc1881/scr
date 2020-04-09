import { Component, ViewChild, OnInit, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatPaginator, MatSort, MatTableDataSource, MatDialog, MatDialogRef } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { FuseConfirmDialogComponent } from '../../../components/confirm-dialog/confirm-dialog.component';
import { CommonService } from 'src/app/common/common.service';
import { SchedulerTrackingService } from '../../../services/scheduler-tracking.service';
import { SchedulerTrackingModel } from 'src/app/models/scheduler-tracking.model';
import { ActivatedRoute } from '@angular/router';
import { DivisionHistoryDialogComponent } from '../../division-history-dialog/division-history-dialog.component';
import { ViewEncapsulation } from '@angular/compiler/src/core';
@Component({
  selector: 'app-jobs-info',
  templateUrl: './jobs-info.component.html',
  styleUrls: ['./jobs-info.component.css'],
  
})
export class JobsInfoComponent implements OnInit {

  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  status: boolean;
  divisionHistoryDialogRef:MatDialogRef<DivisionHistoryDialogComponent>;
  schedulerData: any;
 
 
  schedulerResponse: any;
  portPattern = "^[0-9]{4}$";
  pattern = "[a-zA-Z][a-zA-Z ]*";
  ipPattern = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  title: string = "Add";
  schedulerDisplayedColumns = ['sno','trackingId', 'operationId', 'jobType', 'processedDate', 'startTime', 'endTime', 'jobStatus'];
  schedulerDataSource: MatTableDataSource<SchedulerTrackingModel>;
  @ViewChild(MatPaginator, { static: true }) schedulerPaginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
  constructor(   
    public dialog: MatDialog,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private route: ActivatedRoute,
    private schedulerTrackingService: SchedulerTrackingService,
  ) {
    
  }

  ngOnInit() {
    this.spinnerService.show();
    this.findTrackingIdJobHistoryInfo();   
  }

  

 

  findTrackingIdJobHistoryInfo() {
    const schedulerData: SchedulerTrackingModel[] = [];
    //console.log(JSON.stringify(this.route.snapshot.params['id']))
    localStorage.setItem("pid", this.route.snapshot.params['id']);
    this.schedulerTrackingService.findOperationTypesHistoryInfo(this.route.snapshot.params['id']).subscribe((data) => {
      this.schedulerData = data; 
      //console.log(JSON.stringify(data))     
      for (let i = 0; i < this.schedulerData.length; i++) {
        this.schedulerData[i].sno = i + 1;
        schedulerData.push(this.schedulerData[i]);
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

  /* viewDivisionDetails(divisionId, id){
    this.spinnerService.show();    
    this.schedulerTrackingService.divisionInfo(divisionId, id).subscribe((response) => {     
      this.spinnerService.hide(); 
       this.divisionHistoryDialogRef = this.dialog.open(DivisionHistoryDialogComponent, {
        disableClose: false,
        height: '600px',
        width: '80%',       
        data:response
      });            
    }, error => this.commonService.showAlertMessage(error));
   
    
  } */
  

 /*  runSchedulerJobById(jobId, runTypeId){
    console.log("runSchedulerJobById: "+jobId+" = "+runTypeId);
    this.schedulerTrackingService.reRunByIdByType(jobId, runTypeId).subscribe((response) => {      
      this.schedulerResponse = response;
     // console.log("resp: " + JSON.stringify(response));
      this.findAllJobInfo()
    }, error => this.commonService.showAlertMessage(error));
    this.commonService.scrollTop("forms");
  } */

}
