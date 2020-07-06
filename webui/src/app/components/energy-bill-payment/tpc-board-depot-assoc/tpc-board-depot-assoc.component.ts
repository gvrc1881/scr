import { OnInit, Component, ViewChild } from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import { TPCBoardDepotAssocModel } from 'src/app/models/tpc-board-depot-assoc.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';

@Component({
    selector: 'tpc-board-depot-assoc',
    templateUrl: './tpc-board-depot-assoc.component.html',
    styleUrls: ['./tpc-board-depot-assoc.component.scss']
})
export class TPCBoardDepotAssocComponent implements OnInit{

    addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    addTPCBoardDepotAssoc: boolean ;
    title: string = "Save";
    tpcBoardDepotAssocFormGroup: FormGroup;
    tpcBoardDepotAssocList : any;
    facilityData:any;
    tpcBoardDepotAssocDataSource: MatTableDataSource<TPCBoardDepotAssocModel>;
    tpcBoardDepotAssocDisplayColumns = ['sno' , 'tpcBoard' ,'unitType','unitName','description', 'id' ] ;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    editTpcBoardDepotAssocResponse: any;
    tpcBoardDepotAssocFormErrors: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    funLocTypeData: any;
    tpcBoardData:any;

    constructor(
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private dialog: MatDialog,
        private sendAndRequestService:SendAndRequestService

    ){

   

    }

    ngOnInit () {
        this.getAllTPCBoardDepotAssocData();
        this.tpcBoardDetails();
        var permissionName = this.commonService.getPermissionNameByLoggedData("TRD CONFIG","TPC Board Assoc") ;//p == 0 ? 'No Permission' : p[0].permissionName;
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName); 
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
        this.tpcBoardDepotAssocFormGroup = this.formBuilder.group({
            id: 0,
            'tpcBoard':[null, Validators.compose([Validators.required])],
            'unitType':[null],
            'unitName':[null,Validators.required, this.duplicateTpcBoard.bind(this)],
            'description':[null, Validators.maxLength(250)],
            
        });
        this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FUNCTIONAL_LOCATION_TYPES).subscribe((data) => {
            this.funLocTypeData = data;
         });
}
duplicateTpcBoard() {
    const q = new Promise((resolve, reject) => {
       this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.TPC_BOARD_ASSOC.EXISTS_TPC_BOARD_UNIT_NAME +
        this.tpcBoardDepotAssocFormGroup.controls['tpcBoard'].value + '/'+
        this.tpcBoardDepotAssocFormGroup.controls['unitName'].value
      ).subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicate': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicate': true }); });
    });
    return q;
  }    
  public get f() { return this.tpcBoardDepotAssocFormGroup.controls; }

    getAllTPCBoardDepotAssocData() {
       
        const tpcBoardDepotAssoc : TPCBoardDepotAssocModel[] = [];
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.TPC_BOARD_ASSOC.GET_TPC_BOARD_ASSOC).subscribe((data) => {
            this.tpcBoardDepotAssocList = data;
            for (let i = 0; i < this.tpcBoardDepotAssocList.length; i++) {
                this.tpcBoardDepotAssocList[i].sno = i+1;
                tpcBoardDepotAssoc.push(this.tpcBoardDepotAssocList[i]);              
            }
            this.tpcBoardDepotAssocDataSource = new MatTableDataSource(tpcBoardDepotAssoc);
            this.tpcBoardDepotAssocDataSource.paginator = this.paginator;
            this.tpcBoardDepotAssocDataSource.sort = this.sort;

        } , error => {});

    }

    tpcBoardDepotAssocSubmit () {
        let tpcBoard: string = this.tpcBoardDepotAssocFormGroup.value.tpcBoard;
        let unitType: string = this.tpcBoardDepotAssocFormGroup.value.unitType;
        let unitName: string = this.tpcBoardDepotAssocFormGroup.value.unitName;
        let description: string = this.tpcBoardDepotAssocFormGroup.value.description

        this.addTPCBoardDepotAssoc = false;
        
        if (this.title ==  Constants.EVENTS.SAVE) {
            var saveTPCBoardAssocModel ={
                'tpcBoard':tpcBoard,
                'unitType':unitType,
                'unitName':unitName,
                'description':description
            }
            this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.TPC_BOARD_ASSOC.SAVE_TPC_BOARD_ASSOC, saveTPCBoardAssocModel, false).subscribe(response => {
                this.commonService.showAlertMessage('Successfully saved');
                this.getAllTPCBoardDepotAssocData();
                this.tpcBoardDepotAssocFormGroup.reset();
            } , error => {});
        }else if (this.title == Constants.EVENTS.UPDATE ) {
            let id: number = this.editTpcBoardDepotAssocResponse.id;
            var updateTPCBoardAssocModel={
                'id':id,
                'tpcBoard':tpcBoard,
                'unitType':unitType,
                'unitName':unitName,
                'description':description
            }       
            this.sendAndRequestService.requestForPUT(Constants.app_urls.ENERGY_BILL_PAYMENTS.TPC_BOARD_ASSOC.UPDATE_TPC_BOARD_ASSOC, updateTPCBoardAssocModel, false).subscribe(response => {
                this.commonService.showAlertMessage('Successfully updated');
                this.getAllTPCBoardDepotAssocData();
                this.tpcBoardDepotAssocFormGroup.reset();
                this.addTPCBoardDepotAssoc =  false;
            } , error => {})
            
        }
    }

    editTPCBoardDepotAssoc (id) {
        this.addTPCBoardDepotAssoc = true;
        this.tpcBoardDepotAssocEditAction(id);
        this.title = 'Update';
    }

    tpcBoardDepotAssocEditAction(id: number) {
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.TPC_BOARD_ASSOC.GET_TPC_BOARD_ASSOC_ID+'/'+id).subscribe((responseData) => {
            this.editTpcBoardDepotAssocResponse = responseData;
            this.tpcBoardDepotAssocFormGroup.patchValue({
                id: this.editTpcBoardDepotAssocResponse.id,
                tpcBoard:this.editTpcBoardDepotAssocResponse.tpcBoard,
                unitType:this.editTpcBoardDepotAssocResponse.unitType,
                unitName:this.editTpcBoardDepotAssocResponse.unitName,
                description:this.editTpcBoardDepotAssocResponse.description

            })
        } ,error => {})
    }


    deleteTPCBoardDepotAssoc (id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected tpcBoard Depot Assoc?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
                this.sendAndRequestService.requestForDELETE(Constants.app_urls.ENERGY_BILL_PAYMENTS.TPC_BOARD_ASSOC.DELETE_TPC_BOARD_ASSOC, id).subscribe(response => {
                        this.commonService.showAlertMessage('TPCBoard Depot Assoc Deleted Successfully');
                        this.getAllTPCBoardDepotAssocData();
                    },error => {});
            }
        });
    }

    applyFilter(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase();
        this.tpcBoardDepotAssocDataSource.filter = filterValue;
    }
    getFacilitys(){
    	var unitType = this.tpcBoardDepotAssocFormGroup.value.unitType ;
    	this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_ASSETTYPE_BASED_ON_ASSETID_FACILITYID + unitType).subscribe((data) => {
                 this.facilityData = data;
        		});
    }
    tpcBoardDetails()
    {
            
           this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_TPC_BOARD_DETAILS).subscribe((data) => {
             this.tpcBoardData = data;
    }
           );

   }
    onGoBack() {
        this.tpcBoardDepotAssocFormGroup.reset();
        this.addTPCBoardDepotAssoc = false;
        this.title = 'Save';
    }

    NewTPCBoardDepotAssoc () {
        this.addTPCBoardDepotAssoc = true;
    }

}