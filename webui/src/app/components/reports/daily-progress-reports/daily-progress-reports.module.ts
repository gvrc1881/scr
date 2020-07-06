import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import{Ng4LoadingSpinnerModule}from 'ng4-loading-spinner';
import { MatMenuModule, MatGridListModule,MatSidenavModule, MatToolbarModule} from '@angular/material';
import { CommonModule } from '@angular/common';
import {DailyProgressReportsComponent} from './daily-progress-reports.component';

const routes: Routes = [
    {
        path     : '',
        component :DailyProgressReportsComponent
       
    }
];

@NgModule({
    imports     : [     
        RouterModule.forChild(routes),
        Ng4LoadingSpinnerModule.forRoot(),
        MatMenuModule,
        MatGridListModule,
        CommonModule,
        MatToolbarModule,
        MatSidenavModule,
         
    ],
    declarations: [DailyProgressReportsComponent],
     
    providers   : [
    ],
    exports     : [DailyProgressReportsComponent]
})
export class DailyProgressReportsModule
{
}

