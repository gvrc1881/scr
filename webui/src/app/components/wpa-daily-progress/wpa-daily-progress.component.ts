import { OnInit, Component, ViewChild,ElementRef } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatTableDataSource, MatPaginator, MatSort, MatDialog, MatDialogRef } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { environment } from './../../../environments/environment';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { standardPhaseActivityModel } from 'src/app/models/works.model';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';

@Component({
    selector: 'wpa-daily-progress',
    templateUrl: './wpa-daily-progress.component.html',
    styleUrls: ['./wpa-daily-progress.component.css']
})
export class WPADailyProgressComponent implements OnInit {
        
    workList: any;

    constructor(
        public dialog: MatDialog,
        private formBuilder: FormBuilder,
        private spinnerService: Ng4LoadingSpinnerService,
        private commonService: CommonService,
        private sendAndRequestService:SendAndRequestService        
    ) {
    }
    ngOnInit() {
        //console.log('*** in ng onInit component****');
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WORK).subscribe((data) => {
            this.workList = data;
             //console.log('*** length ***'+this.workList.length);
        },error => {} );
    }

}