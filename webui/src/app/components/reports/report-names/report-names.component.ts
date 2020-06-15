import { Component, OnInit, Input, Output } from '@angular/core';
import { ReportService  } from "src/app/services/report.service";
import { Router } from '@angular/router';
//import { Router,ActivatedRoute } from '@angular/router';

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
    private reportService: ReportService,   
    private router: Router, 
  ) { }

  ngOnInit() {   
    console.log(this.router.url);
    this.reportType = '';   
    if(this.router.url == '/report-names'){
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
    this.reportService.reportNames(this.reportType).subscribe((data)=>{
      this.reportNamesData =data;
     // console.log(reportType+' = '+JSON.stringify(data))
  })
}
  
  }
