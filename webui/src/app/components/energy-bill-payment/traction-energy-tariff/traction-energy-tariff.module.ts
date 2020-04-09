import { NgModule } from '@angular/core';
import { TractionEnergyTariffService } from 'src/app/services/traction-energy-tariff.service';
import { TractionEnergyTariffComponent } from './traction-energy-tariff.component';
import { Routes, RouterModule } from '@angular/router';
import { MatMenuModule, MatGridListModule } from '@angular/material';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';

const routes: Routes = [
    {
        path: '',
        component: TractionEnergyTariffComponent,
    }
];

@NgModule({
    declarations: [
        TractionEnergyTariffComponent
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,
        Ng4LoadingSpinnerModule.forRoot()
    ],
    providers: [
        TractionEnergyTariffService
    ],
    exports:[
        TractionEnergyTariffComponent
    ]
})
export class TractionEnergyTariffModule {

}