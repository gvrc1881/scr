import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-data-view-dialog',
  templateUrl: './data-view-dialog.component.html',
  styleUrls: ['./data-view-dialog.component.css']
})
export class DataViewDialogComponent implements OnInit {
  title:string;
  dataSource=[];
  constructor(@Inject(MAT_DIALOG_DATA) private data: any, 
  public dialogRef: MatDialogRef<DataViewDialogComponent>) { 
    if (data) {
      this.title = data.title;  
      this.dataSource = data.dataSource;                   
  }  
  }

  ngOnInit() {
  }

}
