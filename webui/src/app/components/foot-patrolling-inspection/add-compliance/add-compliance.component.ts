import { Component, OnInit } from '@angular/core';
import { FormBuilder,FormGroup } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialogRef, MatDialog,DateAdapter, MAT_DATE_FORMATS} from '@angular/material';
import { Constants } from 'src/app/common/constants';
import { FuseConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { DatePipe } from '@angular/common';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';



@Component({
  selector: 'app-add-compliance',
  templateUrl: './add-compliance.component.html',
  providers: [
    {
        provide: DateAdapter, useClass: AppDateAdapter
    },
    {
        provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
    }
    ]
})

export class AddComplianceComponent implements OnInit {
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
  save: boolean = true;
  update: boolean = false;
  title: string =Constants.EVENTS.ADD;
  id: number = 0;
  obsId:any;
  obsData:any;
  isSubmit: boolean = false;
  resp: any;
  toMinDate=new Date();
  selectedFiles: File[] = [];
  filesExists: boolean = false;
  addComplianceFormGroup: FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  complianceFormErrors: any;
  stateList: any;
  assertTypeList: any;
  attachedImages:any;
  statusTypeData:any;
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;

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
    this.complianceFormErrors = {
      status: {},
      action: {},
      complianceBy: {},
      compliedDateTime: {}
    };

  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
    this.obsId = +this.route.snapshot.params['obsId'];
    this.statusList();
    this.createComplianceForm();
    if (!isNaN(this.id)) {
      this.addComplianceFormGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      });
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;
      this.getComplianceById(this.id);

    } else {
      this.save = true;
      this.update = false;
      this.title = Constants.EVENTS.ADD;
    }
    this.getObservationDetails(this.obsId);
  }
  statusList()
    {  
        this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_STATUS_ITEM + 'COMPLIANCES_STATUS').subscribe((data) => {
            this.statusTypeData = data;
    }
           );

   }

  public get f() { return this.addComplianceFormGroup.controls; }
getObservationDetails(obsId:any){
  this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.OBSERVATION.GET_OBSERVATION_ID + obsId).subscribe((data) => {
    this.obsData = data;
    console.log("obsData"+JSON.stringify(data));
})
}

  getComplianceById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.COMPLIANCES.GET_COMPLIANCE_ID + id)
      .subscribe((resp) => {
        this.resp = resp;
        this.obsId = this.resp.obeservationSeqId;
        console.log("this.obsid"+this.obsId);
        this.getObservationDetails(this.obsId);
        this.addComplianceFormGroup.patchValue({
          id: this.resp.id,
          status: this.resp.status,
          action: this.resp.action,
          complianceBy: this.resp.complianceBy,
          compliedDateTime:new Date(this.resp.compliedDateTime), 
          
        });
        var commonId = !!this.resp.document && this.resp.document;
        this.spinnerService.hide();
        this.findAttachedFiles(commonId);
      })
  }
  
  findAttachedFiles(commonId){
    this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.OBSERVATION.GET_OBSERVATION_CONTENT_ID + commonId)
    .subscribe((resp) => {
      this.attachedImages = resp;
    })
  }


  onFormValuesChanged() {
    for (const field in this.complianceFormErrors) {
      if (!this.complianceFormErrors.hasOwnProperty(field)) {
        continue;
      }
      this.complianceFormErrors[field] = {};
      const control = this.addComplianceFormGroup.get(field);

      if (control && control.dirty && !control.valid) {
        this.complianceFormErrors[field] = control.errors;
      }
    }
  }

  createComplianceForm() {
    this.addComplianceFormGroup
      = this.formBuilder.group({
        id: 0,
        'obeservationSeqId':[''],
            'status':[''],
            'action':[''],
            'complianceBy': [''],
            'compliedDateTime' : [''],
            'document'  :['']
      });
  }
  

  onAddComplianceFormSubmit() {

    if (this.addComplianceFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    if (this.save) {
      let save = {
        obeservationSeqId:this.obsId,
        status: this.addComplianceFormGroup.value.status,
        action: this.addComplianceFormGroup.value.action,
        complianceBy: this.addComplianceFormGroup.value.complianceBy,
        compliedDateTime: this.addComplianceFormGroup.value.compliedDateTime,
        "createdBy": this.loggedUserData.id,
      }
      let formdata: FormData = new FormData();
      for(var i=0;i<this.selectedFiles.length;i++){
          formdata.append('file', this.selectedFiles[i]);
      }
      formdata.append('obeservationSeqId', save.obeservationSeqId);
      formdata.append('status', save.status);
      formdata.append('action', save.action);
      formdata.append('complianceBy', save.complianceBy);
      formdata.append('compliedDateTime', save.compliedDateTime);
      formdata.append('createdBy', save.createdBy);
      this.sendAndRequestService.requestForPOST(Constants.app_urls.DAILY_SUMMARY.COMPLIANCES.SAVE_COMPLIANCE,formdata , true).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Compliance Data saved Successfully");
        this.router.navigate(['../../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Compliance Data saving Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Compliance Data saving Failed.");
      })
    } else if (this.update) {
      let update = {
        id: this.id,
        obeservationSeqId:this.obsId,
        status: this.addComplianceFormGroup.value.status,
        action: this.addComplianceFormGroup.value.action,
        complianceBy: this.addComplianceFormGroup.value.complianceBy,
        compliedDateTime: this.addComplianceFormGroup.value.compliedDateTime,
        document: this.resp.document,
        "updatedBy": this.loggedUserData.id,
      }
      let formdata: FormData = new FormData();
      for(var i=0;i<this.selectedFiles.length;i++){
          formdata.append('file', this.selectedFiles[i]);
      }
      formdata.append('obeservationSeqId', update.obeservationSeqId);
      formdata.append('id', update.id.toString());
      formdata.append('status', update.status);
      formdata.append('action', update.action);
      formdata.append('complianceBy', update.complianceBy);
      formdata.append('compliedDateTime', update.compliedDateTime);
      formdata.append('updatedBy', update.updatedBy);
      formdata.append('document',update.document);
      this.sendAndRequestService.requestForPUT(Constants.app_urls.DAILY_SUMMARY.COMPLIANCES.UPDATE_COMPLIANCE,formdata,true).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Compliance Data Updated Successfully");
        this.router.navigate(['../../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Compliance Data Updation Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Compliance Data Updation Failed.");
      })
    }
  }
  onGoBack() {
    this.router.navigate(['../../'], { relativeTo: this.route });
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
          var id = sessionStorage.getItem('complianceFileTypeId');
          var data ={
            "id":commonFileid,
            "fileName":rowid,
            "type":'Compliance'
        }
          this.sendAndRequestService.requestForPOST(Constants.app_urls.DAILY_SUMMARY.OBSERVATION.DELETE_FILE, data, false).subscribe(data => {
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
