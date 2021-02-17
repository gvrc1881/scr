import { OnInit, Component, ViewChild } from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import { TPCBoardDepotAssocModel } from 'src/app/models/tpc-board-depot-assoc.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { DataViewDialogComponent } from '../../data-view-dialog/data-view-dialog.component';

import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';




@Component({
    selector: 'tpc-board-depot-assoc',
    templateUrl: './tpc-board-depot-assoc.component.html',
    styleUrls: []
})
export class TPCBoardDepotAssocComponent implements OnInit{

    pagination =Constants.PAGINATION_NUMBERS;
    FiledLabels = FieldLabelsConstant.LABELS;
    Titles = FieldLabelsConstant.TITLE;
    addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    addTPCBoardDepotAssoc: boolean ;
    title: string = Constants.EVENTS.ADD;
    tpcBoardDepotAssocFormGroup: FormGroup;
    tpcBoardDepotAssocList : any;
    id: number = 0;
    facilityData:any;
    loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
    originalDepotsData: any = JSON.parse(sessionStorage.getItem('depotData'));

    depotsList:any;


   distinctDepotType:any = this.originalDepotsData.map(item => item.depotType)
   .filter((value, index, self) => self.indexOf(value) === index)

    tpcBoardDepotAssocDataSource: MatTableDataSource<TPCBoardDepotAssocModel>;
    tpcBoardDepotAssocDisplayColumns = ['sno' , 'tpcBoard' ,'unitType','unitName','description', 'id' ] ;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    editTpcBoardDepotAssocResponse: any;
    tpcBoardDepotAssocFormErrors: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
    funLocTypeData: any;
    tpcBoardData:any;
    facilityNames:any
    unitType: any;
    divisionList:any;

    constructor(
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private dialog: MatDialog,
        private sendAndRequestService:SendAndRequestService

    ){

   


    }

    ngOnInit () {
        this.getAllTPCBoardDepotAssocData();
        this.divisionData();
        this.getFacilityNames();
        var permissionName = this.commonService.getPermissionNameByLoggedData("TRD CONFIG","TPC Board Assoc") ;//p == 0 ? 'No Permission' : p[0].permissionName;
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName); 
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
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
  duplicateTpcBoardUnitNameAndId() {
    let id=this.id;
    let tpcBoard: string = this.tpcBoardDepotAssocFormGroup.controls['tpcBoard'].value;
    let unitName: string = this.tpcBoardDepotAssocFormGroup.controls['unitName'].value;

    const q = new Promise((resolve, reject) => {          

       this.sendAndRequestService.requestForGET(
              Constants.app_urls.ENERGY_BILL_PAYMENTS.TPC_BOARD_ASSOC.EXISTS_TPC_BOARD_UNIT_NAME_AND_ID+id+'/'+tpcBoard+'/'+unitName).subscribe
              ((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateTpcBoardUnitNameAndId': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateTpcBoardUnitNameAndId': true }); });
    });
    return q;
  }    
  public get f() { return this.tpcBoardDepotAssocFormGroup.controls; }

    getAllTPCBoardDepotAssocData() {
       
        const tpcBoardDepotAssoc : TPCBoardDepotAssocModel[] = [];
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.TPC_BOARD_ASSOC.GET_TPC_BOARD_ASSOC_BASED_ON_DIV+this.loggedUserData.username).subscribe((data) => {
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
        
        if (this.title ==  Constants.EVENTS.ADD) {
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
        this.title = Constants.EVENTS.UPDATE;
    }

    tpcBoardDepotAssocEditAction(id: number) {
        this.tpcBoardDepotAssocFormGroup = this.formBuilder.group({
            id: 0,
            'tpcBoard':[null, Validators.compose([Validators.required])],
            'unitType':[null],
            'unitName':[null,Validators.required,this.duplicateTpcBoardUnitNameAndId.bind(this)],
            'description':[null, Validators.maxLength(250)],
            
        });
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.TPC_BOARD_ASSOC.GET_TPC_BOARD_ASSOC_ID+id).subscribe((responseData) => {
            this.editTpcBoardDepotAssocResponse = responseData;
            this.tpcBoardDepotAssocFormGroup.patchValue({
                id: this.editTpcBoardDepotAssocResponse.id,
                tpcBoard:this.editTpcBoardDepotAssocResponse.tpcBoard,
                unitType:this.editTpcBoardDepotAssocResponse.unitType,
                unitName:this.editTpcBoardDepotAssocResponse.unitName,
                description:this.editTpcBoardDepotAssocResponse.description

            })
        } ,error => {})
        this.id=id;
        if (!isNaN(this.id)) {
            this.title = Constants.EVENTS.UPDATE;
          } else {
            this.title = Constants.EVENTS.ADD;      
          }
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
      this.depotsList = this.originalDepotsData.filter(value => {
        return value.depotType == unitType;
      });
    }

    getFacilityNames(){
        this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY_NAMES).subscribe((data) => {
            this.facilityNames = data;
           });
    }
    tpcBoardDetails(){
        let dataDiv=this.divisionList.division
         this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_TPC_BOARD_BASED_ON_DIVISION+dataDiv).subscribe((data) => {
           this.tpcBoardData = data;
           console.log("tpcBoard"+JSON.stringify(data))
     }
         );
   
      }
      divisionData(){
        var username=this.loggedUserData.username
       this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_DIVISION_BASED_ON_LOGIN_USER+username).subscribe((data) => {
         this.divisionList = data;
         if(this.divisionList){
           this.tpcBoardDetails();
         }
   }
       );
      
      }
    onGoBack() {
        this.tpcBoardDepotAssocFormGroup.reset();
        this.addTPCBoardDepotAssoc = false;
        this.title = Constants.EVENTS.ADD;
    }
    
    NewTPCBoardDepotAssoc () {
        this.tpcBoardDepotAssocFormGroup = this.formBuilder.group({
            id: 0,
            'tpcBoard':[null, Validators.compose([Validators.required])],
            'unitType':[null],
            'unitName':[null,Validators.required, this.duplicateTpcBoard.bind(this)],
            'description':[null, Validators.maxLength(250)],
            
        });
        this.addTPCBoardDepotAssoc = true;
    }
    ViewData(data){
        var result = {
          'title':this.Titles.TPC_BOARD_ASSOC_DATA,
          'dataSource':[
           
                        { label:FieldLabelsConstant.LABELS.TPC_BOARD, value:data.tpcBoard },
                        { label:FieldLabelsConstant.LABELS.UNIT_TYPE, value:data.unitType },
                        { label:FieldLabelsConstant.LABELS.UNIT_NAME, value:data.unitName },
                        { label:FieldLabelsConstant.LABELS.DESCRIPTION, value:data.description }
                        
                    
                    ]
        }
        this.dataViewDialogRef = this.dialog.open(DataViewDialogComponent, {
          disableClose: false,
          height: '400px',
          width: '80%',       
          data:result,  
        });            
      }
}