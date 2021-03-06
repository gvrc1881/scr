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
import { DatePipe } from '@angular/common';
import { DataViewDialogComponent } from 'src/app/components/data-view-dialog/data-view-dialog.component';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';



@Component({
    selector: 'tss-feeder',
    templateUrl: './tss-feeder.component.html',
    styleUrls: []
})
export class TssFeederComponent implements OnInit{

  pagination = Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
    editPermission: boolean = true;
    addPermission: boolean = true;
    deletePermission: boolean = true;
    addTssFeeder:boolean;
    tssFeederFormGroup:FormGroup;
    id: number = 0;
    title: string= Constants.EVENTS.ADD;
    feederList:any;    
    editFeederResponse: any;  
    feederErrors : any;
    saveFeeder:boolean;
    stateElectricityBoardList:any;
    value:string;
    pattern = "^[A-Za-z]+$";
    tssFeederDataSource: MatTableDataSource<TssFeederModel>;
    feederDisplayColumns = ['sno','division','feederName','description','stateElectricityBoard','id'] ;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
    feederResponse:any;
    loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
    userHierarchy:any = JSON.parse(sessionStorage.getItem('userHierarchy'));
    zoneHierarchy:any = JSON.parse(sessionStorage.getItem('zoneData'));
    divisionHierarchy:any = JSON.parse(sessionStorage.getItem('divisionData'));  
    facilityHierarchy:any = JSON.parse(sessionStorage.getItem('depotData')); 
    zoneList: FacilityModel [] = [];
    divisionList:  FacilityModel [] = [];
    facilityList:any;
    standByFeederNameList:any;
    enableZone: boolean ;
  enableDivision: boolean;
  enableDepot:boolean;

    constructor( 
        private formBuilder: FormBuilder,
        private commonService: CommonService,
        private spinnerService: Ng4LoadingSpinnerService,
        private sendAndRequestService:SendAndRequestService,
        public dialog: MatDialog,
        private datePipe: DatePipe,
        ){
                this.feederErrors = {
                    feederName: {},
                    tssName:{},
                    standByFeederName:{}
                  
            
                };
            
    }

    ngOnInit () {

        var permissionName = this.commonService.getPermissionNameByLoggedData("TRD CONFIG","Tss Feeder") ;
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
       
        this.getAllFeedersData(); 
        this.displayHierarchyFields();  
        this.findBoardsList();
    }
    findBoardsList(){
      this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_STATUS_ITEM + Constants.STATUS_ITEMS.STATE_ELECTRICITY_BOARD)
      .subscribe((resp) => {
        this.stateElectricityBoardList = resp;
      });
    }
    tssFeederSubmit(){ 
        let dataDiv:String=this.tssFeederFormGroup.value.dataDiv;
        let feederName:String=this.tssFeederFormGroup.value.feederName;
        let tssName:String =this.tssFeederFormGroup.value.tssName;
        let description:String=this.tssFeederFormGroup.value.description;
        let standByFeederName:String = this.tssFeederFormGroup.value.standByFeederName;
        let stateElectricityBoard:String=this.tssFeederFormGroup.value.stateElectricityBoard;
              
   
       this.addTssFeeder=false;
       if (this.title ==  Constants.EVENTS.ADD) {
        var saveFeederModel={
                                
                                'dataDiv':dataDiv,
                                'feederName': feederName,
                                'tssName': tssName,
                                'description':description,
                                'standByFeederName': standByFeederName,
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
                                        'tssName': tssName,
                                        'description':description,
                                        'standByFeederName':standByFeederName,
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
                                        this.title = Constants.EVENTS.ADD;
                                        
                                    }
                                    else {
                                            this.commonService.showAlertMessage("Feeders Data Updating Failed.");
                                        }
                                        this.displayHierarchyFields();
                                } , 
                                error => {
            	
                                        this.spinnerService.hide();
                                        this.commonService.showAlertMessage("Feeder Data Updating Failed.");
                                        })
                                        
        }
    }

    getAllFeedersData() {

            const feeder : TssFeederModel[] = [];
            this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.TSS_FEEDER.GET_FEEDERS_BASEDON_DIVISION+this.loggedUserData.username).subscribe((data) => {
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
        this.title = Constants.EVENTS.ADD;
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
            'tssName' :[null],
            'standByFeederName':[null],
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
        this.title = Constants.EVENTS.UPDATE;
    
    }
    feederEditAction(id: number) {
        this.tssFeederFormGroup = this.formBuilder.group({
            id: 0,
            'zone' : [null],
            'dataDiv' : [null,Validators.compose([Validators.required,Validators.maxLength(255)])],
            'feederName' : [null,Validators.compose([Validators.required,Validators.maxLength(255)]) , this.duplicateFeederNameAndId.bind(this)],
            'description': [null,Validators.maxLength(255)],
            'tssName' : [null],
            'standByFeederName':[null],
            'stateElectricityBoard':[null]
        });
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.TSS_FEEDER.GET_FEEDER_ID+id).subscribe((responseData) => {
            this.editFeederResponse = responseData;           
             
            this.displayHierarchyFields();
              this.tssFeederFormGroup.patchValue
              ({
                                id: this.editFeederResponse.id,
                                dataDiv:this.editFeederResponse.dataDiv,
                                feederName: this.editFeederResponse.feederName,
                                tssName : this.editFeederResponse.tssName,
                                standByFeederName :this.editFeederResponse.standByFeederName,
                                description: this.editFeederResponse.description,
                                stateElectricityBoard:this.editFeederResponse.stateElectricityBoard
                 })
                 
                 
        } ,error => {})
        this.id=id;
        if (!isNaN(this.id)) {
            this.title = Constants.EVENTS.UPDATE;
          } else {
            this.title = Constants.EVENTS.ADD;      
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
                Constants.app_urls.ENERGY_BILL_PAYMENTS.TSS_FEEDER.EXIST_FEEDER_NAME +feederName
	           
	      ).subscribe((duplicate) => {
	        if (duplicate) {
	          resolve({ 'duplicateFeederName': true });
	        } else {
	          resolve(null);
	        }
	      }, () => { resolve({ 'duplicateFeederName': true }); });
	    });
      return q;
      
    }

    duplicateFeederNameAndId() {
        let id=this.id;
        let feederName:string= this.tssFeederFormGroup.controls['feederName'].value 
         const q = new Promise((resolve, reject) => {
            this.sendAndRequestService.requestForGET(
                 Constants.app_urls.ENERGY_BILL_PAYMENTS.TSS_FEEDER.EXIST_FEEDER_NAME_AND_ID+id+'/' +feederName
                
           ).subscribe((duplicate) => {
             if (duplicate) {
               resolve({ 'duplicateFeederNameAndId': true });
             } else {
               resolve(null);
             }
           }, () => { resolve({ 'duplicateFeederNameAndId': true }); });
         });
       return q;
       
     }

    displayHierarchyFields(){
     
        this.zoneList = [];
        this.divisionList = [];
        this.facilityList =[];
        //this.findFacility();

        this.zoneHierarchy.zoneList;
        this.enableZone = true;
       
        if(!this.enableZone){
         
          this.enableDivision = true;
          this.enableDepot = true;
          this.enableZone=false;
        }  
        if(this.zoneHierarchy.length>0)
        {
          
          this.enableZone=true;
          this.enableDivision=false;
          this.enableDepot = false;


        } if(this.divisionHierarchy.length>0){
          this.enableZone=false;
          this.enableDivision=true;
          this.enableDepot = true;
        }
        else{

          this.enableDepot = true;
          this.enableZone=false;
          this.enableDivision=false;
         
        }
    
       
    }
    
findDivisions(){
    let zone: string = this.tssFeederFormGroup.value.zone;
    this.divisionList=[];    
    
    for (let i = 0; i < this.divisionHierarchy.length; i++) {
        
           if(this.divisionHierarchy[i].zone == zone&& this.divisionHierarchy[i].depotType == 'DIV'){
           
               //this.divisionList.push(this.divisionHierarchy[i]);
               this.divisionHierarchy.divisionList;
               this.enableDivision = true;
           }
        }
}

findDepots(){
 
  this.facilityList=[]; 
  let division: string = this.tssFeederFormGroup.controls['dataDiv'].value;
  for (let i = 0; i < this.facilityHierarchy.length; i++) {
        
    if(this.facilityHierarchy[i].division == division && this.facilityHierarchy[i].depotType == 'TSS'){
       
       this.facilityList.push(this.facilityHierarchy[i]);
      
       this.enableDepot = true;
        
    }
 } 
}
findFacility(){
  this.facilityList=[]; 
  
  for (let i = 0; i < this.facilityHierarchy.length; i++) {
        
    if(this.facilityHierarchy[i].depotType == 'TSS'){
       
       this.facilityList.push(this.facilityHierarchy[i]);
      
       this.enableDepot = true;
        
    }
 } 
}

findStandByFeeders(){
  let tssName: string = this.tssFeederFormGroup.controls['tssName'].value;
  this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.TSS_FEEDER.GET_STANDBY_FEEDER+tssName)
  .subscribe((data) => {
    console.log("list==="+JSON.stringify(data))
    this.standByFeederNameList = data;
  })
}

ViewData(data){
  var result = {
    'title':this.Titles.TSS_FEEDER_MASTER,
    'dataSource':[
      
    { label:FieldLabelsConstant.LABELS.DIVISION, value:data.dataDiv },
    { label:FieldLabelsConstant.LABELS.FEEDER, value:data.feederName },
    { label:FieldLabelsConstant.LABELS.STATE_ELECTRICITY_BOARD, value:data.stateElectricityBoard },
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

      
     



    
    
    

   
	
	

   