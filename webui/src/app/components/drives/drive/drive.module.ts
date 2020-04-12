import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DrivesComponent } from './drives.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddDriveComponent } from './add-drive/add-drive.component';
import { DrivesService } from 'src/app/services/drives.service';

const routes: Routes = [
    {
        path: '',
        component: DrivesComponent,
    },   
    {
        path     : ':id',
        component: AddDriveComponent
    },
    {
        path        : 'add-drive',
        component:   AddDriveComponent
    },
];

@NgModule({
    declarations: [
        DrivesComponent,
        AddDriveComponent,
        
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
        DrivesComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class DriveModule {

}