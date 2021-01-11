import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { ChekPointsModel } from 'src/app/models/check-points.model';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { ActivatedRoute, Router } from '@angular/router';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { MatTableDataSource, MatPaginator,  MatDialogRef, MatDialog, } from '@angular/material';
import { FuseConfirmPopupComponent } from '../confirm-popup/confirm-popup.component';
import { Location } from '@angular/common';


@Component({
  selector: 'app-check-points',
  templateUrl: './check-points.component.html',
  styleUrls: ['./check-points.component.css'],
  
})

export class CheckPointsComponent implements OnInit {
  FiledLabels = FieldLabelsConstant.LABELS;
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  title: string = Constants.EVENTS.UPDATE;
  searchInputFormGroup: FormGroup;
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  dataSource: MatTableDataSource<ChekPointsModel>;
  displayedColumns = ['sno','checkPointPart', 'checkPointDescription','commparisonPoints','displayGroup','displayOrder'];
  enableUpdate: boolean; 
  divisionsList:any;
  funLocTypeData:any;
  depotsList:any;
  checkPointsList = [];
  res: any;
  enableSave: boolean;


  constructor(
    public dialog: MatDialog,
    private formBuilder: FormBuilder,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private sendAndRequestService:SendAndRequestService ,  
    private location: Location,    
) {
}

ngOnInit() {
  this.divisionDetails();
  var permissionName = this.commonService.getPermissionNameByLoggedData("THERMOVISION","Thermovision Check Points") ;
  this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

this.spinnerService.show();

  this.searchInputFormGroup = this.formBuilder.group({
      'division': [null],
      'depotType':[null],
      'facilityId':[null]     
  });
  this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FUNCTIONAL_LOCATION_TYPES).subscribe((data) => {
    this.funLocTypeData = data;
 });
 this.enableUpdate=false;
}


  

applyFilter(filterValue: string) {
  filterValue = filterValue.trim();
  filterValue = filterValue.toLowerCase();
   this.dataSource.filter = filterValue;

  
}
getCheckPoints() {  
  this.checkPointsList = [];
  this.enableSave = false;
  this.dataSource = new MatTableDataSource(this.checkPointsList);
   this.sendAndRequestService.requestForGET(Constants.app_urls.THERMOVISION.THERMOVISION_CHECK_POINTS.GET_CHECK_POINTS_BASED_ON_FACILITY_ID+this.searchInputFormGroup.value.facilityId).subscribe((data) => {
           this.checkPointsList = data;
           console.log('** rseponse **'+JSON.stringify(this.checkPointsList));
           if(this.checkPointsList.length > 0) {
               for(let i =0 ; i < this.checkPointsList.length ; i++ ){
                   this.checkPointsList[i].sno = i+1;
                  console.log('checkPointPart'+this.checkPointsList[i].checkPointPart);
               }
               this.enableSave = true;
           }
            this.dataSource = new MatTableDataSource(this.checkPointsList);
   });
}
updateCheckPoints()
 {
  this.sendAndRequestService.requestForPOST(Constants.app_urls.THERMOVISION.THERMOVISION_CHECK_POINTS.UPDATE_CHECK_POINTS,this.checkPointsList,false).subscribe((response) => {
    this.spinnerService.hide();
   this.res = response;
   if( this.res.code == 200 ){
        this.commonService.showAlertMessage("Check Points Updated Successfully");
   } else {
       this.commonService.showAlertMessage("Check Points Updation failed");
   }    
})
 
}


onGoBack() {
 // this.router.navigate(['../'], { relativeTo: this.route });
 this.location.back();
  }

divisionDetails() {
  this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.GET_DIVISIONS)
    .subscribe((data) => {
      this.divisionsList = data;
    });
}

getFacilitys(){
  var depotType = this.searchInputFormGroup.value.depotType ;
this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY_BASED_ON_DEPOTTYPE+depotType).subscribe((data) => {
           this.depotsList = data;
      });
}

}

