import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { SchedulerTrackingService } from 'src/app/services/scheduler-tracking.service';
import { CommonService } from 'src/app/common/common.service';

@Component({
    selector: 'app-remark-dialog',
    templateUrl: './remark-dialog.component.html',
    styleUrls: ['./remark-dialog.component.scss']
})
export class RemarkDialogComponent implements OnInit {
    public confirmMessage: string;
    remarkFormGroup: FormGroup;
    remarkErrors: any;
    public response:any=[];
    loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
    constructor(
        @Inject(MAT_DIALOG_DATA) private data: any,
        private formBuilder: FormBuilder,
        public dialogRef: MatDialogRef<RemarkDialogComponent>,
        private schedulerTrackingService: SchedulerTrackingService,
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
        console.log("remark: "+remark);   
        const remarkDetails ={
            "jobId":this.response.jobId,
            "runTypeId": this.response.runTypeId,
            "remark":remark,
            "runBy":this.loggedUserData.id
        }
        this.schedulerTrackingService.reRunByIdByType(remarkDetails).subscribe((response) => {
           console.log("response: "+JSON.stringify(response));

          }, error => this.commonService.showAlertMessage(error));
        this.dialogRef.close(false);
    }
}
