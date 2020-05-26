import { OnInit, Component, ViewChild } from '@angular/core';
import { ObservationCategoriesService } from 'src/app/services/observation-categories.service';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import { ObservationCategoriesModel } from 'src/app/models/observation-categories.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { ReportService  } from "src/app/services/report.service";
import { MatDatepickerInputEvent } from '@angular/material';

@Component({
    selector: 'observation-categories',
    templateUrl: './observation-categories.component.html',
    styleUrls: ['./observation-categories.component.scss']
})
export class ObservationCategoriesComponent implements OnInit{

    addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    addObservationCategories: boolean ;
    title: string = "Save";
    observationCategoriesFormGroup: FormGroup;
    observationCategoriesList : any;
    toMinDate=new Date();
    inspectionTypeData:any;
    observationCategoriesItemDataSource: MatTableDataSource<ObservationCategoriesModel>;
    observationCategoriesDisplayColumns = ['sno' ,'inspectionType','department' , 'observationCategory' , 'description' , 'remark' , 'fromDate' , 'thruDate' , 'id' ] ;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    editobservationCategoriesResponse: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;


    constructor(
        private observationCategoriesService: ObservationCategoriesService,
        private reportService: ReportService,
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private dialog: MatDialog
    ){

    }

    ngOnInit () {
        console.log('in ngOnintit method:::');
        this.getAllObservationCategoriesData();
        this.inspectionType();
        var permissionName = this.commonService.getPermissionNameByLoggedData("ENERGY BILL PAYMENT","Obs Categories") ;//p == 0 ? 'No Permission' : p[0].permissionName;
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName); 
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
        this.observationCategoriesFormGroup = this.formBuilder.group({
            id: 0,
            'inspectionType':[null],
            'department':[null],
            'observationCategory': [null,Validators.maxLength(250)],
            'description': [null,Validators.maxLength(250)],
            'remark': [null,Validators.maxLength(250)],
            'fromDate' : [null],
            'thruDate' : [null]
        });
    }

    addEvent(type: string, event: MatDatepickerInputEvent<Date>) {
        this.toMinDate = event.value;
      }
    getAllObservationCategoriesData() {
        const observationCategories : ObservationCategoriesModel[] = [];
        this.observationCategoriesService.getAllObservationCategoriesDetails().subscribe((data) => {
            this.observationCategoriesList = data;
            for (let i = 0; i < this.observationCategoriesList.length; i++) {
                this.observationCategoriesList[i].sno = i+1;
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
        if (this.title ==  Constants.EVENTS.SAVE) {
            this.observationCategoriesService.saveObservationCategories({
                'inspectionType':inspectionType,
                'department':department,
                'observationCategory': observationCategory,
                'description': description,
                'remark': remark,
                'fromDate':fromDate,
                'thruDate': thruDate
            }).subscribe((data) => {
                this.commonService.showAlertMessage('Successfully saved');
                this.getAllObservationCategoriesData();
                this.observationCategoriesFormGroup.reset();
            } , error => {});
        }else if (this.title == Constants.EVENTS.UPDATE ) {
            let id: number = this.editobservationCategoriesResponse.id;
            this.observationCategoriesService.updateObservationCategories({
                'id':id,
                'inspectionType':inspectionType,
                'department':department,
                'observationCategory': observationCategory,
                'description':description,
                'remark': remark,
                'fromDate' : fromDate,
                'thruDate': thruDate
            }).subscribe((data) =>{
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
        this.title = 'Update';
    }

    observationCategoriesEditAction(id: number) {
        this.observationCategoriesService.findObservationCategoriesById(id).subscribe((responseData) => {
            this.editobservationCategoriesResponse = responseData;
            this.observationCategoriesFormGroup.patchValue({
                id: this.editobservationCategoriesResponse.id,
                inspectionType:this.editobservationCategoriesResponse.inspectionType,
                department:this.editobservationCategoriesResponse.department,
                observationCategory: this.editobservationCategoriesResponse.observationCategory,
                description: this.editobservationCategoriesResponse.description,
                remark: this.editobservationCategoriesResponse.remark,
                fromDate: this.editobservationCategoriesResponse.fromDate,
                thruDate: this.editobservationCategoriesResponse.thruDate,
            })
        } ,error => {})
    }


    deleteObservationCategories (id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected Observation Categories?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
                this.observationCategoriesService.deleteObservationCategories(id)
                    .subscribe((data) => {
                        this.commonService.showAlertMessage('Observation Categories Deleted Successfully');
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
        this.title = 'Save';
    }
    inspectionType()
        {
               
               this.reportService. inspectionType().subscribe((data) => {
                 this.inspectionTypeData = data;
        }
               );

       }

    NewObservationCategories () {
        this.addObservationCategories = true;
    }

}