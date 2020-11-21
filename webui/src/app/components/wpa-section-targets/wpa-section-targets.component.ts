import { OnInit, Component, ViewChild,ElementRef } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatTableDataSource, MatPaginator, MatSort, MatDialog, MatDialogRef } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { environment } from './../../../environments/environment';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { WPASectionTargetsModel } from 'src/app/models/works.model';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';

@Component({
    selector: 'wpa-section-targets',
    templateUrl: './wpa-section-targets.component.html',
    styleUrls: ['./wpa-section-targets.component.css']
})
export class WPASectionTargetsComponent implements OnInit {
        
    FiledLabels = FieldLabelsConstant.LABELS;
    workList: any;
    workGroupList: any;
    workPhaseList: any;
    inputFormGroup: FormGroup;
    WPASectionTargetsList =  [];
    dataSource: MatTableDataSource<WPASectionTargetsModel>;
    displayedColumns = ['sno','activity', 'population','uom','apr','may','jun','jul','aug','sep','oct','nov','dec','jan','feb','mar'];
    enableSave: boolean;
    res: any;
    
    
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
            'year' : [null , Validators.required ]
        });
      this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WORK).subscribe((data) => {
            this.workList = data;
             //console.log('*** length ***'+this.workList.length);
        },error => {} );   
    }
    
    saveAction() {
       this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.SAVE_WPA_SECTION_TARGETS,this.WPASectionTargetsList,false).subscribe((response) => {
            this.spinnerService.hide();
           this.res = response;
           if( this.res.code == 200 ){
                this.commonService.showAlertMessage("Targets Updated Successfully");
           } else {
               this.commonService.showAlertMessage("Targets Updation failed");
           }    
       })     
    }
    
    getWPASectionPopulation(){
        this.WPASectionTargetsList = [];
        this.enableSave = false;
        this.dataSource = new MatTableDataSource(this.WPASectionTargetsList);
       // this.spinnerService.show();
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WPA_SECTION_Targets_BASED_ON_GROUP_ACTIVITY_YEAR
            +this.inputFormGroup.value.work.id +'/' +this.inputFormGroup.value.workGroup.id
            +'/'+this.inputFormGroup.value.workPhase.id  +'/'+ this.inputFormGroup.value.year
            ).subscribe((response) => {
                this.WPASectionTargetsList = response;
                //console.log('** rseponse **'+JSON.stringify(this.WPASectionTargetsList));
                if(this.WPASectionTargetsList.length > 0) {
                    for(let i =0 ; i < this.WPASectionTargetsList.length ; i++ ){
                        this.WPASectionTargetsList[i].sno = i+1;
                       // console.log('*** aprial***'+this.WPASectionTargetsList[i].apr);
                    }
                    this.enableSave = true;
                }
                 this.dataSource = new MatTableDataSource(this.WPASectionTargetsList);
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