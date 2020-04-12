import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DriveChecklistComponent } from './drive-checklist.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { DrivesService } from 'src/app/services/drives.service';
import { AddDriveChecklistComponent } from './add-drive-checklist/add-drive-checklist.component';

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
        Ng4LoadingSpinnerModule.forRoot()
    ],
    providers: [
        DrivesService
    ],
    exports:[
        DriveChecklistComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class DriveChecklistModule {

}