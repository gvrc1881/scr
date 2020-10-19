import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AssetsScheduleComponent } from './assets-schedule.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddAssetsScheduleComponent } from './add-assets-schedule/add-assets-schedule.component';
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';

const routes: Routes = [
    {
        path: '',
        component: AssetsScheduleComponent,
    },   
    {
        path     : ':id',
        component: AddAssetsScheduleComponent
    },
    {
        path        : 'add-assets-schedule',
        component:   AddAssetsScheduleComponent
    },
];

@NgModule({
    declarations: [
        AssetsScheduleComponent,
        AddAssetsScheduleComponent,
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
        AssetsScheduleComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class AssetsScheduleModule {

}