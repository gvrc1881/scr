import { OnInit, Component, ViewChild } from '@angular/core';
import { TPCBoardDepotAssocService } from 'src/app/services/tpc-board-depot-assoc.service';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import { ReportService } from 'src/app/services/report.service';
import { TPCBoardDepotAssocModel } from 'src/app/models/tpc-board-depot-assoc.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { FacilityModel } from 'src/app/models/facility.model';

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
    facilityData:FacilityModel;
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
        private tpcBoardDepotAssocService: TPCBoardDepotAssocService,
        private reportService:ReportService,
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private dialog: MatDialog
    ){

   

    }

    ngOnInit () {
        console.log('in ngOnintit method:::');
        this.getAllTPCBoardDepotAssocData();
        this.tpcBoardDetails();
        var permissionName = this.commonService.getPermissionNameByLoggedData("ENERGY BILL PAYMENT","TPC Board Assoc") ;//p == 0 ? 'No Permission' : p[0].permissionName;
  		console.log("permissionName = "+permissionName);
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName); 
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
        this.tpcBoardDepotAssocFormGroup = this.formBuilder.group({
            id: 0,
            'tpcBoard':[null, Validators.compose([Validators.required]), this.duplicateTpcBoard.bind(this)],
            'unitType':[null],
            'unitName':[null, Validators.compose([Validators.required]), this.duplicateDepot.bind(this)],
            'description':[null, Validators.maxLength(250)],
            
        });
        this.reportService.functionalLocationTypes().subscribe((data) => {
            this.funLocTypeData = data;
         });
}
duplicateTpcBoard() {
    const q = new Promise((resolve, reject) => {
      this.tpcBoardDepotAssocService.existsByTpcBoard(
        this.tpcBoardDepotAssocFormGroup.controls['tpcBoard'].value
      ).subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateTpcBoard': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateTpcBoard': true }); });
    });
    return q;
  }

  duplicateDepot() {
    const q = new Promise((resolve, reject) => {
      this.tpcBoardDepotAssocService.existsUnitName(
        this.tpcBoardDepotAssocFormGroup.controls['unitName'].value
      ).subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateDepot': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateDepot': true }); });
    });
    return q;
  }

    getAllTPCBoardDepotAssocData() {
       
        const tpcBoardDepotAssoc : TPCBoardDepotAssocModel[] = [];
        this.tpcBoardDepotAssocService.getAllTPCBoardDepotAssocDetails().subscribe((data) => {
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
            this.tpcBoardDepotAssocService.saveTPCBoardDepotAssoc({
                'tpcBoard':tpcBoard,
                'unitType':unitType,
                'unitName':unitName,
                'description':description
            }).subscribe((data) => {
                this.commonService.showAlertMessage('Successfully saved');
                this.getAllTPCBoardDepotAssocData();
                this.tpcBoardDepotAssocFormGroup.reset();
            } , error => {});
        }else if (this.title == Constants.EVENTS.UPDATE ) {
            let id: number = this.editTpcBoardDepotAssocResponse.id;
            this.tpcBoardDepotAssocService.updateTPCBoardDepotAssoc({
                'id':id,
                'tpcBoard':tpcBoard,
                'unitType':unitType,
                'unitName':unitName,
                'description':description,
            }).subscribe((data) =>{
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
        this.tpcBoardDepotAssocService.findTPCBoardDepotAssocById(id).subscribe((responseData) => {
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
                this.tpcBoardDepotAssocService.deleteTPCBoarDepotAssocdById(id)
                    .subscribe((data) => {
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
    	this.reportService.getFacilitysBasedOnDepotType(unitType).subscribe((data) => {
                 this.facilityData = data;
        		});
    }
    tpcBoardDetails()
    {
            
           this.reportService. tpcBoardDetails().subscribe((data) => {
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