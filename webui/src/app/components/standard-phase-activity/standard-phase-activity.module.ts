import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { StandardPhaseActivityComponent } from './standard-phase-activity.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddStandardPhaseActivityComponent } from './add-standard-phase-activity/add-standard-phase-activity.component';
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';
import { filterDirective } from 'src/app/common/filter.directive';

const routes: Routes = [
    {
        path: '',
        component: StandardPhaseActivityComponent,
    },   
    {
        path     : ':id',
        component: AddStandardPhaseActivityComponent
    },
    {
        path        : 'add-standard-phase-activity',
        component:   AddStandardPhaseActivityComponent
    },
];

@NgModule({
    declarations: [
        StandardPhaseActivityComponent,
        AddStandardPhaseActivityComponent,
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,      
        Ng4LoadingSpinnerModule.forRoot(),
        NumberValidationsModule,
        CommonDirectivesModule,
        DecimalValidationsModule
    ],
    providers: [
        
    ],
    exports:[
        StandardPhaseActivityComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class StandardPhaseActivityModule {

}