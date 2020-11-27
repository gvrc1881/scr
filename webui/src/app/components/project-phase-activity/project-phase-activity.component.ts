import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { ProjectPhaseModel } from 'src/app/models/projectPhase.model';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { ActivatedRoute, Router } from '@angular/router';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog, DateAdapter, MAT_DATE_FORMATS } from '@angular/material';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';

@Component({
  selector: 'app-project-phase-activity',
  templateUrl: './project-phase-activity.component.html',
  styleUrls: ['./project-phase-activity.component.css'],
  providers: [
    {
        provide: DateAdapter, useClass: AppDateAdapter
    },
    {
        provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
    }
    ]
 
})

export class ProjectPhaseActivityComponent implements OnInit {
 

    FiledLabels = FieldLabelsConstant.LABELS;
    editPermission: boolean = true;
    addPermission: boolean = true;
    deletePermission: boolean = true;
    title: string = Constants.EVENTS.UPDATE;
    searchInputFormGroup: FormGroup;
    standardPhaseActivityList: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    dataSource: MatTableDataSource<ProjectPhaseModel>;
    displayedColumns = ['sno','projectPhaseName', 'name','description','sequence','dependencyToStart','uom','isCheckList','isObjectIdRequired','depotType','assetType','plannedStartDate','targetCompletionDate','commenceDate','completionDate','actions'];
    enableUpdate: boolean; 
    workList:any;
    PhaseActivityList:any;
    activity = [];
    resp: any; 
    maxDate = new Date();
    toMinDate=new Date();
    completeMinDate=new Date();  
    dateFormat = 'dd-MM-yyyy';
    currentDate = new Date();
    expectDate=new Date();
    completeDate = new Date();
    toTargetDate=new Date();
    workPhaseList:any;
    isCheckList = [{ 'id': 1, "value": 'Yes' }, { 'id': 2, "value": 'No' }];
    isObjectIdRequired = [{ 'id': 1, "value": 'Yes' }, { 'id': 2, "value": 'No' }];
    depotType = [{ 'id': 1, "value": 'OHE' }, { 'id': 2, "value": 'PSI' },{ 'id': 3, "value": 'TSS'},{ 'id': 4, "value": 'SSP'},{ 'id': 5, "value": 'SP'}];
  
    constructor(
      public dialog: MatDialog,
      private formBuilder: FormBuilder,
      private spinnerService: Ng4LoadingSpinnerService,
      private commonService: CommonService,
      private sendAndRequestService:SendAndRequestService ,
      private route: ActivatedRoute,
      private router: Router,       
  ) {
  }
  
  ngOnInit() {
    this.searchInputFormGroup = this.formBuilder.group({
        'work': [null]  ,
        'workPhase': [null]     
    });
     this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WORK).subscribe((data) => {
        this.workList = data;      
    },error => {} );   

   this.enableUpdate=false;
  }
  getWorkPhase(){
    this.sendAndRequestService.requestForGET( Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WORK_PHASES_BASED_ON_WORK + this.searchInputFormGroup.value.work.id).subscribe((response) => {
        this.workPhaseList = response;
     });
  }
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }
  
  getPhaseActivity() {  
  
    //const phase: ProjectPhaseModel[] = [];
    this.PhaseActivityList = [];
    this.activity = []; 

     this.sendAndRequestService.requestForGET(Constants.app_urls.PROJECT_ADMIN.PHASE_ACTIVITY.GET_WORK_PHASE_ACTIVITY_ID+this.searchInputFormGroup.value.workPhase).subscribe((data) => {
             this.PhaseActivityList = data;
             this.toMinDate=new Date(this.PhaseActivityList.plannedStartDate),
             this.toTargetDate=new Date(this.PhaseActivityList.commenceDate)
              for (var i = 0; i < this.PhaseActivityList.length; i++) {
             this.PhaseActivityList[i].sno = i + 1;
            
             this.activity.push(this.PhaseActivityList[i]);
         }
         this.dataSource = new MatTableDataSource(this.activity);
         this.enableUpdate=true;
         this.addPermission=false;
     });
  }
  updatePhase()
   {
    this.sendAndRequestService.requestForPOST(Constants.app_urls.PROJECT_ADMIN.PHASE_ACTIVITY.UPDATE, this.activity, false).subscribe(response => {
      this.spinnerService.show();
      this.resp = response;
   
      if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Project Phase Data Updated Successfully");
        this.searchInputFormGroup.reset();
        this.dataSource=new MatTableDataSource();
        this.enableUpdate=false;
        this.addPermission=true;
        //this.router.navigate(['../'], { relativeTo: this.route });
      } else {
        this.commonService.showAlertMessage("Project Phase Updating Failed.");
      }
    }, error => {
      console.log('ERROR >>>');
      this.spinnerService.hide();
      this.commonService.showAlertMessage("Project Phase Updating Failed.");
    });
   
  }
  addEvent($event) {
    this.toMinDate = new Date($event.value); 
    
  }
  addTargetEvent($event) {
    
    this.toTargetDate = new Date($event.value);
    
  }

  delete(id) {
    this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
      disableClose: false

    });
    this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete?';
    this.confirmDialogRef.afterClosed().subscribe(result => {
      if (result) {

        this.spinnerService.show();
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.PROJECT_ADMIN.PHASE_ACTIVITY.DELETE, id).subscribe(data => {
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted Phase Activity  Successfully");
          this.searchInputFormGroup.reset();
        this.dataSource=new MatTableDataSource();
        this.enableUpdate=false;
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Phase Activity Deletion Failed.");
        })
      }
      this.confirmDialogRef = null;
    });
  }
  
  
  }
  
  