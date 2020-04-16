import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { DrivesService } from 'src/app/services/drives.service';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { StipulationstModel } from 'src/app/models/drive.model';

@Component({
  selector: 'app-add-drive-inspection',
  templateUrl: './add-drive-inspection.component.html',
  styleUrls: ['./add-drive-inspection.component.css']
})
export class AddDriveInspectionComponent implements OnInit {
  save: boolean = true;
  update: boolean = false;
  title:string;
  id:number=0;
  isSubmit: boolean = false;
  resp:any;
  selectedFiles: File[] = [];
  filesExists: boolean = false;
  addDriveInspectionFormGroup: FormGroup;
  stipulationsList:any;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  inspectionFormErrors:any;
  stateList: any;
  
  constructor(
    private formBuilder: FormBuilder,
    private drivesService: DrivesService,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private route: ActivatedRoute,
    private router: Router,
  ) { 
     // Reactive form errors
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
      stipulationsId: {}
    };
  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
    this.getStipulationData();
    this.createInspectionForm();
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
  getStipulationData() {    
    this.drivesService.getStipulationData().subscribe((data) => {
      this.stipulationsList = data;
      console.log(JSON.stringify(data))
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }

  getInspectionDataById(id){
    this.drivesService.findInspectionsDataById(id)
    .subscribe((resp) => {
      console.log('depoTypes = ' + JSON.stringify(resp));
      this.resp = resp;
      console.log(this.resp.station)
      this.addDriveInspectionFormGroup.patchValue({
        id: this.resp.id,
        inspectionType: this.resp.inspectionType,
        section: this.resp.section,
        sectionStartLocation: this.resp.sectionStartLocation,
        sectionEndLocation: this.resp.sectionEndLocation,
        dateOfInspection: new Date(this.resp.dateOfInspection),        
        TKM: this.resp.tkm,
        RKM: this.resp.rkm,
        remarks: this.resp.remarks,
        authorisationDate: new Date(this.resp.authorisationDate),
        chargingDate: new Date(this.resp.chargingDate),
       // attachment: this.resp.attachment,
        station: this.resp.station,
        stipulationsId: !!this.resp.stipulationsId && this.resp.stipulationsId['id']
      });
      this.spinnerService.hide();
    })
  }

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
        'inspectionType': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
        'section': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.pattern(this.pattern)])],
        'sectionStartLocation': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
        'sectionEndLocation': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.pattern(this.pattern)])],
        'dateOfInspection': [null, Validators.required],
        'TKM': [null, Validators.required],
        'RKM': [null, Validators.required],
        'remarks': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
        'authorisationDate': [null, Validators.required],
        'chargingDate': [null, Validators.required],
        'attachment': [null, Validators.compose([Validators.required])],
        'station': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
        'stipulationsId': [null, Validators.compose([Validators.required])]
      });
  }
  onAddInspectionsFormSubmit() {

    if (this.addDriveInspectionFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    console.log(this.addDriveInspectionFormGroup.value);
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
      //  attachment: this.addDriveInspectionFormGroup.value.attachment,
        station: this.addDriveInspectionFormGroup.value.station,
        stipulationsId: this.addDriveInspectionFormGroup.value.stipulationsId,
        "createdBy": "1",
        "createdOn": "2020-04-01",
        "updatedBy": "1",
        "updatedOn": "2020-04-01"
      }
      this.drivesService.saveInspectionsData(save, this.selectedFiles).subscribe(response => {
        console.log(JSON.stringify(response));
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Inspection Data saved Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
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
        attachment: this.addDriveInspectionFormGroup.value.attachment,
        station: this.addDriveInspectionFormGroup.value.station,
        stipulationsId: this.addDriveInspectionFormGroup.value.stipulationsId,
        "createdBy": "1",
        "createdOn": "2020-04-01",
        "updatedBy": "1",
        "updatedOn": "2020-04-01"
      }
      this.drivesService.updateInspectionsData(update, this.selectedFiles).subscribe(response => {
        console.log(JSON.stringify(response));
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Inspection Data Updated Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
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
    console.log(this.selectedFiles)
  }
  removeFile(id) {
    this.selectedFiles.splice(id, 1);
  }
}
