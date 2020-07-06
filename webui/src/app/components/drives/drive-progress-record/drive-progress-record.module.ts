import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DriveProgressRecordComponent } from './drive-progress-record.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddDriveProgressRecordComponent } from './add-drive-progress-record/add-drive-progress-record.component';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime'; 
const routes: Routes = [
    {
        path: '',
        component: DriveProgressRecordComponent,
    },   
    {
        path     : ':id',
        component: AddDriveProgressRecordComponent
    },
    {
        path        : 'add-drive-progress-record',
        component:   AddDriveProgressRecordComponent
    },
];

@NgModule({
    declarations: [
        DriveProgressRecordComponent,
        AddDriveProgressRecordComponent,
        
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,      
        Ng4LoadingSpinnerModule.forRoot(),
        OwlDateTimeModule, 
        OwlNativeDateTimeModule,
    ],
    providers: [
        
    ],
    exports:[
        DriveProgressRecordComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class DriveProgressRecordModule {

}