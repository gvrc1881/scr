import { OnInit, Component, ViewChild } from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import { TssFeederModel } from 'src/app/models/tss-feeder.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FacilityModel } from 'src/app/models/facility.model';

@Component({
    selector: 'tss-feeder',
    templateUrl: './tss-feeder.component.html',
    styleUrls: []
})
export class TssFeederComponent implements OnInit{

    editPermission: boolean = true;
    addPermission: boolean = true;
    deletePermission: boolean = true;
    addTssFeeder:boolean;
    tssFeederFormGroup:FormGroup;
    id: number = 0;
    title: string = "Save";
    feederList:any;    
    editFeederResponse: any;  
    feederErrors : any;
    saveFeeder:boolean;
    value:string;
    pattern = "^[A-Za-z]+$";
    tssFeederDataSource: MatTableDataSource<TssFeederModel>;
    feederDisplayColumns = ['sno','division','feederName','description','stateElectricityBoard','id'] ;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    feederResponse:any;
    loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
    userHierarchy:any = JSON.parse(localStorage.getItem('userHierarchy'));
    zoneList: FacilityModel [] = [];
    divisionList:  FacilityModel [] = [];
    enableZone: boolean ;
	enableDivision: boolean;

    constructor( 
        private formBuilder: FormBuilder,
        private commonService: CommonService,
        private spinnerService: Ng4LoadingSpinnerService,
        private sendAndRequestService:SendAndRequestService,
        public dialog: MatDialog
        ){
                this.feederErrors = {
                    feederName: {},
                  
            
                };
            
    }

    ngOnInit () {

        var permissionName = this.commonService.getPermissionNameByLoggedData("TRD CONFIG","Tss Feeder") ;
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
    
        this.getAllFeedersData(); 
        this.displayHierarchyFields();  
    }
    
    tssFeederSubmit(){ 
        let dataDiv:String=this.tssFeederFormGroup.value.dataDiv;
        let feederName:String=this.tssFeederFormGroup.value.feederName;
        let description:String=this.tssFeederFormGroup.value.description;
        let stateElectricityBoard:String=this.tssFeederFormGroup.value.stateElectricityBoard;
              
   
       this.addTssFeeder=false;
       if (this.title ==  Constants.EVENTS.SAVE) {
        var saveFeederModel={
                                'dataDiv':dataDiv,
                                'feederName': feederName,
                                'description':description,
                                'stateElectricityBoard':stateElectricityBoard,
                                "createdBy" : this.loggedUserData.username
        }              
        this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.TSS_FEEDER.SAVE_FEEDER,saveFeederModel , false).subscribe(data => {
            this.feederResponse = data;
            if(this.feederResponse.code == 200 && !!this.feederResponse) {
                this.commonService.showAlertMessage(this.feederResponse.message);
                this.getAllFeedersData();
                this.tssFeederFormGroup.reset();
            }else {
                this.commonService.showAlertMessage("Feeders Data Saving Failed.");
            }
            this.spinnerService.hide();
        } , error => {
            console.log('ERROR >>>');
            this.spinnerService.hide();
            this.commonService.showAlertMessage("Feeders Data Saving Failed.");
        });
    }

      
        else if (this.title == Constants.EVENTS.UPDATE )
         {
            let id: number = this.editFeederResponse.id;
            var updateFeederModel={
                                        'id':id,
                                        'dataDiv':dataDiv,
                                        'feederName': feederName,
                                        'description':description,
                                        'stateElectricityBoard':stateElectricityBoard,
                                        "updatedBy" : this.loggedUserData.username
                                     }    
                this.sendAndRequestService.requestForPUT(Constants.app_urls.ENERGY_BILL_PAYMENTS.TSS_FEEDER.UPDATE_FEEDER,updateFeederModel, false).
                subscribe(data => {
                                    this.feederResponse = data;
                                    if(this.feederResponse.code == 200 && !!this.feederResponse)
                                    {
                                        this.commonService.showAlertMessage(this.feederResponse.message);
                                        this.getAllFeedersData();
                                        this.tssFeederFormGroup.reset();
                                        this.addTssFeeder =  false;
                                        this.title = "Save";
                                        this.displayHierarchyFields();
                                    }
                                    else {
                                            this.commonService.showAlertMessage("Feeders Data Updating Failed.");
                                        }
                                } , 
                                error => {
            	
                                        this.spinnerService.hide();
                                        this.commonService.showAlertMessage("Feeder Data Updating Failed.");
                                        })
                                        
        }
    }

    getAllFeedersData() {

            const feeder : TssFeederModel[] = [];
            this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.TSS_FEEDER.GET_FEEDERS).subscribe((data) => {
                this.feederList = data;
                for (let i = 0; i < this.feederList.length; i++) {
                    this.feederList[i].sno = i+1;
                    feeder.push(this.feederList[i]);              
                }
                this.tssFeederDataSource = new MatTableDataSource(feeder);
                this.tssFeederDataSource.paginator = this.paginator;
                this.tssFeederDataSource.sort = this.sort;
                this.spinnerService.hide();
            } , error => {
                this.spinnerService.hide();
            });
    
        }
    
    onGoBack() {
        this.tssFeederFormGroup.reset();
        this.addTssFeeder = false;
        this.title = 'Save';
        this.displayHierarchyFields();
    }


    NewTssFeeder () 
    {
        this.addTssFeeder = true;
    
        this.tssFeederFormGroup = this.formBuilder.group({
            id: 0,
            'zone' : [null],
            'dataDiv' : [null,Validators.compose([Validators.required,Validators.maxLength(255)]) ],
            'feederName' : [null,Validators.compose([Validators.required,Validators.maxLength(255)]) , this.duplicateFeederName.bind(this)],
            'description': [null,Validators.maxLength(255)],
            'stateElectricityBoard':[null]
        });
    }
        
    

    applyFilter(filterValue: string) {
        filterValue = filterValue.trim(); // Remove whitespace
        filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
        this.tssFeederDataSource.filter = filterValue;
      }

      deleteTssFeeder (id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected Feeder?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
                this.sendAndRequestService.requestForDELETE(Constants.app_urls.ENERGY_BILL_PAYMENTS.TSS_FEEDER.DELETE_FEEDER, id).subscribe(data => {
                    	this.feederResponse = data;
            			if(this.feederResponse.code == 200 && !!this.feederResponse) {
                        	this.commonService.showAlertMessage(this.feederResponse.message);
                         	this.getAllFeedersData();
                         } else {
                         	this.commonService.showAlertMessage("feeder Deletion Failed.");
                         }	
                    },error => {
                    	console.log('ERROR >>>');
          				this.spinnerService.hide();
          				this.commonService.showAlertMessage("feeder Deletion Failed.");
                    });
            }
        });
    }
    
    editTssFeeder (id) {
        this.addTssFeeder = true;
        this.feederEditAction(id);
        this.title = 'Update';
    
    }
    feederEditAction(id: number) {
        this.tssFeederFormGroup = this.formBuilder.group({
            id: 0,
            'dataDiv' : [null,Validators.maxLength(255)],
            'feederName' : [null,Validators.maxLength(255)],
            'description': [null,Validators.maxLength(255)],
            'stateElectricityBoard':[null]
        });
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.TSS_FEEDER.GET_FEEDER_ID+id).subscribe((responseData) => {
            this.editFeederResponse = responseData;
             
              this.tssFeederFormGroup.patchValue
              ({
                                id: this.editFeederResponse.id,
                                dataDiv:this.editFeederResponse.dataDiv,
                                feederName: this.editFeederResponse.feederName,
                                description: this.editFeederResponse.description,
                                stateElectricityBoard:this.editFeederResponse.stateElectricityBoard
                 })
                 this.displayHierarchyFields();
            
        } ,error => {})
        this.id=id;
        if (!isNaN(this.id)) {
            this.title = 'Update';
          } else {
            this.title = 'Save';      
          }
    }
      onFormValuesChanged() {
        for (const field in this.feederErrors) {
          if (!this.feederErrors.hasOwnProperty(field)) {
            continue;
          }
          // Clear previous errors
          this.feederErrors[field] = {};
    
          // Get the control
          const control = this.tssFeederFormGroup.get(field);
          if (control && control.dirty && !control.valid) {
            this.feederErrors[field] = control.errors;
          }
        }
      }
      duplicateFeederName() {
       let feederName:string= this.tssFeederFormGroup.controls['feederName'].value 
    	const q = new Promise((resolve, reject) => {
	       this.sendAndRequestService.requestForGET(
                Constants.app_urls.MASTERS.MEASURE_ACTIVITY.EXISTS_ACTIVITY_ID +feederName
	           
	      ).subscribe((duplicate) => {
	        if (duplicate) {
	          resolve({ 'duplicate': true });
	        } else {
	          resolve(null);
	        }
	      }, () => { resolve({ 'duplicate': true }); });
	    });
      return q;
      
    }

    displayHierarchyFields(){
        this.zoneList = [];
        this.divisionList = [];
       
        for (let i = 0; i < this.userHierarchy.length; i++) {
               if(this.userHierarchy[i].depotType == 'ZONE'){
                   this.zoneList.push(this.userHierarchy[i]);
                   this.enableZone = true;
               }
            }
           
       
    }
    
findDivisions(){
    let zone: string = this.tssFeederFormGroup.value.zone;
    this.divisionList=[];

    for (let i = 0; i < this.userHierarchy.length; i++) {
        
           if(this.userHierarchy[i].zone == zone && this.userHierarchy[i].depotType == 'DIV'){
           
               this.divisionList.push(this.userHierarchy[i]);
               this.enableDivision = true;
           }
        }
}

   

    
    

}

      
     



    
    
    

   
	
	

   