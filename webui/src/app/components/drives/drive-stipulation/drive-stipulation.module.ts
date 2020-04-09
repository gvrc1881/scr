import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DriveStipulationComponent } from './drive-stipulation.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddDriveStipulationComponent } from './add-drive-stipulation/add-drive-stipulation.component';
import { DrivesService } from 'src/app/services/drives.service';

const routes: Routes = [
    {
        path: '',
        component: DriveStipulationComponent,
    },   
    {
        path     : ':id',
        component: AddDriveStipulationComponent
    },
    {
        path        : 'add-drive',
        component:   AddDriveStipulationComponent
    },
];

@NgModule({
    declarations: [
        DriveStipulationComponent,
        AddDriveStipulationComponent,
        
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
        DriveStipulationComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class DriveStipulationModule {

}