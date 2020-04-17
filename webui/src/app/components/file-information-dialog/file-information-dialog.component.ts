import { Component, OnInit, Inject, ViewChild, ElementRef } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatSort, MatPaginator, MatTableDataSource } from '@angular/material';
import { DivisionHistoryModel } from 'src/app/models/division-history.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { DrivesService } from 'src/app/services/drives.service';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';

@Component({
    selector: 'file-information-dialog',
    templateUrl: './file-information-dialog.component.html',
    styleUrls: ['./file-information-dialog.component.scss']
})
export class FilesInformationDialogComponent implements OnInit {
    public response: any = [];
    type: string;
    schedulerDisplayedColumns = ['sno', 'fileName', 'actions'];
    schedulerDataSource: MatTableDataSource<any>;
    @ViewChild(MatPaginator, { static: true }) schedulerPaginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    @ViewChild('filter', { static: true }) filter: ElementRef;
    constructor(
        @Inject(MAT_DIALOG_DATA) private data: any,
        public dialogRef: MatDialogRef<FilesInformationDialogComponent>,
        private spinnerService: Ng4LoadingSpinnerService,
        private commonService: CommonService,
        private drivesService: DrivesService, ) {
        if (data) {
            this.response = data;
        }
    }

    ngOnInit() {
        //localStorage.setItem('driveFileType','INSPECTION');
        this.type = localStorage.getItem("driveFileType");
        this.prepareTable();
    }
    prepareTable() {
        const divisionHistoryData = [];
        console.log(this.response)
        var data
        for (let i = 0; i < this.response.length; i++) {
            /*  this.response[i].sno = i + 1;
             this.response[i].fileName = this.response[i]; */
            // divisionHistoryData.push(this.response[i]);                
            divisionHistoryData.push({
                "sno": i + 1,
                "fileName": this.response[i]
            });
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

    delete(fileName) {
        this.spinnerService.show();
        var id = localStorage.getItem('driveFileTypeId');
        this.drivesService.deleteFile(id, fileName, this.type).subscribe(data => {
            console.log(JSON.stringify(data));
            this.spinnerService.hide();
            this.commonService.showAlertMessage("Deleted File Successfully");
            this.updateData(id);
            // this.getStipulationData();
        }, error => {
            console.log('ERROR >>>');
            this.spinnerService.hide();
            this.commonService.showAlertMessage("File Deletion Failed.");
        })
    }
    filesInfor: any;
    updateData(id) {
        if (this.type == 'Stipulation') {
            this.drivesService.findStipulationDataById(id).subscribe((response) => {
                this.filesInfor = response;
                console.log(JSON.stringify(response));
                this.response = [];
                if (this.filesInfor.attachment != '') {
                    var data = this.filesInfor.attachment.split(',');
                    console.log('data= ' + JSON.stringify(data))
                    this.spinnerService.hide();
                    this.response = data;
                   
                }
                this.prepareTable();

            }, error => this.commonService.showAlertMessage(error));
        } else if (this.type == 'Inspection') {
            this.drivesService.findInspectionsDataById(id).subscribe((response) => {
                this.filesInfor = response;
                console.log(JSON.stringify(response));
                this.response = [];
                if (this.filesInfor.attachment != '') {
                    var data = this.filesInfor.attachment.split(',');
                    console.log('data= ' + JSON.stringify(data))
                    this.spinnerService.hide();
                    this.response = data;
                }                
                this.prepareTable();
            }, error => this.commonService.showAlertMessage(error));
        }
    }

}
