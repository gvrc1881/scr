import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CbFailComponent } from './cb-fail.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import { AddCbFailComponent } from './add-cb-fail/add-cb-fail.component';

const routes: Routes = [
    {
        path: '',
        component: CbFailComponent,
    },   
    {
        path     : ':id',
        component: AddCbFailComponent
    },
    {
        path        : 'add-cb-fail',
        component:   AddCbFailComponent
    },
];

@NgModule({
    declarations: [
        CbFailComponent,
        AddCbFailComponent,
        
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
        CbFailComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class CbFailModule {

}