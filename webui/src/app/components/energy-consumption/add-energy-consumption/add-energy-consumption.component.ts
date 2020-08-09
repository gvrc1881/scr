import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';

@Component({
  selector: 'app-add-energy-consumption',
  templateUrl: './add-energy-consumption.component.html',
  styleUrls: ['./add-energy-consumption.component.css']
})
export class AddEnergyConsumptionComponent implements OnInit {

  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  save: boolean = true;
  update: boolean = false;
  disabled:boolean=true;
  id: number = 0;
  isSubmit: boolean = false;
  title:string;
  relayIndicationList = [];
  natureOfCloseList = [];
  addEnergyConsumptionFailFromGroup: FormGroup;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  stateList = [{ 'id': 1, "value": 'Yes' }, { 'id': 2, "value": 'No' }];
  driveList = [];
  reportedList=[];
  energyConsumptionFailFormErrors: any;
  feedersList:any;
  extendedFromList:any=[];
  resp: any;
  reportDescriptionFlag=false;
  maxDate = new Date();
  minDate=new Date();
  dateFormat = 'MM-dd-yyyy HH:MM:SS';
  divisionList:any;
  failureList:any;
  failurecasList:any;
  difference:any;
  constructor(
    private formBuilder: FormBuilder,    
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private route: ActivatedRoute,
    private router: Router,
    private sendAndRequestService: SendAndRequestService
  ) {
    // Reactive form errors
    this.energyConsumptionFailFormErrors = {
      Feeder_Name:{},
      Multification_Factor:{},
      Joint_Reading:{},
      CMD:{},
      Old_KWH:{},
      Current_KWH:{},
      Consumption_KWH:{},
      Old_KVAH:{},
      Current_KVAH:{},
      Consumption_KVAH:{},
      Old_RKVAH_Lag:{},
      Current_RKVAH_Lag:{},
      Consumption_RKVAH_Lag:{},
      Old_RKVAH_Lead:{},
      Current_RKVAH_Lead:{},
      Consumption_RKVAH_Lead:{},
      PF:{},
      CPF:{},
      RMD:{},
      Vol_Max:{},
      Vol_Min:{},
      Max_Load:{}
    };
  }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];    
    this.createForm();
    if (!isNaN(this.id)) {
      this.addEnergyConsumptionFailFromGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      });
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = 'Edit';
      this.findEnergyConsumptionById(this.id);
    } else {
      this.save = true;
      this.update = false;
      this.title = 'Save';
    }
    
  }


  createForm() {
    this.addEnergyConsumptionFailFromGroup
      = this.formBuilder.group({
        id: 0,
        'Feeder_Name':[null],
        'Multification_Factor':[null],
        'Joint_Reading':[null],
        'CMD':[null],
        'Old_KWH':[null],
        'Current_KWH':[null],
        'Consumption_KWH':[null],
        'Old_KVAH':[null],
        'Current_KVAH':[null],
        'Consumption_KVAH':[null],
        'Old_RKVAH_Lag':[null],
        'Current_RKVAH_Lag':[null],
        'Consumption_RKVAH_Lag':[null],
        'Old_RKVAH_Lead':[null],
        'Current_RKVAH_Lead':[null],
        'Consumption_RKVAH_Lead':[null],
        'PF':[null],
        'CPF':[null],
        'RMD':[null],
        'Vol_Max':[null],
        'Vol_Min':[null],
        'Max_Load':[null]
      });
  }

  onFormValuesChanged() {
    for (const field in this.energyConsumptionFailFormErrors) {
      if (!this.energyConsumptionFailFormErrors.hasOwnProperty(field)) {
        continue;
      }
      this.energyConsumptionFailFormErrors[field] = {};
      const control = this.addEnergyConsumptionFailFromGroup.get(field);
      if (control && control.dirty && !control.valid) {
        this.energyConsumptionFailFormErrors[field] = control.errors;
      }
    }
  }
 
  updateFeedOff($event){
    if ($event.value) {
      console.log($event.value)
      this.extendedFromList = [];
      //this.reportDescriptionFlag = $event.value == Constants.YES ? true : false;
      this.feedersList.map(element => {
        if(element.feederName != $event.value){
          this.extendedFromList.push(element);
        }
      });
    }
  }
  findEnergyConsumptionById(id) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.FAILURES.FAILURE_TYPE_BY_ID+id)
      .subscribe((resp) => {
        this.resp = resp;
        console.log(this.resp);
        this.addEnergyConsumptionFailFromGroup.patchValue({
          id: this.resp.id,
          Feeder_Name:this.resp.Feeder_Name,
          Multification_Factor:this.resp.Multification_Factor,
          Joint_Reading:this.resp.Joint_Reading,
          CMD:this.resp.CMD,
          Old_KWH:this.resp.Old_KWH,
          Current_KWH:this.resp.Current_KWH,
          Consumption_KWH:this.resp.Consumption_KWH,
          Old_KVAH:this.resp.Old_KVAH,
          Current_KVAH:this.resp.Current_KVAH,
          Consumption_KVAH:this.resp.Consumption_KVAH,
          Old_RKVAH_Lag:this.resp.Old_RKVAH_Lag,
          Current_RKVAH_Lag:this.resp.Current_RKVAH_Lag,
          Consumption_RKVAH_Lag:this.resp.Consumption_RKVAH_Lag,
          Old_RKVAH_Lead:this.resp.Old_RKVAH_Lead,
          Current_RKVAH_Lead:this.resp.Current_RKVAH_Lead,
          Consumption_RKVAH_Lead:this.resp.Consumption_RKVAH_Lead,
          PF:this.resp.PF,
          CPF:this.resp.CPF,
          RMD:this.resp.RMD,
          Vol_Max:this.resp.Vol_Max,
          Vol_Min:this.resp.Vol_Min,
          Max_Load:this.resp.Max_Load
        });
        this.spinnerService.hide();
      })
  }
  

  addEvent($event) {
    this.minDate = new Date($event.value);
  }
  addEventTargetDate($event) {
    this.minDate = new Date($event.value);
  }

  onAddEnergyConsumptionFormSubmit() {
    if (this.addEnergyConsumptionFailFromGroup.invalid) {
      this.isSubmit = false;
      return;
    }
    this.spinnerService.show();
    var data = {};
    var message = '';
    var failedMessage = '';
  
      let casc=this.addEnergyConsumptionFailFromGroup.value.cascadeAssets;
      data = {
        "id":this.id,
        'Feeder_Name':this.addEnergyConsumptionFailFromGroup.value.Feeder_Name,
        'Multification_Factor':this.addEnergyConsumptionFailFromGroup.value.Multification_Factor,
        'Joint_Reading':this.addEnergyConsumptionFailFromGroup.value.Joint_Reading,
        'CMD':this.addEnergyConsumptionFailFromGroup.value.CMD,
        'Old_KWH':this.addEnergyConsumptionFailFromGroup.value.Old_KWH,
        'Current_KWH':this.addEnergyConsumptionFailFromGroup.value.Current_KWH,
        'Consumption_KWH':this.addEnergyConsumptionFailFromGroup.value.Consumption_KWH,
        'Old_KVAH':this.addEnergyConsumptionFailFromGroup.value.Old_KVAH,
        'Current_KVAH':this.addEnergyConsumptionFailFromGroup.value.Current_KVAH,
        'Consumption_KVAH':this.addEnergyConsumptionFailFromGroup.value.Consumption_KVAH,
        'Old_RKVAH_Lag':this.addEnergyConsumptionFailFromGroup.value.Old_RKVAH_Lag,
        'Current_RKVAH_Lag':this.addEnergyConsumptionFailFromGroup.value.Current_RKVAH_Lag,
        'Consumption_RKVAH_Lag':this.addEnergyConsumptionFailFromGroup.value.Consumption_RKVAH_Lag,
        'Old_RKVAH_Lead':this.addEnergyConsumptionFailFromGroup.value.Old_RKVAH_Lead,
        'Current_RKVAH_Lead':this.addEnergyConsumptionFailFromGroup.value.Current_RKVAH_Lead,
        'Consumption_RKVAH_Lead':this.addEnergyConsumptionFailFromGroup.value.Consumption_RKVAH_Lead,
        'PF':this.addEnergyConsumptionFailFromGroup.value.PF,
        'CPF':this.addEnergyConsumptionFailFromGroup.value.CPF,
        'RMD':this.addEnergyConsumptionFailFromGroup.value.RMD,
        'Vol_Max':this.addEnergyConsumptionFailFromGroup.value.Vol_Max,
        'Vol_Min':this.addEnergyConsumptionFailFromGroup.value.Vol_Min,
        'Max_Load':this.addEnergyConsumptionFailFromGroup.value.Max_Load,
        "updatedBy": this.loggedUserData.username,
        "updatedOn": new Date()
      }   
      message = 'Updated';
      failedMessage = "Updating";
      this.sendAndRequestService.requestForPOST(Constants.app_urls.FAILURES.FAILURE_TYPE_UPDATE,data, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("energyConsumption Fail Data "+message+" Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("energyConsumption Fail Data "+failedMessage+" Failed."); 
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("energyConsumption Fail Data "+failedMessage+" Failed.");
      })
    
    
  }
  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }

}
