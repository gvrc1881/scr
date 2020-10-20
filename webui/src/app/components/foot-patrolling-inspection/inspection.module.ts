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
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime'; 
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';

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
        
    ],
    exports: [
        InspectionComponent,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InspectionModule {

}