import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddFailureAnalysisComponent } from './add-failure-analysis/add-failure-analysis.component';
import { DateTimeAdapter, OwlDateTimeModule, OwlNativeDateTimeModule, OWL_DATE_TIME_FORMATS } from 'ng-pick-datetime';
import { FailureAnalysisComponent } from './failure-analysis.component';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';
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
        component: FailureAnalysisComponent,
    },   
    {
        path     : ':id',
        component: AddFailureAnalysisComponent
    },
    {
        path        : 'add-failure-analysis',
        component:   AddFailureAnalysisComponent
    },
];

@NgModule({
    declarations: [
        FailureAnalysisComponent,
        AddFailureAnalysisComponent,
        
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,      
        Ng4LoadingSpinnerModule.forRoot(),
        OwlDateTimeModule, 
        CommonDirectivesModule,  
        OwlNativeDateTimeModule,        
    ],
    providers: [
        { provide: DateTimeAdapter, useClass: DateFnsDateTimeAdapter },
    { provide: OWL_DATE_TIME_FORMATS, useValue: DATEFNS_FORMATS_EN_LOCALE }
    ],
    exports:[
        FailureAnalysisComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class FailureAnalysisModule {

}