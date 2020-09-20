import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import{Ng4LoadingSpinnerModule}from 'ng4-loading-spinner';
import { MatMenuModule, MatGridListModule,MatSidenavModule, MatToolbarModule, MatSelectModule} from '@angular/material';
import { CommonModule } from '@angular/common';
import {ReportNamesComponent} from './report-names.component';
import { ReportByQueryComponent } from './report-by-query/report-by-query.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';

const routes: Routes = [
    {
        path     : '',
        component :ReportNamesComponent       
    },
    {
        path:'report-by-query/:reportName/:reportId',
        component:ReportByQueryComponent
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
        ReactiveFormsModule,
        FormsModule,
        MaterialModule,
        MatSelectModule
    ],
    declarations: [ReportNamesComponent, ReportByQueryComponent],
     
    providers   : [
    ],
    exports     : [ReportNamesComponent]
})
export class ReportNamesModule
{
}

