import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';

@Component({
  selector: 'app-add-elementary-section',
  templateUrl: './add-elementary-section.component.html',
  styleUrls: []
})
export class AddElementarySectionComponent implements OnInit {
  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  resp: any;
  facilityData:any;
  sectorData:any;
  subSectorData:any;
  statSectData:any;
  lineCode:any;
  title:string;
  addElementarySectionFormGroup: FormGroup;
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
    this.findSubSector();
    this.findLineCodeDetails();
    this.findStationSectionDetails();
    this.id = +this.route.snapshot.params['id'];
    this.createElementarySectionForm();
    if (!isNaN(this.id)) { 
      this.updateElementarySectionForm();    
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = 'Edit';
      this.getElementarySectionDataById(this.id);
    } else {
      this.createElementarySectionForm();
      this.save = true;
      this.update = false;
      this.title = 'Save';
    }
    
  }
  
  

  createElementarySectionForm() {
    this.addElementarySectionFormGroup = this.formBuilder.group({
      id: 0,
      'elementarySectionCode':['', Validators.compose([Validators.required]),this.duplicateElementarySectionCode.bind(this)],
      'facilityId':['', Validators.compose([Validators.required])],
      'stationCode':[''],
      'trackCode':[''],
      'sidingMain':[''],
      'sectionCode':[''],
      'sectorCode': ['', Validators.compose([Validators.required])],
      'subSectorCode':['', Validators.compose([Validators.required])],
      'fromKm':[''],
      'fromSeq':[''],
      'toKm':[''],
      'toSeq':[''],
      'devisionId':[''],
      'protectionCrossover': [''],
      'protectionTurnout':[''],
      'remarksShunting':['', Validators.maxLength(255)],
      'remarksNo': ['', Validators.maxLength(255)],
      'isAutoDead':[''],
    });
  }
  updateElementarySectionForm() {
    this.addElementarySectionFormGroup = this.formBuilder.group({
      id: 0,
      'elementarySectionCode':['', Validators.compose([Validators.required]),this.duplicateElementarySectionCodeByID.bind(this)],
      'facilityId':['', Validators.compose([Validators.required])],
      'stationCode':[''],
      'trackCode':[''],
      'sidingMain':[''],
      'sectionCode':[''],
      'sectorCode': ['', Validators.compose([Validators.required])],
      'subSectorCode':['', Validators.compose([Validators.required])],
      'fromKm':[''],
      'fromSeq':[''],
      'toKm':[''],
      'toSeq':[''],
      'devisionId':[''],
      'protectionCrossover': [''],
      'protectionTurnout':[''],
      'remarksShunting':['', Validators.maxLength(255)],
      'remarksNo': ['', Validators.maxLength(255)],
      'isAutoDead':[''],
    });
  }
  duplicateElementarySectionCode() {
    const q = new Promise((resolve, reject) => {
              
      let elementarySectionCode: string = this.addElementarySectionFormGroup.controls['elementarySectionCode'].value;
      
     
      this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ELEMENTARYSECTIONS.EXIST_ELEMENTARY_SECTION_CODE+elementarySectionCode)
      .subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateElementarySectionCode': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateElementarySectionCode': true }); });
    });
    return q;
  }
  duplicateElementarySectionCodeByID() {
    const q = new Promise((resolve, reject) => {
      let id=this.id;              
      let elementarySectionCode: string = this.addElementarySectionFormGroup.controls['elementarySectionCode'].value;
      this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ELEMENTARYSECTIONS.EXIST_ELEMENTARY_SECTION_CODE_ID+id+'/'+elementarySectionCode)
      .subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateElementarySectionCodeByID': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateElementarySectionCodeByID': true }); });
    });
    return q;
  }
  getElementarySectionDataById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.ELEMENTARYSECTIONS.GET_ELEMENTARY_SECTIONS_ID+id)
    .subscribe((resp) => {
        this.resp = resp;
        this.addElementarySectionFormGroup.patchValue({
          id: this.resp.id,
          elementarySectionCode: this.resp.elementarySectionCode,
          facilityId: this.resp.facilityId,
          stationCode:this.resp.stationCode,
          trackCode: this.resp.trackCode,
          sidingMain: this.resp.sidingMain,
          sectionCode: this.resp.sectionCode,
          sectorCode: this.resp.sectorCode,
          subSectorCode: this.resp.subSectorCode,
          fromKm: this.resp.fromKm,
          fromSeq: this.resp.fromSeq,
          toKm: this.resp.toKm,
          toSeq: this.resp.toSeq,
          devisionId: this.resp.devisionId,
          protectionCrossover: this.resp.protectionCrossover,
          protectionTurnout: this.resp.protectionTurnout,
          remarksShunting: this.resp.remarksShunting,
          remarksNo: this.resp.remarksNo,
          isAutoDead: this.resp.isAutoDead,

        });
        this.spinnerService.hide();
      })
  }
  
  ElementarySectionFormFormSubmit() {
    this.isSubmit = true;
    if (this.addElementarySectionFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    if (this.save) {
      var saveElementarySectionModel = {
        "elementarySectionCode": this.addElementarySectionFormGroup.value.elementarySectionCode,
        "facilityId": this.addElementarySectionFormGroup.value.facilityId,
        "stationCode":this.addElementarySectionFormGroup.value.stationCode,
        "trackCode": this.addElementarySectionFormGroup.value.trackCode,
        "sidingMain": this.addElementarySectionFormGroup.value.sidingMain,
        "sectionCode": this.addElementarySectionFormGroup.value.sectionCode,
        "sectorCode": this.addElementarySectionFormGroup.value.sectorCode,
        "subSectorCode": this.addElementarySectionFormGroup.value.subSectorCode,
        "fromKm": this.addElementarySectionFormGroup.value.fromKm,
        "fromSeq": this.addElementarySectionFormGroup.value.fromSeq,
        "toKm": this.addElementarySectionFormGroup.value.toKm,  
        "toSeq": this.addElementarySectionFormGroup.value.toSeq,  
        "devisionId": this.addElementarySectionFormGroup.value.devisionId,  
        "protectionCrossover": this.addElementarySectionFormGroup.value.protectionCrossover,  
        "protectionTurnout": this.addElementarySectionFormGroup.value.protectionTurnout,  
        "remarksShunting": this.addElementarySectionFormGroup.value.remarksShunting,  
        "isAutoDead": this.addElementarySectionFormGroup.value.isAutoDead,  
        "createdStamp": new Date(),
        "createdTxStamp": new Date(),
        "lastUpdatedStamp": new Date(),
        "lastUpdatedTxStamp": new Date()

      }
      this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.ELEMENTARYSECTIONS.SAVE_ELEMENTARY_SECTIONS, saveElementarySectionModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
     
        if (this.resp.code == Constants.CODES.SUCCESS) {
          this.commonService.showAlertMessage("Elementary SectionData Saved Successfully");
          this.router.navigate(['../'], { relativeTo: this.route });
        } else {
          this.commonService.showAlertMessage("Elementary SectionData Saving Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Elementary SectionData Saving Failed.");
      });
    } else if (this.update) {
      var updateElementarySectionModel = {
        "id": this.id,
        "elementarySectionCode": this.addElementarySectionFormGroup.value.elementarySectionCode,
        "facilityId": this.addElementarySectionFormGroup.value.facilityId,
        "stationCode":this.addElementarySectionFormGroup.value.stationCode,
        "trackCode": this.addElementarySectionFormGroup.value.trackCode,
        "sidingMain": this.addElementarySectionFormGroup.value.sidingMain,
        "sectionCode": this.addElementarySectionFormGroup.value.sectionCode,
        "sectorCode": this.addElementarySectionFormGroup.value.sectorCode,
        "subSectorCode": this.addElementarySectionFormGroup.value.subSectorCode,
        "fromKm": this.addElementarySectionFormGroup.value.fromKm,
        "fromSeq": this.addElementarySectionFormGroup.value.fromSeq,
        "toKm": this.addElementarySectionFormGroup.value.toKm,  
        "toSeq": this.addElementarySectionFormGroup.value.toSeq,  
        "devisionId": this.addElementarySectionFormGroup.value.devisionId,  
        "protectionCrossover": this.addElementarySectionFormGroup.value.protectionCrossover,  
        "protectionTurnout": this.addElementarySectionFormGroup.value.protectionTurnout,  
        "remarksShunting": this.addElementarySectionFormGroup.value.remarksShunting,  
        "isAutoDead": this.addElementarySectionFormGroup.value.isAutoDead,                                         
        "lastUpdatedStamp": new Date(),
        "lastUpdatedTxStamp": new Date()
      }
      this.sendAndRequestService.requestForPUT(Constants.app_urls.ENERGY_BILL_PAYMENTS.ELEMENTARYSECTIONS.UPDATE_ELEMENTARY_SECTIONS, updateElementarySectionModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Elementary Section Data Updated Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Elementary Section Data Updating Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("ElementarySection Data Updating Failed.");
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
findSubSector(){

  this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.SUBSECTOR.GET_SUB_SECTOR).subscribe((data) => {
  this.subSectorData = data;
}
);

}
findStationSectionDetails(){
  this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.STATION_SECTIONS.GET_STATION_SECTIONS).subscribe((data) => {
    this.statSectData = data;
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
  
  
  
  
















