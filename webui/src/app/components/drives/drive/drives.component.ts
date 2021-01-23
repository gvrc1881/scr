import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource, MatDialogRef, MatDialog } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { DriveModel, DriveCategoryModel, DriveCategoryAssoModel } from 'src/app/models/drive.model';
import { Router, ActivatedRoute } from '@angular/router';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { DataViewDialogComponent } from 'src/app/components/data-view-dialog/data-view-dialog.component';
import { FuseConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';
import { DatePipe } from '@angular/common';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { FuseConfirmPopupComponent } from '../../confirm-popup/confirm-popup.component';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { DocumentDialogComponent } from '../../document-view-dialog/document-dialog.component';



@Component({
  selector: 'app-drives',
  templateUrl: './drive.component.html',
  styleUrls: ['./drive.component.scss']
})
export class DrivesComponent implements OnInit {

  drivePagination=Constants.PAGINATION_NUMBERS;
  driveCatPagination=Constants.PAGINATION_NUMBERS;
  driveCatAssocPagination=Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  resp: any;
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(sessionStorage.getItem('userData')); 
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  displayedColumns = ['sno', 'name', 'description', 'fromDate', 'toDate', 'depoType', 'assetType', 'frequency',
   'functionalUnit', 'active', 'actions'];
  driveCategoryDisplayedColumns = ['sno', 'name', 'description', 'fromDate', 'toDate', 'authority', 'actions'];
  driveCategoryAssoDisplayedColumns = ['sno', 'drive', 'driveCategory', 'active', 'actions'];

  dataSource: MatTableDataSource<DriveModel>;
  driveCategoryDataSource: MatTableDataSource<DriveCategoryModel>;
  driveCategoryAssoDataSource: MatTableDataSource<DriveCategoryAssoModel>;
  dataViewDialogRef: MatDialogRef<DataViewDialogComponent>;
  depoTypeList = [];
  assetTypeList = [];
  scheduleList = [];

  functionalUnitList: any;
  allFunctionalUnitsList: any;

  @ViewChild('drivePaginator', { static: true }) drivePaginator: MatPaginator;
  @ViewChild('sort', { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
  drivesList: any;

   @ViewChild('driveCategoryPaginator', { static: true }) driveCategoryPaginator: MatPaginator;
   @ViewChild('driveCategorySort', { static: true }) driveCategorySort: MatSort;
   @ViewChild('filter', { static: true }) driveCategoryFilter: ElementRef;
  driveCategoryList: any;

   @ViewChild('driveCategoryAssoPaginator', { static: true }) driveCategoryAssoPaginator: MatPaginator;
   @ViewChild('driveCategoryAssoSort', { static: true }) driveCategoryAssoSort: MatSort;
  @ViewChild('filter', { static: true }) driveCategoryAssoFilter: ElementRef;
  driveCategoryAssoList: any;
    
    uploadFile: boolean;
    driveId: any;
    contentManagementFormGroup: FormGroup;
    pattern = "[a-zA-Z][a-zA-Z ]*";
    selectedFiles: File[] = [];
    filesExists: boolean = false;
    userDefaultData: any;
    documentDialogRef:MatDialogRef<DocumentDialogComponent>;

  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private route: ActivatedRoute,
    public dialog: MatDialog,
    private datePipe: DatePipe,
    private sendAndRequestService: SendAndRequestService,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit() {
      //this.dataSource.sort = this.sort;
      //this.driveCategoryDataSource.sort = this.driveCategorySort;
      //this.driveCategoryAssoDataSource.sort = this.drivecate
    var permissionName = this.commonService.getPermissionNameByLoggedData("DRIVES", "DRIVES");
    this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();
    this.findDepoTypeList();
    this.findFunctionalUnits();
    this.getDrivesData();

    this.getDriveCategoryData();
    this.getDriveCategoryAssoData();
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_USER_DEFAULT_DATA + this.userdata.username).subscribe((data) => {
                                   this.userDefaultData = data;
    });
  }
    
  onContentManagementSubmit () {
        let category = this.contentManagementFormGroup.value.contentCategory;
        this.uploadFile = false;
        this.addPermission = true;
        let saveDetails = {
            'driveId': this.driveId,
                'description': this.contentManagementFormGroup.value.description,
                'divisionCode': this.userdata.divisionCode,
                'createdBy': this.userdata.id,
                'createdDate': new Date(),
                'contentCategory': 'OPERATIONS',
                'zonal': this.userDefaultData.zone,
                'FU': this.userDefaultData.facilityName,
                'contentTopic': 'DRIVE',
          }
          let formdata: FormData = new FormData();
          for(var i=0;i<this.selectedFiles.length;i++){
              formdata.append('file', this.selectedFiles[i]);
          }
          formdata.append('driveId', saveDetails.driveId);
          formdata.append('contentCategory', saveDetails.contentCategory);
          formdata.append('description', saveDetails.description);
          formdata.append('divisionCode', saveDetails.divisionCode);
          formdata.append('createdBy', saveDetails.createdBy);
          formdata.append('zonal', saveDetails.zonal);
          formdata.append('FU', saveDetails.FU);
          formdata.append('contentTopic', saveDetails.contentTopic);
        this.sendAndRequestService.requestForPOST(Constants.app_urls.DRIVE.DRIVE.DRIVE_UPLOAD_FILES, formdata, true).subscribe(data => {
                this.spinnerService.hide();
                this.commonService.showAlertMessage("Files Uploaded and Saved Successfully");
                this.selectedFiles = [];
                this.filesExists = false;
            }, error => {
                console.log('ERROR >>>');
                this.spinnerService.hide();
                this.commonService.showAlertMessage("Files Uploading Failed.");
            })
            
    }
    
  viewDocumentDetails(id){
        this.spinnerService.show();    
        this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE.ATTACHMENT_LIST + id).subscribe((response) => {     
          this.spinnerService.hide(); 
          this.documentDialogRef = this.dialog.open(DocumentDialogComponent, {
                disableClose: false,
                height: '600px',
                width: '80%',       
                data:response,       
            });            
        }, error => this.commonService.showAlertMessage(error));
  }
    
  removeFile(id) {
        this.selectedFiles.splice(id, 1);
        if(this.selectedFiles.length === 0) {
            this.filesExists = false;
            this.contentManagementFormGroup.reset();
        }
  }
    
  fileUpload(id) {
        this.uploadFile = true;
        this.addPermission = false;
        this.driveId = id;
        this.contentManagementFormGroup = this.formBuilder.group({
            contentCategory: [''],
            description: ['', Validators.compose([Validators.required, Validators.pattern(this.pattern)])],
            uploadFiles: ['', Validators.required],
            contentTopic: [''],
        });
  }
    
  public get f() { return this.contentManagementFormGroup.controls; }
    
  upload(event) {
        if (event.target.files.length > 0) { this.filesExists = true; }
        for (var i = 0; i < event.target.files.length; i++) {
            this.selectedFiles.push(event.target.files[i]);
        }
   }
    
  close() {
        this.contentManagementFormGroup.reset();
        this.uploadFile = false;
        this.selectedFiles = [];
        this.filesExists = false;
        this.addPermission = true;
  }
    
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }

  driveCategoryApplyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.driveCategoryDataSource.filter = filterValue;
  }

  driveCategoryAssoApplyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.driveCategoryAssoDataSource.filter = filterValue;
  }
  findDepoTypeList() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FUNCTIONAL_LOCATION_TYPES)
      .subscribe((depoTypes) => {
        this.depoTypeList = depoTypes;
      })
  }

  findAssetTypeList(assertType) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_ASSET_TYPES + assertType)
      .subscribe((assetTypes) => {
        this.assetTypeList = assetTypes;
      })
  }

  findFunctionalUnits() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY_NAMES)
      .subscribe((units) => {
        this.allFunctionalUnitsList = units;
      })
  }
  findScheduleList() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ASSET_SCH_ASSOC.GET_SCH)
      .subscribe((schedule) => {
        this.scheduleList = schedule;
      })
  }
  getDrivesData() {
    const drive: DriveModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE.GET_DRIVES).subscribe((data) => {
      this.drivesList = data;
      for (let i = 0; i < this.drivesList.length; i++) {
        this.drivesList[i].sno = i + 1;
        this.drivesList[i].targetQuantity = this.drivesList[i].target_qty;
        this.drivesList[i].status = this.drivesList[i].active;
        this.drivesList[i].checkList = this.drivesList[i].checklist;

        this.drivesList[i].depoType = !!this.drivesList[i].depotType ? this.drivesList[i].depotType['code'] : '';
        this.drivesList[i].fromDate = this.datePipe.transform(this.drivesList[i].fromDate, 'dd-MM-yyyy ');
        this.drivesList[i].toDate = this.datePipe.transform(this.drivesList[i].toDate, 'dd-MM-yyyy ');
        drive.push(this.drivesList[i]);
      }

      this.dataSource = new MatTableDataSource(drive);
     this.dataSource.paginator = this.drivePaginator;
      this.dataSource.sort = this.sort;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }

  getDriveCategoryData() {
    const driveCat: DriveCategoryModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CATEGORY.GET_DRIVE_CATEGORY).subscribe((data) => {
      this.driveCategoryList = data;
      for (let i = 0; i < this.driveCategoryList.length; i++) {
        this.driveCategoryList[i].sno = i + 1;
        this.driveCategoryList[i].name = this.driveCategoryList[i].driveCategoryName;
        this.driveCategoryList[i].fromDate = this.datePipe.transform(this.driveCategoryList[i].fromDate, 'dd-MM-yyyy ');
        this.driveCategoryList[i].toDate = this.datePipe.transform(this.driveCategoryList[i].toDate, 'dd-MM-yyyy ');
        driveCat.push(this.driveCategoryList[i]);
      }
      this.driveCategoryDataSource = new MatTableDataSource(driveCat);
      this.driveCategoryDataSource.paginator = this.driveCategoryPaginator;
        this.driveCategoryDataSource.sort = this.driveCategorySort;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }

  getDriveCategoryAssoData() {
    const driveCatAssoc: DriveCategoryAssoModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CATEGORY_ASSOCIATION.GET_DRIVE_CATEGORY_ASSOC).subscribe((data) => {
      this.driveCategoryAssoList = data;
      for (let i = 0; i < this.driveCategoryAssoList.length; i++) {
        this.driveCategoryAssoList[i].sno = i + 1;
        this.driveCategoryAssoList[i].drive = this.driveCategoryAssoList[i].driveId['name'];
        this.driveCategoryAssoList[i].driveCategory = this.driveCategoryAssoList[i].driveCategoryId['driveCategoryName'];
        driveCatAssoc.push(this.driveCategoryAssoList[i]);
      }

      this.driveCategoryAssoDataSource = new MatTableDataSource(driveCatAssoc);
      this.driveCategoryAssoDataSource.paginator = this.driveCategoryAssoPaginator;
      this.driveCategoryAssoDataSource.sort = this.driveCategoryAssoSort;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });

  }

  processEditAction(id) {
    this.router.navigate(['drive/' + id], { relativeTo: this.route });
  }
  delete(id) {
    this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
      disableClose: false
    });
    this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete?';
    this.confirmDialogRef.afterClosed().subscribe(result => {
      if (result) {

        this.spinnerService.show();
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.DRIVE.DRIVE.DELETE_DRIVE, id).subscribe((data) => {
          this.resp = data;

          if (this.resp.code === 200) {

            this.confirmDialogRef = this.dialog.open(FuseConfirmPopupComponent, {
              disableClose: false

            });
            this.confirmDialogRef.componentInstance.confirmMessage = this.resp.message;
            // this.commonService.showAlertMessage(this.resp.message);
            //this.confirmDialogRef.componentInstance.confirmMessage=this.resp.message;
          } else {
            this.confirmDialogRef = this.dialog.open(FuseConfirmPopupComponent, {
              disableClose: false
            });
            this.confirmDialogRef.componentInstance.confirmMessage = this.resp.message;
            // this.commonService.showAlertMessage(this.resp.message);
            // this.confirmDialogRef.componentInstance.confirmMessage=this.resp.message;
          }
          this.spinnerService.hide();
          // this.commonService.showAlertMessage("Deleted Drive Successfully");
          this.getDrivesData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Drive Deletion Failed.");
        })
      }
      this.confirmDialogRef = null;
    });
  }

  driveCategoryEdit(id) {
    this.router.navigate(['drive-category/' + id], { relativeTo: this.route });
  }
  driveCategoryDelete(id) {
    this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
      disableClose: false

    });
    this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete?';
    this.confirmDialogRef.afterClosed().subscribe(result => {
      if (result) {

        this.spinnerService.show();
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.DRIVE.DRIVE_CATEGORY.DELETE_DRIVE_CATEGORY, id).subscribe(data => {
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted Drive Category Successfully");
          this.getDriveCategoryData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Drive Category Deletion Failed.");
        })
      }
      this.confirmDialogRef = null;
    });
  }

  driveCategoryAssoEdit(id) {
    this.router.navigate(['drive-category-association/' + id], { relativeTo: this.route });
  }
  driveCategoryAssoDelete(id) {
    this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
      disableClose: false

    });
    this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete?';
    this.confirmDialogRef.afterClosed().subscribe(result => {
        console.log('*** assos ***'+result);
      if (result) {
        this.spinnerService.show();
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.DRIVE.DRIVE_CATEGORY_ASSOCIATION.DELETE_DRIVE_CATEGORY_ASSOC, id).subscribe(data => {
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted Drive Category Asso Successfully");
          this.getDriveCategoryAssoData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Drive Category Asso Deletion Failed.");
        })
      }
      this.confirmDialogRef = null;
    });
  }
  ViewData(data) {
    var result = {
      'title': 'Drives',
      'dataSource': [{ label: 'Name', value: data.name }, { label: 'Description', value: data.description },
      { label: 'FromDate', value: data.fromDate }, { label: 'ToDate', value: data.toDate },
      { label: 'DepotType', value: data.depoType }, { label: 'AssetType', value: data.assetType },
      { label: 'Frequency', value: data.frequency }, { label: 'AssetDescription', value: data.assetDescription },
      { label: 'Criteria', value: data.criteria }, { label: 'TargetQuantity', value: data.targetQuantity },
      { label: 'IsIdRequired', value: data.isIdRequired }, { label: 'FunctionalUnit', value: data.functionalUnit },
      { label: 'CheckList', value: data.checkList }, { label: 'Active', value: data.active }]
    }
    this.dataViewDialogRef = this.dialog.open(DataViewDialogComponent, {
      disableClose: false,
      height: '400px',
      width: '80%',
      data: result,
    });
  }
  ViewDataDriveCat(data) {
    var result = {
      'title':this.Titles.DRIVES,
      'dataSource': [
        
{ label:FieldLabelsConstant.LABELS.NAME, value:data.name },
{ label:FieldLabelsConstant.LABELS.DESCRIPTION, value:data.description },
{ label:FieldLabelsConstant.LABELS.FROM_DATE, value:data.fromDate },
{ label:FieldLabelsConstant.LABELS.TO_DATE, value:data.toDate },
{ label:FieldLabelsConstant.LABELS.DEPOT_TYPE, value:data.depotType },
{ label:FieldLabelsConstant.LABELS.ASSET_TYPE, value:data.assetType },
{ label:FieldLabelsConstant.LABELS.FREQUENCY, value:data.frequency },
{ label:FieldLabelsConstant.LABELS.ASSET_DESCRIPTION, value:data.assetDescription },
{ label:FieldLabelsConstant.LABELS.CRITERIA, value:data.criteria },
{ label:FieldLabelsConstant.LABELS.TARGET_QUANTITY, value:data.targetQuantity },
{ label:FieldLabelsConstant.LABELS.IS_ID_REQUIRED, value:data.isIdRequired },
{ label:FieldLabelsConstant.LABELS.FUNCTIONAL_UNIT, value:data.functionalUnit },
{ label:FieldLabelsConstant.LABELS.CHECK_LIST, value:data.checkList },
{ label:FieldLabelsConstant.LABELS.ACTIVE, value:data.active },
{ label:FieldLabelsConstant.LABELS.AUTHORITY, value:data.authority }

    ]
    }
    this.dataViewDialogRef = this.dialog.open(DataViewDialogComponent, {
      disableClose: false,
      height: '400px',
      width: '80%',
      data: result,
    });
  }

  copy() {
    const dialogRef = this.dialog.open(CopyDrivesComponent, {
      height: '400px',
      width: '80%',
    });
    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    });
  }

}

@Component({
  selector: 'copy-drives',
  templateUrl: 'copy-drives.component.html',
})
export class CopyDrivesComponent implements OnInit {
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;

  driveFormGroup: FormGroup;
  driveCategoryList = [];
  drivesList = [];
  allDrives = [];
  drivesExists: boolean;
  dataSource: MatTableDataSource<DriveModel>;
  displayedColumns = ['sno', 'check', 'oldDriveName', 'newDriveName'];
  selectedDrives = [];
  year: any;
  driveCategory: any;
  createDrives: boolean;
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;


  constructor(
    private formBuilder: FormBuilder,
    private spinnerService: Ng4LoadingSpinnerService,
    private sendAndRequestService: SendAndRequestService,
    private dialogRef: MatDialogRef<CopyDrivesComponent>,
    private commonService: CommonService,
    public dialog: MatDialog
  ) { }

  ngOnInit() {
    this.getDriveCategoryData();
    this.createDrives = false;
    this.driveFormGroup = this.formBuilder.group({
      driveCategory: [null],
      newDriveName: [null],
      oldDriveName: [null]
    })
    this.year = new Date().getFullYear();
  }

  saveAction() {
    const drives: DriveModel[] = [];
    this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
      disableClose: false

    });
    this.confirmDialogRef.componentInstance.confirmMessage = 'Do you want to copy existing documents to new Drives ?';
    this.confirmDialogRef.afterClosed().subscribe(result => {
      if (result) {}
          for (var i = 0; i < this.selectedDrives.length; i++) {
            this.selectedDrives[i].driveId.id = 0;
            this.selectedDrives[i].driveId.name = this.selectedDrives[i].newDriveName;
              if(!result){
                  this.selectedDrives[i].driveId.contentLink = null;
              }
            drives.push(this.selectedDrives[i].driveId);
          }
            let copyObject = {
              drives: drives,
              driveCategory: this.driveCategory
            };
            if (this.selectedDrives.length > 0) {
              this.driveCategory.id = 0;
              this.driveCategory.driveCategoryName = this.driveCategory.driveCategoryName + '_' + this.year
              this.sendAndRequestService.requestForPOST(Constants.app_urls.DRIVE.DRIVE.COPY_DRIVES, copyObject, true).subscribe(data => {
                this.spinnerService.hide();
                this.commonService.showAlertMessage("Drives Created Successfully");
                this.allDrives = [];
                this.dataSource = new MatTableDataSource(this.allDrives);
                this.selectedDrives = [];
                this.drivesList = [];
                this.createDrives = false;
                this.getDriveCategoryData();
              }, error => {
                console.log('ERROR >>>');
                this.spinnerService.hide();
              })
            }
    });
    
    
  }


  onCheckboxChange(e, row) {
    if (e.target.checked) {
      this.selectedDrives.push(row);
      this.createDrives = true;
    } else {
      this.selectedDrives.splice(row.index, 1);
      if (this.selectedDrives.length == 0) {
        this.createDrives = false;
      }
    }
  }

  copyDrivesFormSubmit() {
    this.driveCategory = this.driveFormGroup.controls['driveCategory'].value;
    this.drivesList = [];
    this.allDrives = [];
    this.dataSource = new MatTableDataSource(this.allDrives);
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CATEGORY_ASSOCIATION.GET_DRIVES_BASED_ON_CATEGORY + this.driveCategory.id).subscribe((data) => {
      this.drivesList = data;
      if (data) {
        this.drivesExists = true;
        for (var i = 0; i < this.drivesList.length; i++) {
          this.drivesList[i].sno = i + 1;
          this.drivesList[i].index = i;
          this.drivesList[i].driveId.name = this.drivesList[i].driveId.name
          this.drivesList[i].newDriveName = this.drivesList[i].driveId.name + '_' + this.year
          this.allDrives.push(this.drivesList[i]);
        }
        this.dataSource = new MatTableDataSource(this.allDrives);
      }
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }

  getDriveCategoryData() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CATEGORY.GET_DRIVE_CATEGORY).subscribe((data) => {
      this.driveCategoryList = data;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }

  onGoBack(): void {
    this.dialogRef.close();
  }

}