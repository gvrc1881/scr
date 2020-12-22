import { NgModule } from '@angular/core';
import { TractionEnergyTariffComponent } from './traction-energy-tariff.component';
import { Routes, RouterModule } from '@angular/router';
import { MatMenuModule, MatGridListModule } from '@angular/material';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { OwlDateTimeModule, OwlNativeDateTimeModule, DateTimeAdapter , OWL_DATE_TIME_FORMATS } from 'ng-pick-datetime';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { DateFnsDateTimeAdapter } from 'src/app/common/date-fns-date-time-adapter.class';

const DATEFNS_FORMATS_EN_LOCALE = {
    parseInput: "dd-MM-yyyy HH:mm || dd/MM/yyyy", // multiple date input types separated by ||
    fullPickerInput: "dd-MM-yyyy HH:mm:ss",
    datePickerInput: "dd/MM/yyyy",
    timePickerInput: "HH:mm",
    monthYearLabel: "MMM yyyy",
    dateA11yLabel: "dd/MM/yyyy",
    monthYearA11yLabel: "MMMM yyyy"
  };

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
        Ng4LoadingSpinnerModule.forRoot(),
        OwlDateTimeModule, 
        OwlNativeDateTimeModule,
        DecimalValidationsModule,
    ],
    providers: [
    
        { provide: DateTimeAdapter, useClass: DateFnsDateTimeAdapter },
        { provide: OWL_DATE_TIME_FORMATS, useValue: DATEFNS_FORMATS_EN_LOCALE }
        
    ],
    exports:[
        TractionEnergyTariffComponent
    ]
})
export class TractionEnergyTariffModule {

}