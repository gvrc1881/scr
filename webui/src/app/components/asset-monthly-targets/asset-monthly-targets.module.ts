import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AssetMonthlyTargetsComponent } from './asset-monthly-targets.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddAssetMonthlyTargetsComponent } from './add-asset-monthly-targets/add-asset-monthly-targets.component';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime'; 
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { MatNativeDateModule } from '@angular/material/core';

const routes: Routes = [
    {
        path: '',
        component: AssetMonthlyTargetsComponent,
    },   
    {
        path     : ':id',
        component: AddAssetMonthlyTargetsComponent
    },
    {
        path        : 'add-asset-monthly-targets',
        component:   AddAssetMonthlyTargetsComponent
    }
   
];

@NgModule({
    declarations: [
        AssetMonthlyTargetsComponent, 
        AddAssetMonthlyTargetsComponent 
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
        DecimalValidationsModule,
        MatNativeDateModule
    ],
    providers: [
        
    ],
    exports: [
        AssetMonthlyTargetsComponent,
    ],
   
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AssetMonthlyTargetsModule {

}