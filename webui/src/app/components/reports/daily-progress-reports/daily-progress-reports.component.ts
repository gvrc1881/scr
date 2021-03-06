import { Component, OnInit, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';

@Component({
  selector: 'app-daily-progress-reports.component',
  templateUrl: './daily-progress-reports.component.html',
  styleUrls: ['./daily-progress-reports.component.css']
})
export class DailyProgressReportsComponent implements OnInit {

  dailyReportData: any;
  
  constructor(
    private sendAndRequestService: SendAndRequestService,
    private router: Router, 
  ) { }

  ngOnInit() {   
    let reportType = '';
   
    if(this.router.url == '/daily-progress-reports'){
      reportType = 'DailyProgress';  
    }
    else if(this.router.url == '/asset-reports') {
      reportType = 'AssetReports';      
    }
    else if(this.router.url == '/inventory-reports') {
      reportType = 'WHRS_WAREHOUSE';
    }
    else if(this.router.url == '/psi-reports'){
     reportType = 'PSIReports'
    }
    else if(this.router.url == '/zonal-reports'){
      reportType = 'zonal';
    }
    else if(this.router.url == '/asset-master-reports'){
      reportType = 'Asset_Master_Reports';
    }
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_REPORT_NAMES + reportType).subscribe((data)=>{
      this.dailyReportData =data;
  })
}
  
  }
