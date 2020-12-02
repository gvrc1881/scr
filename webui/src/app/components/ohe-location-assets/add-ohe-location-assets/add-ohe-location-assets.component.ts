import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { MatDialogRef,MatDialog } from '@angular/material';


@Component({
  selector: 'app-add-ohe-location-assets',
  templateUrl: './add-ohe-location-assets.component.html',
  
})
export class AddOheLocationAssetsComponent implements OnInit {

  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  save: boolean = true;
  update: boolean = false;
  id: number = 0;
  isSubmit: boolean = false;
  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  resp: any;
  assetType: any;
  assetTypeData:any;
  workList:any;
  workData:any;
  facilityData:any;
  title:string = Constants.EVENTS.ADD;
  addOheLocationAndAssetsFormGroup: FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private sendAndRequestService:SendAndRequestService,
    public dialog: MatDialog,

  ) {
   
  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
    this.oheAssetTypes();
    this.depotTypeForOhe();
    if (!isNaN(this.id)) {  
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;
    } else {
      this.createOheLocationAndAssetsForm();
      this.save = true;
      this.update = false;
      this.title = Constants.EVENTS.ADD;
    }
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WORK).subscribe((data) => {
      this.workList = data;
       console.log('*** length ***'+this.workList.length);
  },error => {} );
  }
  
  
  createOheLocationAndAssetsForm() {
    this.addOheLocationAndAssetsFormGroup = this.formBuilder.group({
      id: 0,
      'project': [null],
      'workGroup': [null],
      'section': [null],
      'agency': [null],
      'division': [null],
      'facilityId': [null],
      'assetId': [null],
      'kilometer': [null],
      'sequenceNo': [null],
      'foundation': [null],
      'oheMast': [null],
      'line': [null],
      'trackLine': [null],
      'siding': [null],
      'station': [null],
      'yard': [null],
      'assetType':[null]
      
    });
  }
  
  

   public get f() { return this.addOheLocationAndAssetsFormGroup.controls; } 

 
  
  
  
  
  onAddProductCateMemberFormSubmit() {
    console.log("assetId"+this.addOheLocationAndAssetsFormGroup.value.assetId)
    this.isSubmit = true;
    if (this.addOheLocationAndAssetsFormGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    if (this.save) {
      var saveOLAModel = {
        "project": this.addOheLocationAndAssetsFormGroup.value.project.workName,
        "section": this.addOheLocationAndAssetsFormGroup.value.section,
        "division": this.addOheLocationAndAssetsFormGroup.value.division,
        "facilityId": this.addOheLocationAndAssetsFormGroup.value.facilityId,
        "assetId": this.addOheLocationAndAssetsFormGroup.value.assetId,
        "kilometer":this.addOheLocationAndAssetsFormGroup.value.kilometer,
        "sequenceNo": this.addOheLocationAndAssetsFormGroup.value.sequenceNo,
        "foundation": this.addOheLocationAndAssetsFormGroup.value.foundation,
        "oheMast": this.addOheLocationAndAssetsFormGroup.value.oheMast,
        "line": this.addOheLocationAndAssetsFormGroup.value.line,
        "trackLine": this.addOheLocationAndAssetsFormGroup.value.trackLine,
        "station": this.addOheLocationAndAssetsFormGroup.value.station,
        "assetType": this.addOheLocationAndAssetsFormGroup.value.assetType, 
          
        
      }
        this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.OHELOCATION.SAVE_OHE_LOCATION_AND_ASSETS, saveOLAModel, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        
        if(this.resp.code == 200 && !!this.resp) {
          this.commonService.showAlertMessage(this.resp.message);
          this.router.navigate(['../'], { relativeTo: this.route });
          this.addOheLocationAndAssetsFormGroup.reset();
      }else {
          this.commonService.showAlertMessage("Ohe Location And Assets Data Saving Failed.");
      }
      this.spinnerService.hide();
  } , error => {
      console.log('ERROR >>>');
      this.spinnerService.hide();
      this.commonService.showAlertMessage("Ohe Location And Assets Data Saving Failed.");
  });
}
  }

  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }
  getWorkGroup(){
    var work = this.addOheLocationAndAssetsFormGroup.value.project ;
  this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WORK_GROUPS_BASED_ON_WORK+work.id).subscribe((data) => {
             this.workData = data;
             console.log("workGroupData"+JSON.stringify(data))
             console.log("workGroupDataLength"+this.workData.length)

        });
}
oheAssetTypes(){
  this.assetType = 'OHE_FIXED_ASSET';
  this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_ASSET_TYPES + this.assetType).subscribe((data) => {
    this.assetTypeData = data;
});

}
depotTypeForOhe()
        {  
               this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_DEPOTTYPE_FOR_OHE).subscribe((data) => {
                 this.facilityData = data;
        }
               );

       }
assetTypeSelection(){
  var assetType = this.addOheLocationAndAssetsFormGroup.value.assetType ;
  this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
    disableClose: false
  });
  this.confirmDialogRef.componentInstance.confirmMessage = 'AssetTypes Selected';
}
}
