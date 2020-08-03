import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { PowerBlockComponent } from './power-block.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime'; 
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { AddPowerBlockComponent } from './add-power-block/add-power-block.component';

const routes: Routes = [
    {
        path: '',
        component: PowerBlockComponent,
    },
    {
        path     : ':id',
        component: AddPowerBlockComponent,
    },
    {
    	path: 'add-powerBlock',
    	component: AddPowerBlockComponent,
    },   
    
];


@NgModule({
    declarations: [
        PowerBlockComponent,
        AddPowerBlockComponent
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
    ],
    providers: [
        
    ],
    exports: [
    	PowerBlockComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PowerBlockModule {

}








