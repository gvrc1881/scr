import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { RegisterComponent } from './register.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatFormFieldModule, MatIconModule, MatCheckboxModule, MatInputModule, } from '@angular/material';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/modules/material.modules';
/* import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { AlertService } from 'src/app/services/alert.service';
 */

const routes = [
    {
        path     : '',
        component: RegisterComponent
    }
];

@NgModule({
    declarations: [
        RegisterComponent,        
    ],
    imports     : [        
        RouterModule.forChild(routes),       
        FormsModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        CommonModule,
        MatIconModule,
        MatCheckboxModule,
        MatInputModule,
        MaterialModule
    ],
    providers: [
       /* Ng4LoadingSpinnerService,
       AuthenticationService,
       AlertService */    
    ],
    exports:[
        RegisterComponent
    ]
})

export class RegisterModule
{

}
