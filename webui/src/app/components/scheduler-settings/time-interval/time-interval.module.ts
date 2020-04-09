import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TimeIntervalComponent } from './time-interval.component';
import { MatMenuModule, MatGridListModule } from '@angular/material';
import { CommonModule, DatePipe } from '@angular/common';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { TimeIntervalService } from 'src/app/services/time-interval.service';

const routes: Routes = [
    {
        path     : '',
        component: TimeIntervalComponent,
       
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
        TimeIntervalComponent,        
    ],
    providers   : [
       TimeIntervalService,
       DatePipe
    ],
    exports     : [
        TimeIntervalComponent
    ]
})
export class TimeIntervalModule
{
}

