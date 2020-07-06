import { Component, OnInit, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';

@Component({
  selector: 'app-report-names.component',
  templateUrl: './report-names.component.html',
  styleUrls: ['./report-names.component.css']
})
export class ReportNamesComponent implements OnInit {

  reportNamesData: any;
  reportType:string;
  breadcrumb:string;
  constructor(  
    private router: Router, 
    private sendAndRequestService:SendAndRequestService

  ) { }

  ngOnInit() {   
    this.reportType = '';   
    if(this.router.url == '/daily-progress-reports'){
      this.reportType = 'DailyProgress';   
      this.breadcrumb = 'Daily Progress Reports';       
    }
    else if(this.router.url == '/asset-reports') {
      this.reportType = 'AssetReports';      
      this.breadcrumb = 'Asset Reports';
    }
    else if(this.router.url == '/inventory-reports') {
      this.reportType = 'WHRS_WAREHOUSE';
      this.breadcrumb = 'Inventory Reports';
    }
    else if(this.router.url == '/psi-reports'){
      this.reportType = 'PSIReports';
      this.breadcrumb = 'PSI Reports';
    }
    else if(this.router.url == '/zonal-reports'){
      this.reportType = 'zonal';
      this.breadcrumb = 'Zonal Reports';
    }
    else if(this.router.url == '/asset-master-reports'){
      this.reportType = 'Asset_Master_Reports';
      this.breadcrumb = 'Asset Master Reports';
    }
    console.log("reportType"+this.reportType)  
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_REPORT_NAMES+'/'+this.reportType)
    .subscribe((data)=>{
      this.reportNamesData =data;
  })
}
  
  }
