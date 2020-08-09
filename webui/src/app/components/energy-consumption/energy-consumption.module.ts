import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EnergyConsumptionComponent } from './energy-consumption.component';
import { MatMenuModule, MatGridListModule } from '@angular/material';
import { CommonModule } from '@angular/common';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { FusionChartsModule } from 'angular-fusioncharts';
import { MaterialModule } from 'src/app/modules/material.modules';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AddEnergyConsumptionComponent } from './add-energy-consumption/add-energy-consumption.component';

const routes: Routes = [
    {
        path     : '',
        component: EnergyConsumptionComponent,
       
    },
    {
        path     : ':id',
        component: AddEnergyConsumptionComponent,
       
    }
];

@NgModule({
    imports     : [     
        RouterModule.forChild(routes),
        Ng4LoadingSpinnerModule.forRoot(),
        MatMenuModule,
        MatGridListModule,
        CommonModule,
        FusionChartsModule,
        MaterialModule,
        FormsModule,
        DecimalValidationsModule,
        ReactiveFormsModule
    ],
    declarations: [
        EnergyConsumptionComponent,
        AddEnergyConsumptionComponent,
    ],
    providers   : [
        Ng4LoadingSpinnerService,
    ],
    exports     : [
        EnergyConsumptionComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class EnergyConsumptionModule
{
    
}

