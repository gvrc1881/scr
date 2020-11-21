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
import { FuseConfirmPopupComponent } from '../confirm-popup/confirm-popup.component';

@Component({
  selector: 'app-project-phases',
  templateUrl: './project-phases.component.html',
  styleUrls: ['./project-phases.component.css'],
  providers: [
    {
        provide: DateAdapter, useClass: AppDateAdapter
    },
    {
        provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
    }
    ]
})

export class ProjectPhasesComponent implements OnInit {
  FiledLabels = FieldLabelsConstant.LABELS;
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  title: string = Constants.EVENTS.UPDATE;
  searchInputFormGroup: FormGroup;
  standardPhaseActivityList: any;
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  dataSource: MatTableDataSource<ProjectPhaseModel>;
  displayedColumns = ['sno','workName', 'phaseName','description','sequence','dependencyToStart','weightage','status','plannedStartDate','targetCompletionDate','commenceDate','completionDate','actions'];
  enableUpdate: boolean; 
  workList:any;
  PhasesList:any;  
  phase = [];
  resp: any; 
  maxDate = new Date();
  toMinDate=new Date();
  completeMinDate=new Date();  
  dateFormat = 'dd-MM-yyyy';
  currentDate = new Date();
  expectDate=new Date();
  completeDate = new Date();
  toTargetDate=new Date();

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
      'work': [null]     
  });
   this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WORK).subscribe((data) => {
      this.workList = data;      
  },error => {} );
 this.enableUpdate=false;
}

// duplicateWorkIdAndName(row) {
//   const q = new Promise((resolve, reject) => {              

//     let work  = row.workId.id;
//     let phaseName = row.phaseName;
    
//     this.sendAndRequestService.requestForGET(Constants.app_urls.PROJECT_ADMIN.PHASES.EXIST_WORK_AND_PHASE_NAME+work+'/'+phaseName)
//     .subscribe((duplicate) => {
//       if (duplicate) {
//         resolve({ 'duplicateWorkIdAndName': true });
//       } else {
//         resolve(null);
//       }
//     }, () => { resolve({ 'duplicateWorkIdAndName': true }); });
//   });
//   return q;
// }

// duplicateWorkIdAndSequence(row) {
//   console.log('check');
//   const q = new Promise((resolve, reject) => {              

//     let work  = row.workId;
//     let sequence = row.phaseName;
    
//     this.sendAndRequestService.requestForGET(Constants.app_urls.PROJECT_ADMIN.PHASES.EXIST_WORK_AND_SEQUENCE+work+'/'+sequence)
//     .subscribe((duplicate) => {
//       if (duplicate) {
//         resolve({ 'duplicateWorkIdAndSequence': true });
//       } else {
//         resolve(null);
//       }
//     }, () => { resolve({ 'duplicateWorkIdAndSequence': true }); });
//   });
//   return q;
// }

  
applyFilter(filterValue: string) {
  filterValue = filterValue.trim(); // Remove whitespace
  filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
  this.dataSource.filter = filterValue;
}

getPhases() {  
  //const phase: ProjectPhaseModel[] = [];
  this.PhasesList = [];
  this.phase = []; 
   this.sendAndRequestService.requestForGET(Constants.app_urls.PROJECT_ADMIN.PHASES.GET_WORK_PHASES_BY_ID+this.searchInputFormGroup.value.work).subscribe((data) => {
           this.PhasesList = data;
              for (var i = 0; i < this.PhasesList.length; i++) {
           this.PhasesList[i].sno = i + 1;
          
           this.phase.push(this.PhasesList[i]);
       }
       this.dataSource = new MatTableDataSource(this.phase);
       this.enableUpdate=true;
       this.addPermission=false;
   });
}
updatePhase()
 {
  this.sendAndRequestService.requestForPOST(Constants.app_urls.PROJECT_ADMIN.PHASES.UPDATE, this.phase, false).subscribe(response => {
    this.spinnerService.hide();
    this.resp = response;
 
    if (this.resp.code == Constants.CODES.SUCCESS) {
      this.commonService.showAlertMessage("Project Phase Data Updated Successfully");
      this.searchInputFormGroup.reset();
      this.dataSource=new MatTableDataSource();
      this.enableUpdate=false;
      
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
      this.sendAndRequestService.requestForDELETE(Constants.app_urls.PROJECT_ADMIN.PHASES.DELETE, id).subscribe((data) => {
        this.resp = data;

        if (this.resp.code === 200) {

          this.confirmDialogRef = this.dialog.open(FuseConfirmPopupComponent, {
            disableClose: false

          });
          this.confirmDialogRef.componentInstance.confirmMessage = this.resp.message;
         
        } else {
          this.confirmDialogRef = this.dialog.open(FuseConfirmPopupComponent, {
            disableClose: false
          });
          this.confirmDialogRef.componentInstance.confirmMessage = this.resp.message;
        
        }
        this.spinnerService.hide();       
        this.searchInputFormGroup.reset();
        this.dataSource=new MatTableDataSource();
        this.enableUpdate=false;
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Drive Deletion Failed.");
      })
    }
    this.confirmDialogRef = null;
  });
}


}

