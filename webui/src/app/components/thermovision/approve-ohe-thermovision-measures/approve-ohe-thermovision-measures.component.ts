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
    selector: 'approve-psi-thermovision-measures',
    templateUrl: './approve-ohe-thermovision-measures.component.html',
    styleUrls: ['./approve-ohe-thermovision-measures.component.css'],
    providers: [
    {
        provide: DateAdapter, useClass: AppDateAdapter
    },
    {
        provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
    }
    ]
})
export class ApproveOheThermovisionMeasuresComponent implements OnInit {
  
  pagination = Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
    inputFormGroup: FormGroup;
    //divisionsList: any;
    depotsData: any = JSON.parse(sessionStorage.getItem('depotData'));
    depotsList: any;
    thermovisionMeasureData = [];
    displayedColumns =['sno','station','location','point1description','measure1','point2description','measure2','tempDiff','remark','check']//,'fixed','criticality','previous1','previous2','previous3','actions'
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
    selectedApprovedPsi = [];
    checked: any;
    depotType = "OHE";

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
            'depotType' : [null],
            'facilityId' : [null],
        });
        
        if(this.divisionsList.length > 0){
            this.checkDivisionUser = true;
        }else {
            this.checkDivisionUser = false;   
        }
        this.getUserContext();
        this.depotsList = this.depotsData.filter(value => {
            return value.depotType == "OHE";
        });
        
    }

    getUserContext(){
        this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_USER_DEFAULT_DATA + this.loggedUserData.username).subscribe((data) => {
                                   this.userDefaultData = data;
                if(this.userDefaultData.division) {
                   this.setDefaultDivision();
                    /*
                    if(this.inputFormGroup.value.division) {
                        this.getDepots();
                    }
                    */
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
    
    saveAction(){
        //console.log('*** llog***'+JSON.stringify(this.selectedApprovedPsi));
        this.sendAndRequestService.requestForPOST(Constants.app_urls.THERMOVISION.THERMOVISION_MEASURE.SAVE_PSI_APPROVE_THERMO_MEASURES,this.selectedApprovedPsi,false).subscribe((response) => {
            this.spinnerService.hide();
            this.resp = response;
            if(this.resp.code == 200 && !!this.resp) {
                this.commonService.showAlertMessage("Measures Data Updated Successfully");
                this.enableSave = false;
                this.checked = false
                this.getThermoMeasuresData();   
            }else
                this.commonService.showAlertMessage("Measures Data Updating Failed");
            
        });
    }
    
    onCheckboxChange(e, row) {
        if (e.target.checked) {
            row.approvedStatus = "Approve";
            row.approveBy = this.loggedUserData.username;
          this.selectedApprovedPsi.push(row);
          this.enableSave = true;
        } else {
          this.selectedApprovedPsi.splice(row.index, 1);
          if (this.selectedApprovedPsi.length == 0) {
            this.enableSave = false;
          }
        }
      }
    
    selectAll(event) {
        for (var i = 0; i < this.thermovisionMeasuresList.length; i++) {
                if(event.target.checked) {
                    this.thermovisionMeasuresList[i].checked = true;
                    this.thermovisionMeasuresList[i].approvedStatus = "Approve";
                    this.thermovisionMeasuresList[i].approveBy = this.loggedUserData.username;
                    this.selectedApprovedPsi.push(this.thermovisionMeasuresList[i]); 
                    this.enableSave = true;   
                }else {
                    this.thermovisionMeasuresList[i].checked = false;
                    this.selectedApprovedPsi = [];
                    this.enableSave = false;
                }
                
            }
    }
    
    getThermoMeasuresData(){
        this.checked = false;
        this.enableSave = false;
        this.thermovisionMeasureData = [];
        this.thermovisionMeasuresList = [];
        this.dataSource = new MatTableDataSource(this.thermovisionMeasuresList);
        this.spinnerService.show();
        this.sendAndRequestService.requestForGET(Constants.app_urls.THERMOVISION.THERMOVISION_MEASURE.GET_NON_APPROVED_OHE_THERMO_MEASURES
            +this.inputFormGroup.value.dateTime+'/'+this.loggedUserData.username+'/'+this.inputFormGroup.value.facilityId
            ).subscribe((data)=>{
               //console.log('infor***'+JSON.stringify(data));
                this.thermovisionMeasureData = data;
                
                if(this.thermovisionMeasureData.length > 0){
                   // this.enableSave = true;
                    for (let i = 0; i < this.thermovisionMeasureData.length; i++) {
                        this.thermovisionMeasureData[i].sno = i+1;
                        this.thermovisionMeasureData[i].index = i
                        this.thermovisionMeasureData[i].tempDiff = this.thermovisionMeasureData[i].fDiff;
                        
                        if(this.thermovisionMeasureData[i].measurePoint1 == 0) {
                           this.thermovisionMeasureData[i].measurePoint1 = ''; 
                        }
                        if(this.thermovisionMeasureData[i].measurePoint2 == 0) {
                           this.thermovisionMeasureData[i].measurePoint2 = ''; 
                        }
                        if(this.thermovisionMeasureData[i].measurePoint1 && this.thermovisionMeasureData[i].measurePoint2 ) {
                           this.thermovisionMeasureData[i].tempDiff = Math.abs(this.thermovisionMeasureData[i].measurePoint1 - this.thermovisionMeasureData[i].measurePoint2).toFixed(2);
                        }
                        
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
    
    getFacilitys(){
      var depotType = this.inputFormGroup.value.depotType ;
        this.getDepots();
        this.inputFormGroup.patchValue( {'facilityId':null} );
        this.inputFormGroup.setErrors({ 'invalid': true });
      this.depotsList = this.depotsData.filter(value => {
        return value.depotType == depotType;
      });
    }
    
    getDepots(){
        //console.log('*** division code ***'+this.inputFormGroup.value.division);
        this.inputFormGroup.patchValue( {'facilityId':null} );
        this.inputFormGroup.setErrors({ 'invalid': true });
        let division = this.inputFormGroup.value.division;
        this.depotsList = this.depotsData.filter(value => {
            return value.division.toLowerCase() == division.toLowerCase() && value.depotType == this.depotType;
         });
    }
    
    reset(){
        this.inputFormGroup.reset();
        this.getUserContext();
        this.depotsList = [];
    }
    
 }
