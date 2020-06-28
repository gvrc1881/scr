import { Component, OnInit , ViewChild } from '@angular/core';
import { WorksService } from 'src/app/services/work.service';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { Constants } from 'src/app/common/constants';
import { MatTableDataSource, MatDialogRef, MatDialog, MatPaginator, MatSort } from '@angular/material';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { WorksModel } from 'src/app/models/works.model';
import { CommonService } from 'src/app/common/common.service';
import { WorksPayload } from 'src/app/payloads/works.payload';
import { ReportService } from 'src/app/services/report.service';
import { FacilityModel } from 'src/app/models/facility.model';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';

@Component({
    selector: 'works',
    templateUrl: './works.component.html',
    styleUrls: ['./works.component.scss']
})
export class WorksComponent implements OnInit {

	addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    workFormGroup: FormGroup;
    addWork: boolean = false;
    title: string = "Save";
    workList: any;
    editWorkResponse: any;
    workDataSource: MatTableDataSource<WorksModel>;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    workDisplayedColumns = ['sno' ,  'division'  , 'workName' , 'section' , 'executedBy' , 'physicalProgressPercentage' , 'latestRevisedCost' , 'id' ];
    loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
    statusItems: any;
    execAgencyList: any;
    userHierarchy:any = JSON.parse(localStorage.getItem('userHierarchy'));
    divisionList:  FacilityModel [] = [];
    workResponse: any;
    currentYear: any;

    constructor(
        private workService: WorksService,
        private commonService: CommonService,
        private dialog: MatDialog,
        private spinnerService: Ng4LoadingSpinnerService,
        private formBuilder: FormBuilder,
        private reportService: ReportService,
        private sendAndRequestService:SendAndRequestService

    ){

    }

    ngOnInit() {
          for (let i = 0; i < this.userHierarchy.length; i++) {
               if(this.userHierarchy[i].depotType == 'DIV'){
	               console.log('IN SIDE LOOP ****'+JSON.stringify(this.userHierarchy[i]));
               	this.divisionList.push(this.userHierarchy[i]);
               }
            }
        this.currentYear = (new Date()).getFullYear() ;
		var permissionName = this.commonService.getPermissionNameByLoggedData("ENERGY BILL PAYMENT","WORK") ;
  		console.log("permissionName = "+permissionName);
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName); 
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
        this.getAllWorksData();
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
        this.reportService.statusItemDetails('WORK_PROGRESS_STATUS').subscribe((data) => {
                 this.statusItems = data;
      		});
      	this.reportService.statusItemDetails('ELECTRIFICATION_EXEC_AGENCY').subscribe((data) => {
        	 this.execAgencyList = data;
        	},  error => {
                this.commonService.showAlertMessage("Error in Get")
        });
        this.spinnerService.show();	
    }
    
    duplicateWorkName() {
    const q = new Promise((resolve, reject) => {
      let work: string = this.workFormGroup.controls['workName'].value;
      var filter = !!this.workList && this.workList.filter(works => {
        return works.workName.toLowerCase() == work.trim().toLowerCase();
      });
      if (filter.length > 0) {
        resolve({ 'duplicateWork': true });
      }
      this.workService.existsWorkName(
        this.workFormGroup.controls['workName'].value
      ).subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateWork': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateWork': true }); });
    });
    return q;
  }
    
    addNewWork() {
        this.addWork = true;
    }
    
    onTractionEneTariffSubmit() {
    	WorksPayload.ADD_PAYLOAD.allocation = this.workFormGroup.value.allocation;
        WorksPayload.ADD_PAYLOAD.division = this.workFormGroup.value.division;
        WorksPayload.ADD_PAYLOAD.estdLatestAnticCost = this.workFormGroup.value.estdLatestAnticCost;
        WorksPayload.ADD_PAYLOAD.executedBy = this.workFormGroup.value.executedBy;
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
        WorksPayload.ADD_PAYLOAD.createdBy = this.loggedUserData.id;
        if (this.title == Constants.EVENTS.SAVE) {
            this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.SAVE_WORK, WorksPayload.ADD_PAYLOAD).subscribe((data) => {
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
	        WorksPayload.UPDATE_PAYLOAD.updatedBy = this.loggedUserData.id;
            this.sendAndRequestService.requestForPUT(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.UPDATE_WORK, WorksPayload.UPDATE_PAYLOAD).subscribe((data) => {
                this.workResponse = data;
                if(this.workResponse.code == 200 && !!this.workResponse) {
                	this.commonService.showAlertMessage(this.workResponse.message);
                	this.getAllWorksData();
                	this.workFormGroup.reset();
                	this.addWork = false;
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
            this.workDataSource = new MatTableDataSource(work);
            this.workDataSource.paginator = this.paginator;
            this.workDataSource.sort = this.sort;
        },error => {} );
    }
    
    
    onGoBack() {
        this.workFormGroup.reset();
        this.addWork = false;
        this.title = 'Save';
    }
    
    editWork(id) {
        this.spinnerService.show();
        this.addWork = true;
        this.workEditAction(id);
        this.title = "Update";
        this.spinnerService.hide();
    }
    
    workEditAction(id: number) {
    	this.sendAndRequestService.requestForGETId(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WORK_ID, id)
            .subscribe((responseData) => {
                this.editWorkResponse = responseData;
                // console.log('responseData'+JSON.stringify(responseData));
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
    
    applyFilter(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase();
        this.workDataSource.filter = filterValue;
    }

}