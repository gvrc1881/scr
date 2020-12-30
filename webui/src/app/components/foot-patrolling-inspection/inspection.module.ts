import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { InspectionComponent } from './inspection.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddFpInspectionComponent } from './add-fp-inspection/add-fp-inspection.component';
import { AddObservationComponent } from './add-observation/add-observation.component';
import { AddComplianceComponent } from './add-compliance/add-compliance.component';
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
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
        component: InspectionComponent,
    },
    {
        path: 'inspection/:id',
        component: AddFpInspectionComponent
    },
    {
        path: 'add-fp-inspection',
        component: AddFpInspectionComponent,
    },
    {
        path: 'add-observation/:insId',
        component: AddObservationComponent,
    },
    {
        path: 'observation/:id',
        component: AddObservationComponent,
    },
    
    {
        path: 'add-compliance/:obsId',
        component: AddComplianceComponent
    },
    {
        path: 'compliance/:id',
        component: AddComplianceComponent
    },
];

@NgModule({
    declarations: [
        InspectionComponent,
        AddFpInspectionComponent,
        AddObservationComponent,
        AddComplianceComponent,
        
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,
        NumberValidationsModule,
        Ng4LoadingSpinnerModule.forRoot(),
        OwlDateTimeModule, 
        OwlNativeDateTimeModule,
        CommonDirectivesModule,
        DecimalValidationsModule
    ],
    providers: [
        Ng4LoadingSpinnerService,
        { provide: DateTimeAdapter, useClass: DateFnsDateTimeAdapter },
        { provide: OWL_DATE_TIME_FORMATS, useValue: DATEFNS_FORMATS_EN_LOCALE }   
    ],
    exports: [
        InspectionComponent,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InspectionModule {

}