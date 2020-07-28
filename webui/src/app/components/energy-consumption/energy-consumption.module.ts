import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EnergyConsumptionComponent } from './energy-consumption.component';
import { MatMenuModule, MatGridListModule } from '@angular/material';
import { CommonModule } from '@angular/common';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { FusionChartsModule } from 'angular-fusioncharts';
import { MaterialModule } from 'src/app/modules/material.modules';
import { filterDirective } from 'src/app/common/filter.directive';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { FormsModule } from '@angular/forms';

const routes: Routes = [
    {
        path     : '',
        component: EnergyConsumptionComponent,
       
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
        DecimalValidationsModule
    ],
    declarations: [
        EnergyConsumptionComponent,
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

