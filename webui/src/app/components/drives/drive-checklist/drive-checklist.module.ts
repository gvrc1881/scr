import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DriveChecklistComponent } from './drive-checklist.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddDriveChecklistComponent } from './add-drive-checklist/add-drive-checklist.component';
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';

const routes: Routes = [
    {
        path: '',
        component: DriveChecklistComponent,
    },   
    {
        path     : ':id',
        component: AddDriveChecklistComponent
    },
    {
        path        : 'add-drive-checklist',
        component:   AddDriveChecklistComponent
    },
];

@NgModule({
    declarations: [
        DriveChecklistComponent,
        AddDriveChecklistComponent,
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,      
        Ng4LoadingSpinnerModule.forRoot(),
        NumberValidationsModule,
        DecimalValidationsModule
    ],
    providers: [
        
    ],
    exports:[
        DriveChecklistComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class DriveChecklistModule {

}