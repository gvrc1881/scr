import { OnInit, Component, ViewChild } from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { TractionEnergyTariffService } from 'src/app/services/traction-energy-tariff.service';
import { TractionEnergyTariffModel } from 'src/app/models/traction-energy-tariff.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { Constants } from 'src/app/common/constants';
import { MatTableDataSource, MatDialogRef, MatDialog, MatPaginator, MatSort } from '@angular/material';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { TractionEnergyTariffPayload } from 'src/app/payloads/traction-energy-tariff.payload';
import { ReportService } from 'src/app/services/report.service';
import { DocumentDialogComponent } from '../../document-view-dialog/document-dialog.component';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';


@Component({
    selector: 'traction-energy-tariff',
    templateUrl: './traction-energy-tariff.component.html',
    styleUrls: ['./traction-energy-tariff.component.scss']
})
export class TractionEnergyTariffComponent implements OnInit{
	
	addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    tractionEnergyTariffFormGroup: FormGroup;
    addTractionEnergyTariff: boolean = false;
    title: string = "Save";
    tractionEnergyTariffList: any;
    editTractionEnergyTariffResponse: any;
    tractionEnergyTariffDataSource: MatTableDataSource<TractionEnergyTariffModel>;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    TractionEneTariffDisplayedColumns = ['sno' ,  'supplier'  , 'rate' , 'fromDate' , 'thruDate' , 'specification' , 'condition' , 'id' ];
    loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
    contentCategoryList: any;
    contentTopicList: any;
    uploadFile: boolean = false;
    contentManagementFormGroup: FormGroup;
    selectedFiles: File[] = [];
    filesExists: boolean = false;
    tractionEnergyTariffId: any;
    pattern = "[a-zA-Z][a-zA-Z ]*";
    toMinDate=new Date();
    isSubmit: boolean = false;
    documentDialogRef:MatDialogRef<DocumentDialogComponent>;
    eleEnergySuppliersList: any;
    tariffResponse: any;
    
    constructor(
        private commonService: CommonService,
        private dialog: MatDialog,
        private tractionEnergyTariffService: TractionEnergyTariffService,
        private spinnerService: Ng4LoadingSpinnerService,
        private formBuilder: FormBuilder,
        private reportService: ReportService,
        private sendAndRequestService:SendAndRequestService

    ){

    }
    
	ngOnInit() {
		var permissionName = this.commonService.getPermissionNameByLoggedData("ENERGY","TRACTION ENERGY TARIFF") ;//p == 0 ? 'No Permission' : p[0].permissionName;
  		console.log("permissionName = "+permissionName);
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName); //getPermission("Add", );
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
        this.getTractionEnergyTariffData();
        this.tractionEnergyTariffFormGroup = this.formBuilder.group({
             id: 0,
            "supplier": [null],
            "rate": [null],
            "specification":[null, Validators.maxLength(250)],
            "condition": [null, Validators.maxLength(250)],
            "fromDate":[null,  Validators.required, this.duplicateFromDate.bind(this)],
            "thruDate":[null]
        });
        this.reportService.getAllEleEnergySuppliers().subscribe((data) => {
        	 this.eleEnergySuppliersList = data;
        	},  error => {
                this.commonService.showAlertMessage("Error in Get")
            });
        /*
        this.reportService.getAllContentCategories().subscribe((data) => {
        	 this.contentCategoryList = data;
        	},  error => {
                this.commonService.showAlertMessage("Error in Get")
            });
       this.reportService.getAllContentTopics().subscribe((data) => {
        	 this.contentTopicList = data;
        	},  error => {
                this.commonService.showAlertMessage("Error in Get")
            });     */
    }
    
    duplicateFromDate() {
    	const q = new Promise((resolve, reject) => {
	      //console.log(JSON.stringify(this.scheduleJobData))
	       this.tractionEnergyTariffService.existsSupplierAndFromDate(
	        this.tractionEnergyTariffFormGroup.controls['supplier'].value,
	        this.tractionEnergyTariffFormGroup.controls['fromDate'].value
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
        
    addNewTractionEnergyTariff() {
        this.addTractionEnergyTariff = true;
    }
    
    addEvent($event) {
    	this.toMinDate = new Date($event.value);
  	}
  	
    // public get f() { return this.contentManagementFormGroup.controls; }
    fileUpload(id) {
    	this.uploadFile = true;
    	this.addPermission = false;
    	this.tractionEnergyTariffId = id;
    	this.contentManagementFormGroup = this.formBuilder.group({
            contentCategory: ['', Validators.required],
            description: ['', Validators.compose([Validators.required, Validators.pattern(this.pattern)])],
            uploadFiles: ['', Validators.required],
            contentTopic: ['', Validators.compose([Validators.required, Validators.pattern(this.pattern)])],
        });
    }
    
    public get f() { return this.contentManagementFormGroup.controls; }
    
    viewDocumentDetails(id){
	    this.spinnerService.show();    
	    this.tractionEnergyTariffService.attachedDocumentList(id).subscribe((response) => {     
	      this.spinnerService.hide(); 
	      // console.log('vallues::::'+JSON.stringify(response));
	      this.documentDialogRef = this.dialog.open(DocumentDialogComponent, {
	        	disableClose: false,
	        	height: '600px',
	        	width: '80%',       
	        	data:response,       
	      	});            
	    }, error => this.commonService.showAlertMessage(error));
	   
	    
	  }
    
    removeFile(id) {
        this.selectedFiles.splice(id, 1);
        if(this.selectedFiles.length === 0) {
        	this.filesExists = false;
        }
    }
    
    onContentManagementSubmit () {
    //	console.log("hello:::"+this.contentManagementFormGroup.value.contentCategory);
    	let category = this.contentManagementFormGroup.value.contentCategory;
    	let saveDetails = {
    		'tractionEnergyTariffId': this.tractionEnergyTariffId,
                'description': this.contentManagementFormGroup.value.description,
                'divisionCode': this.loggedUserData.divisionCode,
                'createdBy': this.loggedUserData.id,
                'createdDate': new Date(),
                'contentCategory': 'OPERATIONS',
                'zonal': 'zonal',
                'FU': 'PSI',
                'contentTopic': 'TARIFF',
          }
          
    	this.tractionEnergyTariffService.uploadAttachedFiles(this.selectedFiles, saveDetails).subscribe(data => {
                console.log(JSON.stringify(data));
                this.spinnerService.hide();
                this.commonService.showAlertMessage("Files Uploaded and Saved Successfully");
                this.selectedFiles = [];
                this.filesExists = false;
                window.location.reload();
                //this.contentManagementFormGroup.reset();
               // this.getUploadedFiles();
            }, error => {
                console.log('ERROR >>>');
                this.spinnerService.hide();
                this.commonService.showAlertMessage("Files Uploading Failed.");
            })
            
    }

    upload(event) {
        if (event.target.files.length > 0) { this.filesExists = true; }
        for (var i = 0; i < event.target.files.length; i++) {
            this.selectedFiles.push(event.target.files[i]);
        }
    }

    onTractionEneTariffSubmit() {
    	TractionEnergyTariffPayload.ADD_PAYLOAD.supplier = this.tractionEnergyTariffFormGroup.value.supplier;
        TractionEnergyTariffPayload.ADD_PAYLOAD.rate = this.tractionEnergyTariffFormGroup.value.rate;
        TractionEnergyTariffPayload.ADD_PAYLOAD.specification = this.tractionEnergyTariffFormGroup.value.specification;
        TractionEnergyTariffPayload.ADD_PAYLOAD.condition = this.tractionEnergyTariffFormGroup.value.condition;
        // TractionEnergyTariffPayload.ADD_PAYLOAD.year = this.tractionEnergyTariffFormGroup.value.year;
        TractionEnergyTariffPayload.ADD_PAYLOAD.fromDate = this.tractionEnergyTariffFormGroup.value.fromDate;
        TractionEnergyTariffPayload.ADD_PAYLOAD.thruDate = this.tractionEnergyTariffFormGroup.value.thruDate;
        TractionEnergyTariffPayload.ADD_PAYLOAD.createdBy = this.loggedUserData.id;
        // console.log('json object::'+JSON.stringify(TractionEnergyTariffPayload.ADD_PAYLOAD));
        if (this.title == Constants.EVENTS.SAVE) {
            this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.TARIFF.SAVE_TARIFF,TractionEnergyTariffPayload.ADD_PAYLOAD).subscribe((data) => {
                this.tariffResponse = data;
              	if(this.tariffResponse.code == 200 && !!this.tariffResponse) {  
	                this.commonService.showAlertMessage(this.tariffResponse.message);
	                this.getTractionEnergyTariffData();
	                this.tractionEnergyTariffFormGroup.reset();
	                this.addTractionEnergyTariff = false;
                }else {
                	this.commonService.showAlertMessage(" Tariff Data Saving Failed.");
                }
            }, error => {
                this.commonService.showAlertMessage(" Tariff Data Saving Failed.")
            })
        }else if(this.title == Constants.EVENTS.UPDATE){
            //console.log('in else if block::');
            TractionEnergyTariffPayload.UPDATE_PAYLOAD.id = this.editTractionEnergyTariffResponse.id;
            TractionEnergyTariffPayload.UPDATE_PAYLOAD.supplier = this.tractionEnergyTariffFormGroup.value.supplier;
	        TractionEnergyTariffPayload.UPDATE_PAYLOAD.rate = this.tractionEnergyTariffFormGroup.value.rate;
	        TractionEnergyTariffPayload.UPDATE_PAYLOAD.specification = this.tractionEnergyTariffFormGroup.value.specification;
	        TractionEnergyTariffPayload.UPDATE_PAYLOAD.condition = this.tractionEnergyTariffFormGroup.value.condition;
	       // TractionEnergyTariffPayload.UPDATE_PAYLOAD.year = this.tractionEnergyTariffFormGroup.value.year;
	        TractionEnergyTariffPayload.UPDATE_PAYLOAD.fromDate = this.tractionEnergyTariffFormGroup.value.fromDate;
	        TractionEnergyTariffPayload.UPDATE_PAYLOAD.thruDate = this.tractionEnergyTariffFormGroup.value.thruDate;
	        TractionEnergyTariffPayload.UPDATE_PAYLOAD.updatedBy = this.loggedUserData.id;
            this.sendAndRequestService.requestForPUT(Constants.app_urls.ENERGY_BILL_PAYMENTS.TARIFF.UPDATE_TARIFF,TractionEnergyTariffPayload.UPDATE_PAYLOAD).subscribe((data) => {
                this.tariffResponse = data;
              	if(this.tariffResponse.code == 200 && !!this.tariffResponse) {  
	                this.commonService.showAlertMessage(this.tariffResponse.message);
	                this.getTractionEnergyTariffData();
	                this.tractionEnergyTariffFormGroup.reset();
	                this.addTractionEnergyTariff = false;
	                this.title = "Save";
	            }else {
                	this.commonService.showAlertMessage("Tariff Data Updating Failed.");
                }    
            } , error => {
                this.commonService.showAlertMessage("Tariff Data Updating Failed.")
            });

        }
    }
    
    onGoBack() {
        this.tractionEnergyTariffFormGroup.reset();
        this.addTractionEnergyTariff = false;
        this.title = 'Save';
    }
    
    close() {
    	this.contentManagementFormGroup.reset();
        this.uploadFile = false;
        this.selectedFiles = [];
        this.filesExists = false;
        this.addPermission = true;
    }
    
    
    getTractionEnergyTariffData() {
        const tractionEnergyTariff: TractionEnergyTariffModel [] = [];
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.TARIFF.GET_TARIFF_ID).subscribe((data) => {
            this.tractionEnergyTariffList = data;
            for (let i = 0; i < this.tractionEnergyTariffList.length; i++) {
                this.tractionEnergyTariffList[i].sno = i+1;
               tractionEnergyTariff.push(this.tractionEnergyTariffList[i]);
            }
            this.tractionEnergyTariffDataSource = new MatTableDataSource(tractionEnergyTariff);
            this.tractionEnergyTariffDataSource.paginator = this.paginator;
            this.tractionEnergyTariffDataSource.sort = this.sort; 
        }, error => {
           this.spinnerService.hide();     
        }); 
    }
    
    editTractionEneTariff(id) {
        this.spinnerService.show();
        this.addTractionEnergyTariff = true;
        this.tractionEnergyTariffEditAction(id);
        this.title = "Update";
        this.spinnerService.hide();
    }   
     
    tractionEnergyTariffEditAction(id: number) {
        this.sendAndRequestService.requestForGETId(Constants.app_urls.ENERGY_BILL_PAYMENTS.TARIFF.GET_TARIFF_ID, id)
            .subscribe((responseData) => {
                this.editTractionEnergyTariffResponse = responseData;
                // console.log('responseData'+JSON.stringify(responseData));
                this.tractionEnergyTariffFormGroup.patchValue({
                    id: this.editTractionEnergyTariffResponse.id,
                    supplier: this.editTractionEnergyTariffResponse.supplier,
                    rate: this.editTractionEnergyTariffResponse.rate,
                    fromDate: !! this.editTractionEnergyTariffResponse.fromDate ? new Date(this.editTractionEnergyTariffResponse.fromDate) : '',
                    thruDate: !! this.editTractionEnergyTariffResponse.thruDate ? new Date(this.editTractionEnergyTariffResponse.thruDate) : '',
                    specification: this.editTractionEnergyTariffResponse.specification,
                    condition: this.editTractionEnergyTariffResponse.condition
                });
            } , error => {});
    } 
    
    
    deleteTractionEneTariff (id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected tarction energy tariff?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
                this.sendAndRequestService.requestForDELETE(Constants.app_urls.ENERGY_BILL_PAYMENTS.TARIFF.DELETE_TARIFF, id).subscribe((data) => {
                    	this.tariffResponse = data;
              				if(this.tariffResponse.code == 200 && !!this.tariffResponse) {  
	                			this.commonService.showAlertMessage(this.tariffResponse.message);
                        		this.getTractionEnergyTariffData();
                        	} else {
                         		this.commonService.showAlertMessage("Tariff Deletion Failed.");
                         		}	
                    },error => {});
            }
        });
    }
    
    applyFilter(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase();
        this.tractionEnergyTariffDataSource.filter = filterValue;
    }

}