import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SchedulerSettingsComponent } from './scheduler-settings.component';
import { MatMenuModule, MatGridListModule } from '@angular/material';
import { CommonModule } from '@angular/common';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { RepositoryService } from 'src/app/services/repository.service';
import { ScheduleJobService } from 'src/app/services/schedule-job.service';

const routes: Routes = [
    {
        path     : '',
        component: SchedulerSettingsComponent,
       
    }
];

@NgModule({
    imports     : [     
        RouterModule.forChild(routes),
        Ng4LoadingSpinnerModule.forRoot(),
        MatMenuModule,
        MatGridListModule,
        CommonModule,
        MatMenuModule,
        MatGridListModule,
        CommonModule,
        MaterialModule,
        FormsModule,
        ReactiveFormsModule
    ],
    declarations: [
        SchedulerSettingsComponent,        
    ],
    providers   : [
       ScheduleJobService
    ],
    exports     : [
        SchedulerSettingsComponent
    ]
})
export class SchedulerSettingsModule
{
}

