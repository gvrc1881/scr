import { OnInit, Component, ViewChild } from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import { GuidenceItemModel } from 'src/app/models/guidence-item.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog, DateAdapter, MAT_DATE_FORMATS } from '@angular/material';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { DocumentDialogComponent } from '../../document-view-dialog/document-dialog.component';
import { DatePipe } from '@angular/common';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';


@Component({
    selector: 'guidence-item',
    templateUrl: './guidence-item.component.html',
    styleUrls: ['./guidence-item.component.scss'],
    providers: [
    {
        provide: DateAdapter, useClass: AppDateAdapter
    },
    {
        provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
    }
    ]
})
export class GuidenceItemComponent implements OnInit{

    pagination =Constants.PAGINATION_NUMBERS;
    FiledLabels = FieldLabelsConstant.LABELS;
    Titles = FieldLabelsConstant.TITLE;
    addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    addGuidenceItem: boolean ;
    title: string = Constants.EVENTS.ADD;
    guidenceItemFormGroup: FormGroup;
    guidenceItemList : any;
    guidenceItemDataSource: MatTableDataSource<GuidenceItemModel>;
    guidenceItemDisplayColumns = ['sno' , 'agencyRbRdso' , 'date' , 'heading' , 'letterNo' , 'reportContinue'  , 'status'  ,    'detailsOfIssue' , 'reponse' , 'shortDescription' , 'closedRemark' , 'id' ] ;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    editguidenceItemResponse: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    statusItems: any;
    maxDate=new Date();
    guidenceItemResponse: any;
    contentCategoryList: any;
    contentTopicList: any;
    uploadFile: boolean = false;
    contentManagementFormGroup: FormGroup;
    selectedFiles: File[] = [];
    filesExists: boolean = false;
    guidenceItemId: any;
    documentDialogRef:MatDialogRef<DocumentDialogComponent>;
    pattern = "[a-zA-Z][a-zA-Z ]*";
    loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
    id: any;
    constructor(
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private dialog: MatDialog,
        private spinnerService: Ng4LoadingSpinnerService,
        private sendAndRequestService:SendAndRequestService,
        private datePipe: DatePipe

    ){

    }

    ngOnInit () {
        this.getAllGuidenceItemData();
        var permissionName = this.commonService.getPermissionNameByLoggedData("ASSET REGISTER","GUIDENCE ITEM") ;
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName); 
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
        this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_STATUS_ITEM + 'GUIDENCE_ITEM').subscribe((data) => {
                 this.statusItems = data;
      		});
    }
    
    fileUpload(id) {
    	this.uploadFile = true;
    	this.addPermission = false;
    	this.guidenceItemId = id;
    	this.contentManagementFormGroup = this.formBuilder.group({
            contentCategory: [''],
            description: ['', Validators.compose([Validators.required, Validators.pattern(this.pattern)])],
            uploadFiles: ['', Validators.required],
            contentTopic: [''],
        });
    }
    
    createForm(){
        this.guidenceItemFormGroup = this.formBuilder.group({
            id: 0,
            'agencyRbRdso':[null, Validators.compose([Validators.required, Validators.maxLength(250)]) ],
            'date': [null, Validators.required ],
            'detailsOfIssue': [null],
            'heading': [null , Validators.maxLength(250) ],
            'letterNo' : [null , Validators.compose([Validators.required, Validators.maxLength(250)]) , this.duplicate.bind(this) ],
            'reportContinue' : [null, Validators.maxLength(250) ],
            'response': [null, Validators.maxLength(250) ],
            'shortDescription' : [null, Validators.maxLength(250) ],
            'status' : [null],
            'closedRemark' : [null,Validators.maxLength(250)]
        });    
    }
    
    public get f() { return this.contentManagementFormGroup.controls; }
    
    onContentManagementSubmit () {
    	//console.log('*** guidence item id ***'+this.guidenceItemId);
    	let category = this.contentManagementFormGroup.value.contentCategory;
        this.uploadFile = false;
    	let saveDetails = {
    		'guidenceItemId': this.guidenceItemId,
                'description': this.contentManagementFormGroup.value.description,
                'divisionCode': this.loggedUserData.divisionCode,
                'createdBy': this.loggedUserData.id,
                'createdDate': new Date(),
                'contentCategory': 'OPERATIONS',
                'zonal': 'zonal',
                'FU': 'PSI',
                'contentTopic': 'GUIDENCEITEM',
          }
          let formdata: FormData = new FormData();
          for(var i=0;i<this.selectedFiles.length;i++){
              formdata.append('file', this.selectedFiles[i]);
          }
          formdata.append('guidenceItemId', saveDetails.guidenceItemId);
          formdata.append('contentCategory', saveDetails.contentCategory);
          formdata.append('description', saveDetails.description);
          formdata.append('divisionCode', saveDetails.divisionCode);
          formdata.append('createdBy', saveDetails.createdBy);
          formdata.append('zonal', saveDetails.zonal);
          formdata.append('FU', saveDetails.FU);
          formdata.append('contentTopic', saveDetails.contentTopic);
    	this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.GUIDENCE_ITEM.GUIDENCE_ITEM_UPLOAD_FILES, formdata, true).subscribe(data => {
                this.spinnerService.hide();
                this.commonService.showAlertMessage("Files Uploaded and Saved Successfully");
                this.selectedFiles = [];
                this.filesExists = false;
                //window.location.reload();
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
    
    removeFile(id) {
        this.selectedFiles.splice(id, 1);
        if(this.selectedFiles.length === 0) {
        	this.filesExists = false;
            this.contentManagementFormGroup.reset();
        }
    }
    
    close() {
    	this.contentManagementFormGroup.reset();
        this.uploadFile = false;
        this.selectedFiles = [];
        this.filesExists = false;
        this.addPermission = true;
    }
    
    
    viewDocumentDetails(id){
	    this.spinnerService.show();    
	    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.GUIDENCE_ITEM.ATTACHMENT_LIST + id).subscribe((response) => {     
	      this.spinnerService.hide(); 
	      this.documentDialogRef = this.dialog.open(DocumentDialogComponent, {
	        	disableClose: false,
	        	height: '600px',
	        	width: '80%',       
	        	data:response,       
	      	});            
	    }, error => this.commonService.showAlertMessage(error));
	}    

    getAllGuidenceItemData() {
        const guidenceItem : GuidenceItemModel[] = [];
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.GUIDENCE_ITEM.GET_GUIDENCE_ITEM).subscribe((data) => {
            this.guidenceItemList = data;
            for (let i = 0; i < this.guidenceItemList.length; i++) {
                this.guidenceItemList[i].sno = i+1;
                this.guidenceItemList[i].date = this.datePipe.transform(this.guidenceItemList[i].date, 'dd-MM-yyyy');
                guidenceItem.push(this.guidenceItemList[i]);              
            }
            this.guidenceItemDataSource = new MatTableDataSource(guidenceItem);
            this.guidenceItemDataSource.paginator = this.paginator;
            this.guidenceItemDataSource.sort = this.sort;

        } , error => {});

    }

    gudenceItemSubmit () {
        let agencyRbRdso: string = this.guidenceItemFormGroup.value.agencyRbRdso;
        let date: Date = this.guidenceItemFormGroup.value.date;
        let detailsOfIssue: string = this.guidenceItemFormGroup.value.detailsOfIssue;
        let heading: string = this.guidenceItemFormGroup.value.heading;
        let letterNo: string = this.guidenceItemFormGroup.value.letterNo;
        let reportContinue: string = this.guidenceItemFormGroup.value.reportContinue;
        let response: string = this.guidenceItemFormGroup.value.response;
        let shortDescription: string = this.guidenceItemFormGroup.value.shortDescription;
        let status: string = this.guidenceItemFormGroup.value.status;
        let closedRemark: string = this.guidenceItemFormGroup.value.closedRemark;
        this.addGuidenceItem = false;
        
        if (this.title ==  Constants.EVENTS.ADD ) {
                var guidenceItemModel ={
                    'agencyRbRdso':agencyRbRdso,
                    'detailsOfIssue': detailsOfIssue,
                    'date':date,
                    'heading': heading,
                    'letterNo' : letterNo,
                    'reportContinue': reportContinue,
                    'response' : response,
                    'shortDescription' : shortDescription,
                    'status' : status,
                    'closedRemark' : closedRemark
                }
                this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.GUIDENCE_ITEM.SAVE_GUIDENCE_ITEM, guidenceItemModel, false).subscribe(response => {
                    this.guidenceItemResponse = response
            	if(this.guidenceItemResponse.code == 200 && !!this.guidenceItemResponse) {
	                this.commonService.showAlertMessage(this.guidenceItemResponse.message);
    	            this.getAllGuidenceItemData();
	                this.guidenceItemFormGroup.reset();
	                this.addGuidenceItem =  false;
	            }else {
                	this.commonService.showAlertMessage("Guidance Item Data Saving Failed.");
                }    
            } , error => {
            	console.log('ERROR >>>');
        		this.spinnerService.hide();
        		this.commonService.showAlertMessage("Guidance Item Data Saving Failed.");
            });
        }else if (this.title == Constants.EVENTS.UPDATE ) {
            let id: number = this.editguidenceItemResponse.id;
            var updateguidenceItemModel={
                'id':id,
                'agencyRbRdso':agencyRbRdso,
                'detailsOfIssue': detailsOfIssue,
                'date':date,
                'heading': heading,
                'letterNo' : letterNo,
                'reportContinue': reportContinue,
                'response' : response,
                'shortDescription' : shortDescription,
                'status' : status,
                'closedRemark' : closedRemark,
                'contentLink': this.editguidenceItemResponse.contentLink
            }
            this.sendAndRequestService.requestForPUT(Constants.app_urls.ENERGY_BILL_PAYMENTS.GUIDENCE_ITEM.UPDATE_GUIDENCE_ITEM,updateguidenceItemModel, false).subscribe(data => {
            	this.guidenceItemResponse = data
            	if(this.guidenceItemResponse.code == 200 && !!this.guidenceItemResponse) {
                	this.commonService.showAlertMessage(this.guidenceItemResponse.message);
                	this.getAllGuidenceItemData();
                	this.guidenceItemFormGroup.reset();
                	this.addGuidenceItem =  false;
                	this.title = Constants.EVENTS.ADD;
                }else {
                	this.commonService.showAlertMessage("Guidance Item Data Updating Failed.");
                }
            } , error => {
            	console.log('ERROR >>>');
        		this.spinnerService.hide();
        		this.commonService.showAlertMessage("Guidance Item Data Updating Failed.");
            });
            
        }
    }

    editGuidenceItem (id) {
        this.addGuidenceItem = true;
        this.guidenceItemEditAction(id);
        this.title = Constants.EVENTS.UPDATE;
    }

    guidenceItemEditAction(id: number) {
        this.guidenceItemFormGroup = this.formBuilder.group({
            id: 0,
            'agencyRbRdso':[null, Validators.compose([Validators.required, Validators.maxLength(250)]), this.duplicateWithIdAndAuthority.bind(this) ],
            'date': [null, Validators.required, this.duplicateWithIdAndDate.bind(this) ],
            'detailsOfIssue': [null],
            'heading': [null , Validators.maxLength(250) ],
            'letterNo' : [null , Validators.compose([Validators.required, Validators.maxLength(250)]) , this.duplicateWithId.bind(this) ],
            'reportContinue' : [null, Validators.maxLength(250) ],
            'response': [null, Validators.maxLength(250) ],
            'shortDescription' : [null, Validators.maxLength(250) ],
            'status' : [null],
            'closedRemark' : [null,Validators.maxLength(250)]
        });
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.GUIDENCE_ITEM.GET_GUIDENCE_ITEM_ID+id).subscribe((responseData) => {
            this.editguidenceItemResponse = responseData;
            this.id = this.editguidenceItemResponse.id;
            this.guidenceItemFormGroup.patchValue({
                id: this.editguidenceItemResponse.id,
                agencyRbRdso:this.editguidenceItemResponse.agencyRbRdso,
                detailsOfIssue: this.editguidenceItemResponse.detailsOfIssue,
                date: !! this.editguidenceItemResponse.date ? new Date(this.editguidenceItemResponse.date) : '',
                heading: this.editguidenceItemResponse.heading,
                letterNo: this.editguidenceItemResponse.letterNo,
                reportContinue: this.editguidenceItemResponse.reportContinue,
                response: this.editguidenceItemResponse.response,
                shortDescription: this.editguidenceItemResponse.shortDescription,
                status: this.editguidenceItemResponse.status,
                closedRemark: this.editguidenceItemResponse.closedRemark
            });
        } ,error => {})
    }


    deleteGuidenceItem (id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected guidance item?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
                this.sendAndRequestService.requestForDELETE(Constants.app_urls.ENERGY_BILL_PAYMENTS.GUIDENCE_ITEM.DELETE_GUIDENCE_ITEM, id)
                    .subscribe((data) => {
                    	this.guidenceItemResponse = data;
                    	if(this.guidenceItemResponse.code == 200 && !!this.guidenceItemResponse) {
                        	this.commonService.showAlertMessage('Guidance Item Deleted Successfully');
                        	this.getAllGuidenceItemData();
                        } else {
                         	this.commonService.showAlertMessage("Guidance Item Deletion Failed.");
                         }
                    },error => {
                    	console.log('ERROR >>>');
          				this.spinnerService.hide();
          				this.commonService.showAlertMessage("Guidance Item Deletion Failed.");
                    });
            }
        });
    }


    applyFilter(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase();
        this.guidenceItemDataSource.filter = filterValue;
    }

    onGoBack() {
        this.guidenceItemFormGroup.reset();
        this.addGuidenceItem = false;
        this.title = Constants.EVENTS.ADD;
    }

    NewGuidenceItem () {
        this.createForm();
        this.addGuidenceItem = true;
    }
    
    duplicate() {
        const q = new Promise((resolve, reject) => {
          this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.GUIDENCE_ITEM.EXISTS_GUIDENCE_ITEM +
            this.guidenceItemFormGroup.controls['agencyRbRdso'].value+'/'+this.guidenceItemFormGroup.controls['date'].value+'/'+this.guidenceItemFormGroup.controls['letterNo'].value
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
    
   duplicateWithId() {
        const q = new Promise((resolve, reject) => {
          this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.GUIDENCE_ITEM.EXISTS_GUIDENCE_ITEM_WITH_ID+
            this.id+'/'+this.guidenceItemFormGroup.controls['agencyRbRdso'].value+'/'+this.guidenceItemFormGroup.controls['date'].value+'/'+this.guidenceItemFormGroup.controls['letterNo'].value
          ).subscribe((duplicate) => {
              console.log('** with id ***'+duplicate);
            if (duplicate) {
              resolve({ 'duplicateWithId': true });
            } else {
              resolve(null);
            }
          }, () => { resolve({ 'duplicateWithId': true }); });
        });
        return q;
   }
    
   duplicateWithIdAndAuthority() {
        const q = new Promise((resolve, reject) => {
          this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.GUIDENCE_ITEM.EXISTS_GUIDENCE_ITEM_WITH_ID+
            this.id+'/'+this.guidenceItemFormGroup.controls['agencyRbRdso'].value+'/'+this.guidenceItemFormGroup.controls['date'].value+'/'+this.guidenceItemFormGroup.controls['letterNo'].value
          ).subscribe((duplicate) => {
              console.log('** with id ***'+duplicate);
            if (duplicate) {
              resolve({ 'duplicateWithIdAndAuthority': true });
            } else {
              resolve(null);
            }
          }, () => { resolve({ 'duplicateWithIdAndAuthority': true }); });
        });
        return q;
   }
    
   duplicateWithIdAndDate() {
        const q = new Promise((resolve, reject) => {
          this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.GUIDENCE_ITEM.EXISTS_GUIDENCE_ITEM_WITH_ID+
            this.id+'/'+this.guidenceItemFormGroup.controls['agencyRbRdso'].value+'/'+this.guidenceItemFormGroup.controls['date'].value+'/'+this.guidenceItemFormGroup.controls['letterNo'].value
          ).subscribe((duplicate) => {
              console.log('** with id ***'+duplicate);
            if (duplicate) {
              resolve({ 'duplicateWithIdAndDate': true });
            } else {
              resolve(null);
            }
          }, () => { resolve({ 'duplicateWithIdAndDate': true }); });
        });
        return q;
   }
    
}