import { OnInit, Component, ViewChild, ElementRef} from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { FacilityModel } from 'src/app/models/facility.model';
import { Constants } from 'src/app/common/constants';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { DatePipe } from '@angular/common';
import { DataViewDialogComponent } from 'src/app/components/data-view-dialog/data-view-dialog.component';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';


 


@Component({
    selector: 'facility',
    templateUrl: './facility.component.html',
    styleUrls: []
})

export class FacilityComponent implements OnInit{

  pagination = Constants.PAGINATION_NUMBERS;
    FiledLabels = FieldLabelsConstant.LABELS;
    Titles = FieldLabelsConstant.TITLE;
    addFacility:boolean;
    editPermission: boolean = true;
    addPermission: boolean = true;
    deletePermission: boolean = true;
    facilityFormGroup:FormGroup;
    id: number = 0;
    title: string = Constants.EVENTS.ADD;
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
    dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
    facilityResponse:any;
    loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
    userHierarchy:any = JSON.parse(sessionStorage.getItem('userHierarchy'));
    zoneList:any;
    divisionsList:any;
    subDivisionList:any;
    enableFacilityId:any;
    enableFacilityName:any;
    zone: any;
    division:any;
    enableZone:boolean;
    enableDivision:boolean;
    enableSubDivision:boolean;   
   

    constructor( 
        private formBuilder: FormBuilder,
        private commonService: CommonService,
        private spinnerService: Ng4LoadingSpinnerService,
        private sendAndRequestService:SendAndRequestService,
        public dialog: MatDialog,
        private datePipe: DatePipe,
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
               // this.findZones();
               
              
               
        var permissionName = this.commonService.getPermissionNameByLoggedData("TRD CONFIG","FUNCTIONAL UNIT") ;
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
    
        this.getAllFacilityData(); 
        this.displayHierarchyFields();  
        
    }
   
  //   findZones()
  //   {
  //       this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_ZONE_LIST).subscribe((data) => {
  //           this.zoneList = data;
  //  }
  //         ); 

  //   }
  //   getDivisions(){
  //       this.zone =this.facilityFormGroup.controls['zone'].value; 
            
  //   	this.sendAndRequestService.requestForPOST(Constants.app_urls.REPORTS.GET_DIVISION_BASED_ON_ZONE, this.zone, false).subscribe((data) => {
           
  //       this.divisionsList = data;
                 
  //               });
              
  //   }
    

  //   getSubDivisions(){
       
  //     this.division=this.facilityFormGroup.controls['division'].value; 
  //       this.sendAndRequestService.requestForPOST(Constants.app_urls.REPORTS.GET_SUBDIVISION_BASED_ON_DIVISION ,this.division, false).subscribe((data) => {
  //           this.subDivisionList = data;
  //            });
        

  //   }
    facilitySubmit(){     

            let facilityId: string=this.facilityFormGroup.value.facilityId;
           let facilityName: string=this.facilityFormGroup.value.facilityName;
           let facilityTypeId: string=this.facilityFormGroup.value.facilityTypeId;
           let depotType: string=this.facilityFormGroup.value.depotType;
           let parentDepot: string=this.facilityFormGroup.value.parentDepot;
           let description: string=this.facilityFormGroup.value.description;
           let zone: string=this.facilityFormGroup.value.zone;
           let division: string=this.facilityFormGroup.value.division;
           let subDivision: string=this.facilityFormGroup.value.subDivision;

          
   
           if (this.title ==  Constants.EVENTS.ADD) {
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
                                               this.displayHierarchyFields();
                                               
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
           this.title = Constants.EVENTS.ADD;
           this.enableDivision = false;
        this.enableSubDivision = false;
        
           this.displayHierarchyFields();
       }
   
   
       NewFacility(){
           this.addFacility = true;
          
           this.facilityFormGroup = this.formBuilder.group({
             id: 0,
             'facilityId': [null,Validators.compose([Validators.required,Validators.maxLength(255)]),this.duplicatefacilityId.bind(this)],
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
           this.title = Constants.EVENTS.UPDATE;
           this.enableZone=false;
           this.enableDivision=false;
           this.enableSubDivision=false;
       
       }
       FacilityEditAction(id: number) {
           this.facilityFormGroup = this.formBuilder.group({
               id: 0,
               'facilityId': [null,Validators.compose([Validators.required,Validators.maxLength(255)]),this.duplicatefacilityIdAndId.bind(this)],
               'facilityName': [null, Validators.compose([Validators.required,Validators.maxLength(255)]),this.duplicatefacilityNameAndId.bind(this)],
               'facilityTypeId': [null, Validators.maxLength(255)],
               'depotType' : [null, Validators.maxLength(255)],
               'parentDepot' : [null, Validators.maxLength(255)],
               'description' : [null, Validators.maxLength(255)],
               'zone': [null, Validators.maxLength(255)],
               'division': [null, Validators.maxLength(255)],
               'subDivision': [null, Validators.maxLength(255)], 
           });
           this.sendAndRequestService.requestForGET(Constants.app_urls.CONFIG.FACILITY.GET_FACILITY_ID+id).subscribe((responseData) =>{
           
               this.editFacilityResponse = responseData;
                console.log("fac=="+this.editFacilityResponse);
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
           this.displayHierarchyFields();
           this.findDivisions();
           this.findSubDivisions();
           if (!isNaN(this.id)) {
               this.title = Constants.EVENTS.UPDATE;
             } else {
               this.title = Constants.EVENTS.ADD;      
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
                 resolve({ 'duplicatefacilityName': true });
               } else {
                 resolve(null);
               }
             }, () => { resolve({ 'duplicatefacilityName': true }); });
           });
           return q;
         }

         duplicatefacilityId() {
          let facilityId: string = this.facilityFormGroup.controls['facilityId'].value;         
  
          const q = new Promise((resolve, reject) => {          
    
             this.sendAndRequestService.requestForGET(
                    Constants.app_urls.CONFIG.FACILITY.EXIST_FACILITYID+facilityId).subscribe
                    ((duplicate) => {
              if (duplicate) {
                resolve({ 'duplicatefacilityId': true });
              } else {
                resolve(null);
              }
            }, () => { resolve({ 'duplicatefacilityId': true }); });
          });
          return q;
        }

        duplicatefacilityNameAndId() {
          let id=this.id;
          let facilityName: string = this.facilityFormGroup.controls['facilityName'].value;         
  
          const q = new Promise((resolve, reject) => {          
    
             this.sendAndRequestService.requestForGET(
                    Constants.app_urls.CONFIG.FACILITY.EXIST_FACILITYNAME_AND_ID+id+'/'+facilityName).subscribe
                    ((duplicate) => {
              if (duplicate) {
                resolve({ 'duplicatefacilityNameAndId': true });
              } else {
                resolve(null);
              }
            }, () => { resolve({ 'duplicatefacilityNameAndId': true }); });
          });
          return q;
        }

        duplicatefacilityIdAndId() {

          let id=this.id;
         let facilityId: string = this.facilityFormGroup.controls['facilityId'].value;         
 
         const q = new Promise((resolve, reject) => {          
   
            this.sendAndRequestService.requestForGET(
                   Constants.app_urls.CONFIG.FACILITY.EXIST_FACILITYID_AND_ID+id+'/'+facilityId).subscribe
                   ((duplicate) => {
             if (duplicate) {
               resolve({ 'duplicatefacilityIdAndId': true });
             } else {
               resolve(null);
             }
           }, () => { resolve({ 'duplicatefacilityIdAndId': true }); });
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

      displayHierarchyFields(){
        this.zoneList = [];
        this.divisionsList = [];
        this.subDivisionList = [];
        
        for (let i = 0; i < this.userHierarchy.length; i++) {
                 if(this.userHierarchy[i].depotType == 'ZONE'){
                   this.zoneList.push(this.userHierarchy[i]);
                   this.enableZone = true;
                 }
              }
            }

       findDivisions(){
        let zone: string = this.facilityFormGroup.value.zone;
        for (let i = 0; i < this.userHierarchy.length; i++) {
                 if(this.userHierarchy[i].zone == zone && this.userHierarchy[i].depotType == 'DIV'){
                   this.divisionsList.push(this.userHierarchy[i]);
                   this.enableDivision = true;
                 }
                
              }
      }
      
      findSubDivisions(){
        let division: string = this.facilityFormGroup.value.division;
        for (let i = 0; i < this.userHierarchy.length; i++) {
                 if(this.userHierarchy[i].division == division && this.userHierarchy[i].depotType == 'SUB_DIV'){
                   this.subDivisionList.push(this.userHierarchy[i]);
                   this.enableSubDivision = true;
                 }
                
              }
      }
        
      ViewData(data){
        var result = {
          'title':this.Titles.FUNCTIONAL_UNIT,
          'dataSource':[
        
          { label:FieldLabelsConstant.LABELS.ZONE, value:data.zone },
          { label:FieldLabelsConstant.LABELS.DIVISION, value:data.division },
          { label:FieldLabelsConstant.LABELS.SUB_DIV, value:data.subDivision },
          { label:FieldLabelsConstant.LABELS.FUNCTIONAL_UNIT_ID, value:data.facilityId },
          { label:FieldLabelsConstant.LABELS.FUNCTIONAL_UNIT_NAME, value:data.facilityName },
          { label:FieldLabelsConstant.LABELS.FACILITY_TYPE_ID, value:data.facilityTypeId },
          { label:FieldLabelsConstant.LABELS.FUNCTIONAL_TYPE, value:data.depotType },
          { label:FieldLabelsConstant.LABELS.PARENT_FUN_UNIT, value:data.parentDepot },
          { label:FieldLabelsConstant.LABELS.DESCRIPTION, value:data.description }
          
       
        ]
        }
        this.dataViewDialogRef = this.dialog.open(DataViewDialogComponent, {
          disableClose: false,
          height: '400px',
          width: '80%',       
          data:result,  
        });            
      }
        
   }
   