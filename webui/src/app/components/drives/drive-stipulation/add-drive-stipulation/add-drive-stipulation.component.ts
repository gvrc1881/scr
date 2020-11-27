import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDatepickerInputEvent, MatDialogRef, MatDialog,DateAdapter, MAT_DATE_FORMATS } from '@angular/material';
import { Constants } from 'src/app/common/constants';
import { FuseConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { InspectionstModel } from 'src/app/models/drive.model';
import { DatePipe } from '@angular/common';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';



@Component({
  selector: 'app-add-drive-stipulation',
  templateUrl: './add-drive-stipulation.component.html',
  providers: [
    {
        provide: DateAdapter, useClass: AppDateAdapter
    },
    {
        provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
    }
    ]
})

export class AddDriveStipulationComponent implements OnInit {

  pagination = Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  save: boolean = true;
  update: boolean = false;
  title: string = Constants.EVENTS.ADD;
  id: number = 0;
  isSubmit: boolean = false;
  resp: any;
  selectedFiles: File[] = [];
  filesExists: boolean = false;
  addDriveStipulationFormGroup: FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  stipulationFormErrors: any;
  stateList: any;
  assertTypeList: any;
  minDateComplied = new Date(); 
  currentDate = new Date();
  maxDate = new Date();
  dateFormat = 'dd-MM-yyyy ';
  attachedImages:any;
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  crsEigInspections: InspectionstModel[] = [];

  constructor(
    private formBuilder: FormBuilder,
    private sendAndRequestService : SendAndRequestService,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private route: ActivatedRoute,
    private router: Router,
    public dialog: MatDialog,
    private datePipe: DatePipe
  ) {
    // Reactive form errors
    this.stipulationFormErrors = {
      stipulation: {},
      inspectionId: {},
      dateOfStipulation: {},
      dateComplied: {},
      compliance: {},
      attachment: {},
      compliedBy: {},
    };

  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
    this.findAssertTypeList();
    this.createStipulationForm();
    if (!isNaN(this.id)) {
      this.addDriveStipulationFormGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      });
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;
      this.getStipulationDataById(this.id);

    } else {
      this.save = true;
      this.update = false;
      this.title = Constants.EVENTS.ADD;
    }
    this.sendAndRequestService.requestForGET(Constants.app_urls.INSPECTIONS.INSPECTIONS.GET_INSPECTIONS).subscribe((data) => {
      let crsEigInss = data;
        for (let i = 0; i < crsEigInss.length; i++) {
        crsEigInss[i].dateOfInspection = this.datePipe.transform(crsEigInss[i].dateOfInspection, 'dd-MM-yyyy');
        this.crsEigInspections.push(crsEigInss[i]);
      }
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });  
  }
  findAssertTypeList() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.INSPECTIONS.STIPULATION.ASSERT_TYPE).subscribe((data) => {
      this.assertTypeList = data;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }

  public get f() { return this.addDriveStipulationFormGroup.controls; }


  getStipulationDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.INSPECTIONS.STIPULATION.GET_STIPULATION_ID + id)
      .subscribe((resp) => {
        this.resp = resp;
        this.addDriveStipulationFormGroup.patchValue({
          id: this.resp.id,
          stipulation: this.resp.stipulation,
          inspectionId: parseInt(this.resp.inspectionId),
          dateOfStipulation: !!this.resp.dateOfStipulation ? new Date(this.resp.dateOfStipulation) : '',
          dateComplied:!!this.resp.dateComplied ? new Date(this.resp.dateComplied) : '', 
          compliance: this.resp.compliance,
          compliedBy: this.resp.compliedBy,
        });
          
        this.minDateComplied =  new Date(this.resp.dateOfStipulation);
        var commonId = !!this.resp.attachment && this.resp.attachment;
        this.spinnerService.hide();
        this.findAttachedFiles(commonId);
      })
  }

  findAttachedFiles(commonId){
    this.sendAndRequestService.requestForGET(Constants.app_urls.INSPECTIONS.STIPULATION.GET_INSPECTION_AND_STIPULATION_ID + commonId)
    .subscribe((resp) => {
      this.attachedImages = resp;
    })
  }


  onFormValuesChanged() {
    for (const field in this.stipulationFormErrors) {
      if (!this.stipulationFormErrors.hasOwnProperty(field)) {
        continue;
      }
      this.stipulationFormErrors[field] = {};
      const control = this.addDriveStipulationFormGroup.get(field);

      if (control && control.dirty && !control.valid) {
        this.stipulationFormErrors[field] = control.errors;
      }
    }
  }

  createStipulationForm() {
    this.addDriveStipulationFormGroup
      = this.formBuilder.group({
        id: 0,
        'stipulation': ['', Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
        'inspectionId': [''],
        'dateOfStipulation': [''],
        'dateComplied': [''],
        'compliance': [''],
        'attachment': [''],
        'compliedBy': ['',Validators.maxLength(255)],
      });
  }
  addEvent($event) {
    this.minDateComplied = new Date($event.value);
  }

  onAddStipulationFormSubmit() {

    if (this.addDriveStipulationFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    if (this.save) {
      let save = {
        stipulation: this.addDriveStipulationFormGroup.value.stipulation,
        inspectionId: this.addDriveStipulationFormGroup.value.inspectionId,
        dateOfStipulation: this.addDriveStipulationFormGroup.value.dateOfStipulation,
        dateComplied: this.addDriveStipulationFormGroup.value.dateComplied,
        compliance: this.addDriveStipulationFormGroup.value.compliance,
        compliedBy: this.addDriveStipulationFormGroup.value.compliedBy,
        "createdBy": this.loggedUserData.id,
        "createdOn": new Date()
      }
      let formdata: FormData = new FormData();
      for(var i=0;i<this.selectedFiles.length;i++){
          formdata.append('file', this.selectedFiles[i]);
      }

      formdata.append('stipulation', save.stipulation);
      formdata.append('inspectionId', save.inspectionId);
      formdata.append('dateOfStipulation', save.dateOfStipulation);
      formdata.append('dateComplied', save.dateComplied);
      formdata.append('compliance', save.compliance);
      formdata.append('compliedBy', save.compliedBy);
      formdata.append('createdBy', save.createdBy);
      formdata.append('createdOn', save.createdOn.toLocaleDateString());
      this.sendAndRequestService.requestForPOST(Constants.app_urls.INSPECTIONS.STIPULATION.SAVE_STIPULATION ,formdata, true).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Stipulation Data saved Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Stipulation Data saving Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Stipulation Data saving Failed.");
      })
    } else if (this.update) {
      let update = {
        id: this.id,
        stipulation: this.addDriveStipulationFormGroup.value.stipulation,
        inspectionId: this.addDriveStipulationFormGroup.value.inspectionId,
        dateOfStipulation: this.addDriveStipulationFormGroup.value.dateOfStipulation,
        dateComplied: this.addDriveStipulationFormGroup.value.dateComplied,
        compliance: this.addDriveStipulationFormGroup.value.compliance,
        compliedBy: this.addDriveStipulationFormGroup.value.compliedBy,
        attachment: this.resp.attachment,
        "updatedBy": this.loggedUserData.id,
        "updatedOn": new Date()
      }
      let formdata: FormData = new FormData();
      for(var i=0;i<this.selectedFiles.length;i++){
          formdata.append('file', this.selectedFiles[i]);
      }
      formdata.append('id', update.id.toString());
      formdata.append('stipulation', update.stipulation);
      formdata.append('inspectionId', update.inspectionId);
      formdata.append('dateOfStipulation', update.dateOfStipulation);
      formdata.append('dateComplied', update.dateComplied);
      formdata.append('compliance', update.compliance);
      formdata.append('compliedBy', update.compliedBy);
      formdata.append('updatedBy', update.updatedBy);
      formdata.append('updatedOn', update.updatedOn.toLocaleDateString());
      formdata.append('attachment',update.attachment);
      this.sendAndRequestService.requestForPUT(Constants.app_urls.INSPECTIONS.STIPULATION.UPDATE_STIPULATION ,formdata, true).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Stipulation Data Updated Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Stipulation Data Updation Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Stipulation Data Updation Failed.");
      })
    }
  }
  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }
  upload(event) {
    if (event.target.files.length > 0) { this.filesExists = true; }
    for (var i = 0; i < event.target.files.length; i++) {
      this.selectedFiles.push(event.target.files[i]);
    }
  }
  removeFile(id) {
    this.selectedFiles.splice(id, 1);
  }

  removeEditFile(commonFileid, rowid){
    this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
      disableClose: false
  });
  this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete?';
  this.confirmDialogRef.afterClosed().subscribe(result => {
      if (result) {
          var id = localStorage.getItem('driveFileTypeId');
          var data ={
            "id":commonFileid,
            "fileName":rowid,
            "type":'Stipulation'
        }
          this.sendAndRequestService.requestForPOST(Constants.app_urls.INSPECTIONS.INSPECTIONS.DELETE_FILE, data, false).subscribe(data => {
              this.commonService.showAlertMessage("Deleted File Successfully");
             this.findAttachedFiles(commonFileid);
          }, error => {
              console.log('ERROR >>>');
              this.commonService.showAlertMessage("File Deletion Failed.");
          })
           this.confirmDialogRef = null;
      }
     
  });
  }
}
