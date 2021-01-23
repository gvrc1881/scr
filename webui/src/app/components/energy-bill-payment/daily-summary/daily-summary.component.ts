import { OnInit, Component, ViewChild } from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import { DailySummaryModel } from 'src/app/models/daily-summary.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog,DateAdapter, MAT_DATE_FORMATS} from '@angular/material';
import { FacilityModel } from 'src/app/models/facility.model';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { DataViewDialogComponent } from '../../data-view-dialog/data-view-dialog.component';
import { DatePipe } from '@angular/common';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';



@Component({
    selector: 'daily-summary',
    templateUrl: './daily-summary.component.html',
    providers: [
        {
            provide: DateAdapter, useClass: AppDateAdapter
        },
        {
            provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
        }
        ]
})
export class DailySummaryComponent implements OnInit{

    pagination = Constants.PAGINATION_NUMBERS;
    FiledLabels = FieldLabelsConstant.LABELS;
    Titles = FieldLabelsConstant.TITLE;
    addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    addDailySummary: boolean ;
    title: string =  Constants.EVENTS.ADD;
    dailySummaryFormGroup: FormGroup;
    id: number = 0;
    dailySummaryList : any;
    facilityData: any;
    today=new Date();
    dailySummaryDataSource: MatTableDataSource<DailySummaryModel>;
    dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
    dailySummaryDisplayColumns = ['sno' , 'createdDate' , 'facilityId' , 'nameOfStaff' , 'dayProgress' , 'npbProgress' , 'psiProgress' , 'tomorrowForecast',
    'footPatrolling', 'footInspection', 'footPlateInspection', 'supervisor', 'staffStrength', 'powerBlock','nonPowerBlock','remarks','id' ] ;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    editDailySummaryResponse: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    userHierarchy:any = JSON.parse(sessionStorage.getItem('userHierarchy'));
    zoneList: FacilityModel [] = [];
    divisionList:  FacilityModel [] = [];
    subDivList:  FacilityModel [] = [];
    facilityList: FacilityModel [] = [];
	enableZone: boolean ;
	enableDivision: boolean;
	enableSubDiv: boolean;
	enableDepot: boolean;
	enableDepotLevel: boolean;
	disableFacility: boolean;


    constructor(
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private dialog: MatDialog,
        private datePipe: DatePipe,
        private sendAndRequestService:SendAndRequestService

    ){

    }

    ngOnInit () {
        this.getAllDailySummaryData();
        var permissionName = this.commonService.getPermissionNameByLoggedData("ENERGY BILL PAYMENT","Daily Summary") ;//p == 0 ? 'No Permission' : p[0].permissionName;
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName); 
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
        this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
        
        this.displayHierarchyFields();
    }
    
    displayHierarchyFields(){
    	this.zoneList = [];
    	this.divisionList = [];
    	this.subDivList = [];
    	this.facilityList = [];
    	for (let i = 0; i < this.userHierarchy.length; i++) {
               if(this.userHierarchy[i].depotType == 'ZONE'){
               	this.zoneList.push(this.userHierarchy[i]);
               	this.enableZone = true;
               }
            }
        if(!this.enableZone){
        	this.depotTypeOheAndPsi();
        	this.enableDepotLevel = true;
        }    
    }
    
    duplicateFromDate() {
    	const q = new Promise((resolve, reject) => {
	       this.sendAndRequestService.requestForGET(
                Constants.app_urls.DAILY_SUMMARY.DAILY_SUMMARY.EXISTS_FACILITY_ID_CREATED_DATE +
	            this.dailySummaryFormGroup.controls['facilityId'].value + '/'+
	            this.dailySummaryFormGroup.controls['createdDate'].value
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
      duplicateFacilityCreatedDateAndId() {
        let id=this.id;
        let facilityId: string = this.dailySummaryFormGroup.controls['facilityId'].value;
        let createdDate: string = this.dailySummaryFormGroup.controls['createdDate'].value;

        const q = new Promise((resolve, reject) => {          
  
           this.sendAndRequestService.requestForGET(
                  Constants.app_urls.DAILY_SUMMARY.DAILY_SUMMARY.EXISTS_FACILITY_ID_CREATED_DATE_AND_ID+id+'/'+facilityId+'/'+createdDate).subscribe
                  ((duplicate) => {
            if (duplicate) {
              resolve({ 'duplicateFacilityCreatedDateAndId': true });
            } else {
              resolve(null);
            }
          }, () => { resolve({ 'duplicateFacilityCreatedDateAndId': true }); });
        });
        return q;
      }
      public get f() { return this.dailySummaryFormGroup.controls; }

    getAllDailySummaryData() {
        const dailyProgressSummery : DailySummaryModel[] = [];
        this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.DAILY_SUMMARY.GET_DAILY_SUMMARY).subscribe((data) => {
            this.dailySummaryList = data;
            for (let i = 0; i < this.dailySummaryList.length; i++) {
                this.dailySummaryList[i].sno = i+1;
                this.dailySummaryList[i].createdDate = this.datePipe.transform(this.dailySummaryList[i].createdDate, 'dd-MM-yyyy');
                dailyProgressSummery.push(this.dailySummaryList[i]);              
            }
            this.dailySummaryDataSource = new MatTableDataSource(dailyProgressSummery);
            this.dailySummaryDataSource.paginator = this.paginator;
            this.dailySummaryDataSource.sort = this.sort;

        } , error => {});

    }

    dailySummarySubmit () {
        let createdDate: Date = this.dailySummaryFormGroup.value.createdDate;
        let facilityId: string = this.dailySummaryFormGroup.value.facilityId;
        let nameOfStaff: string = this.dailySummaryFormGroup.value.nameOfStaff;
        let dayProgress: string = this.dailySummaryFormGroup.value.dayProgress;
        let npbProgress: string = this.dailySummaryFormGroup.value.npbProgress;
        let psiProgress: string = this.dailySummaryFormGroup.value.psiProgress;
        let tomorrowForecast: string = this.dailySummaryFormGroup.value.tomorrowForecast;
        let footPatrolling: string = this.dailySummaryFormGroup.value.footPatrolling;
        let footInspection: string = this.dailySummaryFormGroup.value.footInspection;
        let footPlateInspection: string = this.dailySummaryFormGroup.value.footPlateInspection;
        let supervisor: string = this.dailySummaryFormGroup.value.supervisor;
        let staffStrength: string = this.dailySummaryFormGroup.value.staffStrength;
        let powerBlock: string = this.dailySummaryFormGroup.value.powerBlock;
        let nonPowerBlock: string = this.dailySummaryFormGroup.value.nonPowerBlock;
        let remarks: string = this.dailySummaryFormGroup.value.remarks;
        this.addDailySummary = false;
        
        if (this.title ==   Constants.EVENTS.ADD) {
            var saveDailySummaryModel={
                'createdDate':createdDate,
                'facilityId':facilityId,
                'nameOfStaff':nameOfStaff,
                'dayProgress':dayProgress,
                'npbProgress':npbProgress,
                'psiProgress':psiProgress,
                'tomorrowForecast':tomorrowForecast,
                'footPatrolling':footPatrolling,
                'footInspection':footInspection,
                'footPlateInspection':footPlateInspection,
                'supervisor':supervisor,
                'staffStrength':staffStrength,
                'powerBlock':powerBlock,
                'nonPowerBlock':nonPowerBlock,
                'remarks':remarks
            }
            this.sendAndRequestService.requestForPOST(Constants.app_urls.DAILY_SUMMARY.DAILY_SUMMARY.SAVE_DAILY_SUMMARY, saveDailySummaryModel, false).subscribe(response => {
                this.commonService.showAlertMessage('Successfully saved');
                this.getAllDailySummaryData();
                this.dailySummaryFormGroup.reset();
            } , error => {});
        }else if (this.title == Constants.EVENTS.UPDATE ) {
            let id: number = this.editDailySummaryResponse.id;
            var updateDailySummaryModel={
                'id':id,
                'createdDate':createdDate,
                'facilityId':facilityId,
                'nameOfStaff':nameOfStaff,
                'dayProgress':dayProgress,
                'npbProgress':npbProgress,
                'psiProgress':psiProgress,
                'tomorrowForecast':tomorrowForecast,
                'footPatrolling':footPatrolling,
                'footInspection':footInspection,
                'footPlateInspection':footPlateInspection,
                'supervisor':supervisor,
                'staffStrength':staffStrength,
                'powerBlock':powerBlock,
                'nonPowerBlock':nonPowerBlock,
                'remarks':remarks,
            }
            this.sendAndRequestService.requestForPUT(Constants.app_urls.DAILY_SUMMARY.DAILY_SUMMARY.UPDATE_DAILY_SUMMARY,updateDailySummaryModel, false).subscribe(response => {
                this.commonService.showAlertMessage('Successfully updated');
                this.getAllDailySummaryData();
                this.dailySummaryFormGroup.reset();
                this.addDailySummary =  false;
                this.displayHierarchyFields();
            } , error => {})
            
        }
    }
    
    editDailySummaryItem (id) {
        this.addDailySummary = true;
        this.dailySummaryEditAction(id);
        this.title =  Constants.EVENTS.UPDATE;
        this.enableZone = false;
        this.enableDivision = false;
        this.enableSubDiv = false;
        this.enableDepot = false;
        this.disableFacility = true;
        this.enableDepotLevel = false;
    }

    dailySummaryEditAction(id: number) {
        this.dailySummaryFormGroup = this.formBuilder.group({
            id: 0,
            'createdDate':[null, Validators.required, this.duplicateFacilityCreatedDateAndId.bind(this)],
            'facilityId':[null, Validators.required],
            'nameOfStaff':[null,Validators.maxLength(250)],
            'dayProgress': [null],
            'npbProgress': [null],
            'psiProgress': [null],
            'tomorrowForecast' : [null],
            'footPatrolling' : [null],
            'footInspection' : [null],
            'footPlateInspection' : [null],
            'supervisor' : [null],
            'staffStrength' : [null],
            'powerBlock' : [null],
            'nonPowerBlock' : [null],
            'remarks' : [null,Validators.maxLength(250)],
            'zone' : [null],
            'division' : [null],
            'subDiv' : [null],
        });
        this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.DAILY_SUMMARY.GET_DAILY_SUMMARY_ID+id).subscribe((responseData) => {
            this.editDailySummaryResponse = responseData;
            this.dailySummaryFormGroup.patchValue({
                id: this.editDailySummaryResponse.id,
                createdDate:this.editDailySummaryResponse.createdDate,
                facilityId: this.editDailySummaryResponse.facilityId,
                nameOfStaff: this.editDailySummaryResponse.nameOfStaff,
                dayProgress: this.editDailySummaryResponse.dayProgress,
                npbProgress: this.editDailySummaryResponse.npbProgress,
                psiProgress: this.editDailySummaryResponse.psiProgress,
                tomorrowForecast: this.editDailySummaryResponse.tomorrowForecast,
                footPatrolling: this.editDailySummaryResponse.footPatrolling,
                footInspection: this.editDailySummaryResponse.footInspection,
                footPlateInspection: this.editDailySummaryResponse.footPlateInspection,
                supervisor: this.editDailySummaryResponse.supervisor,
                staffStrength: this.editDailySummaryResponse.staffStrength,
                powerBlock: this.editDailySummaryResponse.powerBlock,
                nonPowerBlock: this.editDailySummaryResponse.nonPowerBlock,
                remarks: this.editDailySummaryResponse.remarks
            })
        } ,error => {})
    }


    deleteDailySummary (id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected Daily Summary?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
                this.sendAndRequestService.requestForDELETE(Constants.app_urls.DAILY_SUMMARY.DAILY_SUMMARY.DELETE_DAILY_SUMMARY, id).subscribe(response => {
                        this.commonService.showAlertMessage('DailySummary Deleted Successfully');
                        this.getAllDailySummaryData();
                        this.displayHierarchyFields();
                    },error => {});
            }
        });
    }


    applyFilter(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase();
        this.dailySummaryDataSource.filter = filterValue;
    }

    onGoBack() {
        this.dailySummaryFormGroup.reset();
        this.addDailySummary = false;
        this.title =  Constants.EVENTS.ADD;
        this.enableDivision = false;
        this.enableSubDiv = false;
        this.enableDepot = false;
        this.displayHierarchyFields();
    }
    depotTypeOheAndPsi()
        {
               this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_DEPOT_OHE_AND_PSI).subscribe((data) => {
                 this.facilityData = data;
        }
               );

       }

       NewDailySummary () {
        this.dailySummaryFormGroup = this.formBuilder.group({
            id: 0,
            'createdDate':[null, Validators.required, this.duplicateFromDate.bind(this)],
            'facilityId':[null, Validators.required],
            'nameOfStaff':[null,Validators.maxLength(250)],
            'dayProgress': [null],
            'npbProgress': [null],
            'psiProgress': [null],
            'tomorrowForecast' : [null],
            'footPatrolling' : [null],
            'footInspection' : [null],
            'footPlateInspection' : [null],
            'supervisor' : [null],
            'staffStrength' : [null],
            'powerBlock' : [null],
            'nonPowerBlock' : [null],
            'remarks' : [null,Validators.maxLength(250)],
            'zone' : [null],
            'division' : [null],
            'subDiv' : [null],
        });
        this.addDailySummary = true;
        this.disableFacility = false;
    }
    
    findDivisions(){
    	let zone: string = this.dailySummaryFormGroup.value.zone;
    	for (let i = 0; i < this.userHierarchy.length; i++) {
               if(this.userHierarchy[i].zone == zone && this.userHierarchy[i].depotType == 'DIV'){
               	this.divisionList.push(this.userHierarchy[i]);
               	this.enableDivision = true;
               }
            }
    }
    
    findSubDivisions(){
    	let division: string = this.dailySummaryFormGroup.value.division;
    	for (let i = 0; i < this.userHierarchy.length; i++) {
               if(this.userHierarchy[i].division == division && this.userHierarchy[i].depotType == 'SUBDIV'){
               	this.subDivList.push(this.userHierarchy[i]);
               	this.enableSubDiv = true;
               }
            }
    }
    
    findDepots(){
    	let subDiv: string = this.dailySummaryFormGroup.value.subDiv;
    	for (let i = 0; i < this.userHierarchy.length; i++) {
               if(this.userHierarchy[i].subDivision == subDiv && (this.userHierarchy[i].depotType == 'OHE' || this.userHierarchy[i].depotType == 'PSI') ){
               	this.facilityList.push(this.userHierarchy[i]);
               	this.enableDepot = true;
               }
            }
    }
    ViewData(data){
        var result = {
          'title':this.Titles.DAILY_SUMMARY_DATA,
          'dataSource':[                   
                        { label:FieldLabelsConstant.LABELS.FACILITY_NAME, value:data.facilityId },
                        { label:FieldLabelsConstant.LABELS.NAME_OF_STAFF, value:data.nameOfStaff },
                        { label:FieldLabelsConstant.LABELS.DAY_PROGRESS, value:data.dayProgress },
                        { label:FieldLabelsConstant.LABELS.NPB_PROGRESS, value:data.npbProgress },
                        { label:FieldLabelsConstant.LABELS.PSI_PROGRESS, value:data.psiProgress },
                        { label:FieldLabelsConstant.LABELS.TOMORROW_FORECAST, value:data.tomorrowForecast },
                        { label:FieldLabelsConstant.LABELS.FOOT_PATROLLING, value:data.footPatrolling },
                        { label:FieldLabelsConstant.LABELS.FOOT_INSPECTION, value:data.footInspection },
                        { label:FieldLabelsConstant.LABELS.FOOT_PLATE_INSPECTION, value:data.footPlateInspection },
                        { label:FieldLabelsConstant.LABELS.SUPERVISOR, value:data.supervisor },
                        { label:FieldLabelsConstant.LABELS.STAFF_STRENGTH, value:data.staffStrength },
                        { label:FieldLabelsConstant.LABELS.POWER_BLOCK, value:data.powerBlock }
                        
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

    

    


   


    
    

   

