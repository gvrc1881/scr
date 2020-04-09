import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DriveInspectionComponent } from './drive-inspection.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddDriveInspectionComponent } from './add-drive-inspection/add-drive-inspection.component';
import { DrivesService } from 'src/app/services/drives.service';

const routes: Routes = [
    {
        path: '',
        component: DriveInspectionComponent,
    },   
    {
        path     : ':id',
        component: AddDriveInspectionComponent
    },
    {
        path        : 'add-drive',
        component:   AddDriveInspectionComponent
    },
];

@NgModule({
    declarations: [
        DriveInspectionComponent,
        AddDriveInspectionComponent,
        
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,      
        Ng4LoadingSpinnerModule.forRoot()
    ],
    providers: [
        DrivesService
    ],
    exports:[
        DriveInspectionComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class DriveInspectionModule {

}