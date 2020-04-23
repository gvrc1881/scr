import { NgModel, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FootPatrollingSectionsComponent } from './foot-patrolling-sections.component';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/modules/material.modules';
import { FootPatrollingSectionsService } from 'src/app/services/foot-patrolling-sections.service';
import { ReportService } from 'src/app/services/report.service';

const routes: Routes = [
    {
        path:'',
        component: FootPatrollingSectionsComponent,
    }
]

@NgModule({
    declarations: [
        FootPatrollingSectionsComponent
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule
        
    ],
    providers: [
        FootPatrollingSectionsService,
        ReportService
    ],
    exports: [
        FootPatrollingSectionsComponent
    ]
})
export class FootPatrollingSectionsModule{

}