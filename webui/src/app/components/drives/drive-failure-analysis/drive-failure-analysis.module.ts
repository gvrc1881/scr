import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DriveFailureAnalysisComponent } from './drive-failure-analysis.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddDriveFailureAnalysisComponent } from './add-drive-failure-analysis/add-drive-failure-analysis.component';
import { DrivesService } from 'src/app/services/drives.service';

const routes: Routes = [
    {
        path: '',
        component: DriveFailureAnalysisComponent,
    },   
    {
        path     : ':id',
        component: AddDriveFailureAnalysisComponent
    },
    {
        path        : 'add-drive-failure-analysis',
        component:   AddDriveFailureAnalysisComponent
    },
];

@NgModule({
    declarations: [
        DriveFailureAnalysisComponent,
        AddDriveFailureAnalysisComponent,
        
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
        DriveFailureAnalysisComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class DriveFailureAnalysisModule {

}