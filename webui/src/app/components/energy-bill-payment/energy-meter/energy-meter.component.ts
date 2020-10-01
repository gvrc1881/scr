import { OnInit, Component, ViewChild } from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import { EnergyMeterModel } from 'src/app/models/energy-meter.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { DataViewDialogComponent } from '../../data-view-dialog/data-view-dialog.component';
import { DatePipe } from '@angular/common';

@Component({
    selector: 'energy-meter',
    templateUrl: './energy-meter.component.html',
    styleUrls: []
})
export class EnergyMeterComponent implements OnInit{
	
	addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    id: number = 0;
    title: string = "Save";
    energyMeterFormGroup: FormGroup;
    energyMeterList : any;
    energyMeterDataSource: MatTableDataSource<EnergyMeterModel>;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    editEnergyMeterResponse: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    addEnergyMeter: boolean ;
    energyMeterDisplayColumns = ['sno' , 'cmd' , 'feederId' , 'startKvah' , 'startKwh' , 'id' ] ;
    tssFeederMaterList: any;
    energyMeterResponse: any;
    toMinDate=new Date();
    enableEndReadings: boolean;
    loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
    tssFeeder: any;
    dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;

    constructor(
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private dialog: MatDialog,
        private spinnerService: Ng4LoadingSpinnerService,
        private sendAndRequestService:SendAndRequestService,
        private datePipe: DatePipe

    ){

    }

    ngOnInit () {
    	var permissionName = this.commonService.getPermissionNameByLoggedData("ENERGY","ENERGY METER") ;
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
        this.getAllEnergyMeterData();
        this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_TSS_FEEDER_MASTER_DETAILS).subscribe((data) => {
            this.tssFeederMaterList = data;
            } , error => {});
    }
    
    duplicateFeederAndStartDate() {
        let feeder = this.energyMeterFormGroup.controls['feederId'].value;
    	const q = new Promise((resolve, reject) => {
	       this.sendAndRequestService.requestForGET(
                Constants.app_urls.ENERGY_BILL_PAYMENTS.ENERGY_METER.EXISTS_FEEDER_START_DATE +
	            feeder.feederId + '/'+
	            this.energyMeterFormGroup.controls['startDate'].value
	      ).subscribe((duplicate) => {
	        if (duplicate) {
	          resolve({ 'duplicateFeederAndStartDate': true });
	        } else {
	          resolve(null);
	        }
	      }, () => { resolve({ 'duplicateFeederAndStartDate': true }); });
	    });
    	return q;
  	}
    
    energyMeterSubmit () {
        let cmd: string = this.energyMeterFormGroup.value.cmd;
        let feederId: Date = this.energyMeterFormGroup.value.feederId.feederId;
        let startKvah: string = this.energyMeterFormGroup.value.startKvah;
        let startKwh: string = this.energyMeterFormGroup.value.startKwh;
        let startRkvahLag: string = this.energyMeterFormGroup.value.startRkvahLag;
        let startRkvahLead: string = this.energyMeterFormGroup.value.startRkvahLead;
        let endKvah: string = this.energyMeterFormGroup.value.endKvah;
        let endKwh: string = this.energyMeterFormGroup.value.endKwh;
        let endRkvahLag: string = this.energyMeterFormGroup.value.endRkvahLag;
        let endRkvahLead: string = this.energyMeterFormGroup.value.endRkvahLead;
        let meterNo: string = this.energyMeterFormGroup.value.meterNo;
        let multiplicationFac: string = this.energyMeterFormGroup.value.multiplicationFac;
        let meterMake: string = this.energyMeterFormGroup.value.meterMake;
        let meterModel: string = this.energyMeterFormGroup.value.meterModel;
        let remarks: string = this.energyMeterFormGroup.value.remarks;
        let startDate: string = this.energyMeterFormGroup.value.startDate;
        let endDate: string = this.energyMeterFormGroup.value.endDate;
        let dataDiv: string = this.energyMeterFormGroup.value.feederId.dataDiv;
        this.addEnergyMeter = false;
        
        if (this.title ==  Constants.EVENTS.SAVE) {
            var saveEnergyMeterModel={
                'cmd':cmd,
                'startKvah': startKvah,
                'feederId':feederId,
                'startKwh': startKwh,
                'startRkvahLag' : startRkvahLag,
                'startRkvahLead': startRkvahLead,
                'endKvah' : endKvah,
                'endKwh' : endKwh,
                'endRkvahLag' : endRkvahLag,
                'endRkvahLead' : endRkvahLead,
 	            'meterNo' : meterNo,
    	        'multiplicationFac' : multiplicationFac,
        	    'meterMake' : meterMake,
            	'meterModel' : meterModel,
            	'remarks' : remarks,
            	'startDate' : startDate,
            	'endDate' : endDate,
            	'dataDiv' : dataDiv
            }              
            this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.ENERGY_METER.SAVE_ENERGY_METER, saveEnergyMeterModel, false).subscribe(data => {
            	this.energyMeterResponse = data;
            	if(this.energyMeterResponse.code == 200 && !!this.energyMeterResponse) {
	                this.commonService.showAlertMessage(this.energyMeterResponse.message);
	                this.getAllEnergyMeterData();
	                this.energyMeterFormGroup.reset();
                }else {
                	this.commonService.showAlertMessage("Energy Meter Data Saving Failed.");
                }
                this.spinnerService.hide();
            } , error => {
            	console.log('ERROR >>>');
        		this.spinnerService.hide();
        		this.commonService.showAlertMessage("Energy Meter Data Saving Failed.");
            });
        }else if (this.title == Constants.EVENTS.UPDATE ) {
            let id: number = this.editEnergyMeterResponse.id;
            var updateEnergyMeterModel={
                'id':id,
                'cmd':cmd,
                'startKvah': startKvah,
                'feederId': this.editEnergyMeterResponse.feederId,
                'startKwh': startKwh,
                'startRkvahLag' : startRkvahLag,
                'startRkvahLead': startRkvahLead,
                'endKvah' : endKvah,
                'endKwh' : endKwh,
                'endRkvahLag' : endRkvahLag,
                'endRkvahLead' : endRkvahLead,
                'meterNo' : meterNo,
    	        'multiplicationFac' : multiplicationFac,
        	    'meterMake' : meterMake,
            	'meterModel' : meterModel,
            	'remarks' : remarks,
            	'startDate' : startDate,
            	'endDate' : endDate,
            	'dataDiv' : this.editEnergyMeterResponse.dataDiv,
            	'seqId': this.editEnergyMeterResponse.seqId
            }    
            this.sendAndRequestService.requestForPUT(Constants.app_urls.ENERGY_BILL_PAYMENTS.ENERGY_METER.UPDATE_ENERGY_METER,updateEnergyMeterModel, false).subscribe(data => {
            	this.energyMeterResponse = data;
            	if(this.energyMeterResponse.code == 200 && !!this.energyMeterResponse) {
	                this.commonService.showAlertMessage(this.energyMeterResponse.message);
	                this.getAllEnergyMeterData();
	                this.energyMeterFormGroup.reset();
	                this.addEnergyMeter =  false;
	                this.title = "Save";
                }else {
                	this.commonService.showAlertMessage("Energy Meter Data Updating Failed.");
                }
            } , error => {
            	console.log('ERROR >>>');
        		this.spinnerService.hide();
        		this.commonService.showAlertMessage("Energy Meter Data Updating Failed.");
            })
            
        }
    }
    
    editEnergyMeter (id) {
        this.addEnergyMeter = true;
        this.energyMeterEditAction(id);
        this.title = 'Update';
        this.enableEndReadings = true;
    }

    energyMeterEditAction(id: number) {
        this.energyMeterFormGroup = this.formBuilder.group({
            id: 0,
            'meterNo' : [null],
            'multiplicationFac' : [null,Validators.required],
            'startKvah': [null,Validators.required],
            'startKwh': [null,Validators.required],
            'startRkvahLag': [null,Validators.required],
            'startRkvahLead': [null,Validators.required],
            'endKvah' : [null],
            'endKwh' : [null],
            'endRkvahLag' : [null],
            'endRkvahLead' : [null],
            'meterMake' : [null],
            'meterModel' : [null],
            'cmd':[null],
            'remarks' : [null,Validators.maxLength(250)],
            'startDate': [null,Validators.required],
            'endDate': [null],
            'feederId': [null]
        });
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ENERGY_METER.GET_ENERGY_METER_ID+id).subscribe((responseData) => {
            this.editEnergyMeterResponse = responseData;
              this.toMinDate = new Date(this.editEnergyMeterResponse.startDate);
		    	 this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_TSS_FEEDER_BASED_ON_FEEDER_ID+'/'+this.editEnergyMeterResponse.feederId).subscribe((response) => {
		                	this.tssFeeder = response;
		               		this.energyMeterFormGroup.patchValue({ feederId: this.tssFeeder.feederName })       	
		         });
      		this.energyMeterFormGroup.patchValue({
                id: this.editEnergyMeterResponse.id,
                cmd:this.editEnergyMeterResponse.cmd,
                startKvah: this.editEnergyMeterResponse.startKvah,
                startKwh: this.editEnergyMeterResponse.startKwh,
                startRkvahLag: this.editEnergyMeterResponse.startRkvahLag,
                startRkvahLead: this.editEnergyMeterResponse.startRkvahLead,
                endKvah: this.editEnergyMeterResponse.endKvah,
                endKwh: this.editEnergyMeterResponse.endKwh,
                endRkvahLag: this.editEnergyMeterResponse.endRkvahLag,
                endRkvahLead: this.editEnergyMeterResponse.endRkvahLead,
                meterNo:this.editEnergyMeterResponse.meterNo,
                multiplicationFac: this.editEnergyMeterResponse.multiplicationFac,
                meterMake: this.editEnergyMeterResponse.meterMake,
                meterModel: this.editEnergyMeterResponse.meterModel,
                remarks: this.editEnergyMeterResponse.remarks,
                startDate: !!this.editEnergyMeterResponse.startDate ? new Date(this.editEnergyMeterResponse.startDate) : '',
                endDate: !!this.editEnergyMeterResponse.endDate ? new Date(this.editEnergyMeterResponse.endDate) : ''
            })
            
        } ,error => {})
        this.id=id;
        if (!isNaN(this.id)) {
            this.title = 'Update';
          } else {
            this.title = 'Save';      
          }
    }
    
    
    getAllEnergyMeterData() {
        const energyMeter : EnergyMeterModel[] = [];
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ENERGY_METER.GET_ENERGY_METER).subscribe((data) => {
            this.energyMeterList = data;
            this.spinnerService.show();
            for (let i = 0; i < this.energyMeterList.length; i++) {
                this.energyMeterList[i].sno = i+1;
                this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_TSS_FEEDER_BASED_ON_FEEDER_ID+'/'+this.energyMeterList[i].feederId).subscribe((response) => {
                	this.tssFeeder = response;
                	this.energyMeterList[i].feederId = this.tssFeeder.feederName;
                });
                energyMeter.push(this.energyMeterList[i]);              
            }
            this.energyMeterDataSource = new MatTableDataSource(energyMeter);
            this.energyMeterDataSource.paginator = this.paginator;
            this.energyMeterDataSource.sort = this.sort;
			this.spinnerService.hide();
        } , error => {
        	this.spinnerService.hide();
        });

    }
    
    deleteEnergyMeter (id) {
    	this.addEnergyMeter = false;
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected Energy Meter?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
                this.sendAndRequestService.requestForDELETE(Constants.app_urls.ENERGY_BILL_PAYMENTS.ENERGY_METER.DELETE_ENERGY_METER, id).subscribe(data => {
                    	this.energyMeterResponse = data;
            			if(this.energyMeterResponse.code == 200 && !!this.energyMeterResponse) {
                        	this.commonService.showAlertMessage(this.energyMeterResponse.message);
                         	this.getAllEnergyMeterData();
                         } else {
                         	this.commonService.showAlertMessage("Energy Meter Deletion Failed.");
                         }	
                    },error => {
                    	console.log('ERROR >>>');
          				this.spinnerService.hide();
          				this.commonService.showAlertMessage("Energy Meter Deletion Failed.");
                    });
            }
        });
    }
    
    applyFilter(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase();
        this.energyMeterDataSource.filter = filterValue;
    }

    onGoBack() {
        this.energyMeterFormGroup.reset();
        this.addEnergyMeter = false;
        this.title = 'Save';
    }

    newEnergyMeter() {
        this.addEnergyMeter = true;
        this.enableEndReadings = false;
        this.energyMeterFormGroup = this.formBuilder.group({
            id: 0,
            'meterNo' : [null],
            'multiplicationFac' : [null,Validators.required],
            'startKvah': [null,Validators.required],
            'startKwh': [null,Validators.required],
            'startRkvahLag': [null,Validators.required],
            'startRkvahLead': [null,Validators.required],
            'endKvah' : [null],
            'endKwh' : [null],
            'endRkvahLag' : [null],
            'endRkvahLead' : [null],
            'meterMake' : [null],
            'meterModel' : [null],
            'cmd':[null],
            'remarks' : [null,Validators.maxLength(250)],
            'startDate': [null,Validators.required, this.duplicateFeederAndStartDate.bind(this)],
            'endDate': [null],
            'feederId': [null,Validators.required , this.dupllicateFeederhavingEndDate.bind(this)]
        });
    }
    
    dupllicateFeederhavingEndDate() {
        let feeder = this.energyMeterFormGroup.controls['feederId'].value;
    	const q = new Promise((resolve, reject) => {
	       this.sendAndRequestService.requestForGET(
        	        Constants.app_urls.ENERGY_BILL_PAYMENTS.ENERGY_METER.EXISTS_FEEDER_HAVING_END_DATE +feeder.feederId
	      ).subscribe((duplicate) => {
	        if (duplicate) {
	          resolve({ 'dupllicateFeederhavingEndDate': true });
	        } else {
	          resolve(null);
	        }
	      }, () => { resolve({ 'dupllicateFeederhavingEndDate': true }); });
	    });
    	return q;
    }
    
     ViewData(data){
      var result = {
        'title':'Energy Meter',
        'dataSource':[{label:'Feeder',value:data.feederId},{label:'Start Date',value:this.datePipe.transform(data.startDate, 'dd-MM-yyyy hh:mm:ss')},
                      {label:'Multiplication Factor', value:data.multiplicationFac},{label:'Meter No',value:data.meterNo},{label:'CMD',value:data.cmd},
                      {label:'Start Kvah',value:data.startKvah},
                      {label:'Start Kwh',value:data.startKwh},{label:'Start Rkvah Lag',value:data.startRkvahLag},
                      {label:'Start Rkvah Lead',value:data.startRkvahLead},{label:'End Date',value:this.datePipe.transform(data.endDate, 'dd-MM-yyyy hh:mm:ss')},
                      {label:'End Kvah',value:data.endKvah},{label:'End Kwh',value:data.endKwh},
                      {label:'End Rkvah Lag',value:data.endRkvahLag},{label:'End Rkvah Lead',value:data.endRkvahLead},
                      {label:'Meter Make',value:data.meterMake},{label:'Meter Model',value:data.meterModel},
                      {label:'Remark',value:data.remarks}
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