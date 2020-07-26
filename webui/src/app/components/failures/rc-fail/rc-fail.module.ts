import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RcFailComponent } from './rc-fail.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import { AddRcFailComponent } from './add-rc-fail/add-rc-fail.component';

const routes: Routes = [
    {
        path: '',
        component: RcFailComponent,
    },   
    {
        path     : ':id',
        component: AddRcFailComponent
    },
    {
        path        : 'add-rc-fail',
        component:   AddRcFailComponent
    },
];

@NgModule({
    declarations: [
        RcFailComponent,
        AddRcFailComponent,
        
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,      
        Ng4LoadingSpinnerModule.forRoot(),
        OwlDateTimeModule, 
        OwlNativeDateTimeModule,
    ],
    providers: [
        
    ],
    exports:[
        RcFailComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class RcFailModule {

}