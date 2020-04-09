import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { JobTypeComponent } from './job-type.component';
import { MatMenuModule, MatGridListModule } from '@angular/material';
import { CommonModule } from '@angular/common';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { MaterialModule } from 'src/app/modules/material.modules';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RepositoryService } from 'src/app/services/repository.service';
import { JobTypeService } from 'src/app/services/job-type.service';

const routes: Routes = [
    {
        path     : '',
        component: JobTypeComponent,
       
    }
];

@NgModule({
    imports     : [     
        RouterModule.forChild(routes),
        Ng4LoadingSpinnerModule.forRoot(),
        MatMenuModule,
        MatGridListModule,
        CommonModule,
        MaterialModule,
        FormsModule,
        ReactiveFormsModule
    ],
    declarations: [
        JobTypeComponent,        
    ],
    providers   : [
       JobTypeService
    ],
    exports     : [
        JobTypeComponent
    ]
})
export class JobTypeModule
{
}

