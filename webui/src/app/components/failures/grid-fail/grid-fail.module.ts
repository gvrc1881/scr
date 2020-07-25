import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { GridFailComponent } from './grid-fail.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import { AddGridFailComponent } from './add-grid-fail/add-grid-fail.component';

const routes: Routes = [
    {
        path: '',
        component: GridFailComponent,
    },   
    {
        path     : ':id',
        component: AddGridFailComponent
    },
    {
        path        : 'add-grid-fail',
        component:   AddGridFailComponent
    },
];

@NgModule({
    declarations: [
        GridFailComponent,
        AddGridFailComponent,
        
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
        GridFailComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class GridFailModule {

}