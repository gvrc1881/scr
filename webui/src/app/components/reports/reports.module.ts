import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { MatMenuModule, MatGridListModule } from '@angular/material';
import { CommonModule } from '@angular/common';
import { ReportsComponent } from './reports.component';

const routes: Routes = [
    {
        path     : '', component:ReportsComponent,
        
       
    }
];

@NgModule({
    declarations: [
        ReportsComponent,
    ],
    imports     : [     
        RouterModule.forChild(routes),
        MatMenuModule,
        MatGridListModule,
        CommonModule
    ],    
    exports: [
        ReportsComponent
    ]
})
export class ReportsModule
{
}

