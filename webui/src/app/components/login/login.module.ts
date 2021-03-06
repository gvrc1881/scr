import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { LoginComponent } from './login.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatFormFieldModule, MatIconModule, MatCheckboxModule, MatInputModule, } from '@angular/material';
import { CommonModule } from '@angular/common';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
const routes = [
    {
        path: '',
        component: LoginComponent
    }
];

@NgModule({
    declarations: [
        LoginComponent,
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
        Ng4LoadingSpinnerService,
    ],
    exports: [
        LoginComponent
    ]
})

export class LoginModule {

}
