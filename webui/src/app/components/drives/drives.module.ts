import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
//import { DrivesComponent } from './drives.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
//import { AddDriveComponent } from './add-drive/add-drive.component';
import { DrivesService } from 'src/app/services/drives.service';
import { DriveTargetComponent } from './drive-target/drive-target.component';
import { AddDriveTargetComponent } from './drive-target/add-drive-target/add-drive-target.component';
import { DriveProgressRecordComponent } from './drive-progress-record/drive-progress-record.component';
import { AddDriveProgressRecordComponent } from './drive-progress-record/add-drive-progress-record/add-drive-progress-record.component';
import { DriveFailureAnalysisComponent } from './drive-failure-analysis/drive-failure-analysis.component';
import { AddDriveFailureAnalysisComponent } from './drive-failure-analysis/add-drive-failure-analysis/add-drive-failure-analysis.component';
import { DriveElectrificationTargetsComponent } from './drive-electrification-targets/drive-electrification-targets.component';
import { AddDriveElectrificationTargetsComponent } from './drive-electrification-targets/add-drive-electrification-targets/add-drive-electrification-targets.component';
import { DriveInspectionComponent } from './drive-inspection/drive-inspection.component';
import { AddDriveInspectionComponent } from './drive-inspection/add-drive-inspection/add-drive-inspection.component';
import { DriveStipulationComponent } from './drive-stipulation/drive-stipulation.component';
import { AddDriveStipulationComponent } from './drive-stipulation/add-drive-stipulation/add-drive-stipulation.component';

const routes: Routes = [
    {
        path: '',
        loadChildren: './drive/drive.module#DriveModule',
    },
    {
        path:'drive-checklist',
        loadChildren:'./drive-checklist/drive-checklist.module#DriveChecklistModule',
    }
    /* ,   
    {
        path     : ':id',
        component: AddDriveComponent
    },
    {
        path        : 'add-drive',
        component:   AddDriveComponent
    }, */
];

@NgModule({
    declarations: [
        //DrivesComponent,
        //AddDriveComponent,
        
    DriveTargetComponent,
        AddDriveTargetComponent,
        DriveProgressRecordComponent,
        AddDriveProgressRecordComponent,
        DriveFailureAnalysisComponent,
        AddDriveFailureAnalysisComponent,
        DriveElectrificationTargetsComponent,
        AddDriveElectrificationTargetsComponent,
        DriveInspectionComponent,
        AddDriveInspectionComponent,
        DriveStipulationComponent,
        AddDriveStipulationComponent],
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
       // DrivesComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class DrivesModule {

}