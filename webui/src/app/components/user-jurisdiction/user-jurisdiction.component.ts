import { OnInit, Component, ViewChild,ElementRef } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatTableDataSource, MatPaginator, MatSort, MatDialog, MatDialogRef } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { environment } from './../../../environments/environment';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { WPADailyProgressModel } from 'src/app/models/works.model';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';
import { DateAdapter, MAT_DATE_FORMATS } from '@angular/material';

@Component({
    selector: 'user-jurisdiction',
    templateUrl: './user-jurisdiction.component.html',
    styleUrls: ['./user-jurisdiction.component.css']    
})

export class UserJurisdictionComponent implements OnInit {
        
    FiledLabels = FieldLabelsConstant.LABELS;
    addPermission: boolean;
    editPermission: boolean;
    deletePermission: boolean;
    
    

    constructor(
        public dialog: MatDialog,
        private formBuilder: FormBuilder,
        private spinnerService: Ng4LoadingSpinnerService,
        private commonService: CommonService,
        private sendAndRequestService:SendAndRequestService        
    ) {
    }
    
    ngOnInit() {
        console.log('*** in ng onInit component****');
        var permissionName = this.commonService.getPermissionNameByLoggedData("PROJECT ADMIN","UserJurisdiction") ;//p == 0 ? 'No Permission' : p[0].permissionName;
    this.addPermission = this.commonService.getPermissionByType("Add", permissionName); //getPermission("Add", );
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
        
    }
    
    
    
}