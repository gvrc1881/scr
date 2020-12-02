import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { OheLocationAssetsComponent } from './ohe-location-assets.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddOheLocationAssetsComponent } from './add-ohe-location-assets/add-ohe-location-assets.component';
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';

const routes: Routes = [
    {
        path: '',
        component: OheLocationAssetsComponent,
    },   
    {
        path     : ':id',
        component: AddOheLocationAssetsComponent
    },
    {
        path        : 'add-ohe-location-assets',
        component:   AddOheLocationAssetsComponent
    },
];

@NgModule({
    declarations: [
        OheLocationAssetsComponent,
        AddOheLocationAssetsComponent,
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,      
        Ng4LoadingSpinnerModule.forRoot(),
        NumberValidationsModule,
        CommonDirectivesModule,
        DecimalValidationsModule
    ],
    providers: [
        
    ],
    exports:[
        OheLocationAssetsComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class OheLocationAssetsModule {

}