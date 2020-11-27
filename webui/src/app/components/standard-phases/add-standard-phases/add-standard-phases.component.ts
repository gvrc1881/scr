import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';


@Component({
  selector: 'app-add-standard-phases',
  templateUrl: './add-standard-phases.component.html',
  
})
export class AddStandardPhasesComponent implements OnInit {

  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  resp: any;
  title:string = Constants.EVENTS.ADD;
  addStandardPhasesFormGroup: FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  dependency:boolean=false;
  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private sendAndRequestService:SendAndRequestService
  ) {
   
  }

  ngOnInit() {
  this.id = +this.route.snapshot.params['id'];
    if (!isNaN(this.id)) { 
    this.updateStandardPhasesForm();
    this.spinnerService.show();
    this.save = false;
    this.update = true;
    this.title = Constants.EVENTS.UPDATE;
    this.getStandardPhasesById(this.id);
  } else {
    this.createStandardPhasesForm();
      this.save = true;
      this.update = false;
    this.title = Constants.EVENTS.ADD;
  }
  
}


createStandardPhasesForm() {
this.addStandardPhasesFormGroup = this.formBuilder.group({
id: 0, 
'name': [null, Validators.compose([Validators.required])],
'description': [null],
'sequence':[null,Validators.compose([Validators.required]),this.duplicateSequence.bind(this)],
'dependencyToStart':[null],
'typeOfWork':[null, Validators.compose([Validators.required]),this.duplicateDescription.bind(this),this.duplicateName.bind(this)],
'weightage': [null],
});
}
updateStandardPhasesForm() {
  this.addStandardPhasesFormGroup = this.formBuilder.group({
  id: 0, 
  'name': [null, Validators.compose([Validators.required])],
  'description': [null],
  'sequence':[null,Validators.compose([Validators.required]),this.duplicateSequenceAndId.bind(this)],
  'dependencyToStart':[null],
  'typeOfWork':[null, Validators.compose([Validators.required]),this.duplicateDescriptionAndId.bind(this)],
  'weightage': [null],
  });
  }
getStandardPhasesById(id) {
  this.sendAndRequestService.requestForGET(Constants.app_urls.STANDARD_PHASES.GET_STANDARD_PHASES_ID+id)
  .subscribe((resp) => {
      this.resp = resp;
      this.addStandardPhasesFormGroup.patchValue({
        id: this.resp.id,
        name: this.resp.name,
        description: this.resp.description,
        sequence:this.resp.sequence,
        dependencyToStart: this.resp.dependencyToStart,
        typeOfWork: this.resp.typeOfWork,
        weightage: this.resp.weightage,
      });
      this.spinnerService.hide();
    })
}
standardPhaseSubmit() {
this.isSubmit = true;
if (this.addStandardPhasesFormGroup.invalid) {
this.isSubmit = false;
return;
}
this.spinnerService.show();

if (this.save) {
  var savePhaseModel = {
    "name": this.addStandardPhasesFormGroup.value.name,
    "description": this.addStandardPhasesFormGroup.value.description,
    "sequence": this.addStandardPhasesFormGroup.value.sequence,
    "dependencyToStart": this.addStandardPhasesFormGroup.value.dependencyToStart,
    "typeOfWork": this.addStandardPhasesFormGroup.value.typeOfWork,
    "weightage": this.addStandardPhasesFormGroup.value.weightage,
    
  }
  this.sendAndRequestService.requestForPOST(Constants.app_urls.STANDARD_PHASES.SAVE_STANDARD_PHASES, savePhaseModel, false).subscribe(response => {
    this.spinnerService.hide();
    this.resp = response;
 
    if (this.resp.code == Constants.CODES.SUCCESS) {
      this.commonService.showAlertMessage("Standard Phase Data Saved Successfully");
      this.router.navigate(['../'], { relativeTo: this.route });
    } else {
      this.commonService.showAlertMessage("Standard Phase Saving Failed.");
    }
  }, error => {
    console.log('ERROR >>>');
    this.spinnerService.hide();
    this.commonService.showAlertMessage("Standard Phase Saving Failed.");
  });
}
else if (this.update) {
  var updateStandardPhasesModel = {
    "id": this.id,
    "name": this.addStandardPhasesFormGroup.value.name,
    "description": this.addStandardPhasesFormGroup.value.description,
    "sequence": this.addStandardPhasesFormGroup.value.sequence,
    "dependencyToStart": this.addStandardPhasesFormGroup.value.dependencyToStart,
    "typeOfWork": this.addStandardPhasesFormGroup.value.typeOfWork,
    "weightage": this.addStandardPhasesFormGroup.value.weightage,
  }
  this.sendAndRequestService.requestForPUT(Constants.app_urls.STANDARD_PHASES.UPDATE_STANDARD_PHASES, updateStandardPhasesModel, false).subscribe(response => {
    this.spinnerService.hide();
    this.resp = response;
    if (this.resp.code == Constants.CODES.SUCCESS) {
    this.commonService.showAlertMessage("Standard Phases Updated Successfully");
    this.router.navigate(['../'], { relativeTo: this.route });
    }else{
      this.commonService.showAlertMessage("Standard Phases Data Updating Failed.");
    }
  }, error => {
    console.log('ERROR >>>');
    this.spinnerService.hide();
    this.commonService.showAlertMessage("Standard Phases Data Updating Failed.");
  })

}
}

onGoBack() {
this.router.navigate(['../'], { relativeTo: this.route });
}
test() {

  console.log("get sequence");
  if(this.addStandardPhasesFormGroup.controls['sequence'].value > this.addStandardPhasesFormGroup.controls['dependencyToStart'].value)
  {
    console.log("get sequence in if condition");
    this.dependency = false;
  }else{
    console.log("get sequence in else condition");
    this.dependency = true;
  }

}
duplicateDescription() {
  const q = new Promise((resolve, reject) => {
     this.sendAndRequestService.requestForGET(
            Constants.app_urls.STANDARD_PHASES.EXISTS_DESCRIPTION_TYPE_OF_WORK +
          this.addStandardPhasesFormGroup.controls['description'].value + '/'+
          this.addStandardPhasesFormGroup.controls['typeOfWork'].value
    ).subscribe((duplicate) => {
      if (duplicate) {
        resolve({ 'duplicate': true });
      } else {
        resolve(null);
      }
    }, () => { resolve({ 'duplicate': true }); });
  });
  return q;
  }    
  duplicateDescriptionAndId() {
    let id=this.id;
    let description: string = this.addStandardPhasesFormGroup.controls['description'].value;
    let typeOfWork: string = this.addStandardPhasesFormGroup.controls['typeOfWork'].value;

    const q = new Promise((resolve, reject) => {          

       this.sendAndRequestService.requestForGET(
              Constants.app_urls.STANDARD_PHASES.EXISTS_DESCRIPTION_TYPE_OF_WORK_AND_ID+id+'/'+description+'/'+typeOfWork).subscribe
              ((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateDescriptionAndId': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateDescriptionAndId': true }); });
    });
    return q;
  }
  duplicateSequence() {
    const q = new Promise((resolve, reject) => {
       this.sendAndRequestService.requestForGET(
              Constants.app_urls.STANDARD_PHASES.EXISTS_SEQUENCE_TYPE_OF_WORK +
            this.addStandardPhasesFormGroup.controls['sequence'].value + '/'+
            this.addStandardPhasesFormGroup.controls['typeOfWork'].value
      ).subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicate': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicate': true }); });
    });
    return q;
    }    
    duplicateSequenceAndId() {
      let id=this.id;
      let sequence: string = this.addStandardPhasesFormGroup.controls['sequence'].value;
      let typeOfWork: string = this.addStandardPhasesFormGroup.controls['typeOfWork'].value;
  
      const q = new Promise((resolve, reject) => {          
  
         this.sendAndRequestService.requestForGET(
                Constants.app_urls.STANDARD_PHASES.EXISTS_SEQUENCE_TYPE_OF_WORK_AND_ID+id+'/'+sequence+'/'+typeOfWork).subscribe
                ((duplicate) => {
          if (duplicate) {
            resolve({ 'duplicateSequenceAndId': true });
          } else {
            resolve(null);
          }
        }, () => { resolve({ 'duplicateSequenceAndId': true }); });
      });
      return q;
    }
    duplicateName() {
      const q = new Promise((resolve, reject) => {
         this.sendAndRequestService.requestForGET(
                Constants.app_urls.STANDARD_PHASES.EXISTS_NAME_TYPE_OF_WORK +
              this.addStandardPhasesFormGroup.controls['name'].value + '/'+
              this.addStandardPhasesFormGroup.controls['typeOfWork'].value
        ).subscribe((duplicate) => {
          if (duplicate) {
            resolve({ 'duplicate': true });
          } else {
            resolve(null);
          }
        }, () => { resolve({ 'duplicate': true }); });
      });
      return q;
      }    
      duplicateNameAndId() {
        let id=this.id;
        let name: string = this.addStandardPhasesFormGroup.controls['name'].value;
        let typeOfWork: string = this.addStandardPhasesFormGroup.controls['typeOfWork'].value;
    
        const q = new Promise((resolve, reject) => {          
    
           this.sendAndRequestService.requestForGET(
                  Constants.app_urls.STANDARD_PHASES.EXISTS_NAME_TYPE_OF_WORK_AND_ID+id+'/'+name+'/'+typeOfWork).subscribe
                  ((duplicate) => {
            if (duplicate) {
              resolve({ 'duplicateNameAndId': true });
            } else {
              resolve(null);
            }
          }, () => { resolve({ 'duplicateNameAndId': true }); });
        });
        return q;
      }
  
}