import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import{Ng4LoadingSpinnerModule}from 'ng4-loading-spinner';
import { MatMenuModule, MatGridListModule,MatSidenavModule, MatToolbarModule} from '@angular/material';
import { CommonModule } from '@angular/common';
import { ReportService } from 'src/app/services/report.service';
import {ReportNamesComponent} from './report-names.component';

//import {ReportParameterDisplayComponent} from '/home/opentaps/git/scr/SCR/scr-uiapp/src/app/components/reports/reportParameterDisplay.component'

const routes: Routes = [
    {
        path     : '',
        component :ReportNamesComponent
       
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
    declarations: [ReportNamesComponent/*,ReportParameterDisplayComponent*/ ],
     
    providers   : [
       // AnalyticsDashboardService
       ReportService
    ],
    exports     : [ReportNamesComponent /*,ReportParameterDisplayComponent*/]
})
export class ReportNamesModule
{
}

