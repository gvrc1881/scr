import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ActionsComponent } from './actions.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import { AddActionsComponent } from './add-actions/add-actions.component';

const routes: Routes = [
    {
        path: '',
        component: ActionsComponent,
    },   
    {
        path     : ':id',
        component: AddActionsComponent
    },
    {
        path        : 'add-actions',
        component:   AddActionsComponent
    },
];

@NgModule({
    declarations: [
        ActionsComponent,
        AddActionsComponent,
        
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
        ActionsComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class ActionsModule {

}