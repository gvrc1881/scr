import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { DriveTargetModel } from 'src/app/models/drive.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FuseConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { R3TargetBinder } from '@angular/compiler';

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
    userdata: any = JSON.parse(sessionStorage.getItem('userData'));
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    displayedColumns = ['sno','driveId','assetType','unitType','unitName', 'target', 'poulation','divisionAggregation','subDivisionAggregation','depotAggregation'];    
    dataSource: MatTableDataSource<DriveTargetModel>;
    driveTargetFormGroup:FormGroup;
    loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
    zoneHierarchy:any = JSON.parse(sessionStorage.getItem('zoneData'));
    divisionHierarchy:any = JSON.parse(sessionStorage.getItem('divisionData'));   
    subDivisionHierarchy:any =JSON.parse(sessionStorage.getItem('subDivData'));
    depotHierarchy:any = JSON.parse(sessionStorage.getItem('depotData')); 
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
    enableSave:boolean=false;
    editTargetResponse:any;
    responseStatus:any;
    id:any;
    zoneList:any;
    divisionList:any;
    subDivisionList:any;
    depotsList:any;
    enableZone:boolean;
    enableDivision:boolean;
    enableSubDivision:boolean;
    enableDepot:boolean;
  
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
     this.findZone();

      this.driveTargetFormGroup = this.formBuilder.group({
           'driveCategory': [null,Validators.required ],
           'drive' : [null  ],
           'zone' : [null  ],
           'division' : [null ],
           'subDivision':[null],
           'facility':[null]
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
  /* findDepoTypeList() {
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
      this.facility = this.facilityList.filter(element => {
        console.log("return==="+this.facilityHierarchy.element)
        return element.depotType == $event.value;
       
      });
      
    }
  }
   */

  findZone(){
    this.zoneList = [];
    this.divisionList = [];
    this.subDivisionList = [];
    this.depotsList = [];

    this.zoneHierarchy.zoneList;
    this.enableZone = true;
       
   if(this.zoneHierarchy.length>0)
   {
   
     this.enableZone=true;
   }
   else if(this.divisionHierarchy.length>0){
    
     this.enableZone=false;
     this.enableDivision=true;    
     
   }
   else if(this.subDivisionHierarchy.length>0){
    this.enableZone=false;
    this.enableDivision=false;    
    this.enableSubDivision=true;
   }
   else{
    this.enableZone=false;
    this.enableDivision=false;  
    this.enableSubDivision=false;
    this.enableDepot=true;
   }

   
}
findDivisions(){
let zone: string = this.driveTargetFormGroup.value.zone;
this.divisionList=[];    

for (let i = 0; i < this.divisionHierarchy.length; i++) {
    
       if(this.divisionHierarchy[i].zone == zone&& this.divisionHierarchy[i].depotType == 'DIV'){
                 
           this.divisionHierarchy.divisionList;
           this.enableDivision = true;
       }
    }
}

findSubDivision(){     

  let  division: string = this.driveTargetFormGroup.value.division; 
  this.subDivisionList=[];  
  for (let i = 0; i < this.subDivisionHierarchy.length; i++) {
      
         if(this.subDivisionHierarchy[i].division == division && this.subDivisionHierarchy[i].depotType == 'SUB_DIV'){
         
            // this.subDivisionList.push(this.subDivisionHierarchy[i]); 
             this.subDivisionHierarchy.subDivisionList; 
             
             this.enableSubDivision = true;             
         }
      }
}

findDepot(){
            this.depotsList=[]; 
            let subDivision = this.driveTargetFormGroup.value.subDivision;
            for (let i = 0; i < this.depotHierarchy.length; i++) {
                
                  if(this.depotHierarchy[i].subDivision == subDivision  ){
                  
                      this.depotsList.push(this.depotHierarchy[i]); 
                    // this.depotHierarchy.depotsList;
                      this.enableDepot = true;
                      
                  }
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
      let zone = this.driveTargetFormGroup.value.zone; 
      let division = this.driveTargetFormGroup.value.division; 
      let subDivision = this.driveTargetFormGroup.value.subDivision;
      let facility = this.driveTargetFormGroup.value.facility;      
      this.targetsList = [];
      this.target = []; 
      console.log("category=="+driveId+'-'+driveCategoryId+'-'+zone+'-'+division+'-'+subDivision+'-'+facility);
       this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.TARGETS.GET_TARGETS_BASEDON_DRIVE+
        driveCategoryId+'/'+this.driveTargetFormGroup.value.drive+'/'+zone+'/'+division+'/'+subDivision+'/'+facility).subscribe((data) => {
               this.targetsList = data;
                  console.log("targetslist=="+JSON.stringify(this.targetsList));
                  for (var i = 0; i < this.targetsList.length; i++) {
               this.targetsList[i].sno = i + 1;              
               this.target.push(this.targetsList[i]);  }
           this.dataSource = new MatTableDataSource(this.target);  
           if(this.targetsList.length > 0){
            this.enableSave=true; 
           }
                 
           //this.addPermission=false;
       });
    }

  saveTarget(){

    console.log("save target calling")
  
this.sendAndRequestService.requestForPOST(Constants.app_urls.DRIVE.TARGETS.SAVE_TARGETS,  this.target, false).subscribe(response => {
  this.spinnerService.hide();
  this.resp = response;

  if (this.resp.code == Constants.CODES.SUCCESS) {
    this.commonService.showAlertMessage("Drive Target Data Updated Successfully");
    this.driveTargetFormGroup.reset();
    this.dataSource=new MatTableDataSource();
    this.enableSave=false;
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
  
