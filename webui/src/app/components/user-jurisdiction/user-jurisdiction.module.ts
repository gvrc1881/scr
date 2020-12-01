import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { UserJurisdictionComponent } from './user-jurisdiction.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddUserJurisdictionComponent } from './add-user-jurisdiction/add-user-jurisdiction.component';

const routes: Routes = [
    {
        path: '',
        component: UserJurisdictionComponent,
    },
    {
        path: 'add-user-jurisdiction',
        component: AddUserJurisdictionComponent,
    }
];

@NgModule({
    declarations: [
        UserJurisdictionComponent,
        AddUserJurisdictionComponent
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,        
        Ng4LoadingSpinnerModule.forRoot(),
    ],
    providers: [
        
    ],
    exports:[
        UserJurisdictionComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class UserJurisdictionModule {

}