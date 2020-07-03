import { OnInit, Component, ViewChild } from '@angular/core';
import { GuidenceItemService } from 'src/app/services/guidence-item.service';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import { GuidenceItemModel } from 'src/app/models/guidence-item.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { ReportService } from 'src/app/services/report.service';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';

@Component({
    selector: 'guidence-item',
    templateUrl: './guidence-item.component.html',
    styleUrls: ['./guidence-item.component.scss']
})
export class GuidenceItemComponent implements OnInit{

    addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    addGuidenceItem: boolean ;
    title: string = "Save";
    guidenceItemFormGroup: FormGroup;
    guidenceItemList : any;
    guidenceItemDataSource: MatTableDataSource<GuidenceItemModel>;
    guidenceItemDisplayColumns = ['sno' , 'agencyRbRdso' , 'date' , 'detailsOfIssue' , 'heading' , 'closedRemark' , 'status' , 'id' ] ;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    editguidenceItemResponse: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    statusItems: any;
    maxDate=new Date();
    guidenceItemResponse: any;


    constructor(
        private _guidenceItemService: GuidenceItemService,
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private dialog: MatDialog,
        private reportService: ReportService,
        private spinnerService: Ng4LoadingSpinnerService,
        private sendAndRequestService:SendAndRequestService

    ){

    }

    ngOnInit () {
        this.getAllGuidenceItemData();
        var permissionName = this.commonService.getPermissionNameByLoggedData("WORKS","GUIDENCE ITEM") ;//p == 0 ? 'No Permission' : p[0].permissionName;
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName); //getPermission("Add", );
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
        this.guidenceItemFormGroup = this.formBuilder.group({
            id: 0,
            'agencyRbRdso':[null, Validators.maxLength(250) ],
            'date': [null],
            'detailsOfIssue': [null],
            'heading': [null , Validators.maxLength(250) ],
            'letterNo' : [null , Validators.maxLength(250) ],
            'reportContinue' : [null, Validators.maxLength(250) ],
            'response': [null, Validators.maxLength(250) ],
            'shortDescription' : [null, Validators.maxLength(250) ],
            'status' : [null],
            'closedRemark' : [null,Validators.maxLength(250)]
        });
        this.reportService.statusItemDetails('GUIDENCE_ITEM').subscribe((data) => {
                 this.statusItems = data;
      		});
    }

    getAllGuidenceItemData() {
        // console.log("get all guidence items");
        const guidenceItem : GuidenceItemModel[] = [];
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.GUIDENCE_ITEM.GET_GUIDENCE_ITEM).subscribe((data) => {
            this.guidenceItemList = data;
            for (let i = 0; i < this.guidenceItemList.length; i++) {
                this.guidenceItemList[i].sno = i+1;
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
        
        if (this.title ==  Constants.EVENTS.SAVE) {
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
                'closedRemark' : closedRemark
            }
            this.sendAndRequestService.requestForPUT(Constants.app_urls.ENERGY_BILL_PAYMENTS.GUIDENCE_ITEM.UPDATE_GUIDENCE_ITEM,updateguidenceItemModel).subscribe(data => {
            	this.guidenceItemResponse = data
            	if(this.guidenceItemResponse.code == 200 && !!this.guidenceItemResponse) {
                	this.commonService.showAlertMessage(this.guidenceItemResponse.message);
                	this.getAllGuidenceItemData();
                	this.guidenceItemFormGroup.reset();
                	this.addGuidenceItem =  false;
                	this.title = "Save";
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
        this.title = 'Update';
    }

    guidenceItemEditAction(id: number) {
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.GUIDENCE_ITEM.GET_GUIDENCE_ITEM_ID+'/'+id).subscribe((responseData) => {
            this.editguidenceItemResponse = responseData;
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
            })
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
        this.title = 'Save';
    }

    NewGuidenceItem () {
        this.addGuidenceItem = true;
    }

}