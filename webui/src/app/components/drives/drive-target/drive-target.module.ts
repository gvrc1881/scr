import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DriveTargetComponent } from './drive-target.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddDriveTargetComponent } from './add-drive-target/add-drive-target.component';
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';



const routes: Routes = [
    {
        path: '',
        component: DriveTargetComponent,
    },   
    {
        path     : ':id',
        component: AddDriveTargetComponent
    },
    {
        path        : 'add-drive-target',
        component:   AddDriveTargetComponent
    },
];

@NgModule({
    declarations: [
        DriveTargetComponent,
        AddDriveTargetComponent,
        
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
        DriveTargetComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class DriveTargetModule {

}