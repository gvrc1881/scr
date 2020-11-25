import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { StandardPhasesComponent } from './standard-phases.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddStandardPhasesComponent } from './add-standard-phases/add-standard-phases.component';
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';
import { filterDirective } from 'src/app/common/filter.directive';

const routes: Routes = [
    {
        path: '',
        component: StandardPhasesComponent,
    },   
    {
        path     : ':id',
        component: AddStandardPhasesComponent
    },
    {
        path        : 'add-standard-phases',
        component:   AddStandardPhasesComponent
    },
];

@NgModule({
    declarations: [
        StandardPhasesComponent,
        AddStandardPhasesComponent,
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
        StandardPhasesComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class StandardPhasesModule {

}