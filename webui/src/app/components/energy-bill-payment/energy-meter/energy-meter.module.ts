import { NgModel, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { EnergyMeterComponent } from './energy-meter.component';
import { EnergyMeterService } from 'src/app/services/energy-meter.service';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/modules/material.modules';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';

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
        DecimalValidationsModule,
        MaterialModule,
        OwlDateTimeModule, 
        OwlNativeDateTimeModule,
        Ng4LoadingSpinnerModule.forRoot()
        
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