import { OnInit, Component, ViewChild, ElementRef} from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { AssetScheduleAssocModel } from 'src/app/models/asset-schedule-assoc.model';
import { Constants } from 'src/app/common/constants';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';


@Component({
    selector: 'asset-schedule-assoc',
    templateUrl: './asset-schedule-assoc.component.html',
    styleUrls: []
})

export class AssetScheduleAssocComponent implements OnInit{
    editPermission: boolean = true;
    addPermission: boolean = true;
    deletePermission: boolean = true;
    addSchAssoc:boolean;
    assetSchAssocFormGroup:FormGroup;
    id: number = 0;
    title: string = "Save";
    AssocList:any;
    editAssetSchAssocResponse: any;
    responseStatus: any;
    assocErrors : any;
    saveAssoc:boolean;
    value:string;
    pattern = "^[A-Z0-9]+$";
    assetSchAssocDataSource: MatTableDataSource<AssetScheduleAssocModel>;
    assetSchAssocDisplayColumns = ['sno','assetType','scheduleCode','isDpr','targetPlanMonths',
    'sequenceCode','duration','uomOfDuration','description','asaSeqId','id'] ;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    assetSchassocResponse:any;
    loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
    assetsList:any;
    scheduleList:any;

    constructor( 
        private formBuilder: FormBuilder,
        private commonService: CommonService,
        private spinnerService: Ng4LoadingSpinnerService,
        private sendAndRequestService:SendAndRequestService,
        public dialog: MatDialog
        ){
                this.assocErrors = {
                    assetType: {},
                    scheduleCode:{}
            
                };
            
    }

    ngOnInit () {
                this.findAssetsTypes();
                this.findSchedules();
        var permissionName = this.commonService.getPermissionNameByLoggedData("TRD CONFIG","ASSET SCHEDULE ASSOCIATION") ;
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
    
        this.getAllAssocData();   
    }
   
    findAssetsTypes(){
        
                    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_ALL_ASSET_TYPES)
                .subscribe((resp) => {
                this.assetsList = resp;
               
                });
    }
    findSchedules(){
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSET_SCH_ASSOC.GET_SCH)
        .subscribe((resp) => {
        this.scheduleList = resp;
        });

    }
    assetSchAssocSubmit(){ 

         let assetType: string=this.assetSchAssocFormGroup.value.assetType;
        let scheduleCode: string=this.assetSchAssocFormGroup.value.scheduleCode;
        let isDpr: string=this.assetSchAssocFormGroup.value.isDpr;
        let targetPlanMonths: string=this.assetSchAssocFormGroup.value.targetPlanMonths;
        let sequenceCode: string=this.assetSchAssocFormGroup.value.sequenceCode;
        let duration: string=this.assetSchAssocFormGroup.value.duration;
        let uomOfDuration: string=this.assetSchAssocFormGroup.value.uomOfDuration;
        let description: string=this.assetSchAssocFormGroup.value.description;
        let asaSeqId: string=this.assetSchAssocFormGroup.value.asaSeqId;

        if (this.title ==  Constants.EVENTS.SAVE) {
            var saveAssocModel={
                                    'assetType':assetType,
                                    'scheduleCode': scheduleCode,
                                    'isDpr':isDpr,
                                    'targetPlanMonths':targetPlanMonths,
                                    'sequenceCode':sequenceCode,
                                    'duration':duration,
                                    'uomOfDuration':uomOfDuration,
                                    'description':description,
                                    'asaSeqId':asaSeqId,
                                    "createdBy" : this.loggedUserData.username
            }              
            this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSET_SCH_ASSOC.SAVE_ASSOC,saveAssocModel, false).subscribe(data => {
                this.assetSchassocResponse = data;
                if(this.assetSchassocResponse.code == 200 && !!this.assetSchassocResponse) {
                    this.commonService.showAlertMessage(this.assetSchassocResponse.message);
                    this.getAllAssocData();
                    this.assetSchAssocFormGroup.reset();
                }else {
                    this.commonService.showAlertMessage("Asset Schedule Assoc Data Saving Failed.");
                }
                this.spinnerService.hide();
            } , error => {
                console.log('ERROR >>>');
                this.spinnerService.hide();
                this.commonService.showAlertMessage("Asset Schedule Assoc Data Saving Failed.");
            });
        }
    
          
            else if (this.title == Constants.EVENTS.UPDATE )
             {
                let id: number = this.editAssetSchAssocResponse.id;
                var updateAssocModel={
                                            'id':id,
                                            'assetType':assetType,
                                            'scheduleCode': scheduleCode,
                                            'isDpr':isDpr,
                                            'targetPlanMonths':targetPlanMonths,
                                            'sequenceCode':sequenceCode,
                                            'duration':duration,
                                            'uomOfDuration':uomOfDuration,
                                            'description':description,
                                            'asaSeqId':asaSeqId
                                            
                                         }    
                    this.sendAndRequestService.requestForPUT(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSET_SCH_ASSOC.UPDATE_ASSOC,updateAssocModel, false).
                    subscribe(data => {
                                        this.assetSchassocResponse = data;
                                        if(this.assetSchassocResponse.code == 200 && !!this.assetSchassocResponse)
                                        {
                                            this.commonService.showAlertMessage(this.assetSchassocResponse.message);
                                            this.getAllAssocData();
                                            this.assetSchAssocFormGroup.reset();
                                            this.addSchAssoc=  false;
                                            this.title = "Save";
                                        }
                                        else {
                                                this.commonService.showAlertMessage("Asset Schedule Assoc Data Updating Failed.");
                                            }
                                    } , 
                                    error => {
                    
                                            this.spinnerService.hide();
                                            this.commonService.showAlertMessage("Asset Schedule Assoc Data Updating Failed.");
                                            })
                
            }
    }

    getAllAssocData() {
            const assoc : AssetScheduleAssocModel[] = [];
           
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSET_SCH_ASSOC. GET_ASSET_SCH_ASSOC)
        .subscribe((data) => {
            this.AssocList = data;
            for (let i = 0; i < this.AssocList.length; i++) {
                this.AssocList[i].sno = i+1;
                assoc.push(this.AssocList[i]);              
            }
            this.assetSchAssocDataSource = new MatTableDataSource(assoc);
            this.assetSchAssocDataSource.paginator = this.paginator;
            this.assetSchAssocDataSource.sort = this.sort;

        } , error => {});

    }
    
    onGoBack() {
        this.assetSchAssocFormGroup.reset();
        this.addSchAssoc= false;
        this.title = 'Save';
    }


    NewAssetSchAssoc(){
        this.addSchAssoc = true;
        this.assetSchAssocFormGroup = this.formBuilder.group({
          id: 0,
          'assetType': [null,Validators.compose([Validators.required,Validators.maxLength(255)])],
          'scheduleCode': [null, Validators.compose([Validators.required,Validators.maxLength(255)]),this.duplicateAssetSchCode.bind(this)],
          'isDpr': [null, Validators.maxLength(255)],
          'targetPlanMonths' : [null, Validators.maxLength(255)],
          'sequenceCode' : [null, Validators.maxLength(255)],
          'duration' : [null, Validators.maxLength(255)],
          'uomOfDuration' : [null, Validators.maxLength(255)],
          'description' : [null, Validators.maxLength(255)],
          'asaSeqId' : [null, Validators.maxLength(255)],
          
          
      });
    }

    applyFilter(filterValue: string) {
        filterValue = filterValue.trim(); // Remove whitespace
        filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
        this.assetSchAssocDataSource.filter = filterValue;
      }

      deleteAssetSchAssoc(id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected Assoc?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
              this.sendAndRequestService.requestForDELETE(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSET_SCH_ASSOC.DELETE_ASSOC,id).subscribe(data => {
                      this.assetSchassocResponse = data;
                    
            			if(this.assetSchassocResponse.code == 200 && !!this.assetSchassocResponse) {
                        	this.commonService.showAlertMessage(this.assetSchassocResponse.message);
                         	this.getAllAssocData();
                         } else {
                         	this.commonService.showAlertMessage("ScheduleAssoc Deletion Failed.");
                         }	
                    },error => {
                    	console.log('ERROR >>>');
          				this.spinnerService.hide();
          				this.commonService.showAlertMessage("ScheduleAssoc Deletion Failed.");
                    });
            }
        });
    }
  
      editAssetSchAssoc (id) {
        this.addSchAssoc = true;
        this.AssocEditAction(id);
        this.title = 'Update';
    
    }
    AssocEditAction(id: number) {
        this.assetSchAssocFormGroup = this.formBuilder.group({
            id: 0,
            'assetType': [null],
          'scheduleCode': [null, Validators.maxLength(255)],
          'isDpr': [null, Validators.maxLength(255)],
          'targetPlanMonths' : [null, Validators.maxLength(255)],
          'sequenceCode' : [null, Validators.maxLength(255)],
          'duration' : [null, Validators.maxLength(255)],
          'uomOfDuration' : [null, Validators.maxLength(255)],
          'description' : [null, Validators.maxLength(255)],
          'asaSeqId' : [null, Validators.maxLength(255)],
        });
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSET_SCH_ASSOC.GET_ASSET_SCH_ASSOC_ID+id).subscribe((responseData) => {
            this.editAssetSchAssocResponse = responseData;
             
              this.assetSchAssocFormGroup.patchValue
              ({
                                id: this.editAssetSchAssocResponse.id,
                                assetType:this.editAssetSchAssocResponse.assetType,
                                scheduleCode:this.editAssetSchAssocResponse.scheduleCode,
                                isDpr: this.editAssetSchAssocResponse.isDpr,
                                targetPlanMonths: this.editAssetSchAssocResponse.targetPlanMonths,
                                sequenceCode:this.editAssetSchAssocResponse.sequenceCode,
                                duration :this.editAssetSchAssocResponse.duration,
                                uomOfDuration:this.editAssetSchAssocResponse.uomOfDuration,
                                description:this.editAssetSchAssocResponse.description,
                                asaSeqId:this.editAssetSchAssocResponse.asaSeqId,
                 })
            
        } ,error => {})
        this.id=id;
        if (!isNaN(this.id)) {
            this.title = 'Update';
          } else {
            this.title = 'Save';      
          }
    }
      onFormValuesChanged() {
        for (const field in this.assocErrors) {
          if (!this.assocErrors.hasOwnProperty(field)) {
            continue;
          }
          // Clear previous errors
          this.assocErrors[field] = {};
    
          // Get the control
          const control = this.assetSchAssocFormGroup.get(field);
          if (control && control.dirty && !control.valid) {
            this.assocErrors[field] = control.errors;
          }
        }
      }

      duplicateAssetSchCode() {
        let assetType: string = this.assetSchAssocFormGroup.controls['assetType'].value;
        let scheduleCode: string = this.assetSchAssocFormGroup.controls['scheduleCode'].value;

        const q = new Promise((resolve, reject) => {          
  
           this.sendAndRequestService.requestForGET(
                  Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSET_SCH_ASSOC.Exist_ASSETTYPE_SCH+assetType+'/'+scheduleCode).subscribe
                  ((duplicate) => {
            if (duplicate) {
              resolve({ 'duplicate': true });
            } else {
              resolve(null);
            }
          }, () => { resolve({ 'duplicate': true }); });
        });
        return q;
      }
  
     
     
     
}

    
    
    

   


   

      
     



    
    
    

   