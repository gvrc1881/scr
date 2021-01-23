import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';

@Component({
  selector: 'app-add-sub-sector',
  templateUrl: './add-sub-sector.component.html',
  styleUrls: []
})
export class AddSubSectorComponent implements OnInit {
  pagination =Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;

  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
  resp: any;
  facilityData:any;
  sectorData:any;
  lineCode:any;
  title:string;
  productCateData:any;
  productCateTypeData:any;
  addSubSectorFormGroup: FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  constructor(
    private formBuilder: FormBuilder,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private route: ActivatedRoute,
    private router: Router,
    private sendAndRequestService:SendAndRequestService
  ) {
    
  }

  ngOnInit() {
    this.depotTypeForOhe();
    this.findSector();
    this.findLineCodeDetails();
    this.id = +this.route.snapshot.params['id'];
    if (!isNaN(this.id)) { 
      this.updateSubSectorForm();    
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;
      this.getSubSectorDataById(this.id);
    } else {
      this.createSubSectorForm();
      this.save = true;
      this.update = false;
      this.title = Constants.EVENTS.ADD;
    }
    
  }
  
  

  createSubSectorForm() {
    this.addSubSectorFormGroup = this.formBuilder.group({
      id: 0,
      'facilityId':['', Validators.compose([Validators.required])],
      'sector': ['', Validators.compose([Validators.required])],
      'subSectorCode':['', Validators.compose([Validators.required]),this.duplicateSubSectorCode.bind(this)],
      'fromLocation': [''],
      'fromLocationType':[''],
      'toLocation':[''],
      'toLocationType': [''],
      'division':[''],
      'line1':[''],
      'line2':[''],
    });
  }
  updateSubSectorForm() {
    this.addSubSectorFormGroup = this.formBuilder.group({
      id: 0,
      'facilityId':['', Validators.compose([Validators.required])],
      'sector': ['', Validators.compose([Validators.required])],
      'subSectorCode':['', Validators.compose([Validators.required]),this.duplicateSubSectorCodeByID.bind(this)],
      'fromLocation': [''],
      'fromLocationType':[''],
      'toLocation':[''],
      'toLocationType': [''],
      'division':[''],
      'line1':[''],
      'line2':[''],
    });
  }
  duplicateSubSectorCode() {
    const q = new Promise((resolve, reject) => {
              
      let subSectorCode: string = this.addSubSectorFormGroup.controls['subSectorCode'].value;
      
     
      this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.SUBSECTOR.EXIST_SUB_SECTOR_CODE+subSectorCode)
      .subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateSubSectorCode': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateSubSectorCode': true }); });
    });
    return q;
  }
  duplicateSubSectorCodeByID() {
    const q = new Promise((resolve, reject) => {
      let id=this.id;              
      let subSectorCode: string = this.addSubSectorFormGroup.controls['subSectorCode'].value;
      this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.SUBSECTOR.EXIST_SUB_SECTOR_CODE_ID+id+'/'+subSectorCode)
      .subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateSubSectorCodeByID': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateSubSectorCodeByID': true }); });
    });
    return q;
  }
  getSubSectorDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.SUBSECTOR.GET_SUB_SECTOR_ID+id)
    .subscribe((resp) => {
        this.resp = resp;
        this.addSubSectorFormGroup.patchValue({
          id: this.resp.id,
          facilityId: this.resp.facilityId,
          sector: this.resp.sector,
          subSectorCode:this.resp.subSectorCode,
          fromLocation: this.resp.fromLocation,
          fromLocationType: this.resp.fromLocationType,
          toLocation: this.resp.toLocation,
          toLocationType: this.resp.toLocationType,
          division: this.resp.division,
          line1: this.resp.line1,
          line2: this.resp.line2,
        });
        this.spinnerService.hide();
      })
  }
  
  SubSectorFormSubmit() {
    this.isSubmit = true;
    if (this.addSubSectorFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    if (this.save) {
      var saveSubSectorModel = {
        "facilityId": this.addSubSectorFormGroup.value.facilityId,
        "sector": this.addSubSectorFormGroup.value.sector,
        "subSectorCode":this.addSubSectorFormGroup.value.subSectorCode,
        "fromLocation": this.addSubSectorFormGroup.value.fromLocation,
        "fromLocationType": this.addSubSectorFormGroup.value.fromLocationType,
        "toLocation": this.addSubSectorFormGroup.value.toLocation,
        "toLocationType": this.addSubSectorFormGroup.value.toLocationType,
        "division": this.addSubSectorFormGroup.value.division,
        "line1": this.addSubSectorFormGroup.value.line1,
        "line2": this.addSubSectorFormGroup.value.line2,  
        "createdStamp": new Date(),
        "createdTxStamp": new Date(),
        "lastUpdatedStamp": new Date(),
        "lastUpdatedTxStamp": new Date()

      }
      this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.SUBSECTOR.SAVE_SUB_SECTOR, saveSubSectorModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
     
        if (this.resp.code == Constants.CODES.SUCCESS) {
          this.commonService.showAlertMessage("sub sector Data Saved Successfully");
          this.router.navigate(['../'], { relativeTo: this.route });
        } else {
          this.commonService.showAlertMessage("sub sector Data Saving Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Sub sector Data Saving Failed.");
      });
    } else if (this.update) {
      var updateSubSectorModel = {
        "id": this.id,
        "facilityId": this.addSubSectorFormGroup.value.facilityId,
        "sector": this.addSubSectorFormGroup.value.sector,
        "subSectorCode":this.addSubSectorFormGroup.value.subSectorCode,
        "fromLocation": this.addSubSectorFormGroup.value.fromLocation,
        "fromLocationType": this.addSubSectorFormGroup.value.fromLocationType,
        "toLocation": this.addSubSectorFormGroup.value.toLocation,
        "toLocationType": this.addSubSectorFormGroup.value.toLocationType,
        "division": this.addSubSectorFormGroup.value.division,
        "line1": this.addSubSectorFormGroup.value.line1,
        "line2": this.addSubSectorFormGroup.value.line2,                                          
        "lastUpdatedStamp": new Date(),
        "lastUpdatedTxStamp": new Date()
      }
      this.sendAndRequestService.requestForPUT(Constants.app_urls.ENERGY_BILL_PAYMENTS.SUBSECTOR.UPDATE_SUB_SECTOR, updateSubSectorModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("sub sector Data Updated Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("sub sector Data Updating Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("sub sector Data Updating Failed.");
      })

    }
  }

  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }
  depotTypeForOhe()
  {  
         this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_DEPOTTYPE_FOR_OHE).subscribe((data) => {
           this.facilityData = data;
  }
         );

 }
 findSector(){

    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.SECTOR.GET_SECTOR).subscribe((data) => {
    this.sectorData = data;
}
  );

}
findLineCodeDetails(){
  this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.SECTOR.GET_LINE_CODE).subscribe((data) => {
    this.lineCode = data;
  }
  );
  
  }
  public lineDetails=['UP','DN','UD','SDG'];

}