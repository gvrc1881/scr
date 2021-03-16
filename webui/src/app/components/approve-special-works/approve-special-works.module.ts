import { NgModel, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ApproveSpecialWorksComponent } from './approve-special-works.component';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/modules/material.modules';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { OwlDateTimeModule, OwlNativeDateTimeModule, DateTimeAdapter , OWL_DATE_TIME_FORMATS } from 'ng-pick-datetime';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
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
        path:'',
        component: ApproveSpecialWorksComponent,
    }
]

@NgModule({
    declarations: [
        ApproveSpecialWorksComponent
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
        { provide: DateTimeAdapter, useClass: DateFnsDateTimeAdapter },
        { provide: OWL_DATE_TIME_FORMATS, useValue: DATEFNS_FORMATS_EN_LOCALE }
    ],
    exports: [
        ApproveSpecialWorksComponent
    ]
})
export class ApproveSpecialWorksModule{

}