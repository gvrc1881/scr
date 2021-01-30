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

  energyResponse:any;
  energyDataSource: any = {};
  requestBody: any;
  width: any;
  	height = 400;
	  showEnergyChart : boolean = false;
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
    this.energyData();
   
  }
  
  energyData(){

    this.requestBody = {
      queryType:'ENERGY'    
  }
  this.sendAndRequestService.requestForPOST(Constants.app_urls.DASHBOARD.GET_GRAPHS_DATA, this.requestBody, false).subscribe((data) => {
    console.log(data);
    this.energyResponse = data;    
    if(this.energyResponse) {
      this.energyDataSource = this.prepareEnergyGraphData(this.energyResponse);
       console.log('*** log***'+JSON.stringify(this.energyDataSource));
        if(this.energyDataSource){
          this.showEnergyChart = true;    
        }
      //this.showReportBotton = true; 
    }
  }) 
  }

  prepareEnergyGraphData(data: any){
    let location = [];
    let consumed =[];
    let exceeded =[];
    let canbeconsume =[];
    for(let i=0;i<data.length;i++){
        this.width = this.width +200;
        location.push({               
                
                    label: data[i].location                
            
        })
        consumed.push({
          value:data[i].consumed
        })
        exceeded.push({
          value:data[i].exceeded
        })
        canbeconsume.push({
          value:data[i].canbeconsume
        })
    }
    console.log("locations=="+JSON.stringify(location))
    console.log("data consumed==="+consumed);
    return {
        chart: {
        caption: "Energy",
        xaxisname: "TSS",
        yaxisname: "Percentage",
        formatnumberscale: "1",
        plottooltext:
        "$label has $dataValue (<b>$percentValue</b>) $seriesName ",
       
        theme: "fusion"
      },
      categories: [
        {
          category: location
        }
      ],      
       dataset: [
	    	{
	        seriesname: "consumed",
	        data: consumed
	      },
	      {
	        seriesname: "exceeded",
	        data: exceeded
	      },
	      {
	        seriesname: "canbeconsume",
	        data: canbeconsume
	      },
      ]  
      
    }
   
}
  
}

  
