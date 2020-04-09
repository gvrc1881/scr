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
  
  constructor(
    private reportService: ReportService,   
    private router: Router, 
  ) { }

  ngOnInit() {   
    console.log(this.router.url);
    let reportType = '';
   
    if(this.router.url == '/report-names'){
      reportType = 'DailyProgress';  
      console.log("reportType"+reportType)    
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
    this.reportService.reportNames(reportType).subscribe((data)=>{
      this.reportNamesData =data;
     // console.log(reportType+' = '+JSON.stringify(data))
  })
}
  
  }
