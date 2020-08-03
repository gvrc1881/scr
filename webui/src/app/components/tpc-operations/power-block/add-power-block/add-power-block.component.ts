import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FacilityModel } from 'src/app/models/facility.model';

@Component({
  selector: 'app-add-power-block',
  templateUrl: './add-power-block.component.html',
  styleUrls: []
})
export class AddPowerBlockComponent implements OnInit {

	save: boolean = true;
  	update: boolean = false;
  	resp: any;
  	id: number = 0;
  	title:string;
	addPowerBlockFormGroup: FormGroup;
	reqnBy: string[] = ['None' , 'Traffic Block with Tower Car' , 'Traffic Block with Ladder Trolly' ];
	reqDepartment: string[] = ['CONSTRUCTION' , 'ENGINEERING', 'OHE' , 'PSI','PQRS' , 'RVNL', 'OTHERS'];
	purpose: string[] = ["SCL's AOH" , "SCL's POH" , "MCL's AOH" , "MCL's POH", "ATD AOH" , "ATD POH" , "PTFE NEURTAL SECTION", 
						"TURNOUTS" , "CROSSOVERS" , "SECTION INSULATORS" , "OVERLAPS" , "ISOLATOR" , "GANTRY" , "OTHERS" ]
	facilityList: FacilityModel[]  = [];
	PBSections: any[] = [];
	section: any;
	maxDate=new Date(); 

	constructor(
	    private formBuilder: FormBuilder,
	    private spinnerService: Ng4LoadingSpinnerService,
	    private commonService: CommonService,
	    private route: ActivatedRoute,
	    private router: Router,
	    private sendAndRequestService:SendAndRequestService
	  ) {
	    // Reactive form errors
	   /* this.checkListFormErrors = {
	      drive: {},
	      measureActivityList: {},
	      displayOrder: {},
	      lowerLimit: {},
	      upperLimit: {},
	      status: {}
	    }; */
  	}
  	
  	ngOnInit() {
  		this.id = +this.route.snapshot.params['id'];
  		this.createPowerBlockForm();
  		this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY_BASED_ON_DEPOTTYPE + 'OHE').subscribe((data) => {
	      this.facilityList = data;
	      }, error => {
		      this.spinnerService.hide();
		    });
		 if (!isNaN(this.id)) {     
		      this.spinnerService.show();
		      this.save = false;
		      this.update = true;
		      this.title = 'Edit';
		       this.getPowerBlockData(this.id);
		    } else {
		      this.save = true;
		      this.update = false;
		      this.title = 'Save';
		    }
		this.findPBSections();        
  	}
  	
  	createPowerBlockForm() {
  		this.addPowerBlockFormGroup = this.formBuilder.group({
	      id: 0,
	      'facilityId': [null, Validators.compose([Validators.required])],
	      'createdDate': [null, Validators.compose([Validators.required])],
	      'elementarySectionCode': [null],
	      'equipmentToWork':[null],
	      'section':[null],
	      'typeOfOperation':[null],
	      'reqnBy':[null],
	      'reqDepartment': [null],
	      'purpose' : [null],
	      'reqPeriod' : [null],
	      'supervisorIncharge' : [null],
	      'betweenTrains' : [null]
	    });
  	}
  	
  	getPowerBlockData(id) {
	    this.sendAndRequestService.requestForGET(Constants.app_urls.OPERATIONS.POWER_BLOCK.GET_POWER_BLOCK_BY_ID +  id)
	      .subscribe((resp) => {
	        this.resp = resp;
	        this.addPowerBlockFormGroup.patchValue({
	          id: this.resp.id,
	          equipmentToWork: this.resp.equipmentToWork,
	          section: this.resp.section,
	          createdDate: !! this.resp.createdDate ? new Date(this.resp.createdDate) : '',
	          typeOfOperation: this.resp.typeOfOperation,
	          reqnBy: this.resp.reqnBy,
	          reqDepartment: this.resp.reqDepartment,
	          purpose: this.resp.purpose,
	          elementarySectionCode: this.resp.elementarySectionCode,
	          supervisorIncharge: this.resp.supervisorIncharge,
	          betweenTrains: this.resp.betweenTrains,
	          reqPeriod: this.resp.reqPeriod
	        });
	        this.spinnerService.hide();
	      })
  	}
  	
  onAddPowerBlockFormSubmit() {
    /*
    if (this.addPowerBlockFormGroup.invalid) {
    	console.log('invalid function');
      //this.isSubmit = false;
      return;
    }*/
    this.spinnerService.show();
    if (this.save) {
    console.log('in save function');
    let ele = this.addPowerBlockFormGroup.value.elementarySectionCode;
      let save = {
      	createdDate: this.addPowerBlockFormGroup.value.createdDate,
        elementarySectionCode: ele.toString(),
        section: this.section,
        equipmentToWork: this.addPowerBlockFormGroup.value.equipmentToWork,
        reqnBy: this.addPowerBlockFormGroup.value.reqnBy,
        reqDepartment: this.addPowerBlockFormGroup.value.reqDepartment,
        purpose: this.addPowerBlockFormGroup.value.purpose,
        typeOfOperation: 'OHE_POWER_BLOCK',
        facilityId: this.addPowerBlockFormGroup.value.facilityId,
        supervisorIncharge: this.addPowerBlockFormGroup.value.supervisorIncharge, 
        betweenTrains: this.addPowerBlockFormGroup.value.betweenTrains,
        reqPeriod: this.addPowerBlockFormGroup.value.reqPeriod  
      }
      this.sendAndRequestService.requestForPOST(Constants.app_urls.OPERATIONS.POWER_BLOCK.SAVE_POWER_BLOCK ,save, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Power Block Data saved Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Power Block Data saving Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Power Block Data saving Failed.");
      })
    } else if (this.update) {
      let update = {
        id: this.id,
        createdDate: this.addPowerBlockFormGroup.value.createdDate,
        facilityId: this.addPowerBlockFormGroup.value.facilityId,
        elementarySectionCode: this.addPowerBlockFormGroup.value.elementarySectionCode,
        section: this.section,
        equipmentToWork: this.addPowerBlockFormGroup.value.equipmentToWork,
        reqnBy: this.addPowerBlockFormGroup.value.reqnBy,
        reqDepartment: this.addPowerBlockFormGroup.value.reqDepartment,
        purpose: this.addPowerBlockFormGroup.value.purpose,
        typeOfOperation: 'OHE_POWER_BLOCK',
        supervisorIncharge: this.addPowerBlockFormGroup.value.supervisorIncharge, 
        betweenTrains: this.addPowerBlockFormGroup.value.betweenTrains,
        reqPeriod: this.addPowerBlockFormGroup.value.reqPeriod
      }
      this.sendAndRequestService.requestForPUT(Constants.app_urls.OPERATIONS.POWER_BLOCK.UPDATE_POWER_BLOCK ,update, false).subscribe(response => {
        this.spinnerService.hide();
        this.resp = response;
        if (this.resp.code == Constants.CODES.SUCCESS) {
        this.commonService.showAlertMessage("Power Block Data Updated Successfully");
        this.router.navigate(['../'], { relativeTo: this.route });
        }else{
          this.commonService.showAlertMessage("Power Block Data Updation Failed.");
        }
      }, error => {
        console.log('ERROR >>>');
        this.spinnerService.hide();
        this.commonService.showAlertMessage("Power Block Data Updation Failed.");
      })
    }
  }
  
  findPBSections () {
  	let selectedSection = this.addPowerBlockFormGroup.value.section ;
  	this.PBSections = [];
  if ('sector' == selectedSection) {
  		this.section = 'SECTOR';
	    this.sendAndRequestService.requestForGET(Constants.app_urls.ASSET_REGISTER.SECTORS.GET_SECTORS).subscribe((data) => {
	     // this.powerBlockList = data;
	      for (let i = 0; i < data.length; i++) {
	        //this.powerBlockList[i].sno = i + 1;
	        this.PBSections.push(data[i].sectorCode);
	      }
	      this.spinnerService.hide();
	    }, error => {
	      this.spinnerService.hide();
	    });
   }else if ('gantry' == selectedSection) { 
   		this.section = 'GANTRY';
	    this.sendAndRequestService.requestForGET(Constants.app_urls.ASSET_REGISTER.GANTARY.GET_GANTARY).subscribe((data) => {
	     // this.powerBlockList = data;
	      for (let i = 0; i < data.length; i++) {
	        //this.powerBlockList[i].sno = i + 1;
	        this.PBSections.push(data[i].gantryCode);
	      }
	      this.spinnerService.hide();
	    }, error => {
	      this.spinnerService.hide();
	    });
    }else if ('subSector' == selectedSection) {
    	this.section = 'SUB_SECTOR';
	    this.sendAndRequestService.requestForGET(Constants.app_urls.ASSET_REGISTER.SUB_SECTORS.GET_SUB_SECTORS).subscribe((data) => {
	     // this.powerBlockList = data;
	      for (let i = 0; i < data.length; i++) {
	        //this.powerBlockList[i].sno = i + 1;
	        this.PBSections.push(data[i].subSectorCode);
	      }
	      this.spinnerService.hide();
	    }, error => {
	      this.spinnerService.hide();
	    });
    }else {
    	this.section = 'ELEMENTARY_SECTION';
    	this.sendAndRequestService.requestForGET(Constants.app_urls.ASSET_REGISTER.ELE_SECTIONS.GET_ELE_SECTIONS).subscribe((data) => {
	     // this.powerBlockList = data;
	      for (let i = 0; i < data.length; i++) {
	        //this.powerBlockList[i].sno = i + 1;
	        this.PBSections.push(data[i].elementarySectionCode);
	      }
	      this.spinnerService.hide();
	    }, error => {
	      this.spinnerService.hide();
	    });
    }
    
  }
  	
  onGoBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }
  	
}


















