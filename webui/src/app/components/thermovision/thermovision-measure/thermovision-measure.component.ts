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
    selector: 'thermovision-measure',
    templateUrl: './thermovision-measure.component.html',
    styleUrls: ['./thermovision-measure.component.css'],
    providers: [
    {
        provide: DateAdapter, useClass: AppDateAdapter
    },
    {
        provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
    }
    ]
})
export class ThermovisionMeasureComponent implements OnInit{
  
  pagination = Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
    inputFormGroup: FormGroup;
    //divisionsList: any;
    depotsData: any = JSON.parse(localStorage.getItem('depotData'));
    depotsList: any;
    thermovisionMeasureData = [];
    displayedColumns =['sno','description','fixed','c-clamp','ambientTemp','criticality','remark','fixedDiff','CClampDiff']//,'fixed',
    dataSource: MatTableDataSource<any>;
    thermovisionMeasuresList: any[] = [];
    enableSave: boolean;
    maxDate = new Date();
    resp: any;
    ambientTemp : any;
    divisionsList: any = JSON.parse(localStorage.getItem('divisionData'));
    checkDivisionUser: boolean;
    divCode: string;
    userDefaultData: any;
    loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  

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
            'division': [null , Validators.required ],
            'facilityId' : [null , Validators.required ],
            'location' : [null],
            'by' : [null],
            'generalRemark' : [null]
        });
        //this.divisionDetails();
        /*
        this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_TCP_SCHEDULES_BASED_ON_FACID+18413).subscribe((response) => {
            console.log('*** get schedules ***'+JSON.stringify(response));    
        }); */
        if(this.divisionsList.length > 0){
            this.checkDivisionUser = true;
        }else {
            this.checkDivisionUser = false;   
        }
        this.getUserContext();
        
    }
    
    compareTwoPoints(row){
        console.log('rowvalue***'+JSON.stringify(row));
        //console.log('row comp point ***'+row.tcpCommparisonPoints);
        if(row.tcpCommparisonPoints){
            let index = row.tcpCommparisonPoints - 1;
            console.log('*** index value ***'+index);
            console.log('indext is row '+JSON.stringify(this.thermovisionMeasureData[index]));
            let indexObject = this.thermovisionMeasureData[index];
          //  console.log('*** row fixed measure ****'+row.tcpmFixedMeasure);
           // console.log('*** index fixed measure ****'+indexObject.tcpmFixedMeasure);
            if(row.tcpmFixedMeasure && indexObject.tcpmFixedMeasure){
                let commpare = row.tcpmFixedMeasure - indexObject.tcpmFixedMeasure;
       //         console.log('*** in if condtiton ***'+commpare);
                row.fixedDiff = Math.abs(commpare);
                this.thermovisionMeasureData[index].fixedDiff = Math.abs(commpare);
          //      console.log('** fixed diff ****'+Math.abs(commpare));
            }else {
                row.fixedDiff = '';
                this.thermovisionMeasureData[index].fixedDiff = '';
            }
            
            if(row.tcpmCClampMeasure && indexObject.tcpmCClampMeasure){
                let commpare = row.tcpmCClampMeasure - indexObject.tcpmCClampMeasure;
                row.CClampDiff = Math.abs(commpare);
                this.thermovisionMeasureData[index].CClampDiff = Math.abs(commpare);
            }else {
                row.CClampDiff = '';
                this.thermovisionMeasureData[index].CClampDiff = '';
            }
        }
    }
    
    getUserContext(){
        this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_USER_DEFAULT_DATA + this.loggedUserData.username).subscribe((data) => {
                                   this.userDefaultData = data;
                if(this.userDefaultData.division) {
                    this.divCode = this.userDefaultData.division.toUpperCase();
                    this.inputFormGroup.patchValue({'division':this.divCode});
                    if(this.inputFormGroup.value.division) {
                        this.getDepots();
                    }
                }
       });    
    }
    
    saveAction(){
        this.sendAndRequestService.requestForPOST(Constants.app_urls.THERMOVISION.THERMOVISION_MEASURE.SAVE_THERMO_MEASURE,this.thermovisionMeasuresList,false).subscribe((response) => {
            this.spinnerService.hide();
            this.resp = response;
            this.enableSave = false;
            if(this.resp.code == 200 && !!this.resp) {
                this.commonService.showAlertMessage("Measures Data Updated Successfully");    
            }else
                this.commonService.showAlertMessage("Measures Data Updating Failed");
            
        });
    }
    
    getThermoMeasuresData(){
        this.thermovisionMeasureData = [];
        this.thermovisionMeasuresList = [];
        this.dataSource = new MatTableDataSource(this.thermovisionMeasuresList);
        this.spinnerService.show();
        this.sendAndRequestService.requestForGET(Constants.app_urls.THERMOVISION.THERMOVISION_MEASURE.GET_THERMO_MEASURES
            +this.datePipe.transform(this.inputFormGroup.value.dateTime, 'yyyy-MM-dd')+'/'+this.inputFormGroup.value.facilityId
            ).subscribe((data)=>{
                //console.log('infor***'+JSON.stringify(data));
                this.thermovisionMeasureData = data;
                if(this.thermovisionMeasureData){
                    this.enableSave = true;
                    for (let i = 0; i < this.thermovisionMeasureData.length; i++) {
                        this.thermovisionMeasureData[i].sno = i+1;
                        this.thermovisionMeasureData[i].fixedDiff = this.thermovisionMeasureData[i].fDiff;
                        this.thermovisionMeasureData[i].CClampDiff = this.thermovisionMeasureData[i].cClampDiff;
                        if(this.thermovisionMeasureData[i].tcpTypeOfCheckPoint == "FIXED_CClamp"){
                            this.thermovisionMeasureData[i].enableFixed = true;
                            this.thermovisionMeasureData[i].enableCClamp = true;
                        }else if(this.thermovisionMeasureData[i].tcpTypeOfCheckPoint == "FIXED"){
                            this.thermovisionMeasureData[i].enableFixed = true;
                            this.thermovisionMeasureData[i].enableCClamp = false;
                        }else {
                            this.thermovisionMeasureData[i].enableCClamp = true;
                            this.thermovisionMeasureData[i].enableFixed = false;
                        }
                        this.thermovisionMeasureData[i].tcpsDate = this.datePipe.transform(this.inputFormGroup.value.dateTime, 'yyyy-MM-dd');
                        this.thermovisionMeasureData[i].tcpsGeneralRemark = this.inputFormGroup.value.generalRemark;
                        this.thermovisionMeasureData[i].tcpsBy = this.inputFormGroup.value.by;
                        this.thermovisionMeasuresList.push(this.thermovisionMeasureData[i]);                            
                    }
                    this.dataSource = new MatTableDataSource(this.thermovisionMeasuresList);
                }
        });    
    }
    
    applyAmbientTemp(){
        if(this.thermovisionMeasuresList.length > 0){
           for (let i = 0; i < this.thermovisionMeasureData.length; i++) {
                this.thermovisionMeasuresList[i].tcpmAmbientTemp = this.ambientTemp;                    
           }
            this.dataSource = new MatTableDataSource(this.thermovisionMeasuresList); 
        }
    }
    
    divisionDetails() {
        this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.GET_DIVISIONS)
          .subscribe((data) => {
            this.divisionsList = data;
          });
    }
    
    getDepots(){
        //console.log('*** division code ***'+this.inputFormGroup.value.division);
        let division = this.inputFormGroup.value.division;
        //console.log('*** depots***'+JSON.stringify(this.depotsList));
        this.depotsList = this.depotsData.filter(value => {
            return value.division.toLowerCase() == division.toLowerCase();
          });
    }
    
    
 }   