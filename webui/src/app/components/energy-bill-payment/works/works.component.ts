import { Component, OnInit , ViewChild,ElementRef} from '@angular/core';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { Constants } from 'src/app/common/constants';
import { MatTableDataSource, MatDialogRef, MatDialog, MatPaginator, MatSort } from '@angular/material';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { WorksModel } from 'src/app/models/works.model';
import { CommonService } from 'src/app/common/common.service';
import { WorksPayload } from 'src/app/payloads/works.payload';
import { FacilityModel } from 'src/app/models/facility.model';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { DataViewDialogComponent } from '../../data-view-dialog/data-view-dialog.component';

@Component({
    selector: 'works',
    templateUrl: './works.component.html',
    styleUrls: []
})
export class WorksComponent implements OnInit {

    pagination = Constants.PAGINATION_NUMBERS;
    FiledLabels = FieldLabelsConstant.LABELS;
    Titles = FieldLabelsConstant.TITLE;
	  addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    workFormGroup: FormGroup;
    addWork: boolean = false;
    id: number = 0;
    title: string = Constants.EVENTS.ADD;
    workList: any;
    editWorkResponse: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    displayedColumns = ['sno' ,  'division'  , 'workName' , 'section' , 'executedBy' , 'physicalProgressPercentage' , 'latestRevisedCost' , 'actions' ];
    loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
    statusItems: any;
    execAgencyList: any;
    userHierarchy:any = JSON.parse(sessionStorage.getItem('userHierarchy'));
    divisionList:  FacilityModel [] = [];
    workResponse: any;
    currentYear: any;
    filterData;
    divisionData:any;
    gridData = [];
    dataSource: MatTableDataSource<WorksModel>;
  	@ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  	@ViewChild(MatSort, { static: true }) sort: MatSort;
  	@ViewChild('filter', { static: true }) filter: ElementRef;  	
    dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
    constructor(
        private commonService: CommonService,
        private dialog: MatDialog,
        private spinnerService: Ng4LoadingSpinnerService,
        private formBuilder: FormBuilder,
        private sendAndRequestService:SendAndRequestService

    ){

    }

    ngOnInit() {
      this.getDivisionsData();
          for (let i = 0; i < this.userHierarchy.length; i++) {
               if(this.userHierarchy[i].depotType == 'DIV'){
               	this.divisionList.push(this.userHierarchy[i]);
               }
            }
        this.currentYear = (new Date()).getFullYear() ;
		var permissionName = this.commonService.getPermissionNameByLoggedData("WORKS","WORK") ;
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName); 
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
        this.getAllWorksData();
        this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_STATUS_ITEM + 'WORK_PROGRESS_STATUS').subscribe((data) => {
                 this.statusItems = data;
      		});
              this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_STATUS_ITEM +'ELECTRIFICATION_EXEC_AGENCY').subscribe((data) => {
        	 this.execAgencyList = data;
        	},  error => {
                this.commonService.showAlertMessage("Error in Get")
        });
        this.spinnerService.show();	


        this.filterData = {
          filterColumnNames: [
            { "Key": 'sno', "Value": " " },
            { "Key": 'division', "Value": " " },
            { "Key": 'workName', "Value": " " },
            { "Key": 'section', "Value": " " },
            { "Key": 'executedBy', "Value": "" },
            { "Key": 'physicalProgressPercentage', "Value": " " },
            { "Key": 'latestRevisedCost', "Value": " " },
             
          ],
          gridData: this.gridData,
          dataSource: this.dataSource,
          paginator: this.paginator,
          sort: this.sort
        };
    }
    
    duplicateWorkName() {
    const q = new Promise((resolve, reject) => {
      let work: string = this.workFormGroup.controls['workName'].value;
      /*
      var filter = !!this.workList && this.workList.filter(works => {
        return works.workName.toLowerCase() == work.trim().toLowerCase();
      });
      if (filter.length > 0) {
        resolve({ 'duplicateWork': true });
      } */
      this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.EXISTS_WORK_NAME +
        this.workFormGroup.controls['workName'].value
      ).subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateWorkName': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateWorkName': true }); });
    });
    return q;
  }
    
    addNewWork() {
        this.addWork = true;
        this.workFormGroup = this.formBuilder.group({
            id: 0,
           "allocation" : [null, Validators.maxLength(250)],
             "division" : [null],
           "estdLatestAnticCost" : [null],
           "executedBy" : [null, Validators.maxLength(250) ],
           "executingAgency" : [null],
           "financialProgressPercentage" : [null,Validators.max(100)],
           "latestRevisedCost" : [null],
           "pbLawLswp" : [null],
           "pbLawLswpCode" : [null],
           "physicalProgressPercentage" : [null,Validators.max(100)],
           "presentStatus" : [null],
           "reWorks" : [null],
           "rkm" : [null],
           "sanctionCost" : [null],
           "section" : [ null, Validators.maxLength(250)],
           "statusRemarks" : [null , Validators.maxLength(250)],
           "targetDateOfCompletion" : [null],
           "tkm" : [null],
           "workName" : [null,  Validators.compose([Validators.required, Validators.maxLength(250)]) , this.duplicateWorkName.bind(this)],
           "yearOfSanction" : [null, Validators.compose([Validators.min(2000), Validators.max(this.currentYear)]) ],
       });
    }
    
    onTractionEneTariffSubmit() {
    	WorksPayload.ADD_PAYLOAD.allocation = this.workFormGroup.value.allocation;
        WorksPayload.ADD_PAYLOAD.division = this.workFormGroup.value.division;
        WorksPayload.ADD_PAYLOAD.estdLatestAnticCost = this.workFormGroup.value.estdLatestAnticCost;
        WorksPayload.ADD_PAYLOAD.executedBy = this.workFormGroup.value.executedBy;
        WorksPayload.ADD_PAYLOAD.executingAgency = this.workFormGroup.value.executingAgency;
        WorksPayload.ADD_PAYLOAD.financialProgressPercentage = this.workFormGroup.value.financialProgressPercentage;
        WorksPayload.ADD_PAYLOAD.latestRevisedCost = this.workFormGroup.value.latestRevisedCost;
        WorksPayload.ADD_PAYLOAD.pbLawLswp = this.workFormGroup.value.pbLawLswp;
        WorksPayload.ADD_PAYLOAD.pbLawLswpCode = this.workFormGroup.value.pbLawLswpCode;
        WorksPayload.ADD_PAYLOAD.physicalProgressPercentage = this.workFormGroup.value.physicalProgressPercentage;
        WorksPayload.ADD_PAYLOAD.presentStatus = this.workFormGroup.value.presentStatus;
        WorksPayload.ADD_PAYLOAD.reWorks = this.workFormGroup.value.reWorks;
        WorksPayload.ADD_PAYLOAD.rkm = this.workFormGroup.value.rkm;
        WorksPayload.ADD_PAYLOAD.sanctionCost = this.workFormGroup.value.sanctionCost;
        WorksPayload.ADD_PAYLOAD.section = this.workFormGroup.value.section;
        WorksPayload.ADD_PAYLOAD.statusRemarks = this.workFormGroup.value.statusRemarks;
        WorksPayload.ADD_PAYLOAD.targetDateOfCompletion = this.workFormGroup.value.targetDateOfCompletion;
        WorksPayload.ADD_PAYLOAD.tkm = this.workFormGroup.value.tkm;
        WorksPayload.ADD_PAYLOAD.workName = this.workFormGroup.value.workName;
        WorksPayload.ADD_PAYLOAD.yearOfSanction = this.workFormGroup.value.yearOfSanction;
        WorksPayload.ADD_PAYLOAD.createdBy = this.loggedUserData.username;
        if (this.title == Constants.EVENTS.ADD) {
            this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.SAVE_WORK, WorksPayload.ADD_PAYLOAD, false).subscribe((data) => {
                this.workResponse = data;
                if(this.workResponse.code == 200 && !!this.workResponse) {
                	this.commonService.showAlertMessage(this.workResponse.message);
                	this.getAllWorksData();
                	this.workFormGroup.reset();
                	this.addWork = false;
                }else {
                	this.commonService.showAlertMessage("Work Data Saving Failed.");
                }	
            }, error => {
                console.log('ERROR >>>');
        		this.spinnerService.hide();
        		this.commonService.showAlertMessage("Work Data Saving Failed.");
            })
        }else if(this.title == Constants.EVENTS.UPDATE){
        	WorksPayload.UPDATE_PAYLOAD.id = this.editWorkResponse.id;
            WorksPayload.UPDATE_PAYLOAD.allocation = this.workFormGroup.value.allocation;
	        WorksPayload.UPDATE_PAYLOAD.division = this.workFormGroup.value.division;
	        WorksPayload.UPDATE_PAYLOAD.estdLatestAnticCost = this.workFormGroup.value.estdLatestAnticCost;
	        WorksPayload.UPDATE_PAYLOAD.executedBy = this.workFormGroup.value.executedBy;
            WorksPayload.UPDATE_PAYLOAD.executingAgency = this.workFormGroup.value.executingAgency;
	        WorksPayload.UPDATE_PAYLOAD.financialProgressPercentage = this.workFormGroup.value.financialProgressPercentage;
	        WorksPayload.UPDATE_PAYLOAD.latestRevisedCost = this.workFormGroup.value.latestRevisedCost;
	        WorksPayload.UPDATE_PAYLOAD.pbLawLswp = this.workFormGroup.value.pbLawLswp;
	        WorksPayload.UPDATE_PAYLOAD.pbLawLswpCode = this.workFormGroup.value.pbLawLswpCode;
	        WorksPayload.UPDATE_PAYLOAD.physicalProgressPercentage = this.workFormGroup.value.physicalProgressPercentage;
	        WorksPayload.UPDATE_PAYLOAD.presentStatus = this.workFormGroup.value.presentStatus;
	        WorksPayload.UPDATE_PAYLOAD.reWorks = this.workFormGroup.value.reWorks;
	        WorksPayload.UPDATE_PAYLOAD.rkm = this.workFormGroup.value.rkm;
	        WorksPayload.UPDATE_PAYLOAD.sanctionCost = this.workFormGroup.value.sanctionCost;
	        WorksPayload.UPDATE_PAYLOAD.section = this.workFormGroup.value.section;
	        WorksPayload.UPDATE_PAYLOAD.statusRemarks = this.workFormGroup.value.statusRemarks;
	        WorksPayload.UPDATE_PAYLOAD.targetDateOfCompletion = this.workFormGroup.value.targetDateOfCompletion;
	        WorksPayload.UPDATE_PAYLOAD.tkm = this.workFormGroup.value.tkm;
	        WorksPayload.UPDATE_PAYLOAD.workName = this.workFormGroup.value.workName;
	        WorksPayload.UPDATE_PAYLOAD.yearOfSanction = this.workFormGroup.value.yearOfSanction;
	        WorksPayload.UPDATE_PAYLOAD.updatedBy = this.loggedUserData.username;
            this.sendAndRequestService.requestForPUT(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.UPDATE_WORK, WorksPayload.UPDATE_PAYLOAD, false).subscribe((data) => {
                this.workResponse = data;
                if(this.workResponse.code == 200 && !!this.workResponse) {
                	this.commonService.showAlertMessage(this.workResponse.message);
                	this.id = 0;
                	this.getAllWorksData();
                	this.workFormGroup.reset();
                	this.addWork = false;
                	this.title = Constants.EVENTS.ADD;
                }else {
                	this.commonService.showAlertMessage("Work Data Updating Failed.");
                }	
            } , error => {
                console.log('ERROR >>>');
        		this.spinnerService.hide();
        		this.commonService.showAlertMessage("Work Data Updating Failed.");
            });

        }
    }
    
    

    getAllWorksData() {
    	const work: WorksModel [] = [];
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WORK).subscribe((data) => {
        	this.workList = data;
            for (let i = 0; i < this.workList.length; i++) {
                this.workList[i].sno = i+1;
               work.push(this.workList[i]);
            }
      this.filterData.gridData = work;
      this.dataSource = new MatTableDataSource(work);
      this.commonService.updateDataSource(this.dataSource, this.displayedColumns);
      this.filterData.dataSource = this.dataSource;
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
    }
    
    
    onGoBack() {
        this.workFormGroup.reset();
        this.addWork = false;
        this.title = Constants.EVENTS.ADD;
    }
    
    editWork(id) {
        this.spinnerService.show();
        this.addWork = true;
        this.workEditAction(id);
        this.title = "Update";
        this.spinnerService.hide();
    }
    
    workEditAction(id: number) {
        this.workFormGroup = this.formBuilder.group({
            id: 0,
           "allocation" : [null, Validators.maxLength(250)],
             "division" : [null],
           "estdLatestAnticCost" : [null],
           "executedBy" : [null, Validators.maxLength(250) ],
           "executingAgency" : [null],
           "financialProgressPercentage" : [null,Validators.max(100)],
           "latestRevisedCost" : [null],
           "pbLawLswp" : [null],
           "pbLawLswpCode" : [null],
           "physicalProgressPercentage" : [null,Validators.max(100)],
           "presentStatus" : [null],
           "reWorks" : [null],
           "rkm" : [null],
           "sanctionCost" : [null],
           "section" : [ null, Validators.maxLength(250)],
           "statusRemarks" : [null , Validators.maxLength(250)],
           "targetDateOfCompletion" : [null],
           "tkm" : [null],
           "workName" : [null,  Validators.compose([Validators.required, Validators.maxLength(250)]),this.duplicateWorkNameAndId.bind(this)],
           "yearOfSanction" : [null, Validators.compose([Validators.min(2000), Validators.max(this.currentYear)]) ],
       });
    	this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WORK_ID+id)
            .subscribe((responseData) => {
                this.editWorkResponse = responseData;
                this.workFormGroup.patchValue({
                    id: this.editWorkResponse.id,
                    allocation : this.editWorkResponse.allocation,
		  			division : this.editWorkResponse.division,
					estdLatestAnticCost : this.editWorkResponse.estdLatestAnticCost,
					executedBy : this.editWorkResponse.executedBy,
					executingAgency : this.editWorkResponse.executingAgency,
					financialProgressPercentage : this.editWorkResponse.financialProgressPercentage,
					latestRevisedCost : this.editWorkResponse.latestRevisedCost,
					pbLawLswp : this.editWorkResponse.pbLawLswp,
					pbLawLswpCode : this.editWorkResponse.pbLawLswpCode,
					physicalProgressPercentage : this.editWorkResponse.physicalProgressPercentage,
					presentStatus : this.editWorkResponse.presentStatus,
					reWorks : this.editWorkResponse.reWorks,
					rkm : this.editWorkResponse.rkm,
					sanctionCost : this.editWorkResponse.sanctionCost,
					section : this.editWorkResponse.section,
					statusRemarks : this.editWorkResponse.statusRemarks,
					targetDateOfCompletion : this.editWorkResponse.targetDateOfCompletion,
					tkm : this.editWorkResponse.tkm,
					workName : this.editWorkResponse.workName,
					yearOfSanction : this.editWorkResponse.yearOfSanction
                });
            } , error => {});
            this.id=id;
        if (!isNaN(this.id)) {
            this.title = 'Update';
          } else {
            this.title = Constants.EVENTS.ADD;      
          }
    }
    
    duplicateWorkNameAndId() {
    const q = new Promise((resolve, reject) => {
      let work: string = this.workFormGroup.controls['workName'].value;

      this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.EXISTS_WORK_NAME_AND_ID +this.id+'/'+
        this.workFormGroup.controls['workName'].value
      ).subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateWorkNameAndId': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateWorkNameAndId': true }); });
    });
    return q;
  }
    
    
    deleteWork(id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected work?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
                this.sendAndRequestService.requestForDELETE(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.DELETE_WORK, id)
                    .subscribe((data) => {
                    this.workResponse = data;
                	if(this.workResponse.code == 200 && !!this.workResponse) {
                        this.commonService.showAlertMessage('Work Deleted Successfully');
                        this.getAllWorksData();
                        this.addWork = false;
                    } else {
                         this.commonService.showAlertMessage("Work Deletion Failed.");
                    }    
                    },error => {
                    	console.log('ERROR >>>');
          				this.spinnerService.hide();
          				this.commonService.showAlertMessage("Work Deletion Failed.");
                    });
            }
        });
    }
    updatePagination() {
      this.filterData.dataSource = this.filterData.dataSource;
      this.filterData.dataSource.paginator = this.paginator;
    }
    applyFilter(filterValue: string) {
      filterValue = filterValue.trim(); 
      filterValue = filterValue.toLowerCase(); 
      this.filterData.dataSource.filter = filterValue;
    }
getDivisionsData(){
  this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_DIVISION_DETAILS).subscribe((data) => {
    this.divisionData = data;
  }
  );
}
}