import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';


@Component({
  selector: 'app-add-tpc-boards',
  templateUrl: './add-tpc-boards.component.html',
  
})
export class AddTpcBoardsComponent implements OnInit {

  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
  divisionData:any = JSON.parse(sessionStorage.getItem('divisionData'));
  resp: any;
  title:string = Constants.EVENTS.ADD;
  tpcBoardsFormErrors:any;
  addTpcBoardsFormGroup: FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  selectedWork: any;
  facilityData:any;
  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private sendAndRequestService:SendAndRequestService
  ) {
    this.tpcBoardsFormErrors = {            
      precautionaryMeasure: {},
      doneBy:{},
    };
  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
    if (!isNaN(this.id)) {  
      this.updateTpcBoardsForm();  
      this.addTpcBoardsFormGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      }); 
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;
       this.getTpcBoardsDataById(this.id);
    } else {
      this.createTpcBoardsForm();
      this.save = true;
      this.update = false;
      this.title = Constants.EVENTS.ADD;
    }
  }
  
  onFormValuesChanged() {
    for (const field in this.tpcBoardsFormErrors) {
      if (!this.tpcBoardsFormErrors.hasOwnProperty(field)) {
        continue;
      }
      this.tpcBoardsFormErrors[field] = {};
      const control = this.addTpcBoardsFormGroup.get(field);
      if (control && control.dirty && !control.valid) {
        this.tpcBoardsFormErrors[field] = control.errors;
      }
    }
  }
  createTpcBoardsForm() {
    this.addTpcBoardsFormGroup = this.formBuilder.group({
      id: 0,
      'tpcBoard':[null, Validators.compose([Validators.required, Validators.maxLength(250)])],
      'dataDiv':[null,Validators.required, this.duplicateTpcBoard.bind(this)],
      'description':[null,Validators.maxLength(250)]
      
    });
  }
  updateTpcBoardsForm() {
    this.addTpcBoardsFormGroup = this.formBuilder.group({
      id: 0,
      'tpcBoard':[null, Validators.compose([Validators.required, Validators.maxLength(250)])],
      'dataDiv':[null,Validators.required, this.duplicateTpcBoardDataDivAndId.bind(this)],
      'description':[null,Validators.maxLength(250)]
      
    });
  }
  

   public get f() { return this.addTpcBoardsFormGroup.controls; } 

  
  getTpcBoardsDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.TPC_BOARD.GET_TPC_BOARD_ID+id)
    .subscribe((resp) => {
        this.resp = resp;
        this.addTpcBoardsFormGroup.patchValue({
          id: this.resp.id,
          tpcBoard: this.resp.tpcBoard,
          dataDiv: this.resp.dataDiv,
          description: this.resp.description,
        });
        this.spinnerService.hide();
      })

    }
  onAddTpcBoardsFormSubmit() {
    this.isSubmit = true;
    if (this.addTpcBoardsFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    if (this.save) {
      var saveTpcBoardsModel = {
        "tpcBoard": this.addTpcBoardsFormGroup.value.tpcBoard,
        "dataDiv": this.addTpcBoardsFormGroup.value.dataDiv,
        "description": this.addTpcBoardsFormGroup.value.description,
        "createdStamp": new Date(),
        "createdTxStamp": new Date(),
        "lastUpdatedStamp": new Date(),
        "lastUpdatedTxStamp": new Date()
        
      }
      this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.TPC_BOARD.SAVE_TPC_BOARD, saveTpcBoardsModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
     
        if (this.resp.code == Constants.CODES.SUCCESS) {
          this.commonService.showAlertMessage("Tpc Board  Data Saved Successfully");
          this.router.navigate(['../'], { relativeTo: this.route });
        } else {
          this.commonService.showAlertMessage("Tpc Board  Data Saving Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Tpc Board  Data Saving Failed.");
      });
    } else if (this.update) {
      var updateTpcBoardsModel = {
        "id": this.id,
        "tpcBoard": this.addTpcBoardsFormGroup.value.tpcBoard,
        "dataDiv": this.addTpcBoardsFormGroup.value.dataDiv,
        "description": this.addTpcBoardsFormGroup.value.description,
        "lastUpdatedStamp": new Date(),
        "lastUpdatedTxStamp": new Date()
      }
      this.sendAndRequestService.requestForPUT(Constants.app_urls.ENERGY_BILL_PAYMENTS.TPC_BOARD.UPDATE_TPC_BOARD, updateTpcBoardsModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Tpc Board  Data Updated Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Tpc Board  Data  Updating Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Tpc Board  Data Updating Failed.");
      })

    }
  }

  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }
  
  
  duplicateTpcBoard() {
    const q = new Promise((resolve, reject) => {
      this.sendAndRequestService.requestForGET(
        Constants.app_urls.ENERGY_BILL_PAYMENTS.TPC_BOARD.EXISTS_TPC_BOARD_DATADIV +
           this.addTpcBoardsFormGroup.controls['tpcBoard'].value + '/'+
           this.addTpcBoardsFormGroup.controls['dataDiv'].value
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
    duplicateTpcBoardDataDivAndId() {
      let id=this.id;
      let tpcBoard: string = this.addTpcBoardsFormGroup.controls['tpcBoard'].value;
      let dataDiv: string = this.addTpcBoardsFormGroup.controls['dataDiv'].value;

      const q = new Promise((resolve, reject) => {          

         this.sendAndRequestService.requestForGET(
                Constants.app_urls.ENERGY_BILL_PAYMENTS.TPC_BOARD.EXISTS_TPC_BOARD_DATADIV_AND_ID+id+'/'+tpcBoard+'/'+dataDiv).subscribe
                ((duplicate) => {
          if (duplicate) {
            resolve({ 'duplicateTpcBoardDataDivAndId': true });
          } else {
            resolve(null);
          }
        }, () => { resolve({ 'duplicateTpcBoardDataDivAndId': true }); });
      });
      return q;
    } 
}