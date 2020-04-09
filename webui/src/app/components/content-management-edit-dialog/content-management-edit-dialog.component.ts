import { Component, OnInit, Inject} from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { ContentManagementService } from 'src/app/services/content-management.service';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { ContentManagementPayload } from 'src/app/payloads/content-management.payload';


@Component({
    selector: 'content-management-edit-dialog',
    templateUrl: './content-management-edit-dialog.component.html',
    styleUrls: ['./content-management-edit-dialog.component.scss']
})
export class ContentManagementDialogComponent implements OnInit {
    public response:any=[];
    isSubmit:boolean=false;
    pattern = "[a-zA-Z][a-zA-Z ]*";
    userdata: any = JSON.parse(localStorage.getItem('userData'));
    contentManagementEditFormGroup: FormGroup;
    constructor(@Inject(MAT_DIALOG_DATA) private data: any, 
    private formBuilder: FormBuilder,
    private service: ContentManagementService,    
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    public dialogRef: MatDialogRef<ContentManagementDialogComponent>) {
        if (data) {
            this.response = data;                     
        }      
    }

    ngOnInit() {      
       console.log('response: '+JSON.stringify(this.response));
       this.createEditCMForm();
    }
    public get f(){ return this.contentManagementEditFormGroup.controls;}
    createEditCMForm(){        
        this.contentManagementEditFormGroup = this.formBuilder.group({            
            descriptionEdit:[this.response[0].description, Validators.compose([Validators.required,Validators.pattern(this.pattern)])]            
        });
    }

    onContentManagementEditSubmit(){        
        this.isSubmit = true;
        if(this.contentManagementEditFormGroup.invalid){
            this.isSubmit = false;
            return;
        }
        this.spinnerService.show();   
        debugger;     
        let description = this.contentManagementEditFormGroup.value.descriptionEdit;
        ContentManagementPayload.UPDATE_PAYLOAD.commonFileId = this.response[0].commonFileId;
        ContentManagementPayload.UPDATE_PAYLOAD.description = description;
        ContentManagementPayload.UPDATE_PAYLOAD.id = this.response[0].id;
        ContentManagementPayload.UPDATE_PAYLOAD.modifiedBy = this.userdata.id
       
        let payload = ContentManagementPayload.UPDATE_PAYLOAD;
        this.service.updateDescription(payload).subscribe(data =>{           
            this.spinnerService.hide();
            this.commonService.showAlertMessage("Description Updated Successfully");            
            this.dialogRef.close('closed');
        },error =>{
            console.log('ERROR >>>');
            this.spinnerService.hide();
            this.commonService.showAlertMessage("Description updating  Failed.");
        });

        
    }
}
