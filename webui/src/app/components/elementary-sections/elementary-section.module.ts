import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ElementarySectionComponent } from './elementary-section.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime'; 
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { AddElementarySectionComponent } from './add-elementary-section/add-elementary-section.component';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';

const routes: Routes = [
    {
        path: '',
        component: ElementarySectionComponent,
    },
    {
        path     : ':id',
        component: AddElementarySectionComponent,
    },
    {
    	path: 'add-elementary-section',
    	component: AddElementarySectionComponent,
    },
    
    
];


@NgModule({
    declarations: [
        ElementarySectionComponent,
        AddElementarySectionComponent
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
    	ElementarySectionComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ElementarySectionModule {

}








