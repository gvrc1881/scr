import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComplianceDetailsComponent } from './compliance-details.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { AddComplianceDetailsComponent } from './add-compliance-details/add-compliance-details.component';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { DateTimeAdapter, OwlDateTimeModule, OwlNativeDateTimeModule, OWL_DATE_TIME_FORMATS } from 'ng-pick-datetime';
import { DateFilterPipe } from 'src/app/common/date-filter.pipe';
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
        component: ComplianceDetailsComponent,
    },
    {
        path     : ':id',
        component: AddComplianceDetailsComponent,
    },
    {
    	path: 'add-observation-details',
    	component: AddComplianceDetailsComponent,
    },
    
    
];


@NgModule({
    declarations: [
        ComplianceDetailsComponent,
        AddComplianceDetailsComponent
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,
        DecimalValidationsModule,
        NumberValidationsModule,
        Ng4LoadingSpinnerModule.forRoot(),
        OwlDateTimeModule, 
        CommonDirectivesModule, 
        OwlNativeDateTimeModule
    ],
    providers: [
        Ng4LoadingSpinnerService,
        { provide: DateTimeAdapter, useClass: DateFnsDateTimeAdapter },
        { provide: OWL_DATE_TIME_FORMATS, useValue: DATEFNS_FORMATS_EN_LOCALE }
    ],
    exports: [
    	ComplianceDetailsComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ComplianceDetailsModule {

}








