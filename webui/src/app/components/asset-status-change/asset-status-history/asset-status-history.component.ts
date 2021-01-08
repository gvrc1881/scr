import { Component, OnInit, Inject, ViewChild, ElementRef } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatSort, MatPaginator, MatTableDataSource } from '@angular/material';
import { AssetStatusChangeModel } from 'src/app/models/asset-status-change.model';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { Constants } from 'src/app/common/constants';
import { DatePipe } from '@angular/common';
@Component({
    selector: 'asset-status-history',
    templateUrl: './asset-status-history.component.html'
})
export class AssetStatusDialogComponent implements OnInit {
    public response:any=[];
    type:string;
    historyList:any;
    assetStatusDisplayedColumns = ['sno', 'assetId','dateOfStatus', 'status', 'remarks'];
    assetStatusDataSource: MatTableDataSource<AssetStatusChangeModel>;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    @ViewChild('filter', { static: true }) filter: ElementRef;

    constructor( 
        
        private sendAndRequestService:SendAndRequestService,       
        @Inject(MAT_DIALOG_DATA) public data:any,
        private datePipe: DatePipe, 
        private spinnerService: Ng4LoadingSpinnerService,
        public dialogRef: MatDialogRef<AssetStatusChangeModel>,
    ){
        
    }

    ngOnInit() {      
       
       
        const assetStatusData: AssetStatusChangeModel[] = [];

        this.sendAndRequestService.requestForGET(Constants.app_urls.OPERATIONS.ASSET_STATUS_CHANGE.
            GET_BY_ASSETID+this.data).subscribe((data) => {
            this.historyList = data; 
           
            for (let i = 0; i < this.historyList.length; i++) {               
                this.historyList[i].sno = i + 1;               
                    this.historyList[i].dateOfStatus = this.datePipe.transform(this.historyList[i].dateOfStatus, 'dd-MM-yyyy');  
                  
                assetStatusData.push(this.historyList[i]);                
      
            }
            this.assetStatusDataSource = new MatTableDataSource(assetStatusData);
            this.assetStatusDataSource.paginator = this.paginator;
            this.assetStatusDataSource.sort = this.sort;  

           
            },  error => {
               
            });

           
    }

    
   
}
