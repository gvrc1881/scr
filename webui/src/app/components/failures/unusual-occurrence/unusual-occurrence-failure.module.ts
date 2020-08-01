import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { UnusualOccurrenceFailureComponent } from './unusual-occurrence-failure.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import { AddUnusualOccurrenceFailureComponent } from './add-unusual-occurrence-failure/add-unusual-occurrence-failure.component';
import { AddActionsComponent } from './add-actions/add-actions.component';

const routes: Routes = [
    {
        path: '',
        component: UnusualOccurrenceFailureComponent,
    },   
    {
        path     : ':id',
        component: AddUnusualOccurrenceFailureComponent
    },
    {
        path        : 'add-unusual-occurrence-failure',
        component:   AddUnusualOccurrenceFailureComponent
    },   
    {
        path     : 'actions/:id',
        component: AddActionsComponent
    },
    {
        path        : 'add-actions',
        component:   AddActionsComponent
    },
];

@NgModule({
    declarations: [
        UnusualOccurrenceFailureComponent,
        AddUnusualOccurrenceFailureComponent,
        AddActionsComponent
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
        UnusualOccurrenceFailureComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class UnusualOccurrenceFailureModule {

}