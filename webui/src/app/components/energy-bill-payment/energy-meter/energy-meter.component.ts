import { OnInit, Component, ViewChild } from '@angular/core';
import { EnergyMeterService } from 'src/app/services/energy-meter.service';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import { EnergyMeterModel } from 'src/app/models/energy-meter.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { ReportService } from 'src/app/services/report.service';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';

@Component({
    selector: 'energy-meter',
    templateUrl: './energy-meter.component.html',
    styleUrls: ['./energy-meter.component.scss']
})
export class EnergyMeterComponent implements OnInit{
	
	addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
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

    constructor(
        private energyMeterService: EnergyMeterService,
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private dialog: MatDialog,
        private reportService: ReportService,
        private spinnerService: Ng4LoadingSpinnerService
    ){

    }

    ngOnInit () {
    	var permissionName = this.commonService.getPermissionNameByLoggedData("ENERGY BILL PAYMENT","ENERGY METER") ;
  		console.log("permissionName = "+permissionName);
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
    	this.energyMeterFormGroup = this.formBuilder.group({
            id: 0,
            'meterNo' : [null],
            'multiplicationFac' : [null],
            'startKvah': [null],
            'startKwh': [null],
            'startRkvahLag': [null],
            'startRkvahLead': [null],
            'endKvah' : [null],
            'endKwh' : [null],
            'endRkvahLag' : [null],
            'endRkvahLead' : [null],
            'meterMake' : [null],
            'meterModel' : [null],
            'cmd':[null],
            'remarks' : [null],
            'startDate': [null],
            'endDate': [null],
            'feederId': [null]
        });
        this.getAllEnergyMeterData();
        this.reportService.getAllTssFeederMaster().subscribe((data) => {
            this.tssFeederMaterList = data;
            } , error => {});
    }
    
    energyMeterSubmit () {
        let cmd: string = this.energyMeterFormGroup.value.cmd;
        let feederId: Date = this.energyMeterFormGroup.value.feederId;
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
        this.addEnergyMeter = false;
        
        if (this.title ==  Constants.EVENTS.SAVE) {
            this.energyMeterService.save({
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
            	'endDate' : endDate
            }).subscribe((data) => {
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
            this.energyMeterService.update({
                'id':id,
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
            	'endDate' : endDate
            }).subscribe((data) => {
            	this.energyMeterResponse = data;
            	if(this.energyMeterResponse.code == 200 && !!this.energyMeterResponse) {
	                this.commonService.showAlertMessage(this.energyMeterResponse.message);
	                this.getAllEnergyMeterData();
	                this.energyMeterFormGroup.reset();
	                this.addEnergyMeter =  false;
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
    }

    energyMeterEditAction(id: number) {
        this.energyMeterService.findById(id).subscribe((responseData) => {
            this.editEnergyMeterResponse = responseData;
             // console.log('edit response:::'+JSON.stringify(this.editEnergyMeterResponse));
      		this.energyMeterFormGroup.patchValue({
                id: this.editEnergyMeterResponse.id,
                cmd:this.editEnergyMeterResponse.cmd,
                startKvah: this.editEnergyMeterResponse.startKvah,
                feederId: this.editEnergyMeterResponse.feederId,
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
                startDate: this.editEnergyMeterResponse.strtDate,
                endDate: this.editEnergyMeterResponse.endDate
            })
            
        } ,error => {})
    }
    
    getAllEnergyMeterData() {
        const energyMeter : EnergyMeterModel[] = [];
        this.energyMeterService.getAllEnergyMeters().subscribe((data) => {
            this.energyMeterList = data;
            for (let i = 0; i < this.energyMeterList.length; i++) {
                this.energyMeterList[i].sno = i+1;
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
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected Energy Meter?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
                this.energyMeterService.delete(id)
                    .subscribe((data) => {
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
    }
    
 }   