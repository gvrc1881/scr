import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MatMenuModule, MatGridListModule } from '@angular/material';
import { CommonModule } from '@angular/common';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { FusionChartsModule } from 'angular-fusioncharts';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { EnergyConsumptionGraphComponent } from './energy-consumption-graph.component';

const routes: Routes = [
    {
        path     : '',
        component: EnergyConsumptionGraphComponent,
       
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
        FormsModule,
        MaterialModule,
        ReactiveFormsModule
    ],
    declarations: [
        EnergyConsumptionGraphComponent,
    ],
    providers   : [
        Ng4LoadingSpinnerService,
    ],
    exports     : [
        EnergyConsumptionGraphComponent
    ]
})
export class EnergyConsumptionGraphModule
{
}

