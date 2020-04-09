import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ForgotPasswordComponent } from './forgot-password.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatFormFieldModule, MatIconModule, MatCheckboxModule, MatInputModule, } from '@angular/material';
import { CommonModule } from '@angular/common';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';

const routes = [
    {
        path: '',
        component: ForgotPasswordComponent
    }
];

@NgModule({
    declarations: [
        ForgotPasswordComponent,
    ],
    imports: [
        RouterModule.forChild(routes),
        Ng4LoadingSpinnerModule.forRoot(),
        FormsModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        CommonModule,
        MatIconModule,
        MatCheckboxModule,
        MatInputModule

    ],
    providers: [

    ],
    exports: [
        ForgotPasswordComponent
    ]
})

export class ForgotPasswordModule {

}
