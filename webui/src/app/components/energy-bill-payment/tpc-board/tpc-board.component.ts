import { OnInit, Component, ViewChild } from '@angular/core';
import { TPCBoardService } from 'src/app/services/tpc-board.service';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import { TPCBoardModel } from 'src/app/models/tpc-board.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';

@Component({
    selector: 'tpc-board',
    templateUrl: './tpc-board.component.html',
    styleUrls: ['./tpc-board.component.scss']
})
export class TPCBoardComponent implements OnInit{

    addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    addTPCBoard: boolean ;
    title: string = "Save";
    tpcBoardFormGroup: FormGroup;
    tpcBoardList : any;
    tpcBoardDataSource: MatTableDataSource<TPCBoardModel>;
    tpcBoardDisplayColumns = ['sno' , 'tpcBoard' ,'description', 'id' ] ;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    editTpcBoardResponse: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;


    constructor(
        private tpcBoardService: TPCBoardService,
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private dialog: MatDialog
    ){

    }

    ngOnInit () {
        console.log('in ngOnintit method:::');
        this.getAllTPCBoardData();
        var permissionName = this.commonService.getPermissionNameByLoggedData("ENERGY BILL PAYMENT","TPC BOARD") ;//p == 0 ? 'No Permission' : p[0].permissionName;
  		console.log("permissionName = "+permissionName);
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName); //getPermission("Add", );
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
        this.tpcBoardFormGroup = this.formBuilder.group({
            id: 0,
            'tpcBoard':[null],
            'description':[null]
            
        });
    }

    getAllTPCBoardData() {
        console.log("get all guidence items");
        const tpcBoard : TPCBoardModel[] = [];
        this.tpcBoardService.getAllTPCBoardDetails().subscribe((data) => {
            this.tpcBoardList = data;
            for (let i = 0; i < this.tpcBoardList.length; i++) {
                this.tpcBoardList[i].sno = i+1;
                tpcBoard.push(this.tpcBoardList[i]);              
            }
            this.tpcBoardDataSource = new MatTableDataSource(tpcBoard);
            this.tpcBoardDataSource.paginator = this.paginator;
            this.tpcBoardDataSource.sort = this.sort;

        } , error => {});

    }

    tpcBoardSubmit () {
        let tpcBoard: string = this.tpcBoardFormGroup.value.tpcBoard;
        let description: string = this.tpcBoardFormGroup.value.description

        this.addTPCBoard = false;
        
        if (this.title ==  Constants.EVENTS.SAVE) {
            this.tpcBoardService.saveTPCBoard({
                'tpcBoard':tpcBoard,
                'description':description
            }).subscribe((data) => {
                this.commonService.showAlertMessage('Successfully saved');
                this.getAllTPCBoardData();
                this.tpcBoardFormGroup.reset();
            } , error => {});
        }else if (this.title == Constants.EVENTS.UPDATE ) {
            let id: number = this.editTpcBoardResponse.id;
            this.tpcBoardService.updateTPCBoard({
                'id':id,
                'tpcBoard':tpcBoard,
                'description':description,
            }).subscribe((data) =>{
                this.commonService.showAlertMessage('Successfully updated');
                this.getAllTPCBoardData();
                this.tpcBoardFormGroup.reset();
                this.addTPCBoard =  false;
            } , error => {})
            
        }
    }

    editTPCBoard (id) {
        this.addTPCBoard = true;
        this.tpcBoardEditAction(id);
        this.title = 'Update';
    }

    tpcBoardEditAction(id: number) {
        this.tpcBoardService.findTPCBoardById(id).subscribe((responseData) => {
            this.editTpcBoardResponse = responseData;
            this.tpcBoardFormGroup.patchValue({
                id: this.editTpcBoardResponse.id,
                tpcBoard:this.editTpcBoardResponse.tpcBoard,
                description:this.editTpcBoardResponse.description

            })
        } ,error => {})
    }


    deleteTPCBoard (id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected tpcBoard?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
                this.tpcBoardService.deleteTPCBoardById(id)
                    .subscribe((data) => {
                        this.commonService.showAlertMessage('TPCBoard Deleted Successfully');
                        this.getAllTPCBoardData();
                    },error => {});
            }
        });
    }


    applyFilter(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase();
        this.tpcBoardDataSource.filter = filterValue;
    }

    onGoBack() {
        this.tpcBoardFormGroup.reset();
        this.addTPCBoard = false;
        this.title = 'Save';
    }

    NewTPCBoard () {
        this.addTPCBoard = true;
    }

}