import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RcFailureComponent } from './rc-failure.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import { AddRcFailureComponent } from './add-rc-failure/add-rc-failure.component';

const routes: Routes = [
    {
        path: '',
        component: RcFailureComponent,
    },   
    {
        path     : ':id',
        component: AddRcFailureComponent
    },
    {
        path        : 'add-rc-failure',
        component:   AddRcFailureComponent
    },
];

@NgModule({
    declarations: [
        RcFailureComponent,
        AddRcFailureComponent,
        
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
        RcFailureComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class RcFailureModule {

}