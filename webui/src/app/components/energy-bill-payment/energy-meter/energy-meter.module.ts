import { NgModel, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { EnergyMeterComponent } from './energy-meter.component';
import { EnergyMeterService } from 'src/app/services/energy-meter.service';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/modules/material.modules';

const routes: Routes = [
    {
        path:'',
        component: EnergyMeterComponent,
    }
]

@NgModule({
    declarations: [
        EnergyMeterComponent
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule
        
    ],
    providers: [
        EnergyMeterService
    ],
    exports: [
        EnergyMeterComponent
    ]
})
export class EnergyMeterModule{

}