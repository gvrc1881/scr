import { OnInit, Component, ViewChild, ElementRef} from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { MeasureActivityModel } from 'src/app/models/measure-activity.model';
import { Constants } from 'src/app/common/constants';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';


@Component({
    selector: 'measure-activity',
    templateUrl: './measure-activity.component.html',
    styleUrls: []
})

export class MeasureActivityComponent implements OnInit{
    editPermission: boolean = true;
    addPermission: boolean = true;
    deletePermission: boolean = true;
    addMeasureActivity:boolean;
    measureActivityFormGroup:FormGroup;
    id: number = 0;
    title: string = "Save";
    measureList:any;    
    editMeasureResponse: any;  
    measureErrors : any;
    saveMeasure:boolean;
    value:string;
    filterData;
    gridData = [];
    pattern = "^[A-Z0-9]+$";
    measureActivityDataSource: MatTableDataSource<MeasureActivityModel>;
    measureDisplayColumns = ['sno','activityId','activityName','activityType','unitOfMeasure','id'] ;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    @ViewChild('filter', { static: true }) filter: ElementRef;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    measureResponse:any;
    loggedUserData: any = JSON.parse(localStorage.getItem('userData'));

    constructor( 
        private formBuilder: FormBuilder,
        private commonService: CommonService,
        private spinnerService: Ng4LoadingSpinnerService,
        private sendAndRequestService:SendAndRequestService,
        public dialog: MatDialog
        ){
                this.measureErrors = {
                  activityId: {},
                  activityName :{},
            
                };
            
    }

    ngOnInit () {

        var permissionName = this.commonService.getPermissionNameByLoggedData("MASTERS","Measure activity") ;
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
    
        this.getAllMeasureData();  
       
        this.filterData = {
          filterColumnNames: [
            { "Key": 'sno', "Value": " " },
            { "Key": 'activityId', "Value": " " },           
            { "Key": 'activityName', "Value": " " },
            { "Key": 'activityType', "Value": " " },
            { "Key": 'unitOfMeasure', "Value": " " },
           
          ],
          gridData: this.gridData,
          dataSource: this.measureActivityDataSource,
          paginator: this.paginator,
          sort: this.sort
         
        }; 
       
    }
    updatePagination() {
      this.filterData.dataSource = this.filterData.dataSource;
      this.filterData.dataSource.paginator = this.paginator;
    }
    measureActivitySubmit(){ 
        let activityId:String=this.measureActivityFormGroup.value.activityId;
        let activityName:String=this.measureActivityFormGroup.value.activityName;
        let activityType:String=this.measureActivityFormGroup.value.activityType;
        let unitOfMeasure:String=this.measureActivityFormGroup.value.unitOfMeasure;
              
   
       this.addMeasureActivity=false;
       if (this.title ==  Constants.EVENTS.SAVE) {
        var saveMeasureModel={
                                'activityId':activityId,
                                'activityName': activityName,
                                'activityType':activityType,
                                'unitOfMeasure':unitOfMeasure,
                                "createdBy" : this.loggedUserData.username
        }              
        this.sendAndRequestService.requestForPOST(Constants.app_urls.MASTERS.MEASURE_ACTIVITY.SAVE_MEASURE,saveMeasureModel , false).subscribe(data => {
            this.measureResponse = data;
            if(this.measureResponse.code == 200 && !!this.measureResponse) {
                this.commonService.showAlertMessage(this.measureResponse.message);
                this.getAllMeasureData();
                this.measureActivityFormGroup.reset();
            }else {
                this.commonService.showAlertMessage("Measure Activity Data Saving Failed.");
            }
            this.spinnerService.hide();
        } , error => {
            console.log('ERROR >>>');
            this.spinnerService.hide();
            this.commonService.showAlertMessage("Measure Activity Data Saving Failed.");
        });
    }

      
        else if (this.title == Constants.EVENTS.UPDATE )
         {
            let id: number = this.editMeasureResponse.id;
            var updateMeasureModel={
                                        'id':id,
                                        'activityId':activityId,
                                        'activityName': activityName,
                                        'activityType':activityType,
                                        'unitOfMeasure':unitOfMeasure
                                     }    
                this.sendAndRequestService.requestForPUT(Constants.app_urls.MASTERS.MEASURE_ACTIVITY.UPDATE_MEASURE,updateMeasureModel, false).
                subscribe(data => {
                                    this.measureResponse = data;
                                    if(this.measureResponse.code == 200 && !!this.measureResponse)
                                    {
                                        this.commonService.showAlertMessage(this.measureResponse.message);
                                        this.getAllMeasureData();
                                        this.measureActivityFormGroup.reset();
                                        this.addMeasureActivity =  false;
                                        this.title = "Save";
                                    }
                                    else {
                                            this.commonService.showAlertMessage("Measure Activity Data Updating Failed.");
                                        }
                                } , 
                                error => {
            	
                                        this.spinnerService.hide();
                                        this.commonService.showAlertMessage("Measure Activity Data Updating Failed.");
                                        })
            
        }
    }

    getAllMeasureData() {

            const measure : MeasureActivityModel[] = [];
            this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.MEASURE_ACTIVITY.GET_MEASURE).subscribe((data) => {
                this.measureList = data;
                for (let i = 0; i < this.measureList.length; i++) {
                    this.measureList[i].sno = i+1;
                    measure.push(this.measureList[i]);              
                }
                // this.measureActivityDataSource = new MatTableDataSource(measure);
                // this.measureActivityDataSource.paginator = this.paginator;
                // this.measureActivityDataSource.sort = this.sort;
                this.filterData.gridData = measure;
      this.measureActivityDataSource = new MatTableDataSource(measure);
      this.commonService.updateDataSource(this.measureActivityDataSource, this.measureDisplayColumns);
      this.filterData.dataSource = this.measureActivityDataSource;
      this.measureActivityDataSource.paginator = this.paginator;
      this.measureActivityDataSource.sort = this.sort;
                this.spinnerService.hide();
            } , error => {
                this.spinnerService.hide();
            });
    
        }
    
    onGoBack() {
        this.measureActivityFormGroup.reset();
        this.addMeasureActivity = false;
        this.title = 'Save';
    }


    NewMeasureActivity () 
    {
        this.addMeasureActivity = true;
    
        this.measureActivityFormGroup = this.formBuilder.group({
            id: 0,
            'activityId' : [null,Validators.compose([Validators.required,Validators.maxLength(255)]) , this.duplicateActivityId.bind(this)],
            'activityName' : [null,Validators.compose([Validators.required,Validators.maxLength(255)]) , this.duplicateActivityName.bind(this)],
            'activityType': [null,Validators.compose([Validators.required,Validators.maxLength(255)])],
            'unitOfMeasure':[null,Validators.maxLength(255)]
        });
    }
        
    

    applyFilter(filterValue: string) {
        filterValue = filterValue.trim(); // Remove whitespace
        filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
        this.measureActivityDataSource.filter = filterValue;
      }

     deleteMeasureActivity (id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected Measure OR Activity?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
                this.sendAndRequestService.requestForDELETE(Constants.app_urls.MASTERS.MEASURE_ACTIVITY.DELETE_MEASURE, id).subscribe(data => {
                    	this.measureResponse = data;
            			if(this.measureResponse.code == 200 && !!this.measureResponse) {
                        	this.commonService.showAlertMessage(this.measureResponse.message);
                         	this.getAllMeasureData();
                         } else {
                         	this.commonService.showAlertMessage("Meaure Activity Deletion Failed.");
                         }	
                    },error => {
                    	console.log('ERROR >>>');
          				this.spinnerService.hide();
          				this.commonService.showAlertMessage("Measure Activity Deletion Failed.");
                    });
            }
        });
    }
    
    editMeasurreActivity (id) {
        this.addMeasureActivity = true;
        this.measureEditAction(id);
        this.title = 'Update';
    
    }
    measureEditAction(id: number) {
        this.measureActivityFormGroup = this.formBuilder.group({
            id: 0,
            'activityId' : [null,Validators.maxLength(255)],
            'activityName' : [null,Validators.maxLength(255)],
            'activityType': [null,Validators.maxLength(255)],
            'unitOfMeasure':[null,Validators.maxLength(255)]
        });
        this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.MEASURE_ACTIVITY.GET_MEASURE_ID+id).subscribe((responseData) => {
            this.editMeasureResponse = responseData;
             
              this.measureActivityFormGroup.patchValue
              ({
                                id: this.editMeasureResponse.id,
                                activityId:this.editMeasureResponse.activityId,
                                activityName: this.editMeasureResponse.activityName,
                                activityType: this.editMeasureResponse.activityType,
                                unitOfMeasure:this.editMeasureResponse.unitOfMeasure
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
        for (const field in this.measureErrors) {
          if (!this.measureErrors.hasOwnProperty(field)) {
            continue;
          }
          // Clear previous errors
          this.measureErrors[field] = {};
    
          // Get the control
          const control = this.measureActivityFormGroup.get(field);
          if (control && control.dirty && !control.valid) {
            this.measureErrors[field] = control.errors;
          }
        }
      }
      duplicateActivityId() {
       let activityId:string= this.measureActivityFormGroup.controls['activityId'].value 
    	const q = new Promise((resolve, reject) => {
	       this.sendAndRequestService.requestForGET(
                Constants.app_urls.MASTERS.MEASURE_ACTIVITY.EXISTS_ACTIVITY_ID +activityId
	           
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

    duplicateActivityName() {
      let activityName: string = this.measureActivityFormGroup.controls['activityName'].value;
      const q = new Promise((resolve, reject) => {
        

         this.sendAndRequestService.requestForGET(
                Constants.app_urls.MASTERS.MEASURE_ACTIVITY.EXISTS_ACTIVITYNAME +
                activityName
                ).subscribe
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

    
    duplicateActivityNameAndUnitOfMeasure() {
      let activityName: string = this.measureActivityFormGroup.controls['activityName'].value;     
      let unitOfMeasure: string = this.measureActivityFormGroup.controls['unitOfMeasure'].value;

     if (unitOfMeasure==null) {
        this.duplicateActivityName();
       
     } else {
      const q = new Promise((resolve, reject) => {
    

         this.sendAndRequestService.requestForGET(
                Constants.app_urls.MASTERS.MEASURE_ACTIVITY.EXISTS_ACTIVITYNAME_UNITOFMEASURE +activityName+'/'+unitOfMeasure).subscribe
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

      
     
}


    
    
    

   