import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import{Ng4LoadingSpinnerModule}from 'ng4-loading-spinner';
import { MatMenuModule, MatGridListModule,MatSidenavModule, MatToolbarModule} from '@angular/material';
import { CommonModule } from '@angular/common';
import {ReportNamesComponent} from './report-names.component';

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
    declarations: [ReportNamesComponent],
     
    providers   : [
    ],
    exports     : [ReportNamesComponent]
})
export class ReportNamesModule
{
}

