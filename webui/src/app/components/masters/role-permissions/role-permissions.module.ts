import { NgModule } from '@angular/core';

import { RouterModule, Routes } from '@angular/router';


import {RolePermissionsComponent } from './role-permissions.component';
import { RoleTypeService } from '../../../services/roletype.service';

import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { MatFormFieldModule, MatInputModule, MatCheckboxModule } from '@angular/material';
import { MaterialModule } from 'src/app/modules/material.modules';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { RolePermissionService } from 'src/app/services/role-permission.service';





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
        RoleTypeService,
        RolePermissionService
    ],
    exports     : [
        RolePermissionsComponent
    ]
})
export class RolePermissionsModule{

}
