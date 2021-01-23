import { Component, OnInit, Inject, ViewChild, ElementRef } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatSort, MatPaginator, MatTableDataSource } from '@angular/material';
import { ContentManagementModel } from 'src/app/models/content-management.model';
import {environment} from './../../../environments/environment';

@Component({
    selector: 'document-dialog',
    templateUrl: './document-dialog.component.html'
})
export class DocumentDialogComponent implements OnInit {
    public response:any=[];
    type:string;
    contentManagementDisplayedColumns = ['sno', 'name', 'gen/ops', 'description','actions'];
    url=environment.apiUrl;
    contentManagementDataSource: MatTableDataSource<ContentManagementModel>;
    @ViewChild(MatPaginator, { static: true }) schedulerPaginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    @ViewChild('filter', { static: true }) filter: ElementRef;
    constructor(@Inject(MAT_DIALOG_DATA) private data: any, public dialogRef: MatDialogRef<DocumentDialogComponent>) {
        if (data) {
            this.response = data;                     
        }      
    }

    ngOnInit() {      
       // this.type = sessionStorage.getItem("type");
      // console.log('response:::from document dialog:::'+this.response);
        const contentManagementData: ContentManagementModel[] = [];
            for (let i = 0; i < this.response.length; i++) {               
                  this.response[i].sno = i + 1;
                  contentManagementData.push(this.response[i]);                
        
              }
              this.contentManagementDataSource = new MatTableDataSource(contentManagementData);
              this.contentManagementDataSource.paginator = this.schedulerPaginator;
              this.contentManagementDataSource.sort = this.sort;
    }
    /*
    applyFilter(filterValue: string) {
        filterValue = filterValue.trim(); // Remove whitespace
        filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
        this.contentManagementDataSource.filter = filterValue;
      }
      */
}
