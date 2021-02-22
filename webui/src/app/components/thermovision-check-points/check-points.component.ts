import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { ChekPointsModel } from 'src/app/models/check-points.model';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { MatTableDataSource, MatPaginator,  MatDialogRef, MatDialog, } from '@angular/material';
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
  originalDepotsData: any = JSON.parse(sessionStorage.getItem('depotData'));
  divisionData: any = JSON.parse(sessionStorage.getItem('divisionData'));

   

   distinctDepotType:any = this.originalDepotsData.map(item => item.depotType)
   .filter((value, index, self) => self.indexOf(value) === index)

  searchInputFormGroup: FormGroup;
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  dataSource: MatTableDataSource<ChekPointsModel>;
  displayedColumns = ['sno','checkPointPart', 'checkPoint1Description','checkPoint2Description','displayOrder'];
  enableUpdate: boolean; 
  divisionsList:any;
  funLocTypeData:any;
  depotsList:any;
  pointsData:any;
  checkPointsList = [];
  res: any;
  enableSave: boolean;
  checkPoints =[];
  public stationType = ['SP','SSP','TSS'];


  constructor(
    public dialog: MatDialog,
    private formBuilder: FormBuilder,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private sendAndRequestService:SendAndRequestService ,  
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
                  console.log('checkPointPart'+this.checkPointsList[i].checkPointsList);
               }
               this.enableSave = true;
           }
            this.dataSource = new MatTableDataSource(this.checkPointsList);
   });
}
updateCheckPoints()
 {
  this.sendAndRequestService.requestForPOST(Constants.app_urls.THERMOVISION.THERMOVISION_CHECK_POINTS.UPDATE_CHECK_POINTS,this.checkPointsList,true).subscribe((response) => {
    this.spinnerService.hide();
    console.log('** update Response **'+JSON.stringify(this.checkPointsList));
   this.res = response;
   this.enableSave = false;
   if (this.res.code == Constants.CODES.SUCCESS) {
      this.commonService.showAlertMessage("Check Points Data Updated Successfully");
      this.searchInputFormGroup.reset();
      this.dataSource=new MatTableDataSource();
      this.enableUpdate=false;
      this.addPermission=true;
    } else {
      this.commonService.showAlertMessage("Check Points Updating Failed.");
    }
  }, error => {
    console.log('ERROR >>>');
    this.spinnerService.hide();
    this.commonService.showAlertMessage("Check Points Updating Failed.");
  });
 
}




divisionDetails() {
  this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.GET_DIVISIONS)
    .subscribe((data) => {
      this.divisionsList = data;
    });
}

getFacilitys(){
  var depotType = this.searchInputFormGroup.value.depotType ;
  this.depotsList = this.originalDepotsData.filter(value => {
    return value.depotType == depotType;
  });
}
getDepot(){
  var division = this.searchInputFormGroup.value.division ;
  this.depotsList = this.originalDepotsData.filter(value => {
    return value.division == division;
  });
}
copy() {
  const dialogRef = this.dialog.open(CopyCheckPointsComponent, {
    height: '400px',
    width: '80%',
  });
  dialogRef.afterClosed().subscribe(result => {
    this.ngOnInit();
  });
}
}

@Component({
  selector: 'copy-check-points',
  templateUrl: 'copy-check-points.component.html',
})
export class CopyCheckPointsComponent implements OnInit {
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;

  checkPointFormGroup: FormGroup;
  copyCheckPointFormGroup: FormGroup;
  copyPoints = [];
  checkPointsExists: boolean;
  dataSource: MatTableDataSource<ChekPointsModel>;
  displayedColumns = ['sno', 'active','checkPointPart','checkPoint1Description','checkPoint2Description','displayOrder'];
  facilityId: any;
  createCheckPoints: boolean;
  res: any;
  toFacilityData=[];
  createCopyPoints: boolean;
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  depotData: any = JSON.parse(sessionStorage.getItem('depotData'));
  filterDepot: any = this.depotData.filter(u => 
    u.depotType == 'SP' || u.depotType == 'SSP' || u.depotType == 'TSS');

  constructor(
    private formBuilder: FormBuilder,
    private spinnerService: Ng4LoadingSpinnerService,
    private sendAndRequestService: SendAndRequestService,
    private dialogRef: MatDialogRef<CopyCheckPointsComponent>,
    private commonService: CommonService,
    public dialog: MatDialog
  ) { }

  ngOnInit() {
    this.createCheckPoints = true;
    this.checkPointFormGroup = this.formBuilder.group({
      facilityId: [null]
    })
    this.copyCheckPointFormGroup = this.formBuilder.group({
      facilityId: [null]
    })
  }

  saveAction() {
    this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
      disableClose: false

    });
    this.confirmDialogRef.componentInstance.confirmMessage = 'Do you want to copy existing documents to new Check Points ?';
    this.confirmDialogRef.afterClosed().subscribe(result => {
      if(result){
        var copyPoints={
              "facilityId":this.copyCheckPointFormGroup.value.facilityId,

        }
        this.sendAndRequestService.requestForPOST(Constants.app_urls.THERMOVISION.THERMOVISION_CHECK_POINTS.COPY_CHECK_POINTS,this.copyPoints,true).subscribe((response) => {
          this.spinnerService.hide();
         this.res = response;
         if (this.res.code == Constants.CODES.SUCCESS) {
            this.commonService.showAlertMessage("Copy Check Points Data Saved Successfully");
            this.checkPointFormGroup.reset();
            this.dataSource=new MatTableDataSource();
          } else {
            this.commonService.showAlertMessage("Copy Check Points Data Saving Failed.");
          }
          this.createCheckPoints = true;
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Copy Check Points Data Saving Failed.");
        });  
      }         
    }); 
  }
  
  copyCheckPointsFormSubmit() {
    this.copyPoints = [];
    this.facilityId = this.checkPointFormGroup.controls['facilityId'].value;
    this.dataSource = new MatTableDataSource(this.copyPoints);
    this.sendAndRequestService.requestForGET(Constants.app_urls.THERMOVISION.THERMOVISION_CHECK_POINTS.GET_CHECK_POINTS_BASED_ON_FACILITY_ID+this.facilityId).subscribe((data) => {
      this.copyPoints = data;
      console.log('** rseponse **'+JSON.stringify(this.copyPoints.length));
      if(this.toFacilityData.length == 0){
        this.createCopyPoints = true;
        for(let i =0 ; i < this.copyPoints.length ; i++ ){
          this.copyPoints[i].facilityId = this.copyCheckPointFormGroup.controls['facilityId'].value;;
      }
        
      }
      console.log('** to copyPoints data **'+JSON.stringify(this.copyPoints));
      if(this.copyPoints.length > 0) {
                this.createCopyPoints = true;

          for(let i =0 ; i < this.copyPoints.length ; i++ ){
              this.copyPoints[i].sno = i+1;
          }
          this.checkPointsExists = true;
      }
       this.dataSource = new MatTableDataSource(this.copyPoints);
});
  }

  getCopyFacilityDetails(){
    let facility=this.copyCheckPointFormGroup.controls['facilityId'].value.id;
     this.sendAndRequestService.requestForGET(Constants.app_urls.THERMOVISION.THERMOVISION_CHECK_POINTS.GET_CHECK_POINTS_BASED_ON_FACILITY_ID+facility).subscribe((data) => {
       this.toFacilityData = data;
       console.log("toFacilityData"+JSON.stringify(data))
 }
     );

  }

  onGoBack(): void {
    this.dialogRef.close();
  }

}