import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { DrivesService } from 'src/app/services/drives.service';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { FuseConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';
import { MatDialogRef, MatDialog } from '@angular/material';
@Component({
  selector: 'app-add-drive-inspection',
  templateUrl: './add-drive-inspection.component.html',
  styleUrls: ['./add-drive-inspection.component.css']
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
  pattern = "[a-zA-Z][a-zA-Z ]*";
  inspectionFormErrors:any;
  stateList: any;
  inspectionTypeList:any;
  attachedImages:any;
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  constructor(
    private formBuilder: FormBuilder,
    private drivesService: DrivesService,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private route: ActivatedRoute,
    private router: Router,
    public dialog: MatDialog,
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
      //stipulationsId: {}
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
  getInspectionData() {    
    this.drivesService.getInspectionData().subscribe((data) => {
      this.inspectionsList = data;     
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }
  findInspectionType(){
    this.drivesService.getInspectionsType().subscribe((data) => {
      this.inspectionTypeList = data;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }
  getInspectionDataById(id){
    this.drivesService.findInspectionsDataById(id)
    .subscribe((resp) => {
      this.resp = resp;
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
      this.findAttachedFiles(commonId);
    })
  }
  findAttachedFiles(commonId){
    this.drivesService.findStipulationAndInspectionDataById(commonId)
    .subscribe((resp) => {
      console.log("files : "+JSON.stringify(resp));  
      this.attachedImages = resp;
     // this.attachedImages.filter(ime =>{
      //  var f = new File([""], ime.originalFileName);
      //  f.size = ime.fileSize;
       /*  this.addDriveInspectionFormGroup.patchValue({
          attachment: ime.originalFileName
        }); */
      //  this.filesExists = true;
        
       // this.selectedFiles.push(f);
      //});
      //var f = new File([""], "filename");    
      //this.selectedFiles.push(
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
        'inspectionType': [null, Validators.compose([Validators.required])],
        'section': [null, Validators.compose([Validators.required, Validators.pattern(this.pattern)])],
        'sectionStartLocation': [null],
        'sectionEndLocation': [null],
        'dateOfInspection': [null],
        'TKM': [null],
        'RKM': [null],
        'remarks': [null, Validators.maxLength(250)],
        'authorisationDate': [null],
        'chargingDate': [null],
        'attachment': [null],
        'station': [null]
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
        "createdBy": this.loggedUserData.username,
        "createdOn": new Date()
      }
      this.drivesService.saveInspectionsData(save, this.selectedFiles).subscribe(response => {
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
        "updatedBy": this.loggedUserData.username,
        "updatedOn": new Date()
      }
      this.drivesService.updateInspectionsData(update, this.selectedFiles).subscribe(response => {
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
        console.log('result : '+result)
        //  this.spinnerService.show();
          var id = localStorage.getItem('driveFileTypeId');
          this.drivesService.deleteFile(commonFileid, rowid, 'Inspection').subscribe(data => {
             // this.spinnerService.hide();
              this.commonService.showAlertMessage("Deleted File Successfully");
             // this.updateData(id);
             this.findAttachedFiles(commonFileid);
          }, error => {
              console.log('ERROR >>>');
              //this.spinnerService.hide();
              this.commonService.showAlertMessage("File Deletion Failed.");
          })
           this.confirmDialogRef = null;
      }
     
  });
  }
  
}

