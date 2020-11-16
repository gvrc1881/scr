import { OnInit, Component, ViewChild } from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import { ObservationsCheckListModel } from 'src/app/models/observations-check-list.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog,DateAdapter, MAT_DATE_FORMATS } from '@angular/material';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { Router } from '@angular/router';
import { MatDatepickerInputEvent } from '@angular/material';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { DataViewDialogComponent } from '../../data-view-dialog/data-view-dialog.component';
import { DatePipe } from '@angular/common';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';




@Component({
    selector: 'observation-check-list',
    templateUrl: './observation-check-list.component.html',
    providers: [
        {
            provide: DateAdapter, useClass: AppDateAdapter
        },
        {
            provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
        }
        ]
})
export class ObservationCheckListComponent implements OnInit {

    pagination = Constants.PAGINATION_NUMBERS;
    FiledLabels = FieldLabelsConstant.LABELS;
    Titles = FieldLabelsConstant.TITLE;
    addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    addObservationCheckListItem: boolean;
    title: string = Constants.EVENTS.ADD ;
    ObservationCheckListItemFormGroup: FormGroup;
    observationCheckList: any;
    inspectionTypeData: any;
    inspectionTypeList: any;
    statusTypeData: any;
    toMinDate = new Date();
    currentDate = new Date();
    observationCheckListItemDataSource: MatTableDataSource<ObservationsCheckListModel>;
    dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
    observationCheckListDisplayColumns = ['sno', 'inspectionType', 'observationCategory', 'observationItem', 'severity', 'description', 'priority', 'fromDate', 'thruDate', 'id'];
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    editObservationCheckLisResponse: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;


    constructor(
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private router: Router,
        private dialog: MatDialog,
        private datePipe: DatePipe,
        private sendAndRequestService: SendAndRequestService
    ) {

    }

    ngOnInit() {
        let statusTypeId = '';

        if (this.router.url == '/observation-check-list') {
            statusTypeId = 'FP_SEVERITY_TYPE';
        }
        this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_STATUS_ITEM + statusTypeId).subscribe((data) => {
            this.statusTypeData = data;
        })

        this.getAllObservationsCheckListData();
        this.observationCategories();
        this.inspectionType();
        var permissionName = this.commonService.getPermissionNameByLoggedData("FP", "Obs Check List");//p == 0 ? 'No Permission' : p[0].permissionName;
        this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
        this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
        this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
        this.ObservationCheckListItemFormGroup = this.formBuilder.group({
            id: 0,
            'inspectionType': [null],
            'observationCategory': [null],
            'observationItem': [null],
            'severity': [null],
            'description': [null, Validators.maxLength(250)],
            'priority': [null],
            'fromDate': [null],
            'thruDate': [null]
        });
    }
    addEvent(type: string, event: MatDatepickerInputEvent<Date>) {
        this.toMinDate = new Date(event.value);
    }
    getAllObservationsCheckListData() {
        const observationsCheckList: ObservationsCheckListModel[] = [];
        this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.OBSERVATION_CHECK_LIST.GET_OBS_CHECK_LIST).subscribe((data) => {
            this.observationCheckList = data;
            for (let i = 0; i < this.observationCheckList.length; i++) {
                this.observationCheckList[i].sno = i + 1;
                this.observationCheckList[i].fromDate = this.datePipe.transform(this.observationCheckList[i].fromDate, 'dd-MM-yyyy');
                this.observationCheckList[i].thruDate = this.datePipe.transform(this.observationCheckList[i].thruDate, 'dd-MM-yyyy');
                observationsCheckList.push(this.observationCheckList[i]);
            }
            this.observationCheckListItemDataSource = new MatTableDataSource(observationsCheckList);
            this.observationCheckListItemDataSource.paginator = this.paginator;
            this.observationCheckListItemDataSource.sort = this.sort;

        }, error => { });

    }

    observationsCheckListItemSubmit() {
        let inspectionType: string = this.ObservationCheckListItemFormGroup.value.inspectionType;
        let observationCategory: string = this.ObservationCheckListItemFormGroup.value.observationCategory;
        let observationItem: string = this.ObservationCheckListItemFormGroup.value.observationItem;
        let severity: string = this.ObservationCheckListItemFormGroup.value.severity;
        let description: string = this.ObservationCheckListItemFormGroup.value.description;
        let priority: string = this.ObservationCheckListItemFormGroup.value.priority;
        let fromDate: Date = this.ObservationCheckListItemFormGroup.value.fromDate;
        let thruDate: Date = this.ObservationCheckListItemFormGroup.value.thruDate;
        this.addObservationCheckListItem = false;

        if (this.title == Constants.EVENTS.ADD) {
            var saveObsCheckListModel = {
                'inspectionType': inspectionType,
                'observationCategory': observationCategory,
                'observationItem': observationItem,
                'description': description,
                'priority': priority,
                'severity': severity,
                'fromDate': fromDate,
                'thruDate': thruDate
            }
            this.sendAndRequestService.requestForPOST(Constants.app_urls.DAILY_SUMMARY.OBSERVATION_CHECK_LIST.SAVE__OBS_CHECK_LIST, saveObsCheckListModel, false).subscribe(response => {
                this.commonService.showAlertMessage('Successfully saved');
                this.getAllObservationsCheckListData();
                this.ObservationCheckListItemFormGroup.reset();
            }, error => { });
        } else if (this.title == Constants.EVENTS.UPDATE) {
            let id: number = this.editObservationCheckLisResponse.id;
            var updateObsCheckListModel = {
                'id': id,
                'inspectionType': inspectionType,
                'observationCategory': observationCategory,
                'observationItem': observationItem,
                'description': description,
                'priority': priority,
                'severity': severity,
                'fromDate': fromDate,
                'thruDate': thruDate
            }
            this.sendAndRequestService.requestForPUT(Constants.app_urls.DAILY_SUMMARY.OBSERVATION_CHECK_LIST.UPDATE_OBS_CHECK_LIST, updateObsCheckListModel, false).subscribe(response => {
                this.commonService.showAlertMessage('Successfully updated');
                this.getAllObservationsCheckListData();
                this.ObservationCheckListItemFormGroup.reset();
                this.addObservationCheckListItem = false;
            }, error => { })

        }
    }

    editObservationsCheckListItem(id) {
        this.addObservationCheckListItem = true;
        this.observationCheckListEditAction(id);
        this.title = Constants.EVENTS.UPDATE;
    }

    observationCheckListEditAction(id: number) {
        this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.OBSERVATION_CHECK_LIST.GET_OBS_CHECK_LIST_ID+ id).subscribe((responseData) => {
            this.editObservationCheckLisResponse = responseData;
            this.ObservationCheckListItemFormGroup.patchValue({
                id: this.editObservationCheckLisResponse.id,
                inspectionType: this.editObservationCheckLisResponse.inspectionType,
                observationCategory: this.editObservationCheckLisResponse.observationCategory,
                observationItem: this.editObservationCheckLisResponse.observationItem,
                description: this.editObservationCheckLisResponse.description,
                priority: this.editObservationCheckLisResponse.priority,
                severity: this.editObservationCheckLisResponse.severity,
                fromDate: this.editObservationCheckLisResponse.fromDate,
                thruDate: !!this.editObservationCheckLisResponse.thruDate ? new Date(this.editObservationCheckLisResponse.thruDate) : '',
            });
            this.toMinDate = new Date(this.editObservationCheckLisResponse.fromDate);
        }, error => { })
    }


    deleteObservationCheckListItem(id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
        });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected Observations Check List?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.sendAndRequestService.requestForDELETE(Constants.app_urls.DAILY_SUMMARY.OBSERVATION_CHECK_LIST.DELETE_OBS_CHECK_LIST, id).subscribe(response => {
                    this.commonService.showAlertMessage('Observations Check List Deleted Successfully');
                    this.getAllObservationsCheckListData();
                }, error => { });
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
        this.title = Constants.EVENTS.ADD;
    }
    observationCategories() {

        this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.OBSERVATION_CATEGORIES.GET_OBS_CATEGORIES).subscribe((data) => {
            this.inspectionTypeData = data;
        }
        );

    }
    inspectionType() {

        this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_INSPECTION_TYPE).subscribe((data) => {
            this.inspectionTypeList = data;
        }
        );

    }


    public priority = ['Low', 'Medium', 'High'];

    NewObservationCheckList() {
        this.addObservationCheckListItem = true;
    }
    ViewData(data){
        var result = {
          'title':'Observation Check List Data',
          'dataSource':[{label:'Inspection Type',value:data.inspectionType},{label:'Observation Category',value:data.observationCategory},{label:'Observation Item',value:data.observationItem},
                        {label:'Description', value:data.description},{label:'Priority', value:data.priority},{label:'Severity', value:data.severity},
                        {label:'From Date', value:data.fromDate},{label:'Thru Date', value:data.thruDate}]
        }
        this.dataViewDialogRef = this.dialog.open(DataViewDialogComponent, {
          disableClose: false,
          height: '400px',
          width: '80%',       
          data:result,  
        });            
      }
}