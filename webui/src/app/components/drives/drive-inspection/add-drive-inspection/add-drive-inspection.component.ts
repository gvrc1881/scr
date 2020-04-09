import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-add-drive-inspection',
  templateUrl: './add-drive-inspection.component.html',
  styleUrls: ['./add-drive-inspection.component.css']
})
export class AddDriveInspectionComponent implements OnInit {
  saveUser:boolean=true;
  updateUser:boolean=false;

  addDriveInspectionFormGroup:FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  
  stateList:any;
  constructor(
    private formBuilder: FormBuilder
  ) { }

  ngOnInit() {
    this.addDriveInspectionFormGroup
     = this.formBuilder.group({
      id: 0,
      'inspectionType': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
      'section': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.pattern(this.pattern)])],
      'sectionStartLocation': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
      'sectionEndLocation': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.pattern(this.pattern)])],
      'dateOfInspection': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.pattern(this.pattern)])],
      'TKM': [null, Validators.required],
      'RKM': [null, Validators.required],
      'remarks': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
      'authorisationDate':[null, Validators.required],
      'chargingDate':[null, Validators.required],
      'attachment': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.pattern(this.pattern)])],
      'station': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
      'stipulationsId': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])]
  });
  }

}
