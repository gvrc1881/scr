import { Component, OnInit } from '@angular/core';
import { ReportService  } from "src/app/services/report.service";
import { ReportPayload } from 'src/app/payloads/report.payload';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent implements OnInit {

  reportData: any;
  
  constructor(
    private reportService: ReportService,    
  ) { }

  ngOnInit() {
  
  }

  runReport() {
    this.reportData = "";
     /* this.reportService.runReport(ReportPayload.GET).subscribe((response) => {
      this.reportData = response;
        
     if(this.reportData.outputData != ""){
      
        let pdfWindow = window.open("download","");

        let content = encodeURIComponent(this.reportData.outputData);

        let iframeStart = "<\iframe width='100%' height='100%' src='data:application/pdf;base64, ";

        let iframeEnd= "'><\/iframe>";

        pdfWindow.document.write(iframeStart + content + iframeEnd);
     }
    },
      error => error => {        
        console.log(' >>> ERROR ' + error);
       
      })  */
  }
}
