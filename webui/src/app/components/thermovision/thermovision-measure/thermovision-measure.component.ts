import { OnInit, Component, ViewChild, Inject } from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import { EnergyMeterModel } from 'src/app/models/energy-meter.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material';
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
    depotsData: any = JSON.parse(sessionStorage.getItem('depotData'));
    depotsList: any;
    thermovisionMeasureData = [];
    displayedColumns =['sno','point1description','measure1','point2description','measure2','tempDiff','remark','previous1','previous2','previous3','actions']//,'fixed','criticality',
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
    depotTypes = ['SP','SSP','TSS'];
    /*
    depotTypes:any = this.depotsData.map(item => item.depotType)
   .filter((value, index, self) => self.indexOf(value) === index)
   */

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
            'depotType' : [null, Validators.required],
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
        if(row.tcpmMeasurePoint1 && row.tcpmMeasurePoint2){
            console.log('tcpmMeasurePoint1***'+row.tcpmMeasurePoint1);
            console.log('tcpmMeasurePoint1***'+row.tcpmMeasurePoint2);
            let commpare = row.tcpmMeasurePoint1 - row.tcpmMeasurePoint2;
            row.tempDiff = Math.abs(commpare).toFixed(2) ;
           /*
            let index = this.thermovisionMeasureData.findIndex(value => value.tcpId === row.tcpCommparisonPoints );
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
            } */
        }else {
            row.tempDiff = '';
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
                this.getThermoMeasuresData();   
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
                if(this.thermovisionMeasureData.length > 0){
                    this.enableSave = true;
                    for (let i = 0; i < this.thermovisionMeasureData.length; i++) {
                        this.thermovisionMeasureData[i].sno = i+1;
                        this.thermovisionMeasureData[i].tempDiff = this.thermovisionMeasureData[i].fDiff;
                        if(this.thermovisionMeasureData[i].pre1MTcpsDate){
                            this.thermovisionMeasureData[i].previous1 =   this.thermovisionMeasureData[i].prev1Events;
                            //this.thermovisionMeasureData[i].previous1 =  this.datePipe.transform(this.thermovisionMeasureData[i].pre1MTcpsDate, 'dd-MMM-yyyy') +  '('+this.thermovisionMeasureData[i]. pre1MTcpmMeasurePoint1 +'-'+this.thermovisionMeasureData[i]. pre1MTcpmMeasurePoint2+') '+Math.abs(this.thermovisionMeasureData[i]. pre1MTcpmMeasurePoint1 - this.thermovisionMeasureData[i]. pre1MTcpmMeasurePoint2).toFixed(2) ;
                            this.thermovisionMeasureData[i].previousDiff1 = Math.abs(this.thermovisionMeasureData[i]. pre1MTcpmMeasurePoint1 - this.thermovisionMeasureData[i]. pre1MTcpmMeasurePoint2).toFixed(2);
                        } 
                        if(this.thermovisionMeasureData[i].pre2MTcpsDate) {
                            this.thermovisionMeasureData[i].previous2 =   this.thermovisionMeasureData[i].prev2Events;
                            //this.thermovisionMeasureData[i].previous2 =  this.datePipe.transform(this.thermovisionMeasureData[i].pre2MTcpsDate, 'dd-MMM-yyyy')  +'('+this.thermovisionMeasureData[i]. pre2MTcpmMeasurePoint1 +'-'+this.thermovisionMeasureData[i]. pre2MTcpmMeasurePoint2+') '+ Math.abs(this.thermovisionMeasureData[i]. pre2MTcpmMeasurePoint1 - this.thermovisionMeasureData[i]. pre2MTcpmMeasurePoint2).toFixed(2) ;
                            this.thermovisionMeasureData[i].previousDiff2 = Math.abs(this.thermovisionMeasureData[i]. pre2MTcpmMeasurePoint1 - this.thermovisionMeasureData[i]. pre2MTcpmMeasurePoint2).toFixed(2);
                        } 
                        if(this.thermovisionMeasureData[i].pre3MTcpsDate) {
                            this.thermovisionMeasureData[i].previous3 =   this.thermovisionMeasureData[i].prev3Events;
                            //this.thermovisionMeasureData[i].previous3 = this.datePipe.transform(this.thermovisionMeasureData[i].pre3MTcpsDate, 'dd-MMM-yyyy')  +'('+this.thermovisionMeasureData[i]. pre3MTcpmMeasurePoint1 +'-'+this.thermovisionMeasureData[i]. pre3MTcpmMeasurePoint2+') '+  Math.abs(this.thermovisionMeasureData[i]. pre3MTcpmMeasurePoint1 - this.thermovisionMeasureData[i]. pre3MTcpmMeasurePoint2).toFixed(2) ;
                            this.thermovisionMeasureData[i].previousDiff3 = Math.abs(this.thermovisionMeasureData[i]. pre3MTcpmMeasurePoint1 - this.thermovisionMeasureData[i]. pre3MTcpmMeasurePoint2).toFixed(2);
                        }
                        if(this.thermovisionMeasureData[i].tcpmMeasurePoint1 == 0) {
                           this.thermovisionMeasureData[i].tcpmMeasurePoint1 = ''; 
                        }
                        if(this.thermovisionMeasureData[i].tcpmMeasurePoint2 == 0) {
                           this.thermovisionMeasureData[i].tcpmMeasurePoint2 = ''; 
                        }
                        if(this.thermovisionMeasureData[i].tempDiff == 0) {
                           this.thermovisionMeasureData[i].tempDiff = ''; 
                        }else 
                           this.thermovisionMeasureData[i].tempDiff = this.thermovisionMeasureData[i].tempDiff.toFixed(2)  
                        /*
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
                        */
                        this.thermovisionMeasureData[i].tcpsDate =  this.inputFormGroup.value.dateTime;
                        this.thermovisionMeasureData[i].tcpsGeneralRemark = this.inputFormGroup.value.generalRemark;
                        this.thermovisionMeasureData[i].tcpsBy = this.inputFormGroup.value.by;
                        this.thermovisionMeasureData[i].tcpsLocation = this.inputFormGroup.value.location;
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
    
    getFacilitys(){
      var depotType = this.inputFormGroup.value.depotType ;
        this.inputFormGroup.patchValue( {'facilityId':null} );
        this.inputFormGroup.setErrors({ 'invalid': true });
      this.depotsList = this.depotsData.filter(value => {
        return value.depotType == depotType;
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
    
    retestDialog(row){
         const dialogRef = this.dialog.open(retestDialogComponent, {
          height: '300px',
          width: '80%', 
          data: { thermovisionMeasureId : row,
                }
        });
        
        dialogRef.afterClosed().subscribe(result => {
           this.getThermoMeasuresData();
        });
    }    
    
 }


@Component({
  selector: 'retest-dialog',
  templateUrl: 'retest-dialog.component.html',
  providers: [
    {
        provide: DateAdapter, useClass: AppDateAdapter
    },
    {
        provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
    }
    ]
})
export class retestDialogComponent implements OnInit  {
    
    FiledLabels = FieldLabelsConstant.LABELS;
    Titles = FieldLabelsConstant.TITLE;  
    retestFormGroup: FormGroup;
    facilityId: any;
    date: any;
    tcpmId: any;
    resp: any;
    point1: any;
    point2: any;
    maxDate = new Date();
    
    constructor(
    private formBuilder: FormBuilder,
    private spinnerService: Ng4LoadingSpinnerService,  
    private sendAndRequestService:SendAndRequestService,
    private dialog: MatDialog,
    private commonService: CommonService,
    private dialogRef: MatDialogRef<retestDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private datePipe: DatePipe
    ) {
      
      if(data) {
          console.log('*** row ***'+JSON.stringify(data))
          this.facilityId = data.thermovisionMeasureId.tcpsFacilityId;
          this.date = data.thermovisionMeasureId.tcpsDate;
          this.tcpmId = data.thermovisionMeasureId.tcpmId;
          this.point1 = data.thermovisionMeasureId.tcpCheckPoint1Description;
          this.point2 = data.thermovisionMeasureId.tcpCheckPoint2Description;
      }
  }
    
    ngOnInit() {
        this.retestFormGroup = this.formBuilder.group({
            date:[null ,  Validators.required ],
            measure1: [null,  Validators.required  ],
            measure2: [null,  Validators.required ],
            remarks: [null,  Validators.required ],
        })
        
    }
    
    saveThermoMeasure(){
        let thermoMeasureObject  = {
            "tcpmMeasurePoint1": this.retestFormGroup.controls['measure1'].value,
            "tcpmMeasurePoint2": this.retestFormGroup.controls['measure2'].value,
            "tcpsFacilityId":    this.facilityId,
            "tcpsDate": this.date,
            "tcpmRemark": this.retestFormGroup.controls['remarks'].value,
            "tcpmDateOfRetest": this.retestFormGroup.controls['date'].value,
            "tcpmThermovisionMeasureId": this.tcpmId,
            "tcpmId": this.tcpmId
        }
        this.sendAndRequestService.requestForPOST(Constants.app_urls.THERMOVISION.THERMOVISION_MEASURE.SAVE_THERMO_MEASURE_RETEST,thermoMeasureObject,false).subscribe((response) => {
            this.spinnerService.hide();
            this.resp = response;
            if(this.resp.code == 200 && !!this.resp) {
                this.dialogRef.close();
                this.commonService.showAlertMessage("Measures Data Saved Successfully");
                //this.getThermoMeasuresData();   
            }else
                this.commonService.showAlertMessage("Measures Data Saving Failed");
            
        });  
    }
        
}
    