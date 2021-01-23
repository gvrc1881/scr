import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';

@Component({
  selector: 'app-add-sector',
  templateUrl: './add-sector.component.html',
  styleUrls: []
})
export class AddSectorComponent implements OnInit {
 pagination =Constants.PAGINATION_NUMBERS;
 FiledLabels = FieldLabelsConstant.LABELS;
 Titles = FieldLabelsConstant.TITLE;
  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
  resp: any;
  lineCode:any;
  facilityData:any;
  title:string;
  addSectorFormGroup: FormGroup;
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
    this.findLineCodeDetails();
    this.id = +this.route.snapshot.params['id'];
    if (!isNaN(this.id)) {  
      this.updateSectorForm();   
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;
      this.getSectorDataById(this.id);
    } else {
      this.createSectorForm();
      this.save = true;
      this.update = false;
      this.title = Constants.EVENTS.ADD;
    }
    
  }
  
  

  createSectorForm() {
    this.addSectorFormGroup = this.formBuilder.group({
      id: 0,
      'facilityId':['', Validators.compose([Validators.required])],
      'sectorCode': ['', Validators.compose([Validators.required]),this.duplicateSectorCode.bind(this)],
      'fromLocation': [''],
      'fromLocationType':[''],
      'toLocation':[''],
      'toLocationType': [''],
      'division':[''],
      'line1':[''],
      'line2':[''],
    });
  }
  updateSectorForm() {
    this.addSectorFormGroup = this.formBuilder.group({
      id: 0,
      'facilityId':['', Validators.compose([Validators.required])],
      'sectorCode': ['', Validators.compose([Validators.required]),this.duplicateSectorCodeByID.bind(this)],
      'fromLocation': [''],
      'fromLocationType':[''],
      'toLocation':[''],
      'toLocationType': [''],
      'division':[''],
      'line1':[''],
      'line2':[''],
    });
  }
  duplicateSectorCode() {
    const q = new Promise((resolve, reject) => {
              
      let sectorCode: string = this.addSectorFormGroup.controls['sectorCode'].value;
      
     
      this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.SECTOR.EXIST_SECTOR_CODE+sectorCode)
      .subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateSectorCode': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateSectorCode': true }); });
    });
    return q;
  }
  duplicateSectorCodeByID() {
    const q = new Promise((resolve, reject) => {
      let id=this.id;              
      let sectorCode: string = this.addSectorFormGroup.controls['sectorCode'].value;
      this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.SECTOR.EXIST_SECTOR_CODE_ID+id+'/'+sectorCode)
      .subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateSectorCodeByID': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateSectorCodeByID': true }); });
    });
    return q;
  }
  getSectorDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.SECTOR.GET_SECTOR_ID+id)
    .subscribe((resp) => {
        this.resp = resp;
        this.addSectorFormGroup.patchValue({
          id: this.resp.id,
          facilityId: this.resp.facilityId,
          sectorCode: this.resp.sectorCode,
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
  
  SectorFormSubmit() {
    this.isSubmit = true;
    if (this.addSectorFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    if (this.save) {
      var saveSectorModel = {
        "facilityId": this.addSectorFormGroup.value.facilityId,
        "sectorCode": this.addSectorFormGroup.value.sectorCode,
        "fromLocation": this.addSectorFormGroup.value.fromLocation,
        "fromLocationType": this.addSectorFormGroup.value.fromLocationType,
        "toLocation": this.addSectorFormGroup.value.toLocation,
        "toLocationType": this.addSectorFormGroup.value.toLocationType,
        "division": this.addSectorFormGroup.value.division,
        "line1": this.addSectorFormGroup.value.line1,
        "line2": this.addSectorFormGroup.value.line2,  
        "createdStamp": new Date(),
        "createdTxStamp": new Date(),
        "lastUpdatedStamp": new Date(),
        "lastUpdatedTxStamp": new Date()

      }
      this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.SECTOR.SAVE_SECTOR, saveSectorModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
     
        if (this.resp.code == Constants.CODES.SUCCESS) {
          this.commonService.showAlertMessage("sector Data Saved Successfully");
          this.router.navigate(['../'], { relativeTo: this.route });
        } else {
          this.commonService.showAlertMessage("Sector Data Saving Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("sector Data Saving Failed.");
      });
    } else if (this.update) {
      var updateSectorModel = {
        "id": this.id,
        "facilityId": this.addSectorFormGroup.value.facilityId,
        "sectorCode": this.addSectorFormGroup.value.sectorCode,
        "fromLocation": this.addSectorFormGroup.value.fromLocation,
        "fromLocationType": this.addSectorFormGroup.value.fromLocationType,
        "toLocation": this.addSectorFormGroup.value.toLocation,
        "toLocationType": this.addSectorFormGroup.value.toLocationType,
        "division": this.addSectorFormGroup.value.division,
        "line1": this.addSectorFormGroup.value.line1,
        "line2": this.addSectorFormGroup.value.line2,                                          
        "lastUpdatedStamp": new Date(),
        "lastUpdatedTxStamp": new Date()
      }
      this.sendAndRequestService.requestForPUT(Constants.app_urls.ENERGY_BILL_PAYMENTS.SECTOR.UPDATE_SECTOR, updateSectorModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("sector Data Updated Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("sector Data Updating Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("sector Data Updating Failed.");
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
 findLineCodeDetails(){
  this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.SECTOR.GET_LINE_CODE).subscribe((data) => {
    this.lineCode = data;
  }
  );
  
  }
 public lineDetails=['UP','DN','UD','SDG'];
 
  }
  
  
  
  
















