import { OnInit, Component, ViewChild } from '@angular/core';
import { ObservationsCheckListService } from 'src/app/services/observations-check-list.service';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import { ObservationsCheckListModel } from 'src/app/models/observations-check-list.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { ReportService  } from "src/app/services/report.service";
import { FacilityModel } from 'src/app/models/facility.model';
import { MatDatepickerInputEvent } from '@angular/material';


@Component({
    selector: 'observation-check-list',
    templateUrl: './observation-check-list.component.html',
    styleUrls: ['./observation-check-list.component.scss']
})
export class ObservationCheckListComponent implements OnInit{

    addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    addObservationCheckListItem: boolean ;
    title: string = "Save";
    ObservationCheckListItemFormGroup: FormGroup;
    observationCheckList : any;
    inspectionTypeData:any;
    toMinDate=new Date();
    observationCheckListItemDataSource: MatTableDataSource<ObservationsCheckListModel>;
    observationCheckListDisplayColumns = ['sno' ,'inspectionType','observationCategory' , 'observationItem' , 'severity' ,'description','priority', 'fromDate' , 'thruDate' , 'id' ] ;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    editObservationCheckLisResponse: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;


    constructor(
        private observationsCheckListService: ObservationsCheckListService,
        private reportService: ReportService,
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private dialog: MatDialog
    ){

    }

    ngOnInit () {
        console.log('in ngOnintit method:::');
        this.getAllObservationsCheckListData();
        this.observationCategories();
        var permissionName = this.commonService.getPermissionNameByLoggedData("ENERGY BILL PAYMENT","OBSERVATION CHECK LIST") ;//p == 0 ? 'No Permission' : p[0].permissionName;
  		console.log("permissionName = "+permissionName);
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName); //getPermission("Add", );
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
        this.ObservationCheckListItemFormGroup = this.formBuilder.group({
            id: 0,
            'inspectionType':[null],
            'observationCategory':[null],
            'observationItem': [null],
            'severity': [null],
            'description': [null,Validators.maxLength(250)],
            'priority': [null],
            'fromDate': [null],
            'thruDate' : [null]
        });
    }
    addEvent(type: string, event: MatDatepickerInputEvent<Date>) {
        this.toMinDate = event.value;
      }
    getAllObservationsCheckListData() {
        const observationsCheckList : ObservationsCheckListModel[] = [];
        this.observationsCheckListService.getAllObservationCheckListDetails().subscribe((data) => {
            this.observationCheckList = data;
            for (let i = 0; i < this.observationCheckList.length; i++) {
                this.observationCheckList[i].sno = i+1;
                observationsCheckList.push(this.observationCheckList[i]);              
            }
            this.observationCheckListItemDataSource = new MatTableDataSource(observationsCheckList);
            this.observationCheckListItemDataSource.paginator = this.paginator;
            this.observationCheckListItemDataSource.sort = this.sort;

        } , error => {});

    }

    observationsCheckListItemSubmit () {
        let inspectionType: string = this.ObservationCheckListItemFormGroup.value.inspectionType;
        let observationCategory: string = this.ObservationCheckListItemFormGroup.value.observationCategory;
        let observationItem: string = this.ObservationCheckListItemFormGroup.value.observationItem;
        let severity: string = this.ObservationCheckListItemFormGroup.value.severity;
        let description: string = this.ObservationCheckListItemFormGroup.value.description;
        let priority: string = this.ObservationCheckListItemFormGroup.value.priority;
        let fromDate: Date = this.ObservationCheckListItemFormGroup.value.fromDate;
        let thruDate: Date = this.ObservationCheckListItemFormGroup.value.thruDate;
        this.addObservationCheckListItem = false;
        
        if (this.title ==  Constants.EVENTS.SAVE) {
            this.observationsCheckListService.saveObservationCheckList({
                'inspectionType':inspectionType,
                'observationCategory':observationCategory,
                'observationItem': observationItem,
                'description' : description,
                'priority' : priority,
                'severity': severity,
                'fromDate':fromDate,
                'thruDate': thruDate
            }).subscribe((data) => {
                this.commonService.showAlertMessage('Successfully saved');
                this.getAllObservationsCheckListData();
                this.ObservationCheckListItemFormGroup.reset();
            } , error => {});
        }else if (this.title == Constants.EVENTS.UPDATE ) {
            let id: number = this.editObservationCheckLisResponse.id;
            this.observationsCheckListService.updateObservationCheckList({
                'id':id,
                'inspectionType':inspectionType,
                'observationCategory':observationCategory,
                'observationItem': observationItem,
                'description':description,
                'priority': priority,
                'severity': severity,
                'fromDate': fromDate,
                'thruDate' : thruDate
            }).subscribe((data) =>{
                this.commonService.showAlertMessage('Successfully updated');
                this.getAllObservationsCheckListData();
                this.ObservationCheckListItemFormGroup.reset();
                this.addObservationCheckListItem =  false;
            } , error => {})
            
        }
    }

    editObservationsCheckListItem (id) {
        this.addObservationCheckListItem = true;
        this.observationCheckListEditAction(id);
        this.title = 'Update';
    }

    observationCheckListEditAction(id: number) {
        this.observationsCheckListService.findObservationCheckListById(id).subscribe((responseData) => {
            this.editObservationCheckLisResponse = responseData;
            this.ObservationCheckListItemFormGroup.patchValue({
                id: this.editObservationCheckLisResponse.id,
                inspectionType:this.editObservationCheckLisResponse.inspectionType,
                observationCategory:this.editObservationCheckLisResponse.observationCategory,
                observationItem: this.editObservationCheckLisResponse.observationItem,
                description: this.editObservationCheckLisResponse.description,
                priority: this.editObservationCheckLisResponse.priority,
                severity: this.editObservationCheckLisResponse.severity,
                fromDate: this.editObservationCheckLisResponse.fromDate,
                thruDate: this.editObservationCheckLisResponse.thruDate
            })
        } ,error => {})
    }


    deleteObservationCheckListItem (id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected Observations Check List?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
                this.observationsCheckListService.deleteObservationCheckList(id)
                    .subscribe((data) => {
                        this.commonService.showAlertMessage('Observations Check List Deleted Successfully');
                        this.getAllObservationsCheckListData();
                    },error => {});
            }
        });
    }


    applyFilter(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase();
        this.observationCheckListItemDataSource.filter = filterValue;
    }

    onGoBack() {
        this.ObservationCheckListItemFormGroup.reset();
        this.addObservationCheckListItem = false;
        this.title = 'Save';
    }
    observationCategories()
        {
               
               this.reportService. observationCategories().subscribe((data) => {
                 this.inspectionTypeData = data;
        }
               );

       }

       public priority=['Low','Medium','High'];

    NewObservationCheckList () {
        this.addObservationCheckListItem = true;
    }

}