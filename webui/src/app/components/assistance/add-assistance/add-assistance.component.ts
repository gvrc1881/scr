import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDatepickerInputEvent, MatDialogRef, MatDialog,DateAdapter, MAT_DATE_FORMATS } from '@angular/material';
import { Constants } from 'src/app/common/constants';
import { FuseConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { ObservationModel} from 'src/app/models/foot-patrolling-inspection.model';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';

@Component({
  selector: 'app-add-assistance',
  templateUrl: './add-assistance.component.html',
  providers: [
    {
        provide: DateAdapter, useClass: AppDateAdapter
    },
    {
        provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
    }
    ]
})

export class AddAssistanceComponent implements OnInit {

  pagination = Constants.PAGINATION_NUMBERS;
 FiledLabels = FieldLabelsConstant.LABELS;
 Titles = FieldLabelsConstant.TITLE;
  loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
  save: boolean = true;
  update: boolean = false;
  title: string = Constants.EVENTS.ADD;
  id: number = 0;
  isSubmit: boolean = false;
  resp: any;
  selectedFiles: File[] = [];
  filesExists: boolean = false;
  addAssistanceFormGroup: FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  assistanceFormErrors: any;
  workList:any;
  workData:any;
  usersData:any;
  attachedImages:any;
  toMinDate=new Date();
  today=new Date();
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  observations: ObservationModel[] = [];

  constructor(
    private formBuilder: FormBuilder,
    private sendAndRequestService : SendAndRequestService,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private route: ActivatedRoute,
    private router: Router,
    public dialog: MatDialog,
  ) {
    // Reactive form errors
    this.assistanceFormErrors = {
      workId:{},
      workGroupId: {},
      typeOfAssistance: {},
      assistance: {},
      requestedBy: {},
      requestedDate: {},
      requestTo: {},
      responseBy: {},
      responseDate: {},
      response: {},
      remark: {},
      status: {},
      attachment: {},
    };

  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
    this.getWorkGroup();
    this.getUserDetails();
    this.createAssistanceForm();
    if (!isNaN(this.id)) {
      this.addAssistanceFormGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      });
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;
      this.getAssistanceDataById(this.id);

    } else {
      this.save = true;
      this.update = false;
      this.title = Constants.EVENTS.ADD;
    }
         error => {
      this.spinnerService.hide();
    }; 
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WORK).subscribe((data) => {
      this.workList = data;
       console.log('*** length ***'+this.workList.length);
  },error => {} );
  }
  
    
    
  public get f() { return this.addAssistanceFormGroup.controls; }


  getAssistanceDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.PROJECT_ADMIN.ASSISTANCE.GET_ASSISTANCE_ID+id).subscribe((resp) => {
      this.resp = resp;
      let work = this.resp.workId
        console.log("list==="+this.resp.workId.id);
        if (this.resp) {
      this.addAssistanceFormGroup.patchValue({
        id: this.resp.id,
        workId: this.resp.workId.id,
        workGroupId: this.resp.workGroupId.id,
        typeOfAssistance: this.resp.typeOfAssistance,
        assistance: this.resp.assistance,
        requestedBy: this.resp.requestedBy,
        requestedDate:new Date(this.resp.requestedDate), 
        requestTo: this.resp.requestTo,
        responseBy: this.resp.responseBy,
        responseDate:new Date(this.resp.responseDate), 
        response: this.resp.response,
        remark: this.resp.remark,
        status: this.resp.status,
        
      });
      var commonId = !!this.resp.attachment && this.resp.attachment;
      this.spinnerService.hide();
      this.findAttachedFiles(commonId);
    }
    this.spinnerService.hide();
    })

     
  }

  findAttachedFiles(commonId){
    this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.OBSERVATION.GET_OBSERVATION_CONTENT_ID + commonId)
    .subscribe((resp) => {
      this.attachedImages = resp;
    })
  }


  onFormValuesChanged() {
    for (const field in this.assistanceFormErrors) {
      if (!this.assistanceFormErrors.hasOwnProperty(field)) {
        continue;
      }
      this.assistanceFormErrors[field] = {};
      const control = this.addAssistanceFormGroup.get(field);

      if (control && control.dirty && !control.valid) {
        this.assistanceFormErrors[field] = control.errors;
      }
    }
  }

  createAssistanceForm() {
    this.addAssistanceFormGroup
      = this.formBuilder.group({
        id: 0,
        'workId':[''],
        'workGroupId':[''],
        'typeOfAssistance': [''],
        'assistance': [''],
        'requestedBy': [''],
        'requestedDate': [''],
        'requestTo': [''],
        'responseBy':[''],
        'responseDate': [''],
        'response': [''],
        'remark': [''],
        'status': ['']
      });
  }
  

  onAddAssistanceFormSubmit() {

    if (this.addAssistanceFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    if (this.save) {
      let save = {
        workId: this.addAssistanceFormGroup.value.workId,
        workGroupId: this.addAssistanceFormGroup.value.workGroupId,
        typeOfAssistance: this.addAssistanceFormGroup.value.typeOfAssistance,
        assistance: this.addAssistanceFormGroup.value.assistance,
        requestedBy: this.addAssistanceFormGroup.value.requestedBy,
        requestedDate: this.addAssistanceFormGroup.value.requestedDate,
        requestTo: this.addAssistanceFormGroup.value.requestTo,
        responseBy: this.addAssistanceFormGroup.value.responseBy,
        responseDate: this.addAssistanceFormGroup.value.responseDate,
        response: this.addAssistanceFormGroup.value.response,
        remark: this.addAssistanceFormGroup.value.remark,
        status: this.addAssistanceFormGroup.value.status,
        "createdBy": this.loggedUserData.id,

      }
      let formdata: FormData = new FormData();
      for(var i=0;i<this.selectedFiles.length;i++){
          formdata.append('file', this.selectedFiles[i]);
      }
      formdata.append('workId', save.workId);
      formdata.append('workGroupId', save.workGroupId);
      formdata.append('typeOfAssistance', save.typeOfAssistance);
      formdata.append('assistance', save.assistance);
      formdata.append('requestedBy', save.requestedBy);
      formdata.append('requestedDate', save.requestedDate);
      formdata.append('requestTo', save.requestTo);
      formdata.append('responseBy', save.responseBy);
      formdata.append('responseDate', save.responseDate);
      formdata.append('response', save.response);
      formdata.append('remark', save.remark);
      formdata.append('status', save.status);
      formdata.append('createdBy', save.createdBy);

      this.sendAndRequestService.requestForPOST(Constants.app_urls.PROJECT_ADMIN.ASSISTANCE.SAVE_ASSISTANCE,formdata, true).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Assistance Data saved Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Assistance Data saving Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Assistance Data saving Failed.");
      })
    } else if (this.update) {
      let update = {
        id: this.id,
        workId: this.addAssistanceFormGroup.value.workId,
        workGroupId: this.addAssistanceFormGroup.value.workGroupId,
        typeOfAssistance: this.addAssistanceFormGroup.value.typeOfAssistance,
        assistance: this.addAssistanceFormGroup.value.assistance,
        requestedBy: this.addAssistanceFormGroup.value.requestedBy,
        requestedDate: this.addAssistanceFormGroup.value.requestedDate,
        requestTo: this.addAssistanceFormGroup.value.requestTo,
        responseBy: this.addAssistanceFormGroup.value.responseBy,
        responseDate: this.addAssistanceFormGroup.value.responseDate,
        response: this.addAssistanceFormGroup.value.response,
        remark: this.addAssistanceFormGroup.value.remark,
        status: this.addAssistanceFormGroup.value.status, 
        attachment: this.resp.attachment,
        "updatedBy": this.loggedUserData.id,

      }
      let formdata: FormData = new FormData();
      for(var i=0;i<this.selectedFiles.length;i++){
          formdata.append('file', this.selectedFiles[i]);
      }
      formdata.append('id', update.id.toString());
      formdata.append('workId', update.workId);
      formdata.append('workGroupId', update.workGroupId);
      formdata.append('typeOfAssistance', update.typeOfAssistance);
      formdata.append('assistance', update.assistance);
      formdata.append('requestedBy', update.requestedBy);
      formdata.append('requestedDate', update.requestedDate);
      formdata.append('requestTo', update.requestTo);
      formdata.append('responseBy', update.responseBy);
      formdata.append('responseDate', update.responseDate);
      formdata.append('response', update.response);
      formdata.append('remark', update.remark);
      formdata.append('status', update.status);
      formdata.append('attachment',update.attachment);
      formdata.append('updatedBy', update.updatedBy);
      this.sendAndRequestService.requestForPUT(Constants.app_urls.PROJECT_ADMIN.ASSISTANCE.UPDATE_ASSISTANCE,formdata, true).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Assistance Data Updated Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Assistance Data Updation Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Assistance Data Updation Failed.");
      })
    }
  }
  onGoBack() {
    if (this.save) {
      this.router.navigate(['../'], { relativeTo: this.route });
    } else if (this.update) {
      this.router.navigate(['../'], { relativeTo: this.route });
    }
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
          var id = sessionStorage.getItem('assistanceFileTypeId');
          var data ={
            "id":commonFileid,
            "fileName":rowid,
            "type":'Assistance'
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
  getWorkGroup(){
  this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WORK_GROUP).subscribe((data) => {
             this.workData = data;
        });
}
getUserDetails(){
  this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.USERS.GET_ALLUSERS).subscribe((data) => {
             this.usersData = data;
        });
}
}
