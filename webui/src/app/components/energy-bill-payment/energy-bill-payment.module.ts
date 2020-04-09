import { NgModule } from '@angular/core';
import { EnergyBillPaymentService } from 'src/app/services/energy-bill-payment.service';
import { EnergyBillPaymentComponent } from './energy-bill-payment.component';
import { Routes, RouterModule } from '@angular/router';
import { MatMenuModule, MatGridListModule } from '@angular/material';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';

const routes: Routes = [
    {
        path: '',
        component: EnergyBillPaymentComponent,
    }
];

@NgModule({
    declarations: [
        EnergyBillPaymentComponent
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
        EnergyBillPaymentService
    ],
    exports:[
        EnergyBillPaymentComponent
    ]
})
export class EnergyBillPaymentModule {

}