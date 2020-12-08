import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SpecialWorksComponent } from './special-works.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddSpecialWorksComponent } from './add-special-works/add-special-works.component';
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';

const routes: Routes = [
    {
        path: '',
        component: SpecialWorksComponent,
    },   
    {
        path     : ':id',
        component: AddSpecialWorksComponent
    },
    {
        path        : 'add-special-works',
        component:   AddSpecialWorksComponent
    },
];

@NgModule({
    declarations: [
        SpecialWorksComponent,
        AddSpecialWorksComponent,
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,      
        Ng4LoadingSpinnerModule.forRoot(),
        NumberValidationsModule,
        OwlNativeDateTimeModule,
        OwlDateTimeModule,
        CommonDirectivesModule,
        DecimalValidationsModule
    ],
    providers: [
        
    ],
    exports:[
        SpecialWorksComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class SpecialWorksModule {

}