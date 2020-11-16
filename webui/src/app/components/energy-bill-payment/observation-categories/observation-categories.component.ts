import { OnInit, Component, ViewChild } from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import { ObservationCategoriesModel } from 'src/app/models/observation-categories.model';
import { ActivatedRoute, Router } from '@angular/router';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog,DateAdapter, MAT_DATE_FORMATS} from '@angular/material';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { MatDatepickerInputEvent } from '@angular/material';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { DataViewDialogComponent } from '../../data-view-dialog/data-view-dialog.component';
import { DatePipe } from '@angular/common';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';


@Component({
    selector: 'observation-categories',
    templateUrl: './observation-categories.component.html',
    providers: [
      {
          provide: DateAdapter, useClass: AppDateAdapter
      },
      {
          provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
      }
      ]
})
export class ObservationCategoriesComponent implements OnInit{


  pagination = Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
    addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    addObservationCategories: boolean ;
    title: string = Constants.EVENTS.ADD;
    id: number = 0;
    observationCategoriesFormGroup: FormGroup;
    observationCategoriesList : any;
    toMinDate=new Date();
    inspectionTypeData:any;
    obsCategoriesErrors: any;
    observationCategoriesItemDataSource: MatTableDataSource<ObservationCategoriesModel>;
    dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
    observationCategoriesDisplayColumns = ['sno' ,'inspectionType','department' , 'observationCategory' , 'description' , 'remark' , 'fromDate' , 'thruDate' , 'id' ] ;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    editobservationCategoriesResponse: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;


    constructor(
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private dialog: MatDialog,
        private datePipe: DatePipe,
        private sendAndRequestService:SendAndRequestService

    ){
        this.obsCategoriesErrors = {
            inspectionType: {},
            observationCategory:{}
      
          };
    }

    ngOnInit () {
        this.getAllObservationCategoriesData();
        this.inspectionType();
        var permissionName = this.commonService.getPermissionNameByLoggedData("FP","Obs Categories") ;
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName); 
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
        
    }
     
    onFormValuesChanged() {
        for (const field in this.obsCategoriesErrors) {
          if (!this.obsCategoriesErrors.hasOwnProperty(field)) {
            continue;
          }
          // Clear previous errors
          this.obsCategoriesErrors[field] = {};
    
          // Get the control
          const control = this.observationCategoriesFormGroup.get(field);
          if (control && control.dirty && !control.valid) {
            this.obsCategoriesErrors[field] = control.errors;
          }
        }
      }
      duplicateObservationCategory() {
        const q = new Promise((resolve, reject) => {
          let observationCategories: string = this.observationCategoriesFormGroup.controls['observationCategory'].value;
          var filter = !!this.observationCategoriesList && this.observationCategoriesList.filter(observationCategorie => {
            return observationCategorie.observationCategory.toLowerCase() == observationCategories.trim().toLowerCase();
          });
          if (filter.length > 0) {
            resolve({ 'duplicate': true });
          }
          this.sendAndRequestService.requestForGET(
              Constants.app_urls.DAILY_SUMMARY.OBSERVATION_CATEGORIES.EXISTS_INPECTION_TYPE_OBJ_CATG +
	        this.observationCategoriesFormGroup.controls['inspectionType'].value + '/'+
	        this.observationCategoriesFormGroup.controls['observationCategory'].value
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
      duplicateObservationCategoryAndId() {
        let id=this.id;
        let inspectionType: string = this.observationCategoriesFormGroup.controls['inspectionType'].value;
        let observationCategory: string = this.observationCategoriesFormGroup.controls['observationCategory'].value;

        const q = new Promise((resolve, reject) => {          
  
           this.sendAndRequestService.requestForGET(
                  Constants.app_urls.DAILY_SUMMARY.OBSERVATION_CATEGORIES.EXISTS_INPECTION_TYPE_OBJ_CATG_AND_ID+id+'/'+inspectionType+'/'+observationCategory).subscribe
                  ((duplicate) => {
            if (duplicate) {
              resolve({ 'duplicateObservationCategoryAndId': true });
            } else {
              resolve(null);
            }
          }, () => { resolve({ 'duplicateObservationCategoryAndId': true }); });
        });
        return q;
      } 
      public get f() { return this.observationCategoriesFormGroup.controls; }
     
    addEvent(type: string, event: MatDatepickerInputEvent<Date>) {
        this.toMinDate = new Date(event.value);
    }
    getAllObservationCategoriesData() {
        const observationCategories : ObservationCategoriesModel[] = [];
        this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.OBSERVATION_CATEGORIES.GET_OBS_CATEGORIES).subscribe((data) => {
            this.observationCategoriesList = data;
            for (let i = 0; i < this.observationCategoriesList.length; i++) {
                this.observationCategoriesList[i].sno = i+1;
                this.observationCategoriesList[i].fromDate = this.datePipe.transform(this.observationCategoriesList[i].fromDate, 'dd-MM-yyyy');
                this.observationCategoriesList[i].thruDate = this.datePipe.transform(this.observationCategoriesList[i].thruDate, 'dd-MM-yyyy');
                observationCategories.push(this.observationCategoriesList[i]);              
            }
            this.observationCategoriesItemDataSource = new MatTableDataSource(observationCategories);
            this.observationCategoriesItemDataSource.paginator = this.paginator;
            this.observationCategoriesItemDataSource.sort = this.sort;

        } , error => {});

    }

    observationCategoriesSubmit () {
        let inspectionType: string = this.observationCategoriesFormGroup.value.inspectionType;
        let department: string = this.observationCategoriesFormGroup.value.department;
        let observationCategory: string = this.observationCategoriesFormGroup.value.observationCategory;
        let description: string = this.observationCategoriesFormGroup.value.description;
        let remark: string = this.observationCategoriesFormGroup.value.remark;
        let fromDate: Date = this.observationCategoriesFormGroup.value.fromDate;
        let thruDate: Date = this.observationCategoriesFormGroup.value.thruDate;
        this.addObservationCategories = false;
        if (this.title ==  Constants.EVENTS.ADD) {
            var saveObsCategoriesModel={
                'inspectionType':inspectionType,
                'department':department,
                'observationCategory': observationCategory,
                'description': description,
                'remark': remark,
                'fromDate':fromDate,
                'thruDate': thruDate
            }                
            this.sendAndRequestService.requestForPOST(Constants.app_urls.DAILY_SUMMARY.OBSERVATION_CATEGORIES.SAVE_OBS_CATEGORIES, saveObsCategoriesModel, false).subscribe(response => {
                this.commonService.showAlertMessage('Successfully saved');
                this.getAllObservationCategoriesData();
                this.observationCategoriesFormGroup.reset();
            } , error => {});
        }else if (this.title == Constants.EVENTS.UPDATE ) {
            let id: number = this.editobservationCategoriesResponse.id;
            var updateObsCategoriesMode={
                'id':id,
                'inspectionType':inspectionType,
                'department':department,
                'observationCategory': observationCategory,
                'description':description,
                'remark': remark,
                'fromDate' : fromDate,
                'thruDate': thruDate
            } 
            this.sendAndRequestService.requestForPUT(Constants.app_urls.DAILY_SUMMARY.OBSERVATION_CATEGORIES.UPDATE_OBS_CATEGORIES,updateObsCategoriesMode, false).subscribe(response => {
                this.commonService.showAlertMessage('Successfully updated');
                this.getAllObservationCategoriesData();
                this.observationCategoriesFormGroup.reset();
                this.addObservationCategories =  false;
            } , error => {})
            
        }
    }

    editObservationCategories (id) {
        this.addObservationCategories = true;
        this.observationCategoriesEditAction(id);
        this.title = Constants.EVENTS.UPDATE;
    }

    observationCategoriesEditAction(id: number) {
      this.observationCategoriesFormGroup = this.formBuilder.group({
        id: 0,
        'inspectionType':[null],
        'department':[null,Validators.maxLength(250)],
        'observationCategory':[null, Validators.compose([Validators.required, Validators.maxLength(250)]),this.duplicateObservationCategoryAndId.bind(this)],
        'description': [null,Validators.maxLength(250)],
        'remark': [null,Validators.maxLength(250)],
        'fromDate' : [null],
        'thruDate' : [null]
    });
        this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.OBSERVATION_CATEGORIES.GET_OBS_CATEGORIES_ID+id).subscribe((responseData) => {
            this.editobservationCategoriesResponse = responseData;
            this.observationCategoriesFormGroup.patchValue({
                id: this.editobservationCategoriesResponse.id,
                inspectionType:this.editobservationCategoriesResponse.inspectionType,
                department:this.editobservationCategoriesResponse.department,
                observationCategory: this.editobservationCategoriesResponse.observationCategory,
                description: this.editobservationCategoriesResponse.description,
                remark: this.editobservationCategoriesResponse.remark,
                fromDate: this.editobservationCategoriesResponse.fromDate,
                thruDate: !!this.editobservationCategoriesResponse.thruDate ? new Date(this.editobservationCategoriesResponse.thruDate) : '',   
            });
            this.toMinDate = new Date(this.editobservationCategoriesResponse.fromDate);
        } ,error => {})
        this.id=id;
        if (!isNaN(this.id)) {
            this.observationCategoriesFormGroup.valueChanges.subscribe(() => {
              this.onFormValuesChanged();
            });
            this.title = Constants.EVENTS.UPDATE;
          } else {
            this.title = Constants.EVENTS.ADD;      
          }
    }


    deleteObservationCategories (id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected observation Categories?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
                this.sendAndRequestService.requestForDELETE(Constants.app_urls.DAILY_SUMMARY.OBSERVATION_CATEGORIES.DELETE_OBS_CATEGORIES, id).subscribe(response => {
                        this.commonService.showAlertMessage('observation Categories Deleted Successfully');
                        this.getAllObservationCategoriesData();
                    },error => {});
            }
        });
    }


    applyFilter(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase();
        this.observationCategoriesItemDataSource.filter = filterValue;
    }

    onGoBack() {
        this.observationCategoriesFormGroup.reset();
        this.addObservationCategories = false;
        this.title = Constants.EVENTS.ADD;
    }
    inspectionType()
        {
               
               this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_INSPECTION_TYPE).subscribe((data) => {
                 this.inspectionTypeData = data;
        }
               );

       }

    NewObservationCategories () {
        this.addObservationCategories = true;
        this.observationCategoriesFormGroup = this.formBuilder.group({
          id: 0,
          'inspectionType':[null],
          'department':[null,Validators.maxLength(250)],
          'observationCategory':[null, Validators.compose([Validators.required, Validators.maxLength(250)]), this.duplicateObservationCategory.bind(this)],
          'description': [null,Validators.maxLength(250)],
          'remark': [null,Validators.maxLength(250)],
          'fromDate' : [null],
          'thruDate' : [null]
      });
    }
    ViewData(data){
      var result = {
        'title':this.Titles.OBSERVATION_CATEGORIES_DATA,
        'dataSource':[

           { label:FieldLabelsConstant.LABELS.INSPECTION_TYPE, value:data.inspectionType },
           { label:FieldLabelsConstant.LABELS.REQUEST_DEPARTMENT, value:data.department },
           { label:FieldLabelsConstant.LABELS.OBSERVATION_CATEGORY, value:data.observationCategory },
           { label:FieldLabelsConstant.LABELS.DESCRIPTION, value:data.description },
           { label:FieldLabelsConstant.LABELS.FROM_DATE, value:data.fromDate },
           { label:FieldLabelsConstant.LABELS.THRU_DATE, value:data.thruDate },
           { label:FieldLabelsConstant.LABELS.REMARKS, value:data.remark },
           
          
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