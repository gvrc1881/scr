import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { FailureOccurrenceComponent } from './failure-occurrence-failure.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import { AddFailureOccurrenceComponent } from './add-failure-occurrence-failure/add-failure-occurrence-failure.component';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';
const routes: Routes = [
    {
        path: '',
        component: FailureOccurrenceComponent,
    },   
    {
        path     : ':id',
        component: AddFailureOccurrenceComponent
    },
    {
        path        : 'add-failure-occurrence',
        component:   AddFailureOccurrenceComponent
    },
];

@NgModule({
    declarations: [
        FailureOccurrenceComponent,
        AddFailureOccurrenceComponent,
        
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
        FailureOccurrenceComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class FailureOccurrenceModule {

}