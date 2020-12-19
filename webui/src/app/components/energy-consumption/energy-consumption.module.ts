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
import { OwlDateTimeModule, OwlNativeDateTimeModule, DateTimeAdapter , OWL_DATE_TIME_FORMATS } from 'ng-pick-datetime';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';
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
        ReactiveFormsModule,
        CommonDirectivesModule,
        CommonModule,
        OwlDateTimeModule, 
        OwlNativeDateTimeModule,
    ],
    declarations: [
        EnergyConsumptionComponent,
        AddEnergyConsumptionComponent,
    ],
    providers   : [
        Ng4LoadingSpinnerService,
        { provide: DateTimeAdapter, useClass: DateFnsDateTimeAdapter },
        { provide: OWL_DATE_TIME_FORMATS, useValue: DATEFNS_FORMATS_EN_LOCALE }
    ],
    exports     : [
        EnergyConsumptionComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class EnergyConsumptionModule
{
    
}

