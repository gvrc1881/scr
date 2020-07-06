import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: []
})
export class ReportsComponent implements OnInit {

  reportData: any;
  
  constructor(
  ) { }

  ngOnInit() {
  
  }

  runReport() {
    this.reportData = "";
  }
}
