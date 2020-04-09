import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-add-drive-stipulation',
  templateUrl: './add-drive-stipulation.component.html',
  styleUrls: ['./add-drive-stipulation.component.css']
})
export class AddDriveStipulationComponent implements OnInit {

  saveUser:boolean=true;
  updateUser:boolean=false;

  addDriveStipulationFormGroup:FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  
  stateList:any;
  constructor(
    private formBuilder: FormBuilder
  ) {
    
   }

  ngOnInit() {
    this.addDriveStipulationFormGroup
     = this.formBuilder.group({
      id: 0,
      'stipulation': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
      'stipulationTo': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.pattern(this.pattern)])],
      'dateOfStipulation': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
      'dateComplied': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.pattern(this.pattern)])],
      'compliance': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.pattern(this.pattern)])],
      'attachment': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
      'compliedBy': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])],
      'assetType': [null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]+$')])]
  });
  }

}
