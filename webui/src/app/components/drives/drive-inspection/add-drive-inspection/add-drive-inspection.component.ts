import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { FuseConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';
import { MatDialogRef, MatDialog } from '@angular/material';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
@Component({
  selector: 'app-add-drive-inspection',
  templateUrl: './add-drive-inspection.component.html',
  styleUrls: []
})
export class AddDriveInspectionComponent implements OnInit {
  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  save: boolean = true;
  update: boolean = false;
  title:string;
  id:number=0;
  isSubmit: boolean = false;
  resp:any;
  selectedFiles: File[] = [];
  filesExists: boolean = false;
  addDriveInspectionFormGroup: FormGroup;
  inspectionsList:any;
  pattern = "[a-zA-Z][a-zA-Z]*";
  inspectionFormErrors:any;
  stateList: any;
  inspectionTypeList:any;
  attachedImages:any;
  toMinDate = new Date();
  currentDate = new Date();
  dateFormat = 'MM-dd-yyyy ';
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  constructor(
    private formBuilder: FormBuilder,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private route: ActivatedRoute,
    private router: Router,
    public dialog: MatDialog,
    private sendAndRequestService: SendAndRequestService,
  ) { 
     this.inspectionFormErrors = {
      inspectionType:{},
      section: {},
      sectionStartLocation: {},
      sectionEndLocation: {},
      dateOfInspection: {},      
      TKM: {},
      RKM: {},
      remarks: {},
      authorisationDate: {},
      chargingDate: {},
      attachment: {},
      station: {},
      phase: {},
    };
  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
    this.getInspectionData();
    this.createInspectionForm();
    this.findInspectionType();
    if (!isNaN(this.id)) {
      this.addDriveInspectionFormGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      });
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = 'Edit';
      this.getInspectionDataById(this.id);
    } else {
      this.save = true;
      this.update = false;
      this.title = 'Save'
    }    
  }
  
  
  addEvent($event) {
    this.toMinDate = new Date($event.value);
  }
  getInspectionData() {    
    this.sendAndRequestService.requestForGET(Constants.app_urls.INSPECTIONS.INSPECTIONS.GET_INSPECTIONS).subscribe((data) => {
      this.inspectionsList = data;     
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }
  findInspectionType(){
    this.sendAndRequestService.requestForGET(Constants.app_urls.INSPECTIONS.INSPECTIONS.INSPECTION_TYPE).subscribe((data) => {
      this.inspectionTypeList = data;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }
  getInspectionDataById(id){
    this.sendAndRequestService.requestForGET(Constants.app_urls.INSPECTIONS.INSPECTIONS.EDIT + id)
    .subscribe((resp) => {
      this.resp = resp;
      console.log(this.resp)
      this.addDriveInspectionFormGroup.patchValue({
        id: this.resp.id,
        inspectionType: this.resp.inspectionType,
        section: this.resp.section,
        sectionStartLocation: this.resp.sectionStartLocation,
        sectionEndLocation: this.resp.sectionEndLocation,
        dateOfInspection: !!this.resp.dateOfInspection ? new Date(this.resp.dateOfInspection) : '',        
        TKM: this.resp.tkm,
        RKM: this.resp.rkm,
        remarks: this.resp.remarks,
        authorisationDate: !!this.resp.authorisationDate ? new Date(this.resp.authorisationDate) : '',
        chargingDate: !!this.resp.chargingDate ? new Date(this.resp.chargingDate) : '',
        station: this.resp.station,
      });
      console.log(this.resp.attachment);
      var commonId = !!this.resp.attachment && this.resp.attachment;
      console.log(commonId)
      this.spinnerService.hide();
      this.toMinDate = new Date(this.resp.dateOfInspection);
      this.toMinDate = new Date(this.resp.authorisationDate);
      this.findAttachedFiles(commonId);
    })
  }
  findAttachedFiles(commonId){
    this.sendAndRequestService.requestForGET(Constants.app_urls.INSPECTIONS.STIPULATION.GET_INSPECTION_AND_STIPULATION_ID + commonId)
    .subscribe((resp) => {
      this.attachedImages = resp;
    })
  }

  public get f() { return this.addDriveInspectionFormGroup.controls; }
  onFormValuesChanged() {
    for (const field in this.inspectionFormErrors) {
      if (!this.inspectionFormErrors.hasOwnProperty(field)) {
        continue;
      }
      this.inspectionFormErrors[field] = {};
      const control = this.addDriveInspectionFormGroup.get(field);

      if (control && control.dirty && !control.valid) {
        this.inspectionFormErrors[field] = control.errors;
      }
    }
  }

  createInspectionForm() {
    this.addDriveInspectionFormGroup
      = this.formBuilder.group({
        id: 0,
        'inspectionType': ['', Validators.compose([Validators.required])],
        'section': ['', Validators.compose([Validators.required])],
        'sectionStartLocation': [''],
        'sectionEndLocation': [''],
        'dateOfInspection': [''],
        'TKM': [''],
        'RKM': [''],
        'remarks': ['', Validators.maxLength(255)],
        'authorisationDate': [''],
        'chargingDate': [''],
        'attachment': [null],
        'station': ['']
      });
  }
  onAddInspectionsFormSubmit() {
    if (this.addDriveInspectionFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    if(this.save){
      let save = {
        inspectionType: this.addDriveInspectionFormGroup.value.inspectionType,
        section: this.addDriveInspectionFormGroup.value.section,
        sectionStartLocation: this.addDriveInspectionFormGroup.value.sectionStartLocation,
        sectionEndLocation: this.addDriveInspectionFormGroup.value.sectionEndLocation,
        dateOfInspection: this.addDriveInspectionFormGroup.value.dateOfInspection,        
        tkm: this.addDriveInspectionFormGroup.value.TKM,
        rkm: this.addDriveInspectionFormGroup.value.RKM,
        remarks: this.addDriveInspectionFormGroup.value.remarks,
        authorisationDate: this.addDriveInspectionFormGroup.value.authorisationDate,
        chargingDate: this.addDriveInspectionFormGroup.value.chargingDate,
        station: this.addDriveInspectionFormGroup.value.station,
        "createdBy": this.loggedUserData.id,
        "createdOn": new Date()
      }
      let formdata: FormData = new FormData();
      for(var i=0;i<this.selectedFiles.length;i++){
          formdata.append('file', this.selectedFiles[i]);
      }
      formdata.append('inspectionType', save.inspectionType);
      formdata.append('section', save.section);
      formdata.append('sectionStartLocation', save.sectionStartLocation);
      formdata.append('sectionEndLocation', save.sectionEndLocation);
      formdata.append('dateOfInspection', save.dateOfInspection);
      formdata.append('tkm', save.tkm);
      formdata.append('rkm', save.rkm);
      formdata.append('remarks', save.remarks);
      formdata.append('authorisationDate', save.authorisationDate);
      formdata.append('chargingDate', save.chargingDate);
      formdata.append('station', save.station);
      formdata.append('createdBy', save.createdBy);
      formdata.append('createdOn', save.createdOn.toLocaleDateString());
      this.sendAndRequestService.requestForPOST(Constants.app_urls.INSPECTIONS.INSPECTIONS.SAVE,formdata, true).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if(this.resp.code == Constants.CODES.SUCCESS){
          this.commonService.showAlertMessage("Inspection Data saved Successfully");
          this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Inspection Data saving Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Inspection Data saving Failed.");
      })
    }else if(this.update){
      let update = {
        id:this.id,
        inspectionType: this.addDriveInspectionFormGroup.value.inspectionType,
        section: this.addDriveInspectionFormGroup.value.section,
        sectionStartLocation: this.addDriveInspectionFormGroup.value.sectionStartLocation,
        sectionEndLocation: this.addDriveInspectionFormGroup.value.sectionEndLocation,
        dateOfInspection: this.addDriveInspectionFormGroup.value.dateOfInspection,        
        tkm: this.addDriveInspectionFormGroup.value.TKM,
        rkm: this.addDriveInspectionFormGroup.value.RKM,
        remarks: this.addDriveInspectionFormGroup.value.remarks,
        authorisationDate: this.addDriveInspectionFormGroup.value.authorisationDate,
        chargingDate: this.addDriveInspectionFormGroup.value.chargingDate,
        attachment: this.resp.attachment,
        station: this.addDriveInspectionFormGroup.value.station,
        "updatedBy": this.loggedUserData.id,
        "updatedOn": new Date()
      }
      let formdata: FormData = new FormData();
      for(var i=0;i<this.selectedFiles.length;i++){
          formdata.append('file', this.selectedFiles[i]);
      }
      formdata.append('id', update.id.toString());
      formdata.append('inspectionType', update.inspectionType);
      formdata.append('section', update.section);
      formdata.append('sectionStartLocation', update.sectionStartLocation);
      formdata.append('sectionEndLocation', update.sectionEndLocation);
      formdata.append('dateOfInspection', update.dateOfInspection);
      formdata.append('tkm', update.tkm);
      formdata.append('rkm', update.rkm);
      formdata.append('remarks', update.remarks);
      formdata.append('authorisationDate', update.authorisationDate);
      formdata.append('chargingDate', update.chargingDate);
      formdata.append('station', update.station);
      formdata.append('updatedBy', update.updatedBy);
      formdata.append('updatedOn', update.updatedOn.toLocaleDateString());
      formdata.append('attachment', update.attachment);
      this.sendAndRequestService.requestForPOST(Constants.app_urls.INSPECTIONS.INSPECTIONS.UPDATE ,update, true).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if(this.resp.code == Constants.CODES.SUCCESS){
          this.commonService.showAlertMessage("Inspection Data Updated Successfully");
          this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Inspection Data Updating Failed."); 
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Inspection Data Updating Failed.");
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
        var data ={
          "id":commonFileid,
          "fileName":rowid,
          "type":'Inspection'
      }
          var id = localStorage.getItem('driveFileTypeId');
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

