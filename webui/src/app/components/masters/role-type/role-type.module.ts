import { NgModule } from '@angular/core';

import { RouterModule, Routes } from '@angular/router';


import {RoleTypeComponent } from './role-type.component';

import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { MatFormFieldModule, MatInputModule, MatCheckboxModule } from '@angular/material';
import { MaterialModule } from 'src/app/modules/material.modules';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';





const routes: Routes = [
    {
        path     : '',
        component: RoleTypeComponent
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
        RoleTypeComponent      
       
    ],
    providers: [
        
    ],
    exports     : [
        RoleTypeComponent
    ]
})
export class RoleTypeModule{

}
