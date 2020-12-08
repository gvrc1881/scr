import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { DrivesTargetModel } from 'src/app/models/drive.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FuseConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';

@Component({
  selector: 'app-drives-targets',
  templateUrl: './drives-target.component.html',
  styleUrls: []
})
export class DrivesTargetComponent implements OnInit {

  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
    editPermission: boolean = true;
    addPermission: boolean = true;
    deletePermission: boolean = true;
    userdata: any = JSON.parse(localStorage.getItem('userData'));
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    displayedColumns = ['sno','driveId','assetType','unitType','unitName', 'target', 'population','actions'];    
    dataSource: MatTableDataSource<DrivesTargetModel>;
    driveTargetFormGroup:FormGroup;
    facilityHierarchy:any = JSON.parse(localStorage.getItem('depotData')); 
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    @ViewChild('filter', { static: true }) filter: ElementRef;
  
    driveTargetList: any;
    driveCategoryList:any;
    drivesList:any;
    depoTypeList:any;
    facilityList:any;
    resp:any;
    driveName:any;
    facility:any;
    targetsList:any;
    target:any;
    enableSave:boolean;
    editTargetResponse:any;
    responseStatus:any;
    id:any;
  
    constructor(
      private spinnerService: Ng4LoadingSpinnerService,
      private commonService: CommonService,
     private sendAndRequestService : SendAndRequestService,
      private router: Router,
      private route: ActivatedRoute,
      public dialog: MatDialog,
      private formBuilder: FormBuilder,

    ) { }
  
    ngOnInit() {
      var permissionName = this.commonService.getPermissionNameByLoggedData("DRIVES","Drive Target") ;
        this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
      this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
      this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
  
      this.spinnerService.show();
     // this.getDriveTargetData();
     this.findDepoTypeList();
    this.findUnitNames();  

      this.driveTargetFormGroup = this.formBuilder.group({
           'driveCategory': [null,Validators.required ],
           'drive' : [null  ],
           'unitType' : [null  ],
           'unitName' : [null ],
       });

     this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CATEGORY.GET_DRIVE_CATEGORY).subscribe((data) => {
           this.driveCategoryList = data;
           
       },error => {} );   

   }

   getDrives(){

    let driveCategory = this.driveTargetFormGroup.value.driveCategory; 
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.TARGETS.GET_VALID_DRIVES_BASED_ON_CATEGORY+driveCategory).subscribe((data) => {
      this.drivesList = data;
      
    },error => {} ); 

}
  findDepoTypeList() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FUNCTIONAL_LOCATION_TYPES)
      .subscribe((depoTypes) => {
        this.depoTypeList = depoTypes;
      })
  }

  findUnitNames(){   
   
      this.facilityList=[];    
    
      
        for (let i = 0; i < this.facilityHierarchy.length; i++) {
          
         
              this.facilityHierarchy.facilityList;             
          
       } 
   }

   updateAssertType($event) {
  console.log("event==")
    if ($event.value) {
      console.log("eventvalue=="+$event.value)
      this.facility = [];
      console.log("facility=="+this.facility);
      this.facility = this.facilityHierarchy.filter(element => {
        console.log("return==="+this.facilityHierarchy.element)
        return element.depotType == $event.value;
       
      });
      
    }
  }
   
    applyFilter(filterValue: string) {
      filterValue = filterValue.trim(); // Remove whitespace
      filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
      this.dataSource.filter = filterValue;
    }

    getDriveTargets() {

      let driveCategoryId = this.driveTargetFormGroup.value.driveCategory; 
      let driveId = this.driveTargetFormGroup.value.drive; 
      let unitType = this.driveTargetFormGroup.value.unitType; 
      let unitName = this.driveTargetFormGroup.value.unitName; 

      console.log("category=="+driveCategoryId);
      this.targetsList = [];
      this.target = []; 
       this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.TARGETS.GET_TARGETS_BASEDON_DRIVE+driveCategoryId+'/'+driveId+'/'+unitType+'/'+unitName).subscribe((data) => {
               this.targetsList = data;

                  for (var i = 0; i < this.targetsList.length; i++) {
               this.targetsList[i].sno = i + 1;              
               this.target.push(this.targetsList[i]);  }
           this.dataSource = new MatTableDataSource(this.target);  
           this.enableSave=true;        
           //this.addPermission=false;
       });
    }

saveAction(){

  let targets = {

      driveId : this.driveTargetFormGroup.value.drive, 
       unitType : this.driveTargetFormGroup.value.unitType, 
       unitName : this.driveTargetFormGroup.value.unitName ,
       population:this.driveTargetFormGroup.value.population ,
       target:this.driveTargetFormGroup.value.target
};
this.sendAndRequestService.requestForPOST(Constants.app_urls.DRIVE.TARGETS.SAVE_TARGETS, targets, false).subscribe(response => {
  this.spinnerService.hide();
  this.resp = response;

  if (this.resp.code == Constants.CODES.SUCCESS) {
    this.commonService.showAlertMessage("Drive Target Data Updated Successfully");
    this.driveTargetFormGroup.reset();
    this.dataSource=new MatTableDataSource();
   // this.enableUpdate=false;
    this.addPermission=true;
    //this.router.navigate(['../'], { relativeTo: this.route });
  } else {
    this.commonService.showAlertMessage("Drive Target Updating Failed.");
  }
}, error => {
  console.log('ERROR >>>');
  this.spinnerService.hide();
  this.commonService.showAlertMessage("Drive Target Updating Failed.");
});

}

     processEditAction(id) {
       this.router.navigate([id], { relativeTo: this.route });
     }

  

   

    delete(id) {
      this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
        disableClose: false
      });
      this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete?';
      this.confirmDialogRef.afterClosed().subscribe(result => {
        if (result) {
          this.spinnerService.show();
          this.sendAndRequestService.requestForDELETE(Constants.app_urls.DRIVE.TARGETS.DELETE, id).subscribe(data => {
            console.log(JSON.stringify(data));
            this.spinnerService.hide();
            this.commonService.showAlertMessage("Deleted Drive Target Successfully");
            //this.getDriveTargetData();
          }, error => {
            console.log('ERROR >>>');
            this.spinnerService.hide();
            this.commonService.showAlertMessage("Drive Target Deletion Failed.");
          })
        }
        this.confirmDialogRef = null;
      });
    }
  
  }
  
