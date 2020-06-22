import { Component, OnInit, Inject, ViewChild, ElementRef } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatSort, MatPaginator, MatTableDataSource, MatDialog } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { DrivesService } from 'src/app/services/drives.service';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';

@Component({
    selector: 'file-information-dialog',
    templateUrl: './file-information-dialog.component.html',
    styleUrls: ['./file-information-dialog.component.scss']
})
export class FilesInformationDialogComponent implements OnInit {
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
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
        private drivesService: DrivesService, 
        public dialog: MatDialog,) {
        if (data) {
            this.response = data;
        }
    }

    ngOnInit() {
        //localStorage.setItem('driveFileType','INSPECTION');
        this.type = localStorage.getItem("driveFileType");
        this.prepareTable();
    }
    downloadFile(path, fileName) {
        console.log(path + " = " + fileName)
        /*  const link = document.createElement('a');
         link.setAttribute('target', '_blank');
         link.setAttribute('href', path);
         link.setAttribute('download', fileName);
         document.body.appendChild(link);
         link.click(); */
        //link.remove();
        this.drivesService.downloadDriveFile(this.type, fileName).subscribe((response) => {
            console.log(JSON.stringify(response));
        }, error => this.commonService.showAlertMessage(error));
    }
    prepareTable() {
        const divisionHistoryData = [];
        console.log(window.location.pathname)
        for (let i = 0; i < this.response.length; i++) {
            if (!!this.response[i]) {
                divisionHistoryData.push({
                    "sno": i + 1,
                    "fileName": this.response[i].changeFileName,
                    "type": 'D:/SCR/'+this.type,
                    "rowid":this.response[i].id,
                    "path": '.' + window.location.pathname + window.location.pathname + '/' + this.response[i].changeFileName
                });
            }
        }
        this.schedulerDataSource = new MatTableDataSource(divisionHistoryData);
        this.schedulerDataSource.paginator = this.schedulerPaginator;
        this.schedulerDataSource.sort = this.sort;
    }
    applyFilter(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
        this.schedulerDataSource.filter = filterValue;
    }
    delete(rowid) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
        });
        this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete?';
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.spinnerService.show();
                var id = localStorage.getItem('driveFileTypeId');
                console.log("common id = "+id+" row id="+rowid)
                this.drivesService.deleteFile(id, rowid, this.type).subscribe(data => {
                    this.spinnerService.hide();
                    this.commonService.showAlertMessage("Deleted File Successfully");
                    this.updateData(id);
                }, error => {
                    console.log('ERROR >>>');
                    this.spinnerService.hide();
                    this.commonService.showAlertMessage("File Deletion Failed.");
                })
            }
            this.confirmDialogRef = null;
        });
    }
    filesInfor: any;
    updateData(id) {
       // if (this.type == 'Stipulation') {
            this.drivesService.findStipulationAndInspectionDataById(id).subscribe((response) => {
                this.filesInfor = response;
                console.log(JSON.stringify(response));
                this.response = response;
                this.spinnerService.hide();
                this.prepareTable();
                /* if (this.filesInfor.attachment != '') {
                    var data = this.filesInfor.attachment.split(',');
                    console.log('data= ' + JSON.stringify(data))
                    this.spinnerService.hide();
                    this.response = data;
                    this.prepareTable();
                } */
            }, error => this.commonService.showAlertMessage(error));
        /* } else if (this.type == 'Inspection') {
            this.drivesService.findStipulationAndInspectionDataById(id).subscribe((response) => {
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
        } */
    }

}
