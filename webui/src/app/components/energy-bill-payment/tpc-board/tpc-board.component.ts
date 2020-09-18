import { OnInit, Component, ViewChild } from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder,Validators} from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import { TPCBoardModel } from 'src/app/models/tpc-board.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { FacilityModel } from 'src/app/models/facility.model';



@Component({
    selector: 'tpc-board',
    templateUrl: './tpc-board.component.html',
    styleUrls: []
})
export class TPCBoardComponent implements OnInit{

    addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    addTPCBoard: boolean ;
    id: number = 0;
    title: string = "Save";
    tpcBoardFormGroup: FormGroup;
    tpcBoardList : any;
    divisionsList:any;
    tpcBoardResponse:any;
    tpcBoardDataSource: MatTableDataSource<TPCBoardModel>;
    tpcBoardDisplayColumns = ['sno' , 'tpcBoard' ,'dataDiv','description', 'id' ] ;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    editTpcBoardResponse: any;
    userHierarchy:any = JSON.parse(localStorage.getItem('userHierarchy'));
    divisionList:  FacilityModel [] = [];    
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;

    constructor(
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private dialog: MatDialog,
        private spinnerService: Ng4LoadingSpinnerService,
        private sendAndRequestService:SendAndRequestService
    ){

    }

    ngOnInit () {
      for (let i = 0; i < this.userHierarchy.length; i++) {
        if(this.userHierarchy[i].depotType == 'DIV'){
          this.divisionList.push(this.userHierarchy[i]);
          console.log("this.divisionList"+this.divisionList)
        }
     }
        this.getAllTPCBoardData();
        this.divisionDetails();
        var permissionName = this.commonService.getPermissionNameByLoggedData("TRD CONFIG","TPC BOARD") ;//p == 0 ? 'No Permission' : p[0].permissionName;
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName); //getPermission("Add", );
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
    }
    duplicateTpcBoard() {
    	const q = new Promise((resolve, reject) => {
            let tpcBoard: string = this.tpcBoardFormGroup.controls['tpcBoard'].value;
            var filter = !!this.tpcBoardList && this.tpcBoardList.filter(tpcBoardCase => {
              return tpcBoardCase.tpcBoard.toLowerCase() == tpcBoard.trim().toLowerCase();
            });
            if (filter.length > 0) {
              resolve({ 'duplicate': true });
            }
	       this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.TPC_BOARD.EXISTS_TPC_BOARD_DATADIV + 
	        this.tpcBoardFormGroup.controls['tpcBoard'].value + '/'+
	        this.tpcBoardFormGroup.controls['dataDiv'].value
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
      duplicateTpcBoardDataDivAndId() {
        let id=this.id;
        let tpcBoard: string = this.tpcBoardFormGroup.controls['tpcBoard'].value;
        let dataDiv: string = this.tpcBoardFormGroup.controls['dataDiv'].value;

        const q = new Promise((resolve, reject) => {          
  
           this.sendAndRequestService.requestForGET(
                  Constants.app_urls.ENERGY_BILL_PAYMENTS.TPC_BOARD.EXISTS_TPC_BOARD_DATADIV_AND_ID+id+'/'+tpcBoard+'/'+dataDiv).subscribe
                  ((duplicate) => {
            if (duplicate) {
              resolve({ 'duplicateTpcBoardDataDivAndId': true });
            } else {
              resolve(null);
            }
          }, () => { resolve({ 'duplicateTpcBoardDataDivAndId': true }); });
        });
        return q;
      }  
     
      public get f() { return this.tpcBoardFormGroup.controls; }
    getAllTPCBoardData() {
        const tpcBoard : TPCBoardModel[] = [];
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.TPC_BOARD.GET_TPC_BOARD).subscribe((data) => {
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
        let dataDiv: string = this.tpcBoardFormGroup.value.dataDiv;
        let description: string = this.tpcBoardFormGroup.value.description

        this.addTPCBoard = false;
        
        if (this.title ==  Constants.EVENTS.SAVE) {
            var saveTPCBoardModel ={
                'tpcBoard':tpcBoard,
                'dataDiv':dataDiv,
                'description':description
            }
            this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.TPC_BOARD.SAVE_TPC_BOARD, saveTPCBoardModel, false).subscribe(data => {
              this.tpcBoardResponse = data;
              if(this.tpcBoardResponse.code == 200 && !!this.tpcBoardResponse) {
                this.commonService.showAlertMessage(this.tpcBoardResponse.message);
                this.getAllTPCBoardData();
                this.tpcBoardFormGroup.reset();
            }else {
                this.commonService.showAlertMessage("tpc Board Data Saving Failed.");
            }
            this.spinnerService.hide();
        } , error => {
            console.log('ERROR >>>');
            this.spinnerService.hide();
            this.commonService.showAlertMessage("tpc Board Data Saving Failed.");
        });
    }else if (this.title == Constants.EVENTS.UPDATE ) {
            let id: number = this.editTpcBoardResponse.id;
            var updateTPCBoardModel ={
                'id':id,
                'tpcBoard':tpcBoard,
                'dataDiv':dataDiv,
                'description':description,
            }
            this.sendAndRequestService.requestForPUT(Constants.app_urls.ENERGY_BILL_PAYMENTS.TPC_BOARD.UPDATE_TPC_BOARD, updateTPCBoardModel, false).subscribe(data => {
              this.tpcBoardResponse = data;
              if(this.tpcBoardResponse.code == 200 && !!this.tpcBoardResponse) {
                  this.commonService.showAlertMessage(this.tpcBoardResponse.message);
                  this.getAllTPCBoardData();
                  this.tpcBoardFormGroup.reset();
                  this.addTPCBoard =  false;
              }else {
                  this.commonService.showAlertMessage("tpc Board Data Updating Failed.");
              }
          } , error => {
              console.log('ERROR >>>');
              this.spinnerService.hide();
              this.commonService.showAlertMessage("tpc Board  Data Updating Failed.");
          });
          
      }
  }

    editTPCBoard (id) {
        this.addTPCBoard = true;
        this.tpcBoardEditAction(id);
        this.title = 'Update';
    }

    tpcBoardEditAction(id: number) {
        this.tpcBoardFormGroup = this.formBuilder.group({
            id: 0,
            'tpcBoard':[null, Validators.compose([Validators.required, Validators.maxLength(250)])],
            'dataDiv':[null,Validators.required, this.duplicateTpcBoardDataDivAndId.bind(this)],
            'description':[null,Validators.maxLength(250)]
            
        });
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.TPC_BOARD.GET_TPC_BOARD_ID+id).subscribe((responseData) => {
            this.editTpcBoardResponse = responseData;
            this.tpcBoardFormGroup.patchValue({
                id: this.editTpcBoardResponse.id,
                tpcBoard:this.editTpcBoardResponse.tpcBoard,
                dataDiv:this.editTpcBoardResponse.dataDiv,
                description:this.editTpcBoardResponse.description

            })
        } ,error => {})
        this.id=id;
        if (!isNaN(this.id)) {
            this.title = 'Update';
          } else {
            this.title = 'Save';      
          }
    }


    deleteTPCBoard (id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected tpcBoard?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
                this.sendAndRequestService.requestForDELETE(Constants.app_urls.ENERGY_BILL_PAYMENTS.TPC_BOARD.DELETE_TPC_BOARD, id).subscribe(response => {
                        this.commonService.showAlertMessage('TPCBoard Deleted Successfully');
                        this.getAllTPCBoardData();
                    },error => {});
            }
        });
    }
    divisionDetails()
    {
          
           this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_DIVISION_DETAILS).subscribe((data) => {
             this.divisionsList = data;
    }
           );

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
        this.tpcBoardFormGroup = this.formBuilder.group({
            id: 0,
            'tpcBoard':[null, Validators.compose([Validators.required, Validators.maxLength(250)])],
            'dataDiv':[null,Validators.required, this.duplicateTpcBoard.bind(this)],
            'description':[null,Validators.maxLength(250)]
            
        });
    }

}