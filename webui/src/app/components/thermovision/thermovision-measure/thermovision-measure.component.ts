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
    divisionsList: any;
    depotsData: any = JSON.parse(localStorage.getItem('depotData'));
    depotsList: any;
    thermovisionMeasureData = [];
    displayedColumns =['sno','description','fixed','c-clamp','ambientTemp','criticality','remark']//,'fixed',
    dataSource: MatTableDataSource<any>;
    thermovisionMeasuresList: any[] = [];
    enableSave: boolean;
    maxDate = new Date();
    resp: any;
    
  

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
        this.divisionDetails();
        
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
                        this.thermovisionMeasureData[i].tcpsDate = this.datePipe.transform(this.inputFormGroup.value.dateTime, 'yyyy-MM-dd');
                        this.thermovisionMeasureData[i].tcpsGeneralRemark = this.inputFormGroup.value.generalRemark;
                        this.thermovisionMeasureData[i].tcpsBy = this.inputFormGroup.value.by;
                        this.thermovisionMeasuresList.push(this.thermovisionMeasureData[i]);                            
                    }
                    this.dataSource = new MatTableDataSource(this.thermovisionMeasuresList);
                }
        });    
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