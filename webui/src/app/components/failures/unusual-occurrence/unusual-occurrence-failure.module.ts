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
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';
const routes: Routes = [
    {
        path: '',
        component: UnusualOccurrenceFailureComponent,
    },   
    {
        path     : 'unusual-occurrence/:id',
        component: AddUnusualOccurrenceFailureComponent
    },   
    {
        path     : 'actions/:id',
        component: AddActionsComponent
    },
    {
        path        : 'add-actions',
        component:   AddActionsComponent
    } ,
    {
        path        : 'add-unusual-occurrence-failure',
        component:   AddUnusualOccurrenceFailureComponent
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
        CommonDirectivesModule,
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