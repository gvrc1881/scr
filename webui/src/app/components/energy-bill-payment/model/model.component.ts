import { OnInit, Component, ViewChild } from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { ModelModel } from 'src/app/models/model.model';
import { Constants } from 'src/app/common/constants';
import { ModelPayload } from 'src/app/payloads/model.payload';
import { FuseConfirmDialogComponent } from '../../../components/confirm-dialog/confirm-dialog.component';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { DatePipe } from '@angular/common';
import { DataViewDialogComponent } from 'src/app/components/data-view-dialog/data-view-dialog.component';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';


@Component({
    selector: 'model',
    templateUrl: './model.component.html',
    styleUrls: []
})

export class ModelComponent implements OnInit{
    FiledLabels = FieldLabelsConstant.LABELS;
    Titles = FieldLabelsConstant.TITLE;
    editPermission: boolean = true;
    addPermission: boolean = true;
    deletePermission: boolean = true;
    addModel:boolean;
    modelFormGroup:FormGroup;
    id: number = 0;
    title: string = "Save";
    modelList:any;
    updatedata: boolean = true;
    cloneupdate: boolean = true;
    editModelResponse: any;
    responseStatus: any;
    modelErrors : any;
    saveModel:boolean;
    value:string;
    pattern = "^[A-Z0-9]+$";
    modelDataSource: MatTableDataSource<ModelModel>;
    modelDisplayColumns = ['sno','modelCode','description','brandName','modelType','id'] ;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
    modelResponse:any;
    loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
    
   
    constructor( 
        private formBuilder: FormBuilder,
        private commonService: CommonService,
        private spinnerService: Ng4LoadingSpinnerService,
        private sendAndRequestService:SendAndRequestService,
        public dialog: MatDialog,
        private datePipe: DatePipe,
        ){
                this.modelErrors = {
                    modelCode:{},
                  
            
                };
            
    }

    ngOnInit () {

        var permissionName = this.commonService.getPermissionNameByLoggedData("CONFIG","Model") ;
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
    
        this.getAllModelData();
        
    }


    modelSubmit(){ 

        
         let modelCode: string=this.modelFormGroup.value.modelCode;
        let description: string=this.modelFormGroup.value.description;
        let brandName: string=this.modelFormGroup.value.brandName;
        let modelType: string=this.modelFormGroup.value.modelType;
       
       this.addModel=false;

       if(this.title == Constants.EVENTS.SAVE)
       {
           
            
            ModelPayload.ADD_PAYLOAD.modelCode =modelCode;
            ModelPayload.ADD_PAYLOAD.description =description;
            ModelPayload.ADD_PAYLOAD.brandName =brandName;
            ModelPayload.ADD_PAYLOAD.modelType =modelType;
            ModelPayload.ADD_PAYLOAD.createdBy = this.loggedUserData.username;
            
           this.sendAndRequestService.requestForPOST(Constants.app_urls.CONFIG.MODEL.SAVE_MODEL,ModelPayload.ADD_PAYLOAD, false)
            .subscribe((data)=>{
              this.modelResponse=data;
              this.spinnerService.hide();
              if(this.modelResponse.code==200 && !!this.modelResponse){
                this.commonService.showAlertMessage(this.modelResponse.message);
                this.getAllModelData();
           this.modelFormGroup.reset();
           this.addModel = false;
              }
              else{
                this.commonService.showAlertMessage("Model data Saving Failed.");
              }
            },error => {
    this.spinnerService.hide();
    this.commonService.showAlertMessage("Model Data Saving Failed.");
    })
  }
       else if (this.title == "Update") {
        
        this.saveModel = false;
        let id: number = this.editModelResponse.id;
       ModelPayload.UPDATE_PAYLOAD.id =id;       
       ModelPayload.UPDATE_PAYLOAD.modelCode =modelCode;
       ModelPayload.UPDATE_PAYLOAD.description =description;
       ModelPayload.UPDATE_PAYLOAD.brandName =brandName;
       ModelPayload.UPDATE_PAYLOAD.modelType =modelType;
       ModelPayload.UPDATE_PAYLOAD.updatedBy = this.loggedUserData.username;
       this.sendAndRequestService.requestForPUT(Constants.app_urls.CONFIG.MODEL.UPDATE_MODEL,ModelPayload.UPDATE_PAYLOAD, false)
          .subscribe((data) => {
            this.modelResponse = data;
            this.spinnerService.hide();
            if(this.modelResponse.code==200 && !!this.modelResponse){

              this.commonService.showAlertMessage(this.modelResponse.message);
           
            this.getAllModelData();
           
            this.modelFormGroup.reset();
           
            this.addModel = false;
            this.title = "Save";
            this.saveModel = false;
            this.updatedata = true;
            }else {
              this.commonService.showAlertMessage("Model Data Saving Failed.");
            }
           
          }, error => {
            console.log('ERROR >>>');
            this.spinnerService.hide();
            this.commonService.showAlertMessage("Model Data Saving Failed.");
          });
      }
    }

    getAllModelData() {
        const model : ModelModel[] = [];
        this.sendAndRequestService.requestForGET(Constants.app_urls.CONFIG.MODEL.GET_MODEL)
        .subscribe((data) => {
            this.modelList = data;
            for (let i = 0; i < this.modelList.length; i++) {
                this.modelList[i].sno = i+1;
                model.push(this.modelList[i]);              
            }
            this.modelDataSource = new MatTableDataSource(model);
            this.modelDataSource.paginator = this.paginator;
            this.modelDataSource.sort = this.sort;

        } , error => {});

    }
    
    onGoBack() {
        this.modelFormGroup.reset();
        this.addModel = false;
        this.title = 'Save';
    }


    NewModel () {
        this.addModel = true;
        this.modelFormGroup = this.formBuilder.group({
          id: 0,
          'modelCode': [null,Validators.compose([Validators.required,Validators.maxLength(255)]) , this.duplicateModelCode.bind(this)],
          'description': [null, Validators.maxLength(255)],
          'brandName': [null, Validators.maxLength(255)],
          'modelType' : [null, Validators.maxLength(255)],   
      });
    }

    applyFilter(filterValue: string) {
        filterValue = filterValue.trim(); // Remove whitespace
        filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
        this.modelDataSource.filter = filterValue;
      }

      deleteModel(id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
          disableClose: false
        });
        this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete the selected model?';
        this.confirmDialogRef.afterClosed().subscribe(result => {
          if (result) {
            this.spinnerService.show();
           // this.modelService.delete(id)
           this.sendAndRequestService.requestForDELETE(Constants.app_urls.CONFIG.MODEL.DELETE_MODEL_ID,id)
              .subscribe((data) => {
                this.commonService.showAlertMessage('Model Deleted Successfully.')
                this.getAllModelData();
              }, error => {
                this.modelErrors = error;
                this.commonService.showAlertMessage(error.statusText);
                this.spinnerService.hide();
              });
          }
          this.confirmDialogRef = null;
        });
    
    
        this.spinnerService.hide();
    
      }
    
      editModel(id) {
        this.spinnerService.show();
        this.addModel = true;
        this.cloneupdate = false;
        this.ModelEditAction(id);
        this.title = "Update";
       this.spinnerService.hide();
      }

      ModelEditAction(id: number) {
        this.modelFormGroup = this.formBuilder.group({
          id: 0,
          'modelCode': [null,Validators.compose([Validators.required,Validators.maxLength(255)]),this.duplicateModelCodeAndId.bind(this)],
          'description': [null, Validators.maxLength(255)],
          'brandName': [null, Validators.maxLength(255)],
          'modelType' : [null, Validators.maxLength(255)],  
      });
        this.addModel = true;
       // this.modelService.findModelById(id)
       this.sendAndRequestService.requestForGET(Constants.app_urls.CONFIG.MODEL.GET_MODEL_ID+id)
        .subscribe((resp) => {
          this.cloneupdate = false;
          this.updatedata = false;
          this.saveModel = false;
          this.editModelResponse = resp;
          this.responseStatus = resp;
          this.modelFormGroup.patchValue({
            id: this.responseStatus.id,
            
            modelCode: this.responseStatus.modelCode,
            description: this.responseStatus.description,
            brandName: this.responseStatus.brandName,
            modelType: this.responseStatus.modelType,
            
          });
        }, error => this.modelErrors = error);
        this.commonService.scrollTop("forms");
        this.id=id;
        if (!isNaN(this.id)) {
            this.modelFormGroup.valueChanges.subscribe(() => {
              this.onFormValuesChanged();
            });
            this.title = 'Update';
          } else {
            this.title = 'Save';      
          }
      }

      onFormValuesChanged() {
        for (const field in this.modelErrors) {
          if (!this.modelErrors.hasOwnProperty(field)) {
            continue;
          }
          // Clear previous errors
          this.modelErrors[field] = {};
    
          // Get the control
          const control = this.modelFormGroup.get(field);
          if (control && control.dirty && !control.valid) {
            this.modelErrors[field] = control.errors;
          }
        }
      }
    

      duplicateModelCode() {
        const q = new Promise((resolve, reject) => {
         
          let modelCode: string = this.modelFormGroup.controls['modelCode'].value;
        
         
         // this.modelService.existsModelCode( modelCode )
          this.sendAndRequestService.requestForGET(Constants.app_urls.CONFIG.MODEL.EXIST_MODEL_CODE+modelCode)
          .subscribe((duplicate) => {
            if (duplicate) {
              resolve({ 'duplicateModelCode': true });
            } else {
              resolve(null);
            }
          }, () => { resolve({ 'duplicateModelCode': true }); });
        });
        return q;
      }
      duplicateModelCodeAndId() {
        const q = new Promise((resolve, reject) => {

          let id=this.id;
          let modelCode: string = this.modelFormGroup.controls['modelCode'].value;
        
         
         // this.modelService.existsModelCode( modelCode )
          this.sendAndRequestService.requestForGET(Constants.app_urls.CONFIG.MODEL.EXIST_MODEL_CODE_AND_ID+id+'/'+modelCode)
          .subscribe((duplicate) => {
            if (duplicate) {
              resolve({ 'duplicateModelCodeAndId': true });
            } else {
              resolve(null);
            }
          }, () => { resolve({ 'duplicateModelCodeAndId': true }); });
        });
        return q;
      }
     
      ViewData(data){
        var result = {
          'title':this.Titles.MODEL,
          'dataSource':[        
            { label:FieldLabelsConstant.LABELS.MODEL_CODE, value:data.modelCode },
            { label:FieldLabelsConstant.LABELS.DESCRIPTION, value:data.description },
            { label:FieldLabelsConstant.LABELS.BRAND_NAME, value:data.brandName },
            { label:FieldLabelsConstant.LABELS.MODEL_TYPE, value:data.modelType }       
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
    

     


    
    
    

   