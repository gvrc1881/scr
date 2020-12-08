import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DrivesTargetComponent } from './drives-target.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
//import { AddDrivesTargetComponent } from './add-drives-target/add-drives-target.component';
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';



const routes: Routes = [
    {
        path: '',
        component: DrivesTargetComponent,
    } ,{
        path     : ':id',
        component: DrivesTargetComponent
    },
    
];

@NgModule({
    declarations: [
        DrivesTargetComponent,
        //AddDrivesTargetComponent,
        
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,   
        NumberValidationsModule,
        DecimalValidationsModule,
        Ng4LoadingSpinnerModule.forRoot()
    ],
    providers: [
        
    ],
    exports:[
        DrivesTargetComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class DrivesTargetModule {

}