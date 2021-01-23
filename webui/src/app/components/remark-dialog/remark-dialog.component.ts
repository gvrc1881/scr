import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CommonService } from 'src/app/common/common.service';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';

@Component({
    selector: 'app-remark-dialog',
    templateUrl: './remark-dialog.component.html',
    styleUrls: []
})
export class RemarkDialogComponent implements OnInit {
    public confirmMessage: string;
    remarkFormGroup: FormGroup;
    remarkErrors: any;
    public response:any=[];
    loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
    constructor(
        @Inject(MAT_DIALOG_DATA) private data: any,
        private formBuilder: FormBuilder,
        public dialogRef: MatDialogRef<RemarkDialogComponent>,
        private sendAndRequestService: SendAndRequestService,
        private commonService: CommonService,
    ) {
        if (data) {
            this.response = data;                     
        }  
        this.remarkErrors = {
            remark: {},

        };
    }

    ngOnInit() {
        this.remarkFormGroup = this.formBuilder.group({
            id: 0,
            'remark': ['', Validators.compose([Validators.required])],
        });
        this.remarkFormGroup.valueChanges.subscribe(() => {
            this.onFormValuesChanged();
        });
    }
    onFormValuesChanged() {
        for (const field in this.remarkErrors) {
            if (!this.remarkErrors.hasOwnProperty(field)) {
                continue;
            }
            // Clear previous errors
            this.remarkErrors[field] = {};

            // Get the control
            const control = this.remarkFormGroup.get(field);
            if (control && control.dirty && !control.valid) {
                this.remarkErrors[field] = control.errors;
            }
        }
    }
    onRemarkSubmit(){
        let remark: string = this.remarkFormGroup.value.remark;
        const remarkDetails ={
            "jobId":this.response.jobId,
            "runTypeId": this.response.runTypeId,
            "remark":remark,
            "runBy":this.loggedUserData.id
        }
        this.sendAndRequestService.requestForPOST(Constants.app_urls.MASTERS.SCHEDULER_TRACKING.RERUN_WITH_REMARK, remarkDetails, false).subscribe((response) => {
          }, error => this.commonService.showAlertMessage(error));
        this.dialogRef.close(false);
    }
}
