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
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { DateAdapter, MAT_DATE_FORMATS } from '@angular/material';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';

@Component({
    selector: 'ohe-thermovision-measure',
    templateUrl: './ohe-thermovision-measure.component.html',
    styleUrls: ['./ohe-thermovision-measure.component.css'],
    providers: [
    {
        provide: DateAdapter, useClass: AppDateAdapter
    },
    {
        provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
    }
    ]
})
export class OheThermovisionMeasureComponent implements OnInit{
  
  pagination = Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
    inputFormGroup: FormGroup;
    //divisionsList: any;
    depotsData: any = JSON.parse(sessionStorage.getItem('depotData'));
    depotsList: any;
    thermovisionMeasureData = [];
    displayedColumns =['sno','Date','Depot','Location','connectionPoint1','measure1','connectionPoint2','measure2','diff','doneBy'];
    dataSource: MatTableDataSource<any>;
    thermovisionMeasuresList: any[] = [];
    enableSave: boolean;
    maxDate = new Date();
    resp: any;
    ambientTemp : any;
    divisionsList: any = JSON.parse(sessionStorage.getItem('divisionData'));
    checkDivisionUser: boolean;
    divCode: string;
    userDefaultData: any;
    loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
    /*
    depotTypes:any = this.depotsData.map(item => item.depotType)
   .filter((value, index, self) => self.indexOf(value) === index); */
    statusItems: any;
    depotType = "OHE";
    searchInputFormGroup: FormGroup;
    oheThermoMeasuresOrginalData: any[] = [];
    enableDiff: boolean = false;
    minDate: any;
  

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
        this.inputFormGroup = this.formBuilder.group({
            'dateTime': [null , Validators.required ],
            'division': [null],
            //'depotType' : [null, Validators.required],
            'facilityId' : [null , Validators.required ],
            'location' : [null,Validators.required],
            'by' : [null,Validators.required],
            'generalRemark' : [null],
            'connectionPoint1':[null, Validators.required ],
            'connectionPoint2':[ null ],
            'measure1':[null, Validators.required ],
            'measure2':[null],
            //'ambientTemp': [null,]
        });
        this.searchInputFormGroup = this.formBuilder.group({
            'facilityId' : [null , Validators.required ],
            'fromDate' : [null , Validators.required ],
            'thruDate' : [null , Validators.required ]
        });
       
        
        this.getUserContext();
        this.getStatusItems();
       this.depotsList = this.depotsData.filter(value => {
        return value.depotType == "OHE";
      });
    }
    
    saveOheThermovisionMeasure(){
        let OheTherMeasureObject = {
            'dateTime': this.inputFormGroup.value.dateTime,
            'facilityId': this.inputFormGroup.value.facilityId,
            'connectionPoint1': this.inputFormGroup.value.connectionPoint1,
            'measure1': this.inputFormGroup.value.measure1,
            'connectionPoint2': this.inputFormGroup.value.connectionPoint2,
            'measure2': this.inputFormGroup.value.measure2,
            //'ambientTemp': this.inputFormGroup.value.ambientTemp,
            'by': this.inputFormGroup.value.by,
            'generalRemark': this.inputFormGroup.value.generalRemark,
            'location': this.inputFormGroup.value.location
        }
        this.sendAndRequestService.requestForPOST(Constants.app_urls.THERMOVISION.THERMOVISION_MEASURE.SAVE_OHE_THERMO_MEASURE,OheTherMeasureObject,false).subscribe((response) => {
            this.spinnerService.hide();
            this.resp = response;
            this.enableSave = false;
            if(this.resp.code == 200 && !!this.resp) {
                this.commonService.showAlertMessage("Measures Data Saved Successfully"); 
                this.getThermoMeasuresData();    
            }else
                this.commonService.showAlertMessage("Measures Data Saving Failed");
            
        });
    }
    
    getStatusItems(){
        this.sendAndRequestService.requestForGET(Constants.app_urls.ASH.ASH.STATUS_ON_STATUS_TYPE + 'OHE_thermovision_check_points').subscribe((data) => {
            this.statusItems = data;
        });    
    }
    
    getUserContext(){
        this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_USER_DEFAULT_DATA + this.loggedUserData.username).subscribe((data) => {
                                   this.userDefaultData = data;
                if(this.userDefaultData.division) {
                    this.setDefaultDivision();
                }
       });    
    }
    
    setDefaultDivision(){
    	if(this.divisionsList.length > 0) {
            this.checkDivisionUser = true;
        }else {
            this.checkDivisionUser = false;
            this.inputFormGroup.patchValue({ facilityId: this.depotsData[0] })
        }
        this.divCode = this.userDefaultData.division.toUpperCase();
        this.inputFormGroup.patchValue({'division':this.divCode});
        if(this.inputFormGroup.value.division) {
            this.getDepots();
        }
    }
    
    getThermoMeasuresData(){
        this.searchInputFormGroup.reset();
        this.thermovisionMeasureData = [];
        this.thermovisionMeasuresList = [];
        this.enableDiff = false;
        this.dataSource = new MatTableDataSource(this.thermovisionMeasuresList);
        this.spinnerService.show();
        this.sendAndRequestService.requestForGET(Constants.app_urls.THERMOVISION.THERMOVISION_MEASURE.GET_OHE_THERMO_MEASURES
            +this.inputFormGroup.value.facilityId.id
            ).subscribe((data)=>{
               // console.log('infor***'+JSON.stringify(data));
                this.thermovisionMeasureData = data;
                if(this.thermovisionMeasureData.length > 0){
                    this.enableSave = true;
                    for (let i = 0; i < this.thermovisionMeasureData.length; i++) {
                        this.thermovisionMeasureData[i].sno = i+1;
                        this.thermovisionMeasureData[i].facilityName = this.inputFormGroup.value.facilityId.facilityName;
                        this.thermovisionMeasureData[i].date = this.datePipe.transform(this.thermovisionMeasureData[i].tcpScheduleId.dateTime, 'dd-MM-yyyy');
                        this.thermovisionMeasureData[i].doneBy = this.thermovisionMeasureData[i].tcpScheduleId.by; 
                        this.thermovisionMeasuresList.push(this.thermovisionMeasureData[i]);
                        if( this.thermovisionMeasureData[i].measurePoint1 && this.thermovisionMeasureData[i].measurePoint2 ){
                        	 let commpare = this.thermovisionMeasureData[i].measurePoint1 - this.thermovisionMeasureData[i].measurePoint2;
            				 this.thermovisionMeasureData[i].diff = Math.abs(commpare).toFixed(2) ;
                        }else{
                        	 this.thermovisionMeasureData[i].diff = '';
                        	 this.thermovisionMeasureData[i].measurePoint2 = '';
                        }                            
                    }
                    this.dataSource = new MatTableDataSource(this.thermovisionMeasuresList);
                }
        });    
    }
    
    addEvent($event) {
        this.minDate = new Date($event.value);
    }
    
    getOheThermovisionMeasureData(){
        this.inputFormGroup.reset();
        this.setDefaultDivision();
        this.enableDiff = false;
    	this.thermovisionMeasureData = [];
    	this.thermovisionMeasuresList = [];
        this.oheThermoMeasuresOrginalData  = [];
        this.dataSource = new MatTableDataSource(this.thermovisionMeasuresList);
        this.spinnerService.show();
    	this.sendAndRequestService.requestForGET(Constants.app_urls.THERMOVISION.THERMOVISION_MEASURE.GET_OHE_THERMO_MEASURES_DATA
            +this.searchInputFormGroup.value.facilityId.id+'/'+this.searchInputFormGroup.value.fromDate+'/'+this.searchInputFormGroup.value.thruDate
            ).subscribe((data)=>{
              // console.log('infor***'+JSON.stringify(data));
                this.thermovisionMeasureData = data;
                if(this.thermovisionMeasureData.length > 0){
                    this.enableDiff = true;
                    for (let i = 0; i < this.thermovisionMeasureData.length; i++) {
                        this.thermovisionMeasureData[i].sno = i+1;
                        this.thermovisionMeasureData[i].facilityName = this.searchInputFormGroup.value.facilityId.facilityName;
                        this.thermovisionMeasureData[i].date = this.datePipe.transform(this.thermovisionMeasureData[i].tcpScheduleId.dateTime, 'dd-MM-yyyy');
                        this.thermovisionMeasureData[i].doneBy = this.thermovisionMeasureData[i].tcpScheduleId.by; 
                        
                        if( this.thermovisionMeasureData[i].measurePoint1 && this.thermovisionMeasureData[i].measurePoint2 ){
                        	 let commpare = this.thermovisionMeasureData[i].measurePoint1 - this.thermovisionMeasureData[i].measurePoint2;
            				 this.thermovisionMeasureData[i].diff = Math.abs(commpare).toFixed(2) ;
                        }else{
                        	 this.thermovisionMeasureData[i].diff = '';
                        	 this.thermovisionMeasureData[i].measurePoint2 = '';
                        } 
                        this.thermovisionMeasuresList.push(this.thermovisionMeasureData[i]); 
                        this.oheThermoMeasuresOrginalData.push(this.thermovisionMeasureData[i]);                          
                    }
                    this.dataSource = new MatTableDataSource(this.thermovisionMeasuresList);
                }else {
                    this.enableDiff = false;    
                }
        });
    }
    
    applyFilter(filterValue: number) {
        this.thermovisionMeasuresList = [];
        if(filterValue){
            this.thermovisionMeasuresList = this.oheThermoMeasuresOrginalData.filter(value => {
                let diffValue: number = value.diff;
                return  Math.round( value.diff ) >= filterValue;
            });    
        }else{
            this.thermovisionMeasuresList = this.oheThermoMeasuresOrginalData
        }
        
        this.dataSource = new MatTableDataSource(this.thermovisionMeasuresList);
  		//filterValue = filterValue.trim();
  		//filterValue = filterValue.toLowerCase();
   		//this.dataSource.filter = filterValue;
	}
    
    divisionDetails() {
        this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.GET_DIVISIONS)
          .subscribe((data) => {
            this.divisionsList = data;
          });
    }
    
    getDepots(){
        let division = this.inputFormGroup.value.division;
        this.depotsList = this.depotsData.filter(value => {
            return value.division.toLowerCase() == division.toLowerCase() && value.depotType == this.depotType;
          });
    }
    
    
 }   