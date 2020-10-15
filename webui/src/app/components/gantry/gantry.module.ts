import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { GantryComponent } from './gantry.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddGantryComponent } from './add-gantry/add-gantry.component';
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';

const routes: Routes = [
    {
        path: '',
        component: GantryComponent,
    },   
    {
        path     : ':id',
        component: AddGantryComponent
    },
    {
        path        : 'add-gantry',
        component:   AddGantryComponent
    },
];

@NgModule({
    declarations: [
        GantryComponent,
        AddGantryComponent,
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
        GantryComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class GantryModule {

}