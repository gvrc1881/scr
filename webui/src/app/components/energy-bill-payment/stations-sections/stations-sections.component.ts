import { OnInit, Component, ViewChild } from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import { StationsSectionsModel } from 'src/app/models/stations-sections.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { DataViewDialogComponent } from '../../data-view-dialog/data-view-dialog.component';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';



@Component({
  selector: 'stations-sections',
  templateUrl: './stations-sections.component.html',
  styleUrls: []
})
export class StationsSectionsComponent implements OnInit {

  pagination = Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  addPermission: boolean = true;
  editPermission: boolean = true;
  deletePermission: boolean = true;
  addStationsSections: boolean;
  id: number = 0;
  title: string = Constants.EVENTS.ADD;
  stationsSectionsFormGroup: FormGroup;
  stationsSectionsList: any;
  divisionsList: any;
  stationsSectionsDataSource: MatTableDataSource<StationsSectionsModel>;
  stationsSectionsDisplayColumns = ['sno', 'stationCode', 'stationName', 'majorSectionRoute', 'upSection', 'upSectionName', 'dnSection', 'dnSectionName', 'division', 'id'];
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  editstationsSectionsResponse: any;
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;


  constructor(
    private commonService: CommonService,
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
    private sendAndRequestService: SendAndRequestService
  ) {

  }

  ngOnInit() {
    this.getAllStationsSectionsData();
    this.divisionDetails();
    var permissionName = this.commonService.getPermissionNameByLoggedData("TRD CONFIG", "Stations-sections");//p == 0 ? 'No Permission' : p[0].permissionName;
    this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

  }
  duplicateStationCode() {
    const q = new Promise((resolve, reject) => {
      let stationSection: string = this.stationsSectionsFormGroup.controls['stationCode'].value;
      var filter = !!this.stationsSectionsList && this.stationsSectionsList.filter(stationSections => {
        return stationSections.stationCode.toLowerCase() == stationSection.trim().toLowerCase();
      });
      if (filter.length > 0) {
        resolve({ 'duplicateStationCode': true });
      }
      this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.STATION_SECTIONS.EXISTS_STATION_CODE +
        this.stationsSectionsFormGroup.controls['stationCode'].value
      ).subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateStationCode': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateStationCode': true }); });
    });
    return q;
  }
  duplicateStationName() {
    const q = new Promise((resolve, reject) => {
      let stationSection: string = this.stationsSectionsFormGroup.controls['stationName'].value;
      var filter = !!this.stationsSectionsList && this.stationsSectionsList.filter(stationSections => {
        return stationSections.stationCode.toLowerCase() == stationSection.trim().toLowerCase();
      });
      if (filter.length > 0) {
        resolve({ 'duplicateStationName': true });
      }
      this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.STATION_SECTIONS.EXISTS_STATION_NAME +
        this.stationsSectionsFormGroup.controls['stationName'].value
      ).subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateStationName': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateStationName': true }); });
    });
    return q;
  }
  
  duplicateStationCodeAndId() {
    let id=this.id;
    let stationCode: string = this.stationsSectionsFormGroup.controls['stationCode'].value;         

    const q = new Promise((resolve, reject) => {          

       this.sendAndRequestService.requestForGET(
              Constants.app_urls.ENERGY_BILL_PAYMENTS.STATION_SECTIONS.EXISTS_STATION_CODE_AND_ID+id+'/'+stationCode).subscribe
              ((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateStationCodeAndId': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateStationCodeAndId': true }); });
    });
    return q;
  }

  duplicateStationNameAndId() {

    let id=this.id;
   let stationName: string = this.stationsSectionsFormGroup.controls['stationName'].value;         

   const q = new Promise((resolve, reject) => {          

      this.sendAndRequestService.requestForGET(
             Constants.app_urls.ENERGY_BILL_PAYMENTS.STATION_SECTIONS.EXISTS_STATION_NAME_AND_ID+id+'/'+stationName).subscribe
             ((duplicate) => {
       if (duplicate) {
         resolve({ 'duplicateStationNameAndId': true });
       } else {
         resolve(null);
       }
     }, () => { resolve({ 'duplicateStationNameAndId': true }); });
   });
   return q;
 }
  getAllStationsSectionsData() {
    const stationsSections: StationsSectionsModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.STATION_SECTIONS.GET_STATION_SECTIONS).subscribe((data) => {
      this.stationsSectionsList = data;
      for (let i = 0; i < this.stationsSectionsList.length; i++) {
        this.stationsSectionsList[i].sno = i + 1;
        stationsSections.push(this.stationsSectionsList[i]);
      }
      this.stationsSectionsDataSource = new MatTableDataSource(stationsSections);
      this.stationsSectionsDataSource.paginator = this.paginator;
      this.stationsSectionsDataSource.sort = this.sort;

    }, error => { });

  }

  stationsSectionsSubmit() {
    let stationCode: string = this.stationsSectionsFormGroup.value.stationCode;
    let stationName: string = this.stationsSectionsFormGroup.value.stationName;
    let majorSectionRoute: string = this.stationsSectionsFormGroup.value.majorSectionRoute;
    let upSection: string = this.stationsSectionsFormGroup.value.upSection;
    let upSectionName: string = this.stationsSectionsFormGroup.value.upSectionName;
    let dnSection: string = this.stationsSectionsFormGroup.value.dnSection;
    let dnSectionName: string = this.stationsSectionsFormGroup.value.dnSectionName;
    let division: string = this.stationsSectionsFormGroup.value.division;

    this.addStationsSections = false;
    if (this.title == Constants.EVENTS.SAVE) {
      var saveSSModel = {
        'stationCode': stationCode,
        'stationName': stationName,
        'majorSectionRoute': majorSectionRoute,
        'upSection': upSection,
        'upSectionName': upSectionName,
        'dnSection': dnSection,
        'dnSectionName': dnSectionName,
        'division': division
      }
      this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.STATION_SECTIONS.SAVE_STATION_SECTIONS, saveSSModel, false).subscribe(response => {
        this.commonService.showAlertMessage('Successfully saved');
        this.getAllStationsSectionsData();
        this.stationsSectionsFormGroup.reset();
      }, error => { });
    } else if (this.title == Constants.EVENTS.UPDATE) {
      let id: number = this.editstationsSectionsResponse.id;
      var updateSSModel = {
        'id': id,
        'stationCode': stationCode,
        'stationName': stationName,
        'majorSectionRoute': majorSectionRoute,
        'upSection': upSection,
        'upSectionName': upSectionName,
        'dnSection': dnSection,
        'dnSectionName': dnSectionName,
        'division': division
      }
      this.sendAndRequestService.requestForPUT(Constants.app_urls.ENERGY_BILL_PAYMENTS.STATION_SECTIONS.UPDATE_STATION_SECTIONS, updateSSModel, false).subscribe(response => {
        this.commonService.showAlertMessage('Successfully updated');
        this.getAllStationsSectionsData();
        this.stationsSectionsFormGroup.reset();
        this.addStationsSections = false;
      }, error => { })

    }
  }

  editStationsSections(id) {
    this.addStationsSections = true;
    this.stationsSectionsEditAction(id);
    this.title = Constants.EVENTS.UPDATE;
  }

  stationsSectionsEditAction(id: number) {
    this.stationsSectionsFormGroup = this.formBuilder.group({
      id: 0,
      'stationCode': [null, Validators.compose([Validators.required, Validators.maxLength(250)]), this.duplicateStationCodeAndId.bind(this)],
      'stationName': [null, Validators.compose([Validators.required, Validators.maxLength(250)]), this.duplicateStationNameAndId.bind(this)],
      'majorSectionRoute': [null, Validators.maxLength(250)],
      'upSection': [null, Validators.maxLength(250)],
      'upSectionName': [null, Validators.maxLength(250)],
      'dnSection': [null, Validators.maxLength(250)],
      'dnSectionName': [null, Validators.maxLength(250)],
      'division': [null, Validators.maxLength(250)]
    });
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.STATION_SECTIONS.GET_STATION_SECTIONS_ID + id).subscribe((responseData) => {
      this.editstationsSectionsResponse = responseData;
      this.stationsSectionsFormGroup.patchValue({
        id: this.editstationsSectionsResponse.id,
        stationCode: this.editstationsSectionsResponse.stationCode,
        stationName: this.editstationsSectionsResponse.stationName,
        majorSectionRoute: this.editstationsSectionsResponse.majorSectionRoute,
        upSection: this.editstationsSectionsResponse.upSection,
        upSectionName: this.editstationsSectionsResponse.upSectionName,
        dnSection: this.editstationsSectionsResponse.dnSection,
        dnSectionName: this.editstationsSectionsResponse.dnSectionName,
        division: this.editstationsSectionsResponse.division
      })
    }, error => { })
    this.id = id;
    if (!isNaN(this.id)) {
      this.title = Constants.EVENTS.UPDATE;
    } else {
      this.title = Constants.EVENTS.ADD;
    }
  }


  deleteStationsSections(id) {
    this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
      disableClose: false
    });
    this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the Selected stations Sections?";
    this.confirmDialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.ENERGY_BILL_PAYMENTS.STATION_SECTIONS.DELETE_STATION_SECTIONS, id).subscribe(response => {
          this.commonService.showAlertMessage('Stations sections Deleted Successfully');
          this.getAllStationsSectionsData();
        }, error => { });
      }
    });
  }
  divisionDetails() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_DIVISION_DETAILS).subscribe((data) => {
      this.divisionsList = data;
    });
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.stationsSectionsDataSource.filter = filterValue;
  }

  onGoBack() {
    this.stationsSectionsFormGroup.reset();
    this.addStationsSections = false;
    this.title = Constants.EVENTS.ADD;
  }


  NewStationsSections() {
    this.addStationsSections = true;
    this.stationsSectionsFormGroup = this.formBuilder.group({
      id: 0,
      'stationCode': [null, Validators.compose([Validators.required, Validators.maxLength(250)]), this.duplicateStationCode.bind(this)],
      'stationName': [null, Validators.compose([Validators.required, Validators.maxLength(250)]), this.duplicateStationName.bind(this)],
      'majorSectionRoute': [null, Validators.maxLength(250)],
      'upSection': [null, Validators.maxLength(250)],
      'upSectionName': [null, Validators.maxLength(250)],
      'dnSection': [null, Validators.maxLength(250)],
      'dnSectionName': [null, Validators.maxLength(250)],
      'division': [null, Validators.maxLength(250)]
    });
  }
  ViewData(data){
    var result = {
      'title':this.Titles.STATION_SECTIONS_DATA,
      'dataSource':[
        
                         
                    { label:FieldLabelsConstant.LABELS.STATION_CODE, value:data.stationCode },
                    { label:FieldLabelsConstant.LABELS.STATION, value:data.stationName },
                    { label:FieldLabelsConstant.LABELS.MAJOR_SECTION_ROUTE, value:data.majorSectionRoute },
                    { label:FieldLabelsConstant.LABELS.UP_SECTION, value:data.uPSection },
                    { label:FieldLabelsConstant.LABELS.UP_SECTION_NAME, value:data.uPSectionName },
                    { label:FieldLabelsConstant.LABELS.DN_SECTION, value:data.dNSection },
                    { label:FieldLabelsConstant.LABELS.DN_SECTION_NAME, value:data.dNSectionName },
                    { label:FieldLabelsConstant.LABELS.DIVISION, value:data.division },
                    

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