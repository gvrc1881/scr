import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FacilityModel } from 'src/app/models/facility.model';


@Component({
  selector: 'app-switch-operation',
  templateUrl: './switch-operation.component.html',
  styleUrls: []
})
export class SwitchOperationComponent implements OnInit {
	
	pbId: number = 0;
	resp: any;
	pbSwitchControl: any;

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
  		this.pbId = +this.route.snapshot.params['pbId'];
  		
  		this.sendAndRequestService.requestForGET(Constants.app_urls.OPERATIONS.POWER_BLOCK.GET_POWER_BLOCK_BY_ID +  this.pbId)
	      .subscribe((resp) => {
	        this.resp = resp;
	        this.sendAndRequestService.requestForGET(Constants.app_urls.OPERATIONS.PB_SWITCH_CONTROL.GET_PB_SWITCH_CONTROL_BASED_ON_EXTEND_TYPE_AND_EXTEND_CODE+'/'+this.resp.section+'/'+this.resp.elementarySectionCode)
	        .subscribe((res) => {
	        this.pbSwitchControl = res;
	        })
	        this.spinnerService.hide();
	      })
	      
  	}
	
	
}