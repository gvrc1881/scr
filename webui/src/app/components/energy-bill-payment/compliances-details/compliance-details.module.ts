import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComplianceDetailsComponent } from './compliance-details.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime'; 
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { AddComplianceDetailsComponent } from './add-compliance-details/add-compliance-details.component';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';

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
        
    ],
    exports: [
    	ComplianceDetailsComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ComplianceDetailsModule {

}








