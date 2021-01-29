import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-energy-graphs',
  templateUrl: './energy-graphs.component.html',
  styleUrls: ['./energy-graphs.component.css']
})
    
export class EnergyGraphsComponent implements OnInit {

  response:any;
  energyDataSource: any = {};
  width: any;
  	height = 400;
	  showGraph : boolean = false;
	  type = "scrollstackedcolumn2d";
  	dataFormat = "json";
    

    constructor(
      private formBuilder: FormBuilder,
      private route: ActivatedRoute,
      private spinnerService: Ng4LoadingSpinnerService,
      private commonService: CommonService,
      private router: Router,
      private datePipe: DatePipe,
      private sendAndRequestService: SendAndRequestService
  ) {
  
  }
  
  ngOnInit() {

    // this.sendAndRequestService.requestForPOST(Constants.app_urls.DASHBOARD.GET_GRAPHS_DATA, false).subscribe((data) => {
    //   console.log(data);
    //   this.response = data;
    //   if(this.response) {
    //   	this.energyDataSource = this.energyConsumptionGraph(this.response);
      
    //   }
    // })
  }
  
  
}

  
