import { NgModule } from '@angular/core';

import { RouterModule, Routes } from '@angular/router';


import {RolePermissionsComponent } from './role-permissions.component';

import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import {  MatCheckboxModule } from '@angular/material';
import { MaterialModule } from 'src/app/modules/material.modules';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';





const routes: Routes = [
    {
        path     : '',
        component: RolePermissionsComponent
    }   
   
];

@NgModule({
    imports     : [
        RouterModule.forChild(routes),
        Ng4LoadingSpinnerModule.forRoot(),
        MaterialModule,
        FormsModule,
        ReactiveFormsModule,
        MatCheckboxModule
    ],
    declarations: [
        RolePermissionsComponent      
       
    ],
    providers: [
    ],
    exports     : [
        RolePermissionsComponent
    ]
})
export class RolePermissionsModule{

}
