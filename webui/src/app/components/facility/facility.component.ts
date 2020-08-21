import { OnInit, Component, ViewChild, ElementRef} from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { FacilityModel } from 'src/app/models/facility.model';
import { Constants } from 'src/app/common/constants';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';


@Component({
    selector: 'facility',
    templateUrl: './facility.component.html',
    styleUrls: []
})

export class FacilityComponent implements OnInit{
    
    addFacility:boolean;
    editPermission: boolean = true;
    addPermission: boolean = true;
    deletePermission: boolean = true;
    facilityFormGroup:FormGroup;
    id: number = 0;
    title: string = "Save";
    FacilityList:any;
    editFacilityResponse: any;
    responseStatus: any;
    facilityErrors : any;
    saveFacility:boolean;
    value:string;
    pattern = "^[A-Z0-9]+$";
    facilityDataSource: MatTableDataSource<FacilityModel>;
    facilityDisplayColumns = ['sno','zone','division','subDivision','facilityId','facilityName','facilityTypeId','depotType','parentDepot','description','id'] ;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    facilityResponse:any;
    loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
    zoneList:any;
    divisionsList:any;
    subDivisionList:any;
    enableFacilityId:any;
    enableFacilityName:any;
    zone: any;
    division:any;
    enableZone:any;
    enableDivision:any;
    enableSubDivision:any;

    constructor( 
        private formBuilder: FormBuilder,
        private commonService: CommonService,
        private spinnerService: Ng4LoadingSpinnerService,
        private sendAndRequestService:SendAndRequestService,
        public dialog: MatDialog
        ){
                this.facilityErrors = {
                    facilityId: {},
                    facilityName:{},
                    facilityTypeId:{},
                    depotType:{},
                    parentDepot:{},
                    description:{}            
                };
            
    }

    ngOnInit () {
                this.findZones();
               
        var permissionName = this.commonService.getPermissionNameByLoggedData("TRD CONFIG","FUNCTIONAL UNIT") ;
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
    
        this.getAllFacilityData();   
    }
   
    findZones()
    {
        this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_ZONE_LIST).subscribe((data) => {
            this.zoneList = data;
   }
          ); 

    }
    getDivisions(){
        this.zone =this.facilityFormGroup.controls['zone'].value; 
            
    	this.sendAndRequestService.requestForPOST(Constants.app_urls.REPORTS.GET_DIVISION_BASED_ON_ZONE, this.zone, false).subscribe((data) => {
           
        this.divisionsList = data;
                 
                });
              
    }
    

    getSubDivisions(){
       
      this.division=this.facilityFormGroup.controls['division'].value; 
        this.sendAndRequestService.requestForPOST(Constants.app_urls.REPORTS.GET_SUBDIVISION_BASED_ON_DIVISION ,this.division, false).subscribe((data) => {
            this.subDivisionList = data;
             });
        

    }
    facilitySubmit(){     

            let facilityId: string=this.facilityFormGroup.value.facilityId;
           let facilityName: string=this.facilityFormGroup.value.facilityName;
           let facilityTypeId: string=this.facilityFormGroup.value.facilityTypeId;
           let depotType: string=this.facilityFormGroup.value.depotType;
           let parentDepot: string=this.facilityFormGroup.value.parentDepot;
           let description: string=this.facilityFormGroup.value.description;
           let zone: string=this.facilityFormGroup.value.zone.code;
           let division: string=this.facilityFormGroup.value.division.code;
           let subDivision: string=this.facilityFormGroup.value.subDivision.code;

          
   
           if (this.title ==  Constants.EVENTS.SAVE) {
               var saveFacilityModel={
                                       'facilityId':facilityId,
                                       'facilityName': facilityName,
                                       'facilityTypeId':facilityTypeId,
                                       'depotType':depotType,
                                       'parentDepot':parentDepot,                                      
                                       'description':description,  
                                       'zone':zone,
                                       'division':division,
                                       'subDivision':subDivision,                                   
                                       "createdBy" : this.loggedUserData.username
               }   
               this.sendAndRequestService.requestForPOST(Constants.app_urls.CONFIG.FACILITY.SAVE_FACILITY, saveFacilityModel, false).subscribe((data) =>{              
                   this.facilityResponse = data;
                   if(this.facilityResponse.code == 200 && !!this.facilityResponse) {
                       this.commonService.showAlertMessage(this.facilityResponse.message);
                       this.getAllFacilityData();
                       this.facilityFormGroup.reset();
                   }else {
                       this.commonService.showAlertMessage("Functional Unit Data Saving Failed.");
                   }
                   this.spinnerService.hide();
               } , error => {
                   console.log('ERROR >>>');
                   this.spinnerService.hide();
                   this.commonService.showAlertMessage("Functional Unit Data Saving Failed.");
               });
           }
       
             
               else if (this.title == Constants.EVENTS.UPDATE )
                {
                   let id: number = this.editFacilityResponse.id;
                   var updateFacilityModel={
                                               'id':id,
                                               'facilityId':facilityId,
                                               'facilityName': facilityName,
                                               'facilityTypeId':facilityTypeId,
                                               'depotType':depotType,
                                               'parentDepot':parentDepot,                                      
                                               'description':description,
                                               'zone':zone,
                                               'division':division,
                                               'subDivision':subDivision,      
                                               
                                            }   
                                            this.sendAndRequestService.requestForPUT(Constants.app_urls.CONFIG.FACILITY.UPDATE_FACILITY, updateFacilityModel, false).subscribe((data) =>{ 
                                              this.facilityResponse = data;
                                           if(this.facilityResponse.code == 200 && !!this.facilityResponse)
                                           {
                                               this.commonService.showAlertMessage(this.facilityResponse.message);
                                               this.getAllFacilityData();
                                               this.facilityFormGroup.reset();
                                               this.addFacility=  false;
                                               this.title = "Save";
                                               
                                           }
                                           else {
                                                   this.commonService.showAlertMessage("Functional Unit Data Updating Failed.");
                                               }
                                       } , 
                                       error => {
                       
                                               this.spinnerService.hide();
                                               this.commonService.showAlertMessage("Functional Unit Data Updating Failed.");
                                               })
                   
               }
       }
   
       getAllFacilityData() {
               const fac : FacilityModel[] = [];
               this.sendAndRequestService.requestForGET(Constants.app_urls.CONFIG.FACILITY.GET_FACILITY).subscribe((data) =>{   

               this.FacilityList = data;
               for (let i = 0; i < this.FacilityList.length; i++) {
                   this.FacilityList[i].sno = i+1;
                   fac.push(this.FacilityList[i]);              
               }
               this.facilityDataSource = new MatTableDataSource(fac);
               this.facilityDataSource.paginator = this.paginator;
               this.facilityDataSource.sort = this.sort;
   
           } , error => {});
   
       }
       
       onGoBack() {
           this.facilityFormGroup.reset();
           this.addFacility= false;
           this.title = 'Save';
       }
   
   
       NewFacility(){
           this.addFacility = true;
           this.enableZone=false;
           this.enableDivision=false;
           this.enableSubDivision=false;
           this.facilityFormGroup = this.formBuilder.group({
             id: 0,
             'facilityId': [null,Validators.compose([Validators.required,Validators.maxLength(255)])],
             'facilityName': [null, Validators.compose([Validators.required,Validators.maxLength(255)]),this.duplicatefacilityName.bind(this)],
             'facilityTypeId': [null, Validators.maxLength(255)],
             'depotType' : [null, Validators.maxLength(255)],
             'parentDepot' : [null, Validators.maxLength(255)],
             'description' : [null, Validators.maxLength(255)],
             'zone': [null, Validators.maxLength(255)],
             'division': [null, Validators.maxLength(255)],
             'subDivision': [null, Validators.maxLength(255)], 
            
             
             
         });
       }
   
       applyFilter(filterValue: string) {
           filterValue = filterValue.trim(); // Remove whitespace
           filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
           this.facilityDataSource.filter = filterValue;
         }
   
         deleteFacility(id) {
           this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
               disableClose: false
             });
           this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected Functional unit?";
           this.confirmDialogRef.afterClosed().subscribe(result => {
               if(result){
                this.sendAndRequestService.requestForDELETE(Constants.app_urls.CONFIG.FACILITY.DELETE_FACILITY,id).subscribe((data) =>{
                
                         this.facilityResponse = data;
                       
                           if(this.facilityResponse.code == 200 && !!this.facilityResponse) {
                               this.commonService.showAlertMessage(this.facilityResponse.message);
                                this.getAllFacilityData();
                            } else {
                                this.commonService.showAlertMessage("Functional Unit Deletion Failed.");
                            }	
                       },error => {
                           console.log('ERROR >>>');
                             this.spinnerService.hide();
                             this.commonService.showAlertMessage("Functional Unit Deletion Failed.");
                       });
               }
           });
       }
     
         editFacility (id) {
           this.addFacility = true;
           this.FacilityEditAction(id);
           this.title = 'Update';
           this.enableZone=true;
           this.enableDivision=true;
           this.enableSubDivision=true;
       
       }
       FacilityEditAction(id: number) {
           this.facilityFormGroup = this.formBuilder.group({
               id: 0,
               'facilityId': [null,Validators.compose([Validators.required,Validators.maxLength(255)])],
               'facilityName': [null, Validators.compose([Validators.required,Validators.maxLength(255)])],
               'facilityTypeId': [null, Validators.maxLength(255)],
               'depotType' : [null, Validators.maxLength(255)],
               'parentDepot' : [null, Validators.maxLength(255)],
               'description' : [null, Validators.maxLength(255)],
               'zone': [null, Validators.maxLength(255)],
               'division': [null, Validators.maxLength(255)],
               'subDivision': [null, Validators.maxLength(255)], 
           });
           this.sendAndRequestService.requestForDELETE(Constants.app_urls.CONFIG.FACILITY.GET_FACILITY_ID,id).subscribe((responseData) =>{
           
               this.editFacilityResponse = responseData;
                
                 this.facilityFormGroup.patchValue
                 ({
                                   id: this.editFacilityResponse.id,
                                   facilityId:this.editFacilityResponse.facilityId,
                                   facilityName: this.editFacilityResponse.facilityName,
                                   facilityTypeId: this.editFacilityResponse.facilityTypeId,
                                   depotType:this.editFacilityResponse.depotType,
                                   parentDepot :this.editFacilityResponse.parentDepot,                                 
                                   description:this.editFacilityResponse.description,
                                   zone:this.editFacilityResponse.zone,
                                   division:this.editFacilityResponse.division,
                                   subDivision:this.editFacilityResponse.subDivision,
                                  
                    })
               
           } ,error => {})
           this.id=id;
           if (!isNaN(this.id)) {
               this.title = 'Update';
             } else {
               this.title = 'Save';      
             }
       }
         onFormValuesChanged() {
           for (const field in this.facilityErrors) {
             if (!this.facilityErrors.hasOwnProperty(field)) {
               continue;
             }
             // Clear previous errors
             this.facilityErrors[field] = {};
       
             // Get the control
             const control = this.facilityFormGroup.get(field);
             if (control && control.dirty && !control.valid) {
               this.facilityErrors[field] = control.errors;
             }
           }
         }
   
         duplicatefacilityName() {
           let facilityName: string = this.facilityFormGroup.controls['facilityName'].value;         
   
           const q = new Promise((resolve, reject) => {          
     
              this.sendAndRequestService.requestForGET(
                     Constants.app_urls.CONFIG.FACILITY.EXIST_FACILITYNAME+facilityName).subscribe
                     ((duplicate) => {
               if (duplicate) {
                 resolve({ 'duplicate': true });
               } else {
                 resolve(null);
               }
             }, () => { resolve({ 'duplicate': true }); });
           });
           return q;
         }
     
      depotType()
      {
          let depotType=this.facilityFormGroup.controls['depotType'].value;  

          if(depotType=='DIV')
          {
              this.enableFacilityId=false;
              this.enableFacilityName=false;
          }
          else{
            this.enableFacilityId=true;
            this.enableFacilityName=true;

          }


      }  
        
        
   }
   