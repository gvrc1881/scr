import { Component, OnInit, Inject, ViewChild, ElementRef } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatSort, MatPaginator, MatTableDataSource } from '@angular/material';
import { DivisionHistoryModel } from 'src/app/models/division-history.model';

@Component({
    selector: 'division-history-dialog',
    templateUrl: './division-history-dialog.component.html',
    styleUrls: ['./division-history-dialog.component.scss']
})
export class DivisionHistoryDialogComponent implements OnInit {
    public response:any=[];
    type:string;
    schedulerDisplayedColumns = ['sno', 'operationType', 'tableName', 'updatedDate', 'updatedRecords',  'status'];
    schedulerDataSource: MatTableDataSource<DivisionHistoryModel>;
    @ViewChild(MatPaginator, { static: true }) schedulerPaginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    @ViewChild('filter', { static: true }) filter: ElementRef;
    constructor(@Inject(MAT_DIALOG_DATA) private data: any, public dialogRef: MatDialogRef<DivisionHistoryDialogComponent>) {
        if (data) {
            this.response = data;                     
        }      
    }

    ngOnInit() {      
        this.type = localStorage.getItem("type");
        const divisionHistoryData: DivisionHistoryModel[] = [];
            for (let i = 0; i < this.response.length; i++) {               
                  this.response[i].sno = i + 1;
                  divisionHistoryData.push(this.response[i]);                
        
              }
              this.schedulerDataSource = new MatTableDataSource(divisionHistoryData);
              this.schedulerDataSource.paginator = this.schedulerPaginator;
              this.schedulerDataSource.sort = this.sort;
    }
    applyFilter(filterValue: string) {
        filterValue = filterValue.trim(); // Remove whitespace
        filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
        this.schedulerDataSource.filter = filterValue;
      }
}
