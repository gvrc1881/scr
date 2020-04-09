import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard.component';
import { MatMenuModule, MatGridListModule } from '@angular/material';
import { CommonModule } from '@angular/common';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { FusionChartsModule } from 'angular-fusioncharts';
import { DashboardService } from 'src/app/services/dashboard.service';

const routes: Routes = [
    {
        path     : '',
        component: DashboardComponent,
       
    }
];

@NgModule({
    imports     : [     
        RouterModule.forChild(routes),
        Ng4LoadingSpinnerModule.forRoot(),
        MatMenuModule,
        MatGridListModule,
        CommonModule,
        FusionChartsModule
    ],
    declarations: [
        DashboardComponent
    ],
    providers   : [
        Ng4LoadingSpinnerService,
        DashboardService
    ],
    exports     : [
        DashboardComponent
    ]
})
export class DashboardModule
{
}

