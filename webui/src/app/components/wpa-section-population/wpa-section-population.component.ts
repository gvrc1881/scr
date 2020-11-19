import { OnInit, Component, ViewChild,ElementRef } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatTableDataSource, MatPaginator, MatSort, MatDialog, MatDialogRef } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { environment } from './../../../environments/environment';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { WPASectionPopulationModel } from 'src/app/models/works.model';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';

@Component({
    selector: 'wpa-section-population',
    templateUrl: './wpa-section-population.component.html',
    styleUrls: ['./wpa-section-population.component.css']
})
export class WPASectionPopulationComponent implements OnInit {
        
    FiledLabels = FieldLabelsConstant.LABELS;
    workList: any;
    workGroupList: any;
    workPhaseList: any;
    inputFormGroup: FormGroup;
    WPASectionPopulationList =  [];
    dataSource: MatTableDataSource<WPASectionPopulationModel>;
    displayedColumns = ['sno','section', 'activity', 'population','uom'];
    enableSave: boolean;
    
    constructor(
        public dialog: MatDialog,
        private formBuilder: FormBuilder,
        private spinnerService: Ng4LoadingSpinnerService,
        private commonService: CommonService,
        private sendAndRequestService:SendAndRequestService        
    ) {
    }
        
    ngOnInit() {
       this.inputFormGroup = this.formBuilder.group({
            'work': [null , Validators.required ],
            'workGroup' : [null , Validators.required ],
            'workPhase' : [null , Validators.required ],
        });
      this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WORK).subscribe((data) => {
            this.workList = data;
             //console.log('*** length ***'+this.workList.length);
        },error => {} );   
    }
    
    saveAction() {
       this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.SAVE_WPA_SECTION_POPULATION,this.WPASectionPopulationList,false).subscribe((response) => {
            this.spinnerService.hide();
            this.commonService.showAlertMessage("Population Updated Successfully");
       })     
    }
    
    getWPASectionPopulation(){
        this.WPASectionPopulationList = [];
        this.enableSave = false;
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WPA_SECTION_POPULATION_BASED_ON_GROUP_ACTIVITY
            +this.inputFormGroup.value.work.id +'/' +this.inputFormGroup.value.workGroup.id
            +'/'+this.inputFormGroup.value.workPhase.id 
            ).subscribe((response) => {
                this.WPASectionPopulationList = response;
                if(this.WPASectionPopulationList.length > 0) {
                    for(let i =0 ; i < this.WPASectionPopulationList.length ; i++ ){
                        this.WPASectionPopulationList[i].sno = i+1;
                    }
                    this.enableSave = true;
                }
                 this.dataSource = new MatTableDataSource(this.WPASectionPopulationList);
        }); 
    }
    
    getWorkGroups() {
        this.sendAndRequestService.requestForGET( Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WORK_GROUPS_BASED_ON_WORK + this.inputFormGroup.value.work.id ).subscribe((response) => {
           this.workGroupList = response;
        });
        this.sendAndRequestService.requestForGET( Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WORK_PHASES_BASED_ON_WORK + this.inputFormGroup.value.work.id ).subscribe((response) => {
           this.workPhaseList = response;
        });
    }
    
    
    
    
}