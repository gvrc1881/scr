import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ApproveSpecialWorksComponent } from './approve-special-works.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddApproveSpecialWorksComponent } from './add-approve-special-works/add-approve-special-works.component';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';


const routes: Routes = [
    {
        path: '',
        component: ApproveSpecialWorksComponent,
    },   
    {
        path     : ':id',
        component: AddApproveSpecialWorksComponent 
    },
    {
        path        : 'add-approve-special-works',
        component:   AddApproveSpecialWorksComponent
    },
];

@NgModule({
    declarations: [
        ApproveSpecialWorksComponent,
        AddApproveSpecialWorksComponent,
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
        ApproveSpecialWorksComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class ApproveSpecialWorksModule {

}