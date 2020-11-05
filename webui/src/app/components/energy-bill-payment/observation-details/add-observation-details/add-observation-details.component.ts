import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDatepickerInputEvent, MatDialogRef, MatDialog } from '@angular/material';
import { Constants } from 'src/app/common/constants';
import { FuseConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { ObservationModel} from 'src/app/models/foot-patrolling-inspection.model';

@Component({
  selector: 'app-add-observation-details',
  templateUrl: './add-observation-details.component.html',
  styleUrls: []
})

export class AddObservationDetailsComponent implements OnInit {
  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  save: boolean = true;
  update: boolean = false;
  title: string;
  id: number = 0;
  isSubmit: boolean = false;
  resp: any;
  selectedFiles: File[] = [];
  filesExists: boolean = false;
  addObservationFormGroup: FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  observationFormErrors: any;
  stateList: any;
  assertTypeList: any;
  attachedImages:any;
  observationCategoryData:any;
  observationItemData:any;
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
    this.observationFormErrors = {
      location: {},
      observationCategory: {},
      observationItem: {},
      description: {},
      actionRequired: {},
      attachment: {},
    };

  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
    console.log("obsId"+this.id)
    this.categoryList();
    this.createObservationForm();
    if (!isNaN(this.id)) {
      this.addObservationFormGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      });
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = 'Edit';
      this.getObservationDataById(this.id);

    } else {
      this.save = true;
      this.update = false;
      this.title = 'Save';
    }
         error => {
      this.spinnerService.hide();
    }; 
  }
  categoryList()
        {  
               this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.OBSERVATION_CATEGORIES.GET_OBS_CATEGORIES).subscribe((data) => {
                 this.observationCategoryData = data;
        }
               );

       }
       getObsCheckList(){
        var observationCategory = this.addObservationFormGroup.value.observationCategory ;
    	this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_OBSERVATION_CHECK_LIST_BASED_ON_OBSCATE + observationCategory).subscribe((data) => {
                 this.observationItemData = data;
        		});
    }
    
    
  public get f() { return this.addObservationFormGroup.controls; }


  getObservationDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.DAILY_SUMMARY.OBSERVATION.GET_OBSERVATION_ID+id).subscribe((resp) => {
        this.resp = resp;
        this.addObservationFormGroup.patchValue({
          id: this.resp.id,
          location: this.resp.location,
          observationCategory: this.resp.observationCategory,
          observationItem: this.resp.observationItem,
          description: this.resp.description,
          actionRequired:this.resp.actionRequired == 'true' ? true: false,
        });
        var commonId = !!this.resp.attachment && this.resp.attachment;
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
    for (const field in this.observationFormErrors) {
      if (!this.observationFormErrors.hasOwnProperty(field)) {
        continue;
      }
      this.observationFormErrors[field] = {};
      const control = this.addObservationFormGroup.get(field);

      if (control && control.dirty && !control.valid) {
        this.observationFormErrors[field] = control.errors;
      }
    }
  }

  createObservationForm() {
    this.addObservationFormGroup
      = this.formBuilder.group({
        id: 0,
        'inspectionType':[''],
        'inspectionSeqId':[''],
        'location': ['', Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
        'observationCategory': [''],
        'observationItem': [''],
        'severity': [''],
        'priority': [''],
        'actionRequired':[''],
        'attachment': [''],
        'description': ['',Validators.maxLength(255)],
      });
  }
  

  onAddObservationFormSubmit() {

    if (this.addObservationFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    if (this.save) {
      let save = {
        location: this.addObservationFormGroup.value.location,
        inspectionSeqId: this.addObservationFormGroup.value.inspectionSeqId,
        observationCategory: this.addObservationFormGroup.value.observationCategory,
        observationItem: this.addObservationFormGroup.value.observationItem,
        description: this.addObservationFormGroup.value.description,
        'actionRequired': this.addObservationFormGroup.value.actionRequired == true ? 'true' : 'false', 
        "createdBy": this.loggedUserData.id,
      }
      let formdata: FormData = new FormData();
      for(var i=0;i<this.selectedFiles.length;i++){
          formdata.append('file', this.selectedFiles[i]);
      }
      formdata.append('location', save.location);
      formdata.append('inspectionSeqId', save.inspectionSeqId);
      formdata.append('observationCategory', save.observationCategory);
      formdata.append('observationItem', save.observationItem);
      formdata.append('description', save.description);
      formdata.append('actionRequired', save.actionRequired);
      formdata.append('createdBy', save.createdBy);
      this.sendAndRequestService.requestForPOST(Constants.app_urls.DAILY_SUMMARY.OBSERVATION.SAVE_OBSERVATION,formdata, true).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Observation Data saved Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Observation Data saving Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Observation Data saving Failed.");
      })
    } else if (this.update) {
      let update = {
        id: this.id,
        location: this.addObservationFormGroup.value.location,
        inspectionSeqId: this.addObservationFormGroup.value.inspectionSeqId,
        observationCategory: this.addObservationFormGroup.value.observationCategory,
        observationItem: this.addObservationFormGroup.value.observationItem,
        description: this.addObservationFormGroup.value.description,
        'actionRequired': this.addObservationFormGroup.value.actionRequired == true ? 'true' : 'false', 
        attachment: this.resp.attachment,
        "updatedBy": this.loggedUserData.id,
      }
      let formdata: FormData = new FormData();
      for(var i=0;i<this.selectedFiles.length;i++){
          formdata.append('file', this.selectedFiles[i]);
      }
      formdata.append('id', update.id.toString());
      formdata.append('location', update.location);
      formdata.append('inspectionSeqId', update.inspectionSeqId);
      formdata.append('observationCategory', update.observationCategory);
      formdata.append('observationItem', update.observationItem);
      formdata.append('description', update.description);
      formdata.append('actionRequired', update.actionRequired);
      formdata.append('updatedBy', update.updatedBy);
      formdata.append('attachment',update.attachment);
      this.sendAndRequestService.requestForPUT(Constants.app_urls.DAILY_SUMMARY.OBSERVATION.UPDATE_OBSERVATION,formdata, true).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Observation Data Updated Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Observation Data Updation Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Observation Data Updation Failed.");
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
          var id = localStorage.getItem('observationFileTypeId');
          var data ={
            "id":commonFileid,
            "fileName":rowid,
            "type":'Observation'
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
