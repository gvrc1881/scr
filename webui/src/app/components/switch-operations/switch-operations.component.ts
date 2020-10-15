import { Component, OnInit } from '@angular/core';
import * as core from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder} from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import { PbSwitchModel } from 'src/app/models/pb-switch.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { DataViewDialogComponent } from '../data-view-dialog/data-view-dialog.component';

@Component({
    selector: 'app-switch-operations',
    templateUrl: './switch-operations.component.html',
    styleUrls: ['./switch-operations.component.scss']
})
export class SwitchOperationsComponent implements OnInit {
    addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    id: number = 0;
    title: string = "Save";
    switchOperationsFormGroup: FormGroup;
    switchOperationsList : any;
    facilityData:any;
    public dialogRef: MatDialogRef<SwitchOperationsComponent>;
    switchOperationsDataSource: MatTableDataSource<PbSwitchModel>;
    switchOperationsDisplayColumns = ['sno' ,'switchType','switchId' , 'isNormallOpened' , 'id' ] ;
    @core.ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @core.ViewChild(MatSort, { static: true }) sort: MatSort;
    editSwitchOperationsResponse: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;

    constructor( 
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private dialog: MatDialog,
        private sendAndRequestService:SendAndRequestService
    ){
        
    }
    
    ngOnInit () {
      this.getAllPbSwitchData();
      var permissionName = this.commonService.getPermissionNameByLoggedData("REGISTER","PB SWITCH OPERATIONS") ;
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName); 
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
        this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);  
        this.switchOperationsFormGroup = this.formBuilder.group({
            id: 0,
            'switchType':[null],
            'switchId':[null],
            'isNormallOpened': ['false'],
            
        });  
    }
      
      
      public get f() { return this.switchOperationsFormGroup.controls; }

    getAllPbSwitchData() {
        const pbSwitch : PbSwitchModel[] = [];
            this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.PBSWITCH.GET_SWITCH).subscribe((data) => {
            this.switchOperationsList = data;
            for (let i = 0; i < this.switchOperationsList.length; i++) {
                this.switchOperationsList[i].sno = i+1;
                pbSwitch.push(this.switchOperationsList[i]);              
            }
            this.switchOperationsDataSource = new MatTableDataSource(pbSwitch);
            this.switchOperationsDataSource.paginator = this.paginator;
            this.switchOperationsDataSource.sort = this.sort;

        } , error => {});

    }

    switchOperationsSubmit () {
        let switchType: string = this.switchOperationsFormGroup.value.switchType;
        let switchId: string = this.switchOperationsFormGroup.value.switchId;
        let isNormallOpened: string = this.switchOperationsFormGroup.value.isNormallOpened;
        
        if (this.title ==  Constants.EVENTS.SAVE) {
                var saveSwitchModel ={
                    'switchType':switchType,
                    'switchId':switchId,
                    'isNormallOpened': isNormallOpened
                } 
                this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.FP_SECTIONS.SAVE_FP_SECTIONS, saveSwitchModel, false).subscribe(response => {
                  this.commonService.showAlertMessage('Successfully saved');
                this.getAllPbSwitchData();
                this.switchOperationsFormGroup.reset();
            } , error => {});
        }else if (this.title == Constants.EVENTS.UPDATE ) {
            let id: number = this.editSwitchOperationsResponse.id;
            var updateSwitchModel={
                'id':id,
                'switchType':switchType,
                'switchId':switchId,
                'isNormallOpened': isNormallOpened
            }
            this.sendAndRequestService.requestForPUT(Constants.app_urls.ENERGY_BILL_PAYMENTS.PBSWITCH.UPDATE_SWITCH, updateSwitchModel, false).subscribe(response => {
                this.commonService.showAlertMessage('Successfully updated');
                this.getAllPbSwitchData();
                this.switchOperationsFormGroup.reset();
            } , error => {})
            
        }
    }

    editSwitchItem (id) {
        this.switchEditAction(id);
        this.title = 'Update';
    }

    switchEditAction(id: number) {
        this.switchOperationsFormGroup = this.formBuilder.group({
            id: 0,
            'switchType':[null],
            'switchId':[null],
            'isNormallOpened': ['false']
        });
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.PBSWITCH.GET_SWITCH_ID+id).subscribe((responseData) => {
            this.editSwitchOperationsResponse = responseData;
            this.switchOperationsFormGroup.patchValue({
                id: this.editSwitchOperationsResponse.id,
                switchType:this.editSwitchOperationsResponse.switchType,
                switchId:this.editSwitchOperationsResponse.switchId,
                isNormallOpened: this.editSwitchOperationsResponse.isNormallOpened,
            });
        } ,error => {})
        this.id=id;
        if (!isNaN(this.id)) {
            this.title = 'Update';
          } else {
            this.title = 'Save';      
          }
    }


    deleteSwitchItem (id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected Switch item?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
                this.sendAndRequestService.requestForDELETE(Constants.app_urls.ENERGY_BILL_PAYMENTS.PBSWITCH.DELETE_SWITCH, id).subscribe(response => {
                        this.commonService.showAlertMessage('Switch Deleted Successfully');
                        this.getAllPbSwitchData();
                    },error => {});
            }
        });
    }


    applyFilter(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase();
        this.switchOperationsDataSource.filter = filterValue;
    }

    
    

    public pbSwitchType = ['REMOTE', 'MANUAL'];

}