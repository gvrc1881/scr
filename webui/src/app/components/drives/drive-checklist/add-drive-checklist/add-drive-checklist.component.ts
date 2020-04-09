import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-add-drive-checklist',
  templateUrl: './add-drive-checklist.component.html',
  styleUrls: ['./add-drive-checklist.component.css']
})
export class AddDriveChecklistComponent implements OnInit {
  saveUser:boolean=true;
  updateUser:boolean=false;

  addDriveChecklistFormGroup:FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  measureActivityList=[];
  driveList=[];
  statusList = [];
  constructor(
    private formBuilder: FormBuilder
  ) { }

  ngOnInit() {
    this.addDriveChecklistFormGroup = this.formBuilder.group({
      id: 0,
      'drive': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
      'measureActivityList': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.pattern(this.pattern)])],
      'displayOrder': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
      'lowerLimit': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
      'upperLimit': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
      'status': [null, Validators.required]
  });
  }
}
