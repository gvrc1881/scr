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
import { Location } from '@angular/common';
import { DatePipe } from '@angular/common';


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
  phaseDataSource: MatTableDataSource<ProjectPhaseModel>;
  displayedColumns = ['sno','workName', 'phaseName','description','sequence','dependencyToStart','weightage','status','plannedStartDate','targetCompletionDate','commenceDate','completionDate','actions'];
  enableUpdate: boolean; 
  addPhases:boolean;
  workList:any;
  PhasesList:any;  
  phase = [];
  resp: any; 
  maxDate = new Date();
  toMinDate = new Date();
  minDate = new Date(); 
  dateFormat = 'dd-MM-yyyy';
  currentDate = new Date();
  toTargetDate=new Date();

  constructor(
    public dialog: MatDialog,
    private formBuilder: FormBuilder,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private sendAndRequestService:SendAndRequestService ,
    private datePipe: DatePipe,
   // private route: ActivatedRoute,
   // private router: Router,   
    private location: Location,    
) {
}

ngOnInit() {

  var permissionName = this.commonService.getPermissionNameByLoggedData("PROJECT ADMIN","Phases") ;
  this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

this.spinnerService.show();

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
  filterValue = filterValue.trim();
  filterValue = filterValue.toLowerCase();
   this.phaseDataSource.filter = filterValue;

  
}
getPhases() {  
  //const phase: ProjectPhaseModel[] = [];
  this.PhasesList = [];
  this.phase = []; 
   this.sendAndRequestService.requestForGET(Constants.app_urls.PROJECT_ADMIN.PHASES.GET_WORK_PHASES_BY_ID+this.searchInputFormGroup.value.work).subscribe((data) => {
           this.PhasesList = data;
           this.toMinDate=new Date(this.PhasesList.plannedStartDate),
           this.toTargetDate=new Date(this.PhasesList.commenceDate)
              for (var i = 0; i < this.PhasesList.length; i++) {
           this.PhasesList[i].sno = i + 1;
          //  this.PhasesList[i].plannedStartDate = this.datePipe.transform(this.PhasesList[i].plannedStartDate, 'dd-MM-yyyy ');
          //  this.PhasesList[i].targetCompletionDate = this.datePipe.transform(this.PhasesList[i].targetCompletionDate, 'dd-MM-yyyy ');
          //  this.PhasesList[i].commenceDate = this.datePipe.transform(this.PhasesList[i].commenceDate, 'dd-MM-yyyy ');
          //  this.PhasesList[i].completionDate = this.datePipe.transform(this.PhasesList[i].completionDate, 'dd-MM-yyyy ');

           this.phase.push(this.PhasesList[i]);
       }
       this.phaseDataSource = new MatTableDataSource(this.phase);
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
      this.phaseDataSource=new MatTableDataSource();
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


onGoBack() {
 // this.router.navigate(['../'], { relativeTo: this.route });
 this.location.back();
  }

addEvent($event) {
  this.minDate = new Date($event.value); 
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
        this.phaseDataSource=new MatTableDataSource();
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

