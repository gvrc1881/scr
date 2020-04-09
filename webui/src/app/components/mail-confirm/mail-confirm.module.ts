import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MailConfirmComponent } from './mail-confirm.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatFormFieldModule, MatIconModule, MatCheckboxModule, MatInputModule, } from '@angular/material';
import { CommonModule } from '@angular/common';
/* import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { AlertService } from 'src/app/services/alert.service';
 */

const routes = [
    {
        path     : '',
        component: MailConfirmComponent
    }
];

@NgModule({
    declarations: [
        MailConfirmComponent,        
    ],
    imports     : [        
        RouterModule.forChild(routes),       
        FormsModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        CommonModule,
        MatIconModule,
        MatCheckboxModule,
        MatInputModule
        
    ],
    providers: [
       /* Ng4LoadingSpinnerService,
       AuthenticationService,
       AlertService */    
    ],
    exports:[
        MailConfirmComponent
    ]
})

export class MailConfirmModule
{

}
